package cn.chenc.blog.file.client;

import cn.chenc.blog.file.util.FileUtil;
import cn.hutool.core.date.DateUtil;
import cn.chenc.blog.file.exception.FileException;
import cn.chenc.blog.file.exception.FileUploadException;
import cn.chenc.blog.file.response.FileUploadResponse;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

/**
 *
 */
public abstract class BaseApiClient implements ApiClient {

    protected String storageType;
    protected String newFileName;
    protected String suffix;

    public BaseApiClient(String storageType) {
        this.storageType = storageType;
    }


    /**
     * 创建新文件名
     * @param key
     * @param pathPrefix
     */
    void createNewFileName(String key, String pathPrefix) {
        // 获取后缀 .png
        this.suffix = FileUtil.getSuffix(key);
        /*if (!FileUtil.isImg(this.suffix)) {
            throw new FileException("[" + this.storageType + "] 非法的图片文件[" + key + "]！目前只支持以下图片格式：[jpg, jpeg, png, gif, bmp]");
        }*/
        String fileName = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        this.newFileName = pathPrefix + (fileName + this.suffix);
    }

    @Override
    public FileUploadResponse upload(MultipartFile file) {
        this.check();
        if (file == null) {
            throw new FileException("[" + this.storageType + "]文件上传失败：文件不可为空");
        }
        try {
            FileUploadResponse res = this.upload(file.getInputStream(), file.getOriginalFilename());
            FileUploadResponse fileInfo = FileUtil.getFileInfo(file);
            return res.setCode(200)
                    .setSize(fileInfo.getSize())
                    .setOriginalFileName(file.getOriginalFilename());
        } catch (IOException e) {
            throw new FileException("[" + this.storageType + "]文件上传失败：" + e.getMessage());
        }
    }

    @Override
    public FileUploadResponse upload(File file) {
        this.check();
        if (file == null) {
            throw new FileException("[" + this.storageType + "]文件上传失败：文件不可为空");
        }
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            FileUploadResponse res = this.upload(is, "temp" + FileUtil.getSuffix(file));
            FileUploadResponse fileInfo = FileUtil.getFileInfo(file);
            return res.setCode(200)
                    .setSize(fileInfo.getSize())
                    .setOriginalFileName(file.getName());
        } catch (FileNotFoundException e) {
            throw new FileException("[" + this.storageType + "]文件上传失败：" + e.getMessage());
        }
    }

    /**
     * 上传图片
     * @param file
     * @return
     */
    @Override
    public FileUploadResponse uploadImg(MultipartFile file) {
        if(FileUtil.isImg(FileUtil.getSuffix(file.getOriginalFilename()))){
            throw new FileUploadException("不是图片文件，上传失败");
        }
        if (file == null) {
            throw new FileException("[" + this.storageType + "]图片文件上传失败：文件不可为空");
        }
        try{
            FileUploadResponse res = this.upload(file.getInputStream(), file.getOriginalFilename());
            FileUploadResponse imageInfo = FileUtil.getImageInfo(file.getInputStream());
            return res.setCode(200)
                    .setSize(imageInfo.getSize())
                    .setOriginalFileName(file.getOriginalFilename())
                    .setWidth(imageInfo.getWidth())
                    .setHeight(imageInfo.getHeight());
        }catch (IOException ex){
            throw new FileUploadException("[" + this.storageType + "]图片文件上传失败：" + ex.getMessage());
        }
    }

    protected abstract void check();

}
