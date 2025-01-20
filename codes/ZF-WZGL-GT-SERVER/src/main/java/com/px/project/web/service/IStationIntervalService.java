package com.px.project.web.service;

import com.px.project.common.base.IBaseService;
import com.px.project.common.base.Result;
import com.px.project.web.dto.StationIntervalDto;
public interface IStationIntervalService<T> extends IBaseService<T>{
     /**
     * @name findAll
     * @author 品讯科技
     * @param stationIntervalDto
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-09 08:51:10
     * @throws
     * @return Result
     */
    Result findAll(StationIntervalDto stationIntervalDto);
}
