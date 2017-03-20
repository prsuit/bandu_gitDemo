package com.justep.cordova.plugin.engine.tencent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewEngine;

/**
 * Created by 007slm on 2016-8-2.
 */
public class TencentWebView extends WebView implements CordovaWebViewEngine.EngineView{
    private TencentWebViewClient viewClient;
    TencentWebChromeClient chromeClient;
    private TencentWebViewEngine parentEngine;
    private CordovaInterface cordova;

    public TencentWebView(Context context) {
        this(context, null);
    }

    public TencentWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // Package visibility to enforce that only SystemWebViewEngine should call this method.
    void init(TencentWebViewEngine parentEngine, CordovaInterface cordova) {
        this.cordova = cordova;
        this.parentEngine = parentEngine;
        if (this.viewClient == null) {
            setWebViewClient(new OfflineTencentWebViewClient(parentEngine));
        }

        if (this.chromeClient == null) {
            setWebChromeClient(new TencentWebChromeClient(parentEngine));
        }
    }

    @Override
    public CordovaWebView getCordovaWebView() {
        return parentEngine != null ? parentEngine.getCordovaWebView() : null;
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        viewClient = (TencentWebViewClient)client;
        super.setWebViewClient(client);
    }

    @Override
    public void setWebChromeClient(WebChromeClient client) {
        chromeClient = (TencentWebChromeClient)client;
        super.setWebChromeClient(client);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Boolean ret = parentEngine.client.onDispatchKeyEvent(event);
        if (ret != null) {
            return ret.booleanValue();
        }
        return super.dispatchKeyEvent(event);
    }

}
