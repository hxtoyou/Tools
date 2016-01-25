<!DOCTYPE html>
<html lang="zh-CN">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<head>
	<title>tools</title>
	<%@ include file="common/head.jsp"%>
</head>
<body>


<%@ include file="common/navbar.jsp"%>
<div class="container-fluid">
  <div class="jumbotron">
    <h1>Tools, Conllection!</h1>
    <p>        all be projecting!</p>
    <!-- <p><a class="btn btn-primary btn-lg" href="#" role="button">Check more</a></p> -->
  </div>
</div>
<div class="container-fluid">
  <div class="row">
    <div class="col-xs-12 col-md-6">
      <a href="/excel/createForm" class="thumbnail">
        <img src="${resources}/images/excel.png" alt="...">
      </a>
    </div>
    <div class="col-xs-12 col-md-6">
      <a href="/echart/index" class="thumbnail">
        <img src="${resources}/images/charts2.jpg" alt="...">
      </a>
    </div>
  </div>
</div>

</body>
<script>
  seajs.use('common/index');
</script>
</html>