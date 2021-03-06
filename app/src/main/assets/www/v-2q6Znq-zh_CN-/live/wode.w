<!DOCTYPE HTML>
<html style="width:100%;height:100%" class="no-js">
    <head><meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="format-detection" content="telephone=no">
        <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
        <script src="../system/lib/base/modernizr-2.8.3.min.js"></script>
		<script id="__varReplace">
    	
    	    	window.__justep = window.__justep || {};
				window.__justep.isDebug = false;
				window.__justep.__packageMode = "1";
				window.__justep.__isPackage = true;;
				window.__justep.url = location.href;
				window.__justep.versionInfo = {};
		 
    	</script>
        <script id="__updateVersion">
        
				(function(url, mode){
					if (("@"+"mode@") === mode) mode = "3";
					if (("@"+"versionUrl@") === url) url = "system/service/common/app.j";
					if ((mode!=="1" && (mode!="2") && (mode!="3"))) return;
					var async = (mode=="1");
					var x5Version = "noApp";
					var x5AppAgents = /x5app\/([0-9.]*)/.exec(navigator.userAgent);
					if(x5AppAgents && x5AppAgents.length > 1){
					   	x5Version = x5AppAgents[1] || "";
					} 
					function createXhr(){
						try {	
							return new XMLHttpRequest();
						}catch (tryMS) {	
							var version = ["MSXML2.XMLHttp.6.0",
							               "MSXML2.XMLHttp.3.0",
							               "MSXML2.XMLHttp",
							               "Miscrosoft.XMLHTTP"];
							for(var i = 0; i < version.length; i++){
								try{
							    	return new ActiveXObject(version[i]);
								}catch(e){}
							}
						}
						throw new Error("您的系统或浏览器不支持XHR对象！");
					}
					
					function createGuid(){	
						var guid = '';	
						for (var i = 1; i <= 32; i++){		
							var n = Math.floor(Math.random()*16.0).toString(16);		
							guid += n;		
							if((i==8)||(i==12)||(i==16)||(i==20))			
								guid += '-';	
						}	
						return guid;
					}
					
					function parseUrl(href){
						href = href.split("#")[0];
						var items = href.split("?");
						href = items[0];
						var query = items[1] || "";
						items = href.split("/");
						var baseItems = [];
						var pathItems = [];
						var isPath = false;
						for (var i=0; i<items.length; i++){
							if (mode == "3"){
								if (items[i] && (items[i].indexOf("v_") === 0) 
										&& (items[i].indexOf("l_") !== -1)
										&& (items[i].indexOf("s_") !== -1)
										&& (items[i].indexOf("d_") !== -1)
										|| (items[i]=="v_")){
									isPath = true;
									continue;
								}
							}else{
								if (items[i] && (items[i].indexOf("v-")===0) && (items[i].charAt(items[i].length-1)=="-") ){
									isPath = true;
									continue;
								}
							}
							if (isPath){
								pathItems.push(items[i]);
							}else{
								baseItems.push(items[i]);	
							}
							
						}
						var base = baseItems.join("/");
						if (base.charAt(base.length-1)!=="/") base += "/";
						
						var path = pathItems.join("/");
						if (path.charAt(0) !== "/") path = "/" + path;
						return [base, path, query];
					}
					
					
					var items = parseUrl(window.location.href);
					var base = items[0];
					var path = items[1];
					var query = items[2];
					var xhr = createXhr();
					url += ((url.indexOf("?")!=-1) ? "&" : "?") +"_=" + createGuid();
					if (mode === "3"){
						url += "&url=" + path;
						if (query)
							url += "&" + query;
					}
					xhr.open('GET', base + url, async);
					
					if (async){
						xhr.onreadystatechange=function(){
							if((xhr.readyState == 4) && (xhr.status == 200) && xhr.responseText){
								var versionInfo = JSON.parse(xhr.responseText);
								window.__justep.versionInfo = versionInfo;
								window.__justep.versionInfo.baseUrl = base;
								if (query){
									path = path + "?" + query;
								}
								path = versionInfo.resourceInfo.indexURL || path; /* 如果返回indexPath则使用indexPath，否则使用当前的path */
								var indexUrl = versionInfo.baseUrl + versionInfo.resourceInfo.version + path;
								versionInfo.resourceInfo.indexPageURL = indexUrl;
								if(versionInfo.resourceInfo.resourceUpdateMode != "md5"){
									if (window.__justep.url.indexOf(versionInfo.resourceInfo.version) == -1){
										versionInfo.resourceInfo.isNewVersion = true;
									};
								}
							}
						}
					}
					
					try{
						xhr.send(null);
						if (!async && (xhr.status == 200) && xhr.responseText){
							var versionInfo = JSON.parse(xhr.responseText);
							window.__justep.versionInfo = versionInfo;
							window.__justep.versionInfo.baseUrl = base;
							if ((mode==="3") && window.__justep.isDebug){
								/* 模式3且是调试模式不重定向 */
							}else{
								if (query){
									path = path + "?" + query;
								}
								if(versionInfo.resourceInfo.resourceUpdateMode == "md5"){
									path = versionInfo.resourceInfo.indexURL || path; /* 如果返回indexPath则使用indexPath，否则使用当前的path */
									var indexUrl = versionInfo.baseUrl + versionInfo.resourceInfo.version + path;
									versionInfo.resourceInfo.indexPageURL = indexUrl; 
								}else if (versionInfo.resourceInfo && versionInfo.resourceInfo.version && (window.__justep.url.indexOf(versionInfo.resourceInfo.version) == -1)){
									path = versionInfo.resourceInfo.indexURL || path; /* 如果返回indexPath则使用indexPath，否则使用当前的path */
									var indexUrl = versionInfo.baseUrl + versionInfo.resourceInfo.version + path;
									window.location.href = indexUrl;
								}
							}
						}
					}catch(e2){}
				}("appMetadata_in_server.json", "1"));
                 
        </script>
    <link rel="stylesheet" href="../system/components/bootstrap.min.css" include="$model/UI2/system/components/bootstrap/lib/css/bootstrap,$model/UI2/system/components/bootstrap/lib/css/bootstrap-theme"><link rel="stylesheet" href="../system/components/comp.min.css" include="$model/UI2/system/components/justep/lib/css2/dataControl,$model/UI2/system/components/justep/input/css/datePickerPC,$model/UI2/system/components/justep/messageDialog/css/messageDialog,$model/UI2/system/components/justep/lib/css3/round,$model/UI2/system/components/justep/input/css/datePicker,$model/UI2/system/components/justep/row/css/row,$model/UI2/system/components/justep/attachment/css/attachment,$model/UI2/system/components/justep/barcode/css/barcodeImage,$model/UI2/system/components/bootstrap/dropdown/css/dropdown,$model/UI2/system/components/justep/dataTables/css/dataTables,$model/UI2/system/components/justep/contents/css/contents,$model/UI2/system/components/justep/common/css/forms,$model/UI2/system/components/justep/locker/css/locker,$model/UI2/system/components/justep/menu/css/menu,$model/UI2/system/components/justep/scrollView/css/scrollView,$model/UI2/system/components/justep/loadingBar/loadingBar,$model/UI2/system/components/justep/dialog/css/dialog,$model/UI2/system/components/justep/bar/css/bar,$model/UI2/system/components/justep/popMenu/css/popMenu,$model/UI2/system/components/justep/lib/css/icons,$model/UI2/system/components/justep/lib/css4/e-commerce,$model/UI2/system/components/justep/toolBar/css/toolBar,$model/UI2/system/components/justep/popOver/css/popOver,$model/UI2/system/components/justep/panel/css/panel,$model/UI2/system/components/bootstrap/carousel/css/carousel,$model/UI2/system/components/justep/wing/css/wing,$model/UI2/system/components/bootstrap/scrollSpy/css/scrollSpy,$model/UI2/system/components/justep/titleBar/css/titleBar,$model/UI2/system/components/justep/lib/css1/linear,$model/UI2/system/components/justep/numberSelect/css/numberList,$model/UI2/system/components/justep/list/css/list,$model/UI2/system/components/justep/dataTables/css/dataTables"></head>
	
    <body style="width:100%;height:100%;margin: 0;">
        <script intro="none"></script>
    	<div id="applicationHost" class="applicationHost" style="width:100%;height:100%;" __component-context__="block"><div component="$model/UI2/system/components/justep/window/window" design="device:m;" xid="window" class="window cfYnmMr" data-bind="component:{name:'$model/UI2/system/components/justep/window/window'}" __cid="cfYnmMr" components="$model/UI2/system/components/justep/model/model,$model/UI2/system/components/justep/loadingBar/loadingBar,$model/UI2/system/components/justep/button/button,$model/UI2/system/components/justep/titleBar/titleBar,$model/UI2/system/components/justep/panel/child,$model/UI2/system/components/justep/windowDialog/windowDialog,$model/UI2/system/components/justep/window/window,$model/UI2/system/components/justep/messageDialog/messageDialog,$model/UI2/system/components/justep/panel/panel,">
  <style>.border.cfYnmMr{border: 2px solid rgb(255, 255, 255)} .loginstatus.cfYnmMr{margin-left: 15%; width: 70%; border-style: none none none none} .registerstatus.cfYnmMr{background-color: ff0033; width: 35%; border-style: none none none none} .cancelstatus.cfYnmMr{background-color: rgb(255, 0, 0); margin-left: 15%; width: 70%; border-style: none none none none} .loginstatus1.cfYnmMr{background-color: rgb(0, 168, 255); margin-left: 15%; width: 70%; border-style: none none none none} .cancelstatus1.cfYnmMr{background-color: rgb(255, 0, 0); margin-left: 15%; width: 70%; border-style: none none none none}</style>  
  <div component="$model/UI2/system/components/justep/model/model" xid="model" style="display:none" data-bind="component:{name:'$model/UI2/system/components/justep/model/model'}" data-events="onActive:modelActive;onLoad:modelLoad" __cid="cfYnmMr" class="cfYnmMr"></div>  
  <span component="$model/UI2/system/components/justep/windowDialog/windowDialog" xid="windowDialog1" style="top:14px;left:90px;" data-bind="component:{name:'$model/UI2/system/components/justep/windowDialog/windowDialog'}" data-events="onReceive:windowDialog1Receive" data-config="{&#34;src&#34;:&#34;$model/UI2/live/login.w&#34;}" __cid="cfYnmMr" class="cfYnmMr">
    <div class="x-dialog-overlay cfYnmMr" __cid="cfYnmMr"></div>
    <div class="x-dialog cfYnmMr" style="display:none;" __cid="cfYnmMr">
      <div class="x-dialog-title cfYnmMr" __cid="cfYnmMr">
        <button class="close cfYnmMr" __cid="cfYnmMr">
          <span __cid="cfYnmMr" class="cfYnmMr">×</span>
        </button>
        <div class="x-dialog-title-text cfYnmMr" __cid="cfYnmMr"></div>
      </div>
      <div class="x-dialog-body cfYnmMr" __cid="cfYnmMr"></div>
    </div>
  </span>
  <div component="$model/UI2/system/components/justep/panel/panel" class="x-panel x-full pcaaiEne-iosstatusbar cfYnmMr" xid="panel1" data-bind="component:{name:'$model/UI2/system/components/justep/panel/panel'}" __cid="cfYnmMr"> 
    <div class="x-panel-top cfYnmMr" xid="top1" component="$model/UI2/system/components/justep/panel/child" data-bind="component:{name:'$model/UI2/system/components/justep/panel/child'}" __cid="cfYnmMr"> 
      <div component="$model/UI2/system/components/justep/titleBar/titleBar" class="x-titlebar cfYnmMr" style="background-color:white;" xid="titleBar1" data-bind="component:{name:'$model/UI2/system/components/justep/titleBar/titleBar'}" data-config="{&#34;title&#34;:&#34;个人中心&#34;}" __cid="cfYnmMr"> 
        <div class="x-titlebar-left cfYnmMr" __cid="cfYnmMr"> 
          <a component="$model/UI2/system/components/justep/button/button" class="btn btn-link btn-only-icon cfYnmMr" xid="backBtn" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:{operation:'window.close'}" data-config="{&#34;icon&#34;:&#34;icon-chevron-left&#34;,&#34;label&#34;:&#34;&#34;}" __cid="cfYnmMr"> 
            <i class="icon-chevron-left cfYnmMr" __cid="cfYnmMr"></i>  
            <span __cid="cfYnmMr" class="cfYnmMr"></span> 
          </a> 
        </div>  
        <div class="x-titlebar-title cfYnmMr" style="font-weight:normal;" __cid="cfYnmMr">
          <span xid="span1" __cid="cfYnmMr" class="cfYnmMr"></span>个人中心
        </div>  
        <div class="x-titlebar-right  cfYnmMr" __cid="cfYnmMr">
          <div class="empty cfYnmMr" __cid="cfYnmMr"></div>
          <a component="$model/UI2/system/components/justep/button/button" class="btn btn-link cfYnmMr" xid="enroll" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:enrollClick" __cid="cfYnmMr"> 
            <i xid="i3" __cid="cfYnmMr" class="cfYnmMr"></i>  
            <span xid="span4" __cid="cfYnmMr" class="cfYnmMr"></span>
          </a>
        </div> 
      </div> 
    </div>  
    <div class="x-panel-content cfYnmMr" xid="content1" component="$model/UI2/system/components/justep/panel/child" data-bind="component:{name:'$model/UI2/system/components/justep/panel/child'}" __cid="cfYnmMr"> 
      <div xid="div6" style="text-align:center;z-index:2000;position:absolute;width:100%;top:40px;" __cid="cfYnmMr" class="cfYnmMr">
        <img src="../live/img/192.png" alt="" xid="image6" style="width:80px;" class="img-circle cfYnmMr" __cid="cfYnmMr">  
        <h3 xid="h31" style="color:#9ed04d;" class="hidden cfYnmMr" __cid="cfYnmMr">直播ID:18258393397</h3>
        <a component="$model/UI2/system/components/justep/button/button" class="btn btn-link btn-xs btn-only-icon   hidden cfYnmMr" xid="button4" style="background-color:white;color:#2cc17b;border-radius:50px;margin-top:-120px;" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-config="{&#34;icon&#34;:&#34;glyphicon glyphicon-camera&#34;,&#34;label&#34;:&#34;button&#34;}" __cid="cfYnmMr"> 
          <i xid="i4" class="glyphicon glyphicon-camera cfYnmMr" __cid="cfYnmMr"></i>  
          <span xid="span13" __cid="cfYnmMr" class="cfYnmMr"></span>
        </a> 
      </div>
      <div xid="div5" style="position:absolute;left:0;top:0;overflow:hidden;height:200px;width:100%;border-width:1px;" __cid="cfYnmMr" class="cfYnmMr"></div>  
      <div xid="div7" style="margin-top:200px;" __cid="cfYnmMr" class="cfYnmMr">
        <ul class="list-group x-tuniu cfYnmMr" xid="ul1" __cid="cfYnmMr"> 
          <li xid="li1" class="list-group-item cfYnmMr" __cid="cfYnmMr" data-bind="event:{click:$model._callModelFn.bind($model, 'li1Click')}">我的课程
            <span class="pull-right text-muted cfYnmMr" xid="span17" __cid="cfYnmMr">&gt;</span> 
          </li>  
          <li class="list-group-item cfYnmMr" xid="li2" style="border-bottom:10px solid #f2f2f2;" __cid="cfYnmMr" data-bind="event:{click:$model._callModelFn.bind($model, 'li2Click')}">消费记录
            <span class="pull-right text-muted cfYnmMr" xid="span16" __cid="cfYnmMr">&gt;</span> 
          </li>  
          <li class="list-group-item  hidden cfYnmMr" xid="li2" __cid="cfYnmMr">设置
            <span class="pull-right text-muted cfYnmMr" xid="span16" __cid="cfYnmMr">&gt;</span> 
          </li> 
        </ul>
      </div>  
      <div xid="div1" __cid="cfYnmMr" class="cfYnmMr">
        <a component="$model/UI2/system/components/justep/button/button" class="btn btn-default pull-left  loginstatus cfYnmMr" xid="loginBtn" style="background-color:white" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:loginBtnClick" data-config="{&#34;label&#34;:&#34;登录&#34;}" __cid="cfYnmMr"> 
          <i xid="i1" __cid="cfYnmMr" class="cfYnmMr"></i>  
          <span xid="span3" __cid="cfYnmMr" class="cfYnmMr">登录</span>
        </a> 
      </div> 
    </div>  
    <style __cid="cfYnmMr" class="cfYnmMr">.x-panel.pcaaiEne-iosstatusbar >.x-panel-top {height: 48px;}.x-panel.pcaaiEne-iosstatusbar >.x-panel-content { top: 48px;bottom: nullpx;}.x-panel.pcaaiEne-iosstatusbar >.x-panel-bottom {height: nullpx;}.iosstatusbar .x-panel.pcaaiEne-iosstatusbar >.x-panel-top,.iosstatusbar .x-panel .x-panel-content .x-has-iosstatusbar.x-panel.pcaaiEne-iosstatusbar >.x-panel-top {height: 68px;}.iosstatusbar .x-panel.pcaaiEne-iosstatusbar >.x-panel-content,.iosstatusbar .x-panel .x-panel-content .x-has-iosstatusbar.x-panel.pcaaiEne-iosstatusbar >.x-panel-content { top: 68px;}.iosstatusbar .x-panel .x-panel-content .x-panel.pcaaiEne-iosstatusbar >.x-panel-top {height: 48px;}.iosstatusbar .x-panel .x-panel-content .x-panel.pcaaiEne-iosstatusbar >.x-panel-content {top: 48px;}</style>
  </div>  
  <span component="$model/UI2/system/components/justep/windowDialog/windowDialog" xid="windowDialog2" style="top:20px;left:218px;" data-bind="component:{name:'$model/UI2/system/components/justep/windowDialog/windowDialog'}" data-events="onReceive:windowDialog2Receive" data-config="{&#34;src&#34;:&#34;$model/UI2/live/register.w&#34;}" __cid="cfYnmMr" class="cfYnmMr">
    <div class="x-dialog-overlay cfYnmMr" __cid="cfYnmMr"></div>
    <div class="x-dialog cfYnmMr" style="display:none;" __cid="cfYnmMr">
      <div class="x-dialog-title cfYnmMr" __cid="cfYnmMr">
        <button class="close cfYnmMr" __cid="cfYnmMr">
          <span __cid="cfYnmMr" class="cfYnmMr">×</span>
        </button>
        <div class="x-dialog-title-text cfYnmMr" __cid="cfYnmMr"></div>
      </div>
      <div class="x-dialog-body cfYnmMr" __cid="cfYnmMr"></div>
    </div>
  </span>  
  <span component="$model/UI2/system/components/justep/messageDialog/messageDialog" xid="messageDialog1" data-bind="component:{name:'$model/UI2/system/components/justep/messageDialog/messageDialog'}" data-events="onOK:messageDialog1OK" data-config="{&#34;title&#34;:&#34;确定要注销么？&#34;,&#34;type&#34;:&#34;OKCancel&#34;}" __cid="cfYnmMr" class="cfYnmMr">
    <div class="x-modal-overlay cfYnmMr" __cid="cfYnmMr"></div>
    <div class="x-modal cfYnmMr" __cid="cfYnmMr">
      <div class="x-modal-inner cfYnmMr" __cid="cfYnmMr">
        <div class="x-modal-title cfYnmMr" __cid="cfYnmMr">确定要注销么？</div>
        <div class="x-modal-text cfYnmMr" __cid="cfYnmMr"></div>
        <input class="x-modal-prompt-input cfYnmMr" type="text" __cid="cfYnmMr">
      </div>
      <div class="x-modal-buttons cfYnmMr" __cid="cfYnmMr">
        <a class="x-modal-button x-modal-button-bold OK cfYnmMr" value="ok" __cid="cfYnmMr">确定</a>
        <a class="x-modal-button x-modal-button-bold Yes cfYnmMr" value="yes" __cid="cfYnmMr">是</a>
        <a class="x-modal-button x-modal-button-bold No cfYnmMr" value="no" __cid="cfYnmMr">否</a>
        <a class="x-modal-button x-modal-button-bold Cancel cfYnmMr" value="cancel" __cid="cfYnmMr">取消</a>
      </div>
    </div>
  </span> 
</div></div>
        
        <div id="downloadGCF" style="display:none;padding:50px;">
        	<span>您使用的浏览器需要下载插件才能使用, </span>
        	<a id="downloadGCFLink" href="#">下载地址</a>
        	<p>(安装后请重新打开浏览器)</p>
        </div>
    	<script>
    	
    	            //判断浏览器, 判断GCF
    	 			var browser = {
    			        isIe: function () {
    			            return navigator.appVersion.indexOf("MSIE") != -1;
    			        },
    			        navigator: navigator.appVersion,
    			        getVersion: function() {
    			            var version = 999; // we assume a sane browser
    			            if (navigator.appVersion.indexOf("MSIE") != -1)
    			                // bah, IE again, lets downgrade version number
    			                version = parseFloat(navigator.appVersion.split("MSIE")[1]);
    			            return version;
    			        }
    			    };
    				function isGCFInstalled(){
    			      try{
    			        var i = new ActiveXObject('ChromeTab.ChromeFrame');
    			        if (i) {
    			          return true;
    			        }
    			      }catch(e){}
    			      return false;
    				}
    	            //判断浏览器, 判断GCF
    	            var __continueRun = true;
    				if (browser.isIe() && (browser.getVersion() < 10) && !isGCFInstalled()) {
    					document.getElementById("applicationHost").style.display = 'none';
    					document.getElementById("downloadGCF").style.display = 'block';
    					var downloadLink = "/" + location.pathname.match(/[^\/]+/)[0] + "/v8.msi";
    					document.getElementById("downloadGCFLink").href = downloadLink; 
    					__continueRun = false;
    	            }
		 	
    	</script>
        
        <script id="_requireJS" src="../system/lib/require/require.2.1.10.js"> </script>
        <script src="../system/core.min.js"></script><script src="../system/common.min.js"></script><script src="../system/components/comp.min.js"></script><script id="_mainScript">
        
			if (__continueRun) {
                window.__justep.cssReady = function(fn){
                	var promises = [];
                	for (var p in window.__justep.__ResourceEngine.__loadingCss){
                		if(window.__justep.__ResourceEngine.__loadingCss.hasOwnProperty(p))
                			promises.push(window.__justep.__ResourceEngine.__loadingCss[p].promise());
                	}
                	$.when.apply($, promises).done(fn);
                };
                
            	window.__justep.__ResourceEngine = {
            		readyRegExp : navigator.platform === 'PLAYSTATION 3' ? /^complete$/ : /^(complete|loaded)$/,
            		url: window.location.href,	
            		/*contextPath: 不包括语言 */
            		contextPath: "",
            		serverPath: "",
            		__loadedJS: [],
            		__loadingCss: {},
            		onLoadCss: function(url, node){
            			if (!this.__loadingCss[url]){
            				this.__loadingCss[url] = $.Deferred();	
                			if (node.attachEvent &&
                                    !(node.attachEvent.toString && node.attachEvent.toString().indexOf('[native code') < 0) &&
                                    !(typeof opera !== 'undefined' && opera.toString() === '[object Opera]')) {
                                node.attachEvent('onreadystatechange', this.onLinkLoad.bind(this));
                            } else {
                                node.addEventListener('load', this.onLinkLoad.bind(this), false);
                                node.addEventListener('error', this.onLinkError.bind(this), false);
                            }
            			}
            		},
            		
            		onLinkLoad: function(evt){
            	        var target = (evt.currentTarget || evt.srcElement);
            	        if (evt.type === 'load' ||
                                (this.readyRegExp.test(target.readyState))) {
            	        	var url = target.getAttribute("href");
            	        	if (url && window.__justep.__ResourceEngine.__loadingCss[url]){
            	        		window.__justep.__ResourceEngine.__loadingCss[url].resolve(url);
            	        	}
                        }
            		},
            		
            		onLinkError: function(evt){
            	        var target = (evt.currentTarget || evt.srcElement);
        	        	var url = target.getAttribute("href");
        	        	if (url && window.__justep.__ResourceEngine.__loadingCss[url]){
        	        		window.__justep.__ResourceEngine.__loadingCss[url].resolve(url);
        	        	}
            		},
            		
            		initContextPath: function(){
            			var baseURL = document.getElementById("_requireJS").src;
            			var before = location.protocol + "//" + location.host;
            			var after = "/system/lib/require/require.2.1.10";
            			var i = baseURL.indexOf(after);
            			if (i !== -1){
    	        			var middle = baseURL.substring(before.length, i);
    						var items = middle.split("/");
    						
    						
    						if ((items[items.length-1].indexOf("v_") === 0) 
    								&& (items[items.length-1].indexOf("l_") !== -1)
    								&& (items[items.length-1].indexOf("s_") !== -1)
    								&& (items[items.length-1].indexOf("d_") !== -1)
    								|| (items[items.length-1]=="v_")){
    							items.splice(items.length-1, 1);
    						}
    						
    						
    						if (items.length !== 1){
    							window.__justep.__ResourceEngine.contextPath = items.join("/");
    						}else{
    							window.__justep.__ResourceEngine.contextPath = before;
    						}
    						var index = window.__justep.__ResourceEngine.contextPath.lastIndexOf("/");
    						if (index != -1){
    							window.__justep.__ResourceEngine.serverPath = window.__justep.__ResourceEngine.contextPath.substr(0, index);
    						}else{
    							window.__justep.__ResourceEngine.serverPath = window.__justep.__ResourceEngine.contextPath;
    						}
            			}else{
            				throw new Error(baseURL + " hasn't  " + after);
            			}
            		},
            	
            		loadJs: function(urls){
            			if (urls && urls.length>0){
            				var loadeds = this._getResources("script", "src").concat(this.__loadedJS);
    	       				for (var i=0; i<urls.length; i++){
								var url = urls[i];
    	        				if(!this._isLoaded(url, loadeds)){
    	        					this.__loadedJS[this.__loadedJS.length] = url;
    	        					/*
    	        					var script = document.createElement("script");
    	        					script.src = url;
    	        					document.head.appendChild(script);
    	        					*/
    	        					//$("head").append("<script  src='" + url + "'/>");
									var url = require.toUrl("$UI" + url);
    	        					$.ajax({
    	        						url: url,
    	        						dataType: "script",
    	        						cache: true,
    	        						async: false,
    	        						success: function(){}
    	        						});
    	        				} 
    	       				}
            			}
            		},
            		
            		loadCss: function(styles){
           				var loadeds = this._getResources("link", "href");
            			if (styles && styles.length>0){
            				for (var i=0; i<styles.length; i++){
    	       					var url = window.__justep.__ResourceEngine.contextPath + styles[i].url
    	        				if(!this._isLoaded(url, loadeds)){
    	        					var include = styles[i].include || "";
    	        					var link = $("<link type='text/css' rel='stylesheet' href='" + url + "' include='" + include + "'/>");
    	        					this.onLoadCss(url, link[0]);
    	        					$("head").append(link);
    	        				} 
            				}
            			}
            			
            		},
            		
            		
            		_isLoaded: function(url, loadeds){
            			if (url){
            				var newUrl = "";
            				var items = url.split("/");
            				var isVls = false;
            				for (var i=0; i<items.length; i++){
            					if (isVls){
                					newUrl += "/" + items[i];
            					}else{
                					if (items[i] && (items[i].indexOf("v_")===0)
            								&& (items[i].indexOf("l_")!==-1)
            								&& (items[i].indexOf("s_")!==-1)
            								&& (items[i].indexOf("d_")!==-1)
            								|| (items[i]=="v_")){
                						isVls = true;
                					}
            					}
            				}
            				if (!newUrl)
            					newUrl = url;
            				
            				for (var i=0; i<loadeds.length; i++){
								var originUrl = this._getOriginUrl(loadeds[i]);
								if (originUrl && (originUrl.indexOf(newUrl)!==-1)){
									return true;
								}
    						}
            			}
    					return false;
            		},

					_getOriginUrl: function(url){
						var result = "";
						if (url && (url.indexOf(".md5_")!==-1)){
							url = url.split("#")[0];
							url = url.split("?")[0];
							var items = url.split(".");
							for (var i=0; i<items.length; i++){
								if ((i===items.length-2) && (items[i].indexOf("md5_")!==-1)){
									continue;
								}else{
									if (i>0) result += ".";
									result += items[i];
								}
							}
						}else{
							result = url;
						}
						return result;
					},
            		
            		_getResources: function(tag, attr){
    					var result = [];
    					var scripts = $(tag);
    					for (var i=0; i<scripts.length; i++){
    						var v = scripts[i][attr];
    						if (v){
    							result[result.length] = v;
    						}
    					}
    					return result;
            		}
            	};
            	
            	window.__justep.__ResourceEngine.initContextPath();
    			requirejs.config({
    				baseUrl: window.__justep.__ResourceEngine.contextPath + '/live',
    			    paths: {
    			    	/* 解决require.normalizeName与require.toUrl嵌套后不一致的bug   */
    			    	'$model/UI2/v_': window.__justep.__ResourceEngine.contextPath + '',
    			    	'$model/UI2': window.__justep.__ResourceEngine.contextPath + '',
    			    	'$model': window.__justep.__ResourceEngine.serverPath,
    			        'text': window.__justep.__ResourceEngine.contextPath + '/system/lib/require/text.2.0.10',
    			        'bind': window.__justep.__ResourceEngine.contextPath + '/system/lib/bind/bind',
    			        'jquery': window.__justep.__ResourceEngine.contextPath + '/system/lib/jquery/jquery-1.11.1.min'
    			    },
    			    map: {
    				        '*': {
    				            res: '$model/UI2/system/lib/require/res',
    				            cordova: '$model/UI2/system/lib/require/cordova',
    				            w: '$model/UI2/system/lib/require/w',
    				            css: '$model/UI2/system/lib/require/css'
    				        }
    				},
    				waitSeconds: 300
    			});
    			
    			requirejs(['require', 'jquery', '$model/UI2/system/lib/base/composition', '$model/UI2/system/lib/base/url', '$model/UI2/system/lib/route/hashbangParser', '$model/UI2/system/components/justep/versionChecker/versionChecker', '$model/UI2/system/components/justep/loadingBar/loadingBar', '$model/UI2/system/lib/jquery/domEvent',  '$model/UI2/system/lib/cordova/cordova'],  function (require, $, composition, URL, HashbangParser,versionChecker) { 
    				document.addEventListener('deviceready', function() {
    	                if (navigator && navigator.splashscreen && navigator.splashscreen.hide) {
    	                	/*延迟隐藏，视觉效果更理想*/
    	                	setTimeout(function() {navigator.splashscreen.hide();}, 800);
    	                }
    	            }, false);
					setTimeout(function(){
						versionChecker.check();
					},2000);
    				var context = {};
    				context.model = '$model/UI2/live/wode.w' + (document.location.search || "");
    				context.view = $('#applicationHost').children()[0];
    				var element = document.getElementById('applicationHost');

					    				
    				
    				var ownerid = new URL(window.__justep.__ResourceEngine.url).getParam("$ownerid");
    				var pwindow = opener;
    				if (!pwindow && window.parent && window.parent.window){
    					pwindow = window.parent.window;
    				}
    				if(ownerid && pwindow 
    						&& pwindow.__justep && pwindow.__justep.windowOpeners
    						&& pwindow.__justep.windowOpeners[ownerid]
    						&& $.isFunction(pwindow.__justep.windowOpeners[ownerid].sendToWindow)){
    					window.__justep.setParams = function(params){
    						/* 给windowOpener提供再次传参数的接口  */
    						params = params || {};
    						composition.setParams(document.getElementById('applicationHost'), params);
    					};
    					var winOpener = pwindow.__justep.windowOpeners[ownerid];
    					if(winOpener) winOpener.window = window;
    					$(window).unload(function(event){
    						if(winOpener && winOpener.dispatchCloseEvent) winOpener.dispatchCloseEvent();
    					});
    					var params = winOpener.sendToWindow();
						context.owner = winOpener;
						context.params = params || {};
	        			composition.compose(element, context);
    				}else{
        				var params =  {};
    					var state = new HashbangParser(window.location.hash).parse().__state;
    					if (state){
    						params = state.get("");
    						try{
    							params = JSON.parse(params);
    							if (params.hasOwnProperty("__singleValue__")){
    								params = params.__singleValue__;
    							}
    						}catch(e1){}
    					}
    					context.noUpdateState = true;
        				context.params = params;
        				composition.compose(element, context);
    				}
    			});    
            }
		 	
        </script>
    </body>
</html>