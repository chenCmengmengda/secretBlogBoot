package cn.chenc.blog.business.service;

import cn.chenc.blog.business.entity.SysUser;
import cn.chenc.blog.framework.pojo.ResponseVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenc
 * @since 2020-03-21
 */
public interface SysUserService extends IService<SysUser> {

    ResponseVO selectSysUserListPage(int page,int size);

}
