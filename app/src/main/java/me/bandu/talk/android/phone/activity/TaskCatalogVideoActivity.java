package me.bandu.talk.android.phone.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.net.NetworkUtil;
import com.DFHT.utils.UIUtils;

import java.util.ArrayList;

import butterknife.Bind;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.TaskCatalogVideoAdapter;
import me.bandu.talk.android.phone.bean.MWordBean;
import me.bandu.talk.android.phone.bean.TaskCatalogVideoBean;
import me.bandu.talk.android.phone.bean.WordBean;
import me.bandu.talk.android.phone.impl.OnViedoViewPositionListener;
import me.bandu.talk.android.phone.middle.TaskCatalogVideoMiMiddle;
import me.bandu.talk.android.phone.middle.WordsMiddle;
import me.bandu.talk.android.phone.middle.YouDaoMiddle;
import me.bandu.talk.android.phone.utils.ColorUtil;
import me.bandu.talk.android.phone.utils.New_VoiceUtils;
import me.bandu.talk.android.phone.utils.PopWordUtils;
import me.bandu.talk.android.phone.utils.ScreenUtil;
import me.bandu.talk.android.phone.view.AlertDialogUtils;
import me.bandu.talk.android.phone.view.Video;

/**
 * 创建者：wanglei
 * <p>时间：16/8/2  18:07
 * <p>类描述：从作业页跳转过来的视频页，点击按钮进入做作业页
 * <p>修改人：
 * <p>修改时间：task_catalog_video_layout
 * <p>修改备注：
 */
public class TaskCatalogVideoActivity extends BaseAppCompatActivity implements View.OnClickListener, BaseActivityIF, OnViedoViewPositionListener, Video.PlayItem {

    @Override
    protected int getLayoutId() {
        return R.layout.task_catalog_video_layout;
    }

    /**
     * 返回按钮
     */
    @Bind(R.id.task_catalog_video_back)
    ImageView goback;
    /**
     * 返回按钮
     */
    @Bind(R.id.task_catalog_video_back1)
    ImageView goback1;
    /**
     * 没有网络的蒙层
     */
    @Bind(R.id.task_catalog_video_notnet)
    FrameLayout notnet;
    /**
     * 去做作业页的按钮
     */
    @Bind(R.id.task_catalog_video_but)
    Button but;

    /**
     * 题干
     */
    @Bind(R.id.task_catalog_video_topic)
    TextView topic;

    /**
     * 视频
     */
    @Bind(R.id.task_catalog_video_video)
    Video video;
    /**
     * 中间的listView
     */
    @Bind(R.id.task_catalog_video_lsitview)
    ListView listView;

    private View pupView;

    private long lastClickTime;
    private int quiz_id;
    private String clickstr = "";
    private TaskCatalogVideoMiMiddle middle;
    private ArrayList<TaskCatalogVideoBean.TaskCatalogVideoData.SentenceList> sentenceLists;
    private TaskCatalogVideoAdapter adapter;
    private String description;
    private boolean videoInitState;

    @Override
    protected void toStart() {
        try {
            video.init(this);//video中的videoView.getHolder()方法有时会异常，原因没找到！
            videoInitState = true;
        } catch (Exception e) {
            videoInitState = false;
            e.printStackTrace();
        }
        Intent intent = getIntent();
        quiz_id = (int) intent.getLongExtra("quiz_id", -1);
        description = intent.getStringExtra("description");
        showTopicDaiog();
        but.setOnClickListener(this);
        topic.setOnClickListener(this);
        goback.setOnClickListener(this);
        goback1.setOnClickListener(this);
        notnet.setOnClickListener(this);
        middle = new TaskCatalogVideoMiMiddle(this, this);
        sentenceLists = new ArrayList<>();
        adapter = new TaskCatalogVideoAdapter(this, sentenceLists, this, this);
        listView.setAdapter(adapter);
        getInfo();
    }

    private void getInfo() {
        if (NetworkUtil.checkNetwork(this)) {
            notnet.setVisibility(View.GONE);
            if (quiz_id > 0) {
                showMyprogressDialog();
                middle.getvideoofquiz(quiz_id);
            }
        } else {
            UIUtils.showToastSafe("请连接网络");
            notnet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (!isFastClick()) {
            switch (view.getId()) {
                case R.id.task_catalog_video_but:
                    Intent intent1 = getIntent();
                    intent1.setClass(this, DoWorkActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.task_catalog_video_topic:
                    showTopicDaiog();
                    break;
                case R.id.task_catalog_video_back:
                    goback(view);
                    break;
                case R.id.task_catalog_video_back1:
                    goback(view);
                    break;
                case R.id.ll_main:
                    if (videoInitState) {
                        video.itemClik((int) view.getTag());
                        break;
                    }
                    UIUtils.showToastSafe("视频初始化失败，请返回上一页");
                case R.id.task_catalog_video_notnet:
                    getInfo();
                    break;
            }
        }
    }

    boolean isFirstPlayer;

    /**
     * 显示题干的那个dialog
     */
    private void showTopicDaiog() {
//        AlertDialogUtils.getInstance().init(TaskCatalogVideoActivity.this, description, new AlertDialogUtils.DialogLestener() {
//            @Override
//            public void ok() {
//                if (videoInitState) {
//                    if (!isFirstPlayer) {
//                        video.play(0);
//                        isFirstPlayer = true;
//                    } else
//                        video.pause();
//                } else
//                    UIUtils.showToastSafe("视频初始化失败，请返回上一页");
//            }
//
//            @Override
//            public void cancel() {
//
//            }
//        });
        View layout = LayoutInflater.from(this).inflate(R.layout.exit_dialog, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        final AlertDialog dialog1 = dialog.create();
        dialog1.setView(layout);
        dialog1.setCanceledOnTouchOutside(false);
        layout.findViewById(R.id.make_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoInitState) {
                    if (!isFirstPlayer) {
                        video.play(0);
                        isFirstPlayer = true;
                    } else
                        video.pause();
                } else
                    UIUtils.showToastSafe("视频初始化失败，请返回上一页");
                dialog1.dismiss();
            }
        });
        TextView msg = (TextView) layout.findViewById(R.id.msg);
//        msg.setText("Read as the speaker in the video \n 请给你所看到的视频配音");
        msg.setText(description);
        msg.setTextSize(16);
        layout.findViewById(R.id.cancel).setVisibility(View.GONE);
        dialog1.show();
        if (videoInitState)
            video.publicPause();
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        super.onSuccess(result, requestCode);
        switch (requestCode) {
            case TaskCatalogVideoMiMiddle.GETVIDEOOFQUIZ:
                TaskCatalogVideoBean tcvb = (TaskCatalogVideoBean) result;
                initPlayData(tcvb);
                sentenceLists.clear();
                sentenceLists.addAll(tcvb.getData().getSentence_list());
                adapter.notifyDataSetChanged();
                break;
        }
        hideMyprogressDialog();
    }

    @Override
    public void successFromMid(Object... obj) {
        if (WordsMiddle.GET_WORD == (int) obj[1]) {
            /*if (vp_video != null && vp_video.isPlaying())
                vp_video.playerPause();*/
            final MWordBean wordBean = (MWordBean) obj[0];
            if (wordBean.getStatus() == 0) {
                YouDaoMiddle youDaoMiddle = new YouDaoMiddle(this, this);
                youDaoMiddle.getWord(clickstr);
            } else {
                showWordPopupwindow(wordBean.getData());
            }
        } else if (WordsMiddle.CREATE_WORDS == (int) obj[1]) {
            BaseBean baseBean = (BaseBean) obj[0];
            UIUtils.showToastSafe(baseBean.getMsg());
            if (pupView != null) {
                Button btn = (Button) pupView.findViewById(R.id.btnAddWord);
                btn.setText("已添加");
                btn.setBackgroundResource(R.drawable.corners_gray);
                btn.setTextColor(ColorUtil.getResourceColor(TaskCatalogVideoActivity.this, R.color.white));
                btn.setFocusable(false);
                btn.setClickable(false);
            }
        } else if (YouDaoMiddle.YOUDAO == (int) obj[1]) {
            WordBean wordBean = (WordBean) obj[0];
            showWordPopupwindow(wordBean);
        }
    }

    private void showWordPopupwindow(WordBean data) {
        if (ScreenUtil.isForeground(this, getClass().getName())) {
            if (videoInitState)
                video.publicPause();
//            vp_video.playerPause();
            pupView = PopWordUtils.getInstance().showWorkPop(this, findViewById(R.id.rlRoot), data, new PopWordUtils.PopWordViewOnClick() {
                @Override
                public void btnOnClick(WordBean word) {
                    clickstr = word.getQuery();
                    WordsMiddle wordsMiddle = new WordsMiddle(TaskCatalogVideoActivity.this, TaskCatalogVideoActivity.this);
                    wordsMiddle.createWord(word.getQuery(), word);
                }

                @Override
                public void imgOnClick(WordBean word) {
                    New_VoiceUtils.getInstance().startVoiceNet("http://dict.youdao.com/dictvoice?audio=" + word.getQuery());
                }
            }, new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void initPlayData(TaskCatalogVideoBean tcvb) {
        ArrayList<Integer> end_times = new ArrayList<>();
        for (TaskCatalogVideoBean.TaskCatalogVideoData.SentenceList ttsL : tcvb.getData().getSentence_list()) {
            end_times.add(ttsL.getMp4_end_time());
        }
        if (videoInitState) {
            video.setEnd_times(end_times);
            video.setPath(tcvb.getData().getMp4());
        }
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
        hideMyprogressDialog();
        UIUtils.showToastSafe("服务器又偷懒了");
    }

    private synchronized boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    @Override
    public void currentPosition(int position) {
    }

    @Override
    public void prepared() {
    }

    @Override
    public void onCompletion() {
    }

    @Override
    public void onReplay() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (video != null)
            video.showackground();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (video != null)
            video.stop();
        isFirstPlayer = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (video != null)
            video.stop();
        isFirstPlayer = false;
    }

    @Override
    public void item(int index) {
        adapter.setPosition(index);
        adapter.notifyDataSetChanged();
        listView.setSelection(index);
    }

    @Override
    public void showDialog() {
        View layout = LayoutInflater.from(this).inflate(R.layout.exit_dialog, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        final AlertDialog dialog1 = dialog.create();
        dialog1.setView(layout);
        dialog1.setCanceledOnTouchOutside(false);
        layout.findViewById(R.id.make_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoInitState) {
                    video.isWIFI = true;
                    video.play(0);
                }
                dialog1.dismiss();
            }
        });
        TextView msg = (TextView) layout.findViewById(R.id.msg);
        msg.setText(getResources().getString(R.string.not_wifi));
        layout.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                video.play(0);
                if (videoInitState)
                    video.isWIFI = false;
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }
}
