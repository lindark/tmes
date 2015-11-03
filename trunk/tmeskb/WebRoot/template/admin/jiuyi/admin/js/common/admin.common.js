$register("ingage.admin.common");
ingage.admin.common.tree = function(nodes,addHandle,editHandle,removeBeforeHandle,removeHandle,clickHandle,dragHandle){
var setting = {
            view: {
                addHoverDom: addHandle?addHoverDom: false,
                removeHoverDom: removeHoverDom,
                selectedMulti: false
            },
            edit: {
                enable: true,
                addTitle:"增加",
                removeTitle:"删除",
                renameTitle:"修改",
                showRemoveBtn: removeBeforeHandle ||removeHandle ?true:false,
                showRenameBtn :true,
                drag: {
                prev: dropPrev,
                inner: dropInner,
                next: dropNext
                 }
            },

            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                //renameHandle: editHandle,
            	beforeEditName: editHandle,
                beforeDrag: beforeDrag,
                beforeRemove: beforeRemove,
                onRemove: onRemove,
                beforeDrop: dragHandle?beforeDrop : false,
                onClick: onClick
            }
        };

	
function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.id).length>0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.id
	+ "' title='add node' onfocus='this.blur();'></span>";
    sObj.append(addStr);
    var btn = $("#addBtn_"+treeNode.id);
    if (btn) btn.bind("click", function(){
        if(addHandle) addHandle(treeNode);
    });
}
function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.id).unbind().remove();
}
function dropPrev(treeId, treeNode) {
    var pNode =treeNode.getParentNode();
    if (pNode && pNode.dropInner === false) {
        return false;
    } else {
        for (var i=0,l=curDragNodes.length; i<l; i++) {
            var curPNode = curDragNodes[i].getParentNode();
            if (curPNode && curPNode !== treeNode.getParentNode() && curPNode.childOuter === false) {
                return false;
            }
        }
    }
    return true;
}
function dropInner(treeId, treeNode) {
    if (treeNode && treeNode.dropInner === false) {
        return false;
    } else {
        for (var i=0,l=curDragNodes.length; i<l; i++) {
            if (!treeNode && curDragNodes[i].dropRoot === false) {
                return false;
            } else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== treeNode && curDragNodes[i].getParentNode().childOuter === false) {
                return false;
            }
        }
    }
    return true;
}
function dropNext(treeId, treeNode) {
    var pNode =treeNode.getParentNode();
    if (pNode && pNode.dropInner === false) {
        return false;
    } else {
        for (var i=0,l=curDragNodes.length; i<l; i++) {
            var curPNode = curDragNodes[i].getParentNode();
            if (curPNode && curPNode !== treeNode.getParentNode() && curPNode.childOuter === false) {
                return false;
            }
        }
    }
    return true;
}
function beforeRemove(treeId, treeNode) {
        //有子节点不让删除
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
var curDragNodes;
function beforeDrag(treeId, treeNodes) {
//有子节点不让拖拽
    var zTree = $.fn.zTree.getZTreeObj("ingageTree");
    var nodes = zTree.getNodesByParam("pId", treeNodes[0].id, null);
    if(nodes && nodes.length > 0){
        return false;
    }
    for (var i=0,l=treeNodes.length; i<l; i++) {
        if (treeNodes[i].drag === false) {
            curDragNodes = null;
            return false;
        } else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
            curDragNodes = null;
            return false;
        }
    }
    curDragNodes = treeNodes;
    return true;
}
function getAfterNodes(treeNode,sourceTreeNode){//获取目标节点的后面的节点,排除自己
    var parentNode = treeNode.getParentNode();
    var treeObj = $.fn.zTree.getZTreeObj("ingageTree");
    var nodes = treeObj.getNodesByParam("pId", parentNode.id, null);//获取父ID为目标ID的所有子
    var newnodes = new Array();
    if(nodes){
        for(var i=0,l=nodes.length; i<l; i++){
            if(nodes[i].order > treeNode.order && nodes[i].id != sourceTreeNode.id){//如果当前循环的节点的顺序比传入的顺序大，并且排除自己，存入新的nodes
                newnodes.push(nodes[i])
            }
        }
    }
    return newnodes;
}
function getAfterNodesAndTarget(treeNode,sourceTreeNode){//获取目标节点的后面的节点，包含自己
    var parentNode = treeNode.getParentNode();
    var treeObj = $.fn.zTree.getZTreeObj("ingageTree");
    var nodes = treeObj.getNodesByParam("pId", parentNode.id, null);//获取父ID为目标ID的所有子
    var newnodes = new Array();
    if(nodes){
        for(var i=0,l=nodes.length; i<l; i++){
            if(nodes[i].order >= treeNode.order && nodes[i].id != sourceTreeNode.id){//如果当前循环的节点的顺序比传入的顺序大，存入新的nodes
                newnodes.push(nodes[i])
            }
        }
    }
    return newnodes;
}
function getMaxOrder(treeNode){
    var treeObj = $.fn.zTree.getZTreeObj("ingageTree");
    var nodes = treeObj.getNodesByParam("pId", treeNode.id, null);//获取父ID为目标ID的所有子最大的orderId
    var flag = 0;
    if(nodes){
        for(var i=0,l=nodes.length; i<l; i++){
            if(nodes[i].order >= flag){//取节点
                flag = nodes[i].order;
            }
        }
    }
    return flag;
}
function beforeDrop(treeId, treeNodes, targetNode, moveType){
    if(targetNode.id == 1) return false;
    var newTargetId,newTargetDepth,newOrder,afternodes,afterArrays;
    if(moveType == "inner"){//如果往内部拖拽，父ID为目标ID，深度为目标深度+1,顺序为目标子节点顺序的最大值+1
        newTargetId = targetNode.id;
        newTargetDepth = targetNode.depth + 1;
        newOrder = getMaxOrder(targetNode) + 1;
    }
    else{//父ID为目标ID的父ID，深度为目标深度,顺序为目标节点的父节点的最大值+1
        newTargetId = targetNode.getParentNode().id;
        newTargetDepth = targetNode.depth;
        if(moveType == "prev"){//如果是放在目标上方，顺序为目标顺序，并且目标顺序和目标后面的都+1
            newOrder = targetNode.order;
            afternodes = ingage.admin.common.objToJson(getAfterNodesAndTarget(targetNode,treeNodes[0]),["id","pId","depth","order"]);
            afterArrays = eval(afternodes);
            for(var i=0;i<afterArrays.length;i++){
                afterArrays[i].order =  parseInt(afterArrays[i].order) + 1;
            }

        }
        if(moveType == "next"){//如果是放在目标下方，顺序为目标顺序+1，目标后面的都+1
            newOrder = targetNode.order + 1;
            afternodes = ingage.admin.common.objToJson(getAfterNodes(targetNode,treeNodes[0]),["id","pId","depth","order"]);
            afterArrays = eval(afternodes);
            for(var i=0;i<afterArrays.length;i++){
                afterArrays[i].order =  parseInt(afterArrays[i].order) + 1;
            }
        }

    }
    
    var ajaxjson = ingage.admin.common.objToJson(treeNodes,["id","pId","depth","order"]);
    var array = eval(ajaxjson);
    for(var i=0;i<array.length;i++){
        array[i].pId = newTargetId;
        array[i].depth = newTargetDepth;
        array[i].order = newOrder;
    }
    if(afterArrays && afterArrays.length > 0){
        array = array.concat(afterArrays);//合并两个数组
    }
    var newJson = $.toJSON(array);
    return dragHandle(newJson);//产生新JSON用来处理
}

function onRemove(e, treeId, treeNode) {
   if(removeHandle) {removeHandle(treeNode)}
}
function onClick(event, treeId, treeNode){
   if(clickHandle){clickHandle(treeNode)}
}
$.fn.zTree.init($("#ingageTree"), setting, nodes);
};
ingage.admin.common.tree.checkedTree = function(nodes){
    var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
    $.fn.zTree.init($("#ingageTree"), setting, nodes);
};
ingage.admin.common.tree.getCheckedNodes = function (){
        var treeObj = $.fn.zTree.getZTreeObj("ingageTree");
        var changeNodes =  treeObj.getCheckedNodes(true);
        return ingage.admin.common.objToJson(changeNodes,["id","pId","name","customizeFlg"]);
};
ingage.admin.common.objToJson = function (obj,keys){
    if(!obj)return;
    switch(obj.constructor){
        case Object:
            var str = "{";
            for(var o in obj){
                var ser = ingage.admin.common.objToJson(obj[o],keys);
                if(obj[o] && ser) {//如果key和value都不为空
                    if(keys && keys.constructor == Array){//匹配传入数组的key
                        for(var k in keys){
                           if(keys[k] != o) continue;
                            str += o + ":" + ser +",";
                        }
                    }else{
                         str += o + ":" + ser +",";
                    }
                }
            }
            if(str.substr(str.length-1) == ",")
                str = str.substr(0,str.length -1);
            return str + "}";
            break;
        case Array:
            var str = "[";
            for(var o in obj){
               var ser = ingage.admin.common.objToJson(obj[o],keys);
               if(obj[o] && ser){
                   str += ser +",";
               }
            }
            if(str.substr(str.length-1) == ",")
                str = str.substr(0,str.length -1);
            return str + "]";
            break;
        case Boolean:
            return "\"" + obj.toString() + "\"";
            break;
        case Date:
            return "\"" + obj.toString() + "\"";
            break;
        case Function:
            break;
        case Number:
            return "\"" + obj.toString() + "\"";
            break;
        case String:
            return "\"" + obj.toString() + "\"";
            break;
    }
};
ingage.admin.common.accordion = function(){
    $(".accordion").accordion({
            autoHeight: false,
            navigation: true,
            icons: false ,
            heightStyle: "content"
    });
};
ingage.admin.common.menulink = function(){
     $(".nc_menu").click(function() {
            $(".left_nav").find("li").removeClass("hover");
            $(this).parent().addClass("hover");
            var url = $(this).attr("url");
            var param = $(this).attr("param");
            param = eval('('+param+')');
            if(param!=null){
                $.extend(param,{decorator:'ajaxformat',confirm:true});
            }else{
                param = {'decorator':'ajaxformat','confirm':true};
            }
            $.post(
                    url,
                    param,
                    function(responseText) {
                        var responseTextJson = $.evalJSON(responseText);
                        var status = responseTextJson.status;
                        var statusText = responseTextJson.statusText;
                        var view = responseTextJson.data.view;
                        if (status == 0) {
                            $("#rightContent").html(view);
                        } else {
                            ingage.common.errorResult(statusText);
                        }
                    }
            )
        });
};
/**
 * 刷新后台主显示页面
 * @param url
 */
ingage.admin.common.refreshMain = function(url,param,callback){
    param = param || {};
    $.post(
            url,param,
            function(responseText) {
                var responseTextJson = $.evalJSON(responseText);
                var status = responseTextJson.status;
                var statusText = responseTextJson.statusText;
                var view = responseTextJson.data.view;
                if (status == 0) {
                    $("#rightContent").html(view);
                    if(callback) {
                        callback();
                    }
                } else {
                    ingage.common.errorResult(statusText);
                }
            }
    )
};
/**
 * 刷新后台主显示页面
 * @param url
 */
ingage.admin.common.refreshContent = function(url,param,el,callback){
    param = param || {};
    $.post(
        url,param,
        function(responseText) {
            var responseTextJson = $.evalJSON(responseText);
            var status = responseTextJson.status;
            var statusText = responseTextJson.statusText;
            var view = responseTextJson.data.view;
            if (status == 0) {
                el.html(view);
                if(callback) {
                    callback();
                }
            } else {
                ingage.common.errorResult(statusText);
            }
        }
    )
};

ingage.admin.common.openMasterWin = function(){
    var url = pageContextPath + "/industry-manage/toCreateIndustry.action";
    $.openPopupLayer({
        width: 535,
        url: url
    });
};
ingage.admin.common.manageMember = function(groupId,editable) {
    var url = pageContextPath + "/user-manage/to-groupMember.action?groupId="+groupId;
    if(typeof(editable)!= "undefined" && !editable){
        url +="&editable=false"
    }
    $.openPopupLayer({
        width: 450,
        url: url
    });
};

ingage.admin.common.loadContent = function(){
    $(".nc_menu").click(function(){
    var url = $(this).attr("url");
    var param = $(this).attr("param");
    $.post(
            url,
            {},
            function(responseText) {
                var responseTextJson = $.evalJSON(responseText);
                var status = responseTextJson.status;
                var statusText = responseTextJson.statusText;
                var view = responseTextJson.data.view;
                if (status == 0) {
                    $("#rightContent").html(view);
                } else {
                    ingage.common.errorResult(statusText);
                }
            }
    )
});
};

ingage.admin.common.ingageappLogout = function() {
    $.post(
        pageContextPath+"/admin-home/logout.action",
        function (responseText) {
            window.location.href = "/";
        }
    )
};