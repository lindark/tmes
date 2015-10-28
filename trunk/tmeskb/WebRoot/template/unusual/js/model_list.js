var grid_data = 
[ 
	{id:"1",name:"Desktop Computer",note:"note",stock:"Yes",ship:"FedEx", sdate:"2007-12-03"},
	{id:"2",name:"Laptop",note:"Long text ",stock:"Yes",ship:"InTime",sdate:"2007-12-03"},
	{id:"3",name:"LCD Monitor",note:"note3",stock:"Yes",ship:"TNT",sdate:"2007-12-03"},
	{id:"4",name:"Speakers",note:"note",stock:"No",ship:"ARAMEX",sdate:"2007-12-03"},
	{id:"5",name:"Laser Printer",note:"note2",stock:"Yes",ship:"FedEx",sdate:"2007-12-03"},
	{id:"6",name:"Play Station",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
	{id:"7",name:"Mobile Telephone",note:"note",stock:"Yes",ship:"ARAMEX",sdate:"2007-12-03"},
	{id:"8",name:"Server",note:"note2",stock:"Yes",ship:"TNT",sdate:"2007-12-03"},
	{id:"9",name:"Matrix Printer",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
	{id:"10",name:"Desktop Computer",note:"note",stock:"Yes",ship:"FedEx", sdate:"2007-12-03"},
	{id:"11",name:"Laptop",note:"Long text ",stock:"Yes",ship:"InTime",sdate:"2007-12-03"},
	{id:"12",name:"LCD Monitor",note:"note3",stock:"Yes",ship:"TNT",sdate:"2007-12-03"},
	{id:"13",name:"Speakers",note:"note",stock:"No",ship:"ARAMEX",sdate:"2007-12-03"},
	{id:"14",name:"Laser Printer",note:"note2",stock:"Yes",ship:"FedEx",sdate:"2007-12-03"},
	{id:"15",name:"Play Station",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
	{id:"16",name:"Mobile Telephone",note:"note",stock:"Yes",ship:"ARAMEX",sdate:"2007-12-03"},
	{id:"17",name:"Server",note:"note2",stock:"Yes",ship:"TNT",sdate:"2007-12-03"},
	{id:"18",name:"Matrix Printer",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
	{id:"19",name:"Matrix Printer",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
	{id:"20",name:"Desktop Computer",note:"note",stock:"Yes",ship:"FedEx", sdate:"2007-12-03"},
	{id:"21",name:"Laptop",note:"Long text ",stock:"Yes",ship:"InTime",sdate:"2007-12-03"},
	{id:"22",name:"LCD Monitor",note:"note3",stock:"Yes",ship:"TNT",sdate:"2007-12-03"},
	{id:"23",name:"Speakers",note:"note",stock:"No",ship:"ARAMEX",sdate:"2007-12-03"}
];

var subgrid_data = 
[
 {id:"1", name:"sub grid item 1", qty: 11},
 {id:"2", name:"sub grid item 2", qty: 3},
 {id:"3", name:"sub grid item 3", qty: 12},
 {id:"4", name:"sub grid item 4", qty: 5},
 {id:"5", name:"sub grid item 5", qty: 2},
 {id:"6", name:"sub grid item 6", qty: 9},
 {id:"7", name:"sub grid item 7", qty: 3},
 {id:"8", name:"sub grid item 8", qty: 8}
];

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
		
		url:"model!ajlist.action",
		datatype: "json",
		height: "250",//weitao 修改此参数可以修改表格的高度
		jsonReader : {
	          repeatitems : false,
	          root:"list",
	          total:"pageCount",
	          records:"totalCount"
	        },
	    prmNames : {
	    	rows:"pager.pageSize"
	    },
		colNames:[ '时间','产品编号','产品名称', '种类', '班组','故障原因','维修人','状态'],
		colModel:[
			
			{name:'createDate',index:'createDate', width:60, sorttype:"int", editable: true,summaryType:'sum'},
			{name:'productCode',index:'productCode',width:60, editable:true, sorttype:"date",unformat: pickDate},
			{name:'productName',index:'productName', width:60,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'type',index:'type', width:60, editable: true,edittype:"checkbox",editoptions: {value:"Yes:No"},unformat: aceSwitch},
			{name:'teamId',index:'teamId', width:60, editable: true,edittype:"select",editoptions:{value:"FE:FedEx;IN:InTime;TN:TNT;AR:ARAMEX"}},
			{name:'faultCause',index:'faultCause', width:60, sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"}},
			{name:'fixer',index:'fixer', width:60, sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"}},
			{name:'isDel',index:'isDel', width:60, sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"}}		
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

		editurl: "/dummy.html",//nothing is saved
		caption: "工模维修单记录"

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


	//navButtons
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 	//navbar options
			edit: true,
			editicon : 'ace-icon fa fa-pencil blue',
			//add: true,
			addfunc:function(){
				location.href="model!add.action";
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
			/**
			multipleGroup:true,
			showQuery: true
			*/
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