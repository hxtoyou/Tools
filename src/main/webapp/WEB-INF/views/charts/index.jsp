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
  <div class="jumbotron">
    <h1>Echarts</h1>
    <p>        all be projecting!</p>
    <!-- <p><a class="btn btn-primary btn-lg" href="#" role="button">Check more</a></p> -->
  </div>
</div>
<div class="container-fluid">
  <div class="row">
    <div class="col-xs-12 col-md-12" style='height:400px;' id='charts'>
      
    </div>
  </div>
</div>

</body>
<script>
  seajs.use('charts/index');
</script>
</html>