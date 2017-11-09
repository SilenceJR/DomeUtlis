package com.duimy.dmimsdk.utils;

import android.os.Environment;

import com.duimy.dmimsdk.DMValueCallBack;
import com.duimy.dmimsdk.core.DMCore;
import com.duimy.dmimsdk.listener.onNetCallBack;
import com.duimy.dmimsdk.model.uploadData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by haley on 2017/7/21.
 */

public class FileManager {


    public static void uploadFileChat (String localPath, String fileType, String loginName, String token, final onNetCallBack<uploadData> onNetCallBack) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), new File(localPath));
        RetrofitManage.load(RetrofitManage.getRetrofit()
                .fileUploadChat(requestBody, fileType, loginName, token), new onNetCallBack<uploadData>() {
            @Override
            public void onSuccess(uploadData uploadData) {
                onNetCallBack.onSuccess(uploadData);
            }

            @Override
            public void onError(int code, String errorMsg) {
                onNetCallBack.onError(code, errorMsg);
            }

            @Override
            public void onThrowableError() {
                onNetCallBack.onThrowableError();
            }
        });

    }



    /**
     * 异步上传文件
     * @param uploadUrl
     * @param localPath
     * @param fileType  文件类型(0:其他;1:图片;2:音频;3:视频)
     * @param callBack
     */
    public static void uploadFile(String uploadUrl, String localPath, String fileType, final DMValueCallBack<String> callBack) {
        File file = new File(localPath);
        if (!file.exists()) {
            callBack.onError(-1, "文件不存在");
            return;
        }

        RequestParams params = new RequestParams(uploadUrl);
        params.setMultipart(true);
        params.addBodyParameter("file", file);
        params.addBodyParameter("fileType", fileType);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                callBack.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError(-1, "上传失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 同步上传文件
     * @param uploadUrl
     * @param localPath
     * @param fileType 文件类型(0:其他;1:图片;2:音频;3:视频)
     * @return
     */
    public static String sysnUploadFile(String uploadUrl, String localPath, String fileType) {
        File file = new File(localPath);

        RequestParams params = new RequestParams(uploadUrl);
        params.setMultipart(true);
        params.addBodyParameter("file", file);
        params.addBodyParameter("fileType", fileType);

        try {
            String fileUrl = x.http().postSync(params, String.class);
            return fileUrl;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    /**
     * 异步下载文件
     * @param fileUrl
     * @param callBack
     */
    public static void downloadFile(String fileUrl, final DMValueCallBack<String> callBack) {
        if (fileUrl == null) {
            callBack.onError(-1, "文件地址不正确");
            return;
        }

        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        final String localPath = getFileDirectoryPath() + fileName;

        RequestParams params = new RequestParams(fileUrl);
        params.setSaveFilePath(localPath);
        x.http().get(params, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                callBack.onSuccess(localPath);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError(-1, "下载失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 同步下载文件
     * @param fileUrl
     * @return
     */
    public static String sysnDownloadFile(String fileUrl) {
        if (fileUrl == null) {
            return null;
        }
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        final String localPath = getFileDirectoryPath() + fileName;

        RequestParams params = new RequestParams(fileUrl);
        params.setSaveFilePath(localPath);

        try {
            File file = x.http().postSync(params, File.class);
            if (!file.exists()) {
                return null;
            }
            return localPath;

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    public static String getFileDirectoryPath() {
        String currentUser = DMCore.getInstance().getCurrentUser();
        String dirPath = Environment.getExternalStorageDirectory() + "/dmsdk/";
        if (currentUser != null) {
            dirPath += currentUser + "/";
        }
        return dirPath;
    }
}
