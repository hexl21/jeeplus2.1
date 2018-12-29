<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>树的测试管理</title>
	<meta name="decorator" content="ani"/>
	<%@include file="treeList.js" %>
</head>
<body>

	<div class="wrapper wrapper-content">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">树的测试列表  </h3>
			</div>
			
			<div class="panel-body">
	
			<!-- 工具栏 -->
			<div class="row">
			<div class="col-sm-12">
				<div class="pull-left treetable-bar">
					<shiro:hasPermission name="tree:tree:tree:add">
						<a id="add" class="btn btn-primary" onclick="jp.openSaveDialog('新建树的测试', '${ctx}/tree/tree/tree/form','800px', '500px')"><i class="glyphicon glyphicon-plus"></i> 新建</a><!-- 增加按钮 -->
					</shiro:hasPermission>
			       <button class="btn btn-default" data-toggle="tooltip" data-placement="left" onclick="refresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
				
				</div>
			</div>
			</div>
			<table id="treeTreeTable" class="table table-hover">
				<thead>
					<tr>
						<th>备注信息</th>
						<th>名称</th>
						<th>省</th>
						<th>市</th>
						<th>县</th>
						<th>镇</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="treeTreeTableList"></tbody>
			</table>
			<br/>
			</div>
			</div>
	</div>
</body>
</html>