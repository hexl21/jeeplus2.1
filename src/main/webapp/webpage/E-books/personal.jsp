<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
	<head>
		<meta charset="utf-8" />
    	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/webpage/E-books/css/base.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/webpage/E-books/css/style.css"/>
		<script type="text/javascript"
				src="${pageContext.request.contextPath}/webpage/E-books/js/jquery.1.8.2.min.js"></script>
		<script type="text/javascript"
				src="${pageContext.request.contextPath}/webpage/E-books/js/TouchSlide.1.1.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/webpage/E-books/js/book.js"></script>
		<title>个人中心</title>
	</head>
<body>
<div class="index-head">
<div class="header">
	<ul>
		<li><a href="${pageContext.request.contextPath}/webpage/E-books/index.jsp">
			<i><img src="${pageContext.request.contextPath}/webpage/E-books/images/index-h1.png"/></i>
				<span>首页</span>
		</a></li>
		<li><a href="${pageContext.request.contextPath}/webpage/E-books/library.jsp">
			<i><img src="${pageContext.request.contextPath}/webpage/E-books/images/index-h2.png"/></i>
				<span>书库</span>
		</a></li>
		<li><a href="${pageContext.request.contextPath}/webpage/E-books/list.jsp">
			<i><img src="${pageContext.request.contextPath}/webpage/E-books/images/index-h3.png"/></i>
				<span>榜单</span>
		</a></li>
		<li><a href="${pageContext.request.contextPath}/webpage/E-books/search.jsp">
			<i><img src="${pageContext.request.contextPath}/webpage/E-books/images/index-h4.png"/></i>
				<span>搜索</span>
		</a></li>
		<li><a href="${pageContext.request.contextPath}/webpage/E-books/top-up.jsp">
			<i><img src="${pageContext.request.contextPath}/webpage/E-books/images/index-h5.png"/></i>
				<span>充值</span>
		</a></li>
	</ul>
</div>
</div>
<div class="personal">
	<div class="per-tou">
		<i><img src="${sessionScope.rows.users.portraitpic}"/></i>
		<h3>${sessionScope.rows.users.username}</h3>
		<c:if test="${sessionScope.rows.users.money!=''}">
			<p>ID：${sessionScope.rows.users.userid}&nbsp;&nbsp;&nbsp;书币:${sessionScope.rows.users.money}</p>
		</c:if>
		<c:if test="${sessionScope.rows.users.money==''}">
			<p>ID：${sessionScope.rows.users.userid}&nbsp;&nbsp;&nbsp;书币:0</p>
		</c:if>
	</div>
	<div class="per-ul clearfix">
		<ul>
			<li><a href="${pageContext.request.contextPath}/webpage/E-books/list.jsp">
				<i><img src="${pageContext.request.contextPath}/webpage/E-books/images/per-a.png"/></i>
				<span>榜单</span>
			</a></li>
			<li><a href="${pageContext.request.contextPath}/webpage/E-books/top-up.jsp">
				<i><img src="${pageContext.request.contextPath}/webpage/E-books/images/per-b.png"/></i>
				<span>充值</span>
			</a></li>
			<li class="per-sign"><a href="###">
				<i><img src="${pageContext.request.contextPath}/webpage/E-books/images/per-c.png"/></i>
				<span>签到</span>
			</a></li>
			<li><a href="${pageContext.request.contextPath}/webpage/E-books/search.jsp">
				<i><img src="${pageContext.request.contextPath}/webpage/E-books/images/per-d.png"/></i>
				<span>搜索</span>
			</a></li>
			<li><a href="${pageContext.request.contextPath}/selectAllHistory">
				<i><img src="${pageContext.request.contextPath}/webpage/E-books/images/per-e.png"/></i>
				<span>阅读历史</span>
			</a></li>
            <li><a href="${pageContext.request.contextPath}/selectAllBuychapter">
				<i><img src="${pageContext.request.contextPath}/webpage/E-books/images/per-f.png"/></i>
				<span>消费历史</span>
			</a></li>
            <li><a href="${pageContext.request.contextPath}/selectAllEnshrine">
				<i><img src="${pageContext.request.contextPath}/webpage/E-books/images/per-g.png"/></i>
				<span>收藏</span>
			</a></li>
			<li><a href="${pageContext.request.contextPath}/webpage/E-books/personal-help.jsp">
				<i><img src="${pageContext.request.contextPath}/webpage/E-books/images/per-h.png"/></i>
				<span>帮助中心</span>
			</a></li>
			<li><a href="###">
				<i><img src="${pageContext.request.contextPath}/webpage/E-books/images/per-i.png"/></i>
				<span>敬请期待</span>
			</a></li>
		</ul>
	</div>
	<div class="sign-in">
		<div class="sign-div">
			<i><img src="${pageContext.request.contextPath}/webpage/E-books/images/sign-liwu.png"/></i>
			<p>签到送礼</p>
			<h3>每日签到可获得50书币奖励</h3>
			<h2>签到成功</h2>
			<h2>您今天已签到过，明天再来吧</h2>
		</div>
	</div>
</div>
</body>
</html>
