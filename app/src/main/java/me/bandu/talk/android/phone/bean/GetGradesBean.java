package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * 创建者：Administrator
 * 时间：2015/12/16  15:59
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class GetGradesBean extends BaseBean {

    /**
     * data : {"list":[{"grade_id":1,"grade_name":"一年级"},{"id":2,"grade_name":"二年级"}]}
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * list : [{"grade_id":1,"grade_name":"一年级"},{"id":2,"grade_name":"二年级"}]
         */

        private List<ListEntity> list;

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            /**
             * grade_id : 1
             * grade_name : 一年级
             */

            private int grade_id;
            private String grade_name;

            public void setGrade_id(int grade_id) {
                this.grade_id = grade_id;
            }

            public void setGrade_name(String grade_name) {
                this.grade_name = grade_name;
            }

            public int getGrade_id() {
                return grade_id;
            }

            public String getGrade_name() {
                return grade_name;
            }
        }
    }
}
