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
public class SingleStatisBean extends BaseBean{


    /**
     * stat : {"a":0,"b":3,"c":0,"done":3,"total":4,"score_avg":80}
     * sentence_list : [{"sentence_id":63375,"en":"a lake","good_num":1},{"sentence_id":63373,"en":"a gate","good_num":1},{"sentence_id":63372,"en":"name","good_num":1},{"sentence_id":63374,"en":"a cake","good_num":0}]
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
         * a : 0
         * b : 3
         * c : 0
         * done : 3
         * total : 4
         * score_avg : 80
         */

        private StatEntity stat;
        /**
         * sentence_id : 63375
         * en : a lake
         * good_num : 1
         */

        private List<SentenceListEntity> sentence_list;

        public void setStat(StatEntity stat) {
            this.stat = stat;
        }

        public void setSentence_list(List<SentenceListEntity> sentence_list) {
            this.sentence_list = sentence_list;
        }

        public StatEntity getStat() {
            return stat;
        }

        public List<SentenceListEntity> getSentence_list() {
            return sentence_list;
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

        public static class SentenceListEntity {
            private int sentence_id;
            private String en;
            private int good_num;

            public void setSentence_id(int sentence_id) {
                this.sentence_id = sentence_id;
            }

            public void setEn(String en) {
                this.en = en;
            }

            public void setGood_num(int good_num) {
                this.good_num = good_num;
            }

            public int getSentence_id() {
                return sentence_id;
            }

            public String getEn() {
                return en;
            }

            public int getGood_num() {
                return good_num;
            }
        }
    }
}
