package com.example.dmmonerylibrary;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * @作者: PJ
 * @创建时间: 2017/11/23 / 18:06
 * @描述: 这是一个 UserDao 类.
 */

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE USER_NAME = (:userNames)")
    List<User> loadAllByIds(String userNames);

    @Insert
    void insertAll(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);
}
