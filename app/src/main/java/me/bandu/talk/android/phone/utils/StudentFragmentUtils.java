package me.bandu.talk.android.phone.utils;

import android.text.TextUtils;

import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.adapter.StudentWorkAdapter;
import me.bandu.talk.android.phone.bean.HomeWorkBean;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.bean.UserClassListBean;
import me.bandu.talk.android.phone.fragment.StudentWorkFragment;
import me.bandu.talk.android.phone.greendao.bean.ClassTabelBean;
import me.bandu.talk.android.phone.greendao.bean.TaskListBean;
import me.bandu.talk.android.phone.greendao.dao.ClassTabelDao;
import me.bandu.talk.android.phone.greendao.utils.DBUtils;

public class StudentFragmentUtils {
    private static final int GET_CLASS_DB = 101;
    private static final int UP_TOP_RED = 107;

    //存班级列表
    public static void setDbClassData(final List<UserClassListBean.DataBean.ClassListBean> net_data, final LoginBean.DataEntity uerInfo,final StudentWorkAdapter adapter, final  UserClassListBean.DataBean.ClassListBean curr_class) {
        DBUtils.getDbUtils().getClassListInfo(new DBUtils.inter() {
            @Override
            public void getList(List<?> list, boolean bb, int sign) {
                ArrayList<ClassTabelDao> db_data = (ArrayList<ClassTabelDao>) list;
                if (db_data != null)
                    LogUtils.e("======查询到多少数据呢??? "+ db_data.size());
                else
                    LogUtils.e("=========没有查到数据");
                if (sign == GET_CLASS_DB && list != null && list.size() != 0) {
                    ClassTabelBean bean = new ClassTabelBean();
                    bean.setUserID(Long.valueOf(uerInfo.getUid()));
                    bean.setShowReddot(false);
                    bean.setClassID(Long.valueOf(uerInfo.getCid()));
                    bean.setClassName(curr_class.getClass_name());
                    bean.setClassState(curr_class.getStatus());
                    bean.setTaskLatestTime(Long.parseLong(curr_class.getLast_job_time()));
                    DBUtils.getDbUtils().leaveHomeClassTabel(Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()),bean);

//                    List<ClassTabelBean> class_list = new ArrayList();
//                    for (int i = 0; i < net_data.size(); i++) {
//                        ClassTabelBean bean = new ClassTabelBean();
//                        UserClassListBean.DataBean.ClassListBean classListBean = net_data.get(i);
//                        bean.setUserID(Long.parseLong(UserUtil.getUid()));
//                        bean.setClassID(Long.parseLong(String.valueOf(classListBean.getCid())));
//                        bean.setTaskLatestTime(Long.parseLong(WorkCatalogUtils.date2TimeStamp(classListBean.getLast_job_time(), "yyyy-MM-dd HH:mm:ss")));
//                        for (ClassTabelBean db : db_data) {
//                            if ((db.getClassID() == net_data.get(i).getCid())
//                                    &&
//                                    !(net_data.get(i).getLast_job_time().equals(db.getTaskLatestTime() + ""))
//                                    ) {
//                                bean.setShowReddot(true);
//                            } else {
//                                bean.setShowReddot(false);
//                            }
//                        }
//
//                       /* for (int j = 0; j < list.size(); j++) {
//                            //同一个班级  最后布置时间不同
//                            if (db_data.get(j).getClassID() == classListBean.getCid() && db_data.get(j).getTaskLatestTime() != Long.parseLong(classListBean.getLast_job_time())) {
//                                bean.setShowReddot(true);
//                            } else {
//                                bean.setShowReddot(false);
//                            }
//                        }*/
//                        bean.setClassState(classListBean.getStatus());
//                        bean.setClassName(classListBean.getClass_name());
//                        class_list.add(bean);
//                    }
//                    LogUtils.e("******插入了多少数据呢???" + class_list.size());
//                   DBUtils.getDbUtils().leaveHomeClassTabel(Long.parseLong(uerInfo.getUid()), class_list);
                } else {
                    List<ClassTabelBean> db_list = new ArrayList<>();
                    for (int i = 0; i < net_data.size(); i++) {
                        ClassTabelBean bean = new ClassTabelBean();
                        UserClassListBean.DataBean.ClassListBean classListBean = net_data.get(i);
                        bean.setUserID(Long.parseLong(uerInfo.getUid()));
                        bean.setClassID(classListBean.getCid());
                        String taskLatestTime = DateUtils.parseTime(classListBean.getLast_job_time());
//                        String taskLatestTime = WorkCatalogUtils.date2TimeStamp(classListBean.getLast_job_time(), "yyyy-MM-dd HH:mm:ss");
                        bean.setTaskLatestTime(Long.parseLong(taskLatestTime));
                        if (Integer.parseInt(uerInfo.getCid()) == classListBean.getCid())
                            bean.setShowReddot(false);
                        else
                            bean.setShowReddot(true);
                        bean.setClassState(classListBean.getStatus());
                        bean.setClassName(classListBean.getClass_name());
                        db_list.add(bean);
                    }
                    LogUtils.e("******在没有数据的情况下   插入了多少数据呢???" + db_list.size());
                    DBUtils.getDbUtils().leaveHomeClassTabel(Long.parseLong(uerInfo.getUid()), db_list);
                }
            }
            @Override
            public void isHaveNum(int state, List<?> list, boolean b,final TaskListBean taskListBean, int sign) {

            }
        },GET_CLASS_DB,Long.valueOf(uerInfo.getUid()));


     /*   DBUtils.getDbUtils().getHomeListInfo(new DBUtils.inter() {
            @Override
            public void getList(List<?> list, boolean bb, int sign) {
                ArrayList<ClassTabelBean> db_data = (ArrayList<ClassTabelBean>) list;
                if (db_data != null)
                LogUtils.e("======查询到多少数据呢??? "+ db_data.size());
                else
                LogUtils.e("=========没有查到数据");
                if (sign == GET_CLASS_DB && list != null && list.size() != 0) {
                    ClassTabelBean bean = new ClassTabelBean();
                    bean.setUserID(Long.valueOf(uerInfo.getUid()));
                    bean.setShowReddot(false);
                    bean.setClassID(Long.valueOf(uerInfo.getCid()));
                    bean.setClassName(curr_class.getClass_name());
                    bean.setClassState(curr_class.getStatus());
                    bean.setTaskLatestTime(Long.valueOf(curr_class.getLast_job_time()));
                    DBUtils.getDbUtils().leaveHomeClassTabel(Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()),bean);

//                    List<ClassTabelBean> class_list = new ArrayList();
//                    for (int i = 0; i < net_data.size(); i++) {
//                        ClassTabelBean bean = new ClassTabelBean();
//                        UserClassListBean.DataBean.ClassListBean classListBean = net_data.get(i);
//                        bean.setUserID(Long.parseLong(UserUtil.getUid()));
//                        bean.setClassID(Long.parseLong(String.valueOf(classListBean.getCid())));
//                        bean.setTaskLatestTime(Long.parseLong(WorkCatalogUtils.date2TimeStamp(classListBean.getLast_job_time(), "yyyy-MM-dd HH:mm:ss")));
//                        for (ClassTabelBean db : db_data) {
//                            if ((db.getClassID() == net_data.get(i).getCid())
//                                    &&
//                                    !(net_data.get(i).getLast_job_time().equals(db.getTaskLatestTime() + ""))
//                                    ) {
//                                bean.setShowReddot(true);
//                            } else {
//                                bean.setShowReddot(false);
//                            }
//                        }
//
//                       *//* for (int j = 0; j < list.size(); j++) {
//                            //同一个班级  最后布置时间不同
//                            if (db_data.get(j).getClassID() == classListBean.getCid() && db_data.get(j).getTaskLatestTime() != Long.parseLong(classListBean.getLast_job_time())) {
//                                bean.setShowReddot(true);
//                            } else {
//                                bean.setShowReddot(false);
//                            }
//                        }*//*
//                        bean.setClassState(classListBean.getStatus());
//                        bean.setClassName(classListBean.getClass_name());
//                        class_list.add(bean);
//                    }
//                    LogUtils.e("******插入了多少数据呢???" + class_list.size());
//                   DBUtils.getDbUtils().leaveHomeClassTabel(Long.parseLong(uerInfo.getUid()), class_list);
                } else {
                    List<ClassTabelBean> db_list = new ArrayList<>();
                    for (int i = 0; i < net_data.size(); i++) {
                        ClassTabelBean bean = new ClassTabelBean();
                        UserClassListBean.DataBean.ClassListBean classListBean = net_data.get(i);
                        bean.setUserID(Long.parseLong(uerInfo.getUid()));
                        bean.setClassID(classListBean.getCid());
                        bean.setTaskLatestTime(Long.parseLong(WorkCatalogUtils.date2TimeStamp(classListBean.getLast_job_time(), "yyyy-MM-dd HH:mm:ss")));
                        if (Integer.parseInt(uerInfo.getCid()) == classListBean.getCid())
                            bean.setShowReddot(false);
                        else
                            bean.setShowReddot(true);
                        bean.setClassState(classListBean.getStatus());
                        bean.setClassName(classListBean.getClass_name());
                        db_list.add(bean);
                    }
                    LogUtils.e("******在没有数据的情况下   插入了多少数据呢???" + db_list.size());
                    DBUtils.getDbUtils().leaveHomeClassTabel(Long.parseLong(uerInfo.getUid()), db_list);
                }
            }

            @Override
            public void isHaveNum(List<?> list, boolean b, boolean bb, int sign) {

            }
        }, GET_CLASS_DB, Long.parseLong(uerInfo.getUid()), Long.parseLong(uerInfo.getUid()));*/

    }

    //没查到 写数据库数据
    public static void setWriteData(List<HomeWorkBean.DataBean.ListBean> netData, String cid, StudentWorkFragment fragment, final LoginBean.DataEntity uerInfo) {
        List<TaskListBean> listBeen = new ArrayList<>();
        for (int i = 0; i < netData.size(); i++) {
            TaskListBean bean = new TaskListBean();
            bean.setUserId(Long.parseLong(UserUtil.getUerInfo(fragment.getActivity()).getUid()));
            bean.setClassID(Long.parseLong(cid));
            bean.setTaskID(netData.get(i).getStu_job_id());
            bean.setTaskSubmitTime("");
            bean.setTaskEndTime(Long.parseLong(netData.get(i).getDeadline()));
            bean.setArrangementTime(netData.get(i).getCdate());
            bean.setTaskTime(0);
            bean.setHaveTask(false);
            bean.setTaskTimeCurrent(0);
            bean.setTaskLastSubmitTime("");
            bean.setTaskName(netData.get(i).getTitle());
            bean.setTaskNum(0);
            bean.setTaskState(netData.get(i).getStu_job_status());
            bean.setTaskDegree(Long.parseLong(netData.get(i).getPercent()));
            int evaluated = netData.get(i).getEvaluated();
            if (evaluated == 1) {
                bean.setEvaluate(false);
            } else {
                bean.setEvaluate(false);
            }
            bean.setWeek(netData.get(i).getCday());
            bean.setTaskRequirement("");
            listBeen.add(bean);
        }
        if (listBeen != null && listBeen.size() > 0)
            fragment.writeDbData.put(uerInfo.getCid(), listBeen);
    }

    //查到数据  写数据库数据
    public static void haveDBData(List<HomeWorkBean.DataBean.ListBean> netData, List<?> list, StudentWorkFragment fragment, final LoginBean.DataEntity uerInfo, StudentWorkAdapter adapter) {
        List<HomeWorkBean.DataBean.ListBean> set_adapter_Data = new ArrayList<>();
        List<TaskListBean> set_db_Data = (List<TaskListBean>) list;
        List<TaskListBean> db_Data = (List<TaskListBean>) list;
        List<TaskListBean> listBeen = new ArrayList<>();
        for (int i = 0; i < netData.size(); i++) {
            HomeWorkBean.DataBean.ListBean listBean = netData.get(i);
            //adapter的bean
            HomeWorkBean.DataBean.ListBean set_adapter_bean = new HomeWorkBean.DataBean.ListBean();
            //数据库bean
            TaskListBean set_db_bean = new TaskListBean();
            int job_id = listBean.getStu_job_id();
            for (int j = 0; j < db_Data.size(); j++) {
                long taskID = db_Data.get(j).getTaskID();
                if (job_id == taskID) {
                    set_db_bean.setTaskDegree(db_Data.get(j).getTaskDegree());
                    set_db_bean.setTaskNum(db_Data.get(j).getTaskNum());
                    set_db_bean.setTaskTime(db_Data.get(j).getTaskTime());
                    set_db_bean.setTaskSubmitTime(db_Data.get(j).getTaskSubmitTime());

                    set_adapter_bean.setJob_status((int) db_Data.get(j).getTaskState());
                    set_adapter_bean.setPercent(db_Data.get(j).getTaskDegree() + "");
                } else {
                    set_db_bean.setTaskDegree(Long.parseLong(netData.get(i).getPercent()));
                    set_db_bean.setTaskNum(0l);
                    set_db_bean.setTaskTime(0l);
                    set_db_bean.setTaskSubmitTime("");

                    set_adapter_bean.setJob_status(netData.get(i).getJob_status());
                    set_adapter_bean.setPercent(netData.get(i).getPercent());
                }
                set_adapter_bean.setCdate(netData.get(i).getCdate());
                set_adapter_bean.setJob_id(netData.get(i).getJob_id());
                set_adapter_bean.setStu_job_id(netData.get(i).getStu_job_id());
                set_adapter_bean.setJob_status(netData.get(i).getJob_status());
                set_adapter_bean.setTitle(netData.get(i).getTitle());
                set_adapter_bean.setCdate(netData.get(i).getCdate());
                set_adapter_bean.setDeadline(netData.get(i).getDeadline());
                set_adapter_bean.setCday(netData.get(i).getCday());
                set_adapter_bean.setEvaluated(netData.get(i).getEvaluated());
                set_adapter_bean.setCid(netData.get(i).getCid());
                set_adapter_bean.setIs_done(netData.get(i).getIs_done());


                set_db_bean.setArrangementTime(netData.get(i).getCdate());
                set_db_bean.setUserId(Long.parseLong(UserUtil.getUerInfo(fragment.getActivity()).getUid()));
                set_db_bean.setClassID(Long.parseLong(uerInfo.getCid()));
                set_db_bean.setTaskID(netData.get(i).getStu_job_id());
                set_db_bean.setTaskEndTime(Long.parseLong(netData.get(i).getDeadline()));
                set_db_bean.setTaskTime(0);
                set_db_bean.setHaveTask(false);
                set_db_bean.setTaskTimeCurrent(0);
                set_db_bean.setTaskLastSubmitTime("");
                set_db_bean.setTaskName(netData.get(i).getTitle());
                set_db_bean.setTaskState(netData.get(i).getStu_job_status());

                int evaluated = netData.get(i).getEvaluated();
                if (evaluated == 1) {
                    set_db_bean.setEvaluate(true);
                } else {
                    set_db_bean.setEvaluate(false);
                }
                set_db_bean.setWeek(netData.get(i).getCday());
                set_db_bean.setTaskRequirement("");
                if (!TextUtils.isEmpty(netData.get(i).getPercent())) {
                    set_db_bean.setTaskPercentage(Long.valueOf(netData.get(i).getPercent()));
                } else {
                    set_db_bean.setTaskPercentage(0L);
                }
            }
            set_db_Data.add(set_db_bean);
            set_adapter_Data.add(set_adapter_bean);
        }
        if (set_db_Data != null && set_db_Data.size() > 0)
            fragment.writeDbData.put(uerInfo.getCid(), set_db_Data);

        adapter.setDataDoing(set_adapter_Data);
    }
/*
    public static boolean updataTopClassRed(final LoginBean.DataEntity uerInfo) {
        DBUtils.getDbUtils().getClassListInfo(new DBUtils.inter() {
            @Override
            public void getList(List<?> list, boolean bb, int sign) {
                ArrayList<ClassTabelBean> bean = (ArrayList<ClassTabelBean>) list;
                for (int i = 0; i < bean.size(); i++) {
                    if (bean.get(i).getClassState() == 1) {

                    }
                }
            }

            @Override
            public void isHaveNum(List<?> list, boolean b, boolean bb, int sign) {

            }
        }, UP_TOP_RED, Long.parseLong(uerInfo.getUid()));
        return false;
    }*/
}
