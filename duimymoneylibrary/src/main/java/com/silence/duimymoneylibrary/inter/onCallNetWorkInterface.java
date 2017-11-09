package com.silence.duimymoneylibrary.inter;

/**
 * @作者: PJ
 * @创建时间: 2017/11/1 / 17:48
 * @描述: 这是一个 onCallNetWorkInterface 类.
 */
public interface onCallNetWorkInterface<T> {
    void onSuccess(T t);
    void onError(String errorMessage);
    void onThrowableError(Throwable e);
}
