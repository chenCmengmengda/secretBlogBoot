package cn.chenc.blog.file.util;


import cn.chenc.blog.file.exception.FileException;
import cn.chenc.blog.file.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

/**
 * 操作图片工具类
 *
 */
public class ImageUtil {

    /**
     * 获取图片信息
     *
     * @param file
     * @throws IOException
     */
    public static FileUploadResponse getInfo(File file) {
        if (null == file) {
            return new FileUploadResponse();
        }
        try {
            return getInfo(new FileInputStream(file))
                    .setSize(file.length())
                    .setOriginalFileName(file.getName())
                    .setSuffix(FileUtil.getSuffix(file.getName()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileException("获取图片信息发生异常！", e);
        }
    }

    /**
     * 获取图片信息
     *
     * @param multipartFile
     * @throws IOException
     */
    public static FileUploadResponse getInfo(MultipartFile multipartFile) {
        if (null == multipartFile) {
            return new FileUploadResponse();
        }
        try {
            return getInfo(multipartFile.getInputStream())
                    .setSize(multipartFile.getSize())
                    .setOriginalFileName(multipartFile.getOriginalFilename())
                    .setSuffix(FileUtil.getSuffix(multipartFile.getOriginalFilename()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileException("获取图片信息发生异常！", e);
        }
    }

    /**
     * 获取图片信息
     *
     * @param inputStream
     * @throws IOException
     */
    public static FileUploadResponse getInfo(InputStream inputStream) {
        try (BufferedInputStream in = new BufferedInputStream(inputStream)) {
            //字节流转图片对象
            Image bi = ImageIO.read(in);
            if (null == bi) {
                return new FileUploadResponse();
            }
            //获取默认图像的高度，宽度
            return new FileUploadResponse()
                    .setWidth(bi.getWidth(null))
                    .setHeight(bi.getHeight(null))
                    .setSize((long)inputStream.available());
        } catch (Exception e) {
            throw new FileException("获取图片信息发生异常！", e);
        }
    }
}
