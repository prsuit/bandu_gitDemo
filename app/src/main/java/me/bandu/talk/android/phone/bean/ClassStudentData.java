package me.bandu.talk.android.phone.bean;

/**
 * 创建者：高楠
 * 时间：on 2015/12/16
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ClassStudentData {
    private ClassStudentNormalEntity nomal;
    private ClassStudentWaitingEntity watting;

    @Override
    public String toString() {
        return "ClassStudentData{" +
                "nomal=" + nomal +
                ", watting=" + watting +
                '}';
    }

    public ClassStudentNormalEntity getNomal() {
        return nomal;
    }

    public void setNomal(ClassStudentNormalEntity nomal) {
        this.nomal = nomal;
    }

    public ClassStudentWaitingEntity getWatting() {
        return watting;
    }

    public void setWatting(ClassStudentWaitingEntity watting) {
        this.watting = watting;
    }
}
