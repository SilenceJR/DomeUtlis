package com.example.gankiouilife;

/**
 * @作者: PJ
 * @创建时间: 2017/11/17 / 9:44
 * @描述: 这是一个 CallBack 类.
 */
public interface CallBack<T> {

    void onSuccess(String msg);
    void onError();
    void onGetValue(T t);

}
