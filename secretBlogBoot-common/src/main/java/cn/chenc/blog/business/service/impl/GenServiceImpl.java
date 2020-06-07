package cn.chenc.blog.business.service.impl;

import cn.chenc.blog.business.entity.TableInfo;
import cn.chenc.blog.business.mapper.GenMapper;
import cn.chenc.blog.business.service.GenService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 　@description: TODO
 * 　@author 陈_C
 * 　@date 2020/6/7 20:37
 *
 */
@Service
public class GenServiceImpl implements GenService {
    @Autowired
    private GenMapper genMapper;


    @Override
    public IPage<TableInfo> queryTablePage(int page, int size) {
        IPage pageObj = new Page(page, size);
        //    IPage iPage= sysUserMapper.selectPage(pageObj,null);
        IPage iPage=genMapper.queryTablePage(pageObj,null);
        return iPage;
    }
}
