package cn.chenc.blog.controller;


import cn.chenc.blog.business.annoation.BussinessLog;
import cn.chenc.blog.business.entity.Photo;
import cn.chenc.blog.business.service.PhotoService;
import cn.chenc.blog.framework.object.ResponseVO;
import cn.chenc.blog.utils.ResultUtil;
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
 * @since 2020-04-07
 */
@Controller
@RequestMapping("/photo")
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @ResponseBody
    @RequestMapping("/list")
    public ResponseVO photoList(){
       List<Photo> list=photoService.list(null);
       return ResultUtil.success(list);
    }


    @ResponseBody
    @RequestMapping("/add")
    public ResponseVO addPhoto(Photo photo){
        Boolean bool=photoService.save(photo);
        if(bool){
            return ResultUtil.success("提交成功");
        } else{
            return ResultUtil.error("提交失败");
        }
    }

    @BussinessLog("删除相册图片")
    @ResponseBody
    @RequestMapping("/del")
    public ResponseVO delPhoto(Integer[] ids){
        List<Integer> list= Arrays.asList(ids);
        boolean bool = photoService.removeByIds(list);
        if(bool){
            return ResultUtil.success("删除成功");
        } else{
            return ResultUtil.error("删除失败");
        }
    }


}

