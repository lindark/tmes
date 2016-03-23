
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
		
		url:"up_down!historylist.action",
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
			{name:'id',index:'id', label:"ID", sorttype:"int", editable: false,hidden:true},
			{name:'productDate',label:"生产日期",width:100,index:'productDate',editable:false},
			{name:'shiftx',label:"班次",width:50,index:'shift',editable:false},
			{name:'matnr',label:"组件编码",width:150,index:'matnr',editable:false},
			{name:'maktx',label:"组件描述",width:150,index:'maktx',editable:false},
			{name:'charg',label:"批次",width:100,index:'charg', editable: false},
			{name:'typex',label:"类型",width:100,index:'type',editable:false},
			{name:'uplgpla',label:"发出仓位",width:100,index:'uplgpla',editable:false},
			{name:'downlgpla',label:"接收仓位",width:100,index:'downlgpla',editable:false},
			{name:'lgort',label:"库存地点",width:100,index:'lgort', editable: false},
			{name:'dwnum',label:"数量",width:50,index:'dwnum', editable: false},
			{name:'tanum',label:"转储单号",width:100,index:'tanum', editable: false},
			{name:'tapos',label:"行项目号",width:100,index:'tapos', editable: false},
			{name:'createDate',label:"创建日期",width:150,index:'createDate',editable:false,formatter:datefmt},
			{name:'adminname',label:"确认人",width:100,index:'appvaladmin.name', editable: false},
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

		//editurl: "",//用它做标准删除动作
		caption: "超市领料历史单"

	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
	/*
	jQuery(grid_selector).jqGrid('columnChooser', {
		   done : function (perm) {
		      if (perm) {
		          // "OK" button are clicked
		          this.jqGrid("remapColumns", perm, true);
		          // the grid width is probably changed co we can get new width
		          // and adjust the width of other elements on the page
		          //var gwdth = this.jqGrid("getGridParam","width");
		          //this.jqGrid("setGridWidth",gwdth);
		          $(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
		      } else {
		          // we can do some action in case of "Cancel" button clicked
		      }
		   }
		});
	*/
	
	//navButtons
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 	//navbar options
			edit: false,
			editicon : 'ace-icon fa fa-pencil blue',
			add: false,
			addfunc:function(rowId){
				window.location.href="working_bill!add.action";
			},
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