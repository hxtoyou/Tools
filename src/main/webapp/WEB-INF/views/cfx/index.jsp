<!DOCTYPE html>
<html lang="zh-CN">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<head>
	<title>tools</title>
	<%@ include file="../common/head.jsp"%>
</head>
<body>


<%@ include file="../common/navbar.jsp"%>
<div class="container-fluid">
  <div>
  	<input class="btn btn-default" type="button" value="Input" id="cfxbtn">
  </div>
</div>
<div class="container-fluid">
  <div class="row">
    <div class="col-xs-12 col-md-12" style='height:400px;' id='messg'>
      
    </div>
  </div>
</div>

</body>
<script>
  seajs.use('cxf/index');
</script>
</html>