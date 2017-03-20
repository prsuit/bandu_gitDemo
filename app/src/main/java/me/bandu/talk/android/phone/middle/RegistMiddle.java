package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.RegistBean;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：注册的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class RegistMiddle extends BaseMiddle {
    public static final int REGISTER = 1;

    public RegistMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    /**
     * 注册
     * @param name 姓名
     * @param role 角色类型1:老师   2：学生
     * @param phone 手机号
     * @param checkCode 验证码
     * @param password 密码
     * @param obj
     */
    public void regist(String name, int role,String phone,String checkCode,String password,RegistBean obj){
        Map<String,String> data = new HashMap();
        //data.put("sue", GlobalParams.sue);
        //data.put("sup", GlobalParams.sup);
        data.put("name", name);
        data.put("role", role+"");
        data.put("phone", phone);
        data.put("checkcode", checkCode);
        data.put("password", password);
        send(ConstantValue.REGISTER, REGISTER, data,obj);
    }
}
