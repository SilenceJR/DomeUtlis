package com.silence.androidmvprxjavadome.mvp_oauth;

import com.silence.androidmvprxjavadome.model.UserVo;

/**
 * @作者: PJ
 * @创建时间: 2017/11/7 / 12:07
 * @描述: 这是一个 OauthContract 类.
 */
public class LoginContract {

    /**
     * 对UI 的操作的接口有哪些，一看就只明白了
     *
     */
    public interface OauthView {
        void loginSuccess(UserVo userVo); // 登录成功，展示数据
        void loginFail(String failMsg);
    }

    /**
     *View 层对Presenter 的请求
     *
     */
    public interface OauthPresenter {
        void login(String phone, String password);// Model层面拿回数据后通过回调通知Presenter 再通知View
    }
}
