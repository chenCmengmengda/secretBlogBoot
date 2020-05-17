package cn.chenc.blog.controller;


import cn.chenc.blog.business.annoation.BussinessLog;
import cn.chenc.blog.business.entity.Article;
import cn.chenc.blog.business.entity.ArticleCategory;
import cn.chenc.blog.business.entity.ArticleLabel;
import cn.chenc.blog.business.entity.ArticleLabelKey;
import cn.chenc.blog.business.service.ArticleCategoryService;
import cn.chenc.blog.business.service.ArticleLabelKeyService;
import cn.chenc.blog.business.service.ArticleLabelService;
import cn.chenc.blog.business.service.ArticleService;
import cn.chenc.blog.framework.object.ResponseVO;
import cn.chenc.blog.utils.ResultUtil;
import cn.chenc.blog.utils.TextUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.Action;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 * 文章管理
 * @author chenc
 * @since 2020-04-13
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleCategoryService articleCategoryService;
    @Autowired
    private ArticleLabelService articleLabelService;
    @Autowired
    private ArticleLabelKeyService articleLabelKeyService;

    /**
     * @description: 返回所有类别
     * @param []
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/5/17 陈_C
     */
    @ResponseBody
    @RequestMapping("/allCategory")
    public ResponseVO selectAllCategory(){
        List<ArticleCategory> list=articleCategoryService.list(null);
        List<Map<String,Object>> resultList=new ArrayList<>();
        for(ArticleCategory articleCategory:list){
            Map<String,Object> map=new HashMap<>();
            map.put("name",articleCategory.getName());
            map.put("value",articleCategory.getId());
            resultList.add(map);
        }
        return ResultUtil.success(resultList);
    }

    @ResponseBody
    @RequestMapping("/allLabel")
    public ResponseVO selectAllLabel(){
        List<ArticleLabel> list=articleLabelService.list(null);
        List<Map<String,Object>> resultList=new ArrayList<>();
        for(ArticleLabel articleLabel:list){
            Map<String,Object> map=new HashMap<>();
            map.put("name",articleLabel.getName());
            map.put("value",articleLabel.getId());
            resultList.add(map);
        }
        return ResultUtil.success(resultList);
    }

    @BussinessLog("查询文章列表")
    @ResponseBody
    @RequestMapping("/list")
    public ResponseVO getArticleList(int page, int size){
        IPage iPage=new Page(page,size);
        IPage list=articleService.page(iPage,null);
        return ResultUtil.success(list);
    }

    @ResponseBody
    @RequestMapping("/add")
    public ResponseVO addArticle(Article article){
        article.setSummary(TextUtil.mdToText(article.getContent()));
        if(article.getStatus()!=1){
            article.setReleaseTime(LocalDateTime.now());
        }
        boolean b=articleService.save(article);
        boolean b2=false;
        if(b){
            for(Integer labelId:article.getLabelIds()){
                ArticleLabelKey articleLabelKey=new ArticleLabelKey();
                articleLabelKey.setArticleId(article.getId());
                articleLabelKey.setLabelId(labelId);
                b2=articleLabelKeyService.save(articleLabelKey);
            }
        }
        if(b2){
            return ResultUtil.success("添加成功");
        } else{
            return ResultUtil.error("添加失败");
        }
    }

}

