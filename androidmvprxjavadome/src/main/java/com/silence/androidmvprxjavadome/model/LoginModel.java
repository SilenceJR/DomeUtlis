package com.silence.androidmvprxjavadome.model;

public class LoginModel {

    /**
     * randomStr : fb0-b195bdf7471a
     * encToken : mHkiLgm0jbDPxLw8aZhhMN6Pfvhys2dBNQr8WF9Ja/w=
     * user : {"id":63,"userName":"100100"}
     */

    private String randomStr;
    private String encToken;
    private UserVo user;

    public String getRandomStr() {
        return randomStr;
    }

    public void setRandomStr(String randomStr) {
        this.randomStr = randomStr;
    }

    public String getEncToken() {
        return encToken;
    }

    public void setEncToken(String encToken) {
        this.encToken = encToken;
    }

    public UserVo getUser() {
        return user;
    }

    public void setUserVo(UserVo user) {
        this.user = user;
    }

}
