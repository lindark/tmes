$(function() {
	var $storage = $("#storage");// 入库
	var $dump = $("#dump");// 转储
	var $repair = $("#repair");// 返修
	var $repairin = $("#repairin");// 返修收货
	var $pick = $("#pick");// 领退料
	var $qResponse = $("#qResponse");// 快速响应
	var $handoverprocess = $("#handoverprocess");// 交接
	var $dailywork = $("#dailywork");// 报工
	var $carton = $("#carton");// 纸箱收货
	var $rework = $("#rework");// 返工
	var $sample=$("#sample");//抽检
	var $halfinspection=$("#halfinspection");//半成品巡检
	var $pollingtest = $("#pollingtest");//巡检
	var $scrap=$("#scrap");//报废
	var $ckbox = $(".ckbox");//checkbox
	var $kaoqin=$("#kaoqin");//考勤
	var $endProduct = $("#endProduct");//成品入库
	var $returnProduct = $("#returnProduct");//退回中转仓
	var $table00 = $("#table00");//随工单table
	var $scenecheck=$("#scenecheck");//现场检验
	var $barcode = $(".barcode");//条码打印
	var $deptpk = $("#deptpk");//部门领料
	var $updown = $("#updown");//上架/下架
	var $locatHO = $("#locatHO");//线边仓
	var $csll = $("#csll");//超市领料
	var $processHO = $("#processHO");//工序交接
	
	$table00.find("tbody tr").click(function(){
		var battr = $(this).eq(0).find("input.ckbox").is(":checked");
		if(battr == false){
			$(this).eq(0).find("input.ckbox").prop("checked",true);
		}else if(battr == true){
			$(this).eq(0).find("input.ckbox").removeAttr("checked");
		}
		init.initCheckbox();
	});
	
	
	var init = {
			"isCheck":function(){//需要有选中来改变按钮的属性的
				var cklength = $(".ckbox:checked").length;
				if(cklength > 1){
					layer.alert('请选择一条随工单', {
					    skin: 'layui-layer-molv' //样式类名
					    ,closeBtn: 0
					});
					return false;
				}else if(cklength < 1){
					layer.alert('请选择一条随工单', {
					    skin: 'layui-layer-molv' //样式类名
					    ,closeBtn: 0
					});
					return false;
				}else{
					return true;
				}
				
				
			},
			"notCheck":function(){//不需要选中就可以使用
				return true;
			},
			"initCheckbox":function(){
				var flag = $ckbox.is(":checked");
				if(flag){//有选中的
					$storage.removeClass("disabled");
					$repair.removeClass("disabled");
					$repairin.removeClass("disabled");
					$pick.removeClass("disabled");
					$dailywork.removeClass("disabled");
					//$carton.removeClass("disabled");
					$rework.removeClass("disabled");
					$sample.removeClass("disabled");
					$halfinspection.removeClass("disabled");
					$pollingtest.removeClass("disabled");
					$scrap.removeClass("disabled");
					$scenecheck.removeClass("disabled");
				}else{//未选中
					$storage.addClass("disabled");
					$repair.addClass("disabled");
					$repairin.addClass("disabled");
					$pick.addClass("disabled");
					$dailywork.addClass("disabled");
					//$carton.addClass("disabled");
					$rework.addClass("disabled");
					$sample.addClass("disabled");
					$halfinspection.addClass("disabled");
					$pollingtest.addClass("disabled");
					$scrap.addClass("disabled");
					$scenecheck.addClass("disabled");
				}
			},
			"barcode":function(workingbill){
				//var LODOP=getLodop();
				LODOP.PRINT_INIT("条码打印");
				LODOP.SET_PRINT_PAGESIZE(2,"80mm","50mm","箱标签");//宽度 50mm 高度 80mm 
				LODOP.ADD_PRINT_HTM("0mm","5.42mm","31.15mm","6mm","<p style='text-align:center'>建新赵氏集团</p>");
				LODOP.ADD_PRINT_TEXT("6mm","2mm","49mm","6mm",workingbill.maktx);
				//LODOP.ADD_PRINT_TEXT("12mm","0mm","49mm","6mm","34D839431A5AP");
				LODOP.ADD_PRINT_BARCODE("18mm","4mm","27mm","6mm","128Auto",workingbill.matnr);
				LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
				LODOP.ADD_PRINT_TEXT("28mm","2mm","49mm","6mm","物料号:"+workingbill.matnr);
				LODOP.ADD_PRINT_BARCODE("34mm","4mm","22mm","6mm","128Auto",workingbill.charg);
				LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
				LODOP.ADD_PRINT_TEXT("42mm","2mm","49mm","5mm","包装批次:"+workingbill.charg);
				LODOP.ADD_PRINT_TEXT("47mm","2mm","49mm","5mm","数量:"+workingbill.amount);
				LODOP.ADD_PRINT_TEXT("52mm","2mm","49mm","5mm","生产时间:"+workingbill.time);
				LODOP.ADD_PRINT_BARCODE("57mm","4mm","37mm","6mm","128Auto",workingbill.workingbillCode);
				LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
				LODOP.ADD_PRINT_TEXT("65mm","2mm","49mm","5mm","随工单:"+workingbill.workingbillCode);
				LODOP.SET_PRINT_COPIES(workingbill.pagenumber);//控制打印份数
				//LODOP.SET_PRINT_STYLEA(0,"Angle",90);
				//LODOP.PREVIEW();
				LODOP.PRINT();
				
			}
	}
	
	
	init.initCheckbox();
	/**
	 * checkbox 选中
	 */
	$ckbox.click(function(event){
		init.initCheckbox();
		event.stopPropagation();
	});
	

	/**
	 * 入库按钮点击
	 */
	$storage
			.click(function() {
				var istrue = init.isCheck();
				if (istrue) {
					var id = getCKboxById();
					window.location.href = "enteringware_house!list.action?workingBillId="
							+ id;
				}

			});

	/**
	 * 领/退料按钮点击
	 */
	$pick.click(function() {
		var istrue = init.isCheck();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "pick!list.action?workingBillId=" + id;
		}

	});
	
	
	/**
	 * 半成品巡检按钮点击
	 */
	$halfinspection.click(function() {
		var istrue = init.isCheck();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "itermediate_test!list.action?workingBillId=" + id;
		}

	});
	
	/**
	 * 领/退料按钮点击
	 */
	$rework.click(function() {
		var istrue = init.isCheck();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "rework!list.action?workingBillId=" + id;
		}

	});

	/**
	 * 交接按钮点击
	 */
	$handoverprocess.click(function() {
		var istrue = init.notCheck();
		if (istrue) {
			window.location.href = "hand_over_process!list.action";
		}
	});

	/**
	 * 转储按钮点击
	 */
	$dump.click(function() {
		var istrue = init.notCheck();
		var loginid=$("#loginid").val();//当前登录人的id
		//alert(loginid);
		if (istrue) {
			window.location.href = "dump!list.action?loginid="+loginid+"&type=1";
		}
	});
	/**
	 * 纸箱收货按钮点击
	 */
	$carton.click(function() {
		var istrue = init.notCheck();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "carton!list.action";
		}

	});
	/**
	 * 报工按钮点击
	 */
	$dailywork.click(function() {
		var istrue = init.isCheck();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "daily_work!list.action?workingBillId=" + id;
		}

	});
	/**
	 * 返修按钮点击
	 */
	$repair.click(function() {
		var istrue = init.isCheck();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "repair!list.action?workingBillId=" + id;
		}
	});
	/**
	 * 返修收货按钮点击
	 */
	$repairin.click(function() {
		var istrue = init.isCheck();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "repairin!list.action?workingBillId=" + id;
		}
	});
	
	/**
	 * 巡检按钮点击
	 */
	$pollingtest.click(function() {
		var istrue = init.isCheck();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "pollingtest!list.action?workingBillId=" + id;
		}
	});

	/**
	 * 快速响应按钮点击
	 */
	$qResponse.click(function() {
		var istrue = init.notCheck();
		var loginid=$("#loginid").val();//当前登录人的id
		if (istrue) {
			window.location.href = "abnormal!list.action?loginid="+loginid;
		}
	});

	/**
	 * 抽检
	 */
	$sample.click(function(){
		var istrue=init.isCheck();
		if(istrue)
		{
			var id = getCKboxById();
			window.location.href = "sample!list.action?wbId=" + id;
		}
	});
	
	/**
	 * 报废
	 */
	$scrap.click(function(){
		var loginid=$("#loginid").val();//当前登录人的id
		var istrue=init.isCheck();
		if(istrue)
		{
			var id = getCKboxById();
			window.location.href = "scrap!list.action?wbId=" + id+"&loginid="+loginid;
		} 
	});
	
	/**
	 * 考勤
	 */
	$kaoqin.click(function(){
		var istrue = init.notCheck();
		var loginid=$("#loginid").val();//当前登录人的id
			//alert(loginid);
		if (istrue) 
		{
			window.location.href = "kaoqin!list.action?loginid="+loginid;
		}
	});
	
	/**
	 * 成品入库
	 */
	$endProduct.click(function(){
		var istrue = init.notCheck();
		/**var loginid=$("#loginid").val();//当前登录人的id
			alert(loginid);**/
		var productDate = $("#productDate").val();//生产日期
		var shift = $("#shift").val();//班次
		//alert("生产日期:"+productDate+"班次:"+shift);
		if(productDate == "" || shift == ""){
			layer.alert("生产日期和班次必须填写",{icon: 7});
			return false;
		}
		if (istrue) 
		{
			window.location.href = "end_product!list.action?productDate="+productDate+"&shift="+shift;
		}
	});
	/**
	 * 成品入库
	 */
	$returnProduct.click(function(){
		var istrue = init.notCheck();
		//var loginid=$("#loginid").val();//当前登录人的id
			//alert(loginid);
		if (istrue) 
		{
			window.location.href = "return_product!list.action";
		}
	});
	
	/**
	 * 部门领料
	 */
	$deptpk.click(function(){
		var istrue = init.notCheck();
		if(istrue){
			window.location.href="deptpick!list.action";
		}
	});
	
	$updown.click(function(){
		var istrue = init.notCheck();
		if(istrue){
			window.location.href="up_down!list.action";
		}
	});
	/**
	 * 工序交接
	 */
	$processHO.click(function(){
		var istrue = init.notCheck();
		if(istrue){
			window.location.href="process_handover!list.action";
		}
	});
	/**
	 * 线边仓保存
	 */
	$locatHO.click(function(){
		var istrue = init.notCheck();
		if(istrue){
			var loginid=$("#loginid").val();//当前登录人的id
			window.location.href="locat_hand_over_header!list.action?loginid="+loginid;
		}
	});
	
	$csll.click(function(){
		var istrue = init.notCheck();
		if(istrue){
			window.location.href="up_down!cslist.action";
		}
		
	});
	
	$barcode.click(function(){
		var part = $(this).prev().val();//打印的份数
		var workingbillid = $(this).parent().siblings().eq(0).find(".ckbox").val();//随工单ID
		if(part == ""){
			layer.alert("打印数量必须填写");
			return false;
		}
		
		var workingbill = new Array();
		
		
		
		$.post("working_bill!barcodePrint.action", { wbid: workingbillid},function(data){
			workingbill.workingbillCode = data.workingbillCode;//随工单号
			workingbill.matnr=data.matnr;//物料号1
			workingbill.maktx=data.maktx;//物料描述
			workingbill.charg=data.charg;//批次
			workingbill.amount=data.amount;//数量
			workingbill.pagenumber=part;//打印的页数
			workingbill.time = data.time;//时间
			init.barcode(workingbill);
		},"json" );
		
	});
	
})

/**
 * 获取checkbox的ID值
 */
function getCKboxById() {
	var id = "";
	var ishead = 0;
	$(".ckbox:checked").each(function() {
		var sId = $(this).val();
		if (ishead == 1)
			id += "," + sId;
		else
			id = sId;
		ishead = 1;
	});
	return id;
}




/**
 * 获取当前随工单的投放产出
 */
function wbout_event(wbid,maktx,productDate,shift)
{
	var url="working_inout!beforegetwbinoutput.action?id="+wbid;
	layer.open({
		type:2,
		title:"当前随工单的投入产出信息&nbsp;&nbsp;&nbsp;&nbsp;产品名称:"+maktx+";&nbsp;&nbsp;&nbsp;&nbsp;生产日期:"+productDate+";&nbsp;&nbsp;&nbsp;&nbsp;班次:"+shift,
		//skin: 'layui-layer-lan',
		area: ["90%", "90%"],
		shade:0.52,
		shadeClose:false,
		move:false,
		content:url,
		closeBtn:1,
		btn:["关闭"]
	});
	event.stopPropagation();
	/*var title = "当前随工单的投入产出信息";
	var width="900px";
	var height="600px";
	var content="working_bill!beforegetwbinoutput.action?id="+wbid;
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
		
	});*/
}
/**
 * 获取模具组号
 */
function moudle_event(wbid,info)
{
	layer.open({
        type: 2,
        skin: 'layui-layer-lan',
        shift:2,
        title: "选择模具组号",
        fix: true,
        shade: 0.5,
        shadeClose: true,
        maxmin: true,
        scrollbar: false,
        btn:['确认','取消'],
        area: ["40%", "80%"],//弹出框的高度，宽度
        content:"working_bill!moudlelist.action?id="+wbid+"&info="+info,
        yes:function(index,layero){//确定
        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        	var aufnr = iframeWin.getName();
        	/*$("#infoId").val(info.faunid);
        	$("#infoName").val(info.faunname);
        	$("#infoNames").text(info.faunname);*/
        	if("baga"!=aufnr){
        		window.location.href="working_bill!updateWKInMoudel.action?aufnr="+aufnr+"&wbid="+wbid;
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

















