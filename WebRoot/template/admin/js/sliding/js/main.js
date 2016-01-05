jQuery(document).ready(
		function($) {
			// open the lateral panel
			$('.cd-btn').on('click', function(event) {
				event.preventDefault();
				$('.cd-panel').addClass('is-visible');
				$("#load").show();
				createiframe();
			});
			// clode the lateral panel
			$('.cd-panel').on(
					'click',
					function(event) {
						if ($(event.target).is('.cd-panel')
								|| $(event.target).is('.cd-panel-close')) {
							$('.cd-panel').removeClass('is-visible');
							$(".rightiframe").remove();
							event.preventDefault();
						}
					});
});

function createiframe(){
	
	//var offset = $(".cd-panel-content").offset();
	//var contentwidth = $(".cd-panel-content").width();
	var contentheight= $(".cd-panel-content").height();
	var L1 = $("#load").height();
	var top = (contentheight-L1)/2;
	$("#load").offset({top:top});
	
	var html = "";
	html += "<div class=\"embed-responsive embed-responsive-16by9 rightiframe\" style=\"width:100%;height:100%;\">";
	html+="<iframe style=\"visibility:hidde\" onreadystatechange=stateChangeIE(this) onload=stateChangeFirefox(this) src=\"working_bill!inout.action\"  class=\"embed-responsive-item iframe\"></iframe>";
	html+="</div>";
	$(".cd-panel-content").append(html);
	
	//$(".rightiframe").append("<iframe style=\"visibility:hidde\" onreadystatechange=stateChangeIE(this) onload=stateChangeFirefox(this) src=\"hand_over_process!add.action?materialCode=10300034 &processid=4028c78150a82dbc0150a830279f0001\"  class=\"embed-responsive-item iframe\"></iframe>");
	
}

function stateChangeIE(_frame) {
	if (_frame.readyState == "interactive")// state: loading
											// ,interactive,
											// complete
	{
		var loader = document.getElementById("load");
		//loader.innerHTML = "";
		loader.style.display = "none";
		_frame.style.visibility = "visible";
	}
}
function stateChangeFirefox(_frame) {
	var loader = document.getElementById("load");
	//loader.innerHTML = "";
	loader.style.display = "none";
	_frame.style.visibility = "visible";
}