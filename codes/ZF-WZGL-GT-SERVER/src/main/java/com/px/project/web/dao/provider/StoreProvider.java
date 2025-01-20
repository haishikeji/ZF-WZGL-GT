package com.px.project.web.dao.provider;

import com.px.project.common.base.BaseProvider;
import com.px.project.common.utils.StringUtil;
import com.px.project.web.dto.StoreDto;
import com.px.project.web.entity.StoreModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Package com.px.project.web.dao.provider
 * @ClassName StoreProvider
 * @Description StoreProvider
 * @author 品讯科技
 * @time   2021-03-02 22:42:20
 * @version V 1.0
 */
public class StoreProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql="t.id as id,"+
									 "t.project_Id as projectId,"+
									 "t.store_no as storeNo,"+
									 "t.store_name as storeName,"+
									 "t.address as address,"+
									 "t.area as area,"+
									 "t.contacts as contacts,"+
									 "t.mobile as mobile,"+
									 "t.remarks as remarks,"+
									 "t.createTime as createTime,"+
									 "t.createUserCode as createUserCode,"+
									 "t.updateTime as updateTime,"+
									 "t.updateUserCode as updateUserCode,"+
                                     "t.status as status,"+
									 "t.isRemoved as isRemoved";
    /**
     * @name save
     * @author 品讯科技
     * @param storeModel
     * @Description 增加
     * @time 2021-03-02 22:42:20
     * @throws
     * @return sql
     */
    public String save(final StoreModel storeModel) {
        String sql = new SQL () {
            {
                INSERT_INTO ("b_t_store ");
                VALUES("id", "#{id}");
                VALUES("project_id", "#{projectId}");

                if(!StringUtil.isBlank (storeModel.getStoreNo())){
                    VALUES("store_no", "#{storeNo}");
                }
                if(!StringUtil.isBlank (storeModel.getStoreName())){
                    VALUES("store_name", "#{storeName}");
                }
                if(!StringUtil.isBlank (storeModel.getAddress())){
                    VALUES("address", "#{address}");
                }
                if(!StringUtil.isBlank (storeModel.getArea())){
                    VALUES("area", "#{area}");
                }
                if(!StringUtil.isBlank (storeModel.getContacts())){
                    VALUES("contacts", "#{contacts}");
                }
                if(!StringUtil.isBlank (storeModel.getMobile())){
                    VALUES("mobile", "#{mobile}");
                }
                if(!StringUtil.isBlank (storeModel.getRemarks())){
                    VALUES("remarks", "#{remarks}");
                }
                if (!StringUtil.isBlank(storeModel.getStatus())) {
                    VALUES("status", "#{status}");
                } else {
                    VALUES("status", "1"); //默认启用状态
                }
                if (!StringUtil.isBlank(storeModel.getIsRemoved())) {
                    VALUES("isRemoved", "#{isRemoved}");
                } else {
                    VALUES("isRemoved", "0"); //默认非删除
                }
                if(!StringUtil.isBlank (storeModel.getCreateTime())){
                    VALUES("createTime", "#{createTime}");
                }
                if(!StringUtil.isBlank (storeModel.getCreateUserCode())){
                    VALUES("createUserCode", "#{createUserCode}");
                }
                /*if(!StringUtil.isBlank (storeModel.getUpdateTime())){
                    VALUES("updateTime", "#{updateTime}");
                }
                if (!StringUtil.isBlank(storeModel.getUpdateUserCode())) {
                    VALUES("updateUserCode", "#{updateUserCode}");
                }*/
            }
        }.toString();
        return sql;
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids
     * @Description 物理删除
     * @time 2021-03-02 22:42:20
     * @throws
     * @return sql
     */
    public String deleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                DELETE_FROM("b_t_store");
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
     * @time 2021-03-02 22:42:20
     * @throws
     * @return sql
     */
    public String logicDeleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                UPDATE("b_t_store");
                SET("isRemoved = '1'");
            }
        }.toString();
        sql += batchJoinField("id",ids);
        return sql;
    }

    /**
     * @name update
     * @author 品讯科技
     * @param storeModel
     * @Description 修改
     * @time 2021-03-02 22:42:20
     * @throws
     * @return sql
     */
    public String update(final StoreModel storeModel) {
        String sql = new SQL () {
            {
                UPDATE("b_t_store ");
                //不允许修改项目id
               /* if(storeModel.getProjectId() != null){
                    SET("project_id = #{projectId}");
                }*/

                if (storeModel.getStatus() != null) {
                    SET("status = #{status}");
                }
                if (storeModel.getStoreNo() != null) {
                    SET("store_no = #{storeNo}");
                }
                if (storeModel.getStoreName() != null) {
                    SET("store_name = #{storeName}");
                }
                if (storeModel.getAddress() != null) {
                    SET("address = #{address}");
                }
                if (storeModel.getArea() != null) {
                    SET("area = #{area}");
                }
                if (storeModel.getContacts() != null) {
                    SET("contacts = #{contacts}");
                }
                if (storeModel.getMobile() != null) {
                    SET("mobile = #{mobile}");
                }
                if (storeModel.getRemarks() != null) {
                    SET("remarks = #{remarks}");
                }
                /*if (storeModel.getCreateTime() != null) {
                    SET("createTime = #{createTime}");
                }
                if (storeModel.getCreateUserCode() != null) {
                    SET("createUserCode = #{createUserCode}");
                }*/
                if (storeModel.getUpdateTime() != null) {
                    SET("updateTime = #{updateTime}");
                }
                if (storeModel.getUpdateUserCode() != null) {
                    SET("updateUserCode = #{updateUserCode}");
                }
                if (storeModel.getIsRemoved() != null) {
                    SET("isRemoved = #{isRemoved}");
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
     * @time 2021-03-02 22:42:20
     * @throws
     * @return sql
     */
    public String findById(@Param ("id") String id) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("b_t_store t");
                WHERE("t.id = #{id}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param storeDto
     * @Description 根据条件查询数据并分页
     * @time 2021-03-02 22:42:20
     * @throws
     * @return sql
     */
    public String findAll(final StoreDto storeDto) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("b_t_store t");
                WHERE("t.project_id = #{projectId}");
                WHERE("t.isRemoved = '0'");

                if(!StringUtil.isBlank (storeDto.getId())){
                    WHERE("t.id = #{id}");
                }
                if(!StringUtil.isBlank (storeDto.getStoreNo())){
                    WHERE("t.store_no like concat(concat('%',#{storeNo}),'%')");
                }
                if(!StringUtil.isBlank (storeDto.getStoreName())){
                    WHERE("t.store_name like concat(concat('%',#{storeName}),'%')");
                }
                if(!StringUtil.isBlank (storeDto.getAddress())){
                    WHERE("t.address = #{address}");
                }
                if(!StringUtil.isBlank (storeDto.getArea())){
                    WHERE("t.area = #{area}");
                }
                if(!StringUtil.isBlank (storeDto.getContacts())){
                    WHERE("t.contacts = #{contacts}");
                }
                if(!StringUtil.isBlank (storeDto.getMobile())){
                    WHERE("t.mobile = #{mobile}");
                }
                if(!StringUtil.isBlank (storeDto.getStatus())){
                    WHERE("t.status = #{status}");
                }
                if(!StringUtil.isBlank (storeDto.getRemarks())){
                    WHERE("t.remarks = #{remarks}");
                }
                if(!StringUtil.isBlank (storeDto.getCreateTime())){
                    WHERE("t.createTime = #{createTime}");
                }
                if(!StringUtil.isBlank (storeDto.getCreateUserCode())){
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if(!StringUtil.isBlank (storeDto.getUpdateTime())){
                    WHERE("t.updateTime = #{updateTime}");
                }
                if(!StringUtil.isBlank (storeDto.getUpdateUserCode())){
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (storeDto.getIsRemoved())){
                    WHERE("t.isRemoved = #{isRemoved}");
                }else{
                    WHERE("ifnull(t.isRemoved,'0') <> '1'"); //不包含删除项
                }
            }
        }.toString();
        return sql;
    }

}
