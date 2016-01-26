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

     
    var type = $("#type").val();
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
		url:"dump!ajlist.action?type="+type,
		datatype: "json",
		//mtype:"POST",//提交方式
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
		//colNames:[ '物料凭证号','组件编码','组件名称','批次','组件数量','单位','来源库存地点','去向库存地点','过账日期','凭证年度', '确认人','状态'],
		colModel:[
		    {name:'id',index:'id',label:"ID",width:200,sortable:"true",sorttype:"text"},
			{name:'voucherId',index:'voucherId',label:"物料凭证号",width:200,sortable:"true",sorttype:"text"},
			{name:'matnr',index:'matnr',label:"组件编码",width:200,sortable:"true",sorttype:"text"},
			{name:'maktx',index:'maktx',label:"组件名称",width:200,sortable:"true",sorttype:"text"},
			{name:'menge',index:'menge',label:"组件数量",width:200,sortable:"true",sorttype:"text"},
			{name:'charg',index:'charg',label:"批次",width:200,sortable:"true",sorttype:"text"},
			{name:'deliveryDate',index:'deliveryDate',label:"过账日期",width:200,sortable:"true",sorttype:"date",unformat: pickDate,formatter:datefmtTwo},
			{name:'mjahr',index:'mjahr',label:"凭证年度",width:200,sortable:"true",sorttype:"text"},
			{name:'adminName',index:'adminName',label:"确认人",width:100,sortable:"true",sorttype:"text"},
			{name:'stateRemark',index:'state',label:"状态", width:100,cellattr:addstyle,sortable:"true",sorttype:"text",editable: true,search:true,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname=dumpState"}}

		], 
		//sortable:true,
		//sortname: "deliveryDate",
		//sortorder: "desc",
        loadonce:true,
		viewrecords : true,
		scroll:true,
		rowNum:999999,
		/*rowList:[10,20,30],
		pager : pager_selector,*/
		pager:pager_selector,
		pgbuttons:false,
		pginput:false,
		altRows: true,
		//toppager: true,
		
		multiselect: true,
		//multikey: "ctrlKey",
        multiboxonly: true,
        //footerrow: true,
        
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				styleCheckbox(table);
				
				updateActionIcons(table);
				updatePagerIcons(table);
				enableTooltips(table);
				checkTr(table);
			}, 0);
		},

		editurl: "dump!delete.action",//用它做标准删除动作
		caption: "转储记录"
		
	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size

	function checkTr(table){
		$(table).find("tr").bind("click",function(){
			var voucherId = $(this).find("td[aria-describedby='grid-table_voucherId']").attr("title");
			var $voucherIds = $(table).find("td[aria-describedby='grid-table_voucherId']");
			$voucherIds.each(function(){
				if($(this).attr("title") == voucherId){
					var id = $(this).parent().attr("id");
					alert(id);
					$("#grid-table").jqGrid('setSelection',id);
					
					
					
					
				}
			});
		})
		
	}
	
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
			edit: false,
			editfunc:function(rowId){
				var ids = $("#grid-table").jqGrid('getGridParam','selarrrow');
				if(ids.length>1){
					alert("请选择一条记录");
					return false;
				}
				var workingBillId = $("#workingBillId").val();
				window.location.href="pollingtest!edit.action?id="+rowId+"&workingBillId="+workingBillId;
			},
			editicon : 'ace-icon fa fa-pencil blue',
			add: false,
			del:false,
			search: false,
			searchicon : 'ace-icon fa fa-search orange',
			refresh: true,
			refreshicon : 'ace-icon fa fa-refresh green',
			view: true,
			viewicon : 'ace-icon fa fa-search-plus grey',
		},
		{
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

//给状态加样式
function addstyle(rowId, val, rowObject, cm, rdata)
{
	//未确认
	if(rowObject.state=="2")
	{
		return "style='color:red;font-weight:bold;'";
	}
	//已确认
	if(rowObject.state=="1")
	{
		return "style='color:#006400;font-weight:bold;'";
	}
	//已撤销
	if(rowObject.state=="3")
	{
		return "style='color:#d2b48c;font-weight:bold;'";
	}
}

//按钮事件
function btn_event()
{
	//返回
	$("#btn_back").click(function(){
		window.history.back();
	});
	//查看
/*	$("#btn_show").click(function(){
		var loginid=$("#loginid").val();//当前登录人的id
		var dumpId=$("#grid-table").jqGrid('getGridParam','selarrrow');
		var dumpId=rawObject.voucherId;
		alert(dumpId);
		if(dumpId.length <1){
			layer.msg("请选择一条记录!", {icon: 5});
			return false;
		}
		if(dumpId.length >1){
			layer.msg("请选择一条记录!", {icon: 5});
			return false;
		}
//		var url ="dump_detail!list.action?dumpId="+dumpId+"&loginid="+loginid; 
//		alert(url);
		window.location.href="dump_detail!list.action?loginid="+loginid+"&dumpId="+dumpId;
		
	});*/
	//刷卡确认
	$("#btn_confirm").click(function(){
		var loginid=$("#loginid").val();//当前登录人的id
		var dumpId=$("#grid-table").jqGrid('getGridParam','selarrrow');
		if(dumpId.length <1){
			layer.msg("请选择一条记录!", {icon: 5});
			return false;
		}
		var url = "dump!creditapproval.action?dumpId="+dumpId+"&loginid="+loginid;
		credit.creditCard(url,function(data){
			$.message(data.status,data.message);
			window.location.href = "dump!list.action?loginid="+loginid;
			//$("#grid-table").trigger("reloadGrid");	
		})
		/*$(".cbox").click(function(){
			var thistdValue = $(this).val();
			var $parentTable = $(this).parent().parent().parent();
			var $classTd = $parentTable.find(".cbox");
			if($(this).attr("checked")==true){
				$classTd.each(function(){
					var tdValue = $(this).val();
					if(thistdValue==tdValue){
						$(this).attr("checked",true);
					}
				});
			}else{
				$classTd.each(function(){
					var tdValue = $(this).val();
					if(thistdValue==tdValue){
						$(this).attr("checked",false);
					}
				});
			}
		});*/
		
//		var index = "";
//		var dumpId=$("#grid-table").jqGrid('getGridParam','selarrrow');
//		if(dumpId.length <1){
//			layer.msg("请选择一条记录!", {icon: 5});
//			return false;
//		}
//		$.ajax({	
//			url: "dump!creditapproval.action?dumpId="+dumpId,
//			//data: $(form).serialize(),
//			dataType: "json",
//			async: false,
//			beforeSend: function(data) {
//				$(this).attr("disabled", true);
//				index = layer.load();
//			},
//			success: function(data) {
//				layer.close(index);
//				$.message(data.status,data.message);
//				$("#grid-table").trigger("reloadGrid");
//			},error:function(data){
//				layer.close(index);
//				$.message("error","系统出现问题，请联系系统管理员");
//			}
//		});
		//window.location.href="dump!confirm.action?dumpId="+dumpId;
	});
}