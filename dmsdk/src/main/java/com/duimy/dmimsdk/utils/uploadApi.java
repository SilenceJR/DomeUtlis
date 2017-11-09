package com.duimy.dmimsdk.utils;

import com.duimy.dmimsdk.constant.DMConstant;
import com.duimy.dmimsdk.model.RespMsg;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface uploadApi {

    /**
     * 上传用户头像
     *
     * 文件类型(0:其他;1:图片;2:音频;3:视频)
     * @param imageFile
     * @return
     */
    @Multipart
    @POST(DMConstant.DUIMY_FILE_UPLOAD_CHAT)
    Observable<RespMsg> fileUploadChat(@Part("file\"; filename=\"avatar.png") RequestBody imageFile, @Query("fileType") String fileType, @Query("loginName") String loginName, @Query("token") String token);
}
