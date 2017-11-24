package com.example.cbcdemo.mvp;

import android.support.annotation.NonNull;

/**
 * @作者: PJ
 * @创建时间: 2017/11/23 / 13:56
 * @描述: 这是一个 Resource 类.
 */
public class Resource<T> {
    @NonNull
    public String code;

    public T data;

    public String errorMsg;

    public Resource() {
    }

    public Resource(@NonNull String code, T data, String errorMsg) {
        this.code = code;
        this.data = data;
        this.errorMsg = errorMsg;
    }


}
