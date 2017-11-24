package com.example.dmuser;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dmuser.model.LoginModel;
import com.example.dmuser.model.UserVo;
import com.example.dmuser.network.HttpCall;
import com.example.dmuser.network.RespMsg;
import com.example.dmuser.room.UserVoDataBase;
import com.example.dmuser.room.UserVodb;

import org.bouncycastle.util.encoders.Base64;

import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.dmuser.RSAUtil.bytesToHexString;
import static com.example.dmuser.RSAUtil.encrypt;
import static com.example.dmuser.RSAUtil.generateRSAPublicKey;
import static com.example.dmuser.RSAUtil.md5;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_user)
    EditText mEtUser;
    @BindView(R.id.et_psw)
    EditText mEtPsw;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    private UserVoDataBase mUserVoDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mUserVoDataBase = Room.databaseBuilder(getApplicationContext(), UserVoDataBase.class, "UserVo.db")
                .build();
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        String userName = mEtUser.getText().toString().trim();
        String passWord = mEtPsw.getText().toString().trim();

        String md5_passWord = md5(passWord);
        String loginStr = userName + "_" + md5_passWord + "_007";

        HttpCall.getInstance().getAPI().getRSAPubKey()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<UserVoRespMsg<RSAPublicKeySpec>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserVoRespMsg<RSAPublicKeySpec> rsaPublicKeySpecUserVoRespMsg) {
                        if (RespMsg.OK == rsaPublicKeySpecUserVoRespMsg.getCode()) {
                            RSAPublicKeySpec rsaPublicKeySpec = rsaPublicKeySpecUserVoRespMsg.getData();
                            RSAPublicKey key = null;
                            byte[] cipherBytes = null;
                            try {
                                key = generateRSAPublicKey(rsaPublicKeySpec);
                                cipherBytes = encrypt(key, loginStr.getBytes());
                            } catch (Exception e) {
                                cipherBytes = null;
                            }

                            HttpCall.getInstance().getAPI().login(bytesToHexString(Base64.encode(cipherBytes)))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<UserVoRespMsg<LoginModel>>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(UserVoRespMsg<LoginModel> loginModelUserVoRespMsg) {
                                            if (RespMsg.OK == loginModelUserVoRespMsg.getCode()) {
                                                LoginModel loginModel = loginModelUserVoRespMsg
                                                        .getData();
                                                UserVo userVo = loginModel.getUser();
                                                UserVodb.getInstance().saveUserVo(userVo);

                                                Intent intent = new Intent(MainActivity.this, OneActivity.class);
                                                intent.putExtra("UserVo", userVo);
                                                startActivity(intent);
                                            } else {
                                                Toasty.warning(MainActivity.this, loginModelUserVoRespMsg.getMsg(), Toast.LENGTH_SHORT)
                                                        .show();
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        } else {
                            Toasty.warning(MainActivity.this, rsaPublicKeySpecUserVoRespMsg.getMsg(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }





}
