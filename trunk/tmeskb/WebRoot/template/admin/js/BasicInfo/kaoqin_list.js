//var isstartteam="";
var iscancreditcard="";
var iswork="";
jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
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
		
		url:"temp_kaoqin!ajlist.action?loginid="+$("#loginid").val()+"&sameTeamId="+$("#sameteamid").val(),
		datatype: "json",
		height: "100%",//weitao 修改此参数可以修改表格的高度
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
			{name:'id',index:'id', label:"ID", sorttype:"int", editable: false,hidden:true,sortable:false},
			{name:'cardNumber',index:'cardNumber',label:"员工卡号",width:100,editable: true,search:false,sortable:false},
			{name:'empname',index:'empname',label:"姓名",width:100,editable: false,search:false,sortable:false},
			{name:'workCode',index:'workCode',label:"工号",width:100,editable: false,search:false,sortable:false},
			{name:'phoneNum',index:'phoneNum',label:"手机号",width:100,editable: false,search:false,sortable:false},
			{name:'xteam',label:"班组",width:100,editable: false,search:false,sortable:false},
			{name:'factoryUnitName',label:"单元",width:100,editable: false,search:false,sortable:false},
			{name:'xstationval',label:"xstationval",width:100,editable: false,search:false,sortable:false,hidden:true},
			{name:'postname',label:"岗位",width:100,editable: false,search:false,sortable:false},
			{name:'stationName',label:"工位",width:100,editable: false,search:false,sortable:false},
			{name:'modelName',index:'modelNum',label:"模具组号",width:150,editable: false,search:false,sortable:false},
			{name:'workName',label:"工作范围",width:150,editable: false,search:false,sortable:false},
			{name:'xworkState',index:'workState',label:"员工状态",width:80,editable: false,sortable:false,cellattr:addstyle,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname='adminworkstate'"}},
			{name:'xworkHours',index:'workHours',label:"工作小时",width:80,editable: false,sortable:false,cellattr:addstyle,stype:"select",searchoptions:{dataUrl:"dict!getDict1.action?dict.dictname='workHours'"}},
			{name:'tardyHours',label:"异常小时数",width:100,editable: false,search:false,sortable:false},
			{name:'isdaiban',index:'isdaiban',label:"是否代班",width:80,editable: false,sortable:false},			
			{name:'toedit',label:"操作",width:80,search:false, sortable:false,sortable:false},
			{name:'workState',index:'workState', label:"workState", editable: false,hidden:true},
			{name:'modelNum',index:'modelNum', label:"modelNum", editable: false,hidden:true},
			{name:'stationCode',index:'stationCode', label:"stationCode", editable: false,hidden:true},
			{name:'workNum',index:'workNum', label:"workNum", editable: false,hidden:true},
			{name:'postCode',index:'postCode', label:"postCode", editable: false,hidden:true},
		], 
		viewrecords : true,
		rowNum:10000000,
		//rowList:[30],
		pager : pager_selector,
		altRows: true,
		//toppager: true,
		multiselect: true,
		//multikey: "ctrlKey",
        multiboxonly: true,
        gridComplete : function() {
        	 var ids = jQuery(grid_selector).jqGrid('getDataIDs');
        	 for ( var i = 0; i < ids.length; i++) {        		 
        		var cl = ids[i];        		
        		var rowData = $("#grid-table").jqGrid('getRowData',ids[i]);
        		var be = "<a onclick=edit_event('"+cl+"') href='javascript:void(0)'>[编辑]</a>";
        		
        		if(rowData.isdaiban =='N' )
        		{
        			var bstr="";
        			jQuery(grid_selector).jqGrid('setRowData', ids[i], { isdaiban : bstr });
        		}
        		else
        		{
        			var bstr="<span style='color:#b22;font-weight:bold;'>代班</span>";        			 
        			jQuery(grid_selector).jqGrid('setRowData', ids[i], { isdaiban : bstr });
        			be+= "<a onclick=remove_daiban('"+rowData.id+"') href='javascript:void(0)'>[移除]</a>";
            		
        		}
        		jQuery(grid_selector).jqGrid('setRowData', ids[i], { toedit : be });
        	 }
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
		editurl: "",//用它做标准删除动作
		caption: "员工信息"
	});
	$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
	//navButtons
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 	//navbar options
			edit: false,
			/*editfunc:function(rowId){
			    window.location.href="kaoqin!edit.action?id="+rowId;
		    },*/
			editicon : 'ace-icon fa fa-pencil blue',
			add: false,
			addfunc:function(rowId){
				window.location.href="";
			},
			//addicon : 'ace-icon fa fa-plus-circle purple',
			del: false,
			//delicon : 'ace-icon fa fa-trash-o red',
			search: false,
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
	
	/***=================================================================***/
	/***=================================================================***/
	//isstartteam=$("#isstartteam").val();
	iscancreditcard=$("#iscancreditcard").val();
	iswork=$("#iswork").val();
	//初始化操作事件
	//按钮事件
	btn_style();
	btn_event();
	//输入小时数是否合法--change事件
	$("#input_hours").change(function(){
		hours_event();
	});
	//导出Excel
	$("#btn_outexcel").click(function(){
		window.location.href="temp_kaoqin!outexcel.action?sameTeamId="+$("#sameteamid").val();
	});
});

//按钮事件
function btn_event()
{
	//岗位img_post
	$("#img_post").click(function(){
		post_event();
	});
	//添加代班人
	$("#btn_add").click(function(){
		addemp();
	});
	//开启考勤
	$("#btn_open").click(function(){
		startWorking();
	});
	//点击后刷卡
	$("#btn_clickandcredit").click(function(){
		clickandcredit();
	});
	//返回
	$("#btn_back").click(function(){
		window.history.back();
	});
	//下班
	$("#btn_gooffwork").click(function(){
		gooffwork_event();
	});
	$("#submitButton").click(function(){
		selectWorkHours();
	});
}

//输入误工小时数是否合法--change事件
function hours_event()
{
	var hours=$("#input_hours").val();
	if(hours!=null&&hours!="")
	{
		var reg=/^[0-9]+(\.[0-9]+)?$/;//整数或小数
		if(!reg.test(hours))
		{
			layer.alert("输入不合法!",false);
			$("#input_hours").val("");
		}
		else
		{
			//hours=setScale(hours,0,"");//精度
			$("#input_hours").val(hours);
		}
	}
}

//编辑事件
function edit_event(rowId)
{
	//console.log(rowId);
	var data = $("#grid-table").jqGrid('getRowData',rowId);
	//console.log(data);	
	
	$("#kq-id").val(data.id); 
	$("#kq-name").html(data.empname);
	$("#phoneNum").val(data.phoneNum); 
	$("#select_state").val(data.workState);
	$("#input_hours").val(data.tardyHours); 
	$("#span_postname").text(data.postname);
	$("#input_post").val(data.postCode);
	getandsetstation(data.postCode,data.stationCode);
	
	var xstations =data.stationCode.split(",");
	$('#sel_station').val(xstations);
	$("#sel_station").trigger("chosen:updated");
	
	var xmodels =data.modelNum.split(",");
	$('#model').val(xmodels);
	$("#model").trigger("chosen:updated");
	
	var xproducts =data.workNum.split(",");
	$('#product').val(xproducts);
	$("#product").trigger("chosen:updated");
	
	layer.open({
		type:1,
		title:"修改考勤",
		shade:0.52,
		shadeClose:false,
		move:false,
		area:["800px","420px"],
		content:$("#divbox"),
		closeBtn:1,
		btn:["修改提交","取消"],
		yes:function(){			
			var url="temp_kaoqin!updatetempkaoqin.action";
			var kqdata=$("#xform").serializeArray();
			upd_event(url,kqdata);
		}
	
	});
}

//确认修改
function upd_event(url,kqdata)
{
	$.ajax({	
		url: url,
		data: kqdata,
		dataType: "json",
		async: false,
		beforeSend: function(data) {
			$(this).attr("disabled", true);
			index = layer.load();
		},
		success: function(data) {
			layer.closeAll();
			$.message("success","您的操作已成功!");
			$("#grid-table").trigger("reloadGrid");
		},error:function(data){
			layer.alert("您当前无权限!", {
		        icon:5,
		        skin:'error'
		    },function(){layer.closeAll();});
			//$.message("error","您当前权限不足,请联系系统管理员");
		}
	});
}

//添加员工信息
function addemp()
{
	if(iscancreditcard=="Y")
	{
		//考勤未开启,不可以点击及刷卡
		layer.alert("考勤未开启!",{icon:5,skin:'error'});
		return;
	}
	
	var title = "添加代班员工";
	var width="800px";
	var height="620px";
	var content="kaoqin!beforegetemp.action?info="+$("#sameteamid").val();
	var html="";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var info=iframeWin.getGridId();
		if(info!="baga")
		{
			layer.closeAll();
			$.ajax({
				url:"temp_kaoqin!addnewemp.action?ids="+info+"&sameTeamId="+$("#sameteamid").val()+"&loginid="+$("#loginid").val()+"&workHours="+$("#workHours").val(),
				dataType:"json",
				type:"post",
				data:{},
				success:function(data)
				{
					//1添加成功
					if(data.message=="1")
					{
						$.message("success","您的操作已成功!");
						$("#grid-table").trigger("reloadGrid");
					}
					else if(data.message=="2")
					{
						//2生产日期或班次为空,添加失败!
						layer.alert("生产日期或班次为空,添加失败!", {
					        icon:5,
					        skin:'error'
					    });
					}
					else if(data.message=="3")
					{
						//3系统出现异常!
						layer.alert("系统出现异常!", {
					        icon:5,
					        skin:'error'
					    });
					}
				}
			});
			/*setTimeout(function(){
				$.message("success","您的操作已成功!");
				$("#grid-table").trigger("reloadGrid");
			}, 1000);*/
		}
		/*else if(info=="error")
		{
			layer.alert("添加失败!", {
		        icon:5,
		        skin:'error'
		    },function(){layer.closeAll();});
		}*/
	});
}

//开启考勤
function startWorking()
{
	var $img_startkaoqi=$("#img_startkaoqin");
	var loginid=$("#loginid").val();//当前登录人的id
	if(iscancreditcard=="N")
	{
		return;
		layer.confirm("考勤已经开启,确定要关闭考勤吗?",{icon:3,btn:["确定","取消"]},function(){
			var url="kaoqin!creditreply.action?sameTeamId="+$("#sameteamid").val()+"&loginid="+loginid+"&my_id=2";
			credit.creditCard(url,function(data){
				if(data.status=="success")
				{
					$.message(data.status,data.message);
					iscancreditcard="Y";
					$("#span_startkaoqin").text("考勤未开启");
					$img_startkaoqi.attr("src","/template/admin/images/btn_close.gif");
					location.reload();
				}
				else if(data.status=="error")
				{
					$.message(data.status,data.message);
					
				}
			});
		},function(){
			
		});
	}
	else if(iscancreditcard=="Y")
	{
		var url="temp_kaoqin!creditreply.action?sameTeamId="+$("#sameteamid").val()+"&loginid="+loginid+"&my_id=1";
		credit.creditCard(url,function(data){
			if(data.status=="success")
			{
				$.message(data.status,data.message);
				iscancreditcard="N";
				iswork="Y";
				$("#span_startkaoqin").text("考勤已开启");
				$img_startkaoqi.attr("src","/template/admin/images/btn_open2.gif");
				//$("#grid-table").trigger("reloadGrid");
				location.reload();
			}
			else
			{
				layer.alert(data.message, {
			        icon:5,
			        skin:'error',
			        closeBtn:0
			    },function(){layer.closeAll();});
			}
		});
	}
}

//点击后刷卡
function clickandcredit()
{
	//考勤已开启,可以点击并刷卡
	if(iscancreditcard=="N")
	{
		var url="temp_kaoqin!creditapproval.action?sameTeamId="+$("#sameteamid").val()+"&loginid="+$("#loginid").val();
		credit.creditCard(url,function(data){
			if(data.status=="success")
			{
				$.message(data.status,data.message);
				$("#grid-table").trigger("reloadGrid");
			}
			else
			{
				layer.alert(data.message, {
			        icon:5,
			        skin:'error'
			    },function(){layer.closeAll();});
			}
		});
	}
	else if(iscancreditcard=="Y")
	{
		//考勤未开启,不可以点击及刷卡
		layer.alert("考勤未开启!",{icon:5,skin:'error'});
	}
}

//下班
function gooffwork_event()
{
	var $img_startkaoqi=$("#img_startkaoqin");
	if(iswork=="N")
	{
		layer.alert("班组未上班,无需下班!",{icon:5,skin:'error'});
	}
	else if(iswork=="Y")
	{
		
		$.ajax({
			url:"kaoqin!getDateAndShift.action?loginid="+$("#loginid").val(),
			dataType:"json",
			type:"post",
			data:{},
			success:function(data)
			{
				//提示
				if(data.status=="success")
				{
					layer.confirm(data.message, {icon: 3,btn:["确定","取消"]},function(){
						var url="kaoqin!creditundo.action?sameTeamId="+$("#sameteamid").val()+"&loginid="+$("#loginid").val();
						credit.creditCard(url,function(data){
							if(data.status=="success")
							{
								if(data.info=="s")
								{
									$.message(data.status,data.message);
									iswork="N";
									iscancreditcard="Y";
									$("#span_startkaoqin").text("考勤未开启");
									$img_startkaoqi.attr("src","/template/admin/images/btn_close.gif");
									$("#grid-table").trigger("reloadGrid");
								}
								else
								{
									layer.alert(data.message,{icon:7,closeBtn:0,title:"提示"},function(){
										layer.closeAll();
										iswork="N";
										iscancreditcard="Y";
										$("#span_startkaoqin").text("考勤未开启");
										$img_startkaoqi.attr("src","/template/admin/images/btn_close.gif");
										$("#grid-table").trigger("reloadGrid");
									});
								}
							}
							else
							{
								layer.alert(data.message, {
							        icon:5,
							        skin:'error'
							    },function(){layer.closeAll();});
							}
						});
					},function(){
						
					});
				}
				else
				{
					//1生产日期或班次为空
					if(data.message=="1")
					{
						layer.alert("生产日期或班次为空,添加失败!", {
					        icon:5,
					        skin:'error'
					    });
					}
					else if(data.message=="2")
					{
						//3系统出现异常!
						layer.alert("系统出现异常!", {
					        icon:5,
					        skin:'error'
					    });
					}
				}
			}
		});
	}
}

//样式1，绿色
/*function sapn_stype1(obj)
{
	$("#"+obj).attr("style","color:#008B00;font-weight:bold;");
}
//样式2，红色
function sapn_stype2(obj)
{
	$("#"+obj).attr("style","color:#b22;font-weight:bold;");
}*/
//给状态加样式
function addstyle(rowId, val, rowObject, cm, rdata)
{
	
	if(rowObject.workState=="2"||rowObject.workState=="5"||rowObject.workState=="6")
	{
		return "style='color:#008B00;font-weight:bold;'";
		
	}
	else
	{
		return "style='color:#b22;font-weight:bold;'";
	}
}

//按钮样式
function btn_style()
{
	//添加代班人员
	var $btnadd=$("#btn_add");
	//鼠标移到按钮上时事件
	$btnadd.mouseover(function(){
		$(this).attr("style","background-color:#FF8C69;");
	});
	//鼠标移开按钮时事件
	$btnadd.mouseout(function(){
		$(this).attr("style","background-color:#FFD39B;");
	});
	//鼠标按下按钮事件
	$btnadd.mousedown(function(){
		$(this).attr("style","background-color:#FF8C69;height:50px;box-shadow:1px 3px 3px #CD8162;margin-top:8px;");
	});
	//鼠标按下之后抬起事件
	$btnadd.mouseup(function(){
		$(this).attr("style","background-color:#FF8C69;height:50px;box-shadow:3px 5px 3px #CD8162;margin-top:8px;");
	});
	
	//开启考勤
	btn_style_startkaoqin();
	var $btnopen=$("#btn_open");
	//鼠标移到按钮上时事件
	$btnopen.mouseover(function(){
		$(this).attr("style","background-color:#FF8C69;");
	});
	//鼠标移开按钮时事件
	$btnopen.mouseout(function(){
		$(this).attr("style","background-color:#FFD39B;");
	});
	//鼠标按下按钮事件
	$btnopen.mousedown(function(){
		$(this).attr("style","background-color:#FF8C69;height:52px;box-shadow:1px 3px 3px #CD8162;margin-top:10px;");
	});
	//鼠标按下之后抬起事件
	$btnopen.mouseup(function(){
		$(this).attr("style","background-color:#FF8C69;height:50px;box-shadow:3px 5px 3px #CD8162;margin-top:8px;");
	});

	//点击后刷卡
	var $clickandcredit=$("#btn_clickandcredit");
	//鼠标移到按钮上时事件
	$clickandcredit.mouseover(function(){
		$(this).attr("style","background-color:#FF8C69;");
	});
	//鼠标移开按钮时事件
	$clickandcredit.mouseout(function(){
		$(this).attr("style","background-color:#FFD39B;");
	});
	//鼠标按下按钮事件
	$clickandcredit.mousedown(function(){
		$(this).attr("style","background-color:#FF8C69;height:52px;box-shadow:1px 3px 3px #CD8162;margin-top:10px;");
	});
	//鼠标按下之后抬起事件
	$clickandcredit.mouseup(function(){
		$(this).attr("style","background-color:#FF8C69;height:50px;box-shadow:3px 5px 3px #CD8162;margin-top:8px;");
	});
	
	//刷卡下班
	var $gooffwork=$("#btn_gooffwork");
	//鼠标移到按钮上时事件
	$gooffwork.mouseover(function(){
		$(this).attr("style","background-color:#FF8C69;");
	});
	//鼠标移开按钮时事件
	$gooffwork.mouseout(function(){
		$(this).attr("style","background-color:#FFD39B;");
	});
	//鼠标按下按钮事件
	$gooffwork.mousedown(function(){
		$(this).attr("style","background-color:#FF8C69;height:52px;box-shadow:1px 3px 3px #CD8162;margin-top:10px;");
	});
	//鼠标按下之后抬起事件
	$gooffwork.mouseup(function(){
		$(this).attr("style","background-color:#FF8C69;height:50px;box-shadow:3px 5px 3px #CD8162;margin-top:8px;");
	});
}

//开启考勤按钮样式
function btn_style_startkaoqin()
{
	var $img_startkaoqi=$("#img_startkaoqin");
	//已经开启过考勤
	if(iscancreditcard=="N")
	{
		$("#span_startkaoqin").text("考勤已开启");
		$img_startkaoqi.attr("src","/template/admin/images/btn_open2.gif");
	}
	else if(iscancreditcard=="Y")
	{
		$("#span_startkaoqin").text("考勤未开启");
		$img_startkaoqi.attr("src","/template/admin/images/btn_close.gif");
	}
}


//岗位
function post_event()
{
	layer.open({
        type: 2,
        skin: 'layui-layer-lan',
        shift:2,
        title: "选择岗位",
        fix: false,
        shade: 0.5,
        shadeClose: true,
        maxmin: true,
        scrollbar: false,
        btn:['确认','取消'],
        area: ["1000px", "500px"],//弹出框的高度，宽度
        content:"post!beforegetpost.action",
        yes:function(index,layero){//确定
        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        	var info = iframeWin.getName();
        	if(info!="baga")
        	{
        		$("#input_post").val(info.postid);
            	$("#span_postname").text(info.postname);
            	//$("#span_workstation").text(info.station);
            	//获取对应的工位
            	getstation(info.postid);
            	layer.close(index);
        	}
        	return false;
        },
        no:function(index)
        {
        	layer.close(index);
        	return false;
        }
    });
	return false;
}

//获取工位
function getstation(postid)
{
	$.post("admin!getstationcode.action?postid="+postid,function(data){
		$("#sel_station option").remove();
		$("#sel_station").append(data.message);
		$("#sel_station").chosen();
		$("#sel_station").trigger("chosen:updated");
	},"json");
}
function getandsetstation(postid,ids)
{
	$.post("admin!getstationcode.action?postid="+postid,function(data){
		$("#sel_station option").remove();
		$("#sel_station").append(data.message);		
		var xstations =ids.split(",");
		$('#sel_station').val(xstations);
		$("#sel_station").chosen();
		$("#sel_station").trigger("chosen:updated");
	},"json");
}

function remove_daiban(id)
{
	$.ajax({
		url:"temp_kaoqin!removeDaiban.action?id="+id,
		dataType:"json",
		type:"post",
		data:{},
		success:function(data)
		{
			//1添加成功
			if(data.message=="1")
			{
				$.message("success","您的操作已成功!");
				$("#grid-table").trigger("reloadGrid");
			}
			else if(data.message=="2")
			{
				//2生产日期或班次为空,添加失败!
				layer.alert("生产日期或班次为空,添加失败!", {
			        icon:5,
			        skin:'error'
			    });
			}
			else if(data.message=="3")
			{
				//3系统出现异常!
				layer.alert("系统出现异常!", {
			        icon:5,
			        skin:'error'
			    });
			}
		}
	});
}

function selectWorkHours(){
	var workHours = $("#workHours option:selected").val();
	var productdate = $("#productDate").val();
	var classtime = $("#classtime").val();
	var teamid = $("#teamid").val();
	$.ajax({
		url:"temp_kaoqin!updateWorkHours.action",
		data:{"workHours":workHours,"productdate":productdate,"classtime":classtime,"teamid":teamid},
		dataType:"json",
		success: function(data) {
			layer.alert(data.message, {icon: 6},function(){
				var loginid = $("#loginid").val();
				window.location.href = "kaoqin!list.action?loginid="+loginid;
			}); 
		}
	});
}


