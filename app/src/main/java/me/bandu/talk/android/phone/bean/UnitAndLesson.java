package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * 创建者：高楠
 * 时间：on 2015/12/23
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class UnitAndLesson extends BaseBean{

    /**
     * status : 1
     * data : {"list":[{"unit_id":"5","lesson_id":"29"}]}
     * msg : 成功
     */

    private UnitAndLessonData data;

    public void setData(UnitAndLessonData data) {
        this.data = data;
    }

    public UnitAndLessonData getData() {
        return data;
    }

    public static class UnitAndLessonData {
        /**
         * unit_id : 5
         * lesson_id : 29
         */

        private List<UnitAndLessonEntity> list;

        public void setList(List<UnitAndLessonEntity> list) {
            this.list = list;
        }

        public List<UnitAndLessonEntity> getList() {
            return list;
        }

        public static class UnitAndLessonEntity {
            private long unit_id;
            private long lesson_id;
            private String unit_name;

            public void setUnit_name(String unit_name) {
                this.unit_name = unit_name;
            }

            public String getUnit_name() {
                return unit_name;
            }

            public void setUnit_id(long unit_id) {
                this.unit_id = unit_id;
            }

            public void setLesson_id(long lesson_id) {
                this.lesson_id = lesson_id;
            }

            public long getUnit_id() {
                return unit_id;
            }

            public long getLesson_id() {
                return lesson_id;
            }
        }
    }
}
