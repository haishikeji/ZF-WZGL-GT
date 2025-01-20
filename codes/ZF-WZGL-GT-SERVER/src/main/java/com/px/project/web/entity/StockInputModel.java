package com.px.project.web.entity;

import com.px.project.common.base.BaseModel;
import com.px.project.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 品讯科技
 * @name StockInputModel 入库表
 * @time 2021-03-16 13:07
 */
public class StockInputModel extends BaseModel {
    @ApiModelProperty(value = "项目id", dataType = "String", example = "1")
    @AttributeAnnotation(value="project_id")
    private String projectId;

    @ApiModelProperty(value = "入库单号", dataType = "String", example = "1")
    @AttributeAnnotation(value="input_no")
    private String inputNo;

    @ApiModelProperty(value = "入库仓库id", dataType = "String", example = "2")
    @AttributeAnnotation(value="store_id")
    private String storeId;

    @ApiModelProperty(value = "库位id", dataType = "String", example = "3 ")
    @AttributeAnnotation(value="grid_id")
    private String gridId;

    @ApiModelProperty(value = "物资id", dataType = "String", example = "4")
    @AttributeAnnotation(value="good_id")
    private String goodId;

    @ApiModelProperty(value = "供应商id", dataType = "String", example = "5")
    @AttributeAnnotation(value="supplier_id")
    private String supplierId;

    @ApiModelProperty(value = "入库数量", dataType = "String", example = "6")
    @AttributeAnnotation(value="input_num")
    private String inputNum;

    @ApiModelProperty(value = "入库前库存总数", dataType = "String", example = "7")
    @AttributeAnnotation(value="before_input_num")
    private String beforeInputNum;

    @ApiModelProperty(value = "入库时间", dataType = "String", example = "2021-03-16 13:10:06")
    @AttributeAnnotation(value="input_time")
    private String inputTime;

    @ApiModelProperty(value = "业务id", dataType = "String", example = "11")
    @AttributeAnnotation(value="bus_id")
    private String busId;

    @ApiModelProperty(value = "业务代码", dataType = "String", example = "22")
    @AttributeAnnotation(value="bus_code")
    private String busCode;

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getInputNo() {
        return inputNo;
    }

    public void setInputNo(String inputNo) {
        this.inputNo = inputNo;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getGridId() {
        return gridId;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getInputNum() {
        return inputNum;
    }

    public void setInputNum(String inputNum) {
        this.inputNum = inputNum;
    }

    public String getBeforeInputNum() {
        return beforeInputNum;
    }

    public void setBeforeInputNum(String beforeInputNum) {
        this.beforeInputNum = beforeInputNum;
    }

    public String getInputTime() {
        return inputTime;
    }

    public void setInputTime(String inputTime) {
        this.inputTime = inputTime;
    }
}
