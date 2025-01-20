package com.px.project.web.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import com.px.project.common.base.BaseProvider;
import com.px.project.common.easyExcel.entity.CheckInputExcelModel;
import com.px.project.common.easyExcel.entity.OrderGoodExcelModel;
import com.px.project.common.easyExcel.entity.PickOrderExcelModel;
import com.px.project.common.easyExcel.entity.PlanGoodsExcelModel;
import com.px.project.common.easyExcel.entity.SendOrderExcelModel;
import com.px.project.common.utils.StringUtil;

/**
 * @author 品讯科技
 * @version V 1.0
 * @Package com.px.project.web.dao.provider
 * @ClassName SendOrderProvider
 * @Description SendOrderProvider
 * @time 2021-03-16 14:20:15
 */
public class CommonExcelProvider extends BaseProvider {

    /**
     * @param sendOrderDto
     * @return sql
     * @throws
     * @name findAll
     * @author 品讯科技
     * @Description 根据条件查询数据并分页
     * @time 2021-03-16 14:20:15
     */
    public String findAll(final SendOrderExcelModel sendOrderDto) {
    	String selectOrInsertSql = "t.id as id," +
                "t.project_id as projectId," +
                "t.send_order_no as sendOrderNo," +
                /*"t.store_id as storeId,"+*/
                "t.pick_company_id as pickCompanyId," +
                "t.pick_user as pickUser," +
                "t.station_interval_id as stationIntervalId," +
    									 "t.approver_id as approverId,"+
    									 "t.approver_name as approverName,"+
    									 "t.approve_status as approveStatus,"+
                "t.createTime as createTime," +
                "t.createUserCode as createUserCode," +
                "t.createUserName as createUserName," +
                "t.updateTime as updateTime," +
                "t.updateUserCode as updateUserCode," +
                "t.updateUserName as updateUserName," +
                "t.isremoved as isRemoved";
        String sql = new SQL() {
            {
                SELECT(selectOrInsertSql);
                /* SELECT("s.store_no as storeNo,s.store_name as storeName");*/
                SELECT("si.station_interval_code as stationIntervalCode,si.station_interval_name as stationIntervalName");
                SELECT("p.pick_company_no as pickCompanyNo,p.pick_company_name as pickCompanyName");

                FROM("b_t_send_order t");
                /* LEFT_OUTER_JOIN("b_t_store s on t.store_id = s.id and ifnull(s.isRemoved,'0')<>'1'");*/
                LEFT_OUTER_JOIN("b_t_station_interval si on t.station_interval_id = si.id and ifnull(si.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("b_t_pick_company p on p.id = t.pick_company_id and ifnull(p.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("t_workflow wf on wf.bus_id = t.id"); //审批状态
                WHERE("t.project_id = #{projectId}");

                if (!StringUtil.isBlank(sendOrderDto.getId())) {
                    WHERE("t.id = #{id}");
                }
                if (!StringUtil.isBlank(sendOrderDto.getPickUser())) {
                    WHERE("t.pick_user like concat(concat('%',#{pickUser}),'%')");
                }
                if (!StringUtil.isBlank(sendOrderDto.getSendOrderNo())) {
                    WHERE("t.send_order_no like concat(concat('%',#{sendOrderNo}),'%')");
                }
              /*  if(!StringUtil.isBlank (sendOrderDto.getStoreId())){
                    WHERE("t.store_id = #{storeId}");
                }*/
                if (!StringUtil.isBlank(sendOrderDto.getPickCompanyId())) {
                    WHERE("t.pick_company_id = #{pickCompanyId}");
                }
                if (!StringUtil.isBlank(sendOrderDto.getStationIntervalId())) {
                    WHERE("t.station_interval_id = #{stationIntervalId}");
                }
               /* if(!StringUtil.isBlank (sendOrderDto.getApproverId())){
                    WHERE("t.approver_id = #{approverId}");
                }
                if(!StringUtil.isBlank (sendOrderDto.getApproverName())){
                    WHERE("t.approver_name = #{approverName}");
                }*/
                if (!StringUtil.isBlank(sendOrderDto.getApproveStatus())) {
                    WHERE("wf.approve_status = #{approveStatus}");
                }
                if (!StringUtil.isBlank(sendOrderDto.getCreateTime())) {
                    WHERE("t.createTime = #{createTime}");
                }
                if (!StringUtil.isBlank(sendOrderDto.getCreateUserCode())) {
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if (!StringUtil.isBlank(sendOrderDto.getCreateUserName())) {
                    WHERE("t.createUserName = #{createUserName}");
                }
                if (!StringUtil.isBlank(sendOrderDto.getUpdateTime())) {
                    WHERE("t.updateTime = #{updateTime}");
                }
                if (!StringUtil.isBlank(sendOrderDto.getUpdateUserCode())) {
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if (!StringUtil.isBlank(sendOrderDto.getUpdateUserName())) {
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                if (!StringUtil.isBlank(sendOrderDto.getIsRemoved())) {
                    WHERE("t.isRemoved = #{isRemoved}");
                } else {
                    WHERE("ifnull(t.isRemoved,'0') <> '1'"); //不包含删除项
                }
            }
        }.toString();
        return sql;
    }
    /**
     * @name findAll
     * @author 品讯科技
     * @param planGoodDto
     * @Description 根据条件查询数据并分页
     * @time 2023-09-13 19:47:25
     * @throws
     * @return sql
     */
    public String findAllPlanGoods(final PlanGoodsExcelModel planGoodDto) {
    	String selectOrInsertSql="t.id as id,"+
				 "t.plan_no as planNo,"+
				 "t.project_id as projectId,"+
				 "t.supplier_id as supplierId,"+
				 "t.major_id as majorId,"+
				 "t.station_schedule_id as stationScheduleId,"+
				 "t.plan_over_date as planOverDate,"+
				 "t.approver_id as approverId,"+
				 "t.approver_name as approverName,"+
				 "t.approve_status as approveStatus,"+
				 "t.remark as remark,"+
				 "t.createTime as createTime,"+
				 "t.createUserCode as createUserCode,"+
				 "t.createUserName as createUserName,"+
				 "t.updateTime as updateTime,"+
				 "t.updateUserCode as updateUserCode,"+
				 "t.updateUserName as updateUserName,"+
				 "t.isremoved as isRemoved";
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                SELECT("s.supplier_no as supplierNo,s.supplier_name as supplierName");
                SELECT("m.major_name as majorName,m.major_code as majorCode");
                SELECT("ss.station_interval_name as stationIntervalName"
                		+ ",tfi.id as tfiId,tfi.task_node_id as taskNodeId"
                		+ " ");
                FROM("b_t_plan_goods t");
                LEFT_OUTER_JOIN("b_t_supplier s on t.supplier_id = s.id");
                LEFT_OUTER_JOIN("b_t_major m on t.major_id = m.id");
                LEFT_OUTER_JOIN("b_t_station_interval ss on t.station_schedule_id = ss.id");
                LEFT_OUTER_JOIN("t_workflow wf on wf.bus_id = t.id"); //审批状态
                LEFT_OUTER_JOIN(" ( select DISTINCT(bus_id) as bus_id ,task_node_id,id from  t_flow_ins where task_node_id = 17  group by bus_id ) tfi on tfi.bus_id  = wf.bus_id "); //审批状态
                WHERE("t.project_id = #{projectId}");
                if(!StringUtil.isBlank (planGoodDto.getId())){
                    WHERE("t.id = #{id}");
                }
                if(!StringUtil.isBlank (planGoodDto.getPlanNo())){
                    WHERE("t.plan_no = #{planNo}");
                }
                if(!StringUtil.isBlank (planGoodDto.getProjectId())){
                    WHERE("t.project_id = #{projectId}");
                }
                if(!StringUtil.isBlank (planGoodDto.getSupplierId())){
                    WHERE("t.supplier_id = #{supplierId}");
                }
                if(!StringUtil.isBlank (planGoodDto.getMajorId())){
                    WHERE("t.major_id = #{majorId}");
                }
                if(!StringUtil.isBlank (planGoodDto.getStationScheduleId())){
                    WHERE("t.station_schedule_id = #{stationScheduleId}");
                }
                if(!StringUtil.isBlank (planGoodDto.getPlanOverDate())){
                    WHERE("t.plan_over_date = #{planOverDate}");
                }
                if(!StringUtil.isBlank (planGoodDto.getApproverId())){
                    WHERE("t.approver_id = #{approverId}");
                }
                if(!StringUtil.isBlank (planGoodDto.getApproverName())){
                    WHERE("t.approver_name = #{approverName}");
                }
                if(!StringUtil.isBlank (planGoodDto.getApproveStatus())){
//                    WHERE("t.approve_status = #{approveStatus}");
                    WHERE("wf.approve_status = #{approveStatus}");
                }
                if(!StringUtil.isBlank (planGoodDto.getRemark())){
                    WHERE("t.remark = #{remark}");
                }
                if(!StringUtil.isBlank (planGoodDto.getCreateUserCode())){
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if(!StringUtil.isBlank (planGoodDto.getUpdateTime())){
                    WHERE("t.updateTime = #{updateTime}");
                }
                if(!StringUtil.isBlank (planGoodDto.getUpdateUserCode())){
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (planGoodDto.getUpdateUserName())){
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                if (!StringUtil.isBlank(planGoodDto.getIsRemoved())) {
                    WHERE("t.isRemoved = #{isRemoved}");
                } else {
                    WHERE("ifnull(t.isRemoved,'0') <> '1'"); //不包含删除项
                }
            }
        }.toString();
        return sql;
    }
    
    /**
     * @param orderGoodDto
     * @return sql
     * @throws
     * @name findAll
     * @author 品讯科技
     * @Description 根据条件查询数据并分页
     * @time 2021-03-15 17:07:18
     */
    public String findAllOrderGoods(final OrderGoodExcelModel  orderGoodDto) {
    	String selectOrInsertSql = "t.id as id," +
                "t.order_no as orderNo," +
                "t.supplier_id as supplierId," +
                "t.major_id as majorId," +
                "date_format(t.require_arrival_date,'%Y-%m-%d') as requireArrivalDate," +
                "t.arrival_place as arrivalPlace," +
                "date_format(t.pre_arrival_date,'%Y-%m-%d') as preArrivalDate," +
    									 /*"t.approver_id as approverId,"+
    									 "t.approver_name as approverName,"+*/
    									 "t.approve_status as approveStatus,"+
                "t.project_id as projectId," +
                "t.createTime as createTime," +
                "t.createUserCode as createUserCode," +
                "t.createUserName as createUserName," +
                "t.updateTime as updateTime," +
                "t.updateUserCode as updateUserCode," +
                "t.updateUserName as updateUserName," +
                "t.isremoved as isRemoved";
        String sql = new SQL() {
            {
                SELECT(selectOrInsertSql);
                SELECT("s.supplier_no as supplierNo,s.supplier_name as supplierName");
                SELECT("m.major_name as majorName,m.major_code as majorCode,"
                		+ "tfi.id as tfiId,tfi.task_node_id as taskNodeId ");
                FROM("b_t_order_good t");
                LEFT_OUTER_JOIN("b_t_supplier s on t.supplier_id = s.id");
                LEFT_OUTER_JOIN("b_t_major m on t.major_id = m.id");
                LEFT_OUTER_JOIN("t_workflow wf on wf.bus_id = t.id"); //审批状态
//                LEFT_OUTER_JOIN("t_flow_ins tfi on tfi.bus_id = t.id"); //审批状态
                LEFT_OUTER_JOIN(" ( select DISTINCT(bus_id) as bus_id ,task_node_id,id from  t_flow_ins where task_node_id = 4  group by bus_id ) tfi on tfi.bus_id  = wf.bus_id "); //审批状态

                //必填参数
                WHERE("t.project_id = #{projectId}");

                if (!StringUtil.isBlank(orderGoodDto.getId())) {
                    WHERE("t.id = #{id}");
                }
                if (!StringUtil.isBlank(orderGoodDto.getOrderNo())) {
                    WHERE("t.order_no like concat(concat('%',#{orderNo}),'%')");
                }
                if (!StringUtil.isBlank(orderGoodDto.getSupplierId())) {
                    WHERE("t.supplier_id = #{supplierId}");
                }
                if (!StringUtil.isBlank(orderGoodDto.getMajorId())) {
                    WHERE("t.major_id = #{majorId}");
                }
                if (!StringUtil.isBlank(orderGoodDto.getRequireArrivalDate())) {
                    WHERE("t.require_arrival_date = #{requireArrivalDate}");
                }
                if (!StringUtil.isBlank(orderGoodDto.getArrivalPlace())) {
                    WHERE("t.arrival_place = #{arrivalPlace}");
                }
                if (!StringUtil.isBlank(orderGoodDto.getPreArrivalDate())) {
                    WHERE("t.pre_arrival_date = #{preArrivalDate}");
                }
                /*if(!StringUtil.isBlank (orderGoodDto.getApproverId())){
                    WHERE("t.approver_id = #{approverId}");
                }
                if(!StringUtil.isBlank (orderGoodDto.getApproverName())){
                    WHERE("t.approver_name = #{approverName}");
                }*/
                if (!StringUtil.isBlank(orderGoodDto.getApproveStatus())) {
                    WHERE("wf.approve_status = #{approveStatus}");
                }
                if (!StringUtil.isBlank(orderGoodDto.getCreateTime())) {
                    WHERE("t.createTime = #{createTime}");
                }
                if (!StringUtil.isBlank(orderGoodDto.getCreateUserCode())) {
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if (!StringUtil.isBlank(orderGoodDto.getCreateUserName())) {
                    WHERE("t.createUserName = #{createUserName}");
                }
                if (!StringUtil.isBlank(orderGoodDto.getUpdateTime())) {
                    WHERE("t.updateTime = #{updateTime}");
                }
                if (!StringUtil.isBlank(orderGoodDto.getUpdateUserCode())) {
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if (!StringUtil.isBlank(orderGoodDto.getUpdateUserName())) {
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                if (!StringUtil.isBlank(orderGoodDto.getIsRemoved())) {
                    WHERE("t.isRemoved = #{isRemoved}");
                } else {
                    WHERE("ifnull(t.isRemoved,'0') <> '1'"); //不包含删除项
                }
            }
        }.toString();
        return sql;
    }
    public String findCheckInputAll(final CheckInputExcelModel checkInputDto) {
    	String selectOrInsertSql="t.id as id,"+
				 "t.project_id as projectId,"+
				 "t.input_no as inputNo,"+
				 "t.store_id as storeId,"+
				 "t.supplier_id as supplierId,"+
				 "t.order_good_id as orderGoodId,"+
                //总数（子表）
				 "(select ifnull(sum(num),0) from b_t_check_input_detail d where d.check_input_id = t.id and ifnull(d.isRemoved,'0')<>'1') as inputNum,"+
                /*"t.approve_status as approveStatus,"+*/
				 "t.createTime as createTime,"+
				 "t.createUserCode as createUserCode,"+
				 "t.createUserName as createUserName,"+
				 "t.updateTime as updateTime,"+
				 "t.updateUserCode as updateUserCode,"+
				 "t.updateUserName as updateUserName,"+
				 "t.major_id as majorid,"+
				 "t.file_url as fileUrl,"+
				 "t.isremoved as isRemoved";
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                SELECT("m.major_name as majorName,m.major_code as majorCode ");
                SELECT("st.store_no as storeNo,st.store_name as storeName");
                SELECT("s.supplier_no as supplierNo,s.supplier_name as supplierName"+ ",tfi.id as tfiId,tfi.task_node_id as taskNodeId"
                		+ " ");
                SELECT("o.order_no as orderNo ");

                FROM("b_t_check_input t");
                LEFT_OUTER_JOIN("b_t_store st on t.store_id = st.id and ifnull(st.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("b_t_supplier s on t.supplier_id = s.id and ifnull(s.isRemoved,'0')<>'1' and ifnull(s.status,'')='1'");
                LEFT_OUTER_JOIN("b_t_major m on t.major_id = m.id and ifnull(m.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("t_workflow wf on wf.bus_id = t.id"); //审批状态
                LEFT_OUTER_JOIN(" ( select DISTINCT(bus_id) as bus_id ,task_node_id,id from  t_flow_ins where task_node_id = 8  group by bus_id ) tfi on tfi.bus_id  = wf.bus_id "); //审批状态
                LEFT_OUTER_JOIN("b_t_order_good o on t.order_good_id = o.id and ifnull(o.isRemoved,'0')<>'1'");

                WHERE("t.project_id = #{projectId}");
                if(!StringUtil.isBlank (checkInputDto.getId())){
                    WHERE("t.id = #{id}");
                }
                if(!StringUtil.isBlank (checkInputDto.getApproveStatus())){
                    WHERE("wf.approve_status = #{approveStatus}");
                }
                if(!StringUtil.isBlank (checkInputDto.getInputNo())){
                    WHERE("t.input_no = #{inputNo}");
                }
                if(!StringUtil.isBlank (checkInputDto.getStoreId())){
                    WHERE("t.store_id = #{storeId}");
                }
                if(!StringUtil.isBlank (checkInputDto.getSupplierId())){
                    WHERE("t.supplier_id = #{supplierId}");
                }
                if(!StringUtil.isBlank (checkInputDto.getCreateUserCode())){
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if(!StringUtil.isBlank (checkInputDto.getUpdateTime())){
                    WHERE("t.updateTime = #{updateTime}");
                }
                if(!StringUtil.isBlank (checkInputDto.getUpdateUserCode())){
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (checkInputDto.getUpdateUserName())){
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                if(!StringUtil.isBlank (checkInputDto.getIsRemoved())){
                    WHERE("t.isRemoved = #{isRemoved}");
                }else{
                    WHERE("ifnull(t.isRemoved,'0') <> '1'"); //不包含删除项
                }
            }
        }.toString();
        return sql;
    }
    
    public String findPickOrderAll(final PickOrderExcelModel pickOrderDto) {
    	String selectOrInsertSql = "t.id as id," +
                "t.pick_order_no as pickOrderNo," +
                "t.project_id as projectId," +
                /* "t.store_id as storeId,"+*/
                "t.station_interval_id as stationIntervalId," +
    									/* "t.approve_status as approveStatus,"+
    									 "t.approver_user_id as approverUserId,"+
    									 "t.approver_user_name as approverUserName,"+*/
                "t.pick_company_id as pickCompanyId," +
                "t.major_id as majorId," +
                "DATE_FORMAT(t.createTime,'%Y-%m-%d %H:%i:%S') as createTime," +
                "t.createUserCode as createUserCode," +
                "t.createUserName as createUserName," +
                "DATE_FORMAT(t.updateTime,'%Y-%m-%d %H:%i:%S') as updateTime," +
                "t.updateUserCode as updateUserCode," +
                "t.updateUserName as updateUserName," +
                "t.isremoved as isRemoved";
        String sql = new SQL() {
            {
                SELECT(selectOrInsertSql);
                SELECT("m.major_code as majorCode,m.major_name as majorName"
                		+ ",tfi.id as tfiId,tfi.task_node_id as taskNodeId,pc.pick_company_name as pickCompanyName"
                		+ " ");
                /* SELECT("s.store_no as storeNo,s.store_name as storeName");*/
                SELECT("si.station_interval_code as stationIntervalCode,si.station_interval_name as stationIntervalName");

                FROM("b_t_pick_order t");
                /*LEFT_OUTER_JOIN("b_t_store s on t.store_id = s.id and ifnull(s.isRemoved,'0')<>'1'");*/
                LEFT_OUTER_JOIN("b_t_station_interval si on t.station_interval_id = si.id and ifnull(si.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("t_workflow wf on wf.bus_id = t.id"); //审批状态
                LEFT_OUTER_JOIN("b_t_major m on m.id = t.major_id and ifnull(m.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN(" ( select DISTINCT(bus_id) as bus_id ,task_node_id,id from  t_flow_ins where task_node_id = 6  group by bus_id ) tfi on tfi.bus_id  = wf.bus_id "); //审批状态
                LEFT_OUTER_JOIN("b_t_pick_company pc on pc.id = t.pick_company_id and ifnull(pc.isRemoved,'0')<>'1'");

                WHERE("t.project_id = #{projectId}");
                if (!StringUtil.isBlank(pickOrderDto.getId())) {
                    WHERE("t.id = #{id}");
                }
                if (!StringUtil.isBlank(pickOrderDto.getPickOrderNo())) {
                    WHERE("t.pick_order_no like concat(concat('%',#{pickOrderNo}),'%')");
                }
                /*if(!StringUtil.isBlank (pickOrderDto.getStoreId())){
                    WHERE("t.store_id = #{storeId}");
                }*/
                if (!StringUtil.isBlank(pickOrderDto.getStationIntervalId())) {
                    WHERE("t.station_interval_id = #{stationIntervalId}");
                }
                if (!StringUtil.isBlank(pickOrderDto.getMajorId())) {
                    WHERE("t.major_id = #{majorId}");
                }
                if (!StringUtil.isBlank(pickOrderDto.getApproveStatus())) {
                    WHERE("wf.approve_status = #{approveStatus}");
                }
                if (!StringUtil.isBlank(pickOrderDto.getCreateUserCode())) {
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if (!StringUtil.isBlank(pickOrderDto.getCreateUserName())) {
                    WHERE("t.createUserName like concat(concat('%',#{createUserName}),'%')");
                }
                if (!StringUtil.isBlank(pickOrderDto.getUpdateTime())) {
                    WHERE("t.updateTime = #{updateTime}");
                }
                if (!StringUtil.isBlank(pickOrderDto.getUpdateUserCode())) {
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if (!StringUtil.isBlank(pickOrderDto.getUpdateUserName())) {
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                if (!StringUtil.isBlank(pickOrderDto.getIsRemoved())) {
                    WHERE("t.isremoved = #{isRemoved}");
                } else {
                    WHERE("ifnull(t.isRemoved,'0') <> '1'"); //不包含删除项
                }
            }
        }.toString();
        return sql;
    
    	
    }
}
