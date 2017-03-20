package me.bandu.talk.android.phone.bean;


/**
 * 创建者：高楠
 * 时间：on 2015/11/30
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class LessonInfoEntity{
    private String quiz_id;
    private String quiz_name;
    private int sentences_count;


    public int getSentences_count() {
        return sentences_count;
    }

    public void setSentences_count(int sentences_count) {
        this.sentences_count = sentences_count;
    }

    public String getQuiz_name() {
        return quiz_name;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    public String getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(String quiz_id) {
        this.quiz_id = quiz_id;
    }

    @Override
    public String toString() {
        return "HomeWorkCatlogQuiz{" +
                "quiz_id='" + quiz_id + '\'' +
                ", quiz_name='" + quiz_name + '\'' +
                '}';
    }
}
