package com.silence.duimymoneylibrary.network.api;

import com.silence.duimymoneylibrary.model.RespMsg;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @作者: PJ
 * @创建时间: 2017/11/1 / 17:22
 * @描述: 这是一个 DuimyMoneyApi 类.
 */
public interface DuimyMoneyApi {

    @POST("queryDmBaoBalance")
    Observable<RespMsg> getMoney(@NonNull @Query("loginName") String loginName);

}
