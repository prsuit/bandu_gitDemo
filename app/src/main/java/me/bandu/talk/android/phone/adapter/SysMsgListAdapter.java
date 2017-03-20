package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.SysMsgBean;

/**
 * 创建者:taoge
 * 时间：2015/11/24 14:21
 * 类描述：
 * 修改人:taoge
 * 修改时间：2015/11/24 14:21
 * 修改备注：
 */
public class SysMsgListAdapter extends BaseAdapter {

    private Context context;
    private List<SysMsgBean.DataEntity.ListEntity> mList;

    public SysMsgListAdapter(Context context, List<SysMsgBean.DataEntity.ListEntity> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;
        SysMsgBean.DataEntity.ListEntity msg = mList.get(position);
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_system_msg, null);
            viewholder.title = (TextView) convertView.findViewById(R.id.title_tv);
            viewholder.point = (TextView) convertView.findViewById(R.id.point_tv);
            viewholder.time = (TextView) convertView.findViewById(R.id.time_tv);
            viewholder.content = (TextView) convertView.findViewById(R.id.content_tv);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        if (msg != null) {
            viewholder.title.setText(msg.getTitle());
            viewholder.content.setText(msg.getContent());
            viewholder.time.setText(msg.getCtime());
            if (msg.getIs_read() == 1) {
                viewholder.point.setVisibility(View.INVISIBLE);
            } else {
                viewholder.point.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }


    public class ViewHolder{
        private TextView title;
        private TextView point;
        private TextView time;
        private TextView content;
    }
}
