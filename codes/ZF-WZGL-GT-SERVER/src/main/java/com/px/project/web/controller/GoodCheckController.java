package com.px.project.web.controller;

import com.px.project.common.base.Result;

import com.px.project.common.base.ReturnMsgEnum;
import com.px.project.common.exception.RequestException;
import com.px.project.web.dto.GoodCheckDto;
import com.px.project.web.entity.GoodCheckModel;
import com.px.project.web.service.IGoodCheckService;
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
 * @ClassName: GoodCheckController
 * @Description: 对GoodCheck的业务处理controller
 * @author 品讯科技
 * @time   2021-03-19 23:03:56
 * @version V 1.0
 */
@RestController
@Api(tags = "GoodCheckController", description = "对GoodCheck的业务处理controller")
@RequestMapping("/goodCheck")
public class GoodCheckController {
    @Autowired
    private IGoodCheckService goodCheckService;//service

    private static Logger logger = LoggerFactory.getLogger(GoodCheckController.class);

    /**
     * @name save
     * @author 品讯科技
     * @param goodCheckModel
     * @Description 保存
     * @time 2021-03-19 23:03:56
     * @throws
     * @return Result
     */
    @ApiOperation(value = "保存", notes = "保存")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "goodCheckModel", value = "实体", required = true, dataType = "GoodCheckModel")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestHeader(value="Authorization") String token,@Validated @RequestBody GoodCheckModel goodCheckModel,BindingResult result) {
        goodCheckModel.setToken (token);

        Result returnResult = null;
        try {
            returnResult = goodCheckService.save(goodCheckModel);
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
     * @time 2021-03-19 23:03:56
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
        return goodCheckService.deleteByIds(ids);
    }

    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--逻辑删除
     * @time 2021-03-19 23:03:56
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
        return goodCheckService.logicDeleteByIds(ids);
    }

    /**
     * @name update
     * @author 品讯科技
     * @param goodCheckModel
     * @Description 根据id 修改
     * @time 2021-03-19 23:03:56
     * @throws
     * @return Result
     */
    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "goodCheckModel", value = "实体", required = true, dataType = "GoodCheckModel")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestHeader(value="Authorization") String token,@Validated @RequestBody GoodCheckModel goodCheckModel,BindingResult result) {
        goodCheckModel.setToken (token);
        Result returnResult = null;
        try {
            returnResult = goodCheckService.update(goodCheckModel);
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
    * @param goodId
    * @Description 根据物资主键id查看信息
    * @time 2021-03-19 23:03:56
    * @throws
    * @return Result
    */
    @ApiOperation(value = "根据物资id查询", notes = "根据物资id查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "goodId", value = "物资主键id", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/findById/{goodId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String goodId) {
        return goodCheckService.findById(goodId);
    }

    /**
     * @name findLogById
     * @author 品讯科技
     * @param id
     * @Description 根据主键id查看日志信息
     * @time 2021-03-31 20:48:13
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据主键id查看日志信息", notes = "根据主键id查看日志信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
            @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/findLogById/{id}", method = RequestMethod.GET)
    public Result findLogById(@PathVariable String id) {
        return goodCheckService.findLogById(id);
    }


    /**
     * @name findAll
     * @author 品讯科技
     * @Description 物资检测管理查询，【有检测单位，入库和出库以及检测都按找检测单位来】
     * @time 2021-03-28 00:03:27
     * @throws
     * @return Result
     */
    @ApiOperation(value = "物资检测管理查询", notes = "物资检测管理查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "goodCheckDto", value = "实体", required = true, dataType = "GoodCheckDto")
    })
    @RequestMapping(value = "/goodCheckList", method = RequestMethod.POST)
    public Result goodCheckList(@RequestBody GoodCheckDto goodCheckDto) {
        return goodCheckService.goodCheckList(goodCheckDto);
    }
}
