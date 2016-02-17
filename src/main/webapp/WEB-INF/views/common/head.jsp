<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="resources" value="${pageContext.request.contextPath}/static-resources" />
<c:set var="requestURI" value="${pageContext.request.requestURI}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta charset="UTF-8">
<link href="${resources}/scripts/spm_modules/bootstrap/3.3.1/css/bootstrap.css" rel="stylesheet">
<link href="${resources}/scripts/spm_modules/bootstrap/dialog/css/bootstrap-dialog.min.css" rel="stylesheet">
<link href="${resources}/scripts/spm_modules/bootstrap/grid/jquery.bootgrid.min.css" rel="stylesheet">
<link href="${resources}/scripts/spm_modules/bootstrap/fileInput/fileinput.css" rel="stylesheet">
<link href="${resources}/scripts/spm_modules/bootstrap/date/bootstrap-datepicker.css" rel="stylesheet">
<link href="${resources}/scripts/spm_modules/jquery-ui/1.11.1/jquery-ui.css" rel="stylesheet">

<script src="${resources}/scripts/spm_modules/seajs/2.3.0/dist/sea.js" type="text/javascript"></script>
<script type="text/javascript">
// define(function(require,exports,module){
  var contextPath = "${contextPath}";
  var resources   = "${resources}";
  var requestURI  = "${requestURI}";
// 
</script>
<script type="text/javascript">
	seajs.config({
		base : "${resources}/app",
		alias : {
	      'jquery'    : '${resources}/scripts/spm_modules/jquery/2.1.4/jquery',
	      'bootstrap' : '${resources}/scripts/spm_modules/bootstrap/3.3.1/js/bootstrap',
	      'bootstrapDialog' : '${resources}/scripts/spm_modules/bootstrap/dialog/js/bootstrap-dialog',
	      'bootstrapGrid' : '${resources}/scripts/spm_modules/bootstrap/grid/jquery.bootgrid',
	      'fileinput' : '${resources}/scripts/spm_modules/bootstrap/fileInput/fileinput',
	      'datepicker' : '${resources}/scripts/spm_modules/bootstrap/date/bootstrap-datepicker',
	      'fileLocale' : '${resources}/scripts/spm_modules/bootstrap/fileInput/fileinput_locale_zh',
	      'echarts'   : '${resources}/scripts/spm_modules/echarts/3.0.0/echarts.common.min.js'
		},
		vars: {
			'locale': 'zh'
		},
		debug: true,
		charset: 'utf-8'
	});
	
</script>
