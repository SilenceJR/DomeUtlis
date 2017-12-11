package com.duimy.dmroomlibrary.user;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

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

}
