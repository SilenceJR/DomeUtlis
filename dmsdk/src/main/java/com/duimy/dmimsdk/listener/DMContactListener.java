package com.duimy.dmimsdk.listener;

/**
 * Created by haley on 2017/7/14.
 */

public interface DMContactListener {

    /**
     * 收到申请添加好友的回调
     * @param user
     * @param message
     */
    void onFriendRequestReceived(String user, String message);

    /**
     * 增加了联系人的回调
     * @param user
     */
    void onContactAdded(String user);

    /**
     * 删除了联系人的回调
     * @param user
     */
    void onContactDeleted(String user);

}
