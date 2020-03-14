package cn.chenc.blog.business.service;


import cn.chenc.blog.business.entity.TbBlogCat;
import cn.chenc.blog.business.entity.TbBlogWithBLOBs;
import cn.chenc.blog.framework.pojo.EUDataGridResult;
import cn.chenc.blog.framework.pojo.Result;

/**
 * Created by 陈_C on 2018/8/7.
 */
public interface BlogService {
    Result createBlog(TbBlogWithBLOBs blog) throws Exception;
    EUDataGridResult blogList(int page, int rows);
    Result readBlog(Long id);
    Result editBlog(TbBlogWithBLOBs blog) throws Exception;
    Result findOne(long id);
    Result deleteBlog(Long[] ids);
    Result getNewBlogTitle(Integer rows);
    EUDataGridResult getBlogCatList(Integer page, Integer rows);
    Result addBlogCat(TbBlogCat blogCat);
    Result editBlogCat(TbBlogCat blogCat);
    Result deleteBlogCat(Long[] ids);
}
