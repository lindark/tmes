$register("ingage.admin.depart");
ingage.admin.depart.addHandle = function (treeNode){
     var url = "deptment!adds.action?deptment.parentDept="+treeNode.id;//weitao modify
        $.openPopupLayer({
            width: 650,
            url: url
        });
};

ingage.admin.depart.removeBeforeHandle = function (treeNode){

    var flag = false;
    var url ="deptment!delete.action?id="+treeNode.id;
    if(confirm("是否要删除部门【"+treeNode.name+"】?")){
        $.ajax({
            type: "post",
            url: url,
            async: false,
            success: function(responseText){
            	eval("var obj = "+responseText);
				$.message(obj.status,obj.message);
            	if(obj.status=="success")flag = true;
            	/*
                ingage.common.dealResponse(responseText,function(status,statusText){
                	alert(status+","+statusText);
                    if(status == 0){
                        flag = true;
                    }else{
                        ingage.common.errorResult(statusText)
                    }
                });
                */
            }
        });
    }
    return flag;
};
ingage.admin.depart.removeHandle = function (treeNode) {
    var url = pageContextPath + "/user-manage/delete-depart.action?decorator=ajaxformat&confirm=true&depart.id=" + treeNode.id;
    $.ajax({
      type: "post",
      url: url,
      async: false,
      success: function(responseText){
        ingage.common.dealResponse(responseText,function(status,statusText){
                if(status == 0){
                    ingage.common.successResult(statusText);
                }else{
                    ingage.common.errorResult(statusText)
                }
        });
      }
    });
};
ingage.admin.depart.editHandle = function (treeId, treeNode){
	
	var url = "deptment!edits.action?deptment.id="+treeNode.id;//weitao modify
    $.openPopupLayer({
        width: 550,
        url: url
    });
    return false;
};
ingage.admin.depart.clickHandle =function (treeNode){
    if(!treeNode.id){
        return false;
    }
    var url = "admin!list.action?departid="+treeNode.id;
    $.ajax({
      type: "post",
      url: url,
      async: false,
      success: function(responseText){
    	  //alert(responseText);
    	  $(".list_area").html(responseText);
      }
    });
    
    
    
//    var url = pageContextPath + "/user-manage/search-user.action?decorator=gridJson&confirm=true";
//    var postData = {};
//    if(treeNode.getParentNode() != null){
//        postData['deptment.id'] = treeNode.id;
//        postData['deptment.deptName'] = treeNode.name;
//        $("#userImpBtn").hide();
//        $("#userDisableImpBtn").hide();
//    }else{
//        postData['deptment.id'] = "";
//        postData['deptment.deptName'] = "";
//        $("#userImpBtn").show();
//        $("#userDisableImpBtn").show();
//    }
//    try{
//        $("#departName_span").text("【"+treeNode.name + "】管理员：");
//        //$("#status-select option:first").attr("selected","selected");//重置用户filter
//        $("#searchUser_input").val("").focus().blur();//重置按姓名搜索
//    }catch(e){}
//    $("#departId_hidden").val(treeNode.id);
//    $("#listGrid").jqGrid('setGridParam', {url:url});
//    $("#listGrid").jqGrid('setGridParam', {'page':1});
//    postData['innerUser.name'] = "";
//    postData['innerUser.status'] = "";
//    $("#listGrid").setGridParam({postData:postData});
//    $("#listGrid").trigger("reloadGrid");
};
ingage.admin.depart.dragHandle = function(newDepartJson){
    var url = pageContextPath + "/user-manage/dragDepart.action?decorator=ajaxformat&confirm=true";
    var result;
    $.post(url,{"newDepartJson":newDepartJson},function(responseText){
         result = ingage.common.dealModelWinResponse(responseText);
    });
    return result;
};
ingage.admin.depart.initTree = function(nodes){
   ingage.admin.common.tree(nodes,ingage.admin.depart.addHandle,ingage.admin.depart.editHandle,ingage.admin.depart.removeBeforeHandle,ingage.admin.depart.removeHandle,ingage.admin.depart.clickHandle,ingage.admin.depart.dragHandle);
};

ingage.admin.depart.initDepartList = function(){
    var url = pageContextPath + "/user-manage/search-depart.action?decorator=gridJson&confirm=true";
    var departGrid = $("#listGrid").jqGrid({
        datatype: "json",
        url: url,
        mtype: 'post',
        height:281,
        autowidth:true,
        colModel:[
                    {name:'departName',index:'departName',label:'部门名称', sortable:false},
                    {name:'departCode',index:'departCode',label:'部门编码', sortable:false},
                    {name:'departType',index:'departType',label:'部门类型', sortable:false},
                    {name:'departAdmin',index:'departAdmin',label:'管理员', sortable:false},
                    {name:'groupId',hidden:true}
                ],
        pager: 'listGridPager',
        rowNum:10,
        viewrecords: true,
        rowList:[10,20,30],
        jsonReader: {
            total:"pageCount",
            rows:"pageSize",
            page:"pageNo",
            records:"dataCount",
            root:"gridData",
            cell:"datacell"
        },
        prmNames : {
            page:"pagination.pageNo",
            rows:"pagination.pageSize"
        },
        multiselect: false
    });
    departGrid.jqGrid('navGrid', '#listGridPager', {edit:false,add:false,del:false,search:false});
};
ingage.admin.depart.validateDepart = function(){
     $.validity.start();
     $("#departName,#departType").require();
     return $.validity.end().valid;
};

ingage.admin.depart.departCreate = function(){

    var btnLoading = ingage.common.btnLoading({loadingEle:$("#btnCreateDepart")});
        $("#btnCreateDepart").click(function(){
            if(!ingage.admin.depart.validateDepart()){
                return false
            }
            $("#prev_a").hide();
            btnLoading.start();
            $('#createDepartForm').ajaxSubmit({success: dealFormResult});
        });
        function dealFormResult(responseText){
            $("#prev_a").show();
            btnLoading.stop();
            var responseTextJson = $.evalJSON(responseText);
            var status = responseTextJson.status;
            var statusText = responseTextJson.statusText;
            var departId =responseTextJson.departId;
            if(status != 0 && (typeof(departId) != "number")){
                ingage.common.errorResult(statusText)
            }else{
                 var zTree = $.fn.zTree.getZTreeObj("ingageTree");
                 var parentDepartId = $("#parentDepartId").val();
                 var treeNode = zTree.getNodeByParam("id", parentDepartId, null);
                 zTree.addNodes(treeNode, {id:departId, pId:treeNode.id, name:$("#departName").val(),depth:treeNode.depth + 1});
                 $.closePopupLayer('popup_layer');
                 ingage.common.successResult(statusText)
            }
        }
};
ingage.admin.depart.departModify = function(){
    	alert("OK");
        //$("#btnModifyDepart").click(function(){
        //    if(!ingage.admin.depart.validateDepart()){
        //        return false
        //    }
        //    $('#modifyDepartForm').ajaxSubmit({success: dealFormResult});
        //});
        function dealFormResult(responseText){
            var result = ingage.common.dealModelWinResponse(responseText)
            if(result){
                 var zTree = $.fn.zTree.getZTreeObj("ingageTree");
                 var treeNode = zTree.getNodeByParam("id", $("#departId").val(), null);
                 treeNode.name = $("#departName").val();
                 zTree.updateNode(treeNode);
            }
        }
};