package com.px.project.web.dto;

import com.px.project.web.entity.StockInputModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "StockInputDto", description = "StockInput数据传输对象")
public class StockInputDto extends StockInputModel {
    @ApiModelProperty(value = "时间起", dataType = "String", example = "2020-03-23")
    private String beginTime;

    @ApiModelProperty(value = "时间止", dataType = "String", example = "2022-05-23")
    private String endTime;

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
