package cn.chenc.blog.controller;


import cn.chenc.blog.business.service.SysUserService;
import cn.chenc.blog.framework.object.ResponseVO;
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
 * @since 2020-03-21
 */
@Controller
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @ResponseBody
    @RequestMapping("/list")
    public ResponseVO selectSysUserListPage(int page, int size){
        return sysUserService.selectSysUserListPage(page,size);
    }
}

