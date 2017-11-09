package com.silence.recyclerbasedemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    @InjectView(R.id.recycler)
    RecyclerView mRecycler;
    @InjectView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @InjectView(R.id.fab)
    FloatingActionButton mFab;

    private boolean flag = true;

    private List<Student> mStudents = new ArrayList<>();
    private MyAdatper mAdapter;
    private View mNoDataView;

    private int age = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initView();
        setStudentData(mStudents);
    }

    private void initData() {
        for (int i = 0; i < 30; i++) {
            Student student = new Student("张三" + age, age);
            mStudents.add(student);
            age++;
        }
        setStudentData(mStudents);
    }



    private void initView() {
        initRecycler();
        initSwipe();
        initEmptyView();
    }

    private void initEmptyView() {
        mNoDataView = LayoutInflater.from(this)
                .inflate(R.layout.empty_view, (ViewGroup) mRecycler.getParent(), false);
        mNoDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });
    }

    private void initSwipe() {
        mSwipeRefresh.setRefreshing(false);
        mSwipeRefresh.setEnabled(true);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefresh.setRefreshing(false);
                flag = true;
                initData();
            }
        });
    }

    private void initRecycler() {
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdatper(R.layout.item_main, mStudents);
        mAdapter.setHeaderFooterEmpty(true ,true);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        View view = LayoutInflater.from(this)
                .inflate(R.layout.header_item_main, (ViewGroup) mRecycler.getParent(), false);
        mAdapter.setHeaderView(view);
        mRecycler.setAdapter(mAdapter);

    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        initData();
    }

    private void setStudentData(List<Student> students) {
        if (students != null && students.size() != 0) {
            if (flag) {
                mAdapter.setNewData(students);
                flag = false;
            } else {
                mAdapter.addData(students);
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
            if (mAdapter.isLoadMoreEnable()) {
                mAdapter.setEnableLoadMore(true);
            }
        } else {
            if (mAdapter.isLoading()) {
                mAdapter.loadMoreFail();
            } else {
                mAdapter.setEmptyView(mNoDataView);

                if (mSwipeRefresh.isRefreshing()) {
                    mSwipeRefresh.setRefreshing(false);
                }
                if (mSwipeRefresh.isEnabled()) {
                    mSwipeRefresh.setEnabled(false);
                }
            }
        }

    }

    @Override
    public void onLoadMoreRequested() {
        initData2();
    }

    private void initData2() {
        mSwipeRefresh.setEnabled(false);
        for (int i = 0; i < 10; i++) {
            Student student = new Student("李四" + age, age);
            mStudents.add(student);
            age++;
        }
        setStudentData(mStudents);
    }
}
