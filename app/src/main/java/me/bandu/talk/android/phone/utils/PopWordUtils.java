package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.DFHT.utils.UIUtils;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.WordBean;

/**
 * author by Mckiera
 * time: 2016/2/15  09:58
 * description:
 * updateTime:
 * update description:
 */
public class PopWordUtils {
    private PopupWindow ppw;
    private static PopWordUtils pwu;

    public static PopWordUtils getInstance(){
        if (pwu==null){
            pwu = new PopWordUtils();
        }
        return pwu;
    }
    public interface PopWordViewOnClick{
        void btnOnClick(WordBean word);
        void imgOnClick(WordBean word);
    }

    public PopWordUtils showWorkPop(Context context,View container, WordBean word ,PopWordViewOnClick onClick){
        View view = LayoutInflater.from(context).inflate(R.layout.word_pop, null);
        init(context,view,word,onClick);
        ppw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        ppw.setAnimationStyle(R.style.popwin_anim_style);
        ColorDrawable dw = new ColorDrawable(0xB2B2B2);
        //设置SelectPicPopupWindow弹出窗体的背景
        ppw.setBackgroundDrawable(dw);
        ppw.setOutsideTouchable(true);
        ppw.showAtLocation(container, Gravity.BOTTOM, 0, 0);
        return this;
    }
    public View showWorkPop(Context context, View container, WordBean word , PopWordViewOnClick onClick, PopupWindow.OnDismissListener dismissListener){
        try {
            View view = LayoutInflater.from(context).inflate(R.layout.word_pop, null);
            init(context,view,word,onClick);
            ppw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.getDisplayHeight(context) / 5 * 2,true);
            ppw.setAnimationStyle(R.style.popwin_anim_style);
            ColorDrawable dw = new ColorDrawable(0xB2B2B2);
            //设置SelectPicPopupWindow弹出窗体的背景
            ppw.setBackgroundDrawable(dw);
            ppw.setOutsideTouchable(true);
            ppw.showAtLocation(container, Gravity.BOTTOM, 0, 0);
            ppw.setOnDismissListener(dismissListener);
            return view;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //初始化
    private void init(Context context, View view, final WordBean word, final PopWordViewOnClick onClick) {
        ((TextView)view.findViewById(R.id.tvWork)).setText(StringUtil.getShowText(word.getQuery()));
        if (word.getBasic() != null) {
            ((TextView) view.findViewById(R.id.tvPhonetic)).setText(StringUtil.getShowText(word.getBasic().getPhonetic()));
        }
        if (word.getBasic() != null && word.getBasic().getExplains() != null)
            ((ListView)view.findViewById(R.id.lvContent)).setAdapter(new ArrayAdapter<>(context, R.layout.list_item_text, word.getBasic().getExplains()));
        view.findViewById(R.id.ivWordVoice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClick != null)
                    onClick.imgOnClick(word);
            }
        });
        view.findViewById(R.id.btnAddWord).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(onClick != null)
                    onClick.btnOnClick(word);
            }
        });
    }



}
