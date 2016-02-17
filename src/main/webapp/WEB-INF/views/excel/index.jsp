<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<script type="text/javascript">
		var errorMsg = "${errorMsg}";
		var contextData = '${user}';
		var formName = '${queryFromName}';
		var filePath="${filePath}";
		var validateFile = "${validateFile}";
</script>
<head>
<%@ include file="../common/head.jsp"%>
</head>
<body>
<%@ include file="../common/navbar.jsp"%>
     <div class="container bs-docs-header" style="magin-left:10px;" id ="swift">
	     	<button type="button" class="btn btn-warning" id="statistic">切换</button>

			<!-- Provides extra visual weight and identifies the primary action in a set of buttons -->
			<button type="button" class="btn btn-primary" id="export">导出</button>

			<!-- Indicates a successful or positive action -->
			<button type="button" class="btn btn-success" id="query">查询</button>

			<!-- Contextual button for informational alert messages -->
			<button type="button" class="btn btn-info" id="close">返回</button>
		<div id="pp"  style="background:none;border-left:1px solid #ccc;float:right;padding-right:15px;"></div> 
		<div id="pps"  style="background:#efefef;border:1px solid #ccc;float:right;"></div> 
	</div>
	<div  class="bbcx_box display_1" align="center" id="out">
		<div class="" align="center" id="content"></div>
		<div  align="center" id="statisticContent"></div>
	</div>

	<div id="win"></div> 
	<div id="message"></div>
</body>
<script>
  seajs.use('excel/index');
</script>
</html>