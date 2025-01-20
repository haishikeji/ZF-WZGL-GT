package com.px.project.web.dao.provider;

import com.px.project.common.base.BaseProvider;
import com.px.project.common.utils.StringUtil;
import com.px.project.web.dto.ReturnGoodDto;
import com.px.project.web.entity.ReturnGoodModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Package com.px.project.web.dao.provider
 * @ClassName ReturnGoodProvider
 * @Description ReturnGoodProvider
 * @author 品讯科技
 * @time   2021-03-15 20:50:15
 * @version V 1.0
 */
public class ReturnGoodProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql="t.id as id,"+
									 "t.return_no as returnNo,"+
									 /*"t.approver_id as approverId,"+
									 "t.approver_name as approverName,"+
                                     "t.approve_status as approveStatus,"+*/
                                     "t.project_id as projectId,"+
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
     * @param returnGoodModel
     * @Description 增加
     * @time 2021-03-15 20:50:15
     * @throws
     * @return sql
     */
    public String save(final ReturnGoodModel returnGoodModel) {
        String sql = new SQL () {
            {
                INSERT_INTO ("b_t_return_good ");
                VALUES("id", "#{id}");
                if(!StringUtil.isBlank (returnGoodModel.getReturnNo())){
                    VALUES("return_no", "#{returnNo}");
                }
                if(!StringUtil.isBlank (returnGoodModel.getProjectId())){
                    VALUES("project_id", "#{projectId}");
                }
                if(!StringUtil.isBlank (returnGoodModel.getApproverId())){
                    VALUES("approver_id", "#{approverId}");
                }
                if(!StringUtil.isBlank (returnGoodModel.getApproverName())){
                    VALUES("approver_name", "#{approverName}");
                }
                if(!StringUtil.isBlank (returnGoodModel.getApproveStatus())){
                    VALUES("approve_status", "#{approveStatus}");
                }
                if(!StringUtil.isBlank (returnGoodModel.getCreateTime())){
                    VALUES("createTime", "#{createTime}");
                }
                if(!StringUtil.isBlank (returnGoodModel.getCreateUserCode())){
                    VALUES("createUserCode", "#{createUserCode}");
                }
                if(!StringUtil.isBlank (returnGoodModel.getCreateUserName())){
                    VALUES("createUserName", "#{createUserName}");
                }
                if(!StringUtil.isBlank (returnGoodModel.getUpdateTime())){
                    VALUES("updateTime", "#{updateTime}");
                }
                if(!StringUtil.isBlank (returnGoodModel.getUpdateUserCode())){
                    VALUES("updateUserCode", "#{updateUserCode}");
                }
                if(!StringUtil.isBlank (returnGoodModel.getUpdateUserName())){
                    VALUES("updateUserName", "#{updateUserName}");
                }
                if(!StringUtil.isBlank (returnGoodModel.getIsRemoved())){
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
     * @time 2021-03-15 20:50:15
     * @throws
     * @return sql
     */
    public String deleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                DELETE_FROM("b_t_return_good");
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
     * @time 2021-03-15 20:50:15
     * @throws
     * @return sql
     */
    public String logicDeleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                UPDATE("b_t_return_good");
                SET("isRemoved = '1'");
            }
        }.toString();
        sql += batchJoinField("id",ids);
        return sql;
    }

    /**
     * @name update
     * @author 品讯科技
     * @param returnGoodModel
     * @Description 修改
     * @time 2021-03-15 20:50:15
     * @throws
     * @return sql
     */
    public String update(final ReturnGoodModel returnGoodModel) {
        String sql = new SQL () {
            {
                UPDATE("b_t_return_good ");
                    if(returnGoodModel.getReturnNo() != null){
                        SET("return_no = #{returnNo}");
                    }
                    if(returnGoodModel.getApproverId() != null){
                        SET("approver_id = #{approverId}");
                    }
                    if(returnGoodModel.getApproverName() != null){
                        SET("approver_name = #{approverName}");
                    }
                    if(returnGoodModel.getApproveStatus() != null){
                        SET("approve_status = #{approveStatus}");
                    }
                    if(returnGoodModel.getCreateTime() != null){
                        SET("createTime = #{createTime}");
                    }
                    if(returnGoodModel.getCreateUserCode() != null){
                        SET("createUserCode = #{createUserCode}");
                    }
                    if(returnGoodModel.getCreateUserName() != null){
                        SET("createUserName = #{createUserName}");
                    }
                    if(returnGoodModel.getUpdateTime() != null){
                        SET("updateTime = #{updateTime}");
                    }
                    if(returnGoodModel.getUpdateUserCode() != null){
                        SET("updateUserCode = #{updateUserCode}");
                    }
                    if(returnGoodModel.getUpdateUserName() != null){
                        SET("updateUserName = #{updateUserName}");
                    }
                    if(returnGoodModel.getIsRemoved() != null){
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
     * @time 2021-03-15 20:50:15
     * @throws
     * @return sql
     */
    public String findById(@Param ("id") String id) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("b_t_return_good t");
                WHERE("t.id = #{id}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param returnGoodDto
     * @Description 根据条件查询数据并分页
     * @time 2021-03-15 20:50:15
     * @throws
     * @return sql
     */
    public String findAll(final ReturnGoodDto returnGoodDto) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("b_t_return_good t");
                LEFT_OUTER_JOIN("t_workflow wf on wf.bus_id = t.id"); //审批状态

                if(!StringUtil.isBlank (returnGoodDto.getId())){
                    WHERE("t.id = #{id}");
                }
                if(!StringUtil.isBlank (returnGoodDto.getProjectId())){
                    WHERE("t.project_id = #{projectId}");
                }
                if(!StringUtil.isBlank (returnGoodDto.getReturnNo())){
                    WHERE("t.return_no like concat(concat('%',#{returnNo}),'%')");
                }
                /*if(!StringUtil.isBlank (returnGoodDto.getApproverId())){
                    WHERE("t.approver_id = #{approverId}");
                }
                if(!StringUtil.isBlank (returnGoodDto.getApproverName())){
                    WHERE("t.approver_name = #{approverName}");
                }*/
                if(!StringUtil.isBlank (returnGoodDto.getApproveStatus())){
                    WHERE("wf.approve_status = #{approveStatus}");
                }
                if(!StringUtil.isBlank (returnGoodDto.getCreateTime())){
                    WHERE("t.createTime = #{createTime}");
                }
                if(!StringUtil.isBlank (returnGoodDto.getCreateUserCode())){
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if(!StringUtil.isBlank (returnGoodDto.getCreateUserName())){
                    WHERE("t.createUserName = #{createUserName}");
                }
                if(!StringUtil.isBlank (returnGoodDto.getUpdateTime())){
                    WHERE("t.updateTime = #{updateTime}");
                }
                if(!StringUtil.isBlank (returnGoodDto.getUpdateUserCode())){
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (returnGoodDto.getUpdateUserName())){
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                if(!StringUtil.isBlank (returnGoodDto.getIsRemoved())){
                    WHERE("t.isRemoved = #{isRemoved}");
                }else{
                    WHERE("ifnull(t.isRemoved,'0') <> '1'"); //不包含删除项
                }
            }
        }.toString();
        return sql;
    }

}
