package cn.chenc.blog.business.mapper;


import cn.chenc.blog.business.entity.TbPermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TbPermissionMapperCustom {
    List<TbPermission> findPermissionByRoleId(Long roleId);
}
