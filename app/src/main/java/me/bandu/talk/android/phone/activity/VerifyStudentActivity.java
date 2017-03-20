package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.DFHT.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.VerifyStudentAdapter;
import me.bandu.talk.android.phone.bean.TStudentBean;
import me.bandu.talk.android.phone.middle.AccessRefuseStudentMiddle;
import me.bandu.talk.android.phone.middle.VerifyStudentMiddle;
import me.bandu.talk.android.phone.view.CircleImageView;
/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：审核学生
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class VerifyStudentActivity extends BaseTeacherActivity implements View.OnClickListener{
    private RelativeLayout title_left;
    private ImageView iv_select;
    private TextView tv_middle,tv_right,tv_selectall,tv_selectnotall,tv_access,tv_refuse;
    private CircleImageView civ_head;
    private TextView tv_name;
    private ArrayList<TStudentBean> students;
    private int[] selects;
    private VerifyStudentAdapter adapter;
    private ListView lv_students;
    private AccessRefuseStudentMiddle accessRefuseStudentMiddle;
    private List<Long> uids;
    private String cid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_verify_student;
    }

    @Override
    protected void toStart() {
        initView();
        setData();
        setListeners();
    }

    @Override
    public void initView() {
        GlobalParams.HOME_CHANGED = true;
        title_left = (RelativeLayout) findViewById(R.id.title_left);
        tv_middle = (TextView) findViewById(R.id.title_middle);
        tv_right = (TextView) findViewById(R.id.title_right);

        iv_select = (ImageView) findViewById(R.id.iv_select);
        civ_head = (CircleImageView) findViewById(R.id.civ_head);
        tv_name = (TextView) findViewById(R.id.tv_name);

        lv_students = (ListView) findViewById(R.id.lv_students);

        tv_selectall = (TextView) findViewById(R.id.tv_selectall);
        tv_selectnotall = (TextView) findViewById(R.id.tv_selectnotall);
        tv_access = (TextView) findViewById(R.id.tv_access);
        tv_refuse = (TextView) findViewById(R.id.tv_refuse);
    }

    @Override
    public void setData() {
        students = (ArrayList<TStudentBean>) getIntent().getSerializableExtra("verifyStudents");
        cid = getIntent().getStringExtra("classid");
        accessRefuseStudentMiddle = new AccessRefuseStudentMiddle(this,this);
        uids = new ArrayList<>();
        tv_right.setVisibility(View.GONE);
        tv_middle.setText("待审核名单");
        selects = new int[students.size()];
        adapter = new VerifyStudentAdapter(this,students,selects);
        lv_students.setAdapter(adapter);
    }

    @Override
    public void setListeners() {
        title_left.setOnClickListener(this);
        tv_selectall.setOnClickListener(this);
        tv_selectnotall.setOnClickListener(this);
        tv_access.setOnClickListener(this);
        tv_refuse.setOnClickListener(this);
        lv_students.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (selects[i] == 0)
                    selects[i] = 1;
                else selects[i] = 0;
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.tv_selectall:
                setSelectAll();
                break;
            case R.id.tv_selectnotall:
                setSelectNull();
                break;
            case R.id.tv_access:
                accessRefuseStudent(AccessRefuseStudentMiddle.TYPE_ACCESS);
                break;
            case R.id.tv_refuse:
                accessRefuseStudent(AccessRefuseStudentMiddle.TYPE_REFUSEORDELETE);
                break;
        }
    }

    private void accessRefuseStudent(int type) {
        uids.removeAll(uids);
        for (int i = 0;i<selects.length;i++){
            if (selects[i] == 1){
                uids.add(students.get(i).getUid());
            }
        }
        if (uids.size() <= 0){
            Toast.makeText(VerifyStudentActivity.this, "请选择学生", Toast.LENGTH_SHORT).show();
            return;
        }
        long[] uidArray = new long[uids.size()];
        for (int i = 0;i<uids.size();i++){
            uidArray[i] = uids.get(i);
        }
        accessRefuseStudentMiddle.accessRefuseStudents(cid,uidArray,type);
    }

    private void setSelectNull() {
        for (int i = 0;i<selects.length;i++){
            selects[i] = 0;
        }
        adapter.notifyDataSetChanged();
    }

    private void setSelectAll() {
        for (int i = 0;i<selects.length;i++){
            selects[i] = 1;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void successFromMid(Object... obj) {
        super.successFromMid(obj);
        BaseBean baseBean = (BaseBean) obj[0];
        Toast.makeText(VerifyStudentActivity.this, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
        if (1 == baseBean.getStatus()){
            Intent intent = new Intent();
            intent.putExtra("change",true);
            setResult(RESULT_OK,intent);
            GlobalParams.HOME_CHANGED = true;
            finish();
        }
    }
}
