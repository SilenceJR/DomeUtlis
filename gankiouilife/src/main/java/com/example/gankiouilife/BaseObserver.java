package com.example.gankiouilife;

import android.content.Context;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.CallSuper;
import android.widget.Toast;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * @作者: PJ
 * @创建时间: 2017/11/16 / 16:01
 * @描述: 这是一个 BaseObservable 类.
 */
public abstract class BaseObserver<T> implements Observer<GankModel<T>> {

    private final String TAG = BaseObserver.class.getSimpleName();
    public final static String Thread_Main="main";
    private final int RESPONSE_CODE_FAILED = -1;  //返回数据失败,严重的错误
    private final Context mContext;
    private int errorCode;
    private String errorMsg = "未知的错误！";


    public BaseObserver() {
        mContext = null;
    }

    public BaseObserver(Context context) {
        mContext = context;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(GankModel<T> tGankModel) {
        if (!tGankModel.isError()) {
            onSuccess(tGankModel.getResults());
        } else {
            onFailure(errorMsg);
        }

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            errorCode = httpException.code();
            errorMsg = httpException.getMessage();
        } else if (e instanceof SocketTimeoutException) {  //VPN open
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "服务器响应超时";
        } else if (e instanceof ConnectException) {
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "网络连接异常，请检查网络";
        }else if (e instanceof UnknownHostException) {
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "无法解析主机，请检查网络连接";
        } else if (e instanceof UnknownServiceException) {
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "未知的服务器错误";
        } else if (e instanceof IOException) {  //飞行模式等
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "没有网络，请检查网络连接";
        } else if (e instanceof NetworkOnMainThreadException) {//主线程不能网络请求，这个很容易发现
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "主线程不能网络请求";
        } else if (e instanceof RuntimeException) { //很多的错误都是extends RuntimeException
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "运行时错误";
        }

        onFailure(errorMsg);
    }

    @Override
    public void onComplete() {

    }


    protected abstract void onSuccess(List<T> results);

    @CallSuper
    public void onFailure(String errorMsg) {
        if (errorMsg.equals(RESPONSE_CODE_FAILED) && mContext != null) {
//            HttpUiTips.alertTip(mContext, errorMsg, code);
            Toasty.error(mContext, errorMsg, Toast.LENGTH_LONG).show();
        } else {
            disposeError(errorMsg);

        }
    }

    private void disposeError(String errorMsg) {
        if (mContext != null && Thread.currentThread().getName().toString().equals(Thread_Main)) {
            Toasty.warning(mContext, errorMsg, Toast.LENGTH_LONG).show();
        }
    }
}
