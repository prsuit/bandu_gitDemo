package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.AddressBean;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：获取地区列表的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class GetRegionMiddle extends BaseMiddle {
    public static final int GET_REGION = 1;

    public GetRegionMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    /**
     *  选择地址
     * @param parentId 上级地址id
     * @param obj
     */
    public void getRegion(int parentId, AddressBean obj){
        Map<String,String> data = new HashMap();
        //data.put("sue", GlobalParams.sue);
        //data.put("sup", GlobalParams.sup);
        data.put("parent_id", parentId+"");
        send(ConstantValue.GET_REGION, GET_REGION, data,obj);
    }
}
