package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

/**
 * 创建者：wanglei
 * <p>时间：16/8/23  11:21
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class AppUpdateBean extends BaseBean {
    private UpdateDate data;

    public UpdateDate getData() {
        return data;
    }

    public void setData(UpdateDate data) {
        this.data = data;
    }

    public static class UpdateDate{
        private int version;//versionCode
        private String url;//
        private String versionname;//versionName
        private String description;//说明
        /**
         * true是强制更新 false不是强制更新
         */
        private boolean update;

        public String getVersionname() {
            return versionname;
        }

        public void setVersionname(String versionname) {
            this.versionname = versionname;
        }

        public boolean isUpdate() {
            return update;
        }

        public void setUpdate(boolean update) {
            this.update = update;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
