package com.px.project.web.service;

import com.alibaba.fastjson.JSONObject;
import com.px.project.common.base.IBaseService;
import com.px.project.common.base.Result;
import com.px.project.web.dto.OrderGoodDetailDto;
import com.px.project.web.entity.OrderGoodDetailModel;

import java.util.List;

public interface IOrderGoodDetailService<T> extends IBaseService<T>{
     /**
     * @name findAll
     * @author 品讯科技
     * @param orderGoodDetailDto
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-15 21:47:19
     * @throws
     * @return Result
     */
    Result findAll(OrderGoodDetailDto orderGoodDetailDto);

    /**
     * @name goodDetailList
     * @author 品讯科技
     * @param orderGoodId
     * @Description 根据订货单主键获取明细
     * @time 2021-03-16 10:05:05
     * @throws
     * @return Result
     */
    List<JSONObject> goodDetailList(String orderGoodId);

	void updateOverNums(OrderGoodDetailModel detail) ;

	List<JSONObject> goodDetailBalanceList(String id);
}
