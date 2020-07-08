package cn.chenc.blog.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 陈_C
 * @since 2020-07-08
 */
@TableName("sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 已登录用户ID
     */
    private Long userId;

    /**
     * 日志类型（系统操作日志，用户访问日志，异常记录日志）
     */
    private String type;

    /**
     * 日志级别
     */
    private String logLevel;

    /**
     * 日志内容（业务操作）
     */
    private String content;

    /**
     * 请求参数（业务操作）
     */
    private String params;

    /**
     * 爬虫类型（当访问者被鉴定为爬虫时该字段表示爬虫的类型）
     */
    private String spiderType;

    /**
     * 操作用户的ip
     */
    private String ip;

    /**
     * 操作用户的user_agent
     */
    private String ua;

    /**
     * 评论时的系统类型
     */
    private String os;

    /**
     * 评论时的浏览器类型
     */
    private String browser;

    /**
     * 请求的路径
     */
    private String requestUrl;

    /**
     * 请求来源地址
     */
    private String referer;

    /**
     * 添加时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getSpiderType() {
        return spiderType;
    }

    public void setSpiderType(String spiderType) {
        this.spiderType = spiderType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SysLog{" +
        "id=" + id +
        ", userId=" + userId +
        ", type=" + type +
        ", logLevel=" + logLevel +
        ", content=" + content +
        ", params=" + params +
        ", spiderType=" + spiderType +
        ", ip=" + ip +
        ", ua=" + ua +
        ", os=" + os +
        ", browser=" + browser +
        ", requestUrl=" + requestUrl +
        ", referer=" + referer +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
