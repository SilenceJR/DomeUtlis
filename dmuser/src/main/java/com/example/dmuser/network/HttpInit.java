package com.example.dmuser.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.dmuser.BuildConfig;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @作者: PJ
 * @创建时间: 2017/11/17 / 15:40
 * @描述: 这是一个 HttpInit 类.
 */
public class HttpInit {
    private static HttpInit mInstance;
    private final Retrofit mRetrofit;

//    public final String BaseUrl = "http://www.duimy.com/";
    public final String BaseUrl = "http://192.168.1.40/";

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

        OkHttpClient.Builder builder = initHttpBuilder();

        //添加OkHttp联网
        //添加Gson解析
        //添加RxJava支持
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                //添加OkHttp联网
                .client(builder.build())
                //添加Gson解析
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient()
                        .create()))
                //添加RxJava支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    @NonNull
    private OkHttpClient.Builder initHttpBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        //读取超时
        builder.readTimeout(5, TimeUnit.SECONDS);
        //连接超时
        builder.connectTimeout(5, TimeUnit.SECONDS);
        //写入超时
        builder.writeTimeout(5, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        //添加拦截器
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor(message -> Log.d("HttpInit", "OkHttp:" + message)).setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        //设置请求头
        builder.addInterceptor(chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader("user-agent", "Android")
                    .build();

            return chain.proceed(request);
        });
        return builder;
    }


}
