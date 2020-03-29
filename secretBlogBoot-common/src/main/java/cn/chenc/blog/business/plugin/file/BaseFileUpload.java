package cn.chenc.blog.business.plugin.file;


import cn.chenc.blog.business.enums.ConfigKeyEnum;
import cn.chenc.blog.business.enums.ConfigTypeEnum;
import cn.chenc.blog.business.service.SysConfigService;
import cn.chenc.blog.file.client.ApiClient;
import cn.chenc.blog.file.client.LocalApiClient;
import cn.chenc.blog.file.client.QiniuApiClient;
import cn.chenc.blog.file.exception.FileException;
import cn.chenc.blog.file.util.SpringContextUtil;
import org.springframework.util.StringUtils;

import java.util.Map;

public class BaseFileUpload {

    ApiClient getApiClient(String dir){
        SysConfigService configService = SpringContextUtil.getBean(SysConfigService.class);
        Map<String, String> config = configService.getConfigs(ConfigTypeEnum.UPLOAD.getType());
        String storageType = null;
        if (null == config || StringUtils.isEmpty((storageType = config.get(ConfigKeyEnum.STORAGE_TYPE.getKey())))) {
            throw new FileException("[文件服务]当前系统暂未配置文件服务相关的内容！");
        }
        ApiClient client = null;
        switch (storageType){
            case "local":
                client = new LocalApiClient().init(
                        config.get(ConfigKeyEnum.USE_SM.getKey()),
                        config.get(ConfigKeyEnum.SERVER_URL.getKey()),
                        config.get(ConfigKeyEnum.USE_NGINX.getKey()),
                        config.get(ConfigKeyEnum.NGINX_URL.getKey()),
                        config.get(ConfigKeyEnum.ROOT_PATH.getKey()),
                        dir
                );
                break;
            case "qiniu":
                client = new QiniuApiClient().init(
                        config.get(ConfigKeyEnum.QINIU_ACCESS_KEY.getKey()),
                        config.get(ConfigKeyEnum.QINIU_SECRET_KEY.getKey()),
                        config.get(ConfigKeyEnum.QINIU_BUCKET_NAME.getKey()),
                        config.get(ConfigKeyEnum.QINIU_BASE_PATH.getKey()),
                        dir);
                break;
            case "aliyunOss":
                break;
            default:
                break;
        }
        if(null == client){
            throw new FileException("[文件服务]当前系统暂未配置文件服务相关的内容！");
        }
        return client;
    }
}
