package me.bandu.talk.android.phone.activity;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.NewWordExpandableListViewAdapter;
import me.bandu.talk.android.phone.bean.ExpandableNewWord;
import me.bandu.talk.android.phone.middle.WordsMiddle;

/**
 * author by Mckiera
 * time: 2016/2/19  14:16
 * description:
 * updateTime:
 * update description:
 */
public class NewWordActivity extends BaseStudentActivity {
    @Bind(R.id.elvNewWord)
    ExpandableListView elv;
    @Bind(R.id.tvEmpty)
    TextView tvEmpty;
    @Nullable
    @Bind(R.id.title_right)
     RelativeLayout title_right;
    @Bind(R.id.more)
    TextView more;
    @Bind(R.id.btnDelete)
    Button btnDelete;
    private ExpandableNewWord newWord;

    private  WordsMiddle middle;

    private NewWordExpandableListViewAdapter adapter;

    @Override
    public void initView() {
        title_right.setVisibility(View.VISIBLE);
        more.setVisibility(View.VISIBLE);
        more.setText("编辑");
    }

    @Override
    protected String setTitle() {
        return "生词本";
    }

    @Override
    public void setData() {
        middle = new WordsMiddle(this, this);
        middle.wordList("1","1000");
    }

    @Override
    public void successFromMid(Object... obj) {
        super.successFromMid(obj);
        int code = (int) obj[1];
        switch (code){
            case WordsMiddle.WORDS_LIST:
                LogUtils.i("成功回来了数据 : "+obj.toString());
                newWord = (ExpandableNewWord) obj[0];
                if(newWord != null && newWord.getStatus() == 1){
                    elv.setGroupIndicator(null);
                    adapter = new NewWordExpandableListViewAdapter(this, newWord, elv);
                    elv.setAdapter(adapter);
                    if(newWord.getData().getList().size()>0){
                        tvEmpty.setVisibility(View.GONE);
                        more.setVisibility(View.VISIBLE);
                        elv.expandGroup(0);
                    }else{
                        tvEmpty.setVisibility(View.VISIBLE);
                        more.setVisibility(View.GONE);
                    }
                }else{
                    //无生词本.空白页面.
                    tvEmpty.setVisibility(View.VISIBLE);
                    more.setVisibility(View.GONE);

                }
                break;
            case WordsMiddle.DELETE_WORDS:
                BaseBean bean = (BaseBean) obj[0];
                if(bean.getStatus() == 1){
                    //删除成功
                    middle.wordList("1","10000");
                    more.setText("编辑");
                    btnDelete.setVisibility(View.GONE);
                }
                break;
        }

    }

    @Override
    public void failedFrom(Object... obj) {
        super.failedFrom(obj);
        LogUtils.d("失败回来了数据 : "+obj.toString());
        more.setVisibility(View.GONE);
    }

    @Override
    public void setListeners() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_newword;
    }

    @Override
    protected void toStart() {
        initView();
        setData();
    }
    @OnClick({R.id.more, R.id.btnDelete})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.more:
                if(adapter != null){
                    NewWordExpandableListViewAdapter.EditType type = adapter.getType();
                    switch (type){
                        case EDITABLE:
                            adapter.setType(NewWordExpandableListViewAdapter.EditType.UNEDITABLE);
                            more.setText("完成");
                            btnDelete.setVisibility(View.VISIBLE);
                            break;
                        case UNEDITABLE:
                            adapter.setType(NewWordExpandableListViewAdapter.EditType.EDITABLE);
                            more.setText("编辑");
                            btnDelete.setVisibility(View.GONE);
                            break;
                    }
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.btnDelete:
                if(adapter != null){
                    Map<Integer, Boolean> deleteMap = adapter.getDeleteMap();
                    List<String> deleteIDs = new ArrayList<>();
                    if(deleteMap != null) {
                        for (int i = 0; i < newWord.getData().getList().size(); i++) {
                            try {
                                if (deleteMap.get(i)) {
                                    deleteIDs.add(newWord.getData().getList().get(i).getWord_id());
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                LogUtils.e("您的Map出问题了 deleteMap:"+deleteMap);
                            }

                        }
                    }
                    if(deleteIDs.size() > 0){
                        middle.deleteWord(deleteIDs);
                    }
                }
                break;
        }
    }
}
