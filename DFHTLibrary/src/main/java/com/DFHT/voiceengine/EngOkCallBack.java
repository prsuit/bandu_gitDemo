package com.DFHT.voiceengine;

import com.DFHT.base.AIRecorderDetails;

import java.util.List;
import java.util.Map;

/**
 * 创建者：Mckiera
 * <p>时间：2016-08-24  09:59
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public interface EngOkCallBack {
    /**
     * 成功评分
     * @param score        总分数
     * @param path         录音文件的位置
     * @param sentenceID   句子ID
     * @param position     当前的位置
     * @param details      每个单词的得分
     * @param holderObj    持有的obj对象
     */
    void getScore(String score, String path, String sentenceID, int position, List<AIRecorderDetails> details, Map<String, Object> holderObj);

    /**
     * 评分失败
     * @param path          录音文件的位置
     * @param sentenceID    句子ID
     * @param position      当前的位置
     * @param holderObj     持有的obj对象
     */
    void faild(String content,String path, String sentenceID, int position, String msg,Map<String, Object> holderObj);
}
