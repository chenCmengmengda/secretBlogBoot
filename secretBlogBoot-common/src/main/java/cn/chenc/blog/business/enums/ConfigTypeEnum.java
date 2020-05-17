package cn.chenc.blog.business.enums;

public enum ConfigTypeEnum {

    SYSTEM("system"),

    UPLOAD("upload"),

    PHOTO("photo"),

    ARTICLE("article")
    ;
    private String type;

    ConfigTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
