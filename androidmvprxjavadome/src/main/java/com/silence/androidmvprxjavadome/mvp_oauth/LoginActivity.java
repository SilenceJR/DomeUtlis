package com.silence.androidmvprxjavadome.mvp_oauth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.silence.androidmvprxjavadome.R;
import com.silence.androidmvprxjavadome.model.UserVo;
import com.silence.androidmvprxjavadome.mvp_base.BaseMvpActivity;

import butterknife.InjectView;
import es.dmoral.toasty.Toasty;

/**
 * @作者: PJ
 * @创建时间: 2017/11/7 / 12:04
 * @描述: 这是一个 Oauth_MVP_Activity 类.
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter, LoginModel> implements LoginContract.OauthView {

    @InjectView(R.id.et_user)
    EditText mEtUser;
    @InjectView(R.id.et_password)
    EditText mEtPassword;
    @InjectView(R.id.btn_login)
    Button mBtnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void loginSuccess(UserVo userVo) {
        Toasty.success(mContext, userVo.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFail(String failMsg) {
        Toasty.warning(mContext, failMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {


        mBtnLogin.setOnClickListener(listener);
    }

    @Override
    public void showLoading() {

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String user = mEtUser.getText().toString().trim();
            String psw = mEtPassword.getText().toString().trim();
            if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(psw)) {
                if (mPresenter == null) {
                    Toasty.error(mContext, "mPresenter空了啊!,快检查代码!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mPresenter.login(user, psw);
            } else {
                Toasty.warning(mContext, "账号或密码不能为空!!!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void showError() {

    }
}
