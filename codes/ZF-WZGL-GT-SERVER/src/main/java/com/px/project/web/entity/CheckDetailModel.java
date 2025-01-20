package com.px.project.web.entity;

import com.px.project.common.base.BaseModel;
import com.px.project.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

//盘单点明细
public class CheckDetailModel extends BaseModel {
    @ApiModelProperty(value = "盘点单id", dataType = "String", example = "1")
    @AttributeAnnotation(value="check_id")
    private String checkId;

    @ApiModelProperty(value = "物资id", dataType = "String", example = "2")
    @AttributeAnnotation(value="good_id")
    private String goodId;

    @ApiModelProperty(value = "盘点数量", dataType = "String", example = "3")
    @AttributeAnnotation(value="num")
    private String num;

    @ApiModelProperty(value = "库存数量（快照）", dataType = "String", example = "33")
    @AttributeAnnotation(value="stock_num")
    private String stockNum;

    @ApiModelProperty(value = "库位id", dataType = "String", example = "4")
    @AttributeAnnotation(value="grid_id")
    private String gridId;

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getGridId() {
        return gridId;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
    }
}
