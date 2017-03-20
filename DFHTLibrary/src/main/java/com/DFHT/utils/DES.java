package com.DFHT.utils;

import android.util.Base64;

import com.DFHT.ENConstanValue;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 创建者：Mcablylx
 * 时间：2015/12/7 14:14
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class DES {

    /**
     * 加密
     * @param message 要加密的内容
     * @param key   加密用的密匙
     * @return
     * @throws Exception
     */
    public static String encrypt(String message, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(ENConstanValue.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes(ENConstanValue.UTF_8));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return Base64.encodeToString(cipher.doFinal(message.getBytes(ENConstanValue.UTF_8)), Base64.DEFAULT);
    }
}