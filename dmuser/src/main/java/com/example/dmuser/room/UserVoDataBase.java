package com.example.dmuser.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.dmuser.model.UserVo;

/**
 * @作者: PJ
 * @创建时间: 2017/11/24 / 15:34
 * @描述: 这是一个 UserVoDataBase 类.
 */
@Database(entities = {UserVo.class}, version = 1, exportSchema = false)
public abstract class UserVoDataBase extends RoomDatabase {
    public abstract UserVoDao mUserVoDao();
}
