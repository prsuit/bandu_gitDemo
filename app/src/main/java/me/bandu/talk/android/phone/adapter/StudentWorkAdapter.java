package me.bandu.talk.android.phone.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.UIUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.AddClassActivity;
import me.bandu.talk.android.phone.activity.MyselfPullulateActivity;
import me.bandu.talk.android.phone.activity.StudentHomeActivity;
import me.bandu.talk.android.phone.bean.CancleClassBean;
import me.bandu.talk.android.phone.bean.HomeWorkBean;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.bean.UserClassListBean;
import me.bandu.talk.android.phone.fragment.StudentWorkFragment;
import me.bandu.talk.android.phone.impl.FragmentChangeLisener;
import me.bandu.talk.android.phone.middle.CancleClassMiddle;
import me.bandu.talk.android.phone.middle.GetClassMiddle;
import me.bandu.talk.android.phone.utils.ClassPop2Utils;
import me.bandu.talk.android.phone.utils.DateUtils;
import me.bandu.talk.android.phone.utils.PreferencesUtils;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.utils.WorkCatalogUtils;
import me.bandu.talk.android.phone.view.CircleImageView;
import me.bandu.talk.android.phone.view.Yuan_two;

/**
 * Created by GaoNan on 2015/11/16.
 */
public class StudentWorkAdapter extends BaseAdapter implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    public static int checkID = -1;
    private static final int RED_POINT = 27;
    private int leftID;
    private int rightID;
    private int count = 0;

    private String todo_count;
    private boolean isNoWork = false;
    private boolean needShowTime = true;//区别过期作业和未过期作业
    private FragmentChangeLisener lisener;
    private List<HomeWorkBean.DataBean.ListBean> list = new ArrayList<>();
    private List<HomeWorkBean.DataBean.ListBean> data_list_doing = new ArrayList<>();
    private List<HomeWorkBean.DataBean.ListBean> data_list_over = new ArrayList<>();
    private View v2;
    private StudentWorkFragment fragment;

    private ViewHolderInfo info;
    private ViewHolderList itemlist;
    private ImageView up_down;
    private RelativeLayout ll_name_red;
    private TextView tv_classname;
    private LoginBean.DataEntity uerInfo;
    public StudentWorkAdapter adapter;

    public List<HomeWorkBean.DataBean.ListBean> getList() {
        return list;
    }

    public void setList(List<HomeWorkBean.DataBean.ListBean> list) {
        this.list = list;
    }


    public void setCheckID(int checkID) {
        this.checkID = checkID;
    }

    Context context;

    public StudentWorkAdapter(Context context, List<HomeWorkBean.DataBean.ListBean> list, FragmentChangeLisener lisener, StudentWorkFragment fragment, LoginBean.DataEntity uerInfo) {
        this.context = context;
        this.lisener = lisener;
        this.fragment = fragment;
        if (list != null) {
            this.list = list;
            this.data_list_doing = this.list;
        }
        this.uerInfo = uerInfo;
    }

    private boolean showRedPoint = false;

    public boolean isShowRedPoint() {
        return showRedPoint;
    }

    public void setShowRedPoint(boolean showRedPoint) {
        this.showRedPoint = showRedPoint;
    }

    public boolean isNowork() {
        return isNoWork;
    }

//    public boolean getListview_isShowRedPoint(int position) {
//        if (null != this.list) {
//            if (null == this.listview_isShowRedPoint) {
//                initListview_isShowRedPoint(this.list.size());
//            }
//            return listview_isShowRedPoint.get(position);
//        }
//        return true;
//    }

    @Override
    public int getCount() {
        if (list == null || list.size() == 0) {
            if (null !=uerInfo){
                String state = uerInfo.getState();
//            if ("1".equals(state)) {
//                count = 3;// 0 头像 1 选项卡 2 无作业布局
//            } else {
                count = 2;//0头像 1选项卡
//            }
                isNoWork = true;
            }

        } else {
            count = list.size() + 2;// 0 头像 1 选项卡
            isNoWork = false;
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        if (position > 1)
            return position;
        return list.get(position - 2);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    //    private boolean isFirst = true;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (PreferencesUtils.getUserInfo() != null)
            uerInfo = PreferencesUtils.getUserInfo();
        if (type == 0) {//头像
            final ViewHolderImage holderImage;
            convertView = LayoutInflater.from(context).inflate(R.layout.item_image, null);
            tv_classname = (TextView) convertView.findViewById(R.id.classname);
            v2 = convertView;
            holderImage = new ViewHolderImage(convertView);
            holderImage.todo.setText(getUploadCount() + "");
            holderImage.myself_pullulate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转个人成长页面
                    Intent intent = new Intent(context, MyselfPullulateActivity.class);
                    intent.putExtra("info", "m");
                    context.startActivity(intent);
                }
            });
//            up_down.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    setImgAndDialog();
//                }
//            });
            ll_name_red.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isFastClick())
                        setImgAndDialog();
                }
            });
            tv_classname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isFastClick())
                        setImgAndDialog();
                }
            });

            if ("1".equals(uerInfo.getState())) {
                holderImage.redTv.setVisibility(View.GONE);
                holderImage.classname.setVisibility(View.VISIBLE);
                tv_classname.setText(uerInfo.getClass_name());
                holderImage.tv_info_end.setVisibility(View.VISIBLE);
                holderImage.myself_pullulate.setVisibility(View.VISIBLE);
                holderImage.todo.setVisibility(View.VISIBLE);
                holderImage.tv_cancle_class.setVisibility(View.GONE);
                holderImage.tv_info_start.setText("当前有");
                up_down.setVisibility(View.VISIBLE);
            } else if ("2".equals(uerInfo.getState())) {
                holderImage.redTv.setVisibility(View.GONE);
                holderImage.classname.setVisibility(View.VISIBLE);
//                tv_classname.setText(uerInfo.getClass_name().substring(0, uerInfo.getClass_name().length() - 5));
                tv_classname.setText(uerInfo.getClass_name());
                holderImage.tv_info_end.setVisibility(View.GONE);
                holderImage.myself_pullulate.setVisibility(View.GONE);
                holderImage.todo.setVisibility(View.GONE);
                holderImage.tv_cancle_class.setVisibility(View.VISIBLE);
                holderImage.tv_info_start.setText("正在等待老师审核");
                up_down.setVisibility(View.VISIBLE);
                clear(); // 如果是待审核的班级就把当前的所有集合清空。。
            } else if (TextUtils.isEmpty(uerInfo.getState())) {
                //未加入班级
                holderImage.classname.setVisibility(View.GONE);
                holderImage.redTv.setVisibility(View.GONE);
                up_down.setVisibility(View.GONE);
                holderImage.myself_pullulate.setVisibility(View.GONE);
                holderImage.tv_info_start.setText("你还没有申请班级");
                holderImage.ll_workstate.setGravity(Gravity.CENTER_HORIZONTAL);
                holderImage.text.setGravity(Gravity.CENTER_HORIZONTAL);
                holderImage.todo.setVisibility(View.GONE);
                holderImage.tv_info_end.setVisibility(View.GONE);
            }

            holderImage.redTv.setVisibility(showRedPoint ? View.VISIBLE : View.GONE);
         /*   if(uerInfo.getCid() != null){
                UIUtils.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DBUtils.getDbUtils().getClassListInfo(new DBUtils.inter() {
                            int visibility = View.GONE;
                            @Override
                            public void getList(List<?> list, boolean bb, int sign) {
                                if(list !=null)
                                    LogUtils.e("******查询数据库长度 : "+ list.size());
                                else
                                    LogUtils.e("查询数据库是个空的********************");
                                if (RED_POINT == sign && list != null && list.size() > 0) {
                                    ArrayList<ClassTabelBean> db_data = (ArrayList<ClassTabelBean>) list;
                                    for (ClassTabelBean bean : db_data) {
                                        if(bean.isShowReddot() && bean.getClassID() != Long.valueOf(uerInfo.getCid())){
                                            visibility = View.VISIBLE;
                                            break;
                                        }
                                    }
                                }
                                UIUtils.runInMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        holderImage.redTv.setVisibility(visibility);
                                    }
                                });
                            }

                            @Override
                            public void isHaveNum(List<?> list, boolean b, boolean bb, int sign) {

                            }
                        }, RED_POINT, Long.valueOf(uerInfo.getUid()));
                    }
                },500);
            }*/
//            if("2".equals(uerInfo.getState())){
//              tv_classname.setText(uerInfo.getClass_name().substring(0,uerInfo.getClass_name().length()-5));
//            }
//            tv_classname.setText(uerInfo.getClass_name());
//            if (isFirst) {
//                isFirst = false;
            if (TextUtils.isEmpty(UserUtil.getUerInfo(context).getAvatar())) {
                holderImage.image.setImageResource(R.mipmap.default_avatar);
            } else {
                ImageLoader.getInstance().displayImage(PreferencesUtils.getUserInfo().getAvatar(), holderImage.image ,builder.build());
            }



//            }


//              holderImage.classnum.setText(CacheUtils.getInstance().getStringCache(context,"classnum"));
            holderImage.tv_join_class.setOnClickListener(this);
            holderImage.tv_cancle_class.setOnClickListener(this);
            return convertView;
        }
        if (type == 1) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_info, null);
                info = new ViewHolderInfo();
                info.radio_left = (RadioButton) convertView.findViewById(R.id.radio_left);
                info.radio_right = (RadioButton) convertView.findViewById(R.id.radio_right);
                info.group = (RadioGroup) convertView.findViewById(R.id.textinfo);
                info.ll_rg = (LinearLayout) convertView.findViewById(R.id.ll_rg);
                leftID = info.radio_left.getId();
                rightID = info.radio_right.getId();
                info.group.setOnCheckedChangeListener(this);
                convertView.setTag(info);
            } else {
                info = (ViewHolderInfo) convertView.getTag();
            }
            //根据state = null 代表还没加入班级 时设置不可见
            if (TextUtils.isEmpty(uerInfo.getState()) || "2".equals(uerInfo.getState())) {
                info.group.setVisibility(View.GONE);
            }

            if (!"1".equals(uerInfo.getState())) {
                return convertView;
            } else if ("1".equals(uerInfo.getState())) {
                // 此时说明已经有班级，这时设置成可见的
                info.group.setVisibility(View.VISIBLE);
            }
            if (checkID == -1) {
                checkID = info.radio_left.getId();
            }
            info.group.check(checkID);
            return convertView;
        }
        if (type == 2) {
            String state = uerInfo.getState();

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_text, null);
                itemlist = new ViewHolderList(convertView);
                itemlist.llHasWork.setVisibility(View.VISIBLE);
                itemlist.llNoWork.setVisibility(View.GONE);
                convertView.setTag(itemlist);
            } else {
                itemlist = (ViewHolderList) convertView.getTag();
            }
            if (!"1".equals(state)) {
                return convertView;
            } else if ("1".equals(uerInfo.getState())) {
                // 此时说明已经有班级，这时设置成可见的
                info.group.setVisibility(View.VISIBLE);
            }
            itemlist.llHasWork.setVisibility(View.VISIBLE);
            itemlist.llNoWork.setVisibility(View.GONE);
            HomeWorkBean.DataBean.ListBean entity = list.get(position - 2);
            if (entity.isEmpty()) {
                itemlist.homework_container.setVisibility(View.INVISIBLE);
                return convertView;
            } else {
                itemlist.homework_container.setVisibility(View.VISIBLE);
            }
            if (entity.getEvaluated() == 0) {
                itemlist.tv_evaluated.setVisibility(View.INVISIBLE);
                itemlist.point_view.setVisibility(View.INVISIBLE);
            } else {
                itemlist.tv_evaluated.setVisibility(View.VISIBLE);
                itemlist.point_view.setVisibility(View.VISIBLE);
            }
            if (needShowTime) {//未过期作业
                //自定义倒计时控件
                long d = Long.parseLong(entity.getDeadline());
                itemlist.time.start(d * 1000 - System.currentTimeMillis());
                if ((d * 1000 - System.currentTimeMillis()) > 24 * 60 * 60 * 1000) {
                    //如果大于一天则显示天
                    itemlist.time.customTimeShow(true, true, true, true, false);
                } else {
                    //小于一天不显示天
                    itemlist.time.customTimeShow(false, true, true, true, false);
                }
                itemlist.time_layout.setVisibility(View.VISIBLE);

                if (Integer.parseInt(entity.getPercent()) == 100) {
                    itemlist.tv_percent.setAttr(100, Yuan_two.FINISH);
                } else {
                    itemlist.tv_percent.setAttr(Float.parseFloat(entity.getPercent()), Yuan_two.NORMAL);
                }
                //学生作业状态 0未完成 1已完成 2已检查
                if (entity.getStu_job_status() == 0) {
                    //小红点的显示listview_isShowRedPoint
                    //String str = CacheUtils.getInstance().getStringCache(context, UserUtil.getUerInfo(context).getUid() + "" + entity.getStu_job_id());
                    boolean showRedPoint = list.get(position - 2).isShowRedPoint();
                    if (showRedPoint) {
                        itemlist.point_view.setVisibility(View.VISIBLE);
                    } else {
                        itemlist.point_view.setVisibility(View.INVISIBLE);
                    }
                } else {
                    itemlist.point_view.setVisibility(View.INVISIBLE);
                }
            } else {//过期作业
                //作业状态 1正常 0已撤销
                if (entity.getJob_status() == 0) {
                    itemlist.tv_percent.setAttr(100, Yuan_two.REVOKE);
                } else {
                    //学生作业状态 0未完成 1已完成 2已检查
                    //TODO   确认一下2已检查的作业状态
                    if (entity.getStu_job_status() == 0) {
                        itemlist.tv_percent.setAttr(Float.parseFloat(entity.getPercent()), Yuan_two.OVER);
                    } else {
                        itemlist.tv_percent.setAttr(100, Yuan_two.FINISH);
                    }
                }
                itemlist.time_layout.setVisibility(View.INVISIBLE);
                itemlist.point_view.setVisibility(View.INVISIBLE);
            }
            itemlist.tv_date.setText(WorkCatalogUtils.reSetTime(entity.getCdate()));
            itemlist.tv_week.setText(entity.getCday());
            itemlist.tv_title.setText(entity.getTitle());
            return convertView;
        }
        if (type == 3 && isNoWork) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_text, null);
            itemlist = new ViewHolderList(convertView);
            convertView.setTag(itemlist);
            itemlist.llHasWork.setVisibility(View.GONE);
            itemlist.llNoWork.setVisibility(View.VISIBLE);
            return convertView;
        }
        convertView = LayoutInflater.from(context).inflate(R.layout.item_text, null);
        return convertView;
    }

    //服务器返回404，此时让imageloader加载默认的头像
    DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder().showImageOnFail(R.mipmap.default_avatar);


    private static long lastClickTime;

    public synchronized boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    //图片  and  dialog
    public void setImgAndDialog() {
        ((StudentHomeActivity) context).showMyprogressDialog();
        //获取班级列表
        new GetClassMiddle(new BaseActivityIF() {
            @Override
            public void successFromMid(Object... obj) {

            }

            @Override
            public void failedFrom(Object... obj) {
            }

            @Override
            public void onSuccess(Object result, int requestCode) {
                ((StudentHomeActivity) context).hideMyprogressDialog();
                setImgAnim(up_down);
                UserClassListBean bean = (UserClassListBean) result;
                List<UserClassListBean.DataBean.ClassListBean> class_list = bean.getData().getClass_list();
                for (UserClassListBean.DataBean.ClassListBean listBean : class_list) {
                    listBean.setLast_job_time(DateUtils.parseTime(listBean.getLast_job_time()));
                }
                fragment.net_class_list = class_list;

//                setImgAnim(ll_name_red);
               /* ClassPopUtils.getInstance().showClassPopou(context, uerInfo, up_down, tv_classname.getText().toString(), fragment, v2.findViewById(R.id.text), (UserClassListBean) result, new ClassPopUtils.OnClassPopDismissListener() {
                    @Override
                    public void onCommit(String choseclass, String chosecid, String chosestate) {
                        String cid = uerInfo.getCid();
                        if (choseclass != null && chosecid != null && chosestate != null) {
                            if (!chosecid.equals(cid) && choseclass != null && chosecid != null && chosestate != null) {
                                uerInfo.setClass_name(choseclass);
                                uerInfo.setCid(chosecid);
                                uerInfo.setState(chosestate);
                            }
                        }
                        WorkCatalogUtils.refreshUserInfo(uerInfo, context);
                        fragment.getDataFromNet();
                    }
                }, new ClassPickerView.onTextChanged() {
                    @Override
                    public void onTextChanged(String text) {
                        LogUtils.i("当文本改变" + text);
                    }
                });
                ClassPopUtils.getInstance().ssss(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        if (up_down != null) {
                            RotateAnimation animation = new RotateAnimation(180f, 360f, Animation.RELATIVE_TO_SELF,
                                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            animation.setDuration(100);//设置动画持续时间
                            animation.setFillAfter(true);
                            up_down.startAnimation(animation);
                            GlobalParams.imgflag = false;
                        }
//                        if (ll_name_red != null) {
//                            RotateAnimation animation = new RotateAnimation(180f, 360f, Animation.RELATIVE_TO_SELF,
//                                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                            animation.setDuration(100);//设置动画持续时间
//                            animation.setFillAfter(true);
//                            ll_name_red.startAnimation(animation);
//                            GlobalParams.imgflag = false;
//                        }
                    }
                });*/

                ClassPop2Utils.getInstance().showClassPopou(context, uerInfo, up_down, tv_classname.getText().toString(), fragment, v2.findViewById(R.id.text), (UserClassListBean) result, new ClassPop2Utils.OnClassPopDismissListener() {
                    @Override
                    public void onCommit(String choseclass, String chosecid, String chosestate) {
                        String cid = uerInfo.getCid();
                        if (choseclass != null && chosecid != null && chosestate != null) {
                            if (!chosecid.equals(cid)) {
                                uerInfo.setClass_name(choseclass);
                                uerInfo.setCid(chosecid);
                                uerInfo.setState(chosestate);
                                UserUtil.saveUserInfo(context, uerInfo);
                                PreferencesUtils.saveUserInfo(uerInfo);

                            }
                        }
                        WorkCatalogUtils.refreshUserInfo(uerInfo, context);
                        fragment.getDataFromNet();
                    }
                });
            }

            @Override
            public void onFailed(int requestCode) {
                ((StudentHomeActivity) context).hideMyprogressDialog();
            }
        }, context).getClassList(new UserClassListBean());
    }

    private void setImgAnim(ImageView up_down) {
        GlobalParams.imgflag = true;
        RotateAnimation animation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(100);//设置动画持续时间
        animation.setFillAfter(true);
        up_down.startAnimation(animation);
    }

//    private void setImgAnim(RelativeLayout ll_name_red) {
//        GlobalParams.imgflag = true;
//        RotateAnimation animation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        animation.setDuration(100);//设置动画持续时间
//        animation.setFillAfter(true);
//        ll_name_red.startAnimation(animation);
//    }

    public String getUploadCount() {
        if (fragment.todo_count > 0) {
            todo_count = String.valueOf(fragment.todo_count);
        } else {
            todo_count = "0";
        }
        return todo_count;
    }

    public void clear() {
        if (null != data_list_doing && null != data_list_over && null != list) {
            data_list_doing.clear();
            data_list_over.clear();
            list.clear();
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        checkID = checkedId;
        lisener.onCheckChanged(checkedId);
    }

    //加入空白item
    public void addItem(int num) {
        for (int i = 0; i < num; i++) {
            HomeWorkBean.DataBean.ListBean homeWorkEntity = new HomeWorkBean.DataBean.ListBean();
            homeWorkEntity.setEmpty(true);
            list.add(homeWorkEntity);
        }
        count = list.size();
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_join_class://加入班级
                context.startActivity(new Intent(context, AddClassActivity.class));
                break;
            case R.id.tv_cancle_class://取消班级
                // 取消班级给出提示
                showDialog();
                break;
        }
    }


    public void showDialog() {
        View layout = LayoutInflater.from(context).inflate(R.layout.cancle_dialog, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        final AlertDialog dialog1 = dialog.create();
        dialog1.setView(layout);
        dialog1.setCanceledOnTouchOutside(false);
        layout.findViewById(R.id.make_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                new CancleClassMiddle(fragment, context, uerInfo).setClassCancle(new CancleClassBean());

            }
        });
        layout.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }


    public class ViewHolderImage {

        public ViewHolderImage(View view) {
            tv_join_class = (TextView) view.findViewById(R.id.tv_join_class);
            image = (CircleImageView) view.findViewById(R.id.image);
            redTv = view.findViewById(R.id.redTv);

            todo = (TextView) view.findViewById(R.id.todo);
            myself_pullulate = (ImageButton) view.findViewById(R.id.myself_pullulate);
            tv_cancle_class = (TextView) view.findViewById(R.id.tv_cancle_class);
            up_down = (ImageView) view.findViewById(R.id.up_down);
            ll_name_red = (RelativeLayout) view.findViewById(R.id.ll_name_red);
            tv_info_end = (TextView) view.findViewById(R.id.tv_info_end);
            classname = (TextView) view.findViewById(R.id.classname);
            tv_info_start = (TextView) view.findViewById(R.id.tv_info_start);
            ll_workstate = (LinearLayout) view.findViewById(R.id.ll_workstate);
            text = (LinearLayout) view.findViewById(R.id.text);

        }

        LinearLayout ll_workstate, text;
        View redTv;
        ImageButton myself_pullulate;
        CircleImageView image;
        TextView todo, tv_join_class, tv_cancle_class, tv_info_end, tv_info_start, classname;

    }

    public class ViewHolderInfo {
        RadioGroup group;
        RadioButton radio_left;
        RadioButton radio_right;
        LinearLayout ll_rg;
    }

    public class ViewHolderList {
        public ViewHolderList(View view) {
            tv_date = (TextView) view.findViewById(R.id.date);
            tv_evaluated = (TextView) view.findViewById(R.id.tv_evaluated);
            tv_week = (TextView) view.findViewById(R.id.week);
            tv_title = (TextView) view.findViewById(R.id.title);
            time = (CountdownView) view.findViewById(R.id.countdowntime);
            tv_percent = (Yuan_two) view.findViewById(R.id.percent);
            time_layout = (LinearLayout) view.findViewById(R.id.time_linear);
            point_view = view.findViewById(R.id.point_view);
            homework_container = (LinearLayout) view.findViewById(R.id.homework_container);
            llHasWork = (LinearLayout) view.findViewById(R.id.llHasWork);
            llNoWork = (LinearLayout) view.findViewById(R.id.llNoWork);
        }

        TextView tv_date, tv_week, tv_title;
        TextView tv_evaluated;
        CountdownView time;
        LinearLayout time_layout, homework_container, ll_item, llHasWork, llNoWork;
        Yuan_two tv_percent;
        View point_view;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if (!isNoWork) {//有作业的情况
            if (position == 0) {
                return 0;//头像的布局
            }
            if (position == 1) {
                return 1;//选项卡的布局
            }
            return 2;//作业的布局
        } else {//无作业的情况
            if (position == 0) {
                return 0;//头像的布局
            }
            if (position == 1) {
                return 1;//选项卡的布局
            }
            return 3;//没作业的的布局
        }
    }

    //获取需要完成的作业
    public int getTodoCount() {
        if (data_list_doing == null || data_list_doing.size() == 0) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < data_list_doing.size(); i++) {
            if (data_list_doing.get(i).isEmpty()) {
                continue;
            }
            if (Integer.parseInt(data_list_doing.get(i).getPercent()) != 100) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void notifyDataSetChanged() {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                StudentWorkAdapter.super.notifyDataSetChanged();
            }
        });
    }

    public void setDataDoing(List<HomeWorkBean.DataBean.ListBean> l) {
        this.list = l;
        this.data_list_doing = l;
        needShowTime = true;
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });


    }

    public void setDataDoing(List<HomeWorkBean.DataBean.ListBean> l, String todo_count) {
        this.todo_count = todo_count;
        this.list = l;
        this.data_list_doing = l;
        needShowTime = true;
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    public void setDataOver(List<HomeWorkBean.DataBean.ListBean> l) {
        this.list = l;
        this.data_list_over = l;
        needShowTime = false;
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    public void setDataDoing() {
        this.list = this.data_list_doing;
        needShowTime = true;
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    public void setDataOver() {
        this.list = this.data_list_over;
        needShowTime = false;
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    public HomeWorkBean.DataBean.ListBean getData(int position) {
        return list.get(position - 2);
    }

    public boolean getStatus() {
        return needShowTime;
    }

    /**
     * 作业是否过期
     * @param position 位置
     * @return true 过期  false 未过期
     */
    public boolean getStatus(int position) {
        if(position > 1) {
            HomeWorkBean.DataBean.ListBean listBean = list.get(position - 2);
            int job_status = listBean.getJob_status();
            return job_status == 1;
        }
        return false;
    }


    public boolean getIsDone(int position) {//界面传递数据

        if (position > 1)
            return list.get(position - 2).getIs_done() == 1;
        else return false;
      /*  boolean f = true;
        try {
            if (list.get(position - 3).getStu_job_status() == 0) {
                f = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;*/
    }

    public TextView getTv_classname() {
        return tv_classname;
    }
}
