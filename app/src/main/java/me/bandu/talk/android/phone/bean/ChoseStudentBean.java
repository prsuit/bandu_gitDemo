package me.bandu.talk.android.phone.bean;

/**
 * 创建者：高楠
 * 时间：on 2016/1/27
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ChoseStudentBean {
    private String name ;
    private int score;
    private boolean isSelect;
    private boolean isGroup = false;

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public ChoseStudentBean(String name, int score, boolean isSelect) {
        this.name = name;
        this.score = score;
        this.isSelect = isSelect;
    }
    public ChoseStudentBean(){
    }

    public String getName() {
        if (isGroup()){
            if (name.equals("A")){
                return "A（优秀）";
            }
            if (name.equals("B")){
                return "B（良好）";
            }
            if (name.equals("C")){
                return "C（一般）";
            }
            return "";
        }else {
            return name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
