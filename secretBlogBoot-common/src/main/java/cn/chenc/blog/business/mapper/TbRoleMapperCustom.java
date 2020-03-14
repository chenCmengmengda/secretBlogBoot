package cn.chenc.blog.business.mapper;

import cn.chenc.blog.business.entity.TbPermission;
import cn.chenc.blog.business.entity.TbRoleCustom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TbRoleMapperCustom {
    List<TbPermission> findOtherPermissions(Long roleId);

    void addPermissionToRole(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    List<TbRoleCustom> findRoleByUserId(Long userId);

    TbRoleCustom findRolePermissionById(Long roleId);
}
