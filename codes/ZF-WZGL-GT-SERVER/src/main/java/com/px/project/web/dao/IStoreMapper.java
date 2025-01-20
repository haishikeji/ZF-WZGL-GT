package com.px.project.web.dao;

import com.px.project.web.dao.provider.StoreProvider;
import com.px.project.web.dto.StoreDto;
import com.px.project.web.entity.StoreModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface IStoreMapper {

    @InsertProvider(type = StoreProvider.class, method = "save")
    int save(StoreModel storeModel);

    @DeleteProvider(type = StoreProvider.class, method = "deleteByIds")
    int deleteByIds(@Param("ids") String ids);

    @UpdateProvider(type = StoreProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param("ids") String ids);

    @UpdateProvider(type = StoreProvider.class, method = "update")
    int update(StoreModel storeModel);

    @SelectProvider(type = StoreProvider.class, method = "findById")
    StoreModel findById(@Param("id") String id);

    @SelectProvider(type = StoreProvider.class, method = "findAll")
    List<StoreModel> findAll(StoreDto storeDto);
}
