package cn.chenc.blog.business.service;


import cn.chenc.blog.business.entity.TbPermission;
import cn.chenc.blog.framework.pojo.EUDataGridResult;
import cn.chenc.blog.framework.pojo.Result;

public interface PermissionService {
    EUDataGridResult gerPermissionList(Integer page, Integer rows);
    Result addPermission(TbPermission permission);
}
