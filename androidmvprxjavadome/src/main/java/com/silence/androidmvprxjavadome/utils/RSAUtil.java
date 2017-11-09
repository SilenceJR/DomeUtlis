package com.silence.androidmvprxjavadome.utils;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

public class RSAUtil {

    public static RSAPublicKey generateRSAPublicKey(  RSAPublicKeySpec pubKeySpec ) throws Exception {
        KeyFactory keyFac = null;
        try {
            keyFac = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException ex) {
            throw new Exception(ex.getMessage());
        }

        try {
            return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
        } catch (InvalidKeySpecException ex) {
            throw new Exception(ex.getMessage());
        }
    }


    /** 
     * 加密
     * @param
     * @param data 
     */  
    public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
        try {  
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pk);
            int blockSize = cipher.getBlockSize();  

            int outputSize = cipher.getOutputSize(data.length);  
            int leavedSize = data.length % blockSize;  
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1  
                    : data.length / blockSize;  
            byte[] raw = new byte[outputSize * blocksSize];  
            int i = 0;  
            while (data.length - i * blockSize > 0) {  
                if (data.length - i * blockSize > blockSize)  
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);  
                else  
                    cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);  
                i++;  
            }  
            return raw;  
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }  
  

}