package com.duimy.dmroomlibrary.user;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.os.Handler;
import android.os.Looper;

import com.duimy.dmroomlibrary.inter.BaseCallBack;

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


//        Migration migration = new Migration(1, 2) {
//            @Override
//            public void migrate(@NonNull SupportSQLiteDatabase database) {
//            }
//        };

        mUserVoDataBase = Room.databaseBuilder(sApplication, UserVoDataBase.class, "UserVo.db")
                .build();

    }

    public void getAll(BaseCallBack<List<UserVo>> callBack) {
        mCachedThreadPool.execute(() -> {
            List<UserVo> userVos = mUserVoDataBase.mUserVoDao().getAll();
            if (userVos == null) {
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

    public void queryAllSimpleUser(BaseCallBack<LiveData<List<UserSimpleVo>>> callBack) {
        mCachedThreadPool.execute(() -> {
            LiveData<List<UserSimpleVo>> userSimpleVo = mUserVoDataBase.mUserVoDao()
                    .queryAllUserSimpleVo();
            if (userSimpleVo == null) {
                mHandler.post(() -> callBack.onError());
            } else {
                mHandler.post(() -> callBack.onSuccess(userSimpleVo));
            }
        });
    }

    public void saveUserVo(UserVo user) {
        queryUserVo(user.getUserName(), new BaseCallBack<UserVo>() {
            @Override
            public void onSuccess(UserVo userVo) {
                mCachedThreadPool.execute(() -> mUserVoDataBase.mUserVoDao().updateUserVo(user));
            }

            @Override
            public void onError() {
                mCachedThreadPool.execute(() -> mUserVoDataBase.mUserVoDao().insertUserVo(user));
            }
        });

    }

    public void saveUserVo(List<UserVo> userVos) {

        if (userVos.isEmpty()) {
            return;
        }

        for (int i = 0; i < userVos.size(); i++) {
            saveUserVo(userVos.get(i));
        }
    }

    public void deleteUserVo(Object userVoEntity) {
        if (userVoEntity instanceof List) {
            List<UserVo> entitys = (List<UserVo>) userVoEntity;
            if (entitys.isEmpty()) {
                return;
            }
            for (UserVo userVo : entitys) {
                mCachedThreadPool.execute(() -> mUserVoDataBase.mUserVoDao().deleteUserVo(userVo));
            }
        } else if (userVoEntity instanceof UserVo) {
            mCachedThreadPool.execute(() -> mUserVoDataBase.mUserVoDao()
                    .deleteUserVo((UserVo) userVoEntity));
        }
    }

    public void deleteAllUserVo() {
        getAll(new BaseCallBack<List<UserVo>>() {
            @Override
            public void onSuccess(List<UserVo> userVos) {
                deleteUserVo(userVos);
            }

            @Override
            public void onError() {

            }
        });
    }

    public void getAllListener(LifecycleOwner owner, Observer<List<UserVo>> observer) {
        mUserVoDataBase.mUserVoDao().getAllListener().observe(owner, observer);
    }

    public UserVoDao getUserVoDao() {
        return mUserVoDataBase.mUserVoDao();
    }
}
