$register("jiuyi.admin.depart");
$(function(){
})

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
	var url = "department!add.action?id="+treeNode.id;
	var $dom = $("#dialog-message");
	var flag = jiuyi.admin.depart.inputdata(url,$dom);
	if(flag)
		jiuyi.admin.depart.dialog($dom,function(){
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
					alert(data.status);
//					$deleteButton.attr("href", "javascript:void(0)")
//					if (data.status == "success") {
//						$idsCheckedCheck.parent().parent().remove();
//					}
//					$.tip(data.status, data.message);
				}
			});
		});
}
/**
 * 修改部门
 */
jiuyi.admin.depart.editHandle = function(treeId, treeNode){
	alert("修改");
}
/**
 * 删除部门
 */
jiuyi.admin.depart.removeBeforeHandle = function(rowId){
	//alert("shan'chu");
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
				beforeEditName: beforeEditName,
				beforeRemove: beforeRemove,
				beforeRename: beforeRename,
				onRemove: onRemove,
				onRename: onRename
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
			//var zTree = $.fn.zTree.getZTreeObj("ingageTree");
			//zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
			return false;
		});
	};
	function removeHoverDom(treeId, treeNode) {
		//$("#addBtn_"+treeNode.id).unbind().remove();
		removeBeforeHandle(treeNode.id);
	};
	function selectAll() {
		var zTree = $.fn.zTree.getZTreeObj("ingageTree");
		zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
	}
	
	$.fn.zTree.init($("#ingageTree"), setting, nodes);
};