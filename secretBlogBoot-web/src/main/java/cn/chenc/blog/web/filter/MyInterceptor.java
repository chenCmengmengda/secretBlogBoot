package cn.chenc.blog.web.filter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 　@description: TODO
 * 　@author 陈_C
 * 　@date 2020/3/21 20:33
 *
 */
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        boolean flag =true;
//        User user=(User)request.getSession().getAttribute("user");
//        if(null==user){
//            response.sendRedirect("toLogin");
//            flag = false;
//        }else{
//            flag = true;
//        }
//        return flag;
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
