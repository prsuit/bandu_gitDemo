package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.AppUpdateBean;

/**
 * 创建者：wanglei
 * <p>时间：16/8/23  10:58
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class AppUpdateMiddle extends BaseMiddle {

    public final static int VERSIONUPDATE = 46;

    public AppUpdateMiddle(BaseActivityIF activity, Context context) {
        super(activity, context);
    }

    public void update(int versionCode){
        Map<String, String> data = new HashMap();
        data.put("version", String.valueOf(versionCode));
        send(ConstantValue.APPUPDATE, VERSIONUPDATE, data, new AppUpdateBean());
    }
}
