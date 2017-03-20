package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * 创建者：高楠
 * 时间：on 2015/11/30
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class UnitInfoBean extends  BaseBean{
    private UnitInfoData data;

    @Override
    public String toString() {
        return "UnitInfoBean{" +
                "data=" + data +
                '}';
    }

    public UnitInfoData getData() {
        return data;
    }

    public void setData(UnitInfoData data) {
        this.data = data;
    }
}
