package cn.chenc.blog.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 　@description: TODO
 * 　@author 陈_C
 * 　@date 2020/3/22 19:15
 *
 */
@JsonIgnoreProperties(value = {"handler"})
public class SysPermissionCustom extends SysPermission {
    //父级权限
    private SysPermissionCustom parentPermission;
    //子级权限
    private List<SysPermissionCustom> children;

    public SysPermissionCustom getParentPermission() {
        return parentPermission;
    }

    public void setParentPermission(SysPermissionCustom parentPermission) {
        this.parentPermission = parentPermission;
    }

    public List<SysPermissionCustom> getChildren() {
        return children;
    }

    public void setChildren(List<SysPermissionCustom> children) {
        this.children = children;
    }
}
