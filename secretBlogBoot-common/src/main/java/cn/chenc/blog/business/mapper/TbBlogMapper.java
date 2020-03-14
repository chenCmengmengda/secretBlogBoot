package cn.chenc.blog.business.mapper;


import cn.chenc.blog.business.entity.TbBlog;
import cn.chenc.blog.business.entity.TbBlogExample;
import cn.chenc.blog.business.entity.TbBlogWithBLOBs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TbBlogMapper {
    long countByExample(TbBlogExample example);

    int deleteByExample(TbBlogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbBlogWithBLOBs record);

    int insertSelective(TbBlogWithBLOBs record);

    List<TbBlogWithBLOBs> selectByExampleWithBLOBs(TbBlogExample example);

    List<TbBlog> selectByExample(TbBlogExample example);

    TbBlogWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbBlogWithBLOBs record, @Param("example") TbBlogExample example);

    int updateByExampleWithBLOBs(@Param("record") TbBlogWithBLOBs record, @Param("example") TbBlogExample example);

    int updateByExample(@Param("record") TbBlog record, @Param("example") TbBlogExample example);

    int updateByPrimaryKeySelective(TbBlogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TbBlogWithBLOBs record);

    int updateByPrimaryKey(TbBlog record);
}
