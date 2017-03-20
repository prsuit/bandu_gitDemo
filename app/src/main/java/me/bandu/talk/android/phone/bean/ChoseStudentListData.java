package me.bandu.talk.android.phone.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：高楠
 * 时间：on 2016/2/19
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ChoseStudentListData implements Serializable {

        private List<ChoseStudentListEntity> a;
        private List<ChoseStudentListEntity> b;
        private List<ChoseStudentListEntity> c;
        public void setSelections(List<Integer> selections){
            if (selections==null||selections.size()==0)
                return;
            if (a!=null&&a.size()!=0)
                for (int i = 0;i<a.size();i++){
                    for (int j = 0;j<selections.size();j++){
                        if (a.get(i).getStu_job_id()==selections.get(j)){
                            a.get(i).setSelect(true);
//                            selections.remove(j);
                            break;
                        }
                    }
                }
            if (b!=null&&b.size()!=0)
                for (int i = 0;i<b.size();i++){
                    for (int j = 0;j<selections.size();j++){
                        if (b.get(i).getStu_job_id()==selections.get(j)){
                            b.get(i).setSelect(true);
//                            selections.remove(j);
                            break;
                        }
                    }
                }
            if (c!=null&&c.size()!=0)
                for (int i = 0;i<c.size();i++){
                    for (int j = 0;j<selections.size();j++){
                        if (c.get(i).getStu_job_id()==selections.get(j)){
                            c.get(i).setSelect(true);
//                            selections.remove(j);
                            break;
                        }
                    }
                }
        }
        public List<ChoseStudentListEntity> getB() {
            if (b!=null)
                for (int i =  0;i<b.size();i++){
                    ChoseStudentListEntity entity = b.get(i);
                    entity.setLetter("B");
                }
            return b;
        }

        public void setB(List<ChoseStudentListEntity> b) {
            this.b = b;
        }

        public List<ChoseStudentListEntity> getC() {
            if (c!=null)
                for (int i =  0;i<c.size();i++){
                    ChoseStudentListEntity entity = c.get(i);
                    entity.setLetter("C");
                }
            return c;
        }

        public void setC(List<ChoseStudentListEntity> c) {
            this.c = c;
        }

        public void setA(List<ChoseStudentListEntity> a) {
            this.a = a;
        }

        public List<ChoseStudentListEntity> getA() {
            if (a!=null)
                for (int i =  0;i<a.size();i++){
                    ChoseStudentListEntity entity = a.get(i);
                    entity.setLetter("A");
                }
            return a;
        }
}
