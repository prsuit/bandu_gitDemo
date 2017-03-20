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
public class ClassStudentBean extends BaseBean {

    /**
     * data : {"stu_list":[{"uid":20101,"stu_name":"及永超","avatar":"http://www.bandu.cn/student/20101.jpg"},{}],"dsh_stu_num":10}
     */

    private ClassStudentData data;

    @Override
    public String toString() {
        return "ClassStudentBean{" +
                "data=" + data +
                '}';
    }

    public ClassStudentData getData() {
        return data;
    }

    public void setData(ClassStudentData data) {
        this.data = data;
    }
}
