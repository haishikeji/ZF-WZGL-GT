package com.px.project.web.dao;

import com.px.project.web.dao.provider.FlowInsProvider;
import com.px.project.web.dto.FlowInsDto;
import com.px.project.web.entity.FlowInsModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface IFlowInsMapper {

    @InsertProvider(type = FlowInsProvider.class, method = "save")
    int save(FlowInsModel flowInsModel);

    @DeleteProvider(type = FlowInsProvider.class, method = "deleteByIds")
    int deleteByIds(@Param ("ids") String ids);

    @UpdateProvider(type = FlowInsProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param ("ids") String ids);

    @UpdateProvider(type = FlowInsProvider.class, method = "update")
    int update(FlowInsModel flowInsModel);

    @SelectProvider(type = FlowInsProvider.class, method = "findById")
    FlowInsModel findById(@Param ("id") String id);

    @SelectProvider(type = FlowInsProvider.class, method = "findApproveNodeByBusId")
    FlowInsModel findApproveNodeByBusId(@Param ("busId") String busId);

    @SelectProvider(type = FlowInsProvider.class, method = "findAll")
    List<FlowInsModel> findAll(FlowInsDto flowInsDto);
    @SelectProvider(type = FlowInsProvider.class, method = "approveList")
    List<FlowInsModel> approveList(String busId);
}
