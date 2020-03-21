package cn.chenc.blog.business.service.impl;

import cn.chenc.blog.business.entity.SysUser;
import cn.chenc.blog.business.mapper.SysUserMapper;
import cn.chenc.blog.business.service.SysUserService;
import cn.chenc.blog.framework.pojo.ResponseVO;
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
 * @since 2020-03-21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    public ResponseVO selectSysUserListPage(int page, int size){
        Page pageObj = new Page(page, size);
        IPage iPage= sysUserMapper.selectPage(pageObj,null);
        return ResultUtil.success(iPage);
    }
}
