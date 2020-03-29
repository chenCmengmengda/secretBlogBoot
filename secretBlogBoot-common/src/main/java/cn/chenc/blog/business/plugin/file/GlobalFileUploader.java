package cn.chenc.blog.business.plugin.file;


import cn.chenc.blog.file.client.ApiClient;
import cn.chenc.blog.file.response.FileResponse;
import cn.chenc.blog.file.response.FileUploadResponse;
import cn.chenc.blog.file.uploader.FileUploader;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

public class GlobalFileUploader extends BaseFileUpload implements FileUploader {



    @Override
    public FileUploadResponse upload(MultipartFile file, String dir) {
        ApiClient client = this.getApiClient(dir);
        return client.upload(file);
    }

    @Override
    public FileUploadResponse upload(File file, String dir) {
        ApiClient client = this.getApiClient(dir);
        return client.upload(file);
    }

    @Override
    public FileUploadResponse upload(InputStream is, String fileUrl,String dir) {
        ApiClient client = this.getApiClient(dir);
        return client.upload(is,fileUrl);
    }

    @Override
    public FileUploadResponse uploadImg(MultipartFile file, String dir) {
        ApiClient client = this.getApiClient(dir);
        return client.uploadImg(file);
    }

    @Override
    public FileResponse listFiles(String dir, String accept, String exts, String uploadType) {
        ApiClient client = this.getApiClient(uploadType);
        return client.listFiles(dir,accept,exts);
    }

    @Override
    public boolean removeFile(String fileName) {
        ApiClient client = this.getApiClient(null);
        return client.removeFile(fileName);
    }

    @Override
    public boolean removeDir(String fileName){
        ApiClient client = this.getApiClient(null);
        return client.deleteDirectory(fileName);
    }

    @Override
    public FileResponse rename(String path, String oldname, String newname){
        ApiClient client = this.getApiClient(null);
        return client.renameFile(path, oldname, newname);
    }
}
