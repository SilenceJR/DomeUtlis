package com.example.cbcdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    //商户ID
    private static final String MERCHANTID = "MERCHANTID";
    private static final String MERCHANT_ID = "105320148140002&";
    //商户柜台代码
    private static final String POSID = "POSID";
    private static final String POS_ID = "100001135&";
    //分行代码
    private static final String BRANCHID = "BRANCHID";
    private static final String BRANCH_ID = "320000000&";
    //定单号
    private static final String ORDERID = "ORDERID";
    private static final String ORDER_ID = "320000000&";
    //付款金额
    private static final String PAYMENT = "PAYMENT";
    private static final String PAYMENT_ID = "0.01&";
    //币种
    private static final String CURCODE = "CURCODE";
    private static final String CURCODE_ID = "01&";
    //交易码
    //由建行统一分配为520100
    private static final String TXCODE = "TXCODE";
    private static final String TXCODE_ID = "520100&";
    //公钥后30位
    private static final String PUB = "PUB";
    private static final String PUB_ID = "520100&";
    //网关类型
    private static final String GATEWAY = "GATEWAY";
    private static final String GATEWAY_ID = "UnionPay&";

    /**
     * 0- 非钓鱼接口
     * 1- 防钓鱼接口
     * 目前该字段以银行开关为准，如果有该字段则需要传送以下字段。
     */
    private static final String TYPE = "1";
    private EditText mEditText;
    private Button mSubmit;
    private String mMonry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = ((EditText) findViewById(R.id.et_monry));
        mSubmit = findViewById(R.id.btn_submit);


        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMonry = mEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(mMonry)) {

                    String mac = MERCHANTID + "=" +MERCHANT_ID +
                            POSID + "=" + POS_ID + BRANCHID + "=" + BRANCH_ID + PAYMENT + "=" + mMonry + "&" + ORDERID + "=" + ORDER_ID +
                            CURCODE + "=" + CURCODE_ID + TXCODE + "=" + TXCODE_ID + "TYPE=" + TYPE + "PUB=" + PUB_ID + GATEWAY + "=" + GATEWAY_ID;


                    String md5 = md5(mac);

                    String submit = "https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain?" + MERCHANTID + "=" +MERCHANT_ID +
                            POSID + "=" + POS_ID + BRANCHID + "=" + BRANCH_ID + PAYMENT + "=" + mMonry + "&" + ORDERID + "=" + ORDER_ID +
                            CURCODE + "=" + CURCODE_ID + TXCODE + "=" + TXCODE_ID + "TYPE=" + TYPE + "PUB=" + PUB_ID + GATEWAY + "=" + GATEWAY_ID + "MAC=" +md5;
                    Intent intent = new Intent(MainActivity.this, WebActivity.class);
                    intent.putExtra("submit", submit);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 字符串进行MD5加密, 用于Password
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
