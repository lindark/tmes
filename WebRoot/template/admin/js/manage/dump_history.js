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
		url:"dump!historylist.action",
		datatype: "json",
		//mtype:"POST",//提交方式
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
	    colModel:[
	  		    {name:'id',index:'id', label:"ID", sorttype:"int", editable: false,hidden:true},
	  		    {name:'productionDate',index:'productionDate',label:"生产日期",width:100,editable:true,editoptions:{size:"20",maxlength:"30"}},
	  		    {name:'xshift',index:'shift',search:false,label:"班次", width:50,editable: true,editoptions:{size:"20",maxlength:"30"}},
	  		    {name:'materialCode',index:'materialCode',search:false,label:"组件编码", width:150,editable: true,editoptions:{size:"20",maxlength:"30"}},
	  		    {name:'materialdes',index:'materialdes',search:false,label:"组件描述", width:150,editable: true,editoptions:{size:"20",maxlength:"30"}},
	  			{name:'allcount',index:'allcount',search:false,label:"组件总数量", width:150,editable: true,editoptions:{size:"20",maxlength:"30"}},
	  		    {name:'voucherId',index:'voucherId',search:false,label:"物料凭证号", width:150,editable: true,editoptions:{size:"20",maxlength:"30"}},
	  			{name:'createName',index:'createUser',label:"创建人",search:false, width:100,editable: true,editoptions:{size:"20",maxlength:"30"}},	
	  			{name:'adminName',index:'confirmUser',label:"确认人",search:false, width:100,editable: true,editoptions:{size:"20",maxlength:"30"}},
	  			{name:'createDate',index:'createDate',label:"创建日期",width:150,editable:true,search:false, sorttype:"date",unformat: pickDate,formatter:datefmt},
	  			{name:'stateRemark',index:'state', width:100,label:"状态",cellattr:addstyle,sortable:"true",sorttype:"text",editable: true,search:true,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=returnProState"}},
	  			{name:'state',index:'state', label:"state", editable: false,hidden:true}
	  			], 
		//sortable:true,
		//sortname: "deliveryDate",
		//sortorder: "desc",

		viewrecords : true,
		rowNum:10,
		rowList:[10,20,30],
		pager : pager_selector,
		altRows: true,
		//toppager: true,
		
		multiselect: true,
		//multikey: "ctrlKey",
        multiboxonly: true,
        //footerrow: true,
        
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				styleCheckbox(table);
				
				updateActionIcons(table);
				updatePagerIcons(table);
				enableTooltips(table);
			}, 0);
		},

		editurl: "dump!delete.action",//用它做标准删除动作
		caption: "物料调拨记录"
		
	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size

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
				var ids = $("#grid-table").jqGrid('getGridParam','selarrrow');
				if(ids.length>1){
					alert("请选择一条记录");
					return false;
				}
				var workingBillId = $("#workingBillId").val();
				window.location.href="pollingtest!edit.action?id="+rowId+"&workingBillId="+workingBillId;
			},
			editicon : 'ace-icon fa fa-pencil blue',
			add: false,
			del:false,
			search: true,
			searchicon : 'ace-icon fa fa-search orange',
			refresh: true,
			refreshicon : 'ace-icon fa fa-refresh green',
			view: true,
			viewicon : 'ace-icon fa fa-search-plus grey',
		},
		{
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

//给状态加样式
function addstyle(rowId, val, rowObject, cm, rdata)
{
	//未确认
	if(rowObject.state=="2")
	{
		return "style='color:red;font-weight:bold;'";
	}
	//已确认
	if(rowObject.state=="1")
	{
		return "style='color:#006400;font-weight:bold;'";
	}
	//已撤销
	if(rowObject.state=="3")
	{
		return "style='color:#d2b48c;font-weight:bold;'";
	}
}