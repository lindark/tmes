$(function(){
	//添加产品子件
	$("#btn_addpiece").click(function(){
		addpiece();
	});
});

//添加产品子件
function addpiece()
{
	var title = "添加产品组件";
	var width="800px";
	var height="610px";
	var content="repairin!beforegetpiece.action?info="+$("#wkid").val();
	var html="<th style='width:20%;'>组件编码</th> <th style='width:35%;'>组件描述</th> <th style='width:15%;'>产品数量</th> <th style='width:15%;'>组件数量</th>  <th style='width:15%;'>操作</th>";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var infos=iframeWin.getGridId();
		if(infos!=""&&infos!=null)
		{
			var info1=infos.split("?");//分行
			for(var i=0;i<info1.length;i++)
			{
				var info2=info1[i].split(",");//分列
				//info2 --- 0主键ID 1组件编码 2组件名称 3产品数量  4组件数量
				if(info2.length>1)
				{
					html+="<tr>" +
					"<td>"+info2[1]+"<input type='hidden' name='list_rp["+i+"].rpcode' value='"+info2[1]+"' /></td>" +
					"<td>"+info2[2]+"<input type='hidden' name='list_rp["+i+"].rpname' value='"+info2[2]+"' /></td>" +
					"<td>"+info2[3]+"<input type='hidden' name='list_rp["+i+"].productnum' value='"+info2[3]+"' /></td>" +
					"<td>"+info2[4]+"<input type='hidden' name='list_rp["+i+"].piecenum' value='"+info2[4]+"' /></td>" +
					"<td><a onclick='del_event()' style='cursor:pointer;'>删除</a></td>" +
					"</tr>";
				}
			}
			$("#tb_repairinpiece").html(html);
			layer.close(index);
		}
	});
}

//删除
function del_event()
{
	$(this).parent().parent().remove();
}