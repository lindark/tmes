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