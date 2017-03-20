package com.tt;

/**
 * 创建者：Mckiera
 * <p>时间：2016-07-20  15:25
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class SkConstants {
    public static long engine;

//    final public static String cloudServer = "ws://47.88.194.175:8090";
    final public static String cloudServer = "ws://api.17kouyu.com:8080";
//    final public static String cloudServer = "ws://candidate.17kouyu.com:8090";

//    final public static String appkey = "1255202120000c";
    final public static String appkey = "146907327000000a";
//    final public static String secretkey = "988aaf63008b6db30f38bd60be0c3534";
    final public static String secretkey = "ec0f50b64e088888841d0e385792f06e";

    public static final String CHINESE = "chinese";
    public static final String ENGLISH = "english";
    public static final String NUMBER = "number";

    /***** score type **/

    public static final String CN_WORD = "en.word.score/eng.wrd.g4";
    public static final String EN_WORD = "en.word.score/eng.wrd.g4";
    public static final String EN_WORD_CHILD = "en.word.score/eng.wrd.g4";

    public static final String CN_SENTENCE = "en.sent.score";
    public static final String EN_SENTENCE = "en.sent.score";
    public static final String EN_SENTENCE_CHILD = "en.sent.score";
}
