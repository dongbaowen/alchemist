package com.alchemist.pojo.dto;

import com.alchemist.pojo.User;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Baowen on 2017/12/3.
 */
public class UserVO implements Serializable {

    private String username;

    private String email;

    private String phone;

    private Integer role;

    private Date createTime;

    private Date updateTime;

    public UserVO() {
    }

    public UserVO(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.role = user.getRole();
        this.createTime = user.getCreateTime();
        this.updateTime = user.getUpdateTime();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
