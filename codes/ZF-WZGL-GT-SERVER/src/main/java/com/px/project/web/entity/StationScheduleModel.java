package com.px.project.web.entity;

import java.math.BigDecimal;

import com.px.project.common.base.BaseModel;
import com.px.project.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

public class StationScheduleModel extends BaseModel {
	
	private String goodId;
	private String majorId;
	private String unionKey;
	
    @ApiModelProperty(value = "三量id", dataType = "String", example = "1")
    @AttributeAnnotation(value="three_estimate_id")
    private String threeEstimateId;

    @ApiModelProperty(value = "站区间id", dataType = "String", example = "2")
    @AttributeAnnotation(value="station_schedule_id")
    private String stationScheduleId;

    @ApiModelProperty(value = "站区间值", dataType = "String", example = "3")
    @AttributeAnnotation(value="station_schedule_value")
    private String stationScheduleValue;

    @ApiModelProperty(value = "状态, 0 冻结，1启用", dataType = "String", example = "1")
    @AttributeAnnotation(value="status")
    private String status;

    @ApiModelProperty(value = "已使用数量", dataType = "String", example = "1")
    @AttributeAnnotation(value="used_num")
    private BigDecimal usedNum;
    
    
    
    public String getUnionKey() {
		return unionKey;
	}

	public void setUnionKey(String unionKey) {
		this.unionKey = unionKey;
	}

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

	public BigDecimal getUsedNum() {
		return usedNum;
	}

	public void setUsedNum(BigDecimal usedNum) {
		this.usedNum = usedNum;
	}

	public String getThreeEstimateId() {
        return threeEstimateId;
    }

    public void setThreeEstimateId(String threeEstimateId) {
        this.threeEstimateId = threeEstimateId;
    }

    public String getStationScheduleId() {
        return stationScheduleId;
    }

    public void setStationScheduleId(String stationScheduleId) {
        this.stationScheduleId = stationScheduleId;
    }

    public String getStationScheduleValue() {
        return stationScheduleValue;
    }

    public void setStationScheduleValue(String stationScheduleValue) {
        this.stationScheduleValue = stationScheduleValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
