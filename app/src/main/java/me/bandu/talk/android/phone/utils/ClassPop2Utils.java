package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.bean.UserClassListBean;
import me.bandu.talk.android.phone.fragment.StudentWorkFragment;
import me.bandu.talk.android.phone.greendao.bean.ClassTabelBean;
import me.bandu.talk.android.phone.greendao.bean.TaskListBean;
import me.bandu.talk.android.phone.greendao.utils.DBUtils;
import me.bandu.talk.android.phone.view.ClassPickerView;

/**
 * Created by GaoNan on 2015/11/20.
 * Popupwindow 时间选择器  条件选择器
 */
public class ClassPop2Utils implements ClassPickerView.onClassSelectListener {
    private static final int CLASS_PICKER_CODE = 103;

    private int selectedPosition = -1;// 选中的位置

    private static ClassPop2Utils pu;
    private PopupWindow ppw;
    //    private ClassPickerView picker_classname;
    private ListView lvPick;
    private String className, Cid, state;
    private int index = 0;

    public static ClassPop2Utils getInstance() {
        if (pu == null) {
            pu = new ClassPop2Utils();
        }
        return pu;
    }

    /**
     * 时间选择器
     */
    private OnClassPopDismissListener classlistener;

    private List<ClassTabelBean> bean_db;
    private String time ;

    @Override
    public void onSelect(String classname, String cid, String state) {
        this.className = classname;
        this.Cid = cid;
        this.state = state;
    }

    public interface OnClassPopDismissListener {
        void onCommit(String classname, String cid, String state);
    }

    private Context context;

    public void showClassPopou(Context context, LoginBean.DataEntity uerInfo, ImageView img, String className, StudentWorkFragment fragment, View container, UserClassListBean result, OnClassPopDismissListener listener) {
        this.className = null;
        this.Cid = null;
        this.state = null;
        this.classlistener = listener;
        this.context = context;
        UserClassListBean bean = result;
        View view = LayoutInflater.from(context).inflate(R.layout.popclass, null);
        initClassData(view, uerInfo, bean, img, className, fragment, listener);
        ppw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ppw.setAnimationStyle(R.style.popwin_anim_style);
        ColorDrawable dw = new ColorDrawable(0xB2B2B2);
        //设置SelectPicPopupWindow弹出窗体的背景
        ppw.setBackgroundDrawable(dw);
//        ppw.setBackgroundDrawable(null);
//        ppw.setOutsideTouchable(true);
        ppw.showAtLocation(container, Gravity.BOTTOM, 0, 0);
    }

    private void initClassData(View view, final LoginBean.DataEntity uerInfo, final UserClassListBean bean, final ImageView img, final String className, final StudentWorkFragment fragment, final OnClassPopDismissListener listener) {
        lvPick = (ListView) view.findViewById(R.id.lvPick);
        View dismiss = view.findViewById(R.id.dismiss);
        final RotateAnimation animation = new RotateAnimation(180f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(100);//设置动画持续时间
        animation.setFillAfter(true);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UIUtils.showToastSafe("1111");
                img.startAnimation(animation);
                GlobalParams.imgflag = false;
                if (ppw.isShowing())
                    ppw.dismiss();
            }
        });
        View commit = view.findViewById(R.id.class_commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img.startAnimation(animation);
                if (ppw.isShowing()) {
                    GlobalParams.imgflag = false;
                    ppw.dismiss();
                }

                if(TextUtils.isEmpty(Cid))
                    return;

             /*   if ("1".equals(state)) {
                    fragment.getDataFromNet();
                }*/
                if (classlistener != null)
                    classlistener.onCommit(ClassPop2Utils.this.className, Cid, state);
                ClassTabelBean saveCtb = null;
                //TODO 当点击确定的时候通知数据库更新小红点
                for(ClassTabelBean ctb: bean_db){
                    if(!TextUtils.isEmpty(Cid) && Long.valueOf(Cid)==ctb.getClassID()){
//                        LogUtils.i("time === "+ time);
//                        LogUtils.i("time->long === "+ DateUtils.parseTime(time));
//                        LogUtils.i("time->value === "+ Long.valueOf(DateUtils.parseTime(time)));
                        ctb.setTaskLatestTime(Long.valueOf(DateUtils.parseTime(time)));
                        saveCtb = ctb;
                    }
                }
                final ClassTabelBean finalSaveCtb = saveCtb;
                DBUtils.getDbUtils().leaveHomeClassTabel(Long.valueOf(UserUtil.getUid()), bean_db, new DBUtils.DBSaveOk() {
                    @Override
                    public void saveOK() {
                        // 通知完数据库后也要取消作业首页最上面班级名字后面的那个小红点。。
                       // fragment.checkRedPoint();
                        DBUtils.getDbUtils().leaveHomeClassTabel(Long.valueOf(UserUtil.getUid()), Long.valueOf(Cid), finalSaveCtb, new DBUtils.DBSaveOk() {
                            @Override
                            public void saveOK() {
                                fragment.checkRedPoint();
                            }
                        });

                    }
                });

            }
        });
        View cancel = view.findViewById(R.id.class_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UIUtils.showToastSafe("3333");
                img.startAnimation(animation);
                GlobalParams.imgflag = false;
                if (ppw.isShowing())
                    ppw.dismiss();
            }
        });
        final List<UserClassListBean.DataBean.ClassListBean> classbean = new ArrayList<>();
        final List<UserClassListBean.DataBean.ClassListBean> class_list = bean.getData().getClass_list();
        for (int i = 0; i < class_list.size(); i++) {
            UserClassListBean.DataBean.ClassListBean classListBean = class_list.get(i);
            if (classListBean.getStatus() == 2) {
                String class_name = classListBean.getClass_name();
                classListBean.setClass_name(class_name + "(待审核)");
            }
            classbean.add(classListBean);
        }
        long uid = Long.parseLong(UserUtil.getUid());


        DBUtils.getDbUtils().getClassListInfo(new DBUtils.inter() {
            @Override
            public void getList(List<?> list, boolean bb, int sign) {
                bean_db = (List<ClassTabelBean>) list;
                final PickLvAdapter adapter = new PickLvAdapter(fragment.getActivity(), class_list, bean_db);
                UIUtils.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        lvPick.setAdapter(adapter);
                        lvPick.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                ClassPop2Utils.this.className = class_list.get(position).getClass_name();
                                Cid = class_list.get(position).getCid() + "";
                                state = class_list.get(position).getStatus() + "";

                                //把网络获取的最后提交作业的时间赋值给数据库中的TaskLatestTime
//                                try{
                                    time = class_list.get(position).getLast_job_time();
//                                }catch (NumberFormatException e){
//                                    e.printStackTrace();
//                                }

                                adapter.setSelectedPosition(position);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }

            @Override
            public void isHaveNum(int state, List<?> list, boolean b, final TaskListBean taskListBean, int sign) {

            }

        }, CLASS_PICKER_CODE, uid);


    }

    private PopupWindow.OnDismissListener onDismissListener;

    public void ssss(PopupWindow.OnDismissListener onDismissListener) {
        ppw.setOnDismissListener(onDismissListener);
    }

    public class PickLvAdapter extends BaseAdapter {
        private Context context;
        private List<UserClassListBean.DataBean.ClassListBean> class_list;
        private List<ClassTabelBean> bean;

        public PickLvAdapter(Context context, List<UserClassListBean.DataBean.ClassListBean> class_list, List<ClassTabelBean> bean) {
            this.context = context;
            this.class_list = class_list;
            this.bean = bean;
        }

        @Override
        public int getCount() {
            return class_list.size();
        }

        @Override
        public Object getItem(int position) {
            return class_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return class_list.get(position).getCid();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.pick_listview_item, null);
            //给item设置选择器
            convertView.setBackgroundResource(R.drawable.item_selector);
            TextView tvClassName = (TextView) convertView.findViewById(R.id.tvClassName);
            RelativeLayout rl_pick = (RelativeLayout)convertView.findViewById(R.id.rl_pick);
            View redTv = convertView.findViewById(R.id.redTv);
            tvClassName.setText(class_list.get(position).getClass_name());

            redTv.setVisibility(View.GONE);
            for (ClassTabelBean ctb : bean) {
                if (class_list.get(position).getCid()== ctb.getClassID()) {
                    redTv.setVisibility(!DateUtils.parseTime(class_list.get(position).getLast_job_time()).equals(ctb.getTaskLatestTime()+"") ? View.VISIBLE : View.GONE);
                    break;
                }
            }
            if(selectedPosition == position){
                //设置当前位置的item背景和文字颜色
                rl_pick.setBackgroundColor(UIUtils.getColor(R.color.item_color));
                tvClassName.setTextColor(context.getResources().getColor(R.color.black));
            } else {
                tvClassName.setTextColor(context.getResources().getColor(R.color.text_gray));
            }

            return convertView;
        }


        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

    }

}
