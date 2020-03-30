package cn.chenc.blog.controller;


import cn.chenc.blog.business.annoation.Operation;
import cn.chenc.blog.business.consts.SessionConst;
import cn.chenc.blog.business.entity.SysUser;
import cn.chenc.blog.business.service.SysUserService;
import cn.chenc.blog.framework.object.ResponseVO;
import cn.chenc.blog.utils.BCryptPasswordEncoderUtils;
import cn.chenc.blog.utils.ResultUtil;
import cn.chenc.blog.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
    @Autowired
    private RememberMeServices rememberMeServices;


    @Operation(name="用户登录")
    @RequestMapping("/login")
    @ResponseBody
    public ResponseVO login(SysUser sysUser, HttpServletRequest request, HttpServletResponse response){
        String kaptcha = request.getParameter("kaptcha");
        String sessionKaptcha=SessionUtil.getKaptcha();
        if(sessionKaptcha == null || sessionKaptcha.equals("") || !sessionKaptcha.equals(kaptcha)){
            return ResultUtil.error("验证码错误");
        }
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(sysUser.getUsername(), sysUser.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            rememberMeServices.loginSuccess(request,response,authentication);
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

