$register("jiuyi.admin.browser");
$(function(){

	layer.config({
		extend: 'extend/layer.ext.js'//扩展 layer
	});
})

/**
 * dialog 弹出层 ---Ifream
 * @Param title 标题
 * @Param width 宽度
 * @Param height 高度
 * @Param content url地址
 * @Param callback 回掉
 */
jiuyi.admin.browser.dialog = function(title,width,height,content,callback){
	layer.open({
        type: 2,
        //skin: 'layui-layer-lan',
        title: title,
        fix: false,
        shadeClose: false,
        maxmin: true,
        scrollbar: false,
        btn:['保存','取消'],
        area: [width, height],//弹出框的高度，宽度
        content:content,
        yes:function(index,layero){
        	
        	callback(index,layero);
        }

    });
	
}
