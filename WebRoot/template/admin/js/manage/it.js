 $(function(){
		    var workingBillId=$("#workingBillId").val();
			var $itAddBtn = $(".itAddBtn");//添加用户
			/**
			 * 添加按钮点击
			 */
			$itAddBtn.click(function(){
				var title = "不合格原因";
				var width="800px";
				var height="300px";
				var content="itermediate_test_detail!browser.action";
				jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
				
		        var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
		        var docu = iframeWin.document;
		        $(docu).find("#submit_btn").trigger("click");//点击ifream里面的提交按钮
//		        $("#grid-").jqGrid('setGridParam',{
//					url:"ipRecord!save.action,
//					datatype:"json",
//					page:1
//				}).trigger("reloadGrid");
//		        
		        layer.close(index);            	          	     	       
			  });
			 	
			});
			
		})