package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.base.BaseBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.SchoolAdapter;
import me.bandu.talk.android.phone.bean.SchoolBean;
import me.bandu.talk.android.phone.middle.GetSchoolMiddle;
import me.bandu.talk.android.phone.middle.ModifyUserInfoMiddle;
import me.bandu.talk.android.phone.sortlistview.CharacterParser;
import me.bandu.talk.android.phone.sortlistview.PinyinComparator1;
import me.bandu.talk.android.phone.sortlistview.SideBar;
import me.bandu.talk.android.phone.utils.AlphaUtil;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.view.CustomDialog;
/**
 * 创建者：tg
 * 类描述：选择学校
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class SelectSchoolActivity extends BaseAppCompatActivity {

    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.address_lv)
    ListView listView;
    @Bind(R.id.sidrbar)
    SideBar sideBar;
    @Bind(R.id.title_right)
    RelativeLayout rl;
    @Bind(R.id.more)
    TextView addTv;
    @Bind(R.id.dialog1)
    TextView dialogTv;
    @Bind(R.id.info_tv)
    TextView infoTv;

    List<SchoolBean.DataEntity.ListEntity> list;
    List<SchoolBean.DataEntity.ListEntity> list1;
    TextView tv;
    CustomDialog dialog;
    private int sec = 3;
    private PinyinComparator1 pinyinComparator;
    private CharacterParser characterParser;
    SchoolAdapter adapter;
    Handler handler = new Handler();

    String dicId;
    String schoolName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_address;
    }

    @Override
    protected void toStart() {
        titleTv.setText(R.string.select_school);
        rl.setVisibility(View.VISIBLE);
        addTv.setText(R.string.add_school);
        infoTv.setVisibility(View.VISIBLE);
        dicId = getIntent().getStringExtra("dicId");
        pinyinComparator = new PinyinComparator1();
        characterParser = CharacterParser.getInstance();
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        adapter = new SchoolAdapter(this, list1);
        listView.setAdapter(adapter);
        sideBar.setTextView(dialogTv);
        initEvent();
        new GetSchoolMiddle(this,this).getSchool(dicId,new SchoolBean());
        //initData();
    }

    public void initData() {

        for (SchoolBean.DataEntity.ListEntity address : list) {
            String pinyin = characterParser.getSelling(address.getName());
            String sortString = "";
            if (pinyin.length() > 0) {
                //pinyin = Utils.getPingYing(pinyin);
                //sortString = pinyin.substring(0, 1).toUpperCase();
                sortString = AlphaUtil.getPinYinHeadChar(address.getName());
            }
            //if (sortString.matches("[A-Z]")) {
                address.setSortLetters(sortString.toUpperCase());
           // } else {
            //    address.setSortLetters("#");
          //  }
            list1.add(address);
        }
        Collections.sort(list1, pinyinComparator);
        refreshView();
    }

    public void refreshView() {
        adapter.notifyDataSetChanged();
    }

    @OnItemClick(R.id.address_lv)
    void itemSelect(int position) {
        new ModifyUserInfoMiddle(this,this).modifySchool(GlobalParams.userInfo.getUid(),list1.get(position).getSid());
        schoolName = list1.get(position).getName();
        //GlobalParams.userInfo.setSchool(list1.get(position).getName());
    }

    @OnClick(R.id.more)
    void click(View v) {
        Intent intent = new Intent(this,AddSchoolActivity.class);
        intent.putExtra("dicId",dicId);
        startActivity(intent);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (sec >= 0) {
                tv.setText(sec + "秒后自动返回个人资料页面");
                handler.postDelayed(this, 1000);
                sec--;
            } else {
                dialog.dismiss();
                handler.removeCallbacks(runnable);
                startActivity(new Intent(SelectSchoolActivity.this, PersonalDataActivity.class));
            }
        }
    };

    @Override
    public void onSuccess(Object result, int requestCode) {
        if (requestCode == 1) {
            SchoolBean bean = (SchoolBean) result;
            if (bean != null && bean.getStatus() == 1) {
                list.addAll(bean.getData().getList());
                initData();
            }
        } else if (requestCode == 2) {
            BaseBean bean = (BaseBean) result;
            if (bean != null && bean.getStatus() == 1) {
                UserUtil.getUerInfo(this).setSchool(schoolName);
                UserUtil.saveUserInfo(this,UserUtil.getUerInfo(this));
                View view = LayoutInflater.from(this).inflate(R.layout.customdialog, null, false);
                tv = (TextView) view.findViewById(R.id.time_tv);
                dialog = new CustomDialog(this, R.style.mystyle, view);
                dialog.show();
                handler.postDelayed(runnable, 0);
            }
        }

    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
    }

    public void initEvent() {
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    listView.setSelection(position);
                }
            }
        });
    }
}
