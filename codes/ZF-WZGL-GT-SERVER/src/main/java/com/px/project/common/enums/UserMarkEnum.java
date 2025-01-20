package com.px.project.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserMarkEnum {
    User_Mark_One(1,"系统"),
    User_Mark_Two(2,"其他"),
    ;

    private Integer code;

    private String name;

    UserMarkEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private static final Map<Integer, UserMarkEnum> codeEnum = new HashMap<> ();
    static {
        for (UserMarkEnum paymentType : values()) {
            codeEnum.put(paymentType.getCode(), paymentType);
        }
    }

    // Returns Blah for string, or null if string is invalid
    public static UserMarkEnum fromCode(Integer code) {
        return codeEnum.get(code);
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
