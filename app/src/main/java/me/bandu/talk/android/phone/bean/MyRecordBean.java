package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fanYu on 2016/6/8.
 * 自己录音封装的bean对象
 */
public class MyRecordBean extends BaseBean implements Serializable{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * sentence_id : 123
         * mp3 : http://xxxx.wav
         * seconds : 12
         */

        private List<SentencesBean> sentences;

        public List<SentencesBean> getSentences() {
            return sentences;
        }

        public void setSentences(List<SentencesBean> sentences) {
            this.sentences = sentences;
        }

        public static class SentencesBean implements Serializable {
            private long sentence_id;
            private String mp3;
            private long seconds;
            private String en;
            private String head;


            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public String getEn() {
                return en;
            }

            public void setEn(String en) {
                this.en = en;
            }

            public long getSentence_id() {
                return sentence_id;
            }

            public void setSentence_id(long sentence_id) {
                this.sentence_id = sentence_id;
            }

            public String getMp3() {
                return mp3;
            }

            public void setMp3(String mp3) {
                this.mp3 = mp3;
            }

            public long getSeconds() {
                return seconds;
            }

            public void setSeconds(long seconds) {
                this.seconds = seconds;
            }
        }
    }
}
