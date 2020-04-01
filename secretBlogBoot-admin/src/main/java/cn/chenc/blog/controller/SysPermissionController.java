package cn.chenc.blog.controller;


import cn.chenc.blog.business.entity.SysPermission;
import cn.chenc.blog.business.service.SysPermissionService;
import cn.chenc.blog.framework.object.ResponseVO;
import cn.chenc.blog.utils.ResultUtil;
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
@Controller
@RequestMapping("/sysPermission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @ResponseBody
    @RequestMapping("/list")
    public ResponseVO selectSysRoleListPage(int page, int size){
        IPage iPage=new Page(page,size);
        ResponseVO responseVO=sysPermissionService.selectSysPermissionListPage(page,size);
        return responseVO;
    }

    @ResponseBody
    @RequestMapping("/tree")
    public ResponseVO getPermissionTree(){

        return sysPermissionService.selectTreePermission();

    }

    @ResponseBody
    @RequestMapping("/add")
    public ResponseVO addSysPermission(SysPermission sysPermission){
        boolean bool=sysPermissionService.save(sysPermission);
        if(bool){
            return ResultUtil.success("添加成功");
        } else{
            return ResultUtil.error("添加失败");
        }
    }

    @ResponseBody
    @RequestMapping("/edit")
    public ResponseVO editSysPermission(SysPermission sysPermission){
        boolean bool=sysPermissionService.save(sysPermission);
        if(bool){
            return ResultUtil.success("添加成功");
        } else{
            return ResultUtil.error("添加失败");
        }
    }

    @ResponseBody
    @RequestMapping("/del")
    public ResponseVO delSysPermission(Integer[] ids){
        List<Integer> list= Arrays.asList(ids);
        boolean bool=sysPermissionService.removeByIds(list);
        if(bool){
            return ResultUtil.success("删除成功");
        } else{
            return ResultUtil.error("删除失败");
        }
    }

}

