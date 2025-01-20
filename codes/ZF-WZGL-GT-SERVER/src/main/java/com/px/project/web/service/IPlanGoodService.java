package com.px.project.web.service;

import java.util.List;

import com.px.project.common.base.IBaseService;
import com.px.project.common.base.Result;
import com.px.project.web.dto.OrderGoodDetailDto;
import com.px.project.web.dto.PlanGoodDto;
public interface IPlanGoodService<T> extends IBaseService<T>{
     /**
     * @name findAll
     * @author 品讯科技
     * @param planGoodDto
     * @Description 根据条件查询所有数据并分页
     * @time 2023-09-13 19:47:25
     * @throws
     * @return Result
     */
    Result findAll(PlanGoodDto planGoodDto);

	Result findAllNoOverPlan(PlanGoodDto planGoodDto);

	Result findBalanceById(String id);

	boolean appPlanGoods(List<OrderGoodDetailDto> listOrder);
}
