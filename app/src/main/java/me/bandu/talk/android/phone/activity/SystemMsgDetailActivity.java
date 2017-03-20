package me.bandu.talk.android.phone.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.DFHT.base.BaseBean;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.middle.ReadMsgMiddle;
/**
 * 创建者：tg
 * 类描述：系统消息详情
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class SystemMsgDetailActivity extends BaseAppCompatActivity {

    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.title)
    TextView titleTV;
    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.content_tv)
    TextView contentTv;
    @Bind(R.id.goback)
    ImageView backImg;

    String id;
    String title;
    String content;
    String time;
    int mPosition;
    int isRead;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_msg_detail;
    }

    @Override
    protected void toStart() {
        title_tv.setText("系统消息");
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        time = getIntent().getStringExtra("time");
        isRead = getIntent().getIntExtra("read",0);
        mPosition = getIntent().getIntExtra("position",0);
        titleTV.setText(title);
        contentTv.setText(content);
        timeTv.setText(time);
        if (isRead == 0) {
            readMsg();
        }
    }

    @OnClick(R.id.goback)
    void click(View v){
        finish();
    }

    public void readMsg(){
        new ReadMsgMiddle(this,this).readMsg(GlobalParams.userInfo.getUid(),id,new BaseBean());
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        super.onSuccess(result, requestCode);
        setResult(mPosition);
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
    }

}
