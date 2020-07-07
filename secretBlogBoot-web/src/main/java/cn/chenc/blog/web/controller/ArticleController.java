package cn.chenc.blog.web.controller;

import cn.chenc.blog.business.annoation.BussinessLog;
import cn.chenc.blog.business.enums.PlatformEnum;
import cn.chenc.blog.business.service.ArticleService;
import cn.chenc.blog.framework.object.ResponseVO;
import cn.chenc.blog.utils.ResultUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 　@description: TODO
 * 　@author 陈_C
 * 　@date 2020/7/4 21:49
 *
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @BussinessLog(value="查询文章列表",platform = PlatformEnum.WEB)
    @RequestMapping("/list")
    public ResponseVO getArticleList(int pageNumber, int pageSize){
        IPage iPage=new Page(pageNumber,pageSize);
        IPage list=articleService.page(iPage,null);
        return ResultUtil.success(list);
    }

}

