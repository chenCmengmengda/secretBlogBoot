package cn.chenc.blog.controller;


import cn.chenc.blog.business.annoation.BussinessLog;
import cn.chenc.blog.business.entity.SysPermission;
import cn.chenc.blog.business.entity.SysRole;
import cn.chenc.blog.business.entity.SysRolePermission;
import cn.chenc.blog.business.service.SysPermissionService;
import cn.chenc.blog.business.service.SysRolePermissionService;
import cn.chenc.blog.business.service.SysRoleService;
import cn.chenc.blog.framework.object.ResponseVO;
import cn.chenc.blog.utils.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.relation.RoleList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenc
 * @since 2020-03-22
 */
@BussinessLog("角色管理")
@Controller
@RequestMapping("/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     * @description: 查询角色列表
     * @param [page, size]
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/4/1 陈_C
     */
    @BussinessLog("查询角色列表")
    @ResponseBody
    @RequestMapping("/list")
    public ResponseVO selectSysRoleListPage(int page,int size){
        IPage iPage=new Page(page,size);
        IPage list=sysRoleService.page(iPage,null);
        return ResultUtil.success(list);
    }

    @BussinessLog("查询所有角色")
    @ResponseBody
    @RequestMapping("/alllist")
    public ResponseVO selectSysRoleAllList(){
        List<SysRole> list=sysRoleService.list(null);
        return ResultUtil.success(list);
    }

    /**
     * @description: 根据id查询角色
     * @param [id]
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/4/1 陈_C
     */
    @BussinessLog("查询角色详情")
    @ResponseBody
    @RequestMapping("/selectById")
    public ResponseVO selectSysRoleById(Integer id){
        SysRole sysRole=sysRoleService.getById(id);
        return ResultUtil.success(sysRole);
    }

    @BussinessLog("查询角色权限")
    @ResponseBody
    @RequestMapping("/selectPermByRoleId")
    public ResponseVO selectSysPermissionByRoleId(Integer id){
        List<SysRolePermission> list= sysRolePermissionService.list(new QueryWrapper<SysRolePermission>()
                .eq("role_id",id));
        Iterator<SysRolePermission> it=list.iterator();
       while(it.hasNext()){//移除没有路径的菜单
           SysRolePermission sysRolePermission=it.next();
           SysPermission sysPermission=sysPermissionService.getById(sysRolePermission.getPermissionId());
           if(sysPermission.getHref()==null || "".equals(sysPermission.getHref())) {
               it.remove();
           }
        }
        return ResultUtil.success(list);
    }

    /**
     * @description: 添加角色
     * @param [sysRole, permissionIds]
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/4/1 陈_C
     */
    @BussinessLog("添加角色")
    @ResponseBody
    @RequestMapping("/add")
    public ResponseVO addSysRole(SysRole sysRole,Integer[] permissionIds){
        boolean bool=sysRoleService.save(sysRole);
        List<SysRolePermission> list=new ArrayList<>();
        for(Integer permissionId:permissionIds){//遍历前端上传的权限id
            SysRolePermission sysRolePermission=new SysRolePermission();
            sysRolePermission.setRoleId(sysRole.getId());
            sysRolePermission.setPermissionId(permissionId);
            list.add(sysRolePermission);
        }
        sysRolePermissionService.saveBatch(list);//批量插入role-permission关联表
        if(bool){
            return ResultUtil.success("添加成功");
        } else{
            return ResultUtil.error("添加失败");
        }
    }

    /**
     * @description: 修改角色
     * @param [sysRole]
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/4/1 陈_C
     */
    @BussinessLog("修改角色")
    @ResponseBody
    @RequestMapping("/edit")
    public ResponseVO editSysRole(SysRole sysRole,Integer[] permissionIds){
        boolean bool=sysRoleService.updateById(sysRole);
        //删除权限,再重新添加权限
        sysRolePermissionService.remove(new UpdateWrapper<SysRolePermission>().eq("role_id",sysRole.getId()));
        List<SysRolePermission> list=new ArrayList<>();
        for(Integer permissionId:permissionIds){//遍历前端上传的权限id
            SysRolePermission sysRolePermission=new SysRolePermission();
            sysRolePermission.setRoleId(sysRole.getId());
            sysRolePermission.setPermissionId(permissionId);
            list.add(sysRolePermission);
        }
        sysRolePermissionService.saveBatch(list);//批量插入role-permission关联表

        if(bool){
            return ResultUtil.success("修改成功");
        } else{
            return ResultUtil.error("修改失败");
        }
    }

    /**
     * @description: 删除角色
     * @param [ids]
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/4/1 陈_C
     */
    @BussinessLog("删除角色")
    @ResponseBody
    @RequestMapping("/del")
    public ResponseVO delSysRole(Integer[] ids){
        List<Integer> list= Arrays.asList(ids);
        boolean bool=sysRoleService.removeByIds(list);
        //删除关联权限
        for(Integer roleId:ids){
            sysRolePermissionService.remove(new UpdateWrapper<SysRolePermission>().eq("role_id",roleId));
        }
        if(bool){
            return ResultUtil.success("删除成功");
        } else{
            return ResultUtil.error("删除失败");
        }
    }


}

