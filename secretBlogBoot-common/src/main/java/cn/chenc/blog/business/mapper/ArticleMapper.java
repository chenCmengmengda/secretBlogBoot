package cn.chenc.blog.business.mapper;

import cn.chenc.blog.business.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenc
 * @since 2020-04-07
 */
@Mapper
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

}
