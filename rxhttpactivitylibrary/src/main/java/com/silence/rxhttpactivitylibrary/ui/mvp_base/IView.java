package com.silence.rxhttpactivitylibrary.ui.mvp_base;

// 想起一个笑话，大学2个室友分别去买了2只乌龟，其中一个室友怕混淆，把名字写在乌龟壳上
public interface IView {

    //显示等待框
    void showLoading();
    //显示错误提示
    void showError();

}
