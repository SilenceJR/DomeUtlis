package com.silence.checkappupdate.manage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManage {

    private static RetrofitManage mRetrofitManage;
    private Retrofit mRetrofit;

    public static RetrofitManage getInstance() {
        if (mRetrofitManage == null) {
            synchronized (RetrofitManage.class) {
                mRetrofitManage = new RetrofitManage();
            }
        }

        return mRetrofitManage;
    }

    private void initRetrofit() {
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {

            }
        });
        loggingInterceptor.setLevel(level);

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("user-agent", "Android")
                                .build();
                        return chain.proceed(request);
                    }
                });

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.166/duimy/app/")
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            synchronized (this) {
                initRetrofit();
            }
        }
        return mRetrofit;
    }

}
