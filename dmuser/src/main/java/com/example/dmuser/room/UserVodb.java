package com.example.dmuser.room;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.os.Handler;
import android.os.Looper;

import com.example.dmuser.inter.BaseCallBack;
import com.example.dmuser.inter.UserVodbCallBack;
import com.example.dmuser.model.UserVo;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @作者: PJ
 * @创建时间: 2017/11/24 / 16:20
 * @描述: 这是一个 UserVodb 类.
 */
public class UserVodb {

    private volatile static UserVodb mInstance;
    private static Application sApplication;
    private final UserVoDataBase mUserVoDataBase;
    private final Executor mCachedThreadPool = Executors.newCachedThreadPool();
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public static UserVodb getInstance() {
        if (mInstance == null) {
            synchronized (UserVodb.class) {
                if (mInstance == null) {
                    mInstance = new UserVodb();
                }
            }
        }
        return mInstance;
    }

    public static void init(Application context) {
        sApplication = context;
    }

    public UserVodb() {
        if (sApplication == null) {
            throw new RuntimeException("UserVodb需要进行初始化");
        }
        mUserVoDataBase = Room.databaseBuilder(sApplication, UserVoDataBase.class, "UserVo.db")
                .build();
    }

    public void getAll(UserVodbCallBack callBack) {
                mCachedThreadPool.execute(() -> {
            List<UserVo> userVos = mUserVoDataBase.mUserVoDao().getAll();
                    if (userVos == null ||userVos.size() == 0) {
                        mHandler.post(() -> callBack.onError());
                    } else {
                        mHandler.post(() -> callBack.onSuccess(userVos));
                    }
        });
    }

    public void getAll(BaseCallBack<List<UserVo>> callBack) {
        mCachedThreadPool.execute(() -> {
            List<UserVo> userVos = mUserVoDataBase.mUserVoDao().getAll();
            if (userVos == null ||userVos.size() == 0) {
                mHandler.post(() -> callBack.onError());
            } else {
                mHandler.post(() -> callBack.onSuccess(userVos));
            }
        });
    }

    public void queryUserVo(String userName, BaseCallBack<UserVo> callBack) {
        mCachedThreadPool.execute(() -> {
            UserVo userVo = mUserVoDataBase.mUserVoDao().queryUserVo(userName);
            if (userVo == null) {
                mHandler.post(() -> callBack.onError());
            } else {
                mHandler.post(() -> callBack.onSuccess(userVo));
            }
        });
    }

    public void queryUserVo(String[] userNames, BaseCallBack<List<UserVo>> callBack) {
        mCachedThreadPool.execute(() -> {
            List<UserVo> userVos = mUserVoDataBase.mUserVoDao().queryUserVo(userNames);
            if (userVos == null || userVos.size() == 0) {
                mHandler.post(() -> callBack.onError());
            } else {
                mHandler.post(() -> callBack.onSuccess(userVos));
            }
        });
    }

    public void saveUserVo(UserVo userVo) {
        queryUserVo(userVo.getUserName(), new BaseCallBack<UserVo>() {
            @Override
            public void onSuccess(UserVo userVo) {
                mCachedThreadPool.execute(() -> mUserVoDataBase.mUserVoDao().updateUserVo(userVo));
            }

            @Override
            public void onError() {
                mCachedThreadPool.execute(() -> mUserVoDataBase.mUserVoDao().insertUserVo(userVo));
            }
        });

    }

    public void saveUserVo(List<UserVo> userVos) {
        String[] UserNames = new String[userVos.size()];
        for (int i = 0; i < userVos.size(); i++) {
            UserNames[i] = userVos.get(i).getUserName();
        }

        if (UserNames != null || UserNames.length != 0) {
            queryUserVo(UserNames, new BaseCallBack<List<UserVo>>() {
                @Override
                public void onSuccess(List<UserVo> userVos) {
                    mCachedThreadPool.execute(() -> mUserVoDataBase.mUserVoDao().updateUserVo(userVos));
                }

                @Override
                public void onError() {
                    mCachedThreadPool.execute(() -> mUserVoDataBase.mUserVoDao().insertUserVo(userVos));
                }
            });
        }



    }
}
