package cn.chenc.blog.controller;


import cn.chenc.blog.business.annoation.Operation;
import cn.chenc.blog.business.entity.TbUser;
import cn.chenc.blog.business.entity.TbUserCustom;
import cn.chenc.blog.business.entity.TbUserRoleKey;
import cn.chenc.blog.business.service.UserService;
import cn.chenc.blog.framework.pojo.EUDataGridResult;
import cn.chenc.blog.framework.pojo.ResponseVO;
import cn.chenc.blog.framework.pojo.Result;
import cn.chenc.blog.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 陈_C on 2018/7/29.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(name="用户登录")
    @RequestMapping("/login")
    @ResponseBody
    public ResponseVO login(TbUser tbUser, HttpServletRequest request){
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(tbUser.getNickname(), tbUser.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (UsernameNotFoundException e){//处理登录异常
            return ResultUtil.error(e.getMessage());
        } catch (BadCredentialsException e) {
            return ResultUtil.error("密码错误");
        }


        return ResultUtil.success("登录成功","./index");
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Result userAdd(@RequestBody TbUser user){
        System.out.println(user.getNickname()+":"+user.getBirthday());
        Result result=userService.addUser(user);
        return result;
    }

    /**
     * 用户列表
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridResult getUserList(Integer page, Integer rows){
        EUDataGridResult result=userService.userList(page,rows);
        return result;
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Result updateUser(TbUser user){
        Result result = userService.updateUser(user);
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result deleteUser(Long ids){
        Result result=userService.deleteUser(ids);
        return result;
    }
/*
    @RequestMapping(value="/login",method= RequestMethod.POST)
    @ResponseBody
    public Result userLogin(Model model, String username, String password, HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        Result result = userService.userLogin(username, password,request,response);
        if(result.getStatus()!=400) {
            //储存用户名
        //    request.setAttribute("username",username);
            model.addAttribute("username",username);
        }
        return result;
    }
*/
/*
    @RequestMapping("/editpassword")
    @ResponseBody
    public Result editPassword(TbUser user,String repassword){
        Result result=userService.editPassword(user,repassword);
        System.out.println(user.getPassword());
        System.out.println(repassword);
        return result;
    }
    */

    @RequestMapping("/editpassword")
    @ResponseBody
    public Result editPassword(@RequestBody TbUserObj userObj){

        Result result=userService.editPassword(userObj.getUser(),userObj.getRepassword());

        return Result.ok();
    }

    /**
     *
     * @param id userId
     * @return
     */
    @RequestMapping("/findOne")
    @ResponseBody
    public Result findOne(Long id){
        Result result=userService.findOne(id);
        return result;
    }

    @RequestMapping("/findOtherRoles")
    @ResponseBody
    public EUDataGridResult findOtherRoles(Long id){
        EUDataGridResult result=userService.findOtherRoles(id);
        return result;
    }

    @RequestMapping("/addRoleToUser")
    @ResponseBody
    public Result addRoleToUser(Long userId, Long[] roleIds){
        Result result=userService.addRoleToUser(userId,roleIds);
        return result;
    }

    @RequestMapping("/findUserRolePermissionById")
    @ResponseBody
    public TbUserCustom findUserRolePermissionById(Long userId){
        TbUserCustom userCustom=userService.findUserRolePermissionById(userId);
        return userCustom;
    }

    @RequestMapping("/deleteRoleToUser")
    @ResponseBody
    public Result deleteRoleToUser(@RequestBody TbUserRoleKey userRoleKey){
        Result result=userService.deleteRoleToUser(userRoleKey);
        return result;
    }
}

class TbUserObj{
    TbUser user;
    String repassword;

    public TbUser getUser() {
        return user;
    }

    public void setUser(TbUser user) {
        this.user = user;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }
}
