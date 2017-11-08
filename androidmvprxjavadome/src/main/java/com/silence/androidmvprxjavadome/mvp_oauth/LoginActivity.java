package com.silence.androidmvprxjavadome.mvp_oauth;

import com.silence.androidmvprxjavadome.model.UserVo;
import com.silence.androidmvprxjavadome.mvp_base.BaseMvpActivity;

/**
 * @作者: PJ
 * @创建时间: 2017/11/7 / 12:04
 * @描述: 这是一个 Oauth_MVP_Activity 类.
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter, LoginModel> implements LoginContract.OauthView {

    @Override
    public void showLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void loginSuccess(UserVo userVo) {

    }

    @Override
    public void loginFail(String failMsg) {

    }

    @Override
    protected int setLayoutId() {
        return 0;
    }

    @Override
    protected void initViews() {

    }
}
