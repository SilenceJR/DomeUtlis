package com.duimy.tokendemo.network.api;

import com.duimy.tokendemo.model.RespMsg;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @作者: PJ
 * @创建时间: 2017/12/7 / 16:59
 * @描述: 这是一个 DuimyApi 类.
 */
public interface DuimyApi {

    @POST("SpringMVC-mvn/token/login.do")
    Observable<RespMsg<String>> login(@Query("userName") String userName);

    @POST("SpringMVC-mvn/token/check.do")
    Observable<RespMsg<String>> check(@Query("token") String token);

}
