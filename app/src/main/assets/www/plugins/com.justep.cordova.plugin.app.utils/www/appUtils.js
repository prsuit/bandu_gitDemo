cordova.define("com.justep.cordova.plugin.app.utils.AppUtils", function(require, exports, module) { var exec = require('cordova/exec');
var appUtils = {
		options:{
			wifiDownloadOnly:true,
			quiet:false
		},
		isWifiConnection: function(){
		    var networkState = navigator.connection.type;
		    if (networkState == Connection.WIFI) {
		      return true;
		    }
		    return false;
		},
		getIndexPageUrl:function(success,fail){
			var x5Version = this.getAppVersion();
			try{
				var indexPageKey = "indexPage_" + x5Version;
				plugins.appPreferences.fetch(success,fail,indexPageKey);
			}catch(e5){}
		},
		setIndexPageUrl : function(indexPageUrl){
			var x5Version = this.getAppVersion();
			try{
				var indexPageKey = "indexPage_" + x5Version;
				if(plugins && plugins.appPreferences){
					plugins.appPreferences.store(function(){},function(){},indexPageKey,indexPageUrl);
				}
			}catch(e5){}
		},
		getLocalResourceMd5:function(success,fail){
			var x5Version = this.getAppVersion();
			try{
				var resourceMd5Key = "resourceMd5_" + x5Version;
				plugins.appPreferences.fetch(success,fail,resourceMd5Key);
			}catch(e5){}
		},
		setLocalResourceMd5 : function(md5){
			var x5Version = this.getAppVersion();
			try{
				var resourceMd5Key = "resourceMd5_" + x5Version;
				if(plugins && plugins.appPreferences){
					plugins.appPreferences.store(function(){},function(){},resourceMd5Key,md5);
				}
			}catch(e5){}
		},
		getAppVersion:function(){
			var x5Version = "noApp";
			var x5AppAgents = /x5app\/([0-9.]*)/.exec(navigator.userAgent);
			if(x5AppAgents && x5AppAgents.length > 1){
			   	x5Version = x5AppAgents[1] || "";
			}
			return x5Version; 
		},
		
		getResourceDirPath :function(){
			if(window.__justep && window.__justep.versionInfo){
				var baseUrl = window.__justep.versionInfo.baseUrl;
				var version = window.__justep.versionInfo.resourceInfo.version;
				return "www" + baseUrl.replace(location.protocol + "//" + location.host,"") +  version;
			}else{
				return "www";
			}
		},
		
		getAssetsPath:function(){
			var self = this;
			var dfd = $.Deferred();
			window.resolveLocalFileSystemURL(cordova.file.dataDirectory + self.getResourceDirPath(),function(dirEntry){
				var destDir = dirEntry.toURL();
				dfd.resolve(cordova.file.dataDirectory + "www/");
			},function(){
				window.resolveLocalFileSystemURL(cordova.file.applicationDirectory + self.getResourceDirPath(),function(dirEntry){
					var destDir = dirEntry.toURL();
					dfd.resolve(cordova.file.applicationDirectory + "www/");
				},function(){
					dfd.resolve("");
				});
			});
			return dfd.promise();
		},

		checkAsset:function(path){
			var dfd = $.Deferred();
			window.resolveLocalFileSystemURL(path,function(dirEntry){
				dfd.resolve(true);
			},function(){
				dfd.resolve(false);
			});
			return dfd.promise();
		},
		
		updateAppResource : function(resourceDownloadUrl){
			if(!$.Deferred){
				return;
			}
			var dfd = $.Deferred();
			var self = this;
			window.resolveLocalFileSystemURL(cordova.file.dataDirectory + self.getResourceDirPath(),function(dirEntry){
				var destDir = dirEntry.toURL();
				dfd.resolve(destDir);
			},function(){
				if(self.options.wifiDownloadOnly && !self.isWifiConnection()){
					if(!self.options.quiet){
						plugins.toast.showShortBottom("当前不是wifi环境已经阻止资源包自动更新!等待下次自动更新");
					}
					dfd.reject("only download in wifi");
				}else{
					window.resolveLocalFileSystemURL(cordova.file.dataDirectory,function(dirEntry){
						var ft = new FileTransfer();
						/**
						ft.onprogress = function(progressEvent) {
					      if (progressEvent.lengthComputable) {
					        var percentage = Math.floor(progressEvent.loaded / progressEvent.total * 100) + "%";
					        
					      }
					    };
					    **/
						ft.download(resourceDownloadUrl, dirEntry.toURL() + "www.zip", function(entry) {
							if(!self.options.quiet){
                        		plugins.toast.showShortBottom("开始下载离线资源包!");
                        	}
							var zipFileUrl = entry.toURL();
				        	var destDir = dirEntry.toURL();
				        	zip.unzip(zipFileUrl, destDir,function(code){
				        		if(code === 0){
				        			var path = cordova.file.dataDirectory + self.getResourceDirPath();
				        			window.resolveLocalFileSystemURL(cordova.file.dataDirectory + self.getResourceDirPath(),function(dirEntry){
				        				if(!self.options.quiet){
											plugins.toast.showLongBottom("离线资源已经下载并安装成功!");
										}
				        				dfd.resolve(destDir);
				        			},function(){
				        				if(!self.options.quiet){
											plugins.toast.showShortBottom("资源包校验失败!未找到" + path + "对应目录");
										}
				        				dfd.reject();
				        			});
				        		}else{
				        			dfd.reject(code);
				        		}
				        	});
				        }, function(err){
				        	dfd.reject(err.code);
				        });
					},function(error){
						dfd.reject(error.code);
					});
				}
			});
			return dfd.promise();
		},
		
		updateAppMd5Resource : function(resourceDownloadUrl){
			if(!$.Deferred){
				return;
			}
			var dfd = $.Deferred();
			var self = this;
			if(self.options.wifiDownloadOnly && !self.isWifiConnection()){
				if(!self.options.quiet){
					plugins.toast.showShortBottom("当前不是wifi环境已经阻止资源包自动更新!等待下次自动更新");
				}
				dfd.reject("only download in wifi");
			}else{
				window.resolveLocalFileSystemURL(cordova.file.dataDirectory,function(dirEntry){
					var ft = new FileTransfer();
					ft.download(resourceDownloadUrl, dirEntry.toURL() + "www.zip", function(entry) {
						if(!self.options.quiet){
							plugins.toast.showShortBottom("开始下载离线资源包!");
						}
						var zipFileUrl = entry.toURL();
						var destDir = dirEntry.toURL();
						zip.unzip(zipFileUrl, destDir,function(code){
							if(code === 0){
								var path = cordova.file.dataDirectory + self.getResourceDirPath();
								if(!self.options.quiet){
									plugins.toast.showLongBottom("离线资源已经下载并安装成功!");
								}
								dfd.resolve(destDir);
							}else{
								dfd.reject(code);
							}
						});
					}, function(err){
						if(!self.options.quiet){
							plugins.toast.showShortBottom("资源包安装失败!");
						}
						dfd.reject(err.code);
					});
				},function(error){
					if(!self.options.quiet){
						plugins.toast.showShortBottom("资源包下载失败!url:" + resourceDownloadUrl);
					}
					dfd.reject(error.code);
				});
			}
			return dfd.promise();
		}
};
module.exports =  appUtils;

});
