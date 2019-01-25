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

            function buychapat(id) {


                var member = $("#memberid").val();
                var bookName = $("#bookNameid").val();
                // alert(member=="1")
                //会员
                if (member == "1") {
                    // alert(member=="1")
                    window.location.href = "${pageContext.request.contextPath}/pageSkip?id=" + id;
                    //非会员
                } else if (member == "0") {
                    // alert("非会员  "+id);

                    $.ajax({
                        url: "${pageContext.request.contextPath}/selectOneChapterCharge",
                        data: {id: id},
                        dataType: "JSON",
                        type: "post",
                        success: function (dta) {

                            //免费
                            if (dta.charge == "0") {
                                // alert("免费 "+id);
                                window.location.href = "${pageContext.request.contextPath}/pageSkip?id=" + id;

                                //付费
                            } else if (dta.charge == "1") {
                                $.ajax({
                                    url: "${pageContext.request.contextPath}/selectOneBuychapter",
                                    data: {chapterid: id},
                                    dataType: "JSON",
                                    type: "post",
                                    success: function (dta) {
                                        // alert(dta.bool)
                                        //已购买
                                        if (dta.bool) {
                                            window.location.href = "${pageContext.request.contextPath}/pageSkip?id=" + id;
                                            // alert("000000000")
                                            //未购买
                                        } else {
                                            var money = $("#moneyid").val();
                                            // alert("书币："+money)
                                            $.ajax({
                                                url: "${pageContext.request.contextPath}/judgeMoney",
                                                data: {id: id},
                                                dataType: "JSON",
                                                type: "post",
                                                success: function (dta) {
                                                    // alert(dta.bool);
                                                    if (dta.bool) {
                                                        var con = confirm("是否购买此章节？");
                                                        if (con) {
                                                            // alert(con)
                                                            $.ajax({
                                                                url: "${pageContext.request.contextPath}/insertChapterAndUpdateUsers",
                                                                data: {chapterid: id, bookname: bookName},
                                                                dataType: "JSON",
                                                                type: "post",
                                                                success: function (dta) {
                                                                    window.location.href = "${pageContext.request.contextPath}/pageSkip?id=" + id;
                                                                }
                                                            });
                                                        } else {
                                                            // alert(con)
                                                        }
                                                    } else {
                                                        alert("书币不足，请用户充值！")

                                                    }
                                                }

                                            });


                                        }
                                    }
                                });
                            }

                        },
                    });

                }

            }


		</script>
		<title>书籍详情</title>
	</head>
<body style="background: #f1f1f1;">
<input type="hidden" id="memberid" value="${sessionScope.rows.users.member}"/>
<input type="hidden" id="moneyid" value="${sessionScope.rows.users.money}"/>
<input type="hidden" id="bookNameid" value="${DTO.bookName}"/>
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
    <div class="index-top" id="indextop">
        <img src="${pageContext.request.contextPath}/webpage/E-books/images/index-top.png" width="100%"/></div>
</div>
<div class="deta-more">
	<div class="index-center clearfix">
		<div class="cen-left">
            <i><img src="${sessionScope.rows.users.portraitpic}"/></i>
            <span>${sessionScope.rows.users.username}</span>
		</div>
		<a class="cen-a" href="${pageContext.request.contextPath}/webpage/E-books/personal.jsp">个人中心</a>
	</div>
	<div class="index-lunbo">
		<p class="index-time"><i><img src="${pageContext.request.contextPath}/webpage/E-books/images/index-a6.png"/></i>上次阅读到：《官场红颜路》第1章
		</p>
		<div class="lunbo-book">
			<div class="lunb-img"><img src="${DTO.bookPic}"/></div>
			<%--			<div class="lunb-txt">
                            <h1>${sessionScope.rows.rows.books.bookName}</h1>
                            <h3>因为痛，所以叫婚姻</h3>
                            <p>国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感.</p>
                            <p class="lunb-style">都市言情</p>
                            <p>4613人阅读</p>
                        </div>--%>
			<div class="lunb-txt">
				<%--<h1>${sessionScope.rows.rows}</h1>--%>
				<h3>${DTO.bookName}</h3>
				<p>${DTO.bookIntro}</p>
				<p class="lunb-style">${DTO.cname}</p>
				<p>${DTO.bookReadnumber}人阅读</p>
			</div>
		</div>		
	</div>
	<div class="deta-ul">
		<a class="deta-go" href="###">继续阅读</a>
		<h3><i><img src="${pageContext.request.contextPath}/webpage/E-books/images/deta-bg.png"/></i>目录</h3>
		<ul>
			<c:forEach var="ite" items="${DTO.chapterLists}" end="5">
				<c:if test="${ite.charge=='0'}">
                    <li>
                        <a href="javascript:void(0)" onclick="buychapat('${ite.id}')"><span
                                style="width: 100%">${ite.name}</span></a>
					</li>
				</c:if>
				<c:if test="${ite.charge=='1'}">
					<li>
                            <%--<a href="${pageContext.request.contextPath}/pageSkip?id=${ite.id}"><span>${ite.name}</span><i><img--%>
                        <a href="javascript:void(0)" onclick="buychapat('${ite.id}')"><span>${ite.name}</span>
                            <i>
                                <img src="${pageContext.request.contextPath}/webpage/E-books/images/deta-zs.png"/>
                            </i>
                        </a>
					</li>
				</c:if>
			</c:forEach>
			<%--		<li><a href="###"><span>第1章</span></a></li>
                    <li><a href="###"><span>第2章</span></a></li>
                    <li><a href="###"><span>第3章</span></a></li>
                    <li><a href="###"><span>第4章</span></a></li>
                    <li><a href="###"><span>第5章</span></a></li>
                    <li><a href="###"><span>第6章</span></a></li>
                    <li><a href="###"><span>第7章</span></a></li>
                    <li><a href="###"><span>第8章</span></a></li>--%>
		</ul>
		<a class="deta-all" href="${pageContext.request.contextPath}/selectOneBookDetailsb?id=${DTO.id}">全部目录</a>
	</div>
</div>
</body>
</html>