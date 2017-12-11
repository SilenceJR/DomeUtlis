package com.example.cbcdemo;

import android.app.Application;

import com.example.dmmonerylibrary.UserVodb;

/**
 * @作者: PJ
 * @创建时间: 2017/11/30 / 15:46
 * @描述: 这是一个 cbcApplication 类.
 */
public class cbcApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UserVodb.init(this);
    }
}
