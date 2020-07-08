package cn.chenc.blog.business.service.impl;

import cn.chenc.blog.business.entity.SysLog;
import cn.chenc.blog.business.entity.SysUser;
import cn.chenc.blog.business.enums.LogLevelEnum;
import cn.chenc.blog.business.enums.LogTypeEnum;
import cn.chenc.blog.business.enums.PlatformEnum;
import cn.chenc.blog.business.mapper.SysLogMapper;
import cn.chenc.blog.business.service.SysLogService;
import cn.chenc.blog.framework.object.UserVo;
import cn.chenc.blog.utils.RequestUtil;
import cn.chenc.blog.utils.SessionUtil;
import cn.chenc.blog.utils.WebSpiderUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈_C
 * @since 2020-07-08
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Async
    @Override
    public void asyncSaveSystemLog(PlatformEnum platform, String bussinessName) {
        String ua = RequestUtil.getUa();
        SysLog sysLog = new SysLog();
        sysLog.setLogLevel(LogLevelEnum.INFO.toString());
        sysLog.setType(platform.equals(PlatformEnum.WEB) ? LogTypeEnum.VISIT.toString() : LogTypeEnum.SYSTEM.toString());
        sysLog.setIp(RequestUtil.getIp());
        sysLog.setReferer(RequestUtil.getReferer());
        sysLog.setRequestUrl(RequestUtil.getRequestUrl());
        sysLog.setUa(ua);
        sysLog.setSpiderType(WebSpiderUtils.parseUa(ua));
        sysLog.setParams(JSONObject.toJSONString(RequestUtil.getParametersMap()));
//        SysUser user = SessionUtil.getUser();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserVo user=(UserVo) auth.getPrincipal();
        if (user != null) {
            sysLog.setUserId(user.getId().longValue());
            sysLog.setContent(String.format("用户: [%s] | 操作: %s", user.getUsername(), bussinessName));
        } else {
            sysLog.setContent(String.format("访客: [%s] | 操作: %s", sysLog.getIp(), bussinessName));
        }

        try {
            UserAgent agent = UserAgent.parseUserAgentString(ua);
            sysLog.setBrowser(agent.getBrowser().getName());
            sysLog.setOs(agent.getOperatingSystem().getName());
            this.save(sysLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
