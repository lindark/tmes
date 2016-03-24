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
		
		url:"deptpick!historylist.action",
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
		//colNames:['创建日期','创建人','确认人','物料凭证号','状态'],
		colModel:[
		    {name:'id',index:'id', label:"ID", sorttype:"int", editable: false,hidden:true},
			{name:'materialCode',index:'materialCode',search:false,label:"物料编码", width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'materialName',index:'materialName',search:false,label:"物料描述", width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'materialBatch',index:'materialBatch',search:false,label:"批次", width:100,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'repertorySite',index:'repertorySite',search:false,label:"库存地点", width:100,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'ex_mblnr',index:'ex_mblnr',search:false,label:"物料凭证号", width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'stockMount',index:'stockMount',search:false,label:"领用数量", width:100,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'createDate',index:'createDate',label:"创建日期",search:false,lwidth:400,abel:"创建日期",editable:true, sorttype:"date",unformat: pickDate,formatter:datefmt},
			{name:'xcreateUser',index:'createUser.name',label:"创建人",search:false, width:100,editable: true,editoptions:{size:"20",maxlength:"30"}},	
			{name:'xcomfirmUser',index:'comfirmUser.name',label:"确认人",search:false, width:100,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'xstate',index:'state', width:100,label:"状态",cellattr:addstyle,sortable:"true",sorttype:"text",editable: true,search:true,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=returnProState"}},
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

		editurl: "return_product!delete.action",//用它做标准删除动作
		caption: "部门领用记录"

	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
	//给状态加样式
	function addstyle(rowId, val, rawObject, cm, rdata)
	{
		
		//未确认
		if(rawObject.state=="notapproval")
		{
			return "style='color:red;font-weight:bold;'";
		}
		//已确认
		if(rawObject.state=="approval")
		{
			return "style='color:green;font-weight:bold;'";
		}
		//已撤销
		if(rawObject.state=="undone")
		{
			return "style='color:purple;font-weight:bold;'";
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
