package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * 创建者：gaoye
 * 时间：2015/12/8  17:15
 * 类描述：作业单元bean
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ExerciseDowanloadInfoBean extends BaseBean {


    /**
     * book_id : 2
     * book_name : 人教版三年级上册
     * book_category : 2
     * book_status : 1
     * unit_id : 2
     * unit_name : Unit 2
     * lesson_list : [{"lesson_id":1,"lesson_name":"Lesson 1 Hello","quiz_list":[{"quiz_id":1,"quiz_name":"aaaaa","mp4":"http://xxxx.mp4","sentence_list":[{"sentence_id":1,"en":"aaa","zh":"啊啊啊","mp3":"http://www.bandu.cn/aaaa.mp3","sort":1,"seconds":4,"mp4_start_time":10,"mp4_end_time":1010},{}]}]},{}]
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int book_id;
        private String book_name;
        private int book_category;
        private int book_status;
        private int unit_id;
        private String unit_name;
        /**
         * lesson_id : 1
         * lesson_name : Lesson 1 Hello
         * quiz_list : [{"quiz_id":1,"quiz_name":"aaaaa","mp4":"http://xxxx.mp4","sentence_list":[{"sentence_id":1,"en":"aaa","zh":"啊啊啊","mp3":"http://www.bandu.cn/aaaa.mp3","sort":1,"seconds":4,"mp4_start_time":10,"mp4_end_time":1010},{}]}]
         */

        private List<LessonListBean> lesson_list;

        public int getBook_id() {
            return book_id;
        }

        public void setBook_id(int book_id) {
            this.book_id = book_id;
        }

        public String getBook_name() {
            return book_name;
        }

        public void setBook_name(String book_name) {
            this.book_name = book_name;
        }

        public int getBook_category() {
            return book_category;
        }

        public void setBook_category(int book_category) {
            this.book_category = book_category;
        }

        public int getBook_status() {
            return book_status;
        }

        public void setBook_status(int book_status) {
            this.book_status = book_status;
        }

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
            /**
             * quiz_id : 1
             * quiz_name : aaaaa
             * mp4 : http://xxxx.mp4
             * sentence_list : [{"sentence_id":1,"en":"aaa","zh":"啊啊啊","mp3":"http://www.bandu.cn/aaaa.mp3","sort":1,"seconds":4,"mp4_start_time":10,"mp4_end_time":1010},{}]
             */

            private List<QuizListBean> quiz_list;

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

            public List<QuizListBean> getQuiz_list() {
                return quiz_list;
            }

            public void setQuiz_list(List<QuizListBean> quiz_list) {
                this.quiz_list = quiz_list;
            }

            public static class QuizListBean {
                private int quiz_id;
                private String quiz_name;
                private String mp4;
                /**
                 * sentence_id : 1
                 * en : aaa
                 * zh : 啊啊啊
                 * mp3 : http://www.bandu.cn/aaaa.mp3
                 * sort : 1
                 * seconds : 4
                 * mp4_start_time : 10
                 * mp4_end_time : 1010
                 */

                private List<SentenceListBean> sentence_list;

                public int getQuiz_id() {
                    return quiz_id;
                }

                public void setQuiz_id(int quiz_id) {
                    this.quiz_id = quiz_id;
                }

                public String getQuiz_name() {
                    return quiz_name;
                }

                public void setQuiz_name(String quiz_name) {
                    this.quiz_name = quiz_name;
                }

                public String getMp4() {
                    return mp4;
                }

                public void setMp4(String mp4) {
                    this.mp4 = mp4;
                }

                public List<SentenceListBean> getSentence_list() {
                    return sentence_list;
                }

                public void setSentence_list(List<SentenceListBean> sentence_list) {
                    this.sentence_list = sentence_list;
                }

                public static class SentenceListBean {
                    private int sentence_id;
                    private String en;
                    private String zh;
                    private String mp3;
                    private int sort;
                    private int seconds;
                    private int mp4_start_time;
                    private int mp4_end_time;

                    public int getSentence_id() {
                        return sentence_id;
                    }

                    public void setSentence_id(int sentence_id) {
                        this.sentence_id = sentence_id;
                    }

                    public String getEn() {
                        return en;
                    }

                    public void setEn(String en) {
                        this.en = en;
                    }

                    public String getZh() {
                        return zh;
                    }

                    public void setZh(String zh) {
                        this.zh = zh;
                    }

                    public String getMp3() {
                        return mp3;
                    }

                    public void setMp3(String mp3) {
                        this.mp3 = mp3;
                    }

                    public int getSort() {
                        return sort;
                    }

                    public void setSort(int sort) {
                        this.sort = sort;
                    }

                    public int getSeconds() {
                        return seconds;
                    }

                    public void setSeconds(int seconds) {
                        this.seconds = seconds;
                    }

                    public int getMp4_start_time() {
                        return mp4_start_time;
                    }

                    public void setMp4_start_time(int mp4_start_time) {
                        this.mp4_start_time = mp4_start_time;
                    }

                    public int getMp4_end_time() {
                        return mp4_end_time;
                    }

                    public void setMp4_end_time(int mp4_end_time) {
                        this.mp4_end_time = mp4_end_time;
                    }
                }
            }
        }
    }
}