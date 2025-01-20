package com.px.project.web.dao;

import com.alibaba.fastjson.JSONObject;
import com.px.project.web.dao.provider.LimitProvider;
import com.px.project.web.dto.LimitDetailDto;
import com.px.project.web.dto.LimitDto;
import com.px.project.web.entity.LimitModel;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface ILimitMapper {

    @InsertProvider(type = LimitProvider.class, method = "save")
    int save(LimitModel limitModel);

    @DeleteProvider(type = LimitProvider.class, method = "deleteByIds")
    int deleteByIds(@Param ("ids") String ids);

    @UpdateProvider(type = LimitProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param ("ids") String ids);

    @UpdateProvider(type = LimitProvider.class, method = "update")
    int update(LimitModel limitModel);

    @SelectProvider(type = LimitProvider.class, method = "findById")
    LimitModel findById(@Param ("id") String id);
    @SelectProvider(type = LimitProvider.class, method = "findJsonObjById")
    JSONObject findJsonObjById(@Param ("id") String id);

    @SelectProvider(type = LimitProvider.class, method = "findAll")
    List<LimitModel> findAll(LimitDto limitDto);
    
    @SelectProvider(type = LimitProvider.class, method = "findJsonObjAll")
    List<JSONObject> findJsonObjAll(LimitDto limitDto);
    
    @SelectProvider(type = LimitProvider.class, method = "findAllByMajorAndGoodId")
	List<LimitDetailDto> findAllByMajorAndGoodId(@Param ("goodIds") String goodStr,@Param ("majorIds")  String majorIdStr);
    
    @SelectProvider(type = LimitProvider.class, method = "findAllByMajorAndStationIntervaIds")
    List<LimitModel>  findAllByMajorAndStationIntervaIds(@Param ("stationIntervaIds") String stationIntervaIds, @Param ("majorId")  String majorId);

    @InsertProvider(type = LimitProvider.class, method = "batchSave")
	int batchSave(List<LimitModel> list);
}
