package com.px.project.web.service;

import com.alibaba.fastjson.JSONObject;
import com.px.project.common.base.IBaseService;
import com.px.project.common.base.Result;
import com.px.project.web.dto.InOutputDetailDto;

import java.util.List;

public interface IInOutputDetailService<T> extends IBaseService<T>{
     /**
     * @name findAll
     * @author 品讯科技
     * @param inOutputDetailDto
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-19 22:20:57
     * @throws
     * @return Result
     */
    Result findAll(InOutputDetailDto inOutputDetailDto);


    /**
     * @name inOutputDetailList
     * @author 品讯科技
     * @param inOutputId
     * @Description 根据id获取子表明细
     * @time 2021-03-24 21:59:30
     * @throws
     * @return Result
     */
    List<JSONObject> inOutputDetailList(String inOutputId);
}
