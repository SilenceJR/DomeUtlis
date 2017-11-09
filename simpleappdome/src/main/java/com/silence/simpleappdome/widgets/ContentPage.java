package com.silence.simpleappdome.widgets;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.silence.simpleappdome.R;
import com.silence.simpleappdome.utils.ConstantUtil;

/**
 * @作者: PJ
 * @创建时间: 2017/11/3 / 16:00
 * @描述: 这是一个 ContentPage 类.
 */
public abstract class ContentPage extends FrameLayout{
    public ContentPage(@NonNull Context context) {
        super(context);
        initContentPage();
    }

    public ContentPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initContentPage();
    }

    public ContentPage(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initContentPage();
    }

    // 定义加载状态常量
    enum PageState {
        // 给枚举传入自定义的int值
        STATE_LOADING(0), //加载中的状态
        STATE_SUCCESS(1), //加载成功的状态
        STATE_ERROR(2);    //加载错误的状态

        private int vlaue;

        PageState(int vlaue) {
            this.vlaue = vlaue;
        }

        public int getVlaue() {
            return vlaue;
        }
    }

    // mState表示当前的状态
    private PageState mState = PageState.STATE_LOADING;// 每个界面默认是加载中的状态
    private View loadingView;// 加载中对应的View
    private View errorView;// 加载数据失败的View
    private View successView;// 加载数据成功的View

    public void initContentPage() {
        //2.显示一下默认的VIew
        showPage();
    }

    private void showPage() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        switch (mState) {
            case STATE_LOADING://加载中的状态
                if (loadingView == null) {
                    loadingView = View.inflate(getContext(), R.layout.content_page_loading, null);
                }
                removeAllViews();
                addView(loadingView, params);
                break;
            case STATE_SUCCESS://加载成功的状态
                removeAllViews();
                successView = createSuccessView();
                addView(successView, params);
                break;
            case STATE_ERROR://加载错误的状态
                if (errorView == null) {
                    errorView = View.inflate(getContext(), R.layout.content_page_error, null);
                    Button BtnReload = errorView.findViewById(R.id.btn_reload);
                    BtnReload.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //1.先显示loadingView
                            mState = PageState.STATE_LOADING;
                            showPage();
                            //2.重新请求数据，然后刷新page
                            loadDataAndRefreshPage();
                        }
                    });
                }
                removeAllViews();
                addView(errorView, params);
                break;
        }
    }

    /**
     * 向服务器请求数据，然后根据请求回来的数据刷新page
     */
    public void loadDataAndRefreshPage() {

        //1.获取从服务器请求回来的数据对象
        Object data = loadData();

        //2.根据请求回来的数据对象，判断它对应的state,并赋值给当前的state
        mState = checkData(data);

        //3.根据最新的state，刷新page
        showPage();
    }

    public void refreshPage(Object o) {
        if (o == null) {
            //说明木有数据，那么对应的state应该是error
            mState = PageState.STATE_ERROR;
        } else {
            //说明请求回来的有数据，那么对应的state应该是success
            mState = PageState.STATE_SUCCESS;
        }
        showPage();
    }

    private PageState checkData(Object data) {
        if (data == null) {
            //说明木有数据，那么对应的state应该是error
            return PageState.STATE_ERROR;

        } else if (data == ConstantUtil.STATE_LOADING) {
            return PageState.STATE_LOADING;
        } else {
            //说明请求回来的有数据，那么对应的state应该是success
            return PageState.STATE_SUCCESS;
        }
    }

    /**
     * 每个子界面请求数据和解析数据的过程都不一样，而我只关心每个界面请求回来的数据的对象，那么
     * 每个子界面请求数据和解析的过程应该由每个子界面自己实现
     *
     * @return
     */
    protected abstract Object loadData();

    /**
     * 每个子界面的成功的View不一样，所以应由每个子界面自己去实现，我不用关心你如何实现
     *
     * @return
     */
    protected abstract View createSuccessView();


}
