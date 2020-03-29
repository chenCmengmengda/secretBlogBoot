package cn.chenc.blog.business.entity;

/**
 * 　@description: TODO
 * 　@author 陈_C
 * 　@date 2020/3/22 17:01
 *
 */
public class SysUserCustom extends SysUser {
    private SysRole sysRole;

    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }
}
