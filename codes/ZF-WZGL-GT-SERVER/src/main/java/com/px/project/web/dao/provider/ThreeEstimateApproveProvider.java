package com.px.project.web.dao.provider;

import com.px.project.common.base.BaseProvider;
import com.px.project.common.utils.StringUtil;
import com.px.project.web.dto.ThreeEstimateApproveDto;
import com.px.project.web.entity.ThreeEstimateApproveModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Package com.px.project.web.dao.provider
 * @ClassName ThreeEstimateApproveProvider
 * @Description ThreeEstimateApproveProvider
 * @author 品讯科技
 * @time   2021-03-24 23:29:52
 * @version V 1.0
 */
public class ThreeEstimateApproveProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql="t.id as id,"+
									 "t.project_id as projectId,"+
									 "t.status as status,"+
									 "t.three_estimate_type as threeEstimateType,"+
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
     * @param threeEstimateApproveModel
     * @Description 增加
     * @time 2021-03-24 23:29:52
     * @throws
     * @return sql
     */
    public String save(final ThreeEstimateApproveModel threeEstimateApproveModel) {
        String sql = new SQL () {
            {
                INSERT_INTO ("b_t_three_estimate_approve ");
                VALUES("id", "#{id}");
                if(!StringUtil.isBlank (threeEstimateApproveModel.getProjectId())){
                    VALUES("project_id", "#{projectId}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveModel.getStatus())){
                    VALUES("status", "#{status}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveModel.getThreeEstimateType())){
                    VALUES("three_estimate_type", "#{threeEstimateType}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveModel.getCreateTime())){
                    VALUES("createTime", "#{createTime}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveModel.getCreateUserCode())){
                    VALUES("createUserCode", "#{createUserCode}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveModel.getCreateUserName())){
                    VALUES("createUserName", "#{createUserName}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveModel.getUpdateTime())){
                    VALUES("updateTime", "#{updateTime}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveModel.getUpdateUserCode())){
                    VALUES("updateUserCode", "#{updateUserCode}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveModel.getUpdateUserName())){
                    VALUES("updateUserName", "#{updateUserName}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveModel.getIsRemoved())){
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
     * @time 2021-03-24 23:29:52
     * @throws
     * @return sql
     */
    public String deleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                DELETE_FROM("b_t_three_estimate_approve");
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
     * @time 2021-03-24 23:29:52
     * @throws
     * @return sql
     */
    public String logicDeleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                UPDATE("b_t_three_estimate_approve");
                SET("isRemoved = '1'");
            }
        }.toString();
        sql += batchJoinField("id",ids);
        return sql;
    }

    /**
     * @name update
     * @author 品讯科技
     * @param threeEstimateApproveModel
     * @Description 修改
     * @time 2021-03-24 23:29:52
     * @throws
     * @return sql
     */
    public String update(final ThreeEstimateApproveModel threeEstimateApproveModel) {
        String sql = new SQL () {
            {
                UPDATE("b_t_three_estimate_approve ");
                if(threeEstimateApproveModel.getProjectId() != null){
                    SET("project_id = #{projectId}");
                }
                if(threeEstimateApproveModel.getStatus() != null){
                    SET("status = #{status}");
                }
                if(threeEstimateApproveModel.getThreeEstimateType() != null){
                    SET("three_estimate_type = #{threeEstimateType}");
                }
                if(threeEstimateApproveModel.getCreateTime() != null){
                    SET("createTime = #{createTime}");
                }
                if(threeEstimateApproveModel.getCreateUserCode() != null){
                    SET("createUserCode = #{createUserCode}");
                }
                if(threeEstimateApproveModel.getCreateUserName() != null){
                    SET("createUserName = #{createUserName}");
                }
                if(threeEstimateApproveModel.getUpdateTime() != null){
                    SET("updateTime = #{updateTime}");
                }
                if(threeEstimateApproveModel.getUpdateUserCode() != null){
                    SET("updateUserCode = #{updateUserCode}");
                }
                if(threeEstimateApproveModel.getUpdateUserName() != null){
                    SET("updateUserName = #{updateUserName}");
                }
                if(threeEstimateApproveModel.getIsRemoved() != null){
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
     * @time 2021-03-24 23:29:52
     * @throws
     * @return sql
     */
    public String findById(@Param ("id") String id) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("b_t_three_estimate_approve t");
                WHERE("t.id = #{id}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param threeEstimateApproveDto
     * @Description 根据条件查询数据并分页
     * @time 2021-03-24 23:29:52
     * @throws
     * @return sql
     */
    public String findAll(final ThreeEstimateApproveDto threeEstimateApproveDto) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("b_t_three_estimate_approve t");
                if(!StringUtil.isBlank (threeEstimateApproveDto.getId())){
                    WHERE("t.id = #{id}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveDto.getProjectId())){
                    WHERE("t.project_id = #{projectId}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveDto.getStatus())){
                    WHERE("t.status = #{status}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveDto.getThreeEstimateType())){
                    WHERE("t.three_estimate_type = #{threeEstimateType}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveDto.getCreateTime())){
                    WHERE("t.createTime = #{createTime}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveDto.getCreateUserCode())){
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveDto.getCreateUserName())){
                    WHERE("t.createUserName = #{createUserName}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveDto.getUpdateTime())){
                    WHERE("t.updateTime = #{updateTime}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveDto.getUpdateUserCode())){
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveDto.getUpdateUserName())){
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                if(!StringUtil.isBlank (threeEstimateApproveDto.getIsRemoved())){
                    WHERE("t.isremoved = #{isRemoved}");
                }
            }
        }.toString();
        return sql;
    }

}
