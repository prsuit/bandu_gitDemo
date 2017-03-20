package me.bandu.talk.android.phone.bean;

import java.util.List;

/**
 * 创建者：高楠
 * 时间：on 2015/12/16
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ClassStudentNormalEntity {
    private List<ClassStudentEntity> list;
    private String total;

    @Override
    public String toString() {
        return "ClassStudentWaitingEntity{" +
                "list=" + list +
                ", total='" + total + '\'' +
                '}';
    }

    public List<ClassStudentEntity> getList() {
        return list;
    }

    public void setList(List<ClassStudentEntity> list) {
        this.list = list;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
