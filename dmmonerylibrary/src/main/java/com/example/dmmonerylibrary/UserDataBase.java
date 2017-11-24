package com.example.dmmonerylibrary;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * @作者: PJ
 * @创建时间: 2017/11/23 / 18:11
 * @描述: 这是一个 UserDataBase 类.
 */
@Database(entities = {User.class}, version = 3, exportSchema = false)
public abstract class UserDataBase extends RoomDatabase{
    public abstract UserDao mUserDao();
}
