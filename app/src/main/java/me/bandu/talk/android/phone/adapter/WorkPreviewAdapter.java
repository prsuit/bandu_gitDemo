package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.Quiz;
import me.bandu.talk.android.phone.viewholder.WorkPreviewHolder;

/**
 * 创建者：高楠
 * 时间：2015/11/25
 * 类描述：作业预览
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WorkPreviewAdapter extends RecyclerView.Adapter<WorkPreviewHolder>{
    private Context context;
    private List<Quiz> list;
    public WorkPreviewAdapter(Context context, Quiz quiz){
        this.context = context;
        this.list = new ArrayList<>();
        this.list.add(quiz);
    }
    public void AddQuiz(Quiz quiz){
        for (int i = 0;i<list.size();i++){
            if (list.get(i).getQuiz_id().equals(quiz.getQuiz_id())){
                quiz = list.get(i);
                list.remove(i);
                list.add(0, quiz);
                notifyDataSetChanged();
                return ;//TODO排序
            }
        }
        if (quiz!=null)
            list.add(0, quiz);
        Log.e("AddQuiz",list!=null?list.toString():"");
        notifyDataSetChanged();
    }
    public int getRead(){
        return list.get(0).getRead_count();
    }
    public int  getRepead(){
        return list.get(0).getRepeat_count();
    }
    public boolean getRecite(){
        return list.get(0).isRecit();
    }
    public void setRead(int count){
        list.get(0).setRead_count(count);
    }
    public void setRepead(int count){
        list.get(0).setRepeat_count(count);
    }
    public void setRecite(boolean flag){
        list.get(0).setRecit(flag);
    }
    public boolean isOK(){
        if (list.get(0).getRead_count()==0&&list.get(0).getRepeat_count()==0&&!list.get(0).isRecit()){
            return false;
        }
        return  true;
    }
    @Override
    public WorkPreviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item_text,null);
        WorkPreviewHolder holder = new WorkPreviewHolder(view);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(WorkPreviewHolder holder, int position) {
        if (position==0){
            holder.tv.setBackgroundResource(R.drawable.grid_item_click);
            holder.tv.setTextColor(Color.WHITE);
        }else {
            holder.tv.setTextColor(Color.GRAY);
            holder.tv.setBackgroundResource(R.drawable.grid_item_normal);
        }
        holder.tv.setText(list.get(position).getQuiz_name());
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
    public String getQuizs2(){
        //{"quiz_id":2,"type":1,"times":4}],"uids":[20101,20102,20103]}
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        for(int i = 0 ;i<list.size();i++){
            Quiz qz = list.get(i);
            if(qz.getRead_count()!=0){
                buffer.append(",{\"quiz_id\":\""+qz.getQuiz_id()+"\",\"type\":\"2\",\"times\":\""+qz.getRead_count()+"\"}");
            }
            if(!qz.isRecit()){
                buffer.append(",{\"quiz_id\":\""+qz.getQuiz_id()+"\",\"type\":\"1\",\"times\":\""+(qz.isRecit()?1:0)+"\"}");
            }
            if(qz.getRepeat_count()!=0){
                buffer.append(",{\"quiz_id\":\""+qz.getQuiz_id()+"\",\"type\":\"0\",\"times\":\""+qz.getRepeat_count()+"\"}");
            }
        }
        buffer.append("]");
        if(!buffer.equals("[]")){
            buffer.delete(1, 2);
        }
        return buffer.toString();
    }
    public List<Map<String,String>> getQuizs(){
        List<Map<String,String>> maps = new ArrayList<>();
        for(int i = 0 ;i<list.size();i++){
            Quiz qz = list.get(i);
            if(qz.getRead_count()!=0){
                Map<String,String> map = new HashMap<>();
                map.put("quiz_id",qz.getQuiz_id());
                map.put("type","2");
                map.put("times",qz.getRead_count()+"");
                maps.add(map);
            }
            if(qz.isRecit()){
                Map<String,String> map = new HashMap<>();
                map.put("quiz_id",qz.getQuiz_id());
                map.put("type","1");
                map.put("times","1");
                maps.add(map);
            }
            if(qz.getRepeat_count()!=0){
                Map<String,String> map = new HashMap<>();
                map.put("quiz_id",qz.getQuiz_id());
                map.put("type","0");
                map.put("times",qz.getRepeat_count()+"");
                maps.add(map);
            }
        }
        return maps;
    }
    public ArrayList<String> getSelectedQuizs(){
        ArrayList<String> integerArrayList = new ArrayList<>();
        for (int i = 0;i<list.size();i++){
            integerArrayList.add(list.get(i).getQuiz_id());
        }
        return integerArrayList;
    }
    public List<Quiz> getData(){
        return list;
    }
}
