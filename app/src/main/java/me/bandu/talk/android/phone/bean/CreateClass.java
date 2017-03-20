package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

/**
 * Created by Mckiera on 2015/12/16.
 */
public class CreateClass extends BaseBean {

    /**
     * cid : 45977903084
     * class_name : 花姑姑父
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String cid;
        private String class_name;

        public void setCid(String cid) {
            this.cid = cid;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getCid() {
            return cid;
        }

        public String getClass_name() {
            return class_name;
        }
    }
}
