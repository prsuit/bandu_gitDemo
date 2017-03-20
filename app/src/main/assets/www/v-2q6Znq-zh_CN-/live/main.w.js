window.__justep.__ResourceEngine.loadCss([{url: '/system/components/bootstrap.min.css', include: '$model/system/components/bootstrap/lib/css/bootstrap,$model/system/components/bootstrap/lib/css/bootstrap-theme'},{url: '/system/components/comp.min.css', include: '$model/system/components/justep/lib/css2/dataControl,$model/system/components/justep/input/css/datePickerPC,$model/system/components/justep/messageDialog/css/messageDialog,$model/system/components/justep/lib/css3/round,$model/system/components/justep/input/css/datePicker,$model/system/components/justep/row/css/row,$model/system/components/justep/attachment/css/attachment,$model/system/components/justep/barcode/css/barcodeImage,$model/system/components/bootstrap/dropdown/css/dropdown,$model/system/components/justep/dataTables/css/dataTables,$model/system/components/justep/contents/css/contents,$model/system/components/justep/common/css/forms,$model/system/components/justep/locker/css/locker,$model/system/components/justep/menu/css/menu,$model/system/components/justep/scrollView/css/scrollView,$model/system/components/justep/loadingBar/loadingBar,$model/system/components/justep/dialog/css/dialog,$model/system/components/justep/bar/css/bar,$model/system/components/justep/popMenu/css/popMenu,$model/system/components/justep/lib/css/icons,$model/system/components/justep/lib/css4/e-commerce,$model/system/components/justep/toolBar/css/toolBar,$model/system/components/justep/popOver/css/popOver,$model/system/components/justep/panel/css/panel,$model/system/components/bootstrap/carousel/css/carousel,$model/system/components/justep/wing/css/wing,$model/system/components/bootstrap/scrollSpy/css/scrollSpy,$model/system/components/justep/titleBar/css/titleBar,$model/system/components/justep/lib/css1/linear,$model/system/components/justep/numberSelect/css/numberList,$model/system/components/justep/list/css/list,$model/system/components/justep/dataTables/css/dataTables'}]);window.__justep.__ResourceEngine.loadJs(['/system/core.min.js','/system/common.min.js','/system/components/comp.min.js']);define(function(require){
require('$model/UI2/system/components/justep/model/model');
require('$model/UI2/system/components/justep/loadingBar/loadingBar');
require('$model/UI2/system/components/justep/button/button');
require('$model/UI2/system/components/justep/scrollView/scrollView');
require('$model/UI2/system/components/justep/list/list');
require('$model/UI2/system/components/justep/panel/child');
require('$model/UI2/system/components/justep/panel/panel');
require('$model/UI2/system/components/justep/contents/content');
require('$model/UI2/system/components/justep/row/row');
require('$model/UI2/system/components/justep/titleBar/titleBar');
require('$model/UI2/system/components/justep/contents/contents');
require('$model/UI2/system/components/justep/data/data');
require('$model/UI2/system/components/justep/window/window');
require('$model/UI2/system/components/justep/button/buttonGroup');
var __parent1=require('$model/UI2/system/lib/base/modelBase'); 
var __parent0=require('$model/UI2/live/main'); 
var __result = __parent1._extend(__parent0).extend({
	constructor:function(contextUrl){
	this.__sysParam='true';
	this.__contextUrl=contextUrl;
	this.__id='';
	this.__cid='cviIRBv';
	this._flag_='17bebf768551de383a331b9fffab74e0';
	this.callParent(contextUrl);
 var __Data__ = require("$UI/system/components/justep/data/data");new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"File":{"define":"File","label":"图片","name":"File","relation":"File","type":"String"},"Id":{"define":"Id","label":"主键","name":"Id","relation":"Id","rules":{"integer":true},"type":"Integer"},"Live_name":{"define":"Live_name","label":"课程标题","name":"Live_name","relation":"Live_name","type":"String"},"Money":{"define":"Money","label":"原始价格","name":"Money","relation":"Money","type":"String"},"h5url":{"define":"h5url","label":"直播链接","name":"h5url","relation":"h5url","type":"String"},"live_time":{"define":"live_time","label":"直播时间","name":"live_time","relation":"live_time","type":"String"}},"directDelete":false,"events":{"onCustomRefresh":"liveDataCustomRefresh"},"idColumn":"Id","initData":"[{\"id\":1,\"image\":\"./img/banner01.jpg\",\"title\":\"雷克顿\",\"price1\":99,\"price2\":90,\"current\":3123},{\"id\":2,\"image\":\"./img/banner02.jpg\",\"title\":\"波比\",\"price1\":99,\"price2\":80,\"current\":2423},{\"id\":3,\"image\":\"./img/banner01.jpg\",\"title\":\"安妮\",\"price1\":99,\"price2\":70,\"current\":533},{\"id\":4,\"image\":\"./img/banner02.jpg\",\"title\":\"阿木木\",\"price1\":99,\"price2\":60,\"current\":123},{\"id\":5,\"image\":\"./img/banner01.jpg\",\"title\":\"菲兹\",\"price1\":99,\"price2\":50,\"current\":12312},{\"id\":6,\"image\":\"./img/banner02.jpg\",\"title\":\"提莫\",\"price1\":99,\"price2\":40,\"current\":6456}]","limit":-1,"xid":"liveData"});
 new __Data__(this,{"autoLoad":true,"confirmDelete":false,"confirmRefresh":true,"defCols":{"File":{"define":"File","label":"图片","name":"File","relation":"File","type":"String"},"Id":{"define":"Id","label":"主键","name":"Id","relation":"Id","rules":{"integer":true},"type":"Integer"},"Live_jj":{"define":"Live_jj","label":"直播课程介绍","name":"Live_jj","relation":"Live_jj","type":"String"},"Live_name":{"define":"Live_name","label":"直播标题","name":"Live_name","relation":"Live_name","type":"String"},"Live_time":{"define":"Live_time","label":"直播开播时间","name":"Live_time","relation":"Live_time","type":"String"},"Live_timelength":{"define":"Live_timelength","label":"剩余","name":"Live_timelength","relation":"Live_timelength","rules":{"integer":true},"type":"Integer"},"Money":{"define":"Money","label":"直播费用","name":"Money","relation":"Money","type":"String"},"Teacher_jj":{"define":"Teacher_jj","label":"直播老师介绍","name":"Teacher_jj","relation":"Teacher_jj","type":"String"},"Teacher_name":{"define":"Teacher_name","label":"老师姓名","name":"Teacher_name","relation":"Teacher_name","type":"String"}},"directDelete":false,"events":{"onCustomRefresh":"trailerDataCustomRefresh"},"idColumn":"Id","initData":"[{\"Id\":1,\"File\":\"./img/banner01.jpg\",\"Live_timelength\":20000000,\"Live_time\":\"2017/04/01 17:00\",\"Live_name\":\"你好 \",\"Money\":\"9.9\",\"Live_jj\":\"你好\",\"Teacher_name\":\"王凯杰\",\"Teacher_jj\":\"误杀\"},{},{},{}]","limit":5,"xid":"trailerData"});
 new __Data__(this,{"autoLoad":true,"confirmDelete":false,"confirmRefresh":false,"defCols":{"File":{"define":"File","label":"预览图","name":"File","relation":"File","rules":{"number":true},"type":"String"},"Id":{"define":"Id","label":"课程ID","name":"Id","relation":"Id","rules":{"integer":true},"type":"Integer"},"Live_name":{"define":"Live_name","label":"直播名称","name":"Live_name","relation":"Live_name","type":"String"},"Live_time":{"define":"Live_time","label":"直播时间","name":"Live_time","relation":"Live_time","type":"String"},"Money":{"define":"Money","label":"课程费用","name":"Money","relation":"Money","rules":{"number":true},"type":"Float"},"fChoose":{"define":"fChoose","label":"选择","name":"fChoose","relation":"fChoose","rules":{"integer":true},"type":"Integer"},"fNumber":{"define":"fNumber","label":"数量","name":"fNumber","relation":"fNumber","rules":{"integer":true},"type":"Integer"},"fSum":{"define":"fSum","label":"总计","name":"fSum","relation":"fSum","rules":{"calculate":"$row.val('fChoose')==1? ( $model.calculateData.val(\"allNumber\")>9?($row.val('Money')-0.9):$row.val('Money')):'0'","integer":true},"type":"Integer"},"userid":{"define":"userid","label":"用户ID","name":"userid","relation":"userid","rules":{"integer":true},"type":"Integer"}},"directDelete":false,"events":{},"idColumn":"Id","initData":"[]","limit":20,"xid":"cartData"});
 new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"allNumber":{"define":"allNumber","label":"总数量","name":"allNumber","relation":"allNumber","rules":{"calculate":"$model.cartData.sum('fChoose')"},"type":"String"},"allSum":{"define":"allSum","label":"合计","name":"allSum","relation":"allSum","rules":{"calculate":"$model.cartData.sum('fSum')"},"type":"String"},"isBack":{"define":"isBack","label":"是否返回","name":"isBack","relation":"isBack","rules":{"integer":true},"type":"Integer"}},"directDelete":false,"events":{},"idColumn":"allSum","initData":"[{\"allSum\":\"0\",\"isBack\":0}]","limit":20,"xid":"calculateData"});
}}); 
return __result;});
