package com.example.gankiouilife;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

/**
 * @作者: PJ
 * @创建时间: 2017/11/17 / 9:12
 * @描述: 这是一个 MainLifecycleObserver 类.
 */
public class MainLifecycleObserver implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void load() {
        if (BuildConfig.DEBUG) Log.d("MainLifecycleObserver", "Start----load");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void dismiss() {
        if (BuildConfig.DEBUG) Log.d("MainLifecycleObserver", "Stop---dismiss");
    }

}
