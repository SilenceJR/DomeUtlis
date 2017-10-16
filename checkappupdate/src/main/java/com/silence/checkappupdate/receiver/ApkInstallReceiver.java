package com.silence.checkappupdate.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class ApkInstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
            installApk(context, downloadId);
        }
    }

    private void installApk(Context context, long downloadId) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        long downId = sp.getLong(DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
        if (downloadId == downId) {
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadedFile = downloadManager.getUriForDownloadedFile(downloadId);
            if (downloadedFile != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(downloadedFile, "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
