package me.bandu.talk.android.phone.bean;

import java.io.Serializable;

/**
 * 创建者:taoge
 * 时间：2015/12/7
 * 类描述：
 * 修改人:taoge
 * 修改时间：2015/12/7
 * 修改备注：
 */
public class Address implements Serializable {
    private String id;
    private String address;
    private String sortLetters;
    private boolean isSelect = true;

    public Address(String address) {
        this.address = address;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
