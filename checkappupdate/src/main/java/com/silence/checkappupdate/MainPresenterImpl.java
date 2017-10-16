package com.silence.checkappupdate;

import com.google.gson.Gson;
import com.silence.checkappupdate.manage.Api.DuimyCheckAppUpdateApi;
import com.silence.checkappupdate.manage.RetrofitManage;
import com.silence.checkappupdate.model.CheckAppUpdateModel;

import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainPresenterImpl implements MainPresenter {

    private final Retrofit mRetrofit;
    private final Gson mGson;
    private MainView mMainView;

    public MainPresenterImpl(MainView mainView) {
        mMainView = mainView;
        mRetrofit = RetrofitManage.getInstance().getRetrofit();
        mGson = new Gson();
    }

    @Override
    public void onCheckAppUptade() {
        mRetrofit.create(DuimyCheckAppUpdateApi.class)
                .CheckAppUpdate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RespMsg>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RespMsg respMsg) {
                        if (RespMsg.OK == respMsg.getCode()) {
//                            CheckAppUpdateModel checkAppUpdateModel = mGson.fromJson(mGson.toJson(respMsg
//                                    .getData()), CheckAppUpdateModel.class);
                            CheckAppUpdateModel checkAppUpdateModel = new CheckAppUpdateModel(
                                    "https://storage.jd.com/jdmobile/JDMALL-PC2.apk",
                                    "手机京东.apk",
                                    "12",
                                    new Date(System.currentTimeMillis()),
                                    "大量新鲜玩法哦!");
                            mMainView.onSuccess(checkAppUpdateModel);
                        } else {
                            mMainView.onError(respMsg.getMsg());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mMainView.onThrowableError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
