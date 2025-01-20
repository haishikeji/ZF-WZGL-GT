package com.px.project.web.entity;

import com.px.project.common.base.BaseModel;
import com.px.project.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

//点验入库明细
public class CheckInputDetailModel extends BaseModel {
    @ApiModelProperty(value = "点验入库单id", dataType = "String", example = "1")
    @AttributeAnnotation(value="check_input_id")
    private String checkInputId;

    @ApiModelProperty(value = "物资id", dataType = "String", example = "2")
    @AttributeAnnotation(value="good_id")
    private String goodId;

    @ApiModelProperty(value = "库位id", dataType = "String", example = "3")
    @AttributeAnnotation(value="grid_id")
    private String gridId;

    @ApiModelProperty(value = "数量", dataType = "String", example = "5")
    @AttributeAnnotation(value="num")
    private String num;

    private String remark;
    

    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String orgNum;
//    private String orderDetailId;
    private String orderGoodId;

	public String getOrderGoodId() {
		return orderGoodId;
	}

	public void setOrderGoodId(String orderGoodId) {
		this.orderGoodId = orderGoodId;
	}

	public String getOrgNum() {
		return orgNum;
	}

	public void setOrgNum(String orgNum) {
		this.orgNum = orgNum;
	}

//	public String getOrderDetailId() {
//		return orderDetailId;
//	}
//
//	public void setOrderDetailId(String orderDetailId) {
//		this.orderDetailId = orderDetailId;
//	}

	public String getCheckInputId() {
        return checkInputId;
    }

    public void setCheckInputId(String checkInputId) {
        this.checkInputId = checkInputId;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getGridId() {
        return gridId;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
