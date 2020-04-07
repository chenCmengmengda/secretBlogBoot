package cn.chenc.blog.business.service.impl;

import cn.chenc.blog.business.entity.Article;
import cn.chenc.blog.business.mapper.ArticleMapper;
import cn.chenc.blog.business.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenc
 * @since 2020-04-07
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
