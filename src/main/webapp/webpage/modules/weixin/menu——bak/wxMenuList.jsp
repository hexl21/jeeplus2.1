<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>自定义菜单管理</title>
	<meta name="decorator" content="ani"/>
	<%@include file="wxMenuList.js" %>
</head>
<body>

	<div class="wrapper wrapper-content">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">自定义菜单列表  </h3>
			</div>

			<div class="panel-body">

			<!-- 工具栏 -->
			<div class="row">
			<div class="col-sm-12">
				<div class="pull-left treetable-bar">
					<shiro:hasPermission name="weixin:wxMenu:add">
						<a id="add" class="btn btn-primary" onclick="jp.openSaveDialog('新建自定义菜单', '${ctx}/weixin/wxMenu/form','800px', '500px')"><i class="glyphicon glyphicon-plus"></i> 新建</a><!-- 增加按钮 -->
					</shiro:hasPermission>
                        <button id="publish" class="btn btn-success"  onclick="publish()">
                        <i class="glyphicon glyphicon-edit"></i>同步菜单
                        </button>
                        <button class="btn btn-default" data-toggle="tooltip" data-placement="left" onclick="refresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>


				</div>
			</div>
			</div>
			<table id="wxMenuTreeTable" class="table table-hover">
				<thead>
					<tr>
						<th>名称</th>
						<th>排序</th>
						<th>更新时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="wxMenuTreeTableList"></tbody>
			</table>
			<br/>
			</div>
			</div>
	</div>
</body>
</html>