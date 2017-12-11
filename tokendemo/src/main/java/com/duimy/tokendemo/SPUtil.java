package com.duimy.tokendemo;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * @作者: PJ
 * @创建时间: 2017/12/7 / 18:03
 * @描述: 这是一个 SPUtil 类.
 */
public class SPUtil {

    private static SharedPreferences mSharedPreference;
    private static Context sContext;
    private static boolean isInit;

    public static void initSP(Context context) {
        sContext = context.getApplicationContext();
        if (mSharedPreference == null) {
            mSharedPreference = sContext.getSharedPreferences("config", MODE_PRIVATE);
        }
        isInit = true;
    }

    public static void saveToken(String token) {
        if (!isInit) {
            throw new RuntimeException("使用SPUtils之前请先在Application中初始化！");
        }
        mSharedPreference.edit()
                .putString("token", token)
                .apply();
    }

    public static String getToken() {
        if (!isInit) {
            throw new RuntimeException("使用SPUtils之前请先在Application中初始化！");
        }
        return mSharedPreference.getString("token", "");
    }

}
