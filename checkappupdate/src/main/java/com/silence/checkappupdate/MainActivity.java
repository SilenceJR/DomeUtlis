package com.silence.checkappupdate;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.joker.annotation.PermissionsDenied;
import com.joker.annotation.PermissionsGranted;
import com.joker.api.Permissions4M;
import com.silence.checkappupdate.model.CheckAppUpdateModel;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {
    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 0x01;

    @InjectView(R.id.bt_check)
    Button mBtCheck;
    private MainPresenterImpl mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mMainPresenter = new MainPresenterImpl(this);


    }

    @OnClick(R.id.bt_check)
    public void onViewClicked() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Permissions4M.get(MainActivity.this)
                    // 是否强制弹出权限申请对话框，建议设置为 true，默认为 true
                    //                 .requestForce(true)
                    // 是否支持 5.0 权限申请，默认为 false
                    // .requestUnderM(false)
                    // 权限，单权限申请仅只能填入一个
                    .requestOnRationale()
                    .requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    // 权限码
                    .requestCodes(WRITE_EXTERNAL_STORAGE_CODE)
                    // 如果需要使用 @PermissionNonRationale 注解的话，建议添加如下一行
                    // 返回的 intent 是跳转至**系统设置页面**
                    // .requestPageType(Permissions4M.PageType.MANAGER_PAGE)
                    // 返回的 intent 是跳转至**手机管家页面**
                    // .requestPageType(Permissions4M.PageType.ANDROID_SETTING_PAGE)
                    .request();
        } else {
            mMainPresenter.onCheckAppUptade();
        }
        mMainPresenter.onCheckAppUptade();

    }

    @PermissionsGranted(WRITE_EXTERNAL_STORAGE_CODE)
    public void granted() {
        Toast.makeText(this, "文件访问授权成功", Toast.LENGTH_SHORT).show();
        mMainPresenter.onCheckAppUptade();
    }

    @PermissionsDenied(WRITE_EXTERNAL_STORAGE_CODE)
    public void denied() {
        Toast.makeText(this, "文件访问授权失败", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSuccess(CheckAppUpdateModel checkAppUpdateModel) {
        DownloadApk.download(this, checkAppUpdateModel);
    }

    @Override
    public void onError(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
        if (BuildConfig.DEBUG) Log.d(TAG, errorMsg);
    }

    @Override
    public void onThrowableError() {
        Toast.makeText(this, "踹飞特的服务器又作死的关了!", Toast.LENGTH_SHORT).show();
    }

}
