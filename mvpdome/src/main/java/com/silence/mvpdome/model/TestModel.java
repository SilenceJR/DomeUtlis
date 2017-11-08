package com.silence.mvpdome.model;

import com.silence.mvpdome.inter.TestContract;

/**
 * @作者: PJ
 * @创建时间: 2017/10/19 / 14:19
 * @描述: 这是一个 TestModel 类.
 */
public class TestModel implements TestContract.Model{

    private static TestModel mInstance;

    public static TestModel getInstance() {
        if (mInstance == null) {
            synchronized (TestModel.class) {
                if (mInstance == null) {
                    mInstance = new TestModel();
                }
            }
        }
        return mInstance;
    }



    @Override
    public String doData() {
        return "MVP架构";
    }

}
