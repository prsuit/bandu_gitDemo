package me.bandu.talk.android.phone.bean;

/**
 * 创建者：高楠
 * 时间：on 2015/12/21
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class RepeatEntity {
    private int hw_quiz_id;
    private int times;
    private int count;
    private boolean is_done;

    public boolean is_done() {
        return is_done;
    }

    public void setIs_done(boolean is_done) {
        this.is_done = is_done;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getHw_quiz_id() {
        return hw_quiz_id;
    }

    public void setHw_quiz_id(int hw_quiz_id) {
        this.hw_quiz_id = hw_quiz_id;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}