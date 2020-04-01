package cn.chenc.blog.controller;


import cn.chenc.blog.business.entity.SysRole;
import cn.chenc.blog.business.entity.SysRolePermission;
import cn.chenc.blog.business.service.SysRolePermissionService;
import cn.chenc.blog.business.service.SysRoleService;
import cn.chenc.blog.framework.object.ResponseVO;
import cn.chenc.blog.utils.ResultUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenc
 * @since 2020-03-22
 */
@Controller
@RequestMapping("/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @ResponseBody
    @RequestMapping("/list")
    public ResponseVO selectSysRoleListPage(int page,int size){
        IPage iPage=new Page(page,size);
        IPage list=sysRoleService.page(iPage,null);
        return ResultUtil.success(list);
    }

    @ResponseBody
    @RequestMapping("/add")
    public ResponseVO addSysRole(SysRole sysRole,Integer[] permissionIds){
        boolean bool=sysRoleService.save(sysRole);
        List<SysRolePermission> list=new ArrayList<>();
        for(Integer permissionId:permissionIds){
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

    @ResponseBody
    @RequestMapping("/edit")
    public ResponseVO editSysRole(SysRole sysRole){
        boolean bool=sysRoleService.updateById(sysRole);
        if(bool){
            return ResultUtil.success("修改成功");
        } else{
            return ResultUtil.error("修改失败");
        }
    }

    @ResponseBody
    @RequestMapping("/del")
    public ResponseVO delSysRole(Integer[] ids){
        List<Integer> list= Arrays.asList(ids);
        boolean bool=sysRoleService.removeByIds(list);
        if(bool){
            return ResultUtil.success("删除成功");
        } else{
            return ResultUtil.error("删除失败");
        }
    }


}

