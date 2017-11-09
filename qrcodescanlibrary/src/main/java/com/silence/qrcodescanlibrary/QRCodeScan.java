package com.silence.qrcodescanlibrary;

import android.content.Context;
import android.content.Intent;

import com.journeyapps.barcodescanner.CaptureActivity;

/**
 * @作者: PJ
 * @创建时间: 2017/10/24 / 15:53
 * @描述: 这是一个 QRCodeScan 类.
 */
public class QRCodeScan {

    private static QRCodeScan mQRCodeScan;

    public static QRCodeScan getInstance() {
        if (mQRCodeScan == null) {
            synchronized (QRCodeScan.class) {
                if (mQRCodeScan == null) {
                    mQRCodeScan = new QRCodeScan();
                }
            }
        }
        return mQRCodeScan;
    }

    private void startCapture(Context context) {
        context.startActivity(new Intent(context, CaptureActivity.class));
    }

}
