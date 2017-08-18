var info="";
jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	var loginid=$("#loginid").val();//当前登录人ID
	//resize to fit page size
	$(window).on('resize.jqGrid', function () {
		$(grid_selector).jqGrid( 'setGridWidth', $(".page-content").width() );
    });
	//resize on sidebar collapse/expand
	var parent_column = $(grid_selector).closest('[class*="col-"]');
	$(document).on('settings.ace.jqGrid' , function(ev, event_name, collapsed) {
		if( event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed' ) {
			//setTimeout is for webkit only to give time for DOM changes and then redraw!!!
			setTimeout(function() {
				$(grid_selector).jqGrid( 'setGridWidth', parent_column.width() );
			}, 0);
		}
    });



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
		url:"sark!ajlist.action?loginid="+loginid,
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
		//colNames:[ '返修部位','返修数量','返修日期','责任工序','责任人/批次','创建人', '确认人','状态','状态-隐藏'],
		colModel:[
		    {name:'id',index:'id', sorttype:"int",label:"ID", editable: false,hidden:true},
			{name:'createDate',label:"创建日期",index:'createDate',width:120,sortable:"true",sorttype:"date",unformat: pickDate,formatter:datefmt},
			{name:'productDate',label:"生产日期",index:'productDate', width:120,sortable:"true",sorttype:"text"},
			{name:'xteamshift',label:"班次",index:'teamshift', width:80,sortable:"true",sorttype:"text"},
			{name:'xcreateUser',label:"创建人",index:'createUser.name', width:120,sortable:"true",sorttype:"text"},
			{name:'xconfirmUser',label:"确认人",index:'confirmUser.name', width:120,sortable:"true",sorttype:"text"},
			{name:'revokedUser',index:'revokedUser',label:"撤销人",search:true, width:100,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'revokedTime',index:'revokedTime',label:"撤销时间",search:true, width:100,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'EX_MBLNR',label:"物料凭证",index:'EX_MBLNR', width:120,sortable:"true",sorttype:"text"},
			{name:'bktxt',label:"单据编号",index:'bktxt', width:120,sortable:"true",sorttype:"text"},
			{name:'xstate',label:"状态",index:'state', width:120,cellattr:addstyle,sortable:"true",sorttype:"text",editable: true,search:true,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=sarkState"}},
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
		editurl: "",//用它做标准删除动作
		caption: "衬板收货"

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
			return "style='color:#b22;font-weight:bold;'";
		}
		//已确认
		if(rawObject.state=="1")
		{
			return "style='color:#008B00;font-weight:bold;'";
		}
		//已撤销
		if(rawObject.state=="3")
		{
			return "style='color:#d2b48c;font-weight:bold;'";
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
				window.location.href="sark!edit.action?id="+rowId+"&workingBillId="+workingBillId;
			},
			editicon : 'ace-icon fa fa-pencil blue',*/
			add: false,
			/*addfunc:function(){
				var workingBillId = $("#workingBillId").val();
				window.location.href="sark!add.action?workingBillId="+workingBillId;
			},
			addicon : 'ace-icon fa fa-plus-circle purple',*/
			del: false,
			/*delfunc:function(rowId){
				window.location.href="sark!delete.action?id="+rowId;
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
	//创建报废单
	$("#btn_creat").click(function(){
		window.location.href="sark!add.action";
	});
	//刷卡确认
	$("#btn_confirm").click(function(){
		if(getId())
		{
			var rowData = $("#grid-table").jqGrid('getRowData',info);
			var row_state = rowData.state;
			if(row_state == "1" || row_state =="3"){
				layer.msg("已经确认或已经撤销的领料单无法再确认!",{icon:5});
				return false;
			}
			var url="sark!creditapproval.action?info="+info+"&my_id=1&loginid="+$("#loginid").val();
			sub_event(url);
		}
	});
	//刷卡撤销
	$("#btn_cancel").click(function(){
		if(getId())
		{
			var rowData = $("#grid-table").jqGrid('getRowData',info);
			var row_state = rowData.state;
			if(row_state =="3"){
				layer.msg("已经撤销的领料单无法再撤销!",{icon:5});
				return false;
			}
			var url="sark!creditundo.action?info="+info+"&my_id=2&loginid="+$("#loginid").val();
			sub_event(url);
		}
	});
	//返回
	$("#btn_back").click(function(){
		window.history.back();
	});
	//编辑
	$("#btn_edit").click(function(){
		if(getId2())
		{
			var rowData = $("#grid-table").jqGrid('getRowData',info);
			var row_state=rowData.state;
			if(row_state=="1" || row_state=="3")
			{
				layer.alert("已确认或已撤销的纸箱收货单无法再编辑!",false);
			}
			else
			{
				window.location.href="sark!edit.action?id="+info;
			}
		}
	});
	//查看
	$("#btn_show").click(function(){
		if(getId2())
		{
			window.location.href="sark!show.action?id="+info;
		}
	});
}

//刷卡确认或撤销
function sub_event(url)
{
	credit.creditCard(url,function(data){
		$.message(data.status,data.message);
		$("#grid-table").trigger("reloadGrid");
	});
}

//获取jqGrid表中选择的条数--即数据的ids
function getId()
{
	info=$("#grid-table").jqGrid("getGridParam","selarrrow");
	if(info==null||info=="")
	{
		layer.alert("请选择至少一条纸箱收货记录!",false);
		return false;
	}
	return true;
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
		layer.alert("请选择一条纸箱收货记录!",false);
		return false;
	}
}