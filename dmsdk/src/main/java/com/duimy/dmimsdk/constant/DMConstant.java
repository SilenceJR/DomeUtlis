package com.duimy.dmimsdk.constant;

public class DMConstant {
//        public static final String DM_HOST = "39.108.88.1";
        public static final String DM_HOST = "192.168.1.40";
//    public static final String DM_HOST = "192.168.1.166";
    public static final String DUIMY_HOST = "http://" + DM_HOST + "/duimy/app/";
    public static final int DM_PORT = 5222;
    public static final String DM_DOMAIN = "duimy";
    public static final String DM_FILEPATH = DM_HOST + "/duimy/app/";
    public static final String DM_ANDROID = "Android";
    public static final int DM_TIMEOUT = 5000;
    public static final int DM_RE_CONNET_TIME = 5000 * 4;

    public static final String SP_KEY_TOKEN = "token";

    /**
     * 上传聊天数据
     * 参数 (String loginName, Integer pageNum)
     */
    public static final String DUIMY_FILE_UPLOAD_CHAT = DUIMY_HOST + "file/upload/chat";
}
