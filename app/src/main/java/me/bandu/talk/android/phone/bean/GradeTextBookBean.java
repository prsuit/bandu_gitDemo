package me.bandu.talk.android.phone.bean;

import java.util.List;

/**
 * 创建者：gaoye
 * 时间：2015/11/25  14:05
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class GradeTextBookBean extends com.DFHT.base.BaseBean {

    /**
     * data : {"list":[{"version":"人教版","list":[{"book_id":2,"book_name":"人教一年级上","cover":"http://www.bandu.cn/cover/2.jpg"},{"book_id":2,"book_name":"人教二年级下","cover":"http://www.bandu.cn/cover/2.jpg"}]},{}]}
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
         * list : [{"version":"人教版","list":[{"book_id":2,"book_name":"人教一年级上","cover":"http://www.bandu.cn/cover/2.jpg"},{"book_id":2,"book_name":"人教二年级下","cover":"http://www.bandu.cn/cover/2.jpg"}]},{}]
         */

        private List<Version> list;

        public void setList(List<Version> list) {
            this.list = list;
        }

        public List<Version> getList() {
            return list;
        }

        public static class Version {
            /**
             * version : 人教版
             * list : [{"book_id":2,"book_name":"人教一年级上","cover":"http://www.bandu.cn/cover/2.jpg"},{"book_id":2,"book_name":"人教二年级下","cover":"http://www.bandu.cn/cover/2.jpg"}]
             */

            private String version;
            private List<TextBook> list;

            public void setVersion(String version) {
                this.version = version;
            }

            public void setList(List<TextBook> list) {
                this.list = list;
            }

            public String getVersion() {
                return version;
            }

            public List<TextBook> getList() {
                return list;
            }

            public static class TextBook {
                /**
                 * book_id : 2
                 * book_name : 人教一年级上
                 * cover : http://www.bandu.cn/cover/2.jpg
                 * type_code : 0 普通教材，2新教材
                 */

                private int book_id;
                private String book_name;
                private String cover;
                private int type_code;

                public void setBook_id(int book_id) {
                    this.book_id = book_id;
                }
                public void setType_code(int type_code){
                    this.type_code = type_code;
                }

                public void setBook_name(String book_name) {
                    this.book_name = book_name;
                }

                public void setCover(String cover) {
                    this.cover = cover;
                }

                public int getBook_id() {
                    return book_id;
                }
                public int getType_code(){
                    return type_code;
                }

                public String getBook_name() {
                    return book_name;
                }

                public String getCover() {
                    return cover;
                }
            }
        }
    }
}
