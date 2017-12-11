package com.example.dmmonerylibrary;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * @作者: PJ
 * @创建时间: 2017/11/30 / 14:22
 * @描述: 这是一个 TestUserVoDao 类.
 */
@Dao
public interface TestUserVoDao {

    @Query("SELECT * FROM TestUserVo")
    LiveData<List<TestUserVo>> getAll();

    @Query("SELECT * FROM TestUserVo WHERE _userName = (:userName)")
    List<TestUserVo> getTestUserVoName(String userName);

    @Query("SELECT * FROM TestUserVo WHERE _userName = (:userNames)")
    TestUserVo loadTestUserVo(String userNames);

    @Insert
    void insertAll(TestUserVo testUserVo);

    @Update
    void updateUser(TestUserVo testUserVo);

    @Delete
    void deleteTestUserVo(TestUserVo testUserVo);

}
