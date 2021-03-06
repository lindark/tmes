$register("jiuyi.admin.depart");
jQuery(function($) {
	myevent();
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	//resize to fit page size
	$(window).on('resize.jqGrid', function () {
		$(grid_selector).jqGrid( 'setGridWidth', $(".list_area").width() );
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
		
		url:"department!ajlist.action",
		datatype: "json",
		height: "300",//weitao 修改此参数可以修改表格的高度
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
		colModel:[
			{name:'id',index:'id', sorttype:"int",label:"ID", editable: false,hidden:true},
			{name:'deptCode',index:'deptCode',label:"部门编码",width:100,editable: true},
			{name:'deptName',index:'deptName',label:"部门名称",width:100,editable: true},
			{name:'xparentDept',index:'parentDept',label:"上级部门",width:100,editable: true},
			{name:'costcenter',index:'costcenter',label:"成本中心",width:100,editable: true},
			{name:'movetype',index:'movetype',label:"发料移动类型",width:100,editable: true},
			{name:'movetype1',index:'movetype1',label:"退料移动类型",width:100,editable: true},
			{name:'xdeptLeader',index:'deptLeader',label:"部门负责人",width:100,editable: true},
			{name:'createDate',index:'createDate',label:"创建日期",width:100,editable:true,search:false, sorttype:"date",formatter:datefmt},
			{name:'xcreater',index:'creater',label:"创建人",width:100,editable: true},
			{name:'xisWork',index:'isWork',label:"是否启用",width:100,editable: true}
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
        gridComplete : function() {
        	/*var ids = jQuery(grid_selector).jqGrid('getDataIDs');
         	 for ( var i = 0; i < ids.length; i++) {
         		 var cl = ids[i];
         		 be = "<a href='team!edit.action?id="+ids[i]+"'>[编辑]</a>";
         		 jQuery(grid_selector).jqGrid('setRowData', ids[i], { toedit : be });
         	 }*/
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

		editurl: "department!deletedept.action",//用它做标准删除动作
		caption: "部门管理"

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
		    editfunc:function(rowId){
			    window.location.href="department!editdept.action?id="+rowId;
		    },
			editicon : 'ace-icon fa fa-pencil blue',
			add: true,
			addfunc:function(){
				window.location.href="department!adddept.action";
			},
			addicon : 'ace-icon fa fa-plus-circle purple',
			del: true,
			/*delfunc:function(rowId){
				window.location.href="department!delete.action?id="+rowId;
			},*/
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

//初始化事件
function myevent()
{
	jiuyi.admin.depart.MenuBarToggle();
	$(window).resize(function(){
		var winwidth = $(window).width();//浏览器宽度
		if(winwidth < 480){//手机
			$("#toggleMenuBar").triggerHandler("click");
		}
	});
	$(window).triggerHandler('resize');
	
	//添加部门
	
	//修改部门
	
	//删除部门
}

/**
 * 显示隐藏左边部门选择功能
 */
jiuyi.admin.depart.MenuBarToggle = function(){
	var ishead=0;
	$("#toggleMenuBar").click(function(){
		if(ishead==0){
			$("#left_tree").hide();
		    $("#right_grid").css('margin-left',14);
		    $(this).removeClass("arrow_left").addClass("arrow_right");
			ishead=1;
		}else{
			$("#left_tree").show();
            $("#right_grid").css('margin-left',201);
            $(this).removeClass("arrow_right").addClass("arrow_left");
            ishead=0;
		}
		$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	});
	
}

/**
 * 初始化ztree
 */
jiuyi.admin.depart.initTree = function(nodes){
  jiuyi.admin.depart.tree(nodes,jiuyi.admin.depart.addHandle,jiuyi.admin.depart.editHandle,jiuyi.admin.depart.removeBeforeHandle,jiuyi.admin.depart.removeHandle,jiuyi.admin.depart.clickHandle,jiuyi.admin.depart.dragHandle);
};

/**
 * 添加部门
 */
/**
 * 修改部门
 */
/**
 * 删除部门
 */

/**
 * 点击部门
 */
jiuyi.admin.depart.clickHandle = function(treeNode){
	$("#grid-table").jqGrid('setGridParam',{
		url:"department!ajlist.action?deptid="+treeNode.id,
		datatype:"json",
		page:1
	}).trigger("reloadGrid");
}

/**
 * 加载ztree
 */
jiuyi.admin.depart.tree = function(nodes,addHandle,editHandle,removeBeforeHandle,removeHandle,clickHandle,dragHandle){
	var setting = {
			view: {
				addHoverDom: addHandle?addHoverDom: false,
				//removeHoverDom: removeHoverDom,//删除
				selectedMulti: false
			},
			edit: {
				enable: false,
				editNameSelectAll: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeDrag: beforeDrag,
				beforeEditName: editHandle,
				beforeRemove: beforeRemove,
				beforeRename: beforeRename,
				onRemove: onRemove,
				onRename: onRename,
				onClick: onClick
			}
		};	
	
	var log, className = "dark";
	function beforeDrag(treeId, treeNodes) {
		return false;
	}
	function beforeEditName(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		var zTree = $.fn.zTree.getZTreeObj("ingageTree");
		zTree.selectNode(treeNode);
		return confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
	}
	function beforeRemove(treeId, treeNode) {
		 var zTree = $.fn.zTree.getZTreeObj("ingageTree");
	        var nodes = zTree.getNodesByParam("pId", treeNode.id, null);
	        if(nodes && nodes.length > 0){
	            return false;
	        }
	        zTree.selectNode(treeNode);
	        var flag = false;
	        if(removeBeforeHandle){
	            flag = removeBeforeHandle(treeNode);
	        }
	        
	        return flag;
	}
	function onRemove(e, treeId, treeNode) {
		showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
	}
	function beforeRename(treeId, treeNode, newName) {
		className = (className === "dark" ? "":"dark");
		showLog("[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		if (newName.length == 0) {
			alert("节点名称不能为空.");
			var zTree = $.fn.zTree.getZTreeObj("ingageTree");
			setTimeout(function(){zTree.editName(treeNode)}, 10);
			return false;
		}
		return true;
	}
	function onRename(e, treeId, treeNode) {
		showLog("[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
	}
	function showLog(str) {
		if (!log) log = $("#log");
		log.append("<li class='"+className+"'>"+str+"</li>");
		if(log.children("li").length > 8) {
			log.get(0).removeChild(log.children("li")[0]);
		}
	}
	function getTime() {
		var now= new Date(),
		h=now.getHours(),
		m=now.getMinutes(),
		s=now.getSeconds(),
		ms=now.getMilliseconds();
		return (h+":"+m+":"+s+ " " +ms);
	}

	var newCount = 1;
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.id).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.id
			+ "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.id);
		if (btn) btn.bind("click", function(){
			if(addHandle) addHandle(treeNode);
			
			return false;
		});
	};
	function removeHoverDom(treeId, treeNode) {
		//$("#addBtn_"+treeNode.id).unbind().remove();
		//removeBeforeHandle(treeNode.id);
	};
	
	function onClick(event,treeId,treeNode){
		if(clickHandle){clickHandle(treeNode)}
	}
	
	function selectAll() {
		var zTree = $.fn.zTree.getZTreeObj("ingageTree");
		zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
	}
	
	$.fn.zTree.init($("#ingageTree"), setting, nodes);
};