package com.fcy.run;

import com.fcy.model.LoginModel;
import com.fcy.model.UserModel;
import com.fcy.service.impl.UserServiceImpl;
import com.fcy.util.ForFile;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.fcy.run.GetUserTest.getFileName;

/**
 * @Program: quyou
 * @Description: 提交任务
 * @Author: chaoyue.fan
 * @Create: 2018-11-21 16:19
 * @Version: 1.0.0
 **/
public class TiJiaoRenWuTest {
    public static void main(String[] args) {
        try{
            long l = System.currentTimeMillis();
            UserServiceImpl userServiceImpl = new UserServiceImpl();
            List<UserModel> users = userServiceImpl.getUsers(GetUserTest.getUser());
            if (CollectionUtils.isEmpty(users)){
                System.out.println("没有获取到相关用户信息");
                return;
            }
            for (UserModel user:users) {
                LoginModel loginModel = userServiceImpl.doLogin(user);
                userServiceImpl.doSumbit(loginModel,"D:\\yxb\\Screenshots");
            }
            ForFile.delFile(getFileName()+userServiceImpl.SC_LOG_NAME);
            System.out.println("程序运行："+(System.currentTimeMillis()-l)/1000+"秒");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
