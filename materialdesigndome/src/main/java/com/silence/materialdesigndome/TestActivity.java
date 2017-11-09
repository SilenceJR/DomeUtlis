package com.silence.materialdesigndome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TestActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @InjectView(R.id.recycler)
    RecyclerView mRecycler;
    @InjectView(R.id.fab)
    FloatingActionButton mFab;
    @InjectView(R.id.nav_view)
    NavigationView mNavView;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.drawer)
    DrawerLayout mDrawer;

    private List<String> datas = null;
    private String[] strs = {"拍照", "分享", "反馈", "取消"};
    private BottomSheetDialog mSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.inject(this);

        initToolBar();
        initListener();

        datas = new ArrayList<>();
        for (String str : strs) {
            datas.add(str);
        }


        mRecycler.setLayoutManager(new LinearLayoutManager(this));


        initBottomSheet();
    }

    private void initBottomSheet() {

        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(this)
                .inflate(R.layout.test_list, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyTestListAdapter adapter = new MyTestListAdapter(datas);

        recyclerView.setAdapter(adapter);

        mSheetDialog = new BottomSheetDialog(this);
        mSheetDialog.setContentView(recyclerView);
        mSheetDialog.show();

        adapter.setOnItemClickListener(new MyTestListAdapter.onItemClickListener() {
            @Override
            public void onItenClick(String TvContent) {
                Toast.makeText(TestActivity.this, TvContent, Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void initToolBar() {
        mToolbar.setTitle("Drawer");
        mToolbar.setSubtitle("Test");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);
        View headerView = mNavView.inflateHeaderView(R.layout.header_test_view);

        ImageView headerImage = headerView.findViewById(R.id.header_image);
        headerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TestActivity.this, "点我头像干嘛", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initListener() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Snackbar snackbar = Snackbar.make(mRecycler, "你会用SnackBar么", Snackbar.LENGTH_SHORT);
                snackbar.show();
                snackbar.setAction("会用啦", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSheetDialog.show();
                        if (snackbar.isShown()) {
                            snackbar.dismiss();
                        }
                    }
                });
            }
        });



    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_search:
                Toast.makeText(TestActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ButtonActivity.class));
                break;
            case R.id.nav_settings:
                Toast.makeText(TestActivity.this, "设置", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, CoordinatorActivity.class));
//                showBottomSheet();
                break;
            case R.id.nav_share:
                Toast.makeText(TestActivity.this, "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_feedback:
                Toast.makeText(TestActivity.this, "反馈", Toast.LENGTH_SHORT).show();
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showBottomSheet() {
        new CustomBottomSheetDialogFragment().show(getSupportFragmentManager(), "Dialig");
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
