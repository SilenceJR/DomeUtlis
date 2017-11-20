package com.example.gankiouilife;

import retrofit2.Retrofit;

/**
 * @作者: PJ
 * @创建时间: 2017/11/16 / 15:44
 * @描述: 这是一个 HttpAPI 类.
 */
public class HttpAPI {

    private volatile static HttpAPI mInstall;
    private final Retrofit mRetrofit;

    public static HttpAPI getInstance() {
        if (mInstall == null) {
            synchronized (HttpCall.class) {
                if (mInstall == null) {
                    mInstall = new HttpAPI();
                }
            }
        }
        return mInstall;
    }

    private HttpAPI() {
        mRetrofit = HttpCall.getInstance().getRetrofit();
    }

    public GankioApi getGankio() {
        return mRetrofit.create(GankioApi.class);
    }
}
