package com.duimy.dmimsdk.listener;

import java.util.List;

import com.duimy.dmimsdk.model.DMMessage;

/**
 * Created by haley on 2017/7/17.
 */

public interface DMChatMessageListener {

    /**
     * 收到消息时的回调
     * @param dmMessage
     */
    void onReceiveMessages(final DMMessage dmMessage);
}
