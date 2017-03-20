package me.bandu.talk.android.phone.greendao.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.bandu.talk.android.phone.App;
import me.bandu.talk.android.phone.bean.Detail;
import me.bandu.talk.android.phone.bean.HomeWorkCatlogEntity;
import me.bandu.talk.android.phone.greendao.bean.BestSentenceBean;
import me.bandu.talk.android.phone.greendao.bean.ClassTabelBean;
import me.bandu.talk.android.phone.greendao.bean.CurrentSentenceBean;
import me.bandu.talk.android.phone.greendao.bean.TaskDirectoryBean;
import me.bandu.talk.android.phone.greendao.bean.TaskListBean;
import me.bandu.talk.android.phone.greendao.communal.Session;
import me.bandu.talk.android.phone.greendao.dao.BestSentenceTabelDao;
import me.bandu.talk.android.phone.greendao.dao.ClassTabelDao;
import me.bandu.talk.android.phone.greendao.dao.CurrentSentenceTabelDao;
import me.bandu.talk.android.phone.greendao.dao.TaskDirectoryTabelDao;
import me.bandu.talk.android.phone.greendao.dao.TaskListTabelDao;

/**
 * 创建者：wanglei
 * <p>时间：16/7/5  11:24
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：update的更新方法必须有主键ID的值，否则异常
 * 作业目录页：如果没网并且本地数据库没有存过数据怎么提示的？   TaskDirectoryBean中的repeatTime字段的耗时累计
 */
public class DBUtils {


    private static DBUtils dbUtils;
    private static ExecutorService ftp = Executors.newFixedThreadPool(1);

    private static DBQueue dbQueue;

    public static DBUtils getDbUtils() {
        if (dbUtils == null) {
            dbUtils = new DBUtils();
        }
        return dbUtils;
    }

    private DBUtils() {
        dbQueue = DBQueue.getDbQueue();
    }

    /**
     * 离开首页时，需要存储的当前用户的班级信息
     */
    public void leaveHomeClassTabel(final long usreID, final List<ClassTabelBean> classTabelList, final DBSaveOk ok) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                ClassTabelDao classTabelDao = null;
                try {
                    ArrayList<Long> longs = new ArrayList<>();//ID
                    HashMap<Long, Long> classIDmap = new HashMap<>();//HashMap<classID,ID>
                    Session session = App.daoSession;
                    classTabelDao = session.getClassTabelDao();
                    List<ClassTabelBean> list = classTabelDao.queryBuilder().where(ClassTabelDao.Properties.userId.eq(usreID)).list();
                    if (list != null && list.size() > 0) {
                        for (ClassTabelBean ctb : classTabelList) {
                            classIDmap.put(ctb.getClassID(), 0l);
                        }
                        for (ClassTabelBean ctb : list) {
                            if (classIDmap.containsKey(ctb.getClassID()))
                                classTabelList.remove(ctb);//45
                            else
                                longs.add(ctb.getID());//12

                        }

                        if (classTabelList.size() > 0) {
                            classTabelDao.insertInTx(classTabelList);
                        }
                        if (longs.size() > 0) {
                            classTabelDao.deleteByKeyInTx(longs);
                        }
                    } else
                        classTabelDao.insertInTx(classTabelList);

                    ok.saveOK();
                } catch (Exception e) {
                    if (classTabelDao != null)
                        classTabelDao.insertInTx(classTabelList);
                    e.printStackTrace();
                    ok.saveOK();
                }
            }
        });
    }

    /**
     * 离开首页时，需要存储的当前用户的班级信息
     */
    public void leaveHomeClassTabel(final long usreID, final List<ClassTabelBean> classTabelList) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Session session = App.daoSession;
                    ClassTabelDao classTabelDao = session.getClassTabelDao();
                    List<ClassTabelBean> list = classTabelDao.queryBuilder().where(ClassTabelDao.Properties.userId.eq(usreID)).list();
                    ArrayList<Long> longs = new ArrayList<>();
                    for (ClassTabelBean ctb : list)
                        longs.add(ctb.getID());
                    classTabelDao.deleteByKeyInTx(longs);
                    classTabelDao.insertInTx(classTabelList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 离开首页时，需要存储的当前用户的班级信息
     */
    public void leaveHomeClassTabel(final long usreID, final long classID, final ClassTabelBean classTabel) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Session session = App.daoSession;
                    ClassTabelDao ctd = session.getClassTabelDao();
                    try {
                        List<ClassTabelBean> list = ctd.queryBuilder().where(ClassTabelDao.Properties.userId.eq(usreID), ClassTabelDao.Properties.classID.eq(classID)).list();
                        ClassTabelBean classTabelBean = list.get(0);
                        classTabel.setID(classTabelBean.getID());
                        ctd.update(classTabel);
                    } catch (Exception e) {
                        ctd.insert(classTabel);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 离开首页时，需要存储的当前用户的班级信息
     */
    public void leaveHomeClassTabel(final long usreID, final long classID, final ClassTabelBean classTabel, final DBSaveOk ok) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Session session = App.daoSession;
                    ClassTabelDao ctd = session.getClassTabelDao();
                    try {
                        List<ClassTabelBean> list = ctd.queryBuilder().where(ClassTabelDao.Properties.userId.eq(usreID), ClassTabelDao.Properties.classID.eq(classID)).list();
                        ClassTabelBean classTabelBean = list.get(0);
                        classTabel.setID(classTabelBean.getID());
                        ctd.update(classTabel);
                        ok.saveOK();
                    } catch (Exception e) {
                        ctd.insert(classTabel);
                        ok.saveOK();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface DBSaveOk {
        void saveOK();
    }

    /**
     * 离开首页时，需要存储的首页的各个班级的作业信息
     */
    public void leaveHomeTaskList(final long usreID, final long classID, final long currentTime, final List<TaskListBean> taskListList) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Session session = App.daoSession;
                    TaskListTabelDao taskListTabelDao = session.getTaskListTabelDao();
                    List<TaskListBean> list1 = taskListTabelDao.queryBuilder().where(TaskListTabelDao.Properties.userId.eq(usreID), TaskListTabelDao.Properties.classId.eq(classID)).list();
                    for (TaskListBean tlb : list1)
                        if (tlb.getTaskEndTime() > currentTime) {
                            try {
                                deleteDBInfo(usreID, classID, tlb.getTaskID(), session);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                taskListTabelDao.deleteByKey(tlb.getID());
                                list1.remove(tlb);
                            }
                        }
                    List<TaskListBean> taskListBeen = null;
                    boolean b = taskListList.removeAll(list1);
                    if (b) {
                        taskListBeen = new ArrayList<>();
                        for (TaskListBean tb : taskListList)
                            taskListBeen.add(tb);
                    }
                    taskListTabelDao.insertInTx(b ? taskListBeen : taskListList);
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 插入几条的方法
     * 重复的数据要更新-百分比，小红点
     *
     * @param usreID
     * @param classID
     * @param taskListList
     */
    public void addTaskList(final inter i, final int what, final long usreID, final long classID, final List<TaskListBean> taskListList) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Session session = App.daoSession;
                    TaskListTabelDao taskListTabelDao = session.getTaskListTabelDao();
                    List<TaskListBean> list = null;
                    try {
                        list = taskListTabelDao.queryBuilder().where(TaskListTabelDao.Properties.userId.eq(usreID), TaskListTabelDao.Properties.classId.eq(classID)).list();
                        if (list == null || list.size() == 0) {
                            taskListTabelDao.insertInTx(taskListList);
                            List<TaskListBean> list2 = taskListTabelDao.queryBuilder().where(TaskListTabelDao.Properties.userId.eq(usreID), TaskListTabelDao.Properties.classId.eq(classID)).list();
                            i.getList(list2, true, what);
                            return;
                        }
                    } catch (Exception e) {
                        taskListTabelDao.insertInTx(taskListList);
                        i.getList(taskListList, true, what);
                        e.printStackTrace();
                    }

                    HashMap<Long, TaskListBean> taskMap = new HashMap<>();//HashMap<taskID, TaskListBean>
                    for (TaskListBean tlb : taskListList) {
                        if (tlb.getTaskDegree() != 0)
                            taskMap.put(tlb.getTaskID(), tlb);
                    }

                    boolean isUpdateTaskPercentage = false;
                    ArrayList<Long> longs = new ArrayList<>();
                    for (TaskListBean tlb : list) {
                        TaskListBean taskListBean = taskMap.get(tlb.getTaskID());
                        if (taskListBean != null) {
                            tlb.setTaskState(taskListBean.getTaskState());
                            tlb.setEvaluate(taskListBean.isEvaluate());
                            if (taskListBean.getTaskPercentage() > tlb.getTaskPercentage()) {
                                tlb.setTaskPercentage(taskListBean.getTaskPercentage());
                            }
                            isUpdateTaskPercentage = true;
                            taskMap.remove(tlb.getTaskID());//这样传进来的集合，剩下的就是新的，插入
                        } else {
                            longs.add(tlb.getID());
                        }
                        long taskEndTime = tlb.getTaskEndTime();

                        if (taskEndTime > System.currentTimeMillis() || tlb.getTaskDegree() == 0) {
                            longs.add(tlb.getID());
                            deleteDBInfo(usreID, classID, tlb.getTaskID(), session);
                        }
                    }
                    if (longs.size() > 0)
                        taskListTabelDao.deleteByKeyInTx(longs);


                    if (isUpdateTaskPercentage)
                        taskListTabelDao.updateInTx(list);

                    if (taskMap.size() > 0) {
                        ArrayList<TaskListBean> newTaskListBeen = new ArrayList<>();
                        Iterator iter = taskMap.entrySet().iterator();
                        while (iter.hasNext()) {
                            Map.Entry entry = (Map.Entry) iter.next();
                            newTaskListBeen.add(taskMap.get(entry.getKey()));
                        }
                        if (newTaskListBeen.size() > 0)
                            taskListTabelDao.insertInTx(newTaskListBeen);
                    }

                    List<TaskListBean> list1 = taskListTabelDao.queryBuilder().where(TaskListTabelDao.Properties.userId.eq(usreID), TaskListTabelDao.Properties.classId.eq(classID)).list();
                    i.getList(list1, true, what);
                } catch (Exception e) {
                    i.getList(null, false, what);
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @param i
     * @param what
     * @param usreID
     * @param classID
     * @param taskListList
     */
    public void updateTaskListBean(final inter i, final int what, final long usreID, final long classID, final TaskListBean taskListList) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                List<TaskListBean> list = null;
                try {
                    Session session = App.daoSession;
                    TaskListTabelDao taskListTabelDao = session.getTaskListTabelDao();
                    if (taskListList != null && taskListList.getTaskID() != 0) {
                        list = taskListTabelDao.queryBuilder().where(TaskListTabelDao.Properties.userId.eq(usreID), TaskListTabelDao.Properties.classId.eq(classID), TaskListTabelDao.Properties.taskID.eq(taskListList.getTaskID())).list();
                    }
                    if (list != null & list.size() > 0) {
                        TaskListBean tlb = list.get(0);
                        tlb.setShowRedpoint(false);
                        long taskPercentage = taskListList.getTaskPercentage();
                        if (taskPercentage > tlb.getTaskPercentage()) {
                            tlb.setTaskPercentage(taskPercentage);
                        }
                        taskListTabelDao.updateInTx(list);
                    }
                    i.getList(null, true, what);
                } catch (Exception e) {
                    i.getList(null, false, what);
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 更新当前条目的方法，插入几条的方法
     * 百分比，小红点
     *
     * @param usreID
     * @param classID
     * @param currentTime
     * @param taskListList
     * @param ok
     */
    public void leaveHomeTaskList(final long usreID, final long classID, final long currentTime, final List<TaskListBean> taskListList, final DBSaveOk ok) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                HashMap<Long, TaskListBean> taskMap = new HashMap<>();//HashMap<taskID, TaskListBean> HashMap<userID, HashMap<classID, TaskListBean>>
                Session session = App.daoSession;
                TaskListTabelDao taskListTabelDao = session.getTaskListTabelDao();
                try {
                    List<TaskListBean> list1 = taskListTabelDao.queryBuilder().where(TaskListTabelDao.Properties.userId.eq(usreID), TaskListTabelDao.Properties.classId.eq(classID)).list();
                    if (list1 != null && list1.size() > 0) {
                        for (TaskListBean tlb : list1) {
                            if (tlb.getTaskEndTime() > currentTime) {
                                deleteDBInfo(usreID, classID, tlb.getTaskID(), session);
                                taskListTabelDao.deleteByKey(tlb.getID());
                                list1.remove(tlb);
                            }
                        }
//                        for(TaskListBean tlb : list1){
//                            taskMap.put(tlb.getTaskID(),tlb);
//                        }
                        List<TaskListBean> taskListBeen = null;
                        boolean b = taskListList.removeAll(list1);
                        if (b) {
                            taskListBeen = new ArrayList<>();
                            for (TaskListBean tb : taskListList)
                                taskListBeen.add(tb);
                        }
                        taskListTabelDao.insertInTx(b ? taskListBeen : taskListList);
                    } else
                        taskListTabelDao.insertInTx(taskListList);
                    ok.saveOK();
                } catch (Exception e) {
                    taskListTabelDao.insertInTx(taskListList);
                    ok.saveOK();
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取首页当前班级的作业信息
     */
    public void getHomeListInfo(final inter i, final int sign, final long usreID, final long classID) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Session session = App.daoSession;
                    TaskListTabelDao taskListTabelDao = session.getTaskListTabelDao();
                    i.getList(taskListTabelDao.queryBuilder().where(TaskListTabelDao.Properties.userId.eq(usreID), TaskListTabelDao.Properties.classId.eq(classID)).list(), false, sign);
                } catch (Exception e) {
                    i.getList(null, false, sign);
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 更改百分比
     * todo 作业的百分比
     * todo 获取需要上传的句子
     * todo 大圆里面的分数（分数和/条数）最好句子的
     * todo 更改当前作业总耗时
     * todo 更改当前遍数和总遍数
     */
    public void getSUbmitTaskInfo(final SubmitImp si, final int sign, final long usreID, final long classID, final long taskID, final long partID, final long type, final long taskTime, final boolean isdone) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                TaskDirectoryTabelDao tdtd = null;
                try {
                    Session session = App.daoSession;
                    BestSentenceTabelDao bstd = session.getBestSentenceTabelDao();
                    List<BestSentenceBean> list = bstd.queryBuilder().where(BestSentenceTabelDao.Properties.userId.eq(usreID), BestSentenceTabelDao.Properties.classID.eq(classID), BestSentenceTabelDao.Properties.taskID.eq(taskID)).list();
                    boolean completeTask = isCompleteTask(list);//是否至少完成了一遍

                    tdtd = session.getTaskDirectoryTabelDao();
                    List<TaskDirectoryBean> averageList = tdtd.queryBuilder().where(TaskDirectoryTabelDao.Properties.userId.eq(usreID), TaskDirectoryTabelDao.Properties.classID.eq(classID), TaskDirectoryTabelDao.Properties.taskID.eq(taskID)).list();
//--------------------------------todo 作业的百分比-------------------------------------------------
                    float percentageCurrent = 0;//百分比单数
                    float percentageTotal = 0;//百分比总数
                    boolean isdone1 = true;
                    for (TaskDirectoryBean tdb : averageList) {
                        percentageCurrent += tdb.getRepeatCurrent();
                        percentageTotal += tdb.getRepeatTotal();
                        if (isdone1)
                            isdone1 = tdb.isDone();
                    }
                    int percentage1 = (int) ((percentageCurrent / percentageTotal) * 100);
                    int percentage;
                    if (!isdone1)
                        percentage = percentage1;
                    else
                        percentage = percentage1 < 1 ? 1 : percentage1;//todo 作业的百分
// --------------------------------todo 需要上传的句子-------------------------------------------------
                    List<BestSentenceBean> submitTaskList = new ArrayList<BestSentenceBean>();//
                    HashMap<Long, Long> myNumMap = new HashMap<>();//HashMap<sentenceID,myNum>
                    int taskPercentage = 0;//多少个有分的句子的数量总和
                    for (BestSentenceBean bsb : list) {
                        if (bsb.isState()) {
                            submitTaskList.add(bsb);//todo 需要上传的句子
                        }
// --------------------------------todo 大圆里的平均分-------------------------------------------------
                        if (bsb.getMyNum() > 0) {//多少个有分的句子的数量总和
                            taskPercentage++;
                        }
                        Long num1 = myNumMap.get(bsb.getSentenceID());
                        if (num1 != null) {
                            if (num1 < bsb.getMyNum())
                                myNumMap.put(bsb.getSentenceID(), bsb.getMyNum());
                        } else {
                            myNumMap.put(bsb.getSentenceID(), bsb.getMyNum());
                        }
                    }
                    long Average = 0;
                    Iterator iter = myNumMap.entrySet().iterator();
                    long num2 = 0;
                    long num3 = 0;
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        Long aLong = myNumMap.get(entry.getKey());
                        num2 += aLong;//所有分的总数
                        if (aLong > 0)
                            num3++;//所有有分的题的数量总数
                    }
                    if (num3 != 0) {
                        Average = num2 / num3;//大圆里的平均分
                    }

                    TaskListTabelDao tltd = session.getTaskListTabelDao();
                    List<TaskListBean> tlbList = tltd.queryBuilder().where(TaskListTabelDao.Properties.userId.eq(usreID), TaskListTabelDao.Properties.classId.eq(classID), TaskListTabelDao.Properties.taskID.eq(taskID)).list();
                    TaskListBean tlb = tlbList.get(0);
                    //3.4.4修改
//                    tlb.setTaskTime(taskTime + tlb.getTaskTime());//作业总耗时

                    long l = taskTime + tlb.getTaskTimeCurrent();//提交的那个耗时
                    tlb.setTaskTimeCurrent(l);

                    long l1 = Average == 0 ? tlb.getTaskNum() : Average;
//                    if (l1 > tlb.getTaskNum())
                    tlb.setTaskNum(l1);//大圆里的平均分

                    tlb.setTaskPercentage(percentage);
                    if (!isdone)
                        tltd.update(tlb);

                    try {
                        if (isdone) {//todo 更改当前遍数和总遍数
                            List<TaskDirectoryBean> tdbList = tdtd.queryBuilder().where(TaskDirectoryTabelDao.Properties.userId.eq(usreID), TaskDirectoryTabelDao.Properties.classID.eq(classID), TaskDirectoryTabelDao.Properties.taskID.eq(taskID), TaskDirectoryTabelDao.Properties.partID.eq(partID), TaskDirectoryTabelDao.Properties.type.eq(type)).list();
                            TaskDirectoryBean tdb = tdbList.get(0);
                            long repeatTotal = tdb.getRepeatTotal();
                            long repeatCurrent = tdb.getRepeatCurrent();
                            if (repeatTotal != repeatCurrent)
                                tdb.setRepeatCurrent(repeatCurrent + 1);
                            if (tdb.getRepeatTotal() == tdb.getRepeatCurrent())
                                tdb.setIsDone(true);
                            tdtd.update(tdb);
                            ArrayList<Long> longs = new ArrayList<>();
                            CurrentSentenceTabelDao cstd = session.getCurrentSentenceTabelDao();
                            List<CurrentSentenceBean> list1 = cstd.queryBuilder().where(CurrentSentenceTabelDao.Properties.userId.eq(usreID), CurrentSentenceTabelDao.Properties.classID.eq(classID), CurrentSentenceTabelDao.Properties.taskID.eq(taskID), CurrentSentenceTabelDao.Properties.partID.eq(partID), CurrentSentenceTabelDao.Properties.type.eq(type)).list();
                            for (CurrentSentenceBean csb : list1) {
                                longs.add(csb.getID());
                            }
                            cstd.deleteByKeyInTx(longs);

//--------------------------------todo 作业的百分比-------------------------------------------------
                            List<TaskDirectoryBean> averageList1 = tdtd.queryBuilder().where(TaskDirectoryTabelDao.Properties.userId.eq(usreID), TaskDirectoryTabelDao.Properties.classID.eq(classID), TaskDirectoryTabelDao.Properties.taskID.eq(taskID)).list();
                            float percentageCurrent1 = 0;//百分比单数
                            float percentageTotal1 = 0;//百分比总数
                            for (TaskDirectoryBean tdb1 : averageList1) {
                                percentageCurrent1 += tdb1.getRepeatCurrent();
                                percentageTotal1 += tdb1.getRepeatTotal();
                            }
                            int percentage2 = (int) ((percentageCurrent1 / percentageTotal1) * 100);

                            int percentage3 = percentage2 < 1 ? 1 : percentage2;//todo 作业的百分
                            tlb.setTaskPercentage(percentage3);
                            tltd.update(tlb);

                        }
                    } finally {
                        si.myResult(submitTaskList, Average, sign, tlb.getTaskTime(), tlb.getTaskTimeCurrent(), completeTask);
                    }
                } catch (Exception e) {
                    si.myResult(null, 0, sign, 0, 0, false);
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 是否完成了至少一遍作业，真完成，假没完成
     * （有句子 真   并且所有句子都有耗时，就是操作过的  真）-----否则是假
     *
     * @param list 这个作业里的所有的句子
     * @return
     */
    private boolean isCompleteTask(List<BestSentenceBean> list) {
        List<Long> sentenceList = new ArrayList<>();// List<sentenceID>
        HashMap<Long, Long> llMap = new HashMap<>();//HashMap<sentenceID, MyNum>
        //获取所有有分的句子数量，获取所有的句子的数量，对比，类型合一
        for (BestSentenceBean bsb : list) {
            long sentenceID = bsb.getSentenceID();
            if (!sentenceList.contains(sentenceID)) {
                sentenceList.add(sentenceID);//一共有多少个句子，排除3种类型的，只剩下一种
            }
            if (!llMap.containsKey(sentenceID)) {//存多少有耗时的句子，就是操作过的
                if (bsb.getMyNum() > 0)
                    llMap.put(sentenceID, bsb.getMyNum());
            }
        }//有句子 真                        并且所有句子都有耗时，就是操作过的
        return sentenceList.size() != 0 && llMap.size() == sentenceList.size();
    }

    /**
     * todo  更改百分比  大圆里面的分数（分数和/条数）最好句子的
     * 提交作业时获取应该提交的作业的方法
     * 获取需要上传的句子，更改当前作业总耗时，更改当前遍数和总遍数
     *
     * @param repeatTime      当前这一遍的作业耗时
     * @param isCompleteAgain 当前这一遍的作业是否做完真做完，假没有做完
     * @param isHomeGoon      是从首页（真）过来的还是从做完作业（假）过来调用的这个方法
     *                        如果是首页过来获取上面的分数、总耗时、需要上传的句子--否则还要设置分数 总耗时 更改列表页的百分比  更改当前遍数和总遍数
     */
    public void getSubmitTask(final SubmitInter i, final int sign, final long usreID, final long classID, final long taskID, final long partID, final long type, final long repeatTime, final boolean isCompleteAgain, final boolean isHomeGoon) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                boolean bb = false;
                long taseRepeatTime = 0;
                try {//获取需要上传的句子
                    Session session = App.daoSession;
                    BestSentenceTabelDao bstd = session.getBestSentenceTabelDao();
                    List<BestSentenceBean> list = bstd.queryBuilder().where(BestSentenceTabelDao.Properties.userId.eq(usreID), BestSentenceTabelDao.Properties.classID.eq(classID), BestSentenceTabelDao.Properties.taskID.eq(taskID)).list();
                    List<BestSentenceBean> submitTaskList = new ArrayList<BestSentenceBean>();
                    int num = 0;//平均分
                    int taskPercentage = 0;//百分比
                    for (BestSentenceBean bsb : list) {
                        if (bsb.isState()) {
                            submitTaskList.add(bsb);//获取当前需要上传的句子存到submitTaskList
                        }
                        long myNum = bsb.getMyNum();
                        num += myNum;//所有句子分数总和
                        if (myNum > 0) {//分数大于0，说明这个句子做过,获取做过多少个句子除以总句子数，就是百分比
                            taskPercentage++;
                        }
                    }

                    if (isHomeGoon) {
                        //获取当前这条作业的，然后更改百分比
                        TaskListTabelDao tltd = session.getTaskListTabelDao();
                        List<TaskListBean> taskList = tltd.queryBuilder().where(TaskListTabelDao.Properties.userId.eq(usreID), TaskListTabelDao.Properties.classId.eq(classID), TaskListTabelDao.Properties.taskID.eq(taskID)).list();
                        if (taskList != null && taskList.size() > 0) {
                            TaskListBean tlb = taskList.get(0);
                            tlb.setTaskPercentage(taskPercentage / list.size());
                            tltd.update(tlb);
                        }
                    }

                    //更改当前作业总耗时
                    TaskDirectoryTabelDao tdtd = session.getTaskDirectoryTabelDao();
                    List<TaskDirectoryBean> list2 = tdtd.queryBuilder().where(TaskDirectoryTabelDao.Properties.userId.eq(usreID), TaskDirectoryTabelDao.Properties.classID.eq(classID), TaskDirectoryTabelDao.Properties.taskID.eq(taskID)).list();
                    if (list2.size() > 0) {
                        for (TaskDirectoryBean tdb : list2) {
                            long repeatTime1 = tdb.getRepeatTime();
                            taseRepeatTime = repeatTime + repeatTime1;
                            tdb.setRepeatTime(taseRepeatTime);
                        }
                        tdtd.updateInTx(list2);
                    }

                    if (isCompleteAgain && !isHomeGoon) {//更改当前遍数和总遍数
                        List<TaskDirectoryBean> list3 = tdtd.queryBuilder().where(TaskDirectoryTabelDao.Properties.userId.eq(usreID), TaskDirectoryTabelDao.Properties.classID.eq(classID), TaskDirectoryTabelDao.Properties.taskID.eq(taskID), TaskDirectoryTabelDao.Properties.partID.eq(partID), TaskDirectoryTabelDao.Properties.type.eq(type)).list();
                        if (list3.size() > 0) {
                            TaskDirectoryBean tdb = list3.get(0);
                            long repeatCurrent = tdb.getRepeatCurrent();//当前数
                            long repeatTotal = tdb.getRepeatTotal();//总数
                            if (repeatCurrent != repeatTotal) {
                                tdb.setRepeatCurrent(repeatCurrent + 1);
                            }
                        }
                    }
                    i.submitInter(submitTaskList, taseRepeatTime == 0 ? repeatTime : taseRepeatTime, num / list.size(), sign);
                    bb = true;
                } catch (Exception e) {
                    if (!bb) {
                        i.submitInter(null, 0, 0, sign);
                    }
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取首页弹框的所有班级的信息
     */
    public void getClassListInfo(final inter i, final int sign, final long usreID) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Session session = App.daoSession;
                    ClassTabelDao classTabelDao = session.getClassTabelDao();
                    i.getList(classTabelDao.queryBuilder().where(ClassTabelDao.Properties.userId.eq(usreID)).list(), false, sign);
                } catch (Exception e) {
                    i.getList(null, false, sign);//78
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取作业目录中下方listView中的ListInfo
     */
    public void getDirectoryListInfo(final SubmitImp i, final int sign, final long usreID, final long classID, final long taskID) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                boolean bb = true;
                try {
                    Session daoSession = App.daoSession;
                    TaskDirectoryTabelDao taskDirectoryTabelDao = daoSession.getTaskDirectoryTabelDao();
                    List<TaskDirectoryBean> tdbList = taskDirectoryTabelDao.queryBuilder().where(TaskDirectoryTabelDao.Properties.userId.eq(usreID), TaskDirectoryTabelDao.Properties.classID.eq(classID), TaskDirectoryTabelDao.Properties.taskID.eq(taskID)).list();
                    try {
                        TaskListTabelDao tltd = daoSession.getTaskListTabelDao();
                        List<TaskListBean> list = tltd.queryBuilder().where(TaskListTabelDao.Properties.userId.eq(usreID), TaskListTabelDao.Properties.classId.eq(classID), TaskListTabelDao.Properties.taskID.eq(taskID)).list();
                        if (list != null && list.size() > 0) {
                            TaskListBean tlb = list.get(0);
                            i.submitImp(tdbList, tlb, sign);
                            bb = false;
                        }
                    } finally {
                        if (bb)
                            i.submitImp(tdbList, null, sign);
                    }
                } catch (Exception e) {
                    if (bb)
                        i.submitImp(null, null, sign);
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 进入做作业页获取句子的方法
     *
     * @param isDone 自己的遍数和总遍数对比出来的done
     */
    public void getSentence(final inter i, final int sign, final long usreID, final long classID, final long taskID, final long partID, final long type, final boolean isDone) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    BestSentenceTabelDao bestSentenceTabelDao = App.daoSession.getBestSentenceTabelDao();
                    CurrentSentenceTabelDao currentSentenceTabelDao = App.daoSession.getCurrentSentenceTabelDao();
                    List<BestSentenceBean> list = bestSentenceTabelDao.queryBuilder().where(BestSentenceTabelDao.Properties.userId.eq(usreID), BestSentenceTabelDao.Properties.classID.eq(classID), BestSentenceTabelDao.Properties.taskID.eq(taskID), BestSentenceTabelDao.Properties.partID.eq(partID), BestSentenceTabelDao.Properties.type.eq(type)).list();
                    if (isDone) {
                        boolean b = false;//true 有没分的，false全都有分
                        for (BestSentenceBean bsb : list) {//只要有一个没分数的，done就是错误的，就得去网络拿句子给下个页
                            if (bsb.getMyNum() < 1) {
                                b = true;
                                break;
                            }
                        }
                        i.getList(list, b, sign);//b是true，说明有句子没有录过音，得联网拿数据，false，
                        return;
//                        }
                    }
                    List<CurrentSentenceBean> list1 = null;
                    try {
                        list1 = currentSentenceTabelDao.queryBuilder().where(CurrentSentenceTabelDao.Properties.userId.eq(usreID), CurrentSentenceTabelDao.Properties.classID.eq(classID), CurrentSentenceTabelDao.Properties.taskID.eq(taskID), CurrentSentenceTabelDao.Properties.partID.eq(partID), CurrentSentenceTabelDao.Properties.type.eq(type)).list();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        HashMap<Long, CurrentSentenceBean> map = new HashMap<>();
                        if (list1 != null && list1.size() > 0) {
                            for (CurrentSentenceBean csb : list1) {
                                map.put(csb.getSentenceID(), csb);
                            }
                        }
                        int noNum = 0;//有多少个有东西的句子
                        for (BestSentenceBean bsb : list) {
                            if (bsb.getMyNum() == 0) {
                                noNum++;
                            }
                            long sentenceID = bsb.getSentenceID();
                            if (map != null && map.containsKey(sentenceID)) {
                                CurrentSentenceBean csb = map.get(sentenceID);
                                bsb.setTextColor(csb.getTextColor());//变色文本
                                bsb.setMyAddress(csb.getMyAddress());//我的声音本地地址
                                bsb.setChishengNetAddress(csb.getChishengNetAddress());//弛声的网络声音地址
                                bsb.setMyVoiceTime(csb.getMyVoiceTime());//我的声音时长
                                bsb.setMyNum(csb.getMyNum());//我的分数
                            } else {
                                bsb.setTextColor(null);//变色文本
                                bsb.setMyAddress(null);//我的声音本地地址
                                bsb.setChishengNetAddress(null);//弛声的网络声音地址
                                bsb.setMyVoiceTime(0);//我的声音时长
                                bsb.setMyNum(0);//我的分数
                            }
                        }
//                        i.getList(list, list.size() == noNum, sign);//list.size()==noNum是true list里面全都没有分
                        i.getList(list, false, sign);//list.size()==noNum是true list里面全都没有分
                    }
                } catch (Exception e) {
                    i.getList(null, false, sign);
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * * 作业目录页添加下面ListView的数据源到数据库中
     * 封装数据源是，TaskDirectoryBean的第一个参数直接给null即可
     * //taskName = title    description = 作业描述  level = 作业要求等级   total_time=作业总耗时（本地不清零的那个）  score = 作业分数，本地和网络那个高用哪个
     *
     * @param i
     * @param sign
     * @param taskDirectoryBean
     * @param userID
     * @param classID
     * @param taskID
     * @param level             作业要求等级
     * @param total_time        作业总耗时（本地不清零的那个
     * @param score             作业分数，本地和网络那个高用哪
     * @param submitTime        上一次作业的提交的时间
     * @param taskName          title
     * @param description       作业描述
     */
    public void addDirectoryListInfos(final inter i, final int sign, final List<TaskDirectoryBean> taskDirectoryBean, final Long userID, final Long classID, final long taskID, final String level, final long total_time, final long score, final String submitTime, final String taskName, final String description) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                TaskDirectoryTabelDao taskDirectoryTabelDao = null;
                try {
                    Session session = App.daoSession;
                    try {
                        TaskListTabelDao tltd = session.getTaskListTabelDao();
                        List<TaskListBean> taskListList = tltd.queryBuilder().where(TaskListTabelDao.Properties.userId.eq(userID), TaskListTabelDao.Properties.classId.eq(classID), TaskListTabelDao.Properties.taskID.eq(taskID)).list();
                        if (taskListList != null && taskListList.size() > 0) {
                            TaskListBean tlb = taskListList.get(0);
                            tlb.setTaskRequirement(level);
                            if (total_time > tlb.getTaskTime())
                                tlb.setTaskTime(total_time);
                            if (tlb.getTaskNum() < 1)
                                tlb.setTaskNum(score);
                            tlb.setTaskLastSubmitTime(submitTime);
                            tlb.setTaskName(taskName);
                            tlb.setTaskExplain(description);
                            tltd.update(tlb);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    taskDirectoryTabelDao = session.getTaskDirectoryTabelDao();
                    List<TaskDirectoryBean> list = taskDirectoryTabelDao.queryBuilder().where(TaskDirectoryTabelDao.Properties.userId.eq(userID), TaskDirectoryTabelDao.Properties.classID.eq(classID), TaskDirectoryTabelDao.Properties.taskID.eq(taskID)).list();

                    if (list != null && list.size() > 0) {
                        HashMap<Long, List<HashMap<Long, TaskDirectoryBean>>> map = new HashMap<>();//HashMap<partID, List<HashMap<type, TaskDirectoryBean>>>
                        if (taskDirectoryBean != null && taskDirectoryBean.size() > 0) {
                            for (TaskDirectoryBean tdb : taskDirectoryBean) {
                                List<HashMap<Long, TaskDirectoryBean>> partList;
                                if (map.containsKey(tdb.getPartID())) {
                                    partList = map.get(tdb.getPartID());
                                } else {
                                    partList = new ArrayList<>();
                                }
                                HashMap<Long, TaskDirectoryBean> map2 = new HashMap<>();
                                map2.put(tdb.getType(), tdb);
                                partList.add(map2);
                                map.put(tdb.getPartID(), partList);
                            }

                            for (TaskDirectoryBean tdb : list) {
                                List<HashMap<Long, TaskDirectoryBean>> partList = map.get(tdb.getPartID());
                                for (HashMap<Long, TaskDirectoryBean> typeMap : partList) {
                                    TaskDirectoryBean taskDirectoryBean1 = typeMap.get(tdb.getType());
                                    if (taskDirectoryBean1 != null) {
                                        long repeatCurrent = taskDirectoryBean1.getRepeatCurrent();
                                        if (repeatCurrent > tdb.getRepeatCurrent()) {
                                            tdb.setRepeatCurrent(repeatCurrent);
                                        }
                                    }
                                }
                            }
                            taskDirectoryTabelDao.updateInTx(list);
                        }
                    } else
                        taskDirectoryTabelDao.insertInTx(taskDirectoryBean);
                    i.getList(null, true, sign);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (taskDirectoryTabelDao != null) {
                        taskDirectoryTabelDao.insertInTx(taskDirectoryBean);
                        i.getList(null, true, sign);
                        return;
                    }
                    i.getList(null, false, sign);
                }
            }
        });
    }

    /**
     * 第一次进入作业目录，获取所以的part中的题，存入最好句子表中
     *
     * @param bestSentenceBean
     */
    public void addSentence(final inter i, final int sign, final long usreID, final long classID, final long taskID, final List<BestSentenceBean> bestSentenceBean, final DBSaveOk ok) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (bestSentenceBean != null && bestSentenceBean.size() > 0) {
                        BestSentenceTabelDao bestSentenceTabelDao = App.daoSession.getBestSentenceTabelDao();
                        List<BestSentenceBean> list1 = null;
                        try {
                            list1 = bestSentenceTabelDao.queryBuilder().where(BestSentenceTabelDao.Properties.userId.eq(usreID), BestSentenceTabelDao.Properties.classID.eq(classID), BestSentenceTabelDao.Properties.taskID.eq(taskID)).list();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (list1 != null && list1.size() > 0) {//有东西，不存，
                        } else {//没下载过，存
                            bestSentenceTabelDao.insertInTx(bestSentenceBean);
                            TaskListTabelDao taskListTabelDao = App.daoSession.getTaskListTabelDao();
                            List<TaskListBean> list = taskListTabelDao.queryBuilder().where(TaskListTabelDao.Properties.userId.eq(usreID), TaskListTabelDao.Properties.classId.eq(classID), TaskListTabelDao.Properties.taskID.eq(taskID)).list();
                            TaskListBean taskListBean = list.get(0);
                            taskListBean.setHaveTask(true);
                            taskListTabelDao.update(taskListBean);
                        }
                    }
                    i.getList(null, true, sign);
                } catch (Exception e) {
                    i.getList(null, true, sign);
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 进入到作业目录页面的时候调用这个方法，这里返回的集合中只有一个bean,是当前作业的各种信息
     */
    public void isHaveTask(final inter i, final int sign, final long usreID, final long classID, final long taskID) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                List<BestSentenceBean> list2 = null;
                try {
                    Session session = App.daoSession;
                    TaskListTabelDao taskListTabelDao = session.getTaskListTabelDao();
                    List<TaskListBean> list = taskListTabelDao.queryBuilder().where(TaskListTabelDao.Properties.userId.eq(usreID), TaskListTabelDao.Properties.classId.eq(classID), TaskListTabelDao.Properties.taskID.eq(taskID)).list();
                    List<BestSentenceBean> list1 = null;
                    try {
                        BestSentenceTabelDao bstd = session.getBestSentenceTabelDao();
                        list1 = bstd.queryBuilder().where(BestSentenceTabelDao.Properties.userId.eq(usreID), BestSentenceTabelDao.Properties.classID.eq(classID), BestSentenceTabelDao.Properties.taskID.eq(taskID), BestSentenceTabelDao.Properties.state.eq(true)).list();
                        list2 = bstd.queryBuilder().where(BestSentenceTabelDao.Properties.userId.eq(usreID), BestSentenceTabelDao.Properties.classID.eq(classID), BestSentenceTabelDao.Properties.taskID.eq(taskID)).list();
                    } finally {
                        TaskListBean taskListBean = list.get(0);
//                        i.isHaveNum(1, list1, isCompleteTask(list2), taskListBean.isHaveTask(), taskListBean.getTaskRequirement(), taskListBean.getTaskExplain(), sign);
                        i.isHaveNum(1, list1, isCompleteTask(list2), taskListBean, sign);
                    }
                } catch (Exception e) {
                    i.isHaveNum(0, null, false, null, sign);
//                    i.getList(null, false, 0);
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 是否至少完成过一遍作业的查询
     * 接口中返回的真假值，真代表没完成，假代表至少完成过一遍
     */
    public void isFinishHomework(final inter i, final int sign, final long usreID, final long classID, final long taskID, final long... partID) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    BestSentenceTabelDao bstd = App.daoSession.getBestSentenceTabelDao();
                    int length = partID.length;
                    boolean bb = false;
                    for (int x = 0; x < length; x++) {
                        List<BestSentenceBean> list = bstd.queryBuilder().where(BestSentenceTabelDao.Properties.userId.eq(usreID), BestSentenceTabelDao.Properties.classID.eq(classID), BestSentenceTabelDao.Properties.taskID.eq(taskID), BestSentenceTabelDao.Properties.partID.eq(partID[x])).list();
                        List<Long> sentenceIDs = new ArrayList<>();
                        for (BestSentenceBean bsb : list) {
                            long sentenceID = bsb.getSentenceID();
                            if (!sentenceIDs.contains(sentenceID) && !(bsb.getMyNum() > 0)) {
                                sentenceIDs.add(sentenceID);
                            }
                        }
                        if (sentenceIDs.size() > 0) {//大于0有没完成的，下面的不用继续遍历直接跳出，如果遍历完bb仍然是假，说明完成了至少一遍
                            bb = true;
                            break;
                        }
                    }
                    i.getList(null, bb, sign);
                } catch (Exception e) {
                    i.getList(null, false, sign);
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 更新当前用户班级小红点或其他数据的信息
     */
    public void updateClassRedSpot(final List<ClassTabelBean> entity, final long userID) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Session session = App.daoSession;
                    ClassTabelDao ctd = session.getClassTabelDao();
                    List<ClassTabelBean> list = ctd.queryBuilder().where(ClassTabelDao.Properties.userId.eq(userID)).list();
                    ArrayList<Long> longs = new ArrayList<>();
                    for (ClassTabelBean ctb : list) {
                        longs.add(ctb.getID());
                    }
                    ctd.deleteByKeyInTx(longs);
                    ctd.insertInTx(entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 更新当前用户班级小红点或其他数据的信息，但是需要用getClassListInfo方法返回的Bean
     */
    public void updateClassRedSpots(final List<ClassTabelBean> list) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Session session = App.daoSession;
                    ClassTabelDao classTabelDao = session.getClassTabelDao();
                    classTabelDao.updateInTx(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void saveDB(long userID, long classID, long partID, long stu_job_id, long type, Detail.DataEntity.ListEntity entity) {
        CurrentSentenceBean bean = new CurrentSentenceBean(userID,
                classID,
                entity.getStu_job_id(),
                entity.getQuiz_id(),
                entity.getSentence_id(),
                type,
                entity.getEn(),
                entity.getWords_score(),
                entity.getMp3(),
                entity.getCurrent_mp3(),
                entity.getMp3_url(),
                entity.getCurrent_stu_seconds(),
                entity.getCurrent_score(),
                entity.getAnswer()
        );
        updateSentence(userID, classID, stu_job_id, partID, type, bean);
    }

    /**
     * 作业时更新当前句子信息
     */
    public void updateSentence(final long usreID, final long classID, final long taskID, final long partID, final long type, final CurrentSentenceBean csb) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    BestSentenceTabelDao bestSentenceTabelDao = App.daoSession.getBestSentenceTabelDao();
                    CurrentSentenceTabelDao currentSentenceTabelDao = App.daoSession.getCurrentSentenceTabelDao();
                    List<BestSentenceBean> list = bestSentenceTabelDao.queryBuilder().where(BestSentenceTabelDao.Properties.userId.eq(usreID), BestSentenceTabelDao.Properties.classID.eq(classID), BestSentenceTabelDao.Properties.taskID.eq(taskID), BestSentenceTabelDao.Properties.partID.eq(partID), BestSentenceTabelDao.Properties.type.eq(type), BestSentenceTabelDao.Properties.sentenceID.eq(csb.getSentenceID())).list();
                    List<CurrentSentenceBean> list1 = null;
                    try {
                        list1 = currentSentenceTabelDao.queryBuilder().where(CurrentSentenceTabelDao.Properties.userId.eq(usreID), CurrentSentenceTabelDao.Properties.classID.eq(classID), CurrentSentenceTabelDao.Properties.taskID.eq(taskID), CurrentSentenceTabelDao.Properties.partID.eq(partID), BestSentenceTabelDao.Properties.type.eq(type), BestSentenceTabelDao.Properties.sentenceID.eq(csb.getSentenceID())).list();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        BestSentenceBean bsb = list.get(0);
                        if (csb.getMyNum() > bsb.getMyNum()) {
                            BestSentenceBean bsb1 = new BestSentenceBean(bsb.getID(), csb.getUserId(), csb.getClassID(), csb.getTaskID(), csb.getPartID(), csb.getSentenceID(), csb.getType(), csb.getText(), csb.getTextColor(), csb.getNetAddress(), csb.getMyAddress(), csb.getChishengNetAddress(), csb.getMyVoiceTime(), csb.getMyNum(), true, bsb.getHw_quiz_id(),csb.getAnswer());
                            bestSentenceTabelDao.update(bsb1);
                        }
                        if (list1 != null && list1.size() > 0)
                            currentSentenceTabelDao.update(new CurrentSentenceBean(list1.get(0).getID(), csb.getUserId(), csb.getClassID(), csb.getTaskID(), csb.getPartID(), csb.getSentenceID(), csb.getType(), csb.getText(), csb.getTextColor(), csb.getNetAddress(), csb.getMyAddress(), csb.getChishengNetAddress(), csb.getMyVoiceTime(), csb.getMyNum(), false,csb.getAnswer()));
                        else
                            currentSentenceTabelDao.insert(new CurrentSentenceBean(null, csb.getUserId(), csb.getClassID(), csb.getTaskID(), csb.getPartID(), csb.getSentenceID(), csb.getType(), csb.getText(), csb.getTextColor(), csb.getNetAddress(), csb.getMyAddress(), csb.getChishengNetAddress(), csb.getMyVoiceTime(), csb.getMyNum(), false,csb.getAnswer()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateSentence(final inter i, final int sign, final long userID, final long classID, final long taskID, final long partID, final long type, final Map<Long, BestSentenceBean> bsbMap) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (bsbMap != null && bsbMap.size() > 0) {
                        Session daoSession = App.daoSession;
                        BestSentenceTabelDao bstd = daoSession.getBestSentenceTabelDao();
                        List<BestSentenceBean> list = bstd.queryBuilder().where(BestSentenceTabelDao.Properties.userId.eq(userID), BestSentenceTabelDao.Properties.classID.eq(classID), BestSentenceTabelDao.Properties.taskID.eq(taskID), BestSentenceTabelDao.Properties.partID.eq(partID), BestSentenceTabelDao.Properties.type.eq(type)).list();
                        List<BestSentenceBean> list1 = new ArrayList<>();
                        for (BestSentenceBean bsb1 : list) {
                            BestSentenceBean bestSentenceBean = bsbMap.get(bsb1.getSentenceID());
                            bestSentenceBean.setID(bsb1.getID());
                            bestSentenceBean.setTaskID(bsb1.getTaskID());
                            bestSentenceBean.setPartID(bsb1.getPartID());
                            bestSentenceBean.setChishengNetAddress(bsb1.getChishengNetAddress());
                            bestSentenceBean.setHw_quiz_id(bsb1.getHw_quiz_id());
                            list1.add(bestSentenceBean);
                        }
                        bstd.updateInTx(list1);
                    }
                    i.getList(null, true, sign);
                } catch (Exception e) {
                    i.getList(null, true, sign);
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 提交作业成功后要调用此方法，更改提交过的句子的状态，必须用getSubmitTask方法获取到的集合，从新封装会异常
     *
     * taskTime:3.4.4修改计时
     */
    public void updateSubmitTaskState(final inter i, final int sign, final List<BestSentenceBean> list, final long usreID, final long classID, final long taskID, final String taskLastSubmitTime,final long taskTime) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Session daoSession = App.daoSession;
                    if (list.size() > 0) {
                        BestSentenceTabelDao bstd = daoSession.getBestSentenceTabelDao();
                        for (BestSentenceBean bsb : list) {
                            bsb.setState(false);
                        }
                        bstd.updateInTx(list);
                    }
                    list.clear();
                    TaskListTabelDao tltd = daoSession.getTaskListTabelDao();
                    List<TaskListBean> list1 = tltd.queryBuilder().where(TaskListTabelDao.Properties.userId.eq(usreID), TaskListTabelDao.Properties.classId.eq(classID), TaskListTabelDao.Properties.taskID.eq(taskID)).list();
                    TaskListBean tlb = list1.get(0);
//                    tlb.setTaskSubmitTime(DateUtils.getCurrentTimeText("yyyy年MM月dd日HH点"));
                    tlb.setTaskLastSubmitTime(taskLastSubmitTime);
                    tlb.setTaskTime(taskTime);//作业总耗时
                    tlb.setTaskTimeCurrent(0);
                    tltd.update(tlb);
                    i.getList(null, true, sign);
                } catch (Exception e) {
                    i.getList(null, false, sign);
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @param level //作业的等级要求
     */
    public void updateTaskTabelDirectoryInfo(final long usreID, final long classID, final long taskID, final String level, final String taskExplain, final HomeWorkCatlogEntity homework) {
        ftp.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Session daoSession = App.daoSession;
                    TaskListTabelDao tltd = daoSession.getTaskListTabelDao();
                    List<TaskListBean> list = tltd.queryBuilder().where(TaskListTabelDao.Properties.userId.eq(usreID), TaskListTabelDao.Properties.classId.eq(classID), TaskListTabelDao.Properties.taskID.eq(taskID)).list();
                    TaskListBean tlb = list.get(0);
                    tlb.setTaskNum(homework.getScore());//学生分数
                    tlb.setTaskLastSubmitTime(homework.getCommit_time());//作业提交时间
                    tlb.setTaskName(homework.getTitle());//作业名字
                    tlb.setTaskTime(homework.getTotal_time() /** 1000*/);//服务器给的是秒，本地存的是毫秒--作业耗时
//                    tlb.setTaskRequirement(homework.getDescription());
                    tlb.setTaskRequirement(level);
                    tlb.setTaskExplain(taskExplain);
                    tltd.update(tlb);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void deleteDBInfo(long usreID, long classID, long taskID, Session session) {
        try {
            ArrayList<Long> longs = new ArrayList<>();
            TaskDirectoryTabelDao taskDirectoryTabelDao = session.getTaskDirectoryTabelDao();
            List<TaskDirectoryBean> list = taskDirectoryTabelDao.queryBuilder().where(TaskDirectoryTabelDao.Properties.userId.eq(usreID), TaskDirectoryTabelDao.Properties.classID.eq(classID), TaskDirectoryTabelDao.Properties.taskID.eq(taskID)).list();
            for (TaskDirectoryBean tdb : list) {
                longs.add(tdb.getID());
            }
            taskDirectoryTabelDao.deleteByKeyInTx(longs);
            longs.clear();

            BestSentenceTabelDao bestSentenceTabelDao = session.getBestSentenceTabelDao();
            List<BestSentenceBean> list1 = bestSentenceTabelDao.queryBuilder().where(TaskDirectoryTabelDao.Properties.userId.eq(usreID), TaskDirectoryTabelDao.Properties.classID.eq(classID), TaskDirectoryTabelDao.Properties.taskID.eq(taskID)).list();
            for (BestSentenceBean bsb : list1) {
                deleteMediaFile(bsb.getMyAddress());
                longs.add(bsb.getID());
            }
            bestSentenceTabelDao.deleteByKeyInTx(longs);
            longs.clear();

            CurrentSentenceTabelDao currentSentenceTabelDao = session.getCurrentSentenceTabelDao();
            List<CurrentSentenceBean> list2 = currentSentenceTabelDao.queryBuilder().where(TaskDirectoryTabelDao.Properties.userId.eq(usreID), TaskDirectoryTabelDao.Properties.classID.eq(classID), TaskDirectoryTabelDao.Properties.taskID.eq(taskID)).list();
            for (CurrentSentenceBean csb : list2) {
                deleteMediaFile(csb.getMyAddress());
                longs.add(csb.getID());
            }
            currentSentenceTabelDao.deleteByKeyInTx(longs);
            longs.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据给定的音频文件地址删除这个文件
     *
     * @param mediaFilePath 音频文件地址
     */
    private void deleteMediaFile(String mediaFilePath) {
        new File(mediaFilePath).delete();
    }

    /**
     * list可能是null或size为0
     */
    public interface inter {
        void getList(List<?> list, boolean bb, int sign);

        /**
         * @param state 成功与否的状态，0失败，1成功
         * @param list  要提交的作业
         * @param b     是否至少完成过一遍作业，真完成了，假没有
         * @param sign  标识
         */
        void isHaveNum(int state, List<?> list, boolean b, TaskListBean taskListBean, int sign);
    }

    public interface SubmitInter {
        /**
         * @param list       应该提交的作业
         * @param repeatTime 当前这个没提交的作业的总耗时
         * @param num        大圆里面的分数（分数和/条数）最好句子的
         */
        void submitInter(List<?> list, long repeatTime, long num, int sign);
    }

    public interface SubmitImp {
        /**
         * @param tdbList
         */
        void submitImp(List<TaskDirectoryBean> tdbList, TaskListBean tlb, int sign);


        /**
         * @param submitTaskList 要提交的作业
         * @param average        大圆里的分数
         * @param sign
         * @param taskTime       作业总耗时
         * @param taskTime       提交的作业总耗时，每次提交后都会清除
         * @param b              作业是否完成过一遍
         */
        void myResult(List<BestSentenceBean> submitTaskList, long average, int sign, long taskTime, long taskTimeCurrent, boolean b);
    }
}
