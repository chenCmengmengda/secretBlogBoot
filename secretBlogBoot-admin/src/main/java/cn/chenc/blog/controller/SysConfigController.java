package cn.chenc.blog.controller;


import cn.chenc.blog.business.annoation.BussinessLog;
import cn.chenc.blog.business.entity.SysConfig;
import cn.chenc.blog.business.service.SysConfigService;
import cn.chenc.blog.framework.object.ResponseVO;
import cn.chenc.blog.utils.ResultUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Default;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenc
 * @since 2020-03-24
 */
@BussinessLog("系统配置")
@Controller
@RequestMapping("/sysConfig")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    @BussinessLog("查询系统配置列表")
    @ResponseBody
    @RequestMapping("/list")
    public ResponseVO selectSysConfigListPage(int page,int size){
        IPage iPage=new Page(page,size);
        IPage list=sysConfigService.page(iPage,null);
        return ResultUtil.success(list);
    }

    @BussinessLog("添加系统配置")
    @ResponseBody
    @RequestMapping("/add")
    public ResponseVO addSysConfig(SysConfig sysConfig){
        boolean bool=sysConfigService.save(sysConfig);
        if(bool){
            return ResultUtil.success("添加成功");
        } else{
            return ResultUtil.success("添加失败");
        }
    }

    @BussinessLog("修改系统配置")
    @ResponseBody
    @RequestMapping("/edit")
    public ResponseVO editSysConfig(SysConfig sysConfig){
        boolean bool=sysConfigService.updateById(sysConfig);
        if(bool){
            return ResultUtil.success("修改成功");
        } else{
            return ResultUtil.success("修改失败");
        }
    }

    @BussinessLog("配置详情")
    @ResponseBody
    @RequestMapping("/selectById")
    public ResponseVO selectSysConfigById(int id){
       SysConfig sysConfig=sysConfigService.getById(id);
       return ResultUtil.success(sysConfig);
    }

    /**
     * @description: 删除，支持批量操作
     * @param [ids]
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/3/27 陈_C
     */
    @BussinessLog("配置删除")
    @ResponseBody
    @RequestMapping("/del")
    public ResponseVO delSysConfig(Integer[] ids){
        List<Integer> list= Arrays.asList(ids);
        boolean bool=sysConfigService.removeByIds(list);
        if(true){
            return ResultUtil.success("删除成功");
        } else{
            return ResultUtil.error("删除失败");
        }
    }

}

