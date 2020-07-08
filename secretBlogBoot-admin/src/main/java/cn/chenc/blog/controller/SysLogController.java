package cn.chenc.blog.controller;


import cn.chenc.blog.business.entity.Photo;
import cn.chenc.blog.business.service.SysLogService;
import cn.chenc.blog.framework.object.ResponseVO;
import cn.chenc.blog.utils.ResultUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈_C
 * @since 2020-07-08
 */
@Controller
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @ResponseBody
    @RequestMapping("/list")
    public ResponseVO selectSysRoleListPage(int page,int size){
        IPage iPage=new Page(page,size);
        IPage list=sysLogService.page(iPage,null);
        return ResultUtil.success(list);
    }

}

