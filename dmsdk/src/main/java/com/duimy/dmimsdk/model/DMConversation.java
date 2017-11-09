package com.duimy.dmimsdk.model;

import com.duimy.dmimsdk.core.DMCore;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by haley on 2017/7/17.
 */

@Table(name = "T_Conversation")
public class DMConversation {
    public static final int Chat = 0;
    public static final int GroupChat = 1;
    public static final int ChatRoom = 2;

    @Column(name = "_conversationId", isId  = true)
    private String conversationId;

    @Column(name = "_type")
    private int type;

    @Column(name = "_messageId")
    private String messageId;

    private DMMessage latestMessage;

    @Column(name = "_unReadMsgCount")
    private int unReadMsgCount;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public DMMessage getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(DMMessage latestMessage) {
        this.latestMessage = latestMessage;
    }

    public int getUnReadMsgCount() {
        return unReadMsgCount;
    }

    public void setUnReadMsgCount(int unReadMsgCount) {
        this.unReadMsgCount = unReadMsgCount;
    }

    public DMConversation() {

    }

    public DMConversation(String conversationId, int type) {
        this.conversationId = conversationId;
        this.type = type;
        this.unReadMsgCount = 0;
    }

    @Override
    public String toString() {
        return "DMConversation{" +
                "conversationId='" + conversationId + '\'' +
                ", type=" + type +
                ", messageId='" + messageId + '\'' +
                ", latestMessage=" + latestMessage +
                ", unReadMsgCount=" + unReadMsgCount +
                '}';
    }

    public void markAllMessagesAsRead() {
        DMCore.getInstance().chatManager().clearUnreadMessageCount(conversationId);
    }

    public DMMessage getLastMessage() {
        return latestMessage;
    }
}
