package com.fcy.model;

/**
 * @Program:  获取用户所需要的参数model
 * @Description:
 * @Author: chaoyue.fan
 * @Create: 2018-11-19 15:24
 * @Version: 1.0.0
 **/
public class GetUserModel {

    //用户名前缀部分
    private String prefix;
    //用户名后缀数字部分格式，有几位数字这里写几个0
    private String format;
    //用户密码
    private String pwd;
    //开始账户索引
    private int start;
    //结束id索引
    private int end;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
