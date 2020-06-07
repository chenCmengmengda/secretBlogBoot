package cn.chenc.blog.business.mapper;

import cn.chenc.blog.business.entity.TableInfo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 　@description: TODO
 * 　@author 陈_C
 * 　@date 2020/6/7 20:18
 *
 */
@Mapper
@Repository
public interface GenMapper {
    IPage<TableInfo> queryTablePage(IPage page, @Param(Constants.WRAPPER) Wrapper<?> queryWrapper);
}
