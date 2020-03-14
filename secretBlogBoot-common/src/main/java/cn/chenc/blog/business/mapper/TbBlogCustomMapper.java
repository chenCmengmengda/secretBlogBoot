package cn.chenc.blog.business.mapper;

import cn.chenc.blog.business.entity.TbBlog;
import cn.chenc.blog.business.entity.TbBlogCustom;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * Created by 陈_C on 2018/8/20.
 */
@Mapper
@Repository
public interface TbBlogCustomMapper {
    TbBlogCustom getBlogListById(Long id);
    List<TbBlog> getNewBlogTitle();
    List<TbBlog> getBlogListByCatId(Long catId);
    TbBlog getPreBlogByTime(Date createTime);
    TbBlog getNextBlogByTime(Date createTime);
}
