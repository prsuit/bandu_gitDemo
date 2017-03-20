package me.bandu.talk.android.phone.bean;

import android.os.Parcelable;

import com.DFHT.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * author by Mckiera
 * time: 2015/12/21  20:52
 * description:
 * updateTime:
 * update description:
 */
public class Detail extends BaseBean implements Serializable{

    private DataEntity data;

    /**
     * data : {"list":[{"en":"Show me green.","mp3":"http://test.new.media.bandu.in/mp3/rep/3/1/lessons/Unit2/U2141.mp3","sentence_id":"80","stu_done":false,"stu_mp3":"","stu_score":0},{"en":"Show me blue.","mp3":"http://test.new.media.bandu.in/mp3/rep/3/1/lessons/Unit2/U2142.mp3","sentence_id":"81","stu_done":false,"stu_mp3":"","stu_score":0},{"en":"Show me red.","mp3":"http://test.new.media.bandu.in/mp3/rep/3/1/lessons/Unit2/U2143.mp3","sentence_id":"82","stu_done":false,"stu_mp3":"","stu_score":0},{"en":"Show me yellow.","mp3":"http://test.new.media.bandu.in/mp3/rep/3/1/lessons/Unit2/U2144.mp3","sentence_id":"83","stu_done":false,"stu_mp3":"","stu_score":0}]}
     * msg : 成功
     * status : 1
     */


    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity implements Serializable{
        /**
         * en : Show me green.
         * mp3 : http://test.new.media.bandu.in/mp3/rep/3/1/lessons/Unit2/U2141.mp3
         * sentence_id : 80
         * stu_done : false
         * stu_mp3 :
         * stu_score : 0
         */

        private List<ListEntity> list;
        public int getCount(){
            return list==null?0:list.size();
        }
        public boolean setID(long stu_job_id,long hw_quiz_id,long quiz_id){
            if (list==null||(list!=null&&list.size()==0)){
                return false;
            }
            for (int i = 0 ;i<list.size();i++){
                list.get(i).setStu_job_id(stu_job_id);
                list.get(i).setHw_quiz_id(hw_quiz_id);
                list.get(i).setQuiz_id(quiz_id);
            }
            return true;
        }
        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity implements Serializable {
            private String en;  //text;//文本
            private String mp3;  //netAddress;//泛音地址
            private long sentence_id; //sentenceID;//句子ID
            private long hw_quiz_id;
            private long stu_job_id;  //taskID;//作业ID
            private long quiz_id;   //partID
            private boolean stu_done;  //false
            private String stu_mp3;    // myAddress;//我的声音本地地址     //弛声的网络声音地址
            private String words_score;  //textColor;//变色文本
            private String current_words_score; // //textColor;//变色文本
            private int seconds;   //myVoiceTime  我的声音时长
            private int current_stu_seconds;//myVoiceTime 我的声音时长
            private int stu_score;    //myNum;//我的分数
            private String current_mp3;// myAddress;//我的声音本地地址     //弛声的网络声音地址
            private int current_score;   //myNum;//我的分数
            private int current_type;  //判断myAddress && netAddress  都为null ?  给 DoWorkListAdapter.WORK_NOTDONE ....  :  DoWorkListAdapter.WORK_DONE
            private String mp3_url;////弛声的网络声音地址
            private String answer;//

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public String getMp3_url() {
                return mp3_url;
            }

            public void setMp3_url(String mp3_url) {
                this.mp3_url = mp3_url;
            }

            public long getQuiz_id() {
                return quiz_id;
            }

            public void setQuiz_id(long quiz_id) {
                this.quiz_id = quiz_id;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public int getCurrent_stu_seconds() {
                return current_stu_seconds;
            }

            public void setCurrent_stu_seconds(int current_stu_seconds) {
                this.current_stu_seconds = current_stu_seconds;
            }

            public String getCurrent_words_score() {
                return current_words_score;
            }

            public void setCurrent_words_score(String current_words_score) {
                this.current_words_score = current_words_score;
            }
            public int getCurrent_type() {
                return current_type;
            }

            public void setCurrent_type(int current_type) {
                this.current_type = current_type;
            }

            public String getWords_score() {
                return words_score;
            }

            public void setWords_score(String words_score) {
                this.words_score = words_score;
            }

            public long getHw_quiz_id() {
                return hw_quiz_id;
            }

            public void setHw_quiz_id(long hw_quiz_id) {
                this.hw_quiz_id = hw_quiz_id;
            }

            public long getStu_job_id() {
                return stu_job_id;
            }

            public void setStu_job_id(long stu_job_id) {
                this.stu_job_id = stu_job_id;
            }

            public String getCurrent_mp3() {
                return current_mp3;
            }

            public void setCurrent_mp3(String current_mp3) {
                this.current_mp3 = current_mp3;
            }

            public int getCurrent_score() {
                return current_score;
            }

            public void setCurrent_score(int current_score) {
                this.current_score = current_score;
            }

            public void setEn(String en) {
                this.en = en;
            }

            public void setMp3(String mp3) {
                this.mp3 = mp3;
            }

            public void setSentence_id(long sentence_id) {
                this.sentence_id = sentence_id;
            }

            public void setStu_done(boolean stu_done) {
                this.stu_done = stu_done;
            }

            public void setStu_mp3(String stu_mp3) {
                this.stu_mp3 = stu_mp3;
            }

            public void setStu_score(int stu_score) {
                this.stu_score = stu_score;
            }

            public String getEn() {
                return en;
            }

            public String getMp3() {
                return mp3;
            }

            public long getSentence_id() {
                return sentence_id;
            }

            public boolean isStu_done() {
                return stu_done;
            }

            public String getStu_mp3() {
                return stu_mp3;
            }

            public int getStu_score() {
                return stu_score;
            }

        }
    }
}
