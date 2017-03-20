package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

/**
 * 创建者:taoge
 * 时间：2016/1/4
 * 类描述：
 * 修改人:taoge
 * 修改时间：2016/1/4
 * 修改备注：
 */
public class VersionBean extends BaseBean{

    /**
     * version : 26
     * url : http://www.bandu.cn/apk/bandu2.6.apk
     * description : 这是最新版的app
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int version;
        private String url;
        private String description;

        public void setVersion(int version) {
            this.version = version;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getVersion() {
            return version;
        }

        public String getUrl() {
            return url;
        }

        public String getDescription() {
            return description;
        }
    }
}
