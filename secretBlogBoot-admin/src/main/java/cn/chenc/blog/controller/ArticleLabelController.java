package cn.chenc.blog.controller;


import cn.chenc.blog.business.annoation.BussinessLog;
import cn.chenc.blog.business.entity.ArticleLabel;
import cn.chenc.blog.business.service.ArticleLabelService;
import cn.chenc.blog.framework.object.ResponseVO;
import cn.chenc.blog.utils.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenc
 * @since 2020-04-13
 */
@Controller
@RequestMapping("/articleLabel")
public class ArticleLabelController {

    @Autowired
    private ArticleLabelService articleLabelService;

    /**
     * @description: 查询标签列表
     * @param [page, size]
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/4/13 陈_C
     */
    @BussinessLog("查询文章标签列表")
    @ResponseBody
    @RequestMapping("/list")
    public ResponseVO getArticleLabelList(int page,int size){
        IPage iPage=new Page(page,size);
        IPage list=articleLabelService.page(iPage,null);
        return ResultUtil.success(list);
    }

    /**
     * @description: 添加文章标签
     * @param [articleLabel]
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/4/1 陈_C
     */
    @BussinessLog("添加文章标签")
    @ResponseBody
    @RequestMapping("/add")
    public ResponseVO addSysUser(ArticleLabel articleLabel){
        List<ArticleLabel> list=articleLabelService.list(new QueryWrapper<ArticleLabel>()
                .eq("name",articleLabel.getName()));//判断用户名是否存在
        if(list.size()>0){
            return ResultUtil.error("类别已存在");
        }

        boolean bool=articleLabelService.save(articleLabel);
        if(bool){
            return ResultUtil.success("添加成功");
        } else{
            return ResultUtil.error("添加失败");
        }

    }

    /**
     * @description: 修改文章标签
     * @param [articleLabel]
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/4/1 陈_C
     */
    @BussinessLog("修改文章标签")
    @ResponseBody
    @RequestMapping("/edit")
    public ResponseVO editSysUser(ArticleLabel articleLabel){
        List<ArticleLabel> list=articleLabelService.list(new QueryWrapper<ArticleLabel>()
                .eq("name",articleLabel.getName())
                .ne("id",articleLabel.getId()));//判断用户名是否存在
        if(list.size()>0){
            return ResultUtil.error("类别已存在");
        }
        boolean bool=articleLabelService.updateById(articleLabel);
        if(bool){
            return ResultUtil.success("修改成功");
        } else{
            return ResultUtil.error("修改失败");
        }
    }

    /**
     * @description: 删除文章类别
     * @param [ids]
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/4/1 陈_C
     */
    @BussinessLog("删除文章标签")
    @ResponseBody
    @RequestMapping("/del")
    public ResponseVO delSysUser(Integer[] ids){
        List<Integer> list= Arrays.asList(ids);
        boolean bool=articleLabelService.removeByIds(list);
        if(bool){
            return ResultUtil.success("删除成功");
        } else{
            return ResultUtil.error("删除失败");
        }
    }

}

