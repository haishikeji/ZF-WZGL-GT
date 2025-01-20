package com.px.project.web.dao.provider;

import com.px.project.common.base.BaseProvider;
import com.px.project.common.utils.StringUtil;
import com.px.project.web.dto.PlanGoodDto;
import com.px.project.web.entity.PlanGoodModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Package com.px.project.web.dao.provider
 * @ClassName PlanGoodProvider
 * @Description PlanGoodProvider
 * @author 品讯科技
 * @time   2023-09-13 19:47:25
 * @version V 1.0
 */
public class PlanGoodProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql="t.id as id,"+
									 "t.plan_no as planNo,"+
									 "t.project_id as projectId,"+
									 "t.supplier_id as supplierId,"+
									 "t.major_id as majorId,"+
									 "t.station_interval_id as stationIntervalId,"+
									 "t.plan_over_date as planOverDate,"+
									 "t.approver_id as approverId,"+
									 "t.approver_name as approverName,"+
									 "t.approve_status as approveStatus,"+
									 "t.remark as remark,"+
									 "t.place as place,"+
									 "t.hand_no as handNo,"+
									 "t.createTime as createTime,"+
									 "t.createUserCode as createUserCode,"+
									 "t.createUserName as createUserName,"+
									 "t.updateTime as updateTime,"+
									 "t.updateUserCode as updateUserCode,"+
									 "t.updateUserName as updateUserName,"+
									 "t.isremoved as isRemoved";
    /**
     * @name save
     * @author 品讯科技
     * @param planGoodModel
     * @Description 增加
     * @time 2023-09-13 19:47:25
     * @throws
     * @return sql
     */
    public String save(final PlanGoodModel planGoodModel) {
        String sql = new SQL () {
            {
                INSERT_INTO ("b_t_plan_goods ");
                VALUES("id", "#{id}");
                if(!StringUtil.isBlank (planGoodModel.getPlanNo())){
                    VALUES("plan_no", "#{planNo}");
                }
                if(!StringUtil.isBlank (planGoodModel.getPlace())){
                	VALUES("place", "#{place}");
                }
                if(!StringUtil.isBlank (planGoodModel.getHandNo())){
                	VALUES("hand_no", "#{handNo}");
                }
                if(!StringUtil.isBlank (planGoodModel.getProjectId())){
                    VALUES("project_id", "#{projectId}");
                }
                if(!StringUtil.isBlank (planGoodModel.getSupplierId())){
                    VALUES("supplier_id", "#{supplierId}");
                }
                if(!StringUtil.isBlank (planGoodModel.getMajorId())){
                    VALUES("major_id", "#{majorId}");
                }
                if(!StringUtil.isBlank (planGoodModel.getStationIntervalId())){
                    VALUES("station_interval_id", "#{stationIntervalId}");
                }
                if(!StringUtil.isBlank (planGoodModel.getPlanOverDate())){
                    VALUES("plan_over_date", "#{planOverDate}");
                }
                if(!StringUtil.isBlank (planGoodModel.getApproverId())){
                    VALUES("approver_id", "#{approverId}");
                }
                if(!StringUtil.isBlank (planGoodModel.getApproverName())){
                    VALUES("approver_name", "#{approverName}");
                }
                if(!StringUtil.isBlank (planGoodModel.getApproveStatus())){
                    VALUES("approve_status", "#{approveStatus}");
                }
                if(!StringUtil.isBlank (planGoodModel.getRemark())){
                    VALUES("remark", "#{remark}");
                }
                if(!StringUtil.isBlank (planGoodModel.getCreateTime())){
                    VALUES("createTime", "#{createTime}");
                }
                if(!StringUtil.isBlank (planGoodModel.getCreateUserCode())){
                    VALUES("createUserCode", "#{createUserCode}");
                }
                if(!StringUtil.isBlank (planGoodModel.getCreateUserName())){
                    VALUES("createUserName", "#{createUserName}");
                }
                if(!StringUtil.isBlank (planGoodModel.getUpdateTime())){
                    VALUES("updateTime", "#{updateTime}");
                }
                if(!StringUtil.isBlank (planGoodModel.getUpdateUserCode())){
                    VALUES("updateUserCode", "#{updateUserCode}");
                }
                if(!StringUtil.isBlank (planGoodModel.getUpdateUserName())){
                    VALUES("updateUserName", "#{updateUserName}");
                }
                if (!StringUtil.isBlank(planGoodModel.getIsRemoved())) {
                    VALUES("isremoved", "#{isRemoved}");
                } else {
                    VALUES("isremoved", "0");
                }
            }
        }.toString ();
        return sql;
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids
     * @Description 物理删除
     * @time 2023-09-13 19:47:25
     * @throws
     * @return sql
     */
    public String deleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                DELETE_FROM("b_t_plan_goods");
            }
        }.toString();
        sql += batchJoinField("id",ids);
        return sql;
    }
    
    public String findByIds(@Param ("ids") String ids) {
    	String sql = new SQL () {
    		{
    			SELECT(selectOrInsertSql);
    			FROM("b_t_plan_goods t");
    		}
    	}.toString();
    	sql += batchJoinField("id",ids);
    	return sql;
    }


    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param ids
     * @Description 逻辑删除
     * @time 2023-09-13 19:47:25
     * @throws
     * @return sql
     */
    public String logicDeleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                UPDATE("b_t_plan_goods");
                SET("isRemoved = '1'");
            }
        }.toString();
        sql += batchJoinField("id",ids);
        return sql;
    }

    /**
     * @name update
     * @author 品讯科技
     * @param planGoodModel
     * @Description 修改
     * @time 2023-09-13 19:47:25
     * @throws
     * @return sql
     */
    public String update(final PlanGoodModel planGoodModel) {
        String sql = new SQL () {
            {
                UPDATE("b_t_plan_goods ");
                if(planGoodModel.getPlanNo() != null){
                    SET("plan_no = #{planNo}");
                }
                if(planGoodModel.getHandNo() != null){
                	SET("hand_no = #{handNo}");
                }
                if(planGoodModel.getPlace() != null){
                	SET("place = #{place}");
                }
                if(planGoodModel.getProjectId() != null){
                    SET("project_id = #{projectId}");
                }
                if(planGoodModel.getSupplierId() != null){
                    SET("supplier_id = #{supplierId}");
                }
                if(planGoodModel.getMajorId() != null){
                    SET("major_id = #{majorId}");
                }
                if(planGoodModel.getStationIntervalId() != null){
                    SET("station_interval_id = #{stationIntervalId}");
                }
                if(planGoodModel.getPlanOverDate() != null){
                    SET("plan_over_date = #{planOverDate}");
                }
                if(planGoodModel.getApproverId() != null){
                    SET("approver_id = #{approverId}");
                }
                if(planGoodModel.getApproverName() != null){
                    SET("approver_name = #{approverName}");
                }
                if(planGoodModel.getApproveStatus() != null){
                    SET("approve_status = #{approveStatus}");
                }
                if(planGoodModel.getRemark() != null){
                    SET("remark = #{remark}");
                }
                if(planGoodModel.getCreateTime() != null){
                    SET("createTime = #{createTime}");
                }
                if(planGoodModel.getCreateUserCode() != null){
                    SET("createUserCode = #{createUserCode}");
                }
                if(planGoodModel.getCreateUserName() != null){
                    SET("createUserName = #{createUserName}");
                }
                if(planGoodModel.getUpdateTime() != null){
                    SET("updateTime = #{updateTime}");
                }
                if(planGoodModel.getUpdateUserCode() != null){
                    SET("updateUserCode = #{updateUserCode}");
                }
                if(planGoodModel.getUpdateUserName() != null){
                    SET("updateUserName = #{updateUserName}");
                }
                if(planGoodModel.getIsRemoved() != null){
                    SET("isremoved = #{isRemoved}");
                }
                WHERE("id = #{id}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findById
     * @author 品讯科技
     * @param id
     * @Description 根据Id查询数据
     * @time 2023-09-13 19:47:25
     * @throws
     * @return sql
     */
    public String findById(@Param ("id") String id) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                SELECT("m.major_name as majorName,m.major_code as majorCode ");
                SELECT("s.supplier_no as supplierNo,s.supplier_name as supplierName,s.mobile as mobile");
                SELECT("si.station_interval_code as stationIntervalCode,si.station_interval_name as stationIntervalName,sp.project_name as projectName");
                FROM("b_t_plan_goods t");
                LEFT_OUTER_JOIN("b_t_supplier s on t.supplier_id = s.id and ifnull(s.isRemoved,'0')<>'1' and ifnull(s.status,'')='1'");
                LEFT_OUTER_JOIN("b_t_major m on t.major_id = m.id and ifnull(m.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("b_t_station_interval si on t.station_interval_id = si.id and ifnull(si.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("s_t_project  sp on t.project_id = sp.id and ifnull(sp.isRemoved,'0')<>'1'");
                WHERE("t.id = #{id}");
            }
        }.toString();
        return sql;
    }
    public String findModelById(@Param ("id") String id) {
    	String sql = new SQL () {
    		{
    			SELECT(selectOrInsertSql);
    			SELECT("m.major_name as majorName,m.major_code as majorCode ");
    			SELECT("s.supplier_no as supplierNo,s.supplier_name as supplierName,s.mobile as mobile");
    			FROM("b_t_plan_goods t");
    			LEFT_OUTER_JOIN("b_t_supplier s on t.supplier_id = s.id and ifnull(s.isRemoved,'0')<>'1' and ifnull(s.status,'')='1'");
    			LEFT_OUTER_JOIN("b_t_major m on t.major_id = m.id and ifnull(m.isRemoved,'0')<>'1'");
    			WHERE("t.id = #{id}");
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
    public String findAll(final PlanGoodDto planGoodDto) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                SELECT("s.supplier_no as supplierNo,s.supplier_name as supplierName");
                SELECT("m.major_name as majorName,m.major_code as majorCode");
                SELECT("ss.station_interval_name as stationIntervalName"
                		+ ",tfi.id as tfiId,tfi.task_node_id as taskNodeId,ogd.count,ogd.balanceCount"
                		+ " ");
                FROM("b_t_plan_goods t");
                LEFT_OUTER_JOIN("b_t_supplier s on t.supplier_id = s.id");
                LEFT_OUTER_JOIN("(select order_good_id ,sum(num) as count,sum(plan_balance_num) as balanceCount from b_t_order_good_detail group by order_good_id ) ogd on ogd.order_good_id = t.id");
                LEFT_OUTER_JOIN("b_t_major m on t.major_id = m.id");
                LEFT_OUTER_JOIN("b_t_station_interval ss on t.station_interval_id = ss.id");
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
                if(!StringUtil.isBlank (planGoodDto.getStationIntervalId())){
                    WHERE("t.station_interval_id = #{stationIntervalId}");
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
                if(!StringUtil.isBlank (planGoodDto.getCreateTime())){
                    WHERE("t.createTime = #{createTime}");
                }
                if(!StringUtil.isBlank (planGoodDto.getCreateUserCode())){
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if(!StringUtil.isBlank (planGoodDto.getCreateUserName())){
                    WHERE("t.createUserName = #{createUserName}");
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
                if(!StringUtil.isBlank (planGoodDto.getHandNo())){
                    WHERE("t.hand_no like concat(concat('%',#{handNo}),'%') ");
                }
                if (!StringUtil.isBlank(planGoodDto.getIsRemoved())) {
                    WHERE("t.isRemoved = #{isRemoved}");
                } else {
                    WHERE("ifnull(t.isRemoved,'0') <> '1'"); //不包含删除项
                }
            }
        }.toString();
        sql +=" order by t.createTime desc, t.updateTime desc ";
        return sql;
    }
    /**
     * @name findAllNoOverPlan
     * @author 品讯科技
     * @param planGoodDto
     * @Description 根据条件查询数据并分页
     * @time 2023-09-13 19:47:25
     * @throws
     * @return sql
     */
    public String findAllNoOverPlan(final PlanGoodDto planGoodDto) {
    	selectOrInsertSql="t.id as id,"+
				 "t.plan_no as planNo,"+
				 "t.hand_no as planHandNo,"+
				 "t.project_id as projectId,"+
				 "t.supplier_id as supplierId,"+
				 "t.major_id as majorId,"+
//				 "t.supplier_name as supplierName,"+
				 "t.station_interval_id as stationIntervalId,"+
				 "t.plan_over_date as planOverDate,"+
				 "t.approver_id as approverId,"+
				 "t.approver_name as approverName,"+
				 "t.approve_status as approveStatus,"+
				 "t.remark as remark,"+
				 "t.place as place,"+
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
//    			SELECT("s.supplier_no as supplierNo,s.supplier_name as supplierName");
    			SELECT("m.major_name as majorName,m.major_code as majorCode");
    			SELECT("ss.station_interval_name as stationIntervalName"
    					+ ",tfi.id as tfiId,tfi.task_node_id as taskNodeId"
    					+ " ");
    			FROM("b_t_plan_goods t");
    			LEFT_OUTER_JOIN("b_t_supplier s on t.supplier_id = s.id");
    			LEFT_OUTER_JOIN("b_t_major m on t.major_id = m.id");
    			LEFT_OUTER_JOIN("b_t_station_interval ss on t.station_interval_id = ss.id");
    			LEFT_OUTER_JOIN("t_workflow wf on wf.bus_id = t.id"); //审批状态
    			LEFT_OUTER_JOIN(" ( select DISTINCT(bus_id) as bus_id ,task_node_id,id from  t_flow_ins where task_node_id = 17  group by bus_id ) tfi on tfi.bus_id  = wf.bus_id "); //审批状态
    			WHERE("t.project_id = #{projectId}");
    			if(!StringUtil.isBlank (planGoodDto.getApproveStatus())){
//                    WHERE("t.approve_status = #{approveStatus}");
    				WHERE("wf.approve_status = #{approveStatus}");
    			}
    			WHERE("t.id in (select order_good_id from b_t_order_good_detail where plan_status = 0 ) ");
    			if (!StringUtil.isBlank(planGoodDto.getIsRemoved())) {
    				WHERE("t.isRemoved = #{isRemoved}");
    			} else {
    				WHERE("ifnull(t.isRemoved,'0') <> '1'"); //不包含删除项
    			}
    		}
    	}.toString();
    	return sql;
    }
    
    public String findAllNoOver() {
    	selectOrInsertSql="t.id as id,"+
				 "t.plan_no as planNo,"+
				 "t.project_id as projectId,"+
				 "t.supplier_id as supplierId,"+
				 "t.major_id as majorId,"+
				 "t.station_interval_id as stationIntervalId,"+
				 "t.plan_over_date as planOverDate,"+
				 "t.approver_id as approverId,"+
				 "t.approver_name as approverName,"+
				 "t.approve_status as approveStatus,"+
				 "t.remark as remark,"+
				 "t.place as place,"+
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
    			LEFT_OUTER_JOIN("b_t_station_interval ss on t.station_interval_id = ss.id");
    			LEFT_OUTER_JOIN("t_workflow wf on wf.bus_id = t.id"); //审批状态
    			LEFT_OUTER_JOIN(" ( select DISTINCT(bus_id) as bus_id ,task_node_id,id from  t_flow_ins where task_node_id = 17  group by bus_id ) tfi on tfi.bus_id  = wf.bus_id "); //审批状态
    			WHERE("t.project_id = #{projectId}");
    			WHERE("wf.approve_status = 3");
    			WHERE("t.id in (select order_good_id from b_t_order_good_detail where plan_status = 0 ) ");
    		}
    	}.toString();
    	return sql;
    }

}
