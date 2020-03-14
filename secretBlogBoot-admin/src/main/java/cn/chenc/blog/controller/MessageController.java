package cn.chenc.blog.controller;

import cn.chenc.blog.business.entity.TbMessageCustom;
import cn.chenc.blog.business.service.MessageService;
import cn.chenc.blog.framework.pojo.EUDataGridResult;
import cn.chenc.blog.framework.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridResult getMessageList(Integer page, Integer rows){
        EUDataGridResult result=messageService.getMessageList(page,rows);
        return result;
    }

    @RequestMapping("/findOne")
    @ResponseBody
    public Result findOneMessage(Long messageId){
        Result result=messageService.findOneMessage(messageId);
        return result;
    }

    @RequestMapping("/addReply")
    @ResponseBody
    public Result addReply(@RequestBody TbMessageCustom messageCustom){
        Result result=messageService.addReply(messageCustom);
        return Result.ok();
    }
}
