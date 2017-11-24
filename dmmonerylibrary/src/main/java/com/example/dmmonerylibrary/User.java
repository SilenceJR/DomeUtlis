package com.example.dmmonerylibrary;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * @作者: PJ
 * @创建时间: 2017/11/23 / 18:01
 * @描述: 这是一个 User 类.
 */

@Entity(tableName = "users", indices = {@Index(value = {"USER_NAME"},unique = true)})
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userId;

    @ColumnInfo(name = "USER_NAME")
    private String userName;
    @ColumnInfo(name = "USER_AGE")
    private int userAge;

    @Ignore
    public User() {
    }

    public User(String userName, int userAge) {
        this.userName = userName;
        this.userAge = userAge;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (userAge != user.userAge) return false;
        return userName != null ? userName.equals(user.userName) : user.userName == null;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + userAge;
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userAge=" + userAge +
                '}';
    }
}
