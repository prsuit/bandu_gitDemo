package me.bandu.talk.android.phone.bean;

import java.util.List;

/**
 * 创建者：gaoye
 * 时间：2015/12/8  17:15
 * 类描述：作业单元bean
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ExerciseDowanloadBean {

    /**
     * status : 1
     * data : {"book_name":"人教版三年级上册","cover":"http://www.bandu.cn/cover/1.jpg","pub_date":"2015-11-23","contents":[{"unit_id":1,"unit_name":"Unit 1 Test","lesson_list":[{"lesson_id":1,"lesson_name":"Lesson 1 Hello"},{}]},{},{}]}
     * msg : 成功
     */

    private int status;
    private DataEntity data;
    private String msg;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public static class DataEntity {
        /**
         * book_name : 人教版三年级上册
         * cover : http://www.bandu.cn/cover/1.jpg
         * pub_date : 2015-11-23
         * contents : [{"unit_id":1,"unit_name":"Unit 1 Test","lesson_list":[{"lesson_id":1,"lesson_name":"Lesson 1 Hello"},{}]},{},{}]
         */

        private String book_name;
        private String cover;
        private String pub_date;
        private List<ContentsEntity> contents;

        public void setBook_name(String book_name) {
            this.book_name = book_name;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setPub_date(String pub_date) {
            this.pub_date = pub_date;
        }

        public void setContents(List<ContentsEntity> contents) {
            this.contents = contents;
        }

        public String getBook_name() {
            return book_name;
        }

        public String getCover() {
            return cover;
        }

        public String getPub_date() {
            return pub_date;
        }

        public List<ContentsEntity> getContents() {
            return contents;
        }

        public static class ContentsEntity {
            /**
             * unit_id : 1
             * unit_name : Unit 1 Test
             * lesson_list : [{"lesson_id":1,"lesson_name":"Lesson 1 Hello"},{}]
             */

            private int unit_id;
            private String unit_name;
            private List<LessonListEntity> lesson_list;

            public void setUnit_id(int unit_id) {
                this.unit_id = unit_id;
            }

            public void setUnit_name(String unit_name) {
                this.unit_name = unit_name;
            }

            public void setLesson_list(List<LessonListEntity> lesson_list) {
                this.lesson_list = lesson_list;
            }

            public int getUnit_id() {
                return unit_id;
            }

            public String getUnit_name() {
                return unit_name;
            }

            public List<LessonListEntity> getLesson_list() {
                return lesson_list;
            }

            public static class LessonListEntity {
                /**
                 * lesson_id : 1
                 * lesson_name : Lesson 1 Hello
                 */

                private int lesson_id;
                private String lesson_name;

                public void setLesson_id(int lesson_id) {
                    this.lesson_id = lesson_id;
                }

                public void setLesson_name(String lesson_name) {
                    this.lesson_name = lesson_name;
                }

                public int getLesson_id() {
                    return lesson_id;
                }

                public String getLesson_name() {
                    return lesson_name;
                }
            }
        }
    }
}
