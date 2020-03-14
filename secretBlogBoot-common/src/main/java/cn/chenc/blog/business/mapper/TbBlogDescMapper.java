package cn.chenc.blog.business.mapper;


import cn.chenc.blog.business.entity.TbBlogDesc;
import cn.chenc.blog.business.entity.TbBlogDescExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TbBlogDescMapper {
    long countByExample(TbBlogDescExample example);

    int deleteByExample(TbBlogDescExample example);

    int deleteByPrimaryKey(Long blogId);

    int insert(TbBlogDesc record);

    int insertSelective(TbBlogDesc record);

    List<TbBlogDesc> selectByExampleWithBLOBs(TbBlogDescExample example);

    List<TbBlogDesc> selectByExample(TbBlogDescExample example);

    TbBlogDesc selectByPrimaryKey(Long blogId);

    int updateByExampleSelective(@Param("record") TbBlogDesc record, @Param("example") TbBlogDescExample example);

    int updateByExampleWithBLOBs(@Param("record") TbBlogDesc record, @Param("example") TbBlogDescExample example);

    int updateByExample(@Param("record") TbBlogDesc record, @Param("example") TbBlogDescExample example);

    int updateByPrimaryKeySelective(TbBlogDesc record);

    int updateByPrimaryKeyWithBLOBs(TbBlogDesc record);

    int updateByPrimaryKey(TbBlogDesc record);
}
