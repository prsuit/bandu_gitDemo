package me.bandu.talk.android.phone.view;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.bandu.talk.android.phone.R;

/**
 * 创建者：gaoye
 * 时间：2016/3/28 15:24
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class SearchEditText extends RelativeLayout {
    private ImageView iv_searchLeft,iv_clear;
    private EditText et_input;
    private TextView tv_input;
    public SearchEditText(Context context) {
        this(context,null);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addView(LayoutInflater.from(getContext()).inflate(R.layout.layout_search_edittext,null));
        init();
        setListeners();
    }

    private void setListeners() {
        iv_clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                et_input.setText("");
                et_input.setHint("请输入练习素材的名称");
            }
        });
    }

    private void init() {
        iv_searchLeft = (ImageView) findViewById(R.id.iv_searchLeft);
        iv_clear = (ImageView) findViewById(R.id.iv_clear);
        et_input = (EditText) findViewById(R.id.et_input);
        tv_input = (TextView) findViewById(R.id.tv_input);
    }

    public void editTextHide(){
        et_input.setVisibility(GONE);
        tv_input.setVisibility(VISIBLE);
    }

    public void editTextShow(){
        et_input.setVisibility(VISIBLE);
        tv_input.setVisibility(GONE);
    }

    public ImageView getClearImageView(){
        return iv_clear;
    }

    public EditText getEditText(){
        return et_input;
    }

    public void setCursorVisible(boolean isCursorVisible){
        et_input.setCursorVisible(isCursorVisible);
    }

    public Editable getText() {
        return et_input.getText();
    }

    public void setText(String str){
        if (et_input.getVisibility() == VISIBLE)
            et_input.setText(str);
        if (tv_input.getVisibility() == VISIBLE)
            tv_input.setText(str);
    }

    public void setOnKeyListener(View.OnKeyListener onKeyListener){
        et_input.setOnKeyListener(onKeyListener);
    }
}
