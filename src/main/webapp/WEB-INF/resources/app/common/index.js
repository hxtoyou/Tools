define(function(require, exports, module) {
	var url1 = "/excel/createForm";
	var url2 = "/tools/index";
	var $ = require("jquery");
	var bootstrapGrid = require("bootstrapGrid");
	var bootstrapDialog = require("bootstrapDialog");
		initFileInput();


	// var head = require("head");

	// $("#submit").click(function() {
	// 	var param = $("#form").serialize();
	// 	$.ajax({
	// 		url:'/document/fileUpload?'+param,
	// 		type:'post',
	// 		success:function(data) {
	// 			console.log("success");
	// 		}
	// 	})
	// 	// $("#pannelTabs").find("iframe").attr("src",url1);
	// });
	$("#grid-selection").bootgrid({
		ajax: true,
		post: function() {
			/* To accumulate custom parameter with the request object */
			return {
				id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
			};
		},
		url: "/document/list",
		selection: true,
		multiSelect: true,
		formatters: {
			"isParase": function(column, row) {
				if(row.isParase!==true){
					return '<button type="button" data-tempType="'+row.type+'" data-temp="'+row.path+'" class="btn btn-info parase">解析</button>';
				}else{
					return "已经解析"
				}
			}
		}
	}).on("selected.rs.jquery.bootgrid", function(e, rows) {
		var rowIds = [];
		for (var i = 0; i < rows.length; i++) {
			rowIds.push(rows[i].id);
		}
		alert("Select: " + rowIds.join(","));
	}).on("deselected.rs.jquery.bootgrid", function(e, rows) {
		var rowIds = [];
		for (var i = 0; i < rows.length; i++) {
			rowIds.push(rows[i].id);
		}
		alert("Deselect: " + rowIds.join(","));
	});
	setTimeout(function(){
		$("button.parase").click(function(){
			var path = $(this).attr("data-temp");
			var type= $(this).attr("data-tempType");
			console.log(path);
			console.log(type);
			$.ajax({
				url:"/document/parase",
				data:{
					path:path,
					type:type
				},
				success:function(data){
					console.log("22");
				}

			})
			
		});
	},500);
	

	function initFileInput(){
		$("#input-id").fileinput({
			language: 'zh', //设置语言
			uploadUrl: '', //上传的地址
			allowedFileExtensions: ['xlsx', 'xls'], //接收的文件后缀
			showUpload: false, //是否显示上传按钮
			showCaption: false, //是否显示标题
			showPreview: true,
			browseClass: "btn btn-primary", //按钮样式             
			previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
			previewFileIconSettings: {
				'docx': '<i class="fa fa-file-word-o text-primary"></i>',
				'xlsx': '<i class="fa fa-file-excel-o text-success"></i>',
				'pptx': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
				'jpg': '<i class="fa fa-file-photo-o text-warning"></i>',
				'pdf': '<i class="fa fa-file-pdf-o text-danger"></i>',
				'zip': '<i class="fa fa-file-archive-o text-muted"></i>',
			}
		});
	}
});