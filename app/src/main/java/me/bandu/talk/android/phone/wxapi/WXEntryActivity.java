package me.bandu.talk.android.phone.wxapi;

/**
 * 创建者:taoge
 * 时间：2016/5/9
 * 类描述：
 * 修改人:taoge
 * 修改时间：2016/5/9
 * 修改备注：
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.DFHT.utils.UIUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import me.bandu.talk.android.phone.ConstantValue;

/** 微信客户端回调activity */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, ConstantValue.WX_APP_ID, true);
        api.registerApp(ConstantValue.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq arg0) {
        switch (arg0.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                Log.e("111111", "ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX0");
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                Log.e("111111", "ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX1");
                break;
            default:
                break;
        }
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                UIUtils.showToastSafe("分享成功");
                //分享成功
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                UIUtils.showToastSafe("分享取消");
                //分享取消
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                UIUtils.showToastSafe("分享拒绝");
                //分享拒绝
                finish();
                break;
        }
        finish();
    }
}