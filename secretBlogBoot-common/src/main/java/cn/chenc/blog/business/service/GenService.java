package cn.chenc.blog.business.service;

import cn.chenc.blog.business.entity.TableInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 　@description: TODO
 * 　@author 陈_C
 * 　@date 2020/6/7 20:30
 *
 */
public interface GenService {
    public IPage<TableInfo> queryTablePage(int page,int size);
}
