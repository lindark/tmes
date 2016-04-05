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
		
		url:"itermediate_test!ajlist.action?workingBillId="+$("#workingbill").val(),
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
		//colNames:['创建日期','创建人','确认人', '状态'],
		colModel:[
			{name:'createDate',index:'createDate',lwidth:400,label:"创建日期",editable:true, sorttype:"date",unformat: pickDate,formatter:datefmt},
			{name:'xcreateUser',index:'createUser.name', label:"创建人",width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},	
			{name:'productDate',index:'productDate',label:"生产日期", width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'shiftx',index:'shiftx',label:"班次", width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'xconfirmUser',index:'confirmUser.name',label:"确认人", width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},	
			{name:'stateRemark',index:'state', width:300, label:"状态",cellattr:addstyle,label:"状态",sorttype:"select", sortable:false,editable: false,search:true,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=pickState"}},		 
			{name:'state',index:'state', label:"state", editable: false,hidden:true}
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
			setTimeout(function(){
				styleCheckbox(table);
				
				updateActionIcons(table);
				updatePagerIcons(table);
				enableTooltips(table);
			}, 0);
		},

		editurl: "itermediate_test!delete.action",//用它做标准删除动作
		caption: "半成品巡检管理"

	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
	//给状态加样式
	function addstyle(rowId, val, rawObject, cm, rdata)
	{
		//未确认
		if(rawObject.state=="1")
		{
			return "style='color:red;font-weight:bold;'";
		}
		//已确认
		if(rawObject.state=="2")
		{
			return "style='color:green;font-weight:bold;'";
		}
		//已撤销
		if(rawObject.state=="3")
		{
			return "style='color:purple;font-weight:bold;'";
		}
	}
	
	
	
	//navButtons
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 	//navbar options
		    edit: false,
//		    editfunc : function(rowId) {
//		    	var ids = $("#grid-table").jqGrid('getGridParam','selarrrow');
//		    	if(ids.length>1){
//		    		alert("只能选择一条记录！");
//		    		return false;
//		    	}
//				//window.location.href = "products!edit.action?id=" + rowId;
//			},
			editicon : 'ace-icon fa fa-pencil blue',
			//add: true,
			addfunc:function(rowId){
				window.location.href="working_bill!add.action";
			},
			addicon : 'ace-icon fa fa-plus-circle purple',
			del: false,
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


