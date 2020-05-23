package cn.chenc.blog.file.model;

public enum FileUploadPath {

    COMMEN("common/"),
    /**
     * 七牛基础目录
     */
    QINIU_BASE_PATH("fs/"),

    USER_IMG("user_images/"),

    ARTICLE_IMG("article_images/"),//文章图片上传地址

    PHOTO("photo/"),//相册图片上传地址

    MEDIA("media/"),//媒体资源上传目录
    ;
    private String path;

    FileUploadPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
