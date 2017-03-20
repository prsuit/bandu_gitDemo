package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

/**
 * 创建者:taoge
 * 时间：2016/2/23
 * 类描述：
 * 修改人:taoge
 * 修改时间：2016/2/23
 * 修改备注：
 */
public class SysMsgNumBean extends BaseBean {

    /**
     * un_read : 1
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String un_read;

        public void setUn_read(String un_read) {
            this.un_read = un_read;
        }

        public String getUn_read() {
            return un_read;
        }
    }
}
