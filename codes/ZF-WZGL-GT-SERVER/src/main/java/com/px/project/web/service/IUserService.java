package com.px.project.web.service;

import com.px.project.common.base.IBaseService;
import com.px.project.common.base.Result;
import com.px.project.web.dto.UserDto;

public interface IUserService<T> extends IBaseService<T> {
    /**
     * @name findAll
     * @author 品讯科技
     * @param userDto
     * @Description 根据条件查询所有数据并分页
     * @time 2020-02-18 14:11:47
     * @throws
     * @return Result
     */
    Result findAll(UserDto userDto);


    /**
     * @name saveUser
     * @author 品讯科技
     * @param userDto
     * @Description 保存用户
     * @time 2020-02-18 14:11:47
     * @throws
     * @return Result
     */
    Result saveUser(UserDto userDto);
    /**
     * @name updateUser
     * @author 品讯科技
     * @param userDto
     * @Description 修改用户
     * @time 2020-02-18 14:11:47
     * @throws
     * @return Result
     */
    Result updateUser(UserDto userDto);

    /**
     * @name bingRole
     * @author 品讯科技
     * @param userId=用户id roleId
     * @Description 用户绑定角色
     * @time 2020-02-18 14:11:47
     * @throws
     * @return
     */
    boolean bingRole(String userId,String roleId);

    /**
     * @name bingPost
     * @author 品讯科技
     * @param userId=用户id postId
     * @Description 用户绑定岗位
     * @time 2020-02-18 14:11:47
     * @throws
     * @return
     */
    boolean bingPost(String userId,String postId);

    /**
     * @name bingProject
     * @author 品讯科技
     * @param userId=用户id projectIds
     * @Description 用户绑定项目
     * @time 2020-02-18 14:11:47
     * @throws
     * @return
     */
    boolean bingProject(String userId,String projectIds);

    /**
     * @name bingProject
     * @author 品讯科技
     * @param userId=用户id projectId
     * @Description 用户绑定项目
     * @time 2020-02-18 14:11:47
     * @throws
     * @return
     */
    boolean deleteProjectByUserId(String userId);

    /**
     *
     * @param id
     * @param passWord
     * @return
     */
    Result resetPassword (String id,String loginName,String passWord);

    /**
     *
     * @param userDto
     * @return
     */
    Result findList (UserDto userDto);

    /**
     *
     * @param userId
     * @return
     */
    Result findByUserId (String userId);

    /**
     *
     * @param userId
     * @return
     */
    Result porjectDropdown (String userId);


	boolean deleteMajorByUserId(String userId);

}
