$(function(){
	
});
function btn_addbug_event(index)
{
	layer.open({
	    type: 1,
	    shade:0.52,//遮罩透明度
	    title: "添加缺陷信息",
	    area:["500px","300px"],
	    closeBtn: 1,//0没有关闭按钮，1-3不同样式关闭按钮---右上角的位置
	    //shadeClose: false,//点击遮罩层(阴影部分)：true时点击遮罩就关闭，false时不会
	    btn:["确定","取消"],
	    yes:function(){alert(index);},
	    content: $("#divbox"),
	    
	});
}