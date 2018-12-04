package com.fcy.model;

/**
 * @Program: quyou
 * @Description:
 * @Author: chaoyue.fan
 * @Create: 2018-12-04 14:41
 * @Version: 1.0.0
 **/
public class RwJsonModel {

    private String name;

    private String rwId;

    private String rwName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRwId() {
        return rwId;
    }

    public void setRwId(String rwId) {
        this.rwId = rwId;
    }

    public String getRwName() {
        return rwName;
    }

    public void setRwName(String rwName) {
        this.rwName = rwName;
    }

    @Override
    public String toString() {
        return "RwJsonModel{" +
                "name='" + name + '\'' +
                ", rwId='" + rwId + '\'' +
                ", rwName='" + rwName + '\'' +
                '}';
    }
}
