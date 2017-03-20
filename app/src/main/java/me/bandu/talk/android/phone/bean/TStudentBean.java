package me.bandu.talk.android.phone.bean;

import java.io.Serializable;

/**
 * 创建者：gaoye
 * 时间：2015/11/25  09:49
 * 类描述：一些类 为了无网络数据显示的 提供接口后会修改或者删除
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TStudentBean implements Comparable,Serializable{
    private long uid;
    private String name;
    private String avatar;
    private String alpha;
    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUid() {
        return uid;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getAlpha() {
        return alpha;
    }

    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof TStudentBean)){
            return -1;
        }
        if (getAlpha().length() <= 0){
            return -1;
        }
        if (((TStudentBean)o).getAlpha().length() <= 0){
            return 1;
        }
        char c1 = getAlpha().charAt(0);
        char c2 = ((TStudentBean)o).getAlpha().charAt(0);
        if (c1 > c2) return 1;
        else if(c1 < c2) return -1;
        else return 0;
    }
}
