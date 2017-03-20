package com.chivox.utils;

import android.text.TextUtils;

import com.DFHT.net.NetworkUtil;
import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;
import com.chivox.AIEngine;
import com.chivox.ChivoxConstants;
import com.chivox.aimenu.EnginType;
import com.chivox.android.AIRecorder;
import com.DFHT.voiceengine.OnSpeechEngineLoaded;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.DFHT.utils.UIUtils.getContext;

/**
 * 创建者：Mcablylx
 * 时间：2015/12/7 17:48
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ChivoxCreateUtil {

    public static final int ENGINE_CHIVOX = 1;
    public static final int ENGINE_SK = 0;


    public static long engine_online = 0;
    public static AIRecorder recorder_online = null;
    public static String serialNumber_online = "";

    public static long engine_offline = 0;
    public static AIRecorder recorder_offline = null;
    public static String serialNumber_offline = "";

    private static long engine = 0;
    private static AIRecorder recorder = null;
    public static String serialNumber = "";

    public static boolean offLineCreateOk = false;
    public static boolean onLineCreateOk = false;



    public static Map<String, Object> getEnginAndAIRecorder(EnginType type){
        if(!onLineCreateOk || !offLineCreateOk)
            return null;
        Map<String, Object> data = new HashMap<>();
        switch (type){
            case TYPE_ONLINE:
                data.put("engine",engine_online);
                data.put("recorder", recorder_online);
                data.put("serialNumber", serialNumber_online);
                break;
            case TYPE_OFFLINE:
                data.put("engine",engine_offline);
                data.put("recorder", recorder_offline);
                data.put("serialNumber", serialNumber_offline);
                break;
            case TYPE_CURRENT:
                data.put("engine",engine);
                data.put("recorder", recorder);
                data.put("serialNumber", serialNumber);
                break;
        }
        return data;
    }
    /**
     * 使用VoiceEngCreateUtils.createEnginAndAIRecorder()方法
     * @param l
     */
    @Deprecated
    public static void createEnginAndAIRecorder(OnSpeechEngineLoaded l) {
//        createOffLine(l);
        createOnLine(l);
    }

    /**
     * 使用VoiceEngCreateUtils.createEnginAndAIRecorder()方法
     * @param l
     * @param engine
     */
    @Deprecated
    public static void createEnginAndAIRecorder(OnSpeechEngineLoaded l, int engine) {
//        createOffLine(l);
        createOnLine(l);
    }

    public static long getEngineCur(){
        return engine;
    }

    public static AIRecorder getRecorderCur(){
        return recorder;
    }

    public static String getSerialNumberCur(){
        return serialNumber;
    }

    /**
     * 创建离线引擎
     * 废弃方法
     * @param l
     */
    @Deprecated
    private static void createOffLine(final OnSpeechEngineLoaded l) {
        if(offLineCreateOk){
            createOnLine(l);
            return;
        }



    /*    Intent intent = new Intent();
        intent.putExtra("content", "加载作业");
        intent.setClass(this, LoadingActivity.class);
        this.startActivity(intent);*/
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                byte buf[] = new byte[1024];
                byte opt[] = new byte[1024];

                AIEngine.aiengine_opt(0, 1, opt, 1024);

//				Log.d(tag, "opt=" + new String(opt).trim());
                LogUtils.d( "opt=" + new String(opt).trim());

                AIEngine.aiengine_get_device_id( buf, getContext());
                String deviceId = new String(buf).trim();

                File resourceDir = AIEngineHelperLocal.extractResourceOnce(getContext(), "aiengine.resource.zip");
                File provisionFile = AIEngineHelperLocal.extractProvisionOnce(getContext(), "aiengine.provision");
                File vadResDir = null;
                if (ChivoxConstants.isVadEnable) {
                    vadResDir = AIEngineHelperLocal.extractResourceOnce(getContext(), "vad.zip");
                }
                //Log.d(tag, "resourceDir=" + resourceDir.getAbsolutePath());
//                LogUtils.d("resourceDir=" + resourceDir.getAbsolutePath());
                String userID = ChivoxConstants.userId;
                //TODO 根据登陆信息拿到userID
                /*if(GlobalParams.loginInfo != null){
                    userID = GlobalParams.loginInfo.getData().getUserInfo().getUserId();
                }*/
                serialNumber_offline = AIEngineHelperLocal.registerDeviceOnce(getContext(), ChivoxConstants.appKey, ChivoxConstants.secretKey, deviceId, userID);
//				Log.d(tag, "serialNumber=" + serialNumber);
                LogUtils.d("serialNumber_offline=" + serialNumber_offline);
                if (TextUtils.isEmpty(serialNumber_offline)) {
                    if (l != null) {
                        offLineCreateOk = false;
                        createOnLine(l);
                    }
                    return;
                }
                JSONObject engineParams = new JSONObject();
                try {
                    engineParams.put("appKey", ChivoxConstants.appKey);
                    engineParams.put("secretKey", ChivoxConstants.secretKey);
                    //engineParams.put("serialNumber", serialNumber);
                    engineParams.put("provision", provisionFile.getAbsolutePath());
                    if (ChivoxConstants.isDebug) {
                        String logFileName = "sdk." + new Date().getTime() + ".log";
                        String logPath = AIEngineHelperLocal.getFilesDir(getContext()).getPath()+ "/" + logFileName;
                        JSONObject profObject = new JSONObject();
                        profObject.put("enable", 1);
                        profObject.put("output", logPath);
                        engineParams.put("prof", profObject);
                    }

                    JSONObject nativeObject = new JSONObject();
                    nativeObject.put("enable", 1);
                    nativeObject.put("en.word.score", new JSONObject().put("resDirPath", new File(resourceDir, "bin/eng.wrd.g4.mb.0.3").getAbsolutePath()));
                    nativeObject.put("en.sent.score", new JSONObject().put("resDirPath", new File(resourceDir, "bin/eng.snt.g4.mb.0.3").getAbsolutePath()));
                    engineParams.put("native", nativeObject);

                    if (ChivoxConstants.isVadEnable) {
                        JSONObject vadObject = new JSONObject();
                        vadObject.put("enable", 1);
                        vadObject.put("res", new File(vadResDir, "vad.0.1.bin").getAbsolutePath());
                        vadObject.put("leftMargin", 60);
                        vadObject.put("rightMargin", 60);
                        vadObject.put("sampleRate", 16000);
                        engineParams.put("vad", vadObject);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String cfgString = engineParams.toString();
//				Log.d(tag, "cfgString=" + cfgString);
                LogUtils.d("off_cfgString=" + cfgString);
                engine_offline = AIEngine.aiengine_new(cfgString, getContext());
                //engine = AIEngine.aiengine_new(cfgString,getApplicationContext());
//				Log.d(tag, "engine created, " + engine);
                LogUtils.d("engine created, " + engine_offline);
                recorder_offline = new AIRecorder();
                //Log.d(tag, "recorder created");
                LogUtils.d("recorder created");
                if (l != null) {
                    offLineCreateOk = true;
                    createOnLine(l);
                }
            }
        });
        thread.start();
    }

    /**
     * 创建在线引擎.
     * @param l
     */
    private static void createOnLine(OnSpeechEngineLoaded l) {
        if(onLineCreateOk){
            l.onLoadSuccess(offLineCreateOk ? OnSpeechEngineLoaded.ALL_OK : OnSpeechEngineLoaded.ONLINE_OK);
            return;
        }
        //创建在线..
        String provisionPath = AIEngineHelper.extractResourceOnce(UIUtils.getContext(), "aiengine.provision", false);
        AIEngineHelper.extractResourceOnce(UIUtils.getContext(), "vad.0.10.bin", false);
        String cfg = String.format("{\"appKey\": \"%s\", \"secretKey\": \"%s\", \"provision\": \"%s\", \"cloud\": {\"server\": \"ws://cloud.chivox.com:8080\"}}", ChivoxConstants.appKey, ChivoxConstants.secretKey, provisionPath);
        try {
            engine_online = AIEngine.aiengine_new(cfg, UIUtils.getContext());
        }catch (Exception e){
            l.onLoadError();
            notifyChanged();
            return;
        }
        if (recorder_online == null)
            recorder_online = new AIRecorder();
        if(engine_online != 0){
            onLineCreateOk = true;
             l.onLoadSuccess(offLineCreateOk ? OnSpeechEngineLoaded.ALL_OK : OnSpeechEngineLoaded.ONLINE_OK);
        }else{
            onLineCreateOk = false;
            if(offLineCreateOk)
                l.onLoadSuccess(OnSpeechEngineLoaded.OFFLINE_OK);
            else
                l.onLoadError();
        }
        notifyChanged();
    }


    public static void notifyChanged(){
        engine = onLineCreateOk ?  engine_online : engine_offline;
        recorder = recorder_online == null ? recorder_offline : recorder_online;
        serialNumber = TextUtils.isEmpty(serialNumber_online) ? serialNumber_offline : serialNumber_online;
    }


    public static void deleteEngineAndRecorder() {
        if (engine != 0) {
            AIEngine.aiengine_delete(engine);
            engine = 0;
        }
        if(engine_online != 0){
            AIEngine.aiengine_delete(engine_online);
            engine_online = 0;
        }
        if(engine_offline != 0){
            AIEngine.aiengine_delete(engine_offline);
            engine_offline = 0;
        }

        if (recorder != null) {
            recorder.stop();
            recorder = null;
        }
        if (recorder_online != null) {
            recorder_online.stop();
            recorder_online = null;
        }
        if (recorder_offline != null) {
            recorder_offline.stop();
            recorder_offline = null;
        }
    }

    public static EnginType getType(){
        if(!NetworkUtil.checkNetwork(UIUtils.getContext())){
            onLineCreateOk = false;
        }
        if(onLineCreateOk)
            return EnginType.TYPE_ONLINE;
        if(offLineCreateOk)
            return EnginType.TYPE_OFFLINE;
        return EnginType.TYPE_NULL;
    }
}
