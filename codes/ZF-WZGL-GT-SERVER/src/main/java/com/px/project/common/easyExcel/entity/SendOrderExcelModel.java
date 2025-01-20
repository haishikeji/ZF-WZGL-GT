package com.px.project.common.easyExcel.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.px.project.common.easyExcel.converter.ApproveStatusConver;
import com.px.project.common.easyExcel.entity.base.BaseExcelModel;

/**
 * @author 品讯科技
 * @name SendOrderModel 发料单管理
 * @time 2021-03-16 13:59
 */
public class SendOrderExcelModel extends BaseExcelModel {

	@ExcelIgnore
    private String id;
	@ExcelIgnore
    private String projectId;

    @ExcelIgnore
    private String beginTime;
    @ExcelIgnore
    private String endTime;
    @ExcelIgnore
    private String pickCompanyId;
    @ExcelIgnore
    private String stationIntervalId;
    @ExcelIgnore
    private String approverName;
	@ExcelIgnore
    private String approverId;
    @ExcelProperty(value = { "发料单管理表","发料单编号"})
	@ColumnWidth(30)
    private String sendOrderNo;
    @ExcelProperty(value = { "发料单管理表","领料单位名称"})
    @ColumnWidth(30)
    private String pickCompanyName;
    @ExcelProperty(value = { "发料单管理表","使用站区间"})
    @ColumnWidth(30)
    private String stationIntervalName;
    @ExcelProperty(value = { "发料单管理表","审核状态"},converter = ApproveStatusConver.class)
	@ColumnWidth(30)
    private String approveStatus;
    @ExcelProperty(value = { "发料单管理表","发料人"})
   	@ColumnWidth(30)
    private String pickUser;
	@ExcelProperty(value = { "发料单管理表","领料人"})
   	@ColumnWidth(30)
    private String createUserName;//创建人Name

	@ExcelProperty(value = { "发料单管理表","发料时间"})
   	@ColumnWidth(30)
    private String createTime;//创建时间

	
	
    
    public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getStationIntervalName() {
		return stationIntervalName;
	}

	public void setStationIntervalName(String stationIntervalName) {
		this.stationIntervalName = stationIntervalName;
	}

	public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPickCompanyName() {
        return pickCompanyName;
    }

    public void setPickCompanyName(String pickCompanyName) {
        this.pickCompanyName = pickCompanyName;
    }

    public String getPickUser() {
        return pickUser;
    }

    public void setPickUser(String pickUser) {
        this.pickUser = pickUser;
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

