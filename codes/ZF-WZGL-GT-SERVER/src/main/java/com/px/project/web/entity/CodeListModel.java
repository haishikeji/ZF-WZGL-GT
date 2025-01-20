package com.px.project.web.entity;

import com.px.project.common.base.BaseModel;
import com.px.project.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

public class CodeListModel extends BaseModel {
    @ApiModelProperty(value = "代码值", dataType = "String", example = "1")
    @AttributeAnnotation(value="codevalue")
    private String codeValue;
    @ApiModelProperty(value = "代码值", dataType = "String", example = "男")
    @AttributeAnnotation(value="codeName")
    private String codeName;
    @ApiModelProperty(value = "代码类型", dataType = "String", example = "sex")
    @AttributeAnnotation(value="codeKind")
    private String codeKind;
    @ApiModelProperty(value = "代码类型名称", dataType = "String", example = "性别")
    @AttributeAnnotation(value="kindName")
    private String kindName;
    @ApiModelProperty(value = "级联", dataType = "String", example = "CAR001")
    @AttributeAnnotation(value="codeValueFilter")
    private String codeValueFilter;
    @ApiModelProperty(value = "序号", dataType = "String", example = "1")
    @AttributeAnnotation(value="orderId")
    private String orderId;
    @ApiModelProperty(value = "描述说明", dataType = "String", example = "描述")
    @AttributeAnnotation(value="description")
    private String description;

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeKind() {
        return codeKind;
    }

    public void setCodeKind(String codeKind) {
        this.codeKind = codeKind;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getCodeValueFilter() {
        return codeValueFilter;
    }

    public void setCodeValueFilter(String codeValueFilter) {
        this.codeValueFilter = codeValueFilter;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
