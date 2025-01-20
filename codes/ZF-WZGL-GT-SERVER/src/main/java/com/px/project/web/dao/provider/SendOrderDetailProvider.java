package com.px.project.web.dao.provider;

import com.px.project.common.base.BaseProvider;
import com.px.project.common.utils.StringUtil;
import com.px.project.web.dto.SendOrderDetailDto;
import com.px.project.web.entity.SendOrderDetailModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Package com.px.project.web.dao.provider
 * @ClassName SendOrderDetailProvider
 * @Description SendOrderDetailProvider
 * @author 品讯科技
 * @time   2021-03-16 14:20:38
 * @version V 1.0
 */
public class SendOrderDetailProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql="t.id as id,"+
									 "t.send_order_id as sendOrderId,"+
                                     "t.grid_id as gridId,"+
									 "t.pick_order_id as pickOrderId,"+
                                     "t.pick_order_detail_id as pickOrderDetailId,"+
									 "t.num as num,"+
									 "t.num as orgNum,"+
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
     * @param sendOrderDetailModel
     * @Description 增加
     * @time 2021-03-16 14:20:38
     * @throws
     * @return sql
     */
    public String save(final SendOrderDetailModel sendOrderDetailModel) {
        String sql = new SQL () {
            {
                INSERT_INTO ("b_t_send_order_detail ");
                VALUES("id", "#{id}");
                if(!StringUtil.isBlank (sendOrderDetailModel.getSendOrderId())){
                    VALUES("send_order_id", "#{sendOrderId}");
                }
                if(!StringUtil.isBlank (sendOrderDetailModel.getGridId())){
                    VALUES("grid_id", "#{gridId}");
                }
                if(!StringUtil.isBlank (sendOrderDetailModel.getPickOrderId())){
                    VALUES("pick_order_id", "#{pickOrderId}");
                }
                if(!StringUtil.isBlank (sendOrderDetailModel.getPickOrderDetailId())){
                    VALUES("pick_order_detail_id", "#{pickOrderDetailId}");
                }
                if(!StringUtil.isBlank (sendOrderDetailModel.getNum())){
                    VALUES("num", "#{num}");
                }
                if(!StringUtil.isBlank (sendOrderDetailModel.getCreateTime())){
                    VALUES("createTime", "#{createTime}");
                }
                if(!StringUtil.isBlank (sendOrderDetailModel.getCreateUserCode())){
                    VALUES("createUserCode", "#{createUserCode}");
                }
                if(!StringUtil.isBlank (sendOrderDetailModel.getCreateUserName())){
                    VALUES("createUserName", "#{createUserName}");
                }
                if(!StringUtil.isBlank (sendOrderDetailModel.getUpdateTime())){
                    VALUES("updateTime", "#{updateTime}");
                }
                if(!StringUtil.isBlank (sendOrderDetailModel.getUpdateUserCode())){
                    VALUES("updateUserCode", "#{updateUserCode}");
                }
                if(!StringUtil.isBlank (sendOrderDetailModel.getUpdateUserName())){
                    VALUES("updateUserName", "#{updateUserName}");
                }
                if(!StringUtil.isBlank (sendOrderDetailModel.getIsRemoved())){
                    VALUES("isremoved", "#{isRemoved}");
                }else{
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
     * @time 2021-03-16 14:20:38
     * @throws
     * @return sql
     */
    public String deleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                DELETE_FROM("b_t_send_order_detail");
            }
        }.toString();
        sql += batchJoinField("id",ids);
        return sql;
    }
    public String deleteByParentIds(@Param ("ids") String ids) {
    	String sql = new SQL () {
    		{
    			DELETE_FROM("b_t_send_order_detail");
    		}
    	}.toString();
    	sql += batchJoinField("send_order_id",ids);
    	return sql;
    }
    public String findAllByParentIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("b_t_send_order_detail t");
                WHERE( batchJoinFieldNoPrefix("send_order_id", ids));
            }
        }.toString();
        return sql;
    }


    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param ids
     * @Description 逻辑删除
     * @time 2021-03-16 14:20:38
     * @throws
     * @return sql
     */
    public String logicDeleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                UPDATE("b_t_send_order_detail");
                SET("isRemoved = '1'");
            }
        }.toString();
        sql += batchJoinField("id",ids);
        return sql;
    }

    /**
     * @name update
     * @author 品讯科技
     * @param sendOrderDetailModel
     * @Description 修改
     * @time 2021-03-16 14:20:38
     * @throws
     * @return sql
     */
    public String update(final SendOrderDetailModel sendOrderDetailModel) {
        String sql = new SQL () {
            {
                UPDATE("b_t_send_order_detail ");
                    if(sendOrderDetailModel.getSendOrderId() != null){
                        SET("send_order_id = #{sendOrderId}");
                    }
                    if(sendOrderDetailModel.getGridId() != null){
                        SET("grid_id = #{gridId}");
                    }
                    if(sendOrderDetailModel.getPickOrderId() != null){
                        SET("pick_order_id = #{pickOrderId}");
                    }
                    if(sendOrderDetailModel.getPickOrderDetailId() != null){
                        SET("pick_order_detail_id = #{pickOrderDetailId}");
                    }
                    if(sendOrderDetailModel.getNum() != null){
                        SET("num = #{num}");
                    }
                    if(sendOrderDetailModel.getCreateTime() != null){
                        SET("createTime = #{createTime}");
                    }
                    if(sendOrderDetailModel.getCreateUserCode() != null){
                        SET("createUserCode = #{createUserCode}");
                    }
                    if(sendOrderDetailModel.getCreateUserName() != null){
                        SET("createUserName = #{createUserName}");
                    }
                    if(sendOrderDetailModel.getUpdateTime() != null){
                        SET("updateTime = #{updateTime}");
                    }
                    if(sendOrderDetailModel.getUpdateUserCode() != null){
                        SET("updateUserCode = #{updateUserCode}");
                    }
                    if(sendOrderDetailModel.getUpdateUserName() != null){
                        SET("updateUserName = #{updateUserName}");
                    }
                    if(sendOrderDetailModel.getIsRemoved() != null){
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
     * @time 2021-03-16 14:20:38
     * @throws
     * @return sql
     */
    public String findById(@Param ("id") String id) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("b_t_send_order_detail t");
                WHERE("t.id = #{id}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param sendOrderDetailDto
     * @Description 根据条件查询数据并分页
     * @time 2021-03-16 14:20:38
     * @throws
     * @return sql
     */
    public String findAll(final SendOrderDetailDto sendOrderDetailDto) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("b_t_send_order_detail t");
                if(!StringUtil.isBlank (sendOrderDetailDto.getId())){
                    WHERE("t.id = #{id}");
                }
                if(!StringUtil.isBlank (sendOrderDetailDto.getGridId())){
                    WHERE("t.grid_id = #{gridId}");
                }
                if(!StringUtil.isBlank (sendOrderDetailDto.getSendOrderId())){
                    WHERE("t.send_order_id = #{sendOrderId}");
                }
                if(!StringUtil.isBlank (sendOrderDetailDto.getPickOrderId())){
                    WHERE("t.pick_order_id = #{pickOrderId}");
                }
                if(!StringUtil.isBlank (sendOrderDetailDto.getPickOrderDetailId())){
                    WHERE("t.pick_order_detail_id = #{pickOrderDetailId}");
                }
                if(!StringUtil.isBlank (sendOrderDetailDto.getNum())){
                    WHERE("t.num = #{num}");
                }
                if(!StringUtil.isBlank (sendOrderDetailDto.getCreateTime())){
                    WHERE("t.createTime = #{createTime}");
                }
                if(!StringUtil.isBlank (sendOrderDetailDto.getCreateUserCode())){
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if(!StringUtil.isBlank (sendOrderDetailDto.getCreateUserName())){
                    WHERE("t.createUserName = #{createUserName}");
                }
                if(!StringUtil.isBlank (sendOrderDetailDto.getUpdateTime())){
                    WHERE("t.updateTime = #{updateTime}");
                }
                if(!StringUtil.isBlank (sendOrderDetailDto.getUpdateUserCode())){
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (sendOrderDetailDto.getUpdateUserName())){
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                if(!StringUtil.isBlank (sendOrderDetailDto.getIsRemoved())){
                    WHERE("t.isRemoved = #{isRemoved}");
                }else{
                    WHERE("ifnull(t.isRemoved,'0') <> '1'"); //不包含删除项
                }
            }
        }.toString();
        return sql;
    }

    /**
     * @name sendOrderDetailList
     * @author 品讯科技
     * @param sendOrderId
     * @Description 查询发料单详情（关联领料单，取领料单物资信息）
     * @time 2021-03-16 14:20:38
     * @throws
     * @return sql
     */
    public String sendOrderDetailList(final String sendOrderId) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                SELECT("s.store_no as storeNo,s.store_name as storeName,s.id as storeId");
                SELECT("sg.grid_no as gridNo,sg.grid_name as gridName");
                SELECT("g.good_code as goodCode,g.good_name as goodName,g.measure_unit as measureUnit");
                SELECT("g.test_unit as testUnit,g.specs_model_name as specsModelName,g.id as goodId");
                SELECT("g.good_type as goodType,g.remarks as remarks");
                SELECT("gt.type_name as typeName");
                SELECT("si.station_interval_name as stationIntervalName,si.station_interval_code as stationIntervalCode");
                SELECT("pc.pick_company_no as pickCompanyNo,pc.pick_company_name as pickCompanyName");
                SELECT("pd.num as pickNum");
                SELECT("tm.project_id as projectId");

                FROM("b_t_send_order_detail t");
                LEFT_OUTER_JOIN("b_t_pick_order tp on t.pick_order_id = tp.id and ifnull(tp.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("b_t_station_interval si on si.id = tp.station_interval_id and ifnull(si.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("b_t_send_order tm on tm.id = t.send_order_id and ifnull(tm.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("b_t_pick_order_detail pd on t.pick_order_detail_id = pd.id and ifnull(pd.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("b_t_good g on g.id = pd.good_id and ifnull(g.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("b_t_good_type gt on gt.id = g.good_type and ifnull(gt.isRemoved,'0') <> '1'");
                LEFT_OUTER_JOIN("b_t_store_grid sg on sg.id = t.grid_id and ifnull(sg.isRemoved,'0') <> '1'");
                LEFT_OUTER_JOIN("b_t_store s on sg.store_id = s.id and ifnull(s.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("b_t_send_order so on so.id = t.send_order_id and ifnull(so.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("b_t_pick_company pc on pc.id = so.pick_company_id and ifnull(pc.isRemoved,'0')<>'1'");
                WHERE("t.send_order_id = #{sendOrderId}");
                WHERE("ifnull(t.isRemoved,'0')<>'1'");
            }
        }.toString();
        return sql;
    }
}
