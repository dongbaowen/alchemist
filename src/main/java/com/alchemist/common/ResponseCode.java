package com.alchemist.common;

/**
 * Created by Baowen on 2017/12/3.
 */
public enum ResponseCode {

    //分类相关
    CATEGORY_NOT_FOUND(20002, "未找到该分类"),

    //用户相关
    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NEED_LOGIN(10,"NEED_LOGIN"),
    USER_NOT_FOUND(100001, "没有找到该用户"),
    PERMISSION_DEFINDE(100002, "用户权限不足"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;


    ResponseCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }
}
