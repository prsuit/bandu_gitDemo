package me.bandu.talk.android.phone.bean;

/**
 * 创建者:taoge
 * 时间：2016/3/18
 * 类描述：
 * 修改人:taoge
 * 修改时间：2016/3/18
 * 修改备注：
 */
public class TagBean {
    int tag_id;
    String tag;
    boolean isSelect;

    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
