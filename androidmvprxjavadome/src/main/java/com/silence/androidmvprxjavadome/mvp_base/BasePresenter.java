package com.silence.androidmvprxjavadome.mvp_base;

import java.lang.ref.WeakReference;

import io.reactivex.annotations.NonNull;

/**
 * @作者: PJ
 * @创建时间: 2017/11/7 / 11:07
 * @描述: 这是一个 BasePresenter 类.
 */
public abstract class BasePresenter<M extends IModel, V extends IView> implements IPresenter {

    /**
     * 使用弱引用是为了mViewRef 在需要被回收的时候被回收，不要Activity被关闭了他被强引用了得不到回收内存泄漏
     *
     */
    private WeakReference<V> mViewRef;            // View接口类型的弱引用
    private WeakReference<M> mModelRef;           // 其实根本没有必要


    @Override
    public void attachModelAndView(IModel model, IView view) {
        mViewRef = new WeakReference(view);
        mModelRef = new WeakReference(model);
    }

    /**
     * 解除关联
     */
    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    /**
     * 每次都要这样子判断是不是会很累啊
     *
     *
     * <p>
     * if（isAttachView）{
     * getIView.doSomething;
     * }
     * <p>
     * 能否在getIView 中mViewRef.get() 判断mViewRef = null && mViewRef.get() = null 后
     * 自动的阻止doSomething  的执行，不要每次重复的去判断 ！
     * <p>
     * <p>
     * 问题总是会存在的！试试  Android Architecture Components 吧
     * <p>
     * https://developer.android.com/topic/libraries/architecture/index.html
     *
     * @return
     */
    public boolean isAttachView() {
        return mViewRef != null && mViewRef.get() != null;
    }


    /**
     * 把结果返回到页面等等
     */
    public void invokeMethod(){

    }

    @NonNull
    @Override
    public V getIView() {
        return mViewRef.get();
    }

    @Override
    public M getIModel() {
        return mModelRef.get();
    }
}
