package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

public class MyselfPullateSingleBean extends BaseBean {


    /**
     * status : 1
     * data : {"user_score":92,"class_avg_score":92,"user_spend":742,"class_avg_spend":742,"weak_sentences":[{"sentence_id":"28943","min_score":"0","en_content":"aah","words_score":"[{\"word\":\"aah\",\"score\":0}]"},{"sentence_id":"28945","min_score":"0","en_content":"ooh","words_score":"[{\"word\":\"ooh\",\"score\":0}]"},{"sentence_id":"28942","min_score":"78","en_content":"am(I'm=I am)","words_score":"[{\"word\":\"am\",\"score\":12},{\"word\":\"i'm\",\"score\":100},{\"word\":\"i\",\"score\":100},{\"word\":\"am\",\"score\":100}]"},{"sentence_id":"28946","min_score":"100","en_content":"goodbye (bye-bye)","words_score":"[{\"word\":\"goodbye\",\"score\":100},{\"word\":\"bye\",\"score\":100},{\"word\":\"bye\",\"score\":100}]"},{"sentence_id":"28941","min_score":"100","en_content":"I","words_score":"[{\"score\":100,\"word\":\"i\"}]"}]}
     * msg : 成功
     */

    private int status;
    /**
     * user_score : 92
     * class_avg_score : 92
     * user_spend : 742
     * class_avg_spend : 742
     * weak_sentences : [{"sentence_id":"28943","min_score":"0","en_content":"aah","words_score":"[{\"word\":\"aah\",\"score\":0}]"},{"sentence_id":"28945","min_score":"0","en_content":"ooh","words_score":"[{\"word\":\"ooh\",\"score\":0}]"},{"sentence_id":"28942","min_score":"78","en_content":"am(I'm=I am)","words_score":"[{\"word\":\"am\",\"score\":12},{\"word\":\"i'm\",\"score\":100},{\"word\":\"i\",\"score\":100},{\"word\":\"am\",\"score\":100}]"},{"sentence_id":"28946","min_score":"100","en_content":"goodbye (bye-bye)","words_score":"[{\"word\":\"goodbye\",\"score\":100},{\"word\":\"bye\",\"score\":100},{\"word\":\"bye\",\"score\":100}]"},{"sentence_id":"28941","min_score":"100","en_content":"I","words_score":"[{\"score\":100,\"word\":\"i\"}]"}]
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
        private int user_score;
        private int class_avg_score;
        private int user_spend;
        private int class_avg_spend;
        /**
         * sentence_id : 28943
         * min_score : 0
         * en_content : aah
         * words_score : [{"word":"aah","score":0}]
         */

        private List<WeakSentencesBean> weak_sentences;

        public int getUser_score() {
            return user_score;
        }

        public void setUser_score(int user_score) {
            this.user_score = user_score;
        }

        public int getClass_avg_score() {
            return class_avg_score;
        }

        public void setClass_avg_score(int class_avg_score) {
            this.class_avg_score = class_avg_score;
        }

        public int getUser_spend() {
            return user_spend;
        }

        public void setUser_spend(int user_spend) {
            this.user_spend = user_spend;
        }

        public int getClass_avg_spend() {
            return class_avg_spend;
        }

        public void setClass_avg_spend(int class_avg_spend) {
            this.class_avg_spend = class_avg_spend;
        }

        public List<WeakSentencesBean> getWeak_sentences() {
            return weak_sentences;
        }

        public void setWeak_sentences(List<WeakSentencesBean> weak_sentences) {
            this.weak_sentences = weak_sentences;
        }

        public static class WeakSentencesBean {
            private String sentence_id;
            private String min_score;
            private String en_content;
            private String words_score;

            public String getSentence_id() {
                return sentence_id;
            }

            public void setSentence_id(String sentence_id) {
                this.sentence_id = sentence_id;
            }

            public String getMin_score() {
                return min_score;
            }

            public void setMin_score(String min_score) {
                this.min_score = min_score;
            }

            public String getEn_content() {
                return en_content;
            }

            public void setEn_content(String en_content) {
                this.en_content = en_content;
            }

            public String getWords_score() {
                return words_score;
            }

            public void setWords_score(String words_score) {
                this.words_score = words_score;
            }
        }
    }
}
