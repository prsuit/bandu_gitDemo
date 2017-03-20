package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.DFHT.utils.UIUtils;
import java.util.ArrayList;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.ChoseStudentAdapter;
import me.bandu.talk.android.phone.bean.ChoseStudentListBean;
import me.bandu.talk.android.phone.middle.ChoseStudentListMiddle;
import me.bandu.talk.android.phone.utils.UserUtil;
/**
 * 创建者：高楠
 * 类描述：选择学生
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ChoseStudentActivity extends BaseAppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView listView;
    private TextView select_all,select_none;
    private ChoseStudentAdapter adapter;
    private TextView more;
    private String job_id;
    private ArrayList<Integer> selections;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chose_studentlist;
    }

    @Override
    protected void toStart() {
        job_id = getIntent().getStringExtra("job_id");
        selections = getIntent().getIntegerArrayListExtra("data");
        listView = (ListView) findViewById(R.id.listview);
        select_none = (TextView) findViewById(R.id.select_none);
        select_all = (TextView) findViewById(R.id.select_all);
        select_none.setOnClickListener(this);
        select_all.setOnClickListener(this);
        findViewById(R.id.title_right).setVisibility(View.VISIBLE);
        more = (TextView) findViewById(R.id.more);
        more.setText("确定");
        showMyprogressDialog();
        new ChoseStudentListMiddle(this,this).getStudentList(UserUtil.getUerInfo(this).getUid(),job_id);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (adapter!=null){
            adapter.setItemSelected(position);
        }
    }

    @Override
    public void clickRight() {
        super.clickRight();
        selections = adapter.getSelections();
        if (selections!=null&&selections.size()>0){
            Intent intent = new Intent();
            intent.putIntegerArrayListExtra("data",selections);
            setResult(RESULT_OK,intent);
            finish();
        }else {
            UIUtils.showToastSafe("请选择学生!");
        }
    }

    @Override
    protected String setTitle() {
        return "写评语-选择学生";
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.select_all:
               if (adapter!=null){
                   adapter.selectAllItem();
               }
               break;
           case R.id.select_none:
               if (adapter!=null){
                   adapter.selectNoneItem();
               }
               break;
       }
    }

    @Override
    public void successFromMid(Object... obj) {
        super.successFromMid(obj);
        hideMyprogressDialog();
        int requestCode = (int) obj[0];
        if (requestCode==ChoseStudentListMiddle.CHOSE_STUDENT_LIST){
            ChoseStudentListBean data = (ChoseStudentListBean) obj[1];
            data.getData().setSelections(selections);
            adapter = new ChoseStudentAdapter(this,data);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
            if (selections==null||selections.size()==0){
                adapter.selectAllItem();
            }
        }
    }

    @Override
    public void failedFrom(Object... obj) {
        super.failedFrom(obj);
        hideMyprogressDialog();
    }
}
