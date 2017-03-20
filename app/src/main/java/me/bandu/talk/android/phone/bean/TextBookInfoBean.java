package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * 创建者：gaoye
 * 时间：2015/11/27  14:37
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TextBookInfoBean extends BaseBean{
    /**
     * book_name : 人教版三年级上册
     * cover : http://www.bandu.cn/cover/1.jpg
     * grade : 三年级
     * version : 人教版
     * contents : [{"unit_id":1,"unit_name":"Unit 1 Test","lesson_list":[{"lesson_id":1,"lesson_name":"Lesson 1 Hello"},{}]},{},{}]
     * status : 1
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String book_name;
        private String cover;
        private String grade;
        private String version;
        private int status;
        /**
         * unit_id : 1
         * unit_name : Unit 1 Test
         * lesson_list : [{"lesson_id":1,"lesson_name":"Lesson 1 Hello"},{}]
         */

        private List<ContentsBean> contents;

        public String getBook_name() {
            return book_name;
        }

        public void setBook_name(String book_name) {
            this.book_name = book_name;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<ContentsBean> getContents() {
            return contents;
        }

        public void setContents(List<ContentsBean> contents) {
            this.contents = contents;
        }

        public static class ContentsBean {
            private int unit_id;
            private String unit_name;
            /**
             * lesson_id : 1
             * lesson_name : Lesson 1 Hello
             */

            private List<LessonListBean> lesson_list;

            public int getUnit_id() {
                return unit_id;
            }

            public void setUnit_id(int unit_id) {
                this.unit_id = unit_id;
            }

            public String getUnit_name() {
                return unit_name;
            }

            public void setUnit_name(String unit_name) {
                this.unit_name = unit_name;
            }

            public List<LessonListBean> getLesson_list() {
                return lesson_list;
            }

            public void setLesson_list(List<LessonListBean> lesson_list) {
                this.lesson_list = lesson_list;
            }

            public static class LessonListBean {
                private int lesson_id;
                private String lesson_name;

                public int getLesson_id() {
                    return lesson_id;
                }

                public void setLesson_id(int lesson_id) {
                    this.lesson_id = lesson_id;
                }

                public String getLesson_name() {
                    return lesson_name;
                }

                public void setLesson_name(String lesson_name) {
                    this.lesson_name = lesson_name;
                }
            }
        }
    }
}
