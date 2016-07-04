$(function(){
	
	//返回
	$("#btn_back").click(function(){
		window.history.back();
	});
	
	//添加单元
	$("#img_addfu").click(function(){
		imgaddfu_event();
	});
});

//添加单元
function imgaddfu_event()
{
	var title = "选择单元";
	var width="800px";
	var height="632px";
	var content="team!list2.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		$("#input_fu").val(id[0]);//单元id
		$("#span_fu").text(id[1]);//单元名称
		layer.close(index); 
		var oldId = $("#oldId").val();
		var id = $("#input_fu").val();
		if(oldId != id){
			$("#isChangeUnit").val("1");
		}
	});
}
