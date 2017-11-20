package com.example.gankiouilife;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class MainActivity extends BaseActivity {

    @BindView(R.id.recycle)
    RecyclerView mRecycle;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private List<AndroidDateModel> mAndroidDateModels = new ArrayList<>();
    private MainAdapter mAdapter;
    private int page = 0;
    private MainViewModel mViewModel;
    private boolean flag = true;
    private MyLocationListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public int getlayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mSwipeRefresh.setEnabled(true);
        mSwipeRefresh.setRefreshing(false);

        setToolabrTitle("Gankio");

        mRecycle.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MainAdapter(mAndroidDateModels);
        mAdapter.openLoadAnimation();
        mAdapter.setEnableLoadMore(true);
        mRecycle.setAdapter(mAdapter);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public void initListener() {
        SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                flag = true;
                page = 1;
                initData();
            }
        };

        mSwipeRefresh.setOnRefreshListener(onRefreshListener);

        mListener = new MyLocationListener(this, getLifecycle(), new CallBack<List<AndroidDateModel>>() {
            @Override
            public void onSuccess(String msg) {
                Toasty.success(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError() {
                Toasty.error(MainActivity.this, "发生了一个错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onGetValue(List<AndroidDateModel> androidDateModels) {
                loadData(androidDateModels);
            }
        });

        getLifecycle().addObserver(mListener);
        mListener.enable();

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mSwipeRefresh.setEnabled(false);
                initData();
            }
        }, mRecycle);
    }

    @Override
    public void initData() {
//        mViewModel.getAndroidDateModels(page).observe(this, new Observer<List<AndroidDateModel>>() {
//            @Override
//            public void onChanged(@Nullable List<AndroidDateModel> androidDateModels) {
//                loadData(androidDateModels);
//            }
//        });

        mViewModel.getAndroidDateModels(page).observe(this, androidDateModels -> loadData(androidDateModels));
//        mListener.getData(this, page);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void loadData(List<AndroidDateModel> androidDateModels) {
        if (androidDateModels != null && androidDateModels.size() > 0) {
            page++;
            if (flag) {
                mAdapter.setNewData(androidDateModels);
                flag = false;
            } else {
                mAdapter.addData(androidDateModels);
            }

            if (mSwipeRefresh.isRefreshing()) {
                mSwipeRefresh.setRefreshing(false);
            }
            if (!mSwipeRefresh.isEnabled()) {
                mSwipeRefresh.setEnabled(true);
            }

            if (mAdapter.isLoading()) {
                mAdapter.loadMoreComplete();
            }

            if (!mAdapter.isLoadMoreEnable()) {
                mAdapter.setEnableLoadMore(true);
            }

        }
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {

        //去掉虚拟按键
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏虚拟按键栏
                        | View.SYSTEM_UI_FLAG_IMMERSIVE); //防止点击屏幕时,隐藏虚拟按键栏又弹了出来


        Snackbar.make(getWindow().peekDecorView(), "这是个Gankio的测试", Snackbar.LENGTH_INDEFINITE).setAction("好的,加油哦!", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏SnackBar时记得恢复隐藏虚拟按键栏,不然屏幕底部会多出一块空白布局出来,和难看
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }).show();
    }
}
