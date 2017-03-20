package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * Created by GaoNan on 2015/11/25.
 */
public class BookInfoBean extends BaseBean{
    private BookInfoData data;//"人教版三年级上册"

    public BookInfoData getData() {
        return data;
    }
    public void setData(BookInfoData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BookInfoBean{" +
                "data=" + data +
                '}';
    }
}
