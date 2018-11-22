package com.fcy.model;

/**
 * @Program:  用户信息model
 * @Description:
 * @Author: chaoyue.fan
 * @Create: 2018-11-19 15:15
 * @Version: 1.0.0
 **/
public class UserModel {

    //用户名
    private String name;
    //密码
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
