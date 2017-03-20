package me.bandu.talk.android.phone.activity;


import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.utils.UIUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.SelectStudentAdapter;
import me.bandu.talk.android.phone.bean.ClassStudentBean;
import me.bandu.talk.android.phone.bean.ClassStudentEntity;
import me.bandu.talk.android.phone.middle.SelectStudentMiddle;
import me.bandu.talk.android.phone.utils.AlphaUtil;
import me.bandu.talk.android.phone.view.SideBar;
/**
 * 创建者：高楠
 * 时间：2015/11/26
 * 类描述：选择学生
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

public class SelectStudentActivity extends BaseAppCompatActivity implements SideBar.OnTouchingLetterChangedListener{
    @Bind(R.id.select_student_recycler)
    ListView listView;
    @Bind(R.id.slidebar)
    SideBar slide_listview;
    @Bind(R.id.more)
    TextView more;
    @Bind(R.id.select_student_class)
    TextView select_student_class;
    SelectStudentAdapter adapter;
    List<String> slide_data;
    @Bind(R.id.title_right)
    RelativeLayout title_right;
    private String cid;
    private String uid;
    private String class_name;
    private List<ClassStudentEntity> selects;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_student;
    }

    @Override
    protected void toStart() {
        cid = getIntent().getStringExtra("cid");
        uid = getIntent().getStringExtra("uid");
        class_name = getIntent().getStringExtra("class_name");
        select_student_class.setText(class_name);
        title_right.setVisibility(View.VISIBLE);
        more.setText(R.string.confirm);
        selects = (List<ClassStudentEntity>) getIntent().getSerializableExtra("selected");
        slide_listview.setOnTouchingLetterChangedListener(this);
        showMyprogressDialog();
        new SelectStudentMiddle(this,this).getStudentList(cid,uid);
    }
    public void initListView(ClassStudentBean bean){
        adapter = new SelectStudentAdapter(this,sortList(bean),selects);
        listView.setAdapter(adapter);
    }
    String current;
    public List<ClassStudentEntity> sortList(ClassStudentBean classbean){
        List<ClassStudentEntity> list = new ArrayList<>();
        slide_data = new ArrayList<>();
        for (int i = 0;i<classbean.getData().getNomal().getList().size();i++){
            ClassStudentEntity entity =  classbean.getData().getNomal().getList().get(i);
            char firstchar = entity.getName().charAt(0);
            String s = "";
            if ((firstchar >= 'a' && firstchar <= 'z') || (firstchar >= 'A' && firstchar <= 'Z')){
                s = String.valueOf(firstchar);
                entity.setAlpha(s.toUpperCase());
            }else {
                s = AlphaUtil.getPinYinHeadChar(entity.getName());
                entity.setAlpha(s.substring(0,1).toUpperCase());
            }
            entity.setName(entity.getName());
            list.add(entity);
        }
        Collections.sort(list);
        for (int i = 0;i<list.size();i++){
            if (!list.get(i).getAlpha().equals(current)){
                current = list.get(i).getAlpha();
                slide_data.add(current);
            }
        }
        Collections.sort(slide_data);
        return list;
    }
    @OnClick({R.id.selectnone,R.id.selectall})
    void onClick(View view){
        switch (view.getId()){
            case R.id.selectall:
                if (adapter!=null)
                    adapter.selectAll();
                break;

            case R.id.selectnone:
                if (adapter!=null)
                    adapter.selectNone();
                break;
        }
    }
    @Override
    public void successFromMid(Object... obj) {
        super.successFromMid(obj);
        hideMyprogressDialog();
        int requestCode = (int) obj[1];
        Object result = obj[0];
        if (requestCode==SelectStudentMiddle.STUDENT_LIST){
            ClassStudentBean bean = (ClassStudentBean) result;
            initListView(bean);
        }
    }

    @Override
    public void failedFrom(Object... obj) {
        super.failedFrom(obj);
        hideMyprogressDialog();
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        if (adapter!=null){
            int ss = adapter.getLetterPosition(s);
            if (ss!=-1){
                listView.setSelection(ss);
            }
        }
    }
    @Override
    protected String setTitle() {
        return getString(R.string.select_student);
    }

    @Override
    public void clickRight() {
        super.clickRight();
        if (adapter!=null&&adapter.isOK()) {
            List<ClassStudentEntity> list_selected = adapter.getSelected();
            if (list_selected != null && list_selected.size() != 0) {
                Intent intent = new Intent();
                intent.putExtra("list", (Serializable)list_selected);
                intent.putExtra("total", adapter.getCount());
                setResult(RESULT_OK, intent);
                finish();
            }
        } else {
            UIUtils.showToastSafe(getString(R.string.select_student_info));
        }
    }
}
