package com.DFHT.plugin.manager;

import android.os.Environment;

import com.DFHT.ENConstanValue;

import java.io.File;

/**
 * Created by Mckiera on 2016-03-31.
 */
public class PluginManager {
    /**
     * 检查插件文件是否存在
     * @return 存在  或者  不存在
     */
    public static boolean exFile() {
        File file = new File(ENConstanValue.PLUGIN_FILE);
        return file.exists();
    }

    
}
