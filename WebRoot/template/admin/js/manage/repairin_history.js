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
		
		url:"repairin!historylist.action",
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
		//colNames:[ 'ID','createDate','Name', 'Stock', 'Ship via','Notes'],
	    colModel:[
	  	        {name:'workingbillCode',index:'workingbill.workingbillCode',label:"随工单编号", width:80,sortable:"true",sorttype:"text"},
	  	        {name:'productDate',index:'workingbill.productDate', width:80,label:"生产日期",sortable:"true",sorttype:"text"},
	  	        {name:'matnr',index:'workingbill.matnr', width:80,label:"产品编号",sortable:"true",sorttype:"text"},
	  	        {name:'maktx',index:'workingbill.maktx', width:300,label:"产品名称",sortable:"true",sorttype:"text"},
	  	        
	  	        {name:'id',index:'id', label:"ID", sorttype:"int", editable: false,hidden:true},
	  	        {name:'receiveAmount',index:'receiveAmount',label:"收货数量", width:70},
	  	        {name:'EX_MBLNR',index:'EX_MBLNR',label:"物料凭证号", width:100},
	  			{name:'createDate',index:'createDate',label:"收货时间",width:100,sortable:"true",sorttype:"date",unformat: pickDate,formatter:datefmt},
	  			{name:'createName',index:'createName',label:"创建人", width:80,sortable:"true",sorttype:"text"},
	  			{name:'adminName',index:'adminName', label:"确认人",width:80,sortable:"true",sorttype:"text"},
	  			{name:'stateRemark',index:'state',label:"状态", width:80,cellattr:addstyle,sortable:"true",sorttype:"text",editable: true,search:true,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=repairinState"}}

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

		editurl: "repairin!delete.action",//用它做标准删除动作
		caption: "返修收货记录"

	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
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
			return "style='color:red;font-weight:bold;'";
		}
	}
	//navButtons
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 	//navbar options
			edit: false,
			add: false,
			del: false,
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
			location.href="repairin!showHistory.action?id="+id;
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
