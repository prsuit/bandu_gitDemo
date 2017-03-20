define(function(require){
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");
	require('cordova!com.justep.cordova.plugin.alipay');
	require('cordova!com.justep.cordova.plugin.weixin.v3');

	var Model = function(){
		this.callParent();
	};

   var cartData;
   var payment;
   //支付宝费用
   var fee;
   //微信的费用
   var fee1;
   
   
   // 图片路径转换
	Model.prototype.getImageUrl = function(url) {
		return require.toUrl(url);
	};

	Model.prototype.deleteClick = function(event){
             // 行删除

		var row = event.bindingContext.$object;
		cartData.deleteData([ row ], {
			"async" : true,
			"onSuccess" : function() {
				//cartData.saveData();
			}
		});
		
	};


	Model.prototype.allchooseChange = function(event){

           /*
		1、全选多选框绑定变化事件onChange()
		2、点击全选多选框按钮，获取其值
		3、修改商品表中的fChoose字段为全选多选框按钮的值
		*/
		//var goodsData = this.comp("goodsData");
		
		//var cartData=window.data;
		
		var choose=this.comp("allChoose").val();
		//debugger;
		cartData.each(function(obj){
			if(choose){				
				cartData.setValue("fChoose","1",obj.row);
			} else {
				cartData.setValue("fChoose","",obj.row);
			}	
		});
         

	};


	Model.prototype.modelLoad = function(event){
         cartData=window.data;
          var local=window.localStorage;
	    var role=local.getItem("role");
	    var me=this;      
	    if(role==1){    
		 $(me.getElementByXid("titleBar1")).css("background-color","#00A8FF"); 
		 $(me.getElementByXid("col3")).css("background-color","#00A8FF");   	  
	    }else{
	     $(me.getElementByXid("titleBar1")).css("background-color","#9ed04d"); 
	     $(me.getElementByXid("col3")).css("background-color","#9ed04d"); 
	    }
         
	};
	
	
	Model.prototype.settlementClick = function(event){
	       var me=this;
	       var list=[];
	       var cartData=window.data;
	       //支付宝的计数
	        fee=$(me.getElementByXid("span3")).text();
	       
	       //微信的计数
            fee1=$(me.getElementByXid("span3")).text()*100;
	       
	       var userid=window.localStorage.getItem("userid");
	       
	       cartData.each(function(param){
	        if(param.row.val('fChoose')){
            list.push(param.row.val('id'));
            }
           });
	       if(list.length>0){     
	    	   if(userid!=null&userid!=""){
	    		   //向服务端传送商品编号
	    		   if ($(this.comp("popMenu1").$domNode).css("display") == "block") {
	    			   this.comp("popMenu1").hide();
	    		   } else {
	    			   this.comp("popMenu1").show();
	    		   }
	    	   }else{
	    		   justep.Util.hint("您未登录，不能购买");
	    	   } 
	       }else{
	    	   justep.Util.hint("请选择商品");	
	       } 
	};



	Model.prototype.button4Click = function(event){
	
			this.comp("popOver1").hide();
		

	};

	Model.prototype.radio3Change = function(event){
	
	   	  var row=event.bindingContext.$object;
	      var b=row.val("name");
	      payment=b;

	};

      //离开shi
	Model.prototype.modelUnLoad = function(event){
	
	 var me=this;
	

	};


	Model.prototype.alipaypayClick = function(event){
         
         this.comp("popMenu1").hide();

			var list=[];
			var list1=[];
			var list2=[];
			cartData.each(function(param){
				if(param.row.val('fChoose')){
					list.push(param.row.val('Id'));
					list1.push(param.row.val('Live_name'));
					list2.push(param.row.val('Money'));
				}
			}); 
			
		/*	cartData.each(function(param){
				if(param.row.val('fChoose')){
					list1.push(param.row.val('Live_name'));
				}
			});
            
            cartData.each(function(param){
				if(param.row.val('fChoose')){
					list2.push(param.row.val('Money'));
				}
			});
			*/
     
			var orderID = justep.UUID.createUUID();
			if (!navigator.alipay) {
				alert("没有打入com.justep.cordova.plugin.alipay插件");
				return;
			}
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
					//发送消费记录
					
					var list4=[];
					cartData.each(function(param){
						list4.push(param.row);
					});
					if(list4.length>0){	  
						for (var i = 0; i < list4.length; i++) {
							cartData.deleteData([list4[i]], {
								"async" : true,
								"onSuccess" : function() {
									//cartData.saveData();
								}
							})

						}		
					}
				
				  var userid=window.localStorage.getItem("userid");	
				  for (var i = 0; i <list.length; i++) {
				     var aid=list[i];
				     var Live_name=list1[i]; 
				     var alifee=list2[i]*100;   			          
					justep.Baas.sendRequest({
						"url" : "/live_show/live_show",
						"action" : "consumerDetails1",
						"async" : false,
						"params" : {
							live_list:aid,
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
				}		
						
				} else {
					justep.Util.hint("支付失败");
				}
			}, function(msg) {
				justep.Util.hint("支付失败");
			});
         
	};











	Model.prototype.weixinpayClick = function(event){
          this.comp("popMenu1").hide();

			var list=[];
			var list1=[];
			var list2=[];
			cartData.each(function(param){
				if(param.row.val('fChoose')){
					list.push(param.row.val('Id'));
					list1.push(param.row.val('Live_name'));
					list2.push(param.row.val('Money'));
				}
			}); 
		/*	cartData.each(function(param){
				if(param.row.val('fChoose')){
					list1.push(param.row.val('Live_name'));
				}
			});	
			cartData.each(function(param){
				if(param.row.val('fChoose')){
					list2.push(param.row.val('Money'));
				}
			});*/

			var tradeNo = justep.UUID.createUUID();   //通常是交易流水号
			var notifyUrl = location.origin + "/baas/weixin/weixin/notify";//支付成功通知地址

			var successCallback = function(message) {  //成功回调   	
				//var a=JSON.stringify(message);
				
				var a=JSON.parse(message);
			    //获取用户名
				if(a==0){
				    var list4=[];
					cartData.each(function(param){
						list4.push(param.row);
					});
					if(list4.length>0){	  
						for (var i = 0; i < list4.length; i++) {
							cartData.deleteData([list4[i]], {
								"async" : true,
								"onSuccess" : function() {
									//cartData.saveData();
								}
							})

						}		
					}
				 
					var userid=window.localStorage.getItem("userid");
					for (var i = 0; i < list.length; i++) {
						var livelist=list[i];
						var livetitle=list1[i];
						var wxfee=list2[i]*100;
						//发送消费记录
						justep.Baas.sendRequest({
							"url" : "/live_show/live_show",
							"action" : "consumerDetails",
							"async" : false,
							"params" : {
								live_list:livelist,
								tradeNo:tradeNo,
								userid:userid,
								responseCode:"1",
								fee:wxfee,
								livetitle:livetitle	
							},			
							"error":function(data){
								justep.Util.hint("网络故障");
							}
						});
					}		    
				justep.Util.hint("支付成功");				
				}else if(a==-2){
					justep.Util.hint("支付取消");			
				}else{
					justep.Util.hint("支付失败");
				}
			};
			var failCallback = function(message) { //失败回调
				justep.Util.hint("支付失败");
			};
			var cancelCallback = function(message) { //用户取消支付回调
				justep.Util.hint("支付取消");
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