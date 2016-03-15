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
		url:"odd_hand_over!historylist.action",
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
		colNames:[ '交接随工单号','产品名称','下班随工单号','生产日期','物料编码','物料名称',
		           '实际零头数交接数量','实际异常交接数量','实际物料数量','实际异常物料数量',
		           '物料凭证号','交接日期','提交人', '确认人','状态','状态-隐藏'],
		colModel:[
            {name:'workingBillCode',index:'workingBill.workingBillCode', width:80,sortable:"true",sorttype:"text"},
            {name:'maktx',index:'workingBill.maktx', width:240,sortable:"true",sorttype:"text"},
            {name:'afterWorkingCode',index:'afterWorkingCode', width:80,sortable:"true",sorttype:"text"},
            {name:'productDate',index:'workingBill.productDate', width:60,sortable:"true",sorttype:"text"},
            {name:'materialCode',index:'materialCode', width:60,sortable:"true",sorttype:"text"},
          	{name:'materialDesp',index:'materialDesp', width:80,sortable:"true",sorttype:"text"},
          	
          	
          	
          	{name:'actualHOMount',index:'actualHOMount', width:100,sortable:"true",sorttype:"text"},
          	{name:'unHOMount',index:'unHOMount', width:90,sortable:"true",sorttype:"text"},
          	{name:'actualBomMount',index:'actualBomMount', width:70,sortable:"true",sorttype:"text"},
          	{name:'unBomMount',index:'unBomMount', width:70,sortable:"true",sorttype:"text"},
          	
          	
          	
        	{name:'mblnr',index:'mblnr', width:80,sortable:"true",sorttype:"text"},
			{name:'createDate',index:'createDate',width:100,sortable:"true",sorttype:"date",unformat: pickDate,formatter:datefmt},
			{name:'submitName',index:'submitName', width:40,sortable:"true",sorttype:"text"},
			{name:'sureName',index:'sureName', width:40,sortable:"true",sorttype:"text"},
			{name:'stateRemark',index:'state', width:40,cellattr:addstyle,sortable:"true",sorttype:"text",editable: true,search:true,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=cartonState"}},
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
		editurl: "odd_hand_over!delete.action",//用它做标准删除动作
		caption: "历史零头数交接记录"

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
		//已提交
		if(rawObject.state=="1")
		{
			return "style='color:red;font-weight:bold;'";
		}
		//已确认
		if(rawObject.state=="2")
		{
			return "style='color:green;font-weight:bold;'";
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
	
	
	//查看
	$("#btn_show").click(function(){
		if(getId2())
		{
			location.href="daily_work!view.action?id="+id;
		}
	});


});



//获取jqGrid表中选择的条数--即数据的ids
function getId()
{
	id=$("#grid-table").jqGrid('getGridParam','selarrrow');
	if(id==null||id=="")
	{
		layer.msg("请选择一条记录!", {icon: 5});
		return false;
	}
	return true;
}

//得到1条id
function getId2()
{
	id=$("#grid-table").jqGrid('getGridParam','selarrrow');
	if(id.length==1)
	{
		return true;
	}
	else
	{
		layer.msg("请选择一条记录!", {icon: 5});
		return false;
	}
}