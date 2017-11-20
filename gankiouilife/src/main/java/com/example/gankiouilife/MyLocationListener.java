package com.example.gankiouilife;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.util.Log;

import static android.arch.lifecycle.Lifecycle.State.STARTED;

/**
 * @作者: PJ
 * @创建时间: 2017/11/17 / 10:31
 * @描述: 这是一个 MyLocationListener 类.
 */
public class MyLocationListener implements LifecycleObserver {
    private boolean enabled = false;

    private Context mContext;
    private Lifecycle mLifecycle;
    private CallBack mCallBack;

    public MyLocationListener(Context context, Lifecycle lifecycle, CallBack callBack) {
        mContext = context;
        mLifecycle = lifecycle;
        mCallBack = callBack;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart(LifecycleOwner lifecycleOwner) {
        if (enabled) {
            if (BuildConfig.DEBUG) Log.d("MainLifecycleObserver", "Start");
        }

        if (mCallBack != null) {
            mCallBack.onSuccess("onStart成功");
        }

    }


    public void enable() {
        enabled = true;
        if (mLifecycle.getCurrentState().isAtLeast(STARTED)) {
            if (BuildConfig.DEBUG) Log.d("MyLocationListener", "enable");
            // connect if not connected
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        if (enabled) {
            if (BuildConfig.DEBUG) Log.d("MainLifecycleObserver", "Stop");
        }

        if (mCallBack != null || mContext != null) {
            mCallBack = null;
            mContext = null;

            if (BuildConfig.DEBUG) Log.d("MyLocationListener", "已成功将mCallBack和mContext致NULL");
        }

    }

}
