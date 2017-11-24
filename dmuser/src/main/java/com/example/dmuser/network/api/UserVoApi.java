package com.example.dmuser.network.api;


import com.example.dmuser.UserVoRespMsg;
import com.example.dmuser.model.LoginModel;
import com.example.dmuser.model.UserVo;

import java.security.spec.RSAPublicKeySpec;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @作者: PJ
 * @创建时间: 2017/11/23 / 15:03
 * @描述: 这是一个 MoneryApi 类.
 */
public interface UserVoApi {
    /**
     * 更新用户昵称
     * 参数 (String loginName, String nickName)
     *
     * @return
     */
    @POST(UserVoConstant.DUIMY_UPDATENICKNAME)
    Observable<UserVoRespMsg<String>> updateNikeName(@Query("loginName") String loginName, @Query("nickName") String nickName);

    /**
     * 登陆
     *
     * @param cipher
     * @return
     */
    @POST(UserVoConstant.DUIMY_LOGIN)
    Observable<UserVoRespMsg<LoginModel>> login(@Query("cipher") String cipher);

    /**
     * 获取公钥
     *
     * @return
     */
    @POST(UserVoConstant.DUIMY_GETRSAPUBKEY)
    Observable<UserVoRespMsg<RSAPublicKeySpec>> getRSAPubKey();

    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param checkCode
     * @return
     */
    @POST(UserVoConstant.DUIMY_REGISTER)
    Observable<UserVoRespMsg<String>> registar(@Query("phone") String phone, @Query("password") String password, @Query("checkCode") String checkCode, @Query("phoneAreaCode") String AreaCode);

    /**
     * 判断该用户手机号是否已注册,没有注册则可注册
     *
     * @param loginName
     * @return
     */
    @POST(UserVoConstant.DUIMY_ISREGISTERED)
    Observable<UserVoRespMsg<String>> isRegistered(@Query("loginName") String loginName);

    /**
     * 通知服务器发送短信验证码
     *
     * @param phone
     * @return
     */
    @POST(UserVoConstant.DUIMY_SENDCHECKCODE)
    Observable<UserVoRespMsg<String>> sendCheckCode(@Query("phone") String phone);

    /**
     * 查找好友接口
     *
     * @param userNames : 对面ID数组
     * @return UserVoRespMsg
     */
    @POST(UserVoConstant.DUIMY_FINDUSERVOSBYUSERNAMES)
    Observable<UserVoRespMsg<List<UserVo>>> findUserVosByUserNames(@Query("userNames") String[] userNames);

    /**
     * 查找好友接口
     *
     * ### 参数
     * mask : [可选]是否需要对敏感字段进行部分隐藏(默认true(隐藏))
     * @param loginName : 手机号/邮箱/对面ID
     * @return UserVoRespMsg
     */
    @POST(UserVoConstant.DUIMY_FINDUSERVOBYLOGINNAME)
    Observable<UserVoRespMsg<UserVo>> findUserVoByLoginName(@Query("loginName") String loginName);

    /**
     * 加载好友列表
     *
     * @param loginName
     * @return
     */
    @POST(UserVoConstant.DUIMY_LOADFRIENDS)
    Observable<UserVoRespMsg<List<UserVo>>> loadFriends(@Query("loginName") String loginName);

    /**
     * 查询个人资料
     *
     * @param loginName
     * @return
     */
    @POST(UserVoConstant.DUIMY_FINDSPECIFIEDFIELDSBYLOGINNAME)
    Observable<UserVoRespMsg<UserVo>> findSpecifiedFieldsByLoginName(@Query("loginName") String loginName);

    /**
     * 上传用户头像
     *
     * @param imageFile
     * @return
     */
    @Multipart
    @POST(UserVoConstant.DUIMY_UPLOADHEADIMG)
    Observable<UserVoRespMsg<UserVo>> uploadHeadImg(@Query("loginName") String loginName, @Part("file\"; filename=\"avatar.png") RequestBody imageFile);

    /**
     * 更新用户性别
     * @param loginName
     * @param sex
     * @return
     */
    @POST(UserVoConstant.DUIMY_UPDATESEX)
    Observable<UserVoRespMsg<String>> updateSex(@Query("loginName") String loginName, @Query("sex") int sex);

    /**
     * 发送 忘记密码 验证码
     * @param phone
     * @return
     */
    @POST(UserVoConstant.DUIMY_SENDFORGETPSWCODE)
    Observable<UserVoRespMsg<String>> sendForgetPswCode(@Query("phone") String phone);

    /**
     * 验证短信验证码
     * @param phone
     * @param checkCode
     * @return
     */
    @POST(UserVoConstant.DUIMY_VALIDATESMSCODE)
    Observable<UserVoRespMsg<String>> validateSmsCode(@Query("phone") String phone, @Query("checkCode") String checkCode);

    /**
     * 重置密码
     * @param phone
     * @param newPassword
     * @return
     */
    @POST(UserVoConstant.DUIMY_RESETPSW)
    Observable<UserVoRespMsg<String>> resetPsw(@Query("phone") String phone, @Query("newPassword") String newPassword);

}
