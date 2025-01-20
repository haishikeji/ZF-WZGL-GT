package com.px.project.web.dao.provider;

import com.px.project.common.base.BaseProvider;
import com.px.project.common.utils.StringUtil;
import com.px.project.web.dto.ThreeEstimateDto;
import com.px.project.web.entity.ThreeEstimateModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.HashMap;

/**
 * @author 品讯科技
 * @version V 1.0
 * @Package com.px.project.web.dao.provider
 * @ClassName threeEstimateProvider
 * @Description threeEstimateProvider
 * @time 2021-03-09 22:12:47
 */
public class ThreeEstimateProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql = "t.id as id," +
            "t.project_id as projectId," +
            "t.good_id as goodId," +
            "t.three_estimate_type as threeEstimateType," +
            "t.specs_model_name as specsModelName," +
            "t.unit as unit," +
            "t.remarks as remarks," +
            "t.status as status," +
            "t.createTime as createTime," +
            "t.createUserCode as createUserCode," +
            "t.createUserName as createUserName," +
            "t.updateTime as updateTime," +
            "t.updateUserCode as updateUserCode," +
            "t.updateUserName as updateUserName," +
            "t.isremoved as isRemoved";

    /**
     * @param threeEstimateModel
     * @return sql
     * @throws
     * @name save
     * @author 品讯科技
     * @Description 增加
     * @time 2021-03-09 22:12:47
     */
    public String save(final ThreeEstimateModel threeEstimateModel) {
        String sql = new SQL() {
            {
                INSERT_INTO("b_t_three_estimate ");
                VALUES("id", "#{id}");
                if (!StringUtil.isBlank(threeEstimateModel.getProjectId())) {
                    VALUES("project_id", "#{projectId}");
                }
                if (!StringUtil.isBlank(threeEstimateModel.getGoodId())) {
                    VALUES("good_id", "#{goodId}");
                }
                if (!StringUtil.isBlank(threeEstimateModel.getThreeEstimateType())) {
                    VALUES("three_estimate_type", "#{threeEstimateType}");
                }
                if (!StringUtil.isBlank(threeEstimateModel.getSpecsModelName())) {
                    VALUES("specs_model_name", "#{specsModelName}");
                }
                if (!StringUtil.isBlank(threeEstimateModel.getUnit())) {
                    VALUES("unit", "#{unit}");
                }
                if(!StringUtil.isBlank (threeEstimateModel.getMajorId())){
                    VALUES("major_id", "#{majorId}");
                }
                if (!StringUtil.isBlank(threeEstimateModel.getRemarks())) {
                    VALUES("remarks", "#{remarks}");
                }
                if (!StringUtil.isBlank(threeEstimateModel.getStatus())) {
                    VALUES("status", "#{status}");
                } else {
                    VALUES("status", "1");
                }
                if (!StringUtil.isBlank(threeEstimateModel.getCreateTime())) {
                    VALUES("createTime", "#{createTime}");
                }
                if (!StringUtil.isBlank(threeEstimateModel.getCreateUserCode())) {
                    VALUES("createUserCode", "#{createUserCode}");
                }
                if (!StringUtil.isBlank(threeEstimateModel.getCreateUserName())) {
                    VALUES("createUserName", "#{createUserName}");
                }
                if (!StringUtil.isBlank(threeEstimateModel.getUpdateTime())) {
                    VALUES("updateTime", "#{updateTime}");
                }
                if (!StringUtil.isBlank(threeEstimateModel.getUpdateUserCode())) {
                    VALUES("updateUserCode", "#{updateUserCode}");
                }
                if (!StringUtil.isBlank(threeEstimateModel.getUpdateUserName())) {
                    VALUES("updateUserName", "#{updateUserName}");
                }
                if (!StringUtil.isBlank(threeEstimateModel.getIsRemoved())) {
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
     * @throws
     * @name deleteByIds
     * @author 品讯科技
     * @Description 物理删除
     * @time 2021-03-09 22:12:47
     */
    public String deleteByIds(@Param("ids") String ids) {
        String sql = new SQL() {
            {
                DELETE_FROM("b_t_three_estimate");
            }
        }.toString();
        sql += batchJoinField("id", ids);
        return sql;
    }


    /**
     * @param ids
     * @return sql
     * @throws
     * @name logicDeleteByIds
     * @author 品讯科技
     * @Description 逻辑删除
     * @time 2021-03-09 22:12:47
     */
    public String logicDeleteByIds(@Param("ids") String ids) {
        String sql = new SQL() {
            {
                UPDATE("b_t_three_estimate");
                SET("isRemoved = '1'");
            }
        }.toString();
        sql += batchJoinField("id", ids);
        return sql;
    }

    /**
     * @param threeEstimateModel
     * @return sql
     * @throws
     * @name update
     * @author 品讯科技
     * @Description 修改
     * @time 2021-03-09 22:12:47
     */
    public String update(final ThreeEstimateModel threeEstimateModel) {
        String sql = new SQL() {
            {
                UPDATE("b_t_three_estimate ");
                if (threeEstimateModel.getProjectId() != null) {
                    SET("project_id = #{projectId}");
                }
                if (threeEstimateModel.getGoodId() != null) {
                    SET("good_id = #{goodId}");
                }
                if (threeEstimateModel.getThreeEstimateType() != null) {
                    SET("three_estimate_type = #{threeEstimateType}");
                }
                if (threeEstimateModel.getSpecsModelName() != null) {
                    SET("specs_model_name = #{specsModelName}");
                }
                if (threeEstimateModel.getUnit() != null) {
                    SET("unit = #{unit}");
                }
                if (threeEstimateModel.getRemarks() != null) {
                    SET("remarks = #{remarks}");
                }
                if (threeEstimateModel.getStatus() != null) {
                    SET("status = #{status}");
                }
                if (threeEstimateModel.getCreateTime() != null) {
                    SET("createTime = #{createTime}");
                }
                if (threeEstimateModel.getCreateUserCode() != null) {
                    SET("createUserCode = #{createUserCode}");
                }
                if (threeEstimateModel.getCreateUserName() != null) {
                    SET("createUserName = #{createUserName}");
                }
                if (threeEstimateModel.getUpdateTime() != null) {
                    SET("updateTime = #{updateTime}");
                }
                if (threeEstimateModel.getUpdateUserCode() != null) {
                    SET("updateUserCode = #{updateUserCode}");
                }
                if (threeEstimateModel.getUpdateUserName() != null) {
                    SET("updateUserName = #{updateUserName}");
                }
                if (threeEstimateModel.getIsRemoved() != null) {
                    SET("isremoved = #{isRemoved}");
                }
                if(!StringUtil.isBlank (threeEstimateModel.getMajorId())){
                	SET("major_id = #{majorId}");
                }
                WHERE("id = #{id}");
            }
        }.toString();
        return sql;
    }

    /**
     * @param id
     * @return sql
     * @throws
     * @name findById
     * @author 品讯科技
     * @Description 根据Id查询概算量数据
     * @time 2021-03-09 22:12:47
     */
    public String findById(@Param("id") String id) {
        String sql = new SQL() {
            {
                SELECT("te.id,p.project_name as projectName,p.project_no as projectNo,p.id as projectId,g.good_code as goodCode,g.good_name as goodName,te.specs_model_name as specsModelName");
                SELECT("g.id as goodId,te.three_estimate_type as threeEstimateType,te.unit,sum(ifnull(bu.budget_unit_value,0)) as total");
                SELECT("te.createTime,te.createUserName,te.createUserCode,te.remarks");
                FROM("b_t_three_estimate te");
                LEFT_OUTER_JOIN("b_t_good g on g.id = te.good_id");
                LEFT_OUTER_JOIN("b_t_good_type gt on gt.id = g.good_type");
                LEFT_OUTER_JOIN("s_t_project p on p.id = te.project_id");
                LEFT_OUTER_JOIN("b_t_budget_unit bu on te.id = bu.three_estimate_id");
                LEFT_OUTER_JOIN("b_t_unit u on bu.budget_unit_id = u.id");
                //必填参数
                WHERE("te.id = #{id}");

                //停用状态
                WHERE("ifnull(te.status,'1') ='1' and ifnull(te.isRemoved,'') <>'1'");
                WHERE("ifnull(g.status,'1') ='1' and ifnull(g.isRemoved,'') <>'1'");
                WHERE("ifnull(gt.status,'1') ='1' and ifnull(gt.isRemoved,'') <>'1'");
                WHERE("ifnull(p.status,'1') ='1' and ifnull(p.isRemoved,'') <>'1'");
                WHERE("ifnull(bu.status,'1') ='1' and ifnull(bu.isRemoved,'') <>'1'");
                WHERE("ifnull(u.status,'1') ='1' and ifnull(u.isRemoved,'') <>'1'");

                //GROUP_BY("te.id,p.project_name,p.project_no,g.good_code,g.good_name,g.id,te.three_estimate_type,te.unit,te.specs_model_name");
                //GROUP_BY("p.id,te.createTime,te.createUserName,te.createUserCode");
            }
        }.toString();
        return sql;
    }

    /**
     * @param threeEstimateDto
     * @return sql
     * @throws
     * @name findAll
     * @author 品讯科技
     * @Description 根据条件查询数据并分页
     * @time 2021-03-09 22:12:47
     */
    public String findAll(final ThreeEstimateDto threeEstimateDto) {
        String sql = new SQL() {
            {
                SELECT(selectOrInsertSql);
                FROM("b_t_three_estimate t left join b_t_good  g on t.good_id = g.id " );
                if (!StringUtil.isBlank(threeEstimateDto.getId())) {
                    WHERE("t.id = #{id}");
                }
                if (!StringUtil.isBlank(threeEstimateDto.getProjectId())) {
                    WHERE("t.project_id = #{projectId}");
                }
                if (!StringUtil.isBlank(threeEstimateDto.getGoodId())) {
                    WHERE("t.good_id = #{goodId}");
                }
                if (!StringUtil.isBlank(threeEstimateDto.getThreeEstimateType())) {
                    WHERE("t.three_estimate_type = #{threeEstimateType}");
                }
                if (!StringUtil.isBlank(threeEstimateDto.getSpecsModelName())) {
                    WHERE("t.specs_model_name = #{specsModelName}");
                }
                if (!StringUtil.isBlank(threeEstimateDto.getUnit())) {
                    WHERE("t.unit = #{unit}");
                }
                if (!StringUtil.isBlank(threeEstimateDto.getRemarks())) {
                    WHERE("t.remarks = #{remarks}");
                }
                if (!StringUtil.isBlank(threeEstimateDto.getStatus())) {
                    WHERE("t.status = #{status}");
                }
                if (!StringUtil.isBlank(threeEstimateDto.getCreateTime())) {
                    WHERE("t.createTime = #{createTime}");
                }
                if (!StringUtil.isBlank(threeEstimateDto.getCreateUserCode())) {
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if (!StringUtil.isBlank(threeEstimateDto.getCreateUserName())) {
                    WHERE("t.createUserName = #{createUserName}");
                }
                if (!StringUtil.isBlank(threeEstimateDto.getUpdateTime())) {
                    WHERE("t.updateTime = #{updateTime}");
                }
                if (!StringUtil.isBlank(threeEstimateDto.getUpdateUserCode())) {
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if (!StringUtil.isBlank(threeEstimateDto.getUpdateUserName())) {
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                if (!StringUtil.isBlank(threeEstimateDto.getMajorId())) {
                    WHERE("t.major_id = #{majorId}");
                }
                if (!StringUtil.isBlank(threeEstimateDto.getGoodName())) {
                    WHERE("g.good_name like concat(concat('%',#{goodName}),'%')");
                }
                if (!StringUtil.isBlank(threeEstimateDto.getGoodCode())) {
                    WHERE("g.good_code like concat(concat('%',#{goodCode}),'%')");
                }
                if (!StringUtil.isBlank(threeEstimateDto.getSpecsModelName())) {
                    WHERE("t.specs_model_name like concat(concat('%',#{specsModelName}),'%')");
                }
                if (!StringUtil.isBlank(threeEstimateDto.getIsRemoved())) {
                    WHERE("t.isremoved = #{isRemoved}");
                }
                
            }
        }.toString();
        return sql;
    }
//    public String findAllMajorAndGoodIds(String majorId,   String goodIds) {
//    	String sql = new SQL() {
//    		{
//    			SELECT(selectOrInsertSql +" ");
//    			  FROM("b_t_three_estimate t left join b_t_three_estimate  g on t.three = g.id " );
//    			WHERE(batchJoinFieldNoPrefix("good_id", goodIds));
//    			WHERE(batchJoinFieldNoPrefix("good_id", goodIds));
//    		}
//    	}.toString();
//    	return sql;
//    }
    public String findThreeInfo(final ThreeEstimateDto threeEstimateDto) {
    	String querySql = " t.id , t.good_id as goodId ,t.major_id ,t.three_estimate_type as threeEstimateType ,t.major_id as majorId,\r\n" + 
    			"good.good_name as goodName ,good.measure_unit as unit,good.specs_model_name   as specsModelName,\r\n" + 
    			"good.good_code as goodCode,type.type_name as typeName, major.major_name as majorName\r\n" + 
    			"from b_t_three_estimate t \r\n" + 
    			"left join b_t_good good on t.good_id = good.id\r\n" + 
    			"left join b_t_good_type type on good.good_type =  type.id\r\n" + 
    			"left join b_t_major major on t.major_id  = major.id ";
    	String sql = new SQL() {
    		{
    			SELECT(querySql);
    			if (!StringUtil.isBlank(threeEstimateDto.getId())) {
    				WHERE("t.id = #{id}");
    			}
//    			if (!StringUtil.isBlank(threeEstimateDto.getMajorId())) {
//    				WHERE("t.major_id = #{majorId}");
//    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getProjectId())) {
    				WHERE("t.project_id = #{projectId}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getGoodId())) {
    				WHERE("t.good_id = #{goodId}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getThreeEstimateType())) {
    				WHERE("t.three_estimate_type = #{threeEstimateType}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getSpecsModelName())) {
    				WHERE("t.specs_model_name = #{specsModelName}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getUnit())) {
    				WHERE("t.unit = #{unit}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getRemarks())) {
    				WHERE("t.remarks = #{remarks}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getStatus())) {
    				WHERE("t.status = #{status}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getCreateTime())) {
    				WHERE("t.createTime = #{createTime}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getCreateUserCode())) {
    				WHERE("t.createUserCode = #{createUserCode}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getCreateUserName())) {
    				WHERE("t.createUserName = #{createUserName}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getUpdateTime())) {
    				WHERE("t.updateTime = #{updateTime}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getUpdateUserCode())) {
    				WHERE("t.updateUserCode = #{updateUserCode}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getUpdateUserName())) {
    				WHERE("t.updateUserName = #{updateUserName}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getMajorId())) {
    				WHERE("t.major_id = #{majorId}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getGoodName())) {
    				WHERE("good.good_name like concat(concat('%',#{goodName}),'%')");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getGoodCode())) {
    				WHERE("good.good_code like concat(concat('%',#{goodCode}),'%')");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getSpecsModelName())) {
    				WHERE("t.specs_model_name like concat(concat('%',#{specsModelName}),'%')");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getIsRemoved())) {
    				WHERE("t.isremoved = #{isRemoved}");
    			}
    			
    		}
    	}.toString();
    	return sql;
    }
    public String countData(final ThreeEstimateDto threeEstimateDto) {
    	String querySql = " count(*) " + 
    			"from b_t_three_estimate t \r\n" + 
    			"left join b_t_good good on t.good_id = good.id\r\n" ;
    	String sql = new SQL() {
    		{
    			SELECT(querySql);
    			if (!StringUtil.isBlank(threeEstimateDto.getId())) {
    				WHERE("t.id = #{id}");
    			}
//    			if (!StringUtil.isBlank(threeEstimateDto.getMajorId())) {
//    				WHERE("t.major_id = #{majorId}");
//    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getProjectId())) {
    				WHERE("t.project_id = #{projectId}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getGoodId())) {
    				WHERE("t.good_id = #{goodId}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getThreeEstimateType())) {
    				WHERE("t.three_estimate_type = #{threeEstimateType}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getSpecsModelName())) {
    				WHERE("t.specs_model_name = #{specsModelName}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getUnit())) {
    				WHERE("t.unit = #{unit}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getRemarks())) {
    				WHERE("t.remarks = #{remarks}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getStatus())) {
    				WHERE("t.status = #{status}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getCreateTime())) {
    				WHERE("t.createTime = #{createTime}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getCreateUserCode())) {
    				WHERE("t.createUserCode = #{createUserCode}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getCreateUserName())) {
    				WHERE("t.createUserName = #{createUserName}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getUpdateTime())) {
    				WHERE("t.updateTime = #{updateTime}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getUpdateUserCode())) {
    				WHERE("t.updateUserCode = #{updateUserCode}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getUpdateUserName())) {
    				WHERE("t.updateUserName = #{updateUserName}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getMajorId())) {
    				WHERE("t.major_id = #{majorId}");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getGoodName())) {
    				WHERE("good.good_name like concat(concat('%',#{goodName}),'%')");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getGoodCode())) {
    				WHERE("good.good_code like concat(concat('%',#{goodCode}),'%')");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getSpecsModelName())) {
    				WHERE("t.specs_model_name like concat(concat('%',#{specsModelName}),'%')");
    			}
    			if (!StringUtil.isBlank(threeEstimateDto.getIsRemoved())) {
    				WHERE("t.isremoved = #{isRemoved}");
    			}
    			
    		}
    	}.toString();
    	return sql;
    }

    /**
     * @param paramMap
     * @return sql
     * @throws
     * @name BudgetEstimateList
     * @author 品讯科技
     * @Description 根据条件查询概算量信息并分页（单层动态列）
     * @time 2021-03-10 23:01:39
     */
    public String BudgetEstimateList(final HashMap<String, Object> paramMap) {
        StringBuilder sb = new StringBuilder();
        //概算量查询
        sb.append("call p_budget_estimate_query(#{projectId,mode=IN,jdbcType=VARCHAR}," +
                "#{threeEstimateType,mode=IN,jdbcType=VARCHAR}," + //三量类型 0 概算量 1 图纸量 2 定测量
                "#{goodCode,mode=IN,jdbcType=VARCHAR}," +  //物资编号
                "#{goodName,mode=IN,jdbcType=VARCHAR}," + //物资名称
                "#{specsModel,mode=IN,jdbcType=VARCHAR})"); //规格型号
        return sb.toString();
    }

    /**
     * @param paramMap
     * @return sql
     * @throws
     * @name StationScheduleList
     * @author 品讯科技
     * @Description 根据条件查询图纸量、定测量信息并分页（单层动态列）
     * @time 2021-03-12 13:45:37
     */
    public String StationScheduleList(final HashMap<String, Object> paramMap) {
        StringBuilder sb = new StringBuilder();
        //图纸量、定测量查询
        sb.append("call p_station_schedule_query(#{projectId,mode=IN,jdbcType=VARCHAR}," +
                "#{threeEstimateType,mode=IN,jdbcType=VARCHAR}," + //三量类型 0 概算量 1 图纸量 2 定测量
                "#{goodCode,mode=IN,jdbcType=VARCHAR}," +  //物资编号
                "#{goodName,mode=IN,jdbcType=VARCHAR}," + //物资名称
                "#{specsModel,mode=IN,jdbcType=VARCHAR},"+//规格型号
                "#{page,mode=IN,jdbcType=BIGINT},"+//规格型号
                "#{size,mode=IN,jdbcType=BIGINT},"+//规格型号
    			"#{majorId,mode=IN,jdbcType=VARCHAR})"); //规格型号
        return sb.toString();
    }

    /**
     * @param id
     * @param threeEstimateType 三量类型 0 概算量 1 图纸量 2 定测量
     * @return sql
     * @throws
     * @name findStationSchedule
     * @author 品讯科技
     * @Description 根据Id查询(图纸量 、 定测量)数据
     * @time 2021-03-12 16:42:09
     */
    public String findStationSchedule(@Param("id") String id, @Param("threeEstimateType") String threeEstimateType) {
        String sql = new SQL() {
            {
                SELECT("te.major_id as majorId,ma.major_name as majorName, te.id,p.project_name as projectName,p.project_no as projectNo,p.id as projectId,g.good_code as goodCode,g.good_name as goodName,te.specs_model_name as specsModelName");
                SELECT("g.id as goodId,te.three_estimate_type as threeEstimateType,te.unit,sum(ifnull(ss.station_schedule_value,0)) as total");
                SELECT("te.createTime,te.createUserName,te.createUserCode,te.remarks");
                FROM("b_t_three_estimate te");
                LEFT_OUTER_JOIN("b_t_good g on g.id = te.good_id");
                LEFT_OUTER_JOIN("b_t_good_type gt on gt.id = g.good_type");
                LEFT_OUTER_JOIN("s_t_project p on p.id = te.project_id");
                LEFT_OUTER_JOIN("b_t_station_schedule ss on te.id = ss.three_estimate_id");
                LEFT_OUTER_JOIN("b_t_major ma on te.major_id = ma.id");
                LEFT_OUTER_JOIN("b_t_station_interval si on ss.station_schedule_id = si.id");
                //必填参数
                WHERE("te.id = #{id}");
                WHERE("te.three_estimate_type = #{threeEstimateType}");
                //停用状态
                WHERE("ifnull(te.status,'1') ='1' and ifnull(te.isRemoved,'') <>'1'");
                WHERE("ifnull(g.status,'1') ='1' and ifnull(g.isRemoved,'') <>'1'");
                WHERE("ifnull(gt.status,'1') ='1' and ifnull(gt.isRemoved,'') <>'1'");
                WHERE("ifnull(p.status,'1') ='1' and ifnull(p.isRemoved,'') <>'1'");
                WHERE("ifnull(ss.status,'1') ='1' and ifnull(ss.isRemoved,'') <>'1'");
                WHERE("ifnull(si.status,'1') ='1' and ifnull(si.isRemoved,'') <>'1'");
            }
        }.toString();
        return sql;
    }

    /**
     * @param projectId
     * @return sql
     * @throws
     * @name sumBudgetEstimateNum
     * @author 品讯科技
     * @Description 统计概算量合计
     * @time 2021-04-09 22:45:00
     */
    public String sumBudgetEstimateNum(final String projectId) {
        String sql = new SQL() {
            {
                SELECT("ifnull(sum(ifnull(bu.budget_unit_value,0)),0) as num");
                FROM("b_t_three_estimate t");
                INNER_JOIN("b_t_budget_unit bu on t.id = bu.three_estimate_id");
                //审核通过
                LEFT_OUTER_JOIN("t_workflow wf on wf.bus_id = t.id and ifnull(wf.approve_status,'') = '3'");
                WHERE("t.project_id = #{projectId}");
                WHERE("ifnull(t.isRemoved,'0')<>'1' ");
            }
        }.toString();
        return sql;
    }

    /**
     * @param projectId
     * @return sql
     * @throws
     * @name sumStationScheduleNum
     * @author 品讯科技
     * @Description 统计图纸量，定测量合计
     * @time 2021-04-09 22:45:00
     */
    public String sumStationScheduleNum(final String projectId, String threeEstimateType) {
        String sql = new SQL() {
            {
                SELECT("ifnull(sum(ifnull(ss.station_schedule_value,0)),0) as num");
                FROM("b_t_three_estimate t");
                INNER_JOIN("b_t_station_schedule ss on t.id = ss.three_estimate_id");
                //审核通过
                LEFT_OUTER_JOIN("t_workflow wf on wf.bus_id = t.id and ifnull(wf.approve_status,'') = '3'");
                WHERE("t.project_id = #{projectId}");
                WHERE("t.three_estimate_type = #{threeEstimateType}");
                WHERE("ifnull(t.isRemoved,'0')<>'1' ");
            }
        }.toString();
        return sql;
    }
}
