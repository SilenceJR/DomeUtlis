package com.example.cbcdemo;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dmmonerylibrary.User;
import com.example.dmmonerylibrary.UserDataBase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //商户ID
    private static final String MERCHANT_ID = "105584000000072";
    //商户柜台代码
    private static final String POS_ID = "003803656";
    //分行代码
    private static final String BRANCH_ID = "442000000";
    //定单号
    private static final String ORDER_ID = "1651651651651";
    //付款金额
    private static final String PAYMENT_ID = "0.01";
    //币种
    private static final String CURCODE_ID = "01";
    //交易码
    //由建行统一分配为520100
    private static final String TXCODE_ID = "520100";
    //公钥后30位
    private static final String PUB_ID = "90e5293c878c1d0093e72ba9020111";
    //网关类型
    private static final String GATEWAY_ID = "UnionPay";

    /**
     * 0- 非钓鱼接口
     * 1- 防钓鱼接口
     * 目前该字段以银行开关为准，如果有该字段则需要传送以下字段。
     */
    private static final String TYPE = "1";
    private EditText mEditText;
    private Button mSubmit;
    private Button mGetData;
    private String mMonry;
    private TextView mTvData;
    private UserDataBase mDataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = ((EditText) findViewById(R.id.et_monry));
        mSubmit = findViewById(R.id.btn_submit);
        mGetData = findViewById(R.id.btn_getdata);
        mTvData = findViewById(R.id.tv_data);

        //        getLifecycle().addObserver(new BaseLifecycledemo(new LifecycleCallBack() {
        //            @Override
        //            public void onSuccess() {
        //                if (BuildConfig.DEBUG) Log.d("MainActivity", "lifecycle启动");
        //            }
        //
        //            @Override
        //            public void onError() {
        //                if (BuildConfig.DEBUG) Log.d("MainActivity", "lifecycle关闭");
        //            }
        //        }));


        mDataBase = Room.databaseBuilder(getApplicationContext(), UserDataBase.class, "User.db")
                .fallbackToDestructiveMigration()
                .build();





        mGetData.setOnClickListener(v -> {
            new Thread(() -> {
                for (User user : mDataBase.mUserDao().getAll()) {
                    if (BuildConfig.DEBUG) Log.d("MainActivity", "user:" + user.toString());
                }
            }).start();
        });


        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        User mUser = null;
                        for (int i = 0; i < 10; i++) {
                            mUser = new User("李四" + i, i);
                            List<User> users = mDataBase.mUserDao()
                                    .loadAllByIds(mUser.getUserName());
                            if (users == null || users.size() == 0) {
                                mDataBase.mUserDao().insertAll(mUser);
                            } else {
                                mDataBase.mUserDao().updateUser(mUser);
                            }

                        }
                    }
                }).start();

//                mMonry = mEditText.getText().toString().trim();
//
//                HttpCall.load(HttpCall.getInstance()
//                        .getAPI()
//                        .queryDmBaoBalance("100109"), new Observer<MoneryRespMsg<String>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(MoneryRespMsg<String> stringMoneryRespMsg) {
//                        if (stringMoneryRespMsg.getCode() == 100) {
//                            mTvData.setText(stringMoneryRespMsg.getData().toString());
//                        } else {
//                            mTvData.setText(stringMoneryRespMsg.getMsg());
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT)
//                                .show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
                //                if (!TextUtils.isEmpty(mMonry)) {
                //
                //                    String mac = "MERCHANTID=" + MERCHANT_ID +
                //                            "POSID=" + PUB_ID +
                //                            "BRANCHID=" + BRANCH_ID +
                //                            "ORDERID=" + ORDER_ID +
                //                            "PAYMENT=" + PAYMENT_ID +
                //                            "CURCODE=" + CURCODE_ID +
                //                            "TXCODE=" + TXCODE_ID +
                //                            "REMARK1=&REMARK2=&" +
                //                            "TYPE=1&" + TYPE +
                //                            "GATEWAY=&" + GATEWAY_ID +
                //                            "CLIENTIP=128.128.80.125&" +
                //                            "REGINFO=xiaofeixia&" +
                //                            "PROINFO=digital&REFERER="
                //                            ;
                //
                //
                ////                    String md5 = md5(mac);
                //
                ////                    String submit = "https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain?" + MERCHANTID + "=" +MERCHANT_ID +
                ////                            POSID + "=" + POS_ID + BRANCHID + "=" + BRANCH_ID + ORDERID + "=" + ORDER_ID + PAYMENT + "=" + mMonry + "&" +
                ////                            CURCODE + "=" + CURCODE_ID + TXCODE + "=" + TXCODE_ID + "TYPE=" + TYPE + PUB + "=" + PUB_ID + GATEWAY + "=" + GATEWAY_ID
                ////                            + "CLIENTIP=172.0.0.1&REGINFO=%u5C0F%u98DE%u4FA0&PROINFO=%u5145%u503C%u5361&REFERER=&"
                ////                            + "MAC=" +md5;
                ////                    String test = "MERCHANTID=105584000000072&POSID=003803656&BRANCHID=442000000&ORDERID=4977&PAYMENT=0.01&CURCODE=01&TXCODE=520100&REMARK1=&REMARK2=&TYPE=1&GATEWAY=&CLIENTIP=&REGINFO=XIAOFEIXIA&PROINFO=DIGITAL&REFERER=&ISSINSCODE=UnionPay";
                ////                    String md5 = md5(test);
                ////                    String s = "https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain?" +
                ////                                  "MERCHANTID=105584000000072&POSID=003803656&BRANCHID=442000000&ORDERID=4977&PAYMENT=0.01&CURCODE=01&TXCODE=520100&REMARK1=&REMARK2=&TYPE=1&GATEWAY=&CLIENTIP=&REGINFO=XIAOFEIXIA&PROINFO=DIGITAL&REFERER=&ISSINSCODE=UnionPay&MAC=" + md5;
                ////
                //
                //                    // 必须MAC的字段
                //
                //                    String macParams = "MERCHANTID="+MERCHANT_ID+"&POSID="+POS_ID+"&BRANCHID="+BRANCH_ID+"&ORDERID="+ORDER_ID+"&PAYMENT="+"0.01"
                //                            +"&CURCODE="+CURCODE_ID+"&TXCODE="+TXCODE_ID+"&REMARK1=&REMARK2=&TYPE="+TYPE+"&PUB="+PUB_ID
                //                            + "&GATEWAY=UnionPay&CLIENTIP=&REGINFO=duimy&PROINFO=digital&REFERER=&ISSINSCODE="+GATEWAY_ID;
                //
                //                    String MAC=md5(macParams).toLowerCase();
                //
                //                    // 拼接要发送的参数
                //                    String params = "MERCHANTID="+MERCHANT_ID+"&POSID="+POS_ID+"&BRANCHID="+BRANCH_ID+"&ORDERID="+ORDER_ID+"&PAYMENT="+"0.01"
                //                            + "&CURCODE="+CURCODE_ID+"&TXCODE="+TXCODE_ID+"&REMARK1=&REMARK2=&TYPE=1"
                //                            + "&GATEWAY=UnionPay&CLIENTIP=&REGINFO=duimy&PROINFO=digital&REFERER=&ISSINSCODE="+GATEWAY_ID
                //                            + "&MAC="+MAC;
                //
                //                    String httpCUrl = "https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain?" + params;
                //
                //
                //                    Intent intent = new Intent(MainActivity.this, WebActivity.class);
                //                    intent.putExtra("submit", httpCUrl);
                //                    startActivity(intent);
                //                }
                //            }
                //        });
            }
        });


    }


    /**
     * 字符串进行MD5加密, 用于Password
     *
     * @param password
     * @return
     */

    public static String md5(String password) {
        if (TextUtils.isEmpty(password)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(password.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain?MERCHANTID=105320148140002&POSID=100001135&BRANCHID=320000000&ORDERID=88487&
     * PAYMENT=0.01&CURCODE=01&TXCODE=520100&REMARK1=&REMARK2=&TYPE=1&GATEWAY=&CLIENTIP=128.128.80.125&REGINFO=xiaofeixia&PROINFO=digital&
     * REFERER=&INSTALLNUM=3&SMERID=111&SMERNAME=%u5DE5%u827A%u7F8E%u672F%u5546%u5E97&SMERTYPEID=112&SMERTYPE=%u5BBE%u9986%u9910%u5A31%u7C7B&
     * TRADECODE=001&TRADENAME=%u6D88%u8D39&SMEPROTYPE=1&PRONAME=%u5DE5%u827A%u54C1&TIMEOUT=20161028101226&ISSINSCODE=ICBC&NoCredit=Y&NoDebit=N&
     * USERNAME=%u5C0F%u98DE%u4FA0&IDNUMBER=110902196606183539 &MAC=b2a1adfc9f9a44b57731440e31710740
     */
}
