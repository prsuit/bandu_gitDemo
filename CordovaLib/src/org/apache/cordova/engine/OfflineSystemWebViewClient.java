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


import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import org.apache.cordova.ResourceLoader;

/**
 * This class is the WebViewClient that implements callbacks for our web view.
 * The kind of callbacks that happen here are regarding the rendering of the
 * document instead of the chrome surrounding it, such as onPageStarted(), 
 * shouldOverrideUrlLoading(), etc. Related to but different than
 * CordovaChromeClient.
 */
public class OfflineSystemWebViewClient extends SystemWebViewClient {

    static final String TAG = "OfflineSystemWebViewClient";

    public OfflineSystemWebViewClient(SystemWebViewEngine parentEngine) {
        super(parentEngine);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        WebResourceResponse result = ResourceLoader.load(this.parentEngine.cordova, url);
        if (result == null) {
            return super.shouldInterceptRequest(view, url);
        } else {
            return result;
        }
    }
}
