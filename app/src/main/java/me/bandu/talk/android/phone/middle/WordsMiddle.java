package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.JSONUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.ExpandableNewWord;
import me.bandu.talk.android.phone.bean.MWordBean;
import me.bandu.talk.android.phone.bean.WordBean;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * 创建者：gaoye
 * 时间：2015/11/25 13:49
 * 类描述：绑定教材
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WordsMiddle extends BaseMiddle {
    public static final int CREATE_WORDS = 50;
    public static final int WORDS_LIST = 51;
    public static final int DELETE_WORDS = 52;
    public static final int GET_WORD = 57;

    public WordsMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void getWord(String word){
        Map<String,String> data = new HashMap();
        data.put("word",word);
        send(ConstantValue.GET_WORD, GET_WORD, data, new MWordBean());
    }

    public void createWord(String word, WordBean wordBean){
        Map<String,String> data = new HashMap();
        data.put("sue", GlobalParams.sue);
        data.put("sup", GlobalParams.sup);
        data.put("uid", UserUtil.getUid());
        data.put("word", word);
        data.put("content", JSONUtils.getJson(wordBean));
        send(ConstantValue.CREATE_WORDS, CREATE_WORDS, data, new BaseBean());
    }

    public void wordList(String page,String size){
        Map<String,String> data = new HashMap();
        data.put("uid", UserUtil.getUid());
        data.put("page", page);
        data.put("size",size);
        send(ConstantValue.WORDS_LIST, WORDS_LIST, data, new ExpandableNewWord());
    }

    public void deleteWord(List word_ids){
        Map data = new HashMap();
        data.put("uid", GlobalParams.userInfo.getUid() + "");
        data.put("word_ids", word_ids);
        send(ConstantValue.DELETE_WORDS, DELETE_WORDS, data, new BaseBean());
    }

    @Override
    public void success(Object result, int requestCode) {
        activity.successFromMid(result,requestCode);
    }

    @Override
    public void failed(int requestCode) {
        activity.failedFrom(requestCode);
    }
}
