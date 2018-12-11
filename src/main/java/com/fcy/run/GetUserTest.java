package com.fcy.run;

import com.fcy.model.GetUserModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        getUserModel.setFormat("000");
        getUserModel.setPrefix("yxb");
        getUserModel.setPwd("11111");
        getUserModel.setStart(1);
        getUserModel.setEnd(1);

        return getUserModel;
    }

    public static String getFileName(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String result = format.format(today);
        System.out.println(result);
        return result;
    }
}
