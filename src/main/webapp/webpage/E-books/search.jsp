<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
	<head>
		<meta charset="utf-8" />
    	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
		<link rel="stylesheet" href="css/base.css" />
		<link rel="stylesheet" href="css/style.css" />
		<script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
		<script type="text/javascript" src="js/TouchSlide.1.1.js" ></script>
		<script type="text/javascript" src="js/book.js" ></script>
		<title>搜索</title>
	</head>
<body>
<div class="index-head">
<div class="header">
	<ul>
		<li><a href="index.jsp">
				<i><img src="images/index-h1.png"/></i>
				<span>首页</span>
		</a></li>
		<li><a href="library.jsp">
				<i><img src="images/index-h2.png"/></i>
				<span>书库</span>
		</a></li>
		<li><a href="list.jsp">
				<i><img src="images/index-h3.png"/></i>
				<span>榜单</span>
		</a></li>
		<li class="on"><a href="search.jsp">
				<i><img src="images/index-h4.png"/></i>
				<span>搜索</span>
		</a></li>
		<li><a href="top-up.jsp">
				<i><img src="images/index-h5.png"/></i>
				<span>充值</span>
		</a></li>
	</ul>
</div>
</div>
<div class="search">
	<h1>搜索</h1>
	<div class="sea-inp">
		<span>书名</span>
		<input type="text" placeholder="请输入书名"/>
	</div>
	<div class="sea-btn">
		<input type="button" value="搜索" />
	</div>
	<div class="sea-ul">
		<h3>热门搜索：</h3>
		<ul class="sea-ul-a">
			<li><a href="###">妻子的秘密2</a></li>
			<li><a href="###">妻子的秘密2</a></li>
			<li><a href="###">妻子的秘密2</a></li>
			<li><a href="###">妻子的秘密2</a></li>
		</ul>
		<ul class="sea-ul-b">
			<li><a href="###">高管的未婚妻</a></li>
			<li><a href="###">高管的未婚妻</a></li>
			<li><a href="###">高管的未婚妻</a></li>
			<li><a href="###">高管的未婚妻</a></li>
		</ul>
		<ul class="sea-ul-c">
			<li><a href="###">妻子的秘密2</a></li>
			<li><a href="###">妻子的秘密2</a></li>
			<li><a href="###">妻子的秘密2</a></li>
			<li><a href="###">妻子的秘密2</a></li>
		</ul>
	</div>
</div>
</body>
</html>
