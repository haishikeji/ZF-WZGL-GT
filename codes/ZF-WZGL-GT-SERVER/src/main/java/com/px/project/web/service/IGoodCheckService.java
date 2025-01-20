package com.px.project.web.service;

import com.px.project.common.base.IBaseService;
import com.px.project.common.base.Result;
import com.px.project.web.dto.GoodCheckDto;
public interface IGoodCheckService<T> extends IBaseService<T>{
     /**
     * @name findAll
     * @author 品讯科技
     * @param goodCheckDto
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-19 23:03:56
     * @throws
     * @return Result
     */
    Result findAll(GoodCheckDto goodCheckDto);

    /**
     * @name goodCheckList
     * @author 品讯科技
     * @param goodCheckDto
     * @Description 物资检测管理查询
     * @time 2021-03-28 00:07:10
     * @throws
     * @return Result
     */
    Result goodCheckList(GoodCheckDto goodCheckDto);

    /**
     * @name findLogById
     * @author 品讯科技
     * @param id
     * @Description 查看日志明细
     * @time 2021-03-31 20:49:54
     * @throws
     * @return Result
     */
    Result findLogById(String id);
}
