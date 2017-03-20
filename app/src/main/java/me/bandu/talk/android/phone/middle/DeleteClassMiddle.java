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
 * 创建者：gaoye
 * 时间：2015/11/25 13:49
 * 类描述：删除班级
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class DeleteClassMiddle extends BaseMiddle {
    public static final int DELETE_CLASS = 12;

    public DeleteClassMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void deleteClass(String cid){
        Map<String,String> data = new HashMap();
        data.put("sue", GlobalParams.sue);
        data.put("sup", GlobalParams.sup);
        data.put("uid",GlobalParams.userInfo.getUid() + "");
        data.put("cid", cid);
        send(ConstantValue.DELETE_CLASS, DELETE_CLASS, data,new BaseBean());
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
