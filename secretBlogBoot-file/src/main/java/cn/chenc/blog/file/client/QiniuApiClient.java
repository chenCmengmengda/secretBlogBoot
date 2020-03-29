package cn.chenc.blog.file.client;



import cn.chenc.blog.file.exception.FileUploadException;
import cn.chenc.blog.file.exception.QiniuUploadException;
import cn.chenc.blog.file.model.FileModel;
import cn.chenc.blog.file.model.FileUploadPath;
import cn.chenc.blog.file.response.FileResponse;
import cn.chenc.blog.file.response.FileUploadResponse;
import cn.chenc.blog.file.util.FileUtil;
import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * 七牛云存储操作
 */
public class QiniuApiClient extends BaseApiClient {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String path;
    private String dir;

    public QiniuApiClient() {
        super("七牛云");
    }

    public QiniuApiClient init(String accessKey, String secretKey, String bucketName, String baseUrl, String dir) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucket = bucketName;
        this.path = baseUrl;
        this.dir = StringUtils.isEmpty(dir)||"/".equals(dir)? FileUploadPath.COMMEN.getPath() : dir.endsWith("/")?dir:dir+"/";
        return this;
    }


    /**
     * 上传图片
     *
     * @param is  文件流
     * @param imageUrl 文件路径
     * @return 上传后的路径
     */
    @Override
    public FileUploadResponse upload(InputStream is, String imageUrl) {
        this.check();
        if(this.dir.startsWith("/")){
            this.dir = this.dir.substring(1);
        }
        String key = FileUtil.generateTempFileName(imageUrl);
        this.createNewFileName(key, FileUploadPath.QINIU_BASE_PATH.getPath()+this.dir);
        Date startTime = new Date();
        //Zone.zone0:华东
        //Zone.zone1:华北
        //Zone.zone2:华南
        //Zone.zoneNa0:北美
        Configuration cfg = new Configuration(Zone.autoZone());
        UploadManager uploadManager = new UploadManager(cfg);
        try {
            Auth auth = Auth.create(this.accessKey, this.secretKey);
            String upToken = auth.uploadToken(this.bucket);
            Response response = uploadManager.put(is, this.newFileName, upToken, null, null);

            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);

            return new FileUploadResponse()
                    .setCode(200)
                    .setMsg("上传成功")
                    .setDir(dir)
                    .setOriginalFileName(key)
                    .setUploadStartTime(startTime)
                    .setUploadEndTime(new Date())
                    .setFilePath(putRet.key)
                    .setFileHash(putRet.hash)
                    .setFullFilePath(this.path + putRet.key);
        } catch (QiniuException ex) {
            throw new FileUploadException("[" + this.storageType + "]文件上传失败：" + ex.error());
        }
    }

    /**
     * 列举七牛云中图片
     * @param dir 当前目录
     * @param accept    筛选文件类型
     * @param exts  扩展后缀名
     * @return
     */
    @Override
    public FileResponse listFiles(String dir, String accept, String exts){
        String[] mExts = null;
        if (exts != null && !exts.trim().isEmpty()) {
            mExts = exts.split(",");
        }
        Map<String, Object> rs = new HashMap<>();
        // 如果是根目录，就直接展示rootPath下的文件
        if (dir == null || "/".equals(dir)) {
            dir = "";
        } else if (dir.startsWith("/")) {
            dir = dir.substring(1);
        }
        //
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.autoZone());
        Auth auth = Auth.create(this.accessKey, this.secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 100;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        List<FileModel> dataList = new ArrayList<>();
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, FileUploadPath.QINIU_BASE_PATH.getPath()+dir, limit, delimiter);
        /*  获取公共目录
        FileListing fl = null;
        try {
            fl = bucketManager.listFilesV2(bucket,uploadType,"",limit,delimiter);
        }catch (QiniuException ex){

        }
        String[] prefixs = fl.commonPrefixes;
        for(String prefix:prefixs){
            System.out.println("####"+prefix);
        }*/
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            Set<String> commonPrefixs = new HashSet();

            for (FileInfo item : items) {
                FileModel fileModel = new FileModel();
                String filePath = item.key.substring((FileUploadPath.QINIU_BASE_PATH.getPath()+dir).length());
                //  fs/common/abc.jpg   fs/abc.jpg
                if(filePath.indexOf("/")>-1){
                    //获取公共目录
                    commonPrefixs.add(filePath.substring(0,filePath.indexOf("/")));
                }else{
                    //fileModel.setName(item.key.substring(item.key.lastIndexOf("/")));
                    fileModel.setName(filePath);
                    fileModel.setFilePath(item.key);
                    fileModel.setUrl(this.path+item.key);
                    fileModel.setType(FileUtil.getFileType(fileModel.getUrl())); // 设置type
                    fileModel.setSize(item.fsize);
                    if(item.mimeType.startsWith("image/")){
                        fileModel.setHasSm(true);
                        fileModel.setSmUrl(this.path+item.key);
                    }
                    fileModel.setUpdateTime(item.putTime);
                    dataList.add(fileModel);
                }

            }
            commonPrefixs.forEach(item->{
                FileModel fileModel = new FileModel();
                fileModel.setType("dir");
                fileModel.setIsDir(true);
                fileModel.setName(item);
                dataList.add(fileModel);
            });

            // 根据上传时间排序
            Collections.sort(dataList, new Comparator<FileModel>() {
                public int compare(FileModel m1, FileModel m2) {
                    Long l1 = m1.getUpdateTime();
                    Long l2 = m2.getUpdateTime();
                    return l1.compareTo(l2);
                }
            });
            // 把文件夹排在前面
            Collections.sort(dataList, new Comparator<FileModel>() {
                public int compare(FileModel m1, FileModel m2) {
                    Boolean l1 = m1.getIsDir();
                    Boolean l2 = m2.getIsDir();
                    return l2.compareTo(l1);
                }
            });
            return new FileResponse()
                    .setCode(200)
                    .setData(dataList);
        }
        return new FileResponse();
    }
    /**
     * 删除七牛空间图片方法
     *
     * @param key 七牛空间中文件名称,不带签名的域名
     */
    @Override
    public boolean removeFile(String key) {
        this.check();

        if (StringUtils.isEmpty(key)) {
            throw new QiniuUploadException("[" + this.storageType + "]删除文件失败：文件key为空");
        }
        Auth auth = Auth.create(this.accessKey, this.secretKey);
        Configuration config = new Configuration(Zone.autoZone());
        BucketManager bucketManager = new BucketManager(auth, config);
        try {
            Response re = bucketManager.delete(this.bucket, key);
            return re.isOK();
        } catch (QiniuException e) {
            Response r = e.response;
            throw new FileUploadException("[" + this.storageType + "]删除文件发生异常：" + r.error);
        }
    }

    @Override
    public boolean deleteDirectory(String sPath) {
       return true;
    }

    @Override
    public FileResponse renameFile(String path,String oldname,String newname){
        return new FileResponse();
    }

    @Override
    public void check() {
        if (StringUtils.isEmpty(this.accessKey) || StringUtils.isEmpty(this.secretKey) || StringUtils.isEmpty(this.bucket)) {
            throw new FileUploadException("[" + this.storageType + "]尚未配置七牛云，文件上传功能暂时不可用！");
        }
    }

    public String getPath() {
        return this.path;
    }
}
