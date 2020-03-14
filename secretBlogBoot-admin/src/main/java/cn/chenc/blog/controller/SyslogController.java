package cn.chenc.blog.controller;


import cn.chenc.blog.business.service.SyslogService;
import cn.chenc.blog.framework.pojo.EUDataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/syslog")
public class SyslogController {

    @Autowired
    SyslogService syslogService;

    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridResult getLogList(Integer page,Integer rows) throws Exception{
        EUDataGridResult result=syslogService.getLogList(page,rows);
        return result;
    }



}
