package cn.chenc.blog.controller;


import cn.chenc.blog.business.entity.TbPhotolist;
import cn.chenc.blog.business.service.PhotoService;
import cn.chenc.blog.framework.pojo.EUDataGridResult;
import cn.chenc.blog.framework.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridResult getPhotoList(Integer page, Integer rows){
        return photoService.getPhotoList(page,rows);
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result addPhoto(@RequestBody TbPhotolist photolist){
   //     System.out.println(photolist.getImages());
        return photoService.addPhoto(photolist);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result deletePhoto(Long[] ids){

        return photoService.deletePhoto(ids);
    }

}
