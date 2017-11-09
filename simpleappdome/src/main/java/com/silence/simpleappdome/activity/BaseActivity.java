package com.silence.simpleappdome.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.silence.simpleappdome.utils.ConstantUtil;

/**
 * @作者: PJ
 * @创建时间: 2017/11/3 / 14:30
 * @描述: 这是一个 BaseActivity 类.
 */
public class BaseActivity extends AppCompatActivity {

    private Context mContext;
    MyReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        registerBroadcast();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode && 0 == event.getRepeatCount()) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void registerBroadcast() {
        // 注册广播接收者
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstantUtil.EXIT_APP);
        mContext.registerReceiver(receiver,filter);
    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ConstantUtil.EXIT_APP)){
                Log.e("zs","退出登陆");
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
