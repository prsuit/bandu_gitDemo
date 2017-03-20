package me.bandu.talk.android.phone.bean;

import java.util.List;

/**
 * 创建者：王兰新
 * 时间：20166/6/2
 * 类描述：作业统计Bean
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WorkStatisticsBean {

    /**
     * status : 1
     * data : {"done_total":10,"total":20,"joblist":[{"score":80,"total_time":120,"up_score":5,"status":1}],"weak_sentences":[{"sentence_id":1,"avg_score":10,"en_content":"helloworld"}]}
     * msg : 成功
     */

    private int status;
    /**
     * done_total : 10
     * total : 20
     * joblist : [{"score":80,"total_time":120,"up_score":5,"status":1}]
     * weak_sentences : [{"sentence_id":1,"avg_score":10,"en_content":"helloworld"}]
     */

    private DataBean data;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private int done_total;
        private int total;
        /**
         * score : 80
         * total_time : 120
         * up_score : 5
         * status : 1
         */

        private List<JoblistBean> joblist;
        /**
         * sentence_id : 1
         * avg_score : 10
         * en_content : helloworld
         */

        private List<WeakSentencesBean> weak_sentences;

        public int getDone_total() {
            return done_total;
        }

        public void setDone_total(int done_total) {
            this.done_total = done_total;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<JoblistBean> getJoblist() {
            return joblist;
        }

        public void setJoblist(List<JoblistBean> joblist) {
            this.joblist = joblist;
        }

        public List<WeakSentencesBean> getWeak_sentences() {
            return weak_sentences;
        }

        public void setWeak_sentences(List<WeakSentencesBean> weak_sentences) {
            this.weak_sentences = weak_sentences;
        }

        public static class JoblistBean {
            private int score;
            private int total_time;
            private int up_score;
            private int status;

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getTotal_time() {
                return total_time;
            }

            public void setTotal_time(int total_time) {
                this.total_time = total_time;
            }

            public int getUp_score() {
                return up_score;
            }

            public void setUp_score(int up_score) {
                this.up_score = up_score;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class WeakSentencesBean {
            private int sentence_id;
            private int avg_score;
            private String en_content;
            private String answer;

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public int getSentence_id() {
                return sentence_id;
            }

            public void setSentence_id(int sentence_id) {
                this.sentence_id = sentence_id;
            }

            public int getAvg_score() {
                return avg_score;
            }

            public void setAvg_score(int avg_score) {
                this.avg_score = avg_score;
            }

            public String getEn_content() {
                return en_content;
            }

            public void setEn_content(String en_content) {
                this.en_content = en_content;
            }
        }
    }
}
