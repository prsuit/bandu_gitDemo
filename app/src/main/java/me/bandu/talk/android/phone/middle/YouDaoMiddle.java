package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.net.enenum.MethodType;
import com.DFHT.net.utils.EasyNet;
import com.DFHT.utils.JSONUtils;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.WordBean;
import me.bandu.talk.android.phone.bean.WordListBean;

/**
 * 创建者：gaoye
 * 时间：2015/11/25 13:49
 * 类描述：绑定教材
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class YouDaoMiddle extends BaseMiddle {
    public static final int YOUDAO = 199;

    private  BaseActivityIF baseActivityIF;
    public YouDaoMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
        this.baseActivityIF = baseActivityIF;
    }

    public void getWord(String word){
        Map<String,String> data = new HashMap();
        data.put("keyfrom", "banduWang");
        data.put("key", "1968154877");
        data.put("type", "data");
        data.put("doctype", "json");
        data.put("version", "1.1");
        data.put("q", word);
        //new EasyNet(MethodType.GET, ConstantValue.YOUDAO,YOUDAO,data,this,false).execute();
        send(ConstantValue.YOUDAO, YOUDAO, data, new WordBean(), false);
    }

    @Override
    public void success(Object result, int requestCode) {
        if (result != null)
            activity.successFromMid(result,requestCode);
    }

    @Override
    public void failed(int requestCode) {
        activity.failedFrom(requestCode);
    }
}
