package com.fcy.run;

import com.fcy.model.GetUserModel;

/**
 * @Program: quyou
 * @Description:
 * @Author: chaoyue.fan
 * @Create: 2018-11-21 16:20
 * @Version: 1.0.0
 **/
public class GetUserTest {
    public static GetUserModel getUser(){
        GetUserModel getUserModel = new GetUserModel();

        /*
        getUserModel.setFormat("0000");
        getUserModel.setPrefix("fcy");
        getUserModel.setPwd("11111");
        getUserModel.setStart(1);
        getUserModel.setEnd(22);
*/

        getUserModel.setFormat("000");
        getUserModel.setPrefix("yxb");
        getUserModel.setPwd("11111");
        getUserModel.setStart(1);
        getUserModel.setEnd(1);

        return getUserModel;
    }
}
