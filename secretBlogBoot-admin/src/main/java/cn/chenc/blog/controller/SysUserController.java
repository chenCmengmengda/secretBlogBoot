package cn.chenc.blog.controller;


import cn.chenc.blog.business.annoation.BussinessLog;
import cn.chenc.blog.business.annoation.Operation;
import cn.chenc.blog.business.consts.SessionConst;
import cn.chenc.blog.business.entity.SysUser;
import cn.chenc.blog.business.enums.PlatformEnum;
import cn.chenc.blog.business.service.SysUserService;
import cn.chenc.blog.framework.object.ResponseVO;
import cn.chenc.blog.utils.BCryptPasswordEncoderUtils;
import cn.chenc.blog.utils.ResultUtil;
import cn.chenc.blog.utils.SessionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.aspectj.weaver.bcel.BcelAccessForInlineMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
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
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;


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

    @BussinessLog(value = "退出登录")
    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new PersistentTokenBasedRememberMeServices("INTERNAL_SECRET_KEY",sysUserService,persistentTokenRepository)
                    .logout(request,response,auth);//清理rememberMe
            //new TokenBasedRememberMeServices("INTERNAL_SECRET_KEY",sysUserService).logout(request,response,auth);//清除token
            //new CookieClearingLogoutHandler("remember-me").logout(request, response, auth);//清除cookie
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResultUtil.redirect("/pages/login");
    }

    @ResponseBody
    @RequestMapping("/list")
    public ResponseVO selectSysUserListPage(int page, int size){
        return sysUserService.selectSysUserListPage(page,size);
    }

    @ResponseBody
    @RequestMapping("/add")
    public ResponseVO addSysUser(SysUser sysUser){
        List<SysUser> list=sysUserService.list(new QueryWrapper<SysUser>().eq("username",sysUser.getUsername()));//判断用户名是否存在
        if(list.size()>0){
            return ResultUtil.error("用户名已存在");
        }
        if(sysUser.getPassword()==null || "".equals(sysUser.getPassword())){
            sysUser.setPassword("111111");//默认密码111111
        }
        sysUser.setPassword(BCryptPasswordEncoderUtils.encodePassword(sysUser.getPassword()));//bc加密
        boolean bool=sysUserService.save(sysUser);
        if(bool){
            return ResultUtil.success("添加用户成功");
        } else{
            return ResultUtil.error("添加失败");
        }

    }

    @ResponseBody
    @RequestMapping("/edit")
    public ResponseVO editSysUser(SysUser sysUser,HttpServletRequest request){
        List<SysUser> list=sysUserService.list(new QueryWrapper<SysUser>()
                .eq("username",sysUser.getUsername())
                .ne("id",sysUser.getId()));//判断用户名是否存在
        if(list.size()>0){
            return ResultUtil.error("用户名已存在");
        }
        if(sysUser.getPassword()==null || "".equals(sysUser.getPassword())){
            sysUser.setPassword(request.getParameter("aldPassword"));//如果用户没输入密码，则密码保持不变
        } else{//否则加密新密码
            sysUser.setPassword(BCryptPasswordEncoderUtils.encodePassword(sysUser.getPassword()));
        }
        boolean bool=sysUserService.updateById(sysUser);
        if(bool){
            return ResultUtil.success("添加成功");
        } else{
            return ResultUtil.error("添加失败");
        }
    }

    @ResponseBody
    @RequestMapping("/del")
    public ResponseVO delSysUser(Integer[] ids){
        List<Integer> list= Arrays.asList(ids);
        boolean bool=sysUserService.removeByIds(list);
        if(bool){
            return ResultUtil.success("删除成功");
        } else{
            return ResultUtil.error("删除失败");
        }
    }


}

