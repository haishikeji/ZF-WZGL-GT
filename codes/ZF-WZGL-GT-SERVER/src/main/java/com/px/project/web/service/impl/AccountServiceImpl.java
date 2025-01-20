package com.px.project.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.px.project.common.base.BaseService;
import com.px.project.common.base.CommonBean;
import com.px.project.common.base.Result;
import com.px.project.common.base.ReturnMsgEnum;
import com.px.project.common.config.ApplicationProperties;
import com.px.project.common.exception.RequestException;
import com.px.project.common.utils.*;
import com.px.project.web.dao.IUserMapper;
import com.px.project.web.dto.LoginDto;
import com.px.project.web.entity.UserModel;
import com.px.project.web.service.IAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AccountServiceImpl")
public class AccountServiceImpl extends BaseService implements IAccountService {

    @Autowired
    IUserMapper userMapper;

    private Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    private final ApplicationProperties applicationProperties;

    public AccountServiceImpl(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public JSONObject signIn(LoginDto loginDto) {
        if ("".equals(loginDto.getLoginName()) || "".equals(loginDto.getPassWord())) {
            throw new RequestException(ReturnMsgEnum.Sing_In_Input_Empty.getMsg(), ReturnMsgEnum.Sing_In_Input_Empty);
        }

        //根据用户名去数据库中查询用户信息
        JSONObject loginResult = doLogin(new JSONObject() {
            {
                put("loginName", loginDto.getLoginName());
            }
        });

        int loginResultType = loginResult.getIntValue("loginResultType");
        switch (loginResultType) {
            case 0:
                throw new RequestException(ReturnMsgEnum.Login_Error.getMsg(), ReturnMsgEnum.Login_Error);
            case 1:
                break;
            case 2:
                throw new RequestException(ReturnMsgEnum.User_Locking.getMsg(), ReturnMsgEnum.User_Locking);
            default:
                break;
        }

        JSONObject userInfo = loginResult.getJSONObject("userInfo");
        String userId = userInfo.getString("id");

        if (!userInfo.getString("passWord").equals(MD5EncryptUtil.encrypt(loginDto.getPassWord() + userInfo.getString("loginName")))) {
            throw new RequestException(ReturnMsgEnum.Login_Error.getMsg(), ReturnMsgEnum.Login_Error);
        }
        String roleId = userMapper.findRoleUserIdByUserId(userId);
      /* String storeId = userMapper.findStoreIdByUserId(userId);
        */
        userInfo.put("roleId", roleId);
        JSONObject post = userMapper.findPostByUserId(userId);
        userInfo.put("postId", post.getString("postId"));
        userInfo.put("postName", post.getString("postName"));
        String sign = TokenUtil.getToken(userInfo);
      /*LoginLogModel loginLogModel=new LoginLogModel();
        loginLogModel.setUserId (userId);
        loginLogModel.setLoginTime (DateUtil.currentStringDate ());
        loginLogModel.setIsSuccess ("1");
        loginLogService.save (loginLogModel);*/
        // 将token放入redis
        //RedisUtil.expireDays(userId, sign, applicationProperties.getRedisToken().getTokenTimeOut());

        return new JSONObject() {
            {
                put("Authorization", sign);
                put("userCode", userInfo.getString("userCode"));
                put("userName", userInfo.getString("userName"));
                put("userId", userId);
                put("postId", post.getString("postId"));
                put("postName", post.getString("postName"));
                put("roleId", roleId);
                /*put("roleId", roleId);*/
            }
        };
    }

    @Override
    public JSONObject doLogin(JSONObject condition) {
        JSONObject loginResult = new JSONObject();
        UserModel userModel = userMapper.findByLoginName(condition.getString("loginName"));
        if (userModel == null) {
            loginResult.put("loginResultType", CommonBean.NUM_ZERO);
            return loginResult;
        } else {
            if (userModel.getStatus() != null) {
                if (CommonBean.NUM_ZERO == Integer.parseInt(userModel.getStatus())) {
                    loginResult.put("loginResultType", CommonBean.NUM_TWO);
                    return loginResult;
                }
            }
            loginResult.put("userInfo", JsonUtil.entityConvertToJson(userModel));
            loginResult.put("loginResultType", CommonBean.NUM_ONE);
            return loginResult;
        }
    }

    public Result verifyPassword(String loginName, String password) {
        UserModel userModel = userMapper.findByLoginName(loginName);
        if (userModel == null) {
            throw new RequestException(ReturnMsgEnum.User_IsNull.getMsg(), ReturnMsgEnum.User_IsNull);
        }
        if (!userModel.getPassWord().equals(MD5EncryptUtil.encrypt(password + loginName))) {
            throw new RequestException(ReturnMsgEnum.User_Password_Err.getMsg(), ReturnMsgEnum.User_Password_Err);
        }
        return Result.SUCCESS();
    }

    @Override
    public Result userRegister(UserModel userModel) {
        if (userMapper.findByLoginName(userModel.getLoginName()) != null) {
            return Result.buildResult(ReturnMsgEnum.LoginName_Repeat);
        }
        try {
            String passWord = MD5EncryptUtil.encrypt(userModel.getPassWord() + userModel.getLoginName());
            userModel.setId(UUIDUtil.get32UUID());
            userModel.setPassWord(passWord);
            userMapper.save(userModel);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.ERROR();
        }
        return Result.buildResult(ReturnMsgEnum.OK);
    }
}
