package com.px.project.web.service;

import com.alibaba.fastjson.JSONObject;
import com.px.project.common.base.IBaseService;
import com.px.project.common.base.Result;
import com.px.project.web.dto.AllotDetailDto;

import java.util.List;

public interface IAllotDetailService<T> extends IBaseService<T>{
     /**
     * @name findAll
     * @author 品讯科技
     * @param allotDetailDto
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-19 21:57:34
     * @throws
     * @return Result
     */
    Result findAll(AllotDetailDto allotDetailDto);

    /**
     * @name allotDetailList
     * @author 品讯科技
     * @param allotId
     * @Description 根据id查询子表明细
     * @time 2021-03-23 23:29:57
     * @throws
     * @return Result
     */
    List<JSONObject> allotDetailList(String allotId);
}
