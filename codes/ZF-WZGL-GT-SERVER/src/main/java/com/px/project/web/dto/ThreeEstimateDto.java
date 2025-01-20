package com.px.project.web.dto;

import com.px.project.web.entity.ThreeEstimateModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "threeEstimateDto", description = "threeEstimate数据传输对象")
public class ThreeEstimateDto extends ThreeEstimateModel {
    @ApiModelProperty(value = "物资编号", dataType = "String", example = "1")
    private String goodCode;

    @ApiModelProperty(value = "物资名称", dataType = "String", example = "2")
    private String goodName;

    
    private String majorName;
    
    private String typeName;
    
    
    
    public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
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

    //兼容 specsModelName (之前文档名字写错了，减少前端修改，后端做兼容映射处理 2021-05-12 00:08:10） 666
    public String getSpecsModel() {
        return super.getSpecsModelName();
    }

    public void setSpecsModel(String specsModel) {
        super.setSpecsModelName(specsModel);
    }
}
