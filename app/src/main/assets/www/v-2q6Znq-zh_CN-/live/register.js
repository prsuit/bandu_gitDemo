define(function(require){
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");

	var Model = function(){
		this.callParent();
	};
	
	var tel;
	
		Model.prototype.doOK = function(event) {
		var comp = event.source;
		var value = comp.getValue();
		this.comp('input1').val(value.province + ' ' + value.city + ' ' + value.district)
		
	};



    //地域选择
	Model.prototype.input1Click = function(event) {
		var ditpicker2 = this.comp('distPicker2');
		ditpicker2.show();
	};
	


	Model.prototype.modelLoad = function(event){
		if(justep.Browser.isPCFromUserAgent)
			this.comp('inputDistpicker').$domNode.hide();
			
		var local=window.localStorage;
		var role=local.getItem("role");
		
		
	    var me=this;
	    
		 if(role==1){      
		  me.comp("send").set({"label":"提交"});
		 $(me.getElementByXid("titleBar1")).css("background-color","#00A8FF");
		 $(me.getElementByXid("send")).addClass("loginstatus1");
	     // me.comp("submit")
		 //submit 
	    }else{
	      me.comp("send").set({"label":"提交"});
	     $(me.getElementByXid("titleBar1")).css("background-color","#9ed04d"); 
	     $(me.getElementByXid("send")).addClass("loginstatus");
	     //("background-color","#9ed04d");
	    }
	 	
			
			
	};
	

	Model.prototype.submitClick = function(event){
        
          tel=this.comp("input12").val();
         var a=this.comp("input12").val();
           
              var re = /^1\d{10}$/;
           if(!re.test(a)){
              justep.Util.hint("请输入标准11位手机号");
              return;
           }
           
           var d=this.comp("input14").val(); 
         if(!d.length|d.length<6|d.length>12){
            justep.Util.hint("请输入6~12位的密码");
              return;
         }
         
         var c=this.comp("input13").val(); 
         if(!c.length){
            justep.Util.hint("请输入您的姓名");
              return;
         }
                
         //document.getElementsByTagName("radio") 
         var h=$(":checked");
           if(!h.length){
            justep.Util.hint("请选择您的用户类型");
              return;
         } 
          var g=$(":checked").val();      
                
         var b=this.comp("input1").val();        
         if(!b.length){
            justep.Util.hint("请选择您所在的地域");
              return;
         }
         
           var e=this.comp("input18").val();        
         if(!e.length){
            justep.Util.hint("请填写您所在的学校");
              return;
         }
         
            var f=this.comp("input18").val();        
         if(!f.length){
            justep.Util.hint("请填写您所在学校的班级");
              return;
         }
          var me=this;
         
        justep.Baas.sendRequest({
			"url" : "/live_show/live_show",
			"action" : "register",
			"async" : false,
			"params" : {
			    telephone:a,
			    username:c,
			    password:d,
			    usertype:g,
			    region:b,
			    school:e,
			    classroom:f
			},
			"success" : function(data) {
			    var x=data.error;
			   if(x=="registered successful"){
			      var local=window.localStorage;
			      var userid=data.id;
			      local.setItem("userid",userid);
			      local.setItem("telephone",tel);
			       justep.Util.hint("恭喜你,注册成功");
			      me.owner.send({t:1}); 	      
			   }else if(x=="Already on the account"){
			      justep.Util.hint("该手机用户已经存在,不能重复注册");
			   }else{
			      justep.Util.hint("注册失败，请稍后重试");
			   }			
			},
			"error":function(){
			justep.Util.hint("网络故障，请稍后再试");
			}
		});
        
         
         
              
         
         
	}; 
	

	Model.prototype.backBtnClick = function(event){
            this.owner.send({t:0});  
	}; 
	

	return Model;
});