package me.bandu.talk.android.phone.bean;

/**
 * 创建者：wanglei
 * <p>时间：16/5/25  10:42
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class GraphList {

    private String topNum;//上面的数字
    private String bottomNum;//下面的数字
    private String column;//柱子的高度

    public String getJob_id() {
        return job_id;
    }

    private String job_id;//作业ID

    public String getTopNum() {
        return topNum;
    }

    public void setTopNum(String topNum) {
        this.topNum = topNum;
    }

    public String getBottomNum() {
        return bottomNum;
    }

    public void setBottomNum(String bottomNum) {
        this.bottomNum = bottomNum;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }
}
