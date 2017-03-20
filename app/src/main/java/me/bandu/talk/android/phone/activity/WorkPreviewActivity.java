//package me.bandu.talk.android.phone.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.TextView;
//import com.DFHT.base.BaseBean;
//import com.DFHT.utils.UIUtils;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import butterknife.Bind;
//import butterknife.OnClick;
//import me.bandu.talk.android.phone.R;
//import me.bandu.talk.android.phone.adapter.WorkPreviewAdapter;
//import me.bandu.talk.android.phone.bean.ClassStudentEntity;
//import me.bandu.talk.android.phone.bean.Quiz;
//import me.bandu.talk.android.phone.manager.FullyGridLayoutManager;
//import me.bandu.talk.android.phone.middle.CreatWorkMiddle;
//import me.bandu.talk.android.phone.utils.ActivityManager;
//import me.bandu.talk.android.phone.utils.PopUtils;
//
///**
// * 创建者：高楠
// * 时间：2015/11/25
// * 类描述：作业预览页
// * 修改人：
// * 修改时间：
// * 修改备注：
// */
//
//public class WorkPreviewActivity extends BaseAppCompatActivity implements CompoundButton.OnCheckedChangeListener,PopUtils.OnPopDismissListener {
//    @Bind(R.id.work_preview_recyclerview)
//    RecyclerView recyclerView;
//    @Bind(R.id.read_tv)
//    TextView tv_read;
//    @Bind(R.id.repead_tv)
//    TextView tv_repead;
//    @Bind(R.id.finishtime)
//    TextView tv_time;
//    @Bind(R.id.studentlist)
//    TextView tv_student;
//    @Bind(R.id.finishlevel)
//    TextView tv_level;
//    @Bind(R.id.checkbox_recite)
//    CheckBox cb_recite;
//    @Bind(R.id.editext)
//    EditText editext;
//    public static final int STUDENTLIST_CODE = 40;
//    public static final int ADD_QUIZ = 10;
//    private WorkPreviewAdapter adapter;
//    private Bundle data;
//    private List<ClassStudentEntity> selects;
//    private ArrayList<String> uids;
//    private String deadline;
//    private String score_level = "0";
//    private String cid;
//    private String uid;
//    private String bookid;
//    private String title;
//    private String description;
//    private String year;
//    private String month;
//    private String day;
//    private String class_name;
//    private boolean isCreatWork = false;
//
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_work_preview;
//    }
//
//    @Override
//    protected void toStart() {
//        initData();
//    }
//
//    private void initData() {
//        findViewById(R.id.goback).setVisibility(View.INVISIBLE);
////        tv_time.setText(PopUtils.getInstance().getTimeText());
////        deadline = PopUtils.getInstance().getTimeLong();
////        cb_recite.setOnCheckedChangeListener(this);
//        data = getIntent().getBundleExtra("data");
//        cid = data.getString("cid");
//        uid = data.getString("uid");
//        bookid = data.getString("book_id");
//        class_name = data.getString("class_name");
//        tv_student.setText(class_name+"全体成员");
//        title = data.getString("lesson_name");
//        Quiz quiz = new Quiz();
//        quiz.setQuiz_id(data.getString("quiz_id"));
//        quiz.setQuiz_name(data.getString("quiz_name"));
//        setDefault(quiz.getRead_count(), quiz.getRepeat_count(), quiz.isRecit());
//        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,4);
//        recyclerView.setLayoutManager(manager);
//        adapter = new WorkPreviewAdapter(this, quiz);
//        recyclerView.setAdapter(adapter);
//    }
//    @OnClick({R.id.work_reset,R.id.read_minus,R.id.read_plus,R.id.repead_plus,R.id.repead_minus,R.id.linear_finishtime,R.id.linear_finishlevel,R.id.creatwork,R.id.work_preview_plus,R.id.linear_studentlist})
//    void Click(View view){
//        switch (view.getId()){
////            case R.id.work_reset:
////                Intent ii = new Intent(this, SelectCapterUnitActivity.class);
////                ii.putExtra("data",data);
////                startActivity(ii);
////                ActivityManager.getActivityManager().removeActivity();
////                finish();
////                break;
////            case R.id.read_minus:
////                try {
////                    int times = Integer.parseInt(tv_read.getText().toString());
////                    if (times>0){
////                        times--;
////                        tv_read.setText(times+"");
////                        adapter.setRead(times);
////                    }
////                }catch (Exception e){
////                    e.printStackTrace();
////                }
////                break;
////            case R.id.read_plus:
////                try {
////                    int times = Integer.parseInt(tv_read.getText().toString());
////                    times++;
////                    tv_read.setText(times+"");
////                    adapter.setRead(times);
////                }catch (Exception e){
////                    e.printStackTrace();
////                }
////                break;
////            case R.id.repead_plus:
////                try {
////                    int times = Integer.parseInt(tv_repead.getText().toString());
////                    times++;
////                    tv_repead.setText(times+"");
////                    adapter.setRepead(times);
////                }catch (Exception e){
////                    e.printStackTrace();
////                }
////                break;
////            case R.id.repead_minus:
////                try {
////                    int times = Integer.parseInt(tv_repead.getText().toString());
////                    if (times>0){
////                        times--;
////                        tv_repead.setText(times+"");
////                        adapter.setRepead(times);
////                    }
////                }catch (Exception e){
////                    e.printStackTrace();
////                }
////                break;
////            case R.id.linear_finishtime:
////                PopUtils.getInstance().showTimePopou(this, findViewById(R.id.bottom), this);
////                PopUtils.getInstance().setDate(year,month,day);
////                break;
////            case R.id.linear_finishlevel:
////                PopUtils.getInstance().showPopDescription(this, findViewById(R.id.bottom), tv_level.getText().toString(), this);
////            break;
////            case R.id.linear_studentlist:
////                Intent intent_setudent = new Intent(this, SelectStudentActivity.class);
////                intent_setudent.putExtra("selected", (Serializable) selects);
////                intent_setudent.putExtra("cid", cid);
////                intent_setudent.putExtra("uid", uid);
////                intent_setudent.putExtra("class_name", class_name);
////                startActivityForResult(intent_setudent,STUDENTLIST_CODE);
////            break;
//            case R.id.creatwork:
//                if (isCreatWork){
//
//                }else {
//                    isCreatWork = true;
//                    List<Map<String,String>> mapList = adapter.getQuizs();
//                    if (mapList!=null&&mapList.size()==1&&adapter.isOK()){
//                        description = editext.getText().toString();
////                        new CreatWorkMiddle(this,this).creatWork(uid,cid,bookid,getSelect(selects),title,score_level,description,deadline,adapter.getQuizs());//TODO
//                    }else if (mapList!=null&&mapList.size()>1){
//                        description = editext.getText().toString();
////                        new CreatWorkMiddle(this,this).creatWork(uid,cid,bookid,getSelect(selects),title,score_level,description,deadline,adapter.getQuizs());//TODO
//                    } else {
//                        isCreatWork = false;
//                        UIUtils.showToastSafe("请选择要布置的作业");
//                    }
//                }
//            break;
//            case R.id.work_preview_plus:
//                if (adapter.isOK()){
//                    Intent intent = new Intent(this,SelectCapterQuizActivity.class);
//                    data.putBoolean("add",true);
//                    data.putStringArrayList("selected",adapter.getSelectedQuizs());
//                    intent.putExtra("data",data);
//                    startActivityForResult(intent,ADD_QUIZ);
//                }else {
//                    UIUtils.showToastSafe("请设置跟读、朗读遍数或选中熟背课文");
//                }
//                break;
//        }
//    }
//
////    private ArrayList<String> getSelect(List<ClassStudentEntity> selects) {
////        uids = new ArrayList<>();
////        if (selects==null||(selects!=null&&selects.size()==0)){
////            return null;
////        }
////        for (int i = 0;i<selects.size();i++){
////            if (selects.get(i).isSelected()){
////                uids.add(selects.get(i).getUid());
////            }
////        }
////        return uids;
////    }
//
//    @Override
//    public void onCommit(String result, int l) {
////        tv_level.setText(result);
////        this.score_level = l+"";
//    }
//
//    @Override
//    public void onCommit(String year, String month, String day) {
////        this.year = year;
////        this.month = month;
////        this.day = day;
////        deadline = year+"-"+month+"-"+day+" 23:59:59";
////        tv_time.setText(PopUtils.getInstance().getTime(year,month,day,0));
//    }
//
//
//    @Override
//    public void onCancel() {
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==ADD_QUIZ&&resultCode==RESULT_OK){
//            Quiz quiz = new Quiz();
//            quiz.setQuiz_name(data.getStringExtra("quiz_name"));
//            quiz.setQuiz_id(data.getStringExtra("quiz_id"));
//            adapter.AddQuiz(quiz);
//            setDefault(adapter.getRead(),adapter.getRepead(),adapter.getRecite());
//            return;
//        }
////        if (requestCode==STUDENTLIST_CODE&&resultCode==RESULT_OK){
////            selects = (List<ClassStudentEntity>) data.getSerializableExtra("list");
////            getSelect(selects);
////            int total = data.getIntExtra("total",0);
////            if (total!=0&&uids!=null&&uids.size()!=0){
////                if (total==uids.size()){
////                    tv_student.setText("默认全选");
////                }else {
////                    tv_student.setText("已选："+uids.size()+"/"+total);
////                }
////            }else {
////                tv_student.setText("默认全选");
////            }
////        }
//    }
//    public void setDefault(int read,int repead,boolean recite){
//        tv_read.setText(read+"");
//        tv_repead.setText(repead+"");
//        cb_recite.setChecked(recite);
//    }
//
//    @Override
//    public void successFromMid(Object... obj) {
//        super.successFromMid(obj);
//        int requestcode = (int) obj[1];
//        Object result = obj[0];
//        if (requestcode==CreatWorkMiddle.CREATWORK){
//            BaseBean baseBean = (BaseBean) result;
//            if (baseBean.getStatus()==1){
//                isCreatWork = false;
//                UIUtils.showToastSafe("提交成功");
////                setResult(RESULT_OK);
//                ActivityManager.getActivityManager().removeActivity();
//                finish();
//            }
//        }
//    }
//
//    @Override
//    public void failedFrom(Object... obj) {
//        isCreatWork = false;
//        super.failedFrom(obj);
//    }
//
//    @Override
//    protected String setTitle() {
//        return "作业预览";
//    }
//
//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        adapter.setRecite(isChecked);
//    }
//}
