package cn.chenc.blog.framework.object;

import java.util.List;

/**
 * 　@description: TODO
 * 　@author 陈_C
 * 　@date 2020/6/16 15:29
 *
 */
public class LayUITreeVo {

    private String title;

    private Integer id;

    private String field;

    private List<LayUITreeVo> children;

    private String href;

    private String path;


    private Boolean spread=false;

    private Boolean checked=false;

    private Boolean disabled=false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<LayUITreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<LayUITreeVo> children) {
        this.children = children;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public Boolean getSpread() {
        return spread;
    }

    public void setSpread(Boolean spread) {
        this.spread = spread;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
