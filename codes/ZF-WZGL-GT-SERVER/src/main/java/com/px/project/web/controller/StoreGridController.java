package com.px.project.web.controller;

import com.px.project.common.base.Result;

import com.px.project.common.base.ReturnMsgEnum;
import com.px.project.common.exception.RequestException;
import com.px.project.web.dto.StoreGridDto;
import com.px.project.web.entity.StoreGridModel;
import com.px.project.web.service.IStoreGridService;
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
 * @ClassName: StoreGridController
 * @Description: 对StoreGrid的业务处理controller
 * @author 品讯科技
 * @time   2021-03-04 22:43:20
 * @version V 1.0
 */
@RestController
@Api(tags = "StoreGridController", description = "对StoreGrid的业务处理controller")
@RequestMapping("/storeGrid")
public class StoreGridController {
    @Autowired
    private IStoreGridService storeGridService;//service

    private static Logger logger = LoggerFactory.getLogger(StoreGridController.class);
    /**
     * @name save
     * @author 品讯科技
     * @param storeGridModel
     * @Description 保存
     * @time 2021-03-06 21:04:03
     * @throws
     * @return Result
     */
    @ApiOperation(value = "保存", notes = "保存")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "storeGridModel", value = "实体", required = true, dataType = "StoreGridModel")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestHeader(value="Authorization") String token,@Validated @RequestBody StoreGridModel storeGridModel,BindingResult result) {
        storeGridModel.setToken (token);
        Result returnResult = null;
        try {
            returnResult = storeGridService.save(storeGridModel);
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
     * @name initStoreGrid
     * @author 品讯科技
     * @param storeGridDto
     * @Description 初始化仓库表格，根据行数量和列数量自动创建明细
     * @time 2021-03-06 21:13:18
     * @throws
     * @return Result
     */
    @ApiOperation(value = "初始化仓库表格", notes = "初始化仓库表格，根据行数量和列数量自动创建明细")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
            @ApiImplicitParam(name = "storeGridDto", value = "实体", required = true, dataType = "StoreGridDto")
    })
    @RequestMapping(value = "/initStoreGrid", method = RequestMethod.POST)
    public Result initStoreGrid(@RequestHeader(value = "Authorization") String token, @Validated @RequestBody StoreGridDto storeGridDto, BindingResult result) {
        storeGridDto.setToken(token);
        try {
            return storeGridService.initStoreGrid(storeGridDto);
        } catch (RequestException e) {
            if (ReturnMsgEnum.Repeat_Data.getMsg().equals(e.getMsg())) {
                return Result.buildResult(ReturnMsgEnum.Repeat_Data);
            }
            return Result.buildResult(ReturnMsgEnum.Internal_Server_Error);
        } catch (Exception e) {
            return Result.buildResult(ReturnMsgEnum.Save_Failed);
        }
    }

    /**
     * @name moreStoreGrid
     * @author 品讯科技
     * @param storeGridDto
     * @Description 增加网格行和列，根据行数量和列数量自动创建明细
     * @time 2021-03-06 23:05:08
     * @throws
     * @return Result
     */
    @ApiOperation(value = "增加网格行和列", notes = "增加网格行和列，根据行数量和列数量自动创建明细")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
            @ApiImplicitParam(name = "storeGridDto", value = "实体", required = true, dataType = "StoreGridDto")
    })
    @RequestMapping(value = "/moreStoreGrid", method = RequestMethod.POST)
    public Result moreStoreGrid(@RequestHeader(value = "Authorization") String token, @Validated @RequestBody StoreGridDto storeGridDto, BindingResult result) {
        storeGridDto.setToken(token);
        try {
            return storeGridService.moreStoreGrid(storeGridDto);
        } catch (RequestException e) {
            logger.warn(e.getMessage(), e);
            return Result.buildResult(e.getCode(),e.getMessage());
        }  catch (Exception e) {
            return Result.buildResult(ReturnMsgEnum.Save_Failed);
        }
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--物理删除
     * @time 2021-03-04 22:43:20
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
        return storeGridService.deleteByIds(ids);
    }
    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--逻辑删除
     * @time 2021-03-04 22:43:20
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
        return storeGridService.logicDeleteByIds(ids);
    }

    /**
     * @name update
     * @author 品讯科技
     * @param storeGridModel
     * @Description 根据id 修改
     * @time 2021-03-04 22:43:20
     * @throws
     * @return Result
     */
    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "storeGridModel", value = "实体", required = true, dataType = "StoreGridModel")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestHeader(value="Authorization") String token,@Validated @RequestBody StoreGridModel storeGridModel,BindingResult result) {
        storeGridModel.setToken (token);

        Result returnResult = null;
        try {
            returnResult = storeGridService.update(storeGridModel);
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
    * @time 2021-03-04 22:43:20
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
        return storeGridService.findById(id);
    }

    /**
     * @name findByStoreId
     * @author 品讯科技
     * @param storeGridDto
     *      {
     *          "pageNum":"",页码
     *          "pageSize":"",每页长度
     *          "condition":""条件
     *      }
     * @Description 根据条件查询所有数据
     * @time 2021-03-06 21:44:12
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据仓库id查询全部表格", notes = "根据仓库id查询全部表格")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "storeGridDto", value = "实体", required = true, dataType = "StoreGridDto")
    })
    @RequestMapping(value = "/findByStoreId", method = RequestMethod.POST)
    public Result findByStoreId(@RequestBody StoreGridDto storeGridDto) {
        return storeGridService.findAll(storeGridDto);
    }

    /**
     * @name gridCount
     * @author 品讯科技
     * @param id
     * @Description 根据仓库id查询 库位网格有多少行列
     * @time 2021-03-28 22:53:13
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据仓库id查询，库位网格有多少行列", notes = "根据仓库id查询，库位网格有多少行列")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
            @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/gridCount/{id}", method = RequestMethod.GET)
    public Result gridCount(@PathVariable String id) {
        return storeGridService.gridCount(id);
    }
}
