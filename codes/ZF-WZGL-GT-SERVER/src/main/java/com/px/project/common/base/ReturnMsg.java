package com.px.project.common.base;

/**
 * @author 品讯科技
 * @version V1.0
 * @Package com.px.pay.common.base
 * @ClassName:
 * @Description:
 * @time 2019/02/25
 */
public interface ReturnMsg {
    String getCode();
    String getMsg();
    Boolean getStatus();
    // 1:toast提示；2：确认框；3：页面跳转
    Integer promptType();
}
