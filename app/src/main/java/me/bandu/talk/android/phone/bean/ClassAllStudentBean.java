package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * 创建者：gaoye
 * 时间：2015/11/26  14:24
 * 类描述：班级学生
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ClassAllStudentBean extends BaseBean {

    /**
     * data : {"nomal":{"list":[{"uid":20101,"name":"及永超","avatar":"http://www.bandu.cn/xxxxx"},{}],"total":20},"watting":{"list":[{"uid":20102,"name":"王小明","avatar":"http://www.bandu.cn/xxxxx"},{}],"total":2}}
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * nomal : {"list":[{"uid":20101,"name":"及永超","avatar":"http://www.bandu.cn/xxxxx"},{}],"total":20}
         * watting : {"list":[{"uid":20102,"name":"王小明","avatar":"http://www.bandu.cn/xxxxx"},{}],"total":2}
         */

        private StudentEntity nomal;
        private VerifyStudentEntity watting;

        public StudentEntity getNomal() {
            return nomal;
        }

        public VerifyStudentEntity getWatting() {
            return watting;
        }

        public void setNomal(StudentEntity nomal) {
            this.nomal = nomal;
        }

        public void setWatting(VerifyStudentEntity watting) {
            this.watting = watting;
        }

        public static class StudentEntity {
            /**
             * list : [{"uid":20101,"name":"及永超","avatar":"http://www.bandu.cn/xxxxx"},{}]
             * total : 20
             */

            private int total;
            private List<TStudentBean> list;

            public List<TStudentBean> getList() {
                return list;
            }

            public int getTotal() {
                return total;
            }

            public void setList(List<TStudentBean> list) {
                this.list = list;
            }

            public void setTotal(int total) {
                this.total = total;
            }
        }

        public static class VerifyStudentEntity {
            /**
             * list : [{"uid":20102,"name":"王小明","avatar":"http://www.bandu.cn/xxxxx"},{}]
             * total : 2
             */

            private int total;
            private List<TStudentBean> list;

            public List<TStudentBean> getList() {
                return list;
            }

            public int getTotal() {
                return total;
            }

            public void setList(List<TStudentBean> list) {
                this.list = list;
            }

            public void setTotal(int total) {
                this.total = total;
            }
        }
    }
}
