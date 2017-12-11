package com.example.dmuser.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "UserVo", indices = {@Index(value = "_userName", unique = true)})
public class UserVo implements Serializable {
    /**
     * 用户ID
     */
    public static final String USERVO_ID = "id";
    /**
     * 用户名
     */
    public static final String USERVO_USERNAME = "userName";
    /**
     * 昵称
     */
    public static final String USERVO_NICKNAME = "nickName";
    /**
     * 密码
     */
    public static final String USERVO_PASSWORD = "password";
    /**
     * 盐值
     */
    public static final String USERVO_SALT = "salt";
    /**
     * 真实姓名
     */
    public static final String USERVO_REALNAME = "realName";
    /**
     * 头像图片路径
     */
    public static final String USERVO_HEADIMGPATH = "headImgPath";
    /**
     * 性别(0,未知;1,男;2,女)
     */
    public static final String USERVO_SEX = "sex";
    /**
     * 地址
     */
    public static final String USERVO_ADDRESS = "address";

    /**
     * 性取向(1,异性恋;2,同性恋;3,双性恋)
     */
    public static final String USERVO_SEXUALORIENTATION = "sexualOrientation";
    /**
     * 个性签名
     */
    public static final String USERVO_PERSONALSIGNATURE = "personalSignature";
    /**
     * 好友数
     */
    public static final String USERVO_FRIENDS = "friends";
    /**
     * 关注数
     */
    public static final String USERVO_ATTENTIONS = "attentions";
    /**
     * 微博数
     */
    public static final String USERVO_WEIBOS = "weibos";
    /**
     * 粉丝数
     */
    public static final String USERVO_FANS = "fans";
    /**
     * 粉丝数
     */
    public static final String USERVO_PHONE_AREA_CODE = "phoneAreaCode";

    /**
     * tb_user
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;            // 用户ID
    @ColumnInfo(name = "_userName")
    private String userName;    // 用户名
    @ColumnInfo(name = "_nickName")
    private String nickName;    // 昵称
    @ColumnInfo(name = "_password")
    private String password;    // 密码
    @ColumnInfo(name = "_salt")
    private String salt;        // 盐值
    @ColumnInfo(name = "_realName")
    private String realName;    // 真实姓名
    @ColumnInfo(name = "_headImgPath")
    private String headImgPath;    // 头像图片路径
    @ColumnInfo(name = "_imgState")
    private boolean imgState;    // 头像图片是否下载
    @ColumnInfo(name = "_sex")
    private Integer sex;        // 性别(0,未知;1,男;2,女)
    @ColumnInfo(name = "_address")
    private String address;        // 地址


    @ColumnInfo(name = "_headLocalPath")
    private String headLocalPath; // 头像本地路径

    @ColumnInfo(name = "_sexualOrientation")
    private Integer sexualOrientation;    // 性取向(1,异性恋;2,同性恋;3,双性恋)
    @ColumnInfo(name = "_personalSignature")
    private String personalSignature;    // 个性签名
//    @ColumnInfo(name = "_friends")
//    private String friends;            // 好友数
    @ColumnInfo(name = "_attentions")
    private int attentions;            // 关注数
    @ColumnInfo(name = "_weibos")
    private int weibos;                // 微博数
    @ColumnInfo(name = "_fans")
    private int fans;                // 粉丝数

    private String labels;        // 个人标签集(以逗号分隔)

    public UserVo() {
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getHeadImgPath() {
        return headImgPath;
    }

    public void setHeadImgPath(String headImgPath) {
        this.headImgPath = headImgPath;
        this.headLocalPath = null;
        this.imgState = false;
    }

    public boolean isImgState() {
        return imgState;
    }

    public void setImgState(boolean imgState) {
        this.imgState = imgState;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSexualOrientation() {
        return sexualOrientation;
    }

    public void setSexualOrientation(Integer sexualOrientation) {
        this.sexualOrientation = sexualOrientation;
    }

    public String getPersonalSignature() {
        return personalSignature;
    }

    public void setPersonalSignature(String personalSignature) {
        this.personalSignature = personalSignature;
    }

//    public String getFriends() {
//        return friends;
//    }
//
//    public void setFriends(String friends) {
//        this.friends = friends;
//    }

    public int getAttentions() {
        return attentions;
    }

    public void setAttentions(int attentions) {
        this.attentions = attentions;
    }

    public int getWeibos() {
        return weibos;
    }

    public void setWeibos(int weibos) {
        this.weibos = weibos;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public String getHeadLocalPath() {
        return headLocalPath;
    }

    public void setHeadLocalPath(String headLocalPath) {
        this.headLocalPath = headLocalPath;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", realName='" + realName + '\'' +
                ", headImgPath='" + headImgPath + '\'' +
                ", headLocalPath='" + headLocalPath + '\'' +
                ", imgState='" + imgState + '\'' +
                ", sex=" + sex +
                ", address='" + address + '\'' +
                ", sexualOrientation=" + sexualOrientation +
                ", personalSignature='" + personalSignature + '\'' +
                ", attentions=" + attentions +
                ", weibos=" + weibos +
                ", fans=" + fans +
                ", labels='" + labels + '\'' +
                '}';
    }

}
