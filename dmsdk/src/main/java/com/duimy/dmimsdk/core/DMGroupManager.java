package com.duimy.dmimsdk.core;

import android.util.Log;

import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.SimpleIQ;
import org.jivesoftware.smackx.muc.Affiliate;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.InvitationListener;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.muc.RoomInfo;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.duimy.dmimsdk.DMCallBack;
import com.duimy.dmimsdk.DMValueCallBack;
import com.duimy.dmimsdk.dbmanager.DBManager;
import com.duimy.dmimsdk.listener.DMChatMessageListener;
import com.duimy.dmimsdk.listener.DMConversationListener;
import com.duimy.dmimsdk.listener.DMGroupListener;
import com.duimy.dmimsdk.model.DMConversation;
import com.duimy.dmimsdk.model.DMGroup;
import com.duimy.dmimsdk.model.DMMessage;
import com.duimy.dmimsdk.model.DMMessageBody;
import com.duimy.dmimsdk.utils.DMXMLParser;

/**
 * Created by haley on 2017/7/13.
 */

public class DMGroupManager {

    public static String group_domain = "conference.duimy";
    public static String xmls_muc = "http://jabber.org/protocol/muc";
    public static String xmls_muc_user = "http://jabber.org/protocol/muc#user";
    public static String xmls_muc_owner = "http://jabber.org/protocol/muc#owner";
    public static String xmls_muc_admin = "http://jabber.org/protocol/muc#admin";
    public static String xmls_disco_item = "http://jabber.org/protocol/disco#items";
    public static String xmls_disco_info = "http://jabber.org/protocol/disco#info";

    private DMCore dmCore;

    private MultiUserChatManager multiUserChatManager;

    private List<DMGroupListener> groupListeners = Collections.synchronizedList(new ArrayList<DMGroupListener>());

    protected  DMGroupManager() {}

    DMGroupManager(DMCore dmCore) {
        this.dmCore = dmCore;
        this.multiUserChatManager = MultiUserChatManager.getInstanceFor(dmCore.getConnection());
        this.multiUserChatManager.addInvitationListener(new InvitationListener() {
            @Override
            public void invitationReceived(XMPPConnection xmppConnection, MultiUserChat multiUserChat, String s, String s1, String s2, Message message) {
                // 目前是默认收到群邀请后，自动加入到群里
                // 上面的s 是发起群邀请的人的jid, s1 是邀请的备注信息
                String user = xmppConnection.getUser();
                try {
                    multiUserChat.join(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        loadAllGroups();
    }

    // 从本地数据库加载群组列表
    public List<DMGroup> getAllGroups() {
        ArrayList groups = new ArrayList();

        // 从数据库查询群组,然后构造DMGroup对象

        return groups;
    }

    private void loadAllGroups() {
        dmCore.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String user = dmCore.getConnection().getUser();
                    Collection<HostedRoom> hostedRooms = multiUserChatManager.getHostedRooms(group_domain);

                    for (HostedRoom hostedRoom : hostedRooms) {
                        MultiUserChat group = multiUserChatManager.getMultiUserChat(hostedRoom.getJid());
                        group.join(user);

                        group.addMessageListener(new MessageListener() {
                            @Override
                            public void processMessage(Message message) {

                                if (message.getType() != Message.Type.groupchat) {
                                    return;
                                }

                                InputStream inputStream = new ByteArrayInputStream(message.toString().getBytes());
                                int type = Integer.parseInt(message.getBody());

                                // 1.1 将XML解析成DMMessageBody
                                DMMessageBody body = DMXMLParser.XMLParse(inputStream, type);

                                // 2.构造DMMessage 对象
                                String fulljid = message.getFrom();
                                String[] split = fulljid.split("/");
                                String conversationId = split[0];
                                String from = split[1];
                                DMMessage dmMessage = DMMessage.createMessage(conversationId, from, message.getTo(), body);
                                dmMessage.setChatType(DMConversation.GroupChat);
                                dmMessage.setMsgId(message.getStanzaId());
                                dmMessage.setDirection(DMMessage.Direction_Receive);

                                // 3.存到本地数据库,并更新会话表
                                DBManager.saveMessage(dmMessage);

                                // 4.将新的message通知给监听器
                                List<DMChatMessageListener> msgList = dmCore.chatManager().chatMessageListeners;
                                for (DMChatMessageListener chatMessageListener : msgList) {
                                    chatMessageListener.onReceiveMessages(dmMessage);
                                }

                                List<DMConversationListener> conList = dmCore.chatManager().conversationListeners;
                                // 5.通知有会话更新
                                for (DMConversationListener conversationListener : conList) {
                                    conversationListener.onUpdateConversationList();
                                }
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 异步的获取自己加入的群组列表
     * @param callBack
     */
    public void getJoinedGroupsFromServer(final DMValueCallBack<List<DMGroup>> callBack) {
        dmCore.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String user = dmCore.getConnection().getUser();
                    Collection<HostedRoom> hostedRooms = multiUserChatManager.getHostedRooms(group_domain);

                    List<DMGroup> groups = new ArrayList<DMGroup>();
                    for (HostedRoom hostedRoom : hostedRooms) {
                        Log.e("",hostedRoom.toString());
                        DMGroup group = new DMGroup();
                        group.groupId = hostedRoom.getJid();
                        group.groupName = hostedRoom.getName();
                        groups.add(group);
                    }

                    if (callBack != null) {
                        callBack.onSuccess(groups);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.onError(-1, "获取失败");
                }
            }
        });
    }

    /**
     * 异步的获取群信息
     * @param groupId
     * @param callBack
     */
    public void getGroupInfo(final String groupId, final DMValueCallBack callBack) {
        dmCore.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    RoomInfo roomInfo = multiUserChatManager.getRoomInfo(groupId);
                    if (callBack != null) {
                        callBack.onSuccess(roomInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onError(-1, "获取群信息失败");
                    }
                }
            }
        });
    }

    /**
     * 创建群
     * @param groupName     群名称
     * @param descp         群描述信息
     * @param invitees      被邀请的人的jid 数组
     * @param reason        邀请是的备注
     * @param callBack
     */
    public void createGroup(final String groupName, final String descp, final String[] invitees, final String reason, final DMCallBack callBack) {
        dmCore.execute(new Runnable() {
            @Override
            public void run() {
                String groupId = new Date().hashCode() + "@" + DMGroupManager.group_domain;
                MultiUserChat group = multiUserChatManager.getMultiUserChat(groupId);
                try {
                    String user = dmCore.getConnection().getUser();
                    group.create(user);
                    Form form = group.getConfigurationForm();
                    Form answerForm = form.createAnswerForm();
                    for(FormField field : form.getFields()) {
                        if (!FormField.Type.hidden.name().equals(field.getType()) && field.getVariable() != null) {
                            answerForm.setDefaultAnswer(field.getVariable());
                        }
                    }

                    answerForm.setAnswer("muc#roomconfig_publicroom", false);
                    answerForm.setAnswer("muc#roomconfig_persistentroom", true);
                    answerForm.setAnswer("muc#roomconfig_membersonly", true);
                    answerForm.setAnswer("muc#roomconfig_allowinvites", true);
                    answerForm.setAnswer("muc#roomconfig_roomname", groupName);
                    answerForm.setAnswer("muc#roomconfig_roomdesc", descp);
                    List<String> maxUsers = new ArrayList<String>();
                    maxUsers.add("500");
                    answerForm.setAnswer("muc#roomconfig_maxusers", maxUsers);
                    answerForm.setAnswer("x-muc#roomconfig_reservednick", false);
                    answerForm.setAnswer("muc#roomconfig_passwordprotectedroom", false);

                    group.sendConfigurationForm(answerForm);
                    group.join(user);

                    if (invitees != null && invitees.length > 0) {
                        //邀请加群
                        inviteMembers(groupId, invitees, reason, null);
                    }

                    if (callBack != null) {
                        callBack.onSuccess();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onError(-1, "创建失败");
                    }
                }
            }
        });
    }

    /**
     * 邀请加群（仅限管理员）
     * @param groupId           群jid
     * @param members           被邀请的用户jid数组
     * @param reason            邀请备注
     * @param callBack
     */
    public void inviteMembers(final String groupId, final String[] members, final String reason, final DMCallBack callBack) {
        dmCore.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    MultiUserChat group = multiUserChatManager.getMultiUserChat(groupId);
                    for (String member : members) {
                        group.invite(member, reason);
                    }
                    if (callBack != null) {
                        callBack.onSuccess();
                    }
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onError(-1, "邀请失败");
                    }
                }
            }
        });
    }

    /**
     * 管理员踢人
     * @param groupId   群jid
     * @param user      用户jid
     * @param callBack  完成的回调
     */
    public void removeMemberFromGroup(final String groupId, final String user, final DMCallBack callBack) {
        dmCore.execute(new Runnable() {
            @Override
            public void run() {
                MultiUserChat group = multiUserChatManager.getMultiUserChat(groupId);
                try {
                    group.revokeMembership(user);
                    if (callBack != null) {
                        callBack.onSuccess();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onError(-1, "踢人失败");
                    }
                }
            }
        });
    }

    /**
     * 获取某个群的普通成员列表
     * @param groupId
     * @return
     */
    public List<Affiliate> getMembersList(final String groupId) {
        MultiUserChat group = multiUserChatManager.getMultiUserChat(groupId);
        try {
            List<Affiliate> members = group.getMembers();
            return members;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取某个群的管理员列表
     * @param groupId
     * @return
     */
    public List<Affiliate> getAdminsList(final String groupId) {
        MultiUserChat group = multiUserChatManager.getMultiUserChat(groupId);
        try {
            List<Affiliate> admins = group.getAdmins();
            return admins;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取某个群的群主列表
     * @param groupId
     * @return
     */
    public List<Affiliate> getOwnersList(final String groupId) {
        MultiUserChat group = multiUserChatManager.getMultiUserChat(groupId);
        try {
            List<Affiliate> owners = group.getOwners();
            return owners;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取所有的群成员，包括群主、群管理员、普通成员
     * @param groupId
     * @return
     */
    public List<Affiliate> getAllMembersList(final String groupId) {
        MultiUserChat group = multiUserChatManager.getMultiUserChat(groupId);
        try {
            List<Affiliate> allMembers = new ArrayList<Affiliate>();

            List<Affiliate> members = group.getMembers();
            List<Affiliate> admins = group.getAdmins();
            List<Affiliate> owners = group.getOwners();
            allMembers.addAll(members);
            allMembers.addAll(admins);
            allMembers.addAll(owners);

            return allMembers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置管理员
     * @param groupId
     * @param user          被授权管理员的用户jid
     * @param callBack
     */
    public void addGroupAdmin(final String groupId, final String user, final DMCallBack callBack) {
        dmCore.execute(new Runnable() {
            @Override
            public void run() {
                MultiUserChat group = multiUserChatManager.getMultiUserChat(groupId);
                try {
                    group.grantAdmin(user);
                    if (callBack != null) {
                        callBack.onSuccess();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onError(-1, "设置管理员失败啦");
                    }
                }
            }
        });
    }

    /**
     * 撤销管理员
     * @param groupId
     * @param user          被撤销的管理员的用户jid
     * @param callBack
     */
    public void revokeGroupAdmin(final String groupId, final String user, final DMCallBack callBack) {
        dmCore.execute(new Runnable() {
            @Override
            public void run() {
                MultiUserChat group = multiUserChatManager.getMultiUserChat(groupId);
                try {
                    group.revokeAdmin(user);
                    if (callBack != null) {
                        callBack.onSuccess();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onError(-1, "取消管理员失败啦");
                    }
                }
            }
        });
    }

    /**
     * 解散群，只有群主有这个权限
     * @param groupId
     * @param callBack
     */
    public void destroyGroup(final String groupId, final DMCallBack callBack) {
        dmCore.execute(new Runnable() {
            @Override
            public void run() {
                MultiUserChat group = multiUserChatManager.getMultiUserChat(groupId);
                try {
                    group.destroy(null, null);
                    if (callBack != null) {
                        callBack.onSuccess();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onError(-1, "解散群失败");
                    }
                }
            }
        });
    }

    public void addGroupListener(DMGroupListener listener) {
        if(!this.groupListeners.contains(listener)) {
            this.groupListeners.add(listener);
        }
    }

    public void removeGroupListener(DMGroupListener listener) {
        this.groupListeners.remove(listener);
    }
}
