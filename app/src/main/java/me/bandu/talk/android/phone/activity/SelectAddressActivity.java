package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.OnItemClick;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.AddressAdapter;
import me.bandu.talk.android.phone.bean.AddressBean;
import me.bandu.talk.android.phone.middle.GetRegionMiddle;
import me.bandu.talk.android.phone.sortlistview.CharacterParser;
import me.bandu.talk.android.phone.sortlistview.PinyinComparator;
import me.bandu.talk.android.phone.sortlistview.SideBar;
import me.bandu.talk.android.phone.utils.Utils;

/**
 * 创建者：tg
 * 类描述：选择地址
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class SelectAddressActivity extends BaseAppCompatActivity {

    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.address_lv)
    ListView listView;
    @Bind(R.id.sidrbar)
    SideBar sideBar;
    @Bind(R.id.dialog1)
    TextView dialogTv;

    List<AddressBean.DataEntity.ListEntity> list;
    List<AddressBean.DataEntity.ListEntity> list1;
    private PinyinComparator pinyinComparator;
    private CharacterParser characterParser;
    AddressAdapter adapter;

    int clickNum = 0; //点击次数 0：选择省 1：选择市 2：选择区

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_address;
    }

    @Override
    protected void toStart() {
        titleTv.setText(R.string.select_province);
        pinyinComparator = new PinyinComparator();
        characterParser = CharacterParser.getInstance();
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        adapter = new AddressAdapter(this, list1);
        listView.setAdapter(adapter);
        sideBar.setTextView(dialogTv);
        initEvent();
        getRegionList(0);
    }

    public void getRegionList(int parentId) {
        new GetRegionMiddle(this, this).getRegion(parentId, new AddressBean());
    }

    @OnItemClick(R.id.address_lv)
    void itemClick(int position) {
        clickNum++;
        if (clickNum == 3) {
            Intent intent = new Intent(this, SelectSchoolActivity.class);
            intent.putExtra("dicId", list1.get(position).getDistrict_id() + "");
            startActivity(intent);
        } else {
            if (clickNum == 1) {
                titleTv.setText(R.string.select_city);
            } else if (clickNum == 2) {
                titleTv.setText(R.string.select_district);
            }
            int parentId = list1.get(position).getDistrict_id();
            getRegionList(parentId);
        }

    }

    public void initData() {

        for (AddressBean.DataEntity.ListEntity address : list) {
            //String pinyin = characterParser.getSelling(address.getName());
            String sortString = "";
            //if (pinyin.length() > 0) {
            //pinyin = Utils.getPingYing(pinyin);
            //sortString = pinyin.substring(0, 1).toUpperCase();
            //sortString = AlphaUtil.getPinYinHeadChar(address.getName());
            // }
            // if (sortString.matches("[A-Z]")) {
            sortString = Utils.getPingYing(address.getName());
            address.setSortLetters(sortString.toUpperCase());
            // } else {
            //address.setSortLetters("#");
            // }
            //list1.clear();
            list1.add(address);
        }
        Collections.sort(list1, pinyinComparator);
        refreshView();
    }

    public void refreshView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        AddressBean bean = (AddressBean) result;
        if (bean != null && bean.getStatus() == 1) {
            list1.clear();
            list.clear();
            list.addAll(bean.getData().getList());
            initData();
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
