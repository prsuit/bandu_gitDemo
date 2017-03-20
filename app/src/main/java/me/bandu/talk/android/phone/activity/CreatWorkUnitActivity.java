package me.bandu.talk.android.phone.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.CreatWorkUnitAdapter;
import me.bandu.talk.android.phone.bean.BookInfoBean;
import me.bandu.talk.android.phone.bean.WorkDataBean;
import me.bandu.talk.android.phone.middle.CapterUnitMiddle;
import me.bandu.talk.android.phone.utils.ActivityManager;
import me.bandu.talk.android.phone.utils.BitmapUtil;
import me.bandu.talk.android.phone.view.AlertDialogUtils;

/**
 * 创建者：高楠
 * 时间：on 2016/3/29
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CreatWorkUnitActivity extends BaseAppCompatActivity implements AlertDialogUtils.DialogLestener, AdapterView.OnItemClickListener {
    @Bind(R.id.capter_recyclerview)
    ListView listView;
    @Bind(R.id.bookname)
    TextView tv_bookname;
    @Bind(R.id.capter_one_image)
    ImageView iv_cover;
    @Bind(R.id.addtextbook)
    LinearLayout addtextbook;
    private CreatWorkUnitAdapter adapter;
    private WorkDataBean dataBean;
    public static final int CREATWORK_CODE_OK = 160;
    public static final int ADDBOOK = 170;
    public static final int BINDBOOK = 180;
    private int type_code; // 教材的类型
    @Bind(R.id.iv_book_down)
    ImageView iv_book_down;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_capter_one;
    }

    @Override
    protected void toStart() {
        //方便重置作业与作业完成时的页面小时
        ActivityManager.getActivityManager().addActivity(this);

//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.out_text);
//        Bitmap bitmap = BitmapUtil.zoomImage(this,bmp,bmp.getWidth()/2,bmp.getHeight()/2);
//        iv_book_down.setImageBitmap(bitmap);

        dataBean = (WorkDataBean) getIntent().getSerializableExtra("data");
        if (dataBean!=null&&!dataBean.isBookIdEmpty()){
            getData();
        }else {
            addtextbook.setVisibility(View.VISIBLE);
        }
        listView.setOnItemClickListener(this);

    }

    /**
     * 获取数据
     */
    public void getData(){
        showMyprogressDialog();
        new CapterUnitMiddle(this,this).getBookInfo(dataBean.getBook_id(),new BookInfoBean());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, CreatWorkLessonActivity.class);
        dataBean.setUnitid(adapter.getUnit_id(position));
        dataBean.setUnitname(adapter.getUnit_name(position));
        intent.putExtra("data",dataBean);
        intent.putExtra("type_code",type_code);
        startActivityForResult(intent, CreatWorkUnitActivity.CREATWORK_CODE_OK);
    }

    @OnClick({R.id.bindtextbook,R.id.iv_addtextbook})
    void OnClick(View view){
        switch (view.getId()){
            case R.id.bindtextbook:
                Intent intent = new Intent(this,SelectGradeActivity.class);
                intent.putExtra("classid",dataBean.getCid());
                startActivityForResult(intent,BINDBOOK);
                break;
            case R.id.iv_addtextbook:
                Intent intent1 = new Intent(this,SelectGradeActivity.class);
                intent1.putExtra("classid",dataBean.getCid());
                startActivityForResult(intent1,ADDBOOK);
                break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ADDBOOK&&resultCode==RESULT_OK){
            addtextbook.setVisibility(View.GONE);
            dataBean.setBook_id(data.getIntExtra("bookid",-1)+"");
            GlobalParams.HOME_CHANGED = true;
            getData();
        }else if (requestCode==BINDBOOK&&resultCode==RESULT_OK){
            dataBean.setBook_id(data.getIntExtra("bookid",-1)+"");
            GlobalParams.HOME_CHANGED = true;
            getData();
        }else if (requestCode==CREATWORK_CODE_OK&&resultCode==RESULT_OK){
            WorkDataBean dataresult = (WorkDataBean) data.getSerializableExtra("data");
            if (dataresult.getSTATUS()==WorkDataBean.RESET){
                dataBean.restWork();
            }else if (dataresult.getSTATUS()==WorkDataBean.ADDING){
                dataBean = dataresult;
                adapter.setDataBean(dataBean);
            }
        }


    }
    @Override
    public void successFromMid(Object... obj) {
        super.successFromMid(obj);
        hideMyprogressDialog();
        int requestCode = (int) obj[1];
        Object result = obj[0];
        if (requestCode== CapterUnitMiddle.BOOKINFO_CODE){
            BookInfoBean info = (BookInfoBean) result;
            setViewData(info);
        }
    }

    @Override
    public void failedFrom(Object... obj) {
        super.failedFrom(obj);
        hideMyprogressDialog();
    }
    //设置数据显示
    private void setViewData(BookInfoBean info) {
        if (info!=null&&info.getData()!=null){
            adapter = new CreatWorkUnitAdapter(this,info.getData().getUnit_list(),dataBean);
            listView.setAdapter(adapter);
            tv_bookname.setText(info.getData().getBook_name());
            if(info.getData().getStatus()==0){
                //说明教材已经下架
                iv_book_down.setVisibility(View.VISIBLE);
            } else {
                iv_book_down.setVisibility(View.GONE);
            }
            type_code = info.getData().getType_code(); //教材的类型：0是普通类型，2是新题型
            // 解决布置作业默认教材图片的oom
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.book_cover_defaut);
            Bitmap bitmap = BitmapUtil.zoomImage(this,bmp,bmp.getWidth()/2,bmp.getHeight()/2);
            iv_cover.setImageBitmap(bitmap);

            ImageLoader.getInstance().displayImage(info.getData().getCover(),iv_cover);
        }
    }

    @Nullable
    @Override
    public void goback(View view) {
        AlertDialogUtils.getInstance().init(this,"放弃布置作业？","点击“确定”后，已选择的作业内容将不做保存。",this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            AlertDialogUtils.getInstance().init(this,"放弃布置作业？","点击“确定”后，已选择的作业内容将不做保存。",this);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected String setTitle() {
        return getString(R.string.select_content);
    }

    /**
     * dialog 按钮监听器
     */
    @Override
    public void ok() {
        finish();
    }

    @Override
    public void cancel() {

    }


}
