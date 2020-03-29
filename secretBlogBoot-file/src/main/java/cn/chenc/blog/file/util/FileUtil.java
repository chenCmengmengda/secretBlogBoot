package cn.chenc.blog.file.util;


import cn.chenc.blog.file.exception.FileException;
import cn.chenc.blog.file.response.FileUploadResponse;
import org.apache.tika.Tika;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * 文件处理类
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {

    private static final String[] IMG_SUFFIXS = {".jpg", ".jpeg", ".png", ".gif", ".bmp", ".svg"};

    /**
     * 删除目录，返回删除的文件数
     *
     * @param rootPath 待删除的目录
     * @param fileNum  已删除的文件个数
     * @return 已删除的文件个数
     */
    public static int deleteFiles(String rootPath, int fileNum) {
        File file = new File(rootPath);
        if (!file.exists()) {
            return -1;
        }
        if (file.isDirectory()) {
            File[] sonFiles = file.listFiles();
            if (sonFiles != null && sonFiles.length > 0) {
                for (File sonFile : sonFiles) {
                    if (sonFile.isDirectory()) {
                        fileNum = deleteFiles(sonFile.getAbsolutePath(), fileNum);
                    } else {
                        sonFile.delete();
                        fileNum++;
                    }
                }
            }
        } else {
            file.delete();
        }
        return fileNum;
    }


    public static String getPrefix(File file) {
        return getPrefix(file.getName());
    }

    public static String getPrefix(String fileName) {
        int idx = fileName.lastIndexOf(".");
        int xie = fileName.lastIndexOf("/");
        idx = idx == -1 ? fileName.length() : idx;
        xie = xie == -1 ? 0 : xie + 1;
        return fileName.substring(xie, idx);
    }

    public static String getSuffix(File file) {
        return getSuffix(file.getName());
    }

    public static String getSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        index = -1 == index ? fileName.length() : index;
        return fileName.substring(index);
    }

    public static String getSuffixByUrl(String imgUrl) {
        String defaultSuffix = ".png";
        if (StringUtils.isEmpty(imgUrl)) {
            return defaultSuffix;
        }
        String fileName = imgUrl;
        if(imgUrl.contains("/")) {
            fileName = imgUrl.substring(imgUrl.lastIndexOf("/"));
        }
        String fileSuffix = getSuffix(fileName);
        return StringUtils.isEmpty(fileSuffix) ? defaultSuffix : fileSuffix;
    }

    public static String generateTempFileName(String imgUrl) {
        return "temp" + getSuffixByUrl(imgUrl);
    }

    /**
     * 判断是否是图片
     * @param suffix
     * @return
     */
    public static boolean isImg(String suffix) {
        return !StringUtils.isEmpty(suffix) && Arrays.asList(IMG_SUFFIXS).contains(suffix.toLowerCase());
    }

    /**
     * 创建文件
     * @param filePath
     */
    public static void mkdirs(String filePath) {
        File file = new File(filePath);
        mkdirs(file);
    }

    public static void mkdirs(File file) {
        if (!file.exists()) {
            if (file.isDirectory()) {
                file.mkdirs();
            } else {
                file.getParentFile().mkdirs();
            }
        }
    }

    /**
     * 检查文件路径是否存在，如果不存在，则创建目录
     * @param realFilePath
     */
    public static void checkFilePath(String realFilePath) {
        if (StringUtils.isEmpty(realFilePath)) {
            return;
        }
        File parentDir = new File(realFilePath).getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
    }

    /**
     * 生成md5
     * @author QINB
     * @param file 图片文件
     * @return MD5值
     * @throws FileNotFoundException
     */
    public static String getFileMd5(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(in);

        }
        return value;
    }

    private static void closeStream(FileInputStream closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件信息
     *
     * @param file
     * @throws IOException
     */
    public static FileUploadResponse getFileInfo(File file) {
        if (null == file) {
            return new FileUploadResponse();
        }
        try {
            return new FileUploadResponse()
                    .setSize(file.length())
                    .setOriginalFileName(file.getName())
                    .setSuffix(FileUtil.getSuffix(file.getName()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileException("获取文件信息发生异常！", e);
        }
    }

    /**
     * 获取文件信息
     *
     * @param multipartFile
     * @throws IOException
     */
    public static FileUploadResponse getFileInfo(MultipartFile multipartFile) {
        if (null == multipartFile) {
            return new FileUploadResponse();
        }
        try {
            return new FileUploadResponse()
                    .setSize(multipartFile.getSize())
                    .setOriginalFileName(multipartFile.getOriginalFilename())
                    .setSuffix(FileUtil.getSuffix(multipartFile.getOriginalFilename()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileException("获取文件信息发生异常！", e);
        }
    }

    /**
     * 获取图片信息
     *
     * @param inputStream
     * @throws IOException
     */
    public static FileUploadResponse getImageInfo(InputStream inputStream) {
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

    public static String getContentType(File file){
        String contentType = null;
        try {
            // Path path = Paths.get(f.getName());
            // contentType = Files.probeContentType(path);
            contentType = new Tika().detect(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentType;
    }

    /**
     * 获取文件类型
     * @param file
     * @return ppt,doc,pdf,html,txt,swf,flase
     */
    public static String getFileType(File file){
        String contentType = getContentType(file);
        String suffix = getSuffix(file).substring(1);
        String type = null;
        // 获取文件图标
        if ("ppt".equalsIgnoreCase(suffix) || "pptx".equalsIgnoreCase(suffix)) {
            type = "ppt";
        } else if ("doc".equalsIgnoreCase(suffix) || "docx".equalsIgnoreCase(suffix)) {
            type = "doc";
        } else if ("xls".equalsIgnoreCase(suffix) || "xlsx".equalsIgnoreCase(suffix)) {
            type = "xls";
        } else if ("pdf".equalsIgnoreCase(suffix)) {
            type = "pdf";
        } else if ("html".equalsIgnoreCase(suffix) || "htm".equalsIgnoreCase(suffix)) {
            type = "htm";
        } else if ("txt".equalsIgnoreCase(suffix)) {
            type = "txt";
        } else if ("swf".equalsIgnoreCase(suffix)) {
            type = "flash";
        } else if ("zip".equalsIgnoreCase(suffix) || "rar".equalsIgnoreCase(suffix) || "7z".equalsIgnoreCase(suffix)) {
            type = "zip";
        }else if (contentType != null && contentType.startsWith("audio/")) {
            type = "mp3";
        } else if (contentType != null && contentType.startsWith("video/")) {
            type = "mp4";
        } else {
            type = "file";
        }
        return type;
    }
    public static String getFileType(String filePath){
        String suffix = getSuffix(filePath).substring(1);
        String type = null;
        // 获取文件图标
        if ("ppt".equalsIgnoreCase(suffix) || "pptx".equalsIgnoreCase(suffix)) {
            type = "ppt";
        } else if ("doc".equalsIgnoreCase(suffix) || "docx".equalsIgnoreCase(suffix)) {
            type = "doc";
        } else if ("xls".equalsIgnoreCase(suffix) || "xlsx".equalsIgnoreCase(suffix)) {
            type = "xls";
        } else if ("pdf".equalsIgnoreCase(suffix)) {
            type = "pdf";
        } else if ("html".equalsIgnoreCase(suffix) || "htm".equalsIgnoreCase(suffix)) {
            type = "htm";
        } else if ("txt".equalsIgnoreCase(suffix)) {
            type = "txt";
        } else if ("swf".equalsIgnoreCase(suffix)) {
            type = "flash";
        } else if ("zip".equalsIgnoreCase(suffix) || "rar".equalsIgnoreCase(suffix) || "7z".equalsIgnoreCase(suffix)) {
            type = "zip";
        }else if ("mp3".equalsIgnoreCase(suffix)) {
            type = "mp3";
        } else if ("mp4".equalsIgnoreCase(suffix)) {
            type = "mp4";
        } else {
            type = "file";
        }
        return type;
    }
    public static void main(String[] args) {
        System.out.println(getSuffix("C:/abc/fdafa.png"));
    }
}
