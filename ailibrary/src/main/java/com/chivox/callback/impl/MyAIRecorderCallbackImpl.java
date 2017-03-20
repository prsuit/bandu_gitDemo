package com.chivox.callback.impl;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.DFHT.base.AIRecorderDetails;
import com.DFHT.utils.JSONUtils;
import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;
import com.DFHT.voiceengine.EngOkCallBack;
import com.chivox.AIEngine;
import com.chivox.ChivoxConstants;
import com.chivox.android.AIRecorder;
import com.chivox.bean.AIRecorderCallbackBean;
import com.chivox.bean.AIRecorderCallbackBeanLocal;
import com.chivox.bean.YunZhiShengResult;
import com.chivox.utils.AIEngineHelperLocal;
import com.chivox.utils.AiUtil;
import com.chivox.utils.ChivoxCreateUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mckiera on 2015/12/14.
 * 封装一下驰声的回调
 */
public class MyAIRecorderCallbackImpl implements AIRecorder.Callback, AIEngine.aiengine_callback {
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
     * @param jsonParam  驰声初始化参数
     * @param wavPath    语音文件位置
     * @param sentenceID 句子ID
     * @param position   当前的位置
     * @param holderObj  持有的obj
     * @param callBack
     * @see com.DFHT.voiceengine.EngOkCallBack
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

    /**
     * AIEngine.aiengine_callback 接口实现方法
     *
     * @param id
     * @param type
     * @param data
     * @param size
     * @return
     */
    @Override
    public int run(byte[] id, int type, byte[] data, int size) {
        return onLineCallback(type, data, size);
    }

    /**
     * 初始化方法
     */
    @Override
    public void onStarted() {
        byte[] id = new byte[64];
        LogUtils.i("jsonParam : " + jsonParam);
        LogUtils.i("ChivoxConstants.engine :" + ChivoxConstants.engine);

        final int rv = AIEngine.aiengine_start(ChivoxConstants.engine, jsonParam, id, this, mContext.getApplicationContext());
        // LogUtils.d("engine start: " + rv);
        // LogUtils.d("engine param: " + jsonParam);
        recordingId = new String(id).trim();
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                doSomethingOnUiThreadAtStart(rv);
            }
        });
    }

    /**
     * 在start时候的方法 rv如果 是0 表示成功. 如果是1 表示失败 失败的时候应该立即调用aiengine_stop来获取失败原因
     *
     * @param rv
     */
    public void doSomethingOnUiThreadAtStart(int rv) {
        if (rv == 1) {
            int i = AIEngine.aiengine_stop(ChivoxConstants.engine);
            //TODO 根据 i 来查询驰声报错原因.
            callBack.faild(content, wavPath, sentenceID, curPosition, "初始化引擎失败", holderObj);
        }
    }

    /**
     * AIRecorder.Callback 接口实现方法
     *
     * @param data
     * @param size
     */
    @Override
    public void onData(byte[] data, int size) {
        int aiengine_feed = AIEngine.aiengine_feed(ChivoxConstants.engine, data, size);
        if (aiengine_feed == -1) {
            LogUtils.e("警报:aiengine_feed == " + aiengine_feed);
        }
    }

    /**
     * 销毁方法
     */
    @Override
    public void onStopped() {
        int i = AIEngine.aiengine_stop(ChivoxConstants.engine);
        this.waitStartTime = System.currentTimeMillis();
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                doSomethingOnUiThreadAtStop(waitStartTime);
            }
        });
    }

    private void doSomethingOnUiThreadAtStop(long waitStartTime) {

    }

    public void doSomethingOnUIthreadAtHaveVad(String result) {

    }

    private int onLineCallback(int type, byte[] data, int size) {
        if (type == AIEngine.AIENGINE_MESSAGE_TYPE_JSON) {
            final String result = new String(data, 0, size).trim();
            try {
                JSONObject json = new JSONObject(result);
                if (json.has("vad_status") || json.has("volume")) {
                    int status = json.optInt("vad_status");
                    // final int volume = json.optInt("volume");
                    if (status == 2) {

                        UIUtils.runInMainThread(new Runnable() {
                            @Override
                            public void run() {
                                ChivoxConstants.recorder.stop();
                                doSomethingOnUIthreadAtHaveVad(result);
                            }
                        });
                    }
                } else {
                    if (ChivoxConstants.recorder.isRunning()) {
                        ChivoxConstants.recorder.stop();
                    }
                    waitEndTime = System.currentTimeMillis();
                    UIUtils.runInMainThread(new Runnable() {
                        @Override
                        public void run() {
                            doSomethingOnUIthreadAtNoVad(result);
                        }
                    });
                }

            } catch (JSONException e) {
                /* ignore */
                return -1;
            }
        }
        return 0;
    }

    private int offLineCallback(byte[] id, int type, byte[] data, int size) {
        if (type == AIEngine.AIENGINE_MESSAGE_TYPE_JSON) {
            // must trim the end '\0'
            final String recId = new String(id).trim();
            final String responseString = new String(data, 0, size).trim();
            // Log.d(tag, "recordId=" + recId + "\n" + responseString);
            // vad response
            boolean isVadResponse = false;
            if (ChivoxConstants.isVadEnable) {
                try {
                    int vad_status = (new JSONObject(responseString)).getInt("vad_status");
                    if (2 == vad_status && recId.equals(recordingId)) {
                        recordingId = null;
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//								if (sentIndex == -1) { // 避免已销毁了
//									return;
//								}
                                LogUtils.d("");
                                //stopRecord();
                            }
                        });
                    }
                    isVadResponse = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // OR result response
            if (!isVadResponse) {
                final long endTime = System.currentTimeMillis();
                //Log.d(tag, "评分时长=" + (endTime - stopStartTime));
                //LogUtils.d( "评分时长=" + (endTime - stopStartTime));
                String jsonFilePath = AIEngineHelperLocal.getFilesDir(mContext.getApplicationContext()).getPath() + "/record/" + recId + ".json";
                try {
                    RandomAccessFile f = new RandomAccessFile(new File(jsonFilePath), "rw");
                    f.writeBytes(responseString);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // if (recId.equals(recordId)) {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    public void run() {
                        //TODO something
                        showScoreResult(recId, responseString);
                    }
                });
                // }
            }

        } else if (type == AIEngine.AIENGINE_MESSAGE_TYPE_BIN) {
        }
        return 0;
    }

    /**
     * @param recId
     * @param responseString
     */
    private void showScoreResult(String recId, String responseString) {
        doSomethingOnUIthreadAtNoVad(responseString, recId);
    }

    public void doSomethingOnUIthreadAtNoVad(String result) {
        if (TextUtils.isEmpty(result)) {
            callBack.faild(content, wavPath, sentenceID, curPosition, "在线评分失败,结果是null", holderObj);
            return;
        }
        AIRecorderCallbackBean readValue = JSONUtils.readValue(result, AIRecorderCallbackBean.class);
        if (readValue == null || readValue.getResult() == null) {
            LogUtils.e("驰声错误日志:" + result);
            callBack.faild(content, wavPath, sentenceID, curPosition, result, holderObj);
            return;
        }
        LogUtils.i("得到的结果:" + result);
        List<AIRecorderDetails> details = readValue.getResult().getDetails();
        if (details != null && details.size() >= 0) {

            List<YunZhiShengResult> item = new ArrayList<>();
            YunZhiShengResult yunWord;
            for (int i = 0; i < details.size(); i++) {
                yunWord = new YunZhiShengResult();
                String charStr = details.get(i).getCharStr();
                int score = details.get(i).getScore();
                yunWord.score = String.valueOf(score / 10);
                yunWord.text = charStr;
                yunWord.type = "2";
                //Constans.item.add(yunWord);
                item.add(yunWord);
            }

            Map<String, List<YunZhiShengResult>> dataCache = new HashMap<String, List<YunZhiShengResult>>();
            LogUtils.i("下载地址 : " + readValue.getAudioUrl());
            dataCache.put("http://" + readValue.getAudioUrl() + ".mp3", item);
//            ChivoxConstants.statementResult.add(dataCache);
//            if (!flag) {
            pullPCMFile(readValue.getRecordId());
            //TODO
            if (holderObj == null) {
                holderObj = new HashMap<>();
            }
            holderObj.put("mp3_url", "http://" + readValue.getAudioUrl() + ".mp3");
            callBack.getScore(String.valueOf(readValue.getResult().getOverall()), wavPath, sentenceID, curPosition, details, holderObj);
        }
    }

    public void doSomethingOnUIthreadAtNoVad(final String result, String recorId) {
        if (TextUtils.isEmpty(result)) {
            callBack.faild(content, wavPath, sentenceID, curPosition, "离线评分失败,result为null", holderObj);
            return;
        }
        final AIRecorderCallbackBeanLocal readValue = JSONUtils.readValue(result, AIRecorderCallbackBeanLocal.class);
        if (readValue == null || readValue.getResult() == null) {
            LogUtils.e("驰声错误日志:" + result);
            callBack.faild(content, wavPath, sentenceID, curPosition, "离线评分失败,readValue为null", holderObj);
            return;
        }

        LogUtils.i("得到的结果:" + result);
        List<AIRecorderDetails> details = readValue.getResult().getDetails();
        if (details != null && details.size() >= 0) {
            List<YunZhiShengResult> item = new ArrayList<>();
            YunZhiShengResult yunWord;
            for (int i = 0; i < details.size(); i++) {
                yunWord = new YunZhiShengResult();
                String charStr = details.get(i).getCharStr();
                int score = details.get(i).getScore();
                yunWord.score = String.valueOf(score / 10);
                yunWord.text = charStr;
                yunWord.type = "2";
                //Constans.item.add(yunWord);
                item.add(yunWord);
            }
            Map<String, List<YunZhiShengResult>> dataCache = new HashMap<String, List<YunZhiShengResult>>();
            dataCache.put(wavPath, item);
            ChivoxConstants.statementResult.add(dataCache);
            //TODO
//            chivoOK.getScore(String.valueOf(readValue.getResult().getOverall()), path, quizid, details, tvScore, position);
            callBack.getScore(String.valueOf(readValue.getResult().getOverall()), wavPath, sentenceID, curPosition, details, holderObj);
        }
    }

    private void pullPCMFile(final String recordName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 将要保存的录音文件放到【banduCache.pcm】中
                // if (_pcmBuf != null) {
                //
                // if
                // (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                // {
                // try {
                // saveToSDCard("banduCache.pcm", _pcmBuf.toByteArray());
                // } catch (Exception e) {
                // e.printStackTrace();
                // }
                // } else {
                // save("banduCache.pcm", _pcmBuf.toByteArray());
                // }
                // } else {
                String source = AiUtil.getRecordPath(mContext) + File.separator + recordName + ".wav";
                copyRecordFile2Destination(source, "banduCache.pcm");
                // }
             /*   Intent intent = new Intent();
                intent.setAction(Constans.VIEW_REFRESH_BY_YUN_RESULT_ERROR);// 上传
                mContext.sendBroadcast(intent);
                intent = null;*/
            }
        }).start();
    }

    public void copyRecordFile2Destination(String source, String dstName) {
        if (TextUtils.isEmpty(source))
            return;

        // File file = new File(Environment.getExternalStorageDirectory(),
        // dstName);

        File file = null;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = new File(Environment.getExternalStorageDirectory(), dstName);
        } else {
            file = new File(dstName);
        }

        File sourceFile = new File(source);
        int bytes;
        int BUFFER_SIZE = 2 * 1024;
        byte[] buf = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(sourceFile);
            // fos = new FileOutputStream(file);

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                fos = new FileOutputStream(file);
            } else {
                fos = mContext.openFileOutput(dstName, Context.MODE_PRIVATE);
            }

            while ((bytes = fis.read(buf, 0, BUFFER_SIZE)) > 0) {
                fos.write(buf, 0, bytes);
            }

            fis.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
