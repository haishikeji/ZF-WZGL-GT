package com.px.project.common.base;

import com.px.project.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @Package com.px.project.common.base
 * @ClassName: BaseModel
 * @Description: 实体基类
 * @author 品讯科技
 * @time 2021-01-29
 * @version V 1.0
 */
public class BaseModel {
    @ApiModelProperty(value = "表主键", dataType = "String", example = "1",hidden = false)
    @AttributeAnnotation(value="id",isGenerate =true,isPrimaryKey = true)
    private String id;//表主键

    @ApiModelProperty(value = "创建时间", dataType = "String",example = "2021-01-29 11:32:45",hidden = true)
    @AttributeAnnotation(value="createTime",isGenerate =true)
    private String createTime;//创建时间

    @ApiModelProperty(value = "创建人Code", dataType = "String", example = "007",hidden = true)
    @AttributeAnnotation(value="createUserCode",isGenerate =true)
    private String createUserCode;//创建人Code

    @ApiModelProperty(value = "创建人Name", dataType = "String", example = "gaihe",hidden = true)
    @AttributeAnnotation(value="createUserName",isGenerate =true)
    private String createUserName;//创建人Name

    @ApiModelProperty(value = "修改时间", dataType = "String",example = "2021-01-29 11:32:45",hidden = true)
    @AttributeAnnotation(value="updateTime",isGenerate =true)
    private String updateTime;//修改时间

    @ApiModelProperty(value = "修改人Code", dataType = "String", example = "666",hidden = true)
    @AttributeAnnotation(value="updateUserCode",isGenerate =true)
    private String updateUserCode;//修改人Code

    @ApiModelProperty(value = "修改人Name", dataType = "String", example = "gaihe",hidden = true)
    @AttributeAnnotation(value="updateUserName",isGenerate =true)
    private String updateUserName;//修改人Name

    @ApiModelProperty(value = "是否删除", dataType = "String", example = "1",hidden = false)
    @AttributeAnnotation(value="isremoved",isGenerate =true)
    private String isRemoved;//是否删除

    @ApiModelProperty(value = "令牌", dataType = "String", example = "1",hidden = true)
    private String token;
    @ApiModelProperty(value = "操作类型", dataType = "String", example = "1",hidden = true)
    private String operateType;

    @ApiModelProperty(value = "页码", dataType = "String", example = "1",hidden = false)
    private String pageNum;
    @ApiModelProperty(value = "页长", dataType = "String", example = "5",hidden = false)
    private String pageSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserCode() {
        return createUserCode;
    }

    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserCode() {
        return updateUserCode;
    }

    public void setUpdateUserCode(String updateUserCode) {
        this.updateUserCode = updateUserCode;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(String isRemoved) {
        this.isRemoved = isRemoved;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
