
var grid_selector = "#grid-table";//jqgrid table ID
var pager_selector = "#grid-pager";//jqgrid pager ID

jQuery(function($) {
	grid_selector = "#grid-table";//jqgrid table ID
	pager_selector = "#grid-pager";//jqgrid pager ID
	windowResize();//处理屏幕自适应,用户处理移动端问题
	InitJqGrid();//初始化jqGrid
});

/**
 * jqgrid 自适应
 * author:weitao
 */
function windowResize(){
	//处理手机自适应，设置jqgrid表格的width等于父节点的width
	$(window).on('resize.jqGrid', function () {
		$(grid_selector).jqGrid( 'setGridWidth',$(".page-content").width() );//给jqgrid 的宽度 设定为 page-content的宽度
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
	
}

/**
 * 初始化jqgrid
 * author:weitao
 */
function InitJqGrid(){

	
	jQuery(grid_selector).jqGrid({
		url:"dict!ajlist.action",//json url
		datatype:"json",
		jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
            root: "list",  //包含实际数据的数组  
            repeatitems : false //true:采用jqgrid 标准的json格式,false采用自定义格式     
        }, 
		//datatype: "local",
		height: 250,//weitao 修改此参数可以修改表格的高度
		colNames:[' ', 'ID','Dictname','deptname'],//TH 名称
		colModel:[ //设置 table td 的属性，
	
			{name:'myac',index:'', width:80, fixed:true, sortable:false, resize:false,
				formatter:'actions', 
				formatoptions:{ 
					keys:true,
					//delbutton: false,//disable delete button
					
					delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
					//editformbutton:true, editOptions:{recreateForm: true, beforeShowForm:beforeEditCallback}
				}
			},
			{name:'id',index:'id', width:60,sorttype:"text",editable: true},
//			{name:'name1',index:'name1', width:150,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'dictkey',index:'dictkey', width:150,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'dictdesp',index:'dictdesp', width:150,editable: true,editoptions:{size:"20",maxlength:"30"}}
		], 
		viewrecords : true,
		rowNum:10,  //每页显示数量
		rowList:[10,20,30], //可供选择的选项
		pager : pager_selector, //分页
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

		editurl: "dict!ajlist.action",//编辑/新增页面调用的url
		caption: "jqGrid 表格插件" //table 的标题

	});
	
	$(window).triggerHandler('resize.jqGrid');//页面加载jqgrid 调用 resize 事件,用于处理移动端屏幕自适应
	
	
	//navButtons
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 	//navbar options
			edit: true,
			editicon : 'ace-icon fa fa-pencil blue',
			//add: true,
			addfunc:function(){
				//location.href="admin_list.action";
				alert("OK");
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
}

	
	
	



	
	
	
	

	//enable search/filter toolbar
	//jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})
	//jQuery(grid_selector).filterToolbar({});


	
	
	
