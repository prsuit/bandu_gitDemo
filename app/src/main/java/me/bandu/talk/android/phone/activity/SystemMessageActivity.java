package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnItemClick;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.SysMsgListAdapter;
import me.bandu.talk.android.phone.bean.SysMsgBean;
import me.bandu.talk.android.phone.middle.SysMsgMiddle;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * 创建者：tg
 * 类描述：系统消息列表
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class SystemMessageActivity extends BaseAppCompatActivity {

    @Bind(R.id.lv)
    ListView listView;
    @Bind(R.id.title_tv)
    TextView titleTv;

    List<SysMsgBean.DataEntity.ListEntity> mList;
    SysMsgListAdapter adapter;
    View footView;
    int lastItem;
    int page=1;
    int size=10;
    int mPosition;
    RelativeLayout footLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_message;
    }

    @Override
    protected void toStart() {
        titleTv.setText("系统消息");
        footView = LayoutInflater.from(this).inflate(R.layout.xlistview_footer,null);
        footView.findViewById(R.id.xlistview_footer_progressbar).setVisibility(View.VISIBLE);
        footLayout = (RelativeLayout) footView.findViewById(R.id.xlistview_footer_content) ;
        listView.addFooterView(footView);
        mList = new ArrayList();
        adapter = new SysMsgListAdapter(this,mList);
        getData();
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener(){
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                lastItem = firstVisibleItem + visibleItemCount - 1 ;
            }
            public void onScrollStateChanged(AbsListView view,
                                             int scrollState) {
                if (lastItem == (adapter.getCount()) && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    footLayout.setVisibility(View.VISIBLE);
                    page++;
                    getData();
                }
            }
        });

    }
    private void getData(){
        new SysMsgMiddle(this,this).getSysMsg(UserUtil.getUerInfo(this).getUid(),page+"",size+"",new SysMsgBean());
    }

    @OnItemClick(R.id.lv)
    void itemClick(int position){
        SysMsgBean.DataEntity.ListEntity msg = mList.get(position);
        Intent intent = new Intent(this,SystemMsgDetailActivity.class);
        intent.putExtra("id",msg.getMsg_id());
        intent.putExtra("title",msg.getTitle());
        intent.putExtra("content",msg.getContent());
        intent.putExtra("time",msg.getCtime());
        intent.putExtra("read",msg.getIs_read());
        intent.putExtra("position",position);
        if (msg.getIs_read() == 0) {
            startActivityForResult(intent, 0);
        } else {
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        if (requestCode == SysMsgMiddle.SYS_MSG) {
            SysMsgBean bean = (SysMsgBean) result;
            if (bean != null && bean.getStatus() == 1) {
                List<SysMsgBean.DataEntity.ListEntity> list;
                list = bean.getData().getList();
                if (list.size() != 0) {
                    if (list.size() < 10) {
                        footLayout.setVisibility(View.GONE);
                    }
                    mList.addAll(list);
                    adapter.notifyDataSetChanged();
                } else {
                    footLayout.setVisibility(View.GONE);
                    UIUtils.showToastSafe(getString(R.string.no_data));
                }

            } else {
                UIUtils.showToastSafe(getString(R.string.no_data_now));
                footLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
        footLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            //mList.clear();
            mList.get(resultCode).setIs_read(1);
            if(adapter != null)
                adapter.notifyDataSetChanged();
            //getData();
        }
    }
}
