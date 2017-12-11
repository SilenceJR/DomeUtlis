package com.duimy.tokendemo.network;

import com.duimy.tokendemo.model.RespMsg;
import com.duimy.tokendemo.network.api.DuimyApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * @作者: PJ
 * @创建时间: 2017/12/7 / 16:57
 * @描述: 这是一个 HttpCall 类.
 */
public class HttpCall {
    private static Retrofit mRetrofit = RetrofitInit.getInstance().getBuild();

    public static DuimyApi getDuimyApi() {
        return mRetrofit.create(DuimyApi.class);
    }

    public static <T> void load(Observable<RespMsg<T>> observable, BaseObserver<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
