package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * 创建者：gaoye
 * 时间：2015/11/27  15:05
 * 类描述：待审核学生
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class VerifyStudentsBean extends BaseBean{

    /**
     * data : {"list":[{"uid":"20101","stu_name":"及永超","avatar":"http://www.bandu.cn/student/20101.jpg"},{},{}]}
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
         * list : [{"uid":"20101","stu_name":"及永超","avatar":"http://www.bandu.cn/student/20101.jpg"},{},{}]
         */

        private List<TStudentBean> list;

        public void setList(List<TStudentBean> list) {
            this.list = list;
        }

        public List<TStudentBean> getList() {
            return list;
        }

    }
}
