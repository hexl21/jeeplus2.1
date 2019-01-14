<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webpage/E-books/css/base.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webpage/E-books/css/style.css"/>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webpage/E-books/js/jquery.1.8.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/webpage/E-books/js/book.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webpage/E-books/js/jquery.1.8.2.min.js"></script>
    <script type="text/javascript">
        $(function () {
            var bid = $("#booksid").val();
            var cid = $("#chapterid").val();
            console.log(bid)
            console.log(cid)
            $.ajax({
                url: "${pageContext.request.contextPath}/insertHistory",
                data: {bookid: bid, chapterid: cid},
                dataType: "JSON",
                type: "post",
                success: function (dta) {

                    //alert("2");
                    // console.log(dta.rows);

                },
            });
        })
    </script>
    <title>书籍详情</title>
</head>
<body>
<input type="hidden" value="${chapter.books.id}" id="booksid">
<input type="hidden" value="${chapter.id}" id="chapterid">
<div class="index-body">
    <div class="deta-head">
        <a href="${pageContext.request.contextPath}/webpage/E-books/index.jsp" class="deta-index"><i><img
                src="${pageContext.request.contextPath}/webpage/E-books/images/deta-home.png"/></i>首页</a>
        <div class="deta-htxt">
            <a href="${pageContext.request.contextPath}/webpage/E-books/personal-history-a.jsp">最近阅读</a>
            <a href="${pageContext.request.contextPath}/webpage/E-books/personal.jsp">个人中心</a>
        </div>
        <input class="deta-set" type="button" value="设置"/>
    </div>
    <div class="deta-ctxt">
        <div class="deta-ctou">
            <i><img src="${pageContext.request.contextPath}/webpage/E-books/images/deta-tou.png"/></i>
            <span>${chapter.name}</span>
        </div>
        <div class="deta-cp" style="width: 100%;">
            <p>${chapter.content}</p>
            <%--<p>国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感，国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感...</p>--%>
            <%--<p>国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感...国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感。</p>
            <p>国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感...国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感...国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感...国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感...国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感...国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感！</p>
            <p>国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感...国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感...国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感...国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感...</p>
            <p>国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感...国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感...国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代婚姻生存指南都市情感...国内首部婚姻疗愈系经典作品，*温暖感人的情感小说，传递婚姻正能量！ 这是一本*真实、*贴切的新生代。</p>
            --%>
        </div>
    </div>
    <div class="book-more-div">
        <div class="book-set">
            <div class="book-a">
                <span class="book-span">字号</span>
                <ul>
                    <li class="book-sma"><p>小</p></li>
                    <li class="book-midd"><p>中</p></li>
                    <li class="book-big"><p>大</p></li>
                </ul>
            </div>
            <div class="book-b">
                <span class="book-span">背景</span>
                <ul>
                    <li><p class="book-pa"></p></li>
                    <li><p class="book-pb"></p></li>
                    <li><p class="book-pc"></p></li>
                    <li><p class="book-pd"></p></li>
                    <li><p class="book-pe"></p></li>
                </ul>
            </div>
        </div>
        <div class="book-more">
            <ul>
                <li>
                    <a href="${pageContext.request.contextPath}/selectOneBookDetailsb?id=${chapter.books.id}">
                        <i><img src="${pageContext.request.contextPath}/webpage/E-books/images/book-a.png"/></i>
                        <span>目录</span>
                    </a>
                </li>
                <li class="book-aset">
                    <a href="###">
                        <i><img src="${pageContext.request.contextPath}/webpage/E-books/images/book-b1.png"/></i>
                        <span>设置</span>
                    </a>
                </li>
                <li class="book-aday">
                    <a href="###">
                        <i><img src="${pageContext.request.contextPath}/webpage/E-books/images/book-c1.png"/></i>
                        <span id="bookday">白天</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/webpage/E-books/personal-collection.jsp">
                        <i><img src="${pageContext.request.contextPath}/webpage/E-books/images/book-d.png"/></i>
                        <span>收藏</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
