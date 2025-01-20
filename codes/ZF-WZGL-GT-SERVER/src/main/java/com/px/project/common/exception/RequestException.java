package com.px.project.common.exception;

import com.px.project.common.base.Result;
import com.px.project.common.base.ReturnMsgEnum;

import java.io.Serializable;

//自定义异常类
//@Builder
public class RequestException extends RuntimeException implements Serializable {

    private String code;
    private String msg;
    private Boolean status;
    private Exception e;
    private ReturnMsgEnum returnMsgEnum;
    private Result result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Exception getE() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }

    public ReturnMsgEnum getReturnMsgEnum() {
        return returnMsgEnum;
    }

    public void setReturnMsgEnum(ReturnMsgEnum returnMsgEnum) {
        this.returnMsgEnum = returnMsgEnum;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    //无参构造
    public RequestException() {
    }

    //利用RuntimeException的构造加入message，作为异常抛出信息
    public RequestException(String message, ReturnMsgEnum msgEnum) {
        super(message);
        this.code = msgEnum.getCode();
        this.msg = msgEnum.getMsg();
        this.status = msgEnum.getStatus();
    }

    //加入异常，这个异常是封装之前的异常
    public RequestException(String message, ReturnMsgEnum msgEnum, Exception e) {
        super(message);
        this.msg = msg;
        this.code = msgEnum.getCode();
        this.msg = msgEnum.getMsg();
        this.status = msgEnum.getStatus();
        this.e = e;
    }


    public RequestException(ReturnMsgEnum msgEnum, Exception e) {
        this.code = msgEnum.getCode();
        this.msg = msgEnum.getMsg();
        this.status = msgEnum.getStatus();
        this.e = e;
    }

    public RequestException(Result result) {
        this.result = result;
    }

    public RequestException(ReturnMsgEnum msgEnum) {
        this.code = msgEnum.getCode();
        this.msg = msgEnum.getMsg();
        this.status = msgEnum.getStatus();
        this.returnMsgEnum = msgEnum;
    }

    public RequestException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RequestException(String code, String msg, Boolean status) {
        this.code = code;
        this.msg = msg;
        this.status = status;
        this.e = e;
    }

    public RequestException(String code, String msg, Boolean status, Exception e) {
        this.code = code;
        this.msg = msg;
        this.status = status;
        this.e = e;
    }

    public synchronized static RequestException fail(String msg) {
        return new RequestException(ReturnMsgEnum.Login_Err.getCode(),msg,false);
    }

    public synchronized static RequestException fail(String msg, Exception e) {
        return new RequestException(ReturnMsgEnum.Login_Err.getCode(),msg,false,e);
    }

    public synchronized static RequestException fail(String code, String msg, Exception e) {
        return new RequestException(code,msg,false,e);
    }

}
