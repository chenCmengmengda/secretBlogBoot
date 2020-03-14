package cn.chenc.blog.business.mapper;

import cn.chenc.blog.business.entity.TbBlogCat;
import cn.chenc.blog.business.entity.TbBlogCatExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TbBlogCatMapper {
    long countByExample(TbBlogCatExample example);

    int deleteByExample(TbBlogCatExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbBlogCat record);

    int insertSelective(TbBlogCat record);

    List<TbBlogCat> selectByExample(TbBlogCatExample example);

    TbBlogCat selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbBlogCat record, @Param("example") TbBlogCatExample example);

    int updateByExample(@Param("record") TbBlogCat record, @Param("example") TbBlogCatExample example);

    int updateByPrimaryKeySelective(TbBlogCat record);

    int updateByPrimaryKey(TbBlogCat record);
}
