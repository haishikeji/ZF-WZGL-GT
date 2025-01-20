package com.px.project.web.dao;

import com.alibaba.fastjson.JSONObject;
import com.px.project.web.dao.provider.GoodProvider;
import com.px.project.web.dto.GoodDto;
import com.px.project.web.entity.GoodModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface IGoodMapper {

    @InsertProvider(type = GoodProvider.class, method = "save")
    int save(GoodModel goodModel);

    @DeleteProvider(type = GoodProvider.class, method = "deleteByIds")
    int deleteByIds(@Param("ids") String ids);

    @UpdateProvider(type = GoodProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param("ids") String ids);

    @UpdateProvider(type = GoodProvider.class, method = "update")
    int update(GoodModel goodModel);

    @SelectProvider(type = GoodProvider.class, method = "findById")
    JSONObject findById(@Param("id") String id);

    @SelectProvider(type = GoodProvider.class, method = "findAll")
    List<JSONObject> findAll(GoodDto goodDto);

    @SelectProvider(type = GoodProvider.class, method = "findByIds")
    List<JSONObject> findByIds(String ids);
    @SelectProvider(type = GoodProvider.class, method = "findAllDto")
	List<GoodDto> findAllDto(GoodDto goodDto);
}
