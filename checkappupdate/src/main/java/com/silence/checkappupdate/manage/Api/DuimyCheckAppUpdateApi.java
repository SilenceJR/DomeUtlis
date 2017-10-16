package com.silence.checkappupdate.manage.Api;

import com.silence.checkappupdate.RespMsg;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface DuimyCheckAppUpdateApi {

    @POST("getRSAPubKey")
    Observable<RespMsg> CheckAppUpdate();

}
