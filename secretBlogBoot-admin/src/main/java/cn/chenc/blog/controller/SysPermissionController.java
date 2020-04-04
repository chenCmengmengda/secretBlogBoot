package cn.chenc.blog.controller;


import cn.chenc.blog.business.annoation.BussinessLog;
import cn.chenc.blog.business.entity.SysPermission;
import cn.chenc.blog.business.entity.SysRolePermission;
import cn.chenc.blog.business.service.SysPermissionService;
import cn.chenc.blog.business.service.SysRolePermissionService;
import cn.chenc.blog.framework.object.ResponseVO;
import cn.chenc.blog.utils.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@BussinessLog("权限管理")
@Controller
@RequestMapping("/sysPermission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     * @description: 查询权限列表
     * @param [page, size]
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/4/1 陈_C
     */
    @BussinessLog("查询权限列表")
    @ResponseBody
    @RequestMapping("/list")
    public ResponseVO selectSysPermissionListPage(int page, int size){
        IPage iPage=new Page(page,size);
        ResponseVO responseVO=sysPermissionService.selectSysPermissionListPage(page,size);
        return responseVO;
    }

    /**
     * @description: 根据id查询权限
     * @param [id]
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/4/1 陈_C
     */
    @BussinessLog("权限详情")
    @ResponseBody
    @RequestMapping("/selectById")
    public ResponseVO selectSysPermissionById(Integer id){
        SysPermission sysPermission=sysPermissionService.getById(id);
        return ResultUtil.success(sysPermission);
    }

    /**
     * @description: 查询权限树
     * @param []
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/4/1 陈_C
     */
    @BussinessLog("查询权限树")
    @ResponseBody
    @RequestMapping("/tree")
    public ResponseVO getPermissionTree(){
        return sysPermissionService.selectTreePermission();
    }

    /**
     * @description: 获取菜单
     * @param []
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/4/1 陈_C
     */
    @BussinessLog("获取菜单")
    @ResponseBody
    @RequestMapping("/sysMenu")
    public ResponseVO getSysMenu(){
        return sysPermissionService.selectMenu();
    }


    /**
     * @description: 添加权限
     * @param [sysPermission]
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/4/1 陈_C
     */
    @BussinessLog("添加权限")
    @ResponseBody
    @RequestMapping("/add")
    public ResponseVO addSysPermission(SysPermission sysPermission){
        if(sysPermission.getSpread()==null){//空则不展开
            sysPermission.setSpread(false);
        }
        if(sysPermission.getStatus()==null || "".equals(sysPermission.getStatus())){//空则不启用
            sysPermission.setStatus(1);
        }
        if(sysPermission.getIcon()==null || "".equals(sysPermission.getIcon())){//设置默认图标
            sysPermission.setIcon("&#xe62e;");
        }
        boolean bool=sysPermissionService.save(sysPermission);
        if(bool){
            return ResultUtil.success("添加成功");
        } else{
            return ResultUtil.error("添加失败");
        }
    }

    /**
     * @description: 修改权限
     * @param [sysPermission]
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/4/1 陈_C
     */
    @BussinessLog("修改权限")
    @ResponseBody
    @RequestMapping("/edit")
    public ResponseVO editSysPermission(SysPermission sysPermission){
        if(sysPermission.getSpread()==null){//空则不展开
            sysPermission.setSpread(false);
        }
        if(sysPermission.getStatus()==null || "".equals(sysPermission.getStatus())){//空则不启用
            sysPermission.setStatus(1);
        }
        if(sysPermission.getIcon()==null || "".equals(sysPermission.getIcon())){//设置默认图标
            sysPermission.setIcon("&#xe62e;");
        }
        boolean bool=sysPermissionService.updateById(sysPermission);
        if(bool){
            return ResultUtil.success("修改成功");
        } else{
            return ResultUtil.error("修改失败");
        }
    }

    /**
     * @description: 删除权限,支持批量删除
     * @param [ids]
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/4/1 陈_C
     */
    @BussinessLog("删除权限")
    @ResponseBody
    @RequestMapping("/del")
    public ResponseVO delSysPermission(Integer[] ids){
        List<Integer> list= Arrays.asList(ids);//id转为list集合
        boolean bool=sysPermissionService.removeByIds(list);//批量删除权限
        for(Integer id:ids) {//删除关联表里存在的关联权限
            sysRolePermissionService.remove(new UpdateWrapper<SysRolePermission>()
                    .eq("permission_id",id));
        }
        if(bool){
            return ResultUtil.success("删除成功");
        } else{
            return ResultUtil.error("删除失败");
        }
    }

}

