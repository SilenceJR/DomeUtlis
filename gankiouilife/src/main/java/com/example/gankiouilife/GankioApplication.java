package com.example.gankiouilife;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import butterknife.ButterKnife;

/**
 * @作者: PJ
 * @创建时间: 2017/11/16 / 15:18
 * @描述: 这是一个 GankioApplication 类.
 */
public class GankioApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (activity instanceof IActivity) {
                    activity.setContentView(((IActivity) activity).getlayoutId());
                    ButterKnife.bind(activity);
                    ((IActivity) activity).initView();
                    ((IActivity) activity).initListener();
                    ((IActivity) activity).initData();
                }

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }
}
