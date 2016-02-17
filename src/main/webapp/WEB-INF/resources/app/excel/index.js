define(function(require,exports,module){
	// var contextData = '${context.user}';
	var $ = require("jquery");
	var bootstrapGrid = require("bootstrapGrid");
	var bootstrapDialog = require("bootstrapDialog");
	require('datepicker')($);
	var data = JSON.parse(contextData);
	var validateForm = require("excel/excelValidate");
	var html = "";
	var pageSize;
	var pageNum;
	var statisticPageNum = 1;
	var statisticPageSize = 10;
	var totalPage = 0;
	var param = "";
	var templateType = '';
	var types = '';
	var type = '';
	var re = new RegExp("\\{(.*?)\\}");
	var statisticPagination = "";
	var contentPagination = "";
	var me = this;
	var isLoad=false;
	var colMap = new Object();
	var spanRows='';
	var dialog;
	createForm(data, validateFile);
	/**
	 *提交查询
	 **/

	function submit() {
		// if (!$("#form").form("validate")) {
		// 	return;
		// };
		param = $("#form").serialize();
		if (type === "statistic") {
			pageNum = 1;
			pageSize = 10;
		} else if (type === "detail") {
			pageNum = statisticPageNum;
			pageSize = statisticPageSize;
		}
		var opt = "";
		var url = "/excel/convert/detail?" + param;
		$.ajax({
			url: url,
			data: {
				"templateType": type,
				"templateName": filePath,
				"pageNumber": pageNum,
				"pageSize": pageSize
			},
			async: true,
			success: function(data) {
				isLoad=true;
				var data = JSON.parse(data.data);
				console.log(data.showType);
				if (type === '') {
					templateType = data.showType;
					types = templateType.split('&');
					var pagination = data.pagination;
					
					type = types[0];
					$("#swift").removeClass("display_1");
					$("#out").removeClass("display_1");
					if (types.length === 1) {
						if (types[0] === "detail") {
							contentPagination = pagination;
							$("#statistic").addClass("display_a");
							$("#statisticContent").addClass("display_1");
						} else if (types[0] === "statistic") {
							statisticPagination = pagination;
							$("#statistic").addClass("display_a");
							$("content").addClass("display_1");
						}
					} else if (types.length === 2) {
						if (types[0] === "detail") {
							contentPagination = pagination;
							$("#statisticContent").addClass("display_1");
						} else if (types[0] === "statistic") {
							statisticPagination = pagination;
							$("#bottom").addClass("display_1");
							$("content").addClass("display_1");

						}

					} else {
						// console.log($xcp.i18n('excel.errorMsg'));
					}


				}
				spanRows =data.spanRows; 
				var titleLines = data.tableTitle;
				var data = data.data;
				data = JSON.parse(data);
				totalPage = data.totalCount;
				var contentHtml = "";
				if (type === "detail") {
					generateHtml(contentHtml, "content", data, titleLines,spanRows);
					if (contentPagination === "YES") {
						setPagination(totalPage, data.result[0].tableWidth, "pp");
					}
				} else {
					generateHtml(contentHtml, "statisticContent", data, titleLines,spanRows);
					if (statisticPagination === "YES") {
						setPagination(totalPage, data.result[0].tableWidth, "pps");
					}

				}
			},
			error: function() {
				console.log("error");
			}

		});
		// $xcp
	}
	/**
	 *统计表格与详细数据表格切换
	 **/
	$("#statistic").click(function() {
		if (type === "detail") {
			getStatisticData(spanRows);
			$("#statisticContent").removeClass("display_1");
			$("#statistic").html($xcp.i18n('excel.detail'));
			$("#content").addClass("display_1");
			$("#pp").addClass("display_1");
			if (statisticPagination === "YES") {
				$("#pps").removeClass("display_1");
			}
			$.parser.parse('#statistic');
			type = "statistic";

		} else if (type === "statistic") {
			getData(pageNum, pageSize,spanRows);
			$("#statisticContent").addClass("display_1");
			$("#content").removeClass("display_1");
			$("#statistic").html($xcp.i18n('excel.statistic'));
			$("#pps").addClass("display_1");
			if (contentPagination === "YES") {
				$("#pp").removeClass("display_1");
			}
			type = "detail";
			$.parser.parse('#statistic');
		}

	});
	/**
	 *导出
	 **/
	$("#export").click(function() {
		console.log("11");
		var isopen = window.open("/excel/export?templateType=" + type + "&templateName=" + filePath + "&" + param, "_self");
		if ($("#export").hasClass("bbdc_disabled")) {
			$.messager.alert($xcp.i18n('sys.tip'), $xcp.i18n('excel.exportTip'));
			return false;
		}
		// var title = "";
		// var aler = "";
		// if (type === "detail") {
		// 	var end = "";
		// 	title = $xcp.i18n('excel.exportDetail');
		// 	if (totalPage > 20000) {
		// 		end = $xcp.i18n('excel.export20000');
		// 	}
		// 	if (totalPage > 80000) {
		// 		end = $xcp.i18n('excel.export80000');
		// 	}
		// 	aler = "<p>" + $xcp.i18n('excel.exportMsg') + "</p>";
		// 	aler += "<p>" + $xcp.i18n('excel.exportTotal') + ":";
		// 	aler += totalPage + $xcp.i18n("excel.exportNum") + "</p>" + end;
		// }
		// if (type === "statistic") {
		// 	title = $xcp.i18n('excel.exportStatistic');
		// 	aler = "<p>" + $xcp.i18n('excel.exportMsg') + "</p>"
		// }
		// $.messager.confirm($xcp.i18n('excel.export'), aler, function(r) {
		// 	if (r) {

		// 		$("#export").addClass("bbdc_disabled");
		// 		if (type === "detail") {
		// 			var isopen = window.open("./report.do?method=getExcelExport&templateType=" + type + "&templateName=" + filePath + "&" + param, "_self");
		// 			var time = 0;
		// 			if (totalPage > 20000 && totalPage <= 30000) {
		// 				aler = $xcp.i18n('excel.export20000');
		// 				time = 10000;
		// 				$("#export").addClass("bbdc_disabled");
		// 			}
		// 			if (totalPage > 30000 && totalPage <= 50000) {
		// 				aler = $xcp.i18n('excel.export30000');
		// 				time = 10000;
		// 				$("#export").addClass("bbdc_disabled");
		// 			}
		// 			if (totalPage > 50000 && totalPage <= 80000) {
		// 				aler = $xcp.i18n('excel.export50000');
		// 				time = 60000;
		// 				$("#export").addClass("bbdc_disabled");
		// 			}
		// 			if (totalPage > 80000) {
		// 				aler = $xcp.i18n('excel.export80000');
		// 				time = 240000;
		// 				$("#export").addClass("bbdc_disabled");
		// 			}
		// 			if (totalPage > 20000) {
		// 				$.messager.show({
		// 					title: $xcp.i18n('excel.exportFile'),
		// 					msg: aler,
		// 					id: "show",
		// 					closable: false,
		// 					timeout: time,
		// 					showType: 'slide'
		// 				});
		// 			}
		// 			setTimeout(function() {
		// 				$("#export").removeClass("bbdc_disabled");
		// 			}, time); //单位毫秒 
		// 		} else {
		// 			var isopen = window.open("./report.do?method=getExcelExport&templateType=" + type + "&templateName=" + filePath + "&" + param, "_self");
		// 			setTimeout(function() {
		// 				$("#export").removeClass("bbdc_disabled");
		// 			}, 300); //单位毫秒 
		// 		}
		// 	}
		// });

	});

	/**
	 *生成html
	 **/
	function generateHtml(html, statisticContent, data, titleLines,spanRows) {
		var html = '<table class="table-bordered table-hover" id="tbname" width="' + data.result[0].tableWidth + '" height="auto" border="0" cellpandding="0" cellspacing="0">';
		var tag = 0;
		var titleTag = 0;
		$.each(data.result[0].htmlRowStructure, function(i, value) {
			titleTag++;
			var tLineint = parseInt(titleLines);
			if (titleTag < tLineint) {
				html += '<tr class="bg_wirter">';
			} else {
				if (tag === 0) {
					if (titleTag === tLineint) {
						html += '<tr class="h_top">';
					} else {
						html += '<tr class="bg_color">';
					}
					tag = 1;
				} else {
					if (titleTag === tLineint) {
						html += '<tr class="h_top">';
					} else {
						html += '<tr class="bg_wirter">';
					}
					tag = 0;
				}
			}
			$.each(value.htmlTdStructure, function(j, item) {
				
				var value = "";
				var realValue="";
				if (!re.test(item.value)) {
					value = item.value;
				}
				var isBold = "";
				if (item.isBold === true) {
					isBold = "bolder";
				}
				html += '<td colspan="' + item.colspan + '" rowspan="' + item.rowspan + '" align="center" width="' + item.width + '" height="' + item.height + '" style="font-size:' + item.font_size + 'pt;font-family:' + item.fontFamily + ';color:' + item.font_color + ';background-color:' + item.bg_color + ';  border-top-width:' + item.border_top + ';border-bottom-width:' +
					item.border_bottom + ';border-right-width:' + item.border_right + ';border-left-width:' + item.border_left + ';text-align:' + item.horizontalAlign + ';vertical-align:' + item.verticalAlign + ';font-weight:' + isBold + ';">' + value + '</td>'
			});
			html += '</tr>';
		});
		html += "</table>";
		$("#" + statisticContent).css("width", data.result[0].tableWidth);
		$("#" + statisticContent).html(html);
		var tb = document.getElementById("tbname");
		// var spanRowsList = new Array();
		// spanRowsList = spanRows.split(",");
		// colMap = $xcp.Map.init();
		// $.each(spanRowsList, function(i, value) {
		// 	autoRowSpan(tb,0,value);
		// });
	}

	/**
	 *获取统计数据表格
	 **/
	function getStatisticData(spanRows) {
		$xcp.ajax({
			type: 'POST',
			url: "./report.do?method=getExcelHtml&" + param,
			async: true,
			data: {
				"templateType": "statistic",
				"templateName": filePath,
				"pageSize": me.pageSize,
				"pageNumber": me.pageNum
			},
			success: function(data) {
				// var data = JSON.parse(data);
				var titleLines = data.outEntity.tableTitle;
				statisticPagination = data.outEntity.pagination;
				var data = data.outEntity.data;
				data = JSON.parse(data);
				totalPage = data.totalCount;
				var statisticHtml = "";
				generateHtml(statisticHtml, "statisticContent", data, titleLines,spanRows);
				if (statisticPagination === "YES") {
					setPagination(totalPage, data.result[0].tableWidth, "pps");
				} else {
					$("#pps").addClass("display_1");
				}
			},
			error: function() {
				console.log("error");
			}
		});
	}
	/**
	 *获取详细数据表格
	 **/
	function getData(pageNumber, pageSize,spanRows) {
		$xcp.ajax({
			type: 'POST',
			url: "./report.do?method=getExcelHtml&" + param,
			async: true,
			data: {
				"templateType": "detail",
				"templateName": filePath,
				"pageNumber": statisticPageNum,
				"pageSize": statisticPageSize
			},
			success: function(data) {
				var titleLines = data.outEntity.tableTitle;
				contentPagination = data.outEntity.pagination;
				var data = data.outEntity.data;
				data = JSON.parse(data);
				totalPage = data.totalCount;
				var contentHtml = "";
				generateHtml(contentHtml, "content", data, titleLines,spanRows);
				if (contentPagination === "YES") {
					setPagination(totalPage, data.result[0].tableWidth, "pp");
				} else {
					$("#pp").addClass("display_1");
				}
			},
			error: function() {
				console.log("error");
			}
		});
	}
	/**
	 *弹出查询窗口
	 **/
	$("#query").click(function() {
		dialog.open();
	});
	/**
	 *分页控件
	 **/
	function setPagination(totalPage, tableWidth, id) {
		if ($("#" + id).hasClass("display_1")) {
			$("#" + id).removeClass("display_1");
		}
		// $("#" + id).pagination({
		// 	total: totalPage,
		// 	// pageSize:5 ,
		// 	pageList: [1, 3, 5, 10, 15, 20],
		// 	onSelectPage: function(pageNumber, pageSize) {

		// 		if (id === "pp") {
		// 			statisticPageNum = pageNumber;
		// 			statisticPageSize = pageSize;
		// 			getData(statisticPageNum, statisticPageSize,spanRows);
		// 		} else if (id === "pps") {
		// 			me.pageNum = pageNumber;
		// 			me.pageSize = pageSize;
		// 			getStatisticData(spanRows);
		// 		}
		// 		$(this).pagination('loading');
		// 		// alert('pageNumber:'+pageNumber+',pageSize:'+pageSize);
		// 		$(this).pagination('loaded');
		// 	},
		// 	onChangePageSize: function(pageSize) {
		// 		statisticPageSize = pageSize;
		// 		// getData(statisticPageNum, statisticPageSize);
		// 	}
		// });
		// $('#pp').css("width", tableWidth + "px");
		// $.parser.parse("#" + id);
	}


	/**
	 *根据后台传回数据生成查询窗口
	 **/
	function createForm(data, validateFile) {
		html+='<div style="diaplay:none;" id="dialog"><form id="form" class="commQueryCondWrap">';
		
		for (var i = 0; i < data.length; i++) {
			console.log(data[i]);
				var value = data[i];
				var label  = value.name;
				var name = value.varName;
				var classtype = value.classType;
				html+='<div class="form-group">'+
					    '<label for="exampleInputEmail1">'+label+'</label>'+
					    '<input type="input" class="form-control '+classtype+'" name="'+name+'" id="exampleInputEmail1" placeholder="Email">'+
					  '</div>';

		}
		html+='</form></div>';
		$(html).appendTo('body');
		// var map = validateForm.generatePage;
		// html += "<table class='table_tc'  border='0'>";
		// var isRequird;
		// for (var i = 0; i <= data.length / 2; i++) {
		// 	html += "<tr>";
		// 	if (i * 2 < data.length) {
		// 		var value = data[i * 2];
		// 		var name = value.varName;
		// 		name = name.trim();
		// 		if (!(name in map)) {
		// 			if (value.dataOption === 'YES') {
		// 				isRequird = " easyui-validatebox M";
		// 			} else {
		// 				isRequird = " easyui-validatebox";
		// 			}
		// 			html += "<td class='bzh'>" + value.name + ":</td>";
		// 			if (value.comboAttr == "") {
		// 				html += "<td><input id='" + value.varName + "' class='tc_input " + value.classType + isRequird + " ' name='" + name + "'></td>";
		// 			} else {
		// 				html += "<div class='input'><td><input id='" + value.varName + "' class='tc_input " + value.classType + isRequird+" ' name='" + name + "' " + "data-options=valueField:'val',textField:'name',data:$xcp.getConstant('" + value.comboAttr + "'),></input></td>"
		// 			}
		// 		} else {
		// 			html += map[name];
		// 		}
		// 	}
		// 	if ((i * 2 + 1) < data.length) {
		// 		var value = data[i * 2 + 1];
		// 		var name = value.varName || "";
		// 		name = name.trim();
		// 		if (!(name in map)) {
		// 			if (value.dataOption === 'YES') {
		// 				isRequird = " easyui-validatebox M";
		// 			} else {
		// 				isRequird = " easyui-validatebox";
		// 			}
		// 			html += "<td class='bzh'>" + value.name + ":</td>";
		// 			if (value.comboAttr == "") {
		// 				html += "<td><input id='" + value.varName+ "' class='tc_input " + value.classType +isRequird + " ' name='" + name + "'></td>";
		// 			} else {
		// 				html += "<td><input id='" + value.varName+"' class='tc_input " + value.classType + isRequird+" ' name='" + name + "' " + "data-options=valueField:'val',textField:'name',data:$xcp.getConstant('" + value.comboAttr + "'),></input></td>"
		// 			}
		// 		} else {
		// 			html += map[name];
		// 		}
		// 	}
		// 	html += "</tr>";
		// };
		
		// html += "</table>";
		// var lines = Math.ceil(data.length / 2);
		// var height = (lines + 3) * 41;
		
		console.log("html",$("#dialog").html());
		dialog = bootstrapDialog.show({
			message : $("#dialog").html(),
			 buttons: [{
                label: 'SUBMIT',
                cssClass: 'btn-primary',
                action: function(bootstrapDialog){
                	bootstrapDialog.close();
                    submit();
                }
            }, {
                label: 'Close',
                action: function(dialogItself){
                    dialogItself.close();
                }
            }]
		});
		$('input.easyui-datebox').datepicker({
			format: 'yyyy-mm-dd'
		});
	
		validateForm.init;

	}
	$("#close").click(function() {
		$xcp.formPubMgr.formBack();
	});

	function mouseOver(event) {
		this.addClass("bg_colorout");
	}

	function mouseOut(event) {
		this.removeClass("bg_colorout");
	}
	/**
	 * 合并行
	 */
	 
	function autoRowSpan(tb, row, col) {
		var lastValue = "";
		var value = "";
		var pos = 1;
		col = parseInt(col);
		var deleteCol = new Array();
		for (var i = row; i < tb.rows.length; i++) {
			var formatP = "X"+i;
			try {
				value = tb.rows[i].cells[col-parseInt(colMap.get(formatP))].innerText;
			} catch (e) {
				value="";
				// TODO: handle exception
			}
			if (lastValue == value&&value!=="") {
				tb.rows[i].deleteCell(col-parseInt(colMap.get(formatP)));
				var tempRow = "X"+(i-pos);
				tb.rows[i - pos].cells[col-parseInt(colMap.get(tempRow))].rowSpan = tb.rows[i - pos].cells[col-parseInt(colMap.get(tempRow))].rowSpan + 1;
				tb.rows[i - pos].cells[col-parseInt(colMap.get(tempRow))].setAttribute("align","center");
				tb.rows[i - pos].cells[col-parseInt(colMap.get(tempRow))].setAttribute("text-align","center");
				tb.rows[i - pos].cells[col-parseInt(colMap.get(tempRow))].setAttribute("vertical-align","center");
				pos++;
				var temp  = parseInt(colMap.get(formatP))+1;
				colMap.put(formatP,temp);
			} else {
				lastValue = value;
				pos = 1;
				colMap.put(formatP,colMap.get(formatP));
			}
		}
		
//		$.each(deleteCol,function(i,value){
//			var data = value.split(',');
//			colMap.put(data[0],parseInt(colMap.get(data[0]))+1);
//			tb.rows[data[0]].deleteCell(parseInt(data[1])+parseInt(colMap.get(data[0])));
//		});
	}
});
