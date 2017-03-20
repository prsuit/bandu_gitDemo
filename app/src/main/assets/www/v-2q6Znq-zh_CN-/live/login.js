define(function(require){
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");
	
	var telephone;
	
	
	var Model = function(){
		this.callParent();
	};
    
    
	
	
	//图片路径转换
	Model.prototype.toUrl = function(url){
		return url ? require.toUrl(url) : "";
	};

	Model.prototype.loginBtnClick = function(event){
	   
            var me=this;
            var name=me.comp("nameInput").val();
             var re = /^1\d{10}$/;
           if(!re.test(name)){
              justep.Util.hint("请输入标准的11位手机号码！");
              return;
           } 
            
            var password=me.comp("passwordInput").val();
      
            if(name!=""&password!=""){
            	 justep.Baas.sendRequest({
     				"url" : "/live_show/live_show",
     				"action" : "login",
     				"async" : false,
     				"params" : {
     				      "username":name,
     				      "password":password
     				},
     				"success" : function(data) {
     				var temp=data.error;
     				var userid=data.data;
     				     if(temp=="login ok"){
     				      var local=window.localStorage;
     				             local.setItem("userid", userid);
     				             local.setItem("telephone", name);
     				             justep.Util.hint("登陆成功");
     				             me.comp("nameInput").val("");
     				             me.comp("passwordInput").val("");
     				             me.owner.send({t:1});             
     				  
     				     }else{
     			        $(me.getElementByXid("a")).text("用户名或者密码输入错误");  
     				         me.comp("nameInput").val("");
     				        me.comp("passwordInput").val("");
     				     }
     				}
     			});	
            } else{
              $(me.getElementByXid("a")).text("用户名或密码不能为空");  
            }       
           
	};

	Model.prototype.modelActive = function(event){
           var me=this;
             
             
	};

	Model.prototype.backBtnClick11 = function(event){
	
	       {operation:'window.close'}
	       
          
	};

	Model.prototype.modelLoad = function(event){
	
	   
           
	
	    var local=window.localStorage;
	    var role=local.getItem("role");
	    var me=this;   
	    
	    var userid=local.getItem("userid");   
        var comp=me.comp("message");
        Timmer.apply(comp, [ 60, "免费获取验证码", "重新发送" ]); 
	
	     me.comp("contents1").to("content2");
	   
	    if(role==1){      
		 $(me.getElementByXid("titleBar1")).css("background-color","#00A8FF"); 
		 $(me.getElementByXid("loginBtn")).css("background-color","#00A8FF"); 
		 $(me.getElementByXid("message")).css("background-color","#00A8FF"); 
		 $(me.getElementByXid("reset")).css("background-color","#00A8FF");  
		  $(me.getElementByXid("enroll")).css("background-color","#00A8FF"); 	  
	    }else{
	     $(me.getElementByXid("titleBar1")).css("background-color","#9ed04d");
	     $(me.getElementByXid("loginBtn")).css("background-color","#9ed04d"); 
	     $(me.getElementByXid("message")).css("background-color","#9ed04d");  
	     $(me.getElementByXid("reset")).css("background-color","#9ed04d");
	       $(me.getElementByXid("enroll")).css("background-color","#9ed04d");
	    }
	
	};
	
		function Timmer(loopSecond, titile1, title2, lisentner) { // todo
		// 还要检查一个结果返回变量。
		this.loopSecond = loopSecond;
		this.waittime = loopSecond;
		var self = this;
		this.mytimer = function() {
		 
		    if(self.waittime==55){
		    	 justep.Baas.sendRequest({
		    		 "url" : "/live_show/live_show",
		    		 "action" : "receiveMessage",
		    		 "async" : false,
		    		 "params" : {
		    			 telephone:telephone
		    		 },
		    		 "error" : function(data) {
		    			 justep.Util.hint("网络错误");
		    		 }
		    	 });   
		     }
		     
			if (self.waittime <= 0) {
				self.set({
					"disabled" : false,
					"label" : titile1
				});
				this.waittime =loopSecond; // 可设 60秒
			} else {
				self.set({
					"disabled" : true,
					"label" : title2 + "(" + self.waittime + ")"
				});
				self.waittime--;
				setTimeout(function() {
					self.mytimer();
				}, 1000);
			};
		
			
		}

	};
	
	

	Model.prototype.forgetBtnClick = function(event){
	       
	      this.comp("contents1").to("content4");

	};

	Model.prototype.messageClick = function(event){
	     var me=this;
            var name=me.comp("input1").val();
            
               var re = /^1\d{10}$/;
           if(!re.test(name)){
              justep.Util.hint("请输入标准11位手机号");
              return;
           }
           telephone=name;
         
           
           this.comp("message").mytimer();

	};

	Model.prototype.resetClick = function(event){
	         var me=this;
          var password1=me.comp("password1").val();
          var password2=me.comp("password2").val();
          var message=me.comp("input2").val();
          var o=me.comp("contents1");
           var size1=password1.length;
           var size2=password2.length;
           
           if(size1==size2){
               if(size1>5&size1<13){
                    justep.Baas.sendRequest({
						"url" : "/live_show/live_show",
						"action" : "sendMessage",
						"async" : false,
						"params" : {
						    telephone:telephone, 
						    password:password1,	
						    message:message  
						},
						"success" : function(data) {
							if(data.error=="success!"){
							   justep.Util.hint("密码重置成功，请重新登录!");
							   o.to("conent2");
							}else{
							   justep.Util.hint("验证码错误!");
							}					
						},
						"error": function(data) {
						    justep.Util.hint("网络故障");				
						}
					});
                                 
               }else{
                 justep.Util.hint("密码长度过长或过短，请重新输入");
               }
           }else{
               justep.Util.hint("两次密码输入不一致");
           }  
     
	};

	Model.prototype.backBtnClick = function(event){
	 this.comp("contents1").to("content2");
	  this.owner.send({t:0});  

	};

	Model.prototype.enrollClick = function(event){
        var url="./register.w";      
               
       justep.Shell.showPage(require.toUrl(url));      
	};

	return Model;
});