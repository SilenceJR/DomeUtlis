package com.silence.checkappupdate;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.silence.checkappupdate.model.CheckAppUpdateModel;

import java.io.File;

public class DownloadApk {

    public static final String TAG = DownloadApk.class.getSimpleName();

    public static void download(Context context, CheckAppUpdateModel checkAppUpdateModel) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        long downloadId = sp.getLong(DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
        if (downloadId != -1L) {
            UpdateAppUtils updateAppUtils = UpdateAppUtils.getInstance(context);
            int downloadStatus = updateAppUtils.getDownloadStatus(downloadId);
            if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL) {
                Uri downloadUri = updateAppUtils.getDownloadUri(downloadId);
                if (downloadUri != null) {
                    if (compare(getApkInfo(context, downloadUri.getPath()), context)) {
                        startInstall(context, downloadUri, downloadId);
                        return;
                    } else {
                        updateAppUtils.getDownloadManager().remove(downloadId);
                    }
                }
                start(context, checkAppUpdateModel);
            } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                start(context, checkAppUpdateModel);
            } else {
                Toast.makeText(context, "已经在下载", Toast.LENGTH_SHORT).show();
            }
        } else {
            start(context, checkAppUpdateModel);
        }
    }


    /**
     * 下载的apk和当前程序版本比较
     *
     * @param apkInfo apk file's packageInfo
     * @param context Context
     * @return 如果当前应用版本小于apk的版本则返回true
     */
    private static boolean compare(PackageInfo apkInfo, Context context) {
        if (apkInfo == null) {
            return false;
        }
        String localPackage = context.getPackageName();
        if (apkInfo.packageName.equals(localPackage)) {
            try {
                PackageInfo packageInfo = context.getPackageManager()
                        .getPackageInfo(localPackage, 0);
                if (apkInfo.versionCode > packageInfo.versionCode) {
                    return true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static PackageInfo getApkInfo(Context context, String path) {
        PackageManager pm = context.getPackageManager();
        PackageInfo packageArchiveInfo = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (packageArchiveInfo != null) {
            return packageArchiveInfo;
        }
        return null;
    }


    private static void startInstall(Context context, Uri downloadUri, long downloadId) {

        String downloadPath = UpdateAppUtils.getInstance(context).getDownloadPath(downloadId);
        File apkFile = new File(downloadPath);
        if (!apkFile.exists()) {
            Toast.makeText(context, "APP安装文件不存在或已损坏", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, context.getResources().getString(R.string.app_pack_name), apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(downloadUri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    private static void start(Context context, CheckAppUpdateModel checkAppUpdateModel) {
        long downloadId = UpdateAppUtils.getInstance(context).startDownLoad(checkAppUpdateModel);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putLong(DownloadManager.EXTRA_DOWNLOAD_ID, downloadId).commit();

    }
}
