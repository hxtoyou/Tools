<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
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
    <div class="row">
      <form class="form-horizontal" method="post" action="/document/fileUpload" enctype="multipart/form-data">
      <div class="form-group">
        <label for="inputEmail3" class="col-sm-2 control-label">文件类型</label>
        <div class="col-sm-8">
          <select class="form-control" name="fileType">
              <option value="project">Project&MilePost</option>
              <option value="invoice">Invoice&Collection</option>
            </select>
        </div>
      </div>
      <div class="form-group">
        <label for="inputPassword3" class="col-sm-2 control-label">文件名</label>
        <div class="col-sm-8">
          <input id="input-id" name="fileName" type="file" class="file">
        </div>
      </div>
 
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-8">
          <button type="submit" id="submit" class="btn btn-default">Submit</button>
        </div>
      </div>
    </form>
       
    </div>
</div>
<div class="container-fluid">
    <table id="grid-selection" class="table table-condensed table-hover table-striped table-bordered ">
	    <thead>
	        <tr>
	            <th data-column-id="id" data-type="numeric" data-identifier="true">ID</th>
	            <th data-column-id="name">name</th>
	            <th data-column-id="path" data-order="desc">path</th>
	            <th data-column-id="time" data-formatter="time" data-sortable="false">time</th>
                <th data-column-id="isParase" data-formatter="isParase" data-sortable="false">isParase</th>
	        </tr>
	    </thead>
	</table>
</div>

<div id="fileDiv" style="display:none;">
	   <form id="form">
          <div class="form-group">
            <label for="exampleInputEmail1">FileType</label>
            <select class="form-control" name="fileType">
              <option value="Project&MilePost">Project&MilePost</option>
              <option value="Invoice&Collection">Invoice&Collection</option>
            </select>
          </div>
          <div class="form-group">
            <label for="exampleInputFile">File input</label>
            <input id="input-id" name="fileName" type="file" class="file">
          </div>
        </form>
</div>
</body>
<script>
  seajs.use(['jquery','fileinput'],function($,fileinput){
	  fileinput($);
	});
  seajs.use('common/index');
</script>
</html>