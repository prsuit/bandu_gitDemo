package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.StartPageBean;

/**
 * 创建者：高楠
 * 时间：on 2015/11/30
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StartPageMiddle extends BaseMiddle {
    public static final  int START_PAGE =1;
    public StartPageMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void getHtml(StartPageBean obj){
        Map<String,String> data = new HashMap();
       send(ConstantValue.START_PAGE,START_PAGE,data,obj);
    }
}
