package cn.chenc.blog.business.service.impl;


import cn.chenc.blog.business.entity.Syslog;
import cn.chenc.blog.business.entity.SyslogExample;
import cn.chenc.blog.business.mapper.SyslogMapper;
import cn.chenc.blog.business.service.SyslogService;
import cn.chenc.blog.framework.pojo.EUDataGridResult;
import cn.chenc.blog.framework.pojo.Result;
import cn.chenc.blog.utils.IDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SyslogServiceImpl implements SyslogService {

    @Autowired
    SyslogMapper syslogMapper;

    /**
     *
     * 添加日志
     * @param syslog
     * @return
     * @throws Exception
     */
    @Override
    public Result addLog(Syslog syslog) throws Exception {
        syslog.setId(IDUtils.genItemId());
        syslogMapper.insert(syslog);
        return Result.ok();
    }

    @Override
    public EUDataGridResult getLogList(int page, int rows) throws Exception {
        SyslogExample example=new SyslogExample();
        //分页处理
        PageHelper.startPage(page,rows);
        List<Syslog> list=syslogMapper.selectByExample(example);
        //创建一个返回值对象
        EUDataGridResult result=new EUDataGridResult();
        result.setRows(list);
        //取记录总条数
        PageInfo<Syslog> pageInfo=new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }
}
