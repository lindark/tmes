

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


	function formatAufnr(cellvalue, options, rowObject){
		//var firstPoint=cellvalue.indexOf("000");
		var cellLength=cellvalue.length;
		var aufnrformat=cellvalue.substr(3,cellLength-1);
		
		return aufnrformat;
	}
	
	function formatMatnr(cellvalue, options, rowObject){
		//var firstPoint=cellvalue.indexOf("000");
		var cellLength=cellvalue.length;
		var matnrformat=cellvalue.substr(10,cellLength-10);
		
		return matnrformat;
	}
	
	
	function formatCreateTime(cellvalue, options, rowObject){
		var firstPoint=cellvalue.indexOf(".");
		var lastPoint=cellvalue.lastIndexOf(".");
		var timeformat=cellvalue.substring(firstPoint+1,lastPoint);
		var returnedTime=timeformat.replace(".",":");
		return returnedTime.replace(".",":");
	}
	
	
	function formatAccountDate(cellvalue, options, rowObject){
		var dateObj=JSON.stringify(cellvalue);
		var jsonDate=JSON.parse(dateObj);
		var dateDel= new Date(jsonDate.time);
		var year=dateDel.getFullYear() ;
		var month=dateDel.getMonth() + 1;
		var date=dateDel.getDate();
		var dateformat=year+"-"+month+"-"+date;
		return dateformat;
	}

	jQuery(grid_selector).jqGrid({
		
		url:"product_storage!ajlist.action",
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
			{name:'MBLNR',label:"凭证号",width:100,index:'MBLNR',editable:false},
			{name:'budat',label:"过账日期",width:100,index:'budat',editable:false},
			{name:'ZEILE',label:"物料凭证中的项目",width:100,index:'ZEILE',editable:false},
			{name:'CPUDT',label:"创建日期",width:100,index:'CPUDT',editable: false},
			{name:'CPUTM',label:"创建时间",width:100,index:'CPUTM', editable: false},
			{name:'matnr',label:"物料编码",width:100,index:'matnr',formatter:formatMatnr, editable: false},
			{name:'maktx',label:"物料名称",width:100,index:'maktx', editable: false},
			{name:'aufnr',label:"生产订单号",width:100,index:'aufnr',formatter:formatAufnr,editable: false},
			{name:'SGTXT',label:"文本",width:100,index:'SGTXT', editable: false},
			{name:'werks',label:"工厂",width:100,index:'werks', editable: false},
			{name:'bwart',label:"移动类型",width:100,index:'bwart', editable: false},
			{name:'menge',label:"入库数量",width:100,index:'menge', editable: false},
			{name:'charg',label:"批次",width:100,index:'charg', editable: false},
			{name:'lgort',label:"库存地点",width:100,index:'lgort', editable: false},
			{name:'WEMNG',label:"订单入库数",width:100,index:'WEMNG', editable: false},
			//{name:'ship',index:'ship',  editable: true,edittype:"select",editoptions:{value:"FE:FedEx;IN:InTime;TN:TNT;AR:ARAMEX"}},
			//{name:'note',index:'note',  sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"}} 
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
				
				//ceshi(table);
				
			}, 0);
		},

		editurl: "product_storage!delete.action",//用它做标准删除动作
		caption: "生产计划"

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
				window.location.href="product_storage!add.action";
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