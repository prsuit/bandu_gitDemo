package com.tt.utils;

import android.content.Context;
import android.util.Log;

import com.DFHT.voiceengine.EngOkCallBack;
import com.tt.AIRecorder;
import com.tt.AiUtil;
import com.tt.SkConstants;
import com.tt.SkEgn;
import com.tt.callback.impl.MyAIRecorderCallbackImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 创建者：Mckiera
 * <p>时间：2016-07-20  15:44
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class SkUtil {
    private Context mContext;
    private String userId;
//    private AIRecorder recorder;
    private long engine;

    public SkUtil(Context mContext, String userId) {
        this.mContext = mContext;
        this.userId = userId;
//        recorder = SkConstants.recorder;
        engine = SkConstants.engine;
    }

    /**
     * @param content 文本内容
     * @param path    声音文件的地址
     * @return
     */
    //这里是第二步
    public int start(String content, String path, String sentenceID, int position, Map<String, Object> holdObj, EngOkCallBack callBack) {
        content = AiUtil.replaceStr(content);
        content = AiUtil.formatReftext(content);
        content = AiUtil.addBlank(content);
        String json = "";
        //开始封装那个 文本参数.
        JSONObject params = new JSONObject();
        try {
            JSONObject audio = new JSONObject(
                    "{\"audioType\": \"wav\",\"sampleBytes\": 2,\"sampleRate\": 16000,\"channel\": 1,\"compress\": \"speex\"}");
            params.put("app", new JSONObject(
                    "{\"userId\":\"test.tt.com\"}"));
            params.put("coreProvideType", "cloud"); //这里给的是cloud   native的我给删除了.
            JSONObject request = new JSONObject();
            request.put("rank", 100);
            request.put("coreType", AiUtil.judgeEvaluateType(content));
            request.put("type", "readaloud");
            request.put("refText", content);
            params.put("audio", audio);
            params.put("request", request);
            System.out.println(params.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        json = params.toString();
        holdObj.put("json", json);
        Log.i("FUCK", "第二步文本参数 params == " + json);
        //第二步结束.  封装参数  params
        MyAIRecorderCallbackImpl myAIRecorderCallback = new MyAIRecorderCallbackImpl(mContext, content, json, path, sentenceID, position, holdObj, callBack);
        int start = myAIRecorderCallback.start();//第三步在这个start方法里面
        if (start != 0)
            return -1;
        int start1 = com.tt.AIRecorder.getInstance().start(path, myAIRecorderCallback);
//        int start1 = recorder.start(path, new AIRecorder.Callback() {
//            public void run(byte[] data, int size) {
//                SkEgn.skegn_feed(SkCreateUtil.engine, data, size);
//            }
//        });
        Log.i("FUCK", "第四步recorder调用start start1 == " + start1);
        return start1;
    }

    public int stop() {
        if (com.tt.AIRecorder.getInstance() != null) {
            com.tt.AIRecorder.getInstance().stop();
            return SkEgn.skegn_stop(SkConstants.engine);
        }
        return 0;
    }
}
