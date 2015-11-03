$.widget('rk.flowChartCtxMenu', {
	options: {
		canvasId: null,
		nodeId: null
	},
	_create: function(){
		var me = this;
		var elem = me.element;
		var opt = me.options;

		me.taskRoom = $('#' + opt.canvasId).flowChartCanvas('getTaskRoom');
		me.gateRoom = $('#' + opt.canvasId).flowChartCanvas('getGateRoom');
	},
	handleClick: function (act, type, id) {
		var me = this;
		var elem = me.element;
		var opt = me.options;
		var canvas = $('#' + opt.canvasId);

		canvas.flowChartHistory('save');//为undo保存历史

		if(act == 'addNext') {
			me._appendOneNext(type, id);
			canvas.flowChartCanvas('stepAWay', id, 150);
			canvas.flowChartCanvas('repaint');
		}else 
		if(act == 'addNexts') {
			if(type == 'userTask' || type == 'startEvent') me._appendSeriesNexts(type, id);//串联
			if(type == 'exclusiveGateway') me._appendParallelNexts(type, id);//并联
		}else 
		if(act == 'addBranch') {
			me._askAddParallelNexts(type, id);//新建新的并联分支结点
		}else 
		if(act == 'delete') {
			if(type == 'userTask') me._deleteTask(id);
			if(type == 'goto') me._deleteGoto(id);
			if(type == 'exclusiveGateway') me._deleteGateway(id);
		}else 
		if(act == 'setApprover'){
			flowchartService.popupApproverEditor(id);
		}else 
		if(act == 'goto'){
			flowchartService.popupGoto(id);
		}else 
		if(act == 'addCondition'){
			var from = id;
			var to = canvas.flowChartCanvas('getNodeInfo', id).next[0];
			flowchartService.popupConditionEditor(id, to);
		}else
		if(act == 'addBrother'){
			var nodeInfo = canvas.flowChartCanvas('getNodeInfo', id);
			var prev = nodeInfo.prev;
			if(prev && canvas.flowChartCanvas('getNodeInfo', prev[0]).next.length > 1){//平行节点添加同级分支
				var parentId =  canvas.flowChartCanvas('getNodeInfo', id).prev[0];
				me._appendBranch(1, 'userTask', parentId, 'sibiling');
			}
			if(prev && canvas.flowChartCanvas('getNodeInfo', prev[0]).next.length == 1){//单个节点添加同级分支
				me._addBrotherForSingle(id);
			}
		}
	},
	reSetConfirmZindex: function(){
		if($('#popupLayer_popup_layer').is(':visible')){
			var winZindex = $('#popupLayer_popup_layer').css('z-index');
			winZindex = parseInt(winZindex);		
			if(isNaN(winZindex))winZindex = 1000;	
			$('.ui-widget-overlay:visible').css('z-index', winZindex + 2000 + '');
			$('.ui-dialog:visible').css('z-index', winZindex + 2002 + '');
		}
	},
	_deleteGoto: function(id){
		var me = this;
		var elem = me.element;
		var opt = me.options;
		var canvas = $('#' + opt.canvasId);
		ingage.common.confirm("确认删除吗？",function(){
			var arr = id.split('-goto-');
			var from = arr[0];
			var to = arr[1];
			canvas.flowChartCanvas('delGotoPoint', from, to);
		});
		me.reSetConfirmZindex();
	},
	_deleteTask: function(id){
		var me = this;
		var opt = me.options;

		var canvas = $('#' + opt.canvasId);

		var selectedIds = canvas.flowChartCanvas('getSelectedIds');
		selectedIds.push(id);
		selectedIds = _.uniq(selectedIds);
		selectedIds = _.without(selectedIds, 'task0');//不能删除task0

		ingage.common.confirm("确认删除吗？",function(){
			for(var i = 0, len = selectedIds.length; i < len; i++){
				me.__doDeleteTask(selectedIds[i]);
			}
		});
		me.reSetConfirmZindex();
	},
	__doDeleteTask: function(id){
		var me = this;
		var opt = me.options;

		var canvas = $('#' + opt.canvasId);
		var hasSibiling = canvas.flowChartCanvas('hasSibiling', id);
		var info = canvas.flowChartCanvas('getNodeInfo', id);
		if(!hasSibiling){//没有兄弟
			canvas.flowChartCanvas('stepAWay', id, 'reverse');
		}else 
		if(hasSibiling && info.next && info.next.length > 1){//如果有兄弟节点，那么删除自己后，其孩子应当与兄弟节点对齐
			var offset = canvas.flowChartCanvas('getLeft', info.next[0]) - canvas.flowChartCanvas('getLeft', id);
			canvas.flowChartCanvas('stepAWay', id, 'reverse', offset);
		}
		canvas.flowChartCanvas('deleteNode', id);
	},
	_deleteGateway: function(id){
		var me = this;
		var opt = me.options;

	},
	_appendOneNext: function(type, id, nextType, nextNext){
		var me = this;
		var opt = me.options;

		if(typeof nextType == 'undefined') nextType = 'userTask';

		var canvas = $('#' + opt.canvasId);
		var ninfo = canvas.flowChartCanvas('getNodeInfo', id);

		// if(ninfo.next && ninfo.next.length > 1){//是网关节点，其子应添加为并联节点
		// 	var nearestGatewayId = canvas.flowChartCanvas('getNearestCommonChildId', ninfo.next);
		// 	nextNext = [nearestGatewayId];
		// }
		if(typeof nextNext == 'undefined' || !nextNext) nextNext = _.clone(ninfo.next);//新建点的next是谁

		var newId;
		var newName;
		if(nextType == 'userTask'){
			newId = canvas.flowChartCanvas('getNewTaskId');
			newName = canvas.flowChartCanvas('getNewTaskName', newId);
		} else if(nextType == 'exclusiveGateway'){
			newId = canvas.flowChartCanvas('getNewGateId');
			newName = canvas.flowChartCanvas('getNewGateName', newId);
		} else {
			newId = 'r'+Math.random();
			newName = newId;
		}

		var newleft = ninfo.left;
		var newtop = ninfo.top;
		if(nextType == 'exclusiveGateway')newtop = newtop + 17.85;//网关节点不高，需下调一点

		var newNodes = {};
		newNodes[newId] = {
			id: newId,
			type: nextType,
			name: newName,
			prev: [id],
			next: nextNext,
			left: newleft,
			top: newtop
		};
		canvas.flowChartCanvas('appendNodes', newNodes);
		var parentEl = canvas.flowChartCanvas('getNodeElem', id);
		var parentInfo = canvas.flowChartCanvas('getNodeInfo', id);
		var conditionMap = parentInfo.conditionMap;
		if(parentInfo.next && parentInfo.next.length > 1 && conditionMap && nextNext){//继承父节点的分支条件
			//是给平行孩子追加兄弟节点还是在父节点和平行节点之间添加孩子（上面的if无法界定这两种情况）
			var isParallel = (nextNext.length  == 1 && canvas.flowChartCanvas('getNodeInfo',nextNext[0]).type == 'exclusiveGateway');//是添加平行节点
			var newEl = canvas.flowChartCanvas('getNodeElem', newId);
			var newInfo = canvas.flowChartCanvas('getNodeInfo', newId);
			newInfo.conditionMap = conditionMap;
			if(!isParallel)parentInfo.conditionMap = {};//如果是在父节点和平行节点之间添加孩子的话，父节点的信息需要擦除
			var updateConditions = function(){
				for(var i = 0, len = newInfo.next.length; i < len; i++){
					var toId = newInfo.next[i];
					var toCond = conditionMap[toId];
					if(toCond){
						canvas.flowChartCanvas('delConditionLabel', id, toId);
						canvas.flowChartCanvas('updateViewByConditions', newId, toId, toCond.conditions);
					}
				}
			}
			window.setTimeout(function(){updateConditions()}, canvas.flowChartCanvas('getRepaintTimeout')+200);
		}
		canvas.flowChartCanvas('repaint');
		for(var i = 0, len = nextNext.length; i < len; i++) canvas.flowChartCanvas('disconnect', id, nextNext[i]);
		//更新nextnext的prev
		for(var i = 0, len = nextNext.length; i < len; i++){
			canvas.flowChartCanvas('appendPrevId', nextNext[i], newId);
		} 
		//更新自己的next
		canvas.flowChartCanvas('appendNextId', id, newId);
		return newId;
	},
	_appendSeriesNexts: function(type, id){//添加多个串联节点
		var me = this;
		var opt = me.options;
		flowchartService.popup({
				templateId: 'flowChartSeriesNextsPopupTpl',
				width: 580,
				success: function(popupName){
					var canvas = $('#' + opt.canvasId);
					var win = $('#popupLayer_' + popupName);
					win.find('input:first').focus();
					win.find('[act="btnOk"]').click(function(){
						var num = win.find('input[act="num"]').val();
						num = parseInt(num);
						var theId = id;
						var pId;
						for(var i = 0; i < num; i++){
							pId = theId;
							theId = me._appendOneNext(type, theId);	
							canvas.flowChartCanvas('stepAWay', pId);
						}
						$.closePopupLayer(popupName);
					});
				}
			});
	},
	_appendParallelNexts: function(type, id){//添加多个并联节点
		var me = this;
		flowchartService.popup({
				templateId: 'flowChartParallelNextsPopupTpl',
				width: 580,
				success: function(popupName){
					var win = $('#popupLayer_' + popupName);
					win.find('input[act="num"]').integerInput({
						min: 2,
						max: 10
					}).focus();
					win.find('[act="btnOk"]').click(function(){
						var num = win.find('input[act="num"]').val();
						num = parseInt(num);
						for(var i = 0; i < num; i++){
							me._appendOneNext(type, id);		
						}
					});
				}
			});
	},
	_askAddParallelNexts: function(type, id){
		var me = this;
		var opt = me.options;
		flowchartService.popup({
				templateId: 'flowChartParallelNextsPopupTpl',
				width: 580,
				success: function(popupName){
					var win = $('#popupLayer_' + popupName);
					win.find('input[act="num"]').integerInput({
						min: 2,
						max: 10
					}).focus();
					win.find('[act="btnOk"]').click(function(){
						var num = win.find('input[act="num"]').val();						
						if(!$.isNumeric(num)) {
							ingage.common.errorResult('请输入数字');
							return;
						}	
						num = parseInt(num);				
						if(num < 2) {
							ingage.common.errorResult('分支数不能少于2');
							return;
						}
						if(num > 10) {
							ingage.common.errorResult('分支数不能大于10');
							return;
						}
						var canvas = $('#' + opt.canvasId);

						var vroom = canvas.flowChartCanvas('getTaskRoom', 'y');

						//给新增分支节点腾出空间
						var y = canvas.flowChartCanvas('getY', id);
						var ystart = canvas.flowChartCanvas('getY', 'start');
						vroom = vroom;
						var newRoomUp = (vroom + 110) * num;
						var newRoomDown = (vroom + 50) * (num);

						if(id != 'task0'){
							canvas.flowChartCanvas('stepASide', 'y', id, newRoomUp / 4, false, {ignorInBetween: true});
							canvas.flowChartCanvas('stepASide', 'y', id, -1 * newRoomDown / 4, true, {ignorInBetween: true});								
						}

						me._appendBranch(num, type, id, 'children');

						window.setTimeout(function(){
							var canvas = $('#' + opt.canvasId);
							var firstChildId = canvas.flowChartCanvas('getNodeInfo', id).next[0];
							//canvas.flowChartCanvas('stepAWay', id);
						}, 100);
						canvas.flowChartCanvas('updateConnects', id);//更新连接，以防止拐点的拐弯(midpoint)不正确
						$.closePopupLayer(popupName);
					});
				}
			});
	},
	_appendBranch: function(num, type, id, as){
		var me = this;
		var opt = me.options;
		var canvas = $('#' + opt.canvasId);

		var gap = 60;

		var hasSibiling = canvas.flowChartCanvas('hasSibiling', id);
		
		var ninfo = canvas.flowChartCanvas('getNodeInfo', id);
		var nelem = canvas.flowChartCanvas('getNodeElem', id);//canvas.find('.userTask[id="'+id+'"]');
		var next = ninfo.next;
		var gateId, nextId;
		var hasChildrenBefore = false;
		if(as == 'sibiling' && next.length > 1){
			hasChildrenBefore = true;	
			gateId = canvas.flowChartCanvas('getNearestCommonChildId', next);//获取最近的共有子节点id
		}else{
			hasChildrenBefore = false;
			gateId = me._appendOneNext(type, id, 'exclusiveGateway');
			$('#' + opt.canvasId).flowChartCanvas('stepAWay', id, me.taskRoom - 25);
		}

		var gateInfo = canvas.flowChartCanvas('getNodeInfo', gateId);

		//生成并行节点
		var newidlist = [];
		for(var i = 0; i < num; i++){
			var newid = me._appendOneNext(type, id, 'userTask', [gateId]);		
			newidlist.push(newid);
			//初始化分支条件
			var conditionMap = ninfo.conditionMap;
			if(!conditionMap) conditionMap = {};
			if(!conditionMap[newid])conditionMap[newid] = {};
			conditionMap[newid].name = canvas.flowChartCanvas('getNewCondName', newid);
			conditionMap[newid].conditions = [];
			ninfo.conditionMap = conditionMap;
		}

		//调整子节点的分布
		var fromNode = canvas.flowChartCanvas('getNodeElem', id);//canvas.find('[id="'+id+'"]');

		var fromX = canvas.flowChartCanvas('getX', id);
		var fromY = canvas.flowChartCanvas('getY', id);
		
		var idlist = canvas.flowChartCanvas('getNodeInfo', id).next;
		var idlistlength = idlist.length;
		var factor = (idlistlength % 2 == 0 ? idlistlength : idlistlength - 1) / 2;
		factor = factor * -1;

		for(var i = 0, len = idlist.length; i < len; i++){
			var childid = idlist[i];
			var info = canvas.flowChartCanvas('getNodeInfo', childid);
			var newY = (fromY + factor * me.taskRoom * 0.6);
			var newX = fromX + me.taskRoom + gap - 7;
			if(idlistlength == 2)newY = newY + me.taskRoom * 0.6 / 2;//美化
			canvas.flowChartCanvas('setXY', childid, newX, newY);
			factor++;
		}

		if(!hasChildrenBefore)
		window.setTimeout(function(){
							var canvas = $('#' + opt.canvasId);
							var firstChildId = canvas.flowChartCanvas('getNodeInfo', id).next[0];
							canvas.flowChartCanvas('stepAWay', firstChildId);
						}, 100);

		canvas.flowChartCanvas('repaint');
	},
	_addBrotherForSingle: function(id){
		var me = this;
		var elem = me.element;
		var opt = me.options;
		var canvas = $('#' + opt.canvasId);

		var gap = 60;

		var nodeInfo = canvas.flowChartCanvas('getNodeInfo', id);
		var parentInfo = canvas.flowChartCanvas('getNodeInfo', nodeInfo.prev[0]);

		var gateId = me._appendOneNext(nodeInfo.type, id, 'exclusiveGateway');
		canvas.flowChartCanvas('stepAWay', id, me.taskRoom - 25);

		var gateInfo = canvas.flowChartCanvas('getNodeInfo', gateId);

		//生成并行节点
		var newid = me._appendOneNext(parentInfo.type, parentInfo.id, 'userTask', [gateId]);
		//初始化分支条件
		var conditionMap = parentInfo.conditionMap;
		if(!conditionMap) conditionMap = {};
		if(!conditionMap[id])conditionMap[id] = {};
		if(!conditionMap[newid])conditionMap[newid] = {};
		conditionMap[id].name = canvas.flowChartCanvas('getNewCondName', id);
		conditionMap[id].conditions = [];
		conditionMap[newid].name = canvas.flowChartCanvas('getNewCondName', newid);
		conditionMap[newid].conditions = [];
		parentInfo.conditionMap = conditionMap;

		var x = canvas.flowChartCanvas('getX', id);
		var y = canvas.flowChartCanvas('getY', id);
		canvas.flowChartCanvas('setY', id, y - me.taskRoom * 0.3);

		canvas.flowChartCanvas('setXY', newid, x, y + me.taskRoom * 0.3);
		canvas.flowChartCanvas('stepAWay', parentInfo.id, me.taskRoom * 0.6);

		canvas.flowChartCanvas('updateConnects', id);
		canvas.flowChartCanvas('repaint');
	}
});