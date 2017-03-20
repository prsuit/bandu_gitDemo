package me.bandu.talk.android.phone.bean;

import java.util.List;

/**
 * 创建者：高楠
 * 时间：on 2015/12/15
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HomeWorkData {
    private String status;
    private String cid ;
    private String class_name;
    private String todo_count;
    private String total ;
    private List<HomeWorkEntity> list;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getTodo_count() {
        return todo_count;
    }

    public void setTodo_count(String todo_count) {
        this.todo_count = todo_count;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<HomeWorkEntity> getList() {
        return list;
    }

    public void setList(List<HomeWorkEntity> list) {
        this.list = list;
    }
}
