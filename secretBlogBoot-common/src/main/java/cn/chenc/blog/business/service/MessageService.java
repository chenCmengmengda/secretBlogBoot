package cn.chenc.blog.business.service;


import cn.chenc.blog.business.entity.TbMessageCustom;
import cn.chenc.blog.framework.pojo.EUDataGridResult;
import cn.chenc.blog.framework.pojo.Result;

public interface MessageService {
    EUDataGridResult getMessageList(Integer page, Integer rows);
    Result findOneMessage(Long messageId);
    Result addReply(TbMessageCustom messageCustom);
}
