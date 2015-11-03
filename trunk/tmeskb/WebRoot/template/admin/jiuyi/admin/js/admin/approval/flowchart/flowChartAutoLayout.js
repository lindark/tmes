$.widget('rk.flowChartAutoLayout', $.rk.flowChart, {
	currentData: {},
	_create: function () {
		var me = this;
		var elem = me.element;
		var opt = me.options;
		//注意！不可更改！
	},
	doAutolayout: function(){
		var me = this;
		var elem = me.element;

		me.maxSpace = 0;
		me.spaceHeight = 0;

		var uiinfo = elem.flowChartCanvas('getUiInfo');
		$.extend(true, me.options, uiinfo);

		var nodes = elem.flowChartCanvas('getNodes');
		me.currentData.nodes = nodes;

		if(!elem.data('al-dbg-inited'))
		elem.data('al-dbg-inited', true).on('click', '[id]', function(){
			var id = $(this).attr('id');
			console.log( JSON.stringify(me.altempNodes[id]) );
		})
		
		me.altempNodes = me._deepClone(nodes);
		me._calculateXY();
		me._reArrangeStartEnd();
		me._draw();
	},
	altempNodes: {},//auto-layout template data
	loadData: function(json){
		var me = this;
		var elem = me.element;
		elem.html('');
		me.currentData = me._parseJson(json);
		me.altempNodes = me._deepClone(me.currentData.nodes);
		me._calculateXY();
		me._draw();
	},
	_calculateXY: function(){
		var me = this;
		var elem = me.element;

		var nodes = me.altempNodes;
		me.renderPool = [];

		{//计算所占竖向空间（多少个节点的高度）
			for(var n in nodes){
				n.vspace_calculated = false;//清空
			}
			var start = nodes['start'];		
			while(!start.vspace_calculated){
				me._calculateVerticalSpace('start');
			}
		}
		{//计算所占横向空间，亦即x坐标
			me.logcache = [];
			nodes['start'].horizontalSpace = 0;
			me._calculateHorizontalSpace('start');
		}
		me._outputToCanvas('start');
	},
	_calculateHorizontalSpace: function(nodeId){
		var me = this;
		var nodes = me.altempNodes;
		//start
		var node = nodes[nodeId];
		if(node.next){
			for(var i = 0, len = node.next.length; i < len; i++){
				var childId = node.next[i];
				var childNode = nodes[childId];
				childNode.horizontalSpace = node.horizontalSpace + 1;
			}
			for(var i = 0, len = node.next.length; i < len; i++){
				var childId = node.next[i];
				me._calculateHorizontalSpace(childId);
			}
		}
	},
	_calculateVerticalSpace: function(nodeId){
		var me = this;
		var nodes = me.altempNodes;
		//start
		var node = nodes[nodeId];
		if(node.next){
			var verticalSpace = 0;
			var calculated = true;
			for(var i = 0, len = node.next.length; i < len; i++){
				var childId = node.next[i]
				var n = nodes[childId];
				calculated = calculated && n.vspace_calculated;
				if(!n.vspace_calculated){
					me._calculateVerticalSpace(childId);
				}else{
					var vspace = n.verticalSpace / n.prev.length;
					verticalSpace = verticalSpace + (vspace < 1 ? 1 : vspace);
				}
			}
			if(verticalSpace > me.maxSpace) me.maxSpace = verticalSpace;
			node.verticalSpace = verticalSpace;
			node.vspace_calculated = calculated;
		}else{
			//遍历到End节点，赋初始值
			node.verticalSpace = 1;
			node.vspace_calculated = true;
		}
	},
	_getNodeInfo: function(id){

	},
	_outputToCanvas: function(id){
		var me = this;
		var elem = me.element;
		var opt = me.options;

		var nodes = me.altempNodes;
		var node = nodes[id];
		if(node && node.otc_visited)return;
		if(node) node.otc_visited = true;

		var canvasHeight = me.maxSpace * opt.taskRoom;
		//me.spaceHeight = canvasHeight / ;

		if(node.id == 'start'){
			node.spaceTopPx = 0;
			node.spaceBottomPx = canvasHeight;
			node.spacePx = node.spaceBottomPx - node.spaceTopPx;//所占纵向空间
			node.top = node.spaceTopPx + node.spacePx / 2;
			me._drawNode(node);
		}
		if(node.prev && node.prev.length > 1){//是多个点汇聚的网关
			var alltop = 0;
			var spaceTopPx = 99999999999;
			var spaceBottomPx = 0;
			for(var i = 0, len = node.prev.length; i < len; i++){
				var n = nodes[node.prev[i]];
				alltop = alltop + n.top;
				if(spaceTopPx > n.spaceTopPx) spaceTopPx = n.spaceTopPx;
				if(n.spaceBottomPx > spaceBottomPx ) spaceBottomPx = n.spaceBottomPx;
			}
			node.spaceTopPx = spaceTopPx;
			node.spaceBottomPx = spaceBottomPx;
			node.spacePx = spaceBottomPx - spaceTopPx;
			node.top = alltop / node.prev.length;
			//console.log(node.id, alltop, '/', node.prev.length,'=',node.top, node.spaceTopPx, node.spaceBottomPx, node.spacePx)
			me._drawNode(node);
		}
		//start
		if(node.next){
			if(node.next.length === 1){
				n = nodes[node.next[0]];
				if(n.prev.length == 1){//不是网关
					n.top = node.top;
					n.spaceTopPx = node.spaceTopPx;
					n.spaceBottomPx = node.spaceBottomPx;
					n.spacePx = node.spacePx;
					if(n.prev.length == 1)me._drawNode(n);
					me._outputToCanvas(n.id);					
				}else{
					me._outputToCanvas(n.id)
				}
			}else{
				for(var i = 0, len = node.next.length; i < len; i++){
					var n = nodes[node.next[i]];
					if(n.prev.length > 1) continue;//是网关，不在此计算
					var childrenSpace = n.verticalSpace * (n.type == 'exclusiveGateway' ? opt.gateHeight : opt.taskHeight);
					n.spaceTopPx = node.spaceTopPx + childrenSpace * i;
					n.spaceBottomPx = n.spaceTopPx + childrenSpace;
					n.spacePx = childrenSpace;
					n.top = n.spaceTopPx + n.spacePx / 2;
					//n.otc_visited = true;
					console.log(n.id, n.top)
					me._drawNode(n);
				}
				for(var i = 0, len = node.next.length; i < len; i++){
					var n = nodes[node.next[i]];
					me._outputToCanvas(n.id);
				}
			}
		}

	},
	_reArrangeStartEnd: function(){
		var me = this;
		var elem = me.element;
		var opt = me.options;

		var altempNodes = me._deepClone(me.altempNodes)

		var node = me.altempNodes['start'];
		var rearrangeIds = ['start','end'];
		//找到第一个孩子不是1的节点（亦即前置网关）
		while(node.next[0] != 'end' && node.next.length == 1){
			var id = node.next[0];
			rearrangeIds.push(id);
			node = me.altempNodes[id];
		}
		var alltop = 0;
		$(node.next).each(function(i, nid){
			alltop = alltop + me.altempNodes[nid].top;
		});
		var newtop = alltop / node.next.length;
		$(rearrangeIds).each(function(i, nid){
			me.altempNodes[nid].top = newtop;
		});
	},
	__spreadChildren: function(id){
		var me = this;
		var elem = me.element;
		var opt = me.options;

		var nodes = me.altempNodes;
		var node = nodes[id];
		
	},
	renderPool: [],
	_drawNode: function(node){
		var me = this;
		var elem = me.element;
		var opt = me.options;

		if(node.isCenerated) return;
		node.isCenerated = true;

		node.left = node.horizontalSpace * 180;

		me.renderPool.push(node);
	},
	_draw: function(){
		var me = this;
		var elem = me.element;
		var opt = me.options;

		for(var i = 0, len = me.renderPool.length; i < len; i++){
			var id = me.renderPool[i].id;
			//me.currentData.nodes[id].left = me.renderPool[i].left;
			//me.currentData.nodes[id].top = me.renderPool[i].top;
			elem.flowChartCanvas('setLeft', id, me.renderPool[i].left);
			elem.flowChartCanvas('setTop', id, me.renderPool[i].top);
		}
		elem.flowChartCanvas('repaint');

		// elem.flowChartCanvas('appendNodes', me.currentData.nodes);
		// for(var id in me.currentData.nodes){
		// 	var node = me.currentData.nodes[id];
		// 	if(node.type == 'userTask') elem.append('<div id="'+node.id+'" class="node node-task" style="left: '+node.left+'px; top: '+node.top+'px; ">'+node.id+'</div>');
		// 	if(node.type == 'exclusiveGateway') elem.append('<div id="'+node.id+'" class="node node-gateway" style="left: '+node.left+'px; top: '+node.top+'px; ">'+node.id+'</div>');
		// 	if(node.type == 'startEvent' || node.type == 'endEvent') elem.append('<div id="'+node.id+'" class="node node-startend" style="left: '+node.left+'px; top: '+node.top+'px; ">'+node.id+'</div>');
		// }
	}
});