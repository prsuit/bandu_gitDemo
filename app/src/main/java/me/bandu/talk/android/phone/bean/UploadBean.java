package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

/**
 * 创建者：高楠
 * 时间：on 2016/4/19
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class UploadBean extends BaseBean {


    /**
     * zip : 20101_140123456.zip
     * commit_time : 2016-04-20 17:00:00
     * score : 87
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String zip;
        private String commit_time;
        private int score;

        public void setZip(String zip) {
            this.zip = zip;
        }

        public void setCommit_time(String commit_time) {
            this.commit_time = commit_time;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getZip() {
            return zip;
        }

        public String getCommit_time() {
            return commit_time;
        }

        public int getScore() {
            return score;
        }
    }
}
