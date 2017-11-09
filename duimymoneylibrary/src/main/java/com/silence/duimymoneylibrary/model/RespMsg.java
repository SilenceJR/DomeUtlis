package com.silence.duimymoneylibrary.model;

public class RespMsg {

    public static final int DEFAULT = 0;
    public static final int OK = 100;
    public static final int Timeout = 109;

    private int code = DEFAULT;
    private String msg = null;
    private Object data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
