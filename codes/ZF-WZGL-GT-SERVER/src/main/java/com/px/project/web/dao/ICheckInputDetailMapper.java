package com.px.project.web.dao;

import com.alibaba.fastjson.JSONObject;
import com.px.project.web.dao.provider.CheckInputDetailProvider;
import com.px.project.web.dto.CheckInputDetailDto;
import com.px.project.web.entity.CheckInputDetailModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface ICheckInputDetailMapper {

    @InsertProvider(type = CheckInputDetailProvider.class, method = "save")
    int save(CheckInputDetailModel checkInputDetailModel);

    @DeleteProvider(type = CheckInputDetailProvider.class, method = "deleteByIds")
    int deleteByIds(@Param("ids") String ids);

    @UpdateProvider(type = CheckInputDetailProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param("ids") String ids);

    @UpdateProvider(type = CheckInputDetailProvider.class, method = "update")
    int update(CheckInputDetailModel checkInputDetailModel);

    @SelectProvider(type = CheckInputDetailProvider.class, method = "findById")
    JSONObject findById(@Param("id") String id);

    @SelectProvider(type = CheckInputDetailProvider.class, method = "findAll")
    List<JSONObject> findAll(CheckInputDetailDto checkInputDetailDto);

    @InsertProvider(type = CheckInputDetailProvider.class, method = "batchSave")
	int batchSave(List<CheckInputDetailModel> list);
    
    @SelectProvider(type = CheckInputDetailProvider.class, method = "findAllSumByGoodId")
	List<JSONObject> findAllSumByGoodId(CheckInputDetailDto checkInputDetailDto);
    
    @DeleteProvider(type = CheckInputDetailProvider.class, method = "deleteByCheckImputIds")
    int deleteByCheckImputIds(@Param("checkInputIds") String checkInputIds);
}
