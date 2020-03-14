package cn.chenc.blog.business.service.impl;

import cn.chenc.blog.business.entity.TbPermission;
import cn.chenc.blog.business.entity.TbPermissionExample;
import cn.chenc.blog.business.mapper.TbPermissionMapper;
import cn.chenc.blog.business.service.PermissionService;
import cn.chenc.blog.framework.pojo.EUDataGridResult;
import cn.chenc.blog.framework.pojo.Result;
import cn.chenc.blog.utils.IDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    TbPermissionMapper permissionMapper;

    @Override
    public EUDataGridResult gerPermissionList(Integer page, Integer rows) {
        TbPermissionExample example=new TbPermissionExample();
        //分页处理
        PageHelper.startPage(page,rows);
        List<TbPermission> list=permissionMapper.selectByExample(example);
        //创建一个返回值对象
        EUDataGridResult result=new EUDataGridResult();
        result.setRows(list);
        //取记录总条数
        PageInfo<TbPermission> pageInfo=new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public Result addPermission(TbPermission permission) {
        permission.setId(IDUtils.genItemId());
        permissionMapper.insert(permission);
        return Result.ok();
    }
}
