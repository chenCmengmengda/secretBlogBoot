package cn.chenc.blog.business.service.impl;


import cn.chenc.blog.business.entity.*;
import cn.chenc.blog.business.mapper.TbRoleMapper;
import cn.chenc.blog.business.mapper.TbRoleMapperCustom;
import cn.chenc.blog.business.mapper.TbRolePermissionMapper;
import cn.chenc.blog.business.service.RoleService;
import cn.chenc.blog.framework.pojo.EUDataGridResult;
import cn.chenc.blog.framework.pojo.Result;
import cn.chenc.blog.utils.IDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    TbRoleMapper roleMapper;
    @Autowired
    TbRoleMapperCustom roleMapperCustom;
    @Autowired
    TbRolePermissionMapper rolePermissionMapper;

    @Override
    public EUDataGridResult gerRoleList(Integer page, Integer rows) {
        TbRoleExample example=new TbRoleExample();
        //分页处理
        PageHelper.startPage(page,rows);
        List<TbRole> list=roleMapper.selectByExample(example);
        //创建一个返回值对象
        EUDataGridResult result=new EUDataGridResult();
        result.setRows(list);
        //取记录总条数
        PageInfo<TbRole> pageInfo=new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;

    }

    @Override
    public Result addRole(TbRole role) {
        role.setId(IDUtils.genItemId());
        roleMapper.insert(role);
        return Result.ok();
    }

    /**
     *
     * @param id  roleId
     * @return
     */
    @Override
    public EUDataGridResult findOtherPermissions(Long id){
        List<TbPermission> list=roleMapperCustom.findOtherPermissions(id);
        EUDataGridResult result=new EUDataGridResult();
        result.setRows(list);
        return result;
    }

    @Override
    public Result addPermissionToRole(Long roleId, Long[] permissionIds){
        for(Long permissionId:permissionIds) {
            roleMapperCustom.addPermissionToRole(roleId, permissionId);
        }
        return Result.ok();
    }

    /**
     * 根据角色id查询角色的资源路径
     * @param id roleId
     * @return
     */
    @Override
    public TbRoleCustom findRolePermissionById(Long id){
        TbRoleCustom roleCustom=roleMapperCustom.findRolePermissionById(id);
        return roleCustom;
    }

    @Override
    public Result deletePermissionToRole(TbRolePermissionKey rolePermissionKey){
        rolePermissionMapper.deleteByPrimaryKey(rolePermissionKey);
        return Result.ok();
    }
}
