package me.bandu.talk.android.phone.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.DFHT.base.BaseBean;
import com.DFHT.base.engine.BaseActivityIF;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.BindTextBookActivity;
import me.bandu.talk.android.phone.bean.GradeTextBookBean;
import me.bandu.talk.android.phone.middle.BindTextbookMiddle;
import me.bandu.talk.android.phone.utils.ScreenUtil;

/**
 * 创建者：gaoye
 * 时间：2015/11/25  09:48
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class SelectVersionAdapter extends BaseAdapter{
    private List<GradeTextBookBean.DataEntity.Version> versions;
    private Context context;
    private SelectTextbookListener listener;

    public SelectVersionAdapter(Context context, List<GradeTextBookBean.DataEntity.Version> versions,SelectTextbookListener listener){
        this.context = context;
        this.versions = versions;
        this.listener = listener;
    }
    @Override
    public int getCount() {
        return versions.size();
    }

    @Override
    public Object getItem(int position) {
        return versions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final GradeTextBookBean.DataEntity.Version version = versions.get(position);
        List<GradeTextBookBean.DataEntity.Version.TextBook> textBooks = version.getList();

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_bindtextbook_item, null);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_versionname);
            viewHolder.tv1 = (TextView) convertView.findViewById(R.id.tv_bookname1);
            viewHolder.tv2 = (TextView) convertView.findViewById(R.id.tv_bookname2);
            viewHolder.iv1 = (ImageView) convertView.findViewById(R.id.iv_bookimg1);
            viewHolder.iv2 = (ImageView) convertView.findViewById(R.id.iv_bookimg2);
            viewHolder.rl1 = (RelativeLayout) convertView.findViewById(R.id.rl_left);
            viewHolder.rl2 = (RelativeLayout) convertView.findViewById(R.id.rl_right);
            viewHolder.params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(200,context));
            viewHolder.params.setMargins(0,0,ScreenUtil.dp2px(10,context),0);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (version.getList().size() > 1){
            viewHolder.rl2.setVisibility(View.VISIBLE);
            viewHolder.tv2.setText(version.getList().get(1).getBook_name());
            ImageLoader.getInstance().displayImage(version.getList().get(1).getCover(),viewHolder.iv2);
            viewHolder.rl2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int bookid = version.getList().get(1).getBook_id();
                    if (listener != null)
                        listener.selectTextbook(bookid);
                    //bindTextbookMiddle.bindClassTextbook(classid, "" + version.getList().get(1).getBook_id());
                }
            });
            viewHolder.params.weight = 1;
            viewHolder.params.width = LinearLayout.LayoutParams.MATCH_PARENT;
            viewHolder.rl1.setLayoutParams(viewHolder.params);
        }else {
            viewHolder.rl2.setVisibility(View.GONE);
            int width = viewHolder.rl1.getMeasuredWidth();
            viewHolder.params.weight = 0;
            viewHolder.params.width = width;
            viewHolder.rl1.setLayoutParams(viewHolder.params);
        }
        viewHolder.tv.setText(version.getVersion());
        viewHolder.tv1.setText(version.getList().get(0).getBook_name());

        ImageLoader.getInstance().displayImage(version.getList().get(0).getCover(),viewHolder.iv1);


        viewHolder.rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bookid = version.getList().get(0).getBook_id();
                if (listener != null)
                    listener.selectTextbook(bookid);
                //
            }
        });

        return convertView;
    }

    public interface SelectTextbookListener{
        public void selectTextbook(int bookid);
    }


    class ViewHolder {
        TextView tv;
        TextView tv1;
        TextView tv2;
        ImageView iv1;
        ImageView iv2;
        RelativeLayout rl1;
        RelativeLayout rl2;
        LinearLayout.LayoutParams params;
    }
}
