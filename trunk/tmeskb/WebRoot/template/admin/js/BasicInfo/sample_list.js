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
		
		url:"sample!ajlist.action",
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
			{name:'xstate',index:'state',label:"状态",width:70,editable: false,cellattr:addstyle,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=sampleState"}},
			{name:'createDate',index:'createDate',label:"日期",width:130,editable: false,search:false,sorttype:"date",unformat: pickDate,formatter:datefmt},
			{name:'xproductnum',index:'workingBill.matnr',label:"产品编号",width:100,editable: false},
			{name:'xproductname',index:'workingBill.maktx',label:"产品名称",width:100,editable: false},
			{name:'xsampler',index:'sampler.name',label:"抽检人",width:100,editable: false,search:false},
			{name:'xcomfirmation',index:'comfirmation.name',label:"确认人",width:100,editable: false,search:false},
			{name:'sampleNum',index:'sampleNum',label:"抽检数量",width:90,editable: false,search:false},
			{name:'qulified',index:'qulified',label:"合格数量",width:90,editable: false,search:false},
			{name:'qulifiedRate',index:'qulifiedRate',label:"合格率",width:90,editable: false,search:false},
			{name:'xsampletype',index:'sampleType',label:"抽检类型",width:90,editable: false,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=sampleType"}}
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

		editurl: "sample!delete.action",//用它做标准删除动作
		caption: "抽检单"
	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
	//navButtons
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 	//navbar options
			edit: false,
			//editicon : 'ace-icon fa fa-pencil blue',
			//add: true,
			addfunc:function(rowId){
				var workingBillId = $("#workingBillId").val();
				window.location.href="sample!add.action?workingBillId="+workingBillId;
			},
			addicon : 'ace-icon fa fa-plus-circle purple',
			del: true,
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
	
	//按钮事件
	btn_event();
});

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
		return "style='color:red;font-weight:bold;'";
	}
}

//按钮事件
function btn_event()
{
	var wbId=$("#wbId").val();
	//创建抽检单
	$("#btn_creat").click(function(){
		window.location.href="sample!add.action?wbId="+wbId;
	});
	//刷卡确认
	$("#btn_confirm").click(function(){
		var info=getId();
		if(info!=null&&info!="")
		{
			window.location.href="sample!myaction.action?info="+info+"&wbId="+wbId+"&my_id=1";
		}
	});
	//刷卡撤销
	$("#btn_revoke").click(function(){
		var info=getId();
		if(info!=null&&info!="")
		{
			window.location.href="sample!myaction.action?info="+info+"&wbId="+wbId+"&my_id=2";
		}
	});
	//返回
	$("#btn_back").click(function(){
		window.history.back();
	});
}

//获取jqGrid表中选择的条数--即数据的ids
function getId()
{
	return $("#grid-table").jqGrid("getGridParam","selarrrow");
}

