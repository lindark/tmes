//所有公用的核心javascript
/**override alert**/
/*
(function(proxy) {
    var originalAlert = window.alert;
    proxy.alert = function (msg) {
        result = ingage.common.msgInterceptor(msg);
        if(result === false){
            ingage.common.errorResult(msg);
        }else{
            ingage.common.errorResult(result);//ingage.common.successResult(result);
        }
    };
    //在旧版里，有代码用到了$.alert，在此将其等价于errorResult方法
    if(!$.alert){
        $.alert = function (msg){
            result = ingage.common.msgInterceptor(msg);
            if(result === false){
                ingage.common.errorResult(msg);
            }else{
                ingage.common.errorResult(result);
            }            
        }
    }
    window.__alert = originalAlert;
})(this);
*/
/*auto textarea*/
(function ($) {
	window.$register = function (fullname) {
	    try {
	        var nsArray = fullname.split(".");
	        var strNS = "";
	        var strEval = "";
	        for (var i = 0; i < nsArray.length; i++) {
	            if (strNS.length > 0)
	                strNS += ".";
	            strNS += nsArray[i];
	            strEval += " if(typeof(" + strNS + ") =='undefined') " + strNS + " = new Object(); ";
	        }
	        if (strEval != "") eval(strEval);
	    } catch (e) {
	        alert(e.message);
	    }
	};
})(jQuery);

