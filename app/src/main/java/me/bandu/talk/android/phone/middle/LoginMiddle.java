package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.LoginBean;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：注册的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class LoginMiddle extends BaseMiddle {
    public static final int LOGIN = 3;

    public LoginMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void login(String phone,String password,String app_info,LoginBean obj){
        Map<String,String> data = new HashMap();
        data.put("username", phone);
        data.put("password", password);
        data.put("app_info", app_info);
        send(ConstantValue.LOGIN, LOGIN, data,obj);
    }

}
