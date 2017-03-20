//package me.bandu.talk.android.phone.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//
//import java.util.List;
//
//import butterknife.Bind;
//import butterknife.OnClick;
//import me.bandu.talk.android.phone.App;
//import me.bandu.talk.android.phone.GlobalParams;
//import me.bandu.talk.android.phone.R;
//import me.bandu.talk.android.phone.adapter.CapterUnitAdapter;
//import me.bandu.talk.android.phone.bean.BookInfoBean;
//import me.bandu.talk.android.phone.bean.BookInfoEntity;
//import me.bandu.talk.android.phone.middle.CapterUnitMiddle;
//import me.bandu.talk.android.phone.utils.ActivityManager;
//
///**
// * 创建者：高楠
// * 时间：2015/11/23
// * 类描述：选择教材第一步
// * 修改人：
// * 修改时间：
// * 修改备注：
// */
//
//public class SelectCapterUnitActivity extends BaseAppCompatActivity  {
//    @Bind(R.id.capter_recyclerview)
//    ListView recyclerView;
//    @Bind(R.id.bookname)
//    TextView tv_bookname;
//    @Bind(R.id.capter_one_image)
//    ImageView iv_cover;
//    @Bind(R.id.addtextbook)
//    LinearLayout addtextbook;
//    private CapterUnitAdapter adapter;
//    private String bookID ;
//    private Bundle data;
//    private String classid;
//    public static final int CREATWORK_CODE_OK = 160;
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_select_capter_one;
//    }
//
//    /**
//     * 获取网络数据
//     */
//    public void getData(){
//        ActivityManager.getActivityManager().addActivity(this);
//        if (data==null){
//            data = getIntent().getBundleExtra("data");
//            bookID = data.getString("book_id");
//            if (bookID==null||(bookID!=null&&bookID.equals(""))||(bookID!=null&&bookID.equals("0"))){
//                addtextbook.setVisibility(View.VISIBLE);
//            }
//            classid = data.getString("cid");
//        }
//        new CapterUnitMiddle(this,this).getBookInfo(bookID,new BookInfoBean());
//    }
//
//    @Override
//    protected void toStart() {
//        getData();
//    }
//    public void initRecyclerView(List<BookInfoEntity> list){
//        adapter = new CapterUnitAdapter(this,list,data);
//        recyclerView.setAdapter(adapter);
//    }
//    public void setBookInfo(BookInfoBean info){
//        tv_bookname.setText(info.getData().getBook_name());
//        ImageLoader.getInstance().displayImage(info.getData().getCover(),iv_cover,new DisplayImageOptions.Builder().build());
//    }
//
//    @Override
//    public void successFromMid(Object... obj) {
//        super.successFromMid(obj);
//        int requestCode = (int) obj[1];
//        Object result = obj[0];
//        if (requestCode== CapterUnitMiddle.BOOKINFO_CODE){
//            BookInfoBean info = (BookInfoBean) result;
//            initRecyclerView(info.getData().getUnit_list());
//            setBookInfo(info);
//        }
//    }
//    @OnClick({R.id.bindtextbook,R.id.iv_addtextbook})
//    void OnClick(View view){
//        switch (view.getId()){
//            case R.id.bindtextbook:
//                Intent intent = new Intent(this,SelectGradeActivity.class);
//                intent.putExtra("classid",classid);
//                startActivityForResult(intent,1);
//            break;
//            case R.id.iv_addtextbook:
//                Intent intent1 = new Intent(this,SelectGradeActivity.class);
//                intent1.putExtra("classid",classid);
//                startActivityForResult(intent1,2);
//            break;
//
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==1&&resultCode==RESULT_OK){
//            bookID = data.getIntExtra("bookid",-1)+"";
//            GlobalParams.HOME_CHANGED = true;
//            toStart();
//        }
//        if (requestCode==2&&resultCode==RESULT_OK){
//            bookID = data.getIntExtra("bookid",-1)+"";
//            addtextbook.setVisibility(View.GONE);
//            GlobalParams.HOME_CHANGED = true;
//            toStart();
//        }
//    }
//
//    @Override
//    public void failedFrom(Object... obj) {
//        super.failedFrom(obj);
//    }
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
////                return  R.layout.activity_select_capter_one;
////            }
////
////            @Override
////            protected View createEmptyView() {
////                View empty = UIUtils.inflate(R.layout.empty_layout);
////                empty.findViewById(R.id.refresh).setOnClickListener(new OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                    load();
////                    }
////                });
////                return empty;
////            }
////
////            @Override
////            protected void load() {
////                getData();
////            }
////        };
////        view.show();
////        return view;
////    }
//
