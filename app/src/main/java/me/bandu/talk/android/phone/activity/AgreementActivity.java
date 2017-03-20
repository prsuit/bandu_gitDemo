package me.bandu.talk.android.phone.activity;

import android.webkit.WebView;
import android.widget.TextView;

import butterknife.Bind;
import me.bandu.talk.android.phone.R;
/**
 * 创建者：tg
 * 类描述：用户协议
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class AgreementActivity extends BaseAppCompatActivity {

    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.webview)
    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_members;
    }

    @Override
    protected void toStart() {
        titleTv.setText(R.string.user_agree);
        webView.getSettings().setJavaScriptEnabled(false);
        //找到Html文件，也可以用网络上的文件
        webView.getSettings().setDefaultTextEncodingName("utf-8") ;
        webView.loadUrl("file:///android_asset/agreement.html");
    }
}
