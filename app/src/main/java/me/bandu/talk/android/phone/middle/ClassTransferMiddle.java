package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：班级转让的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ClassTransferMiddle extends BaseMiddle {
    public static final int CLASS_TRANS = 1;

    public ClassTransferMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    /**
     *
     * @param cid 班级编号
     * @param uid 学号
     * @param teaId 另一个教师的学号
     * @param phone 另一个教师的手机号后四位
     */
    public void transferClass(String uid,String cid,String teaId,String phone){
        Map<String,String> data = new HashMap();
        data.put("cid", cid);
        data.put("uid", uid);
        data.put("tea_uid", teaId);
        data.put("phone", phone);
        send(ConstantValue.CLASS_TRANS, CLASS_TRANS, data,new BaseBean());
    }
}
