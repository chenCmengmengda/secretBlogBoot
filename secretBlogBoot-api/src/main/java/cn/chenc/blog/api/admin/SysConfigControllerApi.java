package cn.chenc.blog.api.admin;

import cn.chenc.blog.framework.object.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 　@description: TODO
 * 　@author 陈_C
 * 　@date 2020/6/2 20:51
 *
 */
@Api(value="系统配置管理接口",description = "系统配置管理接口")
public interface SysConfigControllerApi {
    @ApiOperation("分页查询系统配置列表")
    public ResponseVO selectSysConfigListPage(int page, int size);
}
