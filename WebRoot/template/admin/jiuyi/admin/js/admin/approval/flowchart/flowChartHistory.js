$.widget('rk.flowChartHistory', {
	queueSize: 10,
	options: {
		canvasId: null
	},
	historyQueue: [],//队列
	_create: function () {
		var me = this;
		var elem = me.element;
		var opt = me.options;

	},
	_push: function(data){
		var me = this;
		if(me.historyQueue.length >= me.queueSize){
			me.historyQueue.push(data); 
			me.historyQueue.shift()		
		}else{
			me.historyQueue.push(data);
		}
	},
	save: function(){//快照保存
		var me = this;
		var elem = me.element;
		var opt = me.options;
		var canvas = $('#' + opt.canvasId);
		var data = canvas.flowChartCanvas('getJsonString', true);
		me._push(data);
		elem.siblings('.approval_toolbar').find('[act="undo"]').attr('class', 'approval_repeal_normal');
	},
	undo: function(){
		var me = this;
		var elem = me.element;
		var opt = me.options;
		if(me.historyQueue.length == 0)return;
		var jsonstr = me.historyQueue.pop();
		var canvas = $('#' + opt.canvasId);
		canvas.flowChartCanvas('loadData', JSON.parse(jsonstr))
		if(me.historyQueue.length == 0)elem.siblings('.approval_toolbar').find('[act="undo"]').attr('class', 'approval_repeal_disabled');
	},
	reset: function(){
		var me = this;
		var elem = me.element;
		me.historyQueue = [];
		elem.siblings('.approval_toolbar').find('[act="undo"]').attr('class', 'approval_repeal_disabled');
	}
});