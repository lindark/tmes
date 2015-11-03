(function (){
	var canvasId = 'flowchart';
	var popupName = 'flowchartEditor';

	//获取应用text-overflow后的含有...的文字
	jQuery.fn.getShowingText = function () {
	    // Add temporary element for measuring character widths
	    $('body').append('<div id="TempDivOfGetShowingText" style="padding:0;border:0;height:auto;width:auto;position:absolute;display:none;"></div>');
	    var longString = $(this).text();
	    var eleWidth = $(this).innerWidth();
	    var totalWidth = 0;
	    var totalString = '';
	    var finished = false;
	    var ellipWidth = $('#TempDivOfGetShowingText').html('&hellip;').innerWidth();
	    var offset = 18; // seems to differ based on browser (6 for Chrome and 7 for Firefox?)
	    for (var i = 0;
	    (i < longString.length) && ((totalWidth) < (eleWidth-offset)); i++) {
	        $('#TempDivOfGetShowingText').text(longString.charAt(i));
	        totalWidth += $('#TempDivOfGetShowingText').innerWidth();
	        totalString += longString.charAt(i);
	        if(i+1 === longString.length)
	        {
	            finished = true;
	        }
	    }
	    $('body').remove('#TempDivOfGetShowingText'); // Clean up temporary element
	    if(finished === false)
	    {
	        return totalString.substring(0,totalString.length-3)+"…";
	    }
	    else
	    {
	        return longString;
	    }
	}

	window.flowchartService = {
		popup: function(opt){
			var divId = 'popupcontent_for_openPopupLayer';
			if($('#' + divId).size() == 0){
				$(document.body).append('<div id="'+divId+'" style="display:none;"></div>');
			}
			template.config('escape', false)
			var winHtml = template('flowchartPopupTpl', {
				popupName: popupName,
				title: opt.title, 
				content: template(opt.templateId, {})
			});
			$('#' + divId).html(winHtml);
			$(document.body).scrollTop(0);
			$.openPopupLayer({
				name: popupName,
				width:(opt.width ? opt.width : 580),
				target: divId, 
				success: function(){
					$('#' + divId).html('').remove();
					if(opt.success)(opt.success)(popupName);
				},
				beforeClose: function(){
					$(window).trigger('resize');//由于编辑框在第三层，图层弹框在第二层，因此编辑框弹出后会引起第二层的位置错乱，关闭前触发resize校准
				}
			});
		},
		popupApproverEditor: function(id){
			var me = this;
			var canvas = $('#flowchart');
			if(id == 'start' || id=='task0' || id == 'end')return;
			if(canvas.flowChartCanvas('getNodeInfo', id).type != 'userTask') return;
			me.popup({
				templateId: 'flowchartApproverEditorTpl',
				title: '设置审批人',
				width: 580,
				success: function(){
					$('#popupLayer_' + popupName).find('.pop_up:first').flowChartApprovalEditor({
						nodeId: id,
						canvasId: canvasId
					});
				}
			});
		},
		popupConditionEditor: function(sourceId, targetId){
			var me = this;
			var canvas = $('#flowchart');
			var fromInfo = canvas.flowChartCanvas('getNodeInfo', sourceId);
			var toInfo = canvas.flowChartCanvas('getNodeInfo', targetId);

			if(fromInfo.next && fromInfo.next.length > 1){//只有分支线才能弹
				me.popup({
					templateId: 'flowChartConditionEditorTpl',
					title: '设置分支条件',
					width: 580,
					success: function(){
						$('#popupLayer_' + popupName).find('.pop_up:first').flowChartConditionEditor({
							sourceId: sourceId,
							targetId: targetId,
							canvasId: canvasId
						});
					}
				});				
			}

		},
		popupGoto: function(id){
			var me = this;
			var nodeInfo = $('#flowchart').flowChartCanvas('getNodeInfo', id);
			if(nodeInfo.gotoTargetId){
				ingage.common.errorResult('该节点上已经有一个跳转了，请删除后再设置');
				return;
			}
			me.popup({
				templateId: 'flowChartGotoPopupTpl',
				width: 580,
				success: function(){
					$('#popupLayer_' + popupName).find('.pop_up:first').flowChartGotoEditor({
						nodeId: id,
						canvasId: canvasId
					});
				}
			});
		},
		conditionItemsList: [],
		conditionItemsMap: {},
		expressions: [
	        // {key:"equals", text:"="},
	        // {key:"not", text:"&lt;&gt;"},
	        // {key:"greaterThan", text:"&gt;"},
	        // {key:"greaterThanOrEqual", text:"&gt;="},
	        // {key:"lessThan", text:"&lt;"},
	        // {key:"lessThanOrEqual", text:"&lt;="},

	        {key:"equals", text:"等于"},
	        {key:"not", text:"不等于"},
	        {key:"greaterThan", text:"大于"},
	        {key:"greaterThanOrEqual", text:"大于等于"},
	        {key:"lessThan", text:"小于"},
	        {key:"lessThanOrEqual", text:"小于等于"},
	        {key:"ContainsIgnoreCase", text:"包含"}
		],
		//条件选择
		cacheConditionItems: function(items){
			var me = this;
			me.conditionItemsList = items;
			$(items).each(function(i, item){
				me.conditionItemsMap[item.entryPropertyName] = item;//cache
			});
		},
		getConditionItems: function(){
			var me = this;
			return me.conditionItemsList;
		},
		getConditionItem: function(key){
			var me = this;
			return me.conditionItemsMap[key];
		},
		getConditionEntryType: function(key){//itemEntryType，亦即数据类型
			var me = this;
			var item = me.getConditionItem(key);
			return item.itemTypeEntry;
		},
		getConditionLeftText: function(key){
			var me = this;
			var item = me.getConditionItem(key);
			return item ? item.itemName : null;
		},
		getConditionMidText: function(value){
			var me = this;
			for(var i = 0, len = me.expressions.length; i < len; i++){
				if(me.expressions[i].key == value) return me.expressions[i].text;
			}
		},
		getConditionRightText: function(key, selectItemId){
			var me = this;
			var item = me.getConditionItem(key);
			if(item != undefined && item.selectItems){
				for(var i = 0, len = item.selectItems.length; i < len; i++){
					var si = item.selectItems[i];
					if(si.selectItemId + '' == selectItemId + '') return si.selectItemName;
				}
			}
			return selectItemId;
		}
	}
})()