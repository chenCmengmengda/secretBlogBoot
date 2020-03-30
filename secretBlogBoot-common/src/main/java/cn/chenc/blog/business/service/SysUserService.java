package cn.chenc.blog.business.service;

import cn.chenc.blog.business.entity.SysUser;
import cn.chenc.blog.framework.object.ResponseVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenc
 * @since 2020-03-21
 */
public interface SysUserService extends IService<SysUser>, UserDetailsService {

    ResponseVO selectSysUserListPage(int page,int size);

}
