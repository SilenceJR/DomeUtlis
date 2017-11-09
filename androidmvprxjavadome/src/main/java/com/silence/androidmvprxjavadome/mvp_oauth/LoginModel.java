package com.silence.androidmvprxjavadome.mvp_oauth;

import com.silence.androidmvprxjavadome.model.LoginVo;
import com.silence.androidmvprxjavadome.model.RespMsg;
import com.silence.androidmvprxjavadome.mvp_base.BaseModel;

import java.security.spec.RSAPublicKeySpec;

import io.reactivex.Observable;

/**
 * @作者: PJ
 * @创建时间: 2017/11/7 / 12:06
 * @描述: 这是一个 OauthModel 类.
 */
public class LoginModel extends BaseModel{

    /**
     * 直接的返回Observable
     *
     * @param
     * @return
     */
    public Observable<RespMsg<RSAPublicKeySpec>> getRSAPubKeyObservable() {

        return ApiService.getRSAPubKey();
    }

    public Observable<RespMsg<LoginVo>> getLoginObservable(String cliher) {
        return ApiService.login(cliher);
    }
}
