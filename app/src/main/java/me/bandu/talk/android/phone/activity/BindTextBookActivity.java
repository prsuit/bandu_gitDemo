package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.DFHT.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.SelectVersionAdapter;
import me.bandu.talk.android.phone.bean.GradeTextBookBean;
import me.bandu.talk.android.phone.middle.BindTextbookMiddle;
import me.bandu.talk.android.phone.middle.GradeTextBookMiddle;

/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：绑定教材
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

public class BindTextBookActivity extends BaseTeacherActivity implements View.OnClickListener,SelectVersionAdapter.SelectTextbookListener {
    private RelativeLayout title_left;
    private TextView tv_middle,tv_right;
    private ListView lv_versions;
    private List<GradeTextBookBean.DataEntity.Version> versions;
    private SelectVersionAdapter adapter;
    private GradeTextBookMiddle gradeTextBookMiddle;
    private String classid;
    private BindTextbookMiddle bindTextbookMiddle;
    private int bookid;

    public String getClassId(){
        return classid;
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_text_book;
    }

    @Override
    protected void toStart() {
        initView();
        setData();
        setListeners();
    }

    @Override
    public void initView() {
        title_left = (RelativeLayout) findViewById(R.id.title_left);
        tv_middle = (TextView) findViewById(R.id.title_middle);
        tv_right = (TextView) findViewById(R.id.title_right);
        lv_versions = (ListView) findViewById(R.id.lv_versions);
    }

    @Override
    public void setData() {
        String gradeid = getIntent().getStringExtra("gradeid");
        classid = getIntent().getStringExtra("classid");
        tv_right.setVisibility(View.GONE);
        tv_middle.setText(R.string.book_select);
        versions = new ArrayList<>();
        adapter = new SelectVersionAdapter(this,versions,this);
        lv_versions.setAdapter(adapter);

        gradeTextBookMiddle = new GradeTextBookMiddle(this,this);
        gradeTextBookMiddle.getGradeTextbook(gradeid);
        bindTextbookMiddle = new BindTextbookMiddle(this,this);
    }

    @Override
    public void setListeners() {
        title_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
        }
    }

    @Override
    public void successFromMid(Object... obj) {
        super.successFromMid(obj);
        int code = (int) obj[1];{
            if (code == GradeTextBookMiddle.GRADE_TEXTBOOK){
                GradeTextBookBean bookBean = (GradeTextBookBean) obj[0];
                versions.removeAll(versions);
                versions.addAll(bookBean.getData().getList());
                adapter.notifyDataSetChanged();
            }else if (code == BindTextbookMiddle.BIND_TEXTBOOK){
                BaseBean baseBean = (BaseBean) obj[0];
                Toast.makeText(this,baseBean.getMsg(),Toast.LENGTH_SHORT).show();
                if (baseBean.getStatus() == 1){
                    GlobalParams.HOME_CHANGED = true;
                    Intent intent = new Intent();
                    intent.putExtra("bookid",bookid);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }
    }

    @Override
    public void failedFrom(Object... obj) {
        super.failedFrom(obj);
    }

    @Override
    public void selectTextbook(int bookid) {
        this.bookid = bookid;
        bindTextbookMiddle.bindClassTextbook(classid, "" + bookid);
    }
}
