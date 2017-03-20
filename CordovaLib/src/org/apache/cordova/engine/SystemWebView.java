/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/

package org.apache.cordova.engine;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewEngine;
import org.apache.cordova.LOG;

/**
 * Custom WebView subclass that enables us to capture events needed for Cordova.
 */
public class SystemWebView extends WebView implements CordovaWebViewEngine.EngineView {
    private SystemWebViewClient viewClient;
    SystemWebChromeClient chromeClient;
    private SystemWebViewEngine parentEngine;
    private CordovaInterface cordova;

    public SystemWebView(Context context) {
        this(context, null);
    }



    public SystemWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    class CordovaDetect{
        CordovaWebView appView;
        CordovaDetect(CordovaWebView appView){
            this.appView = appView;
        }
        @JavascriptInterface
        public void setExist(boolean exist) {
            LOG.d("CordovaDetect", "通过js判断cordova环境");
            if(!exist){
                LOG.d("CordovaDetect", "通过js判断cordova不存在");
                this.appView.postMessage("splashscreen", "hide");
            }
        }
    }

    // Package visibility to enforce that only SystemWebViewEngine should call this method.
    void init(SystemWebViewEngine parentEngine, CordovaInterface cordova) {
        this.cordova = cordova;
        this.parentEngine = parentEngine;
        if (this.viewClient == null) {
            setWebViewClient(new OfflineSystemWebViewClient(parentEngine));
        }

        if (this.chromeClient == null) {
            setWebChromeClient(new SystemWebChromeClient(parentEngine));
        }
        this.addJavascriptInterface(new CordovaDetect(this.parentEngine.parentWebView), "_cordovaDetect");
    }

    @Override
    public CordovaWebView getCordovaWebView() {
        return parentEngine != null ? parentEngine.getCordovaWebView() : null;
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        viewClient = (SystemWebViewClient)client;
        super.setWebViewClient(client);
    }

    @Override
    public void setWebChromeClient(WebChromeClient client) {
        chromeClient = (SystemWebChromeClient)client;
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
