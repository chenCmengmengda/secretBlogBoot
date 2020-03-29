package cn.chenc.blog.controller;


import cn.chenc.blog.business.service.SysPermissionService;
import cn.chenc.blog.framework.object.ResponseVO;
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

}

