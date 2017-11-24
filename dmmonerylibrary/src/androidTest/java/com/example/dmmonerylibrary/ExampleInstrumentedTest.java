package com.example.dmmonerylibrary;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.dmmonerylibrary.network.HttpCall;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.dmmonerylibrary.test", appContext.getPackageName());
    }

    @Test
    public void onHttp() {
        HttpCall.load(HttpCall.getInstance()
                .getAPI()
                .queryDmBaoBalance("100109"), new Observer<MoneryRespMsg<String>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(MoneryRespMsg<String> stringMoneryRespMsg) {
                System.out.println(stringMoneryRespMsg);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
