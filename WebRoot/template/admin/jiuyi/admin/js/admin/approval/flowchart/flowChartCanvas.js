$.widget('rk.flowChartCanvas', $.rk.flowChart, {
	serverData: {},
	currentData: {},
	maxSpace: 0, //最多占用孩子单位
	spaceHeight: 0, //每一个孩子单位的高度
	options: {
		readonly: false,
		taskHeight: 95,
		taskWidth: 150,
		gateHeight: 25,
		gateWidth: 25
	},
	lineColor: "#cacbcc",
	lineHoverColor: "#cacbcc",	

	tasksNameCount: 1,
	gatesNameCount: 1,
	condsNameCount: 1,

	tasksIdCount: 0,
	gatesIdCount: 0,
	condsIdCount: 0,

	draggrid: [23, 10],
	taskText: '节点',
	gateText: '网关',
	condText: '分支',
	taskIdPrefix: 'task',
	gateIdPrefix: 'gate',
	condIdPrefix: 'condition',
	iamDirty: false,
	_create: function () {
		var me = this;
		var elem = me.element;
		var opt = me.options;

		me.isFF = (platform.name === "Firefox");
		me.isIE = (platform.name === "IE");

		jsPlumb.ready(function () {
			me._initCanvas();
		});

		elem.data('flowchart_inited', true);

		opt.taskRoom = opt.taskWidth * 1.5; //任务节点所占的空间
		opt.taskVRoom = opt.taskHeight * 1.5;
		opt.taskHRoom = opt.taskWidth * 1.5;
		opt.gateRoom = opt.gateWidth * 1.5;
		opt.gateWidth = opt.taskRoom; //网关节点所占的空间
	},
	//对外接口
	isSetBranchCond: function(){//全局查找是否有分支条件存在
		var me = this;
		var nodes = me.currentData.nodes;
		for(var id in nodes){
			var n = nodes[id];
			if(!$.isEmptyObject(n.conditionMap)){
				for(sub in n.conditionMap){
					var sbnode = n.conditionMap[sub];
					if(sbnode.conditions && sbnode.conditions.length > 0)return true;
				}
			}
		}
		return false;
	},
	deleteBranchCond: function(){//全局清空所有分支条件
		var me = this;
		var elem = me.element;
		var nodes = me.currentData.nodes;
		for(var id in nodes){
			var n = nodes[id];
			if(n.next)
			for(var i = 0, len = n.next.length; i < len; i++){
				me.cleanConditionLabel(id, n.next[i]);
			}
			//n.conditionMap = {};
		}
	},
	loadConditionItems: function(){
		var me = this;
		var json = me._getConditionData();
		me.setConditionItems(json);		
	},
	validate: function(){
		var me = this;
		var elem = me.element;
		var nodes = me.currentData.nodes;
		var nodecount = 0, taskcount = 0, branchcount = 0, pointcount = 0;
		var nodeElemets = elem.find('>.rk-endpoint');

		var branchHasNoCondition = false;//分支上没有设定条件
		var branchHasNoName = false;//分支上没有设定名字
		var hasNoApproverAssigned = false;
		var tooManyCandidates = false, tooManyCandidateGroups = false;
		var nodeHasNoName = false;
		for(var id in nodes){
			var node = nodes[id];
			var name = node.name;
			pointcount++;//所有节点个数
			if(node.type == 'userTask'){
				nodecount ++;
				if(node.id != 'task0')taskcount++;//任务计数不包含task0
				var hasCandidate = false, hasCandidateGroup = false, hasSelectable = false;
				if(!node.name) nodeHasNoName = true;
				if($.isArray(node.candidateIds) && node.candidateIds.length > 0) {
					hasCandidate = true;
					//if(node.candidateIds.length > 20)tooManyCandidates = name;
					if(node.candidateIds.length > 50)tooManyCandidates = name;
				}
				if($.isArray(node.candidateGroupIds) && node.candidateGroupIds.length > 0) {
					hasCandidateGroup = true;
					if(node.candidateGroupIds.length > 10)tooManyCandidateGroups = name;
				}
				if(node.selectable == 'true') hasSelectable = true;
				if(id != 'start' && id!='task0'&& id!='end')if(hasCandidate || hasCandidateGroup || hasSelectable) {
					
				}else{
					hasNoApproverAssigned = hasNoApproverAssigned ? hasNoApproverAssigned : name;
				}

			}
			if(node.next && node.next.length > 1){
				branchcount = branchcount + node.next.length;
			}
			if(node.next && node.next.length > 1){//为网关节点
				var div = nodeElemets.filter('[id="'+id+'"]');
				var conditionMap = me.getNodeInfo(id).conditionMap;
				if(!conditionMap) {
					branchHasNoCondition = branchHasNoCondition ? branchHasNoCondition : [id];
				}else{
					var children = node.next;
					for(var i = 0, len = children.length; i < len; i++){
						var childId = children[i];
						var childName = me.getNodeInfo(childId).name;
						var childCond = conditionMap[childId];
						if(!childCond){
							branchHasNoCondition = branchHasNoCondition ? branchHasNoCondition : [id, childId];
						}else{
							if(!childCond.name) branchHasNoName = branchHasNoName ? branchHasNoName : [id, childId];
							var arr = childCond.conditions;
							if(!$.isArray(arr) || arr.length == 0) branchHasNoCondition = branchHasNoCondition ? branchHasNoCondition : [id, childId];							
						}
					}
				}
			}			
		}
		if(nodeHasNoName){ ingage.common.errorResult('节点未命名'); return false; }
		if(me.isDefault()){ ingage.common.errorResult('审批流程至少包含一个审批步骤'); return false; }
		if(taskcount > 50){ ingage.common.errorResult('节点最大个数不能超过50个'); return false; }
		if(branchcount > 20){ ingage.common.errorResult('分支最大个数20个'); return false; }
		if(branchHasNoName){
			var name1 = me.getNodeInfo(branchHasNoName[0]).name;
			var name2 = me.getNodeInfo(branchHasNoName[1]).name;
			name = ('"'+name1 + '"到"' + name2+'"'); 
			ingage.common.errorResult(name+'的分支上未指定名称');
			return false; 
		}
		if(branchHasNoCondition){
			var connInfo = me.getConnInfo(branchHasNoCondition[0], branchHasNoCondition[1]);
			var name;
			if(connInfo){
			   name = connInfo.name;
			}else{
			   var name1 = me.getNodeInfo(branchHasNoCondition[0]).name;
			   var name2 = me.getNodeInfo(branchHasNoCondition[1]).name;
			   name = (name1 + '"和"' + name2);
			}
			ingage.common.errorResult('"'+name+'"的分支上没有设定条件'); 
			return false; 
		}
		if(hasNoApproverAssigned){ ingage.common.errorResult('"'+hasNoApproverAssigned + '"未指定审批人'); return false; }
		//if(tooManyCandidates){ ingage.common.errorResult('"'+tooManyCandidates + '"指定的审批人大于了20个'); return false; }
		if(tooManyCandidates){ ingage.common.errorResult('"'+tooManyCandidates + '"指定的审批人大于了50个'); return false; }
		if(tooManyCandidateGroups){ ingage.common.errorResult('"'+tooManyCandidateGroups + '"指定的审批组大于了10个'); return false; }

		return true;
	},
	isDefault: function(){//是否只有start-task0-end三个点
		var me = this;
		var nodes = me.currentData.nodes;
		var count = 0;
		for(var id in nodes){
			count++;
		}
		if(count == 3 && nodes['start'] && nodes['task0'] && nodes['end']) return true;
		return false;
	},
	createNew: function(){
		var me = this;
		me.beDefaultCanvas();
	},
	isDirty: function(){
		return !!this.iamDirty;
	},
	setConditionItems: function(conditems){
		flowchartService.cacheConditionItems(conditems);
	},
	getConditionItems: function(){
		return flowchartService.getConditionItems();
	},
	getNewTaskName: function(id){
		var me = this;
		var newNum = me.tasksNameCount++;
		(function(){
			var nodes = me.currentData.nodes;
			for(id in nodes){
				var name = nodes[id].name;
				if(name.match("^"+me.taskText)){
					var num = parseInt(name.match(/\d+/ig));
					if(!isNaN(num)){
						if(num >= newNum) newNum = num+1;
					}
				}
			}
		})();
		return me.taskText + newNum;
	},
	getNewGateName: function(id){
		var me = this;
		return me.gateText + me.gatesNameCount++; 
	},
	getNewCondName: function(id){
		var me = this;
		var newNum = me.condsNameCount++;
		(function(){
			var nodes = me.currentData.nodes;
			for(id in nodes){
				var n = nodes[id];
				if(n.conditionMap)
				for(cond in n.conditionMap){
					var name = n.conditionMap[cond].name;					
					if(name && name.match("^"+me.condText)){
						var num = parseInt(name.match(/\d+/ig));
						if(!isNaN(num)){
							if(num >= newNum) newNum = num+1;
						}
					}
				}
			}
		})();
		return me.condText + newNum; 
	},
	getNewTaskId: function(){
		var me = this;
		me.tasksIdCount++;
		return me.taskIdPrefix + me.tasksIdCount;
	},
	getNewGateId: function(){
		var me = this;
		me.gatesIdCount++;
		return me.gateIdPrefix + me.gatesIdCount; 
	},
	getNewCondId: function(){
		var me = this;
		me.condsIdCount++;
		return me.condIdPrefix + me.condsIdCount; 
	},
	_updateIdCount: function(){//获得各自最大的id尾号，便于计数
		var me = this;
		var elem = me.element;
		var nodes = me.currentData.nodes;
		var maxTask = 0;
		var maxGate = 0;
		var maxCond = 0;
		for(id in nodes){
			if(id.match("^"+me.taskIdPrefix)){
				var num = parseInt(id.match(/\d+/ig));
				if(!isNaN(num)){
					if(num > maxTask) maxTask = num;
					me.tasksIdCount = maxTask;
				}
			}
			if(id.match("^"+me.gateIdPrefix)){
				var num = parseInt(id.match(/\d+/ig));
				if(!isNaN(num)){
					if(num > maxGate) maxGate = num;
					me.gatesIdCount = maxGate;
				}
			}
		}
	},
	_updateNameCount: function(){//获得各自最大的name尾号，便于计数
		var me = this;
		var elem = me.element;
		var nodes = me.currentData.nodes;
		var maxTask = 0;
		var maxGate = 0;
		var maxCond = 0;
		for(id in nodes){
			var name = nodes[id].name;
			if(name && name.match("^"+me.taskText)){
				var num = parseInt(name.match(/\d+/ig));
				if(!isNaN(num)){
					if(num > maxTask) maxTask = num;
					me.tasksNameCount = maxTask;					
				}
			}
		}
		var trans = me.currentData.transitions;
		$(trans).each(function(i, tran){
			var text = tran.name;
			if(text && text.match("^"+me.condText)){
				var num = parseInt(text.match(/\d+/ig));
				if(!isNaN(num)){
					if(num > maxCond) maxCond = num;
					me.condsNameCount = maxCond + 1;
				}
			}
		});
	},
	getRepaintTimeout: function(){
		return this.repaintTimeout;
	},
	getUiInfo: function(){
		return this.options;
	},
	getTaskRoom: function(dir){
		var me = this;
		var elem = me.element;
		var opt = me.options;
		if(typeof dir == 'undefined')dir = 'x';
		return dir == 'x' ? opt.taskRoom : opt.taskVRoom;
	},
	getGateRoom: function(){
		var me = this;
		var elem = me.element;
		var opt = me.options;
		return opt.gateRoom;
	},
	beDefaultCanvas: function(){
		var me = this;
		var elem = me.element;
		var opt = me.options;

		me._resetCanvas();
		try{
			elem.flowChartHistory('reset');
		}catch(e){}

		var pHeight = 50;
		var pWidth = 80;

		var top = (elem.outerHeight() - pHeight) / 2;

		var newTaskid = me.getNewTaskId();

		var startAndEnd = {
			start: {
				id: 'start',
				type: 'startEvent',
				name: '开始',
				next: [newTaskid],
				left: 10,
				top: top,
				info:{}
			},
			task0: {
				id: 'task0',
				type: 'userTask',
				name: '提交人',
				prev:['start'],
				next: ['end'],
				left: 90,
				top: top-30,
				info:{}
			},
			end: {
				id: 'end',
				type: 'endEvent',
				name: '结束',
				left: elem.outerWidth() - pWidth - 10,
				top: top,
				info:{}
			}
		};
		me.currentData = {
			nodes: startAndEnd
		}
		me.appendNodes(startAndEnd);
	},
	_resetCanvas: function(){
		var me = this;
		var elem = me.element;
		var opt = me.options;

		me.iamDirty = true;

		if(me.instance){
			// elem.find('>[id]').each(function(){
			// 	var id = $(this).attr('id');
			// 	me._cleanJPlumbEndPoints(id);
			// });
			me._cleanAllJPlumbEndPoints();

			me.instance.reset();			
		    me.instance.bind("click", function (conn, originalEvent) {
	           var source = conn.source;
	           var target = conn.target;
	           var sourceId = source.getAttribute('id');
	           var targetId = target.getAttribute('id');
	           //flowchartService.popupConditionEditor(sourceId, targetId);
	        });
		}
		me.currentData = {};
		me.connCache = {};

		me.tasksIdCount = -1;
		me.gatesIdCount = -1;
		me.condsIdCount = -1;

		me.tasksNameCount = 1;
		me.gatesNameCount = 1;
		me.condsNameCount = 1;

		elem.html('')//.removeData('ctxmenu_clicking_inited');
	},
	loadData: function(json, render){
		var me = this;
		var elem = me.element;
		var opt = me.options;

		if(typeof render == 'undefined') render = true;

		me._resetCanvas();
		rk_userService.loadUser({data:json});
		departmentService.loadUser(json.tree);
		me.serverData = json;
		me.currentData = me._parseJson(json);

		if(!render) return me.currentData;

		var nodes = me.currentData.nodes;
		me.appendNodes(nodes);
		window.setTimeout(function(){//延时以等待repaint方法绘制出显示条件的div
			me._applyConditions(json.transitions);//将条件保存在from节点中		
			me._updateIdCount();
			me._updateNameCount();
		}, me.repaintTimeout + 200);
		me._renderGoto(me.currentData.tempGotoRenderInfo);
		me.repositionStartEnd();
	},
	getNodes: function(){
		var me = this;
		return me.currentData.nodes;
	},
	setNodes: function(nodes){
		var me = this;
		me.currentData.nodes = nodes;
	},
	setX: function(id, x){
		var me = this;
		var elem = me.element;
		var nodeEl = me.getNodeElem(id);//elem.find('>[id="'+id+'"]');
		var nodeInfo = me.getNodeInfo(id);
		nodeInfo.x = x;
		var left = (x - nodeEl.outerWidth() / 2);
		if(nodeInfo) nodeInfo.left = left;
		nodeEl.css('left', left + 'px');
	},
	getX: function(id){
		var me = this;
		var elem = me.element;

		var nodeEl = me.getNodeElem(id);//elem.find('>[id="'+id+'"]');
		var left = parseInt(nodeEl.css('left'));
		return left + nodeEl.outerWidth() / 2;
	},
	setY: function(id, y){
		var me = this;
		var elem = me.element;
		var nodeEl = me.getNodeElem(id);//elem.find('>[id="'+id+'"]');
		var nodeInfo = me.getNodeInfo(id);
		nodeInfo.y = y;
		var top = (y - nodeEl.outerHeight() / 2);
		if(nodeInfo) nodeInfo.top = top;
		nodeEl.css('top', top + 'px');
	},
	getY: function(id){
		var me = this;
		var elem = me.element;

		var nodeEl = me.getNodeElem(id);//elem.find('>[id="'+id+'"]');
		var top = parseInt(nodeEl.css('top'));
		return top + nodeEl.outerHeight() / 2;
	},
	setXY: function(id, x, y){
		this.setX(id, x);
		this.setY(id, y);
	},
	repositionSibilings: function(id){
		var me = this;
		var opt = me.options;

		var myInfo = me.getNodeInfo(id);
		var siblings = me.getSiblings(id);
		if(siblings.length == 0)return;
		siblings.push(id);//含自己！
		var gateId = me.getCommonChildIdList(siblings)[0];
		var gateInfo = me.getNodeInfo(gateId);
		var shouldMove = false;
		var minGap = 9999999999999999;
		var maxLeft = 0;
		var maxId, minId;
		var prevs = gateInfo.prev;
		$(prevs).each(function(i, sibid){
			var ninfo = me.getNodeInfo(sibid);
			if(gateInfo.left < (ninfo.left + opt.taskWidth)) shouldMove = true;
			if(ninfo.left > maxLeft){
				maxLeft = ninfo.left;
				maxId = ninfo.id;
			}
			var gap = gateInfo.left - ninfo.left - opt.taskWidth;
			if(minGap > gap){
				minGap = gap;
				minId = ninfo.id;
			}
		});
		if(shouldMove){
			var offset = maxLeft - gateInfo.left;
			me.stepAWay(maxId, offset + opt.taskWidth);
		}else if(minGap > 100){//后置网管距离子节点太远
			me.stepAWay(minId, minGap * -1 + 10);
		}
	},
	repositionStartEnd: function(){
		var me = this;
		var elem = me.element;

		var nodeDivs = elem.find('>.rk-endpoint');
		var minTop = 999999999999, maxTop = 0;
		var minLeft = 999999999999, maxLeft = 0;
		nodeDivs.each(function(){
			var div = $(this);
			if(!div.hasClass('startEvent') && !div.hasClass('endEvent')){
				var top = parseInt(div.css('top'));
				var left = parseInt(div.css('left'));

				if(top > maxTop)maxTop = top;
				if(top < minTop)minTop = top;

				if(left > maxLeft)maxLeft = left;
				if(left < minLeft)minLeft = left;
			}
		});
		var midTop = (maxTop - minTop) / 2 + minTop;
		// nodeDivs.filter('.startEvent').css('top', midTop + 'px');
		// nodeDivs.filter('.endEvent').css('top', midTop + 'px');

		nodeDivs.filter('.startEvent').css('left', 10 + 'px');
		nodeDivs.filter('.endEvent').css('left', maxLeft + 140 + 'px');

		me.repaint()
	},
	_applyConditions: function(trans){
		var me = this;
		var elem = me.element;

		if(trans)
		for(var i = 0, len = trans.length; i < len; i++){
			var tran = trans[i]
			if(tran.condition){
				var sourceId = tran.from;
				var targetId = tran.to;
				var conditionMap = me.getNodeInfo(sourceId).conditionMap;
				if(!conditionMap){
					conditionMap = {};
				}
				conditionMap[targetId] = tran.condition;				
				conditionMap[targetId].name = tran.name;
				me.getNodeInfo(sourceId).conditionMap = conditionMap;
				me.updateViewByConditions(sourceId, targetId, tran.condition.conditions);
			}
		}
	},
	getConditions: function(from, to){
		var me = this;
		var elem = me.element;
		var conditionMap = me.getNodeInfo(from).conditionMap;
		if(conditionMap)return conditionMap[to];
	},
	getJsonString: function(withUserInfo){
		var me = this;
		var elem = me.element;
		var opt = me.options;

		if(typeof withUserInfo == 'undefined') withUserInfo = false;

		me._updateCurrentTransitions();

		var nodes = JSON.parse(JSON.stringify(me.currentData.nodes));
		var transitions = me.currentData.transitions;
		var cloneTransitions = [];
		//更新普通的from，to
		for(var i = 0, len = transitions.length; i < len; i++){
			cloneTransitions.push($.extend(true, {}, transitions[i]));			
		}
		//更新goto的from，to
		for(var id in nodes){
			var n = nodes[id];
			if(n.gotoTargetId){
				cloneTransitions.push({
					from: id,
					to: n.gotoTargetId
				});
			}
		}
		//********************************************************
		//  寻找网关节点，并构建前置网关
		//********************************************************
		var cacheId = {}, cacheTransitions = {}, gateIds = [];
		var fromId;
		//寻找网关节点（有多孩子即为网关）
		for(var i = 0, len = cloneTransitions.length; i < len; i++){
			var transition = cloneTransitions[i];
			fromId = transition.from;
			if(cacheId[fromId]){
				gateIds.push(fromId);
			}else{
				cacheId[fromId] = true;
				cacheTransitions[fromId] = [];
			}
			cacheTransitions[fromId].push(transition);
		}
		gateIds = _.uniq(gateIds);
		//构建前置网关，注意：这种前置网关只用来提交给后台，前端不予识别
		for(var i = 0, len = gateIds.length; i < len; i++){
			var gateid = gateIds[i];
			var gateInfo = me.getNodeInfo(gateid);
			var gotoId = gateInfo.gotoTargetId;//这是个goto网关，需要增加约定好的condition
			var oldTrans = cacheTransitions[gateid];
			var newId = "gateOf_"+gateid;
			nodes[newId] = {
			      "type": "exclusiveGateway",
			      "name": newId,
			      "id": newId,
			      "left": 0,//因为不会在前端显示，因此不存在left和top
			      "top": 0,
			      "allow_select_approver": "0",
			      "info": {
			      	  belongsTo: gateid,
			      	  hasGoto: gotoId ? gotoId : null //注意，该属性仅供后端使用，前端不予识别
			      }
			   };
			for(var j = 0, lenj = cloneTransitions.length; j < lenj; j++){
				if(cloneTransitions[j].from == gateid) cloneTransitions[j] = null;//置为null，并在之后用_.compact清除
			}
			cloneTransitions = _.compact(cloneTransitions);
			//新增前置网关
			cloneTransitions.push({
					from: gateid,
					to: newId
				})
			//将旧网关的子连接到新网关上
			if(!gotoId){
				//普通前置网关
				for(var j = 0, lenj = oldTrans.length; j < lenj; j++){
					var oldtran = oldTrans[j];
					var to = oldtran.to;
					if(oldtran.condition && oldtran.condition.conditions)
					$(oldtran.condition.conditions).each(function(k, cond){
						var key = cond.left.expression;
						cond.itemTypeEntry = flowchartService.getConditionEntryType(key);
					});
					cloneTransitions.push({
						name: oldtran.name,
						from: newId,
						to: to,
						condition: oldtran.condition
					})
				}
			}else{
				//goto的前置网关
				for(var j = 0, lenj = oldTrans.length; j < lenj; j++){
					var to = oldTrans[j].to;
					cloneTransitions.push({
						name: oldTrans[j].name,
						from: newId,
						to: to,
						condition: (gotoId === to ? {
										                "type": "and",
										                "conditions": [
										                    {
										                        "type": "equals",
										                        "left": {
										                            "expression": "_decision"
										                        },
										                        "right": {
										                            "value": "approve"
										                        }
										                    }
										                ]
										            }:oldTrans[j].condition)
					})
				}
			}
		}
		var json = {
		    "name": "flowchart1",
		    "activities": [],
		    "transitions": cloneTransitions
		};
		//处理goto节点
		var nodeDiv, approvers, departs;
		var gotoPoints = elem.find('>.rk-goto-point');
		var nodeElems = elem.find('>div.rk-endpoint');
		for(var id in nodes){
			var node = nodes[id];
			var nodeEl = nodeElems.filter('[id="'+id+'"]');//me.getNodeElem(id);
			var saveinfo = {
	            "type": node.type,
	            "id": id,
	            "name": node.name,
	            "left": parseInt(nodeEl.css('left')),
	            "top": parseInt(nodeEl.css('top')),
	            "info": node.info ? node.info : {},
	            "allow_select_approver": node.selectable == 'true' ? 1 : 0
	        };
	        nodeDiv = elem.find('#' + id);
	        approvers = nodeDiv.data('approvers');
	        departs = nodeDiv.data('departs');
	        selectable = nodeDiv.data('selectable');

	        if(approvers) {saveinfo.candidateIds = approvers;}
	        if(departs) {saveinfo.candidateGroupIds = departs;}

	        //saveinfo.allow_select_approver = (selectable == 'true' ? 1 : 0);

	        if(node.gotoTargetId){//保存goto信息到info
	        	var gotopoint = gotoPoints.filter('[id="'+id+'-goto-'+node.gotoTargetId+'"]');
	        	saveinfo.info.gotoTargetId = node.gotoTargetId
	        	saveinfo.info.gotoTop = parseInt(gotopoint.css('top'));
	        	saveinfo.info.gotoLeft = parseInt(gotopoint.css('left'));
	        }
	        if(saveinfo.info){
				saveinfo.info = encodeURIComponent(JSON.stringify(saveinfo.info));//存成文字形式
			}
			json.activities.push(saveinfo);
		}

		if(withUserInfo){
			//history实现undo时，user和tree的信息需要一并保存
			json.users = rk_userService.getAllUsers();
			json.tree = departmentService.getAllDeparts();
		}
		return JSON.stringify(json);
	},
	_updateCurrentTransitions: function(){
		var me = this;
		var elem = me.element;

		var nodes = me.currentData.nodes;
		me.currentData.transitions = [];
		var nodeElems = elem.find('>.userTask');
		for(id in nodes){
			var node = nodes[id];
			var next = node.next;
			if(next && $.isArray(next)){
				next = _.uniq(next);
				for(var i = 0; i < next.length; i++){
					var to = next[i];
					var tran = {
						from: id,
						to: to
					};
					var conditionMap = me.getNodeInfo(id).conditionMap;
					if(conditionMap && conditionMap[to]){
						tran.condition = conditionMap[to];
						tran.name = conditionMap[to].name;
					}
					me.currentData.transitions.push(tran);
				}
			}
		}
	},
	showConditionLabel: function(){
		var me = this;
		var elem = me.element;
		var opt = me.options;

		var nodes = me.currentData.nodes;
		var validConditionLabels = [];
		for(var id in nodes){
			if(nodes[id].next && nodes[id].next.length > 1){
				validConditionLabels.push(id);
			}
		}
		var labels = elem.find('>[class*="condition-lable"]');
		labels.remove();
		var nodeElems = me.getNodeElems();
		for(var i = 0, len = validConditionLabels.length; i < len; i++){
			var startId = validConditionLabels[i];
			var ninfo = nodes[startId];
			if(ninfo.next && ninfo.next.length > 1)
			$(ninfo.next).each(function(j, nextId){

				if(nextId.indexOf('-goto-')>=0)return true;

				var from = startId;
				var to = nextId;

				var nodeEl = nodeElems.filter('[id="'+nextId+'"]');
				var nodeInfo = me.getNodeInfo(from);
				var nextInfo = me.getNodeInfo(to);
				if(nextInfo && nextInfo.type == 'exclusiveGateway') return true;

				var svg = me.getConnSvg(startId, nextId);
				var labelWidth = svg.width() * 0.85;
				var labelLeft = nodeEl.position().left - labelWidth;
				var labelTop = nodeEl.position().top + nodeEl.outerHeight()/2 - 24;

				var labelClass = from+ '-' +to;
				var labelClassTop = me.__getCondTopLabelCss(from, to);
				var labelClassBottom = me.__getCondBottomLabelCss(from, to);

				if(!nodeInfo.conditionMap) nodeInfo.conditionMap = {};
				if(!nodeInfo.conditionMap[to]) nodeInfo.conditionMap[to] = {
					name:'',
					conditions:[]
				}

				//上label
				var label = elem.find('.condition-lable-top[from="'+from+'"][to="'+to+'"]');
				if(label.size() == 0){
					elem.append('<div class="condition-lable-top '+labelClassTop+'" from="'+from+'" to="'+to+'" style="height: 20px; border:0;position:absolute;">')
					label = elem.find('>.condition-lable-top.'+labelClassTop);
					var labelName;
					if(nodeInfo.conditionMap && nodeInfo.conditionMap[to]){
						labelName = nodeInfo.conditionMap[to].name;
					}
					if(!labelName) nodeInfo.conditionMap[to].name = me.getNewCondName();
					label.html(template('flowchartConditionNameEditorTpl-top', {
						name: nodeInfo.conditionMap[to].name, //me.getNewCondId()
						isReadonly: opt.readonly
					}));
					me.currentData.nodes[from] = nodeInfo;
				}

				label.css('top', labelTop+'px')
					.css('left', labelLeft+'px')
					.width(labelWidth)
					.show();

				//下label
				var label = elem.find('.condition-lable-bottom[from="'+from+'"][to="'+to+'"]');//labels.filter('.condition-lable-bottom.'+labelClassBottom);
				if(label.size() == 0){
					elem.append('<div class="condition-lable-bottom '+labelClassBottom+'" from="'+from+'" to="'+to+'" style="height: 20px; border:0;position:absolute;">')
					label = elem.find('>.condition-lable-bottom.'+labelClassBottom);
					label.html(template('flowchartConditionNameEditorTpl-bottom'));
				}

				labelTop = labelTop + 30;

				label.css('top', labelTop+'px')
					.css('left', labelLeft+'px')
					.width(labelWidth)
					.show();
			});
		}
	},
	getConnWidth: function(from, to){//获取连线的宽度（其实是连线svg的宽度）
		var me = this;
		var svg = me.getConnSvg();
		return $(svg).width();
	},
	getConnKey: function(from, to){
		return 'from_'+from+'_to_'+to;
	},
	getConnSvg: function(from, to){
		var me = this;
		var conn = me.getConn(from, to);
		if(!conn)return null;
		var svg = conn.canvas;
		return $(svg);
	},
	getConn: function(from, to){
		var me = this;
		var cacheKey = me.getConnKey(from, to);
		return me.connCache[cacheKey];
	},
	getConnInfo: function(from, to){		
		var me = this;
		var fromInfo = me.getNodeInfo(from);
		if(!fromInfo)return;
		if(fromInfo.conditionMap && fromInfo.conditionMap[to]){
			return fromInfo.conditionMap[to];
		}
	},
	cacheConn: function(from, to, connection){
		var me = this;
		var cacheKey = me.getConnKey(from, to);
		me.connCache[cacheKey] = connection;
	},
	removeConnCache: function(from, to){
		var me = this;
		var cacheKey = me.getConnKey(from, to);
		delete me.connCache[cacheKey];
	},
	connCache: {},
	__connect: function(from, to, shouldUpdateInfo, conf){
		if(typeof conf == 'undefined')conf = {};
		var me = this;
		if(me.getConn(from, to)) return;
		var connection = me.instance.connect({ 
												uuids: [ from + "RightMiddle",  to + (conf.targetPoint ? conf.targetPoint : "LeftMiddle")], 
												editable: false, 
												detachable: false , 
												overlays:[//http://www.jsplumb.org/doc/overlays.html#type-label
												   [ "Label", {
												   		label: '', 
												   		location:0.9,
												   		labelStyle:{
												   			cssClass: '' //条件label
												   		}
												   }]
												] 
											});
		me.cacheConn(from, to, connection);

		if(!shouldUpdateInfo)return connection;

		var fromNode = me.getNodeInfo(from);
		var toNode = me.getNodeInfo(to);
		if(fromNode.next){
			fromNode.next.push(to);
			fromNode.next = _.uniq(fromNode.next);
		}
		if(toNode && toNode.prev){
			toNode.prev.push(from);
			toNode.prev = _.uniq(toNode.prev);
		}
		return connection;
	},
	appendNodes: function(nodes, shouldUpdateInfo){
		var me = this;
		var elem = me.element;
		var opt = me.options;

		if(typeof shouldUpdateInfo == 'undefined') shouldUpdateInfo = false;

		var canvasId = elem.attr('id');
		$.extend(true, me.currentData.nodes, nodes);

		//加入批处理任务
		me.instance.batch(function () {
			var nodesHtml = '';
			for(var id in nodes){
				var node = nodes[id];
				var html = '<div class="rk-endpoint '+node.type+'" id="'+id+'" style="left:'+node.left+'px;top:'+node.top+'px;" type="'+node.type+'">'+node.name+'</div>';
				node.readonlyWhenPostponed = (id=='task0' && !!opt.postponed);
				if(node.type == 'startEvent' || node.type == 'endEvent') html = template('flowchartStartEndTypeTpl', $.extend(true, {id:id}, node));
				if(node.type == 'userTask') html = template('flowchartTaskTypeTpl', $.extend(true, {
																										id:id, 
																										isBlank:true, 
																										isReadonly: opt.readonly
																									}, node));
				if(node.type == 'exclusiveGateway') html = template('flowchartGateWayTypeTpl', $.extend(true, {id:id, isReadonly: opt.readonly}, node));
				//if(node.type == 'gotomid') html = template('flowchartGotoMidTypeTpl', $.extend(true, {id:id}, node));
				nodesHtml = nodesHtml + html;
			}
			elem.append(nodesHtml);
			for(var id in nodes){
				var node = nodes[id];
				me._addEndpoints(
					id, 
					( node.type != 'endEvent' ? ["RightMiddle"] : []), 
					( node.type != 'startEvent' ? ["LeftMiddle", "BottomCenter", "TopCenter"] : [])
				);
			}
			var nextId, prevId;
			for(var id in nodes){
				var node = nodes[id];
				if(node.next)
					for(var i = 0; i < node.next.length; i++){
						nextId = node.next[i];
						me.__connect(id, nextId, shouldUpdateInfo);
						//me.instance.connect({ uuids: [ id + "RightMiddle",  nextId + "LeftMiddle"], editable: false, detachable: true });
					}
				if(node.prev)
					for(var i = 0; i < node.prev.length; i++){
						prevId = node.prev[i];
						me.__connect(prevId, id, shouldUpdateInfo);
						//me.instance.connect({ uuids: [ prevId + "RightMiddle",  id + "LeftMiddle"], editable: false, detachable: true });
					}
			}
		});

		$.extend(true, me.currentData.nodes, nodes);//再继承一次，确保更新
		me._updateCurrentTransitions();

		if(!opt.readonly){
			var area = jsPlumb.getSelector("#"+canvasId+" .rk-endpoint:not(.startEvent)");
			me.instance.draggable(area, { 
				grid: me.draggrid, 
				drag: function(){
					elem.find('>[class^="condition-lable"]').hide();
					window.clearTimeout(me.cancelSelTimer);
				},
				stop: function( event, ui ) {
					window.clearTimeout(me.cancelSelTimer);
					var node = $(event.target);
					var id = node.attr('id');
					var nodeInfo = me.getNodeInfo(id);
					var pos = ui.position;
					var oldpos = ui.originalPosition;
					var leftOff = pos.left - oldpos.left;
					var topOff = pos.top - oldpos.top;				
					nodeInfo.left = pos.left;				
					nodeInfo.top = pos.top;

					if(node.hasClass('rk-endpoint-selected'))
					elem.find('.rk-endpoint-selected').not('[id="'+id+'"]').each(function(i, n){
						n = $(n);
						var nid = n.attr('id');
						var ninfo = me.getNodeInfo(nid);
						var newleft = parseInt(n.css('left')) + leftOff;
						var newtop = parseInt(n.css('top')) + topOff;
						me.setLeftTop(nid, newleft, newtop);
					});
					me.repositionSibilings(id);
					me.repositionStartEnd();
					me.repaint();
				} 
			});
			var area = jsPlumb.getSelector("#"+canvasId+" .startEvent");
			me.instance.draggable(area, { 
				axis: "y",
				grid: me.draggrid, 
				drag: function(){
					window.clearTimeout(me.cancelSelTimer);
				},
				stop: function( event, ui ) {
					var node = $(event.target);
					var id = node.attr('id');
					var nodeInfo = me.getNodeInfo(id);
					var pos = ui.position;
					var oldpos = ui.originalPosition;
					var leftOff = pos.left - oldpos.left;
					var topOff = pos.top - oldpos.top;				
					nodeInfo.left = pos.left;				
					nodeInfo.top = pos.top;
					window.clearTimeout(me.cancelSelTimer);
				}
			});
		}

		var nodeElems = me.getNodeElems();
		for(var id in nodes){
			if((me.isFF || me.isIE) && me.getNodeInfo(id).type=='userTask')me.instance.setDraggable(id, false);//FF下不能直接打开拖拽，会妨碍节点内部的滚动条
			var node = nodeElems.filter('[id="'+id+'"]');
			var info = me.currentData.nodes[id];
			if(info.candidateIds) node.data('approvers', info.candidateIds);
			if(info.candidateGroupIds) node.data('departs', info.candidateGroupIds);
			me.updateViewByApprovers(id, info);
		}
		//});
		me._initEvents();
		me._initNodeToolbar();
		me._initConditionToolbar();
		me._initSelection();
		me._initCtxMenu();

		me.repositionStartEnd();
	},
	getServerData: function(){
		var me = this;
		return me.serverData;
	},
	getNodeInfo: function(nid){
		var me = this;
		return me.currentData.nodes[nid];
	},
	getAllNodeInfo: function(){		
		var me = this;
		return me.currentData;
	},
	__initTextEditor: function(toolbar, opt){
		var me = this;
			var span = toolbar.find('span[act="name"]');
			toolbar.find('>a[class^="approval_"]').hide();
			var text = span.text();
			var ipt = span.html(
								'<input type="text" style="width: calc(100% - 50px);flow:left;">'+
								'<a class="grid_save" style="flow:left;"></a>'+
								'<a class="grid_delete" style="flow:left;"></a>'
								).removeClass('readonly').find('input');
			ipt.val(text).focus();
			span.find('a.grid_save').click(function(){
					var a = $(this);
					var val = $.trim(ipt.val());
					if(!opt.validate(val))return;
					span.find('*').off().remove();
					span.text(val).addClass('readonly').attr('title', val);
					toolbar.find('>a[class^="approval_"]').show();
					opt.saveClicked(val);
			});
			span.find('a.grid_delete').click(function(){
					var a = $(this);
					span.find('*').off().remove();
					span.text(text);
					toolbar.find('>a[class^="approval_"]').show();
					opt.cancelClicked(text);
			});
	},
	_initEvents: function(){
		var me = this;
		var elem = me.element;
		var opt = me.options;
		if(elem.data('node-events-inited'))return;

		//解决FF下，由于jsplumb的drag功能，鼠标无法滚动滚动条的问题
		if(me.isFF || me.isIE){
			elem.on('mouseover','.pers_container_inner', function(e){
				var div = $(this).closest('.userTask');
				me.instance.setDraggable(div.attr('id'), true);//打开拖拽
			});

			elem.on('mouseover','.set_approval_add', function(e){
				var div = $(this).closest('.userTask');
				me.instance.setDraggable(div.attr('id'), true);//打开拖拽
			});
			elem.on('mouseout','.pers_container_inner', function(e){
				var div = $(this).closest('.userTask');
				me.instance.setDraggable(div.attr('id'), false);//关闭拖拽
			});		
		}


		elem.data('node-events-inited', true);
	},
	_initNodeToolbar: function(){
		var me = this;
		var elem = me.element;
		var opt = me.options;
		if(elem.data('node-toolbar-inited'))return;
		//编辑name图标
		elem.on('click', '.rk-endpoint a.approval_edit', function(){
			var a = $(this);
			var nodeElem = a.closest('.rk-endpoint');
			var id = nodeElem.attr('id');
			var toolbar = a.closest('.approval_step_name');
			me.__initTextEditor(toolbar, {
				validate: function(val){
					var nodes = me.currentData.nodes;
					for(var nid in nodes){
						if(nid != id)
						if(val == nodes[nid].name) {
							ingage.common.errorResult(me.taskText + '名字已存在');
							return false;
						}
						if(!val){
							ingage.common.errorResult('名称不能为空');
							return false;							
						}
					}
					return true;
				},
				saveClicked: function(val){
					me.getNodeInfo(id).name = val;
				},
				cancelClicked: function(val){
					//me.getNodeInfo(id).name = val;
				}
			});
		});
		//菜单图标
		elem.on('click', '.rk-endpoint a.approval_set', function(){
			var a = $(this);
			var nodeElem = a.closest('.rk-endpoint');
			var id = nodeElem.attr('id');
			me._popupMenu(id);
		});
		elem.on('click', '.rk-goto-point', function(e){
			var target = $(e.currentTarget);
			me._popupMenu(target.attr('id'));
		});
		elem.data('node-toolbar-inited', true);
	},
	_initConditionToolbar: function(){
		var me = this;
		var elem = me.element;
		var opt = me.options;
		if(elem.data('condition-toolbar-inited'))return;
		elem.on('click', '.condition-lable-top a.approval_edit', function(){
			var a = $(this);
			var parentElem = a.closest('.condition-lable-top');
			var from = parentElem.attr('from');
			var to = parentElem.attr('to');
			var toolbar = a.closest('.approval_step_name');
			me.__initTextEditor(toolbar, {
				validate: function(val){
					// var nodes = me.currentData.nodes;
					// for(var id in nodes){
					// 	var n = nodes[id];
					// 	if(n.conditionMap)for(var cond in n.conditionMap){
					// 		var condName = n.conditionMap[cond].name;
					// 		if(val == condName) {
					// 			ingage.common.errorResult(me.taskText + '名字已存在');
					// 			return false;
					// 		}
					// 	}
					// }
					return true;
				},
				saveClicked: function(val){
					var nodeInfo = me.getNodeInfo(from);
					nodeInfo.conditionMap[to].name = val;
				},
				cancelClicked: function(val){
					//me.getNodeInfo(id).name = val;
				}
			});

		});
		elem.on('click', '.condition-lable-top a.approval_set', function(){
			var a = $(this);
			var parentElem = a.closest('.condition-lable-top');
			var from = parentElem.attr('from');
			var to = parentElem.attr('to');
			flowchartService.popupConditionEditor(from, to);
		});
		elem.on('mouseover', '.condition-lable-bottom a.step_cond_more', function(){
			var a = $(this);
			var parentElem = a.closest('.condition-lable-bottom');
			var from = parentElem.attr('from');
			var to = parentElem.attr('to');

			var conditions = me.getConditions(from, to);
			if(!conditions)return;

			window.clearTimeout(me.condViewerTimer)
			conditions = conditions.conditions;
			elem.find('>[id="approval_cond_viewer"]').remove();
			elem.append(template('flowchartConditionViewer', {
				conditions: conditions
			}));
			elem.find('>[id="approval_cond_viewer"]').css({
				top: parseInt(parentElem.css('top')) + a.position().top + a.height() + 'px',
				left: parseInt(parentElem.css('left')) + a.position().left + a.width() + 'px',
				zIndex: 9999
			}).on('mouseover', function(){
				window.clearTimeout(me.condViewerTimer)
			}).on('mouseout', function(){
				me.condViewerTimer = window.setTimeout(function(){
					elem.find('>[id="approval_cond_viewer"]').remove();
				}, 200);
			});
		}).on('mouseout', '.condition-lable-bottom a.step_cond_more', function(){
			me.condViewerTimer = window.setTimeout(function(){
				elem.find('>[id="approval_cond_viewer"]').remove();
			}, 200);
		});

		elem.data('condition-toolbar-inited', true);
	},
	cancelSelTimer: null,
	_initSelection: function(){
		var me = this;
		var elem = me.element;

		if(elem.data('select-inited'))return;
		elem.data('select-inited', true);
		elem.on('dblclick.sel', '.rk-endpoint:not(.startEvent):not(.endEvent)', function(e){
			var id = $(this).attr('id');
			if($(e.target).parents('.task_toolbar').size() > 0){
				return;
			}
			flowchartService.popupApproverEditor(id);
		});
		elem.on('click.sel', '.rk-endpoint:not(.startEvent):not(.endEvent)', function(e){
			var target = $(this);
			window.clearTimeout(me.cancelSelTimer);
			if( e.button != 2) {
				var id = target.attr('id');
				if(!e.ctrlKey){
					//取消全部
					elem.find('.rk-endpoint-selected').removeClass('rk-endpoint-selected');
					target.addClass('rk-endpoint-selected');
				}else{
					if(target.hasClass('rk-endpoint-selected')){
						target.removeClass('rk-endpoint-selected');
					}else{
						target.addClass('rk-endpoint-selected');						
					}
				}
			}
		}).on('mouseup.cancelsel', function(e){
			var target = $(e.srcElement);
			if(!target.hasClass('rk-endpoint') && target.parents('.rk-endpoint').size() == 0){
				me.cancelSelTimer = window.setTimeout(function(){
					elem.find('.rk-endpoint-selected').removeClass('rk-endpoint-selected');
				}, 100);
			}
		});
	},
	hasSibiling: function(id){
		var me = this;
		var info = me.getNodeInfo(id);
		var hasSibiling = false;
		if(info.prev && info.prev.length == 1){
			var p = me.getNodeInfo(info.prev[0]);
			if(p.next && p.next.length > 1) hasSibiling = true;
		}
		return hasSibiling;
	},
	getSiblings: function(id){
		var me = this;
		var info = me.getNodeInfo(id);
		var siblings = [];
		if(info.prev && info.prev.length == 1){
			var p = me.getNodeInfo(info.prev[0]);
			if(p.next){
				for(var i = 0, len = p.next.length; i < len; i++){
					var childid = p.next[i];
					if(childid != id){
						var copy = childid;
						siblings.push(copy);
					}
				}
			}
		}
		return siblings;
	},
	_initCtxMenu: function(){
		var me = this;
		var elem = me.element;
		var opt = me.options;
		if(opt.readonly) return;
		//document.oncontextmenu = function() {return false;};
		$(elem).on('contextmenu',  function() {return false;});

		elem.off('mousedown.ctxmenu', '.rk-endpoint')
			.on('mousedown.ctxmenu', '.rk-endpoint:not(.startEvent), .rk-goto-point', function(e){
			    if( e.button == 2 ) { 
			    	elem.find('#flowchartCtxMenu').flowChartCtxMenu('destroy').off().remove();
			    	var target = $(e.currentTarget);
			    	me._popupMenu(target.attr('id'));
			      	return false; 
			    } 
			    return true; 
			})
			.off('click.ctxmenu');			
		//全局监听菜单点击
		if(!elem.data('ctxmenu_clicking_inited')){
			$(document.body).off('click.ctxmenu').on('click.ctxmenu', function (e){
				var target = $(e.target);
				if(!target.parent().hasClass('approval_set')) me._closePopupMenu();
			})
			elem.on('click.ctxmenu_clicking', '#flowchartCtxMenu a[act]', function(e){
				var a = $(e.currentTarget);
				var ul = a.parents('ul[nid]:first');
				var act = a.attr('act');
				var nid = ul.attr('nid');
				var ntype = ul.attr('ntype');

				elem.find('#flowchartCtxMenu').flowChartCtxMenu('handleClick', act, ntype, nid);
			})
			elem.data('ctxmenu_clicking_inited', true);
		}
	},
	_closePopupMenu: function(){
		var me = this;
		var elem = me.element;
		elem.find('#flowchartCtxMenu').off().remove();
	},
	_popupMenu: function(id){
		var me = this;
		var elem = me.element;
		var opt = me.options;

		me._closePopupMenu();

		var getMenuType = function (nodeId){
			var info = me.getNodeInfo(nodeId);
			if(info){
				var hasSibiling = me.hasSibiling(nodeId);
				var menutype = info.type;
				if(opt.postponed) return 'postponed';
				if(hasSibiling) menutype = 'sibilingTask';
				if(nodeId == 'task0') menutype = 'onlyChild';		
				return menutype;		
			}else{
				return 'goto';
			}
		};

		var canvasId = elem.attr('id');
		var target = me.getNodeElem(id);//elem.find('>[id="'+id+'"]');
		var nid = id;
		var ninfo = me.getNodeInfo(id); ninfo = ninfo ? ninfo : {};//可能是goto的点，无info
		var ntype = target.attr('type');
		var menutype = getMenuType(nid);
		if(menutype == 'endEvent')return;
		    var menuhtml = template('flowchartCtxMenuTpl', {
		    	nid: nid,
		    	ntype: ntype,
		    	menutype: menutype,
		    	hasGoto: !!ninfo.gotoTargetId
		    });
		    elem.append(menuhtml);
		    var menu = $('#flowchartCtxMenu');
		    if(menu.find('.list_item li').size() == 0){
		    	menu.remove();
		    }else{
		    	menu.show();
		    }
		    var top = parseInt(target.css('top'));
		    var left = parseInt(target.css('left'));
		    elem.find('#flowchartCtxMenu').css({
		    	//top: (top + target.outerHeight()) + 'px',
		    	top: (top + 0) + 'px',
		    	left: (left + target.outerWidth()) + 'px'
		    }).flowChartCtxMenu({
		    	canvasId: canvasId
		    });
	},
	_initCanvas: function(){
		var me = this;
		var elem = me.element;
		var opt = me.options;

		var canvasId = elem.attr('id');

	    // setup some defaults for jsPlumb.
	    me.instance = jsPlumb.getInstance({
	        Endpoint: [
	                    "Dot", {
	                        radius: 6
	                	}],
	        HoverPaintStyle: {
	                    strokeStyle: "#1e8151", 
	                    lineWidth: 1
	                },
	        ConnectionOverlays: [
	            [ "Arrow", {
	                width: 10,
	                location: 1,
	                id: "arrow",
	                length: 7,
	                foldback: 0.8,
	                length:10
	            } ]
	            // ,[ "Label", {
	            //     label:"分支1",
	            //     cssClass: "aLabel"
	            // }]
	        ],
	        Container: canvasId
	    });

	},
	_deleteEndpoint:function(id, position){
		var me = this;
	    me.instance.deleteEndpoint(id + position);
	    me.instance.remove(id + position);
	},
	_addEndpoints: function (toId, sourceAnchors, targetAnchors, conf) {
		var me = this;

		if(typeof conf == 'undefined')conf = {};

	    // this is the paint style for the connecting lines..
	    var connectorPaintStyle = {
	            lineWidth: 0,//连线宽度
	            strokeStyle: me.lineColor,
	            joinstyle: "round",
	            outlineColor: me.lineColor,
	            outlineWidth: 1
	        },
	    // .. and this is the hover style.
	        connectorHoverStyle = {//连线的鼠标滑过样式
	            lineWidth: 1,
	            strokeStyle: me.lineHoverColor,
	            outlineWidth: 1,
	            outlineColor: me.lineHoverColor
	        },
	        endpointHoverStyle = {
	            // fillStyle: "#216477",
	            // strokeStyle: "#216477"
	        },
	    // the definition of source endpoints (the small blue ones)
	        sourceEndpoint = {
	            endpoint: "Blank",//"Dot",
	            paintStyle: {
	                strokeStyle: "green",
	                fillStyle: "transparent",
	                radius: 2,
	                lineWidth: 1
	            },
	            maxConnections: -1,
	            isSource: true,
	            //直连，connector: [ "Straight", { stub: [40, 60], gap: 10, cornerRadius: 5, alwaysRespectStubs: true } ],
	            connector: ["Flowchart", {
	                gap: 0, 
	                stub: [10, 10],
	                cornerRadius: 3, 
	                alwaysRespectStubs: false,
	                cornerRadius: 7,//转角弧度
	                midpoint: (conf.midpoint ? conf.midpoint : 0.05) //拐点
	            }],
	            connectorStyle: connectorPaintStyle,
	            hoverPaintStyle: endpointHoverStyle,
	            connectorHoverStyle: connectorHoverStyle,
	            dragOptions: {},
	            overlays: [
	                [ "Label", {
	                    location: [0.5, 1.5],
	                    label: "",
	                    cssClass: "endpointSourceLabel"
	                } ]
	            ]
	        },
	    // the definition of target endpoints (will appear when the user drags a connection)
	        targetEndpoint = {
	            endpoint: "Blank",//"Dot",
	            //uniqueEndpoint: false,
	            paintStyle: { 
	                fillStyle: "red",
	                radius: 2
	            },
	            hoverPaintStyle: endpointHoverStyle,
	            maxConnections: -1,
	            dropOptions: { 
	                hoverClass: "hover", 
	                activeClass: "active" 
	            },
	            isTarget: true,
	            overlays: [
	                [ "Label", {
	                    location: [0.5, -0.5], 
	                    label: "", 
	                    cssClass: "" 
	                } ]
	            ]
	        }
	        if(typeof sourceAnchors != 'undefined')
	        for (var i = 0; i < sourceAnchors.length; i++) {
	            var sourceUUID = toId + sourceAnchors[i];
	            me.instance.deleteEndpoint(sourceUUID);

	            var info = me.getNodeInfo(toId);
	            if(info && info.next){
	            	var nextid = info.next[0];
	            	var nextInfo = me.getNodeInfo(nextid);
		            if(nextInfo.type == 'exclusiveGateway'){
		            	sourceEndpoint.connector[1].midpoint = 0.95;//接入网关的线，拐点为0.9
		            }else{
		            	sourceEndpoint.connector[1].midpoint = 0.05;
		            }	            	
	            }
	            me.instance.addEndpoint(toId, sourceEndpoint, { anchor: sourceAnchors[i], uuid: sourceUUID });
	        }
	        if(typeof targetAnchors != 'undefined')
	        for (var j = 0; j < targetAnchors.length; j++) {
	            var targetUUID = toId + targetAnchors[j];
	            me.instance.deleteEndpoint(targetUUID);
	            me.instance.addEndpoint(toId, targetEndpoint, { anchor: targetAnchors[j], uuid: targetUUID });
	        }
	    },
	    _tempNodesForGANBM: {},
	    getAllNodesBehindMe: function(id, idlist){ //给定两个id，获得在其之间的所有id
	    	var me = this;
	    	if(id == 'end')return [];
	    	if(typeof idlist == 'undefined') {
	    		me._tempNodesForGANBM = me._deepClone(me.currentData.nodes);
	    		idlist = [];//初始化缓存list
	    	}
	    	var nodeInfo = me._tempNodesForGANBM[id];
	    	if(nodeInfo.visited)return;
	    	var nextIds = nodeInfo.next;
	    	nodeInfo.visited = true;
	    	for(var i = 0; i < nextIds.length; i++){
	    		var nextid = nextIds[i];
	    		idlist.push(nextid);
	    		me.getAllNodesBehindMe(nextid, idlist);
	    	}
	    	return _.uniq(idlist);
	    },
	    getCascadingNextList2: function(beginId, finishId, idlist){ //给定两个id，获得在其之间的所有id
	    	var me = this;
	    	if(typeof finishId == 'undefined') finishId = 'end';
	    	if(beginId == 'end')return [];
	    	if(beginId == finishId)return [];
	    	if(typeof idlist == 'undefined') idlist = [];//初始化缓存list
	    	var beginInfo = me.getNodeInfo(beginId);
	    	if(!beginInfo)return [];//可能是goto
	    	var nextIds = me.getNodeInfo(beginId).next;
	    	for(var i = 0; i < nextIds.length; i++){
	    		var nextid = nextIds[i];
	    		if(nextid == finishId)return idlist;//结束
	    		idlist.push(nextid);
	    		me.getCascadingNextList(nextid, finishId, idlist);
	    	}
	    	return _.uniq(idlist);
	    },
	    tempNodes: null,
	    getCascadingNextList: function(beginId, finishId){
	    	//策略：
	    	//1，先从finishId开始逆向查找，砍掉所有不含自己的分支
	    	//2，再从beginId正向查找，所得之id即为他们两个之间的id
	    	var me = this;
	    	if(typeof finishId == 'undefined') finishId = 'end';
	    	if(beginId == 'end')return [];
	    	if(beginId == finishId)return [];
	    	var beginInfo = me.getNodeInfo(beginId);
	    	if(!beginInfo)return [];//可能是goto
	    	if(_.contains(beginInfo.next, finishId))return [];
	    	me.tempNodes = me._deepClone(me.currentData.nodes);
	    	me.tempNodes[finishId].next = [];//断掉后路
	    	me.tempNodes[beginId].prev = [];//不必再向上追朔
	    	me._haveMidNodes = false;//两点之间是否存在共同节点
	    	me.cutBranch(finishId, beginId);//砍掉nodes中，所有跟自己不相干的分支
	    	if(!me._haveMidNodes)return [];
	    	var allInnerIds = [];
	    	me.__doGetCascading(beginId, allInnerIds);
	    	allInnerIds = _.uniq(allInnerIds);
	    	allInnerIds = _.without(allInnerIds, beginId, finishId);
	    	//me.highlight(allInnerIds);
	    	return allInnerIds;
	    },	    
	    __doGetCascading: function(id, idlist){
	    	var me = this;
	    	var info = me.tempNodes[id];
	    	if(info.visited)return;
	    	info.visited = true;
			var nexts = info.next;
			if(nexts)
	    	for(var i = 0; i < nexts.length; i++){
	    		var nextid = nexts[i];
	    		if(nextid){
	    			idlist.push(nextid);
	    			me.__doGetCascading(nextid, idlist);
	    		}
	    	}
	    },
	    cutBranch: function(id, beginId){
	    	var me = this;
	    	var node = me.tempNodes[id];
	    	if(node.prev){
	    		var prevs = node.prev;
	    		var prevId;
	    		var prevNode;
	    		if(!node.prevVisited)
		    	for(var i = 0, len = prevs.length; i < len; i++){
		    		prevId = prevs[i];
		    		if(prevId == beginId)me._haveMidNodes = true;
		    		prevNode = me.tempNodes[prevId];
		    		if(!prevNode.cutted && prevNode.next.length > 1){
		    			prevNode.cutted = true;//已经被兄弟节点砍过分支了，后来的分支节点不要再重复砍掉分支
		    			var deleteIds = _.without(prevNode.next, id);
		    			prevNode.next = [id];//砍掉其他不含自己的分支
		    			me.cutBranch(prevId, beginId);
		    		}else{
		    			prevNode.next.push(id);//可能被先到的兄弟节点误砍掉了，再补上。如果是真需要砍掉的next，则不会有后续节点做这种补id的动作，因为没有回路走到这里
		    			prevNode.next = _.uniq(prevNode.next);
		    			me.cutBranch(prevId, beginId);
		    		}
		    	}
		    	node.prevVisited = true;
	    	}
	    },
	    getNearestCommonChildId: function(idList){ //传入一些点的id，给出距离他们最近的共有孩子id
	    	var me = this;
	    	var commonChildrenList = me.getCommonChildIdList(idList);
	    	var oneOfThem = idList[0];
	    	var nearestId = null;
	    	var minDist = 999999999;
	    	for(var i = 0; i < commonChildrenList.length; i++){
	    		var id = commonChildrenList[i];
	    		var distance = me.getDistance(oneOfThem, id);
	    		if(distance < minDist){
	    			minDist = distance;
	    			nearestId = id;
	    		}
	    	}
	    	if(!nearestId) nearestId = 'end';
	    	return nearestId;
	    },
	    getCommonChildIdList: function(idList){ //传入一些点的id，给出他们的共有孩子id
	    	var me = this;
	    	var array = [];
	    	for(var i = 0; i < idList.length; i++){
	    		var id = idList[i];
	    		array.push( me.getCascadingNextList(id, 'end') );
	    	}
	    	return _.intersection.apply(_, array);
	    },
	    getDistance: function(beginId, finishId){ //返回两点之间的距离
	    	var me = this;
	    	var cascading = me.getCascadingNextList(beginId, finishId);
	    	return cascading.length;
	    },
	    isInBetween: function(id, startId, endId){//判断给定id是否在指定id之间
	    	var me = this;
	    	var commonChildren = me.getCascadingNextList(startId, endId);
	    	return _.contains(commonChildren, id);
	    },
	    repaintTimeout: 10,
	    repaintTimer: null,
	    repaint: function(succ){
	    	var me = this;
	    	var elem = me.element;
	    	window.clearTimeout(me.repaintTimer);
	    	me.repaintTimer = window.setTimeout(function(){
	    		var startTop = me.getNodeInfo('start').top;
	    		var highestTop = 99999999999;
	    		var lowestTop = -1 * highestTop;
	    		var epoints = elem.find('>.rk-endpoint, >.rk-goto-point');
	    		epoints.each(function (){ 
	    			var n = $(this);
	    			var ninfo = me.getNodeInfo(n.attr('id'));
	    			var top = parseInt(n.css('top'));
	    			var width = n.outerWidth();
	    			var height = n.outerHeight();
	    			if(ninfo){
	    				ninfo.width = width;
	    				ninfo.height = height;
	    			}
	    			if(highestTop > top){
	    				highestTop = top;
	    			}
	    			if(lowestTop < top){
	    				lowestTop = top;
	    			}
	    		});
	    		if(highestTop < 0 || highestTop > 60){
					epoints.each(function (){ 
		    			var n = $(this);
		    			var id = n.attr('id');
		    			var info = me.getNodeInfo(id);
		    			var top = parseInt(n.css('top'));
		    			var newTop = top - highestTop + 60;
		    			n.css('top', newTop + 'px');
		    			if(info)info.top = newTop;
		    		});
	    		}
	    		if(me.getNodeInfo('task0').next[0] != 'end'){
	    			// var startInfo = me.getNodeInfo('start');
	    			// var top_h = 99999999999999;
	    			// var tp_low = 0;
	    			// for(var i = 0, len = startInfo.next.length; i < len; i++){
	    			// 	var top = me.getNodeInfo(startInfo.next[i]).top;
	    			// 	if(top > tp) tp = top;
	    			// }
	    			//开始节点不能低于所有节点
	    			var x = me.getX('task0');
	    			var y = me.getY('task0');
	    			me.setX('start', x - me.getNodeElem('task0').width() + 10);
	    			me.setY('start', y);
	    			//me.setTop('start', highestTop + Math.abs(highestTop - lowestTop) / 2);
	    		}
	    		me.instance.repaintEverything();
	    		me.showConditionLabel();
	    		me.updateAllViewByConditions();
	    		me.allNodeElems = epoints;
	    		if(typeof succ == 'function')(succ)();
	    	}, me.repaintTimeout);
	    },
	    allNodeElems: null,
	    connect: function(from, to, conf){
	    	var me = this;	

	    	me.__connect(from, to, true, conf);
	    	me.repaint();
	    },
	    updateConnects: function(from){
	    	var me = this;
	    	var fromInfo = me.getNodeInfo(from);
	    	var oldFromInfo = JSON.parse(JSON.stringify(fromInfo))
	    	$(oldFromInfo.next).each(function(i, toid){
	    		me.disconnect(from, toid);
	    		if(fromInfo.gotoTargetId)me.disconnect(from, from+'-goto-'+fromInfo.gotoTargetId);
	    	});
	    	me._deleteEndpoint(from, 'RightMiddle');
	    	// //更新form和to的next，prev
	    	fromInfo = oldFromInfo;
	    	me.currentData.nodes[from] = oldFromInfo
	    	me._addEndpoints(from, ['RightMiddle']);
	    	$(fromInfo.next).each(function(i, toid){
	    		me.connect(from, toid);
	    		if(fromInfo.gotoTargetId)me.connect(from, from+'-goto-'+fromInfo.gotoTargetId);
	    	});
	    	window.setTimeout(function(){
	    		$(fromInfo.next).each(function(i, to){
	    			var tocond = fromInfo.conditionMap ? fromInfo.conditionMap[to] : null;
	    			var conditions = tocond ? tocond.conditions : null;
	    			if(conditions)
	    			me.updateViewByConditions(from, to, conditions);
	    		});
	    	}, me.repaintTimeout + 50);
	    },
	    disconnect: function(from, to){
	    	var me = this;

	    	var connList = me.instance.getConnections(); 
	    	for(var i = 0, len = connList.length; i < len; i++){
	    		var conn = connList[i];
	    		var source = $(conn.source);
	    		var target = $(conn.target);
	    		if(source.attr('id') == from && target.attr('id') == to){
	    			me.instance.detach(conn);
	    			me.removeConnCache(from, to);
	    			//更新next和prev关系
	    			var fromNode = me.getNodeInfo(from);
	    			var toNode = me.getNodeInfo(to);
	    			if(fromNode.next)fromNode.next = _.without(fromNode.next, to);
	    			if(toNode && toNode.prev)toNode.prev = _.without(toNode.prev, from);
	    			//break;
	    		}
	    	}
	    	me.delConditionLabel(from, to);
	    },
	    _cleanAllJPlumbEndPoints: function(){//清掉所有连接点
			var me = this;
			var elem = me.element;
			elem.css('visibility', 'hidden');
	    	me.instance.deleteEveryEndpoint();
	    	me.instance.removeAllEndpoints ('[id]');
	    	elem.css('visibility', 'visible');
	    },
	    _cleanJPlumbEndPoints: function(id, type){//清掉连接点
			var me = this;
			var elem = me.element;
			elem.css('visibility', 'hidden');
			if(typeof type == 'undefined') type == 'SOMETHING';

	    	me.instance.deleteEndpoint(id + 'RightMiddle')
	    	me.instance.deleteEndpoint(id + 'LeftMiddle');
	    	me.instance.remove(id + 'RightMiddle')
	    	me.instance.remove(id + 'LeftMiddle');

	    	if(type != 'goto'){
		    	me.instance.deleteEndpoint(id + 'BottomCenter');
		    	me.instance.deleteEndpoint(id + 'TopCenter');
		    	me.instance.remove(id + 'BottomCenter');
		    	me.instance.remove(id + 'TopCenter');
	    	}

	    	me.instance.remove(id);
	    	elem.css('visibility', 'visible');	    	
	    },
	    deleteNode: function(id){
			var me = this;
			var elem = me.element;
			var opt = me.options;

			var nodeInfo = me.getNodeInfo(id);
			if(!nodeInfo)return;
			if(id == 'task0')return;

			var parentId = nodeInfo.prev[0];
			var nexts = nodeInfo.next;

			//if(nodeInfo.type == 'userTask')
			me._removeGotoIfHas(id);

			var updateCondition = [];

			var hasChildren = nodeInfo.next.length > 1;

			if(nodeInfo.type == 'userTask'){
				var parentInfo = me.getNodeInfo(nodeInfo.prev[0]);
				var nextInfo = me.getNodeInfo(nodeInfo.next[0]);
				if(nextInfo.type == 'userTask')
				if(parentInfo.next && parentInfo.next.length > 1){//如果是一个并列节点，与前面的网关之间绑有条件，且next后有孩子，则删除自己后其孩子应继承其与网关之间的条件
					var conditionMap = me.getNodeInfo(parentInfo.id).conditionMap;
					conditionMap = conditionMap ? conditionMap : {};
					var nextId = nodeInfo.next[0];
					conditionMap[nextId] = me._deepClone(conditionMap[id]);					
					delete conditionMap[id];
					parentInfo.conditionMap = conditionMap;
					me.currentData.nodes[parentInfo.id] = parentInfo;
					updateCondition.push({
						from: parentInfo.id,
						to: nextId,
						conditions: conditionMap[nextId] ? conditionMap[nextId].conditions : []
					});
				}
				me.delConditionLabel(parentInfo.id, id);
				//
				if(nodeInfo.next && nodeInfo.next.length > 1){
					if(nodeInfo.conditionMap){//如果自己是网关，那么删掉自己后，其前面的父亲应当继承分支条件				
						var pId = nodeInfo.prev[0];
						var pInfo = me.getNodeInfo(pId);
						if(!pInfo.conditionMap) pInfo.conditionMap = {};
						delete pInfo.conditionMap[id];
						pInfo.conditionMap = $.extend(true, pInfo.conditionMap, nodeInfo.conditionMap);
						me.currentData.nodes[pId] = pInfo;
						$(nodeInfo.next).each(function(j, nextId){
							updateCondition.push({
								from: pId,
								to: nextId,
								conditions: pInfo.conditionMap[nextId].conditions
							});
						})
					}
					for(var i = 0, len = nodeInfo.next.length; i < len; i++){
						me.delConditionLabel(nodeInfo.id, nodeInfo.next[i]);//删掉多余的条件标签
					}
				}
			}
			if(nodeInfo.prev && nodeInfo.prev.length > 1){
				for(var i = 0, len = nodeInfo.prev.length; i < len; i++){
					me.delConditionLabel(nodeInfo.prev[i], nodeInfo.id);//删掉多余的条件标签
				}
			}
			if(!hasChildren && me.getSiblings(id).length == 1){//只有一个兄弟节点时，删掉自己后应当清掉其条件，注意，如果自己有孩子的话，不能做，因为孩子会合并到父节点上
				me.delConditionLabel(nodeInfo.prev[0], me.getSiblings(id)[0]);//删掉多余的条件标签
			}
			//删除的是前置网关，需要将条件保留下来
			// if(nodeInfo.type == 'exclusiveGateway' && nodeInfo.next.length > 1 && nodeInfo.prev.length == 1 && me.getNodeInfo(nodeInfo.prev[0]).type == 'userTask'){
			// 	var prevId = nodeInfo.prev[0];
			// 	elem.find('>[id="'+prevId+'"]').data(elem.find('[id="'+id+'"]').data('conditionMap'));
			// 	me.getNodeInfo(nodeInfo.prev[0]).conditionMap = nodeInfo.conditionMap;
			// }

			if(nexts)
			for(var i = 0; i < nexts.length; i++){
				me.disconnect(id, nexts[i]);
			}
			var prevs = nodeInfo.prev;
			if(prevs)
			for(var i = 0; i < prevs.length; i++){
				me.disconnect(prevs[i], id);
			}

			me.getNodeElem(id).off().remove();
			delete me.currentData.nodes[id];

	    	me._cleanJPlumbEndPoints(id);

			nexts = _.uniq(nexts);
			prevs = _.uniq(prevs);

			//是后置网关
			if(nexts && prevs && prevs.length == 1){
				var prevId = prevs[0];
				var prevInfo = me.currentData.nodes[prevId];
				if(nexts.length == 1){
					var nextId = nexts[0];
					if(me.getNodeInfo(nextId).type != 'exclusiveGateway'){//之前的节点没孩子了（断线了），需重连
						me.__connect(prevId, nextId, true);
					}else{
						var commonChildren = me.getCascadingNextList(prevId, nextId);
						if(commonChildren.length == 0){//如果next是网关，且prev和网关之间没有共有孩子，则应当把他们链接起来，至于网关是否存在，则是在ctxMenu中后续处理之
							me.__connect(prevId, nextId, true);
						}
					}
				}
				if(nexts.length > 1){
					for(var i = 0, len = nexts.length; i < len; i++){
						var nid = nexts[i];
						me.__connect(prevId, nid, true);
					}
				}
			}
			//是前置网关
			if(nexts && prevs && prevs.length > 1 && nexts.length == 1){
				var nextId = nexts[0];
				var nextInfo = me.currentData.nodes[nextId];

				if(prevs.length > 1){
					for(var i = 0, len = prevs.length; i < len; i++){
						var nid = prevs[i];
						me.__connect(nid, nextId, true, {
							targetPoint : "LeftMiddle"
						});
					}
				}
			}
			me.cleanLonelyGate();
			me._updateCurrentTransitions();
			me.repaint(function(){
				var pinfo = me.getNodeInfo(parentId);
				$(pinfo.next).each(function(i, nid){
					updateCondition.push({
						from: parentId,
						to: nid
					});
				});
				if(updateCondition.length > 0){
					$(updateCondition).each(function(i, cond){
						var from = cond.from;
						var to = cond.to;
						var conditions = cond.conditions;
						var map = me.getNodeInfo(from).conditionMap;
						if(!conditions && map && map[to]) conditions = map[to].conditions;
						if(conditions)
						me.updateViewByConditions(from, to, conditions)
					});					
				}
				me.updateConnects(parentId);
			});
			//名称计数器清零
			if(me.isDefault()){
				me.tasksNameCount = 1;
				me.gatesNameCount = 1;
				me.condsNameCount = 1;
			}
	    	var haveBranch = true;
	    	var nodes = me.currentData.nodes;
	    	for(var id in nodes){
	    		if(nodes[id].next && nodes[id].next.length > 1){
	    			haveBranch = true;
	    			break;
	    		} 
	    	}
	    	if(!haveBranch) me.condsNameCount = 1;
	    },
	    getNodeElems: function(){
			var me = this;
			var elem = me.element;
	    	return elem.find('>div.rk-endpoint');
	    },
	    getNodeElem: function(id){
			var me = this;
			var elem = me.element;
			//this.allNodeElems ? this.allNodeElems.filter('[id="'+id+'"]') : 
	    	return $(elem).find('>div[id="'+id+'"]');
	    },
	    movedNodeIds: {},//cache
	    stepAWay: function(id, offset, offset2){//某id节点之后的节点向后挪动
			var me = this;
			var elem = me.element;
			var opt = me.options;

			var info = me.getNodeInfo(id);
			var room;
			if(offset == 'reverse') {
				room = info.type == 'exclusiveGateway' ? opt.gateWidth : opt.taskWidth;
			}else{
				room = info.type == 'exclusiveGateway' ? opt.gateRoom : opt.taskRoom;
			}

	    	if(typeof offset == 'undefined') offset = room;
	    	if(offset == 'reverse') {
	    		if(typeof offset2 == 'undefined'){
	    			offset = -1 * room;
	    		}else{ 
	    			offset = -1 * offset2;
	    		}
	    	}
			var me = this;
			var elem = me.element;
			var opt = me.options;

			var list = me.getCascadingNextList(id, 'end');
			list.push('end');
			var nodeElems = me.getNodeElems();
			for(var i = 0, len = list.length; i < len; i++){
				var nid = list[i];
				var nEl = nodeElems.filter('[id="'+nid+'"]');//me.getNodeElem(nid);
				var nInf = me.getNodeInfo(nid);
				var x = me.getX(nid);
				x = x + offset;
				me.setX(nid, x);
			}
			me.repaint();
	    },
	    stepASide: function(dir, id, offset, reverse, option){//移动所有的top或left
			var me = this;
			var elem = me.element;
			var opt = me.options;

			if(typeof option == 'undefined') option = {
				ignorInBetween: false//如果点落在自己和end之间，则不予处理
			};
			if(typeof reverse == 'undefined') reverse = false;

			var points = elem.find('>div.rk-endpoint, >div.rk-goto-point');

			var myNode = points.filter('[id="'+id+'"]');
			var myLeft = me.getLeft(id);
			var myTop = me.getTop(id);

			var myX = me.getX(id);
			var myY = me.getY(id);

			if(dir == 'x'){
				points.each(function(){
					var point = $(this);
					var pid = point.attr('id');
					var nx = me.getX(pid);
					if(pid != id && (!reverse ? (nx > myX) : (nx < myX))){
						nx = nx + offset;
						me.setX(pid, nx);
					}
				});
			}
			if(dir == 'y'){
				points.each(function(){
					var point = $(this);
					var pid = point.attr('id');
					var ny = me.getY(pid);
					if(option.ignorInBetween && me.isInBetween(pid, id, 'end')) return true;
					if(pid != id && (!reverse ? (ny < myY) : (ny > myY))){
						ny = ny - offset;
						me.setY(pid, ny);
					}
				});			
			}
			me.repaint()
	    },
	    setLeft: function(id, left){
	    	var me = this;
			var el = me.getNodeElem(id);
			var info = me.getNodeInfo(id);
			info.left = left;
			el.css('left', left + 'px');
	    },
	    getLeft: function(id){
	    	var me = this;
			//var el = me.getNodeElem(id);
			var info = me.getNodeInfo(id);
			return info.left;
	    },
	    setTop: function(id, top){
	    	var me = this;
			var el = me.getNodeElem(id);
			var info = me.getNodeInfo(id);
			info.top = top;
			el.css('top', top + 'px');
	    },
	    getTop: function(id){
	    	var me = this;
			//var el = me.getNodeElem(id);
			var info = me.getNodeInfo(id);
			return info.top;
	    },
	    setLeftTop: function(id, left, top){
	    	var me = this;
	    	me.setLeft(id, left);
	    	me.setTop(id, top);
	    },
	    appendNextId: function(id, value){
	    	var me = this;
	    	var ninfo = me.getNodeInfo(id);
			if(ninfo.next){
				ninfo.next.push(value);
				ninfo.next = _.uniq(ninfo.next);
			}else{
				ninfo.next = [value];
			}
	    },
	    appendPrevId: function(id, value){
	    	var me = this;
	    	var ninfo = me.getNodeInfo(id);
	    	if(ninfo)
			if(ninfo.prev){
				ninfo.prev.push(value);
				_.uniq(ninfo.prev);
			}else{
				ninfo.prev = [value];
			}
	    },
	    printNodeInfo: function(id){
	    	var me = this;
	    	var info = me.getNodeInfo(id);
	    	console.log(info.prev, info.next);
	    },
	    isLonelyGate: function(id){//是否为名存实亡的网关，只有一个节点接入
	    	var me = this;
	    	var nodes = me.currentData.nodes;
	    	var count = 0;
	    	var nodeInfo = me.getNodeInfo(id);
	    	var prevIsOne = false;
	    	var nextIsOne = false;
	    	if(nodeInfo.next && nodeInfo.next.length ==1) prevIsOne = true;
	    	for(var nid in nodes){
	    		var ninfo = me.getNodeInfo(nid);
	    		if(ninfo.next && _.contains(ninfo.next, id))count++
	    	}
	    	nextIsOne = !(count > 1);

	    	return prevIsOne && nextIsOne;
	    },
	    cleanLonelyGate: function(){
	    	var me = this;
	    	var nodes = me.currentData.nodes;
	    	var delArr = [];
	    	var delArr2 = [];
	    	for(var id in nodes){
	    		var n = me.getNodeInfo(id);
	    		//删掉左右都只有一个孩子的网关
	    		if(n.type == 'exclusiveGateway' && me.isLonelyGate(id)){
	    			delArr.push(id);
	    		}
	    		//如果网关后面有一个前置网关，则删掉自己，继承前置网关
	    		if(n.type == 'exclusiveGateway' && n.next && n.next.length == 1){
		    		var nextInfo = me.getNodeInfo(n.next[0]);
		    		if(nextInfo.type == 'exclusiveGateway' && nextInfo.next.length > 1){
		    			delArr2.push(n.id);
		    		}
	    		}
	    		if(n.type == 'exclusiveGateway'){
	    		}
	    		//如果一个前置网关，next有孩子，prev是一个taskNode的话，这个网关也没必要留着
	    		//{"id":"gate0","type":"exclusiveGateway","name":"gate0","prev":["task1"],"next":["task3","task4"],"left":610,"top":242.85}
	    		if(n.type == 'exclusiveGateway' && n.next && n.next.length > 1 && n.prev && n.prev.length == 1 && me.getNodeInfo(n.prev[0]).type =='userTask'){
		    		delArr2.push(n.id);
	    		}
	    	}
	    	for(var i = 0, len = delArr.length; i < len; i++){
	    		me.deleteNode(delArr[i]);
	    	}
	    	for(var i = 0, len = delArr2.length; i < len; i++){
	    		me.deleteNode(delArr2[i]);
	    	}
	    },
	    getMostTopNode: function(){
	    	var me = this;
	    	var nodes = me.currentData.nodes;
	    	var toppestNode;
	    	var toppest = Number.MAX_VALUE;
	    	for(var id in nodes){
	    		var node = nodes[id];
	    		if(toppest >= node.top){
	    			toppest = node.top;
	    			toppestNode = node;
	    		}
	    	}
	    	return toppestNode;
	    },
	    getMostBottomNode: function(){
	    	var me = this;
	    	var nodes = me.currentData.nodes;
	    	var lowestNode;
	    	var lowest = 0;
	    	for(var id in nodes){
	    		var node = nodes[id];
	    		if(node.top >= lowest){
	    			lowest = node.top;
	    			lowestNode = node;
	    		}
	    	}
	    	return lowestNode;
	    },
	    getBranchLines: function(){//branch line的意思是分支线，一条line可能由多个节点组成
	    	var me = this;
	    	var nodes = me.currentData.nodes;
	    	var lines = [];
	    	for(var id in nodes){
	    		var node = nodes[id];
	    		var nexts = node.next;
	    		if(nexts && nexts.length > 1){
	    			var startId = id;//line的起点一定是多孩子的节点，结束点是网关
	    			var endId = me.getNearestCommonChildId(nexts);
	    			for(var i = 0; i < nexts.length; i++){
	    				var childid = nexts[i];
	    				var newLine = [startId, childid];
	    				var midnodes = me.getCascadingNextList(childid, endId);
	    				newLine = newLine.concat(midnodes);
	    				newLine.push(endId);
	    				lines.push(newLine);
	    			}
	    		}
	    	}
	    	return lines;
	    },
	    //goto节点
	    addGotoPoint: function(from, to, info){
	    	var me = this;
	    	var elem = me.element;
	    	var opt = me.options;

	    	var canvasId = elem.attr('id');
	    	var pid = from + '-goto-' + to;
	    	info.id = pid;
	    	info.type = 'goto';
	    	info.isReadonly = opt.readonly;
	    	var html = template('flowchartGotoMidTypeTpl', info)//'<div class="rk-goto-point" id="'+pid+'" style="left:'+info.left+'px;top:'+info.top+'px;" type="goto"></div>';
	    	elem.append(html);
			me._addEndpoints( pid, ["RightMiddle"] , ["LeftMiddle"], {midpoint: 0.999});

			var connLeft = me.instance.connect({ 
												uuids: [ from + "RightMiddle",  pid + "LeftMiddle"], 
												editable: false, 
												detachable: false
											});
			var connRight = me.instance.connect({ 
												uuids: [ pid + "RightMiddle",  to + "LeftMiddle"], 
												editable: false, 
												detachable: false
											});
			var area = jsPlumb.getSelector("#"+canvasId+" .rk-goto-point");
			me.instance.draggable(area, { 
				grid: me.draggrid, 
				drag: function(){
					window.clearTimeout(me.cancelSelTimer);
				},
				stop: function( event, ui ) {
					window.clearTimeout(me.cancelSelTimer);
					me.repaint();
				} 
			});

	    },
	    _removeGotoIfHas: function(id){
	    	var me = this;
	    	var elem = me.element;

	    	var info = me.getNodeInfo(id);
	    	//若是删头
	    	var from = id;
	    	var to = info.gotoTargetId;	    	
	    	if(to){
	    		me.delGotoPoint(from, to);
	    	}
	    	//若是删尾
	    	var from = info.gotoSourceId;
	    	var to = id;	    	
	    	if(from){
	    		for(var i = 0, len = from.length; i < len; i++) me.delGotoPoint(from[i], to);
	    	}
	    	me.repaint();
	    },
	    __cutoff: function(from, to){
	    	var me = this;
	    	var elem = me.element;
			var connList = me.instance.getConnections(); 
		    for(var i = 0, len = connList.length; i < len; i++){
		    	var conn = connList[i];
		    	var source = $(conn.source);
		    	var target = $(conn.target);
		    	if(source.attr('id') == from && target.attr('id') == to){
		    		me.instance.detach(conn);
		    	}
		    }
	    },
	    delGotoPoint: function(from, to){
	    	var me = this;
	    	var elem = me.element;

	    	var pid = from + '-goto-' + to;
	    	me.__cutoff(from, pid);
	    	me.__cutoff(pid, to);
	    	
	    	elem.find('>div[class*="rk-goto-point"][id="'+pid+'"]').off().remove();	
	    	me._cleanJPlumbEndPoints(pid, 'goto');

	    	var fromInfo = me.getNodeInfo(from);
	    	var toInfo = me.getNodeInfo(to);
			delete fromInfo.gotoTargetId;
			delete toInfo.gotoSourceId;

	    	me.repaint();
	    },
	    _renderGoto: function(gotos){
	    	if(!gotos)return;
	    	var me = this;
	    	var elem = me.element;
	    	for(var i = 0, len = gotos.length; i < len; i++){
	    		var info = gotos[i];
		    	me.addGotoPoint(info.from, info.to, {
					left: info.left,
					top: info.top 
				});
	    	}

	    },
	    getSelectedIds: function(){
	    	var me = this;
	    	var elem = me.element;

	    	var selected = elem.find('>.rk-endpoint-selected');
	    	var ids = [];
	    	selected.each(function(){
	    		ids.push($(this).attr('id'));
	    	});
	    	return ids;
	    },
	    updateViewByApprovers: function(nodeId){//节点变为人员列表，而非头像
	    	var me = this;
	    	var elem = me.element;
	    	var opt = me.options;

			var nodeInfo = me.getNodeInfo(nodeId);
			if(nodeInfo.type != 'userTask') return;

	    	var nodeEl = elem.find('>[id="'+nodeId+'"]');
	    	var approvers = nodeInfo.candidateIds;
			var departs = nodeInfo.candidateGroupIds;
			var selectable = nodeEl.selectable + '';

			if(!approvers) approvers = [];
			if(!departs) departs = [];

			var allApprovers = [];
			//尾号带2的变量是给模板的数据，不能跟原始数据混淆
			var departs2 = departs.length > 0 ? JSON.parse(JSON.stringify(departs)) : [];
			$(departs2).each(function(i, depart){
				depart.cssName = 'depart';
			});
			var approvers2 = approvers.length > 0 ? JSON.parse(JSON.stringify(approvers)) : [];
			$(approvers2).each(function(i, approver){
				approver.cssName = 'approver';
			});
			allApprovers = allApprovers.concat(departs2);
			allApprovers = allApprovers.concat(approvers2);
			if(nodeInfo.selectable == 'true') allApprovers.push({
				value: 'selectable',
				label: '允许自选审批人',
				cssName: 'selectable'
			})
			nodeInfo.allApprovers = allApprovers;

			var html = '';
			if(allApprovers.length > 0){
				nodeInfo.isBlank = false;
				nodeInfo.isReadonly = opt.readonly;
				html = template('flowchartTaskTypeTpl-content', nodeInfo);
			}else{
				nodeInfo.isBlank = true;
				nodeInfo.isReadonly = opt.readonly;
				html = template('flowchartTaskTypeTpl-content', nodeInfo);
			}
			nodeEl.html(html);

	    },
	    updateConditionsTimer: null,
	    updateAllViewByConditions: function(){
	    	var me = this;
	    	window.clearTimeout(me.updateConditionsTimer);
	    	me.updateConditionsTimer = window.setTimeout(function(){
	    		me.__updateAllViewByConditions();
	    	}, 100);
	    },
	    __updateAllViewByConditions: function(){
	    	var me = this;
	    	for(var id in me.currentData.nodes){
	    		var nodeinfo = me.currentData.nodes[id];
	    		if(nodeinfo.next && nodeinfo.next.length > 1){
	    			var map = nodeinfo.conditionMap;
	    			if(map)
		    		$(nodeinfo.next).each(function(i, nid){
		    			if(map[nid] && map[nid].conditions && map[nid].conditions.length > 0)
		    			me.updateViewByConditions(id, nid, map[nid].conditions);
		    		});	    			
	    		}
	    	}
	    },
	    updateViewByConditions: function(from, to, conditions){	 
	    	var me = this;
	    	var elem = me.element;
	    	if(!conditions)return;
	    	var leng = conditions.length;
			if(leng > 0){
	    		var firstCond = conditions[0];
				var div = elem.find('>.condition-lable-bottom[from="'+from+'"][to="'+to+'"]');
				// div.find('.cond_text .title').text(firstCond.leftText);
				// div.find('.cond_text .arrow').text(firstCond.typeText);
				// div.find('.cond_text .result').text(firstCond.rightText);
				var text = firstCond.leftText +' '+ firstCond.typeText +' '+ firstCond.rightText;
				div.find('.cond_text').html(text).attr('title', text);
				var displayingText = div.find('.cond_text').getShowingText();
				var dots = '…';
				var dotsExists = new RegExp(dots+"$").test(displayingText);
				if(leng > 1 || dotsExists){
					div.find('.step_cond_more').show();
				}
				if(dotsExists && displayingText){
					div.find('.cond_text').html(displayingText.substring(0, displayingText.length-1));
				}
			}
	    },
	    __getCondTopLabelCss: function(from, to){
	    	var labelClass = from+ '-' +to;
			return 'condname'+labelClass;
		},
	    __getCondBottomLabelCss: function(from, to){
	    	var labelClass = from+ '-' +to;
			return 'condexp'+labelClass;
		},
	    cleanConditionLabel: function(from, to){//只是清空，并非删除
	    	var me = this;
	    	var elem = me.element;
	    	var opt = me.options;

	    	var fromInfo = me.getNodeInfo(from);
	    	if(fromInfo.conditionMap){
	    		if(fromInfo.conditionMap[to]){
	    			condName = fromInfo.conditionMap[to].name;
	    			fromInfo.conditionMap[to].conditions = [];
	    			
	    			if(!condName) condName = me.getNewCondName();

	    			var css = me.__getCondTopLabelCss(from, to);
			    	elem.find('>.condition-lable-top.'+css).html(template('flowchartConditionNameEditorTpl-top', {
								name: condName,
								isReadonly: opt.readonly
							}));

			    	var css = me.__getCondBottomLabelCss(from, to);
			    	elem.find('>.condition-lable-bottom.'+css).html(template('flowchartConditionNameEditorTpl-bottom', {
								name: condName
							}));
			    	
	    			elem.find('>[id="approval_cond_viewer"]').remove();
	    		} 
	    	}

	    },
	    delConditionLabel: function(from, to){
	    	var me = this;
	    	var elem = me.element;

	    	var fromInfo = me.getNodeInfo(from);
	    	if(fromInfo.conditionMap){
	    		fromInfo.conditionMap[to] = {};
	    	}

	    	var css = me.__getCondTopLabelCss(from, to);
	    	elem.find('>.condition-lable-top.'+css).remove();

	    	var css = me.__getCondBottomLabelCss(from, to);
	    	elem.find('>.condition-lable-bottom.'+css).remove();

	    	elem.find('>[id="approval_cond_viewer"]').remove();
	    }
});