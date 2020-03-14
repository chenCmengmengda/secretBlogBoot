package cn.chenc.blog.business.mapper;

import cn.chenc.blog.business.entity.TbRole;
import cn.chenc.blog.business.entity.TbUser;
import cn.chenc.blog.business.entity.TbUserCustom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TbUserMapperCustom {
    int editPassword(TbUser user);

    List<TbRole> findOtherRoles(Long userId);

    void addRoleToUser(@Param("userId") Long userId, @Param("roleId") Long roleId);

    TbUserCustom findUserRolePermissionById(Long id);
}
