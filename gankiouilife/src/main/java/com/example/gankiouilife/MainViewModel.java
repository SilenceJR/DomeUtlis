package com.example.gankiouilife;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @作者: PJ
 * @创建时间: 2017/11/16 / 16:22
 * @描述: 这是一个 MainViewModel 类.
 */
public class MainViewModel extends ViewModel {

    private MutableLiveData<List<AndroidDateModel>> mAndroidDateModels;


    public MutableLiveData<List<AndroidDateModel>> getAndroidDateModels(int page) {

        if (mAndroidDateModels == null) {
            mAndroidDateModels = new MutableLiveData<>();
        }

        HttpAPI.getInstance().getGankio().getAndroidData(10, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<AndroidDateModel>() {
                    @Override
                    protected void onSuccess(List<AndroidDateModel> results) {
                        mAndroidDateModels.setValue(results);
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        super.onFailure(errorMsg);

                    }
                });


        return mAndroidDateModels;
    }

}
