package cn.chenc.blog.controller;


import cn.chenc.blog.business.entity.TbSysParameter;
import cn.chenc.blog.business.service.SysParameterService;
import cn.chenc.blog.framework.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys")
public class SysParameterController {

    @Autowired
    SysParameterService sysParameterService;

    @RequestMapping("getSysParameter")
    @ResponseBody
    public Result getSysParameter() {
        return sysParameterService.getSysParameter();
    }

    @RequestMapping("editSysParameter")
    @ResponseBody
    public Result editSysParameter(@RequestBody TbSysParameter sysParameter) {
        return sysParameterService.editSysParameter(sysParameter);
    }

}
