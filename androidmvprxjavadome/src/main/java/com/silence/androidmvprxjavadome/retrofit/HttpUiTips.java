package com.silence.androidmvprxjavadome.retrofit;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.silence.androidmvprxjavadome.R;

/**
 * @作者: PJ
 * @创建时间: 2017/11/8 / 10:27
 * @描述: 这是一个 HttpAlterTips 类.
 */
public class HttpUiTips {

    public static void alertTip(Context context, String errorMessage) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("获取数据失败")
                .setMessage(errorMessage)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

    }



}
