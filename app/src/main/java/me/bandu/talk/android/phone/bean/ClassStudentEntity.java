package me.bandu.talk.android.phone.bean;

import java.io.Serializable;

/**
 * 创建者：高楠
 * 时间：on 2015/12/16
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ClassStudentEntity implements Comparable,Serializable {
        private String avatar;
        private String name;
        private String uid;
    private String alpha;
    private boolean selected = false;

    @Override
    public String toString() {
        return "ClassStudentEntity{" +
                "avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", uid='" + uid + '\'' +
                ", alpha='" + alpha + '\'' +
                ", selected=" + selected +
                '}';
    }

    public String getAlpha() {
        return alpha;
    }

    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof ClassStudentEntity)){
            return -1;
        }
        char c1 = getAlpha().charAt(0);
        char c2 = ((ClassStudentEntity)o).getAlpha().charAt(0);
        if (c1 > c2) return 1;
        else if(c1 < c2) return -1;
        else return 0;
    }
}
