package cn.chenc.blog.business.aspect;


import cn.chenc.blog.business.annoation.BussinessLog;
import cn.chenc.blog.business.enums.PlatformEnum;
import cn.chenc.blog.business.service.SysLogService;
import cn.chenc.blog.utils.AspectUtil;
import cn.chenc.blog.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @description: AOP切面记录日志
 * @author 陈_C
 * @date 2020/7/8 陈_C
 */
@Slf4j
@Aspect
@Component
public class BussinessLogAspect {

    @Autowired
    private SysLogService logService;

    @Pointcut(value = "@annotation(cn.chenc.blog.business.annoation.BussinessLog)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object writeLog(ProceedingJoinPoint point) throws Throwable {

        //先执行业务
        Object result = point.proceed();

        try {
            handle(point);
        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }

        return result;
    }

    private void handle(ProceedingJoinPoint point) throws Exception {
        Method currentMethod = AspectUtil.INSTANCE.getMethod(point);
        //获取操作名称
        BussinessLog annotation = currentMethod.getAnnotation(BussinessLog.class);
        boolean save = annotation.save();
        PlatformEnum platform = annotation.platform();
        String bussinessName = AspectUtil.INSTANCE.parseParams(point.getArgs(), annotation.value());
        String ua = RequestUtil.getUa();

        log.info("{} | {} - {} {} - {}", bussinessName, RequestUtil.getIp(), RequestUtil.getMethod(), RequestUtil.getRequestUrl(), ua);
        if (!save) {
            return;
        }

        logService.asyncSaveSystemLog(platform, bussinessName);
    }


}
