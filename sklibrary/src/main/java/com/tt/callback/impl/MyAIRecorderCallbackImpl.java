package com.tt.callback.impl;

import android.content.Context;
import android.util.Log;

import com.DFHT.base.AIRecorderDetails;
import com.DFHT.utils.JSONUtils;
import com.DFHT.utils.UIUtils;
import com.DFHT.voiceengine.EngOkCallBack;
import com.tt.SkConstants;
import com.tt.SkEgn;
import com.tt.bean.SkResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：Mckiera
 * <p>时间：2016-07-20  16:29
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class MyAIRecorderCallbackImpl implements com.tt.AIRecorder.Callback, SkEgn.skegn_callback {
    //这里实现了

    private Context mContext;
    private String jsonParam;
    private String wavPath;
    private String sentenceID;
    private int curPosition;
    private Map<String, Object> holderObj;
    private EngOkCallBack callBack;
    private String content;
    private String recordingId;
    private long waitEndTime;
    private long waitStartTime;

    /**
     * @param mContext   上下文
     * @param jsonParam  初始化参数
     * @param wavPath    语音文件位置
     * @param sentenceID 句子ID
     * @param position   当前的位置
     * @param holderObj  持有的obj
     * @param callBack
     */
    public MyAIRecorderCallbackImpl(Context mContext, String content, String jsonParam, String wavPath, String sentenceID, int position, Map<String, Object> holderObj, EngOkCallBack callBack) {
        this.mContext = mContext;
        this.jsonParam = jsonParam;
        this.wavPath = wavPath;
        this.sentenceID = sentenceID;
        this.curPosition = position;
        this.holderObj = holderObj;
        this.callBack = callBack;
        this.content = content;
    }

    //这里是 第三步   开始调用引擎的skegn_start 方法
    public int start() {
        byte[] id = new byte[64];
        //这个this  就是返回结果的那个回调SkEgn.skegn_callback.  但是不执行那个..
        final int rv = SkEgn.skegn_start(SkConstants.engine, jsonParam, id, this, mContext.getApplicationContext());
        Log.i("FUCK", "第三步skeng调用start  rv == " + rv);
        //skegn_start调用结束.   rv 一直是0
        recordingId = new String(id).trim();
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                doSomethingOnUiThreadAtStart(rv);
            }
        });
        return rv;
    }

    /**
     * @param rv
     */
    public void doSomethingOnUiThreadAtStart(int rv) {
        if (rv == 1) {
            int i = SkEgn.skegn_stop(SkConstants.engine);
            //TODO 根据 i 来查询报错原因.
            callBack.faild(content, wavPath, sentenceID, curPosition, "初始化引擎失败", holderObj);
        }
    }

    //重写了
    @Override
    public void run(byte[] data, int size) {
        SkEgn.skegn_feed(SkConstants.engine, data, size);
    }

    @Override
    public int run(byte[] id, int type, byte[] data, int size) {
        return onLineCallback(id, type, data, size); //这里应该是第五步. 但是  没有进到这里来.
        //
    }

    private int onLineCallback(byte[] id, int type, byte[] data, int size) {
        Log.i("FUCK", "onLineCallback ********  type == " + type);
        if (type == SkEgn.SKEGN_MESSAGE_TYPE_JSON) {
            final String result = new String(data, 0, size).trim();
            Log.i("FUCK", "第五步得到结果 result == " + result);
            try {
                final JSONObject json = new JSONObject(result);
                //setResult(json.toString(4));
                UIUtils.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        com.tt.AIRecorder.getInstance().stop();
                        doSomethingOnUIthreadAtHaveVad(result);
                    }
                });
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return 0;
    }

    public void doSomethingOnUIthreadAtHaveVad(final String result) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                //if (result.contains("error")) {     3.5修改包含error单词的bug如下
                if (result.contains("error") && !result.contains("refText")) {
                    callBack.faild(content, wavPath, sentenceID, curPosition, "", holderObj);
                } else {
                    SkResult resultBean = JSONUtils.readValue(result, SkResult.class);
                    List<AIRecorderDetails> details = new ArrayList<>();
                    List<SkResult.ResultBean.WordsBean> words = resultBean.getResult().getWords();
                    if (words != null)
                        for (SkResult.ResultBean.WordsBean bean : words) {
                            AIRecorderDetails detail = new AIRecorderDetails();
                            detail.setIndict(1);//界外词
                            detail.setScore(bean.getScores().getOverall());
                            detail.setCharStr(bean.getWord());
                            details.add(detail);
                        }
                    holderObj.put("mp3_url", "http://" + resultBean.getAudioUrl() + ".mp3");
                    callBack.getScore(resultBean.getResult().getOverall() + "", wavPath, sentenceID, curPosition, details, holderObj);
                }
            }
        });
    }
}
