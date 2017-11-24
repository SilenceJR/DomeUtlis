package com.example.dmuser.network;


import com.example.dmuser.network.api.UserVoApi;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * @作者: PJ
 * @创建时间: 2017/11/9 / 14:58
 * @描述: 这是一个 HttpCall 类.
 */
public class HttpCall {
    private volatile static HttpCall mInstance;
    private final Retrofit mRetrofit;

    public static HttpCall getInstance() {
        if (mInstance == null) {
            synchronized (HttpCall.class) {
                if (mInstance == null) {
                    mInstance = new HttpCall();
                }
            }
        }
        return mInstance;
    }

    public HttpCall() {
        mRetrofit = HttpInit.getInstance().getRetrofit();
    }

    public UserVoApi getAPI() {
        return mRetrofit.create(UserVoApi.class);
    }

    public static <T> void load(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

//    public static <T> void load(Observable<RespMsg<T>> observable, BaseObserver<T> baseObserver) {
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(baseObserver);
//    }
//
//    public static void UpdateUserVo(Observable<RespMsg<UserVo>> observable, final BaseObserver<UserVo> baseObserver) {
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(userVoRespMsg -> {
//                    if (RespMsg.OK == userVoRespMsg.getCode()) {
//                        DbUserManage.getInstance().saveUserVo(userVoRespMsg.getData());
//                    }
//                    return userVoRespMsg;
//                })
//                .subscribe(baseObserver);
//    }

}
