package com.silence.duimypaydome;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.silence.duimymoneylibrary.DuimyMoney;
import com.silence.duimymoneylibrary.bean;
import com.silence.duimymoneylibrary.inter.onCallNetWorkInterface;
import com.silence.duimymoneylibrary.model.RespMsg;
import com.silence.duimypaydome.util.OrderInfoUtil2_0;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2016073000123948";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC0JsUdmzHKS+khIl/6dVFFUy2y9aLtn+rJRkkzBvt7+D/Uw6IVdnxygncpn4X+OoCWG37VO+8j70dcxT59Lp3+TKL53A3KJ0TFSCvcdFhjbcmw7X11QnNS68CpoHjDhgt1Ts4nPq72s9Cawdszrldiw/dUgwDp8XGeYaONK0DE1n0wC0N/iRKDH9SXzLrOdr0ah+fuXmJiJpnBxNHY2GEZU49zx4G1pLati/ojcZ3KfpkB08qirPr+V3ZmJ1zo1Tm+5P71x4St9A7jS2mQgOEoa0mEaUMkCvDb9pPRyRMWLSNxyOztwP8heyOIjLekry21R94o+tY0jW9eMU29QyzDAgMBAAECggEBAJdzYJObPSiM2Nj6RSYSKtjVLL4es3KB1qiPZkp3vERkV4VPUpwomEbMV9GuQr+P/zoys1YgpNLY/m2HhRlIb5qyYvEbhpxElxQjsHwNc6FjUwRjI+3Ih5tWhwoT1nLJ5iggsD9d+0l7sgvonZe9IcnuZaeu2r3SNWqpOw4bJsBTCGgNqEfHhR9uzM2r/8f5x98YQOcQESSv9NhnCExPxi1xJzdDbUvH8L/h+e6/1FbuYvErsFhaSpv/uFdJc49LvYBL6W/K2nRi3vGACZTey0znvr1GIt1TomBY5Vwzg+3EhKDxZdSTgxDsAJxWcER5GfH+qDMzROLFeWifJQ2zYcECgYEA3sM9Inh8me1NvzaBcZUPn1VSYkFmDrNYR4uOWbl+vEuQnm1CEUvx8VYMgamVeNwY2doAdTNAHwECXzabXnQzvWz4/65JXi0QjUapku6OIWgFY2hn0z/JkZ3dzxb+UEsGzDsyJlE9M9ru4kqdK1YA6eWTCZX9qx/+3eEj9Ulf9P0CgYEAzwfuR+I2c7h7wGwk+VyFyJG52kf4eYBWI3xM19xUZBnkTMNECIWzh8lg8cNgf2AOH/PBL9+PMShKsxZpHSff/Ftk3uxBB7Eczysdfl1dd7IvM+LQLyj0ijtBqvI17PorbAqDRDkmMqqBnd8nIp2D3A0l5VRzTIGucSh8BA0INL8CgYABDb9eSF+egsbZXrKiDOh0An1PRavr6eFDBV/5AK2eymS2uyGS318XmyaX/mtclP3xGdzisQlenCycsq9tdPNBtCBBZVunfGMVp/3pWgOGGO3dmILVNvoHHE4OSreNPa2cjbMhB0VY13JF08CGkfOTJ6jYZMM8afxUpFn9MQmpKQKBgGj1I1csFuD58TsvWFtXDbgOMQO5ov2uE31wAeEZoWsSOUy1XeNYLRk0/cHzhBNvVhna0OD9XLHWNHCwdt/CHGrqxqvBaIKGxw08VXp/yOfq3MVYQqT2BWui+Pq6Sp52gdcHSV8n/faHvKiVvlhtNLE1tTWbZ8ysngxeMuk21icvAoGBALWG0THCdGXBIVisCXwaMz3De9szWN6xg24HS7IF/E3oJYOJyK18lko3zfqqL8fLUNrEAyoxSHIapoFQAt40ex61Yr3Vd+zcA9Q3JC0d3sVvIW5IlUjv8YawjHehJv9AW9tTQwnVo/cE0Ouzc4UQb9EhxjmrtQpVWmm7ttmJiTyw";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "支付失败   :   " + resultInfo, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.dm_icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "返回按钮", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void payV2(View v) {
        if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE)) {
            new AlertDialog.Builder(this).setTitle("警告")
                    .setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .show();
            return;
        }

        boolean rsa2 = RSA2_PRIVATE.length() > 0;
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask payTask = new PayTask(MainActivity.this);
                Map<String, String> result = payTask.payV2(orderInfo, true);
                Log.i(TAG, result.toString());

                Message message = new Message();
                message.what = SDK_PAY_FLAG;
                message.obj = result;
                mHandler.sendMessage(message);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public void authV2(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DuimyMoney.getInstace().getUserMoney("13410280362", new onCallNetWorkInterface<RespMsg>() {
                    @Override
                    public void onSuccess(com.silence.duimymoneylibrary.model.RespMsg resultMessage) {
                        bean bean = new Gson().fromJson(new Gson().toJson(resultMessage.getData()), bean.class);
                        System.out.print("bean.toString()   :   " + bean.toString());
                        Log.e("bean.toString()", bean.toString());
                    }

                    @Override
                    public void onError(String errorMessage) {
                        System.out.print("errorMessage   :   " + errorMessage);
                        Log.e("errorMessage", errorMessage);
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onThrowableError(Throwable e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();

    }

}
