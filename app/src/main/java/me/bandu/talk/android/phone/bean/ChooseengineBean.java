package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

/**
 * 创建者：wanglei
 * <p>时间：16/8/18  10:07
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class ChooseengineBean extends BaseBean {
    private ChooseengineData data;

    public ChooseengineData getData() {
        return data;
    }

    public void setData(ChooseengineData data) {
        this.data = data;
    }

    public class ChooseengineData {
        private int engine;

        public int getEngine() {
            return engine;
        }

        public void setEngine(int engine) {
            this.engine = engine;
        }
    }
}
