//package me.bandu.talk.android.phone.activity;
//
//import android.os.Bundle;
//import android.widget.ListView;
//import com.DFHT.ui.LoadPageView;
//import butterknife.Bind;
//import me.bandu.talk.android.phone.R;
//import me.bandu.talk.android.phone.adapter.CapterLessonAdapter;
//import me.bandu.talk.android.phone.bean.UnitInfoBean;
//import me.bandu.talk.android.phone.middle.CapterLessonMiddle;
//import me.bandu.talk.android.phone.utils.ActivityManager;
//
///**
// * 创建者：高楠
// * 时间：2015/11/23
// * 类描述：选择教材第二部
// * 修改人：
// * 修改时间：
// * 修改备注：
// */
//
//public class SelectCapterLessonActivity extends BaseAppCompatActivity  {
//    @Bind(R.id.capter_recyclerview)
//    ListView recyclerView;
//    CapterLessonAdapter adapter;
//    String unit_id;
//    Bundle bundle;
//    private LoadPageView view;
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_select_capter_two;
//    }
//
//    @Override
//    protected void toStart() {
//        ActivityManager.getActivityManager().addActivity(this);
//        if (bundle==null){
//            bundle = getIntent().getBundleExtra("data");
//            unit_id = bundle.getString("unit_id");
//        }
//        new CapterLessonMiddle(this,this).getUnitInfo(unit_id,new UnitInfoBean());
//    }
//    public void initRecyclerView(UnitInfoBean info){
////        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new CapterLessonAdapter(this,info.getData().getLesson_list(),bundle);
//        recyclerView.setAdapter(adapter);
//    }
//
//    @Override
//    public void successFromMid(Object... obj) {
//        super.successFromMid(obj);
//        int requestCode = (int) obj[1];
//        Object result = obj[0];
//        if (requestCode== CapterLessonMiddle.UNIT_INFO){
//            UnitInfoBean info = (UnitInfoBean) result;
//            initRecyclerView(info);
//        }
//
//    }
//
//    @Override
//    public void failedFrom(Object... obj) {
//        super.failedFrom(obj);
//    }
//
//
//    @Override
//    public void failed(int requestCode) {
//        super.failed(requestCode);
//    }
//
//    @Override
//    protected String setTitle() {
//        return getString(R.string.select_content);
//    }
//
//}
////    @Override
////    protected int getLayoutId() {
////        return -1;
////    }
////    @Override
////    protected View getLayoutView() {
////        view = new LoadPageView(this) {
////            @Override
////            protected int createSuccessView() {
////                return  R.layout.activity_select_capter_two;
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
////                getData();
////            }
////        };
////        view.show();
////        return view;
////    }
////    public void getData(){
////        if (bundle==null){
////            bundle = getIntent().getBundleExtra("data");
////            unit_id = bundle.getString("unit_id");
////        }
////        new CapterLessonMiddle(this,this).getUnitInfo(unit_id,new UnitInfoData());
////    }