package com.px.project.web.service;

import com.alibaba.fastjson.JSONObject;
import com.px.project.common.base.IBaseService;
import com.px.project.common.base.Result;
import com.px.project.web.dto.StockInputDto;

import java.util.List;

public interface IStockInputService<T> extends IBaseService<T>{
     /**
     * @name findAll
     * @author 品讯科技
     * @param stockInputDto
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-16 13:15:52
     * @throws
     * @return Result
     */
    Result findAll(StockInputDto stockInputDto);

    /**
     * @name getLastTime
     * @author 品讯科技
     * @param goodId
     * @Description 获取最后入库时间
     * @time 2021-04-05 20:02:52
     * @throws
     * @return Result
     */
    String getLastTime(String goodId);

    /**
     * @name sumStockInNum
     * @author 品讯科技
     * @param stockInputDto
     * @Description 物资入库数量
     * @time 2021-04-10 20:55:56
     * @throws
     * @return Result
     */
    List<JSONObject> sumStockInNum(StockInputDto stockInputDto);
}
