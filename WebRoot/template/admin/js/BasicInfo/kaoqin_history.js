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
		
		url:"kaoqin!historylist.action",
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
		colModel:[
{name:'id',index:'id', label:"ID", sorttype:"int", editable: false,hidden:true,sortable:false},
{name:'cardNumber',index:'cardNumber',label:"员工卡号",width:80,editable: true,search:false,sortable:false},
{name:'empname',index:'empname',label:"姓名",width:100,editable: false,search:false,sortable:false},
{name:'workCode',index:'workCode',label:"工号",width:100,editable: false,search:false,sortable:false},
{name:'phoneNum',index:'phoneNum',label:"手机号",width:100,editable: false,search:false,sortable:false},
{name:'factory',label:"工厂",width:100,editable: false,search:false,sortable:false},
{name:'workshop',label:"车间",width:70,editable: false,search:false,sortable:false},
{name:'productdate',label:"生产日期",width:100,editable: false,search:false,sortable:false},
{name:'factoryUnitName',label:"单元",width:70,editable: false,search:false,sortable:false},
{name:'xteam',label:"班组",width:100,editable: false,search:false,sortable:false},
{name:'xclasstime',label:"班次",width:50,editable: false,search:false,sortable:false},
{name:'postname',label:"岗位",width:100,editable: false,search:false,sortable:false},
{name:'stationName',label:"工位",width:100,editable: false,search:false,sortable:false},
{name:'modelName',label:"模具组号",width:150,editable: false,search:false,sortable:false},
{name:'workName',label:"工作范围",width:100,editable: false,search:false,sortable:false},
{name:'tardyHours',label:"异常小时数",width:80,editable: false,search:false,sortable:false},
{name:'xworkState',index:'workState',label:"员工状态",width:80,editable: false,sortable:false,cellattr:addstyle,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname='adminworkstate'"}},
{name:'workState',index:'workState', label:"workState", editable: false,hidden:true}
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

		editurl: "return_product!delete.action",//用它做标准删除动作
		caption: "考勤记录"

	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
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
//给状态加样式
function addstyle(rowId, val, rowObject, cm, rdata)
{
	if(rowObject.workState=="2"||rowObject.workState=="5"||rowObject.workState=="6")
	{
		return "style='color:#008B00;font-weight:bold;'";
		
	}
	else
	{
		return "style='color:#b22;font-weight:bold;'";
	}
}
