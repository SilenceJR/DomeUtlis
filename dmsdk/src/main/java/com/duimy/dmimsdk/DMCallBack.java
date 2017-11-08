package com.duimy.dmimsdk;

/**
 * Created by haley on 2017/7/13.
 */

public interface DMCallBack {
    void onSuccess();

    void onError(int code, String message);
}
