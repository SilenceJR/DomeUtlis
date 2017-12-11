package com.duimy.tokendemo.network;

import android.util.Log;

import com.duimy.tokendemo.BuildConfig;
import com.duimy.tokendemo.model.RespMsg;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @作者: PJ
 * @创建时间: 2017/12/7 / 17:43
 * @描述: 这是一个 BaseObserver 类.
 */
public abstract class BaseObserver<T> implements Observer<RespMsg<T>> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(RespMsg<T> respMsg) {
        if (RespMsg.OK == respMsg.getCode()) {
            onSuccess(respMsg.getData());
        } else {
            onFailure(respMsg.getCode(), respMsg.getMsg());
        }
    }




    @Override
    public void onError(Throwable e) {
//        Toast.makeText(TokenApplicationLike.getmContext(), "网络异常啦!!:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        if (BuildConfig.DEBUG) Log.d("BaseObserver", e.getMessage());
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(T data);

    private void onFailure(int code, String msg) {
//        Toast.makeText(TokenApplicationLike.getmContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
