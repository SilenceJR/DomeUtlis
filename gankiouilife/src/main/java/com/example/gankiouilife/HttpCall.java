package com.example.gankiouilife;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @作者: PJ
 * @创建时间: 2017/11/16 / 15:32
 * @描述: 这是一个 HttpCall 类.
 */
public class HttpCall {

    private static final String BASE_URL = "http://gank.io/api/";
    private static final int TIME_OUT = 5;

    private volatile static HttpCall mHttpCall;
    private Retrofit mRetrofit;

    public static HttpCall getInstance() {
        if (mHttpCall == null) {
            synchronized (HttpCall.class) {
                if (mHttpCall == null) {
                    mHttpCall = new HttpCall();
                }
            }
        }
        return mHttpCall;
    }

    private HttpCall() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    if (BuildConfig.DEBUG) Log.d("HttpCall", "OkHttp : " + message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
