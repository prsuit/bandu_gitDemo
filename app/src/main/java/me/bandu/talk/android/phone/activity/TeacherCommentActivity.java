package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.DFHT.utils.UIUtils;
import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.TeacherCommentAdapter;
import me.bandu.talk.android.phone.bean.ChoseStudentListBean;
import me.bandu.talk.android.phone.bean.ChoseStudentListEntity;
import me.bandu.talk.android.phone.bean.NameListSortBean;
import me.bandu.talk.android.phone.bean.WeakSentenceBean;
import me.bandu.talk.android.phone.dao.Comment;
import me.bandu.talk.android.phone.dao.DaoUtils;
import me.bandu.talk.android.phone.fragment.HomeworkSortFragment;
import me.bandu.talk.android.phone.middle.ChoseStudentListMiddle;
import me.bandu.talk.android.phone.middle.TeacherCommentMiddle;
import me.bandu.talk.android.phone.utils.UserUtil;
/**
 * 创建者：高楠
 * 类描述：教师评语
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TeacherCommentActivity extends BaseAppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, TextWatcher {
    private GridView gridView;
    private EditText edit_comment;
    private LinearLayout ll_good,ll_bad,flayout,ll_myself;
    private ImageView iv_good,iv_bad,iv_myself;
    private TextView tv_good,tv_bad,tv_myself,tv_selectionnum,tv_commit,text_num;
    private View line_good,line_bad,line_myself;
    private LinearLayout ll_select_student;
    private TeacherCommentAdapter adapter;
    private String job_id;
    private int stu_job_id = -1;
    private ArrayList<Integer> selections;
    private String custom_comment;

    private static final int TEACHER_COMMENT = 150;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_teacher_comment;
    }

    @Override
    protected void toStart() {
        GlobalParams.isUpdateSort = false;
        selections = new ArrayList<>();
        job_id = getIntent().getStringExtra("job_id");

        stu_job_id = getIntent().getIntExtra("stu_job_id",-1);
        if (stu_job_id!=-1){
            selections.add(stu_job_id);
        }
        gridView = (GridView) findViewById(R.id.gridview);
        ll_good = (LinearLayout) findViewById(R.id.linear_good);
        ll_myself = (LinearLayout) findViewById(R.id.linear_myselfe);
        flayout = (LinearLayout) findViewById(R.id.flayout);
        ll_bad = (LinearLayout) findViewById(R.id.linear_bad);
        iv_good = (ImageView) findViewById(R.id.imag_good);
        iv_bad = (ImageView) findViewById(R.id.imag_bad);
        iv_myself = (ImageView) findViewById(R.id.imag_myself);
        tv_bad = (TextView) findViewById(R.id.tv_bad);
        text_num = (TextView) findViewById(R.id.text_num);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        tv_good = (TextView) findViewById(R.id.tv_good);
        tv_myself = (TextView) findViewById(R.id.tv_myselfe);
        tv_selectionnum = (TextView) findViewById(R.id.tv_selectionnum);
        line_bad = findViewById(R.id.line_bad);
        line_good = findViewById(R.id.line_good);
        line_myself = findViewById(R.id.line_myself);
        edit_comment = (EditText) findViewById(R.id.edit_comment);
        ll_select_student = (LinearLayout) findViewById(R.id.select_student);
        adapter = new TeacherCommentAdapter(this);
        gridView.setAdapter(adapter);
        if (stu_job_id!=-1){
            tv_selectionnum.setText("已选1人");
        }else {
            tv_selectionnum.setText("全部");
        }
        setCardLight(0);
        addLestener();
    }
    private void addLestener(){
        tv_commit.setOnClickListener(this);
        ll_bad.setOnClickListener(this);
        flayout.setOnClickListener(this);
        ll_good.setOnClickListener(this);
        ll_select_student.setOnClickListener(this);
        ll_myself.setOnClickListener(this);
        gridView.setOnItemClickListener(this);
        edit_comment.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_commit:
                String str = edit_comment.getText().toString();
                if (str==null||str.equals("")){
                    UIUtils.showToastSafe("填入评语");
                }else {
                    if (custom_comment!=null&&!custom_comment.equals("")&&str.equals(custom_comment)){
                        Comment comment = new Comment();
                        comment.setComment(custom_comment);
                        comment.setName("自定义评语");
                        DaoUtils.getInstance().insertOrReplaceComment(comment);
                    }
                    if(selections.size()!=0){
                        showMyprogressDialog();
                        new TeacherCommentMiddle(this,this).comment(UserUtil.getUerInfo(this).getUid(),job_id,selections,adapter.getGoods(),adapter.getBads(),str);
                    }else{
                        Class clazz = HomeworkSortFragment.clazz;
                        if (clazz.getName().contains("WeakSentenceBean")) {
                            List<WeakSentenceBean.DataBean.ListBean> data = HomeworkSortFragment.dataList;
                            for (int i = 0; i < data.size(); i++) {
                                if (data.get(i).getStatus() != 1 && data.get(i).getStatus() != 0 && data.get(i).getEvaluated() != 1) { // 如果有未完成的
                                    selections.add(Integer.valueOf(data.get(i).getStu_job_id()));
                                }
                            }
                        } else {
                            List<NameListSortBean.DataBean.ListBean> data = HomeworkSortFragment.dataList;
                            for (int i = 0; i < data.size(); i++) {
                                if (data.get(i).getStatus() != 1 && data.get(i).getStatus() != 0 && data.get(i).getEvaluated() != 1) { // 如果有未完成的
                                    selections.add(Integer.valueOf(data.get(i).getStu_job_id()));
                                }
                            }
                        }

                        if (selections.size() == 0) {
                            UIUtils.showToastSafe("没有可评价的学生");
                        } else {
                            showMyprogressDialog();
                            new TeacherCommentMiddle(this,this).comment(UserUtil.getUerInfo(this).getUid(),job_id,selections,adapter.getGoods(),adapter.getBads(),str);
                        }

                    }

                }
                break;
            case R.id.flayout:
                UIUtils.goneKeyboard(this,flayout);
                break;
            case R.id.linear_myselfe:
                setCardLight(2);
                adapter.setMyselfComment();
                break;
            case R.id.linear_good:
                adapter.setGoodComment();
                setCardLight(0);
                break;
            case  R.id.linear_bad:
                setCardLight(1);
                adapter.setBadComment();
                break;
            case  R.id.select_student:
                Intent intent = new Intent(this,ChoseStudentActivity.class);
                intent.putExtra("job_id",job_id);
                intent.putIntegerArrayListExtra("data",selections);
                startActivityForResult(intent,TEACHER_COMMENT);
                break;
        }
    }
    private boolean input = true;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (adapter!=null){
            adapter.setItemSelect(position,edit_comment.getText().toString().length(),edit_comment.getText().toString().equals(custom_comment));
            input= false;
            edit_comment.setText(adapter.getSelections());
        }
    }
    public void setCardLight(int a){
        if (a==0){
            tv_good.setTextColor(getResources().getColor(R.color.red_line));
            iv_good.setImageResource(R.drawable.icon_good_click);
            line_good.setVisibility(View.VISIBLE);
            tv_bad.setTextColor(getResources().getColor(R.color.btn_blue));
            iv_bad.setImageResource(R.drawable.icon_bad_normal);
            line_bad.setVisibility(View.INVISIBLE);
            tv_myself.setTextColor(getResources().getColor(R.color.btn_blue));
            iv_myself.setImageResource(R.mipmap.icon_custom_comment_normal);
            line_myself.setVisibility(View.INVISIBLE);
        }else if (a==1){
            tv_good.setTextColor(getResources().getColor(R.color.btn_blue));
            iv_good.setImageResource(R.drawable.icon_good_normal);
            line_good.setVisibility(View.INVISIBLE);
            tv_bad.setTextColor(getResources().getColor(R.color.red_line));
            iv_bad.setImageResource(R.drawable.icon_bad_click);
            line_bad.setVisibility(View.VISIBLE);
            tv_myself.setTextColor(getResources().getColor(R.color.btn_blue));
            iv_myself.setImageResource(R.mipmap.icon_custom_comment_normal);
            line_myself.setVisibility(View.INVISIBLE);
        }else if (a==2){
            tv_good.setTextColor(getResources().getColor(R.color.btn_blue));
            iv_good.setImageResource(R.drawable.icon_good_normal);
            line_good.setVisibility(View.INVISIBLE);
            tv_bad.setTextColor(getResources().getColor(R.color.btn_blue));
            iv_bad.setImageResource(R.drawable.icon_bad_normal);
            line_bad.setVisibility(View.INVISIBLE);
            tv_myself.setTextColor(getResources().getColor(R.color.red_line));
            iv_myself.setImageResource(R.mipmap.icon_custom_comment_click);
            line_myself.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void successFromMid(Object... obj) {
        super.successFromMid(obj);
        hideMyprogressDialog();
        int requestCode = (int) obj[0];
        if (requestCode==TeacherCommentMiddle.TEACHER_COMMENT){
            UIUtils.showToastSafe("评价成功！");
            setResult(RESULT_OK);

            // 通知前台更新数据
            Intent i = new Intent("update_homework_sort");
            i.putExtra("status1", HomeworkSortFragment.fragment.getStatus());
            i.putIntegerArrayListExtra("selections",selections);
            sendBroadcast(i);
            finish();
        }

    }

    @Override
    public void failedFrom(Object... obj) {
        super.failedFrom(obj);
        hideMyprogressDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==TEACHER_COMMENT&&resultCode==RESULT_OK){
            selections = data.getIntegerArrayListExtra("data");
            if (selections!=null&&selections.size()==1){
                tv_selectionnum.setText("已选1人");
            }else if (selections!=null&&selections.size()>1){
                tv_selectionnum.setText("已选"+selections.size()+"人");
            }
        }
    }

    @Override
    protected String setTitle() {
        return UIUtils.getString(R.string.write_comment);
    }

    /**
     * TextWatcher监听器重写方法
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        Log.e("onTextChanged","s:"+s+" start:"+start+" before:"+before+" count:"+count);
        if (input){
            setCardLight(2);
            adapter.setMyselfComment();
            adapter.selectNone();
        }

    }

    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString();
        if (str.length()>50){
            UIUtils.showToastSafe("评语不能超过50个字！");
            edit_comment.removeTextChangedListener(this);
            str = str.substring(0,50);
            edit_comment.setText(str);
            edit_comment.addTextChangedListener(this);
        }
        if (input){
            custom_comment = str.toString();//每次自定义的赋值
        }
        input = true;
        text_num.setText(str.length()+"");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter!=null){
            adapter.destroy();
        }
    }
}
