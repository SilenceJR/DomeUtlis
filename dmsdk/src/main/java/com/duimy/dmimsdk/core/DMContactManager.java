package com.duimy.dmimsdk.core;

import android.util.Log;
import android.util.Xml;

import com.duimy.dmimsdk.DMCallBack;
import com.duimy.dmimsdk.DMValueCallBack;
import com.duimy.dmimsdk.listener.DMContactListener;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.roster.packet.RosterPacket;
import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by haley on 2017/7/13.
 */

public class DMContactManager {
    private DMCore dmCore;

    private Roster roster;

    private List<DMContactListener> contactListeners = Collections.synchronizedList(new ArrayList<DMContactListener>());

    protected DMContactManager() {
    }

    protected DMContactManager(final DMCore dmCore) {
        this.dmCore = dmCore;
        this.roster = Roster.getInstanceFor(dmCore.getConnection());
        this.roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
        this.roster.addRosterListener(new RosterListener() {
            @Override
            public void entriesAdded(Collection<String> collection) {
                for (String jid : collection) {
                    RosterEntry rosterEntry = roster.getEntry(jid);
                    if (rosterEntry != null) {
                        if (rosterEntry.getType() == RosterPacket.ItemType.both) {
                            // 别人加自己，会走这个回调
                            for (DMContactListener contactListener : contactListeners) {
                                contactListener.onContactAdded(jid);
                            }
                        }
                    }
                }
            }

            @Override
            public void entriesUpdated(Collection<String> collection) {
                for (String jid : collection) {
                    RosterEntry rosterEntry = roster.getEntry(jid);
                    if (rosterEntry != null) {
                        if (rosterEntry.getType() == RosterPacket.ItemType.both) {
                            // 自己主动加别人，会走这个回调
                            for (DMContactListener contactListener : contactListeners) {
                                contactListener.onContactAdded(jid);
                            }
                        }
                    }
                }
            }

            @Override
            public void entriesDeleted(Collection<String> collection) {
                for (String jid : collection) {
                    for (DMContactListener contactListener : contactListeners) {
                        contactListener.onContactDeleted(jid);
                    }
                }
            }

            @Override
            public void presenceChanged(Presence presence) {
                Log.d("presenceChanged", presence.toString());
            }
        });

        StanzaFilter filter = new AndFilter(new StanzaTypeFilter(Presence.class));

        StanzaListener listener = new StanzaListener() {
            @Override
            public void processPacket(Stanza stanza) throws SmackException.NotConnectedException {
                if (stanza instanceof Presence) {
                    Presence presence = (Presence) stanza;

                    if (presence.getType() == Presence.Type.subscribe) { // 对方请求添加好友

                        RosterEntry entry = roster.getEntry(presence.getFrom());
                        if (entry != null && entry.getType() == RosterPacket.ItemType.to) {
                            // 如果是自己添加对方为好友，收到对方的订阅信息
                            Presence replyPresence = new Presence(Presence.Type.subscribed);
                            replyPresence.setTo(presence.getFrom());
                            dmCore.getConnection().sendStanza(replyPresence);
                            return;
                        }

                        InputStream inputStream = new ByteArrayInputStream(presence.toString()
                                .getBytes());
                        try {
                            String message = XMLParse(inputStream, "message");
                            for (DMContactListener contactListener : contactListeners) {
                                contactListener.onFriendRequestReceived(presence.getFrom(), message);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (presence.getType() == Presence.Type.unsubscribe || presence.getType() == Presence.Type.unsubscribed) { //对方请求删除好友
                        RosterEntry rosterEntry = roster.getEntry(presence.getFrom());
                        if (rosterEntry != null) {
                            try {
                                roster.removeEntry(rosterEntry);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };

        dmCore.getConnection().addAsyncStanzaListener(listener, filter);
    }

    private String XMLParse(InputStream is, String domName) throws Exception {

        String value = null;

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "UTF-8");

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (domName.equals(parser.getName())) {
                        parser.next();
                        value = parser.getText();
                    }
                    break;
            }
            eventType = parser.next();
        }

        return value;
    }

    public void addContactListener(DMContactListener listener) {
        if (!this.contactListeners.contains(listener)) {
            this.contactListeners.add(listener);
        }
    }

    public void removeContactListener(DMContactListener listener) {
        this.contactListeners.remove(listener);
    }

    /**
     * 同步获取好友列表,返回好友jid 列表
     *
     * @return
     */
    public List<String> getAllContactsFromServer() {
        List<String> contactList = new ArrayList<>();
        Set<RosterEntry> rosterEntries = this.roster.getEntries();

        for (RosterEntry entry : rosterEntries) {
            if (entry.getType() == RosterPacket.ItemType.both) {
                String user = entry.getUser();
                if (user.contains("@")) {
                    user = user.split("@")[0];
                }
                contactList.add(user);
            }
        }

        return contactList;
    }

    /**
     * 异步获取指定好友,返回好友
     *
     * @return
     */
    public void aysnGetContactFromServer(final String userJid, final DMValueCallBack<RosterEntry> callBack) {
        dmCore.execute(new Runnable() {
            @Override
            public void run() {
                RosterEntry entry = roster.getEntry(userJid);
                if (callBack != null) {
                    callBack.onSuccess(entry);
                }
            }
        });
    }

    /**
     * 异步设置指定好友的备注
     *
     * @return
     */
    public void aysnSetContactName(final String userJid, final String newName) throws SmackException.NotConnectedException, XMPPException.XMPPErrorException, SmackException.NoResponseException {
        RosterEntry entry = roster.getEntry(userJid);
        entry.setName(newName);
    }


    /**
     * 异步获取好友jid列表
     *
     * @param callBack
     */
    public void aysncGetAllContactsFromServer(final DMValueCallBack<List<String>> callBack) {
        dmCore.execute(new Runnable() {
            @Override
            public void run() {
                List contacts = DMContactManager.this.getAllContactsFromServer();
                if (callBack != null) {
                    callBack.onSuccess(contacts);
                }
            }
        });
    }

    /**
     * 异步发送添加好友申请
     *
     * @param user
     * @param message
     * @param callBack
     */
    public void addContact(final String user, final String message, final DMCallBack callBack) {
        dmCore.execute(new Runnable() {
            @Override
            public void run() {
                Roster roster = Roster.getInstanceFor(dmCore.getConnection());
                try {
                    roster.createEntry(user + "@duimy", user, new String[]{"Friends"});
                    if (callBack != null) {
                        callBack.onSuccess();
                    }
                } catch (SmackException.NotLoggedInException e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onError(-1, "发送失败");
                    }
                } catch (SmackException.NoResponseException e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onError(-1, "发送失败");
                    }
                } catch (XMPPException.XMPPErrorException e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onError(-1, "发送失败");
                    }
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onError(-1, "发送失败");
                    }
                }
            }
        });
    }

    /**
     * 异步同意好友申请
     *
     * @param user
     * @param callBack
     */
    public void acceptRequest(final String user, final DMCallBack callBack) {
        dmCore.execute(new Runnable() {
            @Override
            public void run() {
                    Presence presence = new Presence(Presence.Type.subscribed);
                presence.setTo(user + "@duimy");
                try {
                    // 告诉对方，已订阅成功
                    dmCore.getConnection().sendStanza(presence);

                    // 发送一条订阅对方的消息
                    Presence presence2 = new Presence(Presence.Type.subscribe);
                    presence2.setTo(user + "@duimy");
                    dmCore.getConnection().sendStanza(presence2);
                    if (callBack != null) {
                        callBack.onSuccess();
                    }
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onError(-1, "处理失败");
                    }
                }
            }
        });
    }

    /**
     * 异步拒绝好友申请
     *
     * @param user
     * @param callBack
     */
    public void rejectRequest(final String user, final DMCallBack callBack) {
        dmCore.execute(new Runnable() {
            @Override
            public void run() {
                Presence presence = new Presence(Presence.Type.unsubscribed);
                presence.setTo(user);
                try {
                    dmCore.getConnection().sendStanza(presence);
                    RosterEntry rosterEntry = roster.getEntry(presence.getFrom());
                    if (rosterEntry != null) {
                        roster.removeEntry(rosterEntry);
                    }
                    if (callBack != null) {
                        callBack.onSuccess();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onError(-1, "处理失败");
                    }
                }
            }
        });
    }

    /**
     * 异步删除好友
     *
     * @param user
     * @param callBack
     */
    public void deleteContact(final String user, final DMCallBack callBack) {
        final String newUser = user + "@duimy";
        dmCore.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    roster.removeEntry(roster.getEntry(newUser));
                    if (callBack != null) {
                        callBack.onSuccess();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onError(-1, "删除失败");
                    }
                }

//                Presence presence = new Presence(Presence.Type.unsubscribed);
//                presence.setTo(newUser);
//
//                try {
//                    dmCore.getConnection().sendStanza(presence);
//
//                    if (callBack != null) {
//                        callBack.onSuccess();
//                    }
//                } catch (SmackException.NotConnectedException e) {
//                    e.printStackTrace();
//                    if (callBack != null) {
//                        callBack.onError(-1, "删除失败");
//                    }
//                }
            }
        });
    }
}
