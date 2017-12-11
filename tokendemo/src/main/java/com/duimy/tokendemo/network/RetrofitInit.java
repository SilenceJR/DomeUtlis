package com.duimy.tokendemo.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @作者: PJ
 * @创建时间: 2017/12/7 / 16:42
 * @描述: 这是一个 RetrofitInit 类.
 */
public class RetrofitInit {
    private static volatile RetrofitInit mInstance;

    private final String BASE_URL = "http://192.168.1.142";
    private final Retrofit mBuild;
    private final OkHttpClient.Builder mHttpInit = HttpInit.getInstance().getOkHttp();

    public static RetrofitInit getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitInit.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitInit();
                }
            }
        }
        return mInstance;
    }

    public RetrofitInit() {
        mBuild = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mHttpInit.build())
                .build();
    }

    public Retrofit getBuild() {
        return mBuild;
    }
}
