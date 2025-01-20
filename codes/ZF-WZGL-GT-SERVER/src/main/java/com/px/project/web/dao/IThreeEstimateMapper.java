package com.px.project.web.dao;

import com.alibaba.fastjson.JSONObject;
import com.px.project.web.dao.provider.ThreeEstimateProvider;
import com.px.project.web.dto.ThreeEstimateDto;
import com.px.project.web.entity.ThreeEstimateModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface IThreeEstimateMapper {

    @InsertProvider(type = ThreeEstimateProvider.class, method = "save")
    int save(ThreeEstimateModel threeEstimateModel);

    @DeleteProvider(type = ThreeEstimateProvider.class, method = "deleteByIds")
    int deleteByIds(@Param("ids") String ids);

    @UpdateProvider(type = ThreeEstimateProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param("ids") String ids);

    @UpdateProvider(type = ThreeEstimateProvider.class, method = "update")
    int update(ThreeEstimateModel threeEstimateModel);

    //概算量详情
    @SelectProvider(type = ThreeEstimateProvider.class, method = "findById")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "{projectId=projectId,threeEstimateId=id}", property = "column",
                    one = @One(select = "com.px.project.web.dao.IBudgetUnitMapper.findBudgetUnit"))
    })
    JSONObject findById(@Param("id") String id);

    @SelectProvider(type = ThreeEstimateProvider.class, method = "findAll")
    List<ThreeEstimateModel> findAll(ThreeEstimateDto threeEstimateDto);

    //概算量列表
    @SelectProvider(type = ThreeEstimateProvider.class, method = "BudgetEstimateList")
    @Options(statementType = StatementType.CALLABLE)
    List<JSONObject> BudgetEstimateList(HashMap<String, Object> paramMap);

    //图纸量、概算量列表
    @SelectProvider(type = ThreeEstimateProvider.class, method = "StationScheduleList")
    @Options(statementType = StatementType.CALLABLE)
    List<JSONObject> StationScheduleList(HashMap<String, Object> paramMap);

    //图纸量、定测量详情
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "{projectId=projectId,threeEstimateId=id}", property = "column",
                    one = @One(select = "com.px.project.web.dao.IStationScheduleMapper.findStationSchedule"))
    })
    @SelectProvider(type = ThreeEstimateProvider.class, method = "findStationSchedule")
    JSONObject findStationSchedule(String id,String threeEstimateType);

    @SelectProvider(type = ThreeEstimateProvider.class, method = "sumBudgetEstimateNum")
    int sumBudgetEstimateNum(String projectId);

    @SelectProvider(type = ThreeEstimateProvider.class, method = "sumStationScheduleNum")
    int sumStationScheduleNum(String projectId, String threeEstimateType);
    
    
    
    @SelectProvider(type = ThreeEstimateProvider.class, method = "findThreeInfo")
	List<ThreeEstimateDto> findThreeInfo(ThreeEstimateDto threeEstimateDto);
    @SelectProvider(type = ThreeEstimateProvider.class, method = "countData")
	Integer countData(ThreeEstimateDto threeEstimateDto);
    
//    @SelectProvider(type = ThreeEstimateProvider.class, method = "findAllMajorAndGoodIds")
//	List<ThreeEstimateModel> findAllMajorAndGoodIds(@Param("majorId")String majorId, @Param("goodIds") String goodIds);
}
