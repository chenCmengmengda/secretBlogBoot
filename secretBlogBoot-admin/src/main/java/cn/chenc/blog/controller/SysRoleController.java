package cn.chenc.blog.controller;


import cn.chenc.blog.business.service.SysRoleService;
import cn.chenc.blog.framework.object.ResponseVO;
import cn.chenc.blog.utils.ResultUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ResponseBody
    @RequestMapping("/list")
    public ResponseVO selectSysRoleListPage(int page,int size){
        IPage iPage=new Page(page,size);
        IPage list=sysRoleService.page(iPage,null);
        return ResultUtil.success(list);
    }

}

