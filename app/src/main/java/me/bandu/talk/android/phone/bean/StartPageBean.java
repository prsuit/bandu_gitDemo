package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

/**
 * 创建者:taoge
 * 时间：2016/1/18
 * 类描述：
 * 修改人:taoge
 * 修改时间：2016/1/18
 * 修改备注：
 */
public class StartPageBean extends BaseBean{


    /**
     * content  : <html><head></head><body>aaaaaaaaaaa</body></html>
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String content;

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }
}
