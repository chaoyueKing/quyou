package com.fcy.model;

/**
 * @Program: 登录返回信息model
 * @Description:
 * @Author: chaoyue.fan
 * @Create: 2018-11-19 15:16
 * @Version: 1.0.0
 **/
public class LoginModel {

    //登陆返回cookies
    private String cookies;
    //登陆返回 状态码
    private int statusCode;
    //登录用户
    private UserModel userModel;

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
