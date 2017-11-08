package com.silence.androidmvprxjavadome.mvp_base;

/**
 * @作者: PJ
 * @创建时间: 2017/11/7 / 11:05
 * @描述: 这是一个 IPresenter 类.
 */
public interface IPresenter<M extends IModel, V extends IView> {


    void attachModelAndView(M model,V view);

    /**
     * 防止内存的泄漏, 清除Presenter与Activity之间的绑定
     */
    void detachView();

    /**
     * @return 获取View
     */
    V getIView();

    M getIModel();

}
