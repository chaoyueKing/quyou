package com.fcy.model;

public class RwStatesModel {

    private String userName;
    private String rwName;
    private String rwState;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRwName() {
        return rwName;
    }

    public void setRwName(String rwName) {
        this.rwName = rwName;
    }

    public String getRwState() {
        return rwState;
    }

    public void setRwState(String rwState) {
        this.rwState = rwState;
    }

    @Override
    public String toString() {
        return "当前用户：【"+userName+"】任务："+rwName+"，状态："+rwState+"\n";
    }
}
