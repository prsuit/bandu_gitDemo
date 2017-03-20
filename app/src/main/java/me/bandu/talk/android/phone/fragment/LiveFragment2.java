package me.bandu.talk.android.phone.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.justep.cordova.plugin.engine.tencent.TencentWebView;
import com.justep.cordova.plugin.engine.tencent.TencentWebViewEngine;

import org.apache.cordova.Config;
import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaInterfaceImpl;
import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.PluginEntry;

import java.util.ArrayList;

import me.bandu.talk.android.phone.activity.LoginActivity;

/**
 * Created by SH on 2016/12/27.
 */

public class LiveFragment2 extends Fragment{
    private CordovaWebView webView;
   // SystemWebView systemWebView;
    TencentWebView systemWebView;
    // Read from config.xml:
    protected CordovaPreferences preferences;
    protected String launchUrl;
    protected ArrayList<PluginEntry> pluginEntries;
    protected CordovaInterfaceImpl cordovaInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // LayoutInflater localInflater = inflater.cloneInContext(new CordovaContext(getActivity()));
//        View v = localInflater.inflate(R.layout.fragment_home_cordova, null);
//        systemWebView = (TencentWebView) v.findViewById(R.id.cordovaWebView);
        systemWebView = new TencentWebView(getActivity());
        loadConfig();
        cordovaInterface =  new CordovaInterfaceImpl(getActivity());
        if(savedInstanceState != null)
            cordovaInterface.restoreInstanceState(savedInstanceState);
        webView = new CordovaWebViewImpl(new TencentWebViewEngine(systemWebView));
        webView.init(new CordovaInterfaceImpl(getActivity()), pluginEntries, preferences);
        cordovaInterface.onCordovaInit(webView.getPluginManager());
        Log.e("error", "url:--> "+ launchUrl);
        webView.loadUrl(launchUrl);
        //修改直播颜色
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:" + "window.localStorage.setItem('role'," + LoginActivity.role  + ");");
            }
        }, 500);
        return systemWebView;
    }

    protected void loadConfig() {
        ConfigXmlParser parser = new ConfigXmlParser();
        parser.parse(getActivity());
        preferences = parser.getPreferences();
        preferences.setPreferencesBundle(getActivity().getIntent().getExtras());
        launchUrl = parser.getLaunchUrl();
        pluginEntries = parser.getPluginEntries();
        Config.parser = parser;
    }

    //修改学生、老师端的直播颜色
    private void injectJS (final String js) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:" + js);
            }
        }, 500);

    }


}
