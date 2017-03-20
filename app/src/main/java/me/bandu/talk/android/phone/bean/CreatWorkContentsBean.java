package me.bandu.talk.android.phone.bean;

import java.util.List;

public class CreatWorkContentsBean {

    /**
     * status : 1
     * data : {"quiz_list":[{"quiz_id":"1","sentence_list":[{"sentence_id":1,"en":"Hello! I'm Zoom.","mp3":"http://media.jyc.bandu.in/mp3/rep/3/1/lessons/Unit1/U1001.mp3"},{"sentence_id":2,"en":"Hi! My name's Zip.","mp3":"http://media.jyc.bandu.in/mp3/rep/3/1/lessons/Unit1/U1002.mp3"}]},{"quiz_id":"2","sentence_list":[{"sentence_id":3,"en":"Hello! I'm Miss White.","mp3":"http://media.jyc.bandu.in/mp3/rep/3/1/lessons/Unit1/U1111.mp3"},{"sentence_id":4,"en":"Hello, I'm Wu Yifan.","mp3":"http://media.jyc.bandu.in/mp3/rep/3/1/lessons/Unit1/U1112.mp3"},{"sentence_id":5,"en":"Hi, I'm Sarah.","mp3":"http://media.jyc.bandu.in/mp3/rep/3/1/lessons/Unit1/U1113.mp3"}]}]}
     * msg : 成功
     */

    private int status;
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
        /**
         * quiz_id : 1
         * sentence_list : [{"sentence_id":1,"en":"Hello! I'm Zoom.","mp3":"http://media.jyc.bandu.in/mp3/rep/3/1/lessons/Unit1/U1001.mp3"},{"sentence_id":2,"en":"Hi! My name's Zip.","mp3":"http://media.jyc.bandu.in/mp3/rep/3/1/lessons/Unit1/U1002.mp3"}]
         */

        private List<QuizListBean> quiz_list;

        public List<QuizListBean> getQuiz_list() {
            return quiz_list;
        }

        public void setQuiz_list(List<QuizListBean> quiz_list) {
            this.quiz_list = quiz_list;
        }

        public static class QuizListBean {
            private String quiz_id;
            /**
             * sentence_id : 1
             * en : Hello! I'm Zoom.
             * mp3 : http://media.jyc.bandu.in/mp3/rep/3/1/lessons/Unit1/U1001.mp3
             */

            private List<SentenceListBean> sentence_list;

            public String getQuiz_id() {
                return quiz_id;
            }

            public void setQuiz_id(String quiz_id) {
                this.quiz_id = quiz_id;
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
                private String mp3;
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

                public String getEn() {
                    return en;
                }

                public void setEn(String en) {
                    this.en = en;
                }

                public String getMp3() {
                    return mp3;
                }

                public void setMp3(String mp3) {
                    this.mp3 = mp3;
                }
            }
        }
    }
}
