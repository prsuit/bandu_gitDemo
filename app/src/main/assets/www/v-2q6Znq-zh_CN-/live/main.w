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
    	<div id="applicationHost" class="applicationHost" style="width:100%;height:100%;" __component-context__="block"><div xid="window" class="window cviIRBv" component="$model/UI2/system/components/justep/window/window" design="device:m;" data-bind="component:{name:'$model/UI2/system/components/justep/window/window'}" __cid="cviIRBv" components="$model/UI2/system/components/justep/model/model,$model/UI2/system/components/justep/loadingBar/loadingBar,$model/UI2/system/components/justep/button/button,$model/UI2/system/components/justep/scrollView/scrollView,$model/UI2/system/components/justep/list/list,$model/UI2/system/components/justep/panel/child,$model/UI2/system/components/justep/panel/panel,$model/UI2/system/components/justep/contents/content,$model/UI2/system/components/justep/row/row,$model/UI2/system/components/justep/titleBar/titleBar,$model/UI2/system/components/justep/contents/contents,$model/UI2/system/components/justep/data/data,$model/UI2/system/components/justep/window/window,$model/UI2/system/components/justep/button/buttonGroup,">
  <style>.nopadding.cviIRBv{padding: 0px} nomargin.cviIRBv{margin: 0px} .border.cviIRBv{border: 1px solid rgb(44, 193, 123); padding: 10px; color: rgb(44, 193, 123); border-radius: 20px} .x-nav-guide.cviIRBv{margin-top: 8px} .x-nav-guide.cviIRBv .btn-link.cviIRBv{font-weight: normal; color: rgb(255, 255, 255); opacity: 0.5; font-size: 90%} .x-nav-guide.cviIRBv .btn.cviIRBv{height: 40px; border-top: 0} .x-nav-guide.cviIRBv .btn.active.cviIRBv{border-bottom: 2px solid rgb(255, 255, 255); color: rgb(255, 255, 255); opacity: 1; font-size: 100%} .x-titlebar.cviIRBv .x-titlebar-title.cviIRBv{padding: 0px; overflow: hidden} .del.cviIRBv{text-decoration: line-through} .daojishi.cviIRBv{background-color: rgb(255, 0, 0); color: rgb(255, 255, 255); padding: 2px; width: 80%; margin: -5px auto 15px auto} .daojishi1.cviIRBv{background-color: rgb(0, 133, 202); color: rgb(255, 255, 255); padding: 2px; width: 80%; margin: -5px auto 15px auto} @media screen and (max-width: 768px) {.daojishi { margin: -18px auto 15px auto } } .lianghang.cviIRBv{overflow: hidden; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; word-break: break-all}</style>  
  <div component="$model/UI2/system/components/justep/model/model" xid="model" style="display:none" data-bind="component:{name:'$model/UI2/system/components/justep/model/model'}" data-events="onActive:modelActive;onLoad:modelLoad" __cid="cviIRBv" class="cviIRBv"></div>  
  <div component="$model/UI2/system/components/justep/panel/panel" class="x-panel x-full  pcvQFvQv-iosstatusbar cviIRBv" xid="panel1" data-bind="component:{name:'$model/UI2/system/components/justep/panel/panel'}" __cid="cviIRBv"> 
    <div class="x-panel-top cviIRBv" xid="top1" component="$model/UI2/system/components/justep/panel/child" data-bind="component:{name:'$model/UI2/system/components/justep/panel/child'}" __cid="cviIRBv"> 
      <div component="$model/UI2/system/components/justep/titleBar/titleBar" class="x-titlebar cviIRBv" xid="titleBar1" style="background-color:#FFFFFF;" data-bind="component:{name:'$model/UI2/system/components/justep/titleBar/titleBar'}" __cid="cviIRBv"> 
        <div class="x-titlebar-left cviIRBv" xid="left1" __cid="cviIRBv"> 
          <a component="$model/UI2/system/components/justep/button/button" class="btn btn-link btn-only-icon   cviIRBv" xid="button1" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:button1Click" data-config="{&#34;icon&#34;:&#34;icon-android-social-user&#34;,&#34;label&#34;:&#34;button&#34;}" __cid="cviIRBv"> 
            <i xid="i1" class="icon-android-social-user cviIRBv" __cid="cviIRBv"></i>  
            <span xid="span1" __cid="cviIRBv" class="cviIRBv"></span>
          </a> 
        </div>  
        <div class="x-titlebar-title cviIRBv" xid="title1" __cid="cviIRBv"> 
          <div component="$model/UI2/system/components/justep/button/buttonGroup" class="btn-group x-nav-guide cviIRBv" xid="buttonGroup1" style="margin-top:4px;" data-bind="component:{name:'$model/UI2/system/components/justep/button/buttonGroup'}" data-config="{&#34;tabbed&#34;:true}" __cid="cviIRBv"> 
            <a component="$model/UI2/system/components/justep/button/button" class="btn btn-link btn-only-label  active cviIRBv" xid="button5" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-config="{&#34;label&#34;:&#34;预告&#34;,&#34;target&#34;:&#34;content4&#34;}" __cid="cviIRBv"> 
              <i xid="i11" __cid="cviIRBv" class="cviIRBv"></i>  
              <span xid="span35" __cid="cviIRBv" class="cviIRBv">预告</span>
            </a>  
            <a component="$model/UI2/system/components/justep/button/button" class="btn btn-link btn-only-label cviIRBv" xid="button6" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-config="{&#34;label&#34;:&#34;直播&#34;,&#34;target&#34;:&#34;content3&#34;}" __cid="cviIRBv"> 
              <i xid="i12" __cid="cviIRBv" class="cviIRBv"></i>  
              <span xid="span34" __cid="cviIRBv" class="cviIRBv">直播</span>
            </a> 
          </div> 
        </div>  
        <div class="x-titlebar-right  cviIRBv" xid="right1" __cid="cviIRBv">
          <div class="empty cviIRBv" __cid="cviIRBv"></div>
          <a component="$model/UI2/system/components/justep/button/button" class="btn btn-link btn-only-icon cviIRBv" xid="button3" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:button3Click" data-config="{&#34;icon&#34;:&#34;linear linear-bug&#34;,&#34;label&#34;:&#34;button&#34;}" __cid="cviIRBv"> 
            <i xid="i4" class="linear linear-bug cviIRBv" __cid="cviIRBv"></i>  
            <span xid="span11" __cid="cviIRBv" class="cviIRBv"></span>
          </a>
        </div> 
      </div> 
    </div>  
    <div class="x-panel-content cviIRBv" xid="content1" _xid="C755B074C98000017C1A4060F8DD1B85" style="bottom: 0px;" component="$model/UI2/system/components/justep/panel/child" data-bind="component:{name:'$model/UI2/system/components/justep/panel/child'}" __cid="cviIRBv"> 
      <div component="$model/UI2/system/components/justep/contents/contents" class="x-contents x-full cviIRBv" xid="contents2" data-bind="component:{name:'$model/UI2/system/components/justep/contents/contents'}" data-config="{&#34;active&#34;:0,&#34;swipe&#34;:false}" __cid="cviIRBv"> 
        <div class="x-contents-content  x-scroll-view active cviIRBv" xid="content4" component="$model/UI2/system/components/justep/contents/content" data-bind="component:{name:'$model/UI2/system/components/justep/contents/content'}" __cid="cviIRBv"> 
          <div class="x-scroll  cviIRBv" component="$model/UI2/system/components/justep/scrollView/scrollView" xid="scrollView3" data-bind="component:{name:'$model/UI2/system/components/justep/scrollView/scrollView'}" data-config="{&#34;autoAppend&#34;:true}" __cid="cviIRBv"> 
            <div class="x-content-center x-pull-down container cviIRBv" xid="div15" __cid="cviIRBv"> 
              <i class="x-pull-down-img glyphicon x-icon-pull-down cviIRBv" xid="i10" __cid="cviIRBv"></i>  
              <span class="x-pull-down-label cviIRBv" xid="span26" __cid="cviIRBv">下拉刷新...</span>
            </div>  
            <div class="x-scroll-content  cviIRBv" xid="div14" __cid="cviIRBv"> 
              <div component="$model/UI2/system/components/justep/list/list" class="x-list   cviIRBv" xid="list3" data-bind="component:{name:'$model/UI2/system/components/justep/list/list'}" data-config="{&#34;data&#34;:&#34;trailerData&#34;,&#34;filter&#34;:&#34; $row.val(\&#34;Live_timelength\&#34;)&gt;0&#34;,&#34;limit&#34;:3}" __cid="cviIRBv"> 
                <ul class="x-list-template x-min-height    hide cviIRBv" xid="listTemplateUl3" componentname="$UI/system/components/justep/list/list#listTemplateUl" id="undefined_listTemplateUl1" __cid="cviIRBv" data-bind="foreach:{data:$model.foreach_list3($element),afterRender:$model.foreach_afterRender_list3.bind($model,$element)}"> 
                  <li xid="li3" class="x-min-height    cviIRBv" componentname="li(html)" id="undefined_li1" style="border-bottom:10px solid #f6f6f6;" __cid="cviIRBv" data-bind="event:{click:$model._callModelFn.bind($model, 'li3Click')}"> 
                    <div xid="div12" __cid="cviIRBv" class="cviIRBv"> 
                      <img alt="" xid="image3" style="width:100%;" __cid="cviIRBv" class="cviIRBv" data-bind="attr:{src:$model.getImageUrl(val(&#34;File&#34;))}">
                    </div>  
                    <div xid="div11" __cid="cviIRBv" class="cviIRBv"> 
                      <div component="$model/UI2/system/components/justep/row/row" class="x-row  cviIRBv" xid="row7" style="padding:0 0 10px 0;" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="cviIRBv"> 
                        <div class="x-col x-col-67 cviIRBv" xid="col8" __cid="cviIRBv"> 
                          <div component="$model/UI2/system/components/justep/row/row" class="x-row cviIRBv" xid="row8" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="cviIRBv"> 
                            <span xid="span28" style="font-size:large;" class="lianghang cviIRBv" __cid="cviIRBv" data-bind="text:val(&#34;Live_name&#34;)"></span>
                          </div>  
                          <div component="$model/UI2/system/components/justep/row/row" class="x-row nopadding cviIRBv" xid="row9" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="cviIRBv"> 
                            <div class="x-col cviIRBv" xid="col6" style="color:#999;" __cid="cviIRBv"> 
                              <span xid="span42" __cid="cviIRBv" class="cviIRBv" data-bind="text:val(&#34;Live_time&#34;)"></span>  
                              <span xid="span44" __cid="cviIRBv" class="cviIRBv">开课</span>
                            </div> 
                          </div>  
                          <div component="$model/UI2/system/components/justep/row/row" class="x-row nopadding cviIRBv" xid="row14" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="cviIRBv"> 
                            <div class="x-col cviIRBv" xid="col16" style="color:#999;" __cid="cviIRBv"> 
                              <span xid="span48" class="text-danger  cviIRBv" style="font-size:medium;" __cid="cviIRBv">￥</span>  
                              <span xid="span47" style="margin-left:-4px;font-size:medium;" class="text-danger  cviIRBv" __cid="cviIRBv" data-bind="text:val(&#34;Money&#34;)"></span>
                            </div> 
                          </div> 
                        </div>  
                        <div class="x-col waikuan cviIRBv" xid="col7" style="text-align:center;margin-top:auto;margin-bottom:auto;" __cid="cviIRBv"> 
                          <div xid="div18" style="background-color:ff0000;" class="daojishi cviIRBv" __cid="cviIRBv"> 
                            <p xid="p1" style="margin:0px;" __cid="cviIRBv" class="cviIRBv">剩余时间:</p>  
                            <p xid="p2" style="margin:0px;background-color:transparent;" __cid="cviIRBv" class="cviIRBv" data-bind="text:$model.showTime( $element, val(&#34;Live_timelength&#34;),$object)"></p>
                          </div>  
                          <span xid="span33" class="border cviIRBv" style="height:37px;" __cid="cviIRBv" data-bind="event:{click:$model._callModelFn.bind($model, 'li3Click')}">点击报名</span> 
                        </div> 
                      </div> 
                    </div> 
                  </li> 
                </ul> 
              </div> 
            </div>  
            <div class="x-content-center x-pull-up cviIRBv" xid="div13" __cid="cviIRBv"> 
              <span class="x-pull-up-label cviIRBv" xid="span27" __cid="cviIRBv">加载更多...</span>
            </div> 
          </div>  
          <div xid="div7" style="z-index:2000;margin-top:0px;background-color:white;" class="hidden cviIRBv" __cid="cviIRBv"> 
            <img src="../live10/img/1.png" alt="" xid="image4" style="border:none;max-width:100%;max-height:100%;display:block;margin:180px auto auto auto;" __cid="cviIRBv" class="cviIRBv">
          </div>
        </div>
        <div class="x-contents-content  x-scroll-view cviIRBv" xid="content3" component="$model/UI2/system/components/justep/contents/content" data-bind="component:{name:'$model/UI2/system/components/justep/contents/content'}" data-events="onActive:content3Active" __cid="cviIRBv"> 
          <div class="x-scroll  cviIRBv" component="$model/UI2/system/components/justep/scrollView/scrollView" xid="scrollView1" data-bind="component:{name:'$model/UI2/system/components/justep/scrollView/scrollView'}" __cid="cviIRBv"> 
            <div class="x-content-center x-pull-down container cviIRBv" xid="div1" __cid="cviIRBv"> 
              <i class="x-pull-down-img glyphicon x-icon-pull-down cviIRBv" xid="i3" __cid="cviIRBv"></i>  
              <span class="x-pull-down-label cviIRBv" xid="span3" __cid="cviIRBv">下拉刷新...</span>
            </div>  
            <div class="x-scroll-content  cviIRBv" xid="div2" __cid="cviIRBv"> 
              <div component="$model/UI2/system/components/justep/list/list" class="x-list   cviIRBv" xid="list1" data-bind="component:{name:'$model/UI2/system/components/justep/list/list'}" data-config="{&#34;autoLoad&#34;:true,&#34;data&#34;:&#34;liveData&#34;}" __cid="cviIRBv"> 
                <ul class="x-list-template x-min-height    hide cviIRBv" xid="listTemplateUl1" componentname="$UI/system/components/justep/list/list#listTemplateUl" id="undefined_listTemplateUl1" __cid="cviIRBv" data-bind="foreach:{data:$model.foreach_list1($element),afterRender:$model.foreach_afterRender_list1.bind($model,$element)}"> 
                  <li xid="li1" class="x-min-height    cviIRBv" componentname="li(html)" id="undefined_li1" style="border-bottom:10px solid #f6f6f6;" __cid="cviIRBv" data-bind="event:{click:$model._callModelFn.bind($model, 'li1Click')}"> 
                    <div xid="div4" __cid="cviIRBv" class="cviIRBv"> 
                      <img alt="" xid="image2" style="width:100%;" __cid="cviIRBv" class="cviIRBv" data-bind="attr:{src:$model.getImageUrl(val(&#34;File&#34;))}">
                    </div>  
                    <div xid="div5" __cid="cviIRBv" class="cviIRBv"> 
                      <div component="$model/UI2/system/components/justep/row/row" class="x-row nopadding cviIRBv" xid="row1" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="cviIRBv"> 
                        <div class="x-col x-col-67 x-col-70 cviIRBv" xid="col1" __cid="cviIRBv"> 
                          <div component="$model/UI2/system/components/justep/row/row" class="x-row cviIRBv" xid="row3" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="cviIRBv"> 
                            <span xid="span5" style="font-size:large;" __cid="cviIRBv" class="cviIRBv" data-bind="text:val(&#34;Live_name&#34;)"></span>
                          </div>  
                          <div component="$model/UI2/system/components/justep/row/row" class="x-row nopadding cviIRBv" xid="row4" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="cviIRBv"> 
                            <div class="x-col cviIRBv" xid="col13" __cid="cviIRBv"> 
                              <span xid="span7" style="margin-right:5px;color:#999;" __cid="cviIRBv" class="cviIRBv" data-bind="text:val(&#34;live_time&#34;)"></span>
                              <span xid="span8" class="danger cviIRBv" __cid="cviIRBv">￥</span>  
                              <span xid="span9" class="danger cviIRBv" __cid="cviIRBv" data-bind="text:val(&#34;Money&#34;)"></span>
                            </div> 
                          </div> 
                        </div>  
                        <div class="x-col waikuan cviIRBv" xid="col2" style="text-align:center;margin-top:auto;margin-bottom:auto;" __cid="cviIRBv"> 
                          <span xid="span10" class="border cviIRBv" __cid="cviIRBv">正在直播</span>
                        </div> 
                      </div> 
                    </div> 
                  </li>  
                  <li xid="li4" class="x-min-height    cviIRBv" componentname="li(html)" id="undefined_li1" __cid="cviIRBv"></li> 
                </ul> 
              </div> 
            </div>  
            <div class="x-content-center x-pull-up cviIRBv" xid="div3" __cid="cviIRBv"> 
              <span class="x-pull-up-label cviIRBv" xid="span4" __cid="cviIRBv">加载更多...</span>
            </div> 
          </div>  
          <div xid="div6" style="z-index:2000;margin-top:0px;background-color:white;" class="hidden cviIRBv" __cid="cviIRBv"> 
            <img src="../live10/img/1.png" alt="" xid="image1" style="border:none;max-width:100%;max-height:100%;display:block;margin:180px auto auto auto;" __cid="cviIRBv" class="cviIRBv">
          </div>
        </div> 
      </div> 
    </div>  
    <style __cid="cviIRBv" class="cviIRBv">.x-panel.pcvQFvQv-iosstatusbar >.x-panel-top {height: 48px;}.x-panel.pcvQFvQv-iosstatusbar >.x-panel-content { top: 48px;bottom: nullpx;}.x-panel.pcvQFvQv-iosstatusbar >.x-panel-bottom {height: nullpx;}.iosstatusbar .x-panel.pcvQFvQv-iosstatusbar >.x-panel-top,.iosstatusbar .x-panel .x-panel-content .x-has-iosstatusbar.x-panel.pcvQFvQv-iosstatusbar >.x-panel-top {height: 68px;}.iosstatusbar .x-panel.pcvQFvQv-iosstatusbar >.x-panel-content,.iosstatusbar .x-panel .x-panel-content .x-has-iosstatusbar.x-panel.pcvQFvQv-iosstatusbar >.x-panel-content { top: 68px;}.iosstatusbar .x-panel .x-panel-content .x-panel.pcvQFvQv-iosstatusbar >.x-panel-top {height: 48px;}.iosstatusbar .x-panel .x-panel-content .x-panel.pcvQFvQv-iosstatusbar >.x-panel-content {top: 48px;}</style>
  </div>
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
    				context.model = '$model/UI2/live/main.w' + (document.location.search || "");
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