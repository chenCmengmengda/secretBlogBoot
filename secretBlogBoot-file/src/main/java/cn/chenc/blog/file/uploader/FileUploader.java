package cn.chenc.blog.file.uploader;

import cn.chenc.blog.file.response.FileResponse;
import cn.chenc.blog.file.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;


/**
 * 文件上传
 */
public interface FileUploader {

    /**
     * 上传文件
     * @param file  文件
     * @param uploadType 上传文件类型
     * @return
     */
    FileUploadResponse upload(MultipartFile file, String uploadType);

    FileUploadResponse upload(File file, String uploadType);

    FileUploadResponse upload(InputStream is, String fileUrl, String uploadType);

    FileUploadResponse uploadImg(MultipartFile file, String uploadType);

    FileResponse listFiles(String dir, String accept, String exts, String uploadType);

    boolean removeFile(String key);

    boolean removeDir(String key);

    FileResponse rename(String path, String oldname, String newname);

}
