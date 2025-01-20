package com.px.project.web.controller;

import com.px.project.common.base.Result;

import com.px.project.common.base.ReturnMsgEnum;
import com.px.project.common.exception.RequestException;
import com.px.project.web.dto.PickOrderDetailDto;
import com.px.project.web.entity.PickOrderDetailModel;
import com.px.project.web.service.IPickOrderDetailService;
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
 * @ClassName: PickOrderDetailController
 * @Description: 对PickOrderDetail的业务处理controller
 * @author 品讯科技
 * @time   2021-03-16 13:47:56
 * @version V 1.0
 */
@RestController
@Api(tags = "PickOrderDetailController", description = "对PickOrderDetail的业务处理controller")
@RequestMapping("/pickOrderDetail")
public class PickOrderDetailController {
    @Autowired
    private IPickOrderDetailService pickOrderDetailService;//service

    private static Logger logger = LoggerFactory.getLogger(PickOrderDetailController.class);
    /**
     * @name save
     * @author 品讯科技
     * @param pickOrderDetailModel
     * @Description 保存
     * @time 2021-03-16 13:47:56
     * @throws
     * @return Result
     */
    @ApiOperation(value = "保存", notes = "保存")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "pickOrderDetailModel", value = "实体", required = true, dataType = "PickOrderDetailModel")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestHeader(value="Authorization") String token,@Validated @RequestBody PickOrderDetailModel pickOrderDetailModel,BindingResult result) {
        pickOrderDetailModel.setToken (token);
        Result returnResult = null;
        try {
            returnResult = pickOrderDetailService.save(pickOrderDetailModel);
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
     * @time 2021-03-16 13:47:56
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
        return pickOrderDetailService.deleteByIds(ids);
    }
    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--逻辑删除
     * @time 2021-03-16 13:47:56
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
        return pickOrderDetailService.logicDeleteByIds(ids);
    }

    /**
     * @name update
     * @author 品讯科技
     * @param pickOrderDetailModel
     * @Description 根据id 修改
     * @time 2021-03-16 13:47:56
     * @throws
     * @return Result
     */
    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "pickOrderDetailModel", value = "实体", required = true, dataType = "PickOrderDetailModel")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestHeader(value="Authorization") String token,@Validated @RequestBody PickOrderDetailModel pickOrderDetailModel,BindingResult result) {
        pickOrderDetailModel.setToken (token);
        Result returnResult = null;
        try {
            returnResult = pickOrderDetailService.update(pickOrderDetailModel);
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
    * @time 2021-03-16 13:47:56
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
        return pickOrderDetailService.findById(id);
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param pickOrderDetailDto
     *      {
     *          "pageNum":"",页码
     *          "pageSize":"",每页长度
     *          "condition":""条件
     *      }
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-16 13:47:56
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据条件查询", notes = "根据条件查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "pickOrderDetailDto", value = "实体", required = true, dataType = "PickOrderDetailDto")
    })
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Result findAll(@RequestBody PickOrderDetailDto pickOrderDetailDto) {
        return pickOrderDetailService.findAll(pickOrderDetailDto);
    }

    /**
     * @author 品讯科技
     * @Description 根据主键id（多个）获取明细详情列表
     * @time 2021-03-21 20:47:00
     * @param pickOrderDetailDto
     * @return
     */
    @ApiOperation(value = "获取发料单明细列表", notes = "获取发料单明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
            @ApiImplicitParam(name = "pickOrderDetailDto", value = "实体", required = true, dataType = "PickOrderDetailDto")
    })
    @RequestMapping(value = "/pickOrderDetailList", method = RequestMethod.POST)
    public Result pickOrderDetailList(@RequestBody PickOrderDetailDto pickOrderDetailDto) {
        return pickOrderDetailService.pickOrderDetailList(pickOrderDetailDto);
    }
}
