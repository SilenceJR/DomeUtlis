package com.duimy.dmimsdk.utils;

import android.util.Log;

public class DMLogUtils {

    private static final String TAG = "Duimy";

    /**
     * use DMLogUtils.v.
     */
    public static final int VERBOSE = 0;

    /**
     * use DMLogUtils.d.
     */
    public static final int DEBUG = 1;

    /**
     * use DMLogUtils.i.
     */
    public static final int INFO = 2;

    /**
     * use DMLogUtils.w.
     */
    public static final int WARN = 3;

    /**
     * use DMLogUtils.e.
     */
    public static final int ERROR = 4;

    /**
     * use DMLogUtils.a.
     */
    public static final int ASSERT = 5;

    /**
     * 是否开启输出日志模式
     */
    public static boolean isLog = true;

    /**
     * 详细
     *
     * @param clazz
     * @param msg
     */
    public static void v(Class<?> clazz, String msg) {
        if (isLog) {
            Log.v(clazz.getSimpleName(), "**********************************************");
            Log.v(TAG, "*********" + clazz.toString() + "==>" + Thread.currentThread()
                    .getName() + "");
            Log.v(clazz.getSimpleName(), "**********************************************");
            Log.v(TAG, "*********" + clazz.toString() + "==>" + msg + "");
            Log.v(clazz.getSimpleName(), "**********************************************");
        }
    }

    public static void v(Class<?> clazz, String msg, boolean isThreadName) {
        if (isLog) {
            if (isThreadName) {
                Log.v(clazz.getSimpleName(), "**********************************************");
                Log.v(TAG, "*********" + clazz.toString() + "==>" + Thread.currentThread()
                        .getName() + "");
            }
            Log.v(clazz.getSimpleName(), "**********************************************");
            Log.v(TAG, "*********" + clazz.toString() + "==>" + msg + "");
            Log.v(clazz.getSimpleName(), "**********************************************");
        }
    }

    public static void v(String tag, String msg) {
        if (isLog) {
            Log.v(tag, "**********************************************");
            Log.v(tag, "*********" + tag + "==>" + Thread.currentThread().getName() + "");
            Log.v(tag, "**********************************************");
            Log.v(tag, "*********" + tag + "==>" + msg + "");
            Log.v(tag, "**********************************************");
        }
    }

    public static void v(String tag, String msg, boolean isThreadName) {
        if (isLog) {
            if (isThreadName) {
                Log.v(tag, "**********************************************");
                Log.v(tag, "*********" + tag + "==>" + Thread.currentThread().getName() + "");
            }
            Log.v(tag, "**********************************************");
            Log.v(tag, "*********" + tag + "==>" + msg + "");
            Log.v(tag, "**********************************************");
        }
    }

    /**
     * 调试
     *
     * @param clazz
     * @param msg
     */
    public static void d(Class<?> clazz, String msg) {
        if (isLog) {
            Log.d(clazz.getSimpleName(), "**********************************************");
            Log.d(TAG, "*********" + clazz.toString() + "==>" + Thread.currentThread()
                    .getName() + "");
            Log.d(clazz.getSimpleName(), "**********************************************");
            Log.d(TAG, "*********" + clazz.toString() + "==>" + msg + "");
            Log.d(clazz.getSimpleName(), "**********************************************");
        }
    }

    public static void d(Class<?> clazz, String msg, boolean isThreadName) {
        if (isLog) {
            if (isThreadName) {
                Log.d(clazz.getSimpleName(), "**********************************************");
                Log.d(TAG, "*********" + clazz.toString() + "==>" + Thread.currentThread()
                        .getName() + "");
            }
            Log.d(clazz.getSimpleName(), "**********************************************");
            Log.d(TAG, "*********" + clazz.toString() + "==>" + msg + "");
            Log.d(clazz.getSimpleName(), "**********************************************");
        }
    }

    public static void d(String tag, String msg) {
        if (isLog) {
            Log.d(tag, "**********************************************");
            Log.d(tag, "*********" + tag + "==>" + Thread.currentThread().getName() + "");
            Log.d(TAG, "**********************************************");
            Log.d(TAG, "*********" + tag + "==>" + msg + "");
            Log.d(TAG, "**********************************************");
        }
    }

    public static void d(String tag, String msg, boolean isThreadName) {
        if (isLog) {
            if (isThreadName) {
                Log.d(tag, "**********************************************");
                Log.d(tag, "*********" + tag + "==>" + Thread.currentThread().getName() + "");
            }
            Log.d(TAG, "**********************************************");
            Log.d(TAG, "*********" + tag + "==>" + msg + "");
            Log.d(TAG, "**********************************************");
        }
    }

    /**
     * 信息
     *
     * @param clazz
     * @param msg
     */
    public static void i(Class<?> clazz, String msg) {
        if (isLog) {
            Log.i(clazz.getSimpleName(), "**********************************************");
            Log.i(TAG, "*********" + clazz.toString() + "==>" + Thread.currentThread()
                    .getName() + "");
            Log.i(clazz.getSimpleName(), "**********************************************");
            Log.i(TAG, "*********" + clazz.toString() + "==>" + msg + "");
            Log.i(clazz.getSimpleName(), "**********************************************");
        }
    }

    public static void i(Class<?> clazz, String msg, boolean isThreadName) {
        if (isLog) {
            if (isThreadName) {
                Log.i(clazz.getSimpleName(), "**********************************************");
                Log.i(TAG, "*********" + clazz.toString() + "==>" + Thread.currentThread()
                        .getName() + "");
            }
            Log.i(clazz.getSimpleName(), "**********************************************");
            Log.i(TAG, "*********" + clazz.toString() + "==>" + msg + "");
            Log.i(clazz.getSimpleName(), "**********************************************");
        }
    }

    public static void i(String tag, String msg) {
        if (isLog) {
            Log.i(tag, "**********************************************");
            Log.i(tag, "*********" + tag + "==>" + Thread.currentThread().getName() + "");
            Log.i(TAG, "**********************************************");
            Log.i(TAG, "*********" + tag + "==>" + msg + "");
            Log.i(TAG, "**********************************************");
        }
    }

    public static void i(String tag, String msg, boolean isThreadName) {
        if (isLog) {
            if (isThreadName) {
                Log.i(tag, "**********************************************");
                Log.i(tag, "*********" + tag + "==>" + Thread.currentThread().getName() + "");
            }
            Log.i(TAG, "**********************************************");
            Log.i(TAG, "*********" + tag + "==>" + msg + "");
            Log.i(TAG, "**********************************************");
        }
    }

    /**
     * 警告
     *
     * @param clazz
     * @param msg
     */
    public static void w(Class<?> clazz, String msg) {
        if (isLog) {
            Log.w(clazz.getSimpleName(), "**********************************************");
            Log.w(TAG, "*********" + clazz.toString() + "==>" + Thread.currentThread()
                    .getName() + "");
            Log.w(clazz.getSimpleName(), "**********************************************");
            Log.w(TAG, "*********" + clazz.toString() + "==>" + msg + "");
            Log.w(clazz.getSimpleName(), "**********************************************");
        }
    }

    public static void w(Class<?> clazz, String msg, boolean isThreadName) {
        if (isLog) {
            if (isThreadName) {
                Log.w(clazz.getSimpleName(), "**********************************************");
                Log.w(TAG, "*********" + clazz.toString() + "==>" + Thread.currentThread()
                        .getName() + "");
            }
            Log.w(clazz.getSimpleName(), "**********************************************");
            Log.w(TAG, "*********" + clazz.toString() + "==>" + msg + "");
            Log.w(clazz.getSimpleName(), "**********************************************");
        }
    }

    public static void w(String tag, String msg) {
        if (isLog) {
            Log.w(tag, "**********************************************");
            Log.w(tag, "*********" + tag + "==>" + Thread.currentThread().getName() + "");
            Log.w(TAG, "**********************************************");
            Log.w(TAG, "*********" + tag + "==>" + msg + "");
            Log.w(TAG, "**********************************************");
        }
    }

    public static void w(String tag, String msg, boolean isThreadName) {
        if (isLog) {
            if (isThreadName) {
                Log.w(tag, "**********************************************");
                Log.w(tag, "*********" + tag + "==>" + Thread.currentThread().getName() + "");
            }
            Log.w(TAG, "**********************************************");
            Log.w(TAG, "*********" + tag + "==>" + msg + "");
            Log.w(TAG, "**********************************************");
        }
    }

    /**
     * 错误
     *
     * @param clazz
     * @param msg
     */
    public static void e(Class<?> clazz, String msg) {
        if (isLog) {
            Log.e(clazz.getSimpleName(), "**********************************************");
            Log.e(TAG, "*********" + clazz.toString() + "==>" + Thread.currentThread()
                    .getName() + "");
            Log.e(clazz.getSimpleName(), "**********************************************");
            Log.e(TAG, "*********" + clazz.toString() + "==>" + msg + "");
            Log.e(clazz.getSimpleName(), "**********************************************");
        }
    }

    public static void e(Class<?> clazz, String msg, boolean isThreadName) {
        if (isLog) {
            if (isThreadName) {
                Log.e(clazz.getSimpleName(), "**********************************************");
                Log.e(TAG, "*********" + clazz.toString() + "==>" + Thread.currentThread()
                        .getName() + "");
            }
            Log.e(clazz.getSimpleName(), "**********************************************");
            Log.e(TAG, "*********" + clazz.toString() + "==>" + msg + "");
            Log.e(clazz.getSimpleName(), "**********************************************");
        }
    }

    public static void e(String tag, String msg) {
        if (isLog) {
            Log.e(tag, "**********************************************");
            Log.e(tag, "*********" + tag + "==>" + Thread.currentThread().getName() + "");
            Log.e(tag, "**********************************************");
            Log.e(tag, "*********" + tag + "==>" + msg + "");
            Log.e(tag, "**********************************************");
        }
    }

    public static void e(String tag, String msg, boolean isThreadName) {
        if (isLog) {
            if (isThreadName) {
                Log.e(tag, "**********************************************");
                Log.e(tag, "*********" + tag + "==>" + Thread.currentThread().getName() + "");
            }
            Log.e(tag, "**********************************************");
            Log.e(tag, "*********" + tag + "==>" + msg + "");
            Log.e(tag, "**********************************************");
        }
    }


}
