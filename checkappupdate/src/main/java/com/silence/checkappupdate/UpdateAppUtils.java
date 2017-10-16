package com.silence.checkappupdate;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import com.silence.checkappupdate.model.CheckAppUpdateModel;

public class UpdateAppUtils {

    private static UpdateAppUtils mInstance;
    private Context mContext;
    private final DownloadManager downloadManager;

    public UpdateAppUtils(Context context) {
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        mContext = context.getApplicationContext();
    }

    public static UpdateAppUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (UpdateAppUtils.class) {
                if (mInstance == null) {
                    mInstance = new UpdateAppUtils(context);
                }
            }
        }
        return mInstance;
    }

    public long startDownLoad(CheckAppUpdateModel checkAppUpdateModel) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(checkAppUpdateModel.getUpdateUrl()));
        //设置在什么网络情况下进行下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
                //设置通知栏标题
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setTitle(checkAppUpdateModel
                        .getName())
                .setDescription("apk正在下载...")
                .setAllowedOverRoaming(false)
                //设置文件存放目录
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, checkAppUpdateModel
                        .getName());
        return downloadManager.enqueue(request);
    }

    public String getDownloadPath(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    return cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI));
                }
            } finally {
                cursor.close();
            }
        }
        return null;
    }

    public Uri getDownloadUri(long downloadId) {
        return downloadManager.getUriForDownloadedFile(downloadId);
    }

    public DownloadManager getDownloadManager() {
        return downloadManager;
    }

    /**
     * 获取下载状态
     *
     * @param downloadId an ID for the download, unique across the system.
     *                   This ID is used to make future calls related to this download.
     * @return int
     * @see DownloadManager#STATUS_PENDING
     * @see DownloadManager#STATUS_PAUSED
     * @see DownloadManager#STATUS_RUNNING
     * @see DownloadManager#STATUS_SUCCESSFUL
     * @see DownloadManager#STATUS_FAILED
     */
    public int getDownloadStatus(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    return cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                }
            } finally {
                cursor.close();
            }
        }
        return -1;
    }

}
