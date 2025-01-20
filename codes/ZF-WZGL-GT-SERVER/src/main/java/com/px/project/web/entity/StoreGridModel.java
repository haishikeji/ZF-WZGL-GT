package com.px.project.web.entity;

import com.px.project.common.base.BaseModel;
import com.px.project.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

public class StoreGridModel extends BaseModel {
    @ApiModelProperty(value = "仓库id", dataType = "String", example = "1")
    @AttributeAnnotation(value="store_id")
    private String storeId;


    @ApiModelProperty(value = "网格编码", dataType = "String", example = "2")
    @AttributeAnnotation(value="grid_no")
    private String gridNo;

    @ApiModelProperty(value = "库位名称", dataType = "String", example = "3")
    @AttributeAnnotation(value="grid_name")
    private String gridName;

    @ApiModelProperty(value = "网格行号", dataType = "String", example = "4")
    @AttributeAnnotation(value="grid_row_no")
    private String gridRowNo;

    @ApiModelProperty(value = "网格列号", dataType = "String", example = "5")
    @AttributeAnnotation(value="grid_column_no")
    private String gridColumnNo;

    @ApiModelProperty(value = "启用状态（1启用，0停用）", dataType = "String", example = "1")
    @AttributeAnnotation(value="status")
    private String status;

    @ApiModelProperty(value = "描述", dataType = "String", example = "6")
    @AttributeAnnotation(value="remarks")
    private String remarks;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getGridNo() {
        return gridNo;
    }

    public void setGridNo(String gridNo) {
        this.gridNo = gridNo;
    }

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public String getGridRowNo() {
        return gridRowNo;
    }

    public void setGridRowNo(String gridRowNo) {
        this.gridRowNo = gridRowNo;
    }

    public String getGridColumnNo() {
        return gridColumnNo;
    }

    public void setGridColumnNo(String gridColumnNo) {
        this.gridColumnNo = gridColumnNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
