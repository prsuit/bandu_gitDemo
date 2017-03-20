define(function(require){
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");

	var Model = function(){
		this.callParent();
	};

	Model.prototype.PurchaseHistoryCustomRefresh = function(event){
	      var me=this;
	      //没数据时候的展示
	      var temp=$(me.getElementByXid("div6"));
	      var local=window.localStorage;
	      var userid=local.getItem("userid");
	      var PurchaseHistory=this.comp("PurchaseHistory");
	     if(userid!=null&userid!=""){
	       justep.Baas.sendRequest({
			"url" : "/live_show/live_show",
			"action" : "queryPaytradeNo",
			"async" : false,
			"params" : {
			         userid:userid
			         },
			"success" : function(data) {
			     if(data.data.length>0){
			         temp.attr("class","hidden");
			      PurchaseHistory.loadData(data.data);
			      }else{
			          temp.attr("class","x-full");
			      }
			}
		});
	
        }
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

	return Model;
});