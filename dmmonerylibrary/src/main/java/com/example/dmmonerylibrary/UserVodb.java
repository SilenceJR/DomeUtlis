package com.example.dmmonerylibrary;

import android.app.Application;
import android.arch.persistence.room.Room;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @作者: PJ
 * @创建时间: 2017/11/30 / 15:01
 * @描述: 这是一个 UserVodb 类.
 */
public class UserVodb {

    private static volatile UserVodb mInstance;
    private static Application mContext;
    private final UserDataBase mBase;
    private final ExecutorService mCachedThreadPool = Executors.newCachedThreadPool();

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

    public static void init(Application application) {
        mContext = application;
    }


    public UserVodb() {
        if (mContext == null) {
            throw new RuntimeException("为初始化!");
        }
        mBase = Room.databaseBuilder(mContext, UserDataBase.class, "User.db").build();
    }


    public List<TestUserVo> queryUserName(String userName) {
        //        Future<List<User>> future = mCachedThreadPool.submit(new Callable<List<User>>() {
        //            @Override
        //            public List<User> call() throws Exception {
        //                return mBase.mUserDao().loadAllByIds(userName);
        //            }
        //        });

        Future<List<TestUserVo>> futureTest = mCachedThreadPool.submit(new Callable<List<TestUserVo>>() {
            @Override
            public List<TestUserVo> call() throws Exception {
                List<User> users = mBase.mUserDao().loadAllByIds(userName);
                return mBase.mTestUserVoDao().getTestUserVoName(users.get(0).getUserName());
            }
        });

        try {
            List<TestUserVo> testUserVos = futureTest.get();
            return testUserVos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        //        try {
        //            List<User> users = future.get();
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
    }

    public List<TestUserVo> queryTestUserVos(User user) throws Exception {
        User queryUser = queryUser(user);
        if (queryUser != null) {
            TestUserVo testUserVo = queryTestUserVo(queryUser);
            if (testUserVo != null) {
                Future<List<TestUserVo>> future = mCachedThreadPool.submit(() -> mBase.mTestUserVoDao()
                        .getTestUserVoName(testUserVo.getUserName()));
                return future.get();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    public User queryUser(Object entiy) throws Exception {
        if (entiy instanceof List) {
            List<User> users = (List<User>) entiy;
            Future<User> future = mCachedThreadPool.submit(() -> mBase.mUserDao()
                    .loadUser(users.get(0).getUserName()));
            return future.get();
        } else {
            Future<User> future = mCachedThreadPool.submit(() -> mBase.mUserDao()
                    .loadUser(((User) entiy).getUserName()));
            return future.get();
        }
    }

    public TestUserVo queryTestUserVo(Object entiy) throws Exception {
        if (entiy instanceof List) {
            List<TestUserVo> users = (List<TestUserVo>) entiy;
            Future<TestUserVo> future = mCachedThreadPool.submit(() -> mBase.mTestUserVoDao()
                    .loadTestUserVo(users.get(0).getUserName()));
            return future.get();
        } else if (entiy instanceof User) {
            Future<TestUserVo> future = mCachedThreadPool.submit(() -> mBase.mTestUserVoDao()
                    .loadTestUserVo(((User) entiy).getUserName()));
            return future.get();
        } else {
            Future<TestUserVo> future = mCachedThreadPool.submit(() -> mBase.mTestUserVoDao()
                    .loadTestUserVo(((TestUserVo) entiy).getUserName()));
            return future.get();
        }
    }

    public TestVoBase queryTestBase(Object entiy) throws Exception {
        if (entiy instanceof List) {
            List<TestVoBase> users = (List<TestVoBase>) entiy;
            Future<TestVoBase> future = mCachedThreadPool.submit(() -> mBase.mTestVoBaseDao()
                    .loadIdByTestBase(users.get(0).getId()));
            return future.get();
        }else {
            Future<TestVoBase> future = mCachedThreadPool.submit(() -> mBase.mTestVoBaseDao()
                    .loadIdByTestBase(((TestVoBase) entiy).getId()));
            return future.get();
        }
    }

    public List<TestVoBase> queryAllTestBase() throws ExecutionException, InterruptedException {
        Future<List<TestVoBase>> future = mCachedThreadPool.submit(() -> mBase.mTestVoBaseDao()
                .getAll());

        return future.get();
    }

    public TestVoBase queryUserNameByTestBase(String userName) throws ExecutionException, InterruptedException {
        Future<TestVoBase> future = mCachedThreadPool.submit(() -> mBase.mTestVoBaseDao()
                .UserNameByTestBase(userName));
        return future.get();
    }

    public void saveUser(User user) {
        mCachedThreadPool.execute(() -> {
            try {
                User queryUser = queryUser(user);
                if (queryUser == null) {
                    mBase.mUserDao().insertAll(user);
                } else {
                    mBase.mUserDao().updateUser(user);
                }
            } catch (Exception e) {

            }
        });
    }

    public void saveTestUser(TestUserVo testUserVo) {
        mCachedThreadPool.execute(() -> {
            TestUserVo queryTestUserVo = null;
            try {
                queryTestUserVo = queryTestUserVo(testUserVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (queryTestUserVo == null) {
                mBase.mTestUserVoDao().insertAll(testUserVo);
            } else {
                mBase.mTestUserVoDao().updateUser(testUserVo);
            }
        });
    }

    public void save(User user, TestUserVo testUserVo) {
        saveUser(user);
        saveTestUser(testUserVo);
    }

    public List<User> getUserAll() throws ExecutionException, InterruptedException {
        Future<List<User>> future = mCachedThreadPool.submit(() -> mBase.mUserDao().getAll());
        return future.get();
    }

    public void saveTestBase(TestVoBase testVoBase) {
        mCachedThreadPool.execute(() -> {
            TestVoBase queryTestBase = null;
            try {
                queryTestBase = queryTestBase(testVoBase);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (queryTestBase == null) {
                mBase.mTestVoBaseDao().insertAll(testVoBase);
            } else {
                mBase.mTestVoBaseDao().updateUser(testVoBase);
            }
        });
    }

    public void deleteTestUserVoBase(TestVoBase testVoBase) {
        mCachedThreadPool.execute(() -> mBase.mTestVoBaseDao().deleteTestUserVo(testVoBase));

    }
}
