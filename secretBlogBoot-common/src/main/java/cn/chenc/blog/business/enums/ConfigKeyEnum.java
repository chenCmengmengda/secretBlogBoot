package cn.chenc.blog.business.enums;

public enum ConfigKeyEnum {
    /**
     * 网站Title
     */
    SITE_TITLE("siteName"),
    /**
     * 站点简介
     */
    SITE_DESC("siteDesc"),
    /**
     * 网站首页的Description
     */
    HOME_DESC("homeDesc"),
    /**
     * 网站首页的Keywords
     */
    HOME_KEYWORDS("homeKeywords"),
    /**
     * 系统最后一次更新的日期
     */
    UPDATE_TIME("updateTime"),

    /**
     * 存储类型
     */
    STORAGE_TYPE("storageType"),
    /**
     * 是否开启nginx
     */
    USE_NGINX("useNginx"),
    /**
     * nginx Url
     */
    NGINX_URL("nginxUrl"),

    SERVER_URL("serverUrl"),
    /**
     * 文件上传是否用uuid重命名
     */
    UUID_NAME("uuidName"),
    /**
     * 上传文件根目录
     */
    ROOT_PATH("rootPath"),

    /**
     * 七牛云Bucket 名称
     */
    QINIU_BUCKET_NAME("qiniuBucketName"),
    /**
     * 七牛云AccessKey
     */
    QINIU_ACCESS_KEY("qiniuAccessKey"),
    /**
     * 七牛云Secret Key
     */
    QINIU_SECRET_KEY("qiniuSecretKey"),
    /**
     * 七牛云cdn域名
     */
    QINIU_BASE_PATH("qiniuBasePath"),
    /**
     * 是否用缩略图
     */
    USE_SM("useSm"),


    ;

    private String key;

    ConfigKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }



}
