package com.chivox.utils;

import android.content.Context;

import com.DFHT.utils.JSONUtils;
import com.DFHT.voiceengine.EngOkCallBack;
import com.chivox.android.AIRecorder;
import com.chivox.bean.AIParam;
import com.chivox.bean.AIParamLocal;
import com.chivox.callback.impl.MyAIRecorderCallbackImpl;

import java.util.Map;

/**
 * Created by Mckiera on 2015/12/14.
 */
public class ChivoxUtil {
   /* private Context mContext;
    private String userId;
    private AIRecorder recorder;
    private long engine;

    public ChivoxUtil(Context mContext, String userId) {
        this.mContext = mContext;
        this.userId = userId;
        recorder = ChivoxCreateUtil.recorder;
        engine = ChivoxCreateUtil.engine;
    }

    *//**
     * @param content 文本内容
     * @param path    声音文件的地址
     * @return
     *//*
    public int start(String content, String path, String sentenceID, int position, Map<String, Object> holdObj, EngOkCallBack callBack) {
        content = AiUtil.replaceStr(content);
        content = AiUtil.formatReftext(content);
        content = AiUtil.addBlank(content);
      //  content = "one(large) bowl of...";
        String json = "";
        switch (ChivoxCreateUtil.getType()) {
            case TYPE_ONLINE:
                AIParam goAiParam_on = new AIParam();
                goAiParam_on.getRequest().setRefText(content);
                goAiParam_on.getRequest().setCoreType(AiUtil.judgeEvaluateType(content));
                goAiParam_on.getApp().setUserId(userId);
                json = JSONUtils.getJson(goAiParam_on);
                break;
            case TYPE_OFFLINE:
                AIParamLocal goAiParam_off = new AIParamLocal();
                goAiParam_off.getRequest().setRefText(content);
                goAiParam_off.getRequest().setCoreType(AiUtil.judgeEvaluateType(content));
                goAiParam_off.setSerialNumber(ChivoxCreateUtil.serialNumber);
                goAiParam_off.getApp().setUserId(userId);
                json = JSONUtils.getJson(goAiParam_off);
                break;
            default:
                return -1;
        }
        ChivoxCreateUtil.notifyChanged();
        holdObj.put("json",json);
        return recorder.start(path, new MyAIRecorderCallbackImpl(mContext, content, json, path, sentenceID, position, holdObj, callBack));
    }


    public int stop() {
        if (recorder != null)
            return recorder.stop();
        return 0;
    }*/
}
