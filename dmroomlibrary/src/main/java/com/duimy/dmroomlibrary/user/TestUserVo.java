package com.duimy.dmroomlibrary.user;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * @作者: PJ
 * @创建时间: 2017/11/30 / 11:32
 * @描述: 这是一个 TestUserVo 类.
 */
@Entity(tableName = "TestUserVo")
public class TestUserVo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;            // 用户ID
    @ColumnInfo(name = "_userName")
    private String userName;    // 用户名
    @ColumnInfo(name = "_nickName")
    private String nickName;    // 昵称
    @ColumnInfo(name = "_password")
    private String password;    // 密码

    @ColumnInfo(name = "_USER_AGE")
    private int userAge;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    @Override
    public String toString() {
        return "TestUserVo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", userAge=" + userAge +
                '}';
    }
}
