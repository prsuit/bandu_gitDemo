package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.DFHT.net.EasyNetAsyncTask;
import com.DFHT.net.engine.NetCallback;
import com.DFHT.net.param.EasyNetParam;
import com.DFHT.utils.UIUtils;
import com.chivox.ChivoxConstants;
import com.DFHT.voiceengine.OnSpeechEngineLoaded;
import com.chivox.utils.ChivoxCreateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.DoneWorkActivity;
import me.bandu.talk.android.phone.bean.Detail;
import me.bandu.talk.android.phone.bean.GetKeyBean;
import me.bandu.talk.android.phone.bean.HomeWorkCatlogBean;
import me.bandu.talk.android.phone.bean.HomeWorkCatlogQuiz;
import me.bandu.talk.android.phone.dao.DaoUtils;
import me.bandu.talk.android.phone.utils.DialogUtils;

/**
 * 创建者：高楠
 * 时间：on 2015/12/10
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WorkCommiteOverDueAdapter extends BaseAdapter implements View.OnClickListener, OnSpeechEngineLoaded {
    private Context context;
    private List<HomeWorkCatlogQuiz> listBeen ;
    private Bundle data;
    private long hw_quiz_id;
    private long stu_job_id;
    private String quiz_type;
    private String description;

    public WorkCommiteOverDueAdapter(Context context, HomeWorkCatlogBean bean,Bundle data){
        this.context = context;
        this.data = data;
        stu_job_id = Long.parseLong(data.getString("stu_job_id"));
        if (DaoUtils.getInstance().isEmpty(bean)){
            listBeen = new ArrayList<>();
        }else {
            listBeen = bean.getData().getHomework().getQuizs();
        }
    }
    public void setBean(HomeWorkCatlogBean bean){
        if (DaoUtils.getInstance().isEmpty(bean)){
            listBeen = new ArrayList<>();
        }else {
            listBeen = bean.getData().getHomework().getQuizs();
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return listBeen==null?0:listBeen.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
            convertView = View.inflate(context, R.layout.item_work_catalog,null);
            holder = new ViewHolder();
            holder.tv_partname = (TextView) convertView.findViewById(R.id.tv_partname);
            holder.tv_read = (TextView) convertView.findViewById(R.id.tv_read);
            holder.tv_repead = (TextView) convertView.findViewById(R.id.tv_repead);
            holder.tv_recite = (TextView) convertView.findViewById(R.id.tv_recite);
            holder.tv_read.setOnClickListener(this);
            holder.tv_repead.setOnClickListener(this);
            holder.tv_recite.setOnClickListener(this);
            holder.tv_read.setTag(position);
            holder.tv_repead.setTag(position);
            holder.tv_recite.setTag(position);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        HomeWorkCatlogQuiz bean = listBeen.get(position);
        holder.tv_partname.setText(bean.getName());
        if (bean.getRecite()!=null&&bean.getRecite().getHw_quiz_id()!=0){
            holder.tv_recite.setVisibility(View.VISIBLE);
            if (bean.getRecite().is_done()){
                holder.tv_recite.setText("√");
            }else {
                holder.tv_recite.setText("×");
            }
        }else {
            holder.tv_recite.setVisibility(View.INVISIBLE);
        }
        if (bean.getReading()!=null&&bean.getReading().getHw_quiz_id()!=0){
            holder.tv_read.setVisibility(View.VISIBLE);
            if (bean.getReading().is_done()){
                holder.tv_read.setText("√");
            }else {
                holder.tv_read.setText("×");
            }
        }else {
            holder.tv_read.setVisibility(View.INVISIBLE);;
        }
        if (bean.getRepeat()!=null&&bean.getRepeat().getHw_quiz_id()!=0){
            holder.tv_repead.setVisibility(View.VISIBLE);
            if (bean.getRepeat().is_done()){
                holder.tv_repead.setText("√");
            }else {
                holder.tv_repead.setText("×");
            }
        }else {
            holder.tv_repead.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
    private String currentType = "";
    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        boolean flag = false;
        switch (v.getId()){
            case R.id.tv_read:
                currentType = "2";
                hw_quiz_id = listBeen.get(position).getReading().getHw_quiz_id();
                flag = listBeen.get(position).getReading().is_done();
//                UIUtils.showToastSafe(listBeen.get(position).getReading().getHw_quiz_id()+"");
                break;
            case R.id.tv_repead:
                currentType = "0";
                flag = listBeen.get(position).getRepeat().is_done();
                hw_quiz_id = listBeen.get(position).getRepeat().getHw_quiz_id();
//                UIUtils.showToastSafe(listBeen.get(position).getRepeat().getHw_quiz_id()+"");
                break;
            case R.id.tv_recite:
                currentType = "1";
                flag = listBeen.get(position).getRecite().is_done();
                hw_quiz_id = listBeen.get(position).getRecite().getHw_quiz_id();
//                UIUtils.showToastSafe(listBeen.get(position).getRecite().getHw_quiz_id()+"");
                break;
        }
        quiz_type = listBeen.get(position).getQuiz_type();
        description = listBeen.get(position).getDescription();
        if (flag){
            DialogUtils.showMyprogressDialog(context,UIUtils.getString(R.string.loding_str),false);
            if(TextUtils.isEmpty(ChivoxConstants.secretKey)){
                getKey();
            }else {
                ChivoxCreateUtil.createEnginAndAIRecorder(this);
            }
        }else {

        }
    }
    public void getKey(){
        Map map = new HashMap();
        map.put("pkg", context.getPackageName());
        EasyNetParam param = new EasyNetParam(ConstantValue.GET_KEY, map, new GetKeyBean());
        new EasyNetAsyncTask(-52, new NetCallback() {
            @Override
            public void success(Object result, int requestCode) {
                if(requestCode == -52){
                    GetKeyBean bean = (GetKeyBean) result;
                    ChivoxConstants.secretKey = bean.getData();
                    ChivoxCreateUtil.createEnginAndAIRecorder(WorkCommiteOverDueAdapter.this);
                }
            }

            @Override
            public void failed(int requestCode) {
                UIUtils.showToastSafe("请检查网络是否畅通");
                DialogUtils.hideMyprogressDialog(context);
            }
        }).execute(param);
    }
    @Override
    public void onLoadSuccess(final int state) {
        Map map = new HashMap();
        map.put("uid",data.getString("uid"));
        map.put("stu_job_id",stu_job_id);
        map.put("hw_quiz_id",hw_quiz_id);
        EasyNetParam param = new EasyNetParam(ConstantValue.HOMEWORK_DETAIL,map, new Detail());
        new EasyNetAsyncTask(0, new NetCallback() {
            @Override
            public void success(Object result, int requestCode) {
                Detail detail = (Detail) result;
//                detail.getData().setID(stu_job_id,hw_quiz_id);
                Intent intent = new Intent(context,DoneWorkActivity.class);
                intent.putExtra("state",state);
                intent.putExtra("detail",detail);
                intent.putExtra("description", description);
                intent.putExtra("quiz_type", quiz_type);
                intent.putExtra("currentType",currentType);
                DialogUtils.hideMyprogressDialog(context);//关闭dialog
                context.startActivity(intent);
            }
            @Override
            public void failed(int requestCode) {
                DialogUtils.hideMyprogressDialog(context);//关闭dialog
            }
        }).execute(param);
    }

    @Override
    public void onLoadError() {
        DialogUtils.hideMyprogressDialog(context);//关闭dialog
    }

    public class ViewHolder{
        TextView tv_read,tv_repead,tv_recite,tv_partname;
    }
}
