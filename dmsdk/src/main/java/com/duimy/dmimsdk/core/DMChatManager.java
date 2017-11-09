package com.duimy.dmimsdk.core;

import com.duimy.dmimsdk.DMCallBack;
import com.duimy.dmimsdk.DMValueCallBack;
import com.duimy.dmimsdk.constant.DMConstant;
import com.duimy.dmimsdk.dbmanager.DBManager;
import com.duimy.dmimsdk.listener.DMChatMessageListener;
import com.duimy.dmimsdk.listener.DMConversationListener;
import com.duimy.dmimsdk.listener.onNetCallBack;
import com.duimy.dmimsdk.model.DMConversation;
import com.duimy.dmimsdk.model.DMFileMessageBody;
import com.duimy.dmimsdk.model.DMImageMessageBody;
import com.duimy.dmimsdk.model.DMMessage;
import com.duimy.dmimsdk.model.DMMessageBody;
import com.duimy.dmimsdk.model.DMVoiceMessageBody;
import com.duimy.dmimsdk.model.uploadData;
import com.duimy.dmimsdk.utils.DMXMLParser;
import com.duimy.dmimsdk.utils.FileManager;
import com.duimy.dmimsdk.utils.FutureUtils;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by haley on 2017/7/13.
 */

public class DMChatManager {

    private DMCore dmCore;

    private ChatManager chatManager;

    protected List<DMChatMessageListener> chatMessageListeners = Collections.synchronizedList(new ArrayList<DMChatMessageListener>());

    protected List<DMConversationListener> conversationListeners = Collections.synchronizedList(new ArrayList<DMConversationListener>());

    protected DMChatManager() {
    }

    protected DMChatManager(DMCore dmCore) {
        this.dmCore = dmCore;
        this.chatManager = ChatManager.getInstanceFor(dmCore.getConnection());

        ChatManagerListener chatManagerListener = new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean b) {
                chat.addMessageListener(new ChatMessageImp());
            }
        };

        this.chatManager.addChatListener(chatManagerListener);
    }

    private class ChatMessageImp implements ChatMessageListener {

        @Override
        public void processMessage(final Chat chat, final Message message) {

            dmCore.execute(new Runnable() {
                @Override
                public void run() {
                    if (message.getType() != Message.Type.chat) {
                        return;
                    }

                    InputStream inputStream = new ByteArrayInputStream(message.toString()
                            .getBytes());
                    int type = Integer.parseInt(message.getBody());

                    // 1. 将XML解析成DMMessageBody
                    DMMessageBody body = DMXMLParser.XMLParse(inputStream, type);

                    if (type == DMMessageBody.Body_Type_Image || type == DMMessageBody.Body_Type_Voice) {
                        DMFileMessageBody body1 = (DMFileMessageBody) body;
                        String localPath = FileManager.sysnDownloadFile(body1.getRemotePath());
                        body1.setLocalPath(localPath);
                    }

                    String fullFrom = message.getFrom();
                    String[] split = fullFrom.split("/");
                    String from = split[0];
                    // 2.构造DMMessage 对象
                    DMMessage dmMessage = DMMessage.createMessage(from.split("@")[0], from, message.getTo(), body);
                    dmMessage.setChatType(DMConversation.Chat);
                    dmMessage.setMsgId(message.getStanzaId());
                    dmMessage.setDirection(DMMessage.Direction_Receive);

                    // 3.存到本地数据库,内部会更新会话的msgId
                    DBManager.saveMessage(dmMessage);

                    // 4.将新的message通知给监听器
                    for (DMChatMessageListener chatMessageListener : chatMessageListeners) {
                        chatMessageListener.onReceiveMessages(dmMessage);
                    }
                    // 5.通知有会话更新
                    for (DMConversationListener conversationListener : conversationListeners) {
                        conversationListener.onUpdateConversationList();
                    }
                }
            });
        }
    }

    public void logout() {
        //移除监听以及manager等
    }


    /**
     * 获取一个会话，先从数据库查询，如果不存在就构造一个返回
     * 构造的这个会话，并不会立刻保存到数据库，会在保存消息时保存
     *
     * @param conversationId 会话id
     * @param type           会话类型：单聊、群聊、讨论组
     * @return
     */
    public DMConversation getConversation(String conversationId, int type) {
        // 1、从数据库查
        DMConversation conversation = DBManager.getConversation(conversationId, type);
        // 2.如果数据库没有，则构造一个conversation对象返回
        if (conversation == null) {
            conversation = new DMConversation(conversationId, type);
        }
        return conversation;
    }

    /**
     * 获取本地会话列表   会按照会话的最新消息的时间降序排列
     *
     * @param valueCallBack
     */
    public void getAllConversations(final DMValueCallBack<List<DMConversation>> valueCallBack) {
        FutureUtils.callRun(new Callable<List<DMConversation>>() {
            @Override
            public List<DMConversation> call() throws Exception {
                // 1.从数据库查询
                List<DMConversation> conList = DBManager.findAllConversation();
                return conList;
            }
        }, new DMValueCallBack<List<DMConversation>>() {
            @Override
            public void onSuccess(List<DMConversation> result) {
                if (result == null) {
                    result = new ArrayList<>();
                }
                valueCallBack.onSuccess(result);
            }

            @Override
            public void onError(int code, String message) {
                valueCallBack.onError(code, message);
            }
        });
    }

    /**
     * 删除本地的某个会话
     *
     * @param conversationId 会话id
     * @param callBack
     */
    public void deleteConversation(final String conversationId, final DMCallBack callBack) {
        if (conversationId == null) {
            if (callBack != null) {
                callBack.onError(-1, "会话id为空");
            }
        }

        FutureUtils.callRun(new Callable<String>() {
            @Override
            public String call() throws Exception {
                DBManager.deleteConversation(conversationId);
                return "";
            }
        }, new DMValueCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                callBack.onSuccess();
            }

            @Override
            public void onError(int code, String message) {
                callBack.onError(-1, "删除失败");
            }
        });

        //        dmCore.execute(new Runnable() {
        //            @Override
        //            public void run() {
        //                // 1.从本地数据库删除会话
        //                try {
        //                    DBManager.deleteConversation(conversationId);
        //                    if (callBack != null) {
        //                        callBack.onSuccess();
        //                    }
        //                } catch (DbException e) {
        //                    e.printStackTrace();
        //                    if (callBack != null) {
        //                        callBack.onError(-1, "删除失败");
        //                    }
        //                }
        //            }
        //        });
    }


    /**
     * 发送消息
     * 发送消息内部 会保存消息到消息表，同时更新会话表中的关联的msgId
     *
     * @param dmMessage
     * @param callBack
     */
    public void sendMessage(final DMMessage dmMessage, final String token, final DMCallBack callBack) {
        dmMessage.setDirection(DMMessage.Direction_Send);
        dmCore.execute(new Runnable() {
            @Override
            public void run() {

                final Message message = new Message(dmMessage.getTo());
                // 1.将消息保存到本地,内部会更新会话的msgId
                dmMessage.setMsgId(message.getStanzaId());

                int type = dmMessage.getType();

                String uploadUrl = DMConstant.DM_FILEPATH;
                if (uploadUrl != null) {
                    String remoteUrl = null;
                    if (type == DMMessageBody.Body_Type_Image) {  //图片
                        final DMImageMessageBody body = (DMImageMessageBody) dmMessage.getBody();
                        FileManager.uploadFileChat(body.getLocalPath(), "1", dmCore.getCurrentUser(), token, new onNetCallBack<uploadData>() {
                            @Override
                            public void onSuccess(uploadData uploadData) {
                                body.setRemotePath(uploadData.getHeadImgPath());
                                upload2SendMessage(message, dmMessage, callBack);
                            }

                            @Override
                            public void onError(int code, String errorMsg) {
                                callBack.onError(code, errorMsg);
                            }

                            @Override
                            public void onThrowableError() {

                            }
                        });
//                        remoteUrl = FileManager.sysnUploadFile(uploadUrl, body.getLocalPath(), "1");
//                        body.setRemotePath(remoteUrl);
                    } else if (type == DMMessageBody.Body_Type_Voice) {  //语音
                        final DMVoiceMessageBody body = (DMVoiceMessageBody) dmMessage.getBody();
                        FileManager.uploadFileChat(body.getLocalPath(), "2", dmCore.getCurrentUser(), token, new onNetCallBack<uploadData>() {
                            @Override
                            public void onSuccess(uploadData uploadData) {
                                body.setRemotePath(uploadData.getHeadImgPath());
                                upload2SendMessage(message, dmMessage, callBack);
                            }

                            @Override
                            public void onError(int code, String errorMsg) {
                                callBack.onError(code, errorMsg);
                            }

                            @Override
                            public void onThrowableError() {

                            }
                        });

//                        remoteUrl = FileManager.sysnUploadFile(uploadUrl, body.getLocalPath(), "2");
//                        body.setRemotePath(remoteUrl);
                    } else {
                        upload2SendMessage(message, dmMessage, callBack);
                    }
                }
            }
        });
    }

    private void upload2SendMessage(Message message, DMMessage dmMessage, DMCallBack callBack) {
        // 2.将消息通过Smack 发送出去
        if (dmMessage.getChatType() == DMConversation.Chat) {
            message.setType(Message.Type.chat);
        } else {
            message.setType(Message.Type.groupchat);
        }
        // 1.将消息保存到本地
        DBManager.saveMessage(dmMessage);
        message.addBody(null, String.valueOf(dmMessage.getType()));
        // 为Message 添加子节点
        message.addExtension(dmMessage.getBody());
        try {
            //发送消息
            dmCore.getConnection().sendStanza(message);

            // 4.通知给监听器,会话已更新
            for (DMConversationListener conversationListener : conversationListeners) {
                conversationListener.onUpdateConversationList();
            }

            if (callBack != null) {
                callBack.onSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (callBack != null) {
                callBack.onError(-1, "发送失败");
            }
        }
    }

    /**
     * 将某个会话的未读消息数清零
     *
     * @param conversationId
     */
    public void clearUnreadMessageCount(String conversationId) {
        DBManager.clearUnReadMessageCount(conversationId);
    }

    /**
     * 加载某个会话下的消息列表  分页查询
     *
     * @param conversationId 会话id
     * @param offset         跳过前多少条
     * @param count          查询的条数
     * @param valueCallBack
     */
    public void loadMessages(final String conversationId, final int offset, final int count, final DMValueCallBack<List<DMMessage>> valueCallBack) {
        FutureUtils.callRun(new Callable<List<DMMessage>>() {
            @Override
            public List<DMMessage> call() throws Exception {
                List<DMMessage> msgList = DBManager.loadMessages(conversationId, offset, count);
                return msgList;
            }
        }, new DMValueCallBack<List<DMMessage>>() {
            @Override
            public void onSuccess(List<DMMessage> result) {
                valueCallBack.onSuccess(result);
            }

            @Override
            public void onError(int code, String message) {

            }
        });
    }

    /**
     * 加载某个会话下的消息列表  分页查询
     *
     * @param conversationId 会话id
     * @param offset         跳过前多少条
     * @param count          查询的条数
     */
    public List<DMMessage> loadMessages(final String conversationId, final int offset, final int count) {
        List<DMMessage> msgList = DBManager.loadMessages(conversationId, offset, count);
        return msgList;
    }

    public void addChatMessageListener(DMChatMessageListener listener) {
        if (!this.chatMessageListeners.contains(listener)) {
            this.chatMessageListeners.add(listener);
        }
    }

    public void removeChatMessageListener(DMChatMessageListener listener) {
        this.chatMessageListeners.remove(listener);
    }

    public void addConversationListener(DMConversationListener listener) {
        if (!this.conversationListeners.contains(listener)) {
            this.conversationListeners.add(listener);
        }
    }

    public void removeConversationListener(DMConversationListener listener) {
        this.conversationListeners.remove(listener);
    }

}
