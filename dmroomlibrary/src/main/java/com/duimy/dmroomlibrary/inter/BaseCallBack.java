package com.duimy.dmroomlibrary.inter;

/**
 * @作者: PJ
 * @创建时间: 2017/11/24 / 16:57
 * @描述: 这是一个 BaseCallBack 类.
 */
public interface BaseCallBack<T> {
    void onSuccess(T t);
    void onError();
}
