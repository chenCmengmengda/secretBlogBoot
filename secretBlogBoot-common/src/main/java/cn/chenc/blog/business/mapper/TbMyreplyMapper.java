package cn.chenc.blog.business.mapper;


import cn.chenc.blog.business.entity.TbMyreply;
import cn.chenc.blog.business.entity.TbMyreplyExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TbMyreplyMapper {
    long countByExample(TbMyreplyExample example);

    int deleteByExample(TbMyreplyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbMyreply record);

    int insertSelective(TbMyreply record);

    List<TbMyreply> selectByExampleWithBLOBs(TbMyreplyExample example);

    List<TbMyreply> selectByExample(TbMyreplyExample example);

    TbMyreply selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbMyreply record, @Param("example") TbMyreplyExample example);

    int updateByExampleWithBLOBs(@Param("record") TbMyreply record, @Param("example") TbMyreplyExample example);

    int updateByExample(@Param("record") TbMyreply record, @Param("example") TbMyreplyExample example);

    int updateByPrimaryKeySelective(TbMyreply record);

    int updateByPrimaryKeyWithBLOBs(TbMyreply record);

    int updateByPrimaryKey(TbMyreply record);
}
