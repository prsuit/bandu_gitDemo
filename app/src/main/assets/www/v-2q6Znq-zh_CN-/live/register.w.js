window.__justep.__ResourceEngine.loadCss([{url: '/system/components/bootstrap.min.css', include: '$model/system/components/bootstrap/lib/css/bootstrap,$model/system/components/bootstrap/lib/css/bootstrap-theme'},{url: '/system/components/comp.min.css', include: '$model/system/components/justep/lib/css2/dataControl,$model/system/components/justep/input/css/datePickerPC,$model/system/components/justep/messageDialog/css/messageDialog,$model/system/components/justep/lib/css3/round,$model/system/components/justep/input/css/datePicker,$model/system/components/justep/row/css/row,$model/system/components/justep/attachment/css/attachment,$model/system/components/justep/barcode/css/barcodeImage,$model/system/components/bootstrap/dropdown/css/dropdown,$model/system/components/justep/dataTables/css/dataTables,$model/system/components/justep/contents/css/contents,$model/system/components/justep/common/css/forms,$model/system/components/justep/locker/css/locker,$model/system/components/justep/menu/css/menu,$model/system/components/justep/scrollView/css/scrollView,$model/system/components/justep/loadingBar/loadingBar,$model/system/components/justep/dialog/css/dialog,$model/system/components/justep/bar/css/bar,$model/system/components/justep/popMenu/css/popMenu,$model/system/components/justep/lib/css/icons,$model/system/components/justep/lib/css4/e-commerce,$model/system/components/justep/toolBar/css/toolBar,$model/system/components/justep/popOver/css/popOver,$model/system/components/justep/panel/css/panel,$model/system/components/bootstrap/carousel/css/carousel,$model/system/components/justep/wing/css/wing,$model/system/components/bootstrap/scrollSpy/css/scrollSpy,$model/system/components/justep/titleBar/css/titleBar,$model/system/components/justep/lib/css1/linear,$model/system/components/justep/numberSelect/css/numberList,$model/system/components/justep/list/css/list,$model/system/components/justep/dataTables/css/dataTables'}]);window.__justep.__ResourceEngine.loadJs(['/system/components/comp2.min.js','/system/core.min.js','/system/common.min.js','/system/components/comp.min.js']);define(function(require){
require('$model/UI2/system/components/justep/model/model');
require('$model/UI2/system/components/justep/loadingBar/loadingBar');
require('$model/UI2/system/components/justep/button/button');
require('$model/UI2/system/components/justep/distpicker/distpicker2');
require('$model/UI2/system/components/justep/controlGroup/controlGroup');
require('$model/UI2/system/components/justep/input/input');
require('$model/UI2/system/components/justep/panel/child');
require('$model/UI2/system/components/justep/labelEdit/labelEdit');
require('$model/UI2/system/components/justep/panel/panel');
require('$model/UI2/system/components/justep/row/row');
require('$model/UI2/system/components/justep/titleBar/titleBar');
require('$model/UI2/system/components/justep/button/radio');
require('$model/UI2/system/components/justep/data/data');
require('$model/UI2/system/components/justep/window/window');
var __parent1=require('$model/UI2/system/lib/base/modelBase'); 
var __parent0=require('$model/UI2/live/register'); 
var __result = __parent1._extend(__parent0).extend({
	constructor:function(contextUrl){
	this.__sysParam='true';
	this.__contextUrl=contextUrl;
	this.__id='';
	this.__cid='cjI3mQv';
	this._flag_='740204055a14c4101ffd1b2cab8dd82d';
	this.callParent(contextUrl);
 var __Data__ = require("$UI/system/components/justep/data/data");new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"col0":{"define":"col0","name":"col0","relation":"col0","type":"String"},"col1":{"define":"col1","name":"col1","relation":"col1","type":"String"},"col2":{"define":"col2","name":"col2","relation":"col2","type":"String"}},"directDelete":false,"events":{},"idColumn":"col0","initData":"[{}]","limit":20,"xid":"data1"});
 new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"fName":{"define":"fName","label":"显示名称","name":"fName","relation":"fName","type":"String"},"fValue":{"define":"fValue","label":"值","name":"fValue","relation":"fValue","type":"String"}},"directDelete":false,"events":{},"idColumn":"fValue","initData":"[{\"fValue\":\"1\",\"fName\":\"学生\"},{\"fValue\":\"2\",\"fName\":\"教师\"}]","limit":20,"xid":"userTypeData"});
 new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"fName":{"define":"fName","label":"显示名称","name":"fName","relation":"fName","type":"String"},"fValue":{"define":"fValue","label":"值","name":"fValue","relation":"fValue","type":"String"}},"directDelete":false,"events":{},"idColumn":"fValue","initData":"[{\"fValue\":\"1\",\"fName\":\"男\"},{\"fValue\":\"2\",\"fName\":\"女\"}]","limit":20,"xid":"sexData"});
 new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"id":{"define":"id","label":"主键","name":"id","relation":"id","type":"String"},"school":{"define":"school","label":"学校名称","name":"school","relation":"school","type":"String"}},"directDelete":false,"events":{},"idColumn":"id","initData":"[{\"id\":\"1\",\"school\":\"艺术小学\"},{\"id\":\"2\",\"school\":\"东湖小学\"},{\"id\":\"3\",\"school\":\"当湖小学\"}]","limit":20,"xid":"schoolData"});
}}); 
return __result;});
