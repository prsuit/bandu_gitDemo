package me.bandu.talk.android.phone.bean;

import java.util.List;

/**
 * 创建者：gaoye
 * 时间：2015/12/14  15:38
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ExerciseBean {
    /**
     * status : 1
     * data : {"book_id":2,"book_name":"人教版三年级上册","unit_id":2,"unit_name":"Unit 2","lesson_list":[{"lesson_id":1,"lesson_name":"Lesson 1 Hello","quiz_list":[{"quiz_id":1,"quiz_name":"aaaaa","sentence_list":[{"sentence_id":1,"en":"aaa","zh":"啊啊","mp3":"http://192.168.1.128:8088/bandu/unit1/lesson1/part1/centence1.mp3","sort":1,"seconds":4},{}]}]},{}]}
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
         * book_id : 2
         * book_name : 人教版三年级上册
         * unit_id : 2
         * unit_name : Unit 2
         * lesson_list : [{"lesson_id":1,"lesson_name":"Lesson 1 Hello","quiz_list":[{"quiz_id":1,"quiz_name":"aaaaa","sentence_list":[{"sentence_id":1,"en":"aaa","zh":"啊啊","mp3":"http://192.168.1.128:8088/bandu/unit1/lesson1/part1/centence1.mp3","sort":1,"seconds":4},{}]}]},{}]
         */

        private int book_id;
        private String book_name;
        private int unit_id;
        private String unit_name;
        private List<LessonListEntity> lesson_list;

        public void setBook_id(int book_id) {
            this.book_id = book_id;
        }

        public void setBook_name(String book_name) {
            this.book_name = book_name;
        }

        public void setUnit_id(int unit_id) {
            this.unit_id = unit_id;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public void setLesson_list(List<LessonListEntity> lesson_list) {
            this.lesson_list = lesson_list;
        }

        public int getBook_id() {
            return book_id;
        }

        public String getBook_name() {
            return book_name;
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
             * quiz_list : [{"quiz_id":1,"quiz_name":"aaaaa","sentence_list":[{"sentence_id":1,"en":"aaa","zh":"啊啊","mp3":"http://192.168.1.128:8088/bandu/unit1/lesson1/part1/centence1.mp3","sort":1,"seconds":4},{}]}]
             */

            private int lesson_id;
            private String lesson_name;
            private List<QuizListEntity> quiz_list;

            public void setLesson_id(int lesson_id) {
                this.lesson_id = lesson_id;
            }

            public void setLesson_name(String lesson_name) {
                this.lesson_name = lesson_name;
            }

            public void setQuiz_list(List<QuizListEntity> quiz_list) {
                this.quiz_list = quiz_list;
            }

            public int getLesson_id() {
                return lesson_id;
            }

            public String getLesson_name() {
                return lesson_name;
            }

            public List<QuizListEntity> getQuiz_list() {
                return quiz_list;
            }

            public static class QuizListEntity {
                /**
                 * quiz_id : 1
                 * quiz_name : aaaaa
                 * sentence_list : [{"sentence_id":1,"en":"aaa","zh":"啊啊","mp3":"http://192.168.1.128:8088/bandu/unit1/lesson1/part1/centence1.mp3","sort":1,"seconds":4},{}]
                 */

                private int quiz_id;
                private String quiz_name;
                private List<SentenceListEntity> sentence_list;

                public void setQuiz_id(int quiz_id) {
                    this.quiz_id = quiz_id;
                }

                public void setQuiz_name(String quiz_name) {
                    this.quiz_name = quiz_name;
                }

                public void setSentence_list(List<SentenceListEntity> sentence_list) {
                    this.sentence_list = sentence_list;
                }

                public int getQuiz_id() {
                    return quiz_id;
                }

                public String getQuiz_name() {
                    return quiz_name;
                }

                public List<SentenceListEntity> getSentence_list() {
                    return sentence_list;
                }

                public static class SentenceListEntity {
                    /**
                     * sentence_id : 1
                     * en : aaa
                     * zh : 啊啊
                     * mp3 : http://192.168.1.128:8088/bandu/unit1/lesson1/part1/centence1.mp3
                     * sort : 1
                     * seconds : 4
                     */

                    private int sentence_id;
                    private String en;
                    private String zh;
                    private String mp3;
                    private int sort;
                    private int seconds;

                    public void setSentence_id(int sentence_id) {
                        this.sentence_id = sentence_id;
                    }

                    public void setEn(String en) {
                        this.en = en;
                    }

                    public void setZh(String zh) {
                        this.zh = zh;
                    }

                    public void setMp3(String mp3) {
                        this.mp3 = mp3;
                    }

                    public void setSort(int sort) {
                        this.sort = sort;
                    }

                    public void setSeconds(int seconds) {
                        this.seconds = seconds;
                    }

                    public int getSentence_id() {
                        return sentence_id;
                    }

                    public String getEn() {
                        return en;
                    }

                    public String getZh() {
                        return zh;
                    }

                    public String getMp3() {
                        return mp3;
                    }

                    public int getSort() {
                        return sort;
                    }

                    public int getSeconds() {
                        return seconds;
                    }
                }
            }
        }
    }
}
