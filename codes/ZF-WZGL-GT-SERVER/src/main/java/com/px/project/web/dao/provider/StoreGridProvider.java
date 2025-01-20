package com.px.project.web.dao.provider;

import com.px.project.common.base.BaseProvider;
import com.px.project.common.utils.StringUtil;
import com.px.project.web.dto.StoreGridDto;
import com.px.project.web.entity.StoreGridModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Package com.px.project.web.dao.provider
 * @ClassName StoreGridProvider
 * @Description StoreGridProvider
 * @author 品讯科技
 * @time   2021-03-04 22:43:20
 * @version V 1.0
 */
public class StoreGridProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql="t.id as id,"+
									 "t.store_id as storeId,"+
									 "t.grid_no as gridNo,"+
									 "t.grid_name as gridName,"+
									 "t.grid_row_no as gridRowNo,"+
									 "t.grid_column_no as gridColumnNo,"+
									 "t.status as status,"+
									 "t.remarks as remarks,"+
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
     * @param storeGridModel
     * @Description 增加
     * @time 2021-03-04 22:43:20
     * @throws
     * @return sql
     */
    public String save(final StoreGridModel storeGridModel) {
        String sql = new SQL () {
            {
                INSERT_INTO ("b_t_store_grid ");
                VALUES("id", "#{id}");
                if(!StringUtil.isBlank (storeGridModel.getStoreId())){
                    VALUES("store_id", "#{storeId}");
                }
                if(!StringUtil.isBlank (storeGridModel.getGridNo())){
                    VALUES("grid_no", "#{gridNo}");
                }
                if(!StringUtil.isBlank (storeGridModel.getGridName())){
                    VALUES("grid_name", "#{gridName}");
                }
                if(!StringUtil.isBlank (storeGridModel.getGridRowNo())){
                    VALUES("grid_row_no", "#{gridRowNo}");
                }
                if(!StringUtil.isBlank (storeGridModel.getGridColumnNo())){
                    VALUES("grid_column_no", "#{gridColumnNo}");
                }
                if(!StringUtil.isBlank (storeGridModel.getStatus())){
                    VALUES("status", "#{status}");
                }else{
                    VALUES("status", "1");
                }
                if(!StringUtil.isBlank (storeGridModel.getRemarks())){
                    VALUES("remarks", "#{remarks}");
                }
                if(!StringUtil.isBlank (storeGridModel.getCreateTime())){
                    VALUES("createTime", "#{createTime}");
                }
                if(!StringUtil.isBlank (storeGridModel.getCreateUserCode())){
                    VALUES("createUserCode", "#{createUserCode}");
                }
                if(!StringUtil.isBlank (storeGridModel.getCreateUserName())){
                    VALUES("createUserName", "#{createUserName}");
                }
                if(!StringUtil.isBlank (storeGridModel.getUpdateTime())){
                    VALUES("updateTime", "#{updateTime}");
                }
                if(!StringUtil.isBlank (storeGridModel.getUpdateUserCode())){
                    VALUES("updateUserCode", "#{updateUserCode}");
                }
                if(!StringUtil.isBlank (storeGridModel.getUpdateUserName())){
                    VALUES("updateUserName", "#{updateUserName}");
                }
                if(!StringUtil.isBlank (storeGridModel.getIsRemoved())){
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
     * @time 2021-03-04 22:43:20
     * @throws
     * @return sql
     */
    public String deleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                DELETE_FROM("b_t_store_grid");
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
     * @time 2021-03-04 22:43:20
     * @throws
     * @return sql
     */
    public String logicDeleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                UPDATE("b_t_store_grid");
                SET("isRemoved = '1'");
            }
        }.toString();
        sql += batchJoinField("id",ids);
        return sql;
    }

    /**
     * @return sql
     * @throws
     * @name logicDeleteByColRow
     * @author 品讯科技
     * @Description 根据单元格地址，逻辑删除
     * @time 2021-04-06 15:54:45
     */
    public String logicDeleteByColRow(@Param("storeId") String storeId, @Param("colNo") String colNo, @Param("rowNo") String rowNo) {
        String sql = new SQL() {
            {
                UPDATE("b_t_store_grid");
                SET("isRemoved = '1'");
                WHERE("store_id = #{storeId}");
                WHERE("grid_column_no = #{colNo}");
                WHERE("grid_row_no = #{rowNo}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name update
     * @author 品讯科技
     * @param storeGridModel
     * @Description 修改
     * @time 2021-03-06 22:57:33
     * @throws
     * @return sql
     */
    public String update(final StoreGridModel storeGridModel) {
        String sql = new SQL () {
            {
                UPDATE("b_t_store_grid ");
                   /* if(storeGridModel.getStoreId() != null){
                        SET("store_id = #{storeId}");
                    }*/
                    if(storeGridModel.getGridNo() != null){
                        SET("grid_no = #{gridNo}");
                    }
                    if(storeGridModel.getGridRowNo() != null){
                        SET("grid_row_no = #{gridRowNo}");
                    }
                    if(storeGridModel.getGridColumnNo() != null){
                        SET("grid_column_no = #{gridColumnNo}");
                    }
                    if(storeGridModel.getGridName() != null){
                        SET("grid_name = #{gridName}");
                    }
                    if(storeGridModel.getStatus() != null){
                        SET("status = #{status}");
                    }
                    if(storeGridModel.getRemarks() != null){
                        SET("remarks = #{remarks}");
                    }
                   if(storeGridModel.getCreateTime() != null){
                        SET("createTime = #{createTime}");
                    }
                    if(storeGridModel.getCreateUserCode() != null){
                        SET("createUserCode = #{createUserCode}");
                    }
                    if(storeGridModel.getCreateUserName() != null){
                        SET("createUserName = #{createUserName}");
                    }
                    if(storeGridModel.getUpdateTime() != null){
                        SET("updateTime = #{updateTime}");
                    }
                    if(storeGridModel.getUpdateUserCode() != null){
                        SET("updateUserCode = #{updateUserCode}");
                    }
                    if(storeGridModel.getUpdateUserName() != null){
                        SET("updateUserName = #{updateUserName}");
                    }
                    if(storeGridModel.getIsRemoved() != null){
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
     * @time 2021-03-04 22:43:20
     * @throws
     * @return sql
     */
    public String findById(@Param ("id") String id) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                SELECT("s.project_id as projectId,s.store_no as storeNo,s.store_name as storeName");
                //仓库启用状态
                INNER_JOIN("b_t_store s on t.store_id = s.id and s.isRemoved<>'1' and s.status='1'");
                FROM("b_t_store_grid t");
                WHERE("t.id = #{id}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findMaxGrid
     * @author 品讯科技
     * @param storeId
     * @Description 获取当前最大的行和列数据项
     * @time 2021-03-06 23:14:40
     * @throws
     * @return sql
     */
    public String findMaxGrid(@Param ("storeId") String storeId) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                INNER_JOIN("b_t_store s on t.store_id = s.id and t.isRemoved<>'1' and t.status='1'");
                FROM("b_t_store_grid t");
                WHERE("t.store_id = #{storeId}");
                ORDER_BY("t.grid_no desc limit 1"); //逆序并返回最大的一条数据
            }
        }.toString();
        return sql;
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param storeGridDto
     * @Description 根据条件查询数据并分页
     * @time 2021-03-04 22:43:20
     * @throws
     * @return sql
     */
    public String findAll(final StoreGridDto storeGridDto) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                SELECT("s.project_id as projectId,s.store_no as storeNo,s.store_name as storeName");
                //查询网格内物资数量（给前端判断）
                SELECT("(select count(*) from b_t_good_grid gg \n" +
                        "inner join b_t_good g on g.id = gg.good_id and ifnull(g.isRemoved,'')<>'1'\n" +
                        "where gg.grid_id = t.id and ifnull(gg.isRemoved,'')<>'1') as goodsCount");
                FROM("b_t_store_grid t");
                //仓库启用状态
                INNER_JOIN("b_t_store s on t.store_id = s.id and s.isRemoved<>'1' and s.status='1'");
                WHERE("t.store_id = #{storeId}");
                if(!StringUtil.isBlank (storeGridDto.getIsRemoved())){
                    WHERE("t.isRemoved = #{isRemoved}");
                }else{
                    WHERE("ifnull(t.isRemoved,'0') <> '1'"); //不包含删除项
                }
                if(!StringUtil.isBlank (storeGridDto.getStatus())){
                    WHERE("t.status = #{status}");
                }
                ORDER_BY("t.grid_row_no,t.grid_column_no");

               /* if(!StringUtil.isBlank (storeGridDto.getId())){
                    WHERE("t.id = #{id}");
                }
                if(!StringUtil.isBlank (storeGridDto.getGridNo())){
                    WHERE("t.grid_no = #{gridNo}");
                }
                if(!StringUtil.isBlank (storeGridDto.getGridName())){
                    WHERE("t.grid_name = #{gridName}");
                }
                if(!StringUtil.isBlank (storeGridDto.getGridRowNo())){
                    WHERE("t.grid_row_no = #{gridRowNo}");
                }
                if(!StringUtil.isBlank (storeGridDto.getGridColumnNo())){
                    WHERE("t.grid_column_no = #{gridColumnNo}");
                }
                if(!StringUtil.isBlank (storeGridDto.getStatus())){
                    WHERE("t.status = #{status}");
                }
                if(!StringUtil.isBlank (storeGridDto.getRemarks())){
                    WHERE("t.remarks = #{remarks}");
                }
                if(!StringUtil.isBlank (storeGridDto.getCreateTime())){
                    WHERE("t.createTime = #{createTime}");
                }
                if(!StringUtil.isBlank (storeGridDto.getCreateUserCode())){
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if(!StringUtil.isBlank (storeGridDto.getCreateUserName())){
                    WHERE("t.createUserName = #{createUserName}");
                }
                if(!StringUtil.isBlank (storeGridDto.getUpdateTime())){
                    WHERE("t.updateTime = #{updateTime}");
                }
                if(!StringUtil.isBlank (storeGridDto.getUpdateUserCode())){
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (storeGridDto.getUpdateUserName())){
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                if(!StringUtil.isBlank (storeGridDto.getIsRemoved())){
                    WHERE("t.isremoved = #{isRemoved}");
                }*/
            }
        }.toString();
        return sql;
    }
    public String findAllModel(final StoreGridDto storeGridDto) {
    	String sql = new SQL () {
    		{
    			SELECT(selectOrInsertSql);
    			FROM("b_t_store_grid t");
    			//仓库启用状态
    			WHERE("t.store_id = #{storeId}");
    			if(!StringUtil.isBlank (storeGridDto.getIsRemoved())){
    				WHERE("t.isRemoved = #{isRemoved}");
    			}else{
    				WHERE("ifnull(t.isRemoved,'0') <> '1'"); //不包含删除项
    			}
    			if(!StringUtil.isBlank (storeGridDto.getStatus())){
    				WHERE("t.status = #{status}");
    			}
    			ORDER_BY("t.grid_row_no,t.grid_column_no");
    			
    			/* if(!StringUtil.isBlank (storeGridDto.getId())){
                    WHERE("t.id = #{id}");
                }
                if(!StringUtil.isBlank (storeGridDto.getGridNo())){
                    WHERE("t.grid_no = #{gridNo}");
                }
                if(!StringUtil.isBlank (storeGridDto.getGridName())){
                    WHERE("t.grid_name = #{gridName}");
                }
                if(!StringUtil.isBlank (storeGridDto.getGridRowNo())){
                    WHERE("t.grid_row_no = #{gridRowNo}");
                }
                if(!StringUtil.isBlank (storeGridDto.getGridColumnNo())){
                    WHERE("t.grid_column_no = #{gridColumnNo}");
                }
                if(!StringUtil.isBlank (storeGridDto.getStatus())){
                    WHERE("t.status = #{status}");
                }
                if(!StringUtil.isBlank (storeGridDto.getRemarks())){
                    WHERE("t.remarks = #{remarks}");
                }
                if(!StringUtil.isBlank (storeGridDto.getCreateTime())){
                    WHERE("t.createTime = #{createTime}");
                }
                if(!StringUtil.isBlank (storeGridDto.getCreateUserCode())){
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if(!StringUtil.isBlank (storeGridDto.getCreateUserName())){
                    WHERE("t.createUserName = #{createUserName}");
                }
                if(!StringUtil.isBlank (storeGridDto.getUpdateTime())){
                    WHERE("t.updateTime = #{updateTime}");
                }
                if(!StringUtil.isBlank (storeGridDto.getUpdateUserCode())){
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (storeGridDto.getUpdateUserName())){
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                if(!StringUtil.isBlank (storeGridDto.getIsRemoved())){
                    WHERE("t.isremoved = #{isRemoved}");
                }*/
    		}
    	}.toString();
    	return sql;
    }

}
