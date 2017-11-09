package com.silence.androidmvprxjavadome;

import android.content.Context;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.CallSuper;

import com.silence.androidmvprxjavadome.model.RespMsg;
import com.silence.androidmvprxjavadome.retrofit.HttpUiTips;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * @作者: PJ
 * @创建时间: 2017/11/8 / 10:04
 * @描述: 这是一个 BaseObserver 类.
 */
public abstract class BaseObserver<T> implements Observer<RespMsg<T>> {

    private static final int RESPONSE_CODE_ERROR = -1;
    private int errorCode;
    private String errorMessage = "未知错误";
    private Context mContext;


    public BaseObserver(Context context) {
        mContext = context;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull RespMsg<T> respMsg) {
        if (RespMsg.OK == respMsg.getCode()) {
            onSuccess(respMsg.getData());
        } else {
            onFailure(respMsg.getCode(), respMsg.getMsg());
        }
    }


    /**
     * 成功后的数据交给子类自己去写
     * @param data
     */
    protected abstract void onSuccess(T data);


    /**
     * 一般错误的统一分类
     * @param e
     */
    @Override
    public void onError(@NonNull Throwable e) {

        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            errorCode = httpException.code();
            errorMessage = httpException.message();
            onFailure(errorCode, errorMessage);
        } else if (e instanceof SocketException) {
            errorCode = RESPONSE_CODE_ERROR;
            errorMessage = "服务器响应超时";
        } else if (e instanceof ConnectException) {
            errorCode = RESPONSE_CODE_ERROR;
            errorMessage = "网络连接异常，请检查网络";
        }else if (e instanceof UnknownHostException) {
            errorCode = RESPONSE_CODE_ERROR;
            errorMessage = "无法解析主机，请检查网络连接";
        } else if (e instanceof UnknownServiceException) {
            errorCode = RESPONSE_CODE_ERROR;
            errorMessage = "未知的服务器错误";
        } else if (e instanceof IOException) {  //飞行模式等
            errorCode = RESPONSE_CODE_ERROR;
            errorMessage = "没有网络，请检查网络连接";
        } else if (e instanceof NetworkOnMainThreadException) {//主线程不能网络请求，这个很容易发现
            errorCode = RESPONSE_CODE_ERROR;
            errorMessage = "主线程不能网络请求";
        } else if (e instanceof RuntimeException) { //很多的错误都是extends RuntimeException
            errorCode = RESPONSE_CODE_ERROR;
            errorMessage = "运行时错误";
        }

        onFailure(errorCode, errorMessage);

    }

    @Override
    public void onComplete() {

    }


    /**
     * 在失败后的统一处理,
     * 比如AlterDialog或SnackBar
     * @param code
     * @param msg
     */
    @CallSuper
    public void onFailure(int code, String msg) {
        if (RESPONSE_CODE_ERROR == code && mContext != null) {
            HttpUiTips.alertTip(mContext, msg);
        } else {
            disPoseErrorCode(code, msg);
        }
    }

    /**
     * 跟后台协商错误码并做统一处理
     * @param code
     * @param msg
     */
    private void disPoseErrorCode(int code, String msg) {
    }

}
