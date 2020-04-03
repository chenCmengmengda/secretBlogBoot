package cn.chenc.blog.business.mapper;

import cn.chenc.blog.business.entity.SysPermission;
import cn.chenc.blog.business.entity.SysPermissionCustom;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenc
 * @since 2020-03-22
 */
@Mapper
@Repository
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    public IPage<SysPermissionCustom> selectSysPermissionListPage(IPage page, @Param(Constants.WRAPPER) Wrapper<?> queryWrapper);

    public List<SysPermissionCustom> selectTreePermission();

    public List<SysPermissionCustom> selectMenu();
}
