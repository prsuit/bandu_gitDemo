package me.bandu.talk.android.phone.activity;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.utils.ScreenUtil;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.view.SearchEditText;
/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：学生端下载练习关键字搜索
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StudentBookSearchActivity extends BaseStudentActivity implements View.OnClickListener{
    private SearchEditText title_edit;
    private RelativeLayout title_left;
    private TextView title_right;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_student_book_search;
    }

    @Override
    protected void toStart() {
        initView();
        setData();
        setListeners();
    }

    @Override
    public void initView() {
        title_edit = (SearchEditText) findViewById(R.id.title_edit);
        title_left = (RelativeLayout) findViewById(R.id.title_left);
        title_right = (TextView) findViewById(R.id.title_right);
    }

    @Override
    public void setData() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(ScreenUtil.dp2px(16,this),0,0,0);
        title_left.setLayoutParams(params);
        title_edit.setText(StringUtil.getShowText(getIntent().getStringExtra("keyword")));
    }

    @Override
    public void setListeners() {
        title_right.setOnClickListener(this);
        title_edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_ENTER) && event.getAction() == KeyEvent.ACTION_DOWN){
                /*隐藏软键盘*/
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(inputMethodManager.isActive()){
                        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    String str = title_edit.getText().toString().trim();
                        Intent intent = new Intent();
                        intent.putExtra("keyword",str);
                        setResult(RESULT_OK,intent);
                        finish();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_right:
                finish();
                break;
        }
    }
}
