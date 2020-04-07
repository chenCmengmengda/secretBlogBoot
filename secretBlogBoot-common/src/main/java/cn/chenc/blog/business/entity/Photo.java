package cn.chenc.blog.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author chenc
 * @since 2020-04-07
 */
@TableName("photo")
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 图片标题
     */
    private String title;

    /**
     * 图片路径
     */
    private String url;

    /**
     * 缩略图路径
     */
    private String smUrl;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSmUrl() {
        return smUrl;
    }

    public void setSmUrl(String smUrl) {
        this.smUrl = smUrl;
    }

    @Override
    public String toString() {
        return "Photo{" +
        "id=" + id +
        ", title=" + title +
        ", url=" + url +
        ", smUrl=" + smUrl +
        "}";
    }
}
