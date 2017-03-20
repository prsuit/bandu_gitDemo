package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * 创建者：gaoye
 * 时间：2016/3/31 10:44
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ExerciseTextbookTypeBean extends BaseBean{

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * cat_id : 2
         * name : 课外读物
         * list : [{"sub_cat_id":1,"sub_cat_name":"国家地理"},{"sub_cat_id":2,"sub_cat_name":"积极英语阅读"}]
         */

        private List<ListEntity> list;

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            private int cat_id;
            private String cat_name;
            /**
             * sub_cat_id : 1
             * sub_cat_name : 国家地理
             */

            private List<ListEntity2> list;

            public void setCat_id(int cat_id) {
                this.cat_id = cat_id;
            }


            public void setList(List<ListEntity2> list) {
                this.list = list;
            }

            public int getCat_id() {
                return cat_id;
            }

            public String getCat_name() {
                return cat_name;
            }

            public void setCat_name(String cat_name) {
                this.cat_name = cat_name;
            }

            public List<ListEntity2> getList() {
                return list;
            }

            public static class ListEntity2 {
                private int sub_cat_id;
                private String sub_cat_name;

                public void setSub_cat_id(int sub_cat_id) {
                    this.sub_cat_id = sub_cat_id;
                }

                public void setSub_cat_name(String sub_cat_name) {
                    this.sub_cat_name = sub_cat_name;
                }

                public int getSub_cat_id() {
                    return sub_cat_id;
                }

                public String getSub_cat_name() {
                    return sub_cat_name;
                }
            }
        }
    }
}
