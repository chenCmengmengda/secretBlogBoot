package cn.chenc.blog.business.service;


import cn.chenc.blog.business.entity.TbSysParameter;
import cn.chenc.blog.framework.pojo.Result;

public interface SysParameterService {
    Result  getSysParameter();
    Result editSysParameter(TbSysParameter sysParameter);
}

