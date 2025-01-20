package com.px.project.web.dao.provider;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import com.px.project.common.base.BaseProvider;
import com.px.project.common.utils.StringUtil;
import com.px.project.web.dto.CheckInputDetailDto;
import com.px.project.web.entity.CheckInputDetailModel;

/**
 * @Package com.px.project.web.dao.provider
 * @ClassName CheckInputDetailProvider
 * @Description CheckInputDetailProvider
 * @author 品讯科技
 * @time 2021-03-16 23:35:17
 * @version V 1.0
 */
public class CheckInputDetailProvider extends BaseProvider {

	// sql，selectOrInsert部分
	private String selectOrInsertSql = "t.id as id," + "t.check_input_id as checkInputId," + "t.good_id as goodId,"
			+ "t.grid_id as gridId," + "t.num as num," + "t.createTime as createTime,"
			+ "t.createUserCode as createUserCode," + "t.createUserName as createUserName,"
			+ "t.updateTime as updateTime," + "t.updateUserCode as updateUserCode," + "t.order_good_id as orderGoodId,"
			+ "t.updateUserName as updateUserName," + "t.isremoved as isRemoved";

	/**
	 * @name save
	 * @author 品讯科技
	 * @param checkInputDetailModel
	 * @Description 增加
	 * @time 2021-03-16 23:35:17
	 * @throws @return sql
	 */
	public String save(final CheckInputDetailModel checkInputDetailModel) {
		String sql = new SQL() {
			{
				INSERT_INTO("b_t_check_input_detail ");
				VALUES("id", "#{id}");
				if (!StringUtil.isBlank(checkInputDetailModel.getCheckInputId())) {
					VALUES("check_input_id", "#{checkInputId}");
				}
				if (!StringUtil.isBlank(checkInputDetailModel.getGoodId())) {
					VALUES("good_id", "#{goodId}");
				}
				if (!StringUtil.isBlank(checkInputDetailModel.getGridId())) {
					VALUES("grid_id", "#{gridId}");
				}
				if (!StringUtil.isBlank(checkInputDetailModel.getNum())) {
					VALUES("num", "#{num}");
				}
				if (!StringUtil.isBlank(checkInputDetailModel.getCreateTime())) {
					VALUES("createTime", "#{createTime}");
				}
				if (!StringUtil.isBlank(checkInputDetailModel.getCreateUserCode())) {
					VALUES("createUserCode", "#{createUserCode}");
				}
				if (!StringUtil.isBlank(checkInputDetailModel.getCreateUserName())) {
					VALUES("createUserName", "#{createUserName}");
				}
				if (!StringUtil.isBlank(checkInputDetailModel.getUpdateTime())) {
					VALUES("updateTime", "#{updateTime}");
				}
				if (!StringUtil.isBlank(checkInputDetailModel.getUpdateUserCode())) {
					VALUES("updateUserCode", "#{updateUserCode}");
				}
				if (!StringUtil.isBlank(checkInputDetailModel.getUpdateUserName())) {
					VALUES("updateUserName", "#{updateUserName}");
				}
				if (!StringUtil.isBlank(checkInputDetailModel.getOrderGoodId())) {
					VALUES("order_good_id", "#{orderGoodId}");
				}
				if (!StringUtil.isBlank(checkInputDetailModel.getIsRemoved())) {
					VALUES("isremoved", "#{isRemoved}");
				} else {
					VALUES("isremoved", "0");
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
	 * @time 2021-03-16 23:35:17
	 * @throws @return sql
	 */
	public String deleteByIds(@Param("ids") String ids) {
		String sql = new SQL() {
			{
				DELETE_FROM("b_t_check_input_detail");
			}
		}.toString();
		sql += batchJoinField("id", ids);
		return sql;
	}
	public String deleteByCheckImputIds(@Param("checkInputIds") String checkInputIds) {
		String sql = new SQL() {
			{
				DELETE_FROM("b_t_check_input_detail");
			}
		}.toString();
		sql += batchJoinField("check_input_id", checkInputIds);
		return sql;
	}

	/**
	 * @name logicDeleteByIds
	 * @author 品讯科技
	 * @param ids
	 * @Description 逻辑删除
	 * @time 2021-03-16 23:35:17
	 * @throws @return sql
	 */
	public String logicDeleteByIds(@Param("ids") String ids) {
		String sql = new SQL() {
			{
				UPDATE("b_t_check_input_detail");
				SET("isRemoved = '1'");
			}
		}.toString();
		sql += batchJoinField("id", ids);
		return sql;
	}

	/**
	 * @name update
	 * @author 品讯科技
	 * @param checkInputDetailModel
	 * @Description 修改
	 * @time 2021-03-16 23:35:17
	 * @throws @return sql
	 */
	public String update(final CheckInputDetailModel checkInputDetailModel) {
		String sql = new SQL() {
			{
				UPDATE("b_t_check_input_detail ");
				if (checkInputDetailModel.getCheckInputId() != null) {
					SET("check_input_id = #{checkInputId}");
				}
				if (checkInputDetailModel.getGoodId() != null) {
					SET("good_id = #{goodId}");
				}
				if (checkInputDetailModel.getGridId() != null) {
					SET("grid_id = #{gridId}");
				}
				if (checkInputDetailModel.getNum() != null) {
					SET("num = #{num}");
				}
				if (checkInputDetailModel.getCreateTime() != null) {
					SET("createTime = #{createTime}");
				}
				if (checkInputDetailModel.getCreateUserCode() != null) {
					SET("createUserCode = #{createUserCode}");
				}
				if (checkInputDetailModel.getCreateUserName() != null) {
					SET("createUserName = #{createUserName}");
				}
				if (checkInputDetailModel.getUpdateTime() != null) {
					SET("updateTime = #{updateTime}");
				}
				if (checkInputDetailModel.getUpdateUserCode() != null) {
					SET("updateUserCode = #{updateUserCode}");
				}
				if (checkInputDetailModel.getUpdateUserName() != null) {
					SET("updateUserName = #{updateUserName}");
				}
				if (checkInputDetailModel.getIsRemoved() != null) {
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
	 * @time 2021-03-16 23:35:17
	 * @throws @return sql
	 */
	public String findById(@Param("id") String id) {
		String sql = new SQL() {
			{
				SELECT(selectOrInsertSql);
				FROM("b_t_check_input_detail t");
				WHERE("t.id = #{id}");
			}
		}.toString();
		return sql;
	}
	public String findAllSumByGoodId(CheckInputDetailDto checkInputDetailDto) {
		String sql = new SQL() {
			{
				SELECT( " sum(num) as num,good_id as goodId");
				FROM("b_t_check_input_detail t");
				WHERE("t.order_good_id = #{orderGoodId}");
			}
		}.toString();
		sql+= " group by good_id";
		return sql;
	}

	/**
	 * @name findAll
	 * @author 品讯科技
	 * @param checkInputDetailDto
	 * @Description 根据条件查询数据并分页
	 * @time 2021-03-16 23:35:17
	 * @throws @return sql
	 */
	public String findAll(final CheckInputDetailDto checkInputDetailDto) {
		String sql = new SQL() {
			{
				SELECT(selectOrInsertSql);
				SELECT("g.good_code as goodCode,g.good_name as goodName,g.measure_unit as measureUnit,g.remarks as remarks ");
				SELECT("GROUP_CONCAT(btm.major_name) as majorName,GROUP_CONCAT(btm.id) as majorId ");
				SELECT("g.test_unit as testUnit,g.specs_model_name as specsModelName");
				SELECT("g.good_type as goodType");
				SELECT("gt.type_name as typeName");
				SELECT("sg.grid_no as gridNo,sg.grid_name as gridName,sg.store_id as storeId");
				SELECT("tm.project_id as projectId");

				FROM("b_t_check_input_detail t");
				LEFT_OUTER_JOIN("b_t_check_input tm on tm.id = t.check_input_id and ifnull(tm.isRemoved,'0') <> '1'");
				LEFT_OUTER_JOIN("b_t_good g on g.id = t.good_id and ifnull(g.isRemoved,'0') <> '1'");
				LEFT_OUTER_JOIN("b_t_store_grid sg on sg.id = t.grid_id and ifnull(sg.isRemoved,'0') <> '1'");
				LEFT_OUTER_JOIN("b_t_good_type gt on gt.id = g.good_type and ifnull(gt.isRemoved,'0') <> '1'");
				LEFT_OUTER_JOIN("b_t_good_major gm on gm.good_id = t.good_id ");
				LEFT_OUTER_JOIN("b_t_major btm on btm.id = gm.major_id ");

				if (!StringUtil.isBlank(checkInputDetailDto.getId())) {
					WHERE("t.id = #{id}");
				}
				if (!StringUtil.isBlank(checkInputDetailDto.getCheckInputId())) {
					WHERE("t.check_input_id = #{checkInputId}");
				}
				if (!StringUtil.isBlank(checkInputDetailDto.getGoodId())) {
					WHERE("t.good_id = #{goodId}");
				}
				if (!StringUtil.isBlank(checkInputDetailDto.getOrderGoodId())) {
					WHERE("t.order_good_id = #{orderGoodId}");
				}
				if (!StringUtil.isBlank(checkInputDetailDto.getGridId())) {
					WHERE("t.grid_id = #{gridId}");
				}
				if (!StringUtil.isBlank(checkInputDetailDto.getNum())) {
					WHERE("t.num = #{num}");
				}
				if (!StringUtil.isBlank(checkInputDetailDto.getCreateTime())) {
					WHERE("t.createTime = #{createTime}");
				}
				if (!StringUtil.isBlank(checkInputDetailDto.getCreateUserCode())) {
					WHERE("t.createUserCode = #{createUserCode}");
				}
				if (!StringUtil.isBlank(checkInputDetailDto.getCreateUserName())) {
					WHERE("t.createUserName = #{createUserName}");
				}
				if (!StringUtil.isBlank(checkInputDetailDto.getUpdateTime())) {
					WHERE("t.updateTime = #{updateTime}");
				}
				if (!StringUtil.isBlank(checkInputDetailDto.getUpdateUserCode())) {
					WHERE("t.updateUserCode = #{updateUserCode}");
				}
				if (!StringUtil.isBlank(checkInputDetailDto.getUpdateUserName())) {
					WHERE("t.updateUserName = #{updateUserName}");
				}
				if (!StringUtil.isBlank(checkInputDetailDto.getIsRemoved())) {
					WHERE("t.isremoved = #{isRemoved}");
				} else {
					WHERE("ifnull(t.isremoved,'0') <> '1'");
				}
				GROUP_BY(" t.id ");
			}
		}.toString();
		return sql;
	}

	public String batchSave(List<CheckInputDetailModel> list) {
		String keys = "id ,check_input_id,good_id," + " grid_id   ,num,createTime,"
				+ "createUserCode,createUserName ,updateTime," + "updateUserCode,updateUserName,order_good_id    ";
		String values = "#'{'list[{0}].id}," + "#'{'list[{0}].checkInputId}," + "#'{'list[{0}].goodId},"
				+ "#'{'list[{0}].gridId}," + "#'{'list[{0}].num},"
				+ "#'{'list[{0}].createTime},#'{'list[{0}].createUserCode},"
				+ "#'{'list[{0}].createUserName},#'{'list[{0}].updateTime},"
				+ "#'{'list[{0}].updateUserCode},#'{'list[{0}].updateUserName},#'{'list[{0}].orderGoodId}";
		return batchSaveSql("b_t_check_input_detail", keys, values, list);
	}
}
