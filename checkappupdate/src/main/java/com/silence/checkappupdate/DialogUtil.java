package com.silence.checkappupdate;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class DialogUtil {

    private static DialogUtil mInstance;
    private AlertDialog.Builder mAlertDialog;

    public static DialogUtil getInstance() {
        if (mInstance == null) {
            synchronized (DialogUtil.class) {
                mInstance = new DialogUtil();
            }
        }
        return mInstance;
    }

    public AlertDialog.Builder initDialog(Context context) {
        if (mAlertDialog == null) {
            synchronized (DialogUtil.class) {
                mAlertDialog = new AlertDialog.Builder(context);
            }
        }
        return mAlertDialog;
    }

    public void showDialog(String message, DialogInterface.OnClickListener onClickListener) {
        mAlertDialog.setTitle("有重大更新啦")
                 .setMessage(message)
                 .setPositiveButton("立即更新", onClickListener)
                .setCancelable(false).create().show();
    }


}
