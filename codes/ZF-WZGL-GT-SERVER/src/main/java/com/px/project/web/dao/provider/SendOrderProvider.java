package com.px.project.web.dao.provider;

import com.px.project.common.base.BaseProvider;
import com.px.project.common.utils.StringUtil;
import com.px.project.web.dto.SendOrderDto;
import com.px.project.web.entity.SendOrderModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author 品讯科技
 * @version V 1.0
 * @Package com.px.project.web.dao.provider
 * @ClassName SendOrderProvider
 * @Description SendOrderProvider
 * @time 2021-03-16 14:20:15
 */
public class SendOrderProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql = "t.id as id," +
            "t.project_id as projectId," +
            "t.send_order_no as sendOrderNo," +
            /*"t.store_id as storeId,"+*/
            "t.pick_company_id as pickCompanyId," +
            "t.pick_user as pickUser," +
            "t.station_interval_id as stationIntervalId," + "t.approve_status as approveStatus,"+
									/* "t.approver_id as approverId,"+
									 "t.approver_name as approverName,"+
									 "t.approve_status as approveStatus,"+*/
            "t.createTime as createTime," +
            "t.createUserCode as createUserCode," +
            "t.createUserName as createUserName," +
            "t.updateTime as updateTime," +
            "t.updateUserCode as updateUserCode," +
            "t.updateUserName as updateUserName," +
            "t.seq_no as seqNo," +
            "t.hand_no as handNo," +
            "t.major_id as majorId," +
            "t.isremoved as isRemoved";

    /**
     * @param sendOrderModel
     * @return sql
     * @throws
     * @name save
     * @author 品讯科技
     * @Description 增加
     * @time 2021-03-16 14:20:15
     */
    public String save(final SendOrderModel sendOrderModel) {
        String sql = new SQL() {
            {
                INSERT_INTO("b_t_send_order ");
                VALUES("id", "#{id}");
                if (!StringUtil.isBlank(sendOrderModel.getPickUser())) {
                    VALUES("pick_user", "#{pickUser}");
                }
                if (!StringUtil.isBlank(sendOrderModel.getProjectId())) {
                    VALUES("project_id", "#{projectId}");
                }
                if (!StringUtil.isBlank(sendOrderModel.getSendOrderNo())) {
                    VALUES("send_order_no", "#{sendOrderNo}");
                }
               /* if(!StringUtil.isBlank (sendOrderModel.getStoreId())){
                    VALUES("store_id", "#{storeId}");
                }*/
                if (!StringUtil.isBlank(sendOrderModel.getPickCompanyId())) {
                    VALUES("pick_company_id", "#{pickCompanyId}");
                }
                if (!StringUtil.isBlank(sendOrderModel.getStationIntervalId())) {
                    VALUES("station_interval_id", "#{stationIntervalId}");
                }
                if (!StringUtil.isBlank(sendOrderModel.getApproverId())) {
                    VALUES("approver_id", "#{approverId}");
                }
                if (!StringUtil.isBlank(sendOrderModel.getApproverName())) {
                    VALUES("approver_name", "#{approverName}");
                }
                if (!StringUtil.isBlank(sendOrderModel.getApproveStatus())) {
                    VALUES("approve_status", "#{approveStatus}");
                }
                if (!StringUtil.isBlank(sendOrderModel.getCreateTime())) {
                    VALUES("createTime", "#{createTime}");
                }
                if (!StringUtil.isBlank(sendOrderModel.getCreateUserCode())) {
                    VALUES("createUserCode", "#{createUserCode}");
                }
                if (!StringUtil.isBlank(sendOrderModel.getCreateUserName())) {
                    VALUES("createUserName", "#{createUserName}");
                }
                if (!StringUtil.isBlank(sendOrderModel.getUpdateTime())) {
                    VALUES("updateTime", "#{updateTime}");
                }
                if (!StringUtil.isBlank(sendOrderModel.getUpdateUserCode())) {
                    VALUES("updateUserCode", "#{updateUserCode}");
                }
                if (!StringUtil.isBlank(sendOrderModel.getUpdateUserName())) {
                    VALUES("updateUserName", "#{updateUserName}");
                }
                if (!StringUtil.isBlank(sendOrderModel.getHandNo())) {
                	VALUES("hand_no", "#{handNo}");
                }
                if (!StringUtil.isBlank(sendOrderModel.getMajorId())) {
                	VALUES("major_id", "#{majorId}");
                }
                if (!StringUtil.isBlank(sendOrderModel.getIsRemoved())) {
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
     * @time 2021-03-16 14:20:15
     */
    public String deleteByIds(@Param("ids") String ids) {
        String sql = new SQL() {
            {
                DELETE_FROM("b_t_send_order");
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
     * @time 2021-03-16 14:20:15
     */
    public String logicDeleteByIds(@Param("ids") String ids) {
        String sql = new SQL() {
            {
                UPDATE("b_t_send_order");
                SET("isRemoved = '1'");
            }
        }.toString();
        sql += batchJoinField("id", ids);
        return sql;
    }

    /**
     * @param sendOrderModel
     * @return sql
     * @throws
     * @name update
     * @author 品讯科技
     * @Description 修改
     * @time 2021-03-16 14:20:15
     */
    public String update(final SendOrderModel sendOrderModel) {
        String sql = new SQL() {
            {
                UPDATE("b_t_send_order ");
                if (sendOrderModel.getProjectId() != null) {
                    SET("project_id = #{projectId}");
                }
                if (sendOrderModel.getPickUser() != null) {
                    SET("pick_user = #{pickUser}");
                }
                if (sendOrderModel.getSendOrderNo() != null) {
                    SET("send_order_no = #{sendOrderNo}");
                }
                  /*  if(sendOrderModel.getStoreId() != null){
                        SET("store_id = #{storeId}");
                    }*/
                if (sendOrderModel.getPickCompanyId() != null) {
                    SET("pick_company_id = #{pickCompanyId}");
                }
                if (sendOrderModel.getStationIntervalId() != null) {
                    SET("station_interval_id = #{stationIntervalId}");
                }
                if (sendOrderModel.getApproverId() != null) {
                    SET("approver_id = #{approverId}");
                }
                if (sendOrderModel.getApproverName() != null) {
                    SET("approver_name = #{approverName}");
                }
                if (sendOrderModel.getApproveStatus() != null) {
                    SET("approve_status = #{approveStatus}");
                }
                if (sendOrderModel.getCreateTime() != null) {
                    SET("createTime = #{createTime}");
                }
                if (sendOrderModel.getCreateUserCode() != null) {
                    SET("createUserCode = #{createUserCode}");
                }
                if (sendOrderModel.getCreateUserName() != null) {
                    SET("createUserName = #{createUserName}");
                }
                if (sendOrderModel.getUpdateTime() != null) {
                    SET("updateTime = #{updateTime}");
                }
                if (sendOrderModel.getUpdateUserCode() != null) {
                    SET("updateUserCode = #{updateUserCode}");
                }
                if (sendOrderModel.getUpdateUserName() != null) {
                    SET("updateUserName = #{updateUserName}");
                }
                if (sendOrderModel.getIsRemoved() != null) {
                    SET("isremoved = #{isRemoved}");
                }
                if (sendOrderModel.getHandNo() != null) {
                	SET("hand_no = #{handNo}");
                }
                if (sendOrderModel.getMajorId() != null) {
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
     * @Description 根据Id查询数据
     * @time 2021-03-16 14:20:15
     */
    public String findById(@Param("id") String id) {
        String sql = new SQL() {
            {
                SELECT(selectOrInsertSql);
                SELECT("si.station_interval_code as stationIntervalCode,si.station_interval_name as stationIntervalName");
                SELECT("p.pick_company_no as pickCompanyNo,p.pick_company_name as pickCompanyName ,btm.major_name as majorName");
                FROM("b_t_send_order t left join b_t_major btm on t.major_id = btm.id");
                LEFT_OUTER_JOIN("b_t_station_interval si on t.station_interval_id = si.id and ifnull(si.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("b_t_pick_company p on p.id = t.pick_company_id and ifnull(p.isRemoved,'0')<>'1'");
                WHERE("t.id = #{id}");
            }
        }.toString();
        return sql;
    }

    /**
     * @param sendOrderDto
     * @return sql
     * @throws
     * @name findAll
     * @author 品讯科技
     * @Description 根据条件查询数据并分页
     * @time 2021-03-16 14:20:15
     */
    public String findAll(final SendOrderDto sendOrderDto) {
        String sql = new SQL() {
            {
                SELECT(selectOrInsertSql+",ogd.count,s.major_name as majorName ");
                /* SELECT("s.store_no as storeNo,s.store_name as storeName");*/
                SELECT("si.station_interval_code as stationIntervalCode,si.station_interval_name as stationIntervalName");
                SELECT("p.pick_company_no as pickCompanyNo,p.pick_company_name as pickCompanyName");

                FROM("b_t_send_order t left join b_t_major s on t.major_id = s.id  ");
//                LEFT_JOIN("b_t_major s on t.major_id = s.id  ");
                LEFT_OUTER_JOIN("(select send_order_id ,sum(num) as count from b_t_send_order_detail group by send_order_id ) ogd on ogd.send_order_id = t.id");
                LEFT_OUTER_JOIN("b_t_station_interval si on t.station_interval_id = si.id and ifnull(si.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("b_t_pick_company p on p.id = t.pick_company_id and ifnull(p.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("t_workflow wf on wf.bus_id = t.id"); //审批状态
                WHERE("t.project_id = #{projectId}");

                if (!StringUtil.isBlank(sendOrderDto.getId())) {
                    WHERE("t.id = #{id}");
                }
                if (!StringUtil.isBlank(sendOrderDto.getPickUser())) {
                    WHERE("t.pick_user like concat(concat('%',#{pickUser}),'%')");
                }
                if (!StringUtil.isBlank(sendOrderDto.getSendOrderNo())) {
                    WHERE("t.send_order_no like concat(concat('%',#{sendOrderNo}),'%')");
                }
              /*  if(!StringUtil.isBlank (sendOrderDto.getStoreId())){
                    WHERE("t.store_id = #{storeId}");
                }*/
                if (!StringUtil.isBlank(sendOrderDto.getPickCompanyId())) {
                    WHERE("t.pick_company_id = #{pickCompanyId}");
                }
                if (!StringUtil.isBlank(sendOrderDto.getStationIntervalId())) {
                    WHERE("t.station_interval_id = #{stationIntervalId}");
                }
               /* if(!StringUtil.isBlank (sendOrderDto.getApproverId())){
                    WHERE("t.approver_id = #{approverId}");
                }
                if(!StringUtil.isBlank (sendOrderDto.getApproverName())){
                    WHERE("t.approver_name = #{approverName}");
                }*/
                if (!StringUtil.isBlank(sendOrderDto.getApproveStatus())) {
                    WHERE("wf.approve_status = #{approveStatus}");
                }
                if (!StringUtil.isBlank(sendOrderDto.getCreateTime())) {
                    WHERE("t.createTime = #{createTime}");
                }
                if (!StringUtil.isBlank(sendOrderDto.getCreateUserCode())) {
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if (!StringUtil.isBlank(sendOrderDto.getCreateUserName())) {
                    WHERE("t.createUserName = #{createUserName}");
                }
                if (!StringUtil.isBlank(sendOrderDto.getUpdateTime())) {
                    WHERE("t.updateTime = #{updateTime}");
                }
                if (!StringUtil.isBlank(sendOrderDto.getUpdateUserCode())) {
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if (!StringUtil.isBlank(sendOrderDto.getUpdateUserName())) {
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                if (!StringUtil.isBlank(sendOrderDto.getHandNo())) {
                	WHERE("t.hand_no like concat(concat('%',#{handNo}),'%')");
                }
                if (!StringUtil.isBlank(sendOrderDto.getIsRemoved())) {
                    WHERE("t.isRemoved = #{isRemoved}");
                } else {
                    WHERE("ifnull(t.isRemoved,'0') <> '1'"); //不包含删除项
                }
            }
        }.toString();
        sql +=" order by t.seq_no ";
        return sql;
    }

    /**
     * @param sendOrderDto
     * @return sql
     * @throws
     * @name sumSendOrderNum
     * @author 品讯科技
     * @Description 发料单数量
     * @time 2021-04-09 21:04:38
     */
    public String sumSendOrderNum(final SendOrderDto sendOrderDto) {
        String sql = new SQL() {
            {
                SELECT("ifnull(sum(ifnull(sd.num,0)),0) as num");
                FROM("b_t_send_order t");
                INNER_JOIN("b_t_send_order_detail sd on t.id = sd.send_order_id");
                //查询审核通过的数量
                LEFT_OUTER_JOIN("t_workflow wf on wf.bus_id = t.id and ifnull(wf.approve_status,'') = '3'");
                WHERE("ifnull(sd.isRemoved,'0')<>'1' and ifnull(t.isRemoved,'0')<>'1'");
                WHERE("t.project_id = #{projectId}");
                //时间段
                WHERE("date(t.createTime) >= date(#{beginTime})");
                WHERE("date(t.createTime) <= date(#{endTime})");
            }
        }.toString();
        return sql;
    }

    /**
     * @param sendOrderDto
     * @return sql
     * @throws
     * @name sendOrderNumTrend
     * @author 品讯科技
     * @Description 发料单数量趋势
     * @time 2021-04-10 20:01:29
     */
    public String sendOrderNumTrend(final SendOrderDto sendOrderDto) {
        String sql = new SQL() {
            {
                SELECT("sum(sd.num) as num,date(t.createTime) as createTime");
                FROM("b_t_send_order t");
                INNER_JOIN("b_t_send_order_detail sd on t.id = sd.send_order_id");
                WHERE("ifnull(sd.isRemoved,'0')<>'1' and ifnull(t.isRemoved,'0')<>'1'");
                WHERE("t.project_id = #{projectId}");
                //时间段
                WHERE("date(t.createTime) >= date(#{beginTime})");
                WHERE("date(t.createTime) <= date(#{endTime})");
                GROUP_BY("date(t.createTime)");
                ORDER_BY("date(t.createTime)");
            }
        }.toString();
        return sql;
    }


    /**
     * @param sendOrderDto
     * @return sql
     * @throws
     * @name goodsMonitor
     * @author 品讯科技
     * @Description 物资耗料监测
     * @time 2021-04-10 21:41:43
     */
    public String goodsMonitor(final SendOrderDto sendOrderDto) {
        String sql = new SQL() {
            {
                SELECT("t.id as goodId,t.good_name as goodName,t.good_code AS goodCode,t.measure_unit AS measureUnit,t.specs_model_name AS specsModelName");
                SELECT("pc.pick_company_name as pickCompanyName");
                SELECT("sum(sd.num) as num,si.station_interval_name as stationIntervalName");
                FROM("b_t_pick_order po ");
                INNER_JOIN("b_t_pick_order_detail pd on po.id = pd.pick_order_id and ifnull(pd.isRemoved,'0')<>'1'");
                INNER_JOIN("b_t_send_order_detail sd on sd.pick_order_detail_id = pd.id and ifnull(sd.isRemoved,'0')<>'1'");
                INNER_JOIN("b_t_send_order so on so.id = sd.send_order_id and ifnull(so.isRemoved,'0')<>'1'");
                INNER_JOIN("b_t_good t on t.id = pd.good_id and ifnull(t.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("b_t_station_interval si on si.id = so.station_interval_id and ifnull(si.isRemoved,'0')<>'1'");
                LEFT_OUTER_JOIN("b_t_pick_company pc on pc.id = so.pick_company_id and ifnull(pc.isRemoved,'0')<>'1'");
                //查询审核通过的数量
                LEFT_OUTER_JOIN("t_workflow wf on wf.bus_id = po.id and ifnull(wf.approve_status,'') = '3'");

                WHERE("po.project_id = #{projectId}");
                //时间段
                if (StringUtil.isNotBlank(sendOrderDto.getBeginTime())) {
                    WHERE("date(po.createTime) >= date(#{beginTime})");
                }
                if (StringUtil.isNotBlank(sendOrderDto.getEndTime())) {
                    WHERE("date(po.createTime) <= date(#{endTime})");
                }
                if(StringUtil.isNotBlank(sendOrderDto.getPickCompanyName())){
                    WHERE("pc.pick_company_name like concat(concat('%',#{pickCompanyName}),'%')");
                }
                if(StringUtil.isNotBlank(sendOrderDto.getPickCompanyId())){
                    WHERE("pc.id = #{pickCompanyId}");
                }
                if(StringUtil.isNotBlank(sendOrderDto.getGoodName())){
                	WHERE("t.good_name like concat(concat('%',#{goodName}),'%') ");
                }
                GROUP_BY("t.id,t.good_name,t.good_code,t.measure_unit,t.specs_model_name");
                GROUP_BY("si.station_interval_name");
                GROUP_BY("pc.pick_company_name");
                ORDER_BY("date(so.createTime) desc");
            }
        }.toString();
        return sql;
    }

}
