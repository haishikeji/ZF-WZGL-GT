package com.px.project.web.dao;

import com.alibaba.fastjson.JSONObject;
import com.px.project.web.dao.provider.CheckInputProvider;
import com.px.project.web.dto.CheckInputDto;
import com.px.project.web.entity.CheckInputModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface ICheckInputMapper {

    @InsertProvider(type = CheckInputProvider.class, method = "save")
    int save(CheckInputModel checkInputModel);

    @DeleteProvider(type = CheckInputProvider.class, method = "deleteByIds")
    int deleteByIds(@Param("ids") String ids);

    @UpdateProvider(type = CheckInputProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param("ids") String ids);

    @UpdateProvider(type = CheckInputProvider.class, method = "update")
    int update(CheckInputModel checkInputModel);

    @Results({
            @org.apache.ibatis.annotations.Result(column = "id", property = "id"),
            @org.apache.ibatis.annotations.Result(column = "id", property = "approveInfo",
                    one = @One(select = "com.px.project.web.dao.IWorkflowMapper.findStatusByBusId"))
    })
    @SelectProvider(type = CheckInputProvider.class, method = "findById")
    JSONObject findById(@Param("id") String id);

    @Results({
            @org.apache.ibatis.annotations.Result(column = "id", property = "id"),
            @org.apache.ibatis.annotations.Result(column = "id", property = "approveInfo",
                    one = @One(select = "com.px.project.web.dao.IWorkflowMapper.findStatusByBusId"))
    })
    @SelectProvider(type = CheckInputProvider.class, method = "findAll")
    List<JSONObject> findAll(CheckInputDto checkInputDto);

    @SelectProvider(type = CheckInputProvider.class, method = "sumCheckInputNum")
    int sumCheckInputNum(CheckInputDto checkInputDto);

    @SelectProvider(type = CheckInputProvider.class, method = "checkInputNumTrend")
    List<JSONObject> checkInputNumTrend(CheckInputDto checkInputDto);
}
