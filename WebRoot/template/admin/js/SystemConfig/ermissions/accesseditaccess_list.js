
jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	//resize to fit page size
	$(window).on('resize.jqGrid', function () {
		$(grid_selector).jqGrid( 'setGridWidth', $(".pardiv").width() );
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
		
		url:"access_resource!acccesslist.action?id="+$("#id").val(),
		datatype: "json",
		height: "100%",//weitao 修改此参数可以修改表格的高度
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
	    	//sort:"orderBy",
	    	//order:"orderType"
	    	
	    },
		//colNames:[ 'ID','权限对象名称','权限对象类型', 'myac'],
		colModel:[
			{name:'accessFunction.accessObject.id', label:"ID", sorttype:"int",editable: true,hidden:true},
			{name:'accessFunction.accessResource.id', label:"ID", sorttype:"int",editable: true,hidden:true},
			{name:'accObjName',label:"权限对象名称",width:80,editable:false},
			{name:'type',label:"权限对象类型",width:150,editable:false},
			{name:'accessFunction.state',label:"状态",width:150,editable:true,edittype:"select",editoptions:{dataUrl:'dict!getDictByAcess.action?dict.dictname=accessobject_state',
				buildSelect:function(data){
					eval("var obj = "+data);
					
					var id = $(grid_selector).jqGrid('getGridParam','selrow');//获取选中的一行的id
					var rowData = $(grid_selector).jqGrid('getRowData',id);//根据id获取对象
					var html="<select name='accessFunction.state'>";
					html+="<option></option>"
					for(var i=0;i<obj.list.length;i++){
						var sthis = obj.list[i];
						html+="<option value="+sthis.dictkey+">"+sthis.dictvalue+"</option>"
					}
					html+="</select>"
					return html;
				}
			}},
			{name:'myac',label:"操作",index:'', width:80, fixed:true, sortable:false, resize:false,
				formatter:'actions', 
				formatoptions:{ 
					keys:true,
					delbutton: false,
					
					delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
					//editformbutton:true, editOptions:{recreateForm: true, beforeShowForm:beforeEditCallback}
				}
			}
		], 

		viewrecords : true,
		//rowNum:"all",
		//rowList:[10,20,30],
		//pager : pager_selector,
		altRows: true,
		//toppager: true,
		//sortname: 'type',
       // sortorder: "asc",
		multiselect: true,
		//multikey: "ctrlKey",
        multiboxonly: true,
        grouping:true, 
		groupingView : { 
			 groupField : ['type'],
			 groupColumnShow : [true],
			 groupDataSorted : true,
			 plusicon : 'fa fa-chevron-down bigger-110',
			 minusicon : 'fa fa-chevron-up bigger-110',
		     groupCollapse:true
		},
       
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				styleCheckbox(table);
				
				updateActionIcons(table);
				updatePagerIcons(table);
				enableTooltips(table);
			}, 0);
		},

		
		editurl: "access_resource!saveaccess.action",//用它做标准删除动作
		//caption: "权限对象",
		

	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
	//navButtons
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 	//navbar options
			edit: false,
			editicon : 'ace-icon fa fa-pencil blue',
			add: false,
			
			addicon : 'ace-icon fa fa-plus-circle purple',
			del: false,
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