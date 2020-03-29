package cn.chenc.blog.file.model;

import lombok.Data;

/**
 * 文件Model
 */
@Data
public class FileModel {
    /**
     * 文件名，带后缀
     */
    private String name;
    /**
     * 是否是目录
     */
    private Boolean isDir = false;
    /**
     * 文件类型
     */
    private String type = "file";
    /**
     * 文件路径 （不带域名）
     */
    private String filePath;
    /**
     * 文件全路径 （带域名）
     */
    private String url;
    /**
     * 文件是否有缩略图
     */
    private boolean hasSm;
    /**
     * 文件缩略图地址
     */
    private String smUrl;
    /**
     * 文件的修改时间
     */
    private long updateTime;
    /**
     * 文件大小
     */
    public Long size;

}
