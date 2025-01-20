package com.px.project.web.dto;

import java.math.BigDecimal;

import com.px.project.web.entity.StockModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "StockDto", description = "Stock数据传输对象")
public class StockDto extends StockModel {

    @ApiModelProperty(value = "增减数量（正负）", dataType = "Integer", example = "10")
    private BigDecimal addCount;

    public BigDecimal getAddCount() {
        return addCount;
    }

    public void setAddCount(BigDecimal addCount) {
        this.addCount = addCount;
    }

    @ApiModelProperty(value = "盘点数量）", dataType = "Integer", example = "100")
    private  BigDecimal checkCount;

    public BigDecimal getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(BigDecimal checkCount) {
        this.checkCount = checkCount;
    }
}
