package cn.chenc.blog.file.model;

public enum FileUploadPath {

    COMMEN("common/"),
    /**
     * 七牛基础目录
     */
    QINIU_BASE_PATH("fs/"),

    USER_IMG("user_images/"),

    ARTICLE_IMG("article_images/"),
    ;
    private String path;

    FileUploadPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
