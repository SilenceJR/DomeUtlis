package com.duimy.dmimsdk.core;

import com.duimy.dmimsdk.DMCallBack;
import com.duimy.dmimsdk.constant.DMConstant;
import com.duimy.dmimsdk.dbmanager.DBManager;
import com.duimy.dmimsdk.utils.DMLogUtils;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by haley on 2017/7/13.
 */

public class DMCore {

    private static DMCore instance = null;
    private String pluginAddress;
    private static ExecutorService executor = null;
    private ExecutorService mainQueue = Executors.newSingleThreadExecutor();
    private ExecutorService sendQueue = Executors.newSingleThreadExecutor();
    private boolean sdkInited = false;
    private static final String TAG = "DMCore";

    private static AbstractXMPPConnection dmConnection = null;

    private DMChatManager chatManager;
    private DMContactManager contactManager;
    private DMGroupManager groupManager;

    public static ExecutorService getExecutor() {
        return executor;
    }

    {

        if (dmConnection == null) {

            init();

            //            connect();
        }

    }


    private DMCore() {
    }

    public static DMCore getInstance() {
        if (instance == null) {
            instance = new DMCore();
        }

        return instance;
    }

    public String getCurrentUser() {
        String user = getConnection().getUser();
        if (user == null || user.equals("")) {
            return "";
        }

        if (user.contains("@")) {
            user = user.split("@")[0];
        }

        return user;
    }

    // 配置Openfire服务器的信息
    public void init() {
        XMPPTCPConnectionConfiguration configuration = XMPPTCPConnectionConfiguration.builder()
                .setConnectTimeout(DMConstant.DM_TIMEOUT)//连接超时
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)//安全模式
                .setHost(DMConstant.DM_HOST)//ip
                .setPort(DMConstant.DM_PORT)//端口号设置一般式5222
                .setServiceName(DMConstant.DM_DOMAIN)//服务器名称
                .setResource(DMConstant.DM_ANDROID)
                .setDebuggerEnabled(true)//设置开启调试
                .setSendPresence(true)//设置是否发送Presece信息
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setCompressionEnabled(false)
                .build();
        dmConnection = new XMPPTCPConnection(configuration);

        dmConnection.addConnectionListener(new ConnectionListener() {
            @Override
            public void connected(XMPPConnection connection) {
                DMLogUtils.i(TAG, "已连接");
            }

            @Override
            public void authenticated(XMPPConnection connection, boolean resumed) {
                DMLogUtils.i(TAG, "已认证");
            }

            @Override
            public void connectionClosed() {
                DMLogUtils.i(TAG, "连接关闭");
            }

            @Override
            public void connectionClosedOnError(Exception e) {
                DMLogUtils.i(TAG, "连接关闭错误");
            }

            @Override
            public void reconnectionSuccessful() {
                DMLogUtils.i(TAG, "重连成功");
            }

            @Override
            public void reconnectingIn(int seconds) {
                DMLogUtils.i(TAG, "正在重连");
                getConnection();
            }

            @Override
            public void reconnectionFailed(Exception e) {
                DMLogUtils.i(TAG, "重连失败");
            }
        });

        ReconnectionManager reconnectionManager = ReconnectionManager.getInstanceFor(dmConnection);
        reconnectionManager.setFixedDelay(DMConstant.DM_RE_CONNET_TIME);//重联间隔
        reconnectionManager.enableAutomaticReconnection();//开启重联机制

        executor = Executors.newCachedThreadPool();

    }

    private static Runnable connTask = new Runnable() {
        @Override
        public synchronized void run() {
            if (dmConnection.isConnected()) {
                return;
            }
            try {
                dmConnection.connect();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("服务器连接超时");
            }
        }
    };


    //连接服务器

    public static void connect() {

        Future future = executor.submit(connTask);
        try {
            future.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
//            throw new RuntimeException("服务器连接超时");
        }
        //execute(connTask);
    }

    //返回连接对象，给其他模块使用
    public AbstractXMPPConnection getConnection() {
        if (!isConnected()) {
            connect();
        }
        return dmConnection;
    }

    /**
     * 是否已连接
     *
     * @return
     */
    public boolean isConnected() {
        if (dmConnection != null && dmConnection.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * 是否已登录
     *
     * @return
     */
    public boolean isLogined() {
        if (dmConnection != null && dmConnection.isAuthenticated() && dmConnection.getUser() != null) {
            return true;
        }
        return false;
    }

    /**
     * 断开连接
     *
     * @return
     */
    public void disconnect() {
        dmConnection.disconnect();
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param callBack
     */
    public void login(final String username, final String password, final DMCallBack callBack) {
        if (callBack == null) {
            throw new IllegalArgumentException("callback is null!");
        }

        if (username == null || username.equals("") || password == null || password.equals("")) {
            throw new IllegalArgumentException("username or password is null or empty!");
        }

        if (dmConnection == null) {
            init();
        }

        execute(new Runnable() {
            @Override
            public void run() {
                // 发起登录操作
                try {

                    AbstractXMPPConnection connection = getConnection();
                    if (connection == null) {
                        //返回一个失败的回调
                        callBack.onError(-1, "连接失败");
                        dmConnection = null;
                        throw new RuntimeException("连接失败");
                    }

                    if (!connection.isAuthenticated()) {
                        connection.login(username, password);
                    }
                    if (connection.isAuthenticated()) {
                        Presence presence = new Presence(Presence.Type.available);
                        presence.setStatus("我上线咯");
                        presence.setMode(Presence.Mode.available);
                        connection.sendStanza(presence);
                        callBack.onSuccess();
                        initManagers();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.onError(-1, "登录失败");
                }
            }
        });

    }

    /**
     * 退出登录
     *
     * @param callBack
     */
    public void logout(DMCallBack callBack) {

        if (dmConnection != null) {
            dmConnection.disconnect();
            dmConnection = null;
        }
        //
        this.chatManager = null;
        this.groupManager = null;
        this.contactManager = null;
        DBManager.currentDB = null;

        callBack.onSuccess();
    }

    /**
     * 注册
     *
     * @param username
     * @param password
     * @param callBack
     */
    public void register(final String username, final String password, final DMCallBack callBack) {
        if (callBack == null) {
            throw new IllegalArgumentException("callback is null!");
        }

        if (username == null || username.equals("") || password == null || password.equals("")) {
            throw new IllegalArgumentException("username or password is null or empty!");
        }

        if (dmConnection == null) {
            init();
        }

        this.execute(new Runnable() {
            @Override
            public void run() {
                // 获取连接
                AbstractXMPPConnection connection = getConnection();
                if (connection == null) {
                    //返回一个失败的回调
                    callBack.onError(-1, "连接失败");
                    return;
                }

                try {
                    AccountManager.getInstance(connection).createAccount(username, password);
                    callBack.onSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.onError(-1, "注册失败");
                }
            }
        });
    }

    private void initManagers() {
        this.chatManager();
        this.contactManager();
        this.groupManager();
    }

    /**
     * 获取聊天管理对象
     *
     * @return
     */
    public DMChatManager chatManager() {
        if (this.chatManager == null) {
            this.chatManager = new DMChatManager(this);
        }

        return this.chatManager;
    }

    /**
     * 获取联系人管理对象
     *
     * @return
     */
    public DMContactManager contactManager() {
        if (this.contactManager == null) {
            this.contactManager = new DMContactManager(this);
        }

        return this.contactManager;
    }

    /**
     * 获取群管理对象
     *
     * @return
     */
    public DMGroupManager groupManager() {
        if (this.groupManager == null) {
            this.groupManager = new DMGroupManager(this);
        }
        return this.groupManager;
    }

    static void execute(Runnable var1) {
        executor.execute(var1);
    }


}
