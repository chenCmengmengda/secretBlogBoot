package cn.chenc.blog.business.service.impl;

import cn.chenc.blog.business.entity.SysPermission;
import cn.chenc.blog.business.entity.SysPermissionCustom;
import cn.chenc.blog.business.mapper.SysPermissionMapper;
import cn.chenc.blog.business.service.SysPermissionService;
import cn.chenc.blog.framework.object.ResponseVO;
import cn.chenc.blog.utils.ResultUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenc
 * @since 2020-03-22
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public ResponseVO selectSysPermissionListPage(int page, int size){
        IPage pageObj = new Page(page, size);

        IPage iPage=sysPermissionMapper.selectSysPermissionListPage(pageObj,null);

        return ResultUtil.success(iPage);
    }

    @Override
    public ResponseVO selectTreePermission(){
        List<SysPermissionCustom> list=sysPermissionMapper.selectTreePermission();
        return ResultUtil.success(list);
    }


}
