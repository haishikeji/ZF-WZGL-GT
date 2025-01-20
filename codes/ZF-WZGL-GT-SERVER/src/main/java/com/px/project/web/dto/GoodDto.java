package com.px.project.web.dto;

import com.px.project.web.entity.GoodModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "GoodDto", description = "Good数据传输对象")
public class GoodDto extends GoodModel {
    @ApiModelProperty(value = "物资代码（强匹配）", dataType = "String", example = "0")
    private String goodCodeMatch;

    private String majorId;
    private String majorName;
    
    private String type;
    
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	public String getGoodCodeMatch() {
        return goodCodeMatch;
    }

    public void setGoodCodeMatch(String goodCodeMatch) {
        this.goodCodeMatch = goodCodeMatch;
    }

    @ApiModelProperty(value = "结果筛选（默认空；1：定测量范围）", dataType = "String", example = "0")
    private String filter;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @ApiModelProperty(value = "物资名称（强匹配）", dataType = "String", example = "0")
    private  String goodNameMatch;

    @ApiModelProperty(value = "规格型号（强匹配）", dataType = "String", example = "0")
    private  String specsModelNameMatch;

    public String getGoodNameMatch() {
        return goodNameMatch;
    }

    public void setGoodNameMatch(String goodNameMatch) {
        this.goodNameMatch = goodNameMatch;
    }

    public String getSpecsModelNameMatch() {
        return specsModelNameMatch;
    }

    public void setSpecsModelNameMatch(String specsModelNameMatch) {
        this.specsModelNameMatch = specsModelNameMatch;
    }
}
