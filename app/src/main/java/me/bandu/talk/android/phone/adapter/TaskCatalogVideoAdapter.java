package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.DFHT.base.engine.BaseActivityIF;

import java.util.ArrayList;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.TaskCatalogVideoBean;
import me.bandu.talk.android.phone.utils.ColorUtil;
import me.bandu.talk.android.phone.utils.StringUtil;

/**
 * 创建者：wanglei
 * <p>时间：16/8/4  16:34
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class TaskCatalogVideoAdapter extends BaseAdapter {
    private ArrayList<TaskCatalogVideoBean.TaskCatalogVideoData.SentenceList> sentenceLists;
    private Context context;
    private int position;
    private BaseActivityIF baseActivityIF;
    private View.OnClickListener listener;

    public TaskCatalogVideoAdapter(Context context, ArrayList<TaskCatalogVideoBean.TaskCatalogVideoData.SentenceList> sentenceLists, BaseActivityIF baseActivityIF, View.OnClickListener listener) {
        this.sentenceLists = sentenceLists;
        this.context = context;
        this.baseActivityIF = baseActivityIF;
        this.listener = listener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getCount() {
        return sentenceLists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        TaskCatalogVideoBean.TaskCatalogVideoData.SentenceList sentenceList = sentenceLists.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.task_catalog_video_item, null);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.ll_main = (LinearLayout) convertView.findViewById(R.id.ll_main);
            viewHolder.tv_position = (TextView) convertView.findViewById(R.id.tv_position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_position.setText(String.valueOf(position + 1) + "/" + String.valueOf(sentenceLists.size()));

        viewHolder.tv_content.setText(StringUtil.getShowText(sentenceList.getEn()), TextView.BufferType.SPANNABLE);

        if (this.position == position) {
            viewHolder.ll_main.setBackgroundColor(Color.parseColor("#fbf4da"));
            viewHolder.tv_content.setMovementMethod(LinkMovementMethod.getInstance());
            StringUtil.getEachWord(viewHolder.tv_content, context, baseActivityIF);
        } else {
            viewHolder.tv_content.setMovementMethod(null);
            viewHolder.ll_main.setBackgroundColor(ColorUtil.getResourceColor(context, R.color.white));
        }
        viewHolder.ll_main.setTag(position);
        viewHolder.ll_main.setOnClickListener(listener);
        return convertView;
    }

    class ViewHolder {
        TextView tv_content, tv_position;
        LinearLayout ll_main;
    }
}
