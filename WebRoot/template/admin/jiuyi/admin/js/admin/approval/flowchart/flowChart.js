jsPlumb.Defaults = {
    Anchor : "BottomCenter",
    Anchors : [ null, null ],
    ConnectionsDetachable   : true,
    ConnectionOverlays  : [],
    Connector : "Bezier",
    Container : null,
    DoNotThrowErrors  : false,
    DragOptions : { },
    DropOptions : { },
    Endpoint : "Dot",
    Endpoints : [ null, null ],
    EndpointOverlays : [ ],
    EndpointStyle : { fillStyle : "#456" },
    EndpointStyles : [ null, null ],
    EndpointHoverStyle : null,
    EndpointHoverStyles : [ null, null ],
    HoverPaintStyle : null,
    LabelStyle : { color : "black" },
    LogEnabled : false,
    Overlays : [ ],
    MaxConnections : 1,
    PaintStyle : { lineWidth : 8, strokeStyle : "#456" },
    ReattachConnections : false,
    RenderMode : "svg",
    Scope : "jsPlumb_DefaultScope"
}

$.widget('rk.flowChart', {
	canvas: null,
	linkMap: {},
	linkReverseMap: {},
  conditionFieldId: '#itemList',
	_create: function () {
		var me = this;
		var elem = me.element;
		var opt = me.options;

    if($(me.conditionFieldId).size() == 0){
      $.getJSON( "/admin/js/admin/approval/flowchart/data/conditions.json", function( json ) {
          init(json);
      });
    }else{
          var conditionsJson = me._getConditionData();
          init(conditionsJson);
    }
  
    function init(json){
        me.canvas = elem.find('.canvas');
        me.canvas.flowChartCanvas({
          data: opt.data,
          canvasId: 'flowchart',
          readonly: opt.readonly,
          postponed: opt.postponed//延期申请
        });
        me.canvas.flowChartAutoLayout({
          canvasId: 'flowchart'
        });
        me.canvas.flowChartHistory({
          canvasId: 'flowchart'
        });

        me.canvas.flowChartCanvas('setConditionItems', json);
        if(opt.data) me.canvas.flowChartCanvas('loadData', opt.data);
    }


		elem.find('[act="new"]').click(function(){ me._testNew(); });
		elem.find('[act="load"]').click(function(){ me._testLoad(); });
		elem.find('[act="autolayout"]').click(function(){ me._testAutoLayout(); });
		elem.find('[act="save"]').click(function(){ me._testSave(); });
		elem.find('[act="loadsave"]').click(function(){ me._testSaveLoad(); });
		elem.find('[act="undo"]').click(function(){ me._testUndo(); });
    elem.find('[act="reset"]').click(function(){ me.canvas.flowChartCanvas('createNew'); });
    elem.find('[act="helptip"]').qtip({
        content: {
            text: template('flowchartHelpTipTpl')
        },
        style: {classes: 'flow_help_tip'},
        events: {
            render: function(event, api) {
                // Grab the tip element
                var elem = api.elements.tip;
            }
        },
        position: {
            my: 'left center'
        }
        // ,
        // hide: {
        //     fixed: true,
        //     delay: 1111111500
        // }
    })
	},
  _getConditionData: function(){
    var me = this;
    var str = $(me.conditionFieldId).data('approval_conditions');
    return str ? JSON.parse(str) : {};
  },
	_testNew: function(){
		var me = this;
		me.canvas.flowChartCanvas('createNew');
	},
	_testLoad: function(){
		var me = this;
    var elem = me.element;
    var ipt = elem.find('[act="flowchartid"]');
		$.getJSON( "/admin/js/admin/approval/flowchart/data/"+ipt.val()+".json", function( json ) {
		    me.canvas.flowChartCanvas('loadData', json)
		});
	},
	_testAutoLayout: function(){
		var me = this;
    var elem = me.element;
    var ipt = elem.find('[act="flowchartid"]');

    me.canvas.flowChartAutoLayout('doAutolayout');
    return;

		$.getJSON( "/admin/js/admin/approval/flowchart/data/"+ipt.val()+".json", function( json ) {
		    me.canvas.flowChartAutoLayout('loadData', json)
		});
	},
	_testSave: function(){
		var me = this;
		var jsonstr = me.canvas.flowChartCanvas('getJsonString');
		console.log(jsonstr)
	},
	_testSaveLoad: function(){
		var me = this;
		var jsonstr = me.canvas.flowChartCanvas('getJsonString');
		console.log(jsonstr)
		var json = JSON.parse(jsonstr);
		json.tree = SERVERDATA.tree;
		json.users = SERVERDATA.users;
		me.canvas.flowChartCanvas('loadData', json);
	},
	_testUndo: function(){
		var me = this;
		me.canvas.flowChartHistory('undo');
	},
  _replaceAll: function (value, AFindText,ARepText){
    value = value + '';
    var raRegExp = new RegExp(AFindText.replace(/([\(\)\[\]\{\}\^\$\+\-\*\?\.\"\'\|\/\\])/g,"\\$1"),"ig");
    return value.replace(raRegExp,ARepText);
  },
  highlight: function(idlist){
    var me = this;
    $(idlist).each(function(i, id){
          me.getNodeElem(id).css('border', '10px solid red');
        });
  },
  _deepClone: function(obj){
     return JSON.parse(JSON.stringify(obj));
  },
	_parseJson: function(json){
		//转换为tree结构
		var me = this;

		me.currentData = {
			nodes: {},
			transitions: json.transitions
		};
		me.linkMap = {};
		me.linkReverseMap = {};

		var activities = json.activities;
		var transitions = json.transitions;

		//剔除前置网关
		var frontGateMap = {};
		for(var i = 0, len = activities.length; i < len; i++){
			var activity = activities[i];
      if(activity.info && typeof activity.info == 'string') activity.info = JSON.parse(decodeURIComponent(activity.info));
			if(activity.info && activity.info.belongsTo){
				frontGateMap[activity.id] = activity.info.belongsTo;
				activities[i] = null;//置为null，并用_.compact清除
			}
		}
		activities = _.compact(activities);
		//
		for(var i = 0, len = transitions.length; i < len; i++){
			var tran = transitions[i];
			if(frontGateMap[tran.to]){
				transitions[i] = null;
			}
		}
		transitions = _.compact(transitions);
		for(var i = 0, len = transitions.length; i < len; i++){
			var tran = transitions[i];
			if(frontGateMap[tran.from]){
				tran.from = frontGateMap[tran.from];
			}
		}
		transitions = _.compact(transitions);
		json.transitions = transitions;


		//构造map
		for(var i = 0, len = activities.length; i < len; i++){
			var node = activities[i];
			var nodeId = node.id;
			node.left = parseInt(node.left);
			node.top = parseInt(node.top);
			node.selectable = node.allow_select_approver == '1' ? 'true':'false'
			//构建审批人
			if(node.candidateIds){
				var ids = node.candidateIds;
				for(var j = 0, lenj = ids.length; j < lenj; j++){
				 	var candId = ids[j].value + '';
				 	var candInfo = rk_userService.getUserInfo(candId);
					ids[j] = $.extend(true, {"st": "1"}, ids[j] , candInfo);
					if(ids[j].id){
						ids[j].value = ids[j].id;
					} 
					ids[j].label = ids[j].name;
					delete ids[j].id;
					delete ids[j].name;
				}
			}
			//构建审批组
			if(node.candidateGroupIds){
				var ids = node.candidateGroupIds;
				for(var j = 0, lenj = ids.length; j < lenj; j++){
				 	var candId = ids[j].value + '';
				 	var candInfo = departmentService.getDepartInfo(candId);
					ids[j] = $.extend(true, {}, candInfo);
          			ids[j].value = ids[j].id;
					ids[j].label = ids[j].name;
          			//ids[j].text = ids[j].name;
					delete ids[j].id;
				}
			}
			me.currentData.nodes[nodeId] = node;
		}
		var nodes = me.currentData.nodes;
		var tranNames = {};//transitions的name
		//构造from，to
		for(var i = 0, len = transitions.length; i < len; i++){
			var link = transitions[i];
			var from = link.from;
			var to = link.to;
			var fromInfo = nodes[from];
			var toInfo = nodes[to];

			if(fromInfo && fromInfo.info && fromInfo.info.gotoTargetId && fromInfo.info.gotoTargetId == to){//不能将goto的from和to作为正常连线绘制出来，goto需另行处理
				if(!me.currentData.tempGotoRenderInfo) me.currentData.tempGotoRenderInfo = [];
				me.currentData.tempGotoRenderInfo.push({
					from: from,
					to: to,
					left: fromInfo.info.gotoLeft,
					top: fromInfo.info.gotoTop
				});
				fromInfo.gotoTargetId = to;
				if(!toInfo.gotoSourceId)toInfo.gotoSourceId = [];
				toInfo.gotoSourceId.push(from);
				continue;
			} 

			if(!me.linkMap[from])me.linkMap[from] = [];
			me.linkMap[from].push(to);

			if(!me.linkReverseMap[to])me.linkReverseMap[to] = [];
			me.linkReverseMap[to].push(from);

			if(link.condition && link.condition.conditions){
				var conds = link.condition.conditions;
				$(conds).each(function(ii, cond){
					cond.leftText = flowchartService.getConditionLeftText(cond.left.expression);
					cond.typeText = flowchartService.getConditionMidText(cond.type);
					cond.rightText = flowchartService.getConditionRightText(cond.left.expression, cond.right.value);
				})
			}

			if(link.name){
				tranNames[from+to] = link.name;
			}
		}
		//构造next，prev
		for(var id in nodes){
			var node = nodes[id];
			var nodeId = node.id;
			node.next = me.linkMap[nodeId];
			node.prev = me.linkReverseMap[nodeId];
		}
		//构造conditionMap
		for(var id in nodes){
			var node = nodes[id];
			if(node.next && node.next.length > 1){
				node.conditionMap = {};
				$(node.next).each(function (i, to){
					node.conditionMap[to] = {
						name: tranNames[id+to]
					}
				});
			}
		}
    //兼容旧版数据
    if(!me.currentData.nodes.start.name) me.currentData.nodes.start.name = '开始';
    if(!me.currentData.nodes.end.name) me.currentData.nodes.end.name = '结束';
		return me.currentData;
	}
});

var SERVERDATA = {
	"tree": [
    {
      "id": 10401,
      "parent": "#",
      "pinyin": "quangongsi",
      "text": "全公司"
    },
    {
      "id": 20002,
      "parent": 10401,
      "pinyin": "chanpinyanfa",
      "text": "产品研发"
    },
    {
      "id": 30101,
      "parent": 20002,
      "pinyin": "web",
      "text": "WEB"
    },
    {
      "id": 30104,
      "parent": 30101,
      "pinyin": "java",
      "text": "JAVA"
    },
    {
      "id": 30105,
      "parent": 30101,
      "pinyin": "js",
      "text": "JS"
    },
    {
      "id": 30102,
      "parent": 20002,
      "pinyin": "mobile",
      "text": "MOBILE"
    },
    {
      "id": 30103,
      "parent": 20002,
      "pinyin": "test",
      "text": "TEST"
    },
    {
      "id": 33201,
      "parent": 20002,
      "pinyin": "chanpinyanfa",
      "text": "产品研发"
    },
    {
      "id": 35901,
      "parent": 20002,
      "pinyin": "xiaoshou1bu",
      "text": "销售1部"
    },
    {
      "id": 49001,
      "parent": 35901,
      "pinyin": "xiaoshou1buzibumen",
      "text": "销售1部子部门"
    },
    {
      "id": 20004,
      "parent": 10401,
      "pinyin": "xiaoshoubu",
      "text": "销售部"
    },
    {
      "id": 30001,
      "parent": 20004,
      "pinyin": "zhixiao",
      "text": "直销"
    },
    {
      "id": 48001,
      "parent": 20004,
      "pinyin": "xiaoshoubu",
      "text": "销售部"
    },
    {
      "id": 30302,
      "parent": 20004,
      "pinyin": "waixiao",
      "text": "外销"
    },
    {
      "id": 37201,
      "parent": 20004,
      "pinyin": "shouqian",
      "text": "售前"
    },
    {
      "id": 53212,
      "parent": 10401,
      "pinyin": "zi11",
      "text": "子11"
    },
    {
      "id": 53401,
      "parent": 53212,
      "pinyin": "zi13",
      "text": "子13"
    },
    {
      "id": 30602,
      "parent": 10401,
      "pinyin": "qudaobu",
      "text": "渠道部"
    },
    {
      "id": 46703,
      "parent": 30602,
      "pinyin": "qudaob",
      "text": "渠道B"
    },
    {
      "id": 53207,
      "parent": 30602,
      "pinyin": "zi6",
      "text": "子6"
    },
    {
      "id": 53203,
      "parent": 53207,
      "pinyin": "zi2",
      "text": "子2"
    },
    {
      "id": 53202,
      "parent": 53203,
      "pinyin": "zi1",
      "text": "子1"
    },
    {
      "id": 53208,
      "parent": 53202,
      "pinyin": "123333",
      "text": "123333"
    },
    {
      "id": 53210,
      "parent": 53202,
      "pinyin": "zi9",
      "text": "子9"
    },
    {
      "id": 53201,
      "parent": 10401,
      "pinyin": "zibumenhenduobu",
      "text": "子部门很多部"
    },
    {
      "id": 53204,
      "parent": 53201,
      "pinyin": "zi3",
      "text": "子3"
    },
    {
      "id": 53213,
      "parent": 53204,
      "pinyin": "zi12",
      "text": "子12"
    },
    {
      "id": 53206,
      "parent": 53204,
      "pinyin": "zi5",
      "text": "子5"
    },
    {
      "id": 53205,
      "parent": 53201,
      "pinyin": "zi4",
      "text": "子4"
    }
  ],
  "users": {
    "20015": {
      "id": "20015",
      "name": "刘志强",
      "icon": "/img/default_face.png"
    },
    "20016": {
      "id": "20016",
      "name": "杨松",
      "icon": "https://yangxhbucket.s3.cn-north-1.amazonaws.com.cn/2015/06/02/s_12201969-79f3-4b24-b39d-3bba0f731cfd.jpg"
    },
    "20017": {
      "id": "20017",
      "name": "邓芳",
      "icon": "https://devrs.ingageapp.com/upload/i/10201/20017/s_7dd300098c5f4fad912459b7f061d8c0.jpg"
    },
    "20019": {
      "id": "20019",
      "name": "张磊",
      "icon": "https://devrs.ingageapp.com/upload/i/10201/20019/s_947fac495eec40cba8d03306fe61ceac.jpg"
    },
    "20020": {
      "id": "20020",
      "name": "张云丰",
      "icon": "https://devrs.ingageapp.com/upload/i/10201/20020/s_fb3120a47b2449f5b06c58f153f514f3.jpg"
    },
    "20023": {
      "id": "20023",
      "name": "沈丹丹",
      "icon": "https://devrs.ingageapp.com/upload/i/10201/20023/s_fae06e076f924adbad83697cd188274e.jpg"
    },
    "20025": {
      "id": "20025",
      "name": "顾培",
      "icon": "https://devrs.ingageapp.com/upload/i/10201/20025/s_7214d585d2c34c98b377292becdd7a2a.jpg"
    },
    "20026": {
      "id": "20026",
      "name": "姚任金",
      "icon": "https://devrs.ingageapp.com/upload/i/10201/20026/s_b769864ea5394796bf0291474112ecfe.jpg"
    }
  }
}