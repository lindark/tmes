
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
		
		url:"material!ajlist.action",
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
		colNames:[ '创建日期','展开层','产品编码','产品名称','项目','项目类别','溢出指示符','例外','组件编码','组件名称','组件单位','组件数量','批次','状态', ],
		colModel:[		
			{name:'createDate',index:'createDate',label:"创建日期",editable:true, sorttype:"date",unformat: pickDate,formatter:datefmt},
			{name:'spread',index:'spread', width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'productCode',index:'productCode', width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'materialType',index:'materialType', width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'project',index:'materialType', width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'projectType',index:'materialType', width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'runOver',index:'materialType', width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'exception',index:'materialType', width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'materialCode',index:'materialCode', width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'materialName',index:'materialName', width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'materialUnit',index:'materialUnit', width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'materialAmount',index:'materialAmount', width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'batch',index:'batch', width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'stateRemark',index:'stateRemark', width:200, sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"}}		 
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

		editurl: "material!delete.action",//用它做标准删除动作
		caption: "产品Bom"

	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
	//navButtons
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 	//navbar options
			edit: true,
			editicon : 'ace-icon fa fa-pencil blue',
			//add: true,
			addfunc:function(rowId){
				window.location.href="material!add.action";
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


});