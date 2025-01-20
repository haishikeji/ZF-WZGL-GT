package com.px.project.web.dao;

import com.alibaba.fastjson.JSONObject;
import com.px.project.web.dao.provider.GoodTypeProvider;
import com.px.project.web.dto.GoodTypeDto;
import com.px.project.web.entity.GoodTypeModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface IGoodTypeMapper {

    @InsertProvider(type = GoodTypeProvider.class, method = "save")
    int save(GoodTypeModel goodTypeModel);

    @DeleteProvider(type = GoodTypeProvider.class, method = "deleteByIds")
    int deleteByIds(@Param("ids") String ids);

    @UpdateProvider(type = GoodTypeProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param("ids") String ids);

    @UpdateProvider(type = GoodTypeProvider.class, method = "update")
    int update(GoodTypeModel goodTypeModel);

    @SelectProvider(type = GoodTypeProvider.class, method = "findById")
    GoodTypeModel findById(@Param("id") String id);

    @SelectProvider(type = GoodTypeProvider.class, method = "getMaxLevelCode")
    int getMaxLevelCode(@Param("projectId") String projectId,@Param("typeLevel") int typeLevel);

    @SelectProvider(type = GoodTypeProvider.class, method = "findAll")
    List<JSONObject> findAll(GoodTypeDto goodTypeDto);

    /**
     * @name findByParentId
     * @author 品讯科技
     * @Description 递归根据parentId查询物资类型子项
     * @time 2021-01-29 11:32:45
     */
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "{parentId=id,projectId=projectId}", property = "childs",
                    one = @One(select = "com.px.project.web.dao.IGoodTypeMapper.findByParentId"))
    })
    @SelectProvider(type = GoodTypeProvider.class, method = "findByParentId")
    List<JSONObject> findByParentId(@Param("parentId") String parentId, @Param("projectId") String projectId);

    /**
     * @name findTreeNode
     * @author 品讯科技
     * @Description 获取所有节点数据，通过注解关联返回整个tree结构
     * @time 2021-01-29 11:32:45
     */
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "{parentId=id,projectId=projectId}", property = "childs",
                    one = @One(select = "com.px.project.web.dao.IGoodTypeMapper.findByParentId"))
    })
    @SelectProvider(type = GoodTypeProvider.class, method = "findTreeNode")
    List<JSONObject> findTreeNode(@Param("projectId") String projectId);
}
