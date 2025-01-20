package com.px.project.web.entity;

import com.px.project.common.base.BaseModel;
import com.px.project.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

//调拨明细管理
public class AllotDetailModel extends BaseModel {
    @ApiModelProperty(value = "调拨单id", dataType = "String", example = "1")
    @AttributeAnnotation(value="allot_id")
    private String allotId;

    @ApiModelProperty(value = "物资id", dataType = "String", example = "2")
    @AttributeAnnotation(value="good_id")
    private String goodId;

    @ApiModelProperty(value = "库位id", dataType = "String", example = "4")
    @AttributeAnnotation(value="grid_id")
    private String gridId;

    @ApiModelProperty(value = "数量", dataType = "String", example = "5")
    @AttributeAnnotation(value="num")
    private String num;

    public String getAllotId() {
        return allotId;
    }

    public void setAllotId(String allotId) {
        this.allotId = allotId;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getGridId() {
        return gridId;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
