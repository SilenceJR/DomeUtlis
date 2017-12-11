package com.example.dmmonerylibrary;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * @作者: PJ
 * @创建时间: 2017/11/30 / 16:14
 * @描述: 这是一个 TestVoBase 类.
 */
@Entity(tableName = "TestVoBase", indices = {@Index(value = {"_id"}, unique = true)})
public class TestVoBase {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    protected Long id;            // 用户ID
    @ColumnInfo(name = "_nickName")
    protected String nickName;    // 昵称
    @ColumnInfo(name = "_password")
    protected String password;    // 密码

    @Ignore
    public TestVoBase() {
    }


    public TestVoBase(String nickName, String password) {
        this.nickName = nickName;
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TestVoBase{" +
                "id=" + id +
                "nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
