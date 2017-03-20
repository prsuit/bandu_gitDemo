package me.bandu.talk.android.phone.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.App;
import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.AddClassActivity;
import me.bandu.talk.android.phone.activity.FeedBackActivity;
import me.bandu.talk.android.phone.activity.LoginActivity;
import me.bandu.talk.android.phone.activity.TaskCatalogActivity;
import me.bandu.talk.android.phone.activity.WorkCommitOverDueActivity;
import me.bandu.talk.android.phone.activity.WorkCommitOverDueNoneActivity;
import me.bandu.talk.android.phone.adapter.StudentWorkAdapter;
import me.bandu.talk.android.phone.bean.CancleClassBean;
import me.bandu.talk.android.phone.bean.HomeWorkBean;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.bean.UserClassListBean;
import me.bandu.talk.android.phone.greendao.bean.ClassTabelBean;
import me.bandu.talk.android.phone.greendao.bean.TaskListBean;
import me.bandu.talk.android.phone.greendao.utils.DBUtils;
import me.bandu.talk.android.phone.impl.FragmentChangeLisener;
import me.bandu.talk.android.phone.impl.ImageloadCallback;
import me.bandu.talk.android.phone.middle.AddClassMiddle;
import me.bandu.talk.android.phone.middle.CancleClassMiddle;
import me.bandu.talk.android.phone.middle.GetClassMiddle;
import me.bandu.talk.android.phone.middle.StudentWorkMiddle;
import me.bandu.talk.android.phone.utils.BitmapUtil;
import me.bandu.talk.android.phone.utils.DateUtils;
import me.bandu.talk.android.phone.utils.PreferencesUtils;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.utils.WorkCatalogUtils;
import me.bandu.talk.android.phone.view.CircleImageView;

/**
 * Created by GaoNan on 2015/11/18.
 */

public class StudentWorkFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseActivityIF, View.OnClickListener, AdapterView.OnItemClickListener, RadioGroup.OnCheckedChangeListener, AbsListView.OnScrollListener, FragmentChangeLisener, DBUtils.inter {

    private static final int GET_LIST_DB = 102;
    private static final int FAIL_FROM_NET = 104;
    private ImageView rightImg;
    private ListView listView;
    public View view_info;
    private ImageView head_vator;
    public StudentWorkAdapter adapter;
    private RadioButton radio_left;
    private RadioButton radio_right;
    private RadioGroup group;
    private View view;
    private StudentWorkMiddle middle;
    private HomeWorkBean dataFromNet;
    private HomeWorkBean data_over;
    private FrameLayout fl_class;
    private LinearLayout ll_no_class;
    private LinearLayout ll_wait_class;
    private final static int FRESH = 100;
    private final static int LOAD = 110;
    private int state = 0;
    private String uid;
    private LoginBean.DataEntity uerInfo = PreferencesUtils.getUserInfo();
    int current_page_over = 1;
    int total = 0;
    /*****************
     * 头像动画
     *********************/
    private boolean isImageInit = false;
    private LinearLayout title_num;
    private TextView title_text_num;
    private int imagePosition = 1;
    private int title_layout_height = 0;//标题的高度
    private Bitmap bitmap;//图片
    private TextView tv_join_class;
    private ImageView up_down;
    private TextView tv_noclass_join;
    private TextView tv_cancle_class;
    private TextView tv_classname;
    private CircleImageView noclass_icon;
    private CircleImageView waitclass_icon;
    private SwipeRefreshLayout srlSthHome;

    public static StudentWorkFragment thiz;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = null;
            ViewParent v = view.getParent();
            if (v instanceof ViewGroup) {
                parent = (ViewGroup) v;
            }
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.student_work, null);
            init(view);
        }
        return view;
    }


    /**
     * 初始化控件
     *
     * @param view
     */
    private void init(View view) {
        thiz = this;
        srlSthHome = (SwipeRefreshLayout) view.findViewById(R.id.srlSthHome);
        srlSthHome.setOnRefreshListener(this);
        waitclass_icon = (CircleImageView) view.findViewById(R.id.waitclass_icon);
        noclass_icon = (CircleImageView) view.findViewById(R.id.noclass_icon);
        tv_classname = (TextView) view.findViewById(R.id.tv_classname);
        tv_cancle_class = (TextView) view.findViewById(R.id.tv_cancle_class);
        tv_cancle_class.setOnClickListener(this);
        tv_noclass_join = (TextView) view.findViewById(R.id.tv_noclass_join);
        tv_noclass_join.setOnClickListener(this);
        up_down = (ImageView) view.findViewById(R.id.up_down);
        up_down.setOnClickListener(this);
        tv_join_class = (TextView) view.findViewById(R.id.tv_join_class);
        tv_join_class.setOnClickListener(this);
        view.findViewById(R.id.goback).setVisibility(View.INVISIBLE);
        head_vator = (ImageView) view.findViewById(R.id.imageview);
        rightImg = (ImageView) view.findViewById(R.id.image);
        rightImg.setOnClickListener(this);
        view_info = view.findViewById(R.id.includ_info);
        fl_class = (FrameLayout) view.findViewById(R.id.have_class);
        ll_no_class = (LinearLayout) view.findViewById(R.id.ll_no_class);
        ll_wait_class = (LinearLayout) view.findViewById(R.id.ll_wait_class);
        radio_left = (RadioButton) view.findViewById(R.id.radio_left);
        radio_right = (RadioButton) view.findViewById(R.id.radio_right);
        group = (RadioGroup) view.findViewById(R.id.textinfo);
        listView = (ListView) view.findViewById(R.id.listview);
        listView.setSmoothScrollbarEnabled(true);
        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        listView.setOnItemClickListener(this);
        listView.setDividerHeight(0);
//        listView.setPullRefreshEnable(true);
//        listView.setPullLoadEnable(true);//不可上啦加载
//        listView.setXListViewListener(this);
        middle = new StudentWorkMiddle(this, getActivity());
        adapter = new StudentWorkAdapter(getActivity(), null, this, this, uerInfo);
        listView.setAdapter(adapter);

        group.setOnCheckedChangeListener(this);
        listView.setOnScrollListener(this);
        group.check(radio_left.getId());

        listView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //当可见作业为2个时  不考虑补位item
                if (adapter != null && adapter.getCount() <= 4) {
                    return;
                }
                try {
                    int firstposition = listView.getFirstVisiblePosition();
                    //当第一可见item为0时   且高度为0（有下拉刷新的情况   会影响高度的判断）
                    // 的情况根据高度判断需要加入多少个补位item
                    if (firstposition != 0 || listView.getChildAt(firstposition).getHeight() != 0) {
                        return;
                    }
                    int visbleHeight = listView.getHeight();//ListView 的高度
                    int listcount = adapter.getCount() - 2;//作业的条目  不包含头像和选项卡
                    int imageheight = listView.getChildAt(1).getHeight();//头像的高度
                    int infoheight = listView.getChildAt(2).getHeight();//选项卡的高度
                    int homeworkheight = listView.getChildAt(3).getHeight();//作业条目的高度
                    //在屏幕可见作业条目的个数-即使只露出一点也算1个
                    int kejiantiaomu = (visbleHeight - imageheight - infoheight) / homeworkheight + ((float) (visbleHeight - imageheight - infoheight) % homeworkheight == 0f ? 0 : 1);
                    // 最低补位个数   计算一个头像条目相当于几个作业条目  以此作为最低的补位item 的个数
                    int minnum = (imageheight / homeworkheight) + ((float) imageheight % homeworkheight == 0f ? 0 : 1);
                    //当不可见的个数小于最低补位个数时   加入相应的补位item
                    if (listcount - kejiantiaomu < minnum) {
                        //加入少的补位item
                        adapter.addItem(minnum - (listcount - kejiantiaomu));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("listview补位失败");
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        adapter.checkID = checkedId;
        if (adapter.checkID == radio_left.getId()) {
            //adapter.setDataDoing();
            getHomeWork();
        } else {
            // if (first_over) {
            getHomeWorkOver();
            //   } else {
            //   adapter.setDataOver();
            //   }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (adapter != null && !adapter.isNowork()) {
            LogUtils.i("当前位置：" + position);
            if (position >= 2 && !isFastClick()) {
                //判断是过期作业   未过期作业的
                boolean flag = adapter.getStatus();
                HomeWorkBean.DataBean.ListBean entity = adapter.getData(position);
                //获取完成的百分比
                int percent = Integer.parseInt(entity.getPercent());
                if (entity.isEmpty()) {
                    return;
                }
                final Intent intent;
                if (flag) {
                    if (entity.getEvaluated() == 0) {// 0 为未评价
                        //job_status 作业状态 1正常 0已撤销
//                        未过期作业：1、已评价：过期有结果界面  2、正常（0未完成 1已完成 2已检查）：未过期界面 3、已撤销 （0未完成 1已完成 2已检查） ：后台不提供数据   -跳转到过期无结果
//                        已过期作业：0未完成-》过期无结果界面  其他的情况：过期有结果界面
                        if (entity.getJob_status() == 0) {// 0为被撤销
                            LogUtils.i("此作业已被撤销");
                            intent = new Intent(getActivity(), WorkCommitOverDueActivity.class);
                        } else {//未过期   百分百的情况
                            LogUtils.i("未过期   正常的情况");
//                            intent = new Intent(getActivity(), WorkCatalogActivity.class);
                            intent = new Intent(getActivity(), TaskCatalogActivity.class);
                        }
                    } else {//   已评价
                        LogUtils.i("未过期   已评价的情况");
                        intent = new Intent(getActivity(), WorkCommitOverDueActivity.class);
                        intent.putExtra("evaluated", true);
                    }
                } else {
                    //stu_job_status 学生作业状态 0未完成 1已完成 2已检查
                    if (entity.getStu_job_status() == 0) {//0  为没被撤销
                        intent = new Intent(getActivity(), WorkCommitOverDueNoneActivity.class);
                    } else {
                        if (entity.getEvaluated() == 0) {//TODO   修改
                            intent = new Intent(getActivity(), WorkCommitOverDueActivity.class);
                        } else {
                            intent = new Intent(getActivity(), WorkCommitOverDueActivity.class);
                            intent.putExtra("evaluated", true);
                        }
                    }
                }
                Bundle bundle = new Bundle();
                //百分比
                bundle.putInt("percent", percent);
                boolean isDone = adapter.getIsDone(position);
                bundle.putBoolean("isdone", adapter.getIsDone(position));
                bundle.putString("job_id", entity.getJob_id() + "");
                bundle.putString("stu_job_id", entity.getStu_job_id() + "");
                bundle.putString("uid", PreferencesUtils.getUid());
                bundle.putBoolean("status", adapter.getStatus(position));
                bundle.putSerializable("data", entity);
                intent.putExtra("data", bundle);
                showMyprogressDialog();


                TaskListBean taskListList = new TaskListBean();
                taskListList.setTaskPercentage(percent);
                taskListList.setTaskID(entity.getStu_job_id());


                DBUtils.getDbUtils().updateTaskListBean(new DBUtils.inter() {
                    @Override
                    public void getList(List<?> list, boolean bb, int sign) {
                        if (bb)
                            UIUtils.runInMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().startActivity(intent);
                                    hideMyprogressDialog();
                                }
                            });
                        else {
                            UIUtils.showToastSafe("数据错误.请重新尝试");
                            UIUtils.runInMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    hideMyprogressDialog();
                                }
                            });
                        }

                    }

                    @Override
                    public void isHaveNum(int state, List<?> list, boolean b, TaskListBean taskListBean, int sign) {

                    }
                }, 101, Long.valueOf(PreferencesUtils.getUid()), Long.valueOf(PreferencesUtils.getUserInfo().getCid()), taskListList);


            }
        }
    }


    /**
     * 每次listview滑动停止是调用此方法  所以在此处判断停止的点
     * 如果是头像的position时-> 不满一半高度回滚   超过一半让该item滚出屏幕
     *
     * @param view        当前的listview
     * @param scrollState
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    /**
     * 每次滑动时   以固定时间调用此方法
     *
     * @param view
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     */

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem == 2) {
            if (view_info != null) {
                view_info.setVisibility(View.VISIBLE);
                if (adapter.checkID == -1) {
                    adapter.checkID = radio_left.getId();
                }
                group.check(adapter.checkID);
            }
        }
        if (firstVisibleItem < 2) {
            if (view_info != null) {
                view_info.setVisibility(View.GONE);
                adapter.setCheckID(adapter.checkID);
            }
        }
        if (title_num != null && title_text_num != null) {
            if (firstVisibleItem > imagePosition) {
                title_num.setVisibility(View.VISIBLE);
                title_text_num.setText(adapter.getUploadCount() + "");
            } else {
                if (firstVisibleItem == imagePosition) {
                    if (view.getChildAt(firstVisibleItem).getY() == 0.0f) {
                        title_num.setVisibility(View.VISIBLE);
                        title_text_num.setText(adapter.getUploadCount() + "");
                        return;
                    }
                }
                title_num.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        showMyprogressDialog();
        new GetClassMiddle(StudentWorkFragment.this, getActivity()).getClassList(new UserClassListBean(), GetClassMiddle.GET_CLASS_ASY);

       /* saveClassList(new DBUtils.DBSaveOk() {
            @Override
            public void saveOK() {

            }
        });
        if ("1".equals(uerInfo.getState())) {
            if (adapter.checkID == radio_left.getId()) {
                getHomeWork();
            } else {
                getHomeWorkOver();
            }
        } else {
            if (srlSthHome.isRefreshing())
                srlSthHome.setRefreshing(false);
        }*/

    }

    /**
     * 上拉刷新
     */
/*    @Override
    public void onLoadMore() {
        if ("1".equals(uerInfo.getState())) {
            if (group.getCheckedRadioButtonId() == radio_right.getId()) {
                getHomeWorkOverAdd();
            } else {
                listView.stopLoadMore();
            }
        } else {
            listView.stopLoadMore();
            listView.stopRefresh();
        }
    }*/

    /**
     * 网络获取未过期作业
     */
    public void getHomeWork() {
        //showMyprogressDialog();
        middle.getHomeWork(PreferencesUtils.getUid(), "1", "1000");
    }

    /**
     * 网络获取已过期作业
     */
    public void getHomeWorkOver() {
        showMyprogressDialog();
        state = FRESH;
        first_over = false;
        middle.getHomeWorkOver(PreferencesUtils.getUid(), "1", "1000");
    }

    /**
     * 网络获取已过期作业
     */
    public void getHomeWorkOverAdd() {
        showMyprogressDialog();
        first_over = false;
        if (current_page_over + 1 <= total) {
            state = LOAD;
            current_page_over++;
            middle.getHomeWorkOver(uid, current_page_over + "", "10");
        } else {
            UIUtils.showToastSafe(UIUtils.getString(R.string.tip_lastpage));
        }
    }

    public List<UserClassListBean.DataBean.ClassListBean> net_class_list;
    private static final int GET_CLASS_DB = 101;


    public void checkRedPoint() {
        if (net_class_list == null || net_class_list.size() == 0) {
            if (adapter != null) {
                adapter.setShowRedPoint(false);
                UIUtils.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
            return;
        }
        DBUtils.getDbUtils().getClassListInfo(new DBUtils.inter() {
            @Override
            public void getList(List<?> list, boolean bb, int sign) {
                List<ClassTabelBean> db_data = (ArrayList<ClassTabelBean>) list;
                int count = 0;
                int dbCount = 0;
                if (sign == GET_CLASS_DB && list != null && list.size() != 0) {
                    for (UserClassListBean.DataBean.ClassListBean netBean : net_class_list) {
                        if (netBean.getStatus() == 1) {
                            count++;
                            for (ClassTabelBean dbBean : db_data) {
                                String last_job_time = netBean.getLast_job_time();
                                last_job_time = DateUtils.parseTime(last_job_time);
                                if (netBean.getCid() == dbBean.getClassID() && last_job_time.equals(dbBean.getTaskLatestTime() + "")) {
                                    dbCount++;
                                    break;
                                }
                            }
                        }

                    }
                    if (adapter != null) {
                        adapter.setShowRedPoint(count != dbCount);
                    }

                } else {
                    if (adapter != null) {
                        adapter.setShowRedPoint(false);
                    }
                }
                if (adapter != null) {
                    UIUtils.runInMainThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void isHaveNum(int state, List<?> list, boolean b, final TaskListBean taskListBean, int sign) {

            }
        }, GET_CLASS_DB, Long.valueOf(uerInfo.getUid()));
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        super.onSuccess(result, requestCode);
        hideMyprogressDialog();
        boolean contain_flag = false;
        if (requestCode == GetClassMiddle.GET_CLASS) {
            UserClassListBean bean = (UserClassListBean) result;
            checkClassState(contain_flag, bean);
            //网络获取的班级列表

            net_class_list = bean.getData().getClass_list();
            for (UserClassListBean.DataBean.ClassListBean listBean : net_class_list) {
                listBean.setLast_job_time(DateUtils.parseTime(listBean.getLast_job_time()));
            }
            String cid = PreferencesUtils.getUserInfo().getCid();
            UserClassListBean.DataBean.ClassListBean curClass = null;
            if (!TextUtils.isEmpty(cid)) {
                for (UserClassListBean.DataBean.ClassListBean listBean : net_class_list) {
                    if (cid.equals(String.valueOf(listBean.getCid()))) {
                        curClass = listBean;
                        break;
                    }
                }
            }
            getDataFromNet();
            saveCurClass(curClass);
//            for (UserClassListBean.DataBean.ClassListBean changBean : class_list){
//                changBean.setLast_job_time(DateUtils.parseTime(changBean.getLast_job_time()));
//            }
            //UserClassListBean.DataBean.ClassListBean curr_class = getCurrClass(class_list);
            //StudentFragmentUtils.setDbClassData(class_list, uerInfo, adapter,curr_class);
        }
        if (requestCode == CancleClassMiddle.CANCLE_CLASS) {//取消班级
            uerInfo.setCid(null);
            uerInfo.setClass_name(null);
            uerInfo.setState(null);
            PreferencesUtils.saveUserInfo(uerInfo);
            SharedPreferences sp = getActivity().getSharedPreferences(ConstantValue.SPCONFIG, Context.MODE_PRIVATE);
            sp.edit().putString("Cid", "").putString("cState", "").putString("cName", "").putString("cUid", PreferencesUtils.getUid()).commit();
            onRefresh();
        }

        if (requestCode == GetClassMiddle.GET_CLASS_ASY) {
            UserClassListBean bean = (UserClassListBean) result;
            List<UserClassListBean.DataBean.ClassListBean> l = bean.getData().getClass_list();
            if (l != null && l.size() > 0) {

                checkClassState(false, bean);

                List<String> ids = new ArrayList<>();
                for (UserClassListBean.DataBean.ClassListBean clb : l) {
                    ids.add(String.valueOf(clb.getCid()));
                }
                uerInfo = UserUtil.getUerInfo(getActivity());
                uerInfo = PreferencesUtils.getUserInfo();
                if (!ids.contains(uerInfo.getCid()) && ids.size() > 0) {
                    uerInfo.setCid(String.valueOf(l.get(0).getCid()));
                    uerInfo.setClass_name(l.get(0).getClass_name());
                    uerInfo.setState(String.valueOf(l.get(0).getStatus()));
                    UserUtil.saveUserInfo(getActivity(), uerInfo);
                    PreferencesUtils.saveUserInfo(uerInfo);
                    if ("1".equals(uerInfo.getState())) {
                        if (adapter.checkID == radio_left.getId()) {
                            getHomeWork();
                        } else {
                            getHomeWorkOver();
                        }
                    } else {
                        if (adapter != null) {
                            adapter.clear();
                            adapter.notifyDataSetChanged();
                        }
                        if (srlSthHome.isRefreshing())
                            srlSthHome.setRefreshing(false);
                    }
                } else {
                    if (ids.size() > 0) {
                        for (int i = 0; i < l.size(); i++) {
                            if (l.get(i).getCid() == Integer.valueOf(uerInfo.getCid())) {
                                uerInfo.setClass_name(l.get(i).getClass_name());
                                uerInfo.setState(l.get(i).getStatus() + "");
                                UserUtil.saveUserInfo(getActivity(), uerInfo);
                                break;
                            }
                        }
                    }
                    if ("1".equals(uerInfo.getState())) {
                        if (adapter.checkID == radio_left.getId()) {
                            getHomeWork();
                        } else {
                            getHomeWorkOver();
                        }
                    } else {
                        if (adapter != null) {
                            adapter.clear();
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

                for (UserClassListBean.DataBean.ClassListBean listBean : l) {
                    listBean.setLast_job_time(DateUtils.parseTime(listBean.getLast_job_time()));
                }
                String cid = PreferencesUtils.getUserInfo().getCid();
                UserClassListBean.DataBean.ClassListBean curClass = null;
                if (!TextUtils.isEmpty(cid)) {
                    for (UserClassListBean.DataBean.ClassListBean listBean : l) {
                        if (cid.equals(String.valueOf(listBean.getCid()))) {
                            curClass = listBean;
                            break;
                        }
                    }
                }
                net_class_list = l;
                saveCurClass(curClass);
            } else {
                adapter.clear();
//                UIUtils.showToastSafe("您还未加入班级");
                uerInfo = PreferencesUtils.getUserInfo();
                uerInfo.setCid(null);
                uerInfo.setClass_name(null);
                uerInfo.setState(null);
                PreferencesUtils.saveUserInfo(uerInfo);
                adapter = new StudentWorkAdapter(getActivity(), null, this, this, uerInfo);
                listView.setAdapter(adapter);
            }

            if (srlSthHome.isRefreshing())
                srlSthHome.setRefreshing(false);
        }

    }

    /**
     * 查询数据库班级列表. 如果有班级列表, 则更新当前班级.
     *
     * @param curClass
     */
    private void saveCurClass(final UserClassListBean.DataBean.ClassListBean curClass) {
        if (curClass == null) {
            //如果当前班级在数据库中没有, 直接检查小红点
            checkRedPoint();
            return;
        }
        DBUtils.getDbUtils().getClassListInfo(new DBUtils.inter() {
            @Override
            public void getList(List<?> list, boolean bb, int sign) {
                if (sign == 77 && list != null && list.size() > 0) {
                    //说明有数据库有数据.更新当前数据.
                    ClassTabelBean classTabel = new ClassTabelBean();
                    classTabel.setTaskLatestTime(Long.valueOf(curClass.getLast_job_time()));
                    classTabel.setClassState(curClass.getStatus());
                    classTabel.setClassName(curClass.getClass_name());
                    classTabel.setClassID(curClass.getCid());
                    classTabel.setUserID(Long.valueOf(PreferencesUtils.getUid()));
                    DBUtils.getDbUtils().leaveHomeClassTabel(Long.valueOf(PreferencesUtils.getUid()), Long.valueOf(PreferencesUtils.getUserInfo().getCid()), classTabel, new DBUtils.DBSaveOk() {
                        @Override
                        public void saveOK() {
                            checkRedPoint();
                        }
                    });
                } else {
                    //如果本地没有. 则把数据存入本地.
                    saveClassList(new DBUtils.DBSaveOk() {
                        @Override
                        public void saveOK() {
                            if (adapter != null) {
                                UIUtils.runInMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.setShowRedPoint(false);
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                    });
                }
            }

            @Override
            public void isHaveNum(int state, List<?> list, boolean b, TaskListBean taskListBean, int sign) {

            }
        }, 77, Long.valueOf(PreferencesUtils.getUid()));
    }

    public StudentWorkAdapter getAdapter() {
        return adapter;
    }

    private UserClassListBean.DataBean.ClassListBean getCurrClass(List<UserClassListBean.DataBean.ClassListBean> class_list) {
        for (UserClassListBean.DataBean.ClassListBean bean : class_list) {
            if (bean.getCid() == Integer.valueOf(UserUtil.getUerInfo(getActivity()).getCid())) {
                return bean;
            }
        }
        return null;
    }

    private boolean savebean() {
        SharedPreferences sp = App.getApplication().getSharedPreferences(ConstantValue.SPCONFIG, Context.MODE_PRIVATE);
        String cid = sp.getString("Cid", null);
        String cName = sp.getString("cName", null);
        String cState = sp.getString("cState", null);
        String cUid = sp.getString("cUid", null);

        if (!TextUtils.isEmpty(cid) && !TextUtils.isEmpty(cName) && !TextUtils.isEmpty(cState) && PreferencesUtils.getUid().equals(cUid)) {
            return true;
        }
        return false;
    }

    //检查班级状态
    private void checkClassState(boolean contain_flag, UserClassListBean bean) {
        String cid, state, class_name;
        if (bean != null) {
            List<UserClassListBean.DataBean.ClassListBean> class_list = bean.getData().getClass_list();
            if (savebean()) {
                SharedPreferences sp = getActivity().getSharedPreferences(ConstantValue.SPCONFIG, Context.MODE_PRIVATE);
                String Cid = sp.getString("Cid", null);
                String cName = sp.getString("cName", null);
                String cState = sp.getString("cState", null);
                uerInfo.setCid(Cid);
                uerInfo.setClass_name(cName);
                uerInfo.setState(cState);
                PreferencesUtils.saveUserInfo(uerInfo);
                sp.edit().putString("Cid", null).putString("cName", null).putString("cState", null).putString("cUid", null).commit();
            }

            if (class_list.size() > 0) {
                cid = uerInfo.getCid();
                state = uerInfo.getState();
                class_name = uerInfo.getClass_name();
                if (cid != null && state != null && class_name != null) {
                    for (int i = 0; i < class_list.size(); i++) {
                        UserClassListBean.DataBean.ClassListBean classListBean = class_list.get(i);
                        if (cid.equals(String.valueOf(classListBean.getCid()))) {
                            contain_flag = true;
                            state = String.valueOf(classListBean.getStatus());
                            if ("1".equals(state)) {
                                uerInfo.setClass_name(classListBean.getClass_name());
                                uerInfo.setState(String.valueOf(classListBean.getStatus()));
                            }
                        }
                    }
                } else {
                    uerInfo.setCid(class_list.get(0).getCid() + "");
                    String classState = String.valueOf(class_list.get(0).getStatus());
                    uerInfo.setClass_name(class_list.get(0).getClass_name());
                    uerInfo.setState(classState);
                    PreferencesUtils.saveUserInfo(uerInfo);
                    if ("1".equals(classState)) {
                        uerInfo.setClass_name(class_list.get(0).getClass_name());
                        if (adapter.checkID == radio_left.getId()) {
                            getHomeWork();
                        } else {
                            getHomeWorkOver();
                        }

                    } else {
                        uerInfo.setClass_name(class_list.get(0).getClass_name() + "(待审核)");
                        if (adapter != null) {
                            adapter.clear();
                            adapter.notifyDataSetChanged();
                        }
                    }
                    return;
                }
                if (!contain_flag) {//返回的班级列表不包含当前显示的班级
                    if (!savebean()) {
                        UserClassListBean.DataBean.ClassListBean classListBean = bean.getData().getClass_list().get(0);
                        state = String.valueOf(classListBean.getStatus());
                        if ("1".equals(state)) {
                            uerInfo.setClass_name(classListBean.getClass_name());
                        } else {
                            uerInfo.setClass_name(classListBean.getClass_name() + "(待审核)");
                        }
                        uerInfo.setCid(String.valueOf(classListBean.getCid()));
                        uerInfo.setState(String.valueOf(classListBean.getStatus()));
                    } else {
                        state = UserUtil.getUerInfo(getActivity()).getState();
                    }
                }
                isJoinClass(String.valueOf(state));
            } else {
                //未加入班级
                uerInfo.setCid(null);
                uerInfo.setState(null);
                uerInfo.setClass_name(null);
                PreferencesUtils.saveUserInfo(uerInfo);
                SharedPreferences sp = getActivity().getSharedPreferences(ConstantValue.SPCONFIG, Context.MODE_PRIVATE);
                sp.edit().putString("Cid", "").putString("cState", "").putString("cName", "").putString("cUid", PreferencesUtils.getUid()).commit();
                if (view_info != null)
                    view_info.setVisibility(View.GONE);
                if (TextUtils.isEmpty(UserUtil.getUerInfo(getActivity()).getAvatar())) {
                    noclass_icon.setImageResource(R.mipmap.default_avatar);
                } else {
                    ImageLoader.getInstance().displayImage(UserUtil.getUerInfo(getActivity()).getAvatar(), noclass_icon);
                }
            }
        }
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);

        hideMyprogressDialog();
        if (requestCode == GetClassMiddle.GET_CLASS) {
            //本地没存  没网络
            if (uerInfo.getCid() != null && uerInfo.getClass_name() != null && uerInfo.getState() != null) {
                UIUtils.showToastSafe("保持网络通畅呦~");
            } else {
                // 设置默认头像
                if (TextUtils.isEmpty(UserUtil.getUerInfo(getActivity()).getAvatar())) {
                    noclass_icon.setImageResource(R.mipmap.default_avatar);
                } else {
                    ImageLoader.getInstance().displayImage(UserUtil.getUerInfo(getActivity()).getAvatar(), noclass_icon);
                }
                UIUtils.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        }
        if (srlSthHome.isRefreshing())
            srlSthHome.setRefreshing(false);

    }

    //失去焦点往数据库里写的数据

    public HashMap<String, List<TaskListBean>> writeDbData = new HashMap<>();

    public HashMap<String, List<TaskListBean>> getWriteDbData() {

        return writeDbData;
    }

    public int todo_count = 0;

    /**
     * 网络请求成功时
     *
     * @param obj
     */
    @Override
    public void successFromMid(final Object... obj) {
        super.successFromMid(obj);
        hideMyprogressDialog();
        int request = (int) obj[1];
        if (request == StudentWorkMiddle.HOMEWORK) {
            //每次刷新重新获取班级列表（小红点显示）
            //  new GetClassMiddle(this,getActivity()).getClassList(new UserClassListBean());
            if (srlSthHome.isRefreshing())
                srlSthHome.setRefreshing(false);
            dataFromNet = (HomeWorkBean) obj[0];
            //刷新top小红点
            //updataTopClassRed();
            //设置adapter数据
            List<HomeWorkBean.DataBean.ListBean> netData = dataFromNet.getData().getList();
            todo_count = dataFromNet.getData().getTodo_count();
            setAdapterData(netData);
            // checkRedPoint();
        } else if (request == StudentWorkMiddle.HOMEWORK_OVER) {
            HomeWorkBean over = (HomeWorkBean) obj[0];
            if (state == FRESH) {
                data_over = over;
                String str_total = over.getData().getTotal() + "";
                int int_total = 0;
                if (str_total == null || str_total.equals("")) {
                    int_total = 0;
                } else {
                    int_total = Integer.parseInt(str_total);
                }
                //int_total 除以10 取商  +   int_total 除以10  取余(当余数不为0时表示还有一部分数据不足一页  但是当做一页请求)
                total = int_total / 10 + (int_total % 10 != 0 ? 1 : 0);
                checkRedPoint();
            } else {
                try {
                    data_over.getData().getList().addAll(over.getData().getList());
                    List<HomeWorkBean.DataBean.ListBean> homeWorkEntityList = data_over.getData().getList();
                    Collections.sort(homeWorkEntityList);
                    data_over.getData().setList(homeWorkEntityList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            adapter.setDataOver(data_over.getData().getList());
            if (srlSthHome.isRefreshing())
                srlSthHome.setRefreshing(false);
        }
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                String classname = uerInfo.getClass_name();
                adapter.getTv_classname().setText(classname);
            }
        });

    }

    final int GET_CLASS_INFO = 88;

    @Override
    public void onPause() {
        super.onPause();
        System.gc();//通知GC释放内存.
     /*   if (net_class_list != null && net_class_list.size() > 0) {

            saveClassList(new DBUtils.DBSaveOk() {
                @Override
                public void saveOK() {
                    //do nothing
                }
            });

        }*/
    }

    private void saveClassList(DBUtils.DBSaveOk ok) {
        List<ClassTabelBean> db_list = new ArrayList<>();
        //废代码, 前面判断了
        if (net_class_list == null || net_class_list.size() == 0) {
            new GetClassMiddle(StudentWorkFragment.this, getActivity()).getClassList(new UserClassListBean(), GetClassMiddle.GET_CLASS);
            return;
        }
        for (int i = 0; i < net_class_list.size(); i++) {
            ClassTabelBean bean = new ClassTabelBean();
            UserClassListBean.DataBean.ClassListBean classListBean = net_class_list.get(i);
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

        DBUtils.getDbUtils().leaveHomeClassTabel(Long.parseLong(uerInfo.getUid()), db_list, ok);
//            for (ClassTabelBean ctb : db_list) {
//                if (!TextUtils.isEmpty(uerInfo.getCid()) && Long.valueOf(uerInfo.getCid()) == ctb.getClassID()) {
//                    ctb.setTaskLatestTime(Long.valueOf(DateUtils.parseTime(classListBean.getLast_job_time())));
//                }
//            }
//            DBUtils.getDbUtils().leaveHomeClassTabel(Long.parseLong(uerInfo.getUid()), db_list,ok);
//            // 通知完数据库后也要取消作业首页最上面班级名字后面的那个小红点。。
//            checkRedPoint();
//        }
    }


    private void saveAllData(DBUtils.DBSaveOk ok) {
        WorkCatalogUtils.refreshUserInfo(uerInfo, getActivity());
        //一块存数据库
        HashMap<String, List<TaskListBean>> writeDbData = getWriteDbData();
        Iterator iter = writeDbData.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String cid = (String) entry.getKey();
            List<TaskListBean> list = (List<TaskListBean>) entry.getValue();
            long timeMillis = System.currentTimeMillis();
//            DBUtils.getDbUtils().leaveHomeTaskList(Long.parseLong(UserUtil.getUid()), Long.parseLong(cid), timeMillis, list, ok);
        }
        // writeDbData.clear();
    }

    /**
     * 网络请求失败时
     *
     * @param obj
     */
    @Override
    public void failedFrom(Object... obj) {
        super.failedFrom(obj);
        hideMyprogressDialog();
        int requestcode = (int) obj[0];
        if (requestcode == StudentWorkMiddle.HOMEWORK) {
//            StudentWorkUtils.getInstance().getDBData(StudentWorkUtils.GET_DB_DATA_FAILE, this);
            if (!TextUtils.isEmpty(uerInfo.getCid()))
                DBUtils.getDbUtils().getHomeListInfo(this, FAIL_FROM_NET, Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()));
        } else if (requestcode == StudentWorkMiddle.HOMEWORK_OVER) {
            data_over = new HomeWorkBean();
            if (current_page_over == 1) {
                adapter.setDataOver(null);
            }
            if (srlSthHome.isRefreshing())
                srlSthHome.setRefreshing(false);
        }
    }

    @Override
    public void onCheckChanged(int id) {
        group.check(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refresh:
                getHomeWork();
                break;
            case R.id.image:
                Intent in = new Intent(getActivity(), FeedBackActivity.class);
                startActivityForResult(in, 9);
                break;
            case R.id.up_down:
                //防止多次点击
                up_down.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        up_down.setClickable(true);
                    }
                }, 2000);
                adapter.setImgAndDialog();

                break;
            case R.id.tv_noclass_join:
            case R.id.tv_join_class:
                startActivityForResult(new Intent(getActivity(), AddClassActivity.class), AddClassMiddle.ADD_CLASS);
                break;
            case R.id.tv_cancle_class:
                new CancleClassMiddle(this, getActivity(), uerInfo).setClassCancle(new CancleClassBean());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AddClassMiddle.ADD_CLASS && resultCode == getActivity().RESULT_OK) {
            adapter.getList().clear();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //第一次启动时  查询本地数据   请求网络数据
    private boolean first_start = true;
    //第一次请求已过期作业的标示   以后在刷新或者上拉加载中请求数据
    private boolean first_over = true;
    /*
        每次进入这个界面都要加载数据
        因为小红点和作业百分比需要刷新数据
     */
    //TODO   考虑放到ActivityResult中

    @Override
    public void onResume() {
        super.onResume();
        showMyprogressDialog();
        new GetClassMiddle(StudentWorkFragment.this, getActivity()).getClassList(new UserClassListBean(), GetClassMiddle.GET_CLASS_ASY);
        //-----------------------------------------
      /*  if (TextUtils.isEmpty(uerInfo.getState()) || TextUtils.isEmpty(uerInfo.getCid()) || TextUtils.isEmpty(uerInfo.getClass_name())) {
            showMyprogressDialog();
            new GetClassMiddle(this, getActivity()).getClassList(new UserClassListBean());
        } else {
            if ("1".equals(uerInfo.getState())) {
                showMyprogressDialog();
//                getDataFromNet();
                new GetClassMiddle(this, getActivity()).getClassList(new UserClassListBean());
            } else {
                new GetClassMiddle(StudentWorkFragment.this, getActivity()).getClassList(new UserClassListBean(), GetClassMiddle.GET_CLASS_ASY);
            }
        }*/
    }

    private void isJoinClass(String state) {
        if (!"1".equals(state)) {
            if (TextUtils.isEmpty(uerInfo.getAvatar())) {
                waitclass_icon.setImageResource(R.mipmap.default_avatar);
            } else {
                ImageLoader.getInstance().displayImage(uerInfo.getAvatar(), waitclass_icon);
            }
            tv_classname.setText(uerInfo.getClass_name());
        }
        // getDataFromNet();
    }

    public void getDataFromNet() {
        //TODO   修改更改头像的方式
        //每次都重新加载头像
        if (isImageInit) {
            getBitmap(title_layout_height * 2, new ImageloadCallback() {
                @Override
                public void getBitmap(final Bitmap bitmap) {
                    head_vator.setImageBitmap(bitmap);
                }
            });
        }
        if (uerInfo != null) {
            uid = PreferencesUtils.getUid();
            if (group != null) {
                if (group.getCheckedRadioButtonId() == radio_left.getId()) {
                    getHomeWork();
                } else {
                    getHomeWorkOver();
                }
            }
            first_start = false;
        }
        onScrollStateChanged(listView, SCROLL_STATE_IDLE);
    }

    private void getBitmap(final int height, final ImageloadCallback callback) {
        if (getActivity() == null) {
            return;
        }
        try {
            new Thread() {
                @Override
                public void run() {
                    final Bitmap bitmap = ImageLoader.getInstance().loadImageSync(UserUtil.getUerInfo(getActivity()).getAvatar());
                    UIUtils.runInMainThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.getBitmap(BitmapUtil.getRoundedCornerBitmap(BitmapUtil.zoomImage(getActivity(), bitmap, height, height)));
                        }
                    });
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    @Override
    public void getList(List<?> list, boolean bb, int sign) {
        //TODO 进入查询数据库为null 时候 需要处理
        if (list == null || list.size() == 0)
            return;
        if (sign == FAIL_FROM_NET) {
            ArrayList<TaskListBean> data_from_db = (ArrayList<TaskListBean>) list;
            final ArrayList<HomeWorkBean.DataBean.ListBean> db_data = new ArrayList<>();
            for (int i = 0; i < data_from_db.size(); i++) {
                HomeWorkBean.DataBean.ListBean bean = new HomeWorkBean.DataBean.ListBean();
                TaskListBean listBean = data_from_db.get(i);
//                            bean.setJob_id(listBean.getTaskID());
                bean.setStu_job_id(Integer.parseInt(String.valueOf(listBean.getTaskID())));
                bean.setJob_status(1);
                bean.setStu_job_status(Integer.parseInt(String.valueOf(listBean.getTaskState())));
//                            bean.setIs_done(listBean);
                bean.setPercent(String.valueOf(listBean.getTaskDegree()));
                bean.setTitle(listBean.getTaskName());
                bean.setCdate(listBean.getArrangementTime());
                bean.setDeadline(String.valueOf(listBean.getTaskEndTime()));
                bean.setCday(listBean.getWeek());
                if (listBean.isEvaluate())
                    bean.setEvaluated(1);
                else {
                    bean.setEvaluated(0);
                }
                bean.setCid(Integer.parseInt(String.valueOf(listBean.getClassID())));
                db_data.add(bean);
            }
            UIUtils.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    adapter.setDataDoing(db_data);
                }
            });
        } else if (sign == GET_LIST_DB) {

        }
    }

    @Override
    public void isHaveNum(int state, List<?> list, boolean b, final TaskListBean taskListBean, int sign) {

    }


    public void setAdapterData(final List<HomeWorkBean.DataBean.ListBean> netData) {
        String uid = PreferencesUtils.getUid();
        String cid = PreferencesUtils.getUserInfo().getCid();
        if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(cid)){
            UIUtils.showToastSafe("登录信息过期,请重新登录");
            UIUtils.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            },500);
            return;
        }

        if (netData == null)
            return;
        List<TaskListBean> taskListList = new ArrayList<>();

        for (HomeWorkBean.DataBean.ListBean listBean : netData) {
            TaskListBean taskListBean = new TaskListBean();
            taskListBean.setUserId(Long.valueOf(PreferencesUtils.getUid()));  //1
            taskListBean.setClassID(Long.valueOf(PreferencesUtils.getUserInfo().getCid())); //2
            taskListBean.setTaskID(listBean.getStu_job_id()); //3
            taskListBean.setArrangementTime(listBean.getCdate()); //4
            taskListBean.setTaskSubmitTime(""); //5
            taskListBean.setTaskEndTime(Long.parseLong(listBean.getDeadline()));//6
            taskListBean.setTaskTime(0L);//7
            taskListBean.setTaskTimeCurrent(0L);//8
            taskListBean.setHaveTask(false);//9
            taskListBean.setTaskLastSubmitTime("");//10
            taskListBean.setTaskName(listBean.getTitle());//11
            taskListBean.setTaskNum(0L);//12
            taskListBean.setTaskState(listBean.getStu_job_status());//13
            taskListBean.setTaskDegree(listBean.getJob_status());//14   作业撤销
            taskListBean.setEvaluate(listBean.getEvaluated() == 1);//15
            taskListBean.setWeek(listBean.getCday());//16
            taskListBean.setTaskRequirement("");//17
            taskListBean.setTaskPercentage(TextUtils.isEmpty(listBean.getPercent()) ? 0L : Long.valueOf(listBean.getPercent()));//18
            taskListBean.setTaskExplain("");//19
            long pe = TextUtils.isEmpty(listBean.getPercent()) ? 0L : Long.valueOf(listBean.getPercent());
            taskListBean.setShowRedpoint(pe == 0L);//20
            taskListBean.setJob_id(listBean.getJob_id());//21
            taskListList.add(taskListBean);
        }

        DBUtils.getDbUtils().addTaskList(new DBUtils.inter() {
            @Override
            public void getList(List<?> list, boolean bb, int sign) {
                if (list != null) {
                    ArrayList<TaskListBean> data_from_db = (ArrayList<TaskListBean>) list;
                    final ArrayList<HomeWorkBean.DataBean.ListBean> db_data = new ArrayList<>();
                    for (int i = data_from_db.size() - 1; i >= 0; i--) {
                        TaskListBean listBean = data_from_db.get(i);
                        long taskEndTime = listBean.getTaskEndTime();
                        if (taskEndTime > System.currentTimeMillis()) {
                            continue;
                        }
                        HomeWorkBean.DataBean.ListBean bean = new HomeWorkBean.DataBean.ListBean();

                        bean.setStu_job_id(Integer.parseInt(String.valueOf(listBean.getTaskID())));
                        bean.setJob_status(1);
                        bean.setJob_id((int) listBean.getJob_id());
                        bean.setStu_job_status(Integer.parseInt(String.valueOf(listBean.getTaskState())));
                        bean.setPercent(String.valueOf(listBean.getTaskPercentage()));
                        bean.setTitle(listBean.getTaskName());
                        bean.setCdate(listBean.getArrangementTime());
                        bean.setDeadline(String.valueOf(listBean.getTaskEndTime()));
                        bean.setCday(listBean.getWeek());
                        bean.setShowRedPoint(listBean.isShowRedpoint());
                        if (listBean.isEvaluate())
                            bean.setEvaluated(1);
                        else {
                            bean.setEvaluated(0);
                        }
                        bean.setCid(Integer.parseInt(String.valueOf(listBean.getClassID())));
                        db_data.add(bean);
                    }
                    Collections.sort(db_data);
                    // final ArrayList<HomeWorkBean.DataBean.ListBean> db_data_sort = sortData(db_data);


                    UIUtils.runInMainThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setDataDoing(db_data);
                        }
                    });
                }
            }

            @Override
            public void isHaveNum(int state, List<?> list, boolean b, TaskListBean taskListBean, int sign) {

            }
        }, 66, Long.valueOf(uid), Long.valueOf(cid), taskListList);
    }

    //按照布置作业时间排序   cdate
    private ArrayList<HomeWorkBean.DataBean.ListBean> sortData(ArrayList<HomeWorkBean.DataBean.ListBean> db_data) {
        for (int i = 0; i < db_data.size(); i++) {
            for (int j = i + 1; j < db_data.size(); j++) {
                if (Long.valueOf(DateUtils.parseTime(db_data.get(i).getCdate())) < Long.valueOf(DateUtils.parseTime(db_data.get(j).getCdate()))) {
                    HomeWorkBean.DataBean.ListBean temp = db_data.get(i);
                    db_data.set(i, db_data.get(j));
                    db_data.set(j, temp);
                }
            }
        }
        return db_data;
    }

    private static long lastClickTime;

    public synchronized boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
