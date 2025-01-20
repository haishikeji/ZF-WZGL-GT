package com.px.project.web.dao.provider;

import com.px.project.common.base.BaseProvider;
import com.px.project.common.utils.StringUtil;
import com.px.project.web.dto.WorkflowDto;
import com.px.project.web.entity.WorkflowModel;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Package com.px.project.web.dao.provider
 * @ClassName WorkflowProvider
 * @Description WorkflowProvider
 * @author 品讯科技
 * @time 2021-03-12 09:36:18
 * @version V 1.0
 */
public class WorkflowProvider extends BaseProvider {

	// sql，selectOrInsert部分
	private String selectOrInsertSql = "t.id as id," + "t.bus_id as busId," + "t.task_node_id as taskNodeId,"
			 + "t.bus_no as busNo,"
			 + "t.major_id as majorId,"
			+ "t.task_node_name as taskNodeName," + "t.approver_user_id as approverUserId,"
			+ "t.approver_user_name as approverUserName," + "t.approver_post_id as approverPostId,"
			+ "t.approver_post_name as approverPostName,"
			+ "DATE_FORMAT(t.apply_time,'%Y-%m-%d %H:%i:%S') as applyTime," + "t.apply_user_id as applyUserId,"
			+ "t.apply_user_name as applyUserName," + "t.approve_status as approveStatus," + "t.remarks as remarks,"
			+ "t.previous_operator_id as previousOperatorId,"
			+ "DATE_FORMAT(t.approve_time,'%Y-%m-%d %H:%i:%S') as approveTime,"
			+ "t.previous_operator_name as previousOperatorName,"
			+ "DATE_FORMAT(t.createTime,'%Y-%m-%d %H:%i:%S') as createTime," + "t.createUserCode as createUserCode,"
			+ "t.createUserName as createUserName";

	/**
	 * @name save
	 * @author 品讯科技
	 * @param workflowModel
	 * @Description 增加
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String save(final WorkflowModel workflowModel) {
		String sql = new SQL() {
			{
				INSERT_INTO("t_workflow ");
				VALUES("id", "#{id}");
				if (!StringUtil.isBlank(workflowModel.getBusId())) {
					VALUES("bus_id", "#{busId}");
				}
				if (!StringUtil.isBlank(workflowModel.getTaskNodeId())) {
					VALUES("task_node_id", "#{taskNodeId}");
				}
				if (!StringUtil.isBlank(workflowModel.getTaskNodeName())) {
					VALUES("task_node_name", "#{taskNodeName}");
				}
				if (!StringUtil.isBlank(workflowModel.getApproverUserId())) {
					VALUES("approver_user_id", "#{approverUserId}");
				}
				if (!StringUtil.isBlank(workflowModel.getApproverUserName())) {
					VALUES("approver_user_name", "#{approverUserName}");
				}
				if (!StringUtil.isBlank(workflowModel.getApproverPostId())) {
					VALUES("approver_post_id", "#{approverPostId}");
				}
				if (!StringUtil.isBlank(workflowModel.getApplyTime())) {
					VALUES("approve_time", "#{approveTime}");
				}
				if (!StringUtil.isBlank(workflowModel.getApproverPostName())) {
					VALUES("approver_post_name", "#{approverPostName}");
				}
				if (!StringUtil.isBlank(workflowModel.getApplyUserId())) {
					VALUES("apply_user_id", "#{applyUserId}");
				}
				if (!StringUtil.isBlank(workflowModel.getApplyUserName())) {
					VALUES("apply_user_name", "#{applyUserName}");
				}
				if (!StringUtil.isBlank(workflowModel.getApproveStatus())) {
					VALUES("approve_status", "#{approveStatus}");
				}
				if (!StringUtil.isBlank(workflowModel.getRemarks())) {
					VALUES("remarks", "#{remarks}");
				}
				if (!StringUtil.isBlank(workflowModel.getPreviousOperatorId())) {
					VALUES("previous_operator_id", "#{previousOperatorId}");
				}
				if (!StringUtil.isBlank(workflowModel.getPreviousOperatorName())) {
					VALUES("previous_operator_name", "#{previousOperatorName}");
				}
				if (!StringUtil.isBlank(workflowModel.getApplyTime())) {
					VALUES("apply_time", "#{applyTime}");
				}
				if (!StringUtil.isBlank(workflowModel.getCreateTime())) {
					VALUES("createTime", "#{createTime}");
				}
				if (!StringUtil.isBlank(workflowModel.getCreateUserCode())) {
					VALUES("createUserCode", "#{createUserCode}");
				}
				if (!StringUtil.isBlank(workflowModel.getCreateUserName())) {
					VALUES("createUserName", "#{createUserName}");
				}
				if (!StringUtil.isBlank(workflowModel.getProjectId())) {
					VALUES("project_id", "#{projectId}");
				}
				if (!StringUtil.isBlank(workflowModel.getBusNo())) {
					VALUES("bus_no", "#{busNo}");
				}
				if (!StringUtil.isBlank(workflowModel.getMajorId())) {
					VALUES("major_id", "#{majorId}");
				}
			}
		}.toString();
		return sql;
	}

	/**
	 * @name deleteByIds
	 * @author 品讯科技
	 * @param ids
	 * @Description 物理删除
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String deleteByIds(@Param("ids") String ids) {
		String sql = new SQL() {
			{
				DELETE_FROM("t_workflow");
			}
		}.toString();
		sql += batchJoinField("id", ids);
		return sql;
	}

	public String deleteByBusIds(@Param("ids") String ids) {
		String sql = new SQL() {
			{
				DELETE_FROM("t_workflow");
			}
		}.toString();
		sql += batchJoinField("bus_id", ids);
		return sql;
	}
	public String deleteFlowInsByBusIds(@Param("ids") String ids) {
		String sql = new SQL() {
			{
				DELETE_FROM("t_flow_ins");
			}
		}.toString();
		sql += batchJoinField("bus_id", ids);
		return sql;
	}

	/**
	 * @name logicDeleteByIds
	 * @author 品讯科技
	 * @param ids
	 * @Description 逻辑删除
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String logicDeleteByIds(@Param("ids") String ids) {
		String sql = new SQL() {
			{
				UPDATE("t_workflow");
				SET("isRemoved = '1'");
			}
		}.toString();
		sql += batchJoinField("id", ids);
		return sql;
	}

	/**
	 * @name update
	 * @author 品讯科技
	 * @param workflowModel
	 * @Description 修改
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String update(final WorkflowModel workflowModel) {
		String sql = new SQL() {
			{
				UPDATE("t_workflow ");
				if (workflowModel.getApproveStatus() != null) {
					SET("approver_post_id = #{approverPostId}");
				}
				if (workflowModel.getApproveStatus() != null) {
					SET("approver_post_name = #{approverPostName}");
				}
				if (workflowModel.getApproveTime() != null) {
					SET("approve_time = #{approveTime}");
				}
				if (workflowModel.getApproveStatus() != null) {
					SET("approve_status = #{approveStatus}");
				}
				if (workflowModel.getPreviousOperatorId() != null) {
					SET("previous_operator_id = #{previousOperatorId}");
				}
				if (workflowModel.getPreviousOperatorName() != null) {
					SET("previous_operator_name = #{previousOperatorName}");
				}


				if (workflowModel.getBusNo() != null) {
					SET("bus_no = #{busNo}");
				}
				if (workflowModel.getMajorId() != null) {
					SET("major_id = #{majorId}");
				}
				WHERE("id = #{id}");
			}
		}.toString();
		System.out.println(sql);
		return sql;
	}

	/**
	 * @name findById
	 * @author 品讯科技
	 * @param id
	 * @Description 根据Id查询数据
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String findById(@Param("id") String id) {
		String sql = new SQL() {
			{
				SELECT(selectOrInsertSql);
				FROM("t_workflow t");
				WHERE("t.id = #{id}");
			}
		}.toString();
		return sql;
	}

	/**
	 * @name findAll
	 * @author 品讯科技
	 * @param workflowDto
	 * @Description 根据条件查询数据并分页
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String findAll(final WorkflowDto workflowDto) {
		String sql = new SQL() {
			{
				SELECT(selectOrInsertSql);
				FROM("t_workflow t");
				if (!StringUtil.isBlank(workflowDto.getId())) {
					WHERE("t.id = #{id}");
				}
				if (!StringUtil.isBlank(workflowDto.getBusId())) {
					WHERE("t.bus_id = #{busId}");
				}
				if (!StringUtil.isBlank(workflowDto.getTaskNodeId())) {
					WHERE("t.task_node_id = #{taskNodeId}");
				}
				if (!StringUtil.isBlank(workflowDto.getTaskNodeName())) {
					WHERE("t.task_node_name = #{taskNodeName}");
				}
				if (!StringUtil.isBlank(workflowDto.getApproverUserId())) {
					WHERE("t.approver_user_id = #{approverUserId}");
				}
				if (!StringUtil.isBlank(workflowDto.getApproverUserName())) {
					WHERE("t.approver_user_name = #{approverUserName}");
				}
				if (!StringUtil.isBlank(workflowDto.getApproverPostId())) {
					WHERE("t.approver_post_id = #{approverPostId}");
				}
				if (!StringUtil.isBlank(workflowDto.getApproverPostName())) {
					WHERE("t.approver_post_name = #{approverPostName}");
				}
				if (!StringUtil.isBlank(workflowDto.getApplyUserId())) {
					WHERE("t.apply_user_id = #{applyUserId}");
				}
				if (!StringUtil.isBlank(workflowDto.getApplyUserName())) {
					WHERE("t.apply_user_name = #{applyUserName}");
				}
				if (!StringUtil.isBlank(workflowDto.getApproveStatus())) {
					WHERE("t.approve_status = #{approveStatus}");
				}
				if (!StringUtil.isBlank(workflowDto.getRemarks())) {
					WHERE("t.remarks = #{remarks}");
				}
				if (!StringUtil.isBlank(workflowDto.getPreviousOperatorId())) {
					WHERE("t.previous_operator_id = #{previousOperatorId}");
				}
				if (!StringUtil.isBlank(workflowDto.getPreviousOperatorName())) {
					WHERE("t.previous_operator_name = #{previousOperatorName}");
				}
				if (!StringUtil.isBlank(workflowDto.getApproveTime())) {
					WHERE("t.approve_time = #{approveTime}");
				}
				if (!StringUtil.isBlank(workflowDto.getCreateTime())) {
					WHERE("t.createTime = #{createTime}");
				}
				if (!StringUtil.isBlank(workflowDto.getCreateUserCode())) {
					WHERE("t.createUserCode = #{createUserCode}");
				}
				if (!StringUtil.isBlank(workflowDto.getCreateUserName())) {
					WHERE("t.createUserName = #{createUserName}");
				}
			}
		}.toString();
		return sql;
	}

	/**
	 * @name findFirstApprovePost
	 * @author 品讯科技
	 * @param taskNodeId
	 * @Description 根据工作流id查询，第一个审核岗位
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String findFirstApprovePost(@Param("taskNodeId") String taskNodeId) {
		return "SELECT p.post_id as postId,po.postname as postName  FROM `t_task_post` p LEFT JOIN s_t_post po on po.id=p.post_id  where p.task_node_id=#{taskNodeId} order by p.approve_serial LIMIT 1";
	}
	public String findAllApprovePost(@Param("taskNodeId") String taskNodeId) {
		return "SELECT p.post_id as postId,po.postname as postName  FROM `t_task_post` p LEFT JOIN s_t_post po on po.id=p.post_id  where p.task_node_id=#{taskNodeId} ";
	}

	/**
	 * @name findTaskNodeById
	 * @author 品讯科技
	 * @param id
	 * @Description findTaskNodeById 根据流程id，查询流程节点数
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String findTaskNodeById(@Param("id") String id) {
		return "select task_node_num as num from t_task_node where id=#{id}";
	}

	/**
	 * @name findApprovePostBySerial
	 * @author 品讯科技
	 * @param serialNo
	 * @Description 根据工作流节点序号查询审核岗位
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String findApprovePostBySerial(@Param("serialNo") String serialNo, @Param("taskNodeId") String taskNodeId) {
		return "SELECT p.post_id AS postId, po.postname AS postName FROM `t_task_post` p LEFT JOIN s_t_post po ON po.id = p.post_id WHERE p.approve_serial=#{serialNo} and p.task_node_id=#{taskNodeId}";
	}

	/**
	 * @name myApply
	 * @author 品讯科技
	 * @param userId
	 * @param beginTime
	 * @param endTime
	 * @param taskNodeName
	 * @Description 工作流【我的申请】
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String myApply(@Param("userId") String userId, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("taskNodeName") String taskNodeName,
			@Param("projectId") String projectId) {
		String sql = "select " + selectOrInsertSql + " from t_workflow t where t.apply_user_id=#{userId}";
		if (!StringUtil.isBlank(beginTime) && StringUtil.isBlank(endTime)) {
			sql += " and t.apply_time>#{beginTime}";
		}
		if (!StringUtil.isBlank(endTime) && StringUtil.isBlank(beginTime)) {
			sql += " and t.apply_time<#{endTime}";
		}
		if (!StringUtil.isBlank(endTime) && !StringUtil.isBlank(beginTime)) {
			sql += " and t.apply_time BETWEEN  #{beginTime} and #{endTime}";
		}
		if (!StringUtil.isBlank(taskNodeName)) {
			sql += " and t.task_node_name like concat(concat('%',#{taskNodeName}),'%')";
		}
		if (!StringUtil.isBlank(projectId)) {
			sql += " and t.project_id  = #{projectId} ";
		}
		sql += " order by t.apply_time desc";
		return sql;
	}

	/**
	 * @name myApprove
	 * @author 品讯科技
	 * @param postId
	 * @param beginTime
	 * @param endTime
	 * @param taskNodeName
	 * @Description 工作流【我的审核】
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String myApprove(@Param("postId") String postId, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("taskNodeName") String taskNodeName,
			@Param("projectId") String projectId,@Param("userId") String userId) {
		String sql = "select " + selectOrInsertSql
				+ " from t_workflow t where t.approver_post_id=#{postId} and t.approve_status <> '3' ";

		if (!StringUtil.isBlank(beginTime) && StringUtil.isBlank(endTime)) {
			sql += " and t.apply_time>#{beginTime}";
		}
		if (!StringUtil.isBlank(endTime) && StringUtil.isBlank(beginTime)) {
			sql += " and t.apply_time<#{endTime}";
		}
		if (!StringUtil.isBlank(endTime) && !StringUtil.isBlank(beginTime)) {
			sql += " and t.apply_time BETWEEN  #{beginTime} and #{endTime}";
		}
		if (!StringUtil.isBlank(taskNodeName)) {
			sql += " and t.task_node_name like concat(concat('%',#{taskNodeName}),'%')";
		}
		if (!StringUtil.isBlank(projectId)) {
			sql += " and t.project_id  = #{projectId} ";
		}
		if(!StringUtil.isBlank(userId)) {
			sql += " and t.major_id  in (select majorid from s_t_majoruser where userid = #{userId}) ";
		}
		sql += " order by t.apply_time desc";
		return sql;
	}

	/**
	 * @name goByMyApprove
	 * @author 品讯科技
	 * @param userId
	 * @param beginTime
	 * @param endTime
	 * @param taskNodeName
	 * @Description 工作流【经我审核】
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String goByMyApprove(@Param("userId") String userId, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("taskNodeName") String taskNodeName,
			@Param("projectId") String projectId) {
		String sql = "select " + selectOrInsertSql
				+ "  from t_flow_ins i LEFT JOIN t_workflow t on i.task_id=t.id where t.previous_operator_id=#{userId} ";
		if (!StringUtil.isBlank(beginTime) && StringUtil.isBlank(endTime)) {
			sql += " and t.apply_time>#{beginTime}";
		}
		if (!StringUtil.isBlank(endTime) && StringUtil.isBlank(beginTime)) {
			sql += " and t.apply_time<#{endTime}";
		}
		if (!StringUtil.isBlank(endTime) && !StringUtil.isBlank(beginTime)) {
			sql += " and t.apply_time BETWEEN  #{beginTime} and #{endTime}";
		}
		if (!StringUtil.isBlank(taskNodeName)) {
			sql += " and t.task_node_name like concat(concat('%',#{taskNodeName}),'%')";
		}
		if (!StringUtil.isBlank(projectId)) {
			sql += " and t.project_id  = #{projectId} ";
		}
		sql += " GROUP BY t.id";
		sql += " order by t.apply_time desc";
		return sql;
	}

	/**
	 * @name approveComplete
	 * @author 品讯科技
	 * @param userId
	 * @param beginTime
	 * @param endTime
	 * @param taskNodeName
	 * @Description 工作流【审核完成】，经过我审核的，我申请的
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String approveComplete(@Param("userId") String userId, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("taskNodeName") String taskNodeName,
			@Param("projectId") String projectId) {
		String sql = "select " + selectOrInsertSql
				+ "  from t_flow_ins i LEFT JOIN t_workflow t on i.task_id=t.id where t.approve_status='3' and  (i.approver_user_id=#{userId} or t.apply_user_id=#{userId}) ";
		if (!StringUtil.isBlank(beginTime) && StringUtil.isBlank(endTime)) {
			sql += " and t.apply_time>#{beginTime}";
		}
		if (!StringUtil.isBlank(endTime) && StringUtil.isBlank(beginTime)) {
			sql += " and t.apply_time<#{endTime}";
		}
		if (!StringUtil.isBlank(endTime) && !StringUtil.isBlank(beginTime)) {
			sql += " and t.apply_time BETWEEN  #{beginTime} and #{endTime}";
		}
		if (!StringUtil.isBlank(taskNodeName)) {
			sql += " and t.task_node_name like concat(concat('%',#{taskNodeName}),'%')";
		}
		if (!StringUtil.isBlank(projectId)) {
			sql += " and t.project_id  = #{projectId} ";
		}
		sql += " GROUP BY t.id";
		sql += " order by t.apply_time desc";
		return sql;
	}

	/**
	 * @name findNode
	 * @author 品讯科技
	 * @param taskNodeId
	 * @Description 流程节点查询
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String findNode(@Param("taskNodeId") String taskNodeId) {
		String sql = "SELECT tp.approve_serial as approveSerial,p.postname as postName FROM `t_task_post` tp LEFT JOIN s_t_post p on tp.post_id=p.id where tp.task_node_id=#{taskNodeId} ";
		sql += " GROUP BY tp.approve_serial";
		return sql;
	}

	/**
	 * @name findWorkflowByBusId
	 * @author 品讯科技
	 * @param busId
	 * @Description 查询工作流信息根据业务id
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String findWorkflowByBusId(@Param("busId") String busId) {
		String sql = "select " + selectOrInsertSql
				+ ",(SELECT f.serial_no as node from t_flow_ins f where f.bus_id =#{busId} and f.approve_status='0') as node from t_workflow t where t.bus_id=#{busId} ";
		return sql;
	}

	/**
	 * @name findWorkflowByBusIdForThreeEstimate
	 * @author 品讯科技
	 * @param busId
	 * @Description 三量审核状态查询，当approve_status<>99时，查不出数据，当前工作流未审核，当如果能查询出数据，按查询出来的状态暂时
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String findWorkflowByBusIdForThreeEstimate(@Param("busId") String busId) {
		String sql = "select " + selectOrInsertSql
				+ " from t_workflow t where t.bus_id=#{busId} and t.approve_status<>'99'";
		return sql;
	}

	/**
	 * @name findWorkflowByBusIdNormal
	 * @author 品讯科技
	 * @param busId
	 * @Description 查看正常工作流
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String findWorkflowByBusIdNormal(@Param("busId") String busId) {
		String sql = "select " + selectOrInsertSql
				+ " from t_workflow t where t.bus_id=#{busId} and t.approve_status<>'99'";
		return sql;
	}

	/**
	 * @name todoCount
	 * @author 品讯科技
	 * @param userId
	 * @Description 根据用户id查询工作流待办
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String todoList(@Param("userId") String userId) {
		String sql = "SELECT n.id, n.task_name AS taskNodeName, (SELECT count(1) FROM t_workflow w WHERE w.task_node_id = n.id AND (w.approver_user_id = #{userId} OR w.apply_user_id = #{userId}) and w.approve_status<>'3') AS sum FROM t_task_node n ";
		return sql;
	}

	/**
	 * @name findStatusByBusId
	 * @author 品讯科技
	 * @param busId
	 * @Description 根据业务id查询审核状态
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String findStatusByBusId(@Param("busId") String busId) {
		// 更新时间：2021-04-01 10:38:28
		String selectSql = "t.bus_id as busId," + "t.approver_user_id as approverUserId,"
				+ "t.approver_user_name as approverUserName," + "t.approver_post_id as approverPostId,"
				+ "t.approver_post_name as approverPostName," + "t.approve_status as approveStatus,"
				+ "t.remarks as remarks," + "t.approve_time as approveTime";
		String sql = new SQL() {
			{
				SELECT(selectSql);
				FROM("t_workflow t");
				WHERE("t.bus_id=#{busId}");
			}
		}.toString();
		return sql;
	}

	/**
	 * @name findUserByTaskNodeId
	 * @author 品讯科技
	 * @param taskNodeId
	 * @Description 根据taskNodeId查询用户
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String findUserByTaskNodeId(@Param("taskNodeId") String taskNodeId, @Param("node") String node) {
		String sql = "select u.id as userId,u.username as userName from s_t_user u LEFT JOIN s_t_postuser pu on u.id=pu.userid LEFT JOIN s_t_post p on p.id=pu.postid where p.id in (select mp.post_id from b_t_msg_post mp where mp.task_node_id=#{taskNodeId}  and mp.node=#{node})";
		return sql;
	}

	/**
	 * @name findWorkflowNotCompleteByTaskNodeId
	 * @author 品讯科技
	 * @param projectId
	 * @Description 根据节点id查询未完成工作流
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String findWorkflowNotCompleteByTaskNodeId(@Param("projectId") String projectId) {
		String sql = "select " + selectOrInsertSql
				+ " from t_workflow t where t.bus_id IN (SELECT c.id FROM `b_t_check` c WHERE c.project_id = #{projectId}) and t.approve_status<>'3'";
		return sql;
	}

	/**
	 * @name workflowOverdueNotArrived
	 * @author 品讯科技
	 * @Description 查询订货单逾期未到达
	 * @time 2021-03-12 09:36:18
	 * @throws @return sql
	 */
	public String workflowOverdueNotArrived() {
		String sql = "select g.id as busId from b_t_order_good g where (g.pre_arrival_date>now() or g.require_arrival_date>now())";
		return sql;
	}

//   	List<JSONObject> indexCheckCount();
	public String indexCheckCount(@Param("projectId") String projectId) {
		String sql = "select sum(a.num) as num , a.time from ( "
				+ "select num   ,DATE_FORMAT(createTime, '%Y-%m-%d') AS time "
				+ " from b_t_check_detail  where check_id in (select id from b_t_check where project_id = #{projectId}) order by time ) a  group by a.time ";
		return sql;
	}

//   	List<JSONObject> indexInputCount();
	public String indexInputCount(@Param("projectId") String projectId) {
		String sql = "select sum(a.input_num) as num , a.time from ( "
				+ "select input_num   ,DATE_FORMAT(input_time, '%Y-%m-%d') AS time "
				+ " from b_t_stock_input where project_id = #{projectId} order by time ) a  group by a.time ";
		return sql;
	}

//   	List<JSONObject> indexOutputCount();
	public String indexOutputCount(@Param("projectId") String projectId) {
		String sql = "select sum(a.output_num) as num , a.time from (  "
				+ "select output_num   ,DATE_FORMAT(output_time, '%Y-%m-%d') AS time "
				+ " from b_t_stock_output where project_id = #{projectId} order by time ) a  group by a.time ";
		return sql;
	}

//   	List<JSONObject> indexAllotCount();
	public String indexAllotCount(@Param("projectId") String projectId) {
		String sql = "select sum(a.num) as num , a.time from ( "
				+ "select num   ,DATE_FORMAT(createTime, '%Y-%m-%d') AS time "
				+ " from b_t_allot_detail where allot_id in (select id from b_t_allot where project_id = #{projectId} )  order by time ) a  group by a.time ";
		return sql;
	}
}
