package cn.chenc.blog.business.service;


import cn.chenc.blog.business.entity.TbPhotolist;
import cn.chenc.blog.framework.pojo.EUDataGridResult;
import cn.chenc.blog.framework.pojo.Result;

public interface PhotoService {
    EUDataGridResult getPhotoList(int page, int rows);
    Result addPhoto(TbPhotolist photolist);
    Result deletePhoto(Long[] ids);
}
