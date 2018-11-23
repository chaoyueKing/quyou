package com.fcy.service.impl;

import com.fcy.model.GetUserModel;
import com.fcy.model.LoginModel;
import com.fcy.model.UserModel;
import com.fcy.service.UserService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.fcy.util.HttpClientUtils.*;
import static com.fcy.util.UploadUtil.doUpload;

/**
 * @Program: quyou
 * @Description:
 * @Author: chaoyue.fan
 * @Create: 2018-11-21 16:11
 * @Version: 1.0.0
 **/
public class UserServiceImpl implements UserService {
    public static final String QY_URL="http://www.quyou1688.com/";
    //登录URL
    public static final String LOGIN_URL = "http://www.quyou1688.com/login.php";
    //领取任务URL
    public static final String LQRW_URL = "http://www.quyou1688.com/renwu_view.php?lingqu=";
    //任务URL
    public static final String RW_URL = "http://www.quyou1688.com/wanchengtijiao.php";
    //获取提现金额url
    public static final String TXMSG_URL = "http://www.quyou1688.com/yuer.php";
    //去提现url
    public static final String TX_URL = "http://www.quyou1688.com/yuer.php?tixian=";
    //退出url
    public static final String TC_URL="http://www.quyou1688.com/logout.php";
    //任务列表url
    public static final String RW_LIST_URL="http://www.quyou1688.com/renwu.php?fenye=";

    //整数正则表达式
    public static final String NUM_REGEX = "[^0-9]";
    //汉字正则表达式
    public static final String STRING_REGEX = "[^\\u4e00-\\u9fa5]";

    public static final String[] RW_IDS = null;

    private static HttpClient httpClient= getConnection();

    @Override
    public List<UserModel> getUsers(GetUserModel getUserModel){
        if (StringUtils.isEmpty(getUserModel)) return null;
        List<UserModel> userList = new ArrayList<>();
        for (int i = getUserModel.getStart(); i <= getUserModel.getEnd(); i++) {
            UserModel user = new UserModel();
            DecimalFormat df = new DecimalFormat(getUserModel.getFormat());
            user.setName(getUserModel.getPrefix() + df.format(i));
            user.setPassword(getUserModel.getPwd());
            userList.add(user);
        }
        return userList;
    }

    @Override
    public LoginModel doLogin(UserModel user){
        if (StringUtils.isEmpty(user)) return null;
        Map<String, String> map = new HashMap<String, String>();
        //请求参数
        map.put("name", user.getName());
        map.put("password", user.getPassword());
        HttpUriRequest post = getRequestMethod(map, LOGIN_URL, "post");
        try{
            HttpResponse response = httpClient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 302) {
                LoginModel model = new LoginModel();
                //获取 cookie 信息
                List<Cookie> cookies = cookieStore.getCookies();
                StringBuffer tmpcookies = new StringBuffer();
                for (Cookie c:cookies) {
                    tmpcookies.append(c.getName()+"="+ c.getValue()+";");
                }
                model.setUserModel(user);
                model.setStatusCode(statusCode);
                model.setCookies(tmpcookies.toString());
                return model;
            } else {
                System.out.println("当前用户：【" + user.getName() + "】,登录失败。");
            }
        }catch (Exception e){
            System.out.println("登录异常:【"+e.getMessage()+"】");
        }

        return null;
    }

    @Override
    public void doReceive(LoginModel loginModel, String... rwIds){
        if (StringUtils.isEmpty(loginModel))return;
        if (StringUtils.isEmpty(rwIds))return;
        try{
            for (String s : rwIds) {
                HttpGet httpGet = new HttpGet(LQRW_URL + s);
                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                String message = EntityUtils.toString(entity, "utf-8");
                System.out.println("当前用户：【" + loginModel.getUserModel().getName() + "】，任务id：【" + s + "】,领取状态：【" + getValueByMatcher(message, STRING_REGEX) + "】");
            }
        }catch (Exception e){
            System.out.println("领取异常：【"+e.getMessage()+"】");
        }


    }

    @Override
    public void doReceiveByNum(LoginModel loginModel, int num) {
        String message = getResponseBody(RW_LIST_URL+num);
        Document doc = Jsoup.parse(message);
        Elements elements = doc.select(".ui-tab-content").select("a");
        String[] rwIds =new String[num];
        if (elements.isEmpty())return;
        for (int i = 0; i< elements.size();i++){
            String lqUrl = QY_URL+elements.get(i).attr("href");
            System.out.println(lqUrl);
            String msg = getResponseBody(lqUrl);
            Document doc1 = Jsoup.parse(msg);
            Elements elements1 = doc1.select(".bl_view_mall");
            for (Element e1:elements1) {
                rwIds[i] = getValueByMatcher(e1.text(),NUM_REGEX);
            }
        }
        System.out.println(Arrays.toString(rwIds));
        doReceive(loginModel,rwIds);
        return;
    }

    @Override
    public void doSumbit(LoginModel loginModel, String pathname){
        if (StringUtils.isEmpty(loginModel)) return;
        if (StringUtils.isEmpty(pathname))return;
        //获取任务信息
        String message = getResponseBody(RW_URL);
        if (StringUtils.isEmpty(message)){
            System.out.println("没有获取到对应信息");
            return;
        }
        //封装任务信息 key:任务id  value：任务名称
        Map<String, String> map = new HashMap<>();
        Document doc = Jsoup.parse(message);
        Elements elements = doc.select("option");
        for (Element e : elements) {
            if (!StringUtils.isEmpty(e.val())) {
                map.put(e.val(), e.text());
            }
        }
        //无提交任务直接返回
        if (map == null || map.size() == 0){
            System.out.println("当前用户：【" + loginModel.getUserModel().getName() + "】，暂无可提交任务");
            return;
        }
        //获取用户名整数部分，用来匹配图片
        Integer num = Integer.valueOf(getValueByMatcher(loginModel.getUserModel().getName(), NUM_REGEX));
        //根据用户名数字部分过滤文件
        File allFile = new File(pathname);
        File[] files = allFile.listFiles(new FileFilter() {// 运用内部匿名类获得文件
            @Override
            public boolean accept(File pathname) {// 实现FileFilter类的accept方法
                if (pathname.isDirectory()
                        || (pathname.isFile() && pathname.getName().startsWith(num + "-")))// 目录或文件包含关键字
                    return true;
                return false;
            }
        });
        //遍历map保存图片
        Iterator entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String rwid = (String) entry.getKey();
            String rwname = (String) entry.getValue();
            for (File f : files) {
                String fileName = f.getName().replaceAll("(.jpg|.png|.bmp|.gif)", "");//获得图片名称
                String[] split = fileName.split("-");//根据规则切分图片名
                if (!StringUtils.isEmpty(rwname) && rwname.contains(split[1])) {
                    //封装上传信息
                    Map<String, String> textMap = new HashMap<>();
                    textMap.put("id", rwid);
                    textMap.put("youxiid", split[2]);
                    textMap.put("Cookie",loginModel.getCookies());
                    Map<String, String> fileMap = new HashMap<>();
                    fileMap.put("upfile", pathname + "\\" + f.getName());
                    String upload = doUpload(RW_URL, textMap, fileMap);
                    System.out.println("当前用户：【" + loginModel.getUserModel().getName() + ":("+split[2]+")】,上传文件：【" + f.getName() + "】,任务ID：【" + rwid + "】,上传结果：【"+getValueByMatcher(upload,STRING_REGEX)+"】");
                }
            }
        }
    }

    @Override
    public void doWithdraw(LoginModel loginModel){
        if (StringUtils.isEmpty(loginModel))return;
        try{
            String message = getResponseBody(TXMSG_URL);
            if (StringUtils.isEmpty(message)){
                System.out.println("没有获取到对应信息");
                return;
            }
            Document doc = Jsoup.parse(message);
            Elements elements = doc.select(".register_verify").select("h1");
            for (Element e : elements) {
                String text = getValueByMatcher(e.text(), "[^0-9]");
                if (!StringUtils.isEmpty(text) && Integer.valueOf(text) > 0) {
                    HttpGet httpGet = new HttpGet(TX_URL + text);
                    HttpResponse response = httpClient.execute(httpGet);
                    HttpEntity entity = response.getEntity();
                    String msg = EntityUtils.toString(entity, "utf-8");
                    System.out.println("当前登录用户：【" + loginModel.getUserModel().getName() + "】，提现金额：【" + text + "】，提现结果：【" + getValueByMatcher(msg, STRING_REGEX) + "】");
                } else {
                    System.out.println("当前登录用户：【" + loginModel.getUserModel().getName() + "】，提现金额：【0】，提现结果：【金额为0无法提现】");
                }
            }
        }catch (Exception e){
            System.out.println("提现异常：【"+e.getMessage()+"】");
        }
    }


    /**
     * 根据规则获取对应的值
     *
     * @param target 源
     * @param regEx  匹配规则
     * @return java.lang.String
     * @author chaoyue.fan
     * @creed: Talk is cheap,show me the code
     * @date 2018/11/21 14:41
     */
    private static String getValueByMatcher(String target, String regEx) {
        if (StringUtils.isEmpty(target)) return "";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(target);
        String value = m.replaceAll("").trim();
        return value;
    }

    /**
     * GET请求获取请求信息
     *
     * @param url GET请求地址
     * @return java.lang.String
     * @author chaoyue.fan
     * @creed: Talk is cheap,show me the code
     * @date 2018/11/21 15:09
     */
    private String getResponseBody(String url){
        try{
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String message = EntityUtils.toString(entity, "utf-8");
            return message;
        }catch (Exception e){
            System.out.println("获取连接信息异常：【"+e.getMessage()+"】");
        }
        return null;
    }

    private void tuiChui() throws Exception{
        HttpGet get = new HttpGet(TC_URL);
        HttpResponse req = httpClient.execute(get);
        int statusCode = req.getStatusLine().getStatusCode();
        System.out.println("statusCode:"+statusCode);
        HttpEntity tcentity = req.getEntity();
        String msg = EntityUtils.toString(tcentity, "utf-8");
        System.out.println("用户退出,切换用户");
    }
}
