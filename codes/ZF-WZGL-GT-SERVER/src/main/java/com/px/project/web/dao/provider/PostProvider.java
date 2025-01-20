package com.px.project.web.dao.provider;

import com.px.project.common.base.BaseProvider;
import com.px.project.common.utils.StringUtil;
import com.px.project.web.dto.PostDto;
import com.px.project.web.entity.PostModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Package com.px.project.web.dao.provider
 * @ClassName PostProvider
 * @Description PostProvider
 * @author 品讯科技
 * @time   2021-02-19 13:46:37
 * @version V 1.0
 */
public class PostProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql="t.id as id,"+
									 "t.postcode as postcode,"+
									 "t.postname as postname,"+
									 "t.postdesc as postdesc,"+
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
     * @param postModel
     * @Description 增加
     * @time 2021-02-19 13:46:37
     * @throws
     * @return sql
     */
    public String save(final PostModel postModel) {
        String sql = new SQL () {
            {
                INSERT_INTO ("s_t_post ");
                VALUES("id", "#{id}");
                if(!StringUtil.isBlank (postModel.getPostCode())){
                    VALUES("postcode", "#{postcode}");
                }
                if(!StringUtil.isBlank (postModel.getPostName())){
                    VALUES("postname", "#{postname}");
                }
                if(!StringUtil.isBlank (postModel.getPostDesc())){
                    VALUES("postdesc", "#{postdesc}");
                }
                if(!StringUtil.isBlank (postModel.getStatus())){
                    VALUES("status", "#{status}");
                }
                if(!StringUtil.isBlank (postModel.getCreateTime())){
                    VALUES("createTime", "#{createTime}");
                }
                if(!StringUtil.isBlank (postModel.getCreateUserCode())){
                    VALUES("createUserCode", "#{createUserCode}");
                }
                if(!StringUtil.isBlank (postModel.getCreateUserName())){
                    VALUES("createUserName", "#{createUserName}");
                }
                if(!StringUtil.isBlank (postModel.getUpdateTime())){
                    VALUES("updateTime", "#{updateTime}");
                }
                if(!StringUtil.isBlank (postModel.getUpdateUserCode())){
                    VALUES("updateUserCode", "#{updateUserCode}");
                }
                if(!StringUtil.isBlank (postModel.getUpdateUserName())){
                    VALUES("updateUserName", "#{updateUserName}");
                }
                if(!StringUtil.isBlank (postModel.getIsRemoved())){
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
     * @time 2021-02-19 13:46:37
     * @throws
     * @return sql
     */
    public String deleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                DELETE_FROM("s_t_post");
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
     * @time 2021-02-19 13:46:37
     * @throws
     * @return sql
     */
    public String logicDeleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                UPDATE("s_t_post");
                SET("isremoved = '1'");
            }
        }.toString();
        sql += batchJoinField("id",ids);
        return sql;
    }

    /**
     * @name update
     * @author 品讯科技
     * @param postModel
     * @Description 修改
     * @time 2021-02-19 13:46:37
     * @throws
     * @return sql
     */
    public String update(final PostModel postModel) {
        String sql = new SQL () {
            {
                UPDATE("s_t_post ");
                if(postModel.getPostCode() != null){
                    SET("postcode = #{postCode}");
                }
                if(postModel.getPostName() != null){
                    SET("postname = #{postName}");
                }
                if(postModel.getPostDesc() != null){
                    SET("postdesc = #{postDesc}");
                }
                if(postModel.getStatus() != null){
                    SET("status = #{status}");
                }
                if(postModel.getCreateTime() != null){
                    SET("createTime = #{createTime}");
                }
                if(postModel.getCreateUserCode() != null){
                    SET("createUserCode = #{createUserCode}");
                }
                if(postModel.getCreateUserName() != null){
                    SET("createUserName = #{createUserName}");
                }
                if(postModel.getUpdateTime() != null){
                    SET("updateTime = #{updateTime}");
                }
                if(postModel.getUpdateUserCode() != null){
                    SET("updateUserCode = #{updateUserCode}");
                }
                if(postModel.getUpdateUserName() != null){
                    SET("updateUserName = #{updateUserName}");
                }
                if(postModel.getIsRemoved() != null){
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
     * @time 2021-02-19 13:46:37
     * @throws
     * @return sql
     */
    public String findById(@Param ("id") String id) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("s_t_post t");
                WHERE("t.id = #{id}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param postDto
     * @Description 根据条件查询数据并分页
     * @time 2021-02-19 13:46:37
     * @throws
     * @return sql
     */
    public String findAll(final PostDto postDto) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("s_t_post t");
                WHERE("t.isremoved = '0'");
                if(!StringUtil.isBlank (postDto.getId())){
                    WHERE("t.id = #{id}");
                }
                if(!StringUtil.isBlank (postDto.getPostCode())){
                    WHERE("t.postcode = #{postCode}");
                }
                if(!StringUtil.isBlank (postDto.getPostName())){
                    WHERE("t.postname = #{postName}");
                }
                if(!StringUtil.isBlank (postDto.getPostDesc())){
                    WHERE("t.postdesc = #{postDesc}");
                }
                if(!StringUtil.isBlank (postDto.getStatus())){
                    WHERE("t.status = #{status}");
                }
                if(!StringUtil.isBlank (postDto.getCreateTime())){
                    WHERE("t.createTime = #{createTime}");
                }
                if(!StringUtil.isBlank (postDto.getCreateUserCode())){
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if(!StringUtil.isBlank (postDto.getCreateUserName())){
                    WHERE("t.createUserName = #{createUserName}");
                }
                if(!StringUtil.isBlank (postDto.getUpdateTime())){
                    WHERE("t.updateTime = #{updateTime}");
                }
                if(!StringUtil.isBlank (postDto.getUpdateUserCode())){
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (postDto.getUpdateUserName())){
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                if(!StringUtil.isBlank (postDto.getIsRemoved())){
                    WHERE("t.isremoved = #{isRemoved}");
                }
            }
        }.toString();
        return sql;
    }

    /**
     * @name postIsNormal
     * @author 品讯科技
     * @Description 根据roleId判断 岗位存在并未停用=true 岗位不存在或者决策停用=false
     * @time 2020-02-20 15:09:09
     * @throws
     * @return sql
     */
    public String postIsNormal(@Param ("postId") String postId) {
        String sql = new SQL () {
            {
                SELECT("t.*");
                FROM("s_t_post t");
                WHERE("t.isremoved = '0'");
                WHERE("t.id = #{postId}");
            }
        }.toString();
        return sql;
    }

}
