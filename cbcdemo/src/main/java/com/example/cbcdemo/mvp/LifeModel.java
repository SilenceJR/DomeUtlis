package com.example.cbcdemo.mvp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * @作者: PJ
 * @创建时间: 2017/11/20 / 19:15
 * @描述: 这是一个 LifeModel 类.
 */
public class LifeModel extends ViewModel{

    private MutableLiveData<String> mLiveData = new MutableLiveData<>();

    public void setString(String s) {
        mLiveData.setValue(s);
    }


    public LiveData<String> getData() {
        return mLiveData;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
