package com.px.project.web.controller;

import com.px.project.common.base.Result;
import com.px.project.common.base.ReturnMsgEnum;
import com.px.project.common.exception.RequestException;
import com.px.project.common.utils.HttpClientUtil;
import com.px.project.common.utils.TokenUtil;
import com.px.project.web.dto.ResetPasswordDto;
import com.px.project.web.dto.UserDto;
import com.px.project.web.entity.UserModel;
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
@Api(tags = "UserController", description = "对User的业务处理controller")
@RequestMapping("/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;//service
    @Autowired
    HttpClientUtil httpClientUtil;
    /**
     * @name save
     * @author 品讯科技
     * @Description 保存
     * @time 2019-09-17 10:24:19
     * @throws
     * @return Result
     */
    @ApiOperation(value = "保存", notes = "保存")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "userModel", value = "用户实体", required = true, dataType = "UserModel")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestHeader(value="Authorization") String token, @Validated @RequestBody UserModel userModel, BindingResult result) {
        userModel.setToken (token);
        return userService.save(userModel);
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--物理删除
     * @time 2019-09-17 10:24:19
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据ids，删除数据-物理删除", notes = "根据ids，删除数据")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "ids", value = "id的数据集", required = true, dataType = "String")
    })
    @RequestMapping(value = "/deleteByIds", method = RequestMethod.POST)
    public Result deleteByIds(@RequestBody String ids) {
        return userService.deleteByIds(ids);
    }

    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--逻辑删除
     * @time 2019年9月13日
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据ids，删除数据--逻辑删除", notes = "根据ids，删除数据")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "ids", value = "id的数据集", required = true, dataType = "String")
    })
    @RequestMapping(value = "/logicDeleteByIds", method = RequestMethod.POST)
    public Result logicDeleteByIds(@RequestBody String ids) {
        return userService.logicDeleteByIds(ids);
    }

    /**
     * @name update
     * @author 品讯科技
     * @param userModel
     * @Description 根据id 修改
     * @time 2019年9月13日
     * @throws
     * @return Result
     */
    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "userModel", value = "实体", required = true, dataType = "UserModel")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@Validated @RequestBody UserModel userModel,@RequestHeader(value="Authorization") String token) {
        userModel.setToken (token);
        return userService.update(userModel);
    }

    /**
     * @name findById
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
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) {
        return userService.findById(id);
    }

    /**
     * @name findAll
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
    @PostMapping("/findAll")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
            @ApiImplicitParam(name = "userDto", value = "用户实体", required = true, dataType = "UserDto")
    })
    public Result findAll(@RequestBody UserDto userDto) {
        return userService.findAll(userDto);
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

    /**
     * @name saveUser
     * @author 品讯科技
     * @Description 保存
     * @time 2021-03-02 10:24:19
     * @throws
     * @return Result
     */
    @ApiOperation(value = "保存", notes = "保存")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
            @ApiImplicitParam(name = "userDto", value = "用户实体", required = true, dataType = "UserDto")
    })
    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public Result saveUser(@RequestHeader(value="Authorization") String token, @Validated @RequestBody UserDto userDto, BindingResult result) {
        userDto.setToken (token);
        Result returnResult=null;
        try {
            returnResult = userService.saveUser(userDto);
        }catch (RequestException e){
            logger.warn(e.getMessage(), e);
            if(ReturnMsgEnum.Save_Failed.getMsg().equals(e.getMsg())) {
                return Result.buildResult(ReturnMsgEnum.Save_Failed);
            }
            if(ReturnMsgEnum.Role_IsNull.getMsg().equals(e.getMsg())) {
                return Result.buildResult(ReturnMsgEnum.Role_IsNull);
            }
            if(ReturnMsgEnum.User_Mark_IsNull.getMsg().equals(e.getMsg())) {
                return Result.buildResult(ReturnMsgEnum.User_Mark_IsNull);
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.buildResult(ReturnMsgEnum.Internal_Server_Error);
        }
        return returnResult;
    }

    /**
     * @name updateUser
     * @author 品讯科技
     * @Description 修改用户
     * @time 2019-09-17 10:24:19
     * @throws
     * @return Result
     */
    @ApiOperation(value = "修改用户", notes = "修改用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
            @ApiImplicitParam(name = "userDto", value = "用户实体", required = true, dataType = "UserDto")
    })
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Result updateUser(@RequestHeader(value="Authorization") String token,@Validated @RequestBody UserDto userDto,BindingResult result) {
        userDto.setToken (token);
        Result returnResult=null;
        try {
            returnResult = userService.updateUser(userDto);
        }catch (RequestException e){
            logger.warn(e.getMessage(), e);
            if(ReturnMsgEnum.Role_IsNull.getMsg().equals(e.getMsg())) {
                return Result.buildResult(ReturnMsgEnum.Role_IsNull);
            }
            if(ReturnMsgEnum.Update_Failure.getMsg().equals(e.getMsg())) {
                return Result.buildResult(ReturnMsgEnum.Update_Failure);
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.buildResult(ReturnMsgEnum.Internal_Server_Error);
        }
        return returnResult;
    }

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

}