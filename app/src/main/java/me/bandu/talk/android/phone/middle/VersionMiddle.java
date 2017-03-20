package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.VersionBean;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：注册的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class VersionMiddle extends BaseMiddle {
    public static final int GET_VERION = 4;

    public VersionMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void getNewVersion(int versionCode,VersionBean obj){
        Map<String,String> data = new HashMap();
        data.put("version", versionCode+"");
//        send(ConstantValue.CHECK_VERSION, GET_VERION, data,obj);
    }

}
