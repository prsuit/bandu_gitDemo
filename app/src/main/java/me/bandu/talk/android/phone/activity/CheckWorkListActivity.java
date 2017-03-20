package me.bandu.talk.android.phone.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.utils.UIUtils;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.CheckWorkListAdapter1;
import me.bandu.talk.android.phone.bean.CheckWorkBean1;
import me.bandu.talk.android.phone.middle.CheckWorkMiddle;
import me.bandu.talk.android.phone.middle.DeletWorkMiddle;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.view.widget.SwipeMenu;
import me.bandu.talk.android.phone.view.widget.SwipeMenuCreator;
import me.bandu.talk.android.phone.view.widget.SwipeMenuItem;
import me.bandu.talk.android.phone.view.widget.SwipeMenuListView;

/**
 * 创建者:taoge
 * 时间：2015/11/23 10:21
 * 类描述：检查作业
 * 修改人:taoge
 * 修改时间：2015/11/23 10:21
 * 修改备注：
 */
public class CheckWorkListActivity extends BaseAppCompatActivity implements View.OnClickListener {

    @Bind(R.id.listView)
    SwipeMenuListView listView;
    @Bind(R.id.title_tv)
    TextView tv;
    @Bind(R.id.go_back)
    TextView go_back;
    RelativeLayout footLayout;
    String cid;
    CheckWorkListAdapter1 adapter;
    List<CheckWorkBean1.DataBean.ListBean> mList;
    View footView;
    int lastItem;//最后一条的位置
    int page = 1;//起始页面
    int size = 10;//一页显示的数量
    int deletPosition;//要删除的条目位置
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ImageView goback;
    private List<CheckWorkBean1.DataBean.ListBean> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_work_list;
    }

    @Override
    protected void toStart() {
        showMyprogressDialog();
        tv.setText(R.string.check_work);
        goback = (ImageView) findViewById(R.id.goback);
        this.cid = getIntent().getStringExtra("cid");
        footView = LayoutInflater.from(this).inflate(R.layout.xlistview_footer, null);
        footView.findViewById(R.id.xlistview_footer_progressbar).setVisibility(View.VISIBLE);
        footLayout = (RelativeLayout) footView.findViewById(R.id.xlistview_footer_content);
        listView.addFooterView(footView);
        mList = new ArrayList();

        adapter = new CheckWorkListAdapter1(this, mList);
        initData();
        goback.setOnClickListener(this);
        go_back.setOnClickListener(this);
        listView.setAdapter(adapter);
        listView.setMenuCreator(creator);
        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        deletPosition = position;
//                        CheckWorkBean.DataEntity.ListEntity work = mList.get(position);
                        CheckWorkBean1.DataBean.ListBean work = mList.get(position);
//                        if (Long.parseLong(work.getDeadline()) * 1000 <= System.currentTimeMillis()) {//已过期作业不能撤消
                        if (work.getIs_over() == 1) {
                            //已结束
                            deletConfirm("", "该作业已过期，不能撤消！");
//                        } else if (work.getCount() == work.getUn_ci_count()) {
                        } else if (work.getDone_total() != 0) {
                            //未结束
                            deletConfirm(mList.get(position).getJob_id() + "", "现在已有" + work.getDone_total() + "名同学完成作业，请慎重撤销！");
                        } else {
                            deletConfirm(mList.get(position).getJob_id() + "", "现在还没学生完成作业哦，想撤就撤吧~");
                        }
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                lastItem = firstVisibleItem + visibleItemCount - 1;
            }

            public void onScrollStateChanged(AbsListView view,
                                             int scrollState) {
                if (lastItem == (adapter.getCount()) && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    footLayout.setVisibility(View.VISIBLE);
                    page++;
                    initData();
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean isOut = false; //是否过期
                /*GlobalParams.IS_CHECKED = true;
                long deadline = Long.parseLong(mList.get(position).getDeadline());
                if ((deadline * 1000 - System.currentTimeMillis()) > 0) {
                    isOut = false;
                } else {
                    isOut = true;
                }*/
                if (mList.get(position).getIs_over() == 1) {
                    isOut = true;
                    //过期
                } else if (mList.get(position).getIs_over() == 0) {
                    //未过期
                    isOut = false;
                }
                //检查作业
//              Intent intent = new Intent(CheckWorkListActivity.this,SeeWorkActivity.class);
                Intent intent = new Intent(CheckWorkListActivity.this, WorkStatisticsActivity.class);
                intent.putExtra("over_time", mList.get(position).getDeadline());
                intent.putExtra("jobId", mList.get(position).getJob_id() + "");
                intent.putExtra("isOut", isOut);
                intent.putExtra("cid", CheckWorkListActivity.this.cid);
                intent.putExtra("isChecked",position);
                startActivity(intent);
            }
        });
    }

    public void deletConfirm(final String id, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setMessage(msg);
        builder.setTitle(R.string.notice);
        builder.setPositiveButton(R.string.make_sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!"".equals(id)) {
                    delet(id);
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void delet(String jobId) {
        showMyprogressDialog();
        new DeletWorkMiddle(this, this).deletWork(GlobalParams.userInfo.getUid() + "", cid, jobId, new BaseBean());
    }

    public void initData() {
        showMyprogressDialog();
        new CheckWorkMiddle(this, this).checkWork(UserUtil.getUid(), cid, page + "", size + "", new CheckWorkBean1());
    }

    ArrayList<Integer> positionList = new ArrayList<Integer>();
    @Override
    public void onSuccess(Object result, int requestCode) {
        hideMyprogressDialog();
        switch (requestCode) {
            case CheckWorkMiddle.CHECK_WORK:
                CheckWorkBean1 data = (CheckWorkBean1) result;
                if (data != null && data.getStatus() == 1) {
                    list = data.getData().getList();
                    if (list.size() != 0) {
                        if (list.size() < 10) {
                            footLayout.setVisibility(View.GONE);
                        }
                        mList.addAll(list);
                        setMyPositionList(mList);
                    } else {
                        footLayout.setVisibility(View.GONE);
                        UIUtils.showToastSafe(getString(R.string.no_data));
                    }
                } else {
                    UIUtils.showToastSafe(getString(R.string.no_data_now));
                    footLayout.setVisibility(View.GONE);
                }

                adapter.notifyDataSetChanged();
                break;
            case DeletWorkMiddle.DELET_WORK:
                BaseBean bean = (BaseBean) result;
                if (bean != null && bean.getStatus() == 1) {
                    mList.remove(deletPosition);
                    setMyPositionList(mList);
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
    }


    SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {
            SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
            deleteItem.setWidth(dp2px(90));
            deleteItem.setBackground(new ColorDrawable(Color.rgb(255, 33, 0)));
            deleteItem.setTitleSize(20);
            deleteItem.setTitleColor(Color.WHITE);
            deleteItem.setTitle(getString(R.string.cancel1));
            menu.addMenuItem(deleteItem);
        }
    };


    public int dp2px(float dp) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!GlobalParams.IS_CHECKED) {
            //GlobalParams.IS_CHECKED = true;
        } else {
            mList.clear();
            if (adapter != null)
                adapter.notifyDataSetChanged();
            page = 1;
            initData();
            GlobalParams.IS_CHECKED = false;
        }
        if(GlobalParams.WORK_CHANGED != -1) {
            mList.get(GlobalParams.WORK_CHANGED).setIs_unchecked(0);
            GlobalParams.WORK_CHANGED = -1;
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalParams.IS_CHECKED = false;
    }

    public Context getContext() {
        return this;
    }



    @Override
    public void onClick(View v) {
        startActivity(new Intent(this,ClassStatisticsActivity.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this,ClassStatisticsActivity.class));
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setMyPositionList(List<CheckWorkBean1.DataBean.ListBean> mList) {
        if(mList!=null&&mList.size()>0){
            positionList.clear();
            for(int x = 0;x<mList.size();x++){
                int is_over1 = mList.get(x).getIs_over();
                if(is_over1 == 1){
                    positionList.add(x);
                }
            }
            listView.setPositionList(positionList);
        }
    }
}
