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
		
		url:"dump!alllist.action?loginid="+$("#loginid").val(),
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
	    	sort:"pager.orderBy",
	    	order:"pager.orderType"
	    	
	    },
		//colNames:['创建日期','创建人','确认人','物料凭证号','状态'],
		colModel:[
		    {name:'id',index:'id', label:"ID", sorttype:"int", editable: false,hidden:true},
		    {name:'createDate',index:'createDate',label:"创建日期",width:150,editable:true,search:false, sorttype:"date",unformat: pickDate,formatter:datefmt},
		    {name:'productionDate',index:'productionDate',label:"生产日期",width:100,editable:true,editoptions:{size:"20",maxlength:"30"}},
		    {name:'xshift',index:'shift',search:false,label:"班次", width:50,editable: true,editoptions:{size:"20",maxlength:"30"}},
		    {name:'materialCode',index:'materialCode',search:false,label:"物料编码", width:150,editable: true,editoptions:{size:"20",maxlength:"30"}},
		    {name:'materialdes',index:'materialdes',search:false,label:"物料描述", width:150,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'voucherId',index:'voucherId',search:false,label:"物料凭证号", width:150,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'createName',index:'createUser',label:"创建人",search:false, width:100,editable: true,editoptions:{size:"20",maxlength:"30"}},	
			{name:'adminName',index:'confirmUser',label:"确认人",search:false, width:100,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'allcount',index:'allcount',search:false,label:"物料总数量", width:150,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'stateRemark',index:'state', width:100,label:"状态",cellattr:addstyle,sortable:"true",sorttype:"text",editable: true,search:true,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=returnProState"}},
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

		editurl: "",//用它做标准删除动作
		caption: "中转仓"

	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
	//给状态加样式
	function addstyle(rowId, val, rawObject, cm, rdata)
	{
		
		//已确认
		if(rawObject.state=="1")
		{
			return "style='color:#008B00;font-weight:bold;'";
		}
		//未确认
		if(rawObject.state=="2")
		{
			return "style='color:#b22;font-weight:bold;'";
		}
		//已撤销
		if(rawObject.state=="3")
		{
			return "style='color:#d2b48c;font-weight:bold;'";
		}
	}
	
	
	
	//navButtons
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 	//navbar options
		    edit: false,
//		    editfunc : function(rowId) {
//		    	var ids = $("#grid-table").jqGrid('getGridParam','selarrrow');
//		    	if(ids.length>1){
//		    		alert("只能选择一条记录！");
//		    		return false;
//		    	}
//				//window.location.href = "products!edit.action?id=" + rowId;
//			},
			editicon : 'ace-icon fa fa-pencil blue',
			add: false,
//			addfunc:function(rowId){
//				window.location.href="working_bill!add.action";
//			},
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

	//按钮事件
	btn_event();
});

//按钮事件
function btn_event()
{
	//创建中转仓
	add_event();
	//编辑中转仓
	edit_event();
	//查看中转仓
	show_event();
	//确认中转仓
	//confirm_event();
	//刷卡撤销
	undo_event();
	//返回
	return_event();
}

//创建中转仓
function add_event()
{
	$("#addzzc").click(function(){
		window.location.href="dump!beforetoadd.action";
	});
}

//编辑中转仓
function edit_event()
{
	$("#editzzc").click(function(){
		var id = "";
		id=$("#grid-table").jqGrid('getGridParam','selarrrow');
		if(id=="")
		{
			alert("请选择一条记录");
		}
		else if(id.length>1)
		{
			alert("只能选择一条记录");
		}
		else
		{
			var rowData = $("#grid-table").jqGrid('getRowData',id);
			var row_state = rowData.state;
			if(row_state == "1")
			{
				layer.msg("已经确认的无法再编辑!",{icon:5});
			}
			else if(row_state=="3")
			{
				layer.msg("已经撤销的无法再编辑!",{icon:5});
			}
			else
			{
				var url="dump!beforeeditbatch.action?id="+id;
				window.location.href=url;
			}				
		}		
	});
}

//查看中转仓
function show_event()
{
	$("#showzzc").click(function(){
		var id=$("#grid-table").jqGrid('getGridParam','selarrrow');
		if(id.length>1)
		{
			alert("只能选择一条记录！");
			return false;
		}
		if(id=="")
		{
			alert("请选择一条记录！");
			return false;
		}
		else
		{
			window.location.href="dump!toshow.action?id="+id;				
		}			
	});
}

//确认中转仓
/*function confirm_event()
{
	$("#editzzc").click(function(){
		id=$("#grid-table").jqGrid('getGridParam','selarrrow');
		if(id.length==0)
		{
			layer.msg("请选择一条记录!", {icon: 5});
		}
		else
		{
			var loginId = $("#loginid").val();//当前登录人的id
			var url="dump!creditreply.action?id="+id;
			credit.creditCard(url,function(data){
				$.message(data.status,data.message);
				$("#grid-table").trigger("reloadGrid");
			});
		 }
	});
}*/

//返回
function return_event()
{
	$("#returnzzc").click(function(){
		//window.history.back();
		window.location.href="admin!index.action";
	});
}

//编辑
/*function toedit(url)
{
	layer.open({
		type:2,
		title:"添加物料",
		//skin: 'layui-layer-lan',
		area: ["90%", "90%"],
		shade:0.52,
		shadeClose:false,
		move:false,
		content:url,
		closeBtn:1,
		btn:["关闭"]
	});
}*/

//刷卡撤销
function undo_event()
{
	$("#undozzc").click(function(){
		id=$("#grid-table").jqGrid('getGridParam','selarrrow');
		if(id.length==0)
		{
			layer.msg("请至少选择一条记录!", {icon: 5});
		}
		else
		{
			var loginId = $("#loginid").val();//当前登录人的id
			var url="dump!creditundo.action?id="+id;
			
			credit.creditCard(url,function(data){
				$.message(data.status,data.message);
				$("#grid-table").trigger("reloadGrid");
			});
		 }
	});
}