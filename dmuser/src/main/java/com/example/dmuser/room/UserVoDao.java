package com.example.dmuser.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.dmuser.model.UserSimpleVo;
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
    LiveData<List<UserVo>> getAllListener();

    @Query("SELECT * FROM UserVo")
    List<UserVo> getAll();

    @Query("SELECT * FROM UserVo WHERE _userName = (:userName)")
    UserVo queryUserVo(String userName);

    @Insert
    void insertUserVo(UserVo userVo);

    @Update
    void updateUserVo(UserVo userVo);

    @Delete
    void deleteUserVo(UserVo userVo);

    @Query("SELECT _userName, _nickName, _headImgPath FROM UserVo")
    LiveData<List<UserSimpleVo>> queryAllUserSimpleVo();


}
