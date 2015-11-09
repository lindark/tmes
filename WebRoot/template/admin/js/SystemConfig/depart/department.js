$register("jiuyi.admin.depart");
$(function(){
	jiuyi.admin.depart.MenuBarToggle();
	$(window).resize(function(){
		var winwidth = $(window).width();//浏览器宽度
		if(winwidth < 480){//手机
			$("#toggleMenuBar").triggerHandler("click");
		}
	});
	$(window).triggerHandler('resize');
})

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
jiuyi.admin.depart.addHandle = function(treeNode){
	var url = "department!add.action?pid="+treeNode.id;
	var $dom = $("#dialog-message");
	var flag = jiuyi.admin.browser.inputdata(url,$dom);
	var title = "新增部门";
	if(flag)
		jiuyi.admin.browser.dialog($dom,title,function(){
			var $departform = $("#departform");
			$departform.submit();
//			$.ajax({
//				url: $departform.attr("action"),
//				data: $departform.serialize(),
//				dataType: "json",
//				async: false,
//				beforeSend: function(data) {
//					$(this).attr("disabled", true);
//				},
//				success: function(data) {
//					$.tip(data.status, data.message);
//					alert(data.parentDept);
//					var zTree = $.fn.zTree.getZTreeObj("ingageTree");
//					zTree.addNodes(treeNode, {id:data.id,pId:data.parentDept,name:data.deptName});
//				}
//			});
		});
}
/**
 * 修改部门
 */
jiuyi.admin.depart.editHandle = function(treeId, treeNode){
	var url = "department!edit.action?id="+treeNode.id;
	var $dom = $("#dialog-message");
	var flag = jiuyi.admin.browser.inputdata(url,$dom);
	var title = "修改部门";
	if(flag)
		jiuyi.admin.browser.dialog($dom,title,function(){
			var $departform = $("#departform");
			$.ajax({
				url: $departform.attr("action"),
				data: $departform.serialize(),
				dataType: "json",
				async: false,
				beforeSend: function(data) {
					$(this).attr("disabled", true);
				},
				success: function(data) {
					$.tip(data.status, data.message);
					var zTree = $.fn.zTree.getZTreeObj("ingageTree");
					var node = zTree.getNodeByParam("id", data.id, null);
					node.name=data.deptName;
					zTree.updateNode(node);
				}
			});
		});
	
	return false;
}
/**
 * 删除部门
 */
jiuyi.admin.depart.removeBeforeHandle = function(treeNode){
	var flag = confirm("您确定要删除吗?");
	var isdel= false;
	if(flag){
		var url = "department!delete.action?id="+treeNode.id;
		$.ajax({
			url: url,
			//data: $departform.serialize(),
			dataType: "json",
			async: false,
			success: function(data) {
				$.tip(data.status, data.message);
				isdel = true;
			},error:function(data){
				alert("系统出现错误，请联系系统管理员");
			}
		});
	}
	return isdel;
}

/**
 * 点击部门
 */
jiuyi.admin.depart.clickHandle = function(treeNode){
	$("#grid-table").jqGrid('setGridParam',{
		url:"admin!ajlist.action?departid="+treeNode.id,
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
				removeHoverDom: removeHoverDom,
				selectedMulti: false
			},
			edit: {
				enable: true,
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
		$("#addBtn_"+treeNode.id).unbind().remove();
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