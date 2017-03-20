package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.DFHT.utils.LogUtils;

import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.CreatWorkContentBean1;

/**
 * 创建者：高楠
 * 时间：on 2016/4/8
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CreatWorkContentAdapter extends BaseExpandableListAdapter {
    private List<CreatWorkContentBean1> data;
    private Context context;
    private int gposition = 0;


    public void setData(List<CreatWorkContentBean1> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public CreatWorkContentAdapter(List<CreatWorkContentBean1> data, Context context) {
        this.data = data;
        this.gposition = gposition;
        this.context = context;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        WorkContentGroupHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_workcontent_group, null);
            holder = new WorkContentGroupHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (WorkContentGroupHolder) convertView.getTag();
        }
        if (groupPosition == gposition) {
            holder.imageView.setImageResource(R.mipmap.next_up);
        } else {
            holder.imageView.setImageResource(R.mipmap.next_down);
        }
//        holder.workcontent_content.setText(data.getData().getQuiz_list().get(groupPosition).getQuiz_name());
//        holder.workcontent_type.setText(data.getData().getQuiz_list().get(groupPosition).getQuiz_type());

        CreatWorkContentBean1 cwcb = data.get(groupPosition);


        holder.workcontent_content.setText(cwcb.getPartName());
        holder.workcontent_type.setText(cwcb.getType());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        WorkContentChildHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_workcontent_child, null);
            holder = new WorkContentChildHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (WorkContentChildHolder) convertView.getTag();
        }
        List<CreatWorkContentBean1.TaskSentenceEntity> list = data.get(groupPosition).getList();
//        holder.workcontent_tv.setText(data.getData().getQuiz_list().get(groupPosition).getSentence_list().get(childPosition).getEn());
        holder.workcontent_tv.setText(list.get(childPosition).getText());
        return convertView;
    }

//    public int getQuizPosition(String quiz_id){
//        if (quiz_id==null||quiz_id.equals("")||data==null||data.getData()==null||data.getData().getQuiz_list()==null)
//            return 0;
////        List<CreatWorkContentBean.DataEntity.QuizListEntity> list = data.getData().getQuiz_list();
//        List<CreatWorkContentsBean.DataBean.QuizListBean> list = this.data.getData().getQuiz_list();
//        for (int i = 0 ; i <list.size() ; i++){
//            if (list.get(i).getQuiz_id().equals(quiz_id)){
//                return i;
//            }
//        }
//        return 0;
//    }


    @Override
    public int getGroupCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data == null ? 0 : data.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public String getMp3(int groupPosition, int childPosition) {
        try {
//            return data.getData().getQuiz_list().get(groupPosition).getSentence_list().get(childPosition).getMp3();
            return data.get(groupPosition).getList().get(childPosition).getMap3();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("作业内容数据出问题：" + data);
            return null;
        }
    }

    public class WorkContentGroupHolder {
        TextView workcontent_content;
        TextView workcontent_type;
        ImageView imageView;

        public WorkContentGroupHolder(View view) {
            workcontent_content = (TextView) view.findViewById(R.id.workcontent_content);
            workcontent_type = (TextView) view.findViewById(R.id.workcontent_type);
            imageView = (ImageView) view.findViewById(R.id.iv_group_next);
        }
    }

    public class WorkContentChildHolder {
        TextView workcontent_tv;
        ImageView workcontent_iv;

        public WorkContentChildHolder(View view) {
            workcontent_tv = (TextView) view.findViewById(R.id.workcontent_tv);
            workcontent_iv = (ImageView) view.findViewById(R.id.workcontent_iv);
        }
    }

    public void setGroupPosition(int position) {
        this.gposition = position;
        notifyDataSetChanged();
    }
}
