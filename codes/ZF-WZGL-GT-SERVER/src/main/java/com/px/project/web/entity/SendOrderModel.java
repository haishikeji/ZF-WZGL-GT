package com.px.project.web.entity;

import com.px.project.common.base.BaseModel;
import com.px.project.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 品讯科技
 * @name SendOrderModel 发料单管理
 * @time 2021-03-16 13:59
 */
public class SendOrderModel extends BaseModel {
    @ApiModelProperty(value = "项目id", dataType = "String", example = "0")
    @AttributeAnnotation(value="project_id")
    private String projectId;

    @ApiModelProperty(value = "发料单编号", dataType = "String", example = "1")
    @AttributeAnnotation(value="send_order_no")
    private String sendOrderNo;
    
    private String pickOrderId;
    
  /*  @ApiModelProperty(value = "仓库id", dataType = "String", example = "2")
    @AttributeAnnotation(value="store_id")
    private String storeId;*/

    @ApiModelProperty(value = "领料单位id", dataType = "String", example = "3")
    @AttributeAnnotation(value="pick_company_id")
    private String pickCompanyId;

    @ApiModelProperty(value = "站区间id", dataType = "String", example = "4")
    @AttributeAnnotation(value="station_interval_id")
    private String stationIntervalId;

    @ApiModelProperty(value = "审核人id", dataType = "String", example = "7")
    @AttributeAnnotation(value="approver_id")
    private String approverId;

    @ApiModelProperty(value = "审核人name", dataType = "String", example = "8")
    @AttributeAnnotation(value="approver_name")
    private String approverName;

    @ApiModelProperty(value = "审核状态(0 待审核 1 审核中 2 审核驳回 3 审核成功)", dataType = "String", example = "9")
    @AttributeAnnotation(value="approve_status")
    private String approveStatus;

    @ApiModelProperty(value = "领料人", dataType = "String", example = "9")
    @AttributeAnnotation(value="pick_user")
    private String pickUser;

    @ApiModelProperty(value = "发料单明细")
    private List<SendOrderDetailModel> sendDetail=new ArrayList<>();

    
    private String majorId;
    
    
    
    public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	private String seqNo;
    private String handNo;
    
    public String getHandNo() {
		return handNo;
	}

	public void setHandNo(String handNo) {
		this.handNo = handNo;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
    public List<SendOrderDetailModel> getSendDetail() {
        return sendDetail;
    }

    public void setSendDetail(List<SendOrderDetailModel> sendDetail) {
        this.sendDetail = sendDetail;
    }

    public String getPickUser() {
        return pickUser;
    }

    public void setPickUser(String pickUser) {
        this.pickUser = pickUser;
    }
    
    public String getPickOrderId() {
		return pickOrderId;
	}

	public void setPickOrderId(String pickOrderId) {
		this.pickOrderId = pickOrderId;
	}

	public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSendOrderNo() {
        return sendOrderNo;
    }

    public void setSendOrderNo(String sendOrderNo) {
        this.sendOrderNo = sendOrderNo;
    }

    public String getPickCompanyId() {
        return pickCompanyId;
    }

    public void setPickCompanyId(String pickCompanyId) {
        this.pickCompanyId = pickCompanyId;
    }

    public String getStationIntervalId() {
        return stationIntervalId;
    }

    public void setStationIntervalId(String stationIntervalId) {
        this.stationIntervalId = stationIntervalId;
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
}
