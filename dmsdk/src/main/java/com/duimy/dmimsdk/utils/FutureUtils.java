package com.duimy.dmimsdk.utils;

import com.duimy.dmimsdk.DMValueCallBack;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class FutureUtils {

    private final static ExecutorService excutor = Executors.newCachedThreadPool();

    private FutureUtils(){}

    public static <T> void callRun (Callable<T> call, DMValueCallBack<T> valueCallBack) {
        Future<T> submit = excutor.submit(call);
        T data = null;
        try {
            data = submit.get();
            valueCallBack.onSuccess(data);
        } catch (Exception e) {
            e.printStackTrace();
            valueCallBack.onError(-1, e.getMessage());
        }
    }

    public static void run (Runnable r) {
        excutor.execute(r);
    }


}
