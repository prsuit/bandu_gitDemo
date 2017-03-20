package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * author by Mckiera
 * time: 2015/12/29  15:16
 * description:
 * updateTime:
 * update description:
 */
public class DetailOfStudentBean extends BaseBean{


    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * quiz_id : 576
         * quiz_name : What does Tom do every day? Read.
         * sentences : [{"en":"Tom, it's time to get up and wash your face.","mp3":"http://media.bandu.cn/mp3/langwen/2/2/3/1/1/0.mp3","sentence_id":"4458","stu_done":true,"stu_mp3":"http://media.bandu.cn/eng/2016/01/20/2042186_369486.wav","stu_score":74,"stu_seconds":255},{"en":"What's the time, Mum?","mp3":"http://media.bandu.cn/mp3/langwen/2/2/3/1/1/1.mp3","sentence_id":"4459","stu_done":true,"stu_mp3":"http://media.bandu.cn/eng/2016/01/20/2042186_681340.wav","stu_score":84,"stu_seconds":255},{"en":"It's seven o'clock.","mp3":"http://media.bandu.cn/mp3/langwen/2/2/3/1/1/2.mp3","sentence_id":"4460","stu_done":true,"stu_mp3":"http://media.bandu.cn/eng/2016/01/20/2042186_142162.wav","stu_score":82,"stu_seconds":255},{"en":"It's time to have breakfast, Tom.","mp3":"http://media.bandu.cn/mp3/langwen/2/2/3/1/1/3.mp3","sentence_id":"4461","stu_done":true,"stu_mp3":"http://media.bandu.cn/eng/2016/01/19/2042186_980726.wav","stu_score":89,"stu_seconds":255},{"en":"What's the time?","mp3":"http://media.bandu.cn/mp3/langwen/2/2/3/1/1/4.mp3","sentence_id":"4462","stu_done":true,"stu_mp3":"http://media.bandu.cn/eng/2016/01/20/2042186_531412.wav","stu_score":94,"stu_seconds":255},{"en":"It's seven fifteen.","mp3":"http://media.bandu.cn/mp3/langwen/2/2/3/1/1/5.mp3","sentence_id":"4463","stu_done":true,"stu_mp3":"http://media.bandu.cn/eng/2016/01/20/2042186_661187.wav","stu_score":82,"stu_seconds":255},{"en":"Tom! It's time to go to school.","mp3":"http://media.bandu.cn/mp3/langwen/2/2/3/1/1/6.mp3","sentence_id":"4464","stu_done":true,"stu_mp3":"http://media.bandu.cn/eng/2016/01/19/2042186_903374.wav","stu_score":87,"stu_seconds":255},{"en":"What's the time?","mp3":"http://media.bandu.cn/mp3/langwen/2/2/3/1/1/7.mp3","sentence_id":"4465","stu_done":true,"stu_mp3":"http://media.bandu.cn/eng/2016/01/20/2042186_24951.wav","stu_score":69,"stu_seconds":255},{"en":"It's half past seven.","mp3":"http://media.bandu.cn/mp3/langwen/2/2/3/1/1/8.mp3","sentence_id":"4466","stu_done":true,"stu_mp3":"http://media.bandu.cn/eng/2016/01/19/2042186_199235.wav","stu_score":48,"stu_seconds":255}]
         * types : [{"times":1,"type":"Reading"},{"times":1,"type":"repeat"}]
         */

        private List<ListEntity> list;

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            private String quiz_id;
            private int type_code; //新题型2，普通题型0
            private String quiz_name;
            /**
             * en : Tom, it's time to get up and wash your face.
             * mp3 : http://media.bandu.cn/mp3/langwen/2/2/3/1/1/0.mp3
             * sentence_id : 4458
             * stu_done : true
             * stu_mp3 : http://media.bandu.cn/eng/2016/01/20/2042186_369486.wav
             * stu_score : 74
             * stu_seconds : 255
             */

            private List<SentencesEntity> sentences;
            /**
             * times : 1
             * type : Reading
             */

            private List<TypesEntity> types;

            public int getType_code() {
                return type_code;
            }

            public void setType_code(int type_code) {
                this.type_code = type_code;
            }

            public void setQuiz_id(String quiz_id) {
                this.quiz_id = quiz_id;
            }

            public void setQuiz_name(String quiz_name) {
                this.quiz_name = quiz_name;
            }

            public void setSentences(List<SentencesEntity> sentences) {
                this.sentences = sentences;
            }

            public void setTypes(List<TypesEntity> types) {
                this.types = types;
            }

            public String getQuiz_id() {
                return quiz_id;
            }

            public String getQuiz_name() {
                return quiz_name;
            }

            public List<SentencesEntity> getSentences() {
                return sentences;
            }

            public List<TypesEntity> getTypes() {
                return types;
            }

            public static class SentencesEntity {
                private String en;
                private String mp3;
                private String sentence_id;
                private boolean stu_done;
                private String stu_mp3;
                private int stu_score;
                private int stu_seconds;
                private String words_score;

                public String getWords_score() {
                    return words_score;
                }

                public void setWords_score(String words_score) {
                    this.words_score = words_score;
                }

                public void setEn(String en) {
                    this.en = en;
                }

                public void setMp3(String mp3) {
                    this.mp3 = mp3;
                }

                public void setSentence_id(String sentence_id) {
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

                public void setStu_seconds(int stu_seconds) {
                    this.stu_seconds = stu_seconds;
                }

                public String getEn() {
                    return en;
                }

                public String getMp3() {
                    return mp3;
                }

                public String getSentence_id() {
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

                public int getStu_seconds() {
                    return stu_seconds;
                }
            }

            public static class TypesEntity {
                private int times;
                private String type;

                public void setTimes(int times) {
                    this.times = times;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public int getTimes() {
                    return times;
                }

                public String getType() {
                    return type;
                }
            }
        }
    }
}
