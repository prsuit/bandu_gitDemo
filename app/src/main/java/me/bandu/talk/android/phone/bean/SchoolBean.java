package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * 创建者:taoge
 * 时间：2015/12/10
 * 类描述：
 * 修改人:taoge
 * 修改时间：2015/12/10
 * 修改备注：
 */
public class SchoolBean extends BaseBean {


    /**
     * list : [{"sid":123,"name":"海淀区第一小学","sortLetters":""}]
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
         * sid : 123
         * name : 海淀区第一小学
         * sortLetters :
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
            private int sid;
            private String name;
            private String sortLetters;

            public void setSid(int sid) {
                this.sid = sid;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setSortLetters(String sortLetters) {
                this.sortLetters = sortLetters;
            }

            public int getSid() {
                return sid;
            }

            public String getName() {
                return name;
            }

            public String getSortLetters() {
                return sortLetters;
            }
        }
    }
}
