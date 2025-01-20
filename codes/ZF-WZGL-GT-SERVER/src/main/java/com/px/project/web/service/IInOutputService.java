package com.px.project.web.service;

import com.alibaba.fastjson.JSONObject;
import com.px.project.common.base.IBaseService;
import com.px.project.common.base.Result;
import com.px.project.web.dto.InOutputDto;

import java.util.List;

public interface IInOutputService<T> extends IBaseService<T> {
    /**
     * @param inOutputDto
     * @return Result
     * @throws
     * @name findAll
     * @author 品讯科技
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-19 22:21:16
     */
    Result findAll(InOutputDto inOutputDto);

    /**
     * @param inOutputDto
     * @return Result
     * @throws
     * @name inOutputNumTrend
     * @author 品讯科技
     * @Description 直入直出数量趋势
     * @time 2021-04-10 20:05:53
     */
    List<JSONObject> inOutputNumTrend(InOutputDto inOutputDto);
}
