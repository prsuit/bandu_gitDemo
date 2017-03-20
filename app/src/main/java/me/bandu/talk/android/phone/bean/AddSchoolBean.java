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
public class AddSchoolBean extends BaseBean {


    /**
     * sid : 345
     * name : 我的学校
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int sid;
        private String name;

        public void setSid(int sid) {
            this.sid = sid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSid() {
            return sid;
        }

        public String getName() {
            return name;
        }
    }
}
