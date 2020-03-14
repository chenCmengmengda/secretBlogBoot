package cn.chenc.blog.business.service.impl;


import cn.chenc.blog.business.entity.TbMessage;
import cn.chenc.blog.business.entity.TbMessageCustom;
import cn.chenc.blog.business.entity.TbMessageExample;
import cn.chenc.blog.business.mapper.TbMessageMapper;
import cn.chenc.blog.business.mapper.TbMessageMapperCustom;
import cn.chenc.blog.business.mapper.TbMyreplyMapper;
import cn.chenc.blog.business.service.MessageService;
import cn.chenc.blog.framework.pojo.EUDataGridResult;
import cn.chenc.blog.framework.pojo.Result;
import cn.chenc.blog.utils.IDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    TbMessageMapper messageMapper;
    @Autowired
    TbMessageMapperCustom messageMapperCustom;
    @Autowired
    TbMyreplyMapper myreplyMapper;

    /**
     * 查询评论回复
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EUDataGridResult getMessageList(Integer page, Integer rows) {
        TbMessageExample messageExample=new TbMessageExample();
        PageHelper.startPage(page,rows);
        List<TbMessage> list=messageMapper.selectByExampleWithBLOBs(messageExample);
        //创建一个返回值对象
        EUDataGridResult result=new EUDataGridResult();
        result.setRows(list);
        //取记录总条数
        PageInfo<TbMessage> pageInfo=new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public Result findOneMessage(Long messageId){
        TbMessageCustom messageCustom=messageMapperCustom.findOne(messageId);
        return Result.ok(messageCustom);
    }

    /**
     * 添加回复
     * @param messageCustom
     * @return
     */
    @Override
    public Result addReply(TbMessageCustom messageCustom){
        Long id= IDUtils.genItemId();
        Date date=new Date();
        if(messageCustom.getMyreply().getId()==null){
            messageCustom.getMyreply().setId(id);
            messageCustom.getMyreply().setCreateTime(date);//更新回复时间
            myreplyMapper.insert(messageCustom.getMyreply());//添加回复
            messageMapperCustom.addForeignKey(messageCustom.getId(),id);//更新外键
        } else{
            messageMapperCustom.addForeignKey(messageCustom.getId(),null);
            myreplyMapper.deleteByPrimaryKey(messageCustom.getMyreply().getId());
            messageCustom.getMyreply().setId(id);
            messageCustom.getMyreply().setCreateTime(date);
            myreplyMapper.insert(messageCustom.getMyreply());
            messageMapperCustom.addForeignKey(messageCustom.getId(),id);
        }
        return Result.ok();
    }

}
