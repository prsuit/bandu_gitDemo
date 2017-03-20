package me.bandu.talk.android.phone.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.AboutUsActivity;
import me.bandu.talk.android.phone.activity.BindParentActivity;
import me.bandu.talk.android.phone.activity.DownloadManagerActivity;
import me.bandu.talk.android.phone.activity.FeedBackActivity;
import me.bandu.talk.android.phone.activity.LoginActivity;
import me.bandu.talk.android.phone.activity.ModifyPasswordActivity;
import me.bandu.talk.android.phone.activity.NewWordActivity;
import me.bandu.talk.android.phone.activity.PersonalDataActivity;
import me.bandu.talk.android.phone.activity.TeacherHomeActivity;
import me.bandu.talk.android.phone.fragment.factory.HomeFragmentFactory;
import me.bandu.talk.android.phone.utils.FileUtil;
import me.bandu.talk.android.phone.utils.ImageLoaderOption;
import me.bandu.talk.android.phone.utils.PreferencesUtils;
import me.bandu.talk.android.phone.utils.SystemApplation;
import me.bandu.talk.android.phone.utils.UpdateBandu;

/**
 * 创建者：Mcablylx
 * 时间：2015/11/23 14:57
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TeacherInfoFragment extends Fragment {

    @Bind(R.id.head)
    ImageView headImg;
    @Bind(R.id.name)
    TextView nameTv;
    @Bind(R.id.school)
    TextView schoolTv;
    @Bind(R.id.sys_rl)
    RelativeLayout sysRl;
    @Bind(R.id.bind_rl)
    RelativeLayout bindRl;
    @Bind(R.id.download_rl)
    RelativeLayout downloadRl;
    @Bind(R.id.down_tv)
    TextView downTv;
    @Bind(R.id.modify_pass_tv)
    TextView modifyPassTv;
    @Bind(R.id.bind_parent_tv)
    TextView bindParentTv;
    @Bind(R.id.modify_pass_rl)
    RelativeLayout modifyPassRl;
    @Bind(R.id.update_app_layout)
    RelativeLayout update;
    @Bind(R.id.exit)
    Button exitBtn;
    @Bind(R.id.red_tv)
    TextView redTv;
    @Bind(R.id.newWord_tv)
    TextView newWord_tv;
    //视图
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //   if(view == null){
        view = inflater.inflate(R.layout.teacher_home_info_fragment, container, false);
        ButterKnife.bind(this, view);
        // }else
        //   ViewUtils.removeParent(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (PreferencesUtils.getUserInfo() != null) {
            if (PreferencesUtils.getUserInfo().getRole() == 1) {//教师
                exitBtn.setVisibility(View.VISIBLE);
                bindRl.setVisibility(View.GONE);
                bindRl.setClickable(true);
                modifyPassRl.setClickable(true);
                sysRl.setVisibility(View.GONE);
                if (PreferencesUtils.getUserInfo() != null && !TextUtils.isEmpty(PreferencesUtils.getUserInfo().getName()))
                    nameTv.setText(PreferencesUtils.getUserInfo().getName());
                schoolTv.setVisibility(View.VISIBLE);
                schoolTv.setText(PreferencesUtils.getUserInfo().getSchool() == null ? "" : PreferencesUtils.getUserInfo().getSchool());
                refreshSchool();
                //downloadRl.setVisibility(View.GONE);
            } else if (PreferencesUtils.getUserInfo().getRole() == 2) {//学生
                exitBtn.setVisibility(View.VISIBLE);
                bindRl.setVisibility(View.VISIBLE);
                bindRl.setClickable(true);
                sysRl.setVisibility(View.VISIBLE);
                modifyPassRl.setClickable(true);
                downloadRl.setClickable(true);
                if (PreferencesUtils.getUserInfo() != null && !TextUtils.isEmpty(PreferencesUtils.getUserInfo().getName()))
                    nameTv.setText(PreferencesUtils.getUserInfo().getName());
                downloadRl.setVisibility(View.VISIBLE);
            } else {//试用进来
                exitBtn.setVisibility(View.GONE);
                bindRl.setVisibility(View.VISIBLE);
                downloadRl.setVisibility(View.VISIBLE);
                downloadRl.setClickable(false);
                bindRl.setClickable(false);
                modifyPassRl.setClickable(false);
                sysRl.setClickable(false);
                nameTv.setText("登录/注册");
                newWord_tv.setTextColor(getResources().getColor(R.color.gray));
                downTv.setTextColor(getResources().getColor(R.color.gray));
                modifyPassTv.setTextColor(getResources().getColor(R.color.gray));
                bindParentTv.setTextColor(getResources().getColor(R.color.gray));
            }
        }
        if (PreferencesUtils.getUserInfo() != null)
            ImageLoader.getInstance().displayImage(PreferencesUtils.getUserInfo().getAvatar(), headImg, ImageLoaderOption.getOptions());
//        if (PreferencesUtils.getUserInfo() != null && !TextUtils.isEmpty(PreferencesUtils.getUserInfo().getName()))
//            nameTv.setText(PreferencesUtils.getUserInfo().getName());
//        else {
//            nameTv.setText("登录/注册");
//        }
        //schoolTv.setText(GlobalParams.userInfo.getSchool());
    }

    public void refreshSchool() {
        if (PreferencesUtils.getUserInfo() != null && PreferencesUtils.getUserInfo().getSchool() != null) {
            if (PreferencesUtils.getUserInfo().getSchool().equals("")) {
                redTv.setVisibility(View.VISIBLE);
            } else {
                redTv.setVisibility(View.GONE);
                ((TeacherHomeActivity) getActivity()).getMsgNum();
            }
        } else {
            redTv.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.head_rl, R.id.sys_rl, R.id.modify_pass_rl, R.id.feedback_rl, R.id.us_rl,
            R.id.download_rl, R.id.bind_rl, R.id.exit, R.id.update_app_layout})
    void click(View v) {
        switch (v.getId()) {
            case R.id.head_rl:
                //进入个人资料页面
                if (null == GlobalParams.userInfo) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                } else if (GlobalParams.userInfo != null && GlobalParams.userInfo.getUid() == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                } else {
                    startActivityForResult(new Intent(getActivity(), PersonalDataActivity.class), 9);
                }
                break;
            case R.id.sys_rl:
                //进入生词本页面
                startActivity(new Intent(getActivity(), NewWordActivity.class));
                break;
            case R.id.update_app_layout://更新
                update();
                break;
            case R.id.modify_pass_rl:
                //进入修改密码页面
                startActivity(new Intent(getActivity(), ModifyPasswordActivity.class));
                break;
            case R.id.feedback_rl:
                //进入反馈页面
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
            case R.id.us_rl:
                //进入关于我们页面
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.download_rl:
                //进入下载管理页面
                startActivity(new Intent(getActivity(), DownloadManagerActivity.class));
                break;
            case R.id.bind_rl:
                //进入绑定家长页面
                startActivity(new Intent(getActivity(), BindParentActivity.class));
                break;
            case R.id.exit:
                showDialog();
                break;
        }
    }

    private void update() {
        try {
            UpdateBandu updateBandu = UpdateBandu.getUpdateBandu();
//            if (!updateBandu.isShowOff()) {//显示过就不在重复显示了
                updateBandu.setShowOff(true);
                Point point = new Point();
                Activity activity = getActivity();
                activity.getWindowManager().getDefaultDisplay().getSize(point);
                int versionCode = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionCode;
                updateBandu.setWidthAndHeight(point.x, point.y);
                updateBandu.update(activity, versionCode,1);
//            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showDialog() {
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.exit_dialog, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        final AlertDialog dialog1 = dialog.create();
        dialog1.setView(layout);
        dialog1.setCanceledOnTouchOutside(false);
        layout.findViewById(R.id.make_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                // HomeFragmentFactory.destroyFragment(R.id.llMyClass);
                SharedPreferences sp = getActivity().getSharedPreferences(ConstantValue.SPCONFIG, Context.MODE_PRIVATE);
                if (PreferencesUtils.getUserInfo().getRole() == 2)
                    sp.edit().putString("Cid", PreferencesUtils.getUserInfo().getCid()).putString("cName", PreferencesUtils.getUserInfo().getClass_name()).putString("cState", PreferencesUtils.getUserInfo().getState()).putString("cUid", PreferencesUtils.getUid()).commit();
                PreferencesUtils.clean();
                GlobalParams.userInfo = null;
                FileUtil.deleteFile(new File(getActivity().getDir("user", getActivity().MODE_PRIVATE).getAbsolutePath() + "/user", "user.data"));
                SystemApplation.getInstance().exit(true);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                HomeFragmentFactory.cleanFragment();
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

    @Override
    public void onResume() {
        super.onResume();
        if (PreferencesUtils.getUserInfo() != null) {
            ImageLoader.getInstance().displayImage(PreferencesUtils.getUserInfo().getAvatar(), headImg, ImageLoaderOption.getOptions());
            if (PreferencesUtils.getUserInfo().getRole() == 2 || PreferencesUtils.getUserInfo().getRole() == 1)
                nameTv.setText(PreferencesUtils.getUserInfo().getName());
            schoolTv.setText(PreferencesUtils.getUserInfo().getSchool() == null ? "" : PreferencesUtils.getUserInfo().getSchool());
            if (PreferencesUtils.getUserInfo().getRole() == 1) {
                refreshSchool();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (9 == requestCode) {
            EventBus.getDefault().post("msg");
            if (PreferencesUtils.getUserInfo() != null)
                if (PreferencesUtils.getUserInfo().getRole() == 1) {
                    refreshSchool();
                }
        }
    }
}
