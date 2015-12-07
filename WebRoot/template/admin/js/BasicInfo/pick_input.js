$(function(){

	//刷卡保存
	$("#btn_save").click(function(){
		sub_event("1");
	});
	
	//刷卡确认
	$("#btn_confirm").click(function(){
		sub_event("2");
	});
	
	//返回
	$("#btn_back").click(function(){
		window.history.back();
	});
});


//提交事件:1.刷卡保存，2.刷卡确认
function sub_event(my_id){
	
			//赋值
	fuzhi(my_id);
   $("#inputForm").submit();
  }


//赋值
function fuzhi(my_id)
{
	$("#my_id").val(my_id);//1.保存，2.确认
}