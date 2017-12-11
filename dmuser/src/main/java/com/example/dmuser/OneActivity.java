package com.example.dmuser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmuser.inter.BaseCallBack;
import com.example.dmuser.model.UserSimpleVo;
import com.example.dmuser.model.UserVo;
import com.example.dmuser.network.HttpCall;
import com.example.dmuser.network.RespMsg;
import com.example.dmuser.room.UserVodb;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class OneActivity extends AppCompatActivity {

    @BindView(R.id.btn_load_firends)
    Button mBtnLoadFirends;
    @BindView(R.id.tv_show_data)
    TextView mTvShowData;
    @BindView(R.id.tv_show_simple_data)
    TextView mTvShowSimpleData;
    @BindView(R.id.btn_load_db_firends)
    Button mBtnLoadDbFirends;
    @BindView(R.id.btn_clear)
    Button mBtnClear;
    private UserVo mUserVo;
    private UserVodb mVodb;
    private List<UserVo> mUserVos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        ButterKnife.bind(this);

        mUserVo = ((UserVo) getIntent().getSerializableExtra("UserVo"));
        mVodb = UserVodb.getInstance();


        mTvShowData.setText(mUserVo.toString());



        mVodb.getUserVoDao().getAllListener().observe(this, new android.arch.lifecycle.Observer<List<UserVo>>() {
            @Override
            public void onChanged(@Nullable List<UserVo> userVos) {
                mTvShowData.setText(userVos.toString());
            }
        });

        mVodb.getUserVoDao().queryAllUserSimpleVo().observe(this, new android.arch.lifecycle.Observer<List<UserSimpleVo>>() {
            @Override
            public void onChanged(@Nullable List<UserSimpleVo> userSimpleVos) {
                mTvShowSimpleData.setText(userSimpleVos.toString());
            }
        });
    }


    private int age = 1;
    @OnClick({R.id.btn_load_firends, R.id.btn_load_db_firends, R.id.btn_clear, R.id.btn_delete_firend, R.id.btn_load_simple_user, R.id.btn_alter_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_load_firends:
                HttpCall.load(HttpCall.getInstance()
                        .getAPI()
                        .loadFriends(mUserVo.getUserName()), new Observer<UserVoRespMsg<List<UserVo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserVoRespMsg<List<UserVo>> listUserVoRespMsg) {
                        if (RespMsg.OK == listUserVoRespMsg.getCode()) {
                            List<UserVo> userVos = listUserVoRespMsg.getData();
                            mVodb.saveUserVo(userVos);
                            mUserVos = userVos;
                            mTvShowData.setText(mUserVos.toString());
                        } else {
                            Toasty.warning(OneActivity.this, listUserVoRespMsg.getMsg(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
                break;
            case R.id.btn_load_db_firends:
                mVodb.getAll(new BaseCallBack<List<UserVo>>() {
                    @Override
                    public void onSuccess(List<UserVo> userVos) {
                        mUserVos = userVos;
                        mTvShowData.setText(mUserVos.toString());
                        for (UserVo vo : userVos) {
                            if (BuildConfig.DEBUG) Log.d("OneActivity", "vo:" + vo);
                        }
                    }

                    @Override
                    public void onError() {
                        Toasty.error(OneActivity.this, "数据库读取异常", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case R.id.btn_clear:
                mTvShowData.setText("");
                break;

            case R.id.btn_delete_firend:
                mUserVos.remove(mUserVos.size() - 1);
                mVodb.getAll(new BaseCallBack<List<UserVo>>() {
                    @Override
                    public void onSuccess(List<UserVo> userVos) {
                        mVodb.deleteUserVo(userVos.get(userVos.size() - 1));

                    }

                    @Override
                    public void onError() {

                    }
                });
                mTvShowData.setText(mUserVos.toString());
                break;
            case R.id.btn_load_simple_user:
                UserVo vo = new UserVo();
                vo.setUserName("00001" + age);
                vo.setNickName("我是手动增加的");
                vo.setAddress("sdasdsafasf");
                mVodb.saveUserVo(vo);
                mUserVos.add(vo);
                age++;

//                mVodb.getAll();
//                                mVodb.queryAllSimpleUser(new BaseCallBack<LiveData<List<UserSimpleVo>>>() {
//                    @Override
//                    public void onSuccess(LiveData<List<UserSimpleVo>> listLiveData) {
//                        mTvShowData.setText(listLiveData.getValue().toString());
//                    }
//
//                    @Override
//                    public void onError() {
//                        Toasty.error(OneActivity.this, "数据库读取异常", Toast.LENGTH_SHORT).show();
//                    }
//                });
                break;
            case R.id.btn_alter_user:
                mVodb.queryUserVo("100101", new BaseCallBack<UserVo>() {
                    @Override
                    public void onSuccess(UserVo userVo) {
                        userVo.setNickName("我的名字被修改了!");
                        mVodb.saveUserVo(userVo);

                        mVodb.queryUserVo(userVo.getUserName(), new BaseCallBack<UserVo>() {
                            @Override
                            public void onSuccess(UserVo userVo) {
                                if (BuildConfig.DEBUG) Log.d("OneActivity", userVo.toString());
                            }

                            @Override
                            public void onError() {

                            }
                        });
                    }

                    @Override
                    public void onError() {

                    }
                });
                break;

        }
    }
}
