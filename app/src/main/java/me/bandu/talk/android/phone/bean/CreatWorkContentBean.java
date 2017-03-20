package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * 创建者：高楠
 * 时间：on 2016/4/8
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CreatWorkContentBean extends BaseBean{




    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * quiz_id : 1
         * sentence_list : [{"en":"Hello! I'm Zoom.","mp3":"http://media.jyc.bandu.in/mp3/rep/3/1/lessons/Unit1/U1001.mp3"},{"en":"Hi! My name's Zip.","mp3":"http://media.jyc.bandu.in/mp3/rep/3/1/lessons/Unit1/U1002.mp3"}]
         */

        private List<QuizListEntity> quiz_list;

        public void setQuizNameType(List<Quiz> quizs){
            if (quiz_list==null||quizs==null||quiz_list.size()==0||quizs.size()==0){
                return;
            }
            for (int i = 0; i < quiz_list.size();i++){
                QuizListEntity quizListEntity = quiz_list.get(i);
                for (int j = 0 ; j < quizs.size();j++){
                    Quiz quiz = quizs.get(j);
                    if (quizListEntity.getQuiz_id().equals(quiz.getQuiz_id())){
                        quizListEntity.setQuiz_name(quiz.getQuiz_name());
                        String str = "";
                        if (quiz.getRead_count()>0){
                            str = str+"朗读"+quiz.getRead_count()+"遍";
                        }
                        if (quiz.getRepeat_count()>0){
                            if (str.length()>0){
                                str = str+" ";
                            }
                            str = str+"跟读"+quiz.getRepeat_count()+"遍";
                        }
                        if (quiz.isRecit()){
                            if (str.length()>0){
                                str = str+" ";
                            }
                            str = str+"背诵1遍";
                        }
                        quizListEntity.setQuiz_type(str);
                        quizs.remove(j);
                    }
                }
            }
        }

        public void setQuiz_list(List<QuizListEntity> quiz_list) {
            this.quiz_list = quiz_list;
        }

        public List<QuizListEntity> getQuiz_list() {
            return quiz_list;
        }

        public static class QuizListEntity {
            private String quiz_id;
            private String quiz_name;
            private String quiz_type;

            public String getQuiz_name() {
                return quiz_name;
            }

            public void setQuiz_name(String quiz_name) {
                this.quiz_name = quiz_name;
            }

            public String getQuiz_type() {
                return quiz_type;
            }

            public void setQuiz_type(String quiz_type) {
                this.quiz_type = quiz_type;
            }

            /**
             * en : Hello! I'm Zoom.
             * mp3 : http://media.jyc.bandu.in/mp3/rep/3/1/lessons/Unit1/U1001.mp3
             */

            private List<SentenceListEntity> sentence_list;

            public void setQuiz_id(String quiz_id) {
                this.quiz_id = quiz_id;
            }

            public void setSentence_list(List<SentenceListEntity> sentence_list) {
                this.sentence_list = sentence_list;
            }

            public String getQuiz_id() {
                return quiz_id;
            }

            public List<SentenceListEntity> getSentence_list() {
                return sentence_list;
            }

            public static class SentenceListEntity {
                private String en;
                private String mp3;

                public void setEn(String en) {
                    this.en = en;
                }

                public void setMp3(String mp3) {
                    this.mp3 = mp3;
                }

                public String getEn() {
                    return en;
                }

                public String getMp3() {
                    return mp3;
                }
            }
        }
    }
}
