package com.example.readnews;

import android.app.Application;

import org.xutils.x;

public class MyApplication extends Application {
    public static String nickname;
    public String selectbypageurl="http://10.0.2.2:8000/article/select/page";
    public String inserturl="http://10.0.2.2:8000/article/insert";
    public String selectbyidurl="http://10.0.2.2:8000/article/select/id";
    public String deletebyidurl="http://10.0.2.2:8000/article/delete";
    public String updatebyidurl="http://10.0.2.2:8000/article/update/";
    public String imagebaseurl="http://10.0.2.2:8000/";
    public String NewsTypeInsertUrl = "http://10.0.2.2:8000/newstype/add";
    public String NewsTypeSelectUrl = "http://10.0.2.2:8000/newstype/select";
    public String NewsInsertUrl = "http://10.0.2.2:8000/news/add";
    public String NewsSelectUrl = "http://10.0.2.2:8000/news/select";
    public String NewsDetailInsertUrl = "http://10.0.2.2:8000/newsdetail/add";
    public String NewsDetailSelectUrl = "http://10.0.2.2:8000/newsdetail/select";
    public String UserInsertUrl = "http://10.0.2.2:8000/user/add";
    public String UserLoginUrl = "http://10.0.2.2:8000/user/login";
    public String UserSelectUrl = "http://10.0.2.2:8000/user/select";
    public String UserUpdateUrl = "http://10.0.2.2:8000/user/update";
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        //x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}