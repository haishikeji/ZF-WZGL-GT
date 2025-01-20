package com.px.project.web.service;

import com.px.project.common.base.IBaseService;
import com.px.project.common.base.Result;
import com.px.project.web.dto.StationScheduleDto;
public interface IStationScheduleService<T> extends IBaseService<T>{
     /**
     * @name findAll
     * @author 品讯科技
     * @param stationScheduleDto
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-11 22:58:08
     * @throws
     * @return Result
     */
    Result findAll(StationScheduleDto stationScheduleDto);

    /**
     * @name findAll
     * @author 品讯科技
     * @param projectId
     * @Description 根据项目id查询站区间自定义列
     * @time 2021-03-12 14:39:23
     * @throws
     * @return Result
     */
    Result findStationSchedule(String projectId);

    /**
     * @name logicDeleteByStationScheduleId
     * @author 品讯科技
     * @param id
     * @Description 根据站区间id查询站区间自定义列
     * @time 2021-04-02 10:13:59
     * @throws
     * @return Result
     */
    Result logicDeleteByStationScheduleId(String id);

}
