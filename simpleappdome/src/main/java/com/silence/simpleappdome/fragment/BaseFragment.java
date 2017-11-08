package com.silence.simpleappdome.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silence.simpleappdome.widgets.ContentPage;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者: PJ
 * @创建时间: 2017/11/3 / 15:18
 * @描述: 这是一个 BaseFragment 类.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private AppCompatDialog mCompatDialog;
    private List<Subscriber> mSubscriber;
    private ContentPage mContentPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /**
         * 初始化AppCompatDialog
         */
        mCompatDialog = new AppCompatDialog(getActivity());
        Message msg = new Message();
        msg.obj = "请骚等...";
        mCompatDialog.setCancelMessage(msg);
        mCompatDialog.setCanceledOnTouchOutside(false);
        mCompatDialog.setCancelable(true);

        /**
         * 创建Subscriber容器
         */
        mSubscriber = new ArrayList<>();
        if (mContentPage == null) {
            mContentPage = new ContentPage(getActivity()) {

                @Override
                protected Object loadData() {
                    return requestData();
                }

                @Override
                protected View createSuccessView() {
                    return getSuccessView();
                }
            };
        } else {
//            Comm
        }

        return mContentPage;
    }

    /**
     * 返回据的fragment填充的具体View
     */
    protected abstract View getSuccessView();

    /**
     * 返回请求服务器的数据
     */
    protected abstract Object requestData();

    public void refreshPage(Object o) {
        mContentPage.refreshPage(o);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public <T> Subscriber<T> addSubscriber(Subscriber<T> subscriber) {
        mSubscriber.add(subscriber);
        return subscriber;
    }
}
