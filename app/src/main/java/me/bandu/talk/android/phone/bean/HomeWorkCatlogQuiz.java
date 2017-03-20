package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

/**
 * 创建者：高楠
 * 时间：on 2015/11/30
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HomeWorkCatlogQuiz extends BaseBean implements Comparable{

    private String name;
    private String quiz_id;
    private String description;
    private String quiz_type;
    private ReadingEntity Reading;
    private RepeatEntity repeat;
    private ReciteEntity recite;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuiz_type() {
        return quiz_type;
    }

    public void setQuiz_type(String quiz_type) {
        this.quiz_type = quiz_type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuiz_id(String quiz_id) {
        this.quiz_id = quiz_id;
    }

    public void setReading(ReadingEntity Reading) {
        this.Reading = Reading;
    }

    public void setRepeat(RepeatEntity repeat) {
        this.repeat = repeat;
    }

    public void setRecite(ReciteEntity recite) {
        this.recite = recite;
    }

    public String getName() {
        return name;
    }

    public String getQuiz_id() {
        return quiz_id;
    }

    public ReadingEntity getReading() {
        return Reading;
    }

    public RepeatEntity getRepeat() {
        return repeat;
    }

    public ReciteEntity getRecite() {
        return recite;
    }

    @Override
    public int compareTo(Object another) {
        if (!(another instanceof HomeWorkCatlogQuiz)){
            return  -1;
        }
        if (((HomeWorkCatlogQuiz) another).getQuiz_id().equals(getQuiz_id())){
            return 0;
        }
        return -1;
    }
}
