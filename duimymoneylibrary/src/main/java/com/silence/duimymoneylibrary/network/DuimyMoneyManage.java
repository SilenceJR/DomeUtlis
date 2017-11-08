package com.silence.duimymoneylibrary.network;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.silence.duimymoneylibrary.SecurityUtils;
import com.silence.duimymoneylibrary.inter.onCallNetWorkInterface;
import com.silence.duimymoneylibrary.model.RespMsg;
import com.silence.duimymoneylibrary.network.api.DuimyMoneyApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @作者: PJ
 * @创建时间: 2017/11/1 / 17:21
 * @描述: 这是一个 DuimyMoney 类.
 */
public class DuimyMoneyManage {

    public static final String ROOT_URL = "http://192.168.1.166/duimy/app/money/";

    private static DuimyMoneyApi sDuimyMoneyApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();

    {

        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("OkHttpClient", "OkHttpMessage:" + message);
            }

        });
        loggingInterceptor.setLevel(level);

        OkHttpClient.Builder builder = okHttpClient.newBuilder();
        builder.addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("user-agent", "Android")
                                .build();

                        return chain.proceed(request);
                    }
                }).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String[] keys = {"getRSAPubKey", "login", "register", "resetPsw", "getApkInfo"};

                Request request = chain.request();
                HttpUrl url = request.url();
                for (int i = 0; i < keys.length; i++) {
                    if ((url.pathSegments().get(url.pathSegments().size() - 1)).equals(keys[i])) {
                        return chain.proceed(request);
                    }
                }

                String query = url.query();
                //                if (StringUtils.isEmpty(query)) {
                //                    return chain.proceed(request);
                //                }
                String[] uriQuery = query.split("&");// a=1&a=2&a=3&b=1111
                Map<String, List<String>> kvMap = new HashMap<String, List<String>>();
                for (int i = 0; i < uriQuery.length; i++) {

                    String[] kv = uriQuery[i].split("=");
                    String k = kv[0];

                    String v = kv.length == 1 ? null : kv[1];

                    List<String> vs = kvMap.get(k);
                    if (vs == null) {
                        vs = new ArrayList<String>();
                        kvMap.put(k, vs);
                    }
                    vs.add(v);

                }
                List<String> keyList = new ArrayList<>(kvMap.keySet());
                Collections.sort(keyList);


                StringBuilder sb = new StringBuilder(url.uri().getPath() + "?");

                Iterator<String> iterator = keyList.iterator();

                while (iterator.hasNext()) {
                    String key = iterator.next();

                    List<String> vs = kvMap.get(key);
                    Collections.sort(vs);
                    for (String v : vs) {
                        sb.append(key + "=" + v + "&");
                    }
                }

                sb.replace(sb.length() - 1, sb.length(), "");
                String randomStr = UUID.randomUUID().toString().substring(20);
                String msg = sb.toString();

                String sign = SecurityUtils.aesEnc(msg, randomStr);

                HttpUrl.Builder builderSign = request.url().newBuilder()
                        .setEncodedQueryParameter("sign", sign)
                        .setEncodedQueryParameter("randomStr", randomStr)
                        .setEncodedQueryParameter("token", "1312161565165161");
                Request build = request.newBuilder()
                        .method(request.method(), request.body())
                        .url(builderSign.build())
                        .build();
                return chain.proceed(build);
            }
        });
    }


    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create(new GsonBuilder()
            .setLenient()
            .create());
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();


    public static DuimyMoneyApi getDuimyMoneyApi() {
        if (sDuimyMoneyApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sDuimyMoneyApi = retrofit.create(DuimyMoneyApi.class);
        }
        return sDuimyMoneyApi;
    }

    public void load(Observable<RespMsg> observable, final onCallNetWorkInterface<RespMsg> onCallNetWorkInterface) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<RespMsg>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RespMsg respMsg) {
                        if (RespMsg.OK == respMsg.getCode()) {
                            onCallNetWorkInterface.onSuccess(respMsg);
                        } else {
                            onCallNetWorkInterface.onError(respMsg.getMsg());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        onCallNetWorkInterface.onThrowableError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
