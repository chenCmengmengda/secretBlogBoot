package cn.chenc.blog.file.client;

import cn.chenc.blog.file.response.FileResponse;
import cn.chenc.blog.file.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

/**
 *
 */
public interface ApiClient {

    /**
     * 上传文件
     * @param file
     * @return
     */
    FileUploadResponse upload(MultipartFile file);

    /**
     * 上传文件
     * @param file
     * @return
     */
    FileUploadResponse upload(File file);

    /**
     * 上传文件
     * @param is
     * @param fileUrl
     * @return
     */
    FileUploadResponse upload(InputStream is, String fileUrl);

    /**
     * 上传图片
     * @param file
     * @return
     */
    FileUploadResponse uploadImg(MultipartFile file);

    /**
     * 展示文件
     * @param dir 当前目录
     * @param accept    筛选文件类型
     * @param exts  扩展后缀名
     * @return
     */
    FileResponse listFiles(String dir, String accept, String exts);

    /**
     * 删除文件
     * @param key
     * @return
     */
    boolean removeFile(String key);

    boolean deleteDirectory(String sPath);

    FileResponse renameFile(String path,String oldname,String newname);
}
