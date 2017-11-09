package com.silence.mvpdome.inter;

/**
 * @作者: PJ
 * @创建时间: 2017/10/19 / 14:15
 * @描述: 这是一个 TestContract 类.
 */
public interface TestContract {

    interface View {
        void showData(String str);
    }

    interface Presenter {
        void getData();
        void getIntData();
    }

    interface Model {
        String doData();
    }

    interface intModel {
        int doData();
    }

}
