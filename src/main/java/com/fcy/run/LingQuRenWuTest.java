package com.fcy.run;

import com.fcy.model.LoginModel;
import com.fcy.model.UserModel;
import com.fcy.service.UserService;
import com.fcy.service.impl.UserServiceImpl;

import java.util.List;

/**
 * @Program: quyou
 * @Description: 领取任务
 * @Author: chaoyue.fan
 * @Create: 2018-11-21 16:19
 * @Version: 1.0.0
 **/
public class LingQuRenWuTest {
    public static void main(String[] args) {
        try{
            long l = System.currentTimeMillis();
            UserService userServiceImpl = new UserServiceImpl();
            List<UserModel> users = userServiceImpl.getUsers(GetUserTest.getUser());
            for (UserModel user:users) {
                LoginModel loginModel = userServiceImpl.doLogin(user);
                userServiceImpl.doReceive(loginModel,"1811219883","1811211663");
            }
            System.out.println("程序运行："+(System.currentTimeMillis()-l)/1000+"秒");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
