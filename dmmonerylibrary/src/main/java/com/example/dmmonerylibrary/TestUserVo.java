package com.example.dmmonerylibrary;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;

/**
 * @作者: PJ
 * @创建时间: 2017/11/30 / 11:32
 * @描述: 这是一个 TestUserVo 类.
 */
@Entity(tableName = "TestUserVo", indices = @Index(value = "_new_password", unique = false), inheritSuperIndices = true, foreignKeys = @ForeignKey(entity = TestVoBase.class, parentColumns = "_id", childColumns = "_new_password"))
public class TestUserVo extends TestVoBase{


    @ColumnInfo(name = "_userName")
    private String userName;    // 用户名
    @ColumnInfo(name = "_new_password")
    protected String new_password;    // 密码


    @Ignore
    public TestUserVo() {
    }

    public TestUserVo(String userName, String nickName, String password, String new_password) {
        super(nickName, password);
        this.userName = userName;
        this.new_password = new_password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    @Override
    public String toString() {
        return "TestUserVo{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", new_password='" + new_password + '\'' +
                '}';
    }
}
