package com.px.project.web.entity;

import java.util.List;

import com.px.project.common.base.BaseModel;
import com.px.project.common.customannotation.AttributeAnnotation;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author 品讯科技
 * @name orderGoodModel 订货单管理
 * @time 2021-03-15 17:02
 */
public class LimitModel extends BaseModel {
	

	private String unionKey ;
	
	@ApiModelProperty(value = "项目id", dataType = "String", example = "0")
	@AttributeAnnotation(value = "project_id")
	private String projectId; // `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
	@ApiModelProperty(value = "专业ID", dataType = "String", example = "0")
	@AttributeAnnotation(value = "major_id")
	private String majorId; // `major_id` varchar(32) DEFAULT NULL COMMENT '专业ID',

	@ApiModelProperty(value = "领料区间ID", dataType = "String", example = "0")
	@AttributeAnnotation(value = "station_interval_id")
	private String stationIntervalId; // `station_interval_id` varchar(32) DEFAULT NULL COMMENT '领料区间ID',

	@ApiModelProperty(value = "限额详情", dataType = "String", example = "0")
	private List<LimitDetailModel> listLimitDetail;

	@ApiModelProperty(value = "入库仓库id", dataType = "String", example = "0")
	@AttributeAnnotation(value = "store_id")
	private String storeId; // `store_id` varchar(32) DEFAULT NULL COMMENT '入库仓库id',

	@ApiModelProperty(value = "领料单位ID", dataType = "String", example = "0")
	@AttributeAnnotation(value = "pick_company_id")
	private String pickCompanyId; // `pick_company_id` varchar(32) DEFAULT NULL COMMENT '领料单位ID',
	@ApiModelProperty(value = "供应商id", dataType = "String", example = "0")
	@AttributeAnnotation(value = "supplier_id")
	private String supplierId; // `supplier_id` varchar(32) DEFAULT NULL COMMENT '供应商id',
	private String stationIntervalIds; // 站区间列表
	
	public String getStationIntervalIds() {
		return stationIntervalIds;
	}

	public void setStationIntervalIds(String stationIntervalIds) {
		this.stationIntervalIds = stationIntervalIds;
	}

	public String getUnionKey() {
		return unionKey;
	}

	public void setUnionKey(String unionKey) {
		this.unionKey = unionKey;
	}

	public List<LimitDetailModel> getListLimitDetail() {
		return listLimitDetail;
	}

	public void setListLimitDetail(List<LimitDetailModel> listLimitDetail) {
		this.listLimitDetail = listLimitDetail;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getPickCompanyId() {
		return pickCompanyId;
	}

	public void setPickCompanyId(String pickCompanyId) {
		this.pickCompanyId = pickCompanyId;
	}

	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getStationIntervalId() {
		return stationIntervalId;
	}

	public void setStationIntervalId(String stationIntervalId) {
		this.stationIntervalId = stationIntervalId;
	}
}
