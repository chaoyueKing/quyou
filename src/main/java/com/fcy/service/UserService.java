package com.fcy.service;

import com.fcy.model.GetUserModel;
import com.fcy.model.LoginModel;
import com.fcy.model.UserModel;

import java.util.List;

/**
 * @Program: demo
 * @Description:
 * @Author: chaoyue.fan
 * @Create: 2018-11-19 15:20
 * @Version: 1.0.0
 **/
public interface UserService {

    /**
     * 根据规则批量获取用户  规则参考GetUserModel注释
     * @param getUserModel
     * @return java.util.List<com.example.model.UserModel>
     * @author choayue.fan
     * @creed: Talk is cheap,show me the code
     * @date 2018/11/19 15:47
     */
    List<UserModel> getUsers(GetUserModel getUserModel);

    /**
     * 用户登录
     * @param user 用户信息
     * @return
     */
    LoginModel doLogin(UserModel user);

    /**
     * 领取任务
     * @param loginModel 用户登录信息
     * @param rwIds 任务ids  多个用逗号分割
     */
    void doReceive(LoginModel loginModel, String... rwIds);

    /**
     * 根据天数获取任务ids
     * @param days 需要领取的任务天数，按任务页面顺序取值。num=1 时领取当前日期的任务（num=2，领取【今天+昨天】；num=3，领取【今天+昨天+前天】...）
     *            【超过7天此路不通】【超过7天此路不通】【超过7天此路不通】【超过7天此路不通】【超过7天此路不通】【超过7天此路不通】
     * @return void
     * @author chaoyue.fan
     * @creed: Talk is cheap,show me the code
     * @date 2018/11/23 14:15
     */
    List<String> getReceiveByNum(Integer days);

    /**
     * 提交任务
     * @param loginModel 用户登录信息
     * @param pathname 图片根路径 上传图片命名规则【23-青丘剑姬-王二麻子.png】 ‘23’：代表当前登录账号整数部分，‘青丘剑姬’：游戏没名称，‘王二麻子’：角色名
     */
    void doSumbit(LoginModel loginModel, String pathname);


    /**
     * 提现
     * @param loginModel 用户登录信息
     * @return void
     * @author chaoyue.fan
     * @creed: Talk is cheap,show me the code
     * @date 2018/11/21 16:10
     */
    void doWithdraw(LoginModel loginModel);

    /**
     * 获取当前账号审核状态
     * @param loginModel 用户登录信息
     * @return void
     * @author chaoyue.fan
     * @creed: Talk is cheap,show me the code
     * @date 2018/11/21 16:10
     */
    void getStatus(LoginModel loginModel);


}
