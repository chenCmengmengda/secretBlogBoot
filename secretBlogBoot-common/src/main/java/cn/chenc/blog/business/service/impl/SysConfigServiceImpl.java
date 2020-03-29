package cn.chenc.blog.business.service.impl;

import cn.chenc.blog.business.entity.SysConfig;
import cn.chenc.blog.business.mapper.SysConfigMapper;
import cn.chenc.blog.business.service.SysConfigService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenc
 * @since 2020-03-24
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    /**
     * 获取配置参数
     * @param configType
     * @return
     */
    @Override
    public Map<String,String> getConfigs(String configType){
        SysConfig configDO = new SysConfig();
        if(StringUtils.isNotBlank(configType)){
            configDO.setConfigType(configType);
        }
        List<SysConfig> list = this.list(new QueryWrapper<SysConfig>().eq("config_type", configDO.getConfigType()));
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        Map<String, String> res = new HashMap<>();
        list.forEach(config->{
            res.put(String.valueOf(config.getConfigCode()),config.getConfigValue());
        });

        return res;
    }
}
