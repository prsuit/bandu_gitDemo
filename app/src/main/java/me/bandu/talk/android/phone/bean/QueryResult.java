package me.bandu.talk.android.phone.bean;

/**
 * 创建者：高楠
 * 时间：on 2015/12/15
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class QueryResult {
    private String cid;
    private String name;
    private String creator;

    public String getCid() {
        return "您要加的班级编号是："+cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return "班级："+name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return "创建人："+creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
