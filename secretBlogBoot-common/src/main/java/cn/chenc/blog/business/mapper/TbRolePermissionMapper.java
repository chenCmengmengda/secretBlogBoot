package cn.chenc.blog.business.mapper;

import cn.chenc.blog.business.entity.TbRolePermissionExample;
import cn.chenc.blog.business.entity.TbRolePermissionKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TbRolePermissionMapper {
    long countByExample(TbRolePermissionExample example);

    int deleteByExample(TbRolePermissionExample example);

    int deleteByPrimaryKey(TbRolePermissionKey key);

    int insert(TbRolePermissionKey record);

    int insertSelective(TbRolePermissionKey record);

    List<TbRolePermissionKey> selectByExample(TbRolePermissionExample example);

    int updateByExampleSelective(@Param("record") TbRolePermissionKey record, @Param("example") TbRolePermissionExample example);

    int updateByExample(@Param("record") TbRolePermissionKey record, @Param("example") TbRolePermissionExample example);
}
