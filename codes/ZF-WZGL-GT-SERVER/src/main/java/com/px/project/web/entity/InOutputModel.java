package com.px.project.web.entity;

import com.px.project.common.base.BaseModel;
import com.px.project.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

//直入直出单
public class InOutputModel extends BaseModel {
    @ApiModelProperty(value = "项目id", dataType = "String", example = "1")
    @AttributeAnnotation(value="project_id")
    private String projectId;

    @ApiModelProperty(value = "直入直出单号", dataType = "String", example = "2")
    @AttributeAnnotation(value="inoutput_no")
    private String inOutputNo;

    @ApiModelProperty(value = "直入直出类型（1直出，2直入） ", dataType = "String", example = "1")
    @AttributeAnnotation(value="inoutput_type")
    private String inOutputType;

    @ApiModelProperty(value = "仓库id", dataType = "String", example = "3")
    @AttributeAnnotation(value="store_id")
    private String storeId;

    @ApiModelProperty(value = "站区间id", dataType = "String", example = "4")
    @AttributeAnnotation(value="station_interval_id")
    private String stationIntervalId;

    @ApiModelProperty(value = "领料单位id", dataType = "String", example = "5")
    @AttributeAnnotation(value="pick_company_id")
    private String pickCompanyId;

    @ApiModelProperty(value = "领料人", dataType = "String", example = "6")
    @AttributeAnnotation(value="pick_user")
    private String pickUser;

    @ApiModelProperty(value = "专业id", dataType = "String", example = "7")
    @AttributeAnnotation(value="major_id")
    private String majorId;

    @ApiModelProperty(value = "供应商id", dataType = "String", example = "8")
    @AttributeAnnotation(value="supplier_id")
    private String supplierId;

    @ApiModelProperty(value = "供应商", dataType = "String", example = "8")
    @AttributeAnnotation(value="supplier_name")
    private String supplierName;

    @ApiModelProperty(value = "审核状态(0 待审核 1 审核中 2 审核驳回 3 审核成功)", dataType = "String",example = "9")
    @AttributeAnnotation(value="approve_status")
    private String approveStatus;

    @ApiModelProperty(value = "审核人id", dataType = "String",example = "10")
    @AttributeAnnotation(value="approver_id")
    private String approverId;

    @ApiModelProperty(value = "审核人name", dataType = "String",example = "11")
    @AttributeAnnotation(value="approver_name")
    private String approverName;

    
    
    public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@ApiModelProperty(value = "直入直出单明细")
    public List<InOutputDetailModel> getInOutputDetail() {
        return inOutputDetail;
    }

    public void setInOutputDetail(List<InOutputDetailModel> inOutputDetail) {
        this.inOutputDetail = inOutputDetail;
    }

    @ApiModelProperty(value = "直入直出明细", dataType = "String",example = "11")
    private List<InOutputDetailModel> inOutputDetail =new ArrayList<>();

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getInOutputNo() {
        return inOutputNo;
    }

    public void setInOutputNo(String inOutputNo) {
        this.inOutputNo = inOutputNo;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStationIntervalId() {
        return stationIntervalId;
    }

    public void setStationIntervalId(String stationIntervalId) {
        this.stationIntervalId = stationIntervalId;
    }

    public String getPickCompanyId() {
        return pickCompanyId;
    }

    public void setPickCompanyId(String pickCompanyId) {
        this.pickCompanyId = pickCompanyId;
    }

    public String getPickUser() {
        return pickUser;
    }

    public void setPickUser(String pickUser) {
        this.pickUser = pickUser;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
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

    public String getInOutputType() {
        return inOutputType;
    }

    public void setInOutputType(String inOutputType) {
        this.inOutputType = inOutputType;
    }
}
