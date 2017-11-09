package com.duimy.dmimsdk.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.duimy.dmimsdk.core.DMCore;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.File;

/**
 * Created by haley on 2017/7/17.
 */

@Table(name = "T_Message")
public class DMMessage{
    public static final int Direction_Send = 0;
    public static final int Direction_Receive = 1;

    @Column(name = "_msgId", isId  = true)
    private String msgId;
    @Column(name = "_from")
    private String from;
    @Column(name = "_to")
    private String to;
    @Column(name = "_conversationId")
    private String conversationId;
    @Column(name = "_direction")
    private int direction;
    @Column(name = "_status")
    private String status;
    @Column(name = "_chatType")
    private int    chatType;
    @Column(name = "_type")
    private int type;

    private DMMessageBody body;

    @Column(name = "_timeStamp")
    private long timeStamp;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DMMessageBody getBody() {
        return body;
    }

    public void setBody(DMMessageBody body) {
        this.body = body;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public static DMMessage createMessage(String conversationId, String from, String to, DMMessageBody body) {
        DMMessage message = new DMMessage();
        message.conversationId = conversationId;
        message.from = from;
        message.to = to;
        message.body = body;
        message.type = body.getBodyType();

        return message;
    }

    public static DMMessage createSendMessage(String to) {
        DMMessage message = new DMMessage();
        String[] user = DMCore.getInstance().getCurrentUser().split("/");
        message.from = user[0];
        message.conversationId = to;
        message.to = to + "@duimy";
        message.direction = DMMessage.Direction_Send;

        return message;
    }

    /**
     * 创建用于发送的文本消息
     * @param to    接收人jid
     * @param content 文本内容
     * @return  文本消息对象
     */
    public static DMMessage createTxtSendMessage(String to, String content) {
        DMMessage message = DMMessage.createSendMessage(to);
        DMTextMessageBody txtBody = new DMTextMessageBody(content);
        message.body = txtBody;
        message.type = txtBody.getBodyType();
        return message;
    }

    /**
     * 创建一个用于发送的定位消息
     * @param to    接收人jid
     * @param latitude  纬度
     * @param longitude 经度
     * @param address 定位地址
     * @return  定位消息对象
     */
    public static DMMessage createLocationSendMessage(String to, double latitude, double longitude, String address) {
        DMMessage message = DMMessage.createSendMessage(to);
        DMLocationMessageBody locBody = new DMLocationMessageBody(String.valueOf(latitude), String.valueOf(longitude), address);
        message.body = locBody;
        message.type = locBody.getBodyType();

        return message;
    }

    /**
     * 创建一个用于发送的图片消息
     * @param to    接收人jid
     * @param imagePath 图片的本地路径
     * @return  图片消息对象
     */
    public static DMMessage createImageSendMessage(String to, String imagePath) {
        DMMessage message = DMMessage.createSendMessage(to);
        File file = new File(imagePath);
        if (!file.exists()) {
            return null;
        }

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        if (bitmap == null) {
            return null;
        }
        DMImageMessageBody imgBody = new DMImageMessageBody(String.valueOf(bitmap.getWidth()),String.valueOf(bitmap.getHeight()),imagePath, String.valueOf(file.length()));
        message.body = imgBody;
        message.type = imgBody.getBodyType();

        return message;
    }

    public static DMMessage createVoiceSendMessage(String to, String filePath, int duration) {
        DMMessage message = DMMessage.createSendMessage(to);
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        return message;
    }

    @Override
    public String toString() {
        return "DMMessage{" +
                "msgId='" + msgId + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", conversationId='" + conversationId + '\'' +
                ", direction=" + direction +
                ", status='" + status + '\'' +
                ", chatType=" + chatType +
                ", type=" + type +
                ", body=" + body +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
