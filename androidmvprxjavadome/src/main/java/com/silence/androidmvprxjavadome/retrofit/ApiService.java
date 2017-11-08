package com.silence.androidmvprxjavadome.retrofit;

import com.silence.androidmvprxjavadome.constant.DmConstant;
import com.silence.androidmvprxjavadome.model.LoginModel;
import com.silence.androidmvprxjavadome.model.RespMsg;

import java.security.spec.RSAPublicKeySpec;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @作者: PJ
 * @创建时间: 2017/11/7 / 18:13
 * @描述: 这是一个 ApiServer 类.
 */
public interface ApiService {

    /**
     * 登陆
     *
     * @param cipher
     * @return
     */
    @POST(DmConstant.DUIMY_LOGIN)
    Observable<RespMsg<LoginModel>> login(@Query("cipher") String cipher);

    /**
     * 获取公钥
     *
     * @return
     */
    @POST(DmConstant.DUIMY_GETRSAPUBKEY)
    Observable<RespMsg<RSAPublicKeySpec>> getRSAPubKey();

}
