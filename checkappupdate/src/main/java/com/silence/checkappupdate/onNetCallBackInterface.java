package com.silence.checkappupdate;

public interface onNetCallBackInterface<T> {
    /**
     * 在成功后回调
     * @param t
     */
     void onSuccess(T t);

    /**
     * 在失败后回调
     * @param errorMsg
     */
    void onError(String errorMsg);

    /**
     * 在网络异常后回调
     */
    void onThrowableError();

}
