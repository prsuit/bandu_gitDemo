package com.chivox;

import com.DFHT.manager.KeyManager;
import com.chivox.android.AIRecorder;
import com.chivox.bean.YunZhiShengResult;

import java.security.Key;
import java.util.List;
import java.util.Map;

/**
 * 创建者：Mcablylx
 * 时间：2015/12/9 16:37
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ChivoxConstants {

    public static long engine;
    public static AIRecorder recorder = null;

    public static final boolean DEBUG = true;

    public static final int ENGINE_STATUS_NULL = -2;
    public static final int ENGINE_STATUS_LOADING = -1;
    public static final int ENGINE_STATUS_INITIALIZED = 0;
    public static final int ENGINE_STATUS_ERROR = 1;

    public static final String CHIVOX = "chivox";

    public static final String CHINESE = "chinese";
    public static final String ENGLISH = "english";
    public static final String NUMBER = "number";

    /***** score type **/

    public static final String CN_WORD = "cn.word.score";
    public static final String EN_WORD = "en.word.score";
    public static final String EN_WORD_CHILD = "en.word.child";

    public static final String CN_SENTENCE = "cn.sent.score";
    public static final String EN_SENTENCE = "en.sent.score";
    public static final String EN_SENTENCE_CHILD = "en.sent.child";

    public static List<Map<String, List<YunZhiShengResult>>> statementResult;


    public static boolean isVadEnable = false;
    public static boolean isDebug = false;

    public static int getScoreColor(int score) {
        if (score >= 85) {
            return 0xFF4D9927;
        } else if (score >= 70) {
            return 0xFF000000;
        } else if (score >= 55) {
            return 0xFFCC8218;
        }
        return 0xFFB72F21;
    }
    /**
     * 驰声key secretKey
     */
    public static final String appKey = KeyManager.getAppKey();
    public static String secretKey;
    //TODO 登录后,修改这个userID
    public static String userId = "DFHT_Android";

}
