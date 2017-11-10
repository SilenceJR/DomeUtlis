package com.silence.recyclerbasedemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.silence.recyclerbasedemo.kotlin.Student_Kotlin;
import com.silence.recyclerbasedemo.kotlin.Student_KotlinKt;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.fab)
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
        ButterKnife.bind(this);

        initView();
        setStudentData(mStudents);
    }

    private void initData() {

        setHeaderView();

        for (int i = 0; i < 30; i++) {
            Student student = new Student("张三" + age, null, age, Student.TEXT);
            mStudents.add(student);
            age++;
        }
        setStudentData(mStudents);

        Student_Kotlin kotlin = new Student_Kotlin("李四", 19, Student_KotlinKt.getTEXT());
        if (BuildConfig.DEBUG) Log.d("MainActivity", "kotlin:" + kotlin);
    }

    private void setHeaderView() {
        View view = LayoutInflater.from(this)
                .inflate(R.layout.header_item_main, (ViewGroup) mRecycler.getParent(), false);
        mAdapter.setHeaderView(view);
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
        mAdapter = new MyAdatper(R.layout.item_main_text, mStudents);
        mAdapter.setHeaderFooterEmpty(true, true);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        mAdapter.openLoadAnimation();
        mAdapter.isFirstOnly(false);
        mAdapter.disableLoadMoreIfNotFullPage();

        mRecycler.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(new BaseMultiItemQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MainActivity.this, "adapter.getData().get(position):" + adapter.getData()
                        .get(position), Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MainActivity.this, "adapter.getData().get(position):" + adapter.getData()
                        .get(position), Toast.LENGTH_SHORT).show();
                return false;
            }
        });



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
                if (flog) {
                    mAdapter.loadMoreFail();
                } else {
                    mAdapter.loadMoreEnd();
                }

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

    boolean flog = true;
    @Override
    public void onLoadMoreRequested() {
        if (flog) {
            initData2();
            flog = false;
        } else {
            setStudentData(new ArrayList<Student>());
        }

    }

    private void initData2() {
        mSwipeRefresh.setEnabled(false);
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student student = new Student(null, getResources().getDrawable(R.mipmap.ic_launcher), age, Student.IMAGE);
            students.add(student);
            age++;
        }
        setStudentData(students);
    }

}
