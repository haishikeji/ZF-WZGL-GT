package com.px.project.web.dao.provider;

import com.px.project.common.base.BaseProvider;
import com.px.project.common.utils.StringUtil;
import com.px.project.common.utils.UUIDUtil;
import com.px.project.web.dto.RoleDto;
import com.px.project.web.entity.RoleModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.StringJoiner;

/**
 * @Package com.px.project.web.dao.provider
 * @ClassName RoleProvider
 * @Description RoleProvider
 * @author 品讯科技
 * @time   2021-02-20 09:44:07
 * @version V 1.0
 */
public class RoleProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql="t.id as id,"+
									 "t.roleCode as roleCode,"+
									 "t.roleName as roleName,"+
									 "t.roleDesc as roleDesc,"+
									 "t.status as status,"+
                                     "DATE_FORMAT(t.createTime,'%Y-%m-%d %H:%i:%S') as createTime,"+
									 "t.createUserCode as createUserCode,"+
									 "t.createUserName as createUserName,"+
                                     "DATE_FORMAT(t.updateTime,'%Y-%m-%d %H:%i:%S') as updateTime,"+
									 "t.updateUserCode as updateUserCode,"+
									 "t.updateUserName as updateUserName,"+
									 "t.isremoved as isRemoved";
    /**
     * @name save
     * @author 品讯科技
     * @param roleModel
     * @Description 增加
     * @time 2021-02-20 09:44:07
     * @throws
     * @return sql
     */
    public String save(final RoleModel roleModel) {
        String sql = new SQL () {
            {
                INSERT_INTO ("s_t_role ");
                VALUES("id", "#{id}");
                if(!StringUtil.isBlank (roleModel.getRoleCode())){
                    VALUES("roleCode", "#{roleCode}");
                }
                if(!StringUtil.isBlank (roleModel.getRoleName())){
                    VALUES("roleName", "#{roleName}");
                }
                if(!StringUtil.isBlank (roleModel.getRoleDesc())){
                    VALUES("roleDesc", "#{roleDesc}");
                }
                if(!StringUtil.isBlank (roleModel.getStatus())){
                    VALUES("status", "#{status}");
                }
                if(!StringUtil.isBlank (roleModel.getCreateTime())){
                    VALUES("createTime", "#{createTime}");
                }
                if(!StringUtil.isBlank (roleModel.getCreateUserCode())){
                    VALUES("createUserCode", "#{createUserCode}");
                }
                if(!StringUtil.isBlank (roleModel.getCreateUserName())){
                    VALUES("createUserName", "#{createUserName}");
                }
                if(!StringUtil.isBlank (roleModel.getUpdateTime())){
                    VALUES("updateTime", "#{updateTime}");
                }
                if(!StringUtil.isBlank (roleModel.getUpdateUserCode())){
                    VALUES("updateUserCode", "#{updateUserCode}");
                }
                if(!StringUtil.isBlank (roleModel.getUpdateUserName())){
                    VALUES("updateUserName", "#{updateUserName}");
                }
                if(!StringUtil.isBlank (roleModel.getIsRemoved())){
                    VALUES("isremoved", "#{isRemoved}");
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
     * @time 2021-02-20 09:44:07
     * @throws
     * @return sql
     */
    public String deleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                DELETE_FROM("s_t_role");
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
     * @time 2021-02-20 09:44:07
     * @throws
     * @return sql
     */
    public String logicDeleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                UPDATE("s_t_role");
                SET("isremoved = '1'");
            }
        }.toString();
        sql += batchJoinField("id",ids);
        return sql;
    }

    /**
     * @name update
     * @author 品讯科技
     * @param roleModel
     * @Description 修改
     * @time 2021-02-20 09:44:07
     * @throws
     * @return sql
     */
    public String update(final RoleModel roleModel) {
        String sql = new SQL () {
            {
                UPDATE("s_t_role ");
                if(roleModel.getRoleCode() != null){
                    SET("roleCode = #{roleCode}");
                }
                if(roleModel.getRoleName() != null){
                    SET("roleName = #{roleName}");
                }
                if(roleModel.getRoleDesc() != null){
                    SET("roleDesc = #{roleDesc}");
                }
                if(roleModel.getStatus() != null){
                    SET("status = #{status}");
                }
                if(roleModel.getCreateTime() != null){
                    SET("createTime = #{createTime}");
                }
                if(roleModel.getCreateUserCode() != null){
                    SET("createUserCode = #{createUserCode}");
                }
                if(roleModel.getCreateUserName() != null){
                    SET("createUserName = #{createUserName}");
                }
                if(roleModel.getUpdateTime() != null){
                    SET("updateTime = #{updateTime}");
                }
                if(roleModel.getUpdateUserCode() != null){
                    SET("updateUserCode = #{updateUserCode}");
                }
                if(roleModel.getUpdateUserName() != null){
                    SET("updateUserName = #{updateUserName}");
                }
                if(roleModel.getIsRemoved() != null){
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
     * @time 2021-02-20 09:44:07
     * @throws
     * @return sql
     */
    public String findById(@Param ("id") String id) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("s_t_role t");
                WHERE("t.id = #{id}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param roleDto
     * @Description 根据条件查询数据并分页
     * @time 2021-02-20 09:44:07
     * @throws
     * @return sql
     */
    public String findAll(final RoleDto roleDto) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("s_t_role t");
                WHERE("t.isremoved = '0'");
                if(!StringUtil.isBlank (roleDto.getId())){
                    WHERE("t.id = #{id}");
                }
                if(!StringUtil.isBlank (roleDto.getRoleCode())){
                    WHERE("t.roleCode = #{roleCode}");
                }
                if(!StringUtil.isBlank (roleDto.getRoleName())){
                    WHERE("t.roleName = #{roleName}");
                }
                if(!StringUtil.isBlank (roleDto.getRoleDesc())){
                    WHERE("t.roleDesc = #{roleDesc}");
                }
                if(!StringUtil.isBlank (roleDto.getStatus())){
                    WHERE("t.status = #{status}");
                }
                if(!StringUtil.isBlank (roleDto.getCreateTime())){
                    WHERE("t.createTime = #{createTime}");
                }
                if(!StringUtil.isBlank (roleDto.getCreateUserCode())){
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if(!StringUtil.isBlank (roleDto.getCreateUserName())){
                    WHERE("t.createUserName = #{createUserName}");
                }
                if(!StringUtil.isBlank (roleDto.getUpdateTime())){
                    WHERE("t.updateTime = #{updateTime}");
                }
                if(!StringUtil.isBlank (roleDto.getUpdateUserCode())){
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (roleDto.getUpdateUserName())){
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                if(!StringUtil.isBlank (roleDto.getIsRemoved())){
                    WHERE("t.isremoved = #{isRemoved}");
                }
            }
        }.toString();
        return sql;
    }

    /**
     * 保存角色菜单关联表
     * @param roleId
     * @param menuIds
     * @return
     */
    public String batchSaveRoleMenu(@Param ("roleId") String roleId,@Param ("menuIds") String menuIds){
        String sql = "INSERT INTO s_t_rolemenu values";
        String str="(\"{0}\",\"{1}\",\"{2}\")";
        StringJoiner sj=joinString(roleId,str,menuIds);
        sql+= sj.toString ();
        return sql;
    }

    private StringJoiner joinString(String roleId,String str,String menuIds){
        StringJoiner sj=new StringJoiner (",");
        if(!StringUtil.isBlank (roleId)&&!StringUtil.isBlank (menuIds)){
            String [] menuId=menuIds.split (",");
            for(String id : menuId) {
                Object[] array=null;
                array=new Object[]{UUIDUtil.get32UUID (),roleId,id};
                String value=MessageFormat.format (str,array);
                sj.add (value);
            }
        }
        return sj;
    }

    /**
     * @name deleteRoleMenuByRoleId
     * @author 品讯科技
     * @param roleId
     * @Description 物理删除
     * @time 2020-11-23 21:13:07
     * @throws
     * @return sql
     */
    public String deleteRoleMenuByRoleId(@Param ("roleId") String roleId) {
        String sql = new SQL () {
            {
                DELETE_FROM("s_t_rolemenu");
                WHERE("roleId = #{roleId}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name roleIsNormal
     * @author 品讯科技
     * @Description 根据roleId判断 角色存在并未停用=true 角色不存在或者决策停用=false
     * @time 2020-02-20 15:09:09
     * @throws
     * @return sql
     */
    public String roleIsNormal(String roleId) {
        String sql = new SQL () {
            {
                SELECT("t.id as id,t.roleName as roleName");
                FROM("s_t_role t");
                WHERE("t.isremoved = '0'");
                WHERE("t.id = #{roleId}");
            }
        }.toString();
        return sql;
    }

}
