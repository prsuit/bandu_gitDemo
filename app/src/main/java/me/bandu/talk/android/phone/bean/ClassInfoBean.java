package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

/**
 * 创建者：gaoye
 * 时间：2015/12/25 9:12
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ClassInfoBean extends BaseBean {

    /**
     * cid : 12345
     * name : 三年级二班
     * creator : 李景春
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
        private String name;
        private String creator;
        private String ctime;


        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getCid() {
            return cid;
        }

        public String getName() {
            return name;
        }

        public String getCreator() {
            return creator;
        }
    }
}
