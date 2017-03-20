package me.bandu.talk.android.phone.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.DFHT.utils.UIUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.CreatWorkContentActivity;
import me.bandu.talk.android.phone.bean.Quiz;
import me.bandu.talk.android.phone.bean.WorkDataBean;
import me.bandu.talk.android.phone.viewholder.WorkPreviewHolder;

/**
 * 创建者：高楠
 * 时间：on 2016/3/30
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CreatWorkPreviewAdapter extends RecyclerView.Adapter<WorkPreviewHolder> implements View.OnClickListener {
    private Context context;
    private List<Quiz> list;
    private WorkDataBean dataBean;
    public CreatWorkPreviewAdapter(Context context, WorkDataBean dataBean){
        this.context = context;
        this.dataBean = dataBean;
        list = dataBean.getQuizList();
        if (list==null)
            list = new ArrayList<>();
    }
    @Override
    public WorkPreviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item_text,null);
        WorkPreviewHolder holder = new WorkPreviewHolder(view);
        view.setTag(viewType);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(WorkPreviewHolder holder, int position) {
        if (position<list.size()-1){
            holder.tv.setTextColor(Color.WHITE);
            holder.tv.setBackgroundResource(R.drawable.grid_item_normal);
            holder.tv.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.INVISIBLE);
            holder.tv.setText(list.get(position).getQuiz_name());
        }else if (position==list.size()-1){
            holder.tv.setBackgroundResource(R.drawable.grid_item_click);
            holder.tv.setTextColor(Color.WHITE);
            holder.tv.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.INVISIBLE);
            holder.tv.setText(list.get(position).getQuiz_name());
        }else {
            holder.tv.setVisibility(View.INVISIBLE);
            holder.imageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list==null?1:list.size()+1;
    }
    public List<Quiz> getData(){
        return list;
    }

    @Override
    public void onClick(View v) {
        if (list!=null){
            int position = (int) v.getTag();
            if (position<list.size()){
                Intent intent = new Intent(context, CreatWorkContentActivity.class);
                intent.putExtra("data",dataBean);
                intent.putExtra("quizid", list.get(position).getQuiz_id());
                intent.putExtra("curPosition", position);
                context.startActivity(intent);
//                UIUtils.showToastSafe("1"+position);
            }else {
                //此处为加号的点击事件
                if (dataBean.isCurrentQuizOk()){
                    Intent intent = new Intent();
                    dataBean.setSTATUS(WorkDataBean.ADDING);
                    intent.putExtra("data",dataBean);
                    ((Activity)context).setResult(Activity.RESULT_OK,intent);
                    ((Activity)context).finish();
                }else {
                    UIUtils.showToastSafe(UIUtils.getString(R.string.workpreview_error_msg));
                }

            }
        }
    }
}
