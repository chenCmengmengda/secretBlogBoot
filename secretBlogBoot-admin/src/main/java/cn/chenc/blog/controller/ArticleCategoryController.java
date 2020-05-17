package cn.chenc.blog.controller;


import cn.chenc.blog.business.annoation.BussinessLog;
import cn.chenc.blog.business.entity.ArticleCategory;
import cn.chenc.blog.business.service.ArticleCategoryService;
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
@RequestMapping("/articleCategory")
public class ArticleCategoryController {

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @ResponseBody
    @RequestMapping("/all")
    public ResponseVO selectAll(){
        List<ArticleCategory> list=articleCategoryService.list(null);
        return ResultUtil.success(list);
    }

    @ResponseBody
    @RequestMapping("/list")
    public ResponseVO getarticleCategoryList(int page,int size){
        IPage iPage=new Page(page,size);
        IPage list=articleCategoryService.page(iPage,null);
        return ResultUtil.success(list);
    }

    /**
     * @description: 添加文章类别
     * @param [sysUser]
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/4/1 陈_C
     */
    @BussinessLog("添加文章类别")
    @ResponseBody
    @RequestMapping("/add")
    public ResponseVO addSysUser(ArticleCategory articleCategory){
        List<ArticleCategory> list=articleCategoryService.list(new QueryWrapper<ArticleCategory>()
                .eq("name",articleCategory.getName()));//判断用户名是否存在
        if(list.size()>0){
            return ResultUtil.error("类别已存在");
        }

        boolean bool=articleCategoryService.save(articleCategory);
        if(bool){
            return ResultUtil.success("添加成功");
        } else{
            return ResultUtil.error("添加失败");
        }

    }

    /**
     * @description: 修改文章类别
     * @param [sysUser, request]
     * @return cn.chenc.blog.framework.object.ResponseVO
     * @throws
     * @author 陈_C
     * @date 2020/4/1 陈_C
     */
    @BussinessLog("修改文章类别")
    @ResponseBody
    @RequestMapping("/edit")
    public ResponseVO editSysUser(ArticleCategory articleCategory){
        List<ArticleCategory> list=articleCategoryService.list(new QueryWrapper<ArticleCategory>()
                .eq("name",articleCategory.getName())
                .ne("id",articleCategory.getId()));//判断用户名是否存在
        if(list.size()>0){
            return ResultUtil.error("类别已存在");
        }
        boolean bool=articleCategoryService.updateById(articleCategory);
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
    @BussinessLog("删除文章类别")
    @ResponseBody
    @RequestMapping("/del")
    public ResponseVO delSysUser(Integer[] ids){
        List<Integer> list= Arrays.asList(ids);
        boolean bool=articleCategoryService.removeByIds(list);
        if(bool){
            return ResultUtil.success("删除成功");
        } else{
            return ResultUtil.error("删除失败");
        }
    }


}

