package com.example.dmuser.network.api;

/**
 * @作者: PJ
 * @创建时间: 2017/11/23 / 15:59
 * @描述: 这是一个 PayConstant 类.
 */
class UserVoConstant {

//    public static final String DUIMY_UPLOAD = "http://39.108.88.1/";
    //    public static final String DUIMY_UPLOAD = "http://192.168.1.139/";
    //    public static final String DUIMY_UPLOAD = "http://192.168.1.186:8080/";
        public static final String DUIMY_UPLOAD = "http://192.168.1.40/";
    private static final String DUIMY_APP_HOST = "duimy/app/";
    public static final String DUIMY_HOST = DUIMY_UPLOAD + DUIMY_APP_HOST;
    public static final String DUIMY_MONEY_HOST = DUIMY_APP_HOST + "money/";
    private static final String DUIMY_APK_HOST = DUIMY_APP_HOST + "apk/";
    private static final String DUIMY_SMS_HOST = DUIMY_APP_HOST + "sms/";

    /**
     * 注册接口
     * 参数: phone, password, checkCode
     */
    public static final String DUIMY_REGISTER = DUIMY_APP_HOST + "register";

    /**
     * 获取公钥
     */
    public static final String DUIMY_GETRSAPUBKEY = DUIMY_APP_HOST + "getRSAPubKey";

    public static final String DUIMY_GETAPKINFO = DUIMY_APK_HOST + "getApkInfo";

    /**
     * 判断该用户手机号是否已注册,没有注册则可注册
     * 参数: loginName
     */
    public static final String DUIMY_ISREGISTERED = DUIMY_APP_HOST + "isRegistered";

    /**
     * 登陆接口
     * 参数: phone, password
     */
    public static final String DUIMY_LOGIN = DUIMY_APP_HOST + "login";

    /**
     * 获取验证码接口
     */
    public static final String DUIMY_SENDCHECKCODE = DUIMY_SMS_HOST + "sendCheckCode";


    /**
     * 查找好友-根据好友的对面ID数组
     * 参数: userNames : 对面ID数组
     */
    public static final String DUIMY_FINDUSERVOSBYUSERNAMES = DUIMY_APP_HOST + "findUserVosByUserNames";

    /**
     * 查找好友-精确查找,
     * 参数: loginName : DUimyId, 手机号, 邮箱
     * http://192.168.1.166/duimy/app/findUserVoByLoginName/
     */
    public static final String DUIMY_FINDUSERVOBYLOGINNAME = DUIMY_APP_HOST + "findUserVoByLoginName";

    /**
     * 加载好友列表
     * 参数:userNames
     */
    public static final String DUIMY_LOADFRIENDS = DUIMY_APP_HOST + "loadFriends";

    /**
     * 获取用户资料
     * 参数:loginName 用户DuimyID或手机号
     * 参数:fieldNames 需要的参数数组
     */
    public static final String DUIMY_FINDSPECIFIEDFIELDSBYLOGINNAME = DUIMY_APP_HOST + "findSpecifiedFieldsByLoginName";

    /**
     * 修改个人信息
     * 参数: User类相关的字段(名称要一至)
     * 返回:json
     */
    public static final String DUIMY_UPDATEUSERVOINFO = DUIMY_APP_HOST + "updateUserVoInfo";

    /**
     * 更新用户昵称
     * 参数 (String loginName, String nickName)
     */
    public static final String DUIMY_UPDATENICKNAME = DUIMY_APP_HOST + "updateNickName";

    /**
     * 上传用户头像
     * 参数: (String loginName, MultipartFile file, HttpServletRequest request)
     */
    public static final String DUIMY_UPLOADHEADIMG = "uploadHeadImg";

    /**
     * 更新用户头像
     * 参数 (String loginName, String headImgPath)
     */
    public static final String DUIMY_UPDATEHEADIMGPATH = "updateHeadImgPath";

    /**
     * 更新用户性别
     * 参数 (String loginName, String sex)
     */
    public static final String DUIMY_UPDATESEX = DUIMY_APP_HOST + "updateSex";

    /**
     * 发送手机验证码
     * 参数 (String phone)
     */
    public static final String DUIMY_SENDFORGETPSWCODE = DUIMY_SMS_HOST + "sendForgetPswCode";

    /**
     * 校验短信验证码
     * 参数 (String phone, String checkCode)
     */
    public static final String DUIMY_VALIDATESMSCODE = DUIMY_SMS_HOST + "validateSmsCode";

    /**
     * 重置密码
     * 参数 (String phone, String newPassword)
     */
    public static final String DUIMY_RESETPSW = DUIMY_APP_HOST + "resetPsw";
}
