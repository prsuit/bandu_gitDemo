define(function(require){
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");

	var Model = function(){
		this.callParent();
	};

	Model.prototype.button3Click = function(event){
	
	    this.comp("popMenu1").show();

	};
	
	
	

	Model.prototype.button4Click = function(event){
          this.comp("popMenu1").hide();
	};
	
	
	

	Model.prototype.button33Click = function(event){
	
	         justep.Baas.sendRequest({
				"url" : "/live_show/live_show",
				"action" : "",
				"async" : false,
				"params" : {
				     
				},
				"success" : function(data) {
				   alert();
				}
			});
	    
	};
	
	
	

	Model.prototype.readyDataCustomRefresh = function(event){
                   var me=this;
	     var readyData=this.comp("readyData");
	       var local=window.localStorage;
	      var temp=$(me.getElementByXid("div7"));
	        var userid=local.getItem("userid");
	        
	     if(userid!=null&userid!=""){   
	        justep.Baas.sendRequest({
				"url" : "/live_show/live_show",
				"action" : "purchaseHistory",
				"async" : false,
				"params" : {
				  userid:userid,
				  type:0			
				},
				"success" : function(data) {
				    if(data.data.length>0){	
				        temp.attr("class","hidden");    
					   readyData.loadData(data.data);
					  //debugger;					  
					}else{
					    temp.attr("class","x-full");  
					   // debugger; 
					}
				}
			});
	     }
                   
	};
	
	
	

	Model.prototype.button5Click = function(event){
      	var notifyUrl = location.origin + "/baas/weixin/weixin/notify";
	                alert(notifyUrl);

	};
	
	
	

	return Model;
});