package cn.chenc.blog.business.service;


import cn.chenc.blog.business.entity.Syslog;
import cn.chenc.blog.framework.pojo.EUDataGridResult;
import cn.chenc.blog.framework.pojo.Result;

public interface SyslogService {
    Result addLog(Syslog syslog) throws Exception;

    EUDataGridResult getLogList(int page, int size) throws Exception;
}
