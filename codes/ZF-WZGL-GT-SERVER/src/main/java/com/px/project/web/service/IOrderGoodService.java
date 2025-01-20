package com.px.project.web.service;

import com.px.project.common.base.IBaseService;
import com.px.project.common.base.Result;
import com.px.project.web.dto.OrderGoodDto;
import com.px.project.web.entity.OrderGoodModel;

public interface IOrderGoodService<T> extends IBaseService<T> {
    /**
     * @param orderGoodDto
     * @return Result
     * @throws
     * @name findAll
     * @author 品讯科技
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-15 17:07:18
     */
    Result findAll(OrderGoodDto orderGoodDto);


    /**
     * @param orderGoodDto
     * @return Result
     * @throws
     * @name sumOrderGoodNum
     * @author 品讯科技
     * @Description 订货单数量
     * @time 2021-04-09 21:51:25
     */
    int sumOrderGoodNum(OrderGoodDto orderGoodDto);


	Result saveByPlanId(OrderGoodModel orderGoodModel);


	Result findById2(String id);
}
