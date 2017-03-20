package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * 创建者:taoge
 * 时间：2015/12/18
 * 类描述：
 * 修改人:taoge
 * 修改时间：2015/12/18
 * 修改备注：
 */
public class WorkStatisBean extends BaseBean{


    /**
     * stat : {"a":3,"b":4,"c":5,"done":12,"total":20,"score_avg":88}
     * quiz_list : [{"quiz_id":1,"quiz_name":"Lets play"},{"quiz_id":2,"quiz_name":"lets go"}]
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
         * a : 3
         * b : 4
         * c : 5
         * done : 12
         * total : 20
         * score_avg : 88
         */

        private StatEntity stat;
        /**
         * quiz_id : 1
         * quiz_name : Lets play
         */

        private List<QuizListEntity> quiz_list;
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setStat(StatEntity stat) {
            this.stat = stat;
        }

        public void setQuiz_list(List<QuizListEntity> quiz_list) {
            this.quiz_list = quiz_list;
        }

        public StatEntity getStat() {
            return stat;
        }

        public List<QuizListEntity> getQuiz_list() {
            return quiz_list;
        }

        public static class StatEntity {
            private int a;
            private int b;
            private int c;
            private int done;
            private int total;
            private int score_avg;


            public void setA(int a) {
                this.a = a;
            }

            public void setB(int b) {
                this.b = b;
            }

            public void setC(int c) {
                this.c = c;
            }

            public void setDone(int done) {
                this.done = done;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public void setScore_avg(int score_avg) {
                this.score_avg = score_avg;
            }

            public int getA() {
                return a;
            }

            public int getB() {
                return b;
            }

            public int getC() {
                return c;
            }

            public int getDone() {
                return done;
            }

            public int getTotal() {
                return total;
            }

            public int getScore_avg() {
                return score_avg;
            }
        }

        public static class QuizListEntity {
            private int quiz_id;
            private String quiz_name;

            public void setQuiz_id(int quiz_id) {
                this.quiz_id = quiz_id;
            }

            public void setQuiz_name(String quiz_name) {
                this.quiz_name = quiz_name;
            }

            public int getQuiz_id() {
                return quiz_id;
            }

            public String getQuiz_name() {
                return quiz_name;
            }
        }
    }
}
