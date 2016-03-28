$register("jiuyi.admin.depart");
jQuery(function($) {
	myevents();
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
		
		url:"admin!ajlistemp.action",
		datatype: "local",
		height: "300",//weitao 修改此参数可以修改表格的高度
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
			{name:'id',index:'id', label:"ID", sorttype:"int",editable: false,hidden:true},
			{name:'xdeptcode',label:"部门编码",width:80,fixed:true,index:'department.deptCode', editable: true},
			{name:'departName',label:"部门名称",width:100,fixed:true,index:'department.deptName', editable: true},
			{name:'xteam',label:"班组",width:100,fixed:true,index:'team.teamName', editable: true},
			{name:'workNumber',label:"工号",width:80,fixed:true,index:'workNumber',editable:true},
			{name:'name',label:"姓名",width:80,fixed:true,index:'name', editable: true},
			{name:'xparentAdmin',label:"直接上级",width:50,fixed:true,index:'parentAdmin.name', editable: true},
			{name:'cardNumber',label:"卡号",width:100,fixed:true,index:'cardNumber', editable: true},
			{name:'xpost',label:"岗位",width:100,fixed:true,index:'post.postName', editable: true},
			{name:'xworkscope',label:"模具组号",width:100,fixed:true,index:'', editable: true},
			//{name:'xstation',label:"工位",width:100,index:'', editable: true},
			{name:'xstation',label:"工作范围",width:100,fixed:true,index:'', editable: true},
			{name:'identityCard',label:"身份证号",width:100,fixed:true,index:'identityCard', editable: true},
			{name:'phoneNo',label:"联系电话",width:100,fixed:true,index:'phoneNo', editable: true},
			{name:'email',label:"E-mail",width:100,fixed:true,index:'email', editable: true},
			{name:'startWorkDate',index:'startWorkDate',label:"入职日期",width:100,fixed:true,editable:true,search:false, sorttype:"date",formatter:datefmt},
			{name:'xsex',label:"性别",width:50,fixed:true,index:'sex', editable: true},
			{name:'xisJob',label:"是否离职",width:50,fixed:true,index:'isJob', editable: true},
			{name:'createDate',index:'createDate',label:"创建日期",width:100,fixed:true,editable:true,search:false, sorttype:"date",formatter:datefmt},
			{name:'xempCreater',index:'empCreater.name',label:"创建人",width:80,fixed:true,editable: true}
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

		editurl: "admin!deleteempry.action",//用它做标准删除动作
		caption: "员工列表"

	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
	//navButtons
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 	//navbar options
			edit: true,
			editfunc:function(rowId){
			    window.location.href="admin!editry.action?id="+rowId;
		    },
			editicon : 'ace-icon fa fa-pencil blue',
			add: true,
			addfunc:function(){
				window.location.href="admin!addry.action";
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
function myevents()
{
	jiuyi.admin.depart.MenuBarToggle();
	$(window).resize(function(){
		var winwidth = $(window).width();//浏览器宽度
		if(winwidth < 480){//手机
			$("#toggleMenuBar").triggerHandler("click");
		}
	});
	$(window).triggerHandler('resize');
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
 * 点击部门
 */
jiuyi.admin.depart.clickHandle = function(treeNode){
	$("#grid-table").jqGrid('setGridParam',{
		url:"admin!ajlistemp.action?deptid="+treeNode.id,
		datatype:"json",
		page:1
	}).trigger("reloadGrid");
}
/**
 * 初始进入页面
 */
function tostartpage(url)
{
	$("#grid-table").jqGrid('setGridParam',{
		url:url,
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
				onExpand: onExpand,
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
	
	function onExpand(event,treeId,treeNode){
		var cookie = $.cookie("z_tree");
		var z_tree = null;  
        if(cookie){  
            z_tree = JSON.parse(cookie);  
        }  
          
        if(!z_tree){  
            z_tree = new Array();  
        }  
        if(jQuery.inArray(treeNode.id, z_tree)<0){  
            z_tree.push(treeNode.id);  
        }  
        $.cookie("z_tree", JSON.stringify(z_tree))
	}
	
	
	
	
	function selectAll() {
		var zTree = $.fn.zTree.getZTreeObj("ingageTree");
		zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
	}
	
	$.fn.zTree.init($("#ingageTree"), setting, nodes);
};
