package me.bandu.talk.android.phone.voiceengine;

import android.content.Context;
import android.util.Log;

import com.DFHT.utils.UIUtils;
import com.DFHT.voiceengine.EngineType;
import com.DFHT.voiceengine.OnSpeechEngineLoaded;
import com.chivox.AIEngine;
import com.chivox.ChivoxConstants;
import com.chivox.utils.AIEngineHelper;
import com.tt.SkConstants;
import com.tt.SkEgn;
import com.xs.SingEngine;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 创建者：Mckiera
 * <p>时间：2016-08-24  14:00
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class VoiceEngCreateUtils {

    public static final int ENGINE_CHIVOX = 1;
    public static final int ENGINE_SK = 0;

    public static long engine = 0;
    public static com.chivox.android.AIRecorder recorder_chivox = null;
//    public static com.tt.AIRecorder recorder_sk = null;

    public static String serialNumber = "";

    public static boolean chivoxCreateOk = false;
    public static boolean skCreateOk = false;

    private static String currentEngine = null;
    private static String serverType = "cloud";
    private static String coreType = "en.sent.score";
    final private static String cloudprovision = "skegn.provision";
    JSONObject params = null;

    //创建先声引擎
    public static SingEngine xsEngine;
    private static final String XSENGINE_APPKEY = "6faf5c95eb99cc70";
    private static final String XSENGINE_SECRETKEY = "dddc9d9a7d2df8634808cea282893961";
    public static boolean xsCreateOk = false;

    public static void createXSEngine(final Context mContext, final SingEngine.ResultListener listener){
//        Thread thread = new Thread(){
//           @Override
//            public void run(){
               try {
                   //获取引擎
                   xsEngine = SingEngine.newInstance(mContext);
                   xsEngine.setListener(listener);
                   //引擎类型
                   xsEngine.setServerType("cloud");
                   //是否开启vad功能 开启后， 录音一段时间后不说话 自动调用引擎stop()，结束录音并返回评测结果
                   //引擎默认关闭vad功能
                   //xsEngine.setOpenVad(false,null);
                   JSONObject cfg_init = xsEngine.buildInitJson(XSENGINE_APPKEY,XSENGINE_SECRETKEY);
                   //   设置引擎初始化参数
                   xsEngine.setNewCfg(cfg_init);
                   //   引擎初始化
                   xsEngine.newEngine();
                   xsCreateOk = true;
               } catch (Exception e) {
                   e.printStackTrace();
               }

    }
//        };
//        thread.start();



    public static void createEnginAndAIRecorder(OnSpeechEngineLoaded l) {
        // createChivoEng(l);
//        createEnginAndAIRecorder(l, ENGINE_CHIVOX);
        createEnginAndAIRecorder(l, ENGINE_SK);//3.4.4将驰声改成sk
    }

    public static void createEnginAndAIRecorder(OnSpeechEngineLoaded l, int engine) {
        switch (engine) {
            case ENGINE_CHIVOX:
                createChivoEng(l);
                break;
            case ENGINE_SK:
                createSkEng(l);
                break;
        }
//        createChivoEng(l);
//        createSkEng(l);
    }

    /*
    这里是初始化 引擎的地方.
     */
    private static void createSkEng(OnSpeechEngineLoaded l) {
/*        if (skCreateOk) {
            l.onLoadSuccess(OnSpeechEngineLoaded.ONLINE_OK);
            return;
        }*/

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
                    com.tt.AiUtil.externalFilesDir(UIUtils.getContext()),
                    "skegn.provision");
            com.tt.AiUtil.writeToFile(provisionFile, is);
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
        engine = 0;
        SkConstants.engine = 0;
        //这句话报错 ↓
            /*
            {
                "appKey": "146907327000000a",
                "cloud": {
                    "server": "ws://api.17kouyu.com:8080",
                    "serverList": ""
            },
            "prof": {
                "enable": 1,
                "output": ""
            },
            "provision": "/storage/emulated/0/Android/data/me.bandu.talk.android.phone/files/skegn.provision",
            "secretKey": "ec0f50b64e088888841d0e385792f06e"
            }
             */
        engine = SkEgn.skegn_new(cfg.toString(), UIUtils.getContext());
        if (engine != 0) {
            currentEngine = new String(serverType);
            SkConstants.engine = engine;
            skCreateOk = true;
//                recorder_sk = com.tt.AIRecorder.getInstance();
            l.onLoadSuccess(OnSpeechEngineLoaded.ONLINE_OK);
            Log.i("FUCK", "第一步初始化sk engine == " + engine);
            //这里就第一步结束了.
        } else {
//                setResult("create engine failed");
            skCreateOk = false;
            //l.onLoadError();
            createChivoEng(l);
        }

    }


//=========================================================================================================





   /* public static Map<String, Object> getEnginAndAIRecorder(EnginType type){
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
    }*/


/*    public static long getEngineCur(){
        return engine;
    }

    public static AIRecorder getRecorderCur(){
        return recorder;
    }

    public static String getSerialNumberCur(){
        return serialNumber;
    }*/

    /**
     * 创建离线引擎
     * @param l
     */
    /*private static void createOffLine(final OnSpeechEngineLoaded l) {
        if(offLineCreateOk){
            createOnLine(l);
            return;
        }



    *//*    Intent intent = new Intent();
        intent.putExtra("content", "加载作业");
        intent.setClass(this, LoadingActivity.class);
        this.startActivity(intent);*//*
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
                *//*if(GlobalParams.loginInfo != null){
                    userID = GlobalParams.loginInfo.getData().getUserInfo().getUserId();
                }*//*
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
    }*/

    /**
     * 创建在线引擎.
     *
     * @param l
     */
    private static void createChivoEng(OnSpeechEngineLoaded l) {
 /*       if (chivoxCreateOk) {
            l.onLoadSuccess(OnSpeechEngineLoaded.ONLINE_OK);
            return;
        }*/
        //创建在线..
        String provisionPath = AIEngineHelper.extractResourceOnce(UIUtils.getContext(), "aiengine.provision", false);
        AIEngineHelper.extractResourceOnce(UIUtils.getContext(), "vad.0.10.bin", false);
        String cfg = String.format("{\"appKey\": \"%s\", \"secretKey\": \"%s\", \"provision\": \"%s\", \"cloud\": {\"server\": \"ws://cloud.chivox.com:8080\"}}", ChivoxConstants.appKey, ChivoxConstants.secretKey, provisionPath);
        engine = 0;
        ChivoxConstants.engine = 0;
        try {
            engine = AIEngine.aiengine_new(cfg, UIUtils.getContext());
        } catch (Exception e) {
            l.onLoadError();
            notifyChanged();
            return;
        }
        if (recorder_chivox == null) {
            recorder_chivox = new com.chivox.android.AIRecorder();
            ChivoxConstants.recorder = recorder_chivox;
        }
        if (engine != 0) {
            chivoxCreateOk = true;
            ChivoxConstants.engine = engine;
            l.onLoadSuccess(OnSpeechEngineLoaded.ONLINE_OK);
        } else {
            chivoxCreateOk = false;
            l.onLoadError();
        }
        notifyChanged();
    }


    public static void notifyChanged() {
        // engine = onLineCreateOk ? engine_online : engine_offline;
        // recorder = recorder_online == null ? recorder_offline : recorder_online;
        // serialNumber = TextUtils.isEmpty(serialNumber_online) ? serialNumber_offline : serialNumber_online;
    }


    public static void deleteEngineAndRecorder() {
        if (engine != 0) {
            if (chivoxCreateOk) {
                AIEngine.aiengine_delete(engine);
                ChivoxConstants.engine = 0;
            }
            if (skCreateOk) {
                SkEgn.skegn_delete(engine);
                SkConstants.engine = 0;
            }
            engine = 0;
        }

        if (recorder_chivox != null) {
            recorder_chivox.stop();
            recorder_chivox = null;
        }
        if (com.tt.AIRecorder.getInstance() != null) {
            com.tt.AIRecorder.getInstance().stop();
        }
    }

    public static EngineType getType() {
        if (chivoxCreateOk)
            return EngineType.TYPE_CHIVOX;
        if (skCreateOk)
            return EngineType.TYPE_SK;
        if(xsCreateOk)
            return  EngineType.TYPE_XS;
        return EngineType.TYPE_NULL;
    }
}
