package com.duimy.dmimsdk.utils;

import com.duimy.dmimsdk.constant.DMConstant;
import com.duimy.dmimsdk.listener.onNetCallBack;
import com.duimy.dmimsdk.model.RespMsg;
import com.duimy.dmimsdk.model.uploadData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManage {

    private static Retrofit retrofit;
    private static Gson sGson;

    public static uploadApi getRetrofit() {
        if (retrofit == null) {
            retrofit = initRetrofit();
            sGson = new Gson();
        }
        return retrofit.create(uploadApi.class);
    }


    public static Retrofit initRetrofit() {

        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                DMLogUtils.d("OkHttpClient", "OkHttpMessage:" + message);
            }

        });
        loggingInterceptor.setLevel(level);

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        //读取超时
        builder.readTimeout(15, TimeUnit.SECONDS);
        //连接超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        //写入超时
        builder.writeTimeout(15, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        //添加拦截器
        builder.addInterceptor(loggingInterceptor);
        //设置请求头
        builder.addInterceptor(new Interceptor() {
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

                String[] keys = {"getRSAPubKey", "login", "register"};

                Request request = chain.request();
                HttpUrl url = request.url();
                for (int i = 0; i < keys.length; i++) {
                    if ((url.pathSegments().get(url.pathSegments().size() - 1)).equals(keys[i])) {
                        return chain.proceed(request);
                    }
                }

                String[] uriQuery = url.query().split("&");// a=1&a=2&a=3&b=1111
                Map<String, List<String>> kvMap = new HashMap<String, List<String>>();
                for (int i = 0; i < uriQuery.length; i++) {

                    if (uriQuery[i].contains("token")) {
                        continue;
                    }

                    String[] kv = uriQuery[i].split("=");
                    String k = kv[0];

                    String v = kv.length == 1 ? null : kv[1];

                    List<String> vs = kvMap.get(k);
                    if (vs == null){
                        vs = new ArrayList<String>();
                        kvMap.put(k,vs);
                    }
                    vs.add(v);

                }
                List<String> keyList = new ArrayList<>(kvMap.keySet());
                Collections.sort(keyList);


                StringBuilder sb = new StringBuilder(url.uri().getPath() + "?");

                Iterator<String> iterator = keyList.iterator();

                while (iterator.hasNext()){
                    String key = iterator.next();

                    List<String> vs = kvMap.get(key);
                    Collections.sort(vs);
                    for (String v : vs) {
                        sb.append(key+"="+v+"&");
                    }
                }

                sb.replace(sb.length() - 1, sb.length(), "");
                String randomStr = UUID.randomUUID().toString().substring(20);
                String msg = sb.toString();

                String sign = SecurityUtils.aesEnc(msg, randomStr);

                HttpUrl.Builder builderSign = request.url().newBuilder()
                        .setEncodedQueryParameter("sign", sign)
                        .setEncodedQueryParameter("randomStr", randomStr);
                Request build = request.newBuilder()
                        .method(request.method(), request.body())
                        .url(builderSign.build())
                        .build();
                return chain.proceed(build);
            }
        })
        ;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DMConstant.DUIMY_HOST)
                //添加OkHttp联网
                .client(builder.build())
                //添加Gson解析
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient()
                        .create()))
                //添加RxJava支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    public static void load(Observable<RespMsg> observable, final onNetCallBack<uploadData> onNetCallBack) {
        observable
                .map(new Function<RespMsg, uploadData>() {
                    @Override
                    public uploadData apply(@NonNull RespMsg respMsg) throws Exception {
                        if (respMsg.getCode() == RespMsg.OK) {
                            uploadData uploadData = sGson.fromJson(sGson.toJson(respMsg.getData()), uploadData.class);
                            return uploadData;
                        } else {
                            onNetCallBack.onError(respMsg.getCode(), respMsg.getMsg());
                            return null;
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<uploadData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull uploadData uploadData) {
                        onNetCallBack.onSuccess(uploadData);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        onNetCallBack.onThrowableError();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


}
