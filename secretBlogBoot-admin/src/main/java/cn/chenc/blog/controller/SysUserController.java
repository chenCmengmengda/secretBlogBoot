package cn.chenc.blog.controller;


import cn.chenc.blog.business.annoation.Operation;
import cn.chenc.blog.business.entity.SysUser;
import cn.chenc.blog.business.service.SysUserService;
import cn.chenc.blog.framework.object.ResponseVO;
import cn.chenc.blog.utils.BCryptPasswordEncoderUtils;
import cn.chenc.blog.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(name="用户登录")
    @RequestMapping("/login")
    @ResponseBody
    public ResponseVO login(SysUser sysUser, HttpServletRequest request){

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(sysUser.getUsername(), sysUser.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (UsernameNotFoundException e){//处理登录异常
            return ResultUtil.error(e.getMessage());
        } catch (BadCredentialsException e) {
            return ResultUtil.error("密码错误");
        } catch (DisabledException e){
            return ResultUtil.error("用户未启用");
        }


        return ResultUtil.success("登录成功");
    }

    @ResponseBody
    @RequestMapping("/list")
    public ResponseVO selectSysUserListPage(int page, int size){
        return sysUserService.selectSysUserListPage(page,size);
    }
}

