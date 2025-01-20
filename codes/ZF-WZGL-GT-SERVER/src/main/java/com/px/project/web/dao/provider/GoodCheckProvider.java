package com.px.project.web.dao.provider;

import com.px.project.common.base.BaseProvider;
import com.px.project.common.utils.StringUtil;
import com.px.project.web.dto.GoodCheckDto;
import com.px.project.web.entity.GoodCheckModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.HashMap;

/**
 * @Package com.px.project.web.dao.provider
 * @ClassName GoodCheckProvider
 * @Description GoodCheckProvider
 * @author 品讯科技
 * @time   2021-03-19 23:03:56
 * @version V 1.0
 */
public class GoodCheckProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql="t.id as id,"+
            "t.project_id as projectId,"+
            "t.good_id as goodId,"+
            "t.check_time as checkTime,"+
            "t.check_num as checkNum,"+
            "t.approve_status as approveStatus,"+
            "t.approver_id as approverId,"+
            "t.approver_name as approverName,"+
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
     * @param goodCheckModel
     * @Description 增加
     * @time 2021-03-19 23:03:56
     * @throws
     * @return sql
     */
    public String save(final GoodCheckModel goodCheckModel) {
        String sql = new SQL () {
            {
                INSERT_INTO ("b_t_good_check ");
                VALUES("id", "#{id}");
                if(!StringUtil.isBlank (goodCheckModel.getProjectId())){
                    VALUES("project_id", "#{projectId}");
                }
                if(!StringUtil.isBlank (goodCheckModel.getGoodId())){
                    VALUES("good_id", "#{goodId}");
                }
                if(!StringUtil.isBlank (goodCheckModel.getCheckTime())){
                    VALUES("check_time", "#{checkTime}");
                }
                if(!StringUtil.isBlank (goodCheckModel.getCheckNum())){
                    VALUES("check_num", "#{checkNum}");
                }
                if(!StringUtil.isBlank (goodCheckModel.getApproveStatus())){
                    VALUES("approve_status", "#{approveStatus}");
                }
                if(!StringUtil.isBlank (goodCheckModel.getApproverId())){
                    VALUES("approver_id", "#{approverId}");
                }
                if(!StringUtil.isBlank (goodCheckModel.getApproverName())){
                    VALUES("approver_name", "#{approverName}");
                }
                if(!StringUtil.isBlank (goodCheckModel.getCreateTime())){
                    VALUES("createTime", "#{createTime}");
                }
                if(!StringUtil.isBlank (goodCheckModel.getCreateUserCode())){
                    VALUES("createUserCode", "#{createUserCode}");
                }
                if(!StringUtil.isBlank (goodCheckModel.getCreateUserName())){
                    VALUES("createUserName", "#{createUserName}");
                }
                if(!StringUtil.isBlank (goodCheckModel.getUpdateTime())){
                    VALUES("updateTime", "#{updateTime}");
                }
                if(!StringUtil.isBlank (goodCheckModel.getUpdateUserCode())){
                    VALUES("updateUserCode", "#{updateUserCode}");
                }
                if(!StringUtil.isBlank (goodCheckModel.getUpdateUserName())){
                    VALUES("updateUserName", "#{updateUserName}");
                }
                if(!StringUtil.isBlank (goodCheckModel.getIsRemoved())){
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
     * @time 2021-03-19 23:03:56
     * @throws
     * @return sql
     */
    public String deleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                DELETE_FROM("b_t_good_check");
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
     * @time 2021-03-19 23:03:56
     * @throws
     * @return sql
     */
    public String logicDeleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                UPDATE("b_t_good_check");
                SET("isRemoved = '1'");
            }
        }.toString();
        sql += batchJoinField("id",ids);
        return sql;
    }

    /**
     * @name update
     * @author 品讯科技
     * @param goodCheckModel
     * @Description 修改
     * @time 2021-03-19 23:03:56
     * @throws
     * @return sql
     */
    public String update(final GoodCheckModel goodCheckModel) {
        String sql = new SQL () {
            {
                UPDATE("b_t_good_check ");
                if(goodCheckModel.getProjectId() != null){
                    SET("project_id = #{projectId}");
                }
                if(goodCheckModel.getGoodId() != null){
                    SET("good_id = #{goodId}");
                }
                if(goodCheckModel.getCheckTime() != null){
                    SET("check_time = #{checkTime}");
                }
                if(goodCheckModel.getCheckNum() != null){
                    SET("check_num = #{checkNum}");
                }
                if(goodCheckModel.getApproveStatus() != null){
                    SET("approve_status = #{approveStatus}");
                }
                if(goodCheckModel.getApproverId() != null){
                    SET("approver_id = #{approverId}");
                }
                if(goodCheckModel.getApproverName() != null){
                    SET("approver_name = #{approverName}");
                }
                if(goodCheckModel.getCreateTime() != null){
                    SET("createTime = #{createTime}");
                }
                if(goodCheckModel.getCreateUserCode() != null){
                    SET("createUserCode = #{createUserCode}");
                }
                if(goodCheckModel.getCreateUserName() != null){
                    SET("createUserName = #{createUserName}");
                }
                if(goodCheckModel.getUpdateTime() != null){
                    SET("updateTime = #{updateTime}");
                }
                if(goodCheckModel.getUpdateUserCode() != null){
                    SET("updateUserCode = #{updateUserCode}");
                }
                if(goodCheckModel.getUpdateUserName() != null){
                    SET("updateUserName = #{updateUserName}");
                }
                if(goodCheckModel.getIsRemoved() != null){
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
     * @param goodId
     * @Description 根据物资Id查询数据
     * @time 2021-03-19 23:03:56
     * @throws
     * @return sql
     */
    public String findById(String goodId) {
        String sql = new SQL () {
            {
                String selectSql = new GoodProvider().selectOrInsertSql;
                SELECT(selectSql);
                SELECT("si.input_time as inputTime"); //入库时间
                SELECT("ifnull(SUM(input_num),0) as inputNum"); //入库数量
                SELECT("(select ifnull(sum(check_num),0) from b_t_good_check gc where gc.good_id = t.id) as checkNum");//已检测数量
                FROM("b_t_good t");
                LEFT_OUTER_JOIN("b_t_stock_input si on si.good_id = t.id");
                WHERE("t.id = #{goodId}");
            }
        }.toString();
        return sql;
    }

    public String findLogById(HashMap<String,String> map) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("b_t_good_check t");
                if(map.containsKey("id")){
                    WHERE("t.id = #{id}");
                }
                if(map.containsKey("goodId")){
                    WHERE("t.good_id = #{goodId}");
                }
            }
        }.toString();
        return sql;
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param goodCheckDto
     * @Description 根据条件查询数据并分页
     * @time 2021-03-19 23:03:56
     * @throws
     * @return sql
     */
    public String findAll(final GoodCheckDto goodCheckDto) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("b_t_good_check t");
                if(!StringUtil.isBlank (goodCheckDto.getId())){
                    WHERE("t.id = #{id}");
                }
                if(!StringUtil.isBlank (goodCheckDto.getProjectId())){
                    WHERE("t.project_id = #{projectId}");
                }
                if(!StringUtil.isBlank (goodCheckDto.getGoodId())){
                    WHERE("t.good_id = #{goodId}");
                }
                if(!StringUtil.isBlank (goodCheckDto.getCheckTime())){
                    WHERE("t.check_time = #{checkTime}");
                }
                if(!StringUtil.isBlank (goodCheckDto.getCheckNum())){
                    WHERE("t.check_num = #{checkNum}");
                }
                if(!StringUtil.isBlank (goodCheckDto.getApproveStatus())){
                    WHERE("t.approve_status = #{approveStatus}");
                }
                if(!StringUtil.isBlank (goodCheckDto.getApproverId())){
                    WHERE("t.approver_id = #{approverId}");
                }
                if(!StringUtil.isBlank (goodCheckDto.getApproverName())){
                    WHERE("t.approver_name = #{approverName}");
                }
                if(!StringUtil.isBlank (goodCheckDto.getCreateTime())){
                    WHERE("t.createTime = #{createTime}");
                }
                if(!StringUtil.isBlank (goodCheckDto.getCreateUserCode())){
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if(!StringUtil.isBlank (goodCheckDto.getCreateUserName())){
                    WHERE("t.createUserName = #{createUserName}");
                }
                if(!StringUtil.isBlank (goodCheckDto.getUpdateTime())){
                    WHERE("t.updateTime = #{updateTime}");
                }
                if(!StringUtil.isBlank (goodCheckDto.getUpdateUserCode())){
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (goodCheckDto.getUpdateUserName())){
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                if(!StringUtil.isBlank (goodCheckDto.getIsRemoved())){
                    WHERE("t.isRemoved = #{isRemoved}");
                }else{
                    WHERE("ifnull(t.isRemoved,'0') <> '1'"); //不包含删除项
                }
            }
        }.toString();
        return sql;
    }


    /**
     * @param goodCheckDto
     * @return Result
     * @throws
     * @name goodCheckList
     * @author 品讯科技
     * @Description 物资检测管理查询
     * @time 2021-03-28 00:09:10
     */
    public String goodCheckList(final GoodCheckDto goodCheckDto) {
        String sql = new SQL() {
            {
                String selectSql = new GoodProvider().selectOrInsertSql;
                SELECT(selectSql);
                SELECT("si.input_time as inputTime"); //入库时间
                SELECT("ifnull(SUM(ifnull(input_num,0)),0) as inputNum"); //入库数量
                SELECT("(select ifnull(sum(check_num),0) from b_t_good_check gc where gc.good_id = t.id) as checkNum");//已检测数量
                FROM("b_t_good t");
                LEFT_OUTER_JOIN("b_t_stock_input si on si.good_id = t.id and si.project_id = #{projectId}");
                //检测单位不为空
                WHERE("ifnull(t.test_unit,'') <> ''");
                //非必填查询条件
                if(!StringUtil.isBlank (goodCheckDto.getGoodCode())){
                    WHERE("t.good_code like concat(concat('%',#{goodCode}),'%')");
                }
                if(!StringUtil.isBlank (goodCheckDto.getGoodName())){
                    WHERE("t.good_name like concat(concat('%',#{goodName}),'%')");
                }
                //时间段
                if(!StringUtil.isBlank (goodCheckDto.getBeginTime())){
                    WHERE("date(si.input_time) >= date(#{beginTime})");
                }
                if(!StringUtil.isBlank (goodCheckDto.getEndTime())){
                    WHERE("date(si.input_time) <= date(#{endTime})");
                }
                GROUP_BY("t.id");
            }
        }.toString();
        return sql;
    }
}
