package cn.chenc.blog.business.service.impl;

import cn.chenc.blog.business.entity.SysRole;
import cn.chenc.blog.business.entity.SysUser;
import cn.chenc.blog.business.mapper.SysRoleMapper;
import cn.chenc.blog.business.mapper.SysUserMapper;
import cn.chenc.blog.business.service.SysUserService;
import cn.chenc.blog.framework.object.ResponseVO;
import cn.chenc.blog.utils.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    public ResponseVO selectSysUserListPage(int page, int size){
        IPage pageObj = new Page(page, size);
    //    IPage iPage= sysUserMapper.selectPage(pageObj,null);
        IPage iPage=sysUserMapper.selectSysUserListPage(pageObj,null);

        return ResultUtil.success(iPage);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SysUser> list=sysUserMapper.selectList(new QueryWrapper<SysUser>().eq("username",username));
        if(list.size()==0) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        SysUser sysUser=list.get(0);

        SysRole role= sysRoleMapper.selectById(sysUser.getRoleId());
        List<SysRole> roleList=new ArrayList<SysRole>();
        roleList.add(role);
        User user = new User(sysUser.getUsername(), sysUser.getPassword(), sysUser.getStatus() == 0 ? true : false, true, true, true, getAuthority(roleList));

        return user;
    }

    //作用就是返回一个List集合，集合中装入的是角色描述
    public List<SimpleGrantedAuthority> getAuthority(List<SysRole> roles) {

        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (SysRole role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_" + role.getRolename()));
        }
        return list;
    }
}
