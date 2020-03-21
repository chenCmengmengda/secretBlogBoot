package cn.chenc.blog.business.mapper;

import cn.chenc.blog.business.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
