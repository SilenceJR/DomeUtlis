package com.duimy.tokendemo.network;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @作者: PJ
 * @创建时间: 2017/12/7 / 16:26
 * @描述: 这是一个 HttpInit 类.
 */
public class HttpInit {
    private static volatile HttpInit mInstance;

    private final int TIME_OUT = 10;
    private final OkHttpClient.Builder mBuilder;

    public static HttpInit getInstance() {
        if (mInstance == null) {
            synchronized (HttpInit.class) {
                if (mInstance == null) {
                    mInstance = new HttpInit();
                }
            }
        }
        return mInstance;
    }

    public HttpInit() {
        mBuilder = new OkHttpClient().newBuilder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new HttpLoggingInterceptor(message -> {
                        Log.d("HttpInit", message);
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
//                .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(TokenApplication.getmContext())));
        ;
    }

    public OkHttpClient.Builder getOkHttp() {
        if (mBuilder == null) {
            synchronized (HttpInit.class) {
                if (mBuilder == null) {
                    getInstance();
                }
            }
        }
        return mBuilder;
    }
}
