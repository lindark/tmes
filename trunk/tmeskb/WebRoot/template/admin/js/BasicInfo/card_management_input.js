$(function(){
	//选择单元
	$("#label_xfuname").click(function(){
		showpfactoryunit();//单元
	});
});

//选择单元
function showpfactoryunit()
{
	var title = "选择单元";
	var width="800px";
	var height="632px";
	var content="team!list2.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		$("#xfuid").val(id[0]);//单元id
		$("#label_xfuname").text(id[1]);//单元名称
		layer.close(index); 
	});
}
