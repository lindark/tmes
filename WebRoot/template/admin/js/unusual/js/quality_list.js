var info="";
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
		
		url:"quality!ajlist.action",
		datatype: "json",
		height: "250",//weitao 修改此参数可以修改表格的高度
		jsonReader : {
	          repeatitems : false,
	          root:"list",
	          total:"pageCount",
	          records:"totalCount",
	          id:"id"
	        },
	    prmNames : {
	    	rows:"pager.pageSize",
	    	page:"pager.pageNumber",
		    search:"pager._search",
		    sort:"pager.orderBy",
		    order:"pager.orderType"
	    },
		colNames:[ '时间','产品名称','班组', '工序', '创建人','状态'],
		colModel:[
			
			{name:'createDate',index:'createDate', sorttype:"date",unformat: pickDate,formatter:datefmt,search:false},
			{name:'productsName',index:'productsName',width:60, editable:true},
			{name:'teamName',index:'teamName', width:160,editable: true},
			{name:'processName',index:'processName', width:160, editable: true},
			{name:'founder',index:'founder', width:60, editable: true,search:false},
			{name:'stateRemark',index:'state', width:60,editable: true, sortable:true,cellattr:addstyle}		
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

		editurl: "quality!delete.action",
		caption: "质量问题通知单记录"

	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
		
	function aceSwitch( cellvalue, options, cell ) {
		setTimeout(function(){
			$(cell) .find('input[type=checkbox]')
				.addClass('ace ace-switch ace-switch-5')
				.after('<span class="lbl"></span>');
		}, 0);
	}

	function pickDate( cellvalue, options, cell ) {
		setTimeout(function(){
			$(cell) .find('input[type=text]')
					.datepicker({format:'yyyy-mm-dd' , autoclose:true}); 
		}, 0);
	}

	//给状态加样式
	function addstyle(rowId, val, rawObject, cm, rdata)
	{
		//已提交
		if(rawObject.state=="0")
		{
			return "style='color:red;'";
		}
		
		//已回复
		if(rawObject.state=="1")
		{
			return "style='color:#FFBB66;'";
		}
		
		//已完结
		if(rawObject.state=="3")
		{
			return "style='color:green;'";
		}
	}


	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 
			edit:false,
			editicon : 'ace-icon fa fa-pencil blue',
			add: false,
			addicon : 'ace-icon fa fa-plus-circle purple',
			del: true,			
			delicon : 'ace-icon fa fa-trash-o red',
			search: true,
			searchicon : 'ace-icon fa fa-search orange',
			refresh: false,
			refreshicon : 'ace-icon fa fa-refresh green',
			view: false,	
			viewicon : 'ace-icon fa fa-search-plus grey',
		},
		{
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
	/*var wbId=$("#wbId").val();
	//创建抽检单
	$("#btn_creat").click(function(){
		window.location.href="sample!add.action?wbId="+wbId;
	});
	//刷卡确认
	$("#btn_confirm").click(function(){
		if(getId())
		{
			window.location.href="sample!confirmOrRevoke.action?info="+info+"&wbId="+wbId+"&my_id=1";
		}
	});
	//刷卡撤销
	$("#btn_revoke").click(function(){
		if(getId())
		{
			window.location.href="sample!confirmOrRevoke.action?info="+info+"&wbId="+wbId+"&my_id=2";
		}
	});*/
	//返回
	$("#btn_back").click(function(){
		window.history.back();
	});
	//刷卡编辑
	$("#btn_edit").click(function(){
		
		if(getId2())
		{
			var rowData = $("#grid-table").jqGrid('getRowData',info);
			var row_state=rowData.state;
			if(row_state=="3")
			{
				layer.alert("已完结的单据无法再编辑!",false);
			}
			else
			{
				window.location.href="quality!edit.action?id="+info;
			}
		}
	});
	//刷卡查看
	$("#btn_show").click(function(){
		
		if(getId2())
		{
			window.location.href="quality!view.action?id="+info;
		}
	});
}

//获取jqGrid表中选择的条数--即数据的ids
function getId()
{
	info=$("#grid-table").jqGrid("getGridParam","selarrrow");
	if(info==null||info=="")
	{
		layer.alert("请选择至少一条质量问题单！",false);
		return false;
	}
	return true;
}

//得到1条id
function getId2()
{
	info=$("#grid-table").jqGrid("getGridParam","selarrrow");
	if(info.length==1)
	{
		return true;
	}
	else
	{
		layer.alert("请选择一条质量问题单！",false);
		return false;
	}
}