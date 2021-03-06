package com.fcy.run;

import com.fcy.model.LoginModel;
import com.fcy.model.UserModel;
import com.fcy.service.UserService;
import com.fcy.service.impl.UserServiceImpl;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Program: quyou
 * @Description: 懒人任务领取 不用从页面抓取任务IDS，自动获取
 * @Author: chaoyue.fan
 * @Create: 2018-11-23 14:13
 * @Version: 1.0.0
 **/
public class LanrenLQTest {
    public static void main(String[] args) {
        try{
            long l = System.currentTimeMillis();
            UserService userServiceImpl = new UserServiceImpl();
            List<UserModel> users = userServiceImpl.getUsers(GetUserTest.getUser());
            if (CollectionUtils.isEmpty(users)){
                System.out.println("没有获取到相关用户信息");
                return;
            }
            //根据天数获取任务ids days：天数 为null时领取当天任务
            List<String> list= userServiceImpl.getReceiveByNum(null);
            if (CollectionUtils.isEmpty(list)){
                System.out.println("当前没有可领取的任务");
                return;
            }
            for (UserModel user:users) {
                LoginModel loginModel = userServiceImpl.doLogin(user);
                userServiceImpl.doReceive(loginModel, list.toArray(new String[list.size()]));
            }
            System.out.println("程序运行："+(System.currentTimeMillis()-l)/1000+"秒");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
