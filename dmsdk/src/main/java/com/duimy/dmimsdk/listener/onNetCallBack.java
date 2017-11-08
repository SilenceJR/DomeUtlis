package com.duimy.dmimsdk.listener;

public interface onNetCallBack<T> {

    void onSuccess(T t);

    void onError(int code, String errorMsg);

    void onThrowableError();
}
