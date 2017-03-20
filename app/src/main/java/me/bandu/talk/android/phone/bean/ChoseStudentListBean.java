package me.bandu.talk.android.phone.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.DFHT.base.BaseBean;

import java.io.Serializable;

/**
 * 创建者：高楠
 * 时间：on 2016/1/27
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ChoseStudentListBean extends BaseBean implements Serializable{
    private ChoseStudentListData data;

    public ChoseStudentListData getData() {
        return data;
    }

    public void setData(ChoseStudentListData data) {
        this.data = data;
    }

}
