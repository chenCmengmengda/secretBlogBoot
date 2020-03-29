package cn.chenc.blog.business.service;

import cn.chenc.blog.business.entity.SysConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenc
 * @since 2020-03-24
 */
public interface SysConfigService extends IService<SysConfig> {
    public Map<String,String> getConfigs(String configType);
}
