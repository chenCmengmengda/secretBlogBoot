package cn.chenc.blog.controller;

import cn.chenc.blog.business.consts.CommonConst;
import cn.chenc.blog.file.util.FileUtil;
import cn.chenc.blog.framework.object.LayUITreeVo;
import cn.chenc.blog.framework.object.ResponseVO;
import cn.chenc.blog.framework.object.Result;
import cn.chenc.blog.utils.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 　@description: TODO
 * 　@author 陈_C
 * 　@date 2020/6/16 15:18
 *
 */
@RestController
@RequestMapping("/sysFile")
public class SysFileController {

    /**
     * 列表
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseVO list() throws FileNotFoundException {
        String filePath = ResourceUtils.getURL("classpath:").getPath();
        try {
            filePath = URLDecoder.decode(filePath,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<LayUITreeVo> fileList = new ArrayList<>();
        fileList=getAllFilePaths(filePath,0,"");
        return ResultUtil.success(fileList);
    }

    /**
     * 递归获取某目录下的所有子目录以及子文件
     * @param filePath
     * @param filePathList
     * @return
     */
    private static List<LayUITreeVo> getAllFilePaths(String filePath,
                                                 Integer level,String parentPath) {
        File[] files = new File(filePath).listFiles();
        List<LayUITreeVo> filePathList=new ArrayList<>();
        if (files == null) {
            return filePathList;
        }
        for (File file : files) {
            int num = filePathList.size()+1;
            LayUITreeVo sysFile = new LayUITreeVo();
            sysFile.setTitle(file.getName());
            sysFile.setId(num);
            if (file.isDirectory()) {
                if(level==0){
                    if(file.getName().equals("templates")||
                            file.getName().equals("static")){
                        filePathList.add(sysFile);
                        parentPath = CommonConst.SF_FILE_SEPARATOR+file.getName();
                        List<LayUITreeVo> children=getAllFilePaths(file.getAbsolutePath(),num,parentPath);
                        sysFile.setChildren(children);
                        num++;
                    }
                }else{
                    filePathList.add(sysFile);
                    String subParentPath = parentPath+CommonConst.SF_FILE_SEPARATOR+file.getName();
                    List<LayUITreeVo> children=getAllFilePaths(file.getAbsolutePath(),num,subParentPath);
                    sysFile.setChildren(children);
                    num++;
                }
            } else {
                if(level!=0){
                    sysFile.setPath(parentPath+CommonConst.SF_FILE_SEPARATOR+file.getName());
                    filePathList.add(sysFile);
                    num++;
                }
            }
        }
        return filePathList;
    }

    /**
     * 获取内容
     * @return
     */
    @RequestMapping(value = "/getContent", method = RequestMethod.POST)
    public ResponseVO getContent(String filePath) throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:").getPath();
        try {
            path = URLDecoder.decode(path,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String content = FileUtil.readUtf8String(path+filePath);
        return ResultUtil.success((Object) content);
    }


}
