package com.silence.androidmvprxjavadome.utils;

import android.text.TextUtils;

import com.silence.androidmvprxjavadome.model.UserVo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.text.TextUtils.isEmpty;

/**
 * @作者: PJ
 * @创建时间: 2017/11/8 / 10:59
 * @描述: 这是一个 StringUtil 类.
 */
public class StringUtil {

    /**
     * 字符串进行MD5加密, 用于Password
     * @param password
     * @return
     */
    public static String md5(String password) {
        if (TextUtils.isEmpty(password)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(password.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 字节数组转十六进制字符串
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF).toUpperCase();
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String getUserVoName(UserVo userVo) {
        return isEmpty(userVo.getNickName()) ? userVo.getUserName() : userVo.getNickName() ;
    }
}
