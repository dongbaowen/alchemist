package com.alchemist.common;

/**
 * Created by Baowen on 2017/12/3.
 */
public enum RedisKey {
    LOGIN_USER("login.user", 3600, "用户缓存");

    private String key;
    private int expire;
    private String desc;

    RedisKey(String key, int expire, String desc) {
        this.key = key;
        this.expire = expire;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
