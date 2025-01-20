package com.px.project.web.dao.provider;

import com.px.project.common.base.BaseProvider;
import com.px.project.common.utils.StringUtil;
import com.px.project.web.dto.PickOrderDetailDto;
import com.px.project.web.entity.PickOrderDetailModel;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author 品讯科技
 * @version V 1.0
 * @Package com.px.project.web.dao.provider
 * @ClassName PickOrderDetailProvider
 * @Description PickOrderDetailProvider
 * @time 2021-03-16 13:47:56
 */
public class PickOrderDetailProvider extends BaseProvider {

	// sql，selectOrInsert部分
	private String selectOrInsertSql = "t.id as id," + "t.pick_order_id as pickOrderId," + "t.good_id as goodId,"
			+ "t.num as num," + "t.createTime as createTime," + "t.createUserCode as createUserCode,"
			+ "t.createUserName as createUserName," + "t.updateTime as updateTime,"
			+ "t.updateUserCode as updateUserCode," + "t.updateUserName as updateUserName," + "t.balance as balance,"
			+ "t.isremoved as isRemoved";

	/**
	 * @param pickOrderDetailModel
	 * @return sql
	 * @throws @name save
	 * @author 品讯科技
	 * @Description 增加
	 * @time 2021-03-16 13:47:56
	 */
	public String save(final PickOrderDetailModel pickOrderDetailModel) {
		String sql = new SQL() {
			{
				INSERT_INTO("b_t_pick_order_detail ");
				VALUES("id", "#{id}");
				if (!StringUtil.isBlank(pickOrderDetailModel.getPickOrderId())) {
					VALUES("pick_order_id", "#{pickOrderId}");
				}
				if (!StringUtil.isBlank(pickOrderDetailModel.getGoodId())) {
					VALUES("good_id", "#{goodId}");
				}
				if (!StringUtil.isBlank(pickOrderDetailModel.getNum())) {
					VALUES("num", "#{num}");
					VALUES("balance", "#{num}");

				}
				if (!StringUtil.isBlank(pickOrderDetailModel.getCreateTime())) {
					VALUES("createTime", "#{createTime}");
				}
				if (!StringUtil.isBlank(pickOrderDetailModel.getCreateUserCode())) {
					VALUES("createUserCode", "#{createUserCode}");
				}
				if (!StringUtil.isBlank(pickOrderDetailModel.getCreateUserName())) {
					VALUES("createUserName", "#{createUserName}");
				}
				if (!StringUtil.isBlank(pickOrderDetailModel.getUpdateTime())) {
					VALUES("updateTime", "#{updateTime}");
				}
				if (!StringUtil.isBlank(pickOrderDetailModel.getUpdateUserCode())) {
					VALUES("updateUserCode", "#{updateUserCode}");
				}
				if (!StringUtil.isBlank(pickOrderDetailModel.getUpdateUserName())) {
					VALUES("updateUserName", "#{updateUserName}");
				}
				if (!StringUtil.isBlank(pickOrderDetailModel.getIsRemoved())) {
					VALUES("isremoved", "#{isRemoved}");
				} else {
					VALUES("isremoved", "0");
				}
			}
		}.toString();
		return sql;
	}

	/**
	 * @param ids
	 * @return sql
	 * @throws @name deleteByIds
	 * @author 品讯科技
	 * @Description 物理删除
	 * @time 2021-03-16 13:47:56
	 */
	public String deleteByIds(@Param("ids") String ids) {
		String sql = new SQL() {
			{
				DELETE_FROM("b_t_pick_order_detail");
			}
		}.toString();
		sql += batchJoinField("id", ids);
		return sql;
	}

	public String deleteByOrderIds(@Param("pickOrderIds") String pickOrderIds) {
		String sql = new SQL() {
			{
				DELETE_FROM("b_t_pick_order_detail");
			}
		}.toString();
		sql += batchJoinField("pick_order_id", pickOrderIds);
		return sql;
	}

	/**
	 * @param ids
	 * @return sql
	 * @throws @name logicDeleteByIds
	 * @author 品讯科技
	 * @Description 逻辑删除
	 * @time 2021-03-16 13:47:56
	 */
	public String logicDeleteByIds(@Param("ids") String ids) {
		String sql = new SQL() {
			{
				UPDATE("b_t_pick_order_detail");
				SET("isRemoved = '1'");
			}
		}.toString();
		sql += batchJoinField("id", ids);
		return sql;
	}

	/**
	 * @param pickOrderDetailModel
	 * @return sql
	 * @throws @name update
	 * @author 品讯科技
	 * @Description 修改
	 * @time 2021-03-16 13:47:56
	 */
	public String update(final PickOrderDetailModel pickOrderDetailModel) {
		String sql = new SQL() {
			{
				UPDATE("b_t_pick_order_detail ");
				if (pickOrderDetailModel.getPickOrderId() != null) {
					SET("pick_order_id = #{pickOrderId}");
				}
				if (pickOrderDetailModel.getGoodId() != null) {
					SET("good_id = #{goodId}");
				}
				if (pickOrderDetailModel.getNum() != null) {
					SET("num = #{num}");
				}
				if (pickOrderDetailModel.getCreateTime() != null) {
					SET("createTime = #{createTime}");
				}
				if (pickOrderDetailModel.getCreateUserCode() != null) {
					SET("createUserCode = #{createUserCode}");
				}
				if (pickOrderDetailModel.getCreateUserName() != null) {
					SET("createUserName = #{createUserName}");
				}
				if (pickOrderDetailModel.getUpdateTime() != null) {
					SET("updateTime = #{updateTime}");
				}
				if (pickOrderDetailModel.getUpdateUserCode() != null) {
					SET("updateUserCode = #{updateUserCode}");
				}
				if (pickOrderDetailModel.getUpdateUserName() != null) {
					SET("updateUserName = #{updateUserName}");
				}
				if (pickOrderDetailModel.getIsRemoved() != null) {
					SET("isremoved = #{isRemoved}");
				}
				if (pickOrderDetailModel.getBalance() != null) {
					SET("balance = #{balance}");
				}
				WHERE("id = #{id}");
			}
		}.toString();
		return sql;
	}

	/**
	 * @param id
	 * @return sql
	 * @throws @name findById
	 * @author 品讯科技
	 * @Description 根据Id查询数据
	 * @time 2021-03-16 13:47:56
	 */
	public String findById(@Param("id") String id) {
		String sql = new SQL() {
			{
				SELECT(selectOrInsertSql);
				FROM("b_t_pick_order_detail t");
				WHERE("t.id = #{id}");
			}
		}.toString();
		return sql;
	}

	public String findByGoodsId(@Param("goodIds") String goodIds, @Param("pickOrderId") String pickOrderId) {
		String sql = new SQL() {
			{
				SELECT(selectOrInsertSql);
				FROM("b_t_pick_order_detail t");
				WHERE("t.pick_order_id = #{pickOrderId}");
				WHERE(batchJoinFieldNoPrefix("t.good_id", goodIds));
			}
		}.toString();
		return sql;
	}

	public String findByIds(@Param("ids") String ids, @Param("pickOrderId") String pickOrderId) {
		String sql = new SQL() {
			{
				SELECT(selectOrInsertSql + ",b.good_name as goodName");
				FROM("b_t_pick_order_detail t left join b_t_good b on t.good_id = b.id");
				WHERE("t.pick_order_id = #{pickOrderId}");
				WHERE(batchJoinFieldNoPrefix("t.id", ids));
			}
		}.toString();
		return sql;
	}

	/**
	 * @param pickOrderDetailDto
	 * @return sql
	 * @throws @name findAll
	 * @author 品讯科技
	 * @Description 根据条件查询数据并分页
	 * @time 2021-03-16 13:47:56
	 */
	public String findAll(final PickOrderDetailDto pickOrderDetailDto) {
		String sql = new SQL() {
			{
				SELECT(selectOrInsertSql+",t.id as pickOrderDetailId,t.num as orgNum ");
				SELECT("g.good_code as goodCode,g.good_name as goodName,g.measure_unit as measureUnit");
				SELECT("GROUP_CONCAT(btm.major_name) as majorName,GROUP_CONCAT(btm.id) as majorId ");
				SELECT("g.test_unit as testUnit,g.specs_model_name as specsModelName");
				SELECT("g.good_type as goodType");
				SELECT("gt.type_name as typeName");
				SELECT("gg.grid_id as gridId");
				SELECT("sg.grid_name as gridName");
				SELECT("s.store_name as storeName");
				SELECT("ifnull(sc.stock_num,0) as stockNum"); // 库存数量
				FROM("b_t_pick_order_detail t");
				INNER_JOIN("b_t_good g on g.id = t.good_id and ifnull(g.isRemoved,'0') <> '1'");
				LEFT_OUTER_JOIN("b_t_stock sc on t.good_id = sc.good_id ");
				LEFT_OUTER_JOIN("b_t_good_major gm on gm.good_id = t.good_id ");
				LEFT_OUTER_JOIN("b_t_major btm on btm.id = gm.major_id ");
				LEFT_OUTER_JOIN("b_t_good_type gt on gt.id = g.good_type and ifnull(gt.isRemoved,'0') <> '1'");
				INNER_JOIN("b_t_good_grid gg on gg.good_id = g.id and ifnull(gg.isRemoved,'0') <> '1'");
				INNER_JOIN("b_t_store_grid sg on sg.id = gg.grid_id and ifnull(sg.isRemoved,'0') <> '1'");
				INNER_JOIN("b_t_store s on s.id = sg.store_id and ifnull(s.isRemoved,'0') <> '1'");

				if (!StringUtil.isBlank(pickOrderDetailDto.getId())) {
					WHERE("t.id = #{id}");
				}
				if (!StringUtil.isBlank(pickOrderDetailDto.getPickOrderId())) {
					WHERE("t.pick_order_id = #{pickOrderId}");
				}
				if (!StringUtil.isBlank(pickOrderDetailDto.getGoodId())) {
					WHERE("t.good_id = #{goodId}");
				}
				if (!StringUtil.isBlank(pickOrderDetailDto.getNum())) {
					WHERE("t.num = #{num}");
				}
				if (!StringUtil.isBlank(pickOrderDetailDto.getCreateTime())) {
					WHERE("t.createTime = #{createTime}");
				}
				if (!StringUtil.isBlank(pickOrderDetailDto.getCreateUserCode())) {
					WHERE("t.createUserCode = #{createUserCode}");
				}
				if (!StringUtil.isBlank(pickOrderDetailDto.getCreateUserName())) {
					WHERE("t.createUserName = #{createUserName}");
				}
				if (!StringUtil.isBlank(pickOrderDetailDto.getUpdateTime())) {
					WHERE("t.updateTime = #{updateTime}");
				}
				if (!StringUtil.isBlank(pickOrderDetailDto.getUpdateUserCode())) {
					WHERE("t.updateUserCode = #{updateUserCode}");
				}
				if (!StringUtil.isBlank(pickOrderDetailDto.getUpdateUserName())) {
					WHERE("t.updateUserName = #{updateUserName}");
				}
				if (!StringUtil.isBlank(pickOrderDetailDto.getIsRemoved())) {
					WHERE("t.isremoved = #{isRemoved}");
				} else {
					WHERE("ifnull(t.isRemoved,'0')<>'1'");
				}
				if (pickOrderDetailDto.getPickOrderIds().size() > 0) {
					String pickOrderIds = StringUtils.join(pickOrderDetailDto.getPickOrderIds(), "','");
					WHERE("t.pick_order_id in ('" + pickOrderIds + "')");
				}
				GROUP_BY(" t.id ");
			}
		}.toString();
		return sql;
	}
	
	public String batchSave(List<PickOrderDetailModel> list) {
		String keys = "id ,pick_order_id,good_id," + " num,createTime,"
				+ "createUserCode,createUserName ,updateTime," + "updateUserCode,updateUserName,balance    ";
		String values = "#'{'list[{0}].id}," + "#'{'list[{0}].pickOrderId}," + "#'{'list[{0}].goodId},"
				+ "#'{'list[{0}].num},"
				+ "#'{'list[{0}].createTime},#'{'list[{0}].createUserCode},"
				+ "#'{'list[{0}].createUserName},#'{'list[{0}].updateTime},"
				+ "#'{'list[{0}].updateUserCode},#'{'list[{0}].updateUserName},#'{'list[{0}].balance}";
		return batchSaveSql("b_t_pick_order_detail", keys, values, list);
	}
}
