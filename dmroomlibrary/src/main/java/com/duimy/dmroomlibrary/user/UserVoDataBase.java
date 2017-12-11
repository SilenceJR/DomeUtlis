package com.duimy.dmroomlibrary.user;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * @作者: PJ
 * @创建时间: 2017/11/24 / 15:34
 * @描述: 这是一个 UserVoDataBase 类.
 */
@Database(entities = {UserVo.class, TestUserVo.class}, version = 1, exportSchema = false)
public abstract class UserVoDataBase extends RoomDatabase {
    public abstract UserVoDao mUserVoDao();
    public abstract TestUserVoDao mTestUserVoDao();
}
