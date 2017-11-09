package com.silence.mvpdome.presneter;

import com.silence.mvpdome.inter.TestContract;

/**
 * @作者: PJ
 * @创建时间: 2017/10/19 / 14:18
 * @描述: 这是一个 TestPresenter 类.
 */
public class TestPresenter implements TestContract.Presenter {

    private TestContract.Model mModel;
    private TestContract.intModel mIntModel;
    private TestContract.View mView;

    public TestPresenter(TestContract.Model model, TestContract.View view) {
        mModel = model;
        mView = view;
    }

    public TestPresenter(TestContract.Model model, TestContract.intModel intModel, TestContract.View view) {
        mModel = model;
        mIntModel = intModel;
        mView = view;
    }

    @Override
    public void getData() {
        mView.showData(mModel.doData());
    }

    @Override
    public void getIntData() {
        mView.showData(mIntModel.doData() +"");
    }


}
