define(function(require){
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");

	var Model = function(){
		this.callParent();
	};

	Model.prototype.modelParamsReceive = function(event){
	    
	     var url = event.params.video;
         this.getElementByXid("iframe1").src = url;
	

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

	Model.prototype.modelInactive = function(event){
	    this.getElementByXid("iframe1").src ="";

	};

	return Model;
});