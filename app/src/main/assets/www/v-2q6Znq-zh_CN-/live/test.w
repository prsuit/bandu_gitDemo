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
    	<div id="applicationHost" class="applicationHost" style="width:100%;height:100%;" __component-context__="block"><div component="$model/UI2/system/components/justep/window/window" design="device:m;" xid="window" class="window cuYraam" data-bind="component:{name:'$model/UI2/system/components/justep/window/window'}" __cid="cuYraam" components="$model/UI2/system/components/justep/model/model,$model/UI2/system/components/justep/loadingBar/loadingBar,$model/UI2/system/components/justep/button/button,$model/UI2/system/components/justep/scrollView/scrollView,$model/UI2/system/components/justep/list/list,$model/UI2/system/components/justep/menu/menu,$model/UI2/system/components/justep/panel/child,$model/UI2/system/components/justep/panel/panel,$model/UI2/system/components/justep/button/checkbox,$model/UI2/system/components/justep/row/row,$model/UI2/system/components/justep/popMenu/popMenu,$model/UI2/system/components/justep/titleBar/titleBar,$model/UI2/system/components/justep/data/data,$model/UI2/system/components/justep/window/window,,$model/UI2/system/components/justep/windowDialog/windowDialog,$model/UI2/system/components/justep/tree/tree,$model/UI2/system/components/justep/distpicker/districtSelect,$model/UI2/system/components/justep/labelEdit/labelEdit,$model/UI2/system/components/justep/distpicker/provinceSelect,$model/UI2/system/components/justep/messageDialog/messageDialog,$model/UI2/system/components/justep/contents/content,$model/UI2/system/components/justep/windowContainer/windowContainer,$model/UI2/system/components/justep/contents/contents,$model/UI2/system/components/justep/distpicker/citySelect,$model/UI2/system/components/bootstrap/breadcrumb/breadcrumb,">
  <style>null</style>  
  <div component="$model/UI2/system/components/justep/model/model" xid="model" style="display:none" data-bind="component:{name:'$model/UI2/system/components/justep/model/model'}" __cid="cuYraam" class="cuYraam"></div>  
  <div component="$model/UI2/system/components/justep/panel/panel" class="x-panel x-full pczeMZne-iosstatusbar cuYraam" xid="panel1" data-bind="component:{name:'$model/UI2/system/components/justep/panel/panel'}" __cid="cuYraam"> 
    <div class="x-panel-top cuYraam" xid="top1" component="$model/UI2/system/components/justep/panel/child" data-bind="component:{name:'$model/UI2/system/components/justep/panel/child'}" __cid="cuYraam"> 
      <div component="$model/UI2/system/components/justep/titleBar/titleBar" class="x-titlebar cuYraam" data-bind="component:{name:'$model/UI2/system/components/justep/titleBar/titleBar'}" data-config="{&#34;title&#34;:&#34;标题&#34;}" __cid="cuYraam"> 
        <div class="x-titlebar-left cuYraam" __cid="cuYraam"> 
          <a component="$model/UI2/system/components/justep/button/button" class="btn btn-link btn-only-icon cuYraam" xid="backBtn" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:{operation:'window.close'}" data-config="{&#34;icon&#34;:&#34;icon-chevron-left&#34;,&#34;label&#34;:&#34;&#34;}" __cid="cuYraam"> 
            <i class="icon-chevron-left cuYraam" __cid="cuYraam"></i>  
            <span __cid="cuYraam" class="cuYraam"></span> 
          </a> 
        </div>  
        <div class="x-titlebar-title cuYraam" __cid="cuYraam">标题</div>  
        <div class="x-titlebar-right  cuYraam" __cid="cuYraam">
          <div class="empty cuYraam" __cid="cuYraam"></div>
        </div> 
      </div> 
    </div>  
    <div class="x-panel-content cuYraam" xid="content1" component="$model/UI2/system/components/justep/panel/child" data-bind="component:{name:'$model/UI2/system/components/justep/panel/child'}" __cid="cuYraam"></div>  
    <div class="x-panel-top   cuYraam" xid="top2" visible="true" __cid="cuYraam"> 
      <div component="$model/UI2/system/components/justep/titleBar/titleBar" class="x-titlebar cuYraam" style="background-color:#2cc17b;" xid="titleBar1" data-bind="component:{name:'$model/UI2/system/components/justep/titleBar/titleBar'}" data-config="{&#34;title&#34;:&#34;标题&#34;}" __cid="cuYraam"> 
        <div class="x-titlebar-left cuYraam" xid="left1" __cid="cuYraam"> 
          <a component="$model/UI2/system/components/justep/button/button" class="btn btn-link btn-only-icon cuYraam" xid="button1" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:{operation:'window.close'}" data-config="{&#34;icon&#34;:&#34;icon-chevron-left&#34;,&#34;label&#34;:&#34;&#34;}" __cid="cuYraam"> 
            <i class="icon-chevron-left cuYraam" xid="i1" __cid="cuYraam"></i>  
            <span xid="span1" __cid="cuYraam" class="cuYraam"></span>
          </a> 
        </div>  
        <div class="x-titlebar-title cuYraam" style="font-weight:normal;" xid="title1" __cid="cuYraam">购物车</div>  
        <div class="x-titlebar-right  cuYraam" xid="right1" __cid="cuYraam">
          <div class="empty cuYraam" __cid="cuYraam"></div>
        </div>
      </div> 
    </div>
    <style __cid="cuYraam" class="cuYraam">.x-panel.pczeMZne-iosstatusbar >.x-panel-top {height: 48px;}.x-panel.pczeMZne-iosstatusbar >.x-panel-content { top: 48px;bottom: nullpx;}.x-panel.pczeMZne-iosstatusbar >.x-panel-bottom {height: nullpx;}.iosstatusbar .x-panel.pczeMZne-iosstatusbar >.x-panel-top,.iosstatusbar .x-panel .x-panel-content .x-has-iosstatusbar.x-panel.pczeMZne-iosstatusbar >.x-panel-top {height: 68px;}.iosstatusbar .x-panel.pczeMZne-iosstatusbar >.x-panel-content,.iosstatusbar .x-panel .x-panel-content .x-has-iosstatusbar.x-panel.pczeMZne-iosstatusbar >.x-panel-content { top: 68px;}.iosstatusbar .x-panel .x-panel-content .x-panel.pczeMZne-iosstatusbar >.x-panel-top {height: 48px;}.iosstatusbar .x-panel .x-panel-content .x-panel.pczeMZne-iosstatusbar >.x-panel-content {top: 48px;}</style>
  </div>  
  <div component="$model/UI2/system/components/justep/contents/contents" class="x-contents x-full cuYraam" xid="contents1" data-bind="component:{name:'$model/UI2/system/components/justep/contents/contents'}" data-config="{&#34;active&#34;:0}" __cid="cuYraam"> 
    <div class="x-contents-content active cuYraam" xid="liveContent" component="$model/UI2/system/components/justep/contents/content" data-bind="component:{name:'$model/UI2/system/components/justep/contents/content'}" __cid="cuYraam">
      <div component="$model/UI2/system/components/justep/tree/tree" class="x-tree x-inner-scroll cuYraam" xid="tree1" data-bind="component:{name:'$model/UI2/system/components/justep/tree/tree'}" __cid="cuYraam"> 
        <div class="x-tree-head cuYraam" xid="div1" __cid="cuYraam"> 
          <ul component="$model/UI2/system/components/bootstrap/breadcrumb/breadcrumb" class="breadcrumb cuYraam" data-bind="component:{name:'$model/UI2/system/components/bootstrap/breadcrumb/breadcrumb'}" __cid="cuYraam"></ul>
        </div>  
        <div class="x-tree-content x-scroll-view cuYraam" xid="div2" __cid="cuYraam"> 
          <div component="$model/UI2/system/components/justep/scrollView/scrollView" supportPullDown="true" supportPullUp="true" class="x-scroll cuYraam" xid="scrollView1" data-bind="component:{name:'$model/UI2/system/components/justep/scrollView/scrollView'}" data-config="{&#34;bounce&#34;:true,&#34;hScroll&#34;:false,&#34;hScrollbar&#34;:false,&#34;vScroll&#34;:true,&#34;vScrollbar&#34;:true}" __cid="cuYraam"> 
            <div class="x-content-center x-pull-down container cuYraam" xid="div3" __cid="cuYraam"> 
              <i class="x-pull-down-img glyphicon x-icon-pull-down cuYraam" xid="i2" __cid="cuYraam"></i>  
              <span class="x-pull-down-label cuYraam" xid="span2" __cid="cuYraam">下拉刷新...</span>
            </div>  
            <ul class="x-tree-template x-scroll-content hide cuYraam" xid="treeTemplateUl1" __cid="cuYraam"> 
              <li class="x-tree-link cuYraam" xid="li1" __cid="cuYraam"></li>
            </ul>  
            <div class="x-content-center x-pull-up cuYraam" xid="div4" __cid="cuYraam"> 
              <span class="x-pull-up-label cuYraam" xid="span3" __cid="cuYraam">加载更多...</span>
            </div> 
          </div> 
        </div> 
      </div>
    </div>  
    <div class="x-contents-content cuYraam" xid="trailerContent" component="$model/UI2/system/components/justep/contents/content" data-bind="component:{name:'$model/UI2/system/components/justep/contents/content'}" __cid="cuYraam"> 
      <div component="$model/UI2/system/components/justep/windowContainer/windowContainer" class="x-window-container cuYraam" xid="windowContainer1" __component-context__="block" data-bind="component:{name:'$model/UI2/system/components/justep/windowContainer/windowContainer'}" __cid="cuYraam"></div>
    </div>  
    <div class="x-contents-content cuYraam" xid="cartContent" component="$model/UI2/system/components/justep/contents/content" data-bind="component:{name:'$model/UI2/system/components/justep/contents/content'}" __cid="cuYraam"> 
      <div component="$model/UI2/system/components/justep/windowContainer/windowContainer" class="x-window-container cuYraam" xid="windowContainer2" __component-context__="block" data-bind="component:{name:'$model/UI2/system/components/justep/windowContainer/windowContainer'}" data-config="{&#34;src&#34;:&#34;$model/UI2/live/shopcar.w&#34;}" __cid="cuYraam">
        <div component="$model/UI2/system/components/justep/window/window" design="device:m;" xid="window" class="window cBji26f" data-bind="component:{name:'$model/UI2/system/components/justep/window/window'}" __cid="cBji26f" components="$model/UI2/system/components/justep/model/model,$model/UI2/system/components/justep/loadingBar/loadingBar,$model/UI2/system/components/justep/button/button,$model/UI2/system/components/justep/scrollView/scrollView,$model/UI2/system/components/justep/list/list,$model/UI2/system/components/justep/menu/menu,$model/UI2/system/components/justep/panel/child,$model/UI2/system/components/justep/panel/panel,$model/UI2/system/components/justep/button/checkbox,$model/UI2/system/components/justep/row/row,$model/UI2/system/components/justep/popMenu/popMenu,$model/UI2/system/components/justep/titleBar/titleBar,$model/UI2/system/components/justep/data/data,$model/UI2/system/components/justep/window/window,">  
          <style>@media screen and (min-width: 768px) {body { font-size: 150% } } .bg-white.cBji26f{background-color: rgb(255, 255, 255)} .text-black.cBji26f{color: rgb(85, 85, 85)} .x-titlebar.cBji26f{background-color: rgb(158, 208, 77)} .x-titlebar1.cBji26f{background-color: rgb(0, 168, 255)} .x-titlebar-right.cBji26f .btn.cBji26f i.cBji26f{font-size: 24px} .row.cBji26f{margin: 0} .x-cards.cBji26f{padding-top: 15px} .tb-box.cBji26f{margin-bottom: 10px; border-top: 1px solid rgb(220, 220, 220); border-bottom: 1px solid rgb(220, 220, 220)} .x-card.cBji26f .panel-heading.cBji26f{padding: 5px 15px} .x-card.cBji26f .panel-heading.cBji26f i.cBji26f{margin-right: 13px} .tb-img-shop.cBji26f{width: 20px; height: 20px} .tb-img-good.cBji26f{width: 100%; height: 100px} .tb-nomargin.cBji26f{margin: 0} .tb-nopadding.cBji26f{padding: 0} .lh.cBji26f{overflow: hidden; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; word-break: break-all} .tb-goodList.cBji26f{margin-bottom: 5px; border-bottom: 1px solid rgb(242, 242, 242)} .tb-goodList.cBji26f:last-child{margin-bottom: 0px} .tb-goodList.cBji26f .x-col.cBji26f:first-child{padding-top: 35px} .tb-goodList.cBji26f .x-col.cBji26f:nth-child(2){width: 100px; padding-right: 5px} .tb-goodList.cBji26f .h5.cBji26f{height: 30px; overflow: hidden; text-overflow: ellipsis} .tb-goodList.cBji26f .h6.cBji26f{margin-top: 3px; margin-bottom: 3px} .tb-numberOperation.cBji26f{width: 100%; height: 30px} .tb-numberOperation.cBji26f .btn.cBji26f{border-radius: 0} .tb-numberOperation.cBji26f span.cBji26f{padding: 4px 15px; border-top: 1px solid rgb(225, 225, 225); border-bottom: 1px solid rgb(225, 225, 225)} .x-panel-bottom.cBji26f{border-top: 1px solid rgb(220, 220, 220)} .x-panel-bottom.cBji26f .h4.cBji26f{margin: 5px 0} .tb-settlement.cBji26f{line-height: 50px; color: rgb(255, 255, 255); font-size: 16px}</style>  
          <div component="$model/UI2/system/components/justep/model/model" xid="model" style="display:none" data-bind="component:{name:'$model/UI2/system/components/justep/model/model'}" data-events="onunLoad:modelUnLoad;onLoad:modelLoad" __cid="cBji26f" class="cBji26f"></div>  
          <div component="$model/UI2/system/components/justep/panel/panel" class="x-panel x-full pc67jENv-iosstatusbar cBji26f" xid="panel1" data-bind="component:{name:'$model/UI2/system/components/justep/panel/panel'}" __cid="cBji26f">  
            <div class="x-panel-top   cBji26f" xid="top2" component="$model/UI2/system/components/justep/panel/child" data-bind="component:{name:'$model/UI2/system/components/justep/panel/child'}" data-config="{&#34;visible&#34;:true}" __cid="cBji26f"> 
              <div component="$model/UI2/system/components/justep/titleBar/titleBar" class="x-titlebar cBji26f" xid="titleBar1" style="background-color:white" data-bind="component:{name:'$model/UI2/system/components/justep/titleBar/titleBar'}" data-config="{&#34;title&#34;:&#34;购物车&#34;}" __cid="cBji26f"> 
                <div class="x-titlebar-left cBji26f" xid="left1" __cid="cBji26f"> 
                  <a component="$model/UI2/system/components/justep/button/button" class="btn btn-link btn-only-icon cBji26f" xid="button1" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:{operation:'window.close'}" data-config="{&#34;icon&#34;:&#34;icon-chevron-left&#34;,&#34;label&#34;:&#34;&#34;}" __cid="cBji26f"> 
                    <i class="icon-chevron-left cBji26f" xid="i1" __cid="cBji26f"></i>  
                    <span xid="span5" __cid="cBji26f" class="cBji26f"></span> 
                  </a> 
                </div>  
                <div class="x-titlebar-title cBji26f" style="font-weight:normal;" xid="title1" __cid="cBji26f">购物车</div>  
                <div class="x-titlebar-right  cBji26f" xid="right1" __cid="cBji26f"> 
                  <div class="empty cBji26f" __cid="cBji26f"></div> 
                </div> 
              </div> 
            </div>  
            <div class="x-panel-content  x-scroll-view cBji26f" xid="content1" _xid="C756E1C0FC300001B23D10482650131B" component="$model/UI2/system/components/justep/panel/child" data-bind="component:{name:'$model/UI2/system/components/justep/panel/child'}" __cid="cBji26f"> 
              <div class="x-scroll cBji26f" component="$model/UI2/system/components/justep/scrollView/scrollView" xid="scrollView1" style="background-color:#FFFFFF;" data-bind="component:{name:'$model/UI2/system/components/justep/scrollView/scrollView'}" __cid="cBji26f"> 
                <div class="x-content-center x-pull-down container cBji26f" xid="div3" __cid="cBji26f"> 
                  <i class="x-pull-down-img glyphicon x-icon-pull-down cBji26f" xid="i3" __cid="cBji26f"></i>  
                  <span class="x-pull-down-label cBji26f" xid="span7" __cid="cBji26f">下拉刷新...</span> 
                </div>  
                <div class="x-scroll-content cBji26f" xid="div4" __cid="cBji26f"> 
                  <div component="$model/UI2/system/components/justep/list/list" class="x-list cBji26f" xid="list1" style="height:207px;" data-bind="component:{name:'$model/UI2/system/components/justep/list/list'}" data-config="{&#34;data&#34;:&#34;window.data.datas&#34;}" __cid="cBji26f"> 
                    <ul class="x-list-template x-min-height hide cBji26f" xid="listTemplateUl4" componentname="$UI/system/components/justep/list/list#listTemplateUl" id="undefined_listTemplateUl4" __cid="cBji26f" data-bind="foreach:{data:$model.foreach_list1($element),afterRender:$model.foreach_afterRender_list1.bind($model,$element)}"> 
                      <li xid="li4" class="x-min-height tb-goodList cBji26f" __cid="cBji26f"> 
                        <div component="$model/UI2/system/components/justep/row/row" class="x-row cBji26f" xid="row3" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="cBji26f"> 
                          <div class="x-col x-col-fixed cBji26f" xid="col9" style="width:auto;" __cid="cBji26f"> 
                            <span component="$model/UI2/system/components/justep/button/checkbox" class="x-checkbox x-radio choose cBji26f" xid="checkbox2" checkedValue="1" data-config="{&#34;checked&#34;:false,&#34;checkedValue&#34;:&#34;1&#34;,&#34;disabled&#34;:false}" data-bind="component:{ref:ref('fChoose'),name:'$model/UI2/system/components/justep/button/checkbox'}" __cid="cBji26f"> 
                              <input type="checkbox" __cid="cBji26f" class="cBji26f">  
                              <label __cid="cBji26f" class="cBji26f"></label> 
                            </span> 
                          </div>  
                          <div class="x-col x-col-fixed tb-nopadding cBji26f" xid="col7" __cid="cBji26f"> 
                            <img alt="" xid="image1" class="tb-img-good cBji26f" height="100%" __cid="cBji26f" data-bind="attr:{src:val(&#34;File&#34;)}"> 
                          </div>  
                          <div class="x-col   cBji26f" xid="col8" __cid="cBji26f"> 
                            <span class="x-flex text-black tb-nomargin lh cBji26f" xid="span26" style="font-size:medium;" __cid="cBji26f" data-bind="text:val(&#34;Live_name&#34;)">学好摄影的三个方面</span>  
                            <div class="text-muted  h6 cBji26f" xid="div6" style="margin-top:5px" __cid="cBji26f"> 
                              <span xid="span13" __cid="cBji26f" class="cBji26f">开课时间:</span>  
                              <span xid="span14" __cid="cBji26f" class="cBji26f" data-bind="text:val(&#34;Live_time&#34;)">2016/12/8 17:00</span> 
                            </div>  
                            <div class="text-muted cBji26f" xid="div7" __cid="cBji26f"> 
                              <span xid="span22" class="danger cBji26f" style="color:#FF0000;" __cid="cBji26f">￥</span>  
                              <span xid="span28" class="h4 danger cBji26f" style="color:#FF0000;" __cid="cBji26f" data-bind="text:val('Money')">9.90</span> 
                            </div> 
                          </div>  
                          <a component="$model/UI2/system/components/justep/button/button" class="btn btn-link btn-only-icon cBji26f" xid="delete" style="line-height:80px;color:#999;width:88px;" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:deleteClick" data-config="{&#34;icon&#34;:&#34;round round-music&#34;,&#34;label&#34;:&#34;取消&#34;}" __cid="cBji26f"> 
                            <i xid="i5" class="round round-music cBji26f" __cid="cBji26f"></i>  
                            <span xid="span15" __cid="cBji26f" class="cBji26f">取消</span> 
                          </a> 
                        </div> 
                      </li> 
                    </ul> 
                  </div> 
                </div>  
                <div class="x-content-center x-pull-up cBji26f" xid="div5" __cid="cBji26f"> 
                  <span class="x-pull-up-label cBji26f" xid="span8" style="color:#ddd;" __cid="cBji26f">加载更多...</span> 
                </div> 
              </div> 
            </div>  
            <div class="x-panel-bottom tb-nopadding cBji26f" xid="bottom1" style="border-top:1px solid #f2f2f2;bottom:0px;" component="$model/UI2/system/components/justep/panel/child" data-bind="component:{name:'$model/UI2/system/components/justep/panel/child'}" __cid="cBji26f"> 
              <div component="$model/UI2/system/components/justep/list/list" class="x-list cBji26f" xid="list2" data-bind="component:{name:'$model/UI2/system/components/justep/list/list'}" data-config="{&#34;data&#34;:&#34;window.cdata.datas&#34;}" __cid="cBji26f"> 
                <ul class="x-list-template hide cBji26f" xid="listTemplateUl1" __cid="cBji26f" data-bind="foreach:{data:$model.foreach_list2($element),afterRender:$model.foreach_afterRender_list2.bind($model,$element)}"> 
                  <li xid="li1" __cid="cBji26f" class="cBji26f"> 
                    <div component="$model/UI2/system/components/justep/row/row" class="x-row tb-nopadding cBji26f" xid="row1" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="cBji26f"> 
                      <div class="x-col x-col-20 cBji26f" xid="col1" __cid="cBji26f"> 
                        <span component="$model/UI2/system/components/justep/button/checkbox" class="x-checkbox cBji26f" xid="allChoose" label="全选" style="margin-top:10px;" data-config="{&#34;checked&#34;:false,&#34;disabled&#34;:false,&#34;label&#34;:&#34;全选&#34;}" data-bind="component:{name:'$model/UI2/system/components/justep/button/checkbox'}" data-events="onChange:allchooseChange" __cid="cBji26f"> 
                          <input type="checkbox" __cid="cBji26f" class="cBji26f">  
                          <label __cid="cBji26f" class="cBji26f">全选</label> 
                        </span> 
                      </div>  
                      <div class="x-col cBji26f" xid="col2" __cid="cBji26f"> 
                        <div xid="div1" style="text-align:right;font-size:medium;" __cid="cBji26f" class="cBji26f"> 
                          <span xid="span1" __cid="cBji26f" class="cBji26f">合计:</span>  
                          <span xid="span2" class="danger cBji26f" style="font-weight:bold;color:#FF0000;" __cid="cBji26f">￥</span>  
                          <span xid="span3" class="danger cBji26f" style="font-weight:bold;color:#FF0000;" __cid="cBji26f" data-bind="text:ref('allSum')"></span> 
                        </div>  
                        <div xid="div2" style="text-align:right;" __cid="cBji26f" class="cBji26f"> 
                          <span xid="span4" style="font-size:small;" class="text-muted cBji26f" __cid="cBji26f">购买10门以上享优惠</span> 
                        </div> 
                      </div>  
                      <div class="x-col x-col-33 center-block tb-nopadding  text-center tb-settlement cBji26f" xid="col3" __cid="cBji26f" data-bind="event:{click:$model._callModelFn.bind($model, 'settlementClick')}"> 
                        <span xid="span9" style="font-size:large;" __cid="cBji26f" class="cBji26f">结算（</span>  
                        <span xid="span6" style="font-size:large;" __cid="cBji26f" class="cBji26f" data-bind="text:ref('allNumber')"></span>  
                        <span xid="span11" style="font-size:large;" __cid="cBji26f" class="cBji26f">）</span> 
                      </div> 
                    </div> 
                  </li> 
                </ul> 
              </div> 
            </div>  
            <style __cid="cBji26f" class="cBji26f">.x-panel.pc67jENv-iosstatusbar >.x-panel-top {height: 48px;}.x-panel.pc67jENv-iosstatusbar >.x-panel-content { top: 48px;bottom: nullpx;}.x-panel.pc67jENv-iosstatusbar >.x-panel-bottom {height: nullpx;}.iosstatusbar .x-panel.pc67jENv-iosstatusbar >.x-panel-top,.iosstatusbar .x-panel .x-panel-content .x-has-iosstatusbar.x-panel.pc67jENv-iosstatusbar >.x-panel-top {height: 68px;}.iosstatusbar .x-panel.pc67jENv-iosstatusbar >.x-panel-content,.iosstatusbar .x-panel .x-panel-content .x-has-iosstatusbar.x-panel.pc67jENv-iosstatusbar >.x-panel-content { top: 68px;}.iosstatusbar .x-panel .x-panel-content .x-panel.pc67jENv-iosstatusbar >.x-panel-top {height: 48px;}.iosstatusbar .x-panel .x-panel-content .x-panel.pc67jENv-iosstatusbar >.x-panel-content {top: 48px;}</style> 
          </div>  
          <div component="$model/UI2/system/components/justep/popMenu/popMenu" class="x-popMenu cBji26f" xid="popMenu1" data-bind="component:{name:'$model/UI2/system/components/justep/popMenu/popMenu'}" data-config="{&#34;anchor&#34;:&#34;bottom1&#34;,&#34;autoHidable&#34;:false,&#34;direction&#34;:&#34;auto&#34;,&#34;dismissible&#34;:false}" __cid="cBji26f">  
            <div class="x-popMenu-overlay cBji26f" xid="div8" __cid="cBji26f"></div>  
            <ul component="$model/UI2/system/components/justep/menu/menu" class="x-menu dropdown-menu x-popMenu-content cBji26f" xid="menu1" style="width:100%;" data-bind="component:{name:'$model/UI2/system/components/justep/menu/menu'}" __cid="cBji26f"> 
              <li class="x-menu-item cBji26f" style="text-align:center" __cid="cBji26f"> 
                <a component="$model/UI2/system/components/justep/button/button" class="btn btn-link cBji26f" xid="alipaypay" style="text-align:center" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:alipaypayClick" data-config="{&#34;label&#34;:&#34;支付宝支付&#34;}" __cid="cBji26f"> 
                  <i xid="i2" __cid="cBji26f" class="cBji26f"></i>  
                  <span xid="span10" __cid="cBji26f" class="cBji26f">支付宝支付</span> 
                </a> 
              </li>  
              <li class="x-menu-divider divider cBji26f" xid="divider2" __cid="cBji26f"></li>  
              <li class="x-menu-item  hidden cBji26f" xid="item2" style="text-align:center" __cid="cBji26f"> 
                <a component="$model/UI2/system/components/justep/button/button" class="btn btn-link cBji26f" xid="weixinpay" style="text-align:center" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:weixinpayClick" data-config="{&#34;label&#34;:&#34;微信支付&#34;}" __cid="cBji26f"> 
                  <i xid="i4" __cid="cBji26f" class="cBji26f"></i>  
                  <span xid="span12" __cid="cBji26f" class="cBji26f">微信支付</span> 
                </a> 
              </li>  
              <li class="x-menu-divider divider  hidden cBji26f" xid="divider3" __cid="cBji26f"></li>  
              <li class="x-menu-item cBji26f" xid="item3" __cid="cBji26f"> 
                <a component="$model/UI2/system/components/justep/button/button" class="btn btn-link cBji26f" xid="cancel" style="text-align:center" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:cancelClick" data-config="{&#34;label&#34;:&#34;取消&#34;}" __cid="cBji26f"> 
                  <i xid="i4" __cid="cBji26f" class="cBji26f"></i>  
                  <span xid="span16" __cid="cBji26f" class="cBji26f">取消</span> 
                </a> 
              </li> 
            </ul> 
          </div> 
        </div>
      </div>
    </div>  
    <div class="x-contents-content cuYraam" xid="detailContent" component="$model/UI2/system/components/justep/contents/content" data-bind="component:{name:'$model/UI2/system/components/justep/contents/content'}" __cid="cuYraam"> 
      <div component="$model/UI2/system/components/justep/windowContainer/windowContainer" class="x-window-container cuYraam" xid="windowContainer3" __component-context__="block" data-bind="component:{name:'$model/UI2/system/components/justep/windowContainer/windowContainer'}" data-config="{&#34;src&#34;:&#34;$model/UI2/live/goumai.w&#34;}" __cid="cuYraam">
        <div component="$model/UI2/system/components/justep/window/window" design="device:m;" xid="window" class="window ca2u26n" data-bind="component:{name:'$model/UI2/system/components/justep/window/window'}" __cid="ca2u26n" components="$model/UI2/system/components/justep/model/model,$model/UI2/system/components/justep/loadingBar/loadingBar,$model/UI2/system/components/justep/button/button,$model/UI2/system/components/justep/row/row,$model/UI2/system/components/justep/popMenu/popMenu,$model/UI2/system/components/justep/menu/menu,$model/UI2/system/components/justep/titleBar/titleBar,$model/UI2/system/components/justep/panel/child,$model/UI2/system/components/justep/data/data,$model/UI2/system/components/justep/windowDialog/windowDialog,$model/UI2/system/components/justep/window/window,$model/UI2/system/components/justep/panel/panel,">  
          <style>.nopadding.ca2u26n{padding: 0} .danger.ca2u26n{color: red} .gouwuche.ca2u26n{background-color: rgb(44, 193, 123); opacity: 0.7; color: rgb(255, 255, 255); border-radius: 0px} .gouwuche1.ca2u26n{background-color: rgb(0, 133, 202); opacity: 0.7; color: rgb(255, 255, 255); border-radius: 0px} .goumai.ca2u26n{background-color: rgb(44, 193, 123); color: rgb(255, 255, 255); border-radius: 0px} .goumai1.ca2u26n{background-color: rgb(0, 133, 202); color: rgb(255, 255, 255); border-radius: 0px}</style>  
          <div component="$model/UI2/system/components/justep/model/model" xid="model" style="display:none" data-bind="component:{name:'$model/UI2/system/components/justep/model/model'}" data-events="onLoad:modelLoad;onParamsReceive:modelParamsReceive" __cid="ca2u26n" class="ca2u26n"></div>  
          <span component="$model/UI2/system/components/justep/windowDialog/windowDialog" xid="windowDialog1" style="top:17px;left:137px;" data-bind="component:{name:'$model/UI2/system/components/justep/windowDialog/windowDialog'}" data-events="onReceived:windowDialog1Received" data-config="{&#34;routable&#34;:true,&#34;src&#34;:&#34;$model/UI2/live/login.w&#34;}" __cid="ca2u26n" class="ca2u26n">  
            <div class="x-dialog-overlay ca2u26n" __cid="ca2u26n"></div>  
            <div class="x-dialog ca2u26n" style="display:none;" __cid="ca2u26n">  
              <div class="x-dialog-title ca2u26n" __cid="ca2u26n"> 
                <button class="close ca2u26n" __cid="ca2u26n"> 
                  <span __cid="ca2u26n" class="ca2u26n">×</span> 
                </button>  
                <div class="x-dialog-title-text ca2u26n" __cid="ca2u26n"></div> 
              </div>  
              <div class="x-dialog-body ca2u26n" __cid="ca2u26n"></div> 
            </div> 
          </span>  
          <div component="$model/UI2/system/components/justep/panel/panel" class="x-panel x-full pc2mi6zm-iosstatusbar ca2u26n" xid="panel1" data-bind="component:{name:'$model/UI2/system/components/justep/panel/panel'}" __cid="ca2u26n">  
            <div class="x-panel-top ca2u26n" xid="top1" component="$model/UI2/system/components/justep/panel/child" data-bind="component:{name:'$model/UI2/system/components/justep/panel/child'}" __cid="ca2u26n"> 
              <div component="$model/UI2/system/components/justep/titleBar/titleBar" class="x-titlebar ca2u26n" style="background-color:white" xid="titleBar1" data-bind="component:{name:'$model/UI2/system/components/justep/titleBar/titleBar'}" data-config="{&#34;title&#34;:&#34;预告详情&#34;}" __cid="ca2u26n"> 
                <div class="x-titlebar-left ca2u26n" __cid="ca2u26n"> 
                  <a component="$model/UI2/system/components/justep/button/button" class="btn btn-link btn-only-icon ca2u26n" xid="backBtn1" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:{operation:'window.close'}" data-config="{&#34;icon&#34;:&#34;icon-chevron-left&#34;,&#34;label&#34;:&#34;&#34;}" __cid="ca2u26n"> 
                    <i class="icon-chevron-left ca2u26n" __cid="ca2u26n"></i>  
                    <span __cid="ca2u26n" class="ca2u26n"></span> 
                  </a> 
                </div>  
                <div class="x-titlebar-title ca2u26n" __cid="ca2u26n"> 
                  <span xid="span1" style="font-weight:normal;" __cid="ca2u26n" class="ca2u26n"></span>预告详情
                </div>  
                <div class="x-titlebar-right  ca2u26n" __cid="ca2u26n"> 
                  <div class="empty ca2u26n" __cid="ca2u26n"></div> 
                </div> 
              </div> 
            </div>  
            <div class="x-panel-content ca2u26n" xid="content1" component="$model/UI2/system/components/justep/panel/child" data-bind="component:{name:'$model/UI2/system/components/justep/panel/child'}" __cid="ca2u26n"> 
              <div xid="div1" __cid="ca2u26n" class="ca2u26n"> 
                <img alt="" xid="image1" style="width:100%;" src="../live/img/banner01.png" __cid="ca2u26n" class="ca2u26n"> 
              </div>  
              <div component="$model/UI2/system/components/justep/row/row" class="x-row nopadding ca2u26n" xid="row1" style="border-bottom:10px solid #f2f2f2;padding:10px;" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="ca2u26n"> 
                <div class="x-col nopadding ca2u26n" xid="col1" __cid="ca2u26n"> 
                  <span xid="span2" style="font-size:medium;" __cid="ca2u26n" class="ca2u26n"></span>  
                  <p xid="p1" style="margin:0px 0px 0px 0px;" __cid="ca2u26n" class="ca2u26n"> 
                    <span xid="span3" class="text-danger ca2u26n" __cid="ca2u26n">开课时间:</span>  
                    <span xid="span4" class="text-danger ca2u26n" __cid="ca2u26n">12/15/ 13:00</span> 
                  </p> 
                </div> 
              </div>  
              <div component="$model/UI2/system/components/justep/row/row" class="x-row nopadding ca2u26n" xid="row2" style="border-bottom:10px solid #f2f2f2;padding:10px;" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="ca2u26n"> 
                <div class="x-col nopadding ca2u26n" xid="col4" __cid="ca2u26n"> 
                  <span xid="span5" style="font-size:medium;line-height:2em;" __cid="ca2u26n" class="ca2u26n">直播课简介:</span>  
                  <p xid="p2" style="margin:0px 0px 0px 0px;color:#999;padding-left:10px;" __cid="ca2u26n" class="ca2u26n"></p> 
                </div> 
              </div>  
              <div component="$model/UI2/system/components/justep/row/row" class="x-row nopadding ca2u26n" xid="row3" style="padding:10px;" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="ca2u26n"> 
                <div class="x-col nopadding ca2u26n" xid="col5" __cid="ca2u26n"> 
                  <span xid="span8" style="font-size:medium;line-height:2em;" __cid="ca2u26n" class="ca2u26n">老师简介:</span>  
                  <div class="media ca2u26n" xid="media1" __cid="ca2u26n"> 
                    <div class="media-left ca2u26n" xid="mediaLeft1" __cid="ca2u26n"> 
                      <a href="#" xid="a1" __cid="ca2u26n" class="ca2u26n"> 
                        <img class="media-object img-circle ca2u26n" src="../live/img/tx.jpg" alt="" xid="image2" height="40px" style="width:40px;" __cid="ca2u26n"> 
                      </a> 
                    </div>  
                    <div class="media-body ca2u26n" xid="mediaBody1" __cid="ca2u26n"> 
                      <h4 class="media-heading  ca2u26n" xid="h41" style="color:#333;" __cid="ca2u26n">刘轩</h4>  
                      <p xid="p4" style="color:#999" __cid="ca2u26n" class="ca2u26n"></p> 
                    </div> 
                  </div> 
                </div> 
              </div> 
            </div>  
            <div class="x-panel-bottom ca2u26n" xid="bottom1" style="border-top:1px solid #f2f2f2;" component="$model/UI2/system/components/justep/panel/child" data-bind="component:{name:'$model/UI2/system/components/justep/panel/child'}" __cid="ca2u26n"> 
              <div component="$model/UI2/system/components/justep/row/row" class="x-row nopadding ca2u26n" xid="row4" style="height:100%;" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="ca2u26n"> 
                <div class="x-col x-col-50 ca2u26n" xid="col6" style="padding-left:10px;" __cid="ca2u26n"> 
                  <span xid="span10" __cid="ca2u26n" class="ca2u26n"></span>  
                  <span xid="span11" __cid="ca2u26n" class="ca2u26n">开课</span>  
                  <p xid="p5" style="font-size:medium;font-weight:bold;" __cid="ca2u26n" class="ca2u26n"> 
                    <span xid="span12" class="danger ca2u26n" __cid="ca2u26n">￥:</span>  
                    <span xid="span13" class="danger ca2u26n" __cid="ca2u26n"></span> 
                  </p> 
                </div>  
                <div class="x-col nopadding ca2u26n" xid="col7" __cid="ca2u26n"> 
                  <a component="$model/UI2/system/components/justep/button/button" class="btn btn-block ca2u26n" xid="addcartBtn" style="height:48px;line-height:30px;font-size:small;" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:addcartBtnClick" data-config="{&#34;label&#34;:&#34;加购物车&#34;}" __cid="ca2u26n"> 
                    <i xid="i1" __cid="ca2u26n" class="ca2u26n"></i>  
                    <span xid="span15" __cid="ca2u26n" class="ca2u26n">加购物车</span> 
                  </a> 
                </div>  
                <div class="x-col nopadding ca2u26n" xid="col8" __cid="ca2u26n"> 
                  <a component="$model/UI2/system/components/justep/button/button" class="btn btn-block  ca2u26n" style="height:48;line-height:30px;font-size:small;" xid="pay" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:payClick" data-config="{&#34;label&#34;:&#34;立刻购买&#34;}" __cid="ca2u26n"> 
                    <i xid="i2" __cid="ca2u26n" class="ca2u26n"></i>  
                    <span xid="span16" __cid="ca2u26n" class="ca2u26n">立刻购买</span> 
                  </a> 
                </div> 
              </div>  
              <div xid="div123" __cid="ca2u26n" class="ca2u26n"></div> 
            </div>  
            <style __cid="ca2u26n" class="ca2u26n">.x-panel.pc2mi6zm-iosstatusbar >.x-panel-top {height: 48px;}.x-panel.pc2mi6zm-iosstatusbar >.x-panel-content { top: 48px;bottom: nullpx;}.x-panel.pc2mi6zm-iosstatusbar >.x-panel-bottom {height: nullpx;}.iosstatusbar .x-panel.pc2mi6zm-iosstatusbar >.x-panel-top,.iosstatusbar .x-panel .x-panel-content .x-has-iosstatusbar.x-panel.pc2mi6zm-iosstatusbar >.x-panel-top {height: 68px;}.iosstatusbar .x-panel.pc2mi6zm-iosstatusbar >.x-panel-content,.iosstatusbar .x-panel .x-panel-content .x-has-iosstatusbar.x-panel.pc2mi6zm-iosstatusbar >.x-panel-content { top: 68px;}.iosstatusbar .x-panel .x-panel-content .x-panel.pc2mi6zm-iosstatusbar >.x-panel-top {height: 48px;}.iosstatusbar .x-panel .x-panel-content .x-panel.pc2mi6zm-iosstatusbar >.x-panel-content {top: 48px;}</style> 
          </div>  
          <div component="$model/UI2/system/components/justep/popMenu/popMenu" class="x-popMenu  ca2u26n" xid="popMenu1" data-bind="component:{name:'$model/UI2/system/components/justep/popMenu/popMenu'}" data-config="{&#34;anchor&#34;:&#34;bottom1&#34;,&#34;autoHidable&#34;:false,&#34;direction&#34;:&#34;auto&#34;,&#34;dismissible&#34;:false,&#34;opacity&#34;:0.8}" __cid="ca2u26n">  
            <div class="x-popMenu-overlay ca2u26n" xid="div2" __cid="ca2u26n"></div>  
            <ul component="$model/UI2/system/components/justep/menu/menu" class="x-menu dropdown-menu x-popMenu-content ca2u26n" xid="menu1" style="width:100%;" data-bind="component:{name:'$model/UI2/system/components/justep/menu/menu'}" __cid="ca2u26n"> 
              <li class="x-menu-item ca2u26n" xid="item1" style="text-align:center" __cid="ca2u26n"> 
                <a component="$model/UI2/system/components/justep/button/button" class="btn btn-link ca2u26n" xid="alipaypay" style="text-align:center" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:alipaypayClick" data-config="{&#34;label&#34;:&#34;支付宝支付&#34;}" __cid="ca2u26n"> 
                  <i xid="i3" __cid="ca2u26n" class="ca2u26n"></i>  
                  <span xid="span6" __cid="ca2u26n" class="ca2u26n">支付宝支付</span> 
                </a> 
              </li>  
              <li class="x-menu-divider divider ca2u26n" xid="divider2" __cid="ca2u26n"></li>  
              <li class="x-menu-item   hidden ca2u26n" xid="item2" style="text-align:center" __cid="ca2u26n"> 
                <a component="$model/UI2/system/components/justep/button/button" class="btn btn-link ca2u26n" xid="weixinpay" style="text-align:center" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:weixinpayClick" data-config="{&#34;label&#34;:&#34;微信支付&#34;}" __cid="ca2u26n"> 
                  <i xid="i4" __cid="ca2u26n" class="ca2u26n"></i>  
                  <span xid="span7" __cid="ca2u26n" class="ca2u26n">微信支付</span> 
                </a> 
              </li>  
              <li class="x-menu-divider divider  hidden ca2u26n" xid="divider3" __cid="ca2u26n"></li>  
              <li class="x-menu-item ca2u26n" xid="item3" __cid="ca2u26n"> 
                <a component="$model/UI2/system/components/justep/button/button" class="btn btn-link ca2u26n" xid="cancel" style="text-align:center" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:cancelClick" data-config="{&#34;label&#34;:&#34;取消&#34;}" __cid="ca2u26n"> 
                  <i xid="i5" __cid="ca2u26n" class="ca2u26n"></i>  
                  <span xid="span9" __cid="ca2u26n" class="ca2u26n">取消</span> 
                </a> 
              </li> 
            </ul> 
          </div> 
        </div>
      </div>  
      <div component="$model/UI2/system/components/justep/labelEdit/labelEdit" class="x-label-edit x-label10   hidden cuYraam" xid="labelEdit1" data-bind="component:{name:'$model/UI2/system/components/justep/labelEdit/labelEdit'}" __cid="cuYraam"> 
        <label class="x-label cuYraam" xid="label1" __cid="cuYraam">省</label>  
        <div class="x-edit cuYraam" xid="div7" __cid="cuYraam"> 
          <select class="form-control cuYraam" component="$model/UI2/system/components/justep/distpicker/provinceSelect" xid="provinceSelect4" data-bind="component:{ref:data1.ref('col0'),name:'$model/UI2/system/components/justep/distpicker/provinceSelect'}" __cid="cuYraam"></select>
        </div> 
      </div>  
      <div component="$model/UI2/system/components/justep/labelEdit/labelEdit" class="x-label-edit x-label10    hidden cuYraam" xid="labelEdit2" data-bind="component:{name:'$model/UI2/system/components/justep/labelEdit/labelEdit'}" __cid="cuYraam"> 
        <label class="x-label cuYraam" xid="label2" __cid="cuYraam">市</label>  
        <div class="x-edit cuYraam" xid="div6" __cid="cuYraam"> 
          <select class="form-control cuYraam" component="$model/UI2/system/components/justep/distpicker/citySelect" xid="citySelect4" data-bind="component:{ref:data1.ref('col1'),name:'$model/UI2/system/components/justep/distpicker/citySelect',provinceRef:data1.ref('col0')}" __cid="cuYraam"></select>
        </div> 
      </div>  
      <div component="$model/UI2/system/components/justep/labelEdit/labelEdit" class="x-label-edit x-label10  hidden cuYraam" xid="labelEdit3" data-bind="component:{name:'$model/UI2/system/components/justep/labelEdit/labelEdit'}" __cid="cuYraam"> 
        <label class="x-label cuYraam" xid="label3" __cid="cuYraam">县</label>  
        <div class="x-edit cuYraam" xid="div5" __cid="cuYraam"> 
          <select class="form-control cuYraam" component="$model/UI2/system/components/justep/distpicker/districtSelect" xid="districtSelect4" data-bind="component:{ref:data1.ref('col2'),name:'$model/UI2/system/components/justep/distpicker/districtSelect',cityRef:data1.ref('col1')}" __cid="cuYraam"></select>
        </div> 
      </div>
    </div> 
  </div>  
  <div xid="div9" __cid="cuYraam" class="cuYraam"> 
    <img src="../live/img/banner01.jpg" alt="" xid="image2" style="width:100%;" height="100%" __cid="cuYraam" class="cuYraam">
  </div>  
  <div xid="div8" __cid="cuYraam" class="cuYraam"> 
    <div component="$model/UI2/system/components/justep/row/row" class="x-row nopadding cuYraam" xid="row1" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="cuYraam"> 
      <div class="x-col x-col-67 x-col-70 cuYraam" xid="col1" __cid="cuYraam"> 
        <div component="$model/UI2/system/components/justep/row/row" class="x-row cuYraam" xid="row3" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="cuYraam"> 
          <span xid="span5" style="font-size:large;" __cid="cuYraam" class="cuYraam">学好摄影的三个方面</span>
        </div>  
        <div component="$model/UI2/system/components/justep/row/row" class="x-row nopadding cuYraam" xid="row4" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="cuYraam"> 
          <div class="x-col cuYraam" xid="col13" __cid="cuYraam"> 
            <span xid="span6" style="color:#999;" __cid="cuYraam" class="cuYraam">2016/12/8 17:00开课</span>
          </div> 
        </div> 
      </div>  
      <div class="x-col waikuan cuYraam" xid="col2" style="text-align:center;margin-top:auto;margin-bottom:auto;" __cid="cuYraam"> 
        <span xid="span10" class="border cuYraam" __cid="cuYraam">即将开始</span>
      </div> 
    </div> 
  </div>  
  <span component="$model/UI2/system/components/justep/messageDialog/messageDialog" xid="messageDialog1" data-bind="component:{name:'$model/UI2/system/components/justep/messageDialog/messageDialog'}" data-config="{&#34;title&#34;:&#34;确定要注销么？&#34;,&#34;type&#34;:&#34;OK&#34;}" __cid="cuYraam" class="cuYraam">
    <div class="x-modal-overlay cuYraam" __cid="cuYraam"></div>
    <div class="x-modal cuYraam" __cid="cuYraam">
      <div class="x-modal-inner cuYraam" __cid="cuYraam">
        <div class="x-modal-title cuYraam" __cid="cuYraam">确定要注销么？</div>
        <div class="x-modal-text cuYraam" __cid="cuYraam"></div>
        <input class="x-modal-prompt-input cuYraam" type="text" __cid="cuYraam">
      </div>
      <div class="x-modal-buttons cuYraam" __cid="cuYraam">
        <a class="x-modal-button x-modal-button-bold OK cuYraam" value="ok" __cid="cuYraam">确定</a>
        <a class="x-modal-button x-modal-button-bold Yes cuYraam" value="yes" __cid="cuYraam">是</a>
        <a class="x-modal-button x-modal-button-bold No cuYraam" value="no" __cid="cuYraam">否</a>
        <a class="x-modal-button x-modal-button-bold Cancel cuYraam" value="cancel" __cid="cuYraam">取消</a>
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
        <script src="../system/core.min.js"></script><script src="../system/common.min.js"></script><script src="../system/components/comp.min.js"></script><script src="../system/components/comp2.min.js"></script><script id="_mainScript">
        
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
    				context.model = '$model/UI2/live/test.w' + (document.location.search || "");
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