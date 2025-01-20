package com.px.project.web.dao.provider;

import com.px.project.common.base.BaseProvider;
import com.px.project.common.utils.StringUtil;
import com.px.project.web.dto.MenuDto;
import com.px.project.web.entity.MenuModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Package com.px.project.web.dao.provider
 * @ClassName MenuProvider
 * @Description MenuProvider
 * @author 品讯科技
 * @time   2021-02-20 11:04:12
 * @version V 1.0
 */
public class MenuProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql="t.id as id,"+
									 "t.menuCode as menuCode,"+
									 "t.menuName as menuName,"+
									 "t.parentMenuId as parentMenuId,"+
									 "t.parentMenuCode as parentMenuCode,"+
									 "t.parentMenuName as parentMenuName,"+
									 "t.menuLevel as menuLevel,"+
									 "t.menuSerialNo as menuSerialNo,"+
									 "t.isLeaf as isLeaf,"+
									 "t.isExpand as isExpand,"+
									 "t.isChecked as isChecked,"+
									 "t.menudesc as remark,"+
									 "t.menuIconUrl as menuIconUrl,"+
									 "t.menuAction as menuAction,"+
									 "t.menuType as menuType,"+
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
     * @param menuModel
     * @Description 增加
     * @time 2021-02-20 11:04:12
     * @throws
     * @return sql
     */
    public String save(final MenuModel menuModel) {
        String sql = new SQL () {
            {
                INSERT_INTO ("s_t_menu ");
                VALUES("id", "#{id}");
                if(!StringUtil.isBlank (menuModel.getMenuCode())){
                    VALUES("menu_code", "#{menuCode}");
                }
                if(!StringUtil.isBlank (menuModel.getMenuName())){
                    VALUES("menu_name", "#{menuName}");
                }
                if(!StringUtil.isBlank (menuModel.getParentMenuId())){
                    VALUES("parent_menu_id", "#{parentMenuId}");
                }
                if(!StringUtil.isBlank (menuModel.getParentMenuCode())){
                    VALUES("parent_menu_code", "#{parentMenuCode}");
                }
                if(!StringUtil.isBlank (menuModel.getParentMenuName())){
                    VALUES("parent_menu_name", "#{parentMenuName}");
                }
                if(!StringUtil.isBlank (menuModel.getMenuLevel())){
                    VALUES("menu_level", "#{menuLevel}");
                }
                if(!StringUtil.isBlank (menuModel.getMenuSerialNo())){
                    VALUES("menu_serial_no", "#{menuSerialNo}");
                }
                if(!StringUtil.isBlank (menuModel.getIsLeaf())){
                    VALUES("is_leaf", "#{isLeaf}");
                }
                if(!StringUtil.isBlank (menuModel.getIsExpand())){
                    VALUES("is_expand", "#{isExpand}");
                }
                if(!StringUtil.isBlank (menuModel.getIsChecked())){
                    VALUES("is_checked", "#{isChecked}");
                }
                if(!StringUtil.isBlank (menuModel.getRemark())){
                    VALUES("remark", "#{remark}");
                }
                if(!StringUtil.isBlank (menuModel.getMenuIconUrl())){
                    VALUES("icon_url", "#{menuIconUrl}");
                }
                if(!StringUtil.isBlank (menuModel.getMenuAction())){
                    VALUES("menu_url", "#{menuAction}");
                }
                if(!StringUtil.isBlank (menuModel.getMenuType())){
                    VALUES("menu_url", "#{menuType}");
                }
                if(!StringUtil.isBlank (menuModel.getCreateTime())){
                    VALUES("createTime", "#{createTime}");
                }
                if(!StringUtil.isBlank (menuModel.getCreateUserCode())){
                    VALUES("createUserCode", "#{createUserCode}");
                }
                if(!StringUtil.isBlank (menuModel.getCreateUserName())){
                    VALUES("createUserName", "#{createUserName}");
                }
                if(!StringUtil.isBlank (menuModel.getUpdateTime())){
                    VALUES("updateTime", "#{updateTime}");
                }
                if(!StringUtil.isBlank (menuModel.getUpdateUserCode())){
                    VALUES("updateUserCode", "#{updateUserCode}");
                }
                if(!StringUtil.isBlank (menuModel.getUpdateUserName())){
                    VALUES("updateUserName", "#{updateUserName}");
                }
                if(!StringUtil.isBlank (menuModel.getIsRemoved())){
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
     * @time 2021-02-20 11:04:12
     * @throws
     * @return sql
     */
    public String deleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                DELETE_FROM("s_t_menu");
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
     * @time 2021-02-20 11:04:12
     * @throws
     * @return sql
     */
    public String logicDeleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                UPDATE("s_t_menu");
                SET("isremoved = '1'");
            }
        }.toString();
        sql += batchJoinField("id",ids);
        return sql;
    }

    /**
     * @name update
     * @author 品讯科技
     * @param menuModel
     * @Description 修改
     * @time 2021-02-20 11:04:12
     * @throws
     * @return sql
     */
    public String update(final MenuModel menuModel) {
        String sql = new SQL () {
            {
                UPDATE("s_t_menu ");
                    if(menuModel.getMenuCode() != null){
                        SET("menu_code = #{menuCode}");
                    }
                    if(menuModel.getMenuName() != null){
                        SET("menu_name = #{menuName}");
                    }
                    if(menuModel.getParentMenuId() != null){
                        SET("parent_menu_id = #{parentMenuId}");
                    }
                    if(menuModel.getParentMenuCode() != null){
                        SET("parent_menu_code = #{parentMenuCode}");
                    }
                    if(menuModel.getParentMenuName() != null){
                        SET("parent_menu_name = #{parentMenuName}");
                    }
                    if(menuModel.getMenuLevel() != null){
                        SET("menu_level = #{menuLevel}");
                    }
                    if(menuModel.getMenuSerialNo() != null){
                        SET("menu_serial_no = #{menuSerialNo}");
                    }
                    if(menuModel.getIsLeaf() != null){
                        SET("is_leaf = #{isLeaf}");
                    }
                    if(menuModel.getIsExpand() != null){
                        SET("is_expand = #{isExpand}");
                    }
                    if(menuModel.getIsChecked() != null){
                        SET("is_checked = #{isChecked}");
                    }
                    if(menuModel.getRemark() != null){
                        SET("remark = #{remark}");
                    }
                    if(menuModel.getMenuIconUrl() != null){
                        SET("icon_url = #{menuIconUrl}");
                    }
                    if(menuModel.getMenuAction() != null){
                        SET("menu_url = #{menuAction}");
                    }
                    if(menuModel.getMenuType() != null){
                        SET("menu_url = #{menuType}");
                    }
                    if(menuModel.getCreateTime() != null){
                        SET("createTime = #{createTime}");
                    }
                    if(menuModel.getCreateUserCode() != null){
                        SET("createUserCode = #{createUserCode}");
                    }
                    if(menuModel.getCreateUserName() != null){
                        SET("createUserName = #{createUserName}");
                    }
                    if(menuModel.getUpdateTime() != null){
                        SET("updateTime = #{updateTime}");
                    }
                    if(menuModel.getUpdateUserCode() != null){
                        SET("updateUserCode = #{updateUserCode}");
                    }
                    if(menuModel.getUpdateUserName() != null){
                        SET("updateUserName = #{updateUserName}");
                    }
                    if(menuModel.getIsRemoved() != null){
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
     * @time 2021-02-20 11:04:12
     * @throws
     * @return sql
     */
    public String findById(@Param ("id") String id) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("s_t_menu t");
                WHERE("t.id = #{id}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param menuDto
     * @Description 根据条件查询数据并分页
     * @time 2021-02-20 11:04:12
     * @throws
     * @return sql
     */
    public String findAll(final MenuDto menuDto) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("s_t_menu t");
                if(!StringUtil.isBlank (menuDto.getId())){
                    WHERE("t.id = #{id}");
                }
                if(!StringUtil.isBlank (menuDto.getMenuCode())){
                    WHERE("t.menu_code = #{menuCode}");
                }
                if(!StringUtil.isBlank (menuDto.getMenuName())){
                    WHERE("t.menu_name = #{menuName}");
                }
                if(!StringUtil.isBlank (menuDto.getParentMenuId())){
                    WHERE("t.parent_menu_id = #{parentMenuId}");
                }
                if(!StringUtil.isBlank (menuDto.getParentMenuCode())){
                    WHERE("t.parent_menu_code = #{parentMenuCode}");
                }
                if(!StringUtil.isBlank (menuDto.getParentMenuName())){
                    WHERE("t.parent_menu_name = #{parentMenuName}");
                }
                if(!StringUtil.isBlank (menuDto.getMenuLevel())){
                    WHERE("t.menu_level = #{menuLevel}");
                }
                if(!StringUtil.isBlank (menuDto.getMenuSerialNo())){
                    WHERE("t.menu_serial_no = #{menuSerialNo}");
                }
                if(!StringUtil.isBlank (menuDto.getIsLeaf())){
                    WHERE("t.is_leaf = #{isLeaf}");
                }
                if(!StringUtil.isBlank (menuDto.getIsExpand())){
                    WHERE("t.is_expand = #{isExpand}");
                }
                if(!StringUtil.isBlank (menuDto.getIsChecked())){
                    WHERE("t.is_checked = #{isChecked}");
                }
                if(!StringUtil.isBlank (menuDto.getRemark())){
                    WHERE("t.remark = #{remark}");
                }
                if(!StringUtil.isBlank (menuDto.getMenuIconUrl())){
                    WHERE("t.icon_url = #{menuIconUrl}");
                }
                if(!StringUtil.isBlank (menuDto.getMenuAction())){
                    WHERE("t.menu_url = #{menuAction}");
                }
                if(!StringUtil.isBlank (menuDto.getMenuType())){
                    WHERE("t.menu_url = #{menuType}");
                }
                if(!StringUtil.isBlank (menuDto.getCreateTime())){
                    WHERE("t.createTime = #{createTime}");
                }
                if(!StringUtil.isBlank (menuDto.getCreateUserCode())){
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if(!StringUtil.isBlank (menuDto.getCreateUserName())){
                    WHERE("t.createUserName = #{createUserName}");
                }
                if(!StringUtil.isBlank (menuDto.getUpdateTime())){
                    WHERE("t.updateTime = #{updateTime}");
                }
                if(!StringUtil.isBlank (menuDto.getUpdateUserCode())){
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (menuDto.getUpdateUserName())){
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                if(!StringUtil.isBlank (menuDto.getIsRemoved())){
                    WHERE("t.isremoved = #{isRemoved}");
                }
            }
        }.toString();
        return sql;
    }

    /**
     * @name findMenuByUserId
     * @author 品讯科技
     * @param menuType
     * @Description 据roleId查询对应的菜单信息
     * @time 2018年10月18日16:23:20
     * @throws
     * @return sql
     */
    public String findMenuAll(@Param("menuType") String menuType){
        String sql ="select "+selectOrInsertSql +
                " from s_t_menu t where 1=1";
        if(!StringUtil.isBlank (menuType)){
            sql+=" and t.menuType='"+menuType+"' or t.menuType is null";
        }
        sql+=" order by t.menuSerialNo asc";
        return sql;
    }

    /**
     * @name findUserMenuByUserId
     * @author 品讯科技
     * @param userId
     * @param menuType
     * @Description 根据用户id查看该用户所属角色的全部菜单
     * @time 2018年10月18日16:23:20
     * @throws
     * @return sql
     */
    public String findUserMenuByUserId(@Param("userId") String userId,@Param("menuType") String menuType){
        String sql ="select "+selectOrInsertSql+
                " from s_t_menu t left join s_t_rolemenu m on t.id=m.menuid left join s_t_role r on r.id=m.roleid" +
                " left join s_t_roleuser ru on ru.roleid=r.id left join s_t_user u on u.id=ru.userid" +
                " where u.id='"+userId+"' ";
        if(!StringUtil.isBlank (menuType)){
            sql+=" and t.menutype='"+menuType+"'";
        }
        sql+=" order by t.menuserialno asc";
        return sql;
    }

}
