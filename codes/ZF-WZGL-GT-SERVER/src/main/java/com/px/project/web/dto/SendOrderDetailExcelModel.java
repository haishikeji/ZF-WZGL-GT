package com.px.project.web.dto;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.px.project.common.base.ReturnMsgEnum;
import com.px.project.common.exception.RequestException;

import lombok.Data;

@Data
public class SendOrderDetailExcelModel {
//	物  资  名  称       规  格  型  号	   单 位	数  量	备  注
	private final int goodName_index = 1;// 物资名称*
	private final int specsModelName_index = 2;// 规格*
	private final int measureUnit_index = 3;// 单位*
	private final int num_index = 4;// 数量*
	private final int remark_index = 5;// 备注*
	private String goodName;
	private String measureUnit;
	private String specsModelName;
	private String num;
	private String remark;

	public SendOrderDetailExcelModel(Map<Integer, String> excelMap) {
		super();
		this.goodName = excelMap.get(goodName_index).replace(" ", "");
		this.measureUnit = excelMap.get(measureUnit_index).replace(" ", "");
		this.specsModelName = excelMap.get(specsModelName_index).replace(" ", "");
		this.num = excelMap.get(num_index).replace(" ", "");
		if (StringUtils.isBlank(goodName) || StringUtils.isBlank(measureUnit) || StringUtils.isBlank(specsModelName)
				|| StringUtils.isBlank(num)) {
			throw new RequestException("物资信息数据必填项不能为空！导入失败", ReturnMsgEnum.Commit_FAIL);
		}
	}

}
