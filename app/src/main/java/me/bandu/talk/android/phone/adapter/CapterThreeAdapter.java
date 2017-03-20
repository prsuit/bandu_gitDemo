//package me.bandu.talk.android.phone.adapter;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.List;
//
//import me.bandu.talk.android.phone.R;
//import me.bandu.talk.android.phone.activity.WorkPreviewActivity;
//import me.bandu.talk.android.phone.bean.HomeWorkCatlogQuiz;
//import me.bandu.talk.android.phone.bean.Quiz;
//import me.bandu.talk.android.phone.viewholder.CapterThreeHolder;
//
///**
// * 创建者：高楠
// * 时间：2015/11/23
// * 类描述：选择教材第三步
// * 修改人：
// * 修改时间：
// * 修改备注：
// */
//public class CapterThreeAdapter extends RecyclerView.Adapter<CapterThreeHolder> implements View.OnClickListener{
//    Context context;
//    List<Quiz> list;
//    Bundle bundle;
//    boolean isAdd;
//    public CapterThreeAdapter(Context context, List<Quiz> list, Bundle bundle, boolean isAdd){
//        this.context = context;
//        this.list = list;
//        this.bundle = bundle;
//        this.isAdd = isAdd;
//    }
//    @Override
//    public CapterThreeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(context).inflate(R.layout.list_item_t,null);
//        CapterThreeHolder holder = new CapterThreeHolder(v);
//        holder.tv.setText(list.get(viewType).getQuiz_name());
//        v.setTag(R.layout.list_item_t,viewType);
//        v.setOnClickListener(this);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(CapterThreeHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return list==null?0:list.size();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }
//
//    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(context, WorkPreviewActivity.class);
//        if (isAdd){
//            intent.putExtra("quiz_id",list.get((int)v.getTag(R.layout.list_item_t)).getQuiz_id());
//            intent.putExtra("quiz_name",list.get((int)v.getTag(R.layout.list_item_t)).getQuiz_name());
//            ((Activity)context).setResult(WorkPreviewActivity.ADD_QUIZ,intent);
//            ((Activity) context).finish();
//        }else {
//            bundle.putString("quiz_id",list.get((int)v.getTag(R.layout.list_item_t)).getQuiz_id());
//            bundle.putString("quiz_name",list.get((int)v.getTag(R.layout.list_item_t)).getQuiz_name());
//            intent.putExtra("data",bundle);
//            context.startActivity(intent);
//        }
//    }
//}
