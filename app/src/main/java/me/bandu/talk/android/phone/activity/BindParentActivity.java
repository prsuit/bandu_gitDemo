package me.bandu.talk.android.phone.activity;

import android.widget.TextView;

import butterknife.Bind;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.utils.Utils;
/**
 * 创建者：tg
 * 类描述：绑定家长
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class BindParentActivity extends BaseAppCompatActivity {

    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.tv10)
    TextView bindCodeTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_parent;
    }

    @Override
    protected void toStart() {
        titleTv.setText(R.string.bind_parent);
        bindCodeTv.setText(Utils.getUserInfo(this).getBindcode());
    }
}
