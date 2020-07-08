package cn.chenc.blog.business.mapper;

import cn.chenc.blog.business.entity.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈_C
 * @since 2020-07-08
 */
@Repository
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

}
