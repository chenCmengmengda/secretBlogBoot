package cn.chenc.blog.business.mapper;

import cn.chenc.blog.business.entity.TbUserRoleExample;
import cn.chenc.blog.business.entity.TbUserRoleKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TbUserRoleMapper {
    long countByExample(TbUserRoleExample example);

    int deleteByExample(TbUserRoleExample example);

    int deleteByPrimaryKey(TbUserRoleKey key);

    int insert(TbUserRoleKey record);

    int insertSelective(TbUserRoleKey record);

    List<TbUserRoleKey> selectByExample(TbUserRoleExample example);

    int updateByExampleSelective(@Param("record") TbUserRoleKey record, @Param("example") TbUserRoleExample example);

    int updateByExample(@Param("record") TbUserRoleKey record, @Param("example") TbUserRoleExample example);
}
