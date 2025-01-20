package com.px.project.web.dto;

import java.util.List;

import com.px.project.web.entity.PickOrderModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PickOrderDto", description = "PickOrder数据传输对象")
public class PickOrderDto extends PickOrderModel {
    @ApiModelProperty(value = "时间起", dataType = "String", example = "2020-03-23")
    private String beginTime;

    @ApiModelProperty(value = "时间止", dataType = "String", example = "2022-05-23")
    private String endTime;
    //物资名称
    private String goodName;
    
    private String queryType; //1类型  查询balance充足的。
    
    private List<PickOrderDetailExcelModel> pickOrderDetailExcelModelList;
   
    
    public List<PickOrderDetailExcelModel> getPickOrderDetailExcelModelList() {
		return pickOrderDetailExcelModelList;
	}

	public void setPickOrderDetailExcelModelList(List<PickOrderDetailExcelModel> pickOrderDetailExcelModelList) {
		this.pickOrderDetailExcelModelList = pickOrderDetailExcelModelList;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
