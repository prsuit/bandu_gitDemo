package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：查看作业详情的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class GetCaptchaMiddle extends BaseMiddle {
    public static final int GET_CAPTCHA = 2;

    public GetCaptchaMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    /**
     * 获取验证码
     * @param phone 手机号
     * @param type 0：手机号还未注册  1：手机号已注册
     * @param obj
     */
    public void getcaptcha(String phone,int type,BaseBean obj){
        Map<String,String> data = new HashMap();
        data.put("sue", GlobalParams.sue);
        data.put("sup", GlobalParams.sup);
        data.put("phone", phone);
        data.put("type", type+"");
        setTimeout(15000);
        send(ConstantValue.GET_CAPTCHA, GET_CAPTCHA, data,obj);
    }

}
