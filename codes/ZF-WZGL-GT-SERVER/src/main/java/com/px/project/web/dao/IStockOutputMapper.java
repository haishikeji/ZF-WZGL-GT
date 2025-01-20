package com.px.project.web.dao;

import com.alibaba.fastjson.JSONObject;
import com.px.project.web.dao.provider.StockOutputProvider;
import com.px.project.web.dto.StockOutputDto;
import com.px.project.web.entity.StockOutputModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface IStockOutputMapper {

    @InsertProvider(type = StockOutputProvider.class, method = "save")
    int save(StockOutputModel stockOutputModel);

    @DeleteProvider(type = StockOutputProvider.class, method = "deleteByIds")
    int deleteByIds(@Param("ids") String ids);

    @UpdateProvider(type = StockOutputProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param("ids") String ids);

    @UpdateProvider(type = StockOutputProvider.class, method = "update")
    int update(StockOutputModel stockOutputModel);

    @SelectProvider(type = StockOutputProvider.class, method = "findById")
    StockOutputModel findById(@Param("id") String id);

    @SelectProvider(type = StockOutputProvider.class, method = "findAll")
    List<StockOutputModel> findAll(StockOutputDto stockOutputDto);

    @SelectProvider(type = StockOutputProvider.class, method = "getLastTime")
    String getLastTime(String goodId);

    @SelectProvider(type = StockOutputProvider.class, method = "sumStockOutNum")
    List<JSONObject> sumStockOutNum(StockOutputDto stockOutputDto);
}
