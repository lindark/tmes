$.widget('rk.flowChartApprovalEditor', {
	options: {
		canvasId: null,
		nodeId: null
	},
	approverSelIpt: null,
	departmentSelIpt: null,
	_create: function () {
		var me = this;
		var elem = me.element;
		var opt = me.options;

		me.approverSelIpt = elem.find('#approverSelector').snsmultipeopleselector();
        me.departmentSelIpt = elem.find('#departmentSelector').multiselector({
            source: "/admin/approvalGroup/all-Group.action",
            menuZindex: 1999,
            onResponse: function(data){
            	departmentService.appendDepartInfo(data.list)
            }
        });
        me._initVal();

        elem.find('[act="btnOk"]').click(function(){
        	me._saveToNode();
        	$.closePopupLayer();
        });
        elem.find('.approver_checkbox span').click(function(){
        	var checkBox = elem.find('[act="approverSelectable"]');
        	checkBox.prop("checked", !checkBox.is(':checked'));
        });
	},
	_getNodesInfo: function(){
		var me = this;
		var opt = me.options;

		return $('#' + this.options.canvasId).flowChartCanvas('getNodeInfo', opt.nodeId);
	},
	_getNodes: function(){
		var me = this;
		var opt = me.options;

		return $('#' + this.options.canvasId).find('#' + opt.nodeId);
	},
	_saveToNode: function(){
		var me = this;
		var elem = me.element;
		var opt = me.options;
		var canvas = $('#' + this.options.canvasId);

		var approvers = me.approverSelIpt.snsmultipeopleselector('getItems');
		var approvers30 = me.approverSelIpt.snsmultipeopleselector('getItems', '3.0');//走id，name，icon
		var departs = me.departmentSelIpt.multiselector('val');
		var selectable = elem.find('[act="approverSelectable"]').is(':checked');

		rk_userService.appendUserInfo(approvers30);

		me._getNodesInfo().candidateIds = approvers;
		me._getNodesInfo().candidateGroupIds = departs;
		me._getNodesInfo().selectable = selectable + '';

		for(id in departs){
			if(!departs[id].value)departs[id].value = departs[id].id;
		}

		me._getNodes().data('approvers', approvers);
		me._getNodes().data('departs', departs);
		me._getNodes().data('selectable', selectable+'');

		canvas.flowChartCanvas('updateViewByApprovers', opt.nodeId);
		canvas.flowChartCanvas('repaint', opt.nodeId);
	},
	_initVal: function(){
		var me = this;
		var elem = me.element;
		var opt = me.options;

		var approvers = me._getNodes().data('approvers');
		var departs = me._getNodes().data('departs');
		var selectable = me._getNodesInfo().selectable;
		selectable = (selectable == 'true' ? true : false);

		if(approvers){
			approvers = me.__updateUserInfo(approvers);
			me.approverSelIpt.snsmultipeopleselector('setItems', approvers);
		}
		if(departs){
			departs = me.__updateDeparts(departs);
			me.departmentSelIpt.multiselector('val', departs);
		}
		if(selectable){
			elem.find('[act="approverSelectable"]').prop('checked', 'true');
		}
	},
	__updateDeparts: function(departs){
		var me = this;
		var opt = me.options;
		var serverData = $('#' + opt.canvasId).flowChartCanvas('getServerData');//服务器端存有title等文字信息
		for(var i = 0, leni = departs.length; i < leni; i++){
			var depart = departs[i];
			var id = departs[i].value;
			var info = departmentService.getDepartInfo(id);
			depart = $.extend(true, {}, info);
			if(depart.id)depart.value = depart.id;
		}
		return departs;
	},
	__updateUserInfo: function(approvers){
		var me = this;
		var opt = me.options;
		for(var i = 0, len = approvers.length; i < len; i++){
			var info = rk_userService.getUserInfo(approvers[i].value);
			var approver = approvers[i];
			if(info){
				approver = $.extend(true, {}, info);;
				approver.label = info.name;					
			}
			if(!approver.value && approver.id){
				approver.value = approver.id;
			}
		}
		return approvers;
	}
});