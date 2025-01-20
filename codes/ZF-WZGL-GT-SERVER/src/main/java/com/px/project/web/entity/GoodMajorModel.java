package com.px.project.web.entity;

import com.px.project.common.base.BaseModel;
import com.px.project.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description= "物资管理实体")
public class GoodMajorModel extends BaseModel {

    @ApiModelProperty(value = "物品id", dataType = "String", example = "0")
    @AttributeAnnotation(value = "good_id")
    private String goodId;

    @ApiModelProperty(value = "专业ID", dataType = "String", example = "1")
    @AttributeAnnotation(value="major_id")
    private String majorId;

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

}
