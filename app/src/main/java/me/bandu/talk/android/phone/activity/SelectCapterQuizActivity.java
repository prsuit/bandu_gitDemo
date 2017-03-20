//package me.bandu.talk.android.phone.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.Bind;
//import me.bandu.talk.android.phone.R;
//import me.bandu.talk.android.phone.adapter.CapterQuizAdapter;
//import me.bandu.talk.android.phone.bean.LessonInfoBean;
//import me.bandu.talk.android.phone.bean.LessonInfoEntity;
//import me.bandu.talk.android.phone.middle.CapterQuizMiddle;
//import me.bandu.talk.android.phone.utils.ActivityManager;
//
///**
// * 创建者：高楠
// * 时间：2015/11/23
// * 类描述：选择教材第三步
// * 修改人：
// * 修改时间：
// * 修改备注：
// */
//
//public class SelectCapterQuizActivity extends BaseAppCompatActivity  {
//    @Bind(R.id.capter_recyclerview)
//    ListView recyclerView;
//    CapterQuizAdapter adapter;
//    private String lesson_id;
////    private String unit_name;
//    private Bundle bundle;
//    private boolean isAdd = false;
//    private ArrayList<String> stringArrayList ;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_select_capter_three;
//    }
//    @Override
//    protected void toStart() {
//        ActivityManager.getActivityManager().addActivity(this);
//        bundle = getIntent().getBundleExtra("data");
//        lesson_id = bundle.getString("lesson_id");
////        unit_name = bundle.getString("unit_name");
////        if (unit_name!=null||!unit_name.equals("")){
////            title_tv.setText(unit_name);
////        }
//        isAdd = bundle.getBoolean("add");
//        stringArrayList = bundle.getStringArrayList("selected");
//        if (isAdd){
//            findViewById(R.id.title_right).setVisibility(View.VISIBLE);
//            TextView tv = (TextView) findViewById(R.id.more);
//            tv.setText(R.string.preview);
//        }
//        new CapterQuizMiddle(this,this).getLessonInfo(lesson_id,new LessonInfoBean());
//    }
//
//    public void initRecyclerView(List<LessonInfoEntity> list){
//        adapter = new CapterQuizAdapter(this,list,bundle,isAdd,stringArrayList);
//        recyclerView.setAdapter(adapter);
//    }
//
//    @Override
//    public void successFromMid(Object... obj) {
//        super.successFromMid(obj);
//        int requestCode = (int) obj[1];
//        Object result = obj[0];
//        if (requestCode == CapterQuizMiddle.LESSON_INFO){
//            LessonInfoBean info = (LessonInfoBean) result;
//            initRecyclerView(info.getData().getQuiz_list());
//        }
//    }
//
//    @Override
//    public void failedFrom(Object... obj) {
//        super.failedFrom(obj);
//    }
//
//    @Override
//    protected String setTitle() {
//        return getString(R.string.select_content);
//    }
//
//    @Override
//    public void clickRight() {
//        super.clickRight();
//        setResult(RESULT_CANCELED);
//        finish();
//    }
//}
////    @Override
////    protected View getLayoutView() {
////        view = new LoadPageView(this) {
////            @Override
////            protected int createSuccessView() {
////                return  R.layout.activity_select_capter_three;
////            }
////            @Override
////            protected View createEmptyView() {
////                View empty = UIUtils.inflate(R.layout.empty_layout);
////                empty.findViewById(R.id.refresh).setOnClickListener(new OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        load();
////                    }
////                });
////                return empty;
////            }
////            @Override
////            protected void load() {
////               getDate();
////            }
////        };
////        view.show();
////        return view;
////    }
////
////    private void getDate(){
////        bundle = getIntent().getBundleExtra("data");
////        lesson_id = bundle.getString("lesson_id");
////        isAdd = bundle.getBoolean("add");
////        new CapterQuizMiddle(this,this).getLessonInfo(lesson_id,new LessonInfoData());
////    }