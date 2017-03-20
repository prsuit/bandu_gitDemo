package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.prompt.dialog.MyProgressDialog;
import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;
import com.chivox.ChivoxConstants;
import com.chivox.utils.ChivoxUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.DoWorkActivity;
import me.bandu.talk.android.phone.bean.Detail;
import me.bandu.talk.android.phone.bean.MWordBean;
import me.bandu.talk.android.phone.bean.WordBean;
import me.bandu.talk.android.phone.holder.BaseHolderView;
import me.bandu.talk.android.phone.middle.WordsMiddle;
import me.bandu.talk.android.phone.utils.ColorUtil;
import me.bandu.talk.android.phone.utils.New_VoiceUtils;
import me.bandu.talk.android.phone.utils.PopWordUtils;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.utils.TextColorUtils;
import me.bandu.talk.android.phone.view.ReversalView;

/**
 * author by Mckiera
 * time: 2015/12/21  20:54
 * description: 做作业adapter
 * updateTime:
 * update description:
 */
public class DoneWorkListNewOneAdapter extends DoneWorkListBaseAdapter implements BaseActivityIF {

    public DoneWorkListNewOneAdapter(Detail detail, Context context, String currentType, ListView lv) {
        super(detail, context, currentType, lv);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HolderView holder;
        if (convertView != null) {
            holder = (HolderView) convertView.getTag();
        } else {
            convertView = View.inflate(context, R.layout.list_item_text_reading, null);
            holder = new HolderView(convertView);
            convertView.setTag(holder);
        }
        holders.add(position, holder);

        String en = list.get(position).getEn();
        if (en.contains("#")) {
            String[] split = en.split("#");
            StringBuilder sbEn = new StringBuilder();
            for (int i = 0; i < split.length; i++) {
                if (i == 0)
                    sbEn.append(split[i] + "\r\n");
                else
                    sbEn.append("    " + split[i] + "\r\n");
            }
            holder.tvContent.setText(sbEn.toString());
        } else {
            holder.tvContent.setText(en);
        }

        holder.tvPosition.setText("  "+(position+1)+"/"+list.size()+"  ");



        if (TextUtils.isEmpty(list.get(position).getMp3())) {
            holder.ivHorn.setVisibility(View.GONE);
        }
//        holder.tvContent.setText(TextColorUtils.changTextColor(list.get(position).getEn() + "",list.get(position).getWords_score()));
        holder.ivHorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                New_VoiceUtils.getInstance().startVoiceNet(list.get(position).getMp3());
            }
        });

        holder.rl_ivHorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                New_VoiceUtils.getInstance().startVoiceNet(list.get(position).getMp3());
            }
        });
        holder.tvDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(list.get(position).getStu_mp3())) {
                    UIUtils.showToastSafe("缺少录音");
                    return;
                }
                New_VoiceUtils.getInstance().startVoiceNet(list.get(position).getStu_mp3());
            }
        });
        LogUtils.i("语音时长:" + list.get(position).getSeconds());
        StringBuilder sb = new StringBuilder("    ");
        for (int i = 0; i < list.get(position).getSeconds(); i++) {
            if (i >= 6) {
                break;
            }
            sb.append("　");
        }
        holder.tvDuration.setText(sb.toString() + list.get(position).getSeconds() + "\"");

        getEachWord(holder.tvContent, position);
        return convertView;
    }

    public static class HolderView extends BaseHolderView {
        @Bind(R.id.rl_ivHorn)
        RelativeLayout rl_ivHorn;
        /**
         * 内容
         */
        @Bind(R.id.tvContent)
        TextView tvContent;

        /**
         * 小喇叭
         */
        @Bind(R.id.ivHorn)
        ImageView ivHorn;

        /**
         * 用时
         */
        @Bind(R.id.tvDuration)
        TextView tvDuration;
        @Bind(R.id.tvPosition)
        TextView tvPosition;


        public HolderView(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
