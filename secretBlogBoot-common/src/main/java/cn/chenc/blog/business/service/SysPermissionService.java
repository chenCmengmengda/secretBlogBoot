package cn.chenc.blog.business.service;

import cn.chenc.blog.business.entity.SysPermission;
import cn.chenc.blog.framework.object.ResponseVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenc
 * @since 2020-03-22
 */
public interface SysPermissionService extends IService<SysPermission> {
    public ResponseVO selectSysPermissionListPage(int page, int size);
    public ResponseVO selectTreePermission();
}
