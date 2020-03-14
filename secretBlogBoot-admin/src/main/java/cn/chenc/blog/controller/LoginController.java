package cn.chenc.blog.controller;

import cn.chenc.blog.utils.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 　@description: TODO
 * 　@author 陈_C
 * 　@date 2020/3/13 23:47
 *
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public ModelAndView login(){
        return ResultUtil.view("login");
    }


}
