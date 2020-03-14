package cn.chenc.blog.business.service;


import cn.chenc.blog.business.entity.TbRole;
import cn.chenc.blog.business.entity.TbRoleCustom;
import cn.chenc.blog.business.entity.TbRolePermissionKey;
import cn.chenc.blog.framework.pojo.EUDataGridResult;
import cn.chenc.blog.framework.pojo.Result;

public interface RoleService {
    EUDataGridResult gerRoleList(Integer page, Integer rows);
    Result addRole(TbRole role);
    EUDataGridResult findOtherPermissions(Long id);
    Result addPermissionToRole(Long roleId, Long[] permissionIds);
    TbRoleCustom findRolePermissionById(Long id);
    Result deletePermissionToRole(TbRolePermissionKey rolePermissionKey);
}
