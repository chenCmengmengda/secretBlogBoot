package cn.chenc.blog.api.admin;

import cn.chenc.blog.business.entity.SysUser;
import cn.chenc.blog.framework.object.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 　@description: TODO
 * 　@author 陈_C
 * 　@date 2020/6/2 21:49
 *
 */
@Api(value="系统用户管理接口",description = "系统用户管理接口")
public interface SysUserControllerApi {
    @ApiOperation("登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username",value = "用户名",required=true,paramType="query",dataType="String"),
            @ApiImplicitParam(name="password",value = "密码",required=true,paramType="query",dataType="String"),
            @ApiImplicitParam(name="kaptcha",value = "图片验证码",required=true,paramType="query",dataType="String")
    })
    public ResponseVO login(SysUser sysUser, HttpServletRequest request, HttpServletResponse response);
}
