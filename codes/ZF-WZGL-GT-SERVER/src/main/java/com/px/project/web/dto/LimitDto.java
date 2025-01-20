package com.px.project.web.dto;

import com.px.project.web.entity.LimitModel;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "LimitDto", description = "Limit数据传输对象")
public class LimitDto extends LimitModel {
	private String searchKey;

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	
}
