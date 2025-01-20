package com.px.project.web.entity;

import com.px.project.common.base.BaseModel;
import com.px.project.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Package com.px.project.px.entity
 * @ClassName: UserModel
 * @Description: 用户实体
 * @author 品讯科技
 * @time   2019年2月18日
 * @version V 1.0
 */
public class UserModel extends BaseModel {

    @ApiModelProperty(value = "登录名", dataType = "String", example = "云游潘安")
    @AttributeAnnotation(value="loginname")
    @Size(min=1,max=32,message = "登录名长度在1到32位")
    private String loginName;//登录名

    @ApiModelProperty(value = "用户代码", dataType = "String", example = "001")
    @AttributeAnnotation(value="usercode")
    private String userCode;//用户代码

    @ApiModelProperty(value = "用户姓名", dataType = "String", example = "盖帅")
    @AttributeAnnotation(value="username")
    private String userName;//用户姓名

    @ApiModelProperty(value = "用户姓名", dataType = "String", example = "盖帅")
    @AttributeAnnotation(value="realname")
    private String realName;//用户姓名

    @ApiModelProperty(value = "昵称", dataType = "String", example = "盖帅")
    @AttributeAnnotation(value="nickname")
    private String nickname;//昵称

    @ApiModelProperty(value = "性别", dataType = "String", example = "盖帅")
    @AttributeAnnotation(value="sex")
    private String sex;//性别

    @ApiModelProperty(value = "生日", dataType = "String", example = "盖帅")
    @AttributeAnnotation(value="birthday")
    private String birthday;//生日

    @ApiModelProperty(value = "头像", dataType = "String", example = "url")
    @AttributeAnnotation(value="image")
    private String image;//头像

    @ApiModelProperty(value = "状态, 0 冻结，1启用", dataType = "String", example = "1")
    @AttributeAnnotation(value="status")
    private String status;//状态, 0 冻结，1启用

    @ApiModelProperty(value = "密码", dataType = "String", example = "123456")
    @AttributeAnnotation(value="passWord")
    @Pattern(regexp = "^(\\w){6,18}$",message = "密码应为数字与字母组成的6-18位字符！")
    private String passWord;//密码

    @ApiModelProperty(value = "用户标记", dataType = "String", example = "1")
    @AttributeAnnotation(value="mark")
    @Pattern(regexp = "^\\d+$",message = "密码应为数字与字母组成的6-18位字符！")
    private String mark;//用户标记

    @ApiModelProperty(value = "手机号码", dataType = "String", example = "18686867686")
    @AttributeAnnotation(value="phone")
    private String phone;//手机号码

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
