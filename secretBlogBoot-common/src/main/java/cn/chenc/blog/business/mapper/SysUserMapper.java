package cn.chenc.blog.business.mapper;

import cn.chenc.blog.business.entity.SysUser;
import cn.chenc.blog.business.entity.SysUserCustom;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenc
 * @since 2020-03-21
 */
@Mapper
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    public IPage<SysUserCustom> selectSysUserListPage(IPage page, @Param(Constants.WRAPPER) Wrapper<?> queryWrapper);

}
