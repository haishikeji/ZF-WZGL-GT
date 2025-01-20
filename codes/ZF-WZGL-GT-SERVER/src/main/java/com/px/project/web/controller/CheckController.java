package com.px.project.web.controller;

import com.px.project.common.base.Result;

import com.px.project.common.base.ReturnMsgEnum;
import com.px.project.common.exception.RequestException;
import com.px.project.web.dto.CheckDto;
import com.px.project.web.entity.CheckModel;
import com.px.project.web.service.ICheckService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @Package com.px.project.web.controller
 * @ClassName: CheckController
 * @Description: 对Check的业务处理controller
 * @author 品讯科技
 * @time   2021-03-19 22:53:23
 * @version V 1.0
 */
@RestController
@Api(tags = "CheckController", description = "对Check的业务处理controller")
@RequestMapping("/check")
public class CheckController {
    @Autowired
    private ICheckService checkService;//service

    private static Logger logger = LoggerFactory.getLogger(CheckController.class);
    /**
     * @name save
     * @author 品讯科技
     * @param checkModel
     * @Description 保存
     * @time 2021-03-19 22:53:23
     * @throws
     * @return Result
     */
    @ApiOperation(value = "保存", notes = "保存")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "checkModel", value = "实体", required = true, dataType = "CheckModel")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestHeader(value="Authorization") String token,@Validated @RequestBody CheckModel checkModel,BindingResult result) {
        checkModel.setToken (token);
        Result returnResult = null;
        try {
            returnResult = checkService.save(checkModel);
        } catch (RequestException e) {
            logger.warn(e.getMessage(), e);
            return Result.buildResult(e.getCode(),e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.buildResult(ReturnMsgEnum.Internal_Server_Error);
        }
        return returnResult;
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--物理删除
     * @time 2021-03-19 22:53:23
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
        return checkService.deleteByIds(ids);
    }
    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--逻辑删除
     * @time 2021-03-19 22:53:23
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
        return checkService.logicDeleteByIds(ids);
    }

    /**
     * @name update
     * @author 品讯科技
     * @param checkModel
     * @Description 根据id 修改
     * @time 2021-03-19 22:53:23
     * @throws
     * @return Result
     */
    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "checkModel", value = "实体", required = true, dataType = "CheckModel")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestHeader(value="Authorization") String token,@Validated @RequestBody CheckModel checkModel,BindingResult result) {
        checkModel.setToken (token);
        Result returnResult = null;
        try {
            returnResult = checkService.update(checkModel);
        } catch (RequestException e) {
            logger.warn(e.getMessage(), e);
            return Result.buildResult(e.getCode(),e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.buildResult(ReturnMsgEnum.Internal_Server_Error);
        }
        return returnResult;
    }

    /**
    * @name findById
    * @author 品讯科技
    * @param id
    * @Description 根据主键id查看信息
    * @time 2021-03-19 22:53:23
    * @throws
    * @return Result
    */
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return checkService.findById(id);
    }
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
    	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/findOnlyById/{id}", method = RequestMethod.GET)
    public Result findOnlyById(@PathVariable String id) {
    	return checkService.findOnlyById(id);
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param checkDto
     *      {
     *          "pageNum":"",页码
     *          "pageSize":"",每页长度
     *          "condition":""条件
     *      }
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-19 22:53:23
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据条件查询", notes = "根据条件查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "checkDto", value = "实体", required = true, dataType = "CheckDto")
    })
    @RequestMapping(value = "/checkList", method = RequestMethod.POST)
    public Result findAll(@RequestBody CheckDto checkDto) {
        return checkService.findAll(checkDto);
    }
    @ApiOperation(value = "根据条件查询", notes = "根据条件查询")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
    	@ApiImplicitParam(name = "checkDto", value = "实体", required = true, dataType = "CheckDto")
    })
    @RequestMapping(value = "/findPrintDetail", method = RequestMethod.POST)
    public Result findPrintDetail(@RequestBody CheckDto checkDto) {
    	return checkService.findPrintDetail(checkDto);
    }

    /**
     * @name checkStatusList
     * @author 品讯科技
     * @param id
     * @Description 盘点单（物资状态）查看
     * @time 2021-03-26 21:23:45
     * @throws
     * @return Result
     */
    @ApiOperation(value = "盘点单（物资状态）查看", notes = "盘点单（物资状态）查看")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
            @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/checkStatusList/{id}", method = RequestMethod.GET)
    public Result checkStatusList(@PathVariable String id) {
        return checkService.checkStatusList(id);
    }
}
