define(function(require, exports, module) {
	var $ = require("jquery");
	$("#cfxbtn").click(function(){
		$.ajax({
			url:"/cxf/client",
			success:function(data){
				console.log(data);
				$("#message").html(data);
				console.log("22");
			}

		});
	});
})