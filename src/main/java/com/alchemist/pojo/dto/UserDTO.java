package com.alchemist.pojo.dto;

import com.alchemist.pojo.User;
import com.alchemist.util.MD5Util;

import java.io.Serializable;

/**
 * Created by Baowen on 2017/12/3.
 */
public class UserDTO implements Serializable{

    private String username;

    private String password;

    private String email;

    private String phone;

    private String question;

    private String answer;

    private Integer role;

    public UserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public User converToUser(){
        User user = new User();
        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setPassword(MD5Util.MD5EncodeUtf8(this.password));
        user.setPhone(this.phone);
        user.setQuestion(this.question);
        user.setAnswer(this.answer);
        user.setRole(this.role);
        return user;
    }
}
