package com.silence.androidmvprxjavadome.mvp_base;

import com.silence.androidmvprxjavadome.retrofit.HttpCall;
import com.silence.androidmvprxjavadome.retrofit.ApiService;

/**
 * @作者: PJ
 * @创建时间: 2017/11/7 / 11:06
 * @描述: 这是一个 BaseModel 类.
 */
public class BaseModel implements IModel {

    protected static ApiService ApiService;
    // 初始化HttpService
    static {
        ApiService = HttpCall.getApiService();
    }

}
