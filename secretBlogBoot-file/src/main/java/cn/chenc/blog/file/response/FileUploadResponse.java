package cn.chenc.blog.file.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse {

    private Integer code;

    private String msg;

    private String url; // 回调上传文件地址

    private String smUrl; // 缩略图

    private String dir; // 目录

    private String suffix;
    /**
     * 文件大小
     */
    public Long size;
    /**
     * 图片文件的宽
     */
    public Integer width;
    /**
     * 图片文件的高
     */
    public Integer height;
    /**
     * 文件hash
     */
    private String fileHash;
    /**
     * 文件路径 （不带域名）
     */
    private String filePath;
    /**
     * 文件全路径 （带域名）
     */
    private String fullFilePath;
    /**
     * 原始文件名
     */
    private String originalFileName;
    /**
     * 文件上传开始的时间
     */
    private Date uploadStartTime;
    /**
     * 文件上传结束的时间
     */
    private Date uploadEndTime;
}
