package com.silence.androidmvprxjavadome.mvp_base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.silence.androidmvprxjavadome.BaseActivity;

/**
 * @作者: PJ
 * @创建时间: 2017/11/7 / 11:02
 * @描述: 这是一个 BaseMvpActivity 类.
 */
public abstract class BaseMvpActivity<P extends BasePresenter, M extends BaseModel> extends BaseActivity implements IView{

    protected P mPresenter;
    protected M mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = CreateObjUtil.getT(this, 0);
        mModel = CreateObjUtil.getT(this, 1);

        initCommonData();
    }

    /**
     * V 和 M 在P 中关联起来了，V 要获取M 的数据什么的都是通过P 来获取的
     */
    private void initCommonData() {
        if (mPresenter != null) {
            mPresenter.attachModelAndView(mModel, this);
        }
    }


    /**
     * 取消Presenter 和 View 的关联
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
