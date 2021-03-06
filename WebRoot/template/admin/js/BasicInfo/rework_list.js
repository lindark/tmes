jQuery(function($){
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	//var workingBillId=$("#workingBillId").val();
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

		
		url:"rework!ajlist.action?workingBillId="+$("#workingBillId").val(),
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
		//colNames:[ '创建人','确认人','责任人','返工次数','翻包数量','缺陷数量','状态', '是否完工'],
		colModel:[	
		    {name:'id',index:'id', label:"ID", sorttype:"int", editable: false,hidden:true},
			{name:'xcreateUser',index:'createUser.name',label:"创建人",search:false, width:150,sortable:"true",sorttype:"text"},
			{name:'xconfirmUser',index:'confirmUser.name',label:"确认人",search:false, width:150,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'xduty',index:'duty.name',label:"责任人",search:false,width:150,sortable:"true",sorttype:"text"},
		  //{name:'reworkCount',index:'reworkCount',label:"返工次数",search:false,width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
		  //{name:'reworkAmount',index:'reworkAmount',label:"翻包数量",search:false, width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
		  //{name:'defectAmount',index:'defectAmount',label:"缺陷数量",search:false,width:200,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'stateRemark',index:'state',label:"状态",width:200, cellattr:addstyle,sortable:"true",sorttype:"text",editable: true,search:true,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=reworkState"}},	 
		  //{name:'isCompeletes',index:'isCompelete',width:200,label:"完工状态",sortable:"true",sorttype:"text",editable: true,search:true,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=isCompeletes"}}	
			{name:'createDate',index:'createDate',label:"创建日期",search:false,lwidth:400,abel:"创建日期",editable:true, sorttype:"date",unformat: pickDate,formatter:datefmt}
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

		editurl: "rework!delete.action",//用它做标准删除动作
		caption: "返工"

	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
	
	//给状态加样式
	function addstyle1(rowId, val, rawObject, cm, rdata)
	{
		
		//待确认
		if(rawObject.state=="Y")
		{
			return "style='color:red;font-weight:bold;'";
		}
		//已确认
		if(rawObject.state=="N")
		{
			return "style='color:green;font-weight:bold;'";
		}
		
	}
	
	//给状态加样式
	function addstyle(rowId, val, rawObject, cm, rdata)
	{
		//待确认
		if(rawObject.state=="1")
		{
			return "style='color:red;font-weight:bold;'";
		}
		//已确认
		if(rawObject.state=="2")
		{
			return "style='color:green;font-weight:bold;'";
		}
		//已返工
		if(rawObject.state=="3")
		{
			return "style='color:blue;font-weight:bold;'";
		}
		//已撤销
		if(rawObject.state=="4")
		{
			return "style='color:purple;font-weight:bold;'";
		}
		
	}
	
	
	//navButtons
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 	//navbar options
		   edit: false,
//		   editfunc : function(rowId) {
//			   var ids = $("#grid-table").jqGrid('getGridParam','selarrrow');
//		    	if(ids.length>1){
//		    		alert("只能选择一条记录！");
//		    		return false;
//		    	}
//		    	window.location.href = "rework!edit.action?id=" + rowId+"&workingBillId="+$("#workingBillId").val();
//			},
//			editicon : 'ace-icon fa fa-pencil blue',
			add: false,
//			addfunc:function(rowId){
//				window.location.href="rework!add.action";
//			},
//			addicon : 'ace-icon fa fa-plus-circle purple',
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
		       //alert(1);
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