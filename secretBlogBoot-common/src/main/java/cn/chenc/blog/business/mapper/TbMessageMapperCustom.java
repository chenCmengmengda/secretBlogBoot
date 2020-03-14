package cn.chenc.blog.business.mapper;

import cn.chenc.blog.business.entity.TbMessageCustom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TbMessageMapperCustom {
    void addForeignKey(@Param("messageId") Long messageId, @Param("myreplyId") Long myreplyId);
    List<TbMessageCustom> findAll();
    TbMessageCustom findOne(@Param("messageId") Long messageId);
}
