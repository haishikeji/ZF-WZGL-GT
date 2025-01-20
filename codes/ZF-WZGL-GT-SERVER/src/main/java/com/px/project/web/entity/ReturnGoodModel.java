package com.px.project.web.entity;

import com.px.project.common.base.BaseModel;
import com.px.project.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class ReturnGoodModel extends BaseModel {
    @ApiModelProperty(value = "项目id", dataType = "String", example = "0")
    @AttributeAnnotation(value="project_id")
    private String projectId;

    @ApiModelProperty(value = "退货单编号", dataType = "String",example = "1")
    @AttributeAnnotation(value="return_no")
    private String returnNo;

    @ApiModelProperty(value = "审核人id", dataType = "String",example = "3")
    @AttributeAnnotation(value="approver_id")
    private String approverId;

    @ApiModelProperty(value = "审核人name", dataType = "String",example = "4")
    @AttributeAnnotation(value="approver_name")
    private String approverName;

    @ApiModelProperty(value = "审核状态(0 待审核 1 审核中 2 审核驳回 3 审核成功)", dataType = "String",example = "0")
    @AttributeAnnotation(value="approve_status")
    private String approveStatus;

    @ApiModelProperty(value = "订货单明细")
    private List<ReturnGoodDetailModel> goodDetail=new ArrayList<>();

    public List<ReturnGoodDetailModel> getGoodDetail() {
        return goodDetail;
    }

    public void setGoodDetail(List<ReturnGoodDetailModel> goodDetail) {
        this.goodDetail = goodDetail;
    }

    public String getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
