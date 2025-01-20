package com.px.project.web.entity;

import com.px.project.common.base.BaseModel;
import com.px.project.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "物资管理实体")
public class GoodModel extends BaseModel {

	@ApiModelProperty(value = "项目id", dataType = "String", example = "0")
	@AttributeAnnotation(value = "project_id")
	private String projectId;

	@ApiModelProperty(value = "物资编号", dataType = "String", example = "1")
	@AttributeAnnotation(value = "good_code")
	private String goodCode;

	@ApiModelProperty(value = "物资名称", dataType = "String", example = "2")
	@AttributeAnnotation(value = "good_name")
	private String goodName;

	@ApiModelProperty(value = "计量单位", dataType = "String", example = "3")
	@AttributeAnnotation(value = "measure_unit")
	private String measureUnit;

	@ApiModelProperty(value = "检测单位", dataType = "String", example = "4")
	@AttributeAnnotation(value = "test_unit")
	private String testUnit;

	@ApiModelProperty(value = "规格型号", dataType = "String", example = "7")
	@AttributeAnnotation(value = "specs_model_name")
	private String specsModelName;

	@ApiModelProperty(value = "物资类别", dataType = "String", example = "8")
	@AttributeAnnotation(value = "good_type")
	private String goodType;

	@ApiModelProperty(value = "库存上限", dataType = "String", example = "10")
	@AttributeAnnotation(value = "stock_upper")
	private String stockUpper;

	@ApiModelProperty(value = "库存下限", dataType = "String", example = "11")
	@AttributeAnnotation(value = "stock_lower")
	private String stockLower;

	@ApiModelProperty(value = "备注", dataType = "String", example = "12")
	@AttributeAnnotation(value = "remarks")
	private String remarks;

	@ApiModelProperty(value = "状态, 0 冻结，1启用", dataType = "String", example = "13")
	@AttributeAnnotation(value = "status")
	private String status;

	@ApiModelProperty(value = "换算变量（计量单位 * 换算变量 = 检测单位）", dataType = "String", example = "100")
	@AttributeAnnotation(value = "convert_value")
	private String convertValue;

	private String majorIds;
	public String getMajorIds() {
		return majorIds;
	}

	public void setMajorIds(String majorIds) {
		this.majorIds = majorIds;
	}

	public String getConvertValue() {
		return convertValue;
	}

	public void setConvertValue(String convertValue) {
		this.convertValue = convertValue;
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

	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	public String getTestUnit() {
		return testUnit;
	}

	public void setTestUnit(String testUnit) {
		this.testUnit = testUnit;
	}

	public String getSpecsModelName() {
		return specsModelName;
	}

	public void setSpecsModelName(String specsModelName) {
		this.specsModelName = specsModelName;
	}

	public String getGoodType() {
		return goodType;
	}

	public void setGoodType(String goodType) {
		this.goodType = goodType;
	}

	public String getStockUpper() {
		return stockUpper;
	}

	public void setStockUpper(String stockUpper) {
		this.stockUpper = stockUpper;
	}

	public String getStockLower() {
		return stockLower;
	}

	public void setStockLower(String stockLower) {
		this.stockLower = stockLower;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
}
