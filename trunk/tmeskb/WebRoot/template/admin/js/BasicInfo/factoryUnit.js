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
		
		url:"factory_unit!ajlist.action",
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
		//colNames:['工厂名称','车间名称','单元编码','单元名称','工作中心','状态'/*,'操作'*/],
		colModel:[
			//{name:'id',index:'id', lable:"ID", sorttype:"int", editable: true,summaryType:'sum'},
			//{name:'createDate',index:'createDate',label:"创建日期",editable:true, sorttype:"date",unformat: pickDate,formatter:datefmt},
			{name:'factoryName',label:"工厂名称",index:'workShop.factory.factoryName', width:100,sortable:true,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'workShopName',label:"车间名称",index:'workShop.workShopName', width:100,sortable:true,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'factoryUnitCode',label:"单元编码",index:'factoryUnitCode', width:100,editable: true,sortable:true,editoptions:{size:"20",maxlength:"30"}},
			{name:'factoryUnitName',label:"单元名称",index:'factoryUnitName', width:100,editable: true,sortable:true,editoptions:{size:"20",maxlength:"30"}},
			{name:'psaddress',label:"配送地编码",index:'psaddress', width:100,editable: true,sortable:true,editoptions:{size:"20",maxlength:"30"}},
			{name:'psaddressdes',label:"配送地描述",index:'psaddressdes', width:100,editable: true,sortable:true,editoptions:{size:"20",maxlength:"30"}},
			{name:'warehouse',label:"线边仓编码",index:'warehouse', width:100,editable: true,sortable:true,editoptions:{size:"20",maxlength:"30"}},
			{name:'warehouseName',label:"线边仓描述",index:'warehouseName', width:100,editable: true,sortable:true,editoptions:{size:"20",maxlength:"30"}},
			{name:'psPositionAddress',label:"配送库存地点仓位",index:'psPositionAddress', width:100,editable: true,sortable:true,editoptions:{size:"20",maxlength:"30"}},
			{name:'workCenter',label:"工作中心",index:'workCenter', width:100,editable: true,sortable:true,editoptions:{size:"20",maxlength:"30"}},
			{name:'costcenter',label:"成本中心",index:'costcenter', width:100,editable: true,sortable:true,editoptions:{size:"20",maxlength:"30"}},
			{name:'xiscanrepair',label:"是否可以返修/返修收货",index:'iscanrepair', width:130, sortable:true,editable: true,sorttype:"local",stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=factoryUnitIscanrepair"}},
			{name:'stateRemark',label:"状态",index:'state', width:100, sortable:true,editable: true,sorttype:"local",stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=factoryUnitState"}}
			/*{name:'myac',width:200,fixed:true,sorttable:false,resize:false}*/
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

        gridComplete : function(){
        	  var ids = jQuery(grid_selector).jqGrid('getDataIDs');
        	  for(var i =0 ;i<ids.length;i++){
        	    var cl=ids[i];
        	    be= "<a href='factory_unit!editproducts.action?id="+ids[i]+"'>相关产品</a>";
        	    jQuery(grid_selector).jqGrid('setRowData',ids[i],{myac:be});
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

		editurl: "factory_unit!delete.action",//用它做标准删除动作
		caption: "单元管理"

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
			//edit: true,
		    editfunc:function(rowId){
		    	var ids=$("#grid-table").jqGrid('getGridParam','selarrrow');
				if(ids.length >1){
					alert("请选择一条记录");
					return false;
				}
			    window.location.href="factory_unit!edit.action?id="+rowId;
		    },
			editicon : 'ace-icon fa fa-pencil blue',
			//add: true,
			addfunc:function(){
				window.location.href="factory_unit!add.action";
			},
			addicon : 'ace-icon fa fa-plus-circle purple',
			del: true,
			/*
			delfunc:function(rowId){
				window.location.href="factory_unit!delete.action?id="+rowId;
			},*/
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