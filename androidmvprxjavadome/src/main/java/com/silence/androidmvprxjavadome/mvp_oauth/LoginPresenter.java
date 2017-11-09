package com.silence.androidmvprxjavadome.mvp_oauth;

import com.silence.androidmvprxjavadome.BaseObserver;
import com.silence.androidmvprxjavadome.model.LoginVo;
import com.silence.androidmvprxjavadome.model.RespMsg;
import com.silence.androidmvprxjavadome.mvp_base.BasePresenter;

import org.bouncycastle.util.encoders.Base64;

import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.silence.androidmvprxjavadome.utils.RSAUtil.encrypt;
import static com.silence.androidmvprxjavadome.utils.RSAUtil.generateRSAPublicKey;
import static com.silence.androidmvprxjavadome.utils.StringUtil.bytesToHexString;
import static com.silence.androidmvprxjavadome.utils.StringUtil.md5;

/**
 * @作者: PJ
 * @创建时间: 2017/11/7 / 12:05
 * @描述: 这是一个 OauthPresenter 类.
 */
public class LoginPresenter extends BasePresenter<LoginModel, LoginActivity> implements LoginContract.OauthPresenter{

    @Override
    public void login(String phone, String password) {

        password = md5(password);

        final String srcCipher = phone + "_" + password + "_" + 007;

        getIModel().getRSAPubKeyObservable()
                .flatMap(new Function<RespMsg<RSAPublicKeySpec>, ObservableSource<RespMsg<LoginVo>>>() {
                    @Override
                    public ObservableSource<RespMsg<LoginVo>> apply(@NonNull RespMsg<RSAPublicKeySpec> rsaPublicKeySpecRespMsg) throws Exception {
                        if (RespMsg.OK == rsaPublicKeySpecRespMsg.getCode()) {
                            RSAPublicKey key = null;
                            byte[] cipherBytes = null;
                            try {
                                key = generateRSAPublicKey(rsaPublicKeySpecRespMsg.getData());
                                cipherBytes = encrypt(key, srcCipher.getBytes());
                            } catch (Exception e) {
                                cipherBytes = null;
                            }
                            return getIModel().getLoginObservable(bytesToHexString(Base64.encode(cipherBytes)));
                        } else {
                            return null;
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<LoginVo>(getIView().mContext) {
                    @Override
                    protected void onSuccess(LoginVo data) {
                        if (isAttachView()) {
                            getIView().loginSuccess(data.getUser());
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        getIView().loginFail(msg);
                    }
                });

//        HttpCall.getApiService().getRSAPubKey()
//                .flatMap(new Function<RespMsg<RSAPublicKeySpec>, ObservableSource<RespMsg<LoginVo>>>() {
//                    @Override
//                    public ObservableSource<RespMsg<LoginVo>> apply(@NonNull RespMsg<RSAPublicKeySpec> rsaPublicKeySpecRespMsg) throws Exception {
//                        if (RespMsg.OK == rsaPublicKeySpecRespMsg.getCode()) {
//                            RSAPublicKey key = null;
//                            byte[] cipherBytes = null;
//                            try {
//                                key = generateRSAPublicKey(rsaPublicKeySpecRespMsg.getData());
//                                cipherBytes = encrypt(key, srcCipher.getBytes());
//                            } catch (Exception e) {
//                                cipherBytes = null;
//                            }
//                            return HttpCall.getApiService().login(bytesToHexString(Base64.encode(cipherBytes)));
//                        } else {
//                            return null;
//                        }
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<LoginVo>(getIView().mContext) {
//                    @Override
//                    protected void onSuccess(LoginVo data) {
//                        UserVo userVo = data.getUser();
//                        getIView().loginSuccess(userVo);
//                    }
//
//                    @Override
//                    public void onFailure(int code, String msg) {
//                        super.onFailure(code, msg);
//                        getIView().loginFail(msg);
//                    }
//                });
    }

}
