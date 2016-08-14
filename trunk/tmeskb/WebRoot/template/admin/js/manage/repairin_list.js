var info="";
jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	var workingBillId = $("#workingBillId").val();
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
		url:"repairin!ajlist.action?workingBillId="+workingBillId,
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
		//colNames:[ '返修收货数量','收货日期','创建人', '确认人','状态',''],
		colModel:[
		    {name:'id',index:'id', sorttype:"int",label:"ID", editable: false,hidden:true},
			{name:'receiveAmount',label:"返修收货数量",index:'receiveAmount', width:100},
			{name:'createDate',label:"收货日期",index:'createDate',width:100,sortable:"true",sorttype:"date",unformat: pickDate,formatter:datefmt},
			{name:'xrepairintype',label:"成品/组件",index:'repairintype', width:100,sortable:"true",sorttype:"text"},
			{name:'EX_MBLNR',label:"物料凭证",index:'EX_MBLNR', width:100,sortable:"true",sorttype:"text"},
			{name:'createName',label:"创建人",index:'createName', width:100,sortable:"true",sorttype:"text"},
			{name:'adminName',label:"确认人",index:'adminName', width:100,sortable:"true",sorttype:"text"},
			{name:'revokedUser',label:"撤销人",index:'revokedUser', width:100,sortable:"true",sorttype:"text"},
			{name:'revokedTime',label:"撤销时间",index:'adminName', width:100,sortable:"true",sorttype:"text"},
			{name:'stateRemark',label:"状态",index:'state', width:100,cellattr:addstyle,sortable:"true",sorttype:"text",editable: true,search:true,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=repairinState"}},
			{name:'state',index:'state', editable: false,hidden:true}

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
		editurl: "repairin!delete.action",//用它做标准删除动作
		caption: "返修收货记录"

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
	//给状态加样式
	function addstyle(rowId, val, rawObject, cm, rdata)
	{
		//未确认
		if(rawObject.state=="2")
		{
			return "style='color:red;font-weight:bold;'";
		}
		//已确认
		if(rawObject.state=="1")
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
			/*editfunc:function(rowId){
				var ids = $("#grid-table").jqGrid('getGridParam','selarrrow');
				if(ids.length>1){
					alert("请选择一条记录");
					return false;
				}
				var workingBillId = $("#workingBillId").val();
				window.location.href="carton!edit.action?id="+rowId+"&workingBillId="+workingBillId;
			},
			editicon : 'ace-icon fa fa-pencil blue',*/
			add: false,
			/*addfunc:function(){
				var workingBillId = $("#workingBillId").val();
				window.location.href="carton!add.action?workingBillId="+workingBillId;
			},
			addicon : 'ace-icon fa fa-plus-circle purple',*/
			del: false,
			/*delfunc:function(rowId){
				window.location.href="carton!delete.action?id="+rowId;
			},*/
			//delicon : 'ace-icon fa fa-trash-o red',
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

	//按钮事件
	btn_event();
});

//按钮事件
function btn_event()
{
	//编辑
	$("#btn_edit").click(function(){
		var loginid = $("#loginid").val();
		var id=$("#grid-table").jqGrid('getGridParam','selarrrow');
		if(id.length > 1||id==""||id==null){
			layer.msg("请选择一条记录!", {icon: 5});
			return false;
		}
		var workingBillId = $("#workingBillId").val();
		var rowData = $("#grid-table").jqGrid('getRowData',id);
		var row_state=rowData.state;
		if(row_state=="1"||row_state=="3"){
			layer.msg("已确认或已撤销的返修单无法再编辑!", {icon: 5});
		}else{
 			window.location.href="repairin!edit.action?workingBillId="+workingBillId+"&id="+id+"&loginid="+loginid;				
		}
		
	});
	//查看
	$("#btn_show").click(function(){
		if(getId2())
		{
			window.location.href="repairin!show.action?id="+info+"&workingBillId="+$("#workingBillId").val();
		}
	});
}
//得到1条id
function getId2()
{
	info=$("#grid-table").jqGrid("getGridParam","selarrrow");
	if(info.length==1)
	{
		return true;
	}
	else
	{
		layer.alert("请选择一条返修单！",false);
		return false;
	}
}