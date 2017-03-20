define(function(require){
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");

	var Model = function(){
		this.callParent();
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

	Model.prototype.overDataCustomRefresh = function(event){
	         var me=this;
	     var overData=this.comp("overData");
	       var local=window.localStorage;
	  
	      var temp1=$(me.getElementByXid("div8"));
	         //debugger;
	        var userid=local.getItem("userid");
	        if(userid!=null&userid!=""){
	        justep.Baas.sendRequest({
				"url" : "/live_show/live_show",
				"action" : "purchaseHistory",
				"async" : false,
				"params" : {
				  userid:userid,
				  type:1			
				},
				"success" : function(data) {
				    if(data.data.length>0){	
				        temp1.attr("class","hidden");    
					   overData.loadData(data.data);					  
					}else{
					  
					    temp1.attr("class","x-full");   
					}
				}
			});  
	        }
	};

	Model.prototype.content3Active = function(event){
	         var me=this;
	 
	  

	};

	Model.prototype.modelLoad = function(event){
	
	   var local=window.localStorage;
	    var role=local.getItem("role");
	    var me=this;   
	    if(role==1){      
		 $(me.getElementByXid("titleBar1")).css("background-color","#00A8FF");    	  
	    }else{
	     $(me.getElementByXid("titleBar1")).css("background-color","#9ed04d");  
	    }
	    
	};

	Model.prototype.content2Active = function(event){
	   	      var me=this;
	};

	Model.prototype.data1CustomRefresh = function(event){
	
	      var me=this;
	     var data1=this.comp("data1");
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
				var z=data.data;
				//alert(z[0].fee);
				data1.loadData(data.data);
				  /*  if(data.data.length>0){	
				        temp.attr("class","hidden");    
					   data1.loadData(data.data);
					   debugger;					  
					}else{
					    temp.attr("class","x-full");  
					   // debugger; 
					}*/
				}
			});
	     }

	};

	return Model;
});