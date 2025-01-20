package com.px.project.web.service;

import com.px.project.common.base.IBaseService;
import com.px.project.common.base.Result;
import com.px.project.web.dto.StoreGridDto;

public interface IStoreGridService<T> extends IBaseService<T> {
    /**
     * @param storeGridDto
     * @return Result
     * @throws
     * @name findAll
     * @author 品讯科技
     * @Description 根据仓库id查询全部表格
     * @time 2021-03-06 21:45:00
     */
    Result findAll(StoreGridDto storeGridDto);

    /**
     * @param storeGridDto
     * @return Result
     * @throws
     * @name initStoreGrid
     * @author 品讯科技
     * @Description 初始化仓库表格，根据行数量和列数量自动创建明细
     * @time 2021-03-06 21:08:31
     */
    Result initStoreGrid(StoreGridDto storeGridDto);

    /**
     * @param storeGridDto
     * @return Result
     * @throws
     * @name moreStoreGrid
     * @author 品讯科技
     * @Description 增加网格行和列，根据行数量和列数量自动创建明细
     * @time 2021-03-06 23:06:07
     */
    Result moreStoreGrid(StoreGridDto storeGridDto);

    /**
     * @param storeId
     * @return Result
     * @throws
     * @name gridCount
     * @author 品讯科技
     * @Description 根据仓库id查询 库位网格有多少行列
     * @time 2021-03-28 22:54:12
     */
    Result gridCount(String storeId);

    /**
     * @param storeId
     * @return Result
     * @throws
     * @name logicDeleteByColRow
     * @author 品讯科技
     * @Description 根据单元格地址逻辑删除数据
     * @time 2021-04-06 15:51:39
     */
    Result logicDeleteByColRow(String storeId, String colNo, String rowNo);
}
