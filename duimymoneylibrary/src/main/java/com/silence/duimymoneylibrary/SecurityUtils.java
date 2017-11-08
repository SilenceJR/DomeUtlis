package com.silence.duimymoneylibrary;


import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtils {
    /**
     * AES 加密<br>
     * 说明: <br>
     * 	加密模式: CBC <br>
     * 	填充: pkcs5padding <br>
     *  数据块: 128位 <br>
     *  密码和偏移量: 都是密钥 <br>
     * @param msg  	要加密的字符串
     * @param key	私钥
     * @return
     */
    public static String aesEnc(String msg, String key){

        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        SecretKeySpec sKey = new SecretKeySpec(key.getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,sKey,iv );
            byte[] encryptedData = cipher.doFinal(msg.getBytes("utf-8"));
            return new String(Base64.encode(encryptedData));
        } catch (Exception e) {
            e.printStackTrace();
            throw new SecurityException("AES 加密失败");
        }
    }

    /**
     * AES 解密<br>
     * 说明: <br>
     * 填充: pkcs5padding <br>
     * 密码和偏移量: 都是密钥 <br>
     * @param encrypted
     * @param key
     * @return
     */
    public static String aesDec(String encrypted, String key){
        try {
            byte[] encBytes = Base64.decode(encrypted);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());
            SecretKeySpec sKey = new SecretKeySpec(key.getBytes(),"AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, sKey, iv);
            byte[] decBytes = cipher.doFinal(encBytes);
            return new String(decBytes,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new SecurityException("AES 解密失败");
        }
    }

//    public static void main(String[] args) {
//
//        String randomStr = UUID.randomUUID().toString().substring(20);
//
//        String msg = "1234567";
//        String key = randomStr;
//        String encStr = aesEnc(msg,key);
//        System.out.println(encStr);
//
//        System.out.println("============");
//        String decStr = aesDec("xXVOnJHD4pJzxXc00eCEt3WQjJDqBC1EhjBXJzjcsHA=","811-dc9e0ff20144");
//        System.out.println(decStr);
//
//        String aa = "abcdefghijklmnop";
//        System.out.println(aa.substring(6));
//    }


}
