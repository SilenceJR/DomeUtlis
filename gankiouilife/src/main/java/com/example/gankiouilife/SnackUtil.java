package com.example.gankiouilife;

/**
 * @作者: PJ
 * @创建时间: 2017/11/17 / 11:05
 * @描述: 这是一个 SnackUtil 类.
 */
public class SnackUtil {

    private volatile static SnackUtil mInstance;

    public static SnackUtil getInstance() {
        if (mInstance == null) {
            synchronized (SnackUtil.class) {
                if (mInstance == null) {
                    mInstance = new SnackUtil();
                }
            }
        }
        return mInstance;
    }

    public SnackUtil() {
    }
}
