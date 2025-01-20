package com.px.project.web.dao;

import com.alibaba.fastjson.JSONObject;
import com.px.project.web.dao.provider.PickOrderProvider;
import com.px.project.web.dto.PickOrderDto;
import com.px.project.web.entity.PickOrderModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface IPickOrderMapper {

    @InsertProvider(type = PickOrderProvider.class, method = "save")
    int save(PickOrderModel pickOrderModel);

    @DeleteProvider(type = PickOrderProvider.class, method = "deleteByIds")
    int deleteByIds(@Param("ids") String ids);

    @UpdateProvider(type = PickOrderProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param("ids") String ids);

    @UpdateProvider(type = PickOrderProvider.class, method = "update")
    int update(PickOrderModel pickOrderModel);

    @Results({
            @org.apache.ibatis.annotations.Result(column = "id", property = "id"),
            @org.apache.ibatis.annotations.Result(column = "id", property = "approveInfo",
                    one = @One(select = "com.px.project.web.dao.IWorkflowMapper.findStatusByBusId"))
    })
    @SelectProvider(type = PickOrderProvider.class, method = "findById")
    JSONObject findById(@Param("id") String id);

    @Results({
            @org.apache.ibatis.annotations.Result(column = "id", property = "id"),
            @org.apache.ibatis.annotations.Result(column = "id", property = "approveInfo",
                    one = @One(select = "com.px.project.web.dao.IWorkflowMapper.findStatusByBusId"))
    })
    @SelectProvider(type = PickOrderProvider.class, method = "findAll")
    List<JSONObject> findAll(PickOrderDto pickOrderDto);

    @SelectProvider(type = PickOrderProvider.class, method = "sumPickOrderNum")
    int sumPickOrderNum(PickOrderDto pickOrderDto);

    @SelectProvider(type = PickOrderProvider.class, method = "pickOrderNumTrend")
    List<JSONObject> pickOrderNumTrend(PickOrderDto pickOrderDto);
}
