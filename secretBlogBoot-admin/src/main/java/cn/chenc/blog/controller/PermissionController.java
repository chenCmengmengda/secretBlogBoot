package cn.chenc.blog.controller;


import cn.chenc.blog.business.entity.TbPermission;
import cn.chenc.blog.business.service.PermissionService;
import cn.chenc.blog.framework.pojo.EUDataGridResult;
import cn.chenc.blog.framework.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridResult gerPermissionList(Integer page, Integer rows) {
        EUDataGridResult result=permissionService.gerPermissionList(page,rows);
        return result;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result addPermission(@RequestBody TbPermission permission) {
        Result result=permissionService.addPermission(permission);
        return result;
    }

}
