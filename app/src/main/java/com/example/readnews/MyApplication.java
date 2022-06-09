package com.example.readnews;

import android.app.Application;

import org.xutils.x;

public class MyApplication extends Application {
//    public String selectallurl="http://172.16.97.57:88/json/listjson.php";
    public String selectbypageurl="http://10.0.2.2:8000/article/select/page";
    public String inserturl="http://10.0.2.2:8000/article/insert";
    public String selectbyidurl="http://10.0.2.2:8000/article/select/id";
    public String deletebyidurl="http://10.0.2.2:8000/article/delete";
    public String updatebyidurl="http://10.0.2.2:8000/article/update/";
    public String imagebaseurl="http://10.0.2.2:8000/";
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        //x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}