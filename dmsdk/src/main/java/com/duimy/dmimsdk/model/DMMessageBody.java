package com.duimy.dmimsdk.model;

import org.jivesoftware.smack.packet.ExtensionElement;
import org.xutils.db.annotation.Column;

/**
 * Created by haley on 2017/7/17.
 */

public abstract class DMMessageBody implements ExtensionElement {

    public static final String Message_Body = "messagebody";

    public static final int Body_Type_Text = 1;
    public static final int Body_Type_Image = 2;
    public static final int Body_Type_Video = 3;
    public static final int Body_Type_Location= 4;
    public static final int Body_Type_Voice = 5;
    public static final int Body_Type_System = 6;
    public static final int Body_Type_Cmd = 7;
    public static final int Body_Type_File = 8;


    @Column(name = "_id", isId = true, autoGen = true)
    protected Integer id;

    @Column(name = "_bodyType")
    private int bodyType;

    @Column(name = "_messageId")
    protected String messageId;

    @Column(name = "_conversationId")
    private String conversationId;

    public DMMessageBody() {

    }

    public int getBodyType() {
        return bodyType;
    }

    public void setBodyType(int bodyType) {
        this.bodyType = bodyType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    @Override
    public String getNamespace() {
        return Message_Body;
    }

    @Override
    public String getElementName() {
        return Message_Body;
    }

    @Override
    public abstract CharSequence toXML();

    @Override
    public String toString() {
        return "DMMessageBody{" +
                "id=" + id +
                ", bodyType=" + bodyType +
                ", messageId='" + messageId + '\'' +
                ", conversationId='" + conversationId + '\'' +
                '}';
    }
}
