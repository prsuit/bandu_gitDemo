package me.bandu.talk.android.phone.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.DFHT.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.App;
import me.bandu.talk.android.phone.bean.Detail;
import me.bandu.talk.android.phone.bean.HomeWorkBean;
import me.bandu.talk.android.phone.bean.HomeWorkCatlogBean;
import me.bandu.talk.android.phone.bean.HomeWorkCatlogQuiz;
import me.bandu.talk.android.phone.bean.HomeWorkData;
import me.bandu.talk.android.phone.bean.HomeWorkEntity;
import me.bandu.talk.android.phone.db.mdao.MWorkQuizeDao;
import me.bandu.talk.android.phone.utils.MediaPathUtils;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * 创建者：高楠
 * 时间：on 2015/12/17
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class DaoUtils {
    private static DaoUtils daoUtils;

    private DaoUtils() {
    }

    public static DaoUtils getInstance() {
        if (daoUtils == null) {
            daoUtils = new DaoUtils();
        }
        return daoUtils;
    }

    private DaoMaster daoMaster;
    private DaoSession session;
    private String dbname;
    private DaoMaster.DevOpenHelper helper;

    //此方法需在登录成功时调用

    /**
     * 初始化，用的是ApplicationContext （防止多次初始化daomaster），数据库名为用户的Uid
     *
     * @return
     */
    public boolean init() {
//        if (context == null) {
//            context = App.getApplication();
//            if (context==null)
//                return false;
//        }

        String name = UserUtil.getUserInfoUid(App.getApplication());
        if (name == null || name.equals("")) {
            name = "test";
        }
        if (this.dbname == null || (this.dbname != null && !this.dbname.equals(name))) {
            this.dbname = name;
            helper = new DaoMaster.DevOpenHelper(App.getApplication().getApplicationContext(), dbname, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
            session = daoMaster.newSession();
            migrationCompletionDB(session);
        }
        return true;
    }

    private void migrationCompletionDB(DaoSession session) {

    }


    /**
     * *******************************************以下为作业列表****************************************************************
     */
    public void deleteHomework(final HomeWork homeWork) {
        if (init()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    session.getHomeWorkDao().delete(homeWork);
                }
            }).start();
        }
    }

    public DaoSession getDaoSession() {
        if (init())
            return session;
        else
            return null;
    }

    public List<HomeWork> getHomeWorkList() {
        //TODO   线程
        if (init()) {
            return session.getHomeWorkDao().loadAll();
        } else {
            return new ArrayList<>();
        }
    }

    //加入数据库-第一步

    /**
     * 先查询数据库，存在该homework时执行更新操作，不存在时执行插入操作
     *
     * @param homeWork
     */
    public void saveHomeWork(HomeWork homeWork) {
        if (init()) {
            if (queryHomework(homeWork)) {
                updateHomeWork(homeWork);
            } else {
                insertHomeWork(homeWork);
            }
        }
    }

    /**
     * 遍历执行saveHomeWork()方法
     *
     * @param homeWorks
     */
    public void saveHomeWorks(List<HomeWork> homeWorks) {
        if (homeWorks == null || homeWorks.size() == 0) {
            return;
        }
        if (init()) {
            for (int i = homeWorks.size() - 1; i >= 0; i--) {
                saveHomeWork(homeWorks.get(i));
            }
        }
    }

    /*public void saveHomeWorkBeans(final HomeWorkBean homeWorks) {
        if (init()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    saveHomeWorks(beanToDao(homeWorks));
                }
            }).start();
        }
    }*/

    /**
     * HomeWorkBean转换成HomeWork
     */
    /*public List<HomeWork> beanToDao(HomeWorkBean homeWorkBean) {
        if (isEmpty(homeWorkBean)) {
            return null;
        }
        List<HomeWork> homeWorkList = new ArrayList<>();
        List<HomeWorkEntity> list = homeWorkBean.getData().getList();
        for (int i = 0; i < list.size(); i++) {
            HomeWorkEntity entity = list.get(i);
            if (entity.getEvaluated() == 0) {
                HomeWork homeWork = new HomeWork();
                homeWork.setStu_job_id(entity.getStu_job_id());
                homeWork.setJob_id(entity.getJob_id());
                homeWork.setJob_status(entity.getJob_status());
                homeWork.setStu_job_status(entity.getStu_job_status());
                homeWork.setTitle(entity.getTitle());
                homeWork.setCdate(entity.getCdate());
                homeWork.setCday(entity.getCday());
                homeWork.setDeadline(entity.getDeadline());
                homeWork.setPercent(entity.getPercent());
                homeWorkList.add(homeWork);
            }
        }
        return homeWorkList;

    }*/

    /**
     * dao转换成bean 将HomeworkDao转成HomeWorkBean
     *
     * @param dao
     * @return
     */
    public HomeWorkBean DaoToBean(List<HomeWork> dao) {
        HomeWorkBean bean = new HomeWorkBean();
        HomeWorkData data = new HomeWorkData();
        List<HomeWorkEntity> listentity = new ArrayList<>();
        if (dao != null) {
            for (int i = 0; i < dao.size(); i++) {
                HomeWorkEntity entity = new HomeWorkEntity();
                entity.setStu_job_id((int) dao.get(i).getStu_job_id());
                entity.setStu_job_status(dao.get(i).getStu_job_status());
                entity.setJob_id(dao.get(i).getJob_id());
                entity.setJob_status(dao.get(i).getJob_status());
                entity.setCdate(dao.get(i).getCdate());
                entity.setCday(dao.get(i).getCday());
                entity.setDeadline(dao.get(i).getDeadline());
                entity.setTitle(dao.get(i).getTitle());
                entity.setPercent(dao.get(i).getPercent());
                listentity.add(entity);
            }
        }
        data.setList(listentity);
//        bean.setData(data);
        return bean;
    }

    /**
     * 融合数据 将网络的数据（HomeWorkBean ）与本地的Homework进行结合：
     * 1，先遍历网络的集合（HomeWorkBean dataFromNet）,
     * 2，每次获取stu_job_id，再根据主键stu_job_id在数据库中查询，存在的话就把本地的百分比赋值给网络数据，
     * 3，删除本地数据，插入网络数据。
     */
    /*public HomeWorkBean mixtureDbAndDao(HomeWorkBean dataFromNet) {
        if (init()) {
            try {
                for (int i = 0; i < dataFromNet.getData().getList().size(); i++) {
                    HomeWorkEntity entity = dataFromNet.getData().getList().get(i);
                    HomeWork homeWork = session.getHomeWorkDao().load(entity.getStu_job_id());
                    if (homeWork != null) {
                        entity.setPercent(homeWork.getPercent());
                        //TODO   删除小红点标示
                    }
                }
                session.getHomeWorkDao().deleteAll();
                saveHomeWorkBeans(dataFromNet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dataFromNet;
    }*/

    public boolean isEmpty(HomeWorkBean bean) {
        if (bean == null) {
            return true;
        }
        if (bean.getData() == null) {
            return true;
        }
        if (bean.getData().getList() == null) {
            return true;
        }
        if (bean.getData().getList().size() == 0) {
            return true;
        }
        return false;
    }

    //插入到数据库
    public void insertHomeWork(final HomeWork homeWork) {
        if (init()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    session.getHomeWorkDao().insertOrReplace(homeWork);
                }
            }).start();
        }
    }

    //更新数据库
    public void updateHomeWork(final HomeWork homeWork) {
        if (init()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    session.getHomeWorkDao().update(homeWork);
                }
            }).start();
        }
    }

    //查询数据库 存在true 不存在false
    public boolean queryHomework(HomeWork homeWork) {
        if (init()) {
            if (homeWork == null || homeWork.getStu_job_id() == 0 || session == null || session.getHomeWorkDao() == null) {
                return false;
            }
            HomeWork hh = session.getHomeWorkDao().load(homeWork.getStu_job_id());
            if (hh != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * *******************************************以下为作业目录****************************************************************
     */
    //通过stu_job_id查询数据库
    public List<Quiz> getQuizList(long stu_job_id) {
        /*if (init()) {
            List<Quiz> quizList = session.getQuizDao().queryBuilder().where(QuizDao.Properties.Stu_job_id.eq(stu_job_id)).list();
            return quizList;
        }*/
        MWorkQuizeDao mWorkQuizeDao = new MWorkQuizeDao();
        List<WorkQuize> mwqs = mWorkQuizeDao.getAllData();
        List<WorkQuize> wqs = mWorkQuizeDao.getByWorkId(stu_job_id);
        List<Quiz> quizs = new ArrayList<>();
        for (int i = 0; i < wqs.size(); i++) {
            Quiz quiz = session.getQuizDao().load(wqs.get(i).getQuiz_id());
            quizs.add(quiz);
        }
        // return new ArrayList<>();
        return quizs;
    }

    //通过stu_job_id查询数据库  并返回集合大小
    public int getSentenceCount(long stu_job_id) {
        if (init()) {
            List<Sentence> list = session.getSentenceDao().queryBuilder().where(SentenceDao.Properties.Stu_job_id.eq(stu_job_id)).list();
            if (list == null || list.size() == 0) {
                return 0;
            }
            return list.size();
        }
        return 0;
    }

    //查询数据库  存在true 不存在false
    public boolean queryQuiz(long quiz_id) {
        if (init()) {
            Quiz quiz = session.getQuizDao().load(quiz_id);
            if (quiz == null) {
                return false;
            }
            return true;
        }
        return false;
    }

    //加入数据库-第二步

    /**
     * 先查询是否存在该quiz，存在执行更新操作，不存在执行插入操作
     *
     * @param quizs
     */
    public void saveQuizList(final List<Quiz> quizs) {
        if (init()) {
            if (quizs != null && quizs.size() != 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < quizs.size(); i++) {
                            if (queryQuiz(quizs.get(i).getQuiz_id())) {
                                session.getQuizDao().update(quizs.get(i));
                            } else {
                                session.getQuizDao().insertOrReplace(quizs.get(i));
                            }
                        }
                    }
                }).start();
            }
        }
    }

    //加入数据库-第二步
    public void saveQuizList(final List<Quiz> quizs, final long workId) {
        if (init()) {
            if (quizs != null && quizs.size() != 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < quizs.size(); i++) {
                            if (queryQuiz(quizs.get(i).getQuiz_id())) {
                                session.getQuizDao().update(quizs.get(i));
                            } else {
                                session.getQuizDao().insertOrReplace(quizs.get(i));
                            }
                            saveWorkQuize(workId, quizs.get(i).getQuiz_id());
                        }
                    }
                }).start();
            }
        }
    }

    public void saveWorkQuize(long workId, long quizeId) {
        MWorkQuizeDao mWorkQuizeDao = new MWorkQuizeDao();
        mWorkQuizeDao.saveWrokQuize(workId, quizeId);
    }

    /**
     * 删除Quiz
     */
//    public void deleteQuiz(Context context, long stu_job_id) {
//        if (init()) {
//            List<Quiz> list = session.getQuizDao().queryBuilder().where(QuizDao.Properties.Stu_job_id.eq(stu_job_id)).list();
//            if (list != null && list.size() > 0) {
//                for (int i = 0; i < list.size(); i++) {
//                    session.getQuizDao().delete(list.get(i));
//                }
//            }
//        }
//    }

    /**
     * bean与dao的转换
     * omeWorkCatlogBean转换成List<Quiz>
     *
     * @param bean
     */
    public List<Quiz> beanToDao(HomeWorkCatlogBean bean, long stu_job_id) {
        if (!isEmpty(bean)) {
            List<Quiz> quizList = new ArrayList<>();
            for (int i = 0; i < bean.getData().getHomework().getQuizs().size(); i++) {
                HomeWorkCatlogQuiz entity = bean.getData().getHomework().getQuizs().get(i);
                Quiz quiz = new Quiz();
                quiz.setQuiz_id(Long.parseLong(entity.getQuiz_id()));
                quiz.setStu_job_id(stu_job_id);
                quiz.setScore(bean.getData().getHomework().getScore());
                quiz.setTitle(bean.getData().getHomework().getTitle());
                quiz.setDescription(bean.getData().getHomework().getDescription());
                quiz.setLevel(bean.getData().getHomework().getLevel());
                quiz.setCommit_time(bean.getData().getHomework().getCommit_time());
                quiz.setSpend_time(bean.getData().getHomework().getSpend_time());
                quiz.setQuiz_name(bean.getData().getHomework().getQuizs().get(i).getName());
                quiz.setRead_quiz_id(entity.getReading().getHw_quiz_id());
                quiz.setRead_times(entity.getReading().getTimes());
                quiz.setRead_count(entity.getReading().is_done() ? entity.getReading().getTimes() : entity.getReading().getCount());
                quiz.setRepeat_count(entity.getRepeat().is_done() ? entity.getRepeat().getTimes() : entity.getRepeat().getCount());
                quiz.setRepeat_quiz_id(entity.getRepeat().getHw_quiz_id());
                quiz.setRepeat_times(entity.getRepeat().getTimes());
                quiz.setRecite_count(entity.getRecite().is_done() ? entity.getRecite().getTimes() : entity.getRecite().getCount());
                quiz.setRecite_quiz_id(entity.getRecite().getHw_quiz_id());
                quiz.setRecite_times(entity.getRecite().getTimes());
                quizList.add(quiz);
            }
            return quizList;
        }
        return null;
    }

    /**
     * 判断homeworkbean是否为空
     */
    public boolean isEmpty(HomeWorkCatlogBean bean) {
        if (bean == null) {
            return true;
        }
        if (bean.getData() == null) {
            return true;
        }
        if (bean.getData().getHomework().getQuizs() == null) {
            return true;
        }
        if (bean.getData().getHomework().getQuizs().size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询全部
     */
    public List<Sentence> getSentenceList() {
        if (init()) {
            return session.getSentenceDao().queryBuilder().list();
        }
        return new ArrayList<>();
    }
    /************************************************
     * 以下为句子列表
     ************************************/
    //获取句子列表

    /**
     * 通过hw_quiz_id查询该作业的句子列表（hw_quiz_id：句子id与类型（跟读朗读背诵）的结合，又后台指定）
     *
     * @param hw_quiz_id
     * @return
     */
    public List<Sentence> getSentenceListById(long hw_quiz_id) {
        if (init()) {
            return session.getSentenceDao().queryBuilder().where(SentenceDao.Properties.Hw_quiz_id.eq(hw_quiz_id)).list();
        }
        return new ArrayList<>();
    }

    //获取句子的状态

    /**
     * 通过sententce_id,stu_job_id,hw_quiz_id查询该作业的句子列表（hw_quiz_id：句子id与类型（跟读朗读背诵）的结合，又后台指定）
     */
    public List<Sentence> getSentenceListBySentenceId(long sententce_id, long hw_quiz_id, long stu_job_id) {
        if (init()) {
            List<Sentence> sentenceList = session.getSentenceDao().queryBuilder().where(SentenceDao.Properties.Hw_quiz_id.eq(hw_quiz_id), SentenceDao.Properties.Sentence_id.eq(sententce_id), SentenceDao.Properties.Stu_job_id.eq(stu_job_id)).list();
            return sentenceList;
        }
        return new ArrayList<>();
    }

    //加入数据库-第三步

    /**
     * 此处为保存网络数据，所以是第一次保存，对于每一个listEntity 都要赋值stu_done(学生提交作业的标志：true 可提交 false不可提交)为false
     *
     * @param detail
     */
    public void saveDetail(final Detail detail) {
        if (isEmpty(detail)) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < detail.getData().getList().size(); i++) {
                    Detail.DataEntity.ListEntity listEntity = detail.getData().getList().get(i);
                    Sentence sentence = beanToDao(listEntity);
                    sentence.setStu_done(false);
                    saveNetSentence(sentence);
                }
            }
        }).start();

    }

    //加入数据库-保存句子   此处方法为做作业时调用
    public void saveListEntity(final Detail.DataEntity.ListEntity entity) {
        if (init()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    saveSentence(beanToDao(entity));
                }
            }).start();

        }
    }

    public void updataSentence(final Sentence sentence) {
        if (init()) {
            session.getSentenceDao().update(sentence);
        }
    }

    //加入数据库-保存句子   此处方法为做作业时调用
    public void saveSentence(final Sentence sentence) {
        if (init()) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    //sentence为要保存的对象    sentenceList为数据库的数据
                    sentence.setId(System.currentTimeMillis());
                    List<Sentence> sentenceList = getSentenceListBySentenceId(sentence.getSentence_id(), sentence.getHw_quiz_id(), sentence.getStu_job_id());
                    if (sentenceList == null || sentenceList.size() == 0) {
//                        不存在：Stu_done、Stu_mp3、Stu_scoreWords_score、Stu_seconds都设置为当前的音频和分数秒数
                        sentence.setStu_done(true);
                        sentence.setStu_mp3(sentence.getCurrent_mp3());
                        sentence.setStu_score(sentence.getCurrent_score() + "");
                        sentence.setWords_score(sentence.getCurrent_words_score());//3.0新加
                        sentence.setStu_seconds(sentence.getCurrent_stu_seconds());//3.1新加
                        session.getSentenceDao().insert(sentence);
                        return;
                    }
//                    1，当该sentence的当前得分（current_scorec）比数据库的最好成绩（Stu_score）要低或者相等时，
//                      更新数据库的当前成绩（Current_words_score,Current_mp3,Current_score,Current_stu_seconds）
//                    2，当该sentence的当前得分（current_scorec）比数据库的最好成绩（Stu_score）要高时，
//                      更新数据库的当前成绩（Current_words_score,Current_mp3,Current_score,Current_stu_seconds）
//                      和最好成绩（Stu_done,Stu_mp3,Stu_score,Words_score,Stu_seconds）并设置作业可提交（stu_done = true）
                    sentenceList.get(0).setCurrent_words_score(sentence.getCurrent_words_score());//3.0
                    sentenceList.get(0).setCurrent_mp3(sentence.getCurrent_mp3());
                    sentenceList.get(0).setCurrent_score(sentence.getCurrent_score());
                    sentenceList.get(0).setCurrent_stu_seconds(sentence.getCurrent_stu_seconds());//3.1
                    if (getInt(sentenceList.get(0).getStu_score()) > sentence.getCurrent_score()) {
                        session.getSentenceDao().update(sentenceList.get(0));
                        return;
                    }
                    //修改最好成绩  和最好的MP3 为当前的成绩和MP3
                    sentenceList.get(0).setStu_done(true);
                    sentenceList.get(0).setWords_score(sentence.getCurrent_words_score());//3.0
                    sentenceList.get(0).setStu_score(sentence.getCurrent_score() + "");
                    sentenceList.get(0).setStu_mp3(sentence.getCurrent_mp3());
                    sentenceList.get(0).setStu_seconds(sentence.getCurrent_stu_seconds());//3.1
                    sentenceList.get(0).setMp3_url(sentence.getMp3_url());//3.3
                    session.getSentenceDao().update(sentenceList.get(0));
                }
            }).start();

        }
    }

    //    将ListEntity转换成数据库对象Sentence
    public Sentence beanToDao(Detail.DataEntity.ListEntity entity) {
        Sentence sentence = new Sentence();
        sentence.setSentence_id(entity.getSentence_id());
        sentence.setHw_quiz_id(entity.getHw_quiz_id());
        sentence.setStu_job_id(entity.getStu_job_id());
        sentence.setEn(entity.getEn());
        sentence.setMp3(entity.getMp3());
        sentence.setStu_done(entity.isStu_done());
        sentence.setStu_mp3(entity.getStu_mp3());
        sentence.setStu_score(entity.getStu_score() + "");
        sentence.setCurrent_mp3(entity.getCurrent_mp3());
        sentence.setCurrent_score(entity.getCurrent_score());
        sentence.setWords_score(entity.getWords_score());
        sentence.setCurrent_words_score(entity.getCurrent_words_score());//3.0
        sentence.setCurrent_stu_seconds(entity.getCurrent_stu_seconds());//3.1
        sentence.setStu_seconds(entity.getSeconds());//3.1
        sentence.setQuiz_id(entity.getQuiz_id());//3.2新加
        sentence.setMp3_url(entity.getMp3_url());//3.3新加
        return sentence;
    }

    //    将数据库数据（sentenceList）转换成List<ListEntity>。
    public List<Detail.DataEntity.ListEntity> daoToBean(List<Sentence> sentenceList, int b) {
        List<Detail.DataEntity.ListEntity> listEntities = new ArrayList<>();
        for (int i = 0; i < sentenceList.size(); i++) {
            Sentence sentence = sentenceList.get(i);
            Detail.DataEntity.ListEntity entity = new Detail.DataEntity.ListEntity();
            entity.setSentence_id(sentence.getSentence_id());
            entity.setHw_quiz_id(sentence.getHw_quiz_id());
            entity.setStu_job_id(sentence.getStu_job_id());
            entity.setEn(sentence.getEn());
            entity.setMp3(sentence.getMp3());
            entity.setStu_done(sentence.getStu_done());
            entity.setStu_mp3(sentence.getStu_mp3());
            entity.setStu_score(getInt(sentence.getStu_score()));
            entity.setCurrent_mp3(sentence.getCurrent_mp3());
            entity.setCurrent_score(sentence.getCurrent_score());
            entity.setWords_score(sentence.getWords_score());
            entity.setCurrent_words_score(sentence.getCurrent_words_score());//3.0
            entity.setCurrent_stu_seconds(sentence.getCurrent_stu_seconds());//3.1
            entity.setSeconds(sentence.getStu_seconds());//3.1
            listEntities.add(entity);
        }
        return listEntities;
    }

    //    将List<ListEntity>转换成 Detail。
    public Detail getDetail(List<Detail.DataEntity.ListEntity> entities) {
        Detail detail = new Detail();
        Detail.DataEntity dataEntity = new Detail.DataEntity();
        dataEntity.setList(entities);
        detail.setData(dataEntity);
        return detail;
    }

    //保存sentence  如果不存在该sentence则执行插入操作
    public void saveNetSentence(Sentence sentence) {
        if (init()) {
            sentence.setId(System.currentTimeMillis());
            List<Sentence> list = getSentenceListBySentenceId(sentence.getSentence_id(), sentence.getHw_quiz_id(), sentence.getStu_job_id());
            if (list == null || list.size() == 0) {
                session.getSentenceDao().insertOrReplace(sentence);
            } else if (list.size() > 1) {
                //去除重复数据
                for (int i = 1; i < list.size(); i++) {
                    session.getSentenceDao().delete(list.get(i));
                }
            }
        }
    }

    public boolean isEmpty(Detail detail) {
        if (detail == null) {
            return true;
        }
        if (detail.getData() == null) {
            return true;
        }
        if (detail.getData().getList() == null) {
            return true;
        }
        if (detail.getData().getList().size() == 0) {
            return true;
        }
        return false;
    }

    public long getLong(String str) {
        return Long.parseLong(str);
    }

    public int getInt(String str) {
        return Integer.parseInt(str);
    }

    /**
     * 获取平均分  Quiz的平均分
     * 计算该作业的平均分：遍历作业的quiz（一个作业有多个quiz），
     * 每次根据Stu_job_id和Quiz_id获取该题的最高分的Sentence集合，
     * 把这些最高分的Sentence集合合并到一个集合里面（ List<Sentence> sentenceList），
     * 这就是该作业所有的句子最高分的集合，然后遍历sentenceList，
     * 用分数的和除以集合的size得到平均分。
     */
    public int getHomeWorkScore(List<Quiz> quizList) {
        int score = 0;
        if (quizList == null || quizList.size() == 0) {
            return score;
        }
        if (init()) {
            List<Sentence> sentenceList = new ArrayList<>();
            for (int i = 0; i < quizList.size(); i++) {
                Quiz quiz = quizList.get(i);
                List<Sentence> list = getQuizScore(quiz.getStu_job_id(), quiz.getQuiz_id());
                if (list != null && list.size() > 0) {
                    sentenceList.addAll(list);
                }
            }
            for (int i = 0; i < sentenceList.size(); i++) {
                score += stringToInt(sentenceList.get(i).getStu_score());
            }
            if (sentenceList.size() == 0) {
                return 0;
            }
            score = score / sentenceList.size();
        }
        return score;

    }

    /**
     * 获取数据库Quiz的mp3云地址集合
     */

    public List<Sentence> getQuizMp3(long quiz_id) {
        List<Sentence> listById = null;
        if (init()) {
//            session.getQuizDao().l
            listById = getSentenceListById(quiz_id);
        }
        return listById;
    }

    /**
     * 获取Quiz中最高得分的句子集合
     * 获取该Quiz中最高得分的句子集合：
     * 根据stu_job_id和quiz_id查询出句子的集合（sentenceList），
     * 然后遍历sentenceList，向sentences_best中放数据：
     * 1，当所插入数据在sentences_best中存在时则判断分数高低，
     * （1）高于sentences_best里面的sentences时，更新sentences_best里面的sentences。
     * （2）低于sentences_best里面的sentences时略过。
     * 2，当所插入数据在sentences_best中不存在时，直接放入sentences_best中。
     */
    public List<Sentence> getQuizScore(long stu_job_id, long quiz_id) {
        if (init()) {
            List<Sentence> sentenceList = session.getSentenceDao().queryBuilder().where(SentenceDao.Properties.Stu_job_id.eq(stu_job_id + ""), SentenceDao.Properties.Quiz_id.eq(quiz_id + ""), SentenceDao.Properties.Stu_mp3.isNotNull(), SentenceDao.Properties.Stu_score.isNotNull(), SentenceDao.Properties.Stu_score.notEq("0")).list();
            if (sentenceList == null || sentenceList.size() == 0) {
                return sentenceList;
            }
            List<Sentence> sentences_best = new ArrayList<>();
            for (int i = 0; i < sentenceList.size(); i++) {
                Sentence sentence = sentenceList.get(i);
                boolean flag = true;
                for (int j = 0; j < sentences_best.size(); j++) {
                    Sentence sentence_best = sentences_best.get(j);
                    if ((sentence_best.getSentence_id() + "").equals(sentence.getSentence_id() + "")) {
                        flag = false;
                        if (stringToInt(sentence_best.getStu_score()) < stringToInt(sentence.getStu_score())) {
                            sentence_best.setStu_score(sentence.getStu_score());
                        }
                    }
                }
                if (flag) {
                    sentences_best.add(sentence);
                }
            }
            return sentences_best;
        } else {
            return null;
        }
    }

    public int stringToInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("分数转成int出现问题：" + string);
        }
        return 0;
    }

    /**
     * 获取上传数据：查询stu_job_id且stu_done的属性为true的数据
     */
    public List<Sentence> getUploadData(String stu_job_id) {
        if (init()) {
            List<Sentence> up = session.getSentenceDao().queryBuilder().where(SentenceDao.Properties.Stu_job_id.eq(stu_job_id), SentenceDao.Properties.Stu_done.eq("true")).list();
            return up;
        }
        return new ArrayList<>();
    }

    /**
     * 1，根据hw_stu_id 和quiz_id查询到该题Quiz的句子集合
     * 2，根据quiz_id查询到该题Quiz，再根据currentType判断属于哪一个类型（0-repeat 1-read 2-recite）
     * 3，然后判断当前遍数与总遍数的关系：
     * （1）+1后刚好与总遍数相等，说明该遍刚好做完，此时将当前遍数设置为+1
     * （2）+1后小于总遍数，书名该遍数没有做完，此时需要把该题（quiz_id）下的该类型(hw_stu_id)的句子的当前成绩改成空,然后更新句子
     * 4，更新quiz
     *
     * @param hw_stu_id
     * @param currentType
     * @param quiz_id
     */
    public void addCount(String hw_stu_id, String currentType, long quiz_id) {
        if (init()) {
            Quiz quiz = session.getQuizDao().load(quiz_id);
            List<Sentence> sentenceList = getSentenceListById(getLong(hw_stu_id));
            int count = 0;
            int total = 0;
            if (currentType.equals("0")) {//repeat
                if (hw_stu_id.equals(quiz.getRepeat_quiz_id() + "")) {
                    count = quiz.getRepeat_count();
                    total = quiz.getRepeat_times();
                    if (count + 1 < total) {
                        count++;
                        quiz.setRepeat_count(count);
                        for (int i = 0; i < sentenceList.size(); i++) {
                            sentenceList.get(i).setCurrent_score(0);
                            sentenceList.get(i).setCurrent_mp3("");
                            sentenceList.get(i).setCurrent_words_score("");
                            session.getSentenceDao().update(sentenceList.get(i));
                        }
                    } else if (count + 1 == total) {
                        count++;
                        quiz.setRepeat_count(count);
                    }
                }
                session.getQuizDao().update(quiz);
                return;
            }
            if (currentType.equals("1")) {//recite
                if (hw_stu_id.equals(quiz.getRecite_quiz_id() + "")) {
                    count = quiz.getRecite_count();
                    total = quiz.getRecite_times();
                    if (count + 1 < total) {
                        count++;
                        quiz.setRecite_count(count);
                        for (int i = 0; i < sentenceList.size(); i++) {
                            sentenceList.get(i).setCurrent_score(0);
                            sentenceList.get(i).setCurrent_mp3("");
                            sentenceList.get(i).setCurrent_words_score("");
                            session.getSentenceDao().update(sentenceList.get(i));
                        }
                    } else if (count + 1 == total) {
                        count++;
                        quiz.setRecite_count(count);
                    }
                }
                session.getQuizDao().update(quiz);
                return;
            }
            if (currentType.equals("2")) {//read
                if (hw_stu_id.equals(quiz.getRead_quiz_id() + "")) {
                    count = quiz.getRead_count();
                    total = quiz.getRead_times();
                    if (count + 1 < total) {
                        count++;
                        quiz.setRead_count(count);
                        for (int i = 0; i < sentenceList.size(); i++) {
                            sentenceList.get(i).setCurrent_score(0);
                            sentenceList.get(i).setCurrent_mp3("");
                            sentenceList.get(i).setCurrent_words_score("");
                            session.getSentenceDao().update(sentenceList.get(i));
                        }
                    } else if (count + 1 == total) {
                        count++;
                        quiz.setRead_count(count);
                    }
                }
                session.getQuizDao().update(quiz);
                return;
            }
        }

    }

    /**
     * 在WorkcatelogActivity中调用，用来修改quiz的遍数以及作业的百分比和耗时
     * 1，调用addCount(hw_stu_id, currentType, quiz_id)修改数据库中quiz的遍数
     * 2，根据stu_job_id查询出该作业的quiz集合，遍历该集合，每次设置一下耗时
     * 3，根据已做遍数除以总遍数得到百分比（0-100  此数据用于提交）
     * 4，根据stu_job_id查询到homework，更新百分比
     *
     * @param hw_stu_id
     * @param stu_job_id
     * @param spendtime
     * @param currentType
     * @param quiz_id
     */
    public void addCount(String hw_stu_id, String stu_job_id, long spendtime, String currentType, long quiz_id) {
        if (init()) {
            addCount(hw_stu_id, currentType, quiz_id);
            List<Quiz> quizList = session.getQuizDao().queryBuilder().where(QuizDao.Properties.Stu_job_id.eq(stu_job_id)).list();
            int count_sum = 0;
            int total_sum = 0;
            if (quizList != null && quizList.size() != 0) {
                for (int i = 0; i < quizList.size(); i++) {
                    Quiz quiz = quizList.get(i);
                    if (spendtime != -1l) {
                        String time = quiz.getSpend_time();
                        quiz.setSpend_time(getTime(time, spendtime));


                        String upload_spend_time = quiz.getUpload_spend_time();
                        if (TextUtils.isEmpty(upload_spend_time)) {
                            upload_spend_time = "0";
                        }
                        quiz.setUpload_spend_time(getTime(upload_spend_time, spendtime));
                    }
                    count_sum += quiz.getRead_count();
                    total_sum += quiz.getRead_times();
                    count_sum += quiz.getRepeat_count();
                    total_sum += quiz.getRepeat_times();
                    count_sum += quiz.getRecite_count();
                    total_sum += quiz.getRecite_times();
                }
            }
            saveQuizList(quizList);
            HomeWork homeWork = session.getHomeWorkDao().load(getLong(stu_job_id));
            if (total_sum != 0) {
                homeWork.setStu_job_status("1");
                homeWork.setPercent(count_sum * 100 / total_sum);
            }
            session.getHomeWorkDao().update(homeWork);
        }

    }

    /**
     * addTime( String stu_job_id, long spendtime) -> 修改该作业的耗时时间
     * 用stu_job_id查询出该作业的quiz的集合，遍历设置耗时时间（getTime(time, spendtime)：将两个时间相加）
     *
     * @param stu_job_id
     * @param spendtime
     */
    public void addTime(String stu_job_id, long spendtime) {
        if (init() && spendtime != -1l) {
            List<Quiz> quizList = session.getQuizDao().queryBuilder().where(QuizDao.Properties.Stu_job_id.eq(stu_job_id)).list();
            if (quizList != null && quizList.size() != 0) {
                for (int i = 0; i < quizList.size(); i++) {
                    Quiz quiz = quizList.get(i);
                    String time = quiz.getSpend_time();
                    quiz.setSpend_time(getTime(time, spendtime));
                    //3.3 累积耗时时间
                    String upload_spend_time = quiz.getUpload_spend_time();
                    if (TextUtils.isEmpty(upload_spend_time)) {
                        upload_spend_time = "0";
                    }
                    quiz.setUpload_spend_time(getTime(upload_spend_time, spendtime));
                }
            }
            saveQuizList(quizList);
            HomeWork homeWork = session.getHomeWorkDao().load(getLong(stu_job_id));
            homeWork.setStu_job_status("1");
            session.getHomeWorkDao().update(homeWork);
        }
    }

    public String getTime(String time, long t) {
        long l = 0;
        if (TextUtils.isEmpty(time)) {
            return (l + t) + "";
        }
        try {
            if (time.contains(".")) {
                time = time.substring(0, time.indexOf("."));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        l = getLong(time);
        l += t;
        return l + "";
    }

    public String getTime(String stu_job_id) {
        if (init()) {
            List<Quiz> quizList = session.getQuizDao().queryBuilder().where(QuizDao.Properties.Stu_job_id.eq(stu_job_id)).list();
            if (quizList == null || quizList.size() == 0) {
                return "0";
            }
            return quizList.get(0).getSpend_time();
        }
        return "0";
    }

    public String getUploadSpendTime(String stu_job_id) {
        if (init()) {
            List<Quiz> quizList = session.getQuizDao().queryBuilder().where(QuizDao.Properties.Stu_job_id.eq(stu_job_id)).list();
            if (quizList == null || quizList.size() == 0) {
                return "0";
            }
            return quizList.get(0).getUpload_spend_time();
        }
        return "0";
    }

    public void cleanUploadSpendTime(String stu_job_id) {
        if (init()) {
            List<Quiz> quizList = session.getQuizDao().queryBuilder().where(QuizDao.Properties.Stu_job_id.eq(stu_job_id)).list();
            if (quizList == null || quizList.size() == 0) {
                return;
            }
            quizList.get(0).setUpload_spend_time("0");
        }

    }

    /**
     * 获取百分比
     */
    public Integer getPercent(long stu_job_id) {
        if (init()) {
            HomeWork homeWork = session.getHomeWorkDao().load(stu_job_id);
            return homeWork == null ? 0 : homeWork.getPercent();
        }
        return 0;
    }

    /**
     * isFieldExist(SQLiteDatabase db, String tableName)-->判断某表里某字段是否存在
     * 下面方法可以得到该tableName数据库的创建数据库的sql字符串
     *
     * @param db
     * @param tableName
     * @return
     */
    public String isFieldExist(SQLiteDatabase db, String tableName) {
        String queryStr = "select sql from sqlite_master where type = 'table' and name = '%s'";
        queryStr = String.format(queryStr, tableName);
        Cursor c = db.rawQuery(queryStr, null);
        String tableCreateSql = null;
        try {
            if (c != null && c.moveToFirst()) {
                tableCreateSql = c.getString(c.getColumnIndex("sql"));
            }
        } finally {
            if (c != null)
                c.close();
        }
        return tableCreateSql;
    }

    /**
     * 更新stu_job_id的百分比  已做遍数除以总遍数
     *
     * @param stu_job_id
     */
    public void updateHomeWorkPercent(final long stu_job_id) {
        if (init()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HomeWork homework = session.getHomeWorkDao().load(stu_job_id);
                    if (homework == null)
                        return;

                    List<Quiz> quizList = getQuizList(stu_job_id);
                    int count = 0;
                    int total = 0;
                    try {
                        for (int i = 0; i < quizList.size(); i++) {
                            count += (quizList.get(i).getRecite_count() + quizList.get(i).getRepeat_count() + quizList.get(i).getRead_count());
                            total += (quizList.get(i).getRecite_times() + quizList.get(i).getRepeat_times() + quizList.get(i).getRead_times());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (total != 0) {
                        homework.setPercent(count * 100 / total);
                    }
                    if (count == total) {
                        homework.setStu_job_status("1");
                    }
                    session.update(homework);
                }
            }).start();
        }

    }

    /**
     * *******************************************以下为评语内容****************************************************************
     */
    public List<Comment> getCommentList() {
        if (init()) {
            return session.getCommentDao().loadAll();
        }
        return new ArrayList<>();
    }

    public void insertOrReplaceComment(final Comment comment) {
        if (init()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    session.getCommentDao().insertOrReplace(comment);
                }
            }).start();

        }
    }

    /**
     * *******************************************以下为清理文件****************************************************************
     *
     * @param homeWorks_db
     */
//    public void clearDB(final Context context, final List<HomeWork> homeWorks_db){
//        if (homeWorks_db==null||homeWorks_db.size()==0)
//            return;
//        //一 删除数据库
//        init(context);
//        for (int i = 0 ; i < homeWorks_db.size() ; i ++){
//            //  1删除HomeWork
//            session.getHomeWorkDao().delete(homeWorks_db.get(i));
//            //  2删除Quiz
//            deleteQuiz(context,homeWorks_db.get(i).getStu_job_id());
//            //  3删除Sentence-删除文件
//            deleteSentenceByStuJobId(context,homeWorks_db.get(i).getStu_job_id());
//        }
//    }
    private boolean isFileClear = false;

    /**
     * clearFile-->清理文件
     * 1，当前作业列表为空时，表示数据库中数据已过期，所以将homework、quiz和sentence中的数据清空，并把缓存文件夹下的文件（MediaPathUtils.DOWNLOAD_DIR）清空
     * 2，当前作业列表不为空时，执行方法clearFileByDb（清除部分文件）。
     *
     * @param context
     */
    public void clearFile(final Context context) {
        if (init()) {
            return;
        }
        if (session == null || session.getHomeWorkDao() == null) {
            return;
        }

        if (isFileClear) {
            LogUtils.e("正在删除中。。。");
        } else {

            isFileClear = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        LogUtils.e("删除开始。。。");
                        List<HomeWork> homworks = session.getHomeWorkDao().loadAll();
                        if (homworks == null || homworks.size() == 0) {
                            //清除所有
                            LogUtils.e("删除所有文件。。。");
                            clearAllFile();
                            session.getHomeWorkDao().deleteAll();
                            session.getQuizDao().deleteAll();
                            session.getSentenceDao().deleteAll();
                            isFileClear = false;
                        } else {
                            LogUtils.e("删除部分文件。。。");
                            //清除部分
                            clearFileByDb(context, homworks);
                            isFileClear = false;
                        }
                    } catch (Exception e) {
                        Log.e("clearFile", "清除文件出问题了");
                        e.printStackTrace();
                    }
                }

            }).start();

        }

    }

    public void clearAllFile() {
        File musicdownload = new File(MediaPathUtils.DOWNLOAD_DIR);
        if (musicdownload != null && musicdownload.exists()) {
            File[] files = musicdownload.listFiles();
            for (File f : files) {
                if (f != null && f.isFile())
                    f.delete();
            }
        }
    }

    /**
     * 将homworks遍历，并把该作业下的sentence集合整合到一个集合sentences里面
     * 1，当集合sentences为空时，清除所有文件。
     * 2，当集合不为空时：嵌套遍历文件列表files和集合sentences，
     * 当该文件在sentences集合中（以stu_mp3，mp3，current_mp3的形式存在）时，不删除，
     * 不在集合中时删除。
     *
     * @param context
     * @param homworks
     */
    public void clearFileByDb(Context context, List<HomeWork> homworks) {
        if (homworks == null || homworks.size() == 0) {
            LogUtils.e("数据库中无数据。。。");
            return;
        }
        File musicdownload = new File(MediaPathUtils.DOWNLOAD_DIR);
        File[] files = musicdownload.listFiles();
        if (files == null || files.length == 0) {
            LogUtils.e("文件夹中无文件。。。");
            return;
        }
        if (init()) {
            List<Sentence> sentences = new ArrayList<>();
            for (int i = 0; i < homworks.size(); i++) {
                sentences.addAll(session.getSentenceDao().queryBuilder().where(SentenceDao.Properties.Stu_job_id.eq(homworks.get(i).getStu_job_id())).list());
            }
            if (sentences == null || sentences.size() == 0) {
                clearAllFile();
                return;
            }
            for (File f : files) {
                String path = f.getAbsolutePath();
                if (path != null) {
                    boolean delete = true;
                    for (Sentence s : sentences) {
                        String stu_mp3 = s.getStu_mp3();
                        String mp3 = MediaPathUtils.DOWNLOAD_DIR + File.separator + MediaPathUtils.getFileNameHash(s.getMp3());
                        String current_mp3 = s.getCurrent_mp3();
                        if (stu_mp3 != null && stu_mp3.equals(path)) {
                            delete = false;
                            break;
                        }
                        if (mp3 != null && mp3.equals(path)) {
                            delete = false;
                            break;
                        }
                        if (current_mp3 != null && current_mp3.equals(path)) {
                            delete = false;
                            break;
                        }
                    }
                    if (delete) {
                        LogUtils.e("文件路径：:" + path);
                        f.delete();
                    }
                }
            }
        }
    }
}
