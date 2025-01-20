package com.px.project.web.dao.provider;

import com.px.project.common.base.BaseProvider;
import com.px.project.common.utils.StringUtil;
import com.px.project.web.dto.StockReturnDto;
import com.px.project.web.entity.StockReturnModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Package com.px.project.web.dao.provider
 * @ClassName StockReturnProvider
 * @Description StockReturnProvider
 * @author 品讯科技
 * @time   2021-03-19 22:37:29
 * @version V 1.0
 */
public class StockReturnProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql="t.id as id,"+
									 "t.project_id as projectId,"+
									 "t.return_no as returnNo,"+
									 "t.store_id as storeId,"+
                                     //总数（子表）
                                     "(select ifnull(sum(num),0) from b_t_stock_return_detail d where d.return_id = t.id and ifnull(d.isRemoved,'0')<>'1') as num,"+
									/* "t.approver_id as approverId,"+
									 "t.approver_name as approverName,"+
									 "t.approve_status as approveStatus,"+*/
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
     * @param stockReturnModel
     * @Description 增加
     * @time 2021-03-19 22:37:29
     * @throws
     * @return sql
     */
    public String save(final StockReturnModel stockReturnModel) {
        String sql = new SQL () {
            {
                INSERT_INTO ("b_t_stock_return ");
                VALUES("id", "#{id}");
                if(!StringUtil.isBlank (stockReturnModel.getProjectId())){
                    VALUES("project_id", "#{projectId}");
                }
                if(!StringUtil.isBlank (stockReturnModel.getReturnNo())){
                    VALUES("return_no", "#{returnNo}");
                }
                if(!StringUtil.isBlank (stockReturnModel.getStoreId())){
                    VALUES("store_id", "#{storeId}");
                }
                if(!StringUtil.isBlank (stockReturnModel.getApproverId())){
                    VALUES("approver_id", "#{approverId}");
                }
                if(!StringUtil.isBlank (stockReturnModel.getApproverName())){
                    VALUES("approver_name", "#{approverName}");
                }
                if(!StringUtil.isBlank (stockReturnModel.getApproveStatus())){
                    VALUES("approve_status", "#{approveStatus}");
                }
                if(!StringUtil.isBlank (stockReturnModel.getCreateTime())){
                    VALUES("createTime", "#{createTime}");
                }
                if(!StringUtil.isBlank (stockReturnModel.getCreateUserCode())){
                    VALUES("createUserCode", "#{createUserCode}");
                }
                if(!StringUtil.isBlank (stockReturnModel.getCreateUserName())){
                    VALUES("createUserName", "#{createUserName}");
                }
                if(!StringUtil.isBlank (stockReturnModel.getUpdateTime())){
                    VALUES("updateTime", "#{updateTime}");
                }
                if(!StringUtil.isBlank (stockReturnModel.getUpdateUserCode())){
                    VALUES("updateUserCode", "#{updateUserCode}");
                }
                if(!StringUtil.isBlank (stockReturnModel.getUpdateUserName())){
                    VALUES("updateUserName", "#{updateUserName}");
                }
                if(!StringUtil.isBlank (stockReturnModel.getIsRemoved())){
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
     * @time 2021-03-19 22:37:29
     * @throws
     * @return sql
     */
    public String deleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                DELETE_FROM("b_t_stock_return");
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
     * @time 2021-03-19 22:37:29
     * @throws
     * @return sql
     */
    public String logicDeleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                UPDATE("b_t_stock_return");
                SET("isRemoved = '1'");
            }
        }.toString();
        sql += batchJoinField("id",ids);
        return sql;
    }

    /**
     * @name update
     * @author 品讯科技
     * @param stockReturnModel
     * @Description 修改
     * @time 2021-03-19 22:37:29
     * @throws
     * @return sql
     */
    public String update(final StockReturnModel stockReturnModel) {
        String sql = new SQL () {
            {
                UPDATE("b_t_stock_return ");
                if(stockReturnModel.getProjectId() != null){
                    SET("project_id = #{projectId}");
                }
                if(stockReturnModel.getReturnNo() != null){
                    SET("return_no = #{returnNo}");
                }
                if(stockReturnModel.getStoreId() != null){
                    SET("store_id = #{storeId}");
                }
                if(stockReturnModel.getApproverId() != null){
                    SET("approver_id = #{approverId}");
                }
                if(stockReturnModel.getApproverName() != null){
                    SET("approver_name = #{approverName}");
                }
                if(stockReturnModel.getApproveStatus() != null){
                    SET("approve_status = #{approveStatus}");
                }
                if(stockReturnModel.getCreateTime() != null){
                    SET("createTime = #{createTime}");
                }
                if(stockReturnModel.getCreateUserCode() != null){
                    SET("createUserCode = #{createUserCode}");
                }
                if(stockReturnModel.getCreateUserName() != null){
                    SET("createUserName = #{createUserName}");
                }
                if(stockReturnModel.getUpdateTime() != null){
                    SET("updateTime = #{updateTime}");
                }
                if(stockReturnModel.getUpdateUserCode() != null){
                    SET("updateUserCode = #{updateUserCode}");
                }
                if(stockReturnModel.getUpdateUserName() != null){
                    SET("updateUserName = #{updateUserName}");
                }
                if(stockReturnModel.getIsRemoved() != null){
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
     * @time 2021-03-19 22:37:29
     * @throws
     * @return sql
     */
    public String findById(@Param ("id") String id) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("b_t_stock_return t");
                WHERE("t.id = #{id}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param stockReturnDto
     * @Description 根据条件查询数据并分页
     * @time 2021-03-19 22:37:29
     * @throws
     * @return sql
     */
    public String findAll(final StockReturnDto stockReturnDto) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                SELECT("s.store_no as storeNo,s.store_name as storeName");
                FROM("b_t_stock_return t");
                LEFT_OUTER_JOIN("b_t_store s on t.store_id = s.id and ifnull(s.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("t_workflow wf on wf.bus_id = t.id"); //审批状态
                WHERE("t.project_id = #{projectId}");

                if(!StringUtil.isBlank (stockReturnDto.getId())){
                    WHERE("t.id = #{id}");
                }
                if(!StringUtil.isBlank (stockReturnDto.getReturnNo())){
                    WHERE("t.return_no like concat(concat('%',#{returnNo}),'%')");
                }
                if(!StringUtil.isBlank (stockReturnDto.getStoreId())){
                    WHERE("t.store_id = #{storeId}");
                }
               /* if(!StringUtil.isBlank (stockReturnDto.getApproverId())){
                    WHERE("t.approver_id = #{approverId}");
                }
                if(!StringUtil.isBlank (stockReturnDto.getApproverName())){
                    WHERE("t.approver_name = #{approverName}");
                }*/
                if(!StringUtil.isBlank (stockReturnDto.getApproveStatus())){
                    WHERE("wf.approve_status = #{approveStatus}");
                }
                if(!StringUtil.isBlank (stockReturnDto.getCreateTime())){
                    WHERE("t.createTime = #{createTime}");
                }
                if(!StringUtil.isBlank (stockReturnDto.getCreateUserCode())){
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if(!StringUtil.isBlank (stockReturnDto.getCreateUserName())){
                    WHERE("t.createUserName = #{createUserName}");
                }
                if(!StringUtil.isBlank (stockReturnDto.getUpdateTime())){
                    WHERE("t.updateTime = #{updateTime}");
                }
                if(!StringUtil.isBlank (stockReturnDto.getUpdateUserCode())){
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (stockReturnDto.getUpdateUserName())){
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                if(!StringUtil.isBlank (stockReturnDto.getIsRemoved())){
                    WHERE("t.isremoved = #{isRemoved}");
                }else{
                    WHERE("ifnull(t.isRemoved,'0') <> '1'"); //不包含删除项
                }
                //退库时间段
                if(!StringUtil.isBlank (stockReturnDto.getBeginTime())){
                    WHERE("date(t.createTime) >= date(#{beginTime})");
                }
                if(!StringUtil.isBlank (stockReturnDto.getEndTime())){
                    WHERE("date(t.createTime) <= date(#{endTime})");
                }
            }
        }.toString();
        return sql;
    }

}
