package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * 创建者:taoge
 * 时间：2016/2/23
 * 类描述：
 * 修改人:taoge
 * 修改时间：2016/2/23
 * 修改备注：
 */
public class SysMsgBean extends BaseBean {

    /**
     * list : [{"msg_id":1,"title":"hello","content":"hello hello","is_read":0},{}]
     * total : 20
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int total;
        /**
         * msg_id : 1
         * title : hello
         * content : hello hello
         * is_read : 0
         */

        private List<ListEntity> list;

        public void setTotal(int total) {
            this.total = total;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public int getTotal() {
            return total;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            private String msg_id;
            private String title;
            private String content;
            private String ctime;
            private int is_read;

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public void setMsg_id(String msg_id) {
                this.msg_id = msg_id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setIs_read(int is_read) {
                this.is_read = is_read;
            }

            public String getMsg_id() {
                return msg_id;
            }

            public String getTitle() {
                return title;
            }

            public String getContent() {
                return content;
            }

            public int getIs_read() {
                return is_read;
            }
        }
    }
}
