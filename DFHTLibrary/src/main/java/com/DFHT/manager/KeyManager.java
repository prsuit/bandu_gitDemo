package com.DFHT.manager;

/**
 * Created by Mckiera on 2016-04-08.
 */
public class KeyManager {
    static {
        System.loadLibrary("dfhtprocess");
    }

    public static native String getDESKEY();
    public static native String getAppKey();
    public static native String getSalt();
//    public static native String getSecretKey();
    public static native String getKeyData(String sign, String time, String salt);

}
