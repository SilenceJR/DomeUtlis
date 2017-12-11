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
 * @创建时间: 2017/12/1 / 14:38
 * @描述: 这是一个 TestVoBaseDao 类.
 */
@Dao
public interface TestVoBaseDao {
    @Query("SELECT * FROM TestVoBase")
    List<TestVoBase> getAll();

    @Query("SELECT * FROM TestVoBase")
    LiveData<List<TestVoBase>> getAllListener();

    @Query("SELECT * FROM TestVoBase WHERE _id = (:id)")
    List<TestVoBase> getTestUserVoName(Long id);

    @Query("SELECT * FROM TestVoBase WHERE _nickName = (:userName)")
    TestVoBase UserNameByTestBase(String userName);

    @Query("SELECT * FROM TestVoBase WHERE _id = (:id)")
    TestVoBase loadIdByTestBase(Long id);

    @Insert
    void insertAll(TestVoBase testVoBase);

    @Update
    void updateUser(TestVoBase testVoBase);

    @Delete
    void deleteTestUserVo(TestVoBase testVoBase);
}
