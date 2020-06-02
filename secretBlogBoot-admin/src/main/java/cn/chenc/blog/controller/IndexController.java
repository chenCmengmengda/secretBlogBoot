package cn.chenc.blog.controller;

import cn.chenc.blog.framework.object.Result;
import cn.chenc.blog.utils.ResultUtil;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 陈_C on 2018/7/25.
 */
@Controller
public class IndexController {

    /*
    @RequestMapping("/")
    public String index(Model model){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("username",username);
        return "index";
    }
*/
//    @RequestMapping("/userinfo")
//    @ResponseBody
//    public Result userinfo(){
//        String username= SecurityContextHolder.getContext().getAuthentication().getName();
//        Map<String,String> map=new HashMap<>();
//        map.put("username",username);
//
//        return Result.ok(map);
//    }

    @RequestMapping("/loginSuccess")
    @ResponseBody
    public Result loginSuccess(){
        return Result.build(200,"/admin/index.html");
    }

    @RequestMapping("/loginFailer")
    @ResponseBody
    public Result loginFailer(){
        return Result.build(400,"用户名或密码错误");
    }

    @RequestMapping("/403")
    @ResponseBody
    public Result access(){
        return Result.build(403,"/403.html");
    }

    /**
     * 展示其他页面
     * @param page
     * @return
     */
//    @RequestMapping("/{page}")
//    public ModelAndView showpage(@PathVariable String page) {
//
//        return ResultUtil.view(page);
//    }



    @RequestMapping("/index")
    public ModelAndView index() {

        return ResultUtil.view("index");
    }

    @RequestMapping("/welcome")
    public ModelAndView toWelcome() {
        return ResultUtil.view("welcome");
    }

    @RequestMapping("/pages/**")
    public void pages() {
    //    return ResultUtil.view("/page/chart/chart1");
    }
}
