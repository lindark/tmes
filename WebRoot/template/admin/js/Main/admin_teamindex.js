$(function(){
		$("input.datePicker").datepicker({
			clearText: '清除',   
		    clearStatus: '清除已选日期',   
		    closeText: '关闭',   
		    closeStatus: '不改变当前选择',   
		    prevText: '<上月',   
		    prevStatus: '显示上月',   
		    prevBigText: '<<',   
		    prevBigStatus: '显示上一年',   
		    nextText: '下月>',   
		    nextStatus: '显示下月',   
		    nextBigText: '>>',   
		    nextBigStatus: '显示下一年',   
		    currentText: '今天',   
		    currentStatus: '显示本月',   
		    monthNames: ['一月','二月','三月','四月','五月','六月', '七月','八月','九月','十月','十一月','十二月'],   
		    monthNamesShort: ['一','二','三','四','五','六', '七','八','九','十','十一','十二'],   
		    monthStatus: '选择月份',   
		    yearStatus: '选择年份',   
		    weekHeader: '周',   
		    weekStatus: '年内周次',   
		    dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],   
		    dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],   
		    dayNamesMin: ['日','一','二','三','四','五','六'],   
		    dayStatus: '设置 DD 为一周起始',   
		    dateStatus: '选择 m月 d日, DD',   
		    dateFormat: 'yy-mm-dd',   
		    firstDay: 1,   
		    initStatus: '请选择日期',   
			changeMonth : true,// 设置允许通过下拉框列表选取月份。
			changeYear : true,// 允许通过下拉框列表选取年份
			showMonthAfterYear : true,//是否把月放在年的后面
			showOtherMonths: true,
			selectOtherMonths: true
			});
		var productDate = $("input[name='admin.productDate']").val();
		$("#productDate_input").bind("change",function(){
			var pd = $(this).val();
			if(pd.length!=10){
				alert("日期格式不正确,请重选日期");
				$(this).val(productDate);
				$("#productDate").val(productDate);
				return false;
			}else{
				$("#productDate").val(pd);
			}
		});
		$(".matkx").bind("click",function(){
			var id = $(this).prev().val();
			var matkx = $(this).text();
			var productDate = $("input[name='admin.productDate']").val();
			var shift =$("select[name='admin.shift']").val();
			if(shift=="1"){
				shift = "早";
			}else if(shift=="2"){
				shift = "白";
			}else if(shift=="3"){
				shift = "晚";
			}
			wbout_event(id,matkx,productDate,shift);
		});
		$(".moudle").bind("click",function(){
			var id = $(this).prev().prev().val();
			var info = $("#info").val();
			moudle_event(id,info);
		});
		$("#submitButton").click(function(){
			var pDate = $("#productDate").val();
			if(pDate.length!=10){
				alert("日期格式不正确,,请重选日期");
				return false;
			}
			$.ajax({
				url: "admin!productupdate.action",
				data: $("#inputForm").serialize(),
				dataType: "json",		
				success: function(data) {
					layer.alert(data.message, {icon: 6},function(){
						window.location.href="admin!index.action";
					}); 
				}
			});	
		});
		
		
		$("#changeTeamButton").bind("click",function(){
			var loginid = $("#loginid").val();
			layer.open({
		        type: 2,
		        skin: 'layui-layer-lan',
		        shift:2,
		        title: "选择班组",
		        fix: false,
		        shade: 0.5,
		        shadeClose: true,
		        maxmin: true,
		        scrollbar: false,
		        btn:['确认','取消'],
		        area: ["80%", "70%"],//弹出框的高度，宽度
		        content:"team!beforegetalllist.action",
		        yes:function(index,layero){//确定
		        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
		        	var info = iframeWin.getName();
		        	$.ajax({
		        		url:"admin!changeTeam.action?loginid="+loginid+"&teamId="+info.teamid,
		        		dataType:"json",
		        		success:function(data)
		        		{
		        			alert(data.message);
		        			location.reload();	                	
		        		},
		        		error:function(){
		        			alert("发生错误，请重试！");
		        			//location.reload();
		        		}
		        		
		         	});
		        	layer.close(index);
		        	return false;
		        },
		        no:function(index)
		        {
		        	layer.close(index);
		        	return false;
		        }
		    });
		return false;
	});
	});