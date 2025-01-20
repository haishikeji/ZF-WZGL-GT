package com.px.project.web.entity;

import com.px.project.common.base.BaseModel;
import com.px.project.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 品讯科技
 * @name SendOrderDetailModel 发料单明细
 * @time 2021-03-16 14:17
 */
public class SendOrderDetailModel extends BaseModel {
    @ApiModelProperty(value = "发料单id", dataType = "String", example = "1")
    @AttributeAnnotation(value="send_order_id")
    private String sendOrderId;

    @ApiModelProperty(value = "领料单id", dataType = "String", example = "2")
    @AttributeAnnotation(value="pick_order_id")
    private String pickOrderId;

    @ApiModelProperty(value = "库位id", dataType = "String", example = "101")
    @AttributeAnnotation(value="grid_id")
    private String gridId;

    @ApiModelProperty(value = "领料单明细id", dataType = "String", example = "3")
    @AttributeAnnotation(value="pick_order_detail_id")
    private String pickOrderDetailId;

    @ApiModelProperty(value = "数量", dataType = "String", example = "4")
    @AttributeAnnotation(value="num")
    private String num;
    private String orgNum;
    
    public String getOrgNum() {
		return orgNum;
	}

	public void setOrgNum(String orgNum) {
		this.orgNum = orgNum;
	}

	public String getPickOrderDetailId() {
        return pickOrderDetailId;
    }

    public void setPickOrderDetailId(String pickOrderDetailId) {
        this.pickOrderDetailId = pickOrderDetailId;
    }

    public String getPickOrderId() {
        return pickOrderId;
    }

    public void setPickOrderId(String pickOrderId) {
        this.pickOrderId = pickOrderId;
    }

    public String getSendOrderId() {
        return sendOrderId;
    }

    public void setSendOrderId(String sendOrderId) {
        this.sendOrderId = sendOrderId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getGridId() {
        return gridId;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
    }
}
