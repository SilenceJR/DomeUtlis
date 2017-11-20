package com.example.gankiouilife;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @作者: PJ
 * @创建时间: 2017/11/16 / 15:44
 * @描述: 这是一个 HttpApiService 类.
 */
public interface GankioApi {

    @GET("data/Android/{number}/{page}")
    Observable<GankModel<AndroidDateModel>> getAndroidData(@Path("number") int number, @Path("page") int page);

}
