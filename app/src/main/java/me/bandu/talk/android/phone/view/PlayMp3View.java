package me.bandu.talk.android.phone.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.DFHT.net.NetworkUtil;
import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.BaseAppCompatActivity;
import me.bandu.talk.android.phone.bean.MyRecordBean;
import me.bandu.talk.android.phone.utils.PlayMp3Utils;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.view.impl.onMusicOver;

/**
 * 创建者：Mckiera
 * <p>时间：2016-06-02  09:34
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class PlayMp3View extends RelativeLayout implements View.OnClickListener {
    public static final int MY_IN_LEFT = 1;
    public static final int MY_IN_RIGHT = 0;

    private MyRecordBean my;

    private static final String TAG = "FUCK";
    private ImageView ivPlay;
    private CircleImageView ivHead;
    private ProgressBar pbPercent;
    private TextView tvContent;
    private boolean isPlay = false;
    private MyRecordBean all;
    private Context context;
    private PlayMp3Utils binder;
//    private String localPath;
    private volatile boolean isStart;
    private int currPosition = 0;
//    private ArrayList<String> playTime;

    private BroadcastReceiver receiver ;

    private String action = "com.mcablylx.musicservice";

    public PlayMp3View(Context context) {
        super(context);
        init(context);
    }

    //你在为何惆怅,是什么让你感到失望
    //梦想被流放口口声声说着不会投降.
    //为了生存,为了希望.不懂得珍惜,却总是偏袒自己逞强.
    //由内而外,谁在用心关注我的成长.从下到上,谁在用那讽刺笑我猖狂.
    //我想这一路根本无须解释谁在我的身旁,我的朋友和我的兄弟身边的所有的人全都做的很棒.
    //我感谢他们的关照,帮我隔绝了世态的炎凉,
    //我感谢他们的支持,给我一次又一次力量.
    //望风而变像在骤雨之中无奈的远航.
    //....
    //乘风破浪的战舰想要顶住天上力挽狂澜
    //海浪惊涛的巨响之中谁能说的算
    //我默不作声看看周围的人怎么办
    //我承认自己不行那是我在想要怎么干!怎么看!
    //事情都有正反两面
    //所以脑袋应该转!不要乱!
    //觉得自己出手慢,后发制人依然能胜过战场打翻盘
    //放开了心胸鼓起我士气然后再一战

    //说了不故意 也说了没关系
    //经历了委屈之后我们还是好兄弟
    //如果真的有一天不能陪你走下去
    //我希望你能记住我们曾经有过的回忆

    //烦恼别牢记
    //快乐要去珍惜
    //有些事情过去了就让它们随风而去
    //看着旧时连续剧
    //回到儿时的乐趣
    //我想这也许就是我人生里最后的意义
    //失去了目标 依然要往前走
    //即使误入歧途也一刻不愿意停留
    //直到有一天能扣速在快乐的前头
    //你能否回头出来也不会再会回头

    //我的好兄弟你现在在哪里
    //人生聚少离多

    //战意昂,我必须趁着年轻走这一把狂.
    //周围现象太多 看似透彻的眼却已是为青盲.
    //蒙蔽着自己的想法 只是懦弱的心房
    //想要退缩,先看看自己挺起的胸膛.

    //战意昂,我必须趁着年轻打出我的王.
    //我做我自己喜欢做的 不管别人怎么讲
    //怎么想那是你的权利
    //我不会忘我还有很多事要做 我还有很多梦要想
    public PlayMp3View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayMp3View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        receiver = new Mp3Broad();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Mp3Broad.ERR_ACTION);
        filter.addAction(Mp3Broad.TO_PROGRESS_ACTION);
        context.registerReceiver(receiver, filter);

        LayoutInflater.from(context).inflate(R.layout.palymp3_item, this, true);
        ivPlay = (ImageView) findViewById(R.id.ivPlay);
        ivHead = (CircleImageView) findViewById(R.id.ivHead);
        pbPercent = (ProgressBar) findViewById(R.id.pbPercent);
        tvContent = (TextView) findViewById(R.id.tvContent);

        ivPlay.setOnClickListener(this);
        ivHead.setOnClickListener(this);
        tvContent.setText("55555");
        tvContent.setOnClickListener(this);

        binder = new PlayMp3Utils(context,pbPercent);
    }
    public void setData(MyRecordBean my, MyRecordBean other, int flag) {
        if(this.my == null)
            this.my = my;
        mixData(other ,flag);
        resetAll();
    }

    private void mixData( MyRecordBean other, int flag) {
        all = null;
        all = new MyRecordBean();

        MyRecordBean.DataBean bandataBean = new MyRecordBean.DataBean();
        List<MyRecordBean.DataBean.SentencesBean> banList = new ArrayList<>();
        for (int i = 0 ; i < my.getData().getSentences().size() ; i ++) {
            MyRecordBean.DataBean.SentencesBean banBean = new MyRecordBean.DataBean.SentencesBean();
            banBean.setMp3(my.getData().getSentences().get(i).getMp3());
            banBean.setSeconds(my.getData().getSentences().get(i).getSeconds());
            banBean.setEn(my.getData().getSentences().get(i).getEn());
            banBean.setHead(my.getData().getSentences().get(i).getHead());
            banList.add(banBean);
        }
        bandataBean.setSentences(banList);
        all.setData(bandataBean);

//        all.setData(my.getData());
        if(other == null){
            getPlayTime();
            return;
        }

        for(int i = 0 ; i < my.getData().getSentences().size(); i++){
            if(i % 2 == flag){
                try {
                    all.getData().getSentences().get(i).setMp3(other.getData().getSentences().get(i).getMp3());
                    all.getData().getSentences().get(i).setHead(other.getData().getSentences().get(i).getHead());
                }catch (Exception e){
                    all.getData().getSentences().add(other.getData().getSentences().get(i));
                }

            }
        }
        getPlayTime();
    }

    private void getPlayTime() {
        /*if(playTime != null)
            playTime = null;
        playTime = new ArrayList<>();*/
        final StringBuilder sb = new StringBuilder();
        new Thread(){
            @Override
            public void run() {
                for(int i = 0; i< all.getData().getSentences().size(); i++){
                    sb.append(all.getData().getSentences().get(i).getEn()+" ");
                   /* if(!TextUtils.isEmpty(all.getData().getSentences().get(i).getMp3())){
                        int netTime = 0;
                        try {
                            MediaPlayer mp = new MediaPlayer();
                            mp.setDataSource(all.getData().getSentences().get(i).getMp3());
                            mp.prepare();
                            netTime = mp.getDuration();
                            mp.release();
                            mp = null;
                            playTime.add(String.valueOf(netTime));
                        } catch (IOException e) {
                            e.printStackTrace();
                            playTime.add(String.valueOf(all.getData().getSentences().get(i).getSeconds()));
                        }
                    }else {
                        playTime.add(String.valueOf(all.getData().getSentences().get(i).getSeconds()));
                    }*/
                }

                UIUtils.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                       // pbPercent.setMax(getSeekBarAllProgress());//给seekbar设置长度
                        tvContent.setText(sb.toString());
                    }
                });
            }
        }.start();

        setHead();
    }
    /*
    private int getSeekBarAllProgress() {
        int time = 0;
        for(int i =0; i< playTime.size(); i++){
            time += Integer.valueOf( playTime.get(i));
        }
        return time;
    }*/

    public void cleanMp3(){
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                currPosition = 0;
                isStart = false;
                isPlay = false;
                if(binder != null){
                    binder.clean();
                    pbPercent.setProgress(0);
                    pbPercent.setMax(0);
                    ivPlay.setImageResource(R.mipmap.iv_record_playing);
                }
            }
        });
    }

    private void resetAll(){
        cleanMp3();
        getPlayTime();
        setHead();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivPlay:
                if(NetworkUtil.checkNetwork(context)) {
                    startMp3();
                } else {
                    UIUtils.showToastSafe("网络异常，请检查网络");
                }
                break;
            case R.id.ivHead:
                Log.i(TAG, "ivHead");
                break;
            case R.id.tvContent:
                Log.i(TAG, "tvContent");
                break;
        }
    }

    public void startMp3() {
            Log.i(TAG, "ivPlay");
            if (all == null || TextUtils.isEmpty(all.getData().getSentences().get(currPosition).getMp3())) {
                Toast.makeText(context, "无效的音频地址", Toast.LENGTH_LONG).show();
                return;
            }
            if (binder != null) {
                if (!isPlay) {
                    ivPlay.setImageResource(R.mipmap.iv_record_stop);
                } else {
                    ivPlay.setImageResource(R.mipmap.iv_record_playing);
                }
                isPlay = !isPlay;
                if (!isStart) {
                    isStart = !isStart;
                    setHead();
//                pbPercent.setMax(getSeekBarAllProgress() + (all.getData().getSentences().size() -1)*100);
//                pbPercent.setMax();
                    //pbPercent.setProgress(0);
                    tvContent.setText(all.getData().getSentences().get(currPosition).getEn());
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                Thread.sleep(100);
                                if (binder != null) {
//                                binder.setData(playTime);
                                    binder.init(all.getData().getSentences().get(currPosition).getMp3(), currPosition, new onMusicOver() {
                                        @Override
                                        public void onMusicOver() {
                                            boolean next = next(null);
                                            if (!next) {
                                                resetAll();
                                            }
                                        }
                                    });
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }.start();
                } else {
                    if (!isPlay) {
                        binder.pause();
                    } else {
                        binder.resume();
                    }
                }

            }
    }

    /**
     * 播放下一首
     * @param v
     * @return true 有下一首可以播放
     *          false 没有可以播放的录音
     */
    private boolean next(View v){
        if (NetworkUtil.checkNetwork(context)) {
            if (binder != null) {
                binder.cleanNoPro();
            }
            currPosition++;
            if(all.getData().getSentences().size() == currPosition){
                return false;
            }
            setHead();
            tvContent.setText(all.getData().getSentences().get(currPosition).getEn());
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(100);
                        if (binder != null) {
//                        binder.setData(playTime);
                            binder.init(all.getData().getSentences().get(currPosition).getMp3(), currPosition, new onMusicOver() {
                                @Override
                                public void onMusicOver() {
                                    boolean next = next(null);
                                    if (!next) {
                                        resetAll();
                                    }
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } else {
            UIUtils.showToastSafe("网络异常，请检查网络");
        }
        return true;

    }


    public void unbindService(){
        resetAll();
        context.unregisterReceiver(receiver);
    }

    public class Mp3Broad extends BroadcastReceiver{
        public static final String ERR_ACTION = "PlayMp3View_Err_Broadcast";
        public static final String TO_PROGRESS_ACTION = "PlayMp3View_to_progress_Broadcast";
        public static final String ERR_MSG = "play init err";
        public static final String TO_PROGRESS_MSG = "play to progress";
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case ERR_MSG:
                    resetAll();
                    break;
                case TO_PROGRESS_ACTION:
                    int progress = intent.getIntExtra("progress", 0);
                    currPosition = intent.getIntExtra("currPosition", PlayMp3View.this.currPosition);
                    withProgressMp3(progress);
                    break;
            }
        }
    }

    private void setHead(){
        if("banHead".equals(all.getData().getSentences().get(currPosition).getHead())){
            ivHead.setImageResource(R.mipmap.bandu_icon2);
        }else if(TextUtils.isEmpty(all.getData().getSentences().get(currPosition).getHead())){
            ivHead.setImageResource(R.mipmap.default_avatar);
        } else {
            ImageLoader.getInstance().displayImage(StringUtil.getShowText(all.getData().getSentences().get(currPosition).getHead()), ivHead);
        }
    }

    /**
     * @param progress 音频进度位置
     */
    private void withProgressMp3(final int progress){
        if ( binder != null) {
            binder.cleanNoPro();
        }
        setHead();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(100);
                    if (binder != null) {
//                        binder.setData(playTime);
                        binder.init(all.getData().getSentences().get(currPosition).getMp3(), currPosition, true, progress, new onMusicOver() {
                            @Override
                            public void onMusicOver() {
                                boolean next = next(null);
                                if (!next) {
                                    resetAll();
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
