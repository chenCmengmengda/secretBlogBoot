package cn.chenc.blog.business.mapper;

import cn.chenc.blog.business.entity.TbPhotolist;
import cn.chenc.blog.business.entity.TbPhotolistExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TbPhotolistMapper {
    long countByExample(TbPhotolistExample example);

    int deleteByExample(TbPhotolistExample example);

    int deleteByPrimaryKey(Long photoId);

    int insert(TbPhotolist record);

    int insertSelective(TbPhotolist record);

    List<TbPhotolist> selectByExample(TbPhotolistExample example);

    TbPhotolist selectByPrimaryKey(Long photoId);

    int updateByExampleSelective(@Param("record") TbPhotolist record, @Param("example") TbPhotolistExample example);

    int updateByExample(@Param("record") TbPhotolist record, @Param("example") TbPhotolistExample example);

    int updateByPrimaryKeySelective(TbPhotolist record);

    int updateByPrimaryKey(TbPhotolist record);
}
