package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.DFHT.base.ENBaseApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.MyselfPullulateActivity;
import me.bandu.talk.android.phone.bean.NameListSortBean;
import me.bandu.talk.android.phone.bean.WeakSentenceBean;
import me.bandu.talk.android.phone.fragment.HomeworkSortFragment;
import me.bandu.talk.android.phone.utils.DateUtils;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.view.CircleImageView;

/**
 * Created by fanYu on 2016/6/2.
 * 作业大数据的adapter
 */
public class HomeworkSortAdapter extends BaseAdapter{
    private List<NameListSortBean.DataBean.ListBean> list;
    private List<WeakSentenceBean.DataBean.ListBean> weakList;
    private Context context;
    int flag;
    private String cid;
    private int cid1;

    public HomeworkSortAdapter(Context context, List<WeakSentenceBean.DataBean.ListBean> weakList, int flag ,int cid1){
        this.context = context;
        if (weakList != null){
            this.weakList = weakList;
        }
        this.flag = flag;
        this.cid1 = cid1;
    }

    public HomeworkSortAdapter(Context context, List<NameListSortBean.DataBean.ListBean> list, int flag, String cid){
        this.context = context;
        if (list !=null){
            this.list = list;
        }
        this.flag = flag;
        this.cid = cid;
    }

    @Override
    public int getCount() {
        if(flag==HomeworkSortFragment.Type_WEAKSENTENCE ){
            return weakList.size();
        }else{
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderItem itemlist;
        WeakSentenceBean.DataBean.ListBean weakBean = null;
        NameListSortBean.DataBean.ListBean itemBean = null;
        if(flag==HomeworkSortFragment.Type_WEAKSENTENCE){
            weakBean=weakList.get(position);
        } else {
            itemBean = list.get(position);
        }

        if (convertView == null){
            convertView  = View.inflate(ENBaseApplication.getInstance(),R.layout.item_content,null);
            itemlist = new ViewHolderItem(convertView);
            convertView.setTag(itemlist);
        }else{
            itemlist = (ViewHolderItem)convertView.getTag();
        }

        if (flag == HomeworkSortFragment.Type_WEAKSENTENCE) {
            //防止头像复用
            itemlist.civ_header.setTag(weakBean.getAvatar());

            if("".equals(weakBean.getAvatar())){
                itemlist.civ_header.setImageResource(R.mipmap.default_avatar);
            }else{
                ImageLoader.getInstance().displayImage(StringUtil.getShowText(weakBean.getAvatar()),
                        itemlist.civ_header.getTag().equals(weakBean.getAvatar()) ? itemlist.civ_header : null,builder.build());
            }

            itemlist.tv_name.setText(weakBean.getStu_name());
            if(weakBean.getEvaluated()==1){
                //等于1代表已经评价过
                itemlist.iv_comment.setVisibility(View.VISIBLE);
            }else if(weakBean.getEvaluated()==0){
                //等于0代表未评价
                itemlist.iv_comment.setVisibility(View.INVISIBLE);
            }
            itemlist.iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,MyselfPullulateActivity.class);
                    intent.putExtra("info","t");
                    intent.putExtra("cid",cid1+"");
                    intent.putExtra("uid",weakList.get(position).getUid()+"");
                    intent.putExtra("name",weakList.get(position).getStu_name()+"");
                    context.startActivity(intent);
                }
            });


            itemlist.tv_date.setTextColor(0xff000000);//黑色
            if(weakBean.getStatus()==0){
                //未提交
                itemlist.tv_date.setText("未交");
            }else if(weakBean.getStatus()==1){
                //未完成
                itemlist.tv_date.setText("未完成");
            }else if(weakBean.getStatus()==2){
                int weakScore=weakBean.getScore();
                if(weakScore >=85 && weakScore <=100){          // A级
                    itemlist.tv_date.setText(weakScore+"分");
                    itemlist.tv_date.setTextColor(0xff9ed152);//绿色
                }else if(weakScore >=55 && weakScore< 85){   // B级
                    itemlist.tv_date.setText(weakScore+"分");
                    itemlist.tv_date.setTextColor(0xff3bade5);//蓝色
                }else if(weakScore >=0 && weakScore < 55){    // C级
                    itemlist.tv_date.setText(weakScore+"分");
                    itemlist.tv_date.setTextColor(0xffea6c6d);//红色
                }
            }

        } else {
            itemlist.tv_name.setText(itemBean.getStu_name());

            itemlist.civ_header.setTag(itemBean.getAvatar());
            if("".equals(itemBean.getAvatar())){
                itemlist.civ_header.setImageResource(R.mipmap.default_avatar);
            }else{
                ImageLoader.getInstance().displayImage(StringUtil.getShowText(itemBean.getAvatar()),
                        itemlist.civ_header.getTag().equals(itemBean.getAvatar()) ? itemlist.civ_header : null,builder.build());
            }

            //小图标的点击事件
            itemlist.iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,MyselfPullulateActivity.class);
                    intent.putExtra("info","t");
                    intent.putExtra("cid",cid+"");
                    intent.putExtra("uid",list.get(position).getUid()+"");
                    intent.putExtra("name",list.get(position).getStu_name()+"");
                    context.startActivity(intent);
                }
            });

            itemlist.tv_date.setTextColor(0xff000000);//黑色
            if(itemBean.getStatus()==0){
                //未提交
                itemlist.tv_date.setText("未交");
            }else if(itemBean.getStatus()==1){
                //未完成
                itemlist.tv_date.setText("未完成");
            }else if(itemBean.getStatus()==2){
                //已完成
                if(flag == HomeworkSortFragment.Type_COMMITTIME){
                    //默认排序
                    String commit_time = itemBean.getCommit_time();
                    String formatStr=DateUtils.string2Date(commit_time,"MM月dd日HH:mm");
                    itemlist.tv_date.setText(formatStr);

                }else if(flag == HomeworkSortFragment.Type_SCORE){
                    //分数排序数据源的ListView的显示文本
                    int score=itemBean.getScore();
                    if(score >=85 && score <=100){          // A级
                        itemlist.tv_date.setText(score+"分");
                        itemlist.tv_date.setTextColor(0xff9ed152);//绿色
                    }else if(score >=55 && score< 85){   // B级
                        itemlist.tv_date.setText(score+"分");
                        itemlist.tv_date.setTextColor(0xff3bade5);//蓝色
                    }else if(score >=0 && score < 55){    // C级
                        itemlist.tv_date.setText(score+"分");
                        itemlist.tv_date.setTextColor(0xffea6c6d);//红色
                    }
                }else if(flag ==HomeworkSortFragment.Type_TOTALTIME){
                    //总时间数据源的listView的显示文本
                    int time =itemBean.getTotal_time();  //单位是秒
                    if(time >3600) {
                        int hour = time / 3600; //小时
                        int minute = time % 3600  / 60;   //分钟
                        itemlist.tv_date.setText(hour + "小时" + minute + "分");
                    }else if(time >60){
                        int minute = time / 60;  //分钟
                        int second = time % 60;
                        itemlist.tv_date.setText(minute + "分" + second + "秒");
                    }else{
                        itemlist.tv_date.setText(time+"秒");
                    }
                }else if(flag ==HomeworkSortFragment.Type_UP){
                    //up指数这个数据源的listView显示文本
                    int up_score=itemBean.getUp_score();
                    if(up_score > 0){          //进步
                        itemlist.tv_date.setText(" + "+ up_score);
                        itemlist.tv_date.setTextColor(0xff9ed152);//绿色
                    }else if(up_score == 0){   //持平
                        itemlist.tv_date.setText(up_score+"");
                        itemlist.tv_date.setTextColor(0xff3bade5);//蓝色
                    }else if(up_score < 0){    //退步
                        itemlist.tv_date.setText(""+up_score);
                        itemlist.tv_date.setTextColor(0xffea6c6d);//红色
                    }
                }

            }
            if(itemBean.getEvaluated()==1){
                //等于1代表已经评价过
                itemlist.iv_comment.setVisibility(View.VISIBLE);
            }else if(itemBean.getEvaluated()==0){
                //等于0代表未评价
                itemlist.iv_comment.setVisibility(View.INVISIBLE);
            }
        }
        return convertView;
    }
    //服务器返回404，此时让imageloader加载默认的头像
    DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder().showImageOnFail(R.mipmap.default_avatar);

    public class ViewHolderItem{
        public ViewHolderItem(View view){

            tv_date= (TextView) view.findViewById(R.id.tv_time);
            tv_name= (TextView) view.findViewById(R.id.tv_name);
            civ_header = (CircleImageView) view.findViewById(R.id.civ_head_user);
            iv_comment= (ImageView) view.findViewById(R.id.iv_comment);
            iv_icon= (ImageView) view.findViewById(R.id.iv_icon);

        }
        TextView tv_date,tv_name;
        CircleImageView civ_header;
        ImageView iv_comment;
        ImageView iv_icon;
    }





}
