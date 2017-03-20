package com.DFHT;

import android.os.Environment;

import com.DFHT.manager.KeyManager;

import java.io.File;

/**
 * Created by kiera1 on 15/9/18.
 */
public interface ENConstanValue {
    /* UTF-8 */
    String UTF_8 = "UTF-8";

//    String DES_KEY = "dfht@dsy";
    String DES_KEY = KeyManager.getDESKEY();

    /**
     * 插件文件位置
     */
    String PLUGIN_FILE = Environment.getExternalStorageDirectory() + File.separator + "bandu_plugin.apk";

    class ENReceiver{
        public static final String NET_NONET = "net_nonet";
        public static final String NET_LOADING = "net_loading";
        public static final String NET_HTTP_OK = "net_http_ok";
    }
}
