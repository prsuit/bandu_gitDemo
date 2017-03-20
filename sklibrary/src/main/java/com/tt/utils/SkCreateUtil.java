package com.tt.utils;

import android.util.Log;

import com.DFHT.utils.UIUtils;
import com.tt.AIRecorder;
import com.tt.AiUtil;
import com.tt.SkConstants;
import com.tt.SkEgn;
import com.tt.callback.OnSpeechEngineLoaded;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 创建者：Mckiera
 * <p>时间：2016-07-20  14:52
 * <p>类描述：创建引擎工具类
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class SkCreateUtil {
    private static AIRecorder recorder = null;
    private static long engine = 0;
    private static String currentEngine = null;
    private static String serverType = "cloud";
    private static String coreType = "en.sent.score";
    final private static String cloudprovision = "skegn.provision";

    //    JSONObject cfg = null;
    JSONObject params = null;

    public static boolean onLineCreateOk = false;

    public static void createEnginAndAIRecorder(OnSpeechEngineLoaded l) {
        createOnLine(l);
    }

    /*
    这里是初始化 引擎的地方.
     */
    private static void createOnLine(OnSpeechEngineLoaded l) {
        if (onLineCreateOk) {
            l.onLoadSuccess(OnSpeechEngineLoaded.ONLINE_OK);
            return;
        }
        if (currentEngine == null || !currentEngine.equals(serverType)) {

            /* 初始化cfg */
            JSONObject cfg = new JSONObject();
            try {
                cfg.put("prof", new JSONObject("{\"enable\": 1,\"output\":\"\"}"));
                cfg.put("appKey", SkConstants.appkey);
                cfg.put("secretKey", SkConstants.secretkey);
                InputStream is = null;
                if (serverType.equals("native")) {
                } else {
                    cfg.put("cloud", new JSONObject("{\"server\": \"" + SkConstants.cloudServer
                            + "\", serverList:\"\"}"));
                    is = UIUtils.getContext().getAssets().open(cloudprovision);
                }
                File provisionFile = new File(
                        AiUtil.externalFilesDir(UIUtils.getContext()),
                        "skegn.provision");
                AiUtil.writeToFile(provisionFile, is);
                is.close();
                cfg.put("provision", provisionFile.getAbsolutePath());
                System.out.println(cfg.toString());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //上面是封装参数.  这里是  调用skegn_new  获得引擎.
            engine = SkEgn.skegn_new(cfg.toString(), UIUtils.getContext());
            if (engine != 0) {
                currentEngine = new String(serverType);
                onLineCreateOk = true;
                recorder = AIRecorder.getInstance();
                l.onLoadSuccess(OnSpeechEngineLoaded.ONLINE_OK);
                Log.i("FUCK", "第一步初始化sk engine == " + engine);
                //这里就第一步结束了.
            } else {
//                setResult("create engine failed");
                l.onLoadError();
            }
        }

    }
}
