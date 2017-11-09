package com.duimy.dmimsdk.dbmanager;

import com.duimy.dmimsdk.core.DMCore;
import com.duimy.dmimsdk.model.DMConversation;
import com.duimy.dmimsdk.model.DMImageMessageBody;
import com.duimy.dmimsdk.model.DMLocationMessageBody;
import com.duimy.dmimsdk.model.DMMessage;
import com.duimy.dmimsdk.model.DMMessageBody;
import com.duimy.dmimsdk.model.DMTextMessageBody;
import com.duimy.dmimsdk.model.DMVoiceMessageBody;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * SQLite数据库管理类
 * <p>
 * 主要负责数据库资源的初始化,开启,关闭,以及获得DatabaseHelper帮助类操作
 *
 * @author shimiso
 */
public class DBManager {

    public static DbManager currentDB;

    private DBManager() {
    }

    //    private static class InstanceHolder{
    //        private static DbManager INSTANCE = null;
    //        static {
    //            DbManager.DaoConfig messagetDaoConfig = getMessagetDaoConfig();
    //            if (messagetDaoConfig != null){
    //                INSTANCE = x.getDb(messagetDaoConfig);
    //            }
    //        }
    //    }

    private static DbManager getCurrentDB() {
        if (currentDB == null) {
            synchronized (DBManager.class) {
                if (currentDB == null) {
                    DbManager.DaoConfig messagetDaoConfig = getMessagetDaoConfig();
                    if (messagetDaoConfig != null) {
                        currentDB = x.getDb(messagetDaoConfig);
                    }

                }
            }
        }
        return currentDB;
    }

    private static DbManager.DaoConfig getMessagetDaoConfig() {

        /**
         * 获取用户名，并以用户名创建数据库
         */
        String username = DMCore.getInstance().getCurrentUser();
        if (username == null) {
            return null;
        }
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("DM_" + username + "_imsdk.db")
                .setDbVersion(1)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        db.getDatabase().enableWriteAheadLogging();
                        //开启WAL, 对写入加速提升巨大(作者原话)
                    }
                });

        return daoConfig;
    }

    /**
     * 从数据库查询会话
     *
     * @param conversationId 会话id
     * @param type           会话类型
     * @return
     */
    public static DMConversation getConversation(String conversationId, int type) {
        DbManager manager = getCurrentDB();
        if (manager == null) {
            return null;
        }

        DMConversation conversation = null;
        try {
            conversation = manager.selector(DMConversation.class)
                    .where("_conversationId", "=", conversationId)
                    .and("_type", "=", type)
                    .findFirst();
            if (conversation != null) {
                // 如果存在，则需要设置latestMessage
                DMMessage message = getMessage(conversation.getMessageId());
                conversation.setLatestMessage(message);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        return conversation;
    }

    /**
     * 删除某个会话
     *
     * @param conversationId
     * @throws DbException
     */
    public static void deleteConversation(String conversationId) throws DbException {
        DbManager manager = getCurrentDB();
        if (manager == null) {
            return;
        }

        WhereBuilder conBuilder = WhereBuilder.b();
        conBuilder.and("_conversationId", "=", conversationId);


        // 2.清除消息表数据
        manager.delete(DMMessage.class, conBuilder);
        // 3.清除文本body 表数据
        manager.delete(DMTextMessageBody.class, conBuilder);
        // 4.清除定位body表数据
        manager.delete(DMLocationMessageBody.class, conBuilder);
        // 5.清除图片body表数据
        manager.delete(DMImageMessageBody.class, conBuilder);
        // 6.清除语音body表数据
        manager.delete(DMVoiceMessageBody.class, conBuilder);

        // 1.清除会话数据
        manager.delete(DMConversation.class, conBuilder);
    }

    /**
     * 删除某条消息
     *
     * @param dmMessage
     * @throws DbException
     */
    public static void deleteDMMessage(DMMessage dmMessage) throws DbException {
        DbManager manager = getCurrentDB();
        if (manager == null) {
            return;
        }

        WhereBuilder conBuilder = WhereBuilder.b();
        conBuilder.and("_msgId", "=", dmMessage.getMsgId());

        // 2.清除消息表数据
        manager.delete(DMMessage.class, conBuilder);
        // 3.清除文本body 表数据
        manager.delete(DMTextMessageBody.class, conBuilder);
        // 4.清除定位body表数据
        manager.delete(DMLocationMessageBody.class, conBuilder);
        // 5.清除图片body表数据
        manager.delete(DMImageMessageBody.class, conBuilder);
        // 6.清除语音body表数据
        manager.delete(DMVoiceMessageBody.class, conBuilder);

        List<DMMessage> dmMessageList = manager.findAll(DMMessage.class);

        if (dmMessageList == null || dmMessageList.size() == 0) {
            manager.delete(DMConversation.class, WhereBuilder.b().and("_conversationId", "=", dmMessage.getConversationId()));
        } else {
            DMConversation dmConversation = manager.findById(DMConversation.class, dmMessage.getConversationId());
            String msgId = dmMessageList.get(dmMessageList.size() - 1).getMsgId();
            if (msgId != null) {
                dmConversation.setMessageId(msgId);
                manager.update(dmConversation, "_messageId");
            }
        }
    }


    /**
     * 获取会话列表
     *
     * @return
     */
    public static List<DMConversation> findAllConversation() {
        DbManager manager = getCurrentDB();
        if (manager == null) {
            return null;
        }

        List<DMConversation> conversationList = null;
        try {
            conversationList = manager.findAll(DMConversation.class);
            if (conversationList != null) {
                for (DMConversation conversation : conversationList) {
                    String msgId = conversation.getMessageId();
                    if (msgId != null) {
                        DMMessage message = getMessage(msgId);
                        if (message != null) {
                            conversation.setLatestMessage(message);
                        } else {
                            continue;
                        }
                    }
                }
                //按照会话最新消息的时间降序排列
                Collections.sort(conversationList, new Comparator<DMConversation>() {

                    @Override
                    public int compare(DMConversation dmConversation1, DMConversation dmConversation2) {
                        if (dmConversation1.getLatestMessage() == null || dmConversation2.getLatestMessage() == null) {
                            return 0;
                        }
                        long time1 = dmConversation1.getLatestMessage().getTimeStamp();
                        long time2 = dmConversation2.getLatestMessage().getTimeStamp();

                        return (int) (time2 - time1);
                    }
                });
            }
            return conversationList;
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将会话的未读消息数清零
     *
     * @param conversationId 会话id
     */
    public static void clearUnReadMessageCount(String conversationId) {
        DbManager manager = getCurrentDB();
        if (manager == null) {
            return;
        }

        try {
            DMConversation conversation = manager.findById(DMConversation.class, conversationId);
            if (conversation == null) {
                return;
            }
            conversation.setUnReadMsgCount(0);
            manager.update(conversation, "_unReadMsgCount");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过msgId 查询message
     *
     * @param msgId msgId
     * @return
     */
    public static DMMessage getMessage(String msgId) {
        DbManager manager = getCurrentDB();
        if (manager == null) {
            return null;
        }
        DMMessage message = null;
        try {
            message = manager.selector(DMMessage.class)
                    .where("_msgId", "=", msgId)
                    .findFirst();
            DMMessageBody body = null;
            if (message != null) {
                switch (message.getType()) {
                    case DMMessageBody.Body_Type_Text:
                        body = getMessageBody(DMTextMessageBody.class, msgId);
                        break;
                    case DMMessageBody.Body_Type_Location:
                        body = getMessageBody(DMLocationMessageBody.class, msgId);
                        break;
                    case DMMessageBody.Body_Type_Image:
                        body = getMessageBody(DMImageMessageBody.class, msgId);
                        break;
                    case DMMessageBody.Body_Type_Voice:
                        body = getMessageBody(DMVoiceMessageBody.class, msgId);
                        break;
                }
                message.setBody(body);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        return message;
    }

    /**
     * 查询某个会话下的消息
     *
     * @param conversationId 会话id
     * @param offset         跳过多少条
     * @param count          一次查询多少条
     * @return
     */
    public static List<DMMessage> loadMessages(String conversationId, int offset, int count) {
        DbManager manager = getCurrentDB();
        if (manager == null) {
            return new ArrayList<DMMessage>();
        }

        List<DMMessage> messageList = null;
        try {
            messageList = manager.selector(DMMessage.class)
                    .where("_conversationId", "=", conversationId)
                    .offset(offset)
                    .limit(count)
                    .orderBy("_timeStamp", true)
                    .findAll();
            if (messageList == null) {
                messageList = new ArrayList<DMMessage>();
                return messageList;
            }
            for (DMMessage message : messageList) {
                DMMessageBody body = null;
                switch (message.getType()) {
                    case DMMessageBody.Body_Type_Text:
                        body = getMessageBody(DMTextMessageBody.class, message.getMsgId());
                        break;
                    case DMMessageBody.Body_Type_Location:
                        body = getMessageBody(DMLocationMessageBody.class, message.getMsgId());
                        break;
                    case DMMessageBody.Body_Type_Image:
                        body = getMessageBody(DMImageMessageBody.class, message.getMsgId());
                        break;
                    case DMMessageBody.Body_Type_Voice:
                        body = getMessageBody(DMVoiceMessageBody.class, message.getMsgId());
                        break;
                }
                message.setBody(body);
            }

            Collections.reverse(messageList);
        } catch (DbException e) {
            e.printStackTrace();
            messageList = new ArrayList<DMMessage>();
        }

        return messageList;
    }

    /**
     * 查询某个会话下的消息
     *
     * @param conversationId 会话id
     * @param timeStamp        时间
     * @param count          一次查询多少条
     * @return
     */
    //    public static List<DMMessage> loadMessages(String conversationId, long timeStamp, int count) {
    //        DbManager manager = getCurrentDB();
    //        if (manager == null) {
    //            return new ArrayList<DMMessage>();
    //        }
    //
    //        List<DMMessage> messageList = null;
    //        try {
    //            messageList = manager.selector(DMMessage.class)
    //                    .where("_conversationId", "=", conversationId)
    //                    .and("_timeStamp", "=", timeStamp)
    //
    //                    .offset(timeStamp)
    //                    .limit(count)
    //                    .orderBy("_timeStamp", true)
    //                    .findAll();
    //            if (messageList == null) {
    //                messageList = new ArrayList<DMMessage>();
    //                return messageList;
    //            }
    //            for (DMMessage message : messageList) {
    //                DMMessageBody body = null;
    //                switch (message.getType()) {
    //                    case DMMessageBody.Body_Type_Text:
    //                        body = getMessageBody(DMTextMessageBody.class, message.getMsgId());
    //                        break;
    //                    case DMMessageBody.Body_Type_Location:
    //                        body = getMessageBody(DMLocationMessageBody.class, message.getMsgId());
    //                        break;
    //                    case DMMessageBody.Body_Type_Image:
    //                        body = getMessageBody(DMImageMessageBody.class, message.getMsgId());
    //                        break;
    //                    case DMMessageBody.Body_Type_Voice:
    //                        body = getMessageBody(DMVoiceMessageBody.class, message.getMsgId());
    //                        break;
    //                }
    //                message.setBody(body);
    //            }
    //
    //            Collections.reverse(messageList);
    //        } catch (DbException e) {
    //            e.printStackTrace();
    //            messageList = new ArrayList<DMMessage>();
    //        }
    //
    //        return messageList;
    //    }

    /**
     * 通过msgId 和 body 类型 查询某个消息的body对象
     *
     * @param clazz body 的class
     * @param msgId 消息id
     * @param <T>
     * @return 返回body对象
     */
    public static <T extends DMMessageBody> DMMessageBody getMessageBody(Class<T> clazz, String msgId) {
        DbManager manager = getCurrentDB();
        if (manager == null) {
            return null;
        }

        DMMessageBody body = null;
        try {
            body = manager.selector(clazz).where("_messageId", "=", msgId).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }

        return body;
    }

    /**
     * 保存消息
     *
     * @param message
     */
    public static void saveMessage(DMMessage message) {
        DbManager manager = getCurrentDB();
        if (manager == null) {
            return;
        }

        try {

            DMMessage dbMessage = getMessage(message.getMsgId());
            if (dbMessage != null) {
                return;
            }

            //TODO 目前先用本地时间
            message.setTimeStamp(System.currentTimeMillis());
            // 1.保存message
            manager.save(message);

            // 2.保存body
            DMMessageBody body = message.getBody();
            body.setConversationId(message.getConversationId());
            body.setMessageId(message.getMsgId());
            manager.save(body);

            String currentUser = DMCore.getInstance().getCurrentUser();
            // 3.更新会话表
            DMConversation conversation = getConversation(message.getConversationId(), message.getChatType());
            if (conversation == null) {
                conversation = new DMConversation(message.getConversationId(), message.getChatType());
                conversation.setMessageId(message.getMsgId());
                if (currentUser.startsWith(message.getTo())) {
                    conversation.setUnReadMsgCount(1);
                }
                manager.save(conversation);
            } else {
                conversation.setMessageId(message.getMsgId());
                conversation.setLatestMessage(message);
                if (currentUser.startsWith(message.getTo())) {
                    conversation.setUnReadMsgCount(1 + conversation.getUnReadMsgCount());
                }
                manager.saveOrUpdate(conversation);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
