package com.px.project.app.controller;

import com.px.project.common.base.Result;
import com.px.project.common.utils.TokenUtil;
import com.px.project.web.dto.ResetPasswordDto;
import com.px.project.web.dto.UserDto;
import com.px.project.web.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @Package com.px.project.web.controller
 * @ClassName: UserController
 * @Description: Controller
 * @author 品讯科技
 * @time   2019-09-17 10:24:19
 * @version V 1.0
 */
@RestController
@Api(tags = "AppUserController", description = "对User的业务处理controller")
@RequestMapping("/app/user")
public class AppUserController {
    private static Logger logger = LoggerFactory.getLogger(AppUserController.class);
    @Autowired
    private IUserService userService;//service

    /**
     * 重置密码
     * @author 品讯科技
     * @time 2020-03-08 02:39:36
     */
    @ApiOperation(value = "重置密码", notes = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization",value = "身份认证Token"),
            @ApiImplicitParam(name = "resetPasswordDto", value = "实体对象", required = true, dataType = "ResetPasswordDto")
    })
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public Result resetPassword(@RequestHeader(value = "Authorization") String token, @RequestBody @Validated ResetPasswordDto resetPasswordDto, BindingResult result) {
        try{
            return userService.resetPassword (TokenUtil.getUserId (token),TokenUtil.getUser (token).getString ("loginName"),resetPasswordDto.getPassWord ());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.ERROR();
        }
    }

    /**
     * @name findByUserId
     * @author 品讯科技
     * @param id
     * @Description 根据主键id查看信息
     * @time 2019年9月13日
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
            @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/findByUserId/{id}", method = RequestMethod.GET)
    public Result findByUserId(@PathVariable(value = "id") String id) {
        return userService.findByUserId(id);
    }
    /**
     * @name porjectDropdown
     * @author 品讯科技
     * @param id
     * @Description 根据主键id查看信息
     * @time 2019年9月13日
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
            @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/porjectDropdown/{id}", method = RequestMethod.GET)
    public Result porjectDropdown(@PathVariable(value = "id") String id) {
        return userService.porjectDropdown(id);
    }

    /**
     * @name findList
     * @author 品讯科技
     * @param userDto
     *      {
     *          "pageNum":"",页码
     *          "pageSize":"",每页长度
     *          "condition":""条件
     *      }
     * @Description 根据条件查询所有数据并分页
     * @time 2019年9月13日
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据条件查询", notes = "根据条件查询")
    @PostMapping("/findList")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
            @ApiImplicitParam(name = "userDto", value = "用户实体", required = true, dataType = "UserDto")
    })
    public Result findList(@RequestBody UserDto userDto) {
        return userService.findList(userDto);
    }

}