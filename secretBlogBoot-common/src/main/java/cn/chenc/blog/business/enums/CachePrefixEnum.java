package cn.chenc.blog.business.enums;


public enum CachePrefixEnum {

    BIZ("biz_cache_"),
    VIEW("view_cache_"),
    DDOS("ddos_cache_"),
    WX("wx_api_cache_"),
    SPIDER("spider_cache_"),
    ;
    private String prefix;

    CachePrefixEnum(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
