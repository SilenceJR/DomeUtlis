package com.example.dmuser.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.dmuser.model.UserVo;

import java.util.List;

/**
 * @作者: PJ
 * @创建时间: 2017/11/24 / 15:21
 * @描述: 这是一个 UserVoDao 类.
 */
@Dao
public interface UserVoDao {

    @Query("SELECT * FROM UserVo")
    List<UserVo> getAll();

    @Query("SELECT * FROM UserVo WHERE _userName = (:userName)")
    UserVo queryUserVo(String userName);

    @Query("SELECT * FROM UserVo WHERE _userName IN (:userNames)")
    List<UserVo> queryUserVo(String... userNames);

    @Insert
    void insertUserVo(UserVo userVo);

    @Insert
    void insertUserVo(List<UserVo> userVos);

    @Update
    void updateUserVo(UserVo userVo);

    @Update
    void updateUserVo(List<UserVo> userVos);

    @Delete
    void deleteUserVo(UserVo userVo);


}
