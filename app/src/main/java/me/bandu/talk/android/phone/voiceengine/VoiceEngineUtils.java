package me.bandu.talk.android.phone.voiceengine;

import android.content.Context;
import android.util.Log;

import com.DFHT.voiceengine.EngOkCallBack;
import com.DFHT.voiceengine.EngineType;
import com.chivox.callback.impl.MyAIRecorderCallbackImpl;
import com.tt.SkConstants;
import com.tt.SkEgn;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 创建者：Mckiera
 * <p>时间：2016-08-19  16:55
 * <p>类描述：语音评测 工具类
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class VoiceEngineUtils {
    private Context mContext;
    private String userId;
//    private com.tt.AIRecorder recorder_sk;
    private com.chivox.android.AIRecorder recorder_chivox;
    private long engine;

    public VoiceEngineUtils(Context mContext, String userId) {
        this.mContext = mContext;
        this.userId = userId;
//        recorder_sk = VoiceEngCreateUtils.recorder_sk;
        recorder_chivox = VoiceEngCreateUtils.recorder_chivox;
        engine = VoiceEngCreateUtils.engine;
    }

    /**
     * @param content 文本内容
     * @param path    声音文件的地址
     * @return
     */
    public int start(String content, String path, String sentenceID, int position, Map<String, Object> holdObj, EngOkCallBack callBack) {
        content = com.chivox.utils.AiUtil.replaceStr(content);
        content = com.chivox.utils.AiUtil.formatReftext(content);
        content = com.chivox.utils.AiUtil.addBlank(content);
        //  content = "one(large) bowl of...";
        String json = "";
        EngineType type = VoiceEngCreateUtils.getType();
        Log.e("type", "type:-- "+type);
        switch (type) {
            case TYPE_CHIVOX://这里是驰声的
//                AIParam goAiParam_on = new AIParam();
//                goAiParam_on.getRequest().setRefText(content);
//                goAiParam_on.getRequest().setCoreType(com.chivox.utils.AiUtil.judgeEvaluateType(content));
//                goAiParam_on.getApp().setUserId(userId);
//                json = JSONUtils.getJson(goAiParam_on);
                JSONObject params = new JSONObject();
                try {
                    JSONObject audio = new JSONObject(
                            "{\"audioType\": \"wav\",\"sampleBytes\": 2,\"sampleRate\": 16000,\"channel\": 1}");
                    params.put("app", new JSONObject(
                            "{\"userId\":\""+userId+"\"}"));
                    params.put("coreProvideType", "cloud"); //这里给的是cloud   native的我给删除了.
                    JSONObject request = new JSONObject();
                    request.put("attachAudioUrl",1);
                    request.put("rank", 100);
                    request.put("coreType", com.chivox.utils.AiUtil.judgeEvaluateType(content));
                    request.put("refText", content);
                    params.put("audio", audio);
                    params.put("request", request);
                    params.put("vadEnable",0);
                    params.put("volumeEnable",0);
                    System.out.println(params.toString());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                json = params.toString();

                holdObj.put("json",json);
                return recorder_chivox.start(path, new MyAIRecorderCallbackImpl(mContext, content, json, path, sentenceID, position, holdObj, callBack));
            case TYPE_SK://这里是你们的
                JSONObject params1 = new JSONObject();
                try {
                    JSONObject audio = new JSONObject(
                            "{\"audioType\": \"wav\",\"sampleBytes\": 2,\"sampleRate\": 16000,\"channel\": 1,\"compress\": \"speex\"}");
                    params1.put("app", new JSONObject(
                            "{\"userId\":\""+userId+"\"}"));
                    params1.put("coreProvideType", "cloud"); //这里给的是cloud   native的我给删除了.
                    JSONObject request = new JSONObject();
                    request.put("rank", 100);
                    request.put("coreType", "en.sent.score");
//                    request.put("coreType", com.chivox.utils.AiUtil.judgeEvaluateType(content));
                    request.put("type", "readaloud");
                    request.put("attachAudioUrl",1);
                    request.put("refText", content);
                    params1.put("audio", audio);
                    params1.put("request", request);
                    System.out.println(params1.toString());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                json = params1.toString();
                holdObj.put("json",json);
                com.tt.callback.impl.MyAIRecorderCallbackImpl imp =  new com.tt.callback.impl.MyAIRecorderCallbackImpl(mContext, content, json, path, sentenceID, position, holdObj, callBack);
                int start = imp.start();
                if(start != 0)
                    return -1;
                int start1 = com.tt.AIRecorder.getInstance().start(path, imp);
                Log.i("FUCK", "第四步recorder调用start start1 == "+ start1);
                return start1;
           /* case TYPE_XS:  //先声
                try {
                    JSONObject request = new JSONObject();
                    request.put("coreType","en.sent.score");
                    request.put("refText",content);
                    request.put("rank", 100);
                    //构建评测请求参数
                    JSONObject startCfg = VoiceEngCreateUtils.xsEngine.buildStartJson(userId,request);
                    //设置评测请求参数
                    VoiceEngCreateUtils.xsEngine.setStartCfg(startCfg);
                    //开始测评
                    VoiceEngCreateUtils.xsEngine.start();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
        }
        return -1;
    }

    public static void start_XS(String content, String userId) {
        try {
            JSONObject request = new JSONObject();
            request.put("coreType","en.sent.score");
            request.put("refText",content);
            request.put("rank", 100);
            //构建评测请求参数
            JSONObject startCfg = VoiceEngCreateUtils.xsEngine.buildStartJson(userId,request);
            //设置评测请求参数
            VoiceEngCreateUtils.xsEngine.setStartCfg(startCfg);
            //开始测评
            VoiceEngCreateUtils.xsEngine.start();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int stop() {
        if (recorder_chivox != null)
            return recorder_chivox.stop();
        if(com.tt.AIRecorder.getInstance() != null){
            com.tt.AIRecorder.getInstance().stop();
            return SkEgn.skegn_stop(SkConstants.engine);
        }
        return 0;
    }

}
