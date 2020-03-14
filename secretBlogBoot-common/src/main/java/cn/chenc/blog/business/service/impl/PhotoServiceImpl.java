package cn.chenc.blog.business.service.impl;

import cn.chenc.blog.business.entity.TbPhotolist;
import cn.chenc.blog.business.entity.TbPhotolistExample;
import cn.chenc.blog.business.mapper.TbPhotolistMapper;
import cn.chenc.blog.business.service.PhotoService;
import cn.chenc.blog.framework.pojo.EUDataGridResult;
import cn.chenc.blog.framework.pojo.Result;
import cn.chenc.blog.utils.IDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private TbPhotolistMapper photolistMapper;


    /**
     *
     *
     * @param page 当前页数
     * @param rows 每页所取行数
     * @return easyUI表格格式对象
     */
    @Override
    public EUDataGridResult getPhotoList(int page,int rows){
        TbPhotolistExample example=new TbPhotolistExample();
        //分页处理
        PageHelper.startPage(page,rows);//设置固定8行
        List<TbPhotolist> list=photolistMapper.selectByExample(example);
        //创建一个返回值对象
        EUDataGridResult result=new EUDataGridResult();
        result.setRows(list);
        //取记录总条数
        PageInfo<TbPhotolist> pageInfo=new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;

    }

    @Override
    public Result addPhoto(TbPhotolist photolist){
        Long photolistId= IDUtils.genItemId();
        photolist.setPhotoId(photolistId);
        //添加时间
        Date date = new Date();
        photolist.setCreateTime(date);
        photolist.setUpdateTime(date);
        photolistMapper.insert(photolist);
        return Result.ok();
    }

    @Override
    public Result deletePhoto(Long[] ids){
        for(Long id:ids){
            photolistMapper.deleteByPrimaryKey(id);
        }
        return Result.ok();
    }
}
