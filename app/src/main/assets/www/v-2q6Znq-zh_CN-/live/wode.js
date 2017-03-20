define(function(require){
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");

	var Model = function(){
		this.callParent();
	};

	Model.prototype.li1Click = function(event){
            var url=require.toUrl('./wodekecheng.w');
            
            justep.Shell.showPage(url);
	};
	
	

	Model.prototype.li2Click = function(event){
           var url=require.toUrl('./xiaofeijilu.w'); 
            justep.Shell.showPage(url);
	};
	
	

	Model.prototype.loginBtnClick = function(event){
	     var me=this;
	    
	        var local= window.localStorage;
	       var userid=local.getItem("userid");
	       var role=local.getItem("role")
	      //debugger;
	       me.comp("windowDialog1").open();   
	      
	      /* if(role==1){
	           if(userid.length>0){
	   	       	this.comp('loginBtn').set({
	   			"label" : "登录"	
	   		    });
	   		    $(me.getElementByXid("loginBtn")).removeClass("cancelstatus1");
	   		   $(me.getElementByXid("loginBtn")).addClass("loginstatus1");
	   		   $(me.getElementByXid("enroll")).addClass("registerstatus");
	   		   $(me.getElementByXid("enroll")).show();
	   		    local.setItem("userid","");
	   		    local.setItem("telephone","");
	   		      justep.Util.hint("注销成功");  
	   	       }else{
	   	       me.comp("windowDialog1").open();   
	   	       }       
	       }else{ 
	       
	                
	   	    if(userid.length>0){
	       	this.comp('loginBtn').set({
			"label" : "登录"	
		    });
		    $(me.getElementByXid("loginBtn")).removeClass("cancelstatus");
		   $(me.getElementByXid("loginBtn")).addClass("loginstatus");
		   $(me.getElementByXid("enroll")).addClass("registerstatus");
	   	   $(me.getElementByXid("enroll")).show();
		    local.setItem("userid","");
		    local.setItem("telephone","");
		    $(me.getElementByXid("enroll")).show();  
		      justep.Util.hint("注销成功");  
		      		      
	       }else{
	       me.comp("windowDialog1").open();   
	       }  	    	   
	       }*/
	     
	
	         
	   

	};
	
	Model.prototype.button1Click = function(event){
	
	  var me=this;

	};
	
	

	Model.prototype.modelLoad = function(event){
	    
	    var local= window.localStorage;
	    var userid=local.getItem("userid");
	    var me=this;
	    var role=local.getItem("role");
	    var tel=window.localStorage.getItem("telephone");
	    if(role==1){      
	    	$(me.getElementByXid("titleBar1")).css("background-color","#00A8FF");  
	    	if(userid!=null&userid!=""){
	    		this.comp('enroll').set({
	    			"label" : "注销"	
	    		});
	    		$(me.getElementByXid("loginBtn")).hide();
	    		
	    		$(me.getElementByXid("h31")).text("直播ID:"+tel);
               	$(me.getElementByXid("h31")).attr("color","#00A8FF");
               	$(me.getElementByXid("h31")).attr("class","");  		
	    	}else{
	    	    $(me.getElementByXid("h31")).hide();
	    		this.comp('enroll').set({
	    			"label" : "注册"	
	    		});
	    		this.comp('loginBtn').set({
	    			"label" : "登录"	
	    		});
	    		//$(me.getElementByXid("loginBtn")).addClass("loginstatus1");
	    		$(me.getElementByXid("loginBtn")).css("background-color","#00A8FF");
	    	}
	    	// debugger;	  
	    }else{
	    	$(me.getElementByXid("titleBar1")).css("background-color","#9ed04d"); 
	    	
	    	if(userid!=null&userid!=""){
	    		this.comp('enroll').set({
	    			"label" : "注销"	
	    		});
	    		$(me.getElementByXid("loginBtn")).hide();
	    		$(me.getElementByXid("h31")).text("直播ID:"+tel);
               	$(me.getElementByXid("h31")).attr("color","#9ed04d");
                $(me.getElementByXid("h31")).attr("class","");
	    	}else{
	    	    $(me.getElementByXid("h31")).hide();
	    		this.comp('enroll').set({
	    			"label" : "注册"	
	    		});
	    		this.comp('loginBtn').set({
	    			"label" : "登录"	
	    		});
	    		$(me.getElementByXid("loginBtn")).css("background-color","#9ed04d");
	    		//$(me.getElementByXid("loginBtn")).addClass("loginstatus");
	    		
	    	}  
	    }
	    
	   
		

	};
	
	
    //登陆的对话框
	Model.prototype.windowDialog1Receive = function(event){
	     
	     var me=this;
	      var t=event.data.t;
          //debugger;
           if(t==1){
          
               this.comp("windowDialog1").close();
              	this.comp('enroll').set({
	    			"label" : "注销"	
	    		});
              
		$(me.getElementByXid("loginBtn")).hide();
		 var role=window.localStorage.getItem("role"); 
		 var tel=window.localStorage.getItem("telephone");
		if(role==1){
		  $(me.getElementByXid("h31")).attr("class","");
		    $(me.getElementByXid("h31")).text("直播ID:"+tel);
           	$(me.getElementByXid("h31")).attr("color","#00A8FF"); 
		   	
		}else{
			$(me.getElementByXid("h31")).attr("class","");
		   $(me.getElementByXid("h31")).text("直播ID:"+tel);
           $(me.getElementByXid("h31")).attr("color","#9ed04d"); 
		   
		}
              
         }else{
           this.comp("windowDialog1").close();
         
         }
	         

	};
	

	Model.prototype.enrollClick = function(event){
	
	   var local=window.localStorage;
	   var userid=local.getItem("userid");
	   var telephone=local.getItem("telephone");
	   var role=local.getItem("role");
	   var me=this;
	   var a=me.comp("enroll").get("label");
	   
	   if(a=="注销"){
		  
		   justep.Util.confirm("确认要注销吗？",function(){
		   local.setItem("userid","");
		   local.setItem("telephone","");
		   if(role==1){
			   me.comp("enroll").set({"label":"注册"});
			   me.comp("loginBtn").set({"label":"登录"});
			   $(me.getElementByXid("loginBtn")).css("background-color","#00A8FF");
			   $(me.getElementByXid("loginBtn")).show();
			 $(me.getElementByXid("h31")).attr("class","hidden");
		   }else{
			   me.comp("enroll").set({"label":"注册"});
			   me.comp("loginBtn").set({"label":"登录"});
			   $(me.getElementByXid("loginBtn")).css("background-color","#9ed04d");
			   $(me.getElementByXid("loginBtn")).show();
			   $(me.getElementByXid("h31")).attr("class","hidden");
		   }
		   justep.Util.hint("注销成功");}, function(){
		      return;
		   });
		   
		   //this.comp("messageDialog1").show();
		   
	   }else{
		   this.comp("windowDialog2").open();
	   }
	};
	
	

	
	

	Model.prototype.modelActive = function(event){
	var me=this;
	};
	
	

	
	
    //注册的对话框
	Model.prototype.windowDialog2Receive = function(event){
	     var me=this;
	      var t=event.data.t;
          //debugger;
           if(t==1){
          
               this.comp("windowDialog2").close();
              	this.comp('enroll').set({
	    			"label" : "注销"	
	    		});            
		$(me.getElementByXid("loginBtn")).hide();
		 var role=window.localStorage.getItem("role"); 
		 var tel=window.localStorage.getItem("telephone");
		if(role==1){
			$(me.getElementByXid("h31")).attr("class","");
		    $(me.getElementByXid("h31")).text("直播ID:"+tel);
           	$(me.getElementByXid("h31")).attr("color","#00A8FF"); 
           		 
		}else{
		   $(me.getElementByXid("h31")).attr("class","");
		   $(me.getElementByXid("h31")).text("直播ID:"+tel);
           $(me.getElementByXid("h31")).attr("color","#9ed04d"); 
		  	
		}
              
         }else{
           this.comp("windowDialog2").close();
         
         }
	     

	};
	
	

	
	

	Model.prototype.messageDialog1OK = function(event){
	   var local=window.localStorage;
	   var userid=local.getItem("userid");
	   var telephone=local.getItem("telephone");
	   var role=local.getItem("role");
	   var me=this;
	   
	   local.setItem("userid","");
	   local.setItem("telephone","");
	   if(role==1){
		   me.comp("enroll").set({"label":"注册"});
		   me.comp("loginBtn").set({"label":"登录"});
		   $(me.getElementByXid("loginBtn")).css("background-color","#00A8FF");
		   $(me.getElementByXid("loginBtn")).show();
		   $(me.getElementByXid("h31")).attr("class","hidden");
	   }else{
		   me.comp("enroll").set({"label":"注册"});
		   me.comp("loginBtn").set({"label":"登录"});
		   $(me.getElementByXid("loginBtn")).css("background-color","#9ed04d");
		   $(me.getElementByXid("loginBtn")).show();
		   $(me.getElementByXid("h31")).attr("class","hidden");
	   }
	   justep.Util.hint("注销成功");
        //me.comp("messageDialog2").show();     
	};
	
	

	
	

	return Model;
});