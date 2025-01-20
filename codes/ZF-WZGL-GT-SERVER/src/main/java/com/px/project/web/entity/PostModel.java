package com.px.project.web.entity;

import com.px.project.common.base.BaseModel;
import com.px.project.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

public class PostModel extends BaseModel {

    @ApiModelProperty(value = "岗位代码", dataType = "String", example = "001")
    @AttributeAnnotation(value="postcode")
    private String postCode;//岗位代码

    @ApiModelProperty(value = "岗位名称", dataType = "String", example = "001")
    @AttributeAnnotation(value="postname")
    private String postName;//岗位名称

    @ApiModelProperty(value = "岗位描述", dataType = "String", example = "001")
    @AttributeAnnotation(value="postdesc")
    private String postDesc;//岗位描述

    @ApiModelProperty(value = "状态, 0 冻结，1启用", dataType = "String", example = "1")
    @AttributeAnnotation(value="status")
    private String status;//状态, 0 冻结，1启用

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
