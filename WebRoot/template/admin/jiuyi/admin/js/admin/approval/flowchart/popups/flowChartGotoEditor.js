$.widget('rk.flowChartGotoEditor', {
	options: {
		canvasId: null,
		sourceId: null,
		targetId: null
	},
	_create: function () {
		var me = this;
		var elem = me.element;
		var opt = me.options;

		var canvas = $('#' + opt.canvasId);

		var id = opt.nodeId;
		var nodeInfo = canvas.flowChartCanvas('getNodeInfo', id);
		var select = elem.find('select[act="goto"]');
		elem.find('[act="btnOk"]').click(function(){
			var select = elem.find('select[act="goto"]');
			var from = id;
			if(select.size() > 0){
        		var to = select.val();
	        	if(to){
		        	me._goto(from, to);
	        	}
			}
        	$.closePopupLayer();
        });
		var noGoto = false;
		var ids = canvas.flowChartCanvas('getCascadingNextList', id, 'end');
		if(ids.length == 1 && canvas.flowChartCanvas('getNodeInfo', ids[0]).type == 'exclusiveGateway') noGoto = true;
		if(!noGoto){
			var nextInfo = canvas.flowChartCanvas('getNodeInfo', ids[0]);//如果后续节点与自己只隔了一个网管，也不能goto
			if(nextInfo.type == 'exclusiveGateway'){
				for(var i = 0, len = nextInfo.next.length; i < len; i++){
					ids = _.without(ids, nextInfo.next[i]);
				}
			}
		}
		ids.push('end')
		for(var i = 0, len = ids.length; i < len; i++){
			var ninfo = canvas.flowChartCanvas('getNodeInfo', ids[i]);
			if(!_.contains(nodeInfo.next, ids[i]) && (ninfo.type == 'userTask' || ninfo.type == 'endEvent')){
				select.append('<option value="'+ninfo.id+'">'+ninfo.name+'</option>')
			}
		}
		if(select.find('option').size() == 0) noGoto = true;
		if(noGoto)select.parents('li:first').html('没有可跳转的节点');
	},
	_goto: function(from, to){
		var me = this;
		var opt = me.options;

		var canvas = $('#' + opt.canvasId);
		var fromInfo = canvas.flowChartCanvas('getNodeInfo', from);
		var toInfo = canvas.flowChartCanvas('getNodeInfo', to);

		var newleft = fromInfo.left + (toInfo.left - fromInfo.left) / 2;
		var midPointPosition = fromInfo.top > toInfo.top ? 'BottomCenter':'TopCenter';	

		var taskRoom = canvas.flowChartCanvas('getTaskRoom');

		var mostTopNode = canvas.flowChartCanvas('getMostTopNode');
		var mostBottomNode = canvas.flowChartCanvas('getMostBottomNode');

		var newtop = 0;
		if(midPointPosition == 'TopCenter'){
			var offset = (mostTopNode.type == 'userTask' ? taskRoom/2 : 30);
			newtop = mostTopNode.top - offset;
		}else{
			var offset = (mostBottomNode.type == 'userTask' ? (taskRoom + 10) : 30);
			newtop = mostBottomNode.top + offset;
		}

		canvas.flowChartCanvas('addGotoPoint', from, to, {
			left: newleft,
			top:newtop 
		});

		fromInfo.gotoTargetId = to;
		if(!toInfo.gotoSourceId)toInfo.gotoSourceId = [];
		toInfo.gotoSourceId.push(from);
		_.uniq(toInfo.gotoSourceId);
	}
});