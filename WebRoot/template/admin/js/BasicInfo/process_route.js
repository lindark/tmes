jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
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
		
		url:"process_route!ajlist.action",
		datatype: "json",
		height: "250",//weitao 修改此参数可以修改表格的高度
		jsonReader : {
	          repeatitems : false,
	          root:"list",
	          total:"pageCount",
	          records:"totalCount",
	          id:"id"
	        },
	    prmNames : {
	    	rows:"pager.pageSize",
	    	page:"pager.pageNumber",
	    	search:"pager._search",
	    	sort:"pager.orderBy",
	    	order:"pager.orderType"
	    },
		//colNames:['创建日期','工厂编码','工厂名称','状态', ],
	    colModel:[	
			{name:'id',index:'id', sorttype:"int",label:"ID", editable: false,hidden:true},
			//{name:'productsCode',index:'productsCode',label:"产品编号",width:50, editable: false},
			//{name:'productsName',index:'productsName',label:"产品名称",width:200, editable: false},
			{name:'processCode',index:'processCode',label:"工序编码", width:50,editable: false,editoptions:{size:"20",maxlength:"30"}},
			{name:'processName',index:'processName',label:"工序短文本", width:50,editable: false,editoptions:{size:"20",maxlength:"30"}},	
			{name:'gstrp',index:'processroute.orders.aufnr',label:"生产订单号", width:50,editable: false,editoptions:{size:"20",maxlength:"30"}},
			{name:'gstrp',index:'processroute.orders.gstrp',label:"工艺开始日期", width:40,search:false,editable:false},
			{name:'gltrp',index:'processroute.orders.gltrp',label:"工艺结束日期", width:40,search:false,editable:false},
			{name:'matnr',index:'processroute.orders.matnr',label:"物料编码", width:50,editable: false,editoptions:{size:"20",maxlength:"30"}},
			{name:'maktx',index:'processroute.orders.maktx',label:"物料描述", width:120,editable: false,editoptions:{size:"20",maxlength:"30"}},
			{name:'aufpl',index:'aufpl',label:"工序号", width:50,editable: false,editoptions:{size:"20",maxlength:"30"}},
			{name:'steus',index:'steus',label:"控制码", width:50,editable: false,editoptions:{size:"20",maxlength:"30"}},
			{name:'workCenter',index:'workCenter',label:"工作中心", width:50,editable: false,editoptions:{size:"20",maxlength:"30"}},
			//{name:'processName',index:'processName',label:"工序短文本", width:50,editable: false,editoptions:{size:"20",maxlength:"30"}},			
			//{name:'sortcode',index:'sortcode',label:"排序码",width:50, editable: false},
			{name:'createDate',index:'createDate',label:"创建日期",width:100,search:false,editable:false, sorttype:"date",unformat: pickDate,formatter:datefmt}
			//{name:'version',index:'version', width:50,label:"版本号", sortable:false,editable: false,search:true}
			//{name:"showproducts",label:"相关产品",width:100,search:false, sortable:false, resize:false}
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
        
       gridComplete : function() {
          	 var ids = jQuery(grid_selector).jqGrid('getDataIDs');
          	 for ( var i = 0; i < ids.length; i++) {
          		 var cl = ids[i];
          		 be = "<a href='process!relevant.action?id="+ids[i]+"'>[查看]</a>";
          		 jQuery(grid_selector).jqGrid('setRowData', ids[i], { showproducts : be });
          	 }
          },
        
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				styleCheckbox(table);
				
				updateActionIcons(table);
				updatePagerIcons(table);
				enableTooltips(table);
			}, 0);
		},

		editurl: "process!delete.adction",//用它做标准删除动作
		caption: "工艺路线管理"

		//,autowidth: true,
//		,
//		grouping:true, 
//		groupingView : { 
//			 groupField : ['name'],
//			 groupDataSorted : true,
//			 plusicon : 'fa fa-chevron-down bigger-110',
//			 minusicon : 'fa fa-chevron-up bigger-110'
//		},
//		caption: "Grouping"
		

	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
	

	//enable search/filter toolbar
	//jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})
	//jQuery(grid_selector).filterToolbar({});


	//switch element when editing inline
	function aceSwitch( cellvalue, options, cell ) {
		setTimeout(function(){
			$(cell) .find('input[type=checkbox]')
				.addClass('ace ace-switch ace-switch-5')
				.after('<span class="lbl"></span>');
		}, 0);
	}
	//enable datepicker
	function pickDate( cellvalue, options, cell ) {
		setTimeout(function(){
			$(cell) .find('input[type=text]')
					.datepicker({format:'yyyy-mm-dd' , autoclose:true}); 
		}, 0);
	}


	//navButtons
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 	//navbar options
			edit: false,
		  editfunc:function(rowId){
			    window.location.href="process!edit.action?id="+rowId;
		    },
			editicon : 'ace-icon fa fa-pencil blue',
			add: false,
			addfunc:function(){
				window.location.href="process!add.action";
			},
			addicon : 'ace-icon fa fa-plus-circle purple',
			del: false,
			delfunc:function(rowId){
				window.location.href="process!delete.action?id="+rowId;
			},
			delicon : 'ace-icon fa fa-trash-o red',
			search: true,
			searchicon : 'ace-icon fa fa-search orange',
			refresh: true,
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

