package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

/**
 * 创建者:taoge
 * 时间：2015/12/8
 * 类描述：
 * 修改人:taoge
 * 修改时间：2015/12/8
 * 修改备注：
 */
public class RegistBean extends BaseBean {

    /**
     * uid : 20101
     * role : 2
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String uid;
        private int role;

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getUid() {
            return uid;
        }

        public int getRole() {
            return role;
        }
    }
}
