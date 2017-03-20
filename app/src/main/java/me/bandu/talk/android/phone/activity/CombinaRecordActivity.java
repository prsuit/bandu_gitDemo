package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.DFHT.net.NetworkUtil;
import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.App;
import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.CombinaRecordChangeBean;
import me.bandu.talk.android.phone.bean.GetUserBean;
import me.bandu.talk.android.phone.bean.MyRecordBean;
import me.bandu.talk.android.phone.middle.GetUserRecordMiddle;
import me.bandu.talk.android.phone.middle.PlayPlayUserMiddle;
import me.bandu.talk.android.phone.middle.PlayRandomUserMiddle;
import me.bandu.talk.android.phone.recevice.FinishRecevice;
import me.bandu.talk.android.phone.utils.ShareUtils;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.utils.Utils;
import me.bandu.talk.android.phone.view.CircleImageView;
import me.bandu.talk.android.phone.view.PlayMp3View;
import me.bandu.talk.android.phone.wxapi.BaseUiListener;

/**
 * Created by fanyu on 2016/6/1.
 * 组合录音
 */
public class CombinaRecordActivity extends BaseAppCompatActivity implements View.OnClickListener,IWeiboHandler.Response {
    private static int LEFT = 0;
    private static int RIGHT = 1;
    @Bind(R.id.title_tv)
    TextView title_tv;
    @Bind(R.id.civ_head_user)
    CircleImageView civ_head;//自己头像
    @Bind(R.id.civ_head_user_small)
    CircleImageView civ_head_small;//自己小头像
    @Bind(R.id.ll_qzone)
    LinearLayout ll_qzone;
    @Bind(R.id.ll_qq)
    LinearLayout ll_qq;
    @Bind(R.id.ll_tweet)
    LinearLayout ll_tweet;
    @Bind(R.id.ll_wechart)
    LinearLayout ll_wechart;
    @Bind(R.id.ll_wechart_friend)
    LinearLayout ll_wechart_friend;
    @Bind(R.id.ll_share)
    LinearLayout ll_share;
    @Bind(R.id.civ_head_other)
    CircleImageView civ_head_other;
    @Bind(R.id.civ_head_other1_small)
    CircleImageView civ_other1;
    @Bind(R.id.civ_head_other2_small)
    CircleImageView civ_other2;
    @Bind(R.id.civ_head_other3_small)
    CircleImageView civ_other3;
    @Bind(R.id.civ_othetr4_small)
    CircleImageView civ_other4;

    @Bind({R.id.civ_head_user_small, R.id.civ_head_other1_small, R.id.civ_head_other2_small,
            R.id.civ_head_other3_small, R.id.civ_othetr4_small})
    CircleImageView[] civs_small;

    @Bind(R.id.tv_refresh)
    TextView tv_refresh;
    @Bind(R.id.button_back)
    Button btn_back;
    @Bind(R.id.title_modify_record)
    TextView tv_modify;

    @Bind(R.id.tv_username)
    TextView tv_username;

    @Bind(R.id.tv_othername)
    TextView tv_othername0;

    @Bind(R.id.tv_username1)
    TextView tv_username1;
    @Bind(R.id.tv_share_info)
    TextView tv_share_info;

    @Bind(R.id.tv_other1)
    TextView tv_othername1;
    @Bind(R.id.tv_other2)
    TextView tv_othername2;
    @Bind(R.id.tv_other3)
    TextView tv_othername3;
    @Bind(R.id.tv_other4)
    TextView tv_othername4;
    @Bind(R.id.btn_switch)
    Button btn_switch;
    @Bind(R.id.pmv)
    PlayMp3View pmv;
    @Bind(R.id.btn_qq_share)
    Button btn_qq_share;
    @Bind(R.id.btn_qqzone_share)
    Button btn_qqzone_share;
    @Bind(R.id.btn_tweet)
    Button btn_tweet;
    @Bind(R.id.btn_wechart_share)
    Button btn_wechart_share;
    @Bind(R.id.btn_wechart_friend_share)
    Button btn_wechart_friend_share;
    @Bind(R.id.iv_head_user_small)
    ImageView iv_head_user_small;
    @Bind(R.id.iv_head_other1_small)
    ImageView iv_head_other1_small;
    @Bind(R.id.iv_head_other2_small)
    ImageView iv_head_other2_small;
    @Bind(R.id.iv_head_other3_small)
    ImageView iv_head_other3_small;
    @Bind(R.id.iv_head_other4_small)
    ImageView iv_head_other4_small;
    @Bind({R.id.iv_head_user_small, R.id.iv_head_other1_small, R.id.iv_head_other2_small,
            R.id.iv_head_other3_small, R.id.iv_head_other4_small})
    ImageView[] ivs_small;


    //上个界面穿过来的 我的录音bean
    private MyRecordBean myRecordBean;
    private MyRecordBean banRecordBean;
    private MyRecordBean otherRecordBean;

    private String otherHead;  //需要传他人的头像地址
    private int state = -1;  // 我的资料当前在左还是在右
    private boolean isFirst = true; // 是否 第一次 点击 换一换

    @Bind({R.id.civ_head_other1_small, R.id.civ_head_other2_small, R.id.civ_head_other3_small,
            R.id.civ_othetr4_small})
    CircleImageView[] civs;  //其他人四个小头像的数组

    @Bind({R.id.tv_other1, R.id.tv_other2, R.id.tv_other3, R.id.tv_other4})
    TextView[] tvs; //其他人名字的数组

    private CircleImageView switchCiv; // 当前切换的头像view
    private TextView switchTv; // 当前切换的名字
    private GetUserBean.DataBean.UserListBean myBean; // 自己的Bean
    private GetUserBean.DataBean.UserListBean switchBean; // 需要切换的Bean
    private int pos_sure = -1; // 对勾的角标位置
    private GetUserBean.DataBean.UserListBean[] beans;

    private GetUserBean bean;
    private List<GetUserBean.DataBean.UserListBean> users;

    private Tencent mTencent;
    private BaseUiListener listener;
    private IWeiboShareAPI mWeiboShareAPI;
    private int size = 0;
    private boolean qq = false,wechart = false,tweent = false;

    private long quiz_id;
    private String[] mUids;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_combination_record;
    }

    @Override
    protected void toStart() {
            initView();
            getData();

    }

    public void initView() {
        btn_qq_share.setOnClickListener(this);
        btn_tweet.setOnClickListener(this);
        btn_qqzone_share.setOnClickListener(this);
        btn_wechart_friend_share.setOnClickListener(this);
        btn_wechart_share.setOnClickListener(this);

        if (Utils.isAppInstalled(this, "com.tencent.mobileqq")){
            //腾讯
            mTencent = Tencent.createInstance(ConstantValue.QQ_APP_ID, this.getApplicationContext());
        }else {
            qq = true;
            ll_qq.setVisibility(View.GONE);
            ll_qzone.setVisibility(View.GONE);
            btn_qq_share.setVisibility(View.GONE);
            btn_qqzone_share.setVisibility(View.GONE);
        }
        if (Utils.isAppInstalled(this, "com.sina.weibo")){
            //微博
            mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(getApplicationContext(), ConstantValue.WB_APP_ID);
            mWeiboShareAPI.registerApp();    // 将应用注册到微博客户端
        }else {
            tweent = true;
            btn_tweet.setVisibility(View.GONE);
            ll_tweet.setVisibility(View.GONE);
        }
        if (Utils.isAppInstalled(this, "com.tencent.mm")){

        }else {
            wechart = true;
            btn_wechart_share.setVisibility(View.GONE);
            btn_wechart_friend_share.setVisibility(View.GONE);
            ll_wechart.setVisibility(View.GONE);
            ll_wechart_friend.setVisibility(View.GONE);
        }
        if(qq && wechart && tweent) {
            tv_share_info.setVisibility(View.GONE);
            ll_share.setVisibility(View.GONE);
        }
        listener = new BaseUiListener();




        title_tv.setText("组合录音");
        if (UserUtil.isLogin()) {
            int pos = App.ComRecData.getInt("pos", LEFT);
            state = pos;
            if (pos == LEFT) {
                //设置自己的信息
                ImageLoader.getInstance().displayImage(StringUtil.getShowText
                        (GlobalParams.userInfo.getAvatar()), civ_head,builder.build());
                tv_username.setText(GlobalParams.userInfo.getName());

                switchCiv = civ_head_other;
                switchTv = tv_othername0;

            } else {
                //设置自己的信息
                ImageLoader.getInstance().displayImage(StringUtil.getShowText
                        (GlobalParams.userInfo.getAvatar()), civ_head_other,builder.build());
                tv_othername0.setText(GlobalParams.userInfo.getName());
                switchCiv = civ_head;
                switchTv = tv_username;
            }

            civ_head_small.setImageResource(R.mipmap.bandu_icon2);
            tv_username1.setText("伴小读");

            myBean = new GetUserBean.DataBean.UserListBean();
            myBean.setUid(GlobalParams.userInfo.getUid());
            myBean.setAvatar(GlobalParams.userInfo.getAvatar());
            myBean.setName(GlobalParams.userInfo.getName());

            String o_uid = App.ComRecData.getString("o_uid", "");
            String o_avatar = App.ComRecData.getString("o_avatar", "");
            String o_name = App.ComRecData.getString("o_name", "");


            //相当于初始化默认的右边他人的资料显示信息
            switchBean = new GetUserBean.DataBean.UserListBean();
            switchBean.setUid(o_uid);
            switchBean.setAvatar(o_avatar);
            switchBean.setName(o_name);

            if ("".equals(switchBean.getName())) {
                switchCiv.setImageResource(R.mipmap.default_avatar);
            } else if ("伴小读".equals(switchBean.getName())) {
                switchCiv.setImageResource(R.mipmap.bandu_icon2);
            } else {
                ImageLoader.getInstance().displayImage(StringUtil.getShowText(switchBean.getAvatar()), switchCiv,builder.build());
            }
            switchTv.setText(switchBean.getName());

            pos_sure = App.ComRecData.getInt("pos_sure", -1);
            setSure(pos_sure);


            size = App.ComRecData.getInt("users_size", 0);
            beans = new GetUserBean.DataBean.UserListBean[size >= 4 ? 4 : size];
            for (int i = 0; i < beans.length; i++) {
                GetUserBean.DataBean.UserListBean bean = null;

                String uid = App.ComRecData.getString("o_uid" + i, "");
                String avatar = App.ComRecData.getString("o_avatar" + i, "");
                String name = App.ComRecData.getString("o_name" + i, "");
                if (!TextUtils.isEmpty(uid)) {
                    bean = new GetUserBean.DataBean.UserListBean();
                    beans[i] = bean;
                    beans[i].setUid(uid);
                }
                if (!TextUtils.isEmpty(avatar) && beans[i] != null) {
                    beans[i].setAvatar(avatar);
                    ImageLoader.getInstance().displayImage(StringUtil.getShowText(beans[i].getAvatar()), civs[i] ,builder.build());
                } else {
                    civs[i].setImageResource(R.mipmap.default_avatar);
                }
                if (!TextUtils.isEmpty(name) && beans[i] != null) {
                    beans[i].setName(name);
                    tvs[i].setText(beans[i].getName());
                }
                if (beans[i] != null) {
                    civs[i].setTag(beans[i]);
                }
            }

            isFirst = App.ComRecData.getBoolean("first", true);
        }
    }

    private void setSure(int pos) {
        for (int i = 0; i < ivs_small.length; i++) {
            if (i == pos) {
                ivs_small[i].setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 获取其他小伙伴头像和名字
     */
    private void getData() {
        quiz_id = getIntent().getLongExtra("quiz_id", -1);
        myRecordBean = (MyRecordBean) getIntent().getSerializableExtra("myRecord");
        banRecordBean = (MyRecordBean) getIntent().getSerializableExtra("banRecord");
        otherRecordBean = banRecordBean;

        if (size == 0) {
            //调用玩一玩接口获取前八名信息   这个接口是只要进这个页面就掉
            showMyprogressDialog();
            new PlayPlayUserMiddle(this, this).getUser(String.valueOf(quiz_id));
        }

        showMyprogressDialog();
        if(size == 0){
            //说明没有保存  左边是自己，右边是默认的
            pmv.setData(myRecordBean, null, PlayMp3View.MY_IN_LEFT);
        }else if (state == LEFT && "伴小读".equals(switchBean.getName())){
            pmv.setData(myRecordBean,banRecordBean,PlayMp3View.MY_IN_LEFT);
        }else if (state == RIGHT && "伴小读".equals(switchBean.getName())) {
            pmv.setData(myRecordBean, banRecordBean, PlayMp3View.MY_IN_RIGHT);
        }else if (state == LEFT && "".equals(switchBean.getName())){
            pmv.setData(myRecordBean, null, PlayMp3View.MY_IN_LEFT);
        }else{
            if(state == LEFT){
                //根据被保存的那个人的uid在去及时获取这个人的录音
                showMyprogressDialog();
                otherHead = switchBean.getAvatar();
                new GetUserRecordMiddle(this, this).getUserRecord(String.valueOf(quiz_id), switchBean.getUid());
                pmv.setData(myRecordBean,otherRecordBean,PlayMp3View.MY_IN_LEFT);
            }else{
                //在这里取出那个保存的人 然后赋值给otherBean
                showMyprogressDialog();
                new GetUserRecordMiddle(this, this).getUserRecord(String.valueOf(quiz_id), switchBean.getUid());
                otherHead = switchBean.getAvatar();
                pmv.setData(myRecordBean,otherRecordBean,PlayMp3View.MY_IN_RIGHT);
            }
        }
        hideMyprogressDialog();
    }

    @OnClick({R.id.btn_switch, R.id.tv_refresh, R.id.civ_head_other1_small,R.id.btn_tweet,R.id.button_back,R.id.title_modify_record,
            R.id.civ_head_other2_small, R.id.civ_head_other3_small, R.id.civ_othetr4_small,R.id.civ_head_user_small})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_switch:
                if("".equals(switchBean.getName())){
                    return;
                }
                if (state == LEFT) {
                    //我在左边，当点击按钮时，我的信息变成右边的，右边的信息换成我的
                    tv_othername0.setText(myBean.getName());
                    ImageLoader.getInstance().displayImage(StringUtil.getShowText(myBean.getAvatar()), civ_head_other,builder.build());

                    tv_username.setText(switchBean.getName());
                    ImageLoader.getInstance().displayImage(StringUtil.getShowText(switchBean.getAvatar()), civ_head ,builder.build());

                    switchCiv = civ_head;
                    switchTv = tv_username;
                    state = RIGHT;
                    aotuPlayMp3(PlayMp3View.MY_IN_RIGHT);
                }else {
                    tv_username.setText(myBean.getName());
                    ImageLoader.getInstance().displayImage( StringUtil.getShowText(myBean.getAvatar()), civ_head,builder.build());

                    tv_othername0.setText(switchBean.getName());
                    ImageLoader.getInstance().displayImage(StringUtil.getShowText(switchBean.getAvatar()), civ_head_other,builder.build());

                    switchCiv = civ_head_other;
                    switchTv = tv_othername0;
                    state = LEFT;
                    aotuPlayMp3(PlayMp3View.MY_IN_LEFT);
                }
                if ("".equals(switchBean.getName())) {
                    switchCiv.setImageResource(R.mipmap.default_avatar);
                } else if ("伴小读".equals(switchBean.getName())) {
                    switchCiv.setImageResource(R.mipmap.bandu_icon2);
                    if(state == LEFT ){ //我在左边
                        pmv.setData(myRecordBean,banRecordBean, PlayMp3View.MY_IN_LEFT);
                    }else{
                        pmv.setData(myRecordBean,banRecordBean, PlayMp3View.MY_IN_RIGHT);
                    }
                }
                break;
            case R.id.tv_refresh:
                if (pos_sure != 0) {
                    pos_sure = -1;
                }

                cancelSure(0);
                if (users != null && isFirst && users.size() > 4) {
                    for (int i = 4; i < (users.size() <= 8 ? users.size() : 8); i++) {
                        beans[i - 4] = users.get(i);


                        if(TextUtils.isEmpty(users.get(i).getAvatar())){
                            civs[i - 4].setImageResource(R.mipmap.default_avatar);
                        }else{
                            ImageLoader.getInstance().displayImage(StringUtil.getShowText(users.get(i).getAvatar()),
                                    civs[i - 4],builder.build());
                        }

                        tvs[i - 4].setText(users.get(i).getName());
                        civs[i - 4].setTag(users.get(i));
                    }
                    setSure(beans); // 解决随机出现同一用户的对勾显示问题
                    isFirst = false;
                } else {
                    //通过另外一个接口获取随机的用户信息
                    showMyprogressDialog();
                    new PlayRandomUserMiddle(this, this).getRandomUser(String.valueOf(quiz_id));
                }
                break;
            case R.id.civ_head_other1_small:
                othersClick(1); //上下切换用户

                GetUserBean.DataBean.UserListBean user1 =
                        (GetUserBean.DataBean.UserListBean) civ_other1.getTag();
                if (user1 != null) {
                    showMyprogressDialog();
                    new GetUserRecordMiddle(this, this).getUserRecord(String.valueOf(quiz_id), user1.getUid());
                    otherHead = user1.getAvatar();
                    cancelSure(1);
                }

                break;
            case R.id.civ_head_other2_small:
                othersClick(2);

                GetUserBean.DataBean.UserListBean user2 =
                        (GetUserBean.DataBean.UserListBean) civ_other2.getTag();
                if (user2 != null) {
                    showMyprogressDialog();
                    new GetUserRecordMiddle(this, this).getUserRecord(String.valueOf(quiz_id), user2.getUid());
                    otherHead = user2.getAvatar();
                    cancelSure(2);
                }

                break;
            case R.id.civ_head_other3_small:
                othersClick(3);
                GetUserBean.DataBean.UserListBean user3 =
                        (GetUserBean.DataBean.UserListBean) civ_other3.getTag();
                if (user3 != null) {
                    showMyprogressDialog();
                    new GetUserRecordMiddle(this, this).getUserRecord(String.valueOf(quiz_id), user3.getUid());
                    otherHead = user3.getAvatar();
                    cancelSure(3);
                }

                break;
            case R.id.civ_othetr4_small:
                othersClick(4);
                GetUserBean.DataBean.UserListBean user4 =
                        (GetUserBean.DataBean.UserListBean) civ_other4.getTag();
                if (user4 != null) {
                    showMyprogressDialog();
                    new GetUserRecordMiddle(this, this).getUserRecord(String.valueOf(quiz_id), user4.getUid());
                    otherHead = user4.getAvatar();
                    cancelSure(4);
                }

                break;
            case R.id.civ_head_user_small:
                pos_sure = 0;
                //半小读是选中的，并且半小读在右边
                showMyprogressDialog();
                if(pos_sure == 0 && state==LEFT){
                    pmv.setData(myRecordBean,banRecordBean, PlayMp3View.MY_IN_LEFT);
                }else if(pos_sure==0 && state==RIGHT){
                    //半小读在左边
                    pmv.setData(myRecordBean,banRecordBean, PlayMp3View.MY_IN_RIGHT);
                }
                hideMyprogressDialog();
                UIUtils.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pmv.startMp3();
                    }
                },300);


                switchCiv.setImageResource(R.mipmap.bandu_icon2);
                switchTv.setText("伴小读");
                switchBean.setUid("");
                switchBean.setAvatar("");
                switchBean.setName("伴小读");

                iv_head_user_small.setVisibility(View.VISIBLE);
                cancelSure(0);
                break;
            case R.id.button_back:
                //点击不保存就清空app.ComRecData 并返回练习本列表
                App.ComRecData.clear();
                Intent intent1 =new Intent();
                intent1.setAction(FinishRecevice.FINISH_ACTION);
                sendBroadcast(intent1);
                UIUtils.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },200);

                break;
            case R.id.title_modify_record:
                //保存位置，sure按钮和switchBean中的信息
                App.ComRecData.putInt("pos", state);
                App.ComRecData.putString("o_uid", switchBean.getUid());
                App.ComRecData.putString("o_avatar", switchBean.getAvatar());
                App.ComRecData.putString("o_name", switchBean.getName());
                App.ComRecData.putInt("pos_sure", pos_sure);
                App.ComRecData.putBoolean("first", isFirst);
                App.ComRecData.putInt("users_size", size);
                //保存当前四个用户的信息
                for (int i = 0; i < (size >= 4 ? 4 : size); i++) {
                    App.ComRecData.putString("o_uid" + i, beans[i].getUid());
                    App.ComRecData.putString("o_avatar" + i, beans[i].getAvatar());
                    App.ComRecData.putString("o_name" + i, beans[i].getName());
                }

                StudentDoExerciseActivity_NEW.thiz.setSelection(0);
                pmv.cleanMp3(); //清空录音
                finish(); //返回到练习页

                break;


            //分享
            case R.id.btn_qq_share://QQ
                pmv.cleanMp3();
                mUids = getUid();
                GlobalParams.SHARE_TYPE = 1;
                ShareUtils.qqShare(CombinaRecordActivity.this, mTencent,mUids[0],mUids[1],String.valueOf(quiz_id));
                break;
            case R.id.btn_qqzone_share://QQ空间
                pmv.cleanMp3();
                mUids = getUid();
                GlobalParams.SHARE_TYPE = 1;
                ShareUtils.qqZoneShare(CombinaRecordActivity.this, mTencent,mUids[0],mUids[1],String.valueOf(quiz_id));
                break;
            case R.id.btn_tweet://微博
                pmv.cleanMp3();
                mUids = getUid();
                GlobalParams.SHARE_TYPE = 2;
                ShareUtils.tweetShare(this, mWeiboShareAPI,mUids[0],mUids[1],String.valueOf(quiz_id));
                break;
            case R.id.btn_wechart_friend_share://微信朋友圈
                pmv.cleanMp3();
                mUids = getUid();
                ShareUtils.wechatShare(this, 1,mUids[0],mUids[1],String.valueOf(quiz_id));
                break;
            case R.id.btn_wechart_share://微信
                pmv.cleanMp3();
                mUids = getUid();
                ShareUtils.wechatShare(this, 0,mUids[0],mUids[1],String.valueOf(quiz_id));
                break;
        }
    }

    private String[] getUid() {
        String[] uids = new String[2];
        if(null != myBean && null != switchBean) {
            // 给uid1赋值   我在右边半小读在左边
            if (state == RIGHT && "伴小读".equals(switchBean.getName())) {
                uids[0] = "1";
            } else {
                //我在右边 他人在左边
                uids[0] = state == LEFT ? myBean.getUid() : switchBean.getUid();
            }
            // 给uid2赋值   我在左边
            if (state == LEFT && "".equals(switchBean.getName())) {
                //右边是默认图标
                uids[1] = "0";
            } else if (state == LEFT && "伴小读".equals(switchBean.getName())) {
                //右边是半小读
                uids[1] = "1";
            } else {
                //右边是他人
                uids[1] = state == LEFT ? switchBean.getUid() : myBean.getUid();
            }
        }
        return uids;
    }


    private void cancelSure(int index) {
        for (int i = 0; i < ivs_small.length; i++) {
            if (i != index) {
                ivs_small[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        hideMyprogressDialog();
        switch (requestCode) {
            case 1: //解析前4个用户
                final GetUserBean bean = (GetUserBean) result;
                if (bean != null && bean.getStatus() == 1) {
                    users = bean.getData().getUser_list();
                    size = users.size();

                    int curSize = users.size() >= 4 ? 4 : users.size();
                    beans = new GetUserBean.DataBean.UserListBean[curSize];
                    for (int i = 0; i < curSize; i++) {
                        beans[i] = users.get(i);

                        if(TextUtils.isEmpty(users.get(i).getAvatar())){
                            civs[i].setImageResource(R.mipmap.default_avatar);
                        }else{
                            ImageLoader.getInstance().displayImage(StringUtil.getShowText(users.get(i).getAvatar()), civs[i], builder.build());
                        }
                        tvs[i].setText(users.get(i).getName());
                        civs[i].setTag(users.get(i));
                    }
                    setSure(beans); // 解决随机出现同一用户的对勾显示问题
                }
                break;

            case GetUserRecordMiddle.GET_USERECORD:
                SystemClock.sleep(500);
                hideMyprogressDialog();
                otherRecordBean = (MyRecordBean) result;
                if (otherRecordBean != null && otherRecordBean.getStatus() == 1) {
                    //获取其他人的录音信息封装在一个otherRecordBean对象中
                    for (int i = 0; i < otherRecordBean.getData().getSentences().size(); i++) {
                        otherRecordBean.getData().getSentences().get(i).setHead(otherHead);
                    }
                    aotuPlayMp3(state == LEFT ? PlayMp3View.MY_IN_LEFT : PlayMp3View.MY_IN_RIGHT);
                }
                break;
        }

    }
    //服务器返回404，此时让imageloader加载默认的头像
    DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder().showImageOnFail(R.mipmap.default_avatar);
    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
        hideMyprogressDialog();
        UIUtils.showToastSafe("网络异常，请检查网络");
    }

    private void setSure(GetUserBean.DataBean.UserListBean[] mData) {
        if (mData == null) return;
        for (int i = 0; i < mData.length; i++) {
            if (TextUtils.equals(mData[i].getUid(), switchBean.getUid())) {
                pos_sure = i + 1;
                setSure(pos_sure);
            }
        }
    }

    private void othersClick(int index) {
        GetUserBean.DataBean.UserListBean user =
                (GetUserBean.DataBean.UserListBean) civs_small[index].getTag();
        if (null != user && null !=switchBean && null !=switchCiv && null!= switchTv) {
            ImageLoader.getInstance().displayImage(
                    StringUtil.getShowText(user.getAvatar()), switchCiv,builder.build());
            switchTv.setText(user.getName());

            pos_sure = index;
            switchBean.setUid(user.getUid());
            switchBean.setName(user.getName());
            switchBean.setAvatar(user.getAvatar());
            ivs_small[index].setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        pmv.unbindService();
        pmv.cleanMp3(); //清空录音
    }

    @Override
    protected void onPause() {
        super.onPause();
        pmv.cleanMp3(); //清空录音
    }//正常流程，data！=null   返回伴读data！=null   取消data！=null     单独进入data=null
    public static Intent data;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.data = data;
        Tencent.onActivityResultData(requestCode, resultCode, data, listener);
    }

    @Override
    public void onResponse(BaseResponse baseResp) {
        if(baseResp!= null){
            switch (baseResp.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    if(GlobalParams.SHARE_TYPE == 2) {
                        UIUtils.showToastSafe("分享成功");
                    }
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    if(GlobalParams.SHARE_TYPE == 2) {
                        UIUtils.showToastSafe("分享取消");
                    }
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    if(GlobalParams.SHARE_TYPE == 2) {
                        LogUtils.i("ERR_FAIL" + "Error Message: " + baseResp.errMsg);
                        UIUtils.showToastSafe("ERR_FAIL" + "Error Message: " + baseResp.errMsg);
                    }
                    break;
            }
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }


    private void aotuPlayMp3(int position){
            UIUtils.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        showMyprogressDialog();
                    } catch (Exception e) {
                        cleanMyprogressDialog();
                    }
                }
            });
            pmv.setData(myRecordBean, otherRecordBean, position);
            UIUtils.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    hideMyprogressDialog();
                }
            });

            UIUtils.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (NetworkUtil.checkNetwork(CombinaRecordActivity.this)) {
                        pmv.startMp3();
                    } else {
                        UIUtils.showToastSafe("网络异常，请检查网络");
                    }

                }
            }, 300);
    }

}