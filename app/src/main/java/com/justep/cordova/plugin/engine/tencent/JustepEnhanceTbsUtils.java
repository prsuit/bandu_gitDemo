package com.justep.cordova.plugin.engine.tencent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/*import com.tencent.smtt.export.external.DexLoader;*/

/**
 * Created by 007slm on 2016-9-6.
 * email: 007slm@163.com
 */
public class JustepEnhanceTbsUtils {

    /*public static Uri[] parseResult(int paramInt, Intent paramIntent) {
        try {
            j var2 = j.a(false);
            if (null != var2 && var2.b()) {
                DexLoader var3 = var2.a().o();
                Object paramIntent2 = var3.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "parseFileChooserResult", new Class[]{Integer.TYPE, Intent.class}, new Object[]{Integer.valueOf(paramInt), paramIntent});
                if (paramIntent2 == null) {
                    return null;
                }
                return (Uri[]) paramIntent2;
            } else {
                Object paramIntent2 = com.tencent.smtt.utils.m.a(Class.forName("com.android.webview.chromium.FileChooserParamsAdapter"), "parseFileChooserResult", new Class[]{Integer.TYPE, Intent.class}, new Object[]{Integer.valueOf(paramInt), paramIntent});
                if (paramIntent2 == null) {
                    return null;
                }
                return (Uri[]) paramIntent2;
            }
        } catch (Exception e) {
        }
        return null;
    }*/

    public static Uri[] parseFileChooserResult(int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return null;
        }
        Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
        Uri[] uris = null;
        if (result != null) {
            uris = new Uri[1];
            uris[0] = result;
        }
        return uris;
    }
}
