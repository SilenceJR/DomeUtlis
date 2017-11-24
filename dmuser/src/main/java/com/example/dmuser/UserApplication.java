package com.example.dmuser;

import android.app.Application;

import com.example.dmuser.room.UserVodb;

/**
 * @作者: PJ
 * @创建时间: 2017/11/24 / 17:15
 * @描述: 这是一个 UserApplication 类.
 */
public class UserApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UserVodb.init(this);
    }
}
