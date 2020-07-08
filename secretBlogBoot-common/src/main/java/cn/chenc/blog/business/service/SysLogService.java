package cn.chenc.blog.business.service;

import cn.chenc.blog.business.entity.SysLog;
import cn.chenc.blog.business.enums.PlatformEnum;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈_C
 * @since 2020-07-08
 */
public interface SysLogService extends IService<SysLog> {

    void asyncSaveSystemLog(PlatformEnum platform, String bussinessName);
}
