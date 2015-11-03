$.widget('rk.flowChartConditionEditor', {
	options: {
		canvasId: null,
		sourceId: null,
		targetId: null
	},
	_create: function () {
		var me = this;
		var elem = me.element;
		var opt = me.options;

	    me._initVal();
	    if(elem.find('tbody tr').size() == 0) me._appendRow();

        elem.find('.more_link').click(function(){
        	me._appendRow();
        });

        elem.find('[act="btnOk"]').click(function(){
        	var ok = me._saveToNode();
        	if(ok)$.closePopupLayer();
        });

        elem.on('click', 'a.del_tip', function(){
        	var a = $(this);
        	a.closest('tr').off().remove();
        });
        elem.on('change','select[act="left"]', function(){
        	var select = $(this);
        	me.__handleLeftChange(select);
        });
        elem.on('mousedown', 'input[act="right"]', function(){
        	$(this).keydown()
        });
	},
	_initVal: function(){
		var me = this;
		var opt = me.options;

		var from = opt.sourceId;
		var to = opt.targetId;

		var conditionMap = me._getNodesInfo().conditionMap;
		var conditiondata;
		if(conditionMap)conditiondata = conditionMap[to];
		var conditions;
		if(conditiondata)conditions = conditiondata.conditions;
		if(!$.isArray(conditions))return;
		if(conditions){
			for(var i = 0, len = conditions.length; i < len; i++){
				var cond = conditions[i];
				me._appendRow(cond);
			}
		}
	},
	rowId: 0,
	_appendRow: function(data){
		var me = this;
		var elem = me.element;
		var opt = me.options;
		var thisRowId = me.rowId;
		elem.find('tbody').append( template('flowChartConditionPickerTpl', {
			rowId: thisRowId,
			conditionData: flowchartService.getConditionItems()
		}) );
		me.rowId++;
		var tr = elem.find('tr:last');
		if(typeof data != 'undefined'){
			var key = data.left.expression;
			var item = flowchartService.getConditionItem(key);
			me._createMidAndRight(item, thisRowId);
			tr.find('[act="left"]').val(data.left.expression);
			tr.find('[act="type"]').val(data.type);
			var rinput = tr.find('[act="right"]');
			var type = tr.attr('type');
			me.setRightValue(type, rinput, data.right.value, data.rightText);
		}else{
			me.__handleLeftChange(tr.find('[act="left"]'));
		}
	},
	__handleLeftChange: function(leftIpt){
		var me = this;
		var elem = me.element;

        var val = leftIpt.val()
        var rowId = leftIpt.closest('tr').attr('rowId');
        var rightInput = me._getRight(rowId);

        rightInput.val('');

		var item = flowchartService.getConditionItem(val);
		var p = rightInput.closest('span.value');
		if(rightInput.data('uiAutocomplete')){
			rightInput.rkautocomplete('destroy');
		}
		try{rightInput.integerInput('destroy')}catch(e){
			try{rightInput.numberInput('destroy')}catch(e){
				try{rightInput.numberInputPlus('destroy')}catch(e){
					try{
						rightInput.multiselector('destroy');						
					}catch(e){}
				}
			}
		}
		p.html(template('flowChartConditionPickerRightTpl'));
		if(!item){
			rightInput.val('').attr('rightValue', '');
			return;
		}
		me._createMidAndRight(item, rowId);
	},
	_getTr: function(rowId){
		var me = this;
		var elem = me.element;
		return elem.find('tr[rowId="'+rowId+'"]');
	},
	_getLeft: function(rowId){
		var me = this;
		var elem = me.element;
		return elem.find('tr[rowId="'+rowId+'"] select[act="left"]');
	},
	_getRight: function(rowId){
		var me = this;
		var elem = me.element;
		return elem.find('tr[rowId="'+rowId+'"] input[act="right"]');
	},
	_getMiddle: function(rowId){
		var me = this;
		var elem = me.element;
		return elem.find('tr[rowId="'+rowId+'"] select[act="type"]');
	},
	_createMidAndRight: function(item, rowId){
		var me = this;
		var elem = me.element;
		var rightInput = me._getRight(rowId);
		me._getTr(rowId).attr('type', item.itemTypeEntry);
		if(item.itemTypeEntry + '' == '3'){//单一选择型
			me._getMiddle(rowId).html('<option value="'+flowchartService.expressions[0].key+'" selected>'+flowchartService.expressions[0].text+'</option>');
			var listItems = item.selectItems;
			rightInput.rkautocomplete({
										      minLength: 0,
										      source: listItems,
										      focus: function( event, ui ) {
										          $(this).val( ui.item.label );
										          return false;
										      },
										      select: function( event, ui ) {
										          $(this).val( ui.item.selectItemName );
										          $(this).attr( 'rightValue', ui.item.selectItemId );
										          return false;
										      }
										    })
										    .rkautocomplete( "instance" )._renderItem = function( ul, item ) {
										        return $( "<li>" )
										          .append( "<a value=\""+item.selectItemId+"\">" + item.selectItemName + "</a>" )
										          .appendTo( ul );
										    };
			rightInput.on('change', function(){
				$(this).removeAttr( 'rightValue');
			})
		}
		if(item.itemTypeEntry + '' == '4'){//复选选择型
			me._getMiddle(rowId).html('<option value="'+flowchartService.expressions[6].key+'" selected>'+flowchartService.expressions[6].text+'</option>');
			var listItems = item.selectItems;
			rightInput.multiselector({
	            source: {"list":[{"id":2101,"name":"aaaaa221bb"},{"id":2305,"name":"dddd"},{"id":2306,"name":"测试审批组1"},{"id":3001,"name":"测试审批组2"},{"id":3002,"name":"打掉的"}]},
	            menuZindex: 1999
	        });
		}
		if(item.itemTypeEntry + '' == '5'){//整数型
			var html = template('flowChartConditionExpressionsTpl', {expressions: flowchartService.expressions.slice(0, 6)});
			me._getMiddle(rowId).html(html);
			rightInput.integerInput();
		}
		if(item.itemTypeEntry + '' == '6'){//实数型
			var html = template('flowChartConditionExpressionsTpl', {expressions: flowchartService.expressions.slice(0, 6)});
			me._getMiddle(rowId).html(html);
			rightInput.numberInput().numberInputPlus({
                resolution: 2
            });			
		}
	},
	_getNodesInfo: function(){
		var me = this;
		var opt = me.options;

		return $('#' + this.options.canvasId).flowChartCanvas('getNodeInfo', opt.sourceId);
	},
	_getNodes: function(){
		var me = this;
		var opt = me.options;

		return $('#' + this.options.canvasId).find('#' + opt.sourceId);//一律缓存在source节点中
	},
	_saveToNode: function(){
		var me = this;
		var elem = me.element;
		var opt = me.options;

		var from = opt.sourceId;
		var to = opt.targetId;

		var canvas = $('#' + this.options.canvasId);

		var conditions = [];
		var isOk = true;
		elem.find('tbody tr').each(function(){
			var tr = $(this);
			var itemTypeEntry = tr.attr('type');
			var left = tr.find('[act="left"]').val();
			var leftText = tr.find('[act="left"] option:selected').text();
			var type = tr.find('[act="type"]').val();
			var typeText = tr.find('[act="type"] option:selected').text();
			var widget = tr.find('[act="right"]');
			
			var data = me.getRightValue(itemTypeEntry, widget);			
			var right = data.right, rightText = data.rightText;

			isOk = $.isArray(right) ? right.length > 0 : !!right;

			conditions.push({
				left: {
					expression: left
				},
				type: type,
				right: {
					value: right
				},
				rightText: rightText
			});
		});
		if(!isOk){
			ingage.common.errorResult('值不能为空');
			return false;
		} 
		var conditionMap = me._getNodesInfo().conditionMap;
		conditionMap = conditionMap ? conditionMap : {};
		conditionMap[to] = {
				"name": conditionMap[to].name ? conditionMap[to].name : '',
        		"type": "and",
        		"conditions": conditions
        	};
		me._getNodesInfo().conditionMap = conditionMap;

		if(conditions.length > 0){	
			for(var i = 0, len = conditions.length; i < len; i++){
				var cond = conditions[i];
				var leftText = me._getSelectText('[act="left"]', cond.left.expression);
				var typeText = me._getSelectText('[act="type"]', cond.type);
				cond.leftText = leftText;
				cond.typeText = typeText;	
				if(!cond.rightText)
				cond.rightText = flowchartService.getConditionRightText(cond.left.expression, cond.right.value);			
			}		
			canvas.flowChartCanvas('updateViewByConditions', from, to, conditions);
		}else{
			canvas.flowChartCanvas('cleanConditionLabel', from, to);
		}
		return true;
	},
	_getSelectText: function(selectSel, val){
		var text;
		$(selectSel).find('option').each(function(i){
			var option = $(this);
			if(option.attr('value') == val) {
				text = option.text();
				return false;
			}
		});
		return text;
	},
	setRightValue: function(itemTypeEntry, widget, value, text){
		var me = this;
		//console.log(itemTypeEntry, widget, value, text)
		switch(itemTypeEntry){
				case '5': //整数
					widget.val(text).attr('rightValue', value);
					break;
				case '6': //实数
					widget.val(text).attr('rightValue', value);
					break;
				case '3': //单选
					widget.val(text).attr('rightValue', value);
					break;
				case '4': 
					var data = [];
					$(value).each(function(i, value){
						data.push({
							id: value,
							value: value,
							label: text[i]
						});
					});
					widget.multiselector('val', data);
					break;
				default:;
			}
	},
	getRightValue: function(itemTypeEntry, widget){
		var right, rightText;
		switch(itemTypeEntry){
				case '5': //整数
					right = widget.val();
					rightText = right;
					break;
				case '6': //实数
					right = widget.val();
					rightText = right;
					break;
				case '3': //单选
					right = widget.attr('rightValue');
					rightText = widget.val();
					break;
				case '4': 
					var arr = widget.multiselector('val');
					right = [];
					rightText = [];
					$(arr).each(function(i, item){
						right.push(item.id);
						rightText.push(item.label);
					});
					//rightText = rightText.length > 0 ? rightText.join(',') : '';
					break;
				default:;
			}
		return {
			right: right,
			rightText: rightText
		}
	}
});