package cn.chenc.blog.file.client;


import cn.chenc.blog.file.exception.FileException;
import cn.chenc.blog.file.exception.FileUploadException;
import cn.chenc.blog.file.model.FileConstant;
import cn.chenc.blog.file.model.FileModel;
import cn.chenc.blog.file.model.FileUploadPath;
import cn.chenc.blog.file.response.FileResponse;
import cn.chenc.blog.file.response.FileUploadResponse;
import cn.chenc.blog.file.util.FileUtil;
import cn.chenc.blog.file.util.StreamUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tika.Tika;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 本地上传
 */
@Slf4j
public class LocalApiClient extends BaseApiClient {

    private String rootPath;
    private Boolean useSm; //是否生成缩略图
    private Boolean useNginx; //是否开启Nginx
    private String nginxUrl; // nginx的地址
    private String serverUrl; // 服务器地址，默认admin的地址
    private String dir;


    public LocalApiClient() {
        super("本地服务器");
    }

    public LocalApiClient init(String useSm,String serverUrl,String useNginx,String nginxUrl,
                               String rootPath,String dir) {
        this.nginxUrl = nginxUrl;
        this.serverUrl = serverUrl;
        this.useSm = (!StringUtils.isEmpty(useSm)&&"0".equals(useSm))?false:true; // 默认为true
        this.useNginx = (!StringUtils.isEmpty(useNginx)&&"0".equals(useNginx))?false:true; // 默认为false
        this.rootPath = StringUtils.isEmpty(rootPath)? "/" : rootPath.endsWith("/")?rootPath:rootPath+"/";
        this.dir = StringUtils.isEmpty(dir)||"/".equals(dir)? FileUploadPath.COMMEN.getPath() : dir.endsWith("/")?dir:dir+"/";
       // this.dir = this.dir.startsWith("/")?this.dir.substring(1):this.dir;
        //this.pathPrefix = rootPath+this.dir;
        return this;
    }


    public String generateSmFile(String fileUrl){
        // 这个文件一定要是存在的
        File file = new File(fileUrl);
        // 获取文件类型
        String contentType = null;
        try {
            // 下面的方法如果在linux，获取为空 所以改成了 new Tika().detect(file);
            // contentType = Files.probeContentType(Paths.get(outFile.getName()));
            contentType = new Tika().detect(file);
            if (contentType != null && contentType.startsWith("image/")) {
                File smImg = new File(rootPath + "sm/" + newFileName);
                if (!smImg.getParentFile().exists()) {
                    smImg.getParentFile().mkdirs();
                }
                // 压缩图片,如果是gif图片，无法使用Thumbnails压缩
                if ("image/gif".equals(contentType)) {
                    FileUtil.copy(file,smImg,true);
                }else{
                    Thumbnails.of(file).scale(1f).outputQuality(0.25f).toFile(smImg);
                }

                return "sm/"+newFileName;
            }
        } catch (IOException e) {
            throw new FileUploadException("[" + this.storageType + "]文件缩略图生成失败：" + e.getMessage() + fileUrl);
        }
        return null;
    }
    @Override
    public FileUploadResponse upload(InputStream is, String fileUrl){
        this.check();
        // 生成 temp.png
        if(this.dir.startsWith("/")){
            this.dir = this.dir.substring(1);
        }
        String key = FileUtil.generateTempFileName(fileUrl);
        this.createNewFileName(key,dir);
        Date startTime = new Date();
        String realFilePath = this.rootPath + this.newFileName;
        FileUtil.checkFilePath(realFilePath);
        FileUploadResponse response = new FileUploadResponse();
        try (InputStream uploadIs = StreamUtil.clone(is);
             InputStream fileHashIs = StreamUtil.clone(is);
             FileOutputStream fos = new FileOutputStream(realFilePath)) {
            FileCopyUtils.copy(uploadIs, fos);
            if(useSm != null && useSm){
                response.setSmUrl(generateSmFile(realFilePath));
            }
            return response
                    .setCode(200)
                    .setMsg("上传成功")
                    .setDir(dir)
                    .setOriginalFileName(FileUtil.getName(key))
                    .setUploadStartTime(startTime)
                    .setUploadEndTime(new Date())
                    .setFilePath(this.newFileName) //带前缀
                    .setFileHash(DigestUtils.md5DigestAsHex(fileHashIs))
                    .setUrl(this.serverUrl + FileConstant.LOCAL_URL_API_PREFIX + this.newFileName)
                    .setFullFilePath(this.serverUrl + FileConstant.LOCAL_URL_API_PREFIX + this.newFileName);
        } catch (Exception e) {
            throw new FileUploadException("[" + this.storageType + "]文件上传失败：" + e.getMessage() + fileUrl);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除本地文件，包括缩略图
     * @param key
     * @return
     */
    @Override
    public boolean removeFile(String key) {
        this.check();
        if (StringUtils.isEmpty(key)) {
            throw new FileException("[" + this.storageType + "]删除文件失败：文件key为空");
        }
        File file = new File(this.rootPath + key);
        if (!file.exists()) {
            throw new FileException("[" + this.storageType + "]删除文件失败：文件不存在[" + this.rootPath + key + "]");
        }
        try {
            if(file.delete()){
                // 删除缩略图
                if(useSm != null && useSm){
                    File smFile = new File(this.rootPath+"sm/"+key);
                    if (smFile.exists()) {
                        smFile.delete();
                    }
                }

            }
            return true;
        } catch (Exception e) {
            throw new FileException("[" + this.storageType + "]删除文件失败：" + e.getMessage());
        }
    }

    @Override
    public boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(this.rootPath+sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            if(dirFile.isFile()){//如果是文件，直接删除
                removeFile(sPath);
            }
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = files[i].delete();
                if(flag) {
                    if (useSm != null && useSm) {//删除缩略图
                        File smFile = new File(this.rootPath + "sm/" +sPath +files[i].getName());
                        if (smFile.exists()) {
                            smFile.delete();
                        }
                    }
                }
                if (!flag) break;
            } else { //删除子目录
                flag = deleteDirectory(sPath+files[i].getName()+"/");
                deleteDirectory("sm/"+sPath+files[i].getName()+"/");
                //删除缩略图子目录
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            File smDir = new File(this.rootPath + "sm/" +sPath);//删除缩略图目录
            smDir.delete();
            return true;
        } else {
            return false;
        }
    }

    /**
     * @description: 文件重命名
     * @param [path, oldname, newname]
     * @return boolean
     * @throws
     * @author 陈_C
     * @date 2020/3/29 陈_C
     */
    public FileResponse renameFile(String path,String oldname,String newname){
        File oldfile=new File(this.rootPath+path+oldname);
        File oldsmfile=new File(this.rootPath+"sm"+path+oldname);
        FileResponse fileResponse=new FileResponse();
        if(oldfile.isFile()) {//如果老文件是文件类型，则获取后缀
            int lastIndex=oldname.lastIndexOf(".");
            if(lastIndex != -1) {//确定文件存在后缀
                String suffix = oldname.substring(lastIndex);//获取文件后缀  .xxx
                newname = newname + suffix;
            }
        }
        if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名
            File newfile=new File(this.rootPath+path+newname);
            File newsmfile=new File(this.rootPath+"sm"+path+newname);//缩略图
            if(!oldfile.exists()){
                return fileResponse.setCode(400).setMsg("重命名文件不存在");//重命名文件不存在
            }
            if(newfile.exists()) {//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                System.out.println(newname + "已经存在！");
                return fileResponse.setCode(400).setMsg("文件名重复");
            } else{
                oldfile.renameTo(newfile);
                oldsmfile.renameTo(newsmfile);
            }
        }else{
            System.out.println("新文件名和旧文件名相同...");
        }
        return fileResponse.setCode(200).setMsg("重命名成功");
    }

    @Override
    public void check() {
        if((useNginx != null || useNginx) && StringUtils.isEmpty(nginxUrl)){
            useNginx =false;
            log.warn("[{}]尚未配置Nginx文件服务器或者配置错误");
        }
        if (StringUtils.isEmpty(this.serverUrl) || StringUtils.isEmpty(rootPath)) {
            throw new FileUploadException("[" + this.storageType + "]服务地址配置错误，文件上传功能暂时不可用！");
        }
    }

    /**
     * 本地文件列表
     * @param dir 当前目录
     * @param accept    筛选文件类型
     * @param exts  扩展后缀名
     * @return
     */
    @Override
    public FileResponse listFiles(String dir, String accept, String exts) {
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
        File file = new File(rootPath + dir);
        File[] listFiles = file.listFiles();
        List<FileModel> dataList = new ArrayList<>();
        FileResponse response = new FileResponse();
        if (listFiles != null) {
            // 遍历文件夹下的文件
            for (File f : listFiles) {
                //排除缩略图显示
                if ("sm".equals(f.getName())) {
                    continue;
                }
                FileModel fileModel = new FileModel();
                fileModel.setName(f.getName());
                fileModel.setUpdateTime(f.lastModified());
                fileModel.setIsDir(f.isDirectory());
                if (f.isDirectory()) {
                    fileModel.setType("dir");
                    fileModel.setFilePath(dir+f.getName());
                } else {
                    String type = FileUtil.getFileType(f); // 获取文件类型
                    fileModel.setType(type);
                    fileModel.setFilePath(dir+f.getName()); // 文件地址
                    if(useNginx && !StringUtils.isEmpty(nginxUrl)){
                        fileModel.setUrl(this.nginxUrl+dir+f.getName()); //带域名的完整地址
                    } else {
                        fileModel.setUrl(this.serverUrl + FileConstant.LOCAL_URL_API_PREFIX + dir + f.getName()); //带域名的完整地址
                    }
                    // 获取文件类型
                    String contentType = FileUtil.getContentType(f);
                    // 筛选文件类型
                    if (accept != null && !accept.trim().isEmpty() && !accept.equals("file")) {
                        if (contentType == null || !contentType.startsWith(accept + "/")) {
                            continue;
                        }
                        if (mExts != null) {
                            for (String ext : mExts) {
                                if (!f.getName().endsWith("." + ext)) {
                                    continue;
                                }
                            }
                        }
                    }
                    // 是否有缩略图
                    String smUrl = "sm/" + dir + f.getName();
                    if (new File(rootPath + smUrl).exists()) {
                        fileModel.setHasSm(true);
                        if(this.useNginx){//如果开启nginx，进入nginx映射地址
                            fileModel.setSmUrl(this.nginxUrl + smUrl); // 缩略图地址
                        } else {
                            fileModel.setSmUrl(this.serverUrl + FileConstant.LOCAL_URL_API_PREFIX + smUrl); // 缩略图地址
                        }
                    }
                }
                dataList.add(fileModel);
            }
        }
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
        response.setData(dataList);
        response.setMsg("查询成功");
        response.setCode(200);
        return response;
    }
}
