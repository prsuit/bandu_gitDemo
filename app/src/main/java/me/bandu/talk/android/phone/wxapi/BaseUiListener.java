package me.bandu.talk.android.phone.wxapi;

import com.DFHT.utils.UIUtils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.activity.CombinaRecordActivity;


//分享QQ，QQ空间回掉
public class BaseUiListener implements IUiListener {
    @Override
    public void onComplete(Object response) {
        if (GlobalParams.SHARE_TYPE == 1)
            UIUtils.showToastSafe("分享成功");
    }

    @Override
    public void onError(UiError uiError) {
        if (GlobalParams.SHARE_TYPE == 1)
            UIUtils.showToastSafe("error--->");
    }

    @Override
    public void onCancel() {
        if (GlobalParams.SHARE_TYPE == 1 && CombinaRecordActivity.data != null)
            UIUtils.showToastSafe("分享取消");
    }

}