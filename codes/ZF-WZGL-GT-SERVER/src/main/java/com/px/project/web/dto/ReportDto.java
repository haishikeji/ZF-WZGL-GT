package com.px.project.web.dto;

import com.px.project.common.base.BaseModel;
import com.px.project.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ReportDto", description = "Report数据传输对象")
public class ReportDto extends BaseModel {
    @ApiModelProperty(value = "项目编号", dataType = "String", example = "1")
    private String projectId;

    @ApiModelProperty(value = "时间起", dataType = "String", example = "2020-01-01")
    private String beginTime;

    @ApiModelProperty(value = "时间止", dataType = "String", example = "2021-12-12")
    private String endTime;

    @ApiModelProperty(value = "仓库id", dataType = "String", example = "1")
    private String storeId;

    @ApiModelProperty(value = "物资编号", dataType = "String", example = "2")
    private String goodCode;

    @ApiModelProperty(value = "物资名称", dataType = "String", example = "3")
    private String goodName;

    @ApiModelProperty(value = "规格型号", dataType = "String", example = "4")
    private String specsModelName;

    @ApiModelProperty(value = "库位id", dataType = "String", example = "5")
    private String gridId;

    @ApiModelProperty(value = "领料单位名称", dataType = "String", example = "5")
    private String pickCompanyName;

    @ApiModelProperty(value = "供应商id", dataType = "String",example = "001")
    @AttributeAnnotation(value="supplier_id")
    private String supplierId;

    @ApiModelProperty(value = "领料单位id", dataType = "String",example = "002")
    private String pickCompanyId;

    @ApiModelProperty(value = "业务id", dataType = "String",example = "666")
    private String busId;
    
    private String majorId;//专业ID
    
    public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getPickCompanyId() {
        return pickCompanyId;
    }

    public void setPickCompanyId(String pickCompanyId) {
        this.pickCompanyId = pickCompanyId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getPickCompanyName() {
        return pickCompanyName;
    }

    public void setPickCompanyName(String pickCompanyName) {
        this.pickCompanyName = pickCompanyName;
    }

    public String getGridId() {
        return gridId;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getSpecsModelName() {
        return specsModelName;
    }

    public void setSpecsModelName(String specsModelName) {
        this.specsModelName = specsModelName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
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
