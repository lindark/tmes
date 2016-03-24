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
		
		url:"sample!historylist.action",
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

			{name:'id',index:'id',label:"ID",search:true,sorttype:"int",editable:false,hidden:true,sortable:"true"},
			{name:'xproductnum',index:'workingBill.matnr',label:"产品编号",search:true,editable:false,sortable:"true",sorttype:"text"},
			{name:'xproductname',index:'workingBill.maktx',label:"产品名称",search:true,width:300,editable:false,sortable:"true",sorttype:"text"},
			{name:'sampleNum',index:'sampleNum',label:"抽检数量",search:true,editable:false,sortable:"true",sorttype:"text"},
			{name:'qulified',index:'qulified',label:"合格数量",search:true,editable:false,sortable:"true",sorttype:"text"},
			{name:'qulifiedRate',index:'qulifiedRate',label:"合格率",search:true,editable:false,sortable:"true",sorttype:"text"},
			{name:'sampleType',index:'sampleType',label:"抽检类型",search:true,editable:false,sortable:"true",sorttype:"text"},
			{name:'modifyDate',label:"日期",index:'modifyDate', search:true,editable: false,sortable:"true",sorttype:"date",unformat: pickDate,formatter:datefmt},
			{name:'xsampler',index:'sampler',label:"抽检人",search:true,editable:false,sortable:"true",sorttype:"text"},
			{name:'xcomfirmation',index:'comfirmation',label:"确认人",search:true,editable:false,sortable:"true",sorttype:"text"},
			{name:'state',index:'state', width:60,label:"状态",editable:false,cellattr:addstyle,sortable:"true",sorttype:"text",editable: true,sortable:"true",sorttype:"text",search:true,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=sampleState"}},	
			{name:'state',index:'state',label:"状态",search:true,editable:false,hidden:true},
//			{name:'xstate',index:'state', width:60,label:"状态",cellattr:addstyle,sortable:"true",sorttype:"text",editable: true,search:true,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=sampleState"}},	
//			{name:'state',index:'state', label:"state", editable: false,hidden:true}
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
		caption: "抽检记录"

	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
	//给状态加样式
	function addstyle(rowId, val, rawObject, cm, rdata)
	{
		//已确认
		if(rawObject.state=="已确认")
		{
			return "style='color:green;font-weight:bold;'";
		}
		//未确认
		if(rawObject.state=="未确认")
		{
			return "style='color:red;font-weight:bold;'";
		}
//		//已撤销
//		if(rawObject.stateRemark=="已撤销")
//		{
//			return "style='color:purple;font-weight:bold;'";
//		}
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
			location.href="pick_detail!historyView.action?id="+id;
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
