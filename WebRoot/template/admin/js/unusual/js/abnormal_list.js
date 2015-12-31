jQuery(function($) {
	var grid_selector = "#grid-table1";
	var pager_selector = "#grid-pager1";
	//resize to fit page size
	$(window).on('resize.jqGrid', function () {
		$(grid_selector).jqGrid( 'setGridWidth', $(".page-content").width() );
    })
	//resize on sidebar collapse/expand
	var parent_column = $(grid_selector).closest('[class*="col-"]');
	$(document).on('settings.ace.jqGrid' , function(ev, event_name, collapsed) {
		if( event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed' ) {
			//setTimeout is for webkit only to give time for DOM changes and then redraw!!!
			setTimeout(function() {
				$(grid_selector).jqGrid( 'setGridWidth', parent_column.width() );
			}, 0);
		}
    })

	jQuery(grid_selector).jqGrid({		
		//direction: "rtl",

		//subgrid options
		subGrid : false,
		//subGridModel: [{ name : ['No','Item Name','Qty'], width : [55,200,80] }],
		//datatype: "xml",
		subGridOptions : {
			plusicon : "ace-icon fa fa-plus center bigger-110 blue",
			minusicon  : "ace-icon fa fa-minus center bigger-110 blue",
			openicon : "ace-icon fa fa-chevron-right center orange"
		},
		//for this example we are using local data
		subGridRowExpanded: function (subgridDivId, rowId) {
			var subgridTableId = subgridDivId + "_t";
			$("#" + subgridDivId).html("<table id='" + subgridTableId + "'></table>");
			$("#" + subgridTableId).jqGrid({
				datatype: 'local',
				data: subgrid_data,
				colNames: ['No','Item Name','Qty'],
				colModel: [
					{ name: 'id', width: 50 },
					{ name: 'name', width: 150 },
					{ name: 'qty', width: 50 }
				]
			});
		},
		
		url:"abnormal!ajlist.action",
		datatype: "json",
		height: "250",//weitao 修改此参数可以修改表格的高度
		jsonReader : {
	          repeatitems : false,
	          root:"list",
	          total:"pageCount",
	          records:"totalCount"
	        },
	    prmNames : {
	    	rows:"pager.pageSize",
	    	page:"pager.pageNumber",
		    search:"pager._search",
		    sort:"pager.orderBy",
		    order:"pager.orderType"
	    },
		colNames:[ '呼叫时间','应答时间','处理时间','日志', '消息', '发起人','应答人','状态',"zt","id"],
		colModel:[
			
			{name:'createDate',index:'createDate', width:100,sorttype:"date",unformat: pickDate,search:false,formatter:datefmt},
			{name:'replyDate',index:'replyDate',width:100,sorttype:"date",search:false,formatter:datefmt},
			{name:'disposeTime',index:'disposeTime', width:60,search:false,editable: true},
			{name:'log',index:'log', width:160,search:false,editable: true},
			{name:'callReason',index:'callReason',search:false, width:100, editable: true},
			{name:'originator',index:'iniitiator.name', width:60, editable: true,search:false},
			{name:'answer',index:'answer', width:60,search:false,editable: true},
			{name:'stateRemark',index:'state', width:60,editable: true, sortable:true,cellattr:addstyle,sorttype:"local",stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=abnormalState"}},
			{name:'state',index:'state', width:60,editable: true,hidden:true},
			{name:'id',index:'id', width:60,editable: true,hidden:true}
			
		], 

		viewrecords : true,
		rowNum:10,
		rowList:[10,20,30],
		pager : pager_selector,
		altRows: true,
		//toppager: true,
		
		multiselect: true,
		//multikey: "ctrlKey",
        multiboxonly: true,

		loadComplete : function() {
			var table = this;
			
			$("#quality").click( function() {
				showReceive();
			})
			
			$("#model").click( function() {
				showModel();
			})
			
			$("#craft").click( function() {
				showCraft();
			})
			
			$("#device").click( function() {
				showDevice();
			})
			setTimeout(function(){
				styleCheckbox(table);
				getDate();//jqgrid加载完成后获取id,time 的集合对象
				change();
				updateActionIcons(table);
				updatePagerIcons(table);
				enableTooltips(table);
			}, 0);
		},

		editurl: "abnormal!delete.action",//nothing is saved
		caption: "异常清单"
	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size		
	
	
	function showReceive()
	{
		var title = "质量问题单";
		var width="800px";
		var height="632px";
		var id=$(".abnorId").val();
		var content="quality!sealist.action?abnorId="+id;
		
		layer.open({
	        type: 2,
	        skin: 'layui-layer-lan',
	        shift:2,
	        title: title,
	        fix: false,
	        shadeClose: true,
	        maxmin: true,
	        scrollbar: false,
	        area: [width, height],//弹出框的高度，宽度
	        content:content,
	        yes:function(index,layero){//刷卡保存
	        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象	        	
	        	return false;
	        },

	    });
		
	}
	
	
	function showModel()
	{
		var title = "工模维修单";
		var width="800px";
		var height="632px";
		var id=$(".abnorId").val();
		var content="model!sealist.action?abnorId="+id;
		
		layer.open({
	        type: 2,
	        skin: 'layui-layer-lan',
	        shift:2,
	        title: title,
	        fix: false,
	        shadeClose: true,
	        maxmin: true,
	        scrollbar: false,
	        area: [width, height],//弹出框的高度，宽度
	        content:content,
	        yes:function(index,layero){//刷卡保存
	        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象	        	
	        	return false;
	        },

	    });
		
		/*
		jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
			var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象			
			layer.close(index); 
		});*/
	}
	
	
	function showCraft()
	{
		var title = "工艺维修单";
		var width="800px";
		var height="632px";
		var id=$(".abnorId").val();
		var content="craft!sealist.action?abnorId="+id;
		
		layer.open({
	        type: 2,
	        skin: 'layui-layer-lan',
	        shift:2,
	        title: title,
	        fix: false,
	        shadeClose: true,
	        maxmin: true,
	        scrollbar: false,
	        area: [width, height],//弹出框的高度，宽度
	        content:content,
	        yes:function(index,layero){//刷卡保存
	        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象	        	
	        	return false;
	        },

	    });
		
		/*
		jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
			var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象			
			layer.close(index); 
		});*/
	}
	
	
	function showDevice()
	{
		var title = "设备维修单";
		var width="800px";
		var height="632px";
		var id=$(".abnorId").val();
		var content="device!sealist.action?abnorId="+id;
		
		layer.open({
	        type: 2,
	        skin: 'layui-layer-lan',
	        shift:2,
	        title: title,
	        fix: false,
	        shadeClose: true,
	        maxmin: true,
	        scrollbar: false,
	        area: [width, height],//弹出框的高度，宽度
	        content:content,
	        yes:function(index,layero){//刷卡保存
	        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象	        	
	        	return false;
	        },

	    });
		
		/*
		jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
			var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象			
			layer.close(index); 
		});*/
	}
	
	
	var array;
	function getDate(){
		var obj = $(grid_selector).jqGrid("getRowData");
		array = new Array();
		$(obj).each(function(i){
			if(this.state!=3 && this.state!=4){	
				
				time=parseInt(this.disposeTime);
		    	var obj = new Object();
		    	obj.id = this.id;
		    	obj.time = time;
		        array[i]=obj;
		        $(grid_selector).jqGrid('setCell',this.id,"disposeTime",getTime(time));
			}else{
				time=parseInt(this.disposeTime);
		    	var obj = new Object();
		    	obj.id = this.id;
		    	obj.time = 0;
		        array[i]=obj;
		        $(grid_selector).jqGrid('setCell',this.id,"disposeTime",getTime(time));
			}
						        			
		});

	}
	

	function change(){
		for(var i=0;i<array.length;i++){
			var obj = array[i];
		}
		setTimeout(function(){
			//此处用于更改array 的内容
			for(var i=0;i<array.length;i++){
				var obj = array[i];
				if(obj.time==0){
					
				}else{
					obj.time = obj.time+1;	
					$(grid_selector).jqGrid('setCell',obj.id,"disposeTime",getTime(obj.time));					
					array[i] = obj;
				}
				
			}
			 
			change();
		},1000);

	}
	
	
	/*//时间转换格式
	function changeTime(rowId, val, rawObject, cm,icol,rdata){			
			time=parseInt(rawObject.disposeTime);
			return getTime(time);		
	}*/
	
	function getTime(time){
		return formatTime(time);
	}
	
	function formatTime(second) {
	    return [parseInt(second / 60 / 60), parseInt(second / 60 % 60), second % 60].join(":")
	        .replace(/\b(\d)\b/g, "0$1");
	}
		
	function aceSwitch( cellvalue, options, cell ) {
		setTimeout(function(){
			$(cell) .find('input[type=checkbox]')
				.addClass('ace ace-switch ace-switch-5')
				.after('<span class="lbl"></span>');
		}, 0);
	}

	function pickDate( cellvalue, options, cell ) {
		setTimeout(function(){
			$(cell) .find('input[type=text]')
					.datepicker({format:'yyyy-mm-dd' , autoclose:true}); 
		}, 0);
	}
	
	
	//给状态加样式
	function addstyle(rowId, val, rawObject, cm, rdata)
	{
		//未响应
		if(rawObject.state=="0")
		{
			return "style='color:red;'";
		}
		
		//未完全响应
		if(rawObject.state=="1")
		{
			return "style='color:#FF44AA;'";
		}
		
		//处理中
		if(rawObject.state=="2")
		{
			return "style='color:green;'";
		}
		//已关闭
		if(rawObject.state=="3")
		{
			return "style='color:#AAAAAA;'";
		}
		
		//已撤销
		if(rawObject.state=="4")
		{
			return "style='color:#000000;'";
		}
	}


	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 
			edit: false,		   
			editicon : 'ace-icon fa fa-pencil blue',
			add: false,			
			addicon : 'ace-icon fa fa-plus-circle purple',
			del: false,
			/*delfunc:function(rowId){
				window.location.href="abnormal!delete.action?id="+rowId;
			},*/
			delicon : 'ace-icon fa fa-trash-o red',
			search: true,
			searchicon : 'ace-icon fa fa-search orange',
			refresh: false,
			refreshicon : 'ace-icon fa fa-refresh green',
			view: true,			
			viewicon : 'ace-icon fa fa-search-plus grey',
		},
		{
			//edit record form
			//closeAfterEdit: true,
			//width: 700,
			recreateForm: true,
			beforeShowForm : function(e) {
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
				style_edit_form(form);
			}
		},
		{
			//new record form
			//width: 700,
			closeAfterAdd: true,
			recreateForm: true,
			viewPagerButtons: false,
			beforeShowForm : function(e) {
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
				.wrapInner('<div class="widget-header" />')
				style_edit_form(form);
			}
		},
		{
			//delete record form
			recreateForm: true,
			beforeShowForm : function(e) {
				var form = $(e[0]);
				if(form.data('styled')) return false;
				
				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
				style_delete_form(form);
				
				form.data('styled', true);
			},
			onClick : function(e) {
				alert(1);
			}
		},
		{
			//search form
			recreateForm: true,
			afterShowSearch: function(e){
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
				style_search_form(form);
			},
			afterRedraw: function(){
				style_search_filters($(this));
			}
			,
			multipleSearch: true,
		
			multipleGroup:false,
			showQuery: true
			
		},
		{
			//view record form
			recreateForm: true,
			beforeShowForm: function(e){
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
			}
		}
	)


});



