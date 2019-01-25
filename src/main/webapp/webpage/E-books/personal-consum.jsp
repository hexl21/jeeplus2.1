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
		<script type="text/javascript">
            <%----%>
            <%--$(function () {--%>

            <%--$.ajax({--%>
            <%--url: "${pageContext.request.contextPath}/selectAllBuychapter",--%>
            <%--dataType: "JSON",--%>
            <%--type: "post",--%>
            <%--async: false,--%>
            <%--success: function (dta) {--%>
            <%--console.log(dta.buychapterList);--%>

            <%--var expenseHistory = "";--%>
            <%--for (var i = 0; i <dta.buychapterList.length; i++) {--%>

            <%--expenseHistory ="<li>\n"+--%>
            <%--"\t\t\t\t<div class=\"con-a clearfix\">\n" +--%>
            <%--"\t\t\t\t\t<p class=\"con-left\">2018-10-18 20:30:24</p>\n" +--%>
            <%--"\t\t\t\t\t<p class=\"con-right\">已消费<i><img src=\"images/con-zuo.png\"/></i></p>\n" +--%>
            <%--"\t\t\t\t</div>\n" +--%>
            <%--"\t\t\t\t<div class=\"con-b clearfix\">\n" +--%>
            <%--"\t\t\t\t\t<div class=\"con-left\">\n" +--%>
            <%--"\t\t\t\t\t\t<h3>凉生、我们可不可以不忧伤</h3><p>章节：第28章</p>\n" +--%>
            <%--"\t\t\t\t\t</div>\n" +--%>
            <%--"\t\t\t\t\t<p class=\"con-right\">￥30</p>\n" +--%>
            <%--"\t\t\t\t</div>\n" +--%>
            <%--"\t\t\t</li>";--%>
            <%--}--%>

            <%--$("#expenseHistoryid").html(expenseHistory);--%>
            <%--}--%>
            <%--});--%>
            <%--})--%>

		</script>
		<title>我的消费</title>
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
<div class="consume">
	<h3 class="con-h">我的消费</h3>
	<div class="con-tou clearfix">
		<p class="con-left">消费时间</p>
		<p class="con-right">消费金额</p>
	</div>
	<div class="consu-ul">
		<ul id="expenseHistoryid">
			<c:forEach var="ite" items="${buychapterList}">
				<li>
					<div class="con-a clearfix">
						<p class="con-left"><fmt:formatDate value="${ite.createDate}"
															pattern="yyyy-MM-dd hh:mm:ss"/></p>
						<p class="con-right">已消费<i><img src="webpage/E-books/images/con-zuo.png"/></i></p>
					</div>
					<div class="con-b clearfix">
						<div class="con-left">
							<h3>${ite.bookname}</h3>
							<p>章节：${ite.chaptername}</p>
						</div>
						<p class="con-right">￥${ite.chaptermoney}</p>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
</body>
</html>
