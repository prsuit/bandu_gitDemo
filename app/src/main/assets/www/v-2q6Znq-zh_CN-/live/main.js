define(function(require){
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");
	
	var Model = function(){
		this.callParent();
	};
	
	var trailerData;
	
	// 图片路径转换
	Model.prototype.getImageUrl = function(url) {
		return require.toUrl(url);
	};

	Model.prototype.button1Click = function(event){

       var url=require.toUrl('./wode.w');   
       justep.Shell.showPage(url);
	};

	Model.prototype.list3Click = function(event){
	     var me=this;
	    
         var url=require.toUrl("./goumai.w");
	      justep.Shell.showPage(url);      
	};

	Model.prototype.button3Click = function(event){
	   
	   var url=require.toUrl("./shopcar.w");
	   justep.Shell.showPage(url);

	};




	Model.prototype.modelLoad = function(event){
	 window.data = this.comp('cartData');
	 window.cdata=this.comp('calculateData'); 
	 
	  var local=window.localStorage;
	    var role=local.getItem("role");
	    var me=this;   
	    if(role==1){      
		 $(me.getElementByXid("titleBar1")).css("background-color","#00A8FF");
	    }else{
	     $(me.getElementByXid("titleBar1")).css("background-color","#9ed04d"); 
	    }
	 

	};
	
	//        显示倒计时
        Model.prototype.showTime = function(obj,time,object){
          

        var go = function(){     
            for (var i = 0; i < list.length; i++) {  
                   var a=list[i].ele;
                  //debugger;
                    $(list[i].ele).text(getTimerString(list[i].time ? list[i].time -= 1 : 0,object));     
                if (!list[i].time)  
                    list.splice(i--, 1);
            }     
        };     
   
    
        var getTimerString = function (time,object) {   
                           
                            var int_day, int_hour, int_minute, int_second;
                            time=time*1000;
                            if(time> 0){
                                // 天时分秒换算
                                int_day = Math.floor(time/86400000);
                                time-= int_day * 86400000;
                                int_hour = Math.floor(time/3600000)+'';
                                time-= int_hour * 3600000;
                                int_minute = Math.floor(time/60000)+'';
                                time-= int_minute * 60000;
                                int_second = Math.floor(time/1000)+'';
                         
                                // 时分秒为单数时、前面加零站位
                                if(int_hour < 10)
                                int_hour = "0" + int_hour;
                                if(int_minute < 10)
                                int_minute = "0" + int_minute;
                                if(int_second < 10)
                                int_second = "0" + int_second;
                                
                                var hour1= int_hour.substr(0,1);
                                var hour2= int_hour.substr(1,1);
                                var minute1 = int_minute.substr(0,1);
                                var minute2 = int_minute.substr(1,1);
                                var second1 = int_second.substr(0,1);
                                var second2 = int_second.substr(1,1);
                                // 显示时间
                                //debugger;
                                return int_day+'天'+hour1+hour2+'时'+minute1+minute2+'分'+second1+second2+'秒';
                            }else{
                                var row=object;
		                        trailerData.deleteData([ row ], {
			                         "async" : true,
			                         "onSuccess" : function() {
				                      //trailerData.saveData();
			                             }
		                              });
		                        
                                return '已结束';      
                            }                       
        }; 
        
        
                var list = [],     
            interval;
        if (!interval)     
            interval = setInterval(go, 1000);     
        list.push({ ele: obj, time: time });
             
        };
        

       
	Model.prototype.span33Click = function(event){
              var row=event.bindingContext.$object;
              var title=row.val('title');
              var fee=row.val('fee');
              
                var url=require.toUrl("./goumai.w");
	      justep.Shell.showPage(url);   
                      
              //debugger;
	};
        
        
        
	Model.prototype.li3Click = function(event){
	   var row=event.bindingContext.$object;
	      //debugger;
              var Live_name=row.val('Live_name');
              var File=row.val('File');
              var Teacher_jj=row.val('Teacher_jj');
              var Live_time=row.val('Live_time');
              var Money=row.val('Money');
              var Teacher_name=row.val('Teacher_name');
              var Id=row.val('Id');
              var Live_jj=row.val('Live_jj');
             // debugger;
              params={
                 Live_name:Live_name,
                 File:File,
                 Teacher_jj:Teacher_jj,
                 Live_time:Live_time,
                 Money:Money,
                 Teacher_name:Teacher_name,
                 Id:Id,
                 Live_jj:Live_jj
              }
               var url=require.toUrl("./goumai.w");
	      justep.Shell.showPage(url,params);    
                      


	};
        
        
        




	Model.prototype.trailerDataCustomRefresh = function(event){

            trailerData= event.source;
            var me=this;
             var temp=$(me.getElementByXid("div7"));
              try
		{
			justep.Baas.sendRequest({
				"url" : "/live_show/live_show",
				"action" : "trailer",
				"async" : false,
				"params" : {},
				"success" : function(data) {
				   //trailerData.setTotal(100);  
				   if(data.data.length>0){
					    temp.attr("class","hidden");
					    trailerData.loadData(data.data);
						}else{
						//debugger;
						temp.attr("class","x-full");
						}
						//debugger;
				},
			"error":function(data){
				justep.Util.hint("网络故障");
				}
			})
		}
		catch(e)
		{
		      
			justep.Util.hint("我是加载预告的,网络可能不给力，请稍后再试");
		}      



	};
        
       

	Model.prototype.liveDataCustomRefresh = function(event){
	
	            var init= event.source;
	            var me=this;
	            var temp=$(me.getElementByXid("div6"));
	            
	        var local=window.localStorage;
	        var userid=local.getItem("userid"); 
	     if(userid!=null&userid!=""){   
              try
		{
			justep.Baas.sendRequest({
				"url" : "/live_show/live_show",
				"action" : "live",
				"async" : false,
				"params" : {userid:userid},
				"success" : function(data) {
				   if(data.data.length>0){
				    temp.attr("class","hidden");
					init.loadData(data.data);
					 //debugger;
					}else{
					temp.attr("class","x-full");
					}
				},
			"error":function(data){
				justep.Util.hint("网络故障");
				}
			})
		}
		catch(e)
		{
			justep.Util.hint("我是加载直播的,网络可能不给力，请稍后再试");
		}  
		} else{ 
		
		     temp.attr("class","x-full");
		     //debugger;
		}   

	};
        
        
      
	
        
        

	Model.prototype.deleteBtnClick = function(event){
               var row = event.bindingContext.$object;             
	};
       

	Model.prototype.li1Click = function(event){
	     
	     var row = event.bindingContext.$object;
	     
	     var video=row.val("h5url");
	     var url=require.toUrl("./liveroom.w");
	     var params={
	       video:video
	     }
	     
            justep.Shell.showPage(url,params);

	};
        
        
      
	
        
        
        




	Model.prototype.modelActive = function(event){
	       
	  /*  var local=window.localStorage;
	    var role=local.getItem("role");
	    var me=this;
	    $(me.getElementByXid("titleBar1")).css("background-color","#9ed04d");   
	    if(role==1){      
		 $(me.getElementByXid("titleBar1")).css("background-color","#00A8FF");   	  
	    }else{
	     $(me.getElementByXid("titleBar1")).css("background-color","#9ed04d");   
	    }*/
	    
	     var local=window.localStorage;
	    var role=local.getItem("role");
	    var me=this;   
	    if(role==1){      
		 $(me.getElementByXid("titleBar1")).css("background-color","#00A8FF");
		 
		 //$(me.getElementByXid("div18")).css("background-color","#0085ca");
   	      //debugger;
   	      $(me.getElementByXid("div18")).addClass("daojishi1");
	    }else{
	     $(me.getElementByXid("titleBar1")).css("background-color","#9ed04d"); 
	     //$(me.getElementByXid("div18")).css("background-color","#2cc17b");
         $(me.getElementByXid("div18")).addClass("daojishi");
	    }
	   

	};
        
 

	Model.prototype.content3Active = function(event){
	
	        var init=this.comp("liveData");
	        var me=this;
	        var temp=$(me.getElementByXid("div6"));
	            
	        var local=window.localStorage;
	        var userid=local.getItem("userid"); 
	     if(userid!=null&userid!=""){   
              try
		{
			justep.Baas.sendRequest({
				"url" : "/live_show/live_show",
				"action" : "live",
				"async" : false,
				"params" : {userid:userid},
				"success" : function(data) {
				   if(data.data.length>0){
				    temp.attr("class","hidden");
					init.loadData(data.data);
					 //debugger;
					}else{
					temp.attr("class","x-full");
					}
				},
			"error":function(data){
				justep.Util.hint("网络故障");
				}
			})
		}
		catch(e)
		{
			justep.Util.hint("我是加载直播的,网络可能不给力，请稍后再试");
		}  
		} else{ 	
		     temp.attr("class","x-full");
		} 
	
	};
        
        
      
	
        
        
        




 
        
        
      
	
        
        
        




        
        
      
	
        
        
        




	return Model;
});