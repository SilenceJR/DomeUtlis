package com.example.dmuser.model;

import android.arch.persistence.room.ColumnInfo;

/**
 * @作者: PJ
 * @创建时间: 2017/11/28 / 13:59
 * @描述: 这是一个 UserSimpleVo 类.
 */
public class UserSimpleVo {

    @ColumnInfo(name = "_userName")
    private String userName;
    @ColumnInfo(name = "_nickName")
    private String nickName;
    @ColumnInfo(name = "_headImgPath")
    private String headImgPath;

    public UserSimpleVo() {
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

    public void setNickName(String nikeName) {
        this.nickName = nikeName;
    }

    public String getHeadImgPath() {
        return headImgPath;
    }

    public void setHeadImgPath(String headImgPath) {
        this.headImgPath = headImgPath;
    }

    @Override
    public String toString() {
        return "UserSimpleVo{" +
                "userName='" + userName + '\'' +
                ", nikeName='" + nickName + '\'' +
                ", headImgPath='" + headImgPath + '\'' +
                '}';
    }
}
