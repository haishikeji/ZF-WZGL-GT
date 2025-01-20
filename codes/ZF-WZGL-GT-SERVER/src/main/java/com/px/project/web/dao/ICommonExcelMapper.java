package com.px.project.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.px.project.common.easyExcel.entity.CheckInputExcelModel;
import com.px.project.common.easyExcel.entity.OrderGoodExcelModel;
import com.px.project.common.easyExcel.entity.PickOrderExcelModel;
import com.px.project.common.easyExcel.entity.PlanGoodsExcelModel;
import com.px.project.common.easyExcel.entity.SendOrderExcelModel;
import com.px.project.web.dao.provider.CommonExcelProvider;

@Mapper
@Repository
public interface ICommonExcelMapper {

    @SelectProvider(type = CommonExcelProvider.class, method = "findAll")
	List<SendOrderExcelModel> findAll(SendOrderExcelModel sendOrderExcelDto) ;

    @SelectProvider(type = CommonExcelProvider.class, method = "findAllPlanGoods")
	List<PlanGoodsExcelModel> findAllPlanGoods(PlanGoodsExcelModel planGoodsExcelModel);
    
    @SelectProvider(type = CommonExcelProvider.class, method = "findAllOrderGoods")
	List<OrderGoodExcelModel> findAllOrderGoods(OrderGoodExcelModel olderGoodExcelModel);

    @SelectProvider(type = CommonExcelProvider.class, method = "findCheckInputAll")
	List<CheckInputExcelModel> findCheckInputAll(CheckInputExcelModel checkInputExcelModel);

    @SelectProvider(type = CommonExcelProvider.class, method = "findPickOrderAll")
	List<PickOrderExcelModel> findPickOrderAll(PickOrderExcelModel pickOrderExcelModel);
}
