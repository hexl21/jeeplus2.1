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
            function daleteOneBookEnshrine(id) {
                // alert(id);
                //删除历史=======(START)=======
                $.ajax({
                    url: "${pageContext.request.contextPath}/daleteOneBookEnshrine",
                    dataType: "JSON",
                    data: {id: id},
                    type: "post",
                    success: function (dta) {
                        alert(dta);
                        parent.location.reload();
                    },
                });
                //删除历史========(END)========
            }
        </script>
		<title>收藏</title>
	</head>
<body>
<div class="index-head">
<div class="header">
	<ul>
        <li class="on"><a href="${pageContext.request.contextPath}/webpage/E-books/index.jsp">
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
<div class="collection">
	<h3 class="coll-tou">收藏</h3>
	<ul>
        <c:forEach items="${eash}" var="ites">
            <li>
                <div class="coll-img"><img src="${ites.bookpic}"/></div>
                <div class="coll-txt clearfix">
                    <a class="coll-h"
                       href="${pageContext.request.contextPath}/selectOneBookDetails?id=${ites.bookid}">${ites.bookname}</a>
                    <p>${ites.chaptername}</p>
                    <a class="coll-a" href="${pageContext.request.contextPath}/pageSkip?id=${ites.chapterid}">继续阅读</a>
                    <p><fmt:formatDate value="${ites.date}" pattern="yyyy-MM-dd"/></p>
                    <input type="button" value="删除" onclick="daleteOneBookEnshrine('${ites.id}')"/>
                </div>
            </li>
        </c:forEach>


        <%--		<li>
                    <div class="coll-img"><img src="images/coll-bg.png"/></div>
                    <div class="coll-txt clearfix">
                        <a class="coll-h" href="###">凉生我们可不可以不忧伤</a>
                        <a class="coll-a" href="###">继续阅读</a>
                        <p>2018-10-18</p>
                        <input type="button" value="删除" />
                    </div>
                </li>--%>
	</ul>
</div>
</body>
</html>
