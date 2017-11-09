package com.duimy.dmimsdk;

/**
 * Created by haley on 2017/7/13.
 */

public interface DMValueCallBack<T> {

    void onSuccess(T result);

    void onError(int code, String message);
}
