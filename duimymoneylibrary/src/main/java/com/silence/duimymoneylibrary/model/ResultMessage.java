package com.silence.duimymoneylibrary.model;

/**
 * @作者: PJ
 * @创建时间: 2017/11/1 / 17:24
 * @描述: 这是一个 ResultMessage 类.
 */
public class ResultMessage<T> {

    public static int CODE_OK = 200;

    //消息返回码
    private int code;
    //错误消息
    private String errorMessge;
    //返回结果
    private T t;

    public ResultMessage() {
    }

    public ResultMessage(int code, String errorMessge, T t) {
        this.code = code;
        this.errorMessge = errorMessge;
        this.t = t;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMessge() {
        return errorMessge;
    }

    public void setErrorMessge(String errorMessge) {
        this.errorMessge = errorMessge;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "ResultMessage{" +
                "code=" + code +
                ", errorMessge='" + errorMessge + '\'' +
                ", t=" + t +
                '}';
    }
}
