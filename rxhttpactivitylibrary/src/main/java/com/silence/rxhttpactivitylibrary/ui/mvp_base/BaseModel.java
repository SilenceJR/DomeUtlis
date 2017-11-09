package com.silence.rxhttpactivitylibrary.ui.mvp_base;

import com.silence.rxhttpactivitylibrary.http.core.ApiService;
import com.silence.rxhttpactivitylibrary.http.core.HttpCall;

/**
 * 一般的Model 层会有通过APi 去请求网络
 *
 * 数据库的DaoSession   要不也那个啥
 *
 */
public abstract class BaseModel<T extends ApiService> implements IModel {

    protected T mApiService;

    // 初始化HttpService
    {
        mApiService = (T) HttpCall.getApiService();
    }


}
