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
public class AddressBean extends BaseBean {


    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * district_id : 123
         * name : 海淀区
         */

        private List<ListEntity> list;

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            private int district_id;
            private String name;
            private String sortLetters;

            public String getSortLetters() {
                return sortLetters;
            }

            public void setSortLetters(String sortLetters) {
                this.sortLetters = sortLetters;
            }

            public void setDistrict_id(int district_id) {
                this.district_id = district_id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getDistrict_id() {
                return district_id;
            }

            public String getName() {
                return name;
            }
        }
    }
}
