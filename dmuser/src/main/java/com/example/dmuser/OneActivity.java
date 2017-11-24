package com.example.dmuser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmuser.inter.BaseCallBack;
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
    @BindView(R.id.btn_load_db_firends)
    Button mBtnLoadDbFirends;
    @BindView(R.id.btn_clear)
    Button mBtnClear;
    private UserVo mUserVo;
    private UserVodb mVodb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        ButterKnife.bind(this);

        mUserVo = ((UserVo) getIntent().getSerializableExtra("UserVo"));
        mVodb = UserVodb.getInstance();
    }


    @OnClick({R.id.btn_load_firends, R.id.btn_load_db_firends, R.id.btn_clear})
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
                            mTvShowData.setText(userVos.toString());
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
                        mTvShowData.setText(userVos.toString());
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
        }
    }
}
