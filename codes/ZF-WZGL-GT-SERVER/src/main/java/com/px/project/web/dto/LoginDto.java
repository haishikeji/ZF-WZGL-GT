package com.px.project.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@ApiModel(value = "LoginDto", description = "登录信息传输对象")
public class LoginDto {

    @ApiModelProperty(value = "id", dataType = "String", example = "admin")
    private String id;
    @NotBlank(message = "登录名不可以为空！")
    @ApiModelProperty(value = "登录名", dataType = "String", example = "admin")
    private String loginName;

    @NotBlank(message = "密码不可以为空！")
    @Pattern(regexp = "^(\\w){6,18}$",message = "密码应为数字与字母组成的6-18位字符！")
    @ApiModelProperty(value = "密码", dataType = "String", example = "123456")
    private String passWord;

    @ApiModelProperty(value = "用户类别", dataType = "String", example = "1")
    private String userMark;

    //    @NotBlank(message = "验证码不可以为空！")
    @ApiModelProperty(value = "验证码", dataType = "String", example = "6666")
    private String verification;

    //@Pattern(regexp = "^(\\w){6,18}$",message = "密码应为数字与字母组成的6-18位字符！")
    @ApiModelProperty(value = "老密码", dataType = "String", example = "123456")
    private String oldPassWord;


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getUserMark() {
        return userMark;
    }

    public void setUserMark(String userMark) {
        this.userMark = userMark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOldPassWord() {
        return oldPassWord;
    }

    public void setOldPassWord(String oldPassWord) {
        this.oldPassWord = oldPassWord;
    }
}
