package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.SelectGradeAdapter;
import me.bandu.talk.android.phone.bean.GetGradesBean;
import me.bandu.talk.android.phone.middle.GetGradesMiddle;

/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：选择年级
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class SelectGradeActivity extends BaseTeacherActivity implements View.OnClickListener{
    private RelativeLayout iv_left;
    private TextView tv_middle,tv_right;
    private ListView lv_grades;
    private SelectGradeAdapter adapter;
    private List<GetGradesBean.DataEntity.ListEntity> grades;
    private GetGradesMiddle getGradesMiddle;
    private String classid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_grade;
    }

    @Override
    protected void toStart() {
        initView();
        setData();
        setListeners();
    }



    @Override
    public void initView() {
        iv_left = (RelativeLayout) findViewById(R.id.title_left);
        tv_middle = (TextView) findViewById(R.id.title_middle);
        tv_right = (TextView) findViewById(R.id.title_right);
        lv_grades = (ListView) findViewById(R.id.lv_grades);
    }

    @Override
    public void setData() {
        classid = getIntent().getStringExtra("classid");
        tv_right.setVisibility(View.GONE);
        tv_middle.setText(R.string.select_grade);
        grades = new ArrayList<>();
        adapter = new SelectGradeAdapter(this,grades);
        lv_grades.setAdapter(adapter);
        getGradesMiddle = new GetGradesMiddle(this,this);
        getGradesMiddle.getGrades();
    }

    @Override
    public void setListeners() {
        iv_left.setOnClickListener(this);
        lv_grades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SelectGradeActivity.this,BindTextBookActivity.class);
                GetGradesBean.DataEntity.ListEntity grade = (GetGradesBean.DataEntity.ListEntity) adapter.getItem(i);
                intent.putExtra("gradeid",grade.getGrade_id() + "");
                intent.putExtra("classid",classid);
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            setResult(RESULT_OK,data);
            finish();
        }
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
        GetGradesBean getGradesBean = (GetGradesBean) obj[0];
        grades.removeAll(grades);
        grades.addAll(getGradesBean.getData().getList());
        adapter.notifyDataSetChanged();
    }
}
