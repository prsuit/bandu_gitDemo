define(function(require){
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");
    require('cordova!com.justep.cordova.plugin.alipay');
    require('cordova!com.justep.cordova.plugin.weixin.v3');
	
	var Model = function(){
		this.callParent();
	
	};
    var aid;
    var Id;
    var Money;
    var Live_name;
    var Live_time;
    var File;
    var payment;
    //支付宝费用
    var fee;
    //微信费用
    var fee1;
    var cartData;
    var userid;
    
    // 图片路径转换
	Model.prototype.getImageUrl = function(url) {
		return require.toUrl(url);
	};
    
    //加入购物车
	Model.prototype.addcartBtnClick = function(event){
	
        var me=this;
        var readyData=me.comp("readyData");
	
		var userid=window.localStorage.getItem("userid");
		if(userid!=null&userid!=""){

			if(readyData.find(['id'],[Id]).length===0){

				if (cartData.find(['Id'],[Id]).length ===0) {
					cartData.newData({
						index : 0,
						defaultValues : [ {
							"Id" :Id,
							"File":File,
							"Money" :Money,
							"Live_name":Live_name,
							"Live_time":Live_time,
						} ]
					});

					justep.Util.hint("成功加入购物车!");
				}else{
					justep.Util.hint("您已加入购物车，无需重复添加");	    
				};

				}else{

					justep.Util.hint("您已经购买过此课程，无需重复购买");	 

				} 
			}else{
				var url="./wode.w";
				justep.Shell.showPage(require.toUrl(url));
			} 
		
         
           
	};


	Model.prototype.payClick = function(event){
	
		var me=this;

		var userid=window.localStorage.getItem("userid");

		fee=$(me.getElementByXid("span13")).text(); 
		fee1=$(me.getElementByXid("span13")).text()*100; 
		// debugger;
		if(userid!=null&userid!=""){ 

			var readyData=me.comp("readyData");

			if(readyData.find(['id'],[Id]).length===0){

				if ($(this.comp("popMenu1").$domNode).css("display") == "block") {
					this.comp("popMenu1").hide();
				} else {
					this.comp("popMenu1").show();
				}	
			}else{

				justep.Util.hint("您已经购买过此课程，无需重复购买");	
			}

		}else{

			var url="./wode.w";
			justep.Shell.showPage(require.toUrl(url));

		}		
    
	};






	Model.prototype.modelParamsReceive = function(event){
                 
                  var me=this;
                  
                  //直播名称
                 Live_name=event.params.Live_name;
                 //直播预览图
                 File=event.params.File;
                 //教师简介
                 var Teacher_jj=event.params.Teacher_jj;
                 //debugger;
                 //直播时间
                 Live_time=event.params.Live_time;
                 //课程费用
                 Money=event.params.Money;
                 //教师姓名
                 var Teacher_name=event.params.Teacher_name;
                 //直播课程编号
                 Id=event.params.Id;
                 
                 //直播课程简介
                 Live_jj=event.params.Live_jj;
                 //debugger;
  
                $(me.getElementByXid("image1")).attr("src",File);
                $(me.getElementByXid("span2")).text(Live_name);
                $(me.getElementByXid("h41")).text(Teacher_name);
                $(me.getElementByXid("p4")).text(Teacher_jj);
                $(me.getElementByXid("span10")).text(Live_time);
                $(me.getElementByXid("span4")).text(Live_time);
                $(me.getElementByXid("span13")).text(Money);
                $(me.getElementByXid("p2")).text(Live_jj);
              
                //debugger;

	};


	Model.prototype.windowDialog1Received = function(event){
	
	            var t=event.data.t;
           if(t==1){
               this.comp("windowDialog1").close();
         }
         
         if(t==0){
            this.comp("windowDialog1").close();
         }

	};




	Model.prototype.modelLoad = function(event){
	  	cartData=window.data;
	    var local=window.localStorage;
	  	userid=local.getItem("adminid");
	  	
	  	var local=window.localStorage;
	    var role=local.getItem("role");
	    var me=this;      
	    if(role==1){     
		 $(me.getElementByXid("titleBar1")).css("background-color","#00A8FF");
		 $(me.getElementByXid("addcartBtn")).addClass("gouwuche1");
		 $(me.getElementByXid("pay")).addClass("goumai1");  	  
	    }else{ 
	     $(me.getElementByXid("titleBar1")).css("background-color","#9ed04d");
	     $(me.getElementByXid("addcartBtn")).addClass("gouwuche");
	     $(me.getElementByXid("pay")).addClass("goumai");   
	    }    
	         $(me.getElementByXid("pay")).removeAttr("disabled");
		     $(me.getElementByXid("addcartBtn")).removeAttr("disabled");
	    
	};





	Model.prototype.readyDataCustomRefresh = function(event){
	
	     var readyData=this.comp("readyData");
	
	     var local=window.localStorage;
	     var userid=local.getItem("userid");
	     if(userid!=null&userid!=""){
                  justep.Baas.sendRequest({
				"url":"/live_show/live_show",
				"action":"purchaseHistory",
				"async":false,
				"params":{
				  userid:userid,
				  type:0			
				},
				"success":function(data) {
				    if(data.data.length>0){	 
					   readyData.loadData(data.data);					  
					}
				},
				"error":function(){
				     justep.Util.hint("网络故障，请稍后再试");
				}
			});
	       }

	};





	Model.prototype.alipaypayClick = function(event){
      	this.comp("popMenu1").hide();
	
	     var orderID = justep.UUID.createUUID();
		if (!navigator.alipay) {
			alert("没有打入com.justep.cordova.plugin.alipay插件");
			return;
		}
		var x=$(this.getElementByXid("pay"));
		var y=$(this.getElementByXid("addcartBtn"));
		var alifee=fee*100;
		var userid=window.localStorage.getItem("userid");
		var notifyUrl = location.origin;
		var tradeNo = orderID;
		var alipay = navigator.alipay;
		alipay.pay({
			"seller" : "wangdandan@teacheredu.cn", // 卖家支付宝账号或对应的支付宝唯一用户号
			"subject" : "北京东方汇通", // 商品名称
			"body" : "北京东方汇通", // 商品详情
			"price" :fee, // 金额，单位为RMB
			"tradeNo" : tradeNo, // 唯一订单号
			"timeout" : "30m", // 超时设置
			"notifyUrl" : notifyUrl
		// 服务器通知路径
		}, function(message) {
			var responseCode = parseInt(message);
			if (responseCode === 9000) {
		     	x.attr("disabled","disabled");
		        y.attr("disabled","disabled");				
				//发送消费记录	
			 justep.Baas.sendRequest({
				"url" : "/live_show/live_show",
				"action" : "consumerDetails1",
				"async" : false,
				"params" : {
				    live_list:Id,
				    tradeNo:orderID,
				    userid:userid,
				    responseCode:responseCode,
				    fee:alifee,
				    livetitle:Live_name	
				},
				"error":function(data){
				     justep.Util.hint("网络故障");
				}
			});
			} else {
				justep.Util.hint("支付失败");
			}
		}, function(msg) {
			justep.Util.hint("支付失败");
		});
	};





	Model.prototype.weixinpayClick = function(event){
               this.comp("popMenu1").hide();
          var x=$(this.getElementByXid("pay"));
		  var y=$(this.getElementByXid("addcartBtn"));    
        var userid=window.localStorage.getItem("userid");   
		var tradeNo = justep.UUID.createUUID();   // 通常是交易流水号
		var notifyUrl = location.origin + "/baas/weixin/weixin/notify";// 支付成功通知地址
		var successCallback = function(message) { 	
		  //var a=JSON.stringify(message);
		  //justep.Util.hint(a);
		  var a=JSON.parse(message);
		    // 成功回调
		    //发送消费记录
		    if(a==0){
		     x.attr("disabled","disabled");
		     y.attr("disabled","disabled");
			 justep.Baas.sendRequest({
				"url" : "/live_show/live_show",
				"action" : "consumerDetails",
				"async" : false,
				"params" : {
				    live_list:Id,
				    tradeNo:tradeNo,
				    userid:userid,
				    responseCode:"1",
				    fee:fee1,
				    livetitle:Live_name	
				},
				"error":function(data){
				     justep.Util.hint("网络故障");
				}
			});
			   justep.Util.hint("支付成功");			  
			   
			}else if(a==-2){
			   justep.Util.hint("取消订单");
			}else{
			     justep.Util.hint(a);
			    justep.Util.hint("支付失败");
			}
		
		};
		var failCallback = function(message) { // 失败回调
		    //alert(JSON.stringify(message))
			justep.Util.hint("支付失败是失败回调："+JSON.stringify(message));
		};
		var cancelCallback = function(message) { // 用户取消支付回调
			//alert(JSON.stringify(message));
			justep.Util.hint("用户取消支付");
		};
		var weixin = navigator.weixin;
		weixin.generatePrepayId({ // 生成预支付id
			"body" : "北京东方汇通",
			"notifyUrl" : notifyUrl,
			"totalFee" :fee1,
			"tradeNo" : tradeNo
		}, function(prepayId) {
			weixin.sendPayReq(prepayId, function(message) { // 支付
				successCallback(message);         				
			}, function(message) {
				cancelCallback(message);
			});
		}, function(message) {
			failCallback(message);
		});
	};





	Model.prototype.cancelClick = function(event){
	   
	    this.comp("popMenu1").hide();
	    
	};





	return Model;
});

define(function(require){
	var $ = require("jquery");
	var Model = function(){
		this.callParent();
	};
	Model.prototype.modelParamsReceive = function(event){

	};

	
	

	return Model;
});
