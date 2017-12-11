package com.duimy.tokendemo.model;

/**
 * @作者: PJ
 * @创建时间: 2017/12/7 / 17:11
 * @描述: 这是一个 TokenModel 类.
 */
public class TokenModel {

    private String token;

    public TokenModel() {
    }

    public TokenModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenModel{" +
                "token='" + token + '\'' +
                '}';
    }
}
