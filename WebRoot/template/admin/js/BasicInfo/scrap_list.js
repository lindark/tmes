var info="";
jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	var wbId=$("#wbId").val();
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
		
		url:"scrap!ajlist.action?wbId="+wbId,
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
		//colNames:[ 'ID','createDate','Name', 'Stock', 'Ship via','Notes'],
		colModel:[
			{name:'id',index:'id', label:"ID", sorttype:"int", editable: false,hidden:true},
			{name:'modifyDate',index:'modifyDate',label:"日期",width:150,editable: false,search:false,sorttype:"date",unformat: pickDate,formatter:datefmt},
			{name:'xcreater',index:'creater.name',label:"提交人",width:150,editable: false,search:false},
			{name:'xconfirmation',index:'confirmation.name',label:"确认人",width:150,editable: false,search:false},
			{name:'xstate',index:'state',label:"状态",width:150,editable: false,cellattr:addstyle,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=scrapState"}},
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
		editurl: "scrap!delete.action",//用它做标准删除动作
		caption: "报废单"
	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
	//navButtons
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 	//navbar options
			edit: false,
			//editicon : 'ace-icon fa fa-pencil blue',
			add: false,
			addfunc:function(rowId){
				var workingBillId = $("#workingBillId").val();
				window.location.href="sample!add.action?workingBillId="+workingBillId;
			},
			//addicon : 'ace-icon fa fa-plus-circle purple',
			del: false,
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

//给状态加样式
function addstyle(rowId, val, rowObject, cm, rdata)
{
	//未确认
	if(rowObject.state=="1")
	{
		return "style='color:#b22;font-weight:bold;'";
	}
	//已确认
	if(rowObject.state=="2")
	{
		return "style='color:#006400;font-weight:bold;'";
	}
	//已撤销
	if(rowObject.state=="3")
	{
		return "style='color:#d2b48c;font-weight:bold;'";
	}
}

//按钮事件
function btn_event()
{
	var wbId=$("#wbId").val();
	//创建报废单
	$("#btn_creat").click(function(){
		window.location.href="scrap!add.action?wbId="+wbId;
	});
	//刷卡确认
	$("#btn_confirm").click(function(){
		if(getId())
		{
			window.location.href="scrap!confirmOrRevoke.action?info="+info+"&wbId="+wbId+"&my_id=1";
		}
	});
	//刷卡撤销
	$("#btn_revoke").click(function(){
		if(getId())
		{
			window.location.href="scrap!confirmOrRevoke.action?info="+info+"&wbId="+wbId+"&my_id=2";
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
			if(row_state=="2"||row_state=="3")
			{
				layer.alert("已确认或已撤销的抽检单无法再编辑!",false);
			}
			else
			{
				window.location.href="scrap!edit.action?id="+info+"&wbId="+wbId;
			}
		}
	});
	//查看
	$("#btn_show").click(function(){
		if(getId2())
		{
			window.location.href="scrap!show.action?id="+info+"&wbId="+wbId;
		}
	});
}

//获取jqGrid表中选择的条数--即数据的ids
function getId()
{
	info=$("#grid-table").jqGrid("getGridParam","selarrrow");
	if(info==null||info=="")
	{
		layer.alert("请选择至少一条抽检记录！",false);
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
		layer.alert("请选择一条抽检记录！",false);
		return false;
	}
}

